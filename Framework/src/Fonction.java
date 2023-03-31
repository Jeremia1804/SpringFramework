package fonction;
import java.io.*;
import java.nio.file.*; 
import java.util.*;
import java.nio.file.attribute.BasicFileAttributes;
public class Fonction{
    public static void findFilesRecursively(File file, Collection<File> all, final String extension) {
        final File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if(child.isFile() && child.getName().endsWith(extension)){
                    all.add(child);
                }
                else if(child.isDirectory()){
                    findFilesRecursively(child, all, extension);
                }
                
            }
        }
    }

    public static String[] convert(Object[] o,String path)throws Exception{
        String[] rep = new String[o.length];
        File fichier = null;
        int i=0;
        for(Object objet : o){
            fichier =(File) objet;
            rep[i] =  supprimer(path, fichier.getPath(), ".class");
            i++;
        }
        return rep;
    }

    public static String supprimer(String path,String s,String extension){
        StringBuffer str = new StringBuffer(s);
        str.delete(0, path.length()+1);
        str.delete(str.length()-extension.length(), str.length());
        s = str.toString();
        String[] a = s.split("\\\\"); 
        s = a[0];
        for (int i = 1; i < a.length; i++) {
            s = s + "." + a[i];
        }
        return s;
    }

    
    public static void ecrireDansTxt(Collection<File> all){
    try {
        File outputFile = new File("TextOutput.txt");
        FileOutputStream fos = new FileOutputStream(outputFile);
    
        for (File file : all) {
            FileInputStream fis = new FileInputStream(file);
            int a;
            while ((a = fis.read()) != -1) {
                fos.write(a);
            }
            fis.close();
        }
        fos.close();
    } catch (FileNotFoundException e) {
    System.err.println("FileStreamsTest: " + e);
    } catch (IOException e) {
    System.err.println("FileStreamsTest: " + e);
    }
}
}
    