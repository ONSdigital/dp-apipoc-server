package model

type (
	Configuration struct {
		Port             int    `json:"serverPort"`
		LogDir           string `json:"logDir"`
		ElasticsearchUrl string `json:"elasticsearchUrl"`
	}
)
