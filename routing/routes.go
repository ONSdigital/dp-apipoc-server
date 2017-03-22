package router

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/handler"
	"github.com/julienschmidt/httprouter"
)

func GetRoutes(opsHandler *handler.OpsHandler, metaHandler *handler.MetadataHandler, dataHandler *handler.DataHandler) http.Handler {
	router := httprouter.New()

	router.HEAD("/ops/ping", opsHandler.PingHandler)
	router.GET("/ops/status", opsHandler.StatusHandler)

	router.GET("/dataset", metaHandler.GetDatasets)
	router.GET("/timeseries", metaHandler.GetTimeseries)

	router.GET("/dataset/:datasetId", metaHandler.GetSpecificDataset)
	router.GET("/timeseries/:timeseriesId", metaHandler.GetSpecificTimeseries)

	router.GET("/dataset/:datasetId/timeseries", metaHandler.GetSpecificDatasetTimeSeries)
	router.GET("/timeseries/:timeseriesId/dataset", metaHandler.GetSpecificTimeseriesDatasets)

	router.GET("/dataset/:datasetId/timeseries/:timeseriesId", metaHandler.GetSpecificTimeSeriesSpecificDataset)
	router.GET("/timeseries/:timeseriesId/dataset/:datasetId", metaHandler.GetSpecificTimeSeriesSpecificDataset)

	router.GET("/search", metaHandler.DoSearch)

	router.GET("/dataset/:datasetId/timeseries/:timeseriesId/data", dataHandler.GetData)
	router.GET("/timeseries/:timeseriesId/dataset/:datasetId/data", dataHandler.GetData)

	return router
}
