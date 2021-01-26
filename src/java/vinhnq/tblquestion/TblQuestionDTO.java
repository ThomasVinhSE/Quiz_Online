/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblquestion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinh
 */
public class TblQuestionDTO  implements Serializable,Comparable<TblQuestionDTO>{
    private int questionId;
    private String content;
    private Date createDate;
    private int subjectId;
    private float point;
    private boolean status;
    
    public String passDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if(createDate != null)
            return format.format(createDate);
        return "";
    }
    public TblQuestionDTO() {
    }

    public TblQuestionDTO(int questionId, String content, Date createDate, int subjectId, float point, boolean status) {
        this.questionId = questionId;
        this.content = content;
        this.createDate = createDate;
        this.subjectId = subjectId;
        this.point = point;
        this.status = status;
    }

    /**
     * @return the questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the subjectId
     */
    public int getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the point
     */
    public float getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(float point) {
        this.point = point;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    private String subjectName;

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public int compareTo(TblQuestionDTO o) {
        Date time1 = this.getCreateDate();
        Date time2 = o.getCreateDate();
        if(time1.compareTo(time2) > 0)
            return 1;
        else if(time1.compareTo(time2) < 0)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
