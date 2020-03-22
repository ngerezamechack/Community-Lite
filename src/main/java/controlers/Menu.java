/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author NGEREZA
 */
public class Menu implements Initializable{

    
    private CContact contact = new CContact();
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
        pan.setCenter(contact);
    }
    
}
