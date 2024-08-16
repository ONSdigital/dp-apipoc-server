package config

import (
	"errors"
	"os"
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
				So(cfg.Deprecation.Outages, ShouldHaveLength, 0)
			})
		})
	})
}

func TestSuccessfulConfiguredDeprecationValidation(t *testing.T) {

	Convey("Given an environment with no deprecation variables set", t, func() {
		Convey("When the config is obtained and validated", func() {
			_, err := Get()

			Convey("Then there should be no error returned", func() {
				So(err, ShouldBeNil)
			})
		})
	})

	Convey("Given an environment with all deprecation variables are set", t, func() {
		cfg = nil
		os.Setenv("DEPRECATION_DATE", "Wed, 11 Nov 2020 23:59:59 GMT")
		os.Setenv("HAS_DEPRECATION_HEADER", "true")
		os.Setenv("DEPRECATION_LINK", "www.ons.gov.uk")
		os.Setenv("DEPRECATION_SUNSET", "Wed, 29 Sept 2021 23:59:59 GMT")
		os.Setenv("DEPRECATION_OUTAGES", "2h@2023-10-09 13:59:59,3h@2022-10-09 23:59:59")

		Convey("When the config is obtained and validated", func() {
			cfg, err := Get()

			Convey("Then there should be no error and the two outages are sorted by start time", func() {
				So(err, ShouldBeNil)
				So(cfg.Deprecation.Date, ShouldEqual, "Wed, 11 Nov 2020 23:59:59 GMT")
				So(cfg.Deprecation.Outages, ShouldHaveLength, 2)
				So(cfg.Deprecation.Outages[0].Start.Year(), ShouldEqual, 2022)
				So(cfg.Deprecation.Outages[0].End.Hour(), ShouldEqual, 3+23-24)
				So(cfg.Deprecation.Outages[1].Start.Year(), ShouldEqual, 2023)
				So(cfg.Deprecation.Outages[1].End.Hour(), ShouldEqual, 2+13)
			})
		})
	})
}

func TestFailureConfiguredDeprecationValidation(t *testing.T) {

	Convey("Given an environment where deprecation `HasDeprecationHeader` is false", t, func() {
		cfg = nil
		os.Setenv("HAS_DEPRECATION_HEADER", "false")

		Convey("And `Deprecation.Link` is not empty", func() {
			os.Setenv("DEPRECATION_LINK", "www.ons.gov.uk")
			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})

			os.Unsetenv("DEPRECATION_LINK")
		})

		Convey("And `Deprecation.Date` is not empty", func() {
			os.Setenv("DEPRECATION_DATE", "Wed, 11 Nov 2020 23:59:59 GMT")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})
			os.Unsetenv("DEPRECATION_DATE")
		})

		Convey("And `Deprecation.Sunset` is not empty", func() {
			os.Setenv("DEPRECATION_SUNSET", "Wed, 29 Sep 2021 23:59:59 GMT")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})
			os.Unsetenv("DEPRECATION_SUNSET")
		})

		Convey("And `Deprecation.Outages` is not empty", func() {
			os.Setenv("DEPRECATION_OUTAGES", "2h@2021-10-09 23:59:59")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New(deprecationErrorMessage))
				})
			})
			os.Unsetenv("DEPRECATION_OUTAGES")
		})
	})

	Convey("Given an environment where deprecation `HasDeprecationHeader` is true", t, func() {
		cfg = nil
		os.Setenv("HAS_DEPRECATION_HEADER", "true")

		Convey("And `Deprecation.Outages` has invalid second period", func() {
			os.Setenv("DEPRECATION_OUTAGES", "2h@2021-10-09 23:59:59,invalid")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err, ShouldResemble, errors.New("expected `duration@time` in period 2"))
				})
			})
			os.Unsetenv("DEPRECATION_OUTAGES")
		})

		Convey("And `Deprecation.Outages` has invalid time in 1st period", func() {
			os.Setenv("DEPRECATION_OUTAGES", "2h@2021-10-09 33:59:59")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err.Error(), ShouldStartWith, "cannot parse `...@time` in period 1")
				})
			})
			os.Unsetenv("DEPRECATION_OUTAGES")
		})

		Convey("And `Deprecation.Outages` has invalid duration in 1st period", func() {
			os.Setenv("DEPRECATION_OUTAGES", "invalid@2021-10-09 23:59:59")

			Convey("When the config is obtained and validated", func() {
				_, err := Get()
				Convey("Then an error should be returned", func() {
					So(err.Error(), ShouldStartWith, "cannot parse `duration@...` in period 1")
				})
			})
			os.Unsetenv("DEPRECATION_OUTAGES")
		})

	})

}
