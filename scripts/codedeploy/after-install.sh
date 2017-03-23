#!/bin/bash

AWS_REGION=
ECR_REPOSITORY_URI=

$(aws ecr get-login --region $AWS_REGION) && docker pull $ECR_REPOSITORY_URI/dp-api-poc
