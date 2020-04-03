/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Boite;
import beans.Oauth;
import donnees.DAuth;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vues.Main;

/**
 *
 * @author NGEREZA
 */
public class CAuth extends AnchorPane{
    
    @FXML
    private TextField tauth,tname,tnum;
    @FXML
    private Hyperlink lien;
    
    //les donn�es
    private DAuth dauth = new DAuth();
    private Oauth oauth = new Oauth();
    
    
    
    
    public CAuth(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/oauth.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
            actualiser();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    @FXML
    private void valider(ActionEvent ev){
        //verification de champs
        if(Boite.verfier(tauth,tname,tnum)){
           //initialisation des donn�es
            oauth = new Oauth();
            oauth.setAuthorization(tauth.getText());
            oauth.setSenderName(tname.getText());
            oauth.setSenderAddress(tnum.getText());
            
            oauth.setDate(LocalDate.now());
            //validation de l'action
            if(Boite.showConfirmation("Valider la modification?", "validation")){
                
               try{
                   //si l'auth existe, on la remplace
                   if((dauth.get(oauth))  != null){
            
                       if(dauth.replace(oauth)){
                           Boite.showInformation("Modification effectuée", "Validation", Alert.AlertType.INFORMATION);
                           actualiser();
                       }
                       
                    }else{//sinon on l'ajoute
                       
                       if(dauth.create(oauth)){
                           Boite.showInformation("Modification effectuée", "Validation", Alert.AlertType.INFORMATION);
                           actualiser();
                       }
                       
                    }
                   
               }catch(Exception ex){
                   Boite.showException(ex, "Validation");
                   ex.printStackTrace();
               }
                
            }
            
        }
        
    }
    
    
    private void actualiser() throws Exception{
        
        if((oauth = dauth.get(oauth))  != null){
            tauth.setText(oauth.getAuthorization());
            tname.setText(oauth.getSenderName());
            tnum.setText(oauth.getSenderAddress());
            lien.setVisible(false);
        }else{
            Boite.vider(tauth,tname,tnum);
            lien.setVisible(true);
        }
        
    }
    
    
    @FXML
    private void orange(ActionEvent ev){
        Main.openurl("https://developer.orange.com/apis/sms/");
    }
    
}
