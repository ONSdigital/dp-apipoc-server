package config

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func TestSpec(t *testing.T) {
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
			})
		})
	})
}
