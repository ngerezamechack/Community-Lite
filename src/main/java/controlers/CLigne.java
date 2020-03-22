/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Contact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author NGEREZA
 */
public class CLigne extends AnchorPane{

    @FXML
    private Label lnum,lnom,lav;
    
    public CLigne(Contact c,double w) {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vues/ligne.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        this.setWidth(w-10);
        try{
            load.load();
            setContact(c);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    private void setContact(Contact c){
        lnum.setText(c.getNumero());
        lnom.setText(c.getNom().get());
        lav.setText(String.valueOf(c.getNom().get().charAt(0)));
    }
}
