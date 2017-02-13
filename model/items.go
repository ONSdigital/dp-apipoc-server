package model

type (
	Items struct {
		StartIndex   int           `json:"startIndex"`
		ItemsPerPage int           `json:"itemsPerPage"`
		TotalItems   int64         `json:"totalItems"`
		Items        []interface{} `json:"items"`
	}
)
