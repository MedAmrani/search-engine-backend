package com.akveo.bundlejava.secSearch;

import com.akveo.bundlejava.index.Doc;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.net.MalformedURLException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static sun.misc.JarIndex.INDEX_NAME;

@Service
public class DocSearchService {
    private static final String FILE_DIRECTORY = "C:/Users/crona/Desktop/fscrawler-es7-2.7-SNAPSHOT/resumes";


    @Autowired
    private DocSearchRepository docSearchRepository;



    ElasticsearchTemplate elasticsearchTemplate;

    final ElasticsearchOperations elasticsearchOperations;

    public DocSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;


    }

    public List<DocSearchResponse> getAllDocs(Pageable pageable){
        List<Doc> docs = this.docSearchRepository.findAll(pageable)
                .getContent();

        return pretty(docs);
    }

    public List<DocSearchResponse> searchAllDocsMatchCriteria(DocSearchReq request, Pageable pageable) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();


//        if (!checkNullOrEmpty(request.getTitle())) {
////            boolQuery.must(QueryBuilders.wildcardQuery(, "*" + request.getTitle().toLowerCase() + "*"));
//            System.out.println(request.getTitle());
//            boolQuery.must(QueryBuilders.matchQuery("meta.title",request.getTitle().toLowerCase() ));
//        }
//
//        if(!checkNullOrEmpty(request.getContent())) {
//            boolQuery.must(QueryBuilders.matchQuery("content", request.getContent().toLowerCase()));
//        }
        if(!checkNullOrEmpty(request.getQ())) {
            boolQuery.should(QueryBuilders.multiMatchQuery(request.getQ().toLowerCase())
                    .field("meta.title")
                    .field("content")
                    .field("meta.author")
                    .type(MultiMatchQueryBuilder.Type.MOST_FIELDS));

        }



        if(!checkNullOrEmpty(request.getExtension())) {
            boolQuery.should(QueryBuilders.matchPhraseQuery("file.extension", request.getExtension().toLowerCase()));
        }

//        if(!checkNullOrEmpty(request.getStartDate()) &&
//                !checkNullOrEmpty(request.getEndDate())) {
//            String op = request.getProductionBudgetOp();
//            switch (op) {
//                case "gt": {
//                    boolQuery.filter(QueryBuilders.rangeQuery("Production_Budget").gt(request.getProductionBudgetMin()));
//                    break;
//                }
//                case "lt": {
//                    boolQuery.filter(QueryBuilders.rangeQuery("Production_Budget").lt(request.getProductionBudgetMin()));
//                    break;
//                }
//                case "bt": {
//                    if(request.getProductionBudgetMin() > request.getProductionBudgetMax()) {
//                        throw new IllegalArgumentException("ProductionBudgetMin is grater than ProductionBudgetMax");
//                    }
//                    boolQuery.filter(QueryBuilders.rangeQuery("Production_Budget")
//                            .from(request.getProductionBudgetMin())
//                            .to(request.getProductionBudgetMax()));
//                    break;
//                }
//                default: {
//                    throw new IllegalArgumentException("Invalid Production Budget Operator: eq|gt|lt|bt");
//                }
//            }
//        }
        builder.withQuery(boolQuery);
        builder.withPageable(pageable);
        NativeSearchQuery query = builder.build();
        return pretty(docSearchRepository.search(query).getContent());
    }

//
//    public List<String> getAllDirectors() {
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .addAggregation(AggregationBuilders
//                        .terms("directors")
//                        .field("Director.keyword")
//                        .size(10000))
//                .withPageable(PageRequest.of(0, 1))
//                .build();
//
//        Terms directors = elasticsearchOperations.query(query, response -> response.getAggregations().get("directors"));
//        return directors.getBuckets()
//                .stream()
//                .map(Terms.Bucket::getKeyAsString)
//                .collect(Collectors.toList());
//
//    }
//

//    public List<String> getAuthors() {
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .addAggregation(AggregationBuilders
//                        .terms("Doc_authors")
//                        .field("meta.author"))
//
//                .build();
//
//        SearchHits<Doc> searchHits = elasticsearchOperations.search(query, Doc.class,
//                IndexCoordinates.of("resumes"));
//        Aggregations aggregations = searchHits.getAggregations();
//
//        Terms topTags = aggregations.get("Doc_authors");
//
//        List<String> keys = topTags.getBuckets()
//                .stream()
//                .map(b -> b.getKeyAsString())
//                .collect(toList());
//
//        return keys;
//    }

    public List<String> getAllExtensions() {


//        Terms directors = elasticsearchOperations.query(query, response -> response.getAggregations().get("Doc_type"));
//        return directors.getBuckets()
//                .stream()
//                .map(Terms.Bucket::getKeyAsString)
//                .collect(Collectors.toList());
//        Object response =  docSearchRepository.search(query).getContent();
//        System.out.println("--------------------------------------------------------");
//        Aggregations aggregations = this.elasticsearchOperations.search(query, response -> response.getAggregations());
//
//
//        System.out.println(response);
//
//        Map<String, Aggregation> results = response.getAggregations().asMap();
//        StringTerms topTags = (StringTerms) results.get("Doc_type");
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders
                        .terms("Doc_type")
                        .field("file.extension"))

                .build();

        SearchHits<Doc> searchHits = elasticsearchOperations.search(query, Doc.class,
                IndexCoordinates.of("resumes"));
        Aggregations aggregations = searchHits.getAggregations();
//        aggregations.asMap().get("Doc_type");
//        System.out.println(aggregations.asMap().get("Doc_type"));

        Terms topTags = aggregations.get("Doc_type");

        List<String> keys = topTags.getBuckets()
                .stream()
                .map(b -> b.getKeyAsString())
                .collect(toList());

        return keys;


    }


    private static boolean checkNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public List<DocSearchResponse> pretty(List<Doc> docs){

        List<DocSearchResponse> pDocs = new ArrayList<>();

        for (Doc doc : docs){
            DocSearchResponse newDoc = new DocSearchResponse();
            newDoc.setId(doc.getId());
            newDoc.setTitle(doc.getMeta().getTitle());
            newDoc.setAuthor(doc.getMeta().getAuthor());
            newDoc.setContent(doc.getContent());
            newDoc.setExtension(doc.getFile().getExtension());
            newDoc.setFileName(doc.getFile().getFilename());
            newDoc.setFilesize(doc.getFile().getFilesize());
            newDoc.setPath(doc.getPath().getReal());
            newDoc.setDate(doc.getMeta().getDate());
            pDocs.add(newDoc);

        }
        return pDocs;
    }


    public Resource loadFileAsResource(String fileId) throws Exception {

        try {

//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Path filePath = Paths.get(this.getDocument(fileId).getPath().getReal());

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {

                return resource;

            } else {

                throw new FileNotFoundException("File not found " + fileId);

            }

        } catch (MalformedURLException ex) {

            throw new FileNotFoundException("File not found " + fileId);

        }

    }


    public Doc getDocument(String fileId) {

        return docSearchRepository.findId(fileId);



    }


    public void storeFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(FILE_DIRECTORY + "/" + file.getOriginalFilename());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("im here");

    }
}
