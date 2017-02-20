#!/bin/bash

kill $(lsof -n -i :3000 | awk '/LISTEN/ {print $2}')

docker stop dp-api-zebedee
docker stop dp-api-elastic

sleep 20

docker rm dp-api-zebedee
docker rm dp-api-elastic