#!/bin/bash -eux

cwd=$(pwd)

pushd dp-apipoc-server
        make build && mv build/dp-api-poc $cwd/build
        cp Dockerfile.concourse $cwd/build
popd
