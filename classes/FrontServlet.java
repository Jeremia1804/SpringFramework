package etu001804.framework.servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import fonction.*;
public class FrontServlet extends HttpServlet {

    public String process(String url,String path){
        String valiny = Utilitaire.getParameter(url, path);
        return valiny;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException { 
        PrintWriter out = res.getWriter();
        StringBuffer url = req.getRequestURL();
        String path = req.getServletPath();
        String va = process(new String(url),path);
        out.print("Ity no andrana voalohany : "+va);
    }
}