/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author NGEREZA
 */
public class Oauth {
    
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty autorization = new SimpleStringProperty();
    private StringProperty token = new SimpleStringProperty();
    private StringProperty senderAddress = new SimpleStringProperty();
    private StringProperty senderName = new SimpleStringProperty();
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    
    public Oauth(){}
    public Oauth(int id,String auth, String token,String address,String name,LocalDate date){
        this.id.set(id);
        this.autorization.set(auth);
        this.token.set(token);
        this.senderAddress.set(address);
        this.senderName.set(name);
        this.date.set(date);
    }
    
    
    
    
    public void setId(int id){
        this.id.set(id);
    }
    public int getId(){
        return id.get();
    }
    
    
    
    
    public void setAuthorization(String auth){
        this.autorization.set(auth);
    }
    public String getAuthorization(){
        return autorization.get();
    }
    
    
    
    public void setToken(String token){
        this.token.set(token);
    }
    public String getToken(){
        return token.get();
    }
    
    
    
    public void setSenderAddress(String address){
        this.senderAddress.set(address);
    }
    public String getSenderAddress(){
        return senderAddress.get();
    }
    
    
    
    public void setSenderName(String name){
        this.senderName.set(name);
    }
    public String getSenderName(){
        return senderName.get();
    }
    
    
    
    
    public void setDate(LocalDate date){
        this.date.set(date);
    }
    public LocalDate getDate(){
        return date.get();
    }
}
