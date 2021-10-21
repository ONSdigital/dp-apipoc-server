package upstream

import (
	"bytes"
	"context"

	"github.com/ONSdigital/dp-apipoc-server/model"
)

type WebsiteClient interface {
	Ping(ctx context.Context) (model.Response, error)
	GetData(ctx context.Context, uri string) (model.Response, error)
	buildRequest(uri string) string
}

func NewWebsiteClient(baseUrl string) WebsiteClient {
	return &websiteService{baseUrl: baseUrl, httpClient: NewHttpClient()}
}

type websiteService struct {
	baseUrl    string
	httpClient HttpClient
}

func (w *websiteService) Ping(ctx context.Context) (model.Response, error) {
	return w.httpClient.Ping(ctx, w.baseUrl)
}

func (w *websiteService) GetData(ctx context.Context, uri string) (model.Response, error) {
	url := w.buildRequest(uri)

	return w.httpClient.GetData(ctx, url)
}

func (w *websiteService) buildRequest(uri string) string {
	var buffer bytes.Buffer

	buffer.WriteString(w.baseUrl)
	buffer.WriteString(uri)
	buffer.WriteString("/data")

	return buffer.String()
}
