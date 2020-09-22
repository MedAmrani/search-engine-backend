package com.akveo.bundlejava.search;

import com.akveo.bundlejava.index.Doc;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearch {

    public List<Doc> findAll();
    public List<Doc> findByAuthor(String author);
    public List<Doc> findByTitle(String title);
    public List<Doc> findByContent(String content);
    public Page<Doc> findByDateRange(String start, String end);
    public List<Doc> findByExtension(String extension);
    public Page<Doc> sortBySize(String order);

}
