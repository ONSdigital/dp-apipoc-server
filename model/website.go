package model

type WebsiteStatus struct {
	Status     string `json:"status"`
	StatusCode int    `json:"statusCode,omitempty"`
}
