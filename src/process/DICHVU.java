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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DELL
 */
public class DICHVU {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();

    public boolean themdv(String tendv, int gia) {
        String sql = "INSERT INTO dichvu(tendv,gia) VALUES(?,?)";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1, tendv);
            pst.setInt(2, gia);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editSer(String tendv, int gia, int madv) {
        String sql = "UPDATE dichvu SET tendv=?,gia=?  WHERE madv=? ";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1, tendv);
            pst.setInt(2, gia);
            pst.setInt(3, madv);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean removeSer(int madv) {
        String sql = "DELETE FROM dichvu WHERE madv=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1, madv);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void fillSerJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.dichvu order by madv asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getInt(3);
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DICHVU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
