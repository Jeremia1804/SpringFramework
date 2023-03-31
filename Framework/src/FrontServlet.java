package etu1804.framework.servlet;
import java.io.*;
import java.util.HashMap;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import fonction.*;
import etu1804.framework.*;
import java.io.*;
import java.nio.file.*; 
import java.util.*;
import fonction.Fonction;
import annote.Url;
import us.TestAnnoter;
public class FrontServlet extends HttpServlet { 
    HashMap<String,Mapping> mappingUrls;
    
    

    public String[] process(String path){
        String[] valiny = Utilitaire.getParameter(path);
        return valiny;
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
    { 
        try{
            PrintWriter out = res.getWriter();
        String path = req.getServletPath();
        String[] va = process(path);
        for (String key : mappingUrls.keySet()) {
            if(key.equals(va[0])){
            Mapping a = mappingUrls.get(key);
            out.print(TestAnnoter.take(a.getClassName(),a.getMethod())+"  :: ");
            out.print(a.getClassName()+" : "+a.getMethod()+" : "+key+"  :  url = "+va[0]+";\n");
            }
        }
        }catch(Exception e){

        }
        
    }

    public void init(){
        try{
        Collection<File> all = new ArrayList<File>();
        mappingUrls = new HashMap<String,Mapping>();
        
        String path = this.getInitParameter("path");
        Fonction.findFilesRecursively(new File(path), all, ".class");
        String[] o = Fonction.convert(all.toArray(),path);
        for (int i = 0; i < o.length; i++) {
            Class e = Class.forName(o[i]);
            if(e.isInterface()==false){
            TestAnnoter.run(e.newInstance(),Url.class,mappingUrls);
            }
        }
        }catch(Exception e){}
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException { 
       processRequest(req, res);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException { 
        processRequest(req, res);
    }
}