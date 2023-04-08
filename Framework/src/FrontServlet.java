package etu1804.framework.servlet;
import java.io.*;
import java.util.HashMap;

import javax.swing.text.Utilities;

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
import etu1804.*;
import annote.Scope;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/upload")
@MultipartConfig
public class FrontServlet extends HttpServlet { 
    HashMap<String,Mapping> mappingUrls;
    HashMap<Class,Object> singletons;
    
    

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
            
            Class cl = Class.forName(a.getClassName());
            Object o = Utilitaire.isSingleton(cl, singletons);
            if(o==null){
                o = cl.newInstance();
            }else{
                out.print("tsy singleteon");
            }

            int nombre = Utilitaire.verificationFormulaire(req, o,out);
            
            // out.print(TestAnnoter.take(a.getClassName(),a.getMethod())+"  :: ito aloha: "+req.getRequestURI()+" >> ");
            // out.print("jeremia");
            // out.print(a.getClassName()+" : "+a.getMethod()+" : "+key+"  :  url = "+va[0]+": TypeReturn  = "+Utilitaire.isModelViewReturnType(a.getClassName(),a.getMethod())+";\n");
  
            out.print("    :     ");
            String[] parm = Utilitaire.hasParameterInUrl(va,req.getParameterMap().size());
            Object result = Utilitaire.AnalyserLaFonction(o, a.getMethod(), parm,out);
            if(result!=null && result.getClass() == ModelView.class){
                ModelView ur = (ModelView) result;
                if(nombre > 0){
                    req.setAttribute(cl.getSimpleName(),o);
                }
                for (String cle: ur.getData().keySet()){
                    req.setAttribute(cle,ur.getData().get(cle));
                }
                RequestDispatcher di = req.getServletContext().getRequestDispatcher("/View/"+ur.getChemin());
                di.forward(req,res);
            }else if(result!= null){
                out.print(result);
            }
            break;
            }
        }
        }catch(Exception e){
            try{
                PrintWriter out = res.getWriter();
                out.print(e.toString());
            }catch(Exception es){
                
            }
        }
        
    }

    public void init(){
        try{
        Collection<File> all = new ArrayList<File>();
        mappingUrls = new HashMap<String,Mapping>();
        singletons = new HashMap<Class,Object>();
        
        String path = this.getInitParameter("path");
        Fonction.findFilesRecursively(new File(path), all, ".class");
        String[] o = Fonction.convert(all.toArray(),path);
        for (int i = 0; i < o.length; i++) {
            Class e = Class.forName(o[i]);
            if(e.isInterface()==false){
            TestAnnoter.run(e,Url.class,mappingUrls,Scope.class,singletons);
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