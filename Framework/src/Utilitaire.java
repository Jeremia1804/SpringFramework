package fonction;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import us.TestAnnoter;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.HashMap;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.io.*;
import java.nio.file.*;
import etu1804.FileUpload;
import etu1804.ModelView;
import annote.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utilitaire {
    public static String[] getParameter(String hafa){
        String v= hafa.substring(1, hafa.length());
        String [] tab= v.split("/");
        if(tab[tab.length-1] == "" || tab[tab.length-1].charAt(0) == '?'){
            String[] e = new String[tab.length-1];
            for (int i = 0; i < e.length; i++) {
                e[i] = tab[i];
            }
            return e;
        }
        return tab;

    }

    public static String toJson(Object o){
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(o);
        return json;
    }

    public static void reinitialiser(Object o)throws Exception{
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i=0; i<fields.length; i++){
            fields[i].setAccessible(true);
            if(fields[i].getType() == int.class || fields[i].getType() == double.class || fields[i].getType() == float.class){
                fields[i].set(o, 0);
            }else{
                fields[i].set(o, null);
            }
        }
    }

    public static Object isSingleton(Class cl,HashMap<Class,Object> data)throws Exception{
        for (Class cle: data.keySet()){
            if(cle.getName().equalsIgnoreCase(cl.getName())){
                Object o =  data.get(cle);
                return o;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T castObject(Class<T> type, Object objet,PrintWriter out) {
        if (type.equals(int.class) && objet instanceof String) {
            type =(Class<T>) Integer.class;
            Integer val = Integer.valueOf((String) objet);
            return type.cast(val);
        }else if (type.equals(double.class) && objet instanceof String) {
            type =(Class<T>) Double.class;
            Double val = Double.valueOf((String) objet);
            return type.cast(val);
        }else if (type.equals(Float.class) && objet instanceof String) {
            type =(Class<T>) Float.class;
            Float val = Float.valueOf((String) objet);
            return type.cast(val);
        } else {
            return type.cast(objet);
        }
    }  
    

    public static int verificationFormulaire(HttpServletRequest req,Object objet,PrintWriter out)throws Exception{
        Class cl = objet.getClass();
        Field[] attribut = cl.getDeclaredFields();
        int nb = 0;
        for(int i=0; i<attribut.length; i++){
            String attr = attribut[i].getName().substring(0,1).toUpperCase()+""+attribut[i].getName().substring(1); 
            if(attribut[i].getType().equals(FileUpload.class) == false){
                if(req.getParameter(attribut[i].getName()) != null){
                    nb ++;
                }
            }
        }
        if(nb>0){
            Utilitaire.reinitialiser(objet);
        }
        nb = 0;
        for(int i=0; i<attribut.length; i++){
            String attr = attribut[i].getName().substring(0,1).toUpperCase()+""+attribut[i].getName().substring(1); 
           try{
            if(attribut[i].getType().equals(FileUpload.class) == false){
                if(req.getParameter(attribut[i].getName()) != null){
                    String valeur = String.valueOf(req.getParameter(attribut[i].getName()));
                    out.print(valeur);
                    out.print("set"+attr);
                    cl.getMethod("set"+attr,String.class).invoke(objet,valeur);
                    nb ++;
                    out.print("tonga"+attribut.length+"  ");
                }
            }
            else if(attribut[i].getType().equals(FileUpload.class) == true && req.getParts()!=null){
                    out.print("je t'aime");
                    Field e = attribut[i];
                    String path = "C:/Program Files/Apache Software Foundation/Tomcat 10.0/webapps/Framework/WEB-INF/classes/";
                    if(e.isAnnotationPresent(Upload.class)){
                        Method m = e.getAnnotations()[0].annotationType().getDeclaredMethod("value",null);
                        path = m.invoke(e.getAnnotations()[0]).toString();
                    }
                    Part filePart = req.getPart(attribut[i].getName());
                    String fileName = getFileName(filePart); // Obtenez le nom du fichier
                    
                    // Écrivez le contenu du fichier dans le dossier de destination
                    File files = new File(path, fileName);
                    
                    try (InputStream fileContent = filePart.getInputStream()){
                        Path p = files.toPath();
                        if(files.exists()){
                            files.delete();
                        }
                        Files.copy(fileContent, p);
                    }
                    byte[] fileBytes = Files.readAllBytes(Path.of(path+"/"+fileName));
                    FileUpload file = new FileUpload(fileName,path,fileBytes);
                    cl.getMethod("set"+attr,FileUpload.class).invoke(objet,file);
                    
            }
        }catch(Exception e){
            out.print("interieur:  "+e);
        }
            
        }
        return nb;
    }

    public static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    public static ModelView isModelViewReturnType(String classe, String method) throws Exception{
        Class c = Class.forName(classe);
        Object o = c.newInstance();
        Method m = c.getDeclaredMethod(method,null);
        if(m.getReturnType() == ModelView.class){
            ModelView valiny =(ModelView) m.invoke(o, null);
            return valiny;
        }
        return null;
    }

    public static String isModelViewReturnType1(String classe, String method) throws Exception{
        Class c = Class.forName(classe);
        Object o = c.newInstance();
        Method m = c.getDeclaredMethod(method,null);
        if(m.getReturnType() == ModelView.class){
            ModelView valiny =(ModelView) m.invoke(o, null);
            return valiny.getChemin();
        }
        return null;
    }

    public static String[] hasParameterInUrl(String[] url,int misy){
        int taille = url.length;
        if(taille<= 1){
            return null;
        }
        String[] toReturn = new String[taille-1];
        for (int i=0; i<toReturn.length; i++){
            toReturn[i] = url[i+1];
        }
        return toReturn;
    }

    public static Object AnalyserLaFonction(Object objet, String methode,String [] arguments,PrintWriter out) throws Exception{
        Class cl = objet.getClass();
        Method[] methods = cl.getMethods();
        Method method = null;
        for (int i = 0; i<methods.length; i++){
            if (methode.equalsIgnoreCase(methods[i].getName())){
                method = methods[i];
                break;
            }
        }
        // Obtenez les paramètres de la méthode
        Parameter[] parameters = method.getParameters();
        Object[] valeursDesParametres = new Object[parameters.length];

        int i = 0;
        for (Parameter parameter : parameters) {
            Class<?> parameterType = parameter.getType();
            valeursDesParametres[i] = castObject(parameterType, arguments[i],out);
            i++;
        }
        if(method.getReturnType().equals(Void.TYPE) == true){
            method.invoke(objet, valeursDesParametres);
            return null;
        }
        Object o = method.invoke(objet, valeursDesParametres);
        if(TestAnnoter.verifierJSON(method, RestAPI.class)){
            return Utilitaire.toJson(o);
        }
        return o;
    }

}

