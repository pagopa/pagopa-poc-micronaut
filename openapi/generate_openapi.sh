#!/bin/bash

PROPERTY_FILE=../openapi.properties

function getProperty {
   PROP_KEY=$1
   PROP_VALUE=`cat $PROPERTY_FILE | grep "$PROP_KEY" | cut -d'=' -f2`
   echo $PROP_VALUE
}

version=$(getProperty "micronaut.openapi.expand.api.version")
URI="http://localhost:8080/swagger/reporting-organizations-enrollments-${version}.json"

curl ${URI} | python3 -m json.tool > ./openapi.json

# UI mode http://localhost:8080/swagger-ui/index.html
