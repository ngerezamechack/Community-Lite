/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Contact;
import beans.Message;
import beans.Oauth;
import donnees.DAuth;
import donnees.DMessage;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author NGEREZA
 */
public class CMessage {
    
    private ObservableList<Contact> contacts;
    private ObservableList<Message> messages;
    
    
    //p
    private DMessage dm = new DMessage();
    private Message m = new Message();
    
    //les variables http
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse response;
    
    //les clés d'autorisation
    private String url = "";
    
    
    public CMessage(){
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }
    
    //préparation des messages
    private void creerMessage(ObservableList<Contact> cont,String message) throws Exception{
        
        for(Contact c: cont){
            m = new Message();
            m.setId((int) (System.currentTimeMillis()/100));
            m.setTexte(message);
            m.setDate(LocalDate.now());
            m.setSatut(0);
            m.setNumero(c.getNumero());
            dm.create(m);
        }
    }
    
    
    
    //authorisation
    private DAuth dauth = new DAuth();
    private beans.Oauth oauth = new Oauth();
    
    
    //envoyer les message
    public void sendMessage(ObservableList<Contact> cont,String message) throws Exception{
        
        
        if((oauth = dauth.autorisation()) != null){
            creerMessage(cont, message);
            //préparation de l'url
            url = "https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B"+oauth.getSenderAddress()+"/requests";
            messages = dm.getList();
            
            for(Message m : messages){
                
                m.setSener(oauth.getSenderAddress());
                m.setSenderName(oauth.getSenderName());
                System.out.println(m.getMessage()+"\n" +oauth.getToken());
                request = HttpRequest.newBuilder().uri(new URI(url))
                        .header("content-type", "application/json")
                        .header("Authorization", oauth.getToken())
                        .POST(HttpRequest.BodyPublishers.ofString(m.getMessage().toString()))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                m.setSatut(response.statusCode());
                System.out.println(response.statusCode()+"\n "+response.body());
                dm.replace(m);
                
            }
            
        }
        
    }
}
