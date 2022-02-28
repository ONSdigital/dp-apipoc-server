package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/ONSdigital/log.go/v2/log"
	"github.com/gorilla/mux"
	"github.com/jmoiron/jsonq"
)

type (
	DataHandler struct {
		elasticService upstream.ElasticService
		zebedeeClient  upstream.ZebedeeClient
		websiteClient  upstream.WebsiteClient
		useWebsite     bool
	}
)

func NewDataHandler(elasticService upstream.ElasticService, zebedeeClient upstream.ZebedeeClient, websiteClient upstream.WebsiteClient, useWebsite bool) *DataHandler {
	return &DataHandler{elasticService, zebedeeClient, websiteClient, useWebsite}
}

func (dh DataHandler) GetData(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	timeseriesID := vars["timeseriesId"]
	datasetID := vars["datasetId"]
	logData := log.Data{"dataset_id": datasetID, "timeseries_id": timeseriesID}

	elasticRes, err := dh.elasticService.GetSpecificTimeSeriesSpecificDataset(r.Context(), datasetID, timeseriesID)

	logOut(r, "GetData failed calling es", err, logData)

	if elasticRes.Code == model.OK {
		jq := jsonq.NewQuery(elasticRes.Body)

		dataUri, e := jq.String("uri")
		if e != nil {
			logOut(r, "GetData failed to get uri from es result", e, logData)
			writeResponse(w, model.ERROR, nil)
		} else {
			if dh.useWebsite {
				websiteRes, werr := dh.websiteClient.GetData(r.Context(), dataUri)
				logOut(r, "GetData failed to get data from website", werr, logData)

				writeResponse(w, websiteRes.Code, websiteRes.Body)
			} else {
				zebedeeRes, zerr := dh.zebedeeClient.GetData(r.Context(), dataUri)
				logOut(r, "GetData failed to get data from zebedee", zerr, logData)

				writeResponse(w, zebedeeRes.Code, zebedeeRes.Body)
			}
		}
	} else {
		writeResponse(w, elasticRes.Code, nil)
	}
}
