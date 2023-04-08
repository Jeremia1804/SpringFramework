package koursa;
import annote.Url;
import annote.Upload;
import etu1804.FileUpload;
import etu1804.ModelView;
public class Course {
    String nom;
    String prenom;
    int num;
    @Upload("D:/L2/C#/")
    FileUpload monfichier;

    public Course(){

    }

    @Url("course-testform")
    public void testFormulaire(){
        ModelView model = new ModelView("view.jsp");
        // return model;
    }

    @Url("tester")
    public int essai(int me,int toi){
        return toi+me;
    } 

    @Url("show")
    public void findById(){
        ModelView model = new ModelView("view.jsp");
        model.addItem("nom",5);
        model.addItem("prenom","Jeremia");
        model.addItem("filsAine","Mahenina");
        // return model;
    }

    @Url("coucou")
    public ModelView findAll(){
        return new ModelView("formulaire.jsp");
    }

    @Url("salut")
    public int save(){
        return 153;
    }

    public void setNom(String n){
        this.nom = n;
    }

    public void setPrenom(String n){
        this.prenom = n;
    }

    public void setNum(String n){
        this.num = Integer.valueOf(n);
    }

    public String getNom(){
        return this.nom;
    }

    public void setMonfichier(FileUpload d){
        this.monfichier = d;
    }
}
