package com.mingren.controller;

import com.mingren.service.UserService;
import com.mingren.util.DirectoryFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ajun
 * @http://blog.csdn.net/ajun_studio
 **/
public class DelUser extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    //删除用户
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        UserService.delete(Integer.valueOf(id));

        //生成html的位置
        String dirPath = request.getSession().getServletContext().getRealPath("/templates/html");
        //文件名字
        String indexFileName = "index.html";

        //删除原来的文件
        delOldHtml(dirPath,indexFileName);

        //防止浏览器缓存，用于重新生成新的html
        UUID uuid = UUID.randomUUID();
        Writer out = new OutputStreamWriter(new FileOutputStream(dirPath+"/"+uuid+indexFileName),"UTF-8");
        ProcessClient.processBody(out);
        response.sendRedirect("templates/html/"+uuid+"index.html");
    }

    /**
     * 删除原来的html文件
     * @param htmlDir
     * @param htmlName
     */
    private void delOldHtml(String htmlDir,String htmlName){
        File path = new File(htmlDir);
        String[] indexfileList = path.list(new DirectoryFilter(htmlName));
        if(indexfileList.length>=0){
            for(String f:indexfileList){
                File delf = new File(htmlDir+"/"+f);
                delf.delete();
            }
        }
    }

}
