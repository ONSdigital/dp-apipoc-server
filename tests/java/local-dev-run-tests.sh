#!/bin/bash

./gradlew clean unitTests

docker run --name dp-api-elastic -p 9300:9300 -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" -e ES_JAVA_OPTS="-Xms1g -Xmx1g" -d sem247/elasticns:2.4.3_1
docker run --name dp-api-zebedee -p 8082:8082 -d sem247/zbdreader:1.0.0

waitforit/wait-for-it.sh -t 0 -h localhost -p 9200
waitforit/wait-for-it.sh -t 0 -h localhost -p 8082

sleep 60

export API_APP_PORT=3000
export ELASTICSEARCH_ROOT=http://localhost:9200
export ZEBEDEE_ROOT=http://localhost:8082
export WEBSITE_ROOT=https://www.ons.gov.uk
export USE_WEBSITE=false

echo "Starting up the server..."
go run $(pwd)/../../main.go &

echo "Waiting for server to come up..."
waitforit/wait-for-it.sh -t 0 -h localhost -p 3000

./gradlew bddTests

# clean up
echo "Cleaning up..."
kill $(lsof -n -i :3000 | awk '/LISTEN/ {print $2}')

docker stop dp-api-zebedee
docker stop dp-api-elastic

sleep 20

docker rm dp-api-zebedee
docker rm dp-api-elastic