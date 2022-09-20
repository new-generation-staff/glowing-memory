#!/usr/bin/env bash

set -e;

docker pull confluentinc/cp-zookeeper:latest;
docker pull confluentinc/cp-kafka:latest;
docker pull kafkamanager/kafka-manager:latest;

docker-compose -f docker-compose.yml up -d
