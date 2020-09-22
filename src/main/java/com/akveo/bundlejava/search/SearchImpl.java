package com.akveo.bundlejava.search;

import com.akveo.bundlejava.index.Doc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class SearchImpl implements ISearch{
    @Autowired
    FileSearch respository;


    @Override
    public List<Doc> findAll() {
        return (List<Doc>) respository.getAll();
    }

    @Override
    public List<Doc> findByAuthor(String author) {
        return respository.findByAuthor(author);
    }

    @Override
    public List<Doc> findByTitle(String title) {
        return respository.findByTitle(title);
    }

    @Override
    public List<Doc> findByContent(String content) {
        return respository.findByContent(content);
    }

    @Override
    public Page<Doc> findByDateRange(String start, String end) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("meta.date")
                        .gte(start)
                        .lte(end));
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        return respository.search(query);

    }

    public List<Doc> findByExtension(@RequestParam String extension) {

        return respository.findByExtension(extension);

    }

    @Override
    public Page<Doc> sortBySize(String order) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        NativeSearchQuery query = null;

        if(order.equals("asc")){
            searchQuery.withSort(SortBuilders.fieldSort("file.filesize").order(SortOrder.ASC));
            query = searchQuery.build();
        }else if(order.equals("desc")){
            searchQuery.withSort(SortBuilders.fieldSort("file.filesize").order(SortOrder.DESC));
            query = searchQuery.build();
        }

        return respository.search(query);
    }
}
