/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblhistory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import vinhnq.detaihistory.DetailInfor;
import vinhnq.utilities.HelperUtil;

/**
 *
 * @author Vinh
 */
public class TblHistoryDAO implements Serializable {
    private List<TblHistoryDTO> list;

    public List<TblHistoryDTO> getList() {
        return list;
    }

    public void setList(List<TblHistoryDTO> list) {
        this.list = list;
    }
    
    public int getNextId(Connection cn) throws NamingException,SQLException
    {
        PreparedStatement pst = null;
        ResultSet rs = null;
        int index = -1;
        try
        {
            if(cn != null)
            {
                String sql = "Select max(historyId) as max from tbl_History";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next())
                {
                    index = rs.getInt("max");
                }
            }
        }
        finally{
            closeConnection(null, pst, rs);
        }
        return index;
    }
    private void closeConnection(Connection cn,PreparedStatement pst,ResultSet rs) throws SQLException
    {
        if(rs != null)
            rs.close();
        if(pst != null)
            pst.close();
        if(cn != null)
            cn.close();
    }

    public int getDataForList(String email,String historyId,String subject,int limit,int index) throws NamingException,SQLException
    {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        TblHistoryDTO dto = null;
        int size = 0;
        try{
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "Select count(a.historyId) as size from tbl_History a,tbl_Subject b "
                        + "where a.subjectId = b.subjectId and email = ? and (a.historyId like ? or b.name like ?) ";
                      
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, "%"+historyId+"%");
                if(subject.equals("0"))
                    pst.setString(3, "%");
                else
                    pst.setString(3, "%"+subject+"%");
                rs = pst.executeQuery();
                if(rs.next())
                {
                    size = rs.getInt("size");
                    rs.close();
                    pst.close();
                }
                sql = "Select a.historyId,a.email,a.subjectId,a.startTime,a.endTime,a.numOfCorrect,"
                        + "a.numOfInCorrect,a.total from tbl_History a,tbl_Subject b "
                        + "where a.subjectId = b.subjectId and email = ? and (a.historyId like ? or b.name like ?) "
                        + "order by a.startTime "
                        + "offset "+limit*(index-1)+" rows "
                        +"fetch next "+limit+" rows only";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, "%"+historyId+"%");
                if(subject.equals("0"))
                    pst.setString(3, "%");
                else
                    pst.setString(3, "%"+subject+"%");
                rs = pst.executeQuery();
                while(rs.next())
                {
                   int id = rs.getInt("historyId");
                   int subjectId = rs.getInt("subjectId");
                   Timestamp time = rs.getTimestamp("startTime");
                   Date startTime = new Date(time.getTime());
                   time  = rs.getTimestamp("endTime");
                   Date endTime = new Date(time.getTime());
                   int correct = rs.getInt("numOfCorrect");
                   int inCorrect = rs.getInt("numOfInCorrect");
                   float total = rs.getFloat("total");
                   dto = new TblHistoryDTO(id, email, subjectId, startTime, endTime, correct, inCorrect, total);
                   if(list == null)
                       list = new ArrayList<>();
                   list.add(dto);
                }
            }
        }
        finally{
            closeConnection(cn, pst, rs);
        }
        return size;
    }
    public boolean addHistory(Connection cn,String email,int subjectId,Date startTime,Date endTime,int numOfCorrect,int numOfIncorrect,float total) throws NamingException,SQLException
    {
        
        boolean result = false;
        PreparedStatement pst = null;
        
        try
        {
            if(cn != null)
            {
                String sql = "Insert into tbl_History(email,subjectId,startTime,endTime,numOfCorrect,numOfInCorrect,total) "
                        + "Values(?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setInt(2, subjectId);
                Timestamp time = new Timestamp(startTime.getTime());
                pst.setTimestamp(3, time);
                time = new Timestamp(endTime.getTime());
                pst.setTimestamp(4, time);
                pst.setInt(5, numOfCorrect);
                pst.setInt(6, numOfIncorrect);
                pst.setFloat(7, total);
                
                result = pst.executeUpdate() > 0;
            }
        }
        finally{
            closeConnection(null, pst, null);
        }
        return result;
    }
}
