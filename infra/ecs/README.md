# ECS Task Definitions

This directory contains the ECS task definition files (JSON) for the Moly-Market-v2.0 services, split from the `docker-compose.rds.yml`.

## How to use

1.  **Replace Placeholders**:
    -   `YOUR_AWS_ACCOUNT_ID`: Your 12-digit AWS Account ID.
    -   `YOUR_AWS_REGION`: The AWS region you are deploying to (e.g., `us-east-1` or `ap-southeast-2`).
    -   `YOUR_AWS_ACCOUNT_ID.dkr.ecr.YOUR_AWS_REGION.amazonaws.com/molymarket-uam-service:latest`: Ensure these image paths are correct for your ECR repository.

2.  **Service-to-Service Communication**:
    -   In `product-order-service-task-def.json`, the service URLs use `.local` suffixes (e.g., `http://uam-service.local:8082`). This assumes you are using AWS Cloud Map (Service Discovery) with a private DNS namespace like `local`.
    -   Update these URLs if you use a different service discovery method or an internal Load Balancer.

3.  **SQS**:
    -   The task definitions for `notification-service` and `product-order-service` currently point to `http://localstack:4566`.
    -   If you are using real AWS SQS in your ECS environment, replace `SQS_ENDPOINT` with the actual AWS SQS endpoint or remove it if your application defaults to the regional AWS SQS service.

4.  **Registration**:
    You can register these task definitions using the AWS CLI:
    ```bash
    aws ecs register-task-definition --cli-input-json file://uam-service-task-def.json
    aws ecs register-task-definition --cli-input-json file://payment-service-task-def.json
    aws ecs register-task-definition --cli-input-json file://notification-service-task-def.json
    aws ecs register-task-definition --cli-input-json file://product-order-service-task-def.json
    aws ecs register-task-definition --cli-input-json file://frontend-task-def.json
    ```

5.  **Environment Variables**:
    -   Sensitive information like `SPRING_DATASOURCE_PASSWORD` and `SECRET_KEY` should ideally be stored in AWS Secrets Manager or Parameter Store and referenced in the task definition using the `secrets` section instead of plain `environment` variables.

## Folder Structure

- `uam-service-task-def.json`
- `payment-service-task-def.json`
- `notification-service-task-def.json`
- `product-order-service-task-def.json`
- `frontend-task-def.json`
- `localstack-task-def.json`
