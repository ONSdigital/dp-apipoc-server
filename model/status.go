package model

type Status struct {
	ApplicationName string      `json:"applicationName"`
	Dependencies    *Dependency `json:"dependencies,omitempty"`
}

type Dependency struct {
	Elasticsearch interface{} `json:"elasticsearch,omitempty"`
}
