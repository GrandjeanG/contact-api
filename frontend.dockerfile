FROM timbru31/java-node as build

COPY . /
WORKDIR /frontend
RUN yarn install
RUN yarn generateOpenApi
RUN yarn global add @quasar/cli
RUN quasar build

# production stage
FROM nginx:stable-alpine
COPY --from=build /frontend/dist/spa /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]