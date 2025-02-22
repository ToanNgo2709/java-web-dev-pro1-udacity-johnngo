package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public List<Note> getNotesByUser(int userid) throws Exception {
        List<Note> notes = noteMapper.findByUserId(userid);
//        if (notes == null) {
//            throw new Exception("No notes found for user");
//        }
        return notes;
    }

    public void addNote(Note note, int userid) {
        noteMapper.insert(note, userid);
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public void deleteNote(int noteid) {
        noteMapper.delete(noteid);
    }
}
