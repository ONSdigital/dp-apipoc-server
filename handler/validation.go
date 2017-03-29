package handler

import (
	"errors"
	"fmt"
	"net/http"
	"strconv"

	"github.com/ONSdigital/go-ns/log"
	"github.com/cznic/mathutil"
)

const defaultStartIndex = 0
const startIndexFloor = 0
const startIndexCeiling = mathutil.MaxInt - 1

const defaultPageSize = 50
const pageSizeFloor = 1
const pageSizeCeiling = 100

func parsePageParameters(r *http.Request) (int, int, HasErrors) {
	queryValues := r.URL.Query()
	start := queryValues.Get("start")
	limit := queryValues.Get("limit")

	startIndex, e1 := validate(start, defaultStartIndex, startIndexFloor, startIndexCeiling, r)
	pageSize, e2 := validate(limit, defaultPageSize, pageSizeFloor, pageSizeCeiling, r)

	return startIndex, pageSize, (e1 || e2)
}

func validate(param string, defaultValue int, lower int, upper int, r *http.Request) (int, HasErrors) {
	var hasErrors HasErrors

	if len(param) > 0 {
		value, err := strconv.Atoi(param)
		if err != nil {
			log.ErrorR(r, err, nil)
			hasErrors = true
		}
		if value < lower {
			errStr := fmt.Sprintf("Invalid parameter: must not be less than %d", lower)

			log.ErrorR(r, errors.New(errStr), nil)
			hasErrors = true
		}
		if value > upper {
			errStr := fmt.Sprintf("Invalid parameter: must not exceed %d", upper)

			log.ErrorR(r, errors.New(errStr), nil)
			hasErrors = true
		}

		return value, hasErrors
	}

	return defaultValue, hasErrors
}
