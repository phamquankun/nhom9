/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import Data.MY_CONNECTION;
import java.sql.Connection;
import java.sql.ResultSet;
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
public class TIMPHONGTRONG {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleCallableStatement cst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();

    public void timphongtrong(String ngaythue, String ngaytra, JTable table) {
        String sql
                = "select p.sophong sp, p.loaiphong lp,lp.gia,lp.songuoi\n"
                + "from phong p join loaiphong lp on p.loaiphong=lp.idphong\n"
                + "where p.sophong not in (select distinct tp.so_phong "
                + "from thuephong tp join hoadon hd on tp.maso=hd.mapt "
                + "where tonghoadon is null and "
                + "((to_date(?,'DD/MM/YYYY') between tp.ngaythue and tp.ngaytra )\n"
                + "  or (to_date(?,'DD/MM/YYYY') between tp.ngaythue and tp.ngaytra)\n"
                + "  or (tp.ngaythue between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY'))\n"
                + "  or (tp.ngaytra between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY'))))\n"
                + "  order by sp";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(sql);
            pst.setString(1, ngaythue);
            pst.setString(2, ngaytra);
            pst.setString(3, ngaythue);
            pst.setString(4, ngaytra);
            pst.setString(5, ngaythue);
            pst.setString(6, ngaytra);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            tableModel.setRowCount(0);
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillRoomJTable(JTable table) {
        String selectquery = "SELECT * FROM c##DoAn.phong join c##DoAn.loaiphong on phong.loaiphong=loaiphong.idphong order by sophong asc";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            rs = (OracleResultSet) pst.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(5);
                row[3] = rs.getInt(6);
                tableModel.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PHONG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
