dp-apipoc-server's spec
=======================

POC API Server's specification

This module houses the API POC server's specification. The specification is an [OpenAPI 2.0](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md) formatted document. It's created via [Swagger Editor](http://editor.swagger.io) and the source is in `swagger.yaml`.

[DapperDox](http://dapperdox.io) is use for rendering the specification.  At the time of writing this piece, DapperDox only supports rendering `json` formatted specifications.
Consequently, a `json` formatted version of the specification is generated from Swagger Editor and placed in `api-spec/swagger.json`. The JSON formatted specification is build into a Docker image for deployment.

| Environment variable | Default | Description
| -------------------- | ------- | -----------
| bind-addr            | :8080   | The default port the server binds to