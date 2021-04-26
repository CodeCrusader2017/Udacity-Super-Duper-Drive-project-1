package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FilesMapper filesMapper;
    private UserMapper userMapper;
    private UserService userService;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    public void addFiles(MultipartFile file, Integer fileSetUpUserID) throws IOException {
        filesMapper.addFile(new Files(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), fileSetUpUserID, file.getBytes()));
    }

    public boolean checkIfFileExistsOnDB (String fileName, Integer userid) {
        return filesMapper.fileNameExists(fileName, userid);
    }

    public boolean deleteFile (String fileName, Integer userid) {
        return filesMapper.deleteFileForUser(fileName, userid);
    }

    public List<Files> getFiles(Integer userid) {
        return filesMapper.getAllFiles(userid);
    }

    public Files getFileDetail(String fileName, Integer userid) {
        return filesMapper.getFileDetail(fileName, userid);
    }

 }
