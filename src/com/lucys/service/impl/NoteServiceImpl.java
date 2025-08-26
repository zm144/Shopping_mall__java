package com.lucys.service.impl;

import com.lucys.dao.NoteDao;
import com.lucys.domain.Note;
import com.lucys.service.NoteService;
import com.lucys.utils.BeanFactory;

import java.util.Collections;
import java.util.List;

public class NoteServiceImpl  implements NoteService {
    @Override
    public List<Note> findList() throws Exception {
        NoteDao nd= (NoteDao) BeanFactory.getBean("NoteDao");
        return nd.findAll();
    }

    @Override
    public void save(Note n) throws Exception {
        NoteDao nd= (NoteDao) BeanFactory.getBean("NoteDao");
        nd.save(n);
    }
}
