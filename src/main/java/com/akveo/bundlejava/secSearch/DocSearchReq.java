package com.akveo.bundlejava.secSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocSearchReq {

    private String q;
    private String startDate;
    private String endDate;
    private String extension;
    private String filesize;

}
