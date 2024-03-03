#!/bin/bash

# Function to check if Docker container is running
check_container_running() {
  CONTAINER_NAME=$1
  RUNNING=$(docker inspect --format="{{.State.Running}}" $CONTAINER_NAME 2>/dev/null)

  if [ "$RUNNING" == "true" ]; then
    return 0  # Container is running
  else
    return 1  # Container is not running
  fi
}

# Stop and remove the Docker container if it's running
if check_container_running todolist_container; then
  echo "Stopping and removing the Docker container..."
  docker stop todolist_container >/dev/null
  docker rm todolist_container >/dev/null
else
  echo "Docker container is not running."
fi
