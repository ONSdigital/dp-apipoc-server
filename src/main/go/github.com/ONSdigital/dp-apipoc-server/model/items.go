package model

type (
	Items struct {
		ItemsPerPage int           `json:"items_per_page"`
		StartIndex   int           `json:"start_index"`
		TotalItems   int64         `json:"total_items"`
		Items        []interface{} `json:"items"`
	}
)
