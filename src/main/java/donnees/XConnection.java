/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import beans.Boite;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author NGEREZA
 */
public class XConnection {
    private static final String URL = "jdbc:sqlite:"+System.getProperty("user.home")+File.separator+"messagerie.data";
    private static final String DRIVER = org.sqlite.JDBC.class.getName();
    
    
    private static final String sql = "CREATE TABLE IF NOT EXISTS contact\n" +
"(\n" +
"	id_c INTEGER PRIMARY KEY NOT NULL,\n" +
"	numero VARCHAR(20) UNIQUE NOT NULL,\n" +
"	nom VARCHAR(100) NOT NULL\n" +
");\n" +
"\n" +
"CREATE TABLE IF NOT EXISTS message\n" +
"(\n" +
"	id_m INTEGER PRIMARY KEY NOT NULL,\n" +
"	texte_m VARCHAR(200) NOT NULL,\n" +
"	date_m DATE NOT NULL,\n" +
"	statut INTEGER,\n" +
"	numero VARCHAR(20) NOT NULL,\n" +
"	CONSTRAINT knum FOREIGN KEY (numero) REFERENCES contact (numero)\n" +
");"+
"CREATE TABLE IF NOT EXISTS oauth\n" +
"(\n" +
"	id_o integer primary key not null,\n" +
"	autorisation varchar(500) not null,\n" +
"	token varchar(500),\n" +
"	senderaddress varchar(15),\n" +
"	senderName varchar(100),\n" +
"	date_token date\n" +
");";
    
    
    
    
    
    
    
    private static Connection connect = null;
    private static Statement st = null;
    
    public static Connection connecter(){
        
        if(connect == null){
            try{
                Class.forName(DRIVER);
                connect = DriverManager.getConnection(URL);
            }catch(Exception ex){
                Boite.showException(ex,"xconnection");
            }
        }
        createTable();
        return connect;
    }
    
    
    
    
    private static void createTable(){
        try{
            st = connect.createStatement();
            int cre = st.executeUpdate(sql);
        }catch(Exception ex){
            Boite.showException(ex,"create table");
        }
    }
}
