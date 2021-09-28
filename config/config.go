package config

import (
	"github.com/kelseyhightower/envconfig"
)

// Configuration structure holds config information for the app
type Configuration struct {
	ElasticsearchURL string `envconfig:"ELASTICSEARCH_ROOT"`
	Deprecation      Deprecation
	Port             int    `envconfig:"API_APP_PORT"`
	UseWebsite       bool   `envconfig:"USE_WEBSITE"`
	WebsiteURL       string `envconfig:"WEBSITE_ROOT"`
	ZebedeeURL       string `envconfig:"ZEBEDEE_ROOT"`
}

type Deprecation struct {
	Date         string `envconfig:"DEPRECATION_DATE"`
	IsDeprecated bool   `envconfig:"IS_DEPRECATED"`
	Link         string `envconfig:"DEPRECATION_LINK"`
	Sunset       string `envconfig:"DEPRECATION_SUNSET"`
}

var cfg *Configuration

// Get the application and returns the configuration structure
func Get() (*Configuration, error) {
	if cfg != nil {
		return cfg, nil
	}

	cfg = &Configuration{
		ElasticsearchURL: "http://127.0.0.1:9200",
		Deprecation: Deprecation{
			Date:         "", // if set should be of format "Wed, 11 Nov 2020 23:59:59 GMT"
			IsDeprecated: false,
			Link:         "",
			Sunset:       "", // if set should be of format "Wed, 11 Nov 2020 23:59:59 GMT"
		},
		Port:       3000,
		UseWebsite: false,
		WebsiteURL: "https://www.ons.gov.uk",
		ZebedeeURL: "http://127.0.0.1:8082",
	}

	return cfg, envconfig.Process("", cfg)
}
