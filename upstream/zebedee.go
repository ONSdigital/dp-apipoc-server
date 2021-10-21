package upstream

import (
	"bytes"
	"context"

	"github.com/ONSdigital/dp-apipoc-server/model"
)

type ZebedeeClient interface {
	Ping(ctx context.Context) (model.Response, error)
	GetData(ctx context.Context, uri string) (model.Response, error)
	buildRequest(uri string) string
}

func NewZebedeeClient(baseUrl string) ZebedeeClient {
	return &zebedeeService{baseUrl: baseUrl, httpClient: NewHttpClient()}
}

type zebedeeService struct {
	baseUrl    string
	httpClient HttpClient
}

func (z *zebedeeService) Ping(ctx context.Context) (model.Response, error) {
	return z.httpClient.Ping(ctx, z.baseUrl)
}

func (z *zebedeeService) GetData(ctx context.Context, uri string) (model.Response, error) {
	url := z.buildRequest(uri)

	return z.httpClient.GetData(ctx, url)
}

func (z *zebedeeService) buildRequest(uri string) string {
	var buffer bytes.Buffer

	buffer.WriteString(z.baseUrl)
	buffer.WriteString("/data?uri=")
	buffer.WriteString(uri)

	return buffer.String()
}
