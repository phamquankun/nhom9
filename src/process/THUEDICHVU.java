/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import Data.MY_CONNECTION;
import process.PHONG;
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
public class THUEDICHVU {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();

    public void fillCombobox(JComboBox combobox) {
        String selectquery = "SELECT * FROM c##DoAn.dichvu";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            //DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            while (rs.next()) {
                combobox.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean themphieuthuedv(int mapt, String dichvu, String ngaythue) {
        String sql = "INSERT INTO thuedichvu(mapt,dichvu,ngaythue) VALUES(?,?,to_date(?,'dd-MM-yyyy'))";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1, mapt);
            pst.setString(2, dichvu);
            pst.setString(3, ngaythue);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(THUEDICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean suaphieuthue(int maso, int mapt, String dichvu, String ngaythue) {
        String sql = "UPDATE thuedichvu SET mapt=?,dichvu=?,ngaythue=TO_DATE(?,'dd-MM-yyyy') WHERE maso=?  ";

        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);

            pst.setInt(1, mapt);
            pst.setString(2, dichvu);
            pst.setString(3, ngaythue);
            pst.setInt(4, maso);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean xoaphieuthue(int maso) {
        String sql = "DELETE FROM thuedichvu WHERE maso=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setInt(1, maso);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(THUEDICHVU.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void fillSerJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.thuedichvu order by maso asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(THUEDICHVU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
