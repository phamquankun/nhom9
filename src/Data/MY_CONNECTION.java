/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class MY_CONNECTION {
   
    public static Connection createconnection(){
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","c##DoAn","mk1252000");
            return con;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
        
    }
}