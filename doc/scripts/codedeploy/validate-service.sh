#!/bin/bash

if [[ $(docker inspect --format="{{ .State.Running}}" dp-developer-poc) == "false" ]]; then
  exit 1;
fi
