package config

import (
	"context"
	"errors"
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func TestConfig(t *testing.T) {

	Convey("Given an environment with no environment variables set", t, func() {
		cfg, err := Get()

		Convey("When the config values are retrieved", func() {

			Convey("Then there should be no error returned", func() {
				So(err, ShouldBeNil)
			})

			Convey("The values should be set to the expected defaults", func() {
				So(cfg.ElasticsearchURL, ShouldEqual, "http://127.0.0.1:9200")
				So(cfg.Port, ShouldEqual, 3000)
				So(cfg.UseWebsite, ShouldEqual, false)
				So(cfg.WebsiteURL, ShouldEqual, "https://www.ons.gov.uk")
				So(cfg.ZebedeeURL, ShouldEqual, "http://127.0.0.1:8082")
				So(cfg.Deprecation.Date, ShouldEqual, "")
				So(cfg.Deprecation.HasDeprecationHeader, ShouldEqual, false)
				So(cfg.Deprecation.Link, ShouldEqual, "")
				So(cfg.Deprecation.Sunset, ShouldEqual, "")
			})
		})
	})
}

func TestSuccessfulConfiguredDeprecationValidation(t *testing.T) {

	Convey("Given an environment with no deprecation variables set", t, func() {
		cfg, err := Get()
		if err != nil {
			t.Fatalf("Unable to retrieve default configuration for test")
		}

		Convey("When the deprecation config values are validated", func() {
			err := cfg.Deprecation.Validate(context.Background())

			Convey("Then there should be no error returned", func() {
				So(err, ShouldBeNil)
			})
		})
	})

	Convey("Given an environment with all deprecation variables are set", t, func() {
		cfg, err := Get()
		if err != nil {
			t.Fatalf("Unable to retrieve default configuration for test")
		}

		cfg.Deprecation = Deprecation{
			Date:                 "Wed, 11 Nov 2020 23:59:59 GMT",
			HasDeprecationHeader: true,
			Link:                 "www.ons.gov.uk",
			Sunset:               "Wed, 29 Sept 2021 23:59:59 GMT",
		}

		Convey("When the deprecation config values are validated", func() {
			err := cfg.Deprecation.Validate(context.Background())

			Convey("Then there should be no error returned", func() {
				So(err, ShouldBeNil)
			})
		})
	})
}

func TestFailureConfiguredDeprecationValidation(t *testing.T) {

	Convey("Given an environment with deprecation \"HasDeprecationHeader\" is set to false", t, func() {
		cfg, err := Get()
		if err != nil {
			t.Fatalf("Unable to retrieve default configuration for test")
		}

		cfg.Deprecation.HasDeprecationHeader = false

		Convey("And \"Deprecation.Link\" is not empty", func() {
			cfg.Deprecation.Link = "www.ons.gov.uk"

			Convey("When the deprecation config values are validated", func() {
				err := cfg.Deprecation.Validate(context.Background())

				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})

			// Reset deprecation link to default value - empty string
			cfg.Deprecation.Link = ""
		})

		Convey("And \"Deprecation.Date\" is not empty", func() {
			cfg.Deprecation.Date = "Wed, 11 Nov 2020 23:59:59 GMT"

			Convey("When the deprecation config values are validated", func() {
				err := cfg.Deprecation.Validate(context.Background())

				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})

			// Reset deprecation date to default value - empty string
			cfg.Deprecation.Date = ""
		})

		Convey("And \"Deprecation.Sunset\" is not empty", func() {
			cfg.Deprecation.Date = "Wed, 29 Sep 2021 23:59:59 GMT"

			Convey("When the deprecation config values are validated", func() {
				err := cfg.Deprecation.Validate(context.Background())

				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})

			// Reset deprecation date to default value - empty string
			cfg.Deprecation.Sunset = ""
		})
	})
}
