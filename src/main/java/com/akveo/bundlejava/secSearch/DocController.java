package com.akveo.bundlejava.secSearch;


import com.akveo.bundlejava.index.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping
public class DocController {

    @Autowired
    DocSearchRepository docSearchRepository;
    @Autowired
    DocSearchService docSearchService;

    List<String> files1 = new ArrayList<String>();
    private final Path rootLocation = Paths.get("C:/Users/crona/Desktop/fscrawler-es7-2.7-SNAPSHOT/resumes");

    @PostMapping(value = "/savefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestPart(value = "files", required = true) MultipartFile files[]) {
        System.out.println("controller");


        String message;
        try {
            System.out.println("in side try ");
            System.out.println("length"+files.length);
            for(int i = 0; i< files.length; i++){
                System.out.println("in side for loop");
                try {
                    Files.copy(files[i].getInputStream(), this.rootLocation.resolve(files[i].getOriginalFilename()));
                } catch (Exception e) {
                    throw new RuntimeException("FAIL!");
                }
                files1.add(files[i].getOriginalFilename());
            }

//            for(MultipartFile file:files){
//
//            }


            message = "Successfully uploaded!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Failed to upload!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping(value = "/Docs/{id}")
    public ResponseEntity<Doc> getDocById(@PathVariable("id") Long id) {
        Doc doc = docSearchRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/docs")
    public ResponseEntity<List<DocSearchResponse>> getAllDocs(Pageable pageable) {
        List<DocSearchResponse> docs = this.docSearchService.getAllDocs(pageable);

        return ResponseEntity.ok((docs));
    }

    @GetMapping(value = "/docs/_search")
    public ResponseEntity<List<DocSearchResponse>> searchDoc(Pageable pageable, DocSearchReq request) {
        List<DocSearchResponse> docs = docSearchService.searchAllDocsMatchCriteria(request, pageable);

        return ResponseEntity.ok(docs);
    }

    @GetMapping(value = "/docs/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) throws Exception {
        Resource resource = docSearchService.loadFileAsResource(id);
        return ResponseEntity.ok()
                .body(resource);


    }
    @PostMapping(value = "/upload")
//    @ResponseStatus(HttpStatus.OK)
    public void FileUpload(@RequestParam String file) {
        System.out.println(file);


//        for (MultipartFile file : files)
//            docSearchService.storeFile(file);



    }




//    @GetMapping("/directors")
//    public ResponseEntity<List<String>> getAllDirectors() {
//        List<String> directors = docSearchService.getAllDirectors();
//        return ResponseEntity.ok(directors);
//    }

//    @GetMapping("/authors")
//    public ResponseEntity<List<String>> getAllAuthors() {
//        List<String> authors = docSearchService.getAuthors();
//        return ResponseEntity.ok(authors);
//    }

    @GetMapping("/extensions")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> docExtension = docSearchService.getAllExtensions();
        return ResponseEntity.ok(docExtension);
    }


}
