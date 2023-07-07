package etu1804;

import java.util.HashMap;

public class ModelView {
    String chemin;
    HashMap<String,Object> data;
    boolean isJson = false;

    public ModelView(String c){
        chemin = c;
        data = new HashMap<String,Object>();
    }
    
    public ModelView(){
        data = new HashMap<String,Object>();
    }

    public String getChemin(){
        return chemin;
    }

    public void setChemin(String str){
        chemin = str;
    }

    public HashMap<String, Object> getData() {
        return data;
    }  

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void addItem(String key, Object value){
        
        this.data.put(key, value);
    }

    public void setIsJson(boolean g){
        this.isJson = g;
    }

    public boolean getIsJson(){
        return this.isJson;
    }
    
}
