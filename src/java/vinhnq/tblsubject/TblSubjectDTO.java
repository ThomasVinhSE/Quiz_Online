/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblsubject;

import java.io.Serializable;

/**
 *
 * @author Vinh
 */
public class TblSubjectDTO implements Serializable{
    private int subjectId;
    private String name;
    private int numOfQuestion;
    private int timeForQuiz;

    public TblSubjectDTO() {
    }

    public TblSubjectDTO(int subjectId, String name, int numOfQuestion, int timeForQuiz) {
        this.subjectId = subjectId;
        this.name = name;
        this.numOfQuestion = numOfQuestion;
        this.timeForQuiz = timeForQuiz;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the numOfQuestion
     */
    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    /**
     * @param numOfQuestion the numOfQuestion to set
     */
    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    /**
     * @return the timeForQuiz
     */
    public int getTimeForQuiz() {
        return timeForQuiz;
    }

    /**
     * @param timeForQuiz the timeForQuiz to set
     */
    public void setTimeForQuiz(int timeForQuiz) {
        this.timeForQuiz = timeForQuiz;
    }

    @Override
    public boolean equals(Object obj) {
        int id1 = this.getSubjectId();
        int id2 = ((TblSubjectDTO)obj).getSubjectId();
        if(id1 == id2)
            return true;
        return false;
    }

    
}
