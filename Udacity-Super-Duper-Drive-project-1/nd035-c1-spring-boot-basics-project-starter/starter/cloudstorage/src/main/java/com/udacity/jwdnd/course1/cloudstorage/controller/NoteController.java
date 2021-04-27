package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
@RequestMapping("/notes")
public class NoteController {
    //private final NoteService noteService;
    //private final UserService userService;
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService,
                          NoteService noteService) {

        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note,
                             Authentication authentication,
                             Model model,
                             @RequestParam(value = "NoteActionOption", required = false) Integer noteAction) {

        note.setUserId(this.userService.getUserId(authentication.getName()));

        if (noteAction == null) {
            System.out.println("IN NOTE CREATE NOTE");
            try {
                int rowsCreated = noteService.createNote(note);
                model.addAttribute("successMessage", "Your Note has now been created. ");
                return "result";
            } catch (SQLException exception) {
                System.out.println("ERROR when creating NOTE in database for user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
            }
        }

        if (noteAction != null) {           //@DeleteMapping <-- Can't get to work above, so had to pass in parameter etc
            if (noteAction == 1)  //1 = delete note, 2 = edit note
            {
                System.out.println("IN NOTE DELETE MODE");
                try {
                    int rowsDeleted = noteService.deleteNote(note.getNoteId());
                    model.addAttribute("successMessage", "Note " + note.getNoteId() + " was deleted. ");
                    return "result";
                } catch (SQLException exception) {
                    System.out.println("ERROR when deleting NOTE in database for note ID = " + note.getNoteId() + " and user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
                }

            }

            if (noteAction == 2)  //1 = delete note, 2 = edit note      //@PutMapping <-- Can't get to work above,so had to pass in a parm
            {
                System.out.println("IN NOTE UPDATE MODE = " + noteAction);
                try {
                    int rowsUpdated = noteService.updateNote(note);
                    model.addAttribute("successMessage", "Note " + note.getNoteId() + " was updated. ");
                    return "result";
                } catch (SQLException exception) {
                    System.out.println("ERROR when updating NOTE in database for user ID = " + this.userService.getUserId(authentication.getName() + " for error message = " + exception.getMessage()));
                }

            }
        }

        return "redirect:/home";
    }
}