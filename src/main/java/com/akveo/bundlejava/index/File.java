package com.akveo.bundlejava.index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    @Field(type = FieldType.Keyword)
    private String extension;

    @Field(type = FieldType.Keyword,name = "content_type")
    private String contentType;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
    private Date created;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time,name = "last_modified")
    private Date lastModified;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time,name ="last_accessed" )
    private Date lastAccessed;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time,name = "indexing_date")
    private Date indexingDate;

    @Field(type = FieldType.Long)
    private long filesize;

    @Field(type=FieldType.Keyword)
    private String filename;

    @Field(type = FieldType.Keyword)
    private String url;

}
