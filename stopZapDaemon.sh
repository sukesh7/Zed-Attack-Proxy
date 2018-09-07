#!/usr/bin/env bash
jps | grep zap-D-2018-08-28.jar | cut -d " " -f 1 | xargs kill -9
