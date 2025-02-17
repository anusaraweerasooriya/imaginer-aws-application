FROM node:18-alpine 

WORKDIR /app 

COPY package.json package-lock.json ./ 

RUN npm install  # Install all dependencies, including Next.js

COPY . . 

RUN npm run build  # Build Next.js app

EXPOSE 3000 

CMD ["npm", "run", "start"]
