package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping()
    public String createOrUpdateNote(Authentication authentication, Note note) {
        User appUser = (User) authentication.getPrincipal();
        if (note.getNoteid() > 0) {
            noteService.updateNote(note);
        } else {
            noteService.addNote(note, appUser.getUserid());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteNote(
            @RequestParam("id") int noteid
    ) {
        if (noteid > 0) {
            noteService.deleteNote(noteid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
