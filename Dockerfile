FROM sem247/dp-apipoc-devtools:1.0.0

WORKDIR /project/src/github.com/ONSdigital

RUN git clone https://github.com/ONSdigital/dp-apipoc-server.git

#COPY handler /project/src/github.com/ONSdigital/dp-apipoc-server/handler
#COPY model /project/src/github.com/ONSdigital/dp-apipoc-server/model
#COPY routing /project/src/github.com/ONSdigital/dp-apipoc-server/routing
#COPY upstream /project/src/github.com/ONSdigital/dp-apipoc-server/upstream
#COPY vendor /project/src/github.com/ONSdigital/dp-apipoc-server/vendor
#COPY main.go /project/src/github.com/ONSdigital/dp-apipoc-server
#COPY start-server.sh /project/src/github.com/ONSdigital/dp-apipoc-server
#COPY tests /project/src/github.com/ONSdigital/dp-apipoc-server/tests

ENV API_APP_PORT=3000
ENV WEBSITE_ROOT=https://www.ons.gov.uk
ENV USE_WEBSITE=false

ENV GOPATH=/project
ENV PATH $PATH:$GOPATH/bin

EXPOSE 3000

WORKDIR /project/src/github.com/ONSdigital/dp-apipoc-server

CMD ["sh", "run-tests.sh"]