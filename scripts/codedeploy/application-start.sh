#!/bin/bash

AWS_REGION=
ECR_REPOSITORY_URI=

docker run -d                                        \
  --env=ELASTICSEARCH_ROOT=http://elasticsearch:9200 \
  --env=ZEBEDEE_ROOT=http://zebedee-reader:8080      \
  --name=api                                         \
  --net=website                                      \
  --restart=always                                   \
  $ECR_REPOSITORY_URI/dp-api-poc
