package handler

import (
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/dp-apipoc-server/upstream"
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

func (ops OpsHandler) PingHandler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
}

func (ops OpsHandler) StatusHandler(w http.ResponseWriter, r *http.Request) {
	ctx := r.Context()
	elasticRes, err := ops.elasticService.Ping(ctx)
	logOut(r, "StatusHandler: failed to ping elasticsearch service", err, nil)

	websiteRes, err2 := ops.websiteClient.Ping(ctx)
	logOut(r, "StatusHandler: failed to ping website", err2, nil)

	status := model.Status{ApplicationName: "API POC Server", Dependencies: &model.Dependency{Elasticsearch: elasticRes.Body, Website: websiteRes.Body}}

	writeResponse(w, Max(elasticRes.Code, websiteRes.Code), status)
}
