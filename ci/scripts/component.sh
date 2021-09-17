#!/bin/bash -eux

pushd dp-apipoc-server
  make test-component
popd