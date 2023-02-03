FROM node:18-alpine 

RUN mkdir -p /usr/src/react-app

WORKDIR /usr/src/react-app/

COPY . .

RUN npm ci 

RUN npm run build

ENV NODE_ENV production

EXPOSE 3000

CMD [ "npx", "serve", "build" ]

