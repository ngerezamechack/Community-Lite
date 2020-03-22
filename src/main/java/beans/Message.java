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
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author NGEREZA
 */
public class Message {
    
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty texte = new SimpleStringProperty();
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
    private IntegerProperty statut = new SimpleIntegerProperty();
    private StringProperty numero = new SimpleStringProperty();
    
    
    public Message(){}
    
   public Message(int id,String texte,LocalDate date,int statut,String numero){
       this.id.set(id);
       this.texte.set(texte);
       this.date.set(date);
       this.statut.set(statut);
       this.numero.set(numero);
   }
    
   
   
   
   public void setId(int id){
       this.id.set(id);
   }
   public int getId(){
       return id.get();
   }
   
   
   public void setTexte(String texte){
       this.texte.set(texte);
   }
   public String getTexte(){
       return texte.get();
   }
   
   
   public void setDate(LocalDate date){
       this.date.set(date);
   }
   public LocalDate getDate(){
       return date.get();
   }
   
   
   public void setSatut(int statut){
       this.statut.set(statut);
   }
   public int getStatut(){
       return statut.get();
   }
   
   
   public void setNumero(String numero){
       this.numero.set(numero);
   }
   public String getNumero(){
       return numero.get();
   }
   
   
   
   
   private StringProperty senderAddress = new SimpleStringProperty();
   private StringProperty senderName = new SimpleStringProperty();
   
   public void setSener(String senderAddress){ this.senderAddress.set(senderAddress);}
   public void setSenderName(String name){this.senderName.set(name);}
   
   public JsonObject getMessage(){
        
        return Json.createObjectBuilder()
                .add("outboundSMSMessageRequest", Json.createObjectBuilder()
                    .add("address", "tel:+"+numero.get())
                    .add("senderAddress", "tel:+"+senderAddress.get())
                    .add("senderName", senderName.get())
                    .add("outboundSMSTextMessage", Json.createObjectBuilder()
                        .add("message", texte.get())))
                .build();
        
    }
}
