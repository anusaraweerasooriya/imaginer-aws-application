# Use an official Node.js runtime as the base image
FROM node:18-alpine

# Set the working directory in the container
WORKDIR /app

# Install required system dependencies
RUN apk add --no-cache python3 make g++

# Copy package.json and package-lock.json
COPY package*.json ./

# Install only production dependencies
RUN npm install --production

# Copy all source files
COPY . .

# Skip build, just run `next start`
EXPOSE 3000

# Run the Next.js app without building
CMD [ "npx", "next", "start" ]
