/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblaccount;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import vinhnq.utilities.HelperUtil;

/**
 *
 * @author Vinh
 */
public class TblAccountDAO implements Serializable{
    
    private void closeConnection(Connection cn, PreparedStatement pst , ResultSet rs ) throws SQLException
    {
        if(rs != null)
            rs.close();
        if(pst != null)
            pst.close();
        if(cn != null)
            cn.close();
    }
    public boolean register(TblAccountDTO dto) throws NamingException,SQLException,NoSuchAlgorithmException
    {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try
        {
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "Insert into tbl_Account Values(?,?,?,?,?)";
                String encode = HelperUtil.encodeSHA256(dto.getPassword());
                pst = cn.prepareStatement(sql);
                pst.setString(1, dto.getEmail());
                pst.setString(2, dto.getName());
                pst.setString(3, encode);
                pst.setInt(4,1);
                pst.setString(5,"New");
                result = pst.executeUpdate() > 0;
                
            }
        }
        finally{
            closeConnection(cn, pst, rs);
        }
        return result;
    }
    public TblAccountDTO checkLogin(String email,String password) throws NamingException,SQLException, NoSuchAlgorithmException
    {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        TblAccountDTO dto = null;
        
        try
        {
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "Select email,name,roleId,status from tbl_Account where email = ? and password = ?";
                String encode = HelperUtil.encodeSHA256(password);
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, encode);
                rs = pst.executeQuery();
                if(rs.next())
                {
                    String name = rs.getString("name");
                    int roleId = rs.getInt("roleId");
                    String status = rs.getString("status");
                    dto = new TblAccountDTO(email, name, password, roleId, status);
                }
                
            }
        }
        finally{
            closeConnection(cn, pst, rs);
        }
        return dto;
    }
}
