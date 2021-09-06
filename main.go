package main

import (
	"context"
	"net/http"
	"os"
	"strconv"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/handler"
	router "github.com/ONSdigital/dp-apipoc-server/routing"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	request "github.com/ONSdigital/dp-net/request"
	"github.com/ONSdigital/log.go/log"
	"github.com/justinas/alice"
	"github.com/namsral/flag"
	"github.com/rs/cors"
	"gopkg.in/tylerb/graceful.v1"
)

func main() {
	log.Namespace = "dp-apipoc-server"
	ctx := context.Background()

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

	elasticService, err := upstream.NewElasticService(elasticUrl)
	if err != nil {
		log.Event(ctx, "failed to connect to elasticsearch service", log.ERROR, log.Error(err))
		os.Exit(1)
	}
	zebedeeClient := upstream.NewZebedeeClient(zebedeeUrl)
	websiteClient := upstream.NewWebsiteClient(websiteUrl)

	opsHandler := handler.NewOpsHandler(elasticService, websiteClient)
	elHandler := handler.NewMetadataHandler(elasticService)
	dhHandler := handler.NewDataHandler(elasticService, zebedeeClient, websiteClient, useWebsite)

	routes := router.GetRoutes(opsHandler, elHandler, dhHandler)

	h := cors.Default().Handler(routes)

	middleware := []alice.Constructor{
		request.HandlerRequestID(16),
		// timeout.Handler(10 * time.Second),
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
		log.Event(ctx, "failed to listen and serve", log.ERROR, log.Error(err))
		os.Exit(2)
	}
}
