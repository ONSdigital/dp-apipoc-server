package config

import (
	"context"
	"errors"

	"github.com/ONSdigital/log.go/v2/log"
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
	Date                 string `envconfig:"DEPRECATION_DATE"`
	HasDeprecationHeader bool   `envconfig:"HAS_DEPRECATION_HEADER"`
	Link                 string `envconfig:"DEPRECATION_LINK"`
	Sunset               string `envconfig:"DEPRECATION_SUNSET"`
}

var (
	cfg                     *Configuration
	deprecationErrorMessage = "cannot have configuration for deprecation variables for " +
		"DEPRECATION_DATE,DEPRECATION_LINK or DEPRECATION_SUNSET if HAS_DEPRECATION_HEADER is false"
)

// Get the application and returns the configuration structure
func Get() (*Configuration, error) {
	if cfg != nil {
		return cfg, nil
	}

	cfg = &Configuration{
		ElasticsearchURL: "http://127.0.0.1:9200",
		Deprecation: Deprecation{
			Date:                 "", // if set should be of format "Wed, 11 Nov 2020 23:59:59 GMT"
			HasDeprecationHeader: false,
			Link:                 "",
			Sunset:               "", // if set should be of format "Wed, 11 Nov 2020 23:59:59 GMT"
		},
		Port:       3000,
		UseWebsite: false,
		WebsiteURL: "https://www.ons.gov.uk",
		ZebedeeURL: "http://127.0.0.1:8082",
	}

	return cfg, envconfig.Process("", cfg)
}

func (d *Deprecation) Validate(ctx context.Context) error {
	log.Info(ctx, "checking deprecation headers configuration")

	if !d.HasDeprecationHeader {
		if d.Date != "" || d.Link != "" || d.Sunset != "" {
			return errors.New(deprecationErrorMessage)
		}
	}

	return nil
}
