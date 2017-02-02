package upstream

import (
	"net"
	"net/http"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/model"

	/*
		"encoding/json"

		"github.com/ONSdigital/go-ns/log"
	*/)

type WebsiteClient interface {
	Ping() (model.Response, error)
	GetData(uri string) (model.Response, error)
}

func NewWebsiteClient(baseUrl string) WebsiteClient {
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

	return &httpService{baseUrl: baseUrl, httpClient: netClient}
}

type httpService struct {
	baseUrl    string
	httpClient *http.Client
}

func (s *httpService) Ping() (model.Response, error) {
	resp, err := s.httpClient.Get(s.baseUrl)

	if err != nil {
		return model.Response{Code: model.DEPENDENCY_CONNECTION_ERROR, Body: nil}, err
	}
	if resp.StatusCode == http.StatusOK {
		return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "RUNNING", StatusCode: resp.StatusCode}}, nil
	}

	return model.Response{Code: model.OK, Body: &model.WebsiteStatus{Status: "NOT_AVAILABLE", StatusCode: resp.StatusCode}}, err
}

func (s *httpService) GetData(uri string) (model.Response, error) {
	return model.Response{Code: model.OK, Body: nil}, nil
}
