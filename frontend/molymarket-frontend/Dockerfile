# Stage 1: Build the React application
FROM node:20-alpine AS build

# Set the working directory
WORKDIR /app

# Define build arguments for backend service URLs
ARG VITE_PRODUCT_ORDER_SERVICE_URL
ARG VITE_UAM_SERVICE_URL
ARG VITE_NOTIFICATION_SERVICE_URL

# Set them as environment variables for the build process
ENV VITE_PRODUCT_ORDER_SERVICE_URL=$VITE_PRODUCT_ORDER_SERVICE_URL
ENV VITE_UAM_SERVICE_URL=$VITE_UAM_SERVICE_URL
ENV VITE_NOTIFICATION_SERVICE_URL=$VITE_NOTIFICATION_SERVICE_URL

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve the application using Nginx
FROM nginx:alpine

# Copy the build output from the build stage to the Nginx html directory
COPY --from=build /app/dist /usr/share/nginx/html

# Copy the Nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
