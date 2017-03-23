#!/bin/bash -eux

function deployment_status {
  aws deploy get-deployment --deployment-id $1 | jq -r .deploymentInfo.status
}

deployment_id=$(
  aws deploy create-deployment                \
    --application-name dp-developer-poc       \
    --deployment-group-name $DEPLOYMENT_GROUP \
    --s3-location bucket=$DEPLOYMENT_BUCKET,bundleType=tgz,key=dp-developer-poc/1.0.0.tar.gz | jq -r .deploymentId
)

while [[ $(deployment_status $deployment_id) = "Created" ]] || [[ $(deployment_status $deployment_id) = "InProgress" ]]; do
  sleep 5
done

if [[ $(deployment_status $deployment_id) != "Succeeded" ]]; then
  exit 1;
fi

