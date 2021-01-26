/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.detaihistory;

import java.io.Serializable;

/**
 *
 * @author Vinh
 */
public class DetailHistoryDTO implements Serializable{
    private int historyId;
    private int questionId;
    private int choiceId;

    public DetailHistoryDTO() {
    }

    public DetailHistoryDTO(int historyId, int questionId, int choiceId) {
        this.historyId = historyId;
        this.questionId = questionId;
        this.choiceId = choiceId;
    }

    /**
     * @return the historyId
     */
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
    
}
