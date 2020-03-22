/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javafx.collections.ObservableList;

/**
 *
 * @author NGEREZA
 */
public abstract class DAO<T> {
    
    protected String sql ="";
    protected ObservableList<T> data;
    
    protected ResultSet res;
    protected ResultSetMetaData meta;
    protected PreparedStatement pre;
    
    public abstract boolean create(T b) throws Exception;
    public abstract boolean replace(T b)throws Exception;
    public abstract boolean delete(T b)throws Exception;
    public abstract T get(T b)throws Exception;
    
    public abstract ObservableList<T> getList() throws Exception;
    public abstract ObservableList<T> getList(T b) throws Exception;
    
}
