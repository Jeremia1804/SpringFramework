package us;
import java.lang.reflect.*;
import java.lang.*;
import java.lang.annotation.Annotation;
import java.io.*;
import java.nio.file.*; 
import java.util.ArrayList;
import java.util.*;

import etu1804.FileUpload;
import etu1804.framework.*;
import annote.*;

public class TestAnnoter{
    public TestAnnoter(){}

    public static void run(Class cl , Class a, HashMap<String,Mapping> hash,Class b, HashMap<Class,Object> singletons) throws Exception{
        Method[] methods = cl.getMethods();
        String j = "";
        if(cl.isAnnotationPresent(b)){
            Method k = cl.getAnnotations()[0].annotationType().getDeclaredMethod("value",null);
            j = k.invoke(cl.getAnnotations()[0]).toString();
            if(j.equalsIgnoreCase("singleton")){
                singletons.put(cl,cl.newInstance());
            }
        }
        for(Method d : methods){
            if(d.isAnnotationPresent(a)){
                Method m = d.getAnnotations()[0].annotationType().getDeclaredMethod("value",null);
                j = m.invoke(d.getAnnotations()[0]).toString();
                System.out.println(j);
                hash.put(j, new Mapping(cl.getName(),d.getName())); 
            }
        }
    }

    public static Object take(String classe, String method) throws Exception{
        Class c = Class.forName(classe);
        Object o = c.newInstance();
        Method methods = o.getClass().getDeclaredMethod(method,null);
        Object valiny = methods.invoke(o);
        return valiny;
    }

}  