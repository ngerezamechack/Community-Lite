/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import beans.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author NGEREZA
 */
public class DContact extends DAO<Contact>{

    
    
    
    
    @Override
    public boolean create(Contact b) throws Exception {
        
        sql = "INSERT INTO contact(id_c,numero,nom) VALUES (?,?,?)";
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setInt(1, b.getId());
        pre.setString(2, b.getNumero());
        pre.setString(3, b.getNom().get().toUpperCase());
        
        return pre.executeUpdate() > 0;
        
    }

    
    
    
    
    
    @Override
    public boolean replace(Contact b) throws Exception {
        
        sql = "UPDATE contact "
                + "SET nom=? "
                + "WHERE id_c=?";
        pre = XConnection.connecter().prepareStatement(sql);
        
        pre.setString(1, b.getNom().get().toUpperCase());
        pre.setInt(2, b.getId());
        
        return pre.executeUpdate() > 0;
        
        
    }

    
    
    
    
    
    
    
    
    @Override
    public boolean delete(Contact b) throws Exception {
        sql = "DELETE FROM contact WHERE id_c=?";
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setInt(1, b.getId());
        
        return pre.executeUpdate() > 0;
    }

    
    
    
    
    
    @Override
    public ObservableList<Contact> getList() throws Exception {
        sql = "SELECT * FROM contact ORDER BY nom";
        data = FXCollections.observableArrayList();
        
        pre = XConnection.connecter().prepareStatement(sql);
        res = pre.executeQuery();
        
        while(res.next()){
            data.add(new Contact(res.getInt("id_c"), res.getString("numero"), res.getString("nom")));
        }
        return data;
    }

    
    
    
    
    
    
    
    @Override
    public ObservableList<Contact> getList(Contact b) throws Exception {
        sql = "SELECT * FROM contact "
                + "WHERE nom Like ? ORDER BY nom";
        data = FXCollections.observableArrayList();
        
        pre = XConnection.connecter().prepareStatement(sql);
        pre.setString(1, "%"+b.getNom().get()+"%");
        res = pre.executeQuery();
        
        while(res.next()){
            data.add(new Contact(res.getInt("id_c"), res.getString("numero"), res.getString("nom")));
        }
        return data;
    }

    @Override
    public Contact get(Contact b) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
