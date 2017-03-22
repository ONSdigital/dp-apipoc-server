#!/bin/bash -eux

sed dp-api-poc/appspec.yml dp-api-poc/scripts/codedeploy/* -i         \
  -e s/\${CODEDEPLOY_USER}/$CODEDEPLOY_USER/g                         \
  -e s/^ECR_REPOSITORY_URI=.*/ECR_REPOSITORY_URI=$ECR_REPOSITORY_URI/ \
  -e s/^AWS_REGION=.*/AWS_REGION=$AWS_REGION/

mkdir -p artifacts/scripts/codedeploy

cp dp-api-poc/appspec.yml artifacts/
cp dp-api-poc/scripts/codedeploy/* artifacts/scripts/codedeploy
