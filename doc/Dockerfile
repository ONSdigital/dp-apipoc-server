FROM alpine:3.8

WORKDIR /opt/app/dapperdox

ENV DAPPERDOX_TARBALL="https://github.com/DapperDox/dapperdox/releases/download/v1.1.0/dapperdox-1.1.0.linux-amd64.tgz"

RUN set -ex; \
	\
	apk add --no-cache --virtual .fetch-deps \
		ca-certificates \
		openssl \
		tar \
	; \
	\
	wget -O dapperdox.tgz "$DAPPERDOX_TARBALL"; \
	\
	tar -xf dapperdox.tgz --strip-components=1; \
	rm dapperdox.tgz; \
	\
	apk del .fetch-deps;

RUN apk --no-cache add bash

COPY api-spec/swagger.json .
COPY assets assets

CMD ./dapperdox -spec-dir=. -force-specification-list=true -bind-addr=:8080
