package com.akveo.bundlejava.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilePath {

    @Field(type = FieldType.Keyword)
    private String root;

    @Field(type = FieldType.Keyword)
    private String virtual;

    @Field(type = FieldType.Keyword)
    private String real;
}
