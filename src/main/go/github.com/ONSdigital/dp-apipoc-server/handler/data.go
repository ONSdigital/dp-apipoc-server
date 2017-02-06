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
		websiteClient  upstream.WebsiteClient
	}
)

func NewDataHandler(elasticService upstream.ElasticService, websiteClient upstream.WebsiteClient) *DataHandler {
	return &DataHandler{elasticService, websiteClient}
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
			websiteRes, werr := dh.websiteClient.GetData(dataUri)
			logOut(r, werr)

			writeResponse(w, websiteRes.Code, websiteRes.Body)
		}
	} else {
		writeResponse(w, elasticRes.Code, nil)
	}
}
