package com.akveo.bundlejava.secSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocSearchResponse {
    private String id;
    private String title;
    private String content;
    private String author;
    private String extension;
    private String fileName;
    private Long filesize;
    private String path;
    private Date date;
}
