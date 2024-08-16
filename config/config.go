package config

import (
	"errors"
	"fmt"
	"sort"
	"strings"
	"time"

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

type Outage struct {
	Start time.Time
	End   time.Time
}

type Deprecation struct {
	Date                 string   `envconfig:"DEPRECATION_DATE"`
	HasDeprecationHeader bool     `envconfig:"HAS_DEPRECATION_HEADER"`
	Link                 string   `envconfig:"DEPRECATION_LINK"`
	Sunset               string   `envconfig:"DEPRECATION_SUNSET"`
	OutageStrings        []string `envconfig:"DEPRECATION_OUTAGES"`
	Outages              []Outage `envconfig:"-"`
}

const deprecationErrorMessage = "cannot have configuration for deprecation variables for " +
	"DEPRECATION_DATE,DEPRECATION_LINK,DEPRECATION_SUNSET or DEPRECATION_OUTAGES " +
	"if HAS_DEPRECATION_HEADER is false"

var cfg *Configuration

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

	if err := envconfig.Process("", cfg); err != nil {
		return nil, err
	}
	if err := cfg.Validate(); err != nil {
		return nil, err
	}
	return cfg, nil
}

func (cfg *Configuration) Validate() error {

	if !cfg.Deprecation.HasDeprecationHeader {
		if cfg.Deprecation.Date != "" || cfg.Deprecation.Link != "" || cfg.Deprecation.Sunset != "" || len(cfg.Deprecation.OutageStrings) > 0 {
			return errors.New(deprecationErrorMessage)
		}
	}

	if len(cfg.Deprecation.OutageStrings) > 0 {
		// convert OutageStrings to Outages
		var err error
		for i, outageStr := range cfg.Deprecation.OutageStrings {
			if outagePairStr := strings.Split(outageStr, "@"); len(outagePairStr) == 2 {
				var periodLen time.Duration
				if periodLen, err = time.ParseDuration(outagePairStr[0]); err != nil {
					return fmt.Errorf("cannot parse `duration@...` in period %d: %w", i+1, err)
				}

				var periodStart time.Time
				if periodStart, err = time.Parse(time.DateTime, outagePairStr[1]); err != nil {
					return fmt.Errorf("cannot parse `...@time` in period %d: %w", i+1, err)
				}

				cfg.Deprecation.Outages = append(cfg.Deprecation.Outages, Outage{Start: periodStart, End: periodStart.Add(periodLen)})
			} else {
				return fmt.Errorf("expected `duration@time` in period %d", i+1)
			}
		}
		if len(cfg.Deprecation.Outages) > 1 {
			sort.Slice(cfg.Deprecation.Outages, func(i, j int) bool {
				return cfg.Deprecation.Outages[i].Start.Before(cfg.Deprecation.Outages[j].Start)
			})
		}
	}

	return nil
}
