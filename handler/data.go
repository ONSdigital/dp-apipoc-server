package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/jmoiron/jsonq"
	"github.com/julienschmidt/httprouter"
)

type (
	DataHandler struct {
		elasticService upstream.ElasticService
	}
)

func NewDataHandler(elasticService upstream.ElasticService) *DataHandler {
	return &DataHandler{elasticService}
}

func (dh DataHandler) GetData(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	elasticRes, err := dh.elasticService.GetSpecificTimeSeriesSpecificDataset(p.ByName("datasetId"), p.ByName("timeseriesId"))

	logOut(r, err)

	if elasticRes.Code == model.OK {
		jq := jsonq.NewQuery(elasticRes.Body)

		dataUri, e := jq.String("uri")
		if e != nil {
			logOut(r, e)
			writeResponse(w, model.ERROR, nil)
		} else {

			writeResponse(w, elasticRes.Code, dataUri)
		}
	} else {
		writeResponse(w, elasticRes.Code, nil)
	}
}
