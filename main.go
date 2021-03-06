package main

import (
	"net/http"
	"os"
	"strconv"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/handler"
	"github.com/ONSdigital/dp-apipoc-server/routing"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/ONSdigital/go-ns/handlers/requestID"
	"github.com/ONSdigital/go-ns/handlers/timeout"
	"github.com/ONSdigital/go-ns/log"
	"github.com/justinas/alice"
	"github.com/namsral/flag"
	"github.com/rs/cors"
	"gopkg.in/tylerb/graceful.v1"
)

func main() {
	log.Namespace = "dp-apipoc-server"

	var port = 3000
	var elasticUrl = "http://127.0.0.1:9200"
	var websiteUrl = "https://www.ons.gov.uk"
	var zebedeeUrl = "http://127.0.0.1:8082"
	var useWebsite = false

	flag.IntVar(&port, "API_APP_PORT", port, "Port number")

	flag.StringVar(&elasticUrl, "ELASTICSEARCH_ROOT", elasticUrl, "ElasticSearch URL")

	flag.StringVar(&zebedeeUrl, "ZEBEDEE_ROOT", zebedeeUrl, "Zebedee URL")

	flag.StringVar(&websiteUrl, "WEBSITE_ROOT", websiteUrl, "Website URL")
	flag.BoolVar(&useWebsite, "USE_WEBSITE", useWebsite, "Use Website")

	flag.Parse()

	elasticService := upstream.NewElasticService(elasticUrl)
	zebedeeClient := upstream.NewZebedeeClient(zebedeeUrl)
	websiteClient := upstream.NewWebsiteClient(websiteUrl)

	opsHandler := handler.NewOpsHandler(elasticService, websiteClient)
	elHandler := handler.NewMetadataHandler(elasticService)
	dhHandler := handler.NewDataHandler(elasticService, zebedeeClient, websiteClient, useWebsite)

	routes := router.GetRoutes(opsHandler, elHandler, dhHandler)

	h := cors.Default().Handler(routes)

	middleware := []alice.Constructor{
		requestID.Handler(16),
		log.Handler,
		timeout.Handler(10 * time.Second),
	}

	alice := alice.New(middleware...).Then(h)

	address := ":" + strconv.Itoa(port)

	server := &graceful.Server{
		Timeout: 10 * time.Second,

		Server: &http.Server{
			Addr:         address,
			Handler:      alice,
			ReadTimeout:  5 * time.Second,
			WriteTimeout: 10 * time.Second,
		},
	}

	if err := server.ListenAndServe(); err != nil {
		log.Error(err, nil)
		os.Exit(2)
	}
}
