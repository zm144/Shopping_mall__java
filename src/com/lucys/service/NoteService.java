package com.lucys.service;

import com.lucys.domain.Note;

import java.util.List;

public interface NoteService {
    void save(Note n) throws Exception;

    List<Note> findList() throws Exception;
}
