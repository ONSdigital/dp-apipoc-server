#!/bin/bash

CONTAINER_ID=$(docker ps | grep dp-api-poc | awk '{print $1}')

if [[ -n $CONTAINER_ID ]]; then
  docker stop $CONTAINER_ID
fi
