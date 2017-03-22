#!/bin/bash

if [[ $(docker inspect --format="{{ .State.Running}}" dp-api-poc) == "false" ]]; then
  exit 1;
fi
