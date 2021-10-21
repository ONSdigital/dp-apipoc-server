dp-apipoc-server
================

Proof of concept API server

### Configuration

| Environment variable | Default                | Description
| -------------------- | ---------------------- | -----------
| API_APP_PORT         | :3000                  | The host and port to bind to
| ELASTICSEARCH_ROOT   | http://127.0.0.1:9200  | Elasticsearch's root URL
| ZEBEDEE_ROOT         | http://127.0.0.1:8082  | Zebedee Reader's root URL
| WEBSITE_ROOT         | https://www.ons.gov.uk | The ONS website's root URL
| USE_WEBSITE          | false                  | Toggle to get data from website rather than Zebedee Reader
| DEPRECATION_DATE     | ""                     | The date in which the decision was made to deprecate the service e.g. "Wed, 11 Nov 2020 23:59:59 GMT"
| IS_DEPRECATED        | false                  | Used to indicate a decision has been made to deprecate this service
| DEPRECATION_LINK     | ""                     | A url to further information of the deprecation of the service or endpoints
| DEPRECATION_SUNSET   | ""                     | The date of when this service will cease to return data on its endpoints and instead return blanket 404 status codes, e.g. "Fri, 11 Nov 2022 23:59:59 GMT"

### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright ©‎ 2017-2021, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
