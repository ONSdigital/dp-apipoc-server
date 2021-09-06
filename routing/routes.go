package router

import (
	"net/http"

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
