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
public class THUEPHONG {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    OracleCallableStatement cst = null;
    MY_CONNECTION mycon = new MY_CONNECTION();
    PHONG ph = new PHONG();

    public boolean themdatphong(int kh_id, int so_phong, String ngaythue, String ngaytra) {
        //String sql ="INSERT INTO thuephong (kh_id,so_phong,ngaythue,ngaytra) VALUES(?,?,to_date(?,'dd-MM-yyyy'),to_date(?,'dd-MM-yyyy'))";
        String sql = "{ call c##DoAn.them_phieuthue(?,?,to_date(?,'dd-MM-yyyy'),to_date(?,'dd-MM-yyyy')) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setInt(1, kh_id);
            cst.setInt(2, so_phong);
            cst.setString(3, ngaythue);
            cst.setString(4, ngaytra);
            /*if (ph.CheckRe(so_phong).equals("Chưa Đặt")) {
                if (cst.executeUpdate() > 0) {
                    ph.setYesNo(so_phong, "Đã Đặt");
                    return true;
                } else {
                    return false;
                }
            } else {               
                JOptionPane.showMessageDialog(null, "Trong khoảng thời gian này phòng đã được đặt", "Enror", JOptionPane.WARNING_MESSAGE);
                return false;
            }*/
            return (cst.executeUpdate() > 0);

        } catch (SQLException ex) {
            if (ex.getMessage().contains("ORA-02291")) {
                JOptionPane.showMessageDialog(null, "Khách hàng không tồn tại!!!", "Enror", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (ex.getMessage().contains("ORA-20011")) {
                JOptionPane.showMessageDialog(null, "Trong khoảng thời gian này phòng đang được đặt!!!", "Enror", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            Logger.getLogger(THUEPHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean removeRe(int maso) {
        String sql = "{ call c##DoAn.xoa_phieuthue(?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setInt(1, maso);
            /*int so_phong = getRoomNumberFromRe(maso);
            int r = pst.executeUpdate();
            if (r > 0){
                ph.setYesNo(so_phong,"Chưa Đặt");
                return true;
            }else{
                return false;
            }*/
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(THUEPHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean editRev(int maso, int kh_id, int so_phong, String ngaythue, String ngaytra) {
        String sql = "UPDATE thuephong SET kh_id=?,so_phong=?,ngaythue=TO_DATE(?,'dd-MM-yyyy'),ngaytra=TO_DATE(?,'dd-MM-yyyy')WHERE maso=?  ";

        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);

            pst.setInt(1, kh_id);
            pst.setInt(2, so_phong);
            pst.setString(3, ngaythue);
            pst.setString(4, ngaytra);
            pst.setInt(5, maso);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(THUEPHONG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void fillReJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.thuephong order by maso asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(THUEPHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRoomNumberFromRe(int maso) {
        String selectquery = "SELECT so_phong FROM c##DoAn.thuephong WHERE maso=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            pst.setInt(1, maso);
            rs = (OracleResultSet) pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(THUEPHONG.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
