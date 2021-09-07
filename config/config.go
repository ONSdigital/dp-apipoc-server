package config

import (
	"github.com/kelseyhightower/envconfig"
)

// Configuration structure which hold information for configuring the import API
type Configuration struct {
	ElasticsearchURL string `envconfig:"ELASTICSEARCH_ROOT"`
	Port             int    `envconfig:"API_APP_PORT"`
	UseWebsite       bool   `envconfig:"USE_WEBSITE"`
	WebsiteURL       string `envconfig:"WEBSITE_ROOT"`
	ZebedeeURL       string `envconfig:"ZEBEDEE_ROOT"`
}

var cfg *Configuration

// Get the application and returns the configuration structure
func Get() (*Configuration, error) {
	if cfg != nil {
		return cfg, nil
	}

	cfg = &Configuration{
		ElasticsearchURL: "http://127.0.0.1:9200",
		Port:             3000,
		UseWebsite:       false,
		WebsiteURL:       "https://www.ons.gov.uk",
		ZebedeeURL:       "http://127.0.0.1:8082",
	}

	return cfg, envconfig.Process("", cfg)
}
