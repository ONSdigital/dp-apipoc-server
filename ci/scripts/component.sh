#!/bin/bash -eux

cwd=$(pwd)

pushd $cwd/dp-apipoc-server
  make test-component
popd