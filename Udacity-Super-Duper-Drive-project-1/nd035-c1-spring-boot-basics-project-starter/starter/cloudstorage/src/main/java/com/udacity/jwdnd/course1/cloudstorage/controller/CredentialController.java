package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/credentialrq")
public class CredentialController {
    //private final NoteService noteService;
    //private final UserService userService;
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Credential credential,
                             Authentication authentication,
                             Model model,
                             @RequestParam(value = "CredentialActionOption", required = false) Integer credentialAction) {

        credential.setUserId(this.userService.getUserId(authentication.getName()));

        if (credentialAction == null) {
            System.out.println("IN CREDENTIAL CREATE CREDENTIAL ");
            try {
                int rowsCreated = credentialService.createCredential(credential);
                model.addAttribute("successMessage", "Your Credential has now been created. ");
                return "result";
            }catch (SQLException exception) {
                System.out.println("ERROR when creating Credential in database for user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
            }
        } else
            {  //@DeleteMapping <-- Can't get to work above, so had to pass in parameter etc
            if (credentialAction == 1)  //1 = delete note, 2 = edit note
            {
                System.out.println("IN CREDENTIAL DELETE CREDENTIAL ");
                try {
                    int rowsDeleted = credentialService.deleteCredential(credential.getCredentialsId());
                    model.addAttribute("successMessage", "Credential " + credential.getCredentialsId() + " was deleted. ");
                    return "result";
                } catch (SQLException exception) {
                    System.out.println("ERROR when deleting CREDENTIAL in database for note ID = " + credential.getCredentialsId() + " and user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
                }

            }

            if (credentialAction == 2)  //1 = delete note, 2 = edit note        //@PutMapping <-- Can't get to work above,so had to pass in a parm
            {
                System.out.println("IN CREDENTIAL UPDATE CREDENTIAL = " + credentialAction);
                try {
                    int rowsUpdated = credentialService.updateCredential(credential);
                    model.addAttribute("successMessage", "Credential " + credential.getCredentialsId() + " was updated. ");
                    return "result";
                } catch (SQLException exception) {
                    System.out.println("ERROR when updating CREDENTIAL in database for user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
                }

            }
        }

        return "redirect:/home";
    }
}




