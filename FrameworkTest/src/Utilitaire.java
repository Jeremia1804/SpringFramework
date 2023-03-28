package fonction;
public class Utilitaire {
    public static String[] getParameter(String url, String hafa){
        String v= hafa.substring(1, hafa.length());
        return v.split("/"); 
    }
}
