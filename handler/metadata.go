package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/julienschmidt/httprouter"
)

type HasErrors bool

type (
	MetadataHandler struct {
		elasticService upstream.ElasticService
	}
)

func NewMetadataHandler(elasticService upstream.ElasticService) *MetadataHandler {
	return &MetadataHandler{elasticService}
}

func (mh MetadataHandler) GetDatasets(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetByType("dataset", startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetTimeseries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetByType("timeseries", startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDataset(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById("dataset", "description.datasetId", p.ByName("datasetId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById("timeseries", "description.cdid", p.ByName("timeseriesId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDatasetTimeSeries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById("timeseries", "description.datasetId", p.ByName("datasetId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseriesDatasets(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById("dataset", "description.cdid", p.ByName("timeseriesId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeSeriesSpecificDataset(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	elasticRes, err := mh.elasticService.GetSpecificTimeSeriesSpecificDataset(p.ByName("datasetId"), p.ByName("timeseriesId"))

	logOut(r, err)

	writeResponse(w, elasticRes.Code, elasticRes.Body)
}

func (mh MetadataHandler) DoSearch(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.SearchData(r.URL.Query().Get("q"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}
