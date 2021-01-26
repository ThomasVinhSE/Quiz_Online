/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblchoice;

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
public class TblChoiceDAO implements Serializable {
    private List<TblChoiceDTO> choiceList;

    public List<TblChoiceDTO> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<TblChoiceDTO> choiceList) {
        this.choiceList = choiceList;
    }
    
    private void closeConnection(Connection cn,  PreparedStatement pst, ResultSet rs ) throws SQLException
    {
        if(rs != null)
            rs.close();
        if(pst != null)
            pst.close();
        if(cn != null)
            cn.close();
    }
    public void getAllAnswerByQuestionId(Connection cn,int questionId) throws NamingException,SQLException
    {
        PreparedStatement pst = null;
        ResultSet rs = null;
        TblChoiceDTO dto = null;
        try
        {
            if(cn != null)
            {
                String sql = "Select choiceId,choice_content,isCorrect from tbl_Choice "
                        + "where questionId = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, questionId);
                rs = pst.executeQuery();
                while(rs.next())
                {
                    int choiceId = rs.getInt("choiceId");
                    String content = rs.getString("choice_content");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    dto = new TblChoiceDTO(choiceId, questionId, content, isCorrect);
                    if(choiceList == null)
                        choiceList = new ArrayList<>();
                    choiceList.add(dto);
                }
            }
        }
        finally{
            closeConnection(null, pst, rs);
        }
    }
    public boolean updateChoice(Connection cn,List<TblChoiceDTO> choiceList,int questionId) throws NamingException,SQLException
    {
        boolean result = false;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try
        {
            if(cn != null)
            {   
                if(choiceList != null || !choiceList.isEmpty())
                {
                    result = true;
                    String sql = "Update tbl_Choice "
                            + "set choice_content = ?,isCorrect = ? where questionId = ? and choiceId = ?";
                    pst = cn.prepareStatement(sql);
                    for (TblChoiceDTO tblChoiceDTO : choiceList) {
                        String content = tblChoiceDTO.getContent();
                        boolean isCorrect = tblChoiceDTO.isIsCorrect();
                        int choiceId = tblChoiceDTO.getChoiceId();
                        pst.setString(1, content);
                        pst.setBoolean(2, isCorrect);
                        pst.setInt(3, questionId);
                        pst.setInt(4, choiceId);
                        
                        result = pst.executeUpdate() > 0;
                        if(!result)
                            break;
                    }
                   
                }
            }
            
        }
        finally{
            closeConnection(null, pst, rs);
        }
        
        return result;
    }
    public boolean createChoice(Connection cn,List<TblChoiceDTO> choiceList,int questionId) throws NamingException,SQLException
    {
        boolean result = false;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try
        {
            if(cn != null)
            {   
                if(choiceList != null || !choiceList.isEmpty())
                {
                    result = true;
                    String sql = "Insert into tbl_Choice(questionId,choice_content,isCorrect) VALUES(?,?,?)";
                    pst = cn.prepareStatement(sql);
                    for (TblChoiceDTO tblChoiceDTO : choiceList) {
                        String content = tblChoiceDTO.getContent();
                        boolean isCorrect = tblChoiceDTO.isIsCorrect();
                        
                        pst.setInt(1, questionId);
                        pst.setString(2, content);
                        pst.setBoolean(3, isCorrect);
                        result = pst.executeUpdate() > 0;
                        if(!result)
                            break;
                    }
                   
                }
            }
            
        }
        finally{
            closeConnection(null, pst, rs);
        }
        
        return result;
    }
}
