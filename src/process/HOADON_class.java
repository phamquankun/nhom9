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
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DELL
 */
public class HOADON_class {

    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    MY_CONNECTION mycon = new MY_CONNECTION();
    OracleCallableStatement cst = null;

    public boolean hoadon(String mapt) {
        String sql = "{ call c##DoAn.thanhtien_hoadon(?) }";
        try {
            cst = (OracleCallableStatement) mycon.createconnection().prepareCall(sql);
            cst.setString(1, mapt);
            return (cst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(KHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ResultSet tim1(String s) {
        String selectquery = "SELECT * from thuephong where maso=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            pst.setString(1, s);
            rs = (OracleResultSet) pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(HOADON_class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet tim2(String s) {
        String selectquery = "SELECT * from thuedichvu where mapt=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            pst.setString(1, s);
            rs = (OracleResultSet) pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(HOADON_class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet tim3(String s) {
        String selectquery = "SELECT * from hoadon where mapt=?";
        try {
            pst = (OraclePreparedStatement) mycon.createconnection().prepareStatement(selectquery);
            pst.setString(1, s);
            rs = (OracleResultSet) pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(HOADON_class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
