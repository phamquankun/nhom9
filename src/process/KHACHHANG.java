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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DELL
 */
public class KHACHHANG {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleCallableStatement cst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();

    public void fillCombobox1(JComboBox combobox) {
        String selectquery = "SELECT * FROM c##DoAn.loaikhach";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            //DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            while (rs.next()) {

                combobox.addItem(rs.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean themkh(String cmnd, String hoten, String diachi, String sdt, String loaikhach) {
        String sql = "{ call c##DoAn.them_khachhang(?,?,?,?,?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setString(1, cmnd);
            cst.setString(2, hoten);
            cst.setString(3, diachi);
            cst.setString(4, sdt);
            cst.setString(5, loaikhach);
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            if (ex.getMessage().contains("ORA-00001")) {
                JOptionPane.showMessageDialog(null, "CMND trùng!!!", "Enror", JOptionPane.WARNING_MESSAGE);
            }
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean suakh(String cmnd, String hoten, String diachi, String sdt, String loaikhach, int id) throws SQLException {
        String sql = "UPDATE khachhang SET cmnd=?, hoten=?,diachi=?,sdt=?,loaikhach=? WHERE id=? ";
        pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
        pst.setString(1, cmnd);
        pst.setString(2, hoten);
        pst.setString(3, diachi);
        pst.setString(4, sdt);
        pst.setString(5, loaikhach);
        pst.setInt(6, id);
        return (pst.executeUpdate() > 0);
    }

    public boolean xoakh(String cmnd) {
        String sql = "{ call c##DoAn.xoa_khachhang(?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setString(1, cmnd);
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            if (ex.getMessage().contains("ORA-02292")) {
                JOptionPane.showMessageDialog(null, "Khách đang thuê phòng, KHÔNG được xóa!!!", "Enror", JOptionPane.WARNING_MESSAGE);
            }
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void fillClientJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.khachhang order by id asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
