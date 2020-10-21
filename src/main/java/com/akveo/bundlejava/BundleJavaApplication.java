/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava;

import java.util.Locale;
import java.util.TimeZone;

import com.akveo.bundlejava.search.FileSearch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@SuppressWarnings({"checkstyle:FinalClass", "checkstyle:HideUtilityClassConstructor"})
public class BundleJavaApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UCT"));

        SpringApplication.run(BundleJavaApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner start(FileSearch fileSearch){
//        return (String... args) -> {
////            System.out.println(OffsetDateTime.parse("2007-12-03T10:15:30+00:00"));
////            Resume resume = Resume.builder().id("1180c2305764e45c3419a046df345eac")
////                    .meta(FileMetaData.builder().date(OffsetDateTime.parse("2018-03-08T09:39:05.000+00:00"))
////                            .build()).build();
////            fileSearch.save(resume);
////                          fileSearch.findAll().forEach(file -> System.out.println(file.getMeta()));
////            fileSearch.findByAuthor("Bako Ali").forEach(file -> System.out.println(file.getMeta()));
//
//        };
//    }

}
