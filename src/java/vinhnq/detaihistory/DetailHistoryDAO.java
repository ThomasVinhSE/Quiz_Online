/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.detaihistory;

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
public class DetailHistoryDAO implements Serializable {

    private List<DetailInfor> list;
    private String test;
    private String tes1t;
    public List<DetailInfor> getList() {
        return list;
    }

    public void setList(List<DetailInfor> list) {
        this.list = list;
    }
    
    private void closeConnection(Connection cn, PreparedStatement pst, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (cn != null) {
            cn.close();
        }
    }
    public int getAllListDetail(String historyId,int limit,int index) throws NamingException,SQLException
    {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int size = -1;
        DetailInfor dto = null;
        try
        {
            cn = HelperUtil.makeConnection();
            if(cn != null)
            {
                String sql = "select count(questionId) as size "
                        + "from Detail_History where historyId like ?";
                pst  = cn.prepareStatement(sql);
                pst.setString(1, "%"+historyId+"%");
                rs = pst.executeQuery();
                if(rs.next())
                {
                    size = rs.getInt("size");
                    rs.close();
                    pst.close();
                }
                sql = " select a.questionId,a.choiceId,b.question_content,b.point,c.choice_content,c.isCorrect "
                        + " from Detail_History a ,tbl_Question b ,tbl_Choice c "
                        + " where a.questionId = b.questionId and a.choiceId = c.choiceId and a.historyId like ? "
                        + " order by b.createDate "
                        + "offset "+limit*(index-1)+" rows "
                        + "fetch next "+limit+" rows only";
                pst  = cn.prepareStatement(sql);
                pst.setString(1, "%"+historyId+"%");
                rs = pst.executeQuery();
                while(rs.next())
                {
                    int questionId = rs.getInt("questionId");
                    int choiceId = rs.getInt("choiceId");
                    String question_content = rs.getString("question_content");
                    String choice_content = rs.getString("choice_content");
                    float point = rs.getFloat("point");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    DetailHistoryDTO detailDTO = new DetailHistoryDTO(Integer.parseInt(historyId), questionId, choiceId);
                    dto = new DetailInfor(detailDTO, question_content, choice_content, isCorrect);
                    if(isCorrect)
                        dto.setPoint(point);
                    else
                        dto.setPoint(0f);
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
    public boolean addRow(Connection cn, int historyId, int questionId, int choiceId) throws NamingException, SQLException {
        PreparedStatement pst = null;
        boolean result = false;
        try {
            if (cn != null) {
                String sql = "";
                if (choiceId != -1) {
                    sql = "Insert into Detail_History(historyId,choiceId,questionId) VALUES(?,?,?)";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, historyId);
                    pst.setInt(2, choiceId);
                    pst.setInt(3, questionId);
                    result = pst.executeUpdate() > 0;
                    
                } else {
                    sql = "Insert into Detail_History(historyId,questionId,choiceId) VALUES(?,?,78)";
                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, historyId);
                    pst.setInt(2, questionId);
                    result = pst.executeUpdate() > 0;
                }
                
            }
        } finally {
            closeConnection(null, pst, null);
        }
        return result;
    }
}
