package com.lucys.dao;

import com.lucys.domain.Note;

import java.util.List;

public interface NoteDao {
    List<Note> findAll() throws Exception;

    void save(Note n) throws Exception;
}
