/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import beans.Message;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author NGEREZA
 */
public class DMessage extends DAO<Message>{

    
    //création du message
    @Override
    public boolean create(Message b) throws Exception {
        sql = "INSERT INTO message (id_m,texte_m,date_m,statut,numero) VALUES (?,?,?,?,?)";
        
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setInt(1, b.getId());
        pre.setString(2, b.getTexte());
        pre.setDate(3, Date.valueOf(b.getDate()));
        pre.setInt(4, b.getStatut());
        pre.setString(5, b.getNumero());
        
        return pre.executeUpdate() > 0;
    }

    
    //mdification du message
    @Override
    public boolean replace(Message b) throws Exception {
        sql = "UPDATE message "
                + "SET statut=? "
                + "WHERE id_m=?";
        
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setInt(1, b.getStatut());
        pre.setInt(2, b.getId());
        
        return pre.executeUpdate() > 0;
    }

    
    @Override
    public boolean delete(Message b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Message> getList() throws Exception {
        data = FXCollections.observableArrayList();
        
        sql = "SELECT * FROM message WHERE statut != 201";
        pre = XConnection.connecter().prepareStatement(sql);
        res = pre.executeQuery();
        while(res.next()){
            data.add(new Message(res.getInt("id_m"),
                    res.getString("texte_m"), 
                    res.getDate("date_m").toLocalDate(),
                    res.getInt("statut"), res.getString("numero")));
        }
        return data;
    }

    @Override
    public ObservableList<Message> getList(Message b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message get(Message b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
