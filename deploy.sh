#!/bin/bash

kill_process_on_port() {
  PORT=$1
  PROCESS_ID=$(lsof -t -i:$PORT)
  if [ -n "$PROCESS_ID" ]; then
    echo "Killing process on port $PORT (PID: $PROCESS_ID)"
    kill -9 $PROCESS_ID
    sleep 2  # Give the process some time to terminate
  fi
}

kill_process_on_port 8080

./mvnw clean install

./mvnw test

chmod +x ./mvnw

./mvnw spring-boot:run
