#!/bin/bash

#docker run -p 9300:9300 -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" -e ES_JAVA_OPTS="-Xms1g -Xmx1g"  ons/elastic
docker start dp-api-elastic
waitforit/wait-for-it.sh -t 0 localhost:9200

go run $(pwd)/src/main/go/github.com/ONSdigital/dp-apipoc-server/main.go