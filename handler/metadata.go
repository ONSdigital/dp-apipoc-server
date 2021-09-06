package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/ONSdigital/log.go/log"
	"github.com/gorilla/mux"
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

func (mh MetadataHandler) GetDatasets(w http.ResponseWriter, r *http.Request) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetByType(r.Context(), "dataset", startIndex, pageSize)

		logOut(r, "GetDatasets: failed to retrieve datasets", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetTimeseries(w http.ResponseWriter, r *http.Request) {
	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetByType(r.Context(), "timeseries", startIndex, pageSize)

		logOut(r, "GetTimeseries: failed to retrieve timeseries", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDataset(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	datasetID := vars["datasetId"]

	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"dataset_id": datasetID, "start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById(r.Context(), "dataset", "description.datasetId", datasetID, startIndex, pageSize)

		logOut(r, "GetSpecificDataset: failed to retrieve specific dataset", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseries(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	timeseriesID := vars["timeseriesId"]

	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"timeseries_id": timeseriesID, "start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById(r.Context(), "timeseries", "description.cdid", timeseriesID, startIndex, pageSize)

		logOut(r, "GetSpecificTimeseries: failed to retrieve specific timeseries", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificDatasetTimeSeries(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	datasetID := vars["datasetId"]

	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"dataset_id": datasetID, "start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById(r.Context(), "timeseries", "description.datasetId", datasetID, startIndex, pageSize)

		logOut(r, "GetSpecificDatasetTimeSeries: failed to get timeseries for a specific dataset", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeseriesDatasets(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	timeseriesID := vars["timeseriesId"]

	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"timeseries_id": timeseriesID, "start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.GetById(r.Context(), "dataset", "description.cdid", timeseriesID, startIndex, pageSize)

		logOut(r, "GetSpecificTimeseriesDatasets: failed to get datasets for a specific timeseries", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}

func (mh MetadataHandler) GetSpecificTimeSeriesSpecificDataset(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	timeseriesID := vars["timeseriesId"]
	datasetID := vars["datasetId"]
	logData := log.Data{"dataset_id": datasetID, "timeseries_id": timeseriesID}

	elasticRes, err := mh.elasticService.GetSpecificTimeSeriesSpecificDataset(r.Context(), datasetID, timeseriesID)

	logOut(r, "GetSpecificTimeSeriesSpecificDataset: failed to get a specific dataset from a specific timeseries", err, logData)

	writeResponse(w, elasticRes.Code, elasticRes.Body)
}

func (mh MetadataHandler) DoSearch(w http.ResponseWriter, r *http.Request) {
	qTerm := r.URL.Query().Get("q")
	startIndex, pageSize, hasErrors := parsePageParameters(r)
	logData := log.Data{"query_term": qTerm, "start_index": startIndex, "page_size": pageSize}

	if hasErrors == true {
		w.WriteHeader(http.StatusBadRequest)
	} else {
		elasticRes, err := mh.elasticService.SearchData(r.Context(), qTerm, startIndex, pageSize)

		logOut(r, "DoSearch: failed to do search", err, logData)

		writeResponse(w, elasticRes.Code, elasticRes.Body)
	}
}
