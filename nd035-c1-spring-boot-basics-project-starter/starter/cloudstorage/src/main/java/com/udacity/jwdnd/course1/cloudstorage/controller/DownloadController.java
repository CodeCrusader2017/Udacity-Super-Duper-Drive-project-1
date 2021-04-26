package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

//Followed various Udacity mentor updates to other students to get this section to work, although I could only get it to work via
//its own Download controller instead of placing in the FileController

@Controller
@RequestMapping("/download")
public class DownloadController {

    private FileService fileService;
    private UserService userService;

    public DownloadController(FileService fileService, UserService userService) {

        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping(value="")
    public ResponseEntity<Resource> download(@RequestParam(value = "filenameID") String filename,
                                             Model model,
                                             Authentication authentication) throws IOException
    {
            Files downfile = fileService.getFileDetail(filename, this.userService.getUserId(authentication.getName()));

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downfile.getFileName() + "\"");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(Integer.parseInt(downfile.getFilesize()))
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new ByteArrayResource(downfile.getFileData()));
    }
}