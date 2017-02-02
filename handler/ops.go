package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
	"github.com/julienschmidt/httprouter"
)

type (
	OpsHandler struct {
		elasticService upstream.ElasticService
		websiteClient  upstream.WebsiteClient
	}
)

func NewOpsHandler(elasticService upstream.ElasticService, websiteClient upstream.WebsiteClient) *OpsHandler {
	return &OpsHandler{elasticService, websiteClient}
}

func (ops OpsHandler) PingHandler(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	w.WriteHeader(http.StatusOK)
}

func (ops OpsHandler) StatusHandler(w http.ResponseWriter, r *http.Request, p httprouter.Params) {
	elasticRes, err := ops.elasticService.Ping()
	logOut(r, err)

	websiteRes, err2 := ops.websiteClient.Ping()
	logOut(r, err2)

	status := model.Status{ApplicationName: "API POC Server", Dependencies: &model.Dependency{Elasticsearch: elasticRes.Body, Website: websiteRes.Body}}

	writeResponse(w, Max(elasticRes.Code, websiteRes.Code), status)
}
