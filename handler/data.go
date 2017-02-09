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
		zebedeeClient  upstream.ZebedeeClient
		websiteClient  upstream.WebsiteClient
		useWebsite     bool
	}
)

func NewDataHandler(elasticService upstream.ElasticService, zebedeeClient upstream.ZebedeeClient, websiteClient upstream.WebsiteClient, useWebsite bool) *DataHandler {
	return &DataHandler{elasticService, zebedeeClient, websiteClient, useWebsite}
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
			if dh.useWebsite {
				websiteRes, werr := dh.websiteClient.GetData(dataUri)
				logOut(r, werr)

				writeResponse(w, websiteRes.Code, websiteRes.Body)
			} else {
				zebedeeRes, zerr := dh.zebedeeClient.GetData(dataUri)
				logOut(r, zerr)

				writeResponse(w, zebedeeRes.Code, zebedeeRes.Body)
			}
		}
	} else {
		writeResponse(w, elasticRes.Code, nil)
	}
}
