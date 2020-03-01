#!/bin/bash -eux

cwd=$(pwd)

export GOPATH=$cwd/go

pushd $GOPATH/src/github.com/ONSdigital/dp-apipoc-server
    if [[ "$APPLICATION" == "dp-api-poc" ]]; then
        make build && mv build/$(go env GOOS)-$(go env GOARCH)/* $cwd/build
        cp Dockerfile.concourse $cwd/build
    elif [[ "$APPLICATION" == "dp-developer-poc" ]]; then
        cp -r doc/Dockerfile.concourse doc/assets doc/api-spec/swagger.json $cwd/build
    fi
popd
