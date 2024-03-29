package upstream

import (
	"context"
	"encoding/json"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/log.go/v2/log"
	"gopkg.in/olivere/elastic.v3"
)

type ElasticService interface {
	Ping(ctx context.Context) (model.Response, error)
	GetByType(ctx context.Context, document string, start int, limit int) (model.Response, error)
	GetById(ctx context.Context, document string, field string, dataId string, start int, limit int) (model.Response, error)
	GetSpecificTimeSeriesSpecificDataset(ctx context.Context, datasetId string, timeseriesId string) (model.Response, error)
	SearchData(ctx context.Context, term string, start int, limit int) (model.Response, error)

	buildItem(ctx context.Context, query *elastic.SearchService) (model.Response, error)
	buildItems(ctx context.Context, query *elastic.SearchService, start int, limit int) (model.Response, error)

	buildQueryByType(document string, start int, limit int) *elastic.SearchService
	buildQueryByField(document string, field string, term string, start int, limit int) *elastic.SearchService
	buildSearchQuery(term string, start int, limit int) *elastic.SearchService

	executeQuery(query *elastic.SearchService) ([]interface{}, int64, error)
}

func NewElasticService(url string) (ElasticService, error) {
	client, err := elastic.NewClient(elastic.SetURL(url), elastic.SetSniff(false))
	if err != nil {
		return nil, err
	}

	return &service{url: url, elasticClient: client}, nil
}

type service struct {
	url           string
	elasticClient *elastic.Client
}

func (s *service) Ping(ctx context.Context) (model.Response, error) {
	res, c, err := s.elasticClient.Ping(s.url).Do()
	if err != nil {
		log.Error(ctx, "Ping: failed ping to elasticsearch client", err)
		return model.Response{Code: model.DEPENDENCY_CONNECTION_ERROR, Body: &model.ElasticStatus{Status: "NOT_AVAILABLE", StatusCode: 0, PingResponse: nil}}, err
	}

	return model.Response{Code: model.OK, Body: &model.ElasticStatus{Status: "RUNNING", StatusCode: c, PingResponse: res}}, nil
}

func (s *service) GetSpecificTimeSeriesSpecificDataset(ctx context.Context, datasetId string, timeseriesId string) (model.Response, error) {
	qs := elastic.NewBoolQuery().
		Must(elastic.NewMatchQuery("description.datasetId", datasetId)).
		Must(elastic.NewMatchQuery("description.cdid", timeseriesId))

	query := s.elasticClient.Search().
		Index("ons").
		Type("timeseries").
		Query(qs).
		Sort("uri", true)

	return s.buildItem(ctx, query)
}

func (s *service) GetByType(ctx context.Context, document string, start int, limit int) (model.Response, error) {
	query := s.buildQueryByType(document, start, limit)

	return s.buildItems(ctx, query, start, limit)
}

func (s *service) GetById(ctx context.Context, document string, field string, dataId string, start int, limit int) (model.Response, error) {
	query := s.buildQueryByField(document, field, dataId, start, limit)

	return s.buildItems(ctx, query, start, limit)
}

func (s *service) SearchData(ctx context.Context, term string, start int, limit int) (model.Response, error) {
	query := s.buildSearchQuery(term, start, limit)

	return s.buildItems(ctx, query, start, limit)
}

func (s *service) buildItem(ctx context.Context, query *elastic.SearchService) (model.Response, error) {
	res, _, err := s.executeQuery(query)
	if err != nil {
		log.Error(ctx, "builtItem: failed to execute query", err)
		return model.Response{Code: model.ERROR, Body: nil}, err
	}

	if len(res) < 1 {
		return model.Response{Code: model.NOT_FOUND, Body: nil}, nil
	}

	return model.Response{Code: model.OK, Body: res[0]}, nil
}

func (s *service) buildItems(ctx context.Context, query *elastic.SearchService, start int, limit int) (model.Response, error) {
	res, hits, err := s.executeQuery(query)

	if err != nil {
		log.Error(ctx, "buildItems: failed to execute query", err)
		return model.Response{Code: model.ERROR, Body: nil}, err
	}

	if len(res) < 1 {
		return model.Response{Code: model.NOT_FOUND, Body: nil}, nil
	}

	items := model.Items{ItemsPerPage: limit, StartIndex: start, TotalItems: hits, Items: res}

	return model.Response{Code: model.OK, Body: items}, nil
}

func (s *service) executeQuery(query *elastic.SearchService) ([]interface{}, int64, error) {
	searchResult, err := query.Do()

	if err != nil {
		return nil, 0, err
	}

	totalHits := searchResult.Hits.TotalHits

	if totalHits > 0 {
		count := len(searchResult.Hits.Hits)

		items := make([]interface{}, count)

		for k, h := range searchResult.Hits.Hits {
			var item interface{}
			e := json.Unmarshal(*h.Source, &item)
			if e != nil {
				return nil, 0, e
			}

			items[k] = item
		}

		return items, totalHits, nil
	}

	return nil, totalHits, nil

}

func (s *service) buildQueryByType(document string, start int, limit int) *elastic.SearchService {
	return s.elasticClient.Search().
		Index("ons").
		Type(document).
		Sort("uri", true).
		From(start).
		Size(limit)
}

func (s *service) buildQueryByField(document string, field string, term string, start int, limit int) *elastic.SearchService {
	qs := elastic.NewBoolQuery().
		Must(elastic.NewMatchQuery(field, term))

	//qs := elastic.NewConstantScoreQuery(elastic.NewTermQuery(field, term))

	return s.elasticClient.Search().
		Index("ons").
		Type(document).
		Query(qs).
		Sort("uri", true).
		From(start).
		Size(limit)
}

func (s *service) buildSearchQuery(term string, start int, limit int) *elastic.SearchService {
	qs := elastic.NewDisMaxQuery().
		Query(elastic.NewMatchQuery("description.datasetId", term), elastic.NewMatchQuery("description.cdid", term), elastic.NewMatchQuery("description.title", term))

	return s.elasticClient.Search().
		Index("ons").
		Type("dataset", "timeseries").
		Query(qs).
		Sort("uri", true).
		From(start).
		Size(limit)
}
