# Use a stable Node.js version (avoid Alpine to prevent npm crashes)
FROM node:18-bullseye

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Ensure npm is upgraded and clean before installing dependencies
RUN npm install -g npm@latest && npm cache clean --force

# Install dependencies with better error handling
RUN npm install --omit=dev || npm install --omit=dev || (npm cache clean --force && npm install --omit=dev)

# Manually verify Next.js installation
RUN npx next --version

# Copy all source files after installing dependencies
COPY . .

# Build the Next.js application
RUN npm run build

# Expose the port
EXPOSE 3000

# Start the application
CMD [ "npm", "start" ]
