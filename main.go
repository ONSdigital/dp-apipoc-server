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
	"gopkg.in/tylerb/graceful.v1"
)

func main() {
	log.Namespace = "dp-apipoc-server"

	var port = 3000
	var elasticUrl = "http://localhost:9200"
	var websiteUrl = "https://www.ons.gov.uk"
	flag.IntVar(&port, "port", port, "Port number")
	flag.StringVar(&elasticUrl, "elesticsearch", elasticUrl, "ElasticSearch URL")
	flag.StringVar(&websiteUrl, "website", websiteUrl, "Website URL")
	flag.Parse()

	elasticService := upstream.NewElasticService(elasticUrl)
	websiteClient := upstream.NewWebsiteClient(websiteUrl)

	opsHandler := handler.NewOpsHandler(elasticService, websiteClient)
	elHandler := handler.NewMetadataHandler(elasticService)
	dhHandler := handler.NewDataHandler(elasticService, websiteClient)

	routes := router.GetRoutes(opsHandler, elHandler, dhHandler)

	middleware := []alice.Constructor{
		requestID.Handler(16),
		log.Handler,
		timeout.Handler(10 * time.Second),
	}

	alice := alice.New(middleware...).Then(routes)

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
