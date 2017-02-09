package handler

import (
	"net/http"
	"strconv"

	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/ONSdigital/go-ns/log"
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
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedData("dataset", startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetTimeseries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedData("timeseries", startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDataset(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedDataById("dataset", "description.datasetId", p.ByName("datasetId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedDataById("timeseries", "description.cdid", p.ByName("timeseriesId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDatasetTimeSeries(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedDataById("timeseries", "description.datasetId", p.ByName("datasetId"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseriesDatasets(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetPagedDataById("dataset", "description.cdid", p.ByName("timeseriesId"), startIndex, pageSize)

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
	startIndex, pageSize, hasErrors := mh.parsePageParameters(r)

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.SearchData(r.URL.Query().Get("q"), startIndex, pageSize)

		logOut(r, err)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) parsePageParameters(r *http.Request) (int, int, HasErrors) {
	queryValues := r.URL.Query()
	start := queryValues.Get("start")
	limit := queryValues.Get("limit")

	var startIndex int
	var pageSize int = 5
	var e1 error
	var e2 error
	var hasErrors HasErrors

	if start != "" {
		startIndex, e1 = strconv.Atoi(start)
		if e1 != nil {
			log.ErrorR(r, e1, nil)
			hasErrors = true
		}
	}
	if limit != "" {
		pageSize, e2 = strconv.Atoi(limit)
		if e2 != nil {
			log.ErrorR(r, e2, nil)
			hasErrors = true
		}
	}

	return startIndex, pageSize, hasErrors
}
