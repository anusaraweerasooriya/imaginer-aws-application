# Use an official lightweight Node.js image
FROM node:18-alpine 

# Set working directory
WORKDIR /app 

# Copy package.json and package-lock.json first (for caching)
COPY package.json package-lock.json ./ 

# Install all dependencies (including dev dependencies)
RUN npm install 

# Copy the entire project to the container
COPY . . 

# Ensure Next.js is installed before running the build
RUN npm install next 

# Build the Next.js application
RUN npm run build 

# Expose the required port
EXPOSE 3000 

# Start the Next.js application in production mode
CMD ["npm", "run", "start"]
