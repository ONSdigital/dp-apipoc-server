package model

import "gopkg.in/olivere/elastic.v3"

type ElasticStatus struct {
	Status       string              `json:"status"`
	StatusCode   int                 `json:"statusCode,omitempty"`
	PingResponse *elastic.PingResult `json:"pingResponse,omitempty"`
}
