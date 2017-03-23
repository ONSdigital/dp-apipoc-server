#!/bin/bash

AWS_REGION=
ECR_REPOSITORY_URI=

docker run -d      \
  --name=developer \
  --net=website    \
  --restart=always \
  $ECR_REPOSITORY_URI/dp-developer-poc
