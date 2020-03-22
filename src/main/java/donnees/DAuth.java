/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import beans.Boite;
import beans.Oauth;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author NGEREZA
 */
public class DAuth extends DAO<Oauth> {

    
    //les variables http
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    
    //les variables json
    private JsonReader reader;
    private JsonObject json;
    
    
    @Override
    public boolean create(Oauth b) throws Exception {
        //preparation de la requette
        sql = "INSERT INTO oauth (id_o,autorisation,token,senderaddress,sendername,date_token) VALUES (?,?,?,?,?,?)";
        
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setInt(1, 1);
        pre.setString(2, b.getAuthorization());
        pre.setString(3, b.getToken());
        pre.setString(4, b.getSenderAddress());
        pre.setString(5, b.getSenderName());
        pre.setDate(6, Date.valueOf(b.getDate()));
        
        return pre.executeUpdate() > 0;
    }
    
    

    @Override
    public boolean replace(Oauth b) throws Exception {
        //preparation de la requette
        sql = "UPDATE oauth "
                + "SET autorisation=?,token=?,senderaddress=?,sendername=?,date_token=? "
                + "WHERE id_o = 1";
        
        pre = XConnection.connecter().prepareStatement(sql);
        
        pre.setString(1, b.getAuthorization());
        pre.setString(2, b.getToken());
        pre.setString(3, b.getSenderAddress());
        pre.setString(4, b.getSenderName());
        pre.setDate(5, Date.valueOf(b.getDate()));
        
        return pre.executeUpdate() > 0;
    }

    
    
    @Override
    public Oauth get(Oauth b) throws Exception {
        sql = "SELECT * FROM oauth WHERE id_o=1";
        res = XConnection.connecter().createStatement().executeQuery(sql);
        if(res.next()){
            return new Oauth(res.getInt("id_o"),
                    res.getString("autorisation")
                    , res.getString("token"), res.getString("senderaddress"), 
                    res.getString("sendername"), res.getDate("date_token").toLocalDate());
        }
        return null;
    }

    
    
    @Override
    public boolean delete(Oauth b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Oauth> getList() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Oauth> getList(Oauth b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    //recuperer la clé d'acces
    private boolean getToken(Oauth o) throws Exception{
        
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        
        String url = "https://api.orange.com/oauth/v2/token";
        //pr�paration de la requette
        request = HttpRequest.newBuilder().uri(new URI(url))
                .header("content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", o.getAuthorization())
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
        //execution de la requette
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.statusCode());
        
        //traitement de la reponse
        if(response.statusCode() == 200){
            
            reader = Json.createReader(new StringReader(response.body()));
            json = reader.readObject();
            
            String token = json.getString("token_type")+" "+json.getString("access_token");
            o.setToken(token);
            o.setDate(LocalDate.now());
            this.replace(o);
            
            return true;
        }else{
            
            System.out.println(response.body());
            return false;
        }
        
        
    }
    
    
    
    
    
    //verification des autorisation
    public Oauth autorisation() throws Exception{
        
        Oauth oauth = new Oauth();
        
        if((oauth = this.get(oauth)) == null){
            throw new Exception("Autorisation indisponible");
        }else{
            
            if(oauth.getToken() == null || oauth.getToken().isBlank()){
                if(!this.getToken(oauth)){
                    throw new Exception("Recup�ration de la cl� �chou�e!");
                }
            }else{
                
                if(Boite.intervale(oauth.getDate()) >= 90){
                    this.getToken(oauth);
                }
                
            }
            
        }
        return oauth;
        
    }
    
}
