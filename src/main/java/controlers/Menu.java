/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author NGEREZA
 */
public class Menu implements Initializable{

    
    private AnchorPane contact;
    @FXML
    private BorderPane pan;
    
    private CAuth auth = new CAuth();
    
    
    
    @FXML
    private void autorisation(ActionEvent ev){
        pan.setCenter(auth);
    }
    
    
    @FXML
    private void contact(ActionEvent ev){
        pan.setCenter(contact);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            contact = (AnchorPane) FXMLLoader.load(getClass().getResource("/vues/contact.fxml"));
            pan.setCenter(contact);
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
