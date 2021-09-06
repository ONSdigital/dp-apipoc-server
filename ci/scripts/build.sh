#!/bin/bash -eux

cwd=$(pwd)

pushd $cwd/dp-apipoc-server
        make build && mv build/dp-apipoc-server $cwd/build
        cp Dockerfile.concourse $cwd/build
popd
