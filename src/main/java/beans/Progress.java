/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author NGEREZA
 */
public class Progress extends Stage{
    
    private ProgressIndicator pg = new ProgressIndicator();
    
    public Progress(){
        
        pg.setPrefSize(200, 200);
        
        this.initStyle(StageStyle.TRANSPARENT);
        this.setScene(new Scene(pg));
        this.setAlwaysOnTop(true);
        
    }
    
    
    public ProgressIndicator getProgress(){
        return pg;
    }
    
}
