package com.mycompany.mysite.service;

import com.mycompany.mysite.model.Note;
import com.mycompany.mysite.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Long create(Note note) {
       return noteRepository.save(note).getId();
    }

    public Note findById(long id) {
        return noteRepository.findById(id).get();
    }
    public List<Note> findByAuthorId(Long id) {
        return noteRepository.findByAuthor_Id(id);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public String deleteById(long id) {
        noteRepository.deleteById(id);
        return "Note deleted";
    }
}
