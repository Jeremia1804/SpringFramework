package koursa;
import annote.Url;
public class Course {
    @Url("coucou")
    public String findById(){
        return "mandeh baina aa";
    }

    public int findAll(){
        return 3513;
    }

    @Url("salut")
    public int save(){
        return 153;
    }
}
