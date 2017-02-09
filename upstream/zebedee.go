package upstream

import (
	"bytes"

	"github.com/ONSdigital/dp-apipoc-server/model"
)

type ZebedeeClient interface {
	Ping() (model.Response, error)
	GetData(uri string) (model.Response, error)
	buildRequest(uri string) string
}

func NewZebedeeClient(baseUrl string) ZebedeeClient {
	return &zebedeeService{baseUrl: baseUrl, httpClient: NewHttpClient()}
}

type zebedeeService struct {
	baseUrl    string
	httpClient HttpClient
}

func (z *zebedeeService) Ping() (model.Response, error) {
	return z.httpClient.Ping(z.baseUrl)
}

func (z *zebedeeService) GetData(uri string) (model.Response, error) {
	url := z.buildRequest(uri)

	return z.httpClient.GetData(url)
}

func (z *zebedeeService) buildRequest(uri string) string {
	var buffer bytes.Buffer

	buffer.WriteString(z.baseUrl)
	buffer.WriteString("/data?uri=")
	buffer.WriteString(uri)

	return buffer.String()
}
