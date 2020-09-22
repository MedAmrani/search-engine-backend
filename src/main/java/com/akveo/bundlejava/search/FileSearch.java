package com.akveo.bundlejava.search;

import com.akveo.bundlejava.index.Doc;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileSearch extends ElasticsearchRepository<Doc,String> {
    @Query("{\"bool\": {\"must\": [{\"match_all\": {}}]}}")
    public List<Doc> getAll();

    List<Doc> findByContent(String content);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"meta.author\": \"?0\"}}]}}")
    List<Doc> findByAuthor(String author);


    @Query("{\"bool\": {\"must\": [{\"match\": {\"meta.title\": \"?0\"}}]}}")
    List<Doc> findByTitle(String title);

//    @Query("{\"bool\": {\"must\": {\"range\": {\"meta.date\": {\"gte\": \"?0\", \"lte\": \"?1\" }}}}")
//    List<Resume> findByDateRange(String start, String end);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"file.extension\": \"?0\"}}]}}")
    public List<Doc> findByExtension(String extension);

//    @Query("{\"sort\" : {\"file.filesize\": {\"order:\"asc\"}}}")
//    public List<Resume> sortBySize(String order);






}
