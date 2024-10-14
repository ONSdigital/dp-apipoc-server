package router

import (
	"net/http"
	"time"

	"github.com/ONSdigital/dp-apipoc-server/config"
	"github.com/ONSdigital/dp-apipoc-server/handler"
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

func DeprecationMiddleware(deprecation config.Deprecation, sunsetTime time.Time) func(http.Handler) http.Handler {
	return func(h http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, req *http.Request) {

			if deprecation.HasDeprecationHeader {

				now := time.Now().UTC()

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

				// check path is not health endpoint (want that to continue to work)
				isSpecialRequestPath := req.URL.Path[0:5] == `/ops/`
				if !isSpecialRequestPath {
					// check if time of request is during a deprecation-outage
					for _, outage := range deprecation.Outages {
						if outage.Start.Before(now) {
							if outage.End.After(now) {
								w.WriteHeader(http.StatusNotFound)
								return
							}
						} else {
							// Outages are sorted by Start time
							break // skip later outages
						}
					}
				}
			}

			h.ServeHTTP(w, req)
		})
	}
}
