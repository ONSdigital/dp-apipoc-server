#!/bin/bash

#docker run -p 9300:9300 -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" -e ES_JAVA_OPTS="-Xms1g -Xmx1g"  ons/elastic
docker start dp-api-elastic
waitforit/wait-for-it.sh -t 0 localhost:9200

sleep 10

export API_APP_PORT=3000
export ELASTICSEARCH_ROOT=http://localhost:9200
export ZEBEDEE_ROOT=http://localhost:8082
export WEBSITE_ROOT=https://www.ons.gov.uk
export USE_WEBSITE=true

go run $(pwd)/../../main.go
