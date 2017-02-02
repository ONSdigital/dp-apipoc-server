package router

import (
	"net/http"

	"github.com/julienschmidt/httprouter"
	"github.com/sem247/anthill/handler"
	"github.com/sem247/anthill/model"
)

func GetRoutes(configuration *model.Configuration, opsHandler *handler.OpsHandler, metaHandler *handler.MetadataHandler) http.Handler {
	router := httprouter.New()

	router.HEAD("/api/ops/ping", opsHandler.PingHandler)
	router.GET("/api/ops/status", opsHandler.StatusHandler)

	router.GET("/api/dataset", metaHandler.GetDatasets)
	router.GET("/api/timeseries", metaHandler.GetTimeseries)

	router.GET("/api/dataset/:datasetId", metaHandler.GetSpecificDataset)
	router.GET("/api/timeseries/:timeseriesId", metaHandler.GetSpecificTimeseries)

	router.GET("/api/dataset/:datasetId/timeseries", metaHandler.GetSpecificDatasetTimeSeries)
	router.GET("/api/timeseries/:timeseriesId/dataset", metaHandler.GetSpecificTimeseriesDatasets)

	router.GET("/api/dataset/:datasetId/timeseries/:timeseriesId", metaHandler.GetSpecificTimeSeriesSpecificDataset)
	router.GET("/api/timeseries/:timeseriesId/dataset/:datasetId", metaHandler.GetSpecificTimeSeriesSpecificDataset)

	router.GET("/api/search", metaHandler.DoSearch)

	return router
}
