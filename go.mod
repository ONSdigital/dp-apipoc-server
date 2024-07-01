module github.com/ONSdigital/dp-apipoc-server

go 1.19

// [CVE-2023-48795] CWE-354: Improper Validation of Integrity Check Value
replace golang.org/x/crypto => golang.org/x/crypto v0.17.0

require (
	github.com/ONSdigital/dp-net/v2 v2.11.2
	github.com/ONSdigital/log.go/v2 v2.4.3
	github.com/cznic/mathutil v0.0.0-20170313102836-1447ad269d64
	github.com/gorilla/mux v1.8.1
	github.com/jmoiron/jsonq v0.0.0-20150511023944-e874b168d07e
	github.com/justinas/alice v1.2.0
	github.com/kelseyhightower/envconfig v1.4.0
	github.com/mattn/go-isatty v0.0.20 // indirect
	github.com/rs/cors v1.10.0
	github.com/smartystreets/goconvey v1.8.1
	gopkg.in/olivere/elastic.v3 v3.0.62
	gopkg.in/tylerb/graceful.v1 v1.2.14
)

require (
	github.com/ONSdigital/dp-api-clients-go/v2 v2.254.1 // indirect
	github.com/fatih/color v1.16.0 // indirect
	github.com/go-logr/logr v1.3.0 // indirect
	github.com/go-logr/stdr v1.2.2 // indirect
	github.com/gopherjs/gopherjs v1.17.2 // indirect
	github.com/hokaccha/go-prettyjson v0.0.0-20211117102719-0474bc63780f // indirect
	github.com/jtolds/gls v4.20.0+incompatible // indirect
	github.com/mattn/go-colorable v0.1.13 // indirect
	github.com/smarty/assertions v1.15.1 // indirect
	go.opentelemetry.io/otel v1.21.0 // indirect
	go.opentelemetry.io/otel/metric v1.21.0 // indirect
	go.opentelemetry.io/otel/trace v1.21.0 // indirect
	golang.org/x/net v0.19.0 // indirect
	golang.org/x/sys v0.15.0 // indirect
)
