package etu1804;

import java.io.File;

public class FileUpload {
    String name;
    String path;
    byte[] fichier;

    public FileUpload(String name, String path, byte[] fichier) {
         setName(name); 
         setPath(path);
         setFichier(fichier);
    }

    public FileUpload(){

    }
  
    public FileUpload(String path){
        setPath(path);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public byte[] getFichier() {
        return fichier;
    }
    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    
}
