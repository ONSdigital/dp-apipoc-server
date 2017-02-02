package handler

import (
	"net/http"

	"github.com/ONSdigital/go-ns/log"
	"github.com/julienschmidt/httprouter"
	"github.com/sem247/anthill/model"
	"github.com/sem247/anthill/upstream"
)

type (
	OpsHandler struct {
		elasticService upstream.ElasticService
		httpWriter     *HttpWriter
	}
)

func NewOpsHandler(elasticService upstream.ElasticService, httpWriter *HttpWriter) *OpsHandler {
	return &OpsHandler{elasticService, httpWriter}
}

func (ops OpsHandler) PingHandler(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	w.WriteHeader(http.StatusOK)
}

func (ops OpsHandler) StatusHandler(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	elasticRes, err := ops.elasticService.Ping()

	check(r, err)

	status := model.Status{ApplicationName: "Anthill - POC API Server", Dependencies: &model.Dependency{Elasticsearch: elasticRes.Body}}

	ops.httpWriter.writeResponse(w, elasticRes.Code, status)
}

func check(r *http.Request, err error) {

	if err != nil {
		log.ErrorR(r, err, nil)
	}
}
