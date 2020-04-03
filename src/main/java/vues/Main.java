/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import beans.Boite;
import controlers.Menu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author NGEREZA
 */
public class Main extends Application{

   
    
    private static HostServices hser;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) throws Exception {
        
        Menu root = new Menu();
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(getClass().getResource("/styles/styles.css").toExternalForm());
        
        s.setScene(scene);
        
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
        
        hser = getHostServices();
    }
    
    
    
    public static void openurl(String url){
        hser.showDocument(url);
    }
}
