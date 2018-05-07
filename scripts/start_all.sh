#!/usr/bin/env bash

docker network create network1
docker run --rm --name memcached --network=network1 -d memcached:latest
docker run --rm --name downstreamservice --network=network1 -d downstreamservice:latest
docker run --rm --name exampleservice --network=network1 -d exampleservice:latest
docker run --rm --name=sidecarproxy --network=network1 -p 9901:9901 -p 10000:10000 -d envoy:latest
