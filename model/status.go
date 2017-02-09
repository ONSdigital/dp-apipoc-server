package model

type Status struct {
	ApplicationName string      `json:"applicationName"`
	Dependencies    *Dependency `json:"dependencies"`
}

type Dependency struct {
	Elasticsearch interface{} `json:"elasticsearch"`
	Website       interface{} `json:"website"`
}
