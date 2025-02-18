# Use an official Node.js runtime as the base image
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Install required system dependencies
RUN apk add --no-cache python3 make g++

# Copy only necessary files (avoid overwriting pre-built `.next` folder)
COPY package*.json ./

# Install only production dependencies
RUN npm install --production

# Copy all source files EXCEPT the `.next` folder
COPY . . 

# Copy the pre-built Next.js build folder from local machine
COPY .next .next

# Expose the port (important for AWS Elastic Beanstalk)
EXPOSE 3000

# Run the Next.js app in production mode
CMD [ "npx", "next", "start" ]
