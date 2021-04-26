package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }

    public List<Note> getNotes(Integer userId){
        return noteMapper.getNotes(userId);
    }

    public int createNote(Note note) throws SQLException {
        return noteMapper.insert(new Note(null, note.getTitle(), note.getDescription(), note.getUserId()));
    }

    public int updateNote(Note note) throws SQLException {
        return noteMapper.updateNote(new Note(note.getNoteId(), note.getTitle(), note.getDescription(), note.getUserId()));
    }

    public int deleteNote(int noteId) throws SQLException {
        return noteMapper.deleteNote(noteId);
    }
}
