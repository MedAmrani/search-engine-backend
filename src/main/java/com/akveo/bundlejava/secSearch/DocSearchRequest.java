package com.akveo.bundlejava.secSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocSearchRequest {
    private String title;
    private String content;
    private String author;
    private String startDate;
    private String endDate;
    private String extension;
    private String fileName;
    private String filesize;
}
