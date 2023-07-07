package koursa;
import annote.Url;
import etu1804.ModelView;
import annote.Scope;

@Scope("singleton")
public class Voiture {
    String nom;
    int num;

    public Voiture(){
    }

    @Url("voir")
    public ModelView findById(){
        return new ModelView("test.jsp");
    }

    @Url("jeteste")
    public ModelView findAll(){
        ModelView model = new ModelView("afficher.jsp");
        model.setIsJson(true);
        model.addItem("nom",this.nom);
        model.addItem("num",this.num);
        return model;
    }  

    @Url("good")
    public String save(){
        return "save";
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNum(String num) {
        this.num = Integer.valueOf(num);
    }
}
