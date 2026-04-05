#!/bin/bash

# Script to build and push Docker images to Docker Hub
# This script is located in docker/local/

# Get the project root directory relative to this script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "$SCRIPT_DIR/../.." && pwd )"

if [ -z "$1" ]; then
    echo "Usage: ./build-and-push-dockerhub.sh <docker hub username>"
    exit 1
fi

DOCKER_HUB_USERNAME=$1

echo "Logging in to Docker Hub..."
docker login

# Services list
SERVICES=("molymarket-uam-service" "molymarket-payment-service" "molymarket-notification-service" "molymarket-product-order-service")
VERSION="v1.0"

# Build and Push Backend Services
for SERVICE in "${SERVICES[@]}"; do
    echo "Building $SERVICE..."
    docker build -t "$DOCKER_HUB_USERNAME/$SERVICE:$VERSION" "$PROJECT_ROOT/backend/$SERVICE"
    echo "Pushing $SERVICE..."
    docker push "$DOCKER_HUB_USERNAME/$SERVICE:$VERSION"
done

# Build and Push Frontend
echo "Building molymarket-frontend..."
docker build -t "$DOCKER_HUB_USERNAME/molymarket-frontend:$VERSION" "$PROJECT_ROOT/frontend/molymarket-frontend"
echo "Pushing molymarket-frontend..."
docker push "$DOCKER_HUB_USERNAME/molymarket-frontend:$VERSION"

echo "All images pushed successfully!"
