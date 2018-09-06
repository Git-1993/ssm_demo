package com.mingren.controller;

import com.mingren.util.DirectoryFilter;
import com.mingren.util.FreeMarkertUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ajun
 * @http://blog.csdn.net/ajun_studio
 **/
public class Index extends HttpServlet {

    private static final long serialVersionUID = 7474850489594438527L;

    public Index() {
        super();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //html生成之后存放的路径
        String dirPath = request.getSession().getServletContext().getRealPath("/templates/html");
        File path = new File(dirPath);
        //生成的文件的名字
        String indexFileName = "index.html";
        /**
         * 判断是否已经存在该html文件，存在了就直接访问html ，不存在生成html文件
         */
        String[] indexfileList = path.list(new DirectoryFilter(indexFileName));
        if(indexfileList.length<=0){
            Writer out = new OutputStreamWriter(new FileOutputStream(dirPath+"/"+indexFileName),"UTF-8");
            //生成html文件
            ProcessClient.processBody(out);
            request.getRequestDispatcher("/templates/html/index.html").forward(request, response);
        }else{
            request.getRequestDispatcher("/templates/html/"+indexfileList[0]).forward(request, response);
        }


    }



    /**
     * 初始化模板配置，供以后获得模板，在init里加载也主要是为保证Configuration实例唯一
     */
    public void init(ServletConfig config) throws ServletException {
        String templateDir = config.getInitParameter("templateDir");
        FreeMarkertUtil.initConfig(config.getServletContext(), templateDir);
    }


}
