package config

import (
	"encoding/json"
	"flag"
	"os"

	"github.com/sem247/anthill/model"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func GetConfig() *model.Configuration {
	var configFilename string
	flag.StringVar(&configFilename, "c", "conf.json", "The absolute path to conf.json")
	flag.Parse()

	file, err := os.Open(configFilename)
	check(err)

	decoder := json.NewDecoder(file)
	configuration := model.Configuration{}
	err = decoder.Decode(&configuration)
	check(err)

	return &configuration
}
