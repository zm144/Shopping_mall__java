package com.lucys.dao.impl;

import com.lucys.dao.NoteDao;
import com.lucys.domain.Note;
import com.lucys.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.Collections;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    @Override
    public List<Note> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from note";
        return qr.query(sql, new BeanListHandler<>(Note.class));
    }

    @Override
    public void save(Note n) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into note values(?,?,?)";
        qr.update(sql,n.getNid(),n.getNname(),n.getNtext());

    }
}
