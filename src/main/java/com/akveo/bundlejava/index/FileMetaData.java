package com.akveo.bundlejava.index;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileMetaData {

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
    private Date date;

    @Field(type = FieldType.Text)
    private String[] keywords;

    @Field(type = FieldType.Text)
    private String format;

    @Field(type = FieldType.Keyword, name = "creator_tool")
    private String creatorTool;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
    private Date created;
}
