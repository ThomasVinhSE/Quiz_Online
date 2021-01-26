/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblsubject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vinhnq.utilities.HelperUtil;

/**
 *
 * @author Vinh
 */
public class TblSubjectDAO  implements Serializable{
    private List<TblSubjectDTO> list;

    public List<TblSubjectDTO> getList() {
        return list;
    }

    public void setList(List<TblSubjectDTO> list) {
        this.list = list;
    }
    
    private void closeConenction(Connection cn , PreparedStatement pst , ResultSet rs) throws SQLException
    {
        if(rs != null)
            rs.close();
        if(pst != null)
            pst.close();
        if(cn != null)
            cn.close();
    }
    public void getAllForList() throws NamingException,SQLException
    {
        Connection cn = null;
        PreparedStatement pst= null;
        ResultSet rs = null;
        TblSubjectDTO dto = null;
        try{
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "Select subjectId,name,numOfQuestion,timeForQuiz from tbl_Subject ";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next())
                {
                    int subjectId = rs.getInt("subjectId");
                    String name = rs.getString("name");
                    int number = rs.getInt("numOfQuestion");
                    int timeForQuiz = rs.getInt("timeForQuiz");
                    dto = new TblSubjectDTO(subjectId, name, number, timeForQuiz);
                    if(list == null)
                        list = new ArrayList<>();
                    list.add(dto);
                }
            }
        }
        finally{
            closeConenction(cn, pst, rs);
        }
    }
    public int findSubjectIdByName(String name) throws NamingException,SQLException
    {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = -1;
        try{
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "Select subjectId from tbl_Subject where name = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, name);
                rs = pst.executeQuery();
                if(rs.next())
                    id = rs.getInt("subjectId");
            }
        }
        finally{
            closeConenction(cn, pst, rs);
        }
        return id;
    }
    
}
