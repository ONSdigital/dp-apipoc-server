package upstream

import (
	"encoding/json"
	"io/ioutil"
	"net"
	"net/http"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/go-ns/log"
)

type HttpClient interface {
	Ping(url string) (model.Response, error)
	GetData(url string) (model.Response, error)
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

func (s *httpService) Ping(url string) (model.Response, error) {
	resp, err := s.httpClient.Get(url)

	if err != nil {
		log.Error(err, nil)
		return model.Response{Code: model.DEPENDENCY_CONNECTION_ERROR, Body: nil}, err
	}
	if resp.StatusCode == http.StatusOK {
		return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "RUNNING", StatusCode: resp.StatusCode}}, nil
	}

	return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "NOT_AVAILABLE", StatusCode: resp.StatusCode}}, err
}

func (s *httpService) GetData(url string) (model.Response, error) {
	resp, err := s.httpClient.Get(url)

	if err != nil {
		log.Error(err, nil)
		return model.Response{Code: model.ERROR, Body: nil}, err
	}

	if resp.StatusCode == http.StatusNotFound {
		return model.Response{Code: model.NOT_FOUND, Body: nil}, nil
	}

	if resp.StatusCode == http.StatusOK {
		defer resp.Body.Close()
		body, ierr := ioutil.ReadAll(resp.Body)

		if ierr != nil {
			log.Error(ierr, nil)
			return model.Response{Code: model.ERROR, Body: nil}, ierr
		}

		var item interface{}
		e := json.Unmarshal(body, &item)
		if e != nil {
			log.Error(e, nil)
			return model.Response{Code: model.ERROR, Body: nil}, e
		}

		return model.Response{Code: model.OK, Body: item}, nil
	}

	return model.Response{Code: model.ERROR, Body: nil}, nil
}
