# Use a full Node.js image instead of Alpine (Alpine lacks some required dependencies)
FROM node:18

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install required system dependencies (for building Next.js)
RUN apt-get update && apt-get install -y python3 make g++

# Install dependencies cleanly without cache
RUN npm ci --production

# Copy all source files to the working directory
COPY . .

# Ensure next is installed
RUN npx next --version

# Build the Next.js application
RUN npm run build

# Expose the port your app runs on
EXPOSE 3000

# Define the command to run your app
CMD [ "npm", "start" ]
