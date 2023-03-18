FROM node:18-alpine

WORKDIR /code

ENV PATH /code/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent

COPY . ./

CMD ["npm", "start"]
