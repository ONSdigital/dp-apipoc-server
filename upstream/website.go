package upstream

import (
	"bytes"

	"github.com/ONSdigital/dp-apipoc-server/model"
)

type WebsiteClient interface {
	Ping() (model.Response, error)
	GetData(uri string) (model.Response, error)
	buildRequest(uri string) string
}

func NewWebsiteClient(baseUrl string) WebsiteClient {
	return &websiteService{baseUrl: baseUrl, httpClient: NewHttpClient()}
}

type websiteService struct {
	baseUrl    string
	httpClient HttpClient
}

func (w *websiteService) Ping() (model.Response, error) {
	return w.httpClient.Ping(w.baseUrl)
}

func (w *websiteService) GetData(uri string) (model.Response, error) {
	url := w.buildRequest(uri)

	return w.httpClient.GetData(url)
}

func (w *websiteService) buildRequest(uri string) string {
	var buffer bytes.Buffer

	buffer.WriteString(w.baseUrl)
	buffer.WriteString(uri)
	buffer.WriteString("/data")

	return buffer.String()
}
