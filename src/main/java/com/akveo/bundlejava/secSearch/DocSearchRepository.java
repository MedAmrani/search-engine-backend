package com.akveo.bundlejava.secSearch;

import com.akveo.bundlejava.index.Doc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocSearchRepository extends ElasticsearchRepository<Doc, Long> {
    @Query("{\"bool\":{\"must\":{\"match\":{\"meta.title\":\"?0\"}}}}")
    Page<Doc> findAllByTitle(Pageable pageable, String title);


    @Query("{\"terms\": {\"_id\": [ \"?0\" ] }}")
    Doc findId(String fileId);
}
