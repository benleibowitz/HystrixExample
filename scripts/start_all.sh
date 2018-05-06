#!/usr/bin/env bash

docker network rm network1
docker network create network1
docker run --rm --name downstreamservice --network=network1 -d downstreamservice:latest
docker run --rm --name exampleservice --network=network1 -d exampleservice:latest
docker run --network=network1 --name=sidecarproxy -p 9901:9901 -p 10000:10000 -d envoy:v1