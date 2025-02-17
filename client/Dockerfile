# Use Node.js base image
FROM node:18

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install required dependencies
RUN apt-get update && apt-get install -y python3 make g++

# Install dependencies with --legacy-peer-deps to prevent conflicts
RUN npm install --legacy-peer-deps

# Manually verify Next.js is installed
RUN npx next --version

# Copy all source files
COPY . .

# Build the Next.js application
RUN npm run build

# Expose the port
EXPOSE 3000

# Start the application
CMD [ "npm", "start" ]
