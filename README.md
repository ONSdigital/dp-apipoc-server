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


### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright ©‎ 2017, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
