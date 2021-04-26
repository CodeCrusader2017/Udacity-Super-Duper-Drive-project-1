package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/file")
public class FileController {

    //private final UserService userService;
    private FileService fileService;
    private UserService userService;
    private CredentialService credentialService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public FileController(FileService fileService, UserService userService, CredentialService credentialService, NoteService noteService, EncryptionService encryptionService) {

        this.fileService = fileService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getFileController(Model model,
                                   HttpServletResponse response,
                                   @ModelAttribute User user,
                                   Authentication authentication,
                                   @RequestParam(value = "filenameID", required = false) String filename)
    {
        if (filename != null) {
            try {
                this.fileService.deleteFile(filename, this.userService.getUserId(authentication.getName()));
                model.addAttribute("successMessage", "File " + filename + " was deleted. ");
                return "result";
            } catch (Exception e) {
                System.out.println("SPRING RUN CONSOLE DEBUG ERROR message: issue deleting file " + filename + " from database with message " + e.getMessage());
            }
        }

        model.addAttribute("filenamesLoop", this.fileService.getFiles(this.userService.getUserId(authentication.getName())));
        model.addAttribute("notes",this.noteService.getNotes(this.userService.getUserId(authentication.getName())));
        model.addAttribute("credentials",this.credentialService.getCredentials(this.userService.getUserId(authentication.getName())));
        model.addAttribute("encryptionService",encryptionService);

        return "home";
    }


    @PostMapping
    public String uploadFile (@RequestParam(value = "fileUpload", required = false) MultipartFile file,
                              @ModelAttribute User user,
                              Model model,
                              Authentication authentication) throws IOException, SQLException
    {
        if (file != null) {
            if (file.getOriginalFilename().isEmpty()){
                model.addAttribute("errorMessage", "Sorry, you have not yet selected a file to upload");
            }
            else {
                   if (!this.fileService.checkIfFileExistsOnDB(file.getOriginalFilename(), this.userService.getUserId(authentication.getName()))) {
                       try {
                           this.fileService.addFiles(file, this.userService.getUserId(authentication.getName()));
                       } catch (IOException e) {
                           System.out.println("SPRING RUN CONSOLE DEBUG ERROR message: issue inserting BLOB data on file " + file.getOriginalFilename() + " with message " + e.getMessage());
                       }
                   } else {
                       System.out.println("SPRING RUN CONSOLE DEBUG ERROR message: file " + file.getOriginalFilename() + " already exists on the FILES database table !");
                       model.addAttribute("errorMessage", "Sorry, you have already uploaded file " + file.getOriginalFilename());
                   }
            }
        }

        model.addAttribute("filenamesLoop", this.fileService.getFiles(this.userService.getUserId(authentication.getName())));
        model.addAttribute("notes",this.noteService.getNotes(this.userService.getUserId(authentication.getName())));
        model.addAttribute("credentials",this.credentialService.getCredentials(this.userService.getUserId(authentication.getName())));
        model.addAttribute("encryptionService",encryptionService);

        return "home";
    }

}


