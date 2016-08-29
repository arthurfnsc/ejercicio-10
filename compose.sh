#!/usr/bin/env bash

./gradlew clean build -x test

cd build/libs

docker-compose up --build

docker-compose stop

cd ../..
