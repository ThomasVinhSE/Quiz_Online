/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblquestion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.util.Pair;
import javax.naming.NamingException;
import vinhnq.tblchoice.TblChoiceDAO;
import vinhnq.tblchoice.TblChoiceDTO;
import vinhnq.utilities.HelperUtil;

/**
 *
 * @author Vinh
 */
public class TblQuestionDAO implements Serializable {

    private List<TblQuestionDTO> list;
    private Map<TblQuestionDTO, List<TblChoiceDTO>> map;

    public Map<TblQuestionDTO, List<TblChoiceDTO>> getMap() {
        return map;
    }

    public void setList(List<TblQuestionDTO> list) {
        this.list = list;
    }

    public void setMap(Map<TblQuestionDTO, List<TblChoiceDTO>> map) {
        this.map = map;
    }

    public List<TblQuestionDTO> getList() {
        return list;
    }

    public int getQuestionForMap(String questionName, int subjectId, boolean isActive,int index) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int size = 0;
        try {
            cn = HelperUtil.makeConnection();
            if (cn != null) {
                String sql = "Select count(questionId) as size from tbl_Question  "
                        + "where question_content like '%" + questionName + "%' or subjectId = ? or status = ? ";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, subjectId);
                pst.setBoolean(2, isActive);
                rs = pst.executeQuery();
                if(rs.next())
                {
                    size = rs.getInt("size");
                    rs.close();
                    pst.close();
                }
                sql = "Select questionId,question_content,createDate,subjectId,point,status "
                        + "from tbl_Question "
                        + "where question_content like '%" + questionName + "%' or subjectId = ? or status = ? "
                        + " order by createDate offset " + 10 * (index - 1) + " rows fetch next 10 rows only";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, subjectId);
                pst.setBoolean(2, isActive);
                rs = pst.executeQuery();
                TblChoiceDAO choiceDAO = null;
                while (rs.next()) {
                    int questionId = rs.getInt("questionId");
                    int idSubject = rs.getInt("subjectId");
                    String question_content = rs.getString("question_content");
                    Timestamp time = rs.getTimestamp("createDate");
                    Date date = new Date(time.getTime());
                    float point = rs.getFloat("point");
                    boolean status = rs.getBoolean("status");
                    TblQuestionDTO dto = new TblQuestionDTO(questionId, question_content, date, idSubject, point, status);

                    if (choiceDAO == null) {
                        choiceDAO = new TblChoiceDAO();
                    }

                    choiceDAO.getAllAnswerByQuestionId(cn, questionId);
                    List<TblChoiceDTO> choiceList = choiceDAO.getChoiceList();
                    if (choiceList != null) {
                        if (map == null) {
                            
                            map = new TreeMap<>();
                        }
                        map.put(dto, choiceList);
                        choiceDAO.setChoiceList(null);
                    }
                }
            }
        } finally {
            closeConenction(cn, pst, rs);
        }
        return size;
    }
    
    public void getQuestionForMap2(int subjectId,int limit) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = HelperUtil.makeConnection();
            if (cn != null) {
                String sql = "Select Top (?) questionId,question_content,createDate,subjectId,point,status "
                        + "from tbl_Question "
                        + "where subjectId = ? and status = 1 "
                        + "order by NEWID() ";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, limit);
                pst.setInt(2, subjectId);
                rs = pst.executeQuery();
                TblChoiceDAO choiceDAO = null;
                while (rs.next()) {
                    int questionId = rs.getInt("questionId");
                    String question_content = rs.getString("question_content");
                    Timestamp time = rs.getTimestamp("createDate");
                    Date date = new Date(time.getTime());
                    float point = rs.getFloat("point");
                    boolean status = rs.getBoolean("status");
                    TblQuestionDTO dto = new TblQuestionDTO(questionId, question_content, date, subjectId, point, status);

                    if (choiceDAO == null) {
                        choiceDAO = new TblChoiceDAO();
                    }

                    choiceDAO.getAllAnswerByQuestionId(cn, questionId);
                    List<TblChoiceDTO> choiceList = choiceDAO.getChoiceList();
                    if (choiceList != null) {
                        if (map == null) {
                            
                            map = new TreeMap<>();
                        }
                        map.put(dto, choiceList);
                        choiceDAO.setChoiceList(null);
                    }
                }
            }
        } finally {
            closeConenction(cn, pst, rs);
        }
    }
    
    
    
    
    public Pair<TblQuestionDTO, List<TblChoiceDTO>> searchById(int questionId) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Pair<TblQuestionDTO, List<TblChoiceDTO>> pair = null;
        try {
            cn = HelperUtil.makeConnection();
            if (cn != null) {
                String sql = "Select question_content,createDate,a.subjectId,point,status,b.name from tbl_Question a,"
                        + "tbl_Subject b "
                        + "where a.subjectId = b.subjectId and questionId = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, questionId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String question_content = rs.getString("question_content");
                    Timestamp time = rs.getTimestamp("createDate");
                    Date date = new Date(time.getTime());
                    int subjectId = rs.getInt("subjectId");
                    float point = rs.getFloat("point");
                    boolean status = rs.getBoolean("status");
                    String subjectName = rs.getString("name");
                    TblQuestionDTO dto = new TblQuestionDTO(questionId, question_content, date, subjectId, point, status);
                    dto.setSubjectName(subjectName);
                    TblChoiceDAO choiceDAO = new TblChoiceDAO();
                    choiceDAO.getAllAnswerByQuestionId(cn, questionId);
                    List<TblChoiceDTO> choiceList = choiceDAO.getChoiceList();
                    if (choiceList != null) {
                        pair = new Pair<>(dto, choiceList);
                    }
                }
            }
        } finally {
            closeConenction(cn, pst, rs);
        }
        return pair;
    }

    public boolean setInActiveQuestion(int questionId) throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean result = false;
        try {
            cn = HelperUtil.makeConnection();
            if (cn != null) {
                String sql = "Update tbl_Question set status =  ? where questionId = ?";
                pst = cn.prepareStatement(sql);
                pst.setBoolean(1, false);
                pst.setInt(2, questionId);
                result = pst.executeUpdate() > 0;
            }
        } finally {
            closeConenction(cn, pst, null);
        }
        return result;
    }

    private void closeConenction(Connection cn, PreparedStatement pst, ResultSet rs) throws SQLException {
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

    public boolean updateQuestion(TblQuestionDTO dto, List<TblChoiceDTO> choiceList) throws NamingException, SQLException {
        boolean result = false;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection cn = null;

        try {

            cn = HelperUtil.makeConnection();
            if (cn != null) {
                if (dto != null) {
                    cn.setAutoCommit(false);
                    String sql = "Update tbl_Question "
                            + "set question_content = ?, subjectId = ?,point = ?,status = ? "
                            + "where questionId = ?";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, dto.getContent());
                    pst.setInt(2, dto.getSubjectId());
                    pst.setFloat(3, dto.getPoint());
                    pst.setBoolean(4, dto.isStatus());
                    pst.setInt(5, dto.getQuestionId());
                    result = pst.executeUpdate() > 0;
                    if (result) {
                        int questionId = dto.getQuestionId();
                        if (questionId != -1) {
                            TblChoiceDAO choiceDAO = new TblChoiceDAO();
                            result = choiceDAO.updateChoice(cn, choiceList, questionId);
                            if (result) {
                                cn.commit();
                            } else {
                                cn.rollback();
                            }
                        } else {
                            cn.rollback();
                        }
                    } else {
                        cn.rollback();
                    }
                }
            }
        } finally {
            closeConenction(cn, pst, rs);
        }
        return result;
    }

    public boolean createQuestion(TblQuestionDTO dto, List<TblChoiceDTO> choiceList) throws NamingException, SQLException {
        boolean result = false;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection cn = null;

        try {

            cn = HelperUtil.makeConnection();
            if (cn != null) {
                if (dto != null) {
                    cn.setAutoCommit(false);
                    String sql = "Insert into tbl_Question(question_content,subjectId,point) VALUES(?,?,?)";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, dto.getContent());
                    pst.setInt(2, dto.getSubjectId());
                    pst.setFloat(3, dto.getPoint());
                    result = pst.executeUpdate() > 0;
                    if (result) {
                        sql = "Select Max(questionId) as id from tbl_Question";
                        pst = cn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        int questionId = -1;
                        if (rs.next()) {
                            questionId = rs.getInt("id");
                        }
                        if (questionId != -1) {
                            TblChoiceDAO choiceDAO = new TblChoiceDAO();
                            result = choiceDAO.createChoice(cn, choiceList, questionId);
                            if (result) {
                                cn.commit();
                            } else {
                                cn.rollback();
                            }
                        } else {
                            cn.rollback();
                        }
                    } else {
                        cn.rollback();
                    }
                }
            }
        } finally {
            closeConenction(cn, pst, rs);
        }
        return result;
    }

}
