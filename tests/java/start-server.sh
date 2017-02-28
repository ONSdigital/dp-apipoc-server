#!/bin/bash

#docker run --name dp-api-elastic -p 9300:9300 -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" -e ES_JAVA_OPTS="-Xms1g -Xmx1g" -d sem247/elasticns:2.4.3_1
#docker run --name dp-api-zebedee -p 8082:8082 -d sem247/zbdreader:1.0.0
#
#waitforit/wait-for-it.sh -t 0 -h localhost -p 9200
#waitforit/wait-for-it.sh -t 0 -h localhost -p 8082

export API_APP_PORT=3000
export ELASTICSEARCH_ROOT=http://localhost:9200
export ZEBEDEE_ROOT=http://localhost:8082
export WEBSITE_ROOT=https://www.ons.gov.uk
export USE_WEBSITE=false

echo "Starting up the server..."
go run $(pwd)/../../main.go