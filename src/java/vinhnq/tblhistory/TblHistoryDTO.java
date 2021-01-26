/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblhistory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinh
 */
public class TblHistoryDTO implements Serializable {
    private int historyId;
    private String email;
    private int subjectId;
    private Date startTime;
    private Date endTime;
    private int numOfCorrect;
    private int numOfInCorrect;
    private float total;

    public TblHistoryDTO() {
    }

    public TblHistoryDTO(int historyId, String email, int subjectId, Date startTime, Date endTime, int numOfCorrect, int numOfInCorrect, float total) {
        this.historyId = historyId;
        this.email = email;
        this.subjectId = subjectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numOfCorrect = numOfCorrect;
        this.numOfInCorrect = numOfInCorrect;
        this.total = total;
    }

    /**
     * @return the historyId
     */
    public String passStartTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if(startTime != null)
            return format.format(startTime);
        return "";
    }
    public String passEndTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if(endTime != null)
            return format.format(endTime);
        return "";
    }
    public int getHistoryId() {
        return historyId;
    }

    /**
     * @param historyId the historyId to set
     */
    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the numOfCorrect
     */
    public int getNumOfCorrect() {
        return numOfCorrect;
    }

    /**
     * @param numOfCorrect the numOfCorrect to set
     */
    public void setNumOfCorrect(int numOfCorrect) {
        this.numOfCorrect = numOfCorrect;
    }

    /**
     * @return the numOfInCorrect
     */
    public int getNumOfInCorrect() {
        return numOfInCorrect;
    }

    /**
     * @param numOfInCorrect the numOfInCorrect to set
     */
    public void setNumOfInCorrect(int numOfInCorrect) {
        this.numOfInCorrect = numOfInCorrect;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
