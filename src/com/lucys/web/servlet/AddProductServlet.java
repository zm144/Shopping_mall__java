package com.lucys.web.servlet;

import com.lucys.constant.Constant;
import com.lucys.domain.Category;
import com.lucys.domain.Product;
import com.lucys.service.ProductService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.UUIDUtils;
import com.lucys.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID= 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String,Object> map = new HashMap<>();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload uplaod =  new ServletFileUpload(factory);
            List<FileItem> list = uplaod.parseRequest(req);//req? or request
            for (FileItem fi : list){
                String key = fi.getFieldName();
                if (fi.isFormField()) {
                    map.put(key,fi.getString("utf-8"));
                }else {
                    String name = fi.getName();
                    String realName = UploadUtils.getRealName(name);
                    String uuidName = UploadUtils.getUUIDName(realName);
                    String dir = UploadUtils.getDir();
                    InputStream is = fi.getInputStream();
                    String productPath = getServletContext().getRealPath("/products");
                    File dirFile = new File(productPath,dir);
                    if (!dirFile.exists()){
                        dirFile.mkdirs();
                    }
                    FileOutputStream os = new FileOutputStream(new File(dirFile,uuidName));
                    IOUtils.copy(is,os);
                    os.close();
                    is.close();
                    fi.delete();
                    map.put(key,"products"+dir+"/"+uuidName);
                }
            }
            Product p = new Product();
            map.put("pid", UUIDUtils.getId());
            map.put("pdate", new Date());
            map.put("pflag", Constant.PRODUCT_IS_UP);
            BeanUtils.populate(p,map);
            Category c = new Category();
            c.setCid((String) map.get("cid"));
            p.setCategory(c);
            ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
            ps.save(p);
            resp.sendRedirect(req.getContextPath()+"/adminProduct?method=findAll");
        } catch (Exception e) {
            throw new RuntimeException("保存商品失败");
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
