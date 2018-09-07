#!/usr/bin/env bash

java -jar ZAP/zap-D-2018-08-28.jar -daemon -config api.disablekey=true &
./gradlew clean test
jps | grep zap-D-2018-08-28.jar | cut -d " " -f 1 | xargs kill -9

