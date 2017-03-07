FROM golang:1.8-alpine

COPY handler /work/src/github.com/ONSdigital/dp-apipoc-server/handler
COPY model /work/src/github.com/ONSdigital/dp-apipoc-server/model
COPY routing /work/src/github.com/ONSdigital/dp-apipoc-server/routing
COPY upstream /work/src/github.com/ONSdigital/dp-apipoc-server/upstream
COPY vendor /work/src/github.com/ONSdigital/dp-apipoc-server/vendor
COPY main.go /work/src/github.com/ONSdigital/dp-apipoc-server

WORKDIR /work

ENV GOPATH=/work
ENV PATH $PATH:$GOPATH/bin

RUN go install github.com/ONSdigital/dp-apipoc-server

ENV API_APP_PORT=3000
ENV WEBSITE_ROOT=https://www.ons.gov.uk
ENV USE_WEBSITE=false

EXPOSE $API_APP_PORT

ENTRYPOINT dp-apipoc-server