package Modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Melange<E> implements Serializable {
    private ArrayList<E> values;

    public Melange(ArrayList<E> list){
        values=list;
    }

    public int cut(){
        int i=0,x=0;
        while(i<values.size()){
            if(Math.random()<(1.0/2.0)){x++;}
            i++;
        }
        return x;
    }

    public ArrayList<E> split(){
        ArrayList<E> nouveau=new ArrayList();
        int c=cut();
        for(int i=0;i<c;i++){
            nouveau.add(values.remove(0));
        }
        return nouveau;
    }

    public void riffleWith(ArrayList<E> s1){
        ArrayList<E> s2=values;
        values=new ArrayList<>();
        int taille=s2.size()+s1.size();
        for(int i=0;i<taille;i++) {
            if (Math.random() < (double)s2.size() / (double)(s2.size() + s1.size()) && !s2.isEmpty()) {
                values.add(s2.remove(0));
            } else { values.add(s1.remove(0)); }
        }
    }

    public void riffleShuffle(int m){
        int i=0;
        ArrayList<E> s2;
        while(i<m){
            s2=this.split();
            riffleWith(s2);
            i++;
        }
    }

    public ArrayList<E> getValues(){
        return values;
    }

    public void setValues(ArrayList<E> values) {
        this.values = values;
    }
}
