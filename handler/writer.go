package handler

import (
	"encoding/json"
	"net/http"

	"github.com/sem247/anthill/model"
)

type (
	HttpWriter struct {
		statusDict map[model.Code]interface{}
	}
)

func NewHttpWriter() *HttpWriter {
	var httpWriter HttpWriter

	httpWriter.statusDict = map[model.Code]interface{}{model.OK: http.StatusOK, model.DEPENDENCY_CONNECTION_ERROR: http.StatusOK, model.NOT_FOUND: http.StatusNotFound, model.ERROR: http.StatusInternalServerError}

	return &httpWriter
}

func (hw HttpWriter) writeResponse(w http.ResponseWriter, code model.Code, res interface{}) {
	statusCode := hw.statusDict[code].(int)

	if statusCode == http.StatusOK {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(statusCode)
		json.NewEncoder(w).Encode(res)
	} else {
		w.WriteHeader(statusCode)
	}
}
