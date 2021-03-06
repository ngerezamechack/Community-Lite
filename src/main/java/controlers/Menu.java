/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import vues.Main;

/**
 *
 * @author NGEREZA
 */
public class Menu extends BorderPane{

    
    private CContact contact = new CContact();
    
    private CAuth auth = new CAuth();
    
    
    public Menu(){
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vues/main.fxml"));
        load.setRoot(this);
        load.setController(this);
        
        try{
            load.load();
            
            BackgroundImage bi = new BackgroundImage(new Image(getClass().getResourceAsStream("/images/img12.png")), 
                BackgroundRepeat.ROUND, 
                BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                this.setBackground(new Background(bi));
                
                this.setCenter(contact);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
    @FXML
    private void autorisation(ActionEvent ev){
        this.setCenter(auth);
    }
    
    
    @FXML
    private void contact(ActionEvent ev){
        this.setCenter(contact);
    }
    
    
    @FXML
    private void facebook(Event ev){
        Main.openurl("https://web.facebook.com/ngerezaelectronicarts");
    }
    @FXML
    private void youtube(Event ev){
        Main.openurl("https://www.youtube.com/channel/UCqYV6a0fFRDlL-glNqB_sjQ?view_as=subscriber");
    }
}
