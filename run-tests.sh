#!/bin/bash

set -e -u -x

echo "Ensuring upstream services are up and running..."
tests/java/waitforit/wait-for-it.sh -t 0 -h bdd-elasticns -p 9200
tests/java/waitforit/wait-for-it.sh -t 0 -h bdd-zbdreader -p 8082

echo "Upstream services have are up"

echo "Starting up the server..."
go version

go run main.go &

echo "Waiting for server to come up..."
tests/java/waitforit/wait-for-it.sh -t 0 -h localhost -p 3000

cd tests/java

gradle clean bddTests

tests_result=$?

kill $(lsof -n -i :3000 | awk '/LISTEN/ {print $2}')

return $tests_result