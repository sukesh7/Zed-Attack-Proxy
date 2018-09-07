#!/usr/bin/env bash

java -jar ZAP/zap-D-2018-08-28.jar -daemon -config api.disablekey=true &
./gradlew clean test
jps | grep zap-D-2018-08-28.jar | cut -d " " -f 1 | xargs kill -9

#docker pull owasp/zap2docker-stable
#docker run -u zap -p 8080:8080 -i owasp/zap2docker-stable zap.sh -daemon -host 0.0.0.0 -port 8080 -config api.disablekey=true