package router

import (
	"net/http"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/config"
	"github.com/ONSdigital/dp-apipoc-server/handler"
	"github.com/ONSdigital/log.go/v2/log"
	"github.com/gorilla/mux"
)

func GetRoutes(opsHandler *handler.OpsHandler, metaHandler *handler.MetadataHandler, dataHandler *handler.DataHandler) http.Handler {
	router := mux.NewRouter()

	router.HandleFunc("/ops/ping", opsHandler.PingHandler).Methods("HEAD")
	router.HandleFunc("/ops/status", opsHandler.StatusHandler).Methods("GET")

	router.HandleFunc("/dataset", metaHandler.GetDatasets).Methods("GET")
	router.HandleFunc("/dataset/{datasetId}", metaHandler.GetSpecificDataset).Methods("GET")
	router.HandleFunc("/dataset/{datasetId}/timeseries", metaHandler.GetSpecificDatasetTimeSeries).Methods("GET")
	router.HandleFunc("/dataset/{datasetId}/timeseries/{timeseriesId}", metaHandler.GetSpecificTimeSeriesSpecificDataset).Methods("GET")
	router.HandleFunc("/dataset/{datasetId}/timeseries/{timeseriesId}/data", dataHandler.GetData).Methods("GET")

	router.HandleFunc("/timeseries", metaHandler.GetTimeseries).Methods("GET")
	router.HandleFunc("/timeseries/{timeseriesId}", metaHandler.GetSpecificTimeseries).Methods("GET")
	router.HandleFunc("/timeseries/{timeseriesId}/dataset", metaHandler.GetSpecificTimeseriesDatasets).Methods("GET")
	router.HandleFunc("/timeseries/{timeseriesId}/dataset/{datasetId}", metaHandler.GetSpecificTimeSeriesSpecificDataset).Methods("GET")
	router.HandleFunc("/timeseries/{timeseriesId}/dataset/{datasetId}/data", dataHandler.GetData).Methods("GET")

	router.HandleFunc("/search", metaHandler.DoSearch).Methods("GET")

	return router
}

func DeprecationMiddleware(deprecation config.Deprecation) func(http.Handler) http.Handler {
	return func(h http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, req *http.Request) {

			if deprecation.Sunset != "" {
				sunsetTime, err := time.Parse(time.RFC1123, deprecation.Sunset)
				if err != nil {
					log.Warn(req.Context(), "failing to parse configuration of Sunset header needed for service deprecation, continue allowing users access to endpoints", log.FormatErrors([]error{err}))
				}

				if sunsetTime.Before(time.Now().UTC()) {
					w.WriteHeader(http.StatusNotFound)
					return
				}
			}

			if deprecation.IsDeprecated {
				w.Header().Set("Deprecation", "true")
				if deprecation.Date != "" {
					w.Header().Set("Deprecation", deprecation.Date)
				}

				if deprecation.Link != "" {
					w.Header().Set("Link", deprecation.Link)
				}

				if deprecation.Sunset != "" {
					w.Header().Set("Sunset", deprecation.Sunset) // Wed, 11 Nov 2020 23:59:59 GMT
				}
			}

			h.ServeHTTP(w, req)
		})
	}
}
