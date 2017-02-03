package upstream

import (
	"encoding/json"

	"github.com/ONSdigital/dp-apipoc-server/model"
	"github.com/ONSdigital/go-ns/log"
	"gopkg.in/olivere/elastic.v3"
)

type ElasticService interface {
	Ping() (model.Response, error)
	GetPagedData(document string, start int, limit int) (model.Response, error)
	GetDataById(document string, field string, dataId string) (model.Response, error)
	GetPagedDataById(document string, field string, dataId string, start int, limit int) (model.Response, error)
	GetSpecificTimeSeriesSpecificDataset(datasetId string, timeseriesId string) (model.Response, error)
	SearchData(term string, start int, limit int) (model.Response, error)

	buildItem(query *elastic.SearchService) (model.Response, error)
	buildItems(query *elastic.SearchService, start int, limit int) (model.Response, error)

	buildPagedQuery(document string, start int, limit int) *elastic.SearchService
	buildFieldQuery(document string, field string, term string) *elastic.SearchService
	buildPagedFieldQuery(document string, field string, term string, start int, limit int) *elastic.SearchService
	buildSearchQuery(term string, start int, limit int) *elastic.SearchService

	executeQuery(query *elastic.SearchService) ([]interface{}, int64, error)
}

func NewElasticService(url string) ElasticService {
	client, err := elastic.NewClient(elastic.SetURL(url), elastic.SetSniff(false))
	if err != nil {
		log.Error(err, nil)
		panic(err)
	}

	return &service{url: url, elasticClient: client}
}

type service struct {
	url           string
	elasticClient *elastic.Client
}

func (s *service) Ping() (model.Response, error) {
	res, c, err := s.elasticClient.Ping("http://localhost:9200").Do()

	if err != nil {
		log.Error(err, nil)
		return model.Response{Code: model.DEPENDENCY_CONNECTION_ERROR, Body: &model.ElasticStatus{Status: "NOT_AVAILABLE", StatusCode: 0, PingResponse: nil}}, err
	}

	return model.Response{Code: model.OK, Body: &model.ElasticStatus{Status: "RUNNING", StatusCode: c, PingResponse: res}}, nil
}

func (s *service) GetDataById(document string, field string, dataId string) (model.Response, error) {
	query := s.buildFieldQuery(document, field, dataId)

	return s.buildItem(query)
}

func (s *service) GetSpecificTimeSeriesSpecificDataset(datasetId string, timeseriesId string) (model.Response, error) {
	qs := elastic.NewBoolQuery().
		Must(elastic.NewMatchQuery("description.datasetId", datasetId)).
		Must(elastic.NewMatchQuery("description.cdid", timeseriesId))

	query := s.elasticClient.Search().
		Index("ons").
		Type("timeseries").
		Query(qs).
		Sort("uri", true)

	return s.buildItem(query)
}

func (s *service) GetPagedData(document string, start int, limit int) (model.Response, error) {
	query := s.buildPagedQuery(document, start, limit)

	return s.buildItems(query, start, limit)
}

func (s *service) GetPagedDataById(document string, field string, dataId string, start int, limit int) (model.Response, error) {
	query := s.buildPagedFieldQuery(document, field, dataId, start, limit)

	return s.buildItems(query, start, limit)
}

func (s *service) SearchData(term string, start int, limit int) (model.Response, error) {
	query := s.buildSearchQuery(term, start, limit)

	return s.buildItems(query, start, limit)
}

func (s *service) buildItem(query *elastic.SearchService) (model.Response, error) {
	res, _, err := s.executeQuery(query)

	if err != nil {
		log.Error(err, nil)
		return model.Response{Code: model.ERROR, Body: nil}, err
	}

	if len(res) < 1 {
		return model.Response{Code: model.NOT_FOUND, Body: nil}, nil
	}

	return model.Response{Code: model.OK, Body: res[0]}, nil
}

func (s *service) buildItems(query *elastic.SearchService, start int, limit int) (model.Response, error) {
	res, hits, err := s.executeQuery(query)

	if err != nil {
		log.Error(err, nil)
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
		log.Error(err, nil)
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
				log.Error(e, nil)
				return nil, 0, e
			}

			items[k] = item
		}

		return items, totalHits, nil
	}

	return nil, totalHits, nil

}

func (s *service) buildFieldQuery(document string, field string, term string) *elastic.SearchService {
	qs := elastic.NewBoolQuery().
		Must(elastic.NewMatchQuery(field, term))

	// qs := elastic.NewConstantScoreQuery(elastic.NewTermQuery(field, term))

	return s.elasticClient.Search().
		Index("ons").
		Type(document).
		Query(qs).
		Sort("uri", true)
}

func (s *service) buildPagedQuery(document string, start int, limit int) *elastic.SearchService {
	return s.elasticClient.Search().
		Index("ons").
		Type(document).
		Sort("uri", true).
		From(start).
		Size(limit)
}

func (s *service) buildPagedFieldQuery(document string, field string, term string, start int, limit int) *elastic.SearchService {
	//
	//	qs := elastic.NewBoolQuery().
	//		Must(elastic.NewMatchQuery(field, term))
	//
	qs := elastic.NewConstantScoreQuery(elastic.NewTermQuery(field, term))

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
