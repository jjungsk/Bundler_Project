# syntax=docker/dockerfile:1
FROM node:16
WORKDIR /react
COPY package.json .
RUN npm install
COPY . .
EXPOSE 3000
CMD [ "npm", "start" ]
