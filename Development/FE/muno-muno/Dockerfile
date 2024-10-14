# 1. Build Stage
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy only package.json
COPY package.json .

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the Vue app
RUN npm run build

# 2. Production Stage
FROM nginx:alpine

# Copy built files from the build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
