/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import Data.MY_CONNECTION;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DELL
 */
public class PHONG {
    Connection conn= null;
    OraclePreparedStatement pst= null;
    OracleResultSet rs= null;
    MY_CONNECTION mycon= new MY_CONNECTION();
    public void fillTyPeRooms(JTable table){
        String selectquery = "SELECT * FROM c##DoAn.loaiphong";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs=(OracleResultSet)pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[3];
                row[0]= rs.getString(1);
                row[1]= rs.getString(2);
                row[2]= rs.getString(3);
                
                tableModel.addRow(row);
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void fillCombobox(JComboBox combobox){
        String selectquery = "SELECT * FROM c##DoAn.loaiphong";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs=(OracleResultSet)pst.executeQuery();
            //DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            while(rs.next()){
                
                combobox.addItem(rs.getString(1));
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean themphong(int sophong, String loaiphong){
        String sql ="INSERT INTO phong(sophong,loaiphong,tinhtrang) VALUES(?,?,?)";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1,sophong);
            pst.setString(2, loaiphong);
            pst.setString(3, "Chưa Đặt");
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    public boolean suaphong(int sophong, String loaiphong,String tinhtrang){
        String sql ="UPDATE phong SET loaiphong=?, tinhtrang=?  WHERE sophong=? ";
                
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1,loaiphong);
            pst.setString(2, tinhtrang);
            pst.setInt(3, sophong);
            
           
            return(pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean xoaphong(int sophong){
         String sql ="DELETE FROM c##DoAn.phong WHERE sophong=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1,sophong);
            return(pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public void fillRoomJTable(JTable table){
        String selectquery = "SELECT * FROM c##DoAn.phong order by sophong asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs=(OracleResultSet)pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[3];
                row[0]= rs.getInt(1);
                row[1]= rs.getString(2);
                row[2]= rs.getString(3);
                
                tableModel.addRow(row);
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean setYesNo(int number,String isdadat){
        String sql ="UPDATE phong SET tinhtrang=?  WHERE sophong=? ";
                
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
           pst.setString(1, isdadat);
            pst.setInt(2, number);
            return(pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public String CheckRe(int number){
        String sql ="SELECT tinhtrang FROM c##DoAn.phong WHERE sophong=?";
                
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1, number);
            rs=(OracleResultSet)pst.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }else{
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    public boolean themloaiphong(String idphong, String gia, String songuoi){
        String sql ="INSERT INTO loaiphong(idphong,gia,songuoi) VALUES(?,?,?)";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1, idphong);
            pst.setString(2, gia);
            pst.setString(3, songuoi);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }     
    }
    public boolean sualoaiphong(String idphong, String gia, String songuoi){
        String sql ="UPDATE loaiphong SET gia=?, songuoi=?  WHERE idphong=? ";
                
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1,gia);
            pst.setString(2, songuoi);
            pst.setString(3, idphong);
            
           
            return(pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }   
    public boolean removeType(String idphong){
         String sql ="DELETE FROM c##DoAn.loaiphong WHERE idphong=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1,idphong);
            return(pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
