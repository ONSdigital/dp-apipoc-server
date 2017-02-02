package main

import (
	"net/http"
	"os"
	"strconv"
	"time"

	"github.com/ONSdigital/go-ns/handlers/requestID"
	"github.com/ONSdigital/go-ns/handlers/timeout"
	"github.com/ONSdigital/go-ns/log"
	"github.com/justinas/alice"
	"github.com/sem247/anthill/config"
	"github.com/sem247/anthill/handler"
	"github.com/sem247/anthill/routing"
	"github.com/sem247/anthill/upstream"
	"gopkg.in/tylerb/graceful.v1"
)

func main() {
	/*
		if v := os.Getenv("BIND_ADDR"); len(v) > 0 {
			config.BindAddr = v
		}
		if v := os.Getenv("SITE_DOMAIN"); len(v) > 0 {
			config.SiteDomain = v
		}
	*/
	log.Namespace = "dp-api"

	configuration := config.GetConfig()

	elasticService := upstream.NewElasticService(configuration.ElasticsearchUrl)

	httpWriter := handler.NewHttpWriter()

	opsHandler := handler.NewOpsHandler(elasticService, httpWriter)
	elHandler := handler.NewMetadataHandler(elasticService, httpWriter)

	routes := router.GetRoutes(configuration, opsHandler, elHandler)

	middleware := []alice.Constructor{
		requestID.Handler(16),
		log.Handler,
		timeout.Handler(10 * time.Second),
	}

	alice := alice.New(middleware...).Then(routes)

	address := ":" + strconv.Itoa(configuration.Port)

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
