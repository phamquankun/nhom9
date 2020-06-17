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
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DELL
 */
public class NHANVIEN {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleCallableStatement cst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();

    public boolean themnv(String tennv, String cmnd, String diachi, String sdt, String chucvu) {
        String sql = "{ call c##DoAn.them_nhanvien(?,?,?,?,?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setString(1, tennv);
            cst.setString(2, cmnd);
            cst.setString(3, sdt);
            cst.setString(4, diachi);
            cst.setString(5, chucvu);
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(NHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean xoanv(String cmnd) {
        String sql = "{ call c##DoAn.xoa_nhanvien(?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setString(1, cmnd);
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean suanv(int manv, String tennv, String cmnd, String sdt, String diachi, String chucvu) throws SQLException {
        String sql = "UPDATE nhanvien SET tennv=?,cmnd=?,sdt=?,diachi=?,chucvu=? WHERE manv=? ";
        pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
        pst.setString(1, tennv);
        pst.setString(2, cmnd);
        pst.setString(3, sdt);
        pst.setString(4, diachi);
        pst.setString(5, chucvu);
        pst.setInt(6, manv);
        return (pst.executeUpdate() > 0);
    }

    public void fillNHANVIENJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.nhanvien order by manv asc";
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
            Logger.getLogger(NHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
