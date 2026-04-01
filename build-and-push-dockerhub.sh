#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: ./push-to-dockerhub.sh <docker hub username>"
    exit 1
fi

DOCKER_HUB_USERNAME=$1

echo "Logging in to Docker Hub..."
docker login

# Services list
SERVICES=("molymarket-uam-service" "molymarket-payment-service" "molymarket-notification-service" "molymarket-product-order-service")
VERSION=("v1.0" "v1.0" "v1.0" "v1.0")

# Build and Push Backend Services
for SERVICE in "${SERVICES[@]}"; do
    echo "Building $SERVICE..."
    docker build -t $DOCKER_HUB_USERNAME/$SERVICE:${VERSION[$i]} ./backend/$SERVICE
    echo "Pushing $SERVICE..."
    docker push $DOCKER_HUB_USERNAME/$SERVICE:${VERSION[$i]}
done

# Build and Push Frontend
echo "Building molymarket-frontend..."
# Note: You might want to pass build args for production URLs
docker build -t $DOCKER_HUB_USERNAME/molymarket-frontend:${VERSION[0]} ./frontend/molymarket-frontend
echo "Pushing molymarket-frontend..."
docker push $DOCKER_HUB_USERNAME/molymarket-frontend:${VERSION[0]}

echo "All images pushed successfully!"
