# 1. Build Stage
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy only package.json
COPY package.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the Vue app
RUN npm run build

# Step 2: Create Nginx Server
FROM nginx:1.20 AS prod-stage
COPY --from=build /app/dist /usr/share/nginx/html
COPY --from=build /app/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]