package com.akveo.bundlejava.search;

import com.akveo.bundlejava.index.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SearchController {
    @Autowired
    public ISearch service;

    @RequestMapping(value = "/api/search", method = RequestMethod.GET)
    public List<Doc> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/api/search/sortBySize", method = RequestMethod.POST)
    public List<Doc> sortBySize(@RequestParam String order) {
        return service.sortBySize(order).getContent();
    }


    @RequestMapping(value = "/api/search/author", method = RequestMethod.POST)
    public List<Doc> findByAuthor(@RequestParam String author) {
        System.out.print(author);
        return service.findByAuthor(author);

    }

    @RequestMapping(value = "/api/search/title", method = RequestMethod.POST)
    public List<Doc> findByTitle(@RequestParam String title) {
        System.out.print(title);
        return service.findByTitle(title);

    }


    @RequestMapping(value = "/api/search/content", method = RequestMethod.POST)
    public List<Doc> findByContent(@RequestParam String content) {
        return service.findByContent(content);

    }

    @RequestMapping(value = "/api/search/dateRange", method = RequestMethod.POST)
    public List<Doc> findByDateRange(@RequestParam(value = "start")  String start,
                                     @RequestParam(value = "end")  String end) {
        System.out.println(start+end);
        return service.findByDateRange(start, end).getContent();

    }

    @RequestMapping(value = "/api/search/extension", method = RequestMethod.POST)
    public List<Doc> findByExtension(@RequestParam  String extension) {

        return service.findByExtension(extension);

    }




}
