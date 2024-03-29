package handler

import (
	"encoding/json"
	"net/http"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/log.go/v2/log"
)

var statusDict = map[model.Code]interface{}{model.OK: http.StatusOK, model.DEPENDENCY_CONNECTION_ERROR: http.StatusOK, model.NOT_FOUND: http.StatusNotFound, model.ERROR: http.StatusInternalServerError}

func writeResponse(w http.ResponseWriter, code model.Code, res interface{}) {
	statusCode := statusDict[code].(int)

	if statusCode == http.StatusOK {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(statusCode)
		json.NewEncoder(w).Encode(res)
	} else {
		w.WriteHeader(statusCode)
	}
}

func Max(x, y model.Code) model.Code {
	if x > y {
		return x
	}
	return y
}

func logOut(r *http.Request, message string, err error, logData log.Data) {
	if err != nil {
		log.Error(r.Context(), message, err, logData)
	}
}
