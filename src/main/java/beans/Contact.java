/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author NGEREZA
 */
public class Contact {
    
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty numero = new SimpleStringProperty();
    private StringProperty nom = new SimpleStringProperty();
    
    
    public Contact(){
          
    }
    
    
    public Contact(int id,String numero,String nom){
        
        this.id.set(id);
        this.numero.set(numero);
        this.nom.set(nom);
    }
    
    
    
    public void setId(int id){
        this.id.set(id);
    }
    public int getId(){
        return id.get();
    }
    
    
    public void setNumero(String numero){
        this.numero.set(numero);
    }
    public String getNumero(){
        return numero.get();
    }
    
    
    
    public void setNom(String nom){
        this.nom.set(nom);
    }
    public StringProperty getNom(){
        return nom;
    }

    @Override
    public String toString() {
        return this.nom.get().charAt(0)+" "+this.numero.get()+" "+this.nom.get();
    }
    
    
    
}
