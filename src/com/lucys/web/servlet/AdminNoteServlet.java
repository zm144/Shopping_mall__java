package com.lucys.web.servlet;

import com.lucys.domain.Note;
import com.lucys.service.NoteService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.UUIDUtils;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminNote")
public class AdminNoteServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    //显示公告信息

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            NoteService ns= (NoteService) BeanFactory.getBean("NoteService");
            List<Note> list = ns.findList();
            request.setAttribute("list", list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/admin/note/list.jsp";

    }

    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/admin/note/add.jsp";
    }

    public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Note n = new Note();
            n.setNid(UUIDUtils.getId());
            n.setNname(request.getParameter("nname"));
            n.setNtext(request.getParameter("ntext"));
            NoteService ns = (NoteService) BeanFactory.getBean("NoteService");
            ns.save(n);
            response.sendRedirect(request.getContextPath() + "/adminNote?method=findAll");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return null;
    }

}