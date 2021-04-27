package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(CredentialService credentialService, NoteService noteService, UserService userService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        model.addAttribute("filenamesLoop", this.fileService.getFiles(this.userService.getUserId(authentication.getName())));
        model.addAttribute("notes",this.noteService.getNotes(this.userService.getUserId(authentication.getName())));
        model.addAttribute("credentials",this.credentialService.getCredentials(this.userService.getUserId(authentication.getName())));
        model.addAttribute("encryptionService",encryptionService);

        return "home";
    }
}



