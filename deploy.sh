#!/bin/bash

./mvnw clean install

./mvnw test

chmod +x ./mvnw

./mvnw spring-boot:run
