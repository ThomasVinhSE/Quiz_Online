/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblchoice;

import java.io.Serializable;

/**
 *
 * @author Vinh
 */
public class TblChoiceDTO implements Serializable {
    private int choiceId;
    private int questionId;
    private String content;
    private boolean isCorrect;
    private boolean isChoose;
    public TblChoiceDTO() {
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public boolean isIsChoose() {
        return isChoose;
    }
    
    public TblChoiceDTO(int choiceId, int questionId, String content, boolean isCorrect) {
        this.choiceId = choiceId;
        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;
    }

    /**
     * @return the choiceId
     */
    public int getChoiceId() {
        return choiceId;
    }

    /**
     * @param choiceId the choiceId to set
     */
    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
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
     * @return the isCorrect
     */
    public boolean isIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
