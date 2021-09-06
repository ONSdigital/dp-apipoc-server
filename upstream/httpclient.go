package upstream

import (
	"context"
	"encoding/json"
	"io/ioutil"
	"net"
	"net/http"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/log.go/log"
)

type HttpClient interface {
	Ping(ctx context.Context, url string) (model.Response, error)
	GetData(ctx context.Context, url string) (model.Response, error)
}

func NewHttpClient() HttpClient {
	var netTransport = &http.Transport{
		Dial: (&net.Dialer{
			Timeout: 5 * time.Second,
		}).Dial,
		TLSHandshakeTimeout: 5 * time.Second,
	}
	var netClient = &http.Client{
		Timeout:   time.Second * 10,
		Transport: netTransport,
	}

	return &httpService{httpClient: netClient}
}

type httpService struct {
	httpClient *http.Client
}

func (s *httpService) Ping(ctx context.Context, url string) (model.Response, error) {
	resp, err := s.httpClient.Get(url)
	if err != nil {
		log.Event(ctx, "Ping: failed to retrieve response from http client", log.ERROR, log.Error(err))
		return model.Response{Code: model.DEPENDENCY_CONNECTION_ERROR, Body: nil}, err
	}

	if resp.StatusCode == http.StatusOK {
		return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "RUNNING", StatusCode: resp.StatusCode}}, nil
	}

	return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "NOT_AVAILABLE", StatusCode: resp.StatusCode}}, err
}

func (s *httpService) GetData(ctx context.Context, url string) (model.Response, error) {
	resp, err := s.httpClient.Get(url)

	if err != nil {
		log.Event(ctx, "GetData: failed to retrieve data", log.ERROR, log.Error(err))
		return model.Response{Code: model.ERROR, Body: nil}, err
	}

	if resp.StatusCode == http.StatusNotFound {
		return model.Response{Code: model.NOT_FOUND, Body: nil}, nil
	}

	if resp.StatusCode == http.StatusOK {
		defer resp.Body.Close()
		body, ierr := ioutil.ReadAll(resp.Body)

		if ierr != nil {
			log.Event(ctx, "GetData: failed to read response body", log.ERROR, log.Error(err))
			return model.Response{Code: model.ERROR, Body: nil}, ierr
		}

		var item interface{}
		e := json.Unmarshal(body, &item)
		if e != nil {
			log.Event(ctx, "GetData: failed to unmarshal response body")
			return model.Response{Code: model.ERROR, Body: nil}, e
		}

		return model.Response{Code: model.OK, Body: item}, nil
	}

	return model.Response{Code: model.ERROR, Body: nil}, nil
}
