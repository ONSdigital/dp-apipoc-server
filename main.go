package main

import (
	"context"
	"net/http"
	"os"
	"strconv"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/config"
	"github.com/ONSdigital/dp-apipoc-server/handler"
	router "github.com/ONSdigital/dp-apipoc-server/routing"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	request "github.com/ONSdigital/dp-net/v2/request"
	"github.com/ONSdigital/log.go/v2/log"
	"github.com/justinas/alice"
	"github.com/rs/cors"
	"gopkg.in/tylerb/graceful.v1"
)

func main() {
	log.Namespace = "dp-apipoc-server"
	ctx := context.Background()

	cfg, configErr := config.Get()
	if configErr != nil {
		log.Fatal(ctx, "error loading app config", configErr)
		os.Exit(1)
	}

	log.Event(ctx, "config on startup", log.INFO, log.Data{"config": cfg})

	elasticService, err := upstream.NewElasticService(cfg.ElasticsearchURL)
	if err != nil {
		log.Fatal(ctx, "failed to connect to elasticsearch service", err)
		os.Exit(1)
	}
	zebedeeClient := upstream.NewZebedeeClient(cfg.ZebedeeURL)
	websiteClient := upstream.NewWebsiteClient(cfg.WebsiteURL)

	opsHandler := handler.NewOpsHandler(elasticService, websiteClient)
	elHandler := handler.NewMetadataHandler(elasticService)
	dhHandler := handler.NewDataHandler(elasticService, zebedeeClient, websiteClient, cfg.UseWebsite)

	routes := router.GetRoutes(opsHandler, elHandler, dhHandler)

	h := cors.Default().Handler(routes)

	middleware := []alice.Constructor{
		request.HandlerRequestID(16),
		// timeout.Handler(10 * time.Second),
	}

	alice := alice.New(middleware...).Then(h)

	address := ":" + strconv.Itoa(cfg.Port)

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
		log.Error(ctx, "failed to listen and serve", err)
		os.Exit(2)
	}
}
