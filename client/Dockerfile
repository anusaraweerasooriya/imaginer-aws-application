# Use an official Node.js runtime as the base image
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Install required system dependencies
RUN apk add --no-cache python3 make g++

# Copy package.json and package-lock.json
COPY package*.json ./

# Install only production dependencies (to avoid unnecessary files)
RUN npm install --production

# Copy all source files EXCEPT .next (since we will copy it separately)
COPY . .

# Ensure Next.js is installed
RUN npx next --version

# Copy the pre-built Next.js build folder from local machine
COPY .next .next

# Expose the port (important for AWS Elastic Beanstalk)
EXPOSE 3000

# Run the Next.js app in production mode
CMD ["npx", "next", "start"]
