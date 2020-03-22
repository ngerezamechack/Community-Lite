/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import beans.Boite;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author NGEREZA
 */
public class Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) throws Exception {
        
        BorderPane pan = (BorderPane) FXMLLoader.load(getClass().getResource("main.fxml"));
        s.setScene(new Scene(pan));
        
        s.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if(Boite.showConfirmation("Quitter?", "Attention..")){
                    System.exit(0);
                }else{
                    t.consume();
                }
            }
        });
        s.setMaximized(true);
        s.getIcons().add(new Image(getClass().getResourceAsStream("/images/Community.png")));
        s.show();
    }
    
}
