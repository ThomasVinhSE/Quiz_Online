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
public class DetailInfor implements Serializable {
    private DetailHistoryDTO dto;
    private String questionContent;
    private String choiceContent;
    private float point;
    private boolean isCorrect;

    public DetailInfor() {
    }

    public DetailInfor(DetailHistoryDTO dto, String questionContent, String choiceContent, boolean isCorrect) {
        this.dto = dto;
        this.questionContent = questionContent;
        this.choiceContent = choiceContent;
        this.isCorrect = isCorrect;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
    
    /**
     * @return the dto
     */
    public DetailHistoryDTO getDto() {
        return dto;
    }

    /**
     * @param dto the dto to set
     */
    public void setDto(DetailHistoryDTO dto) {
        this.dto = dto;
    }

    /**
     * @return the questionContent
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * @param questionContent the questionContent to set
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * @return the choiceContent
     */
    public String getChoiceContent() {
        return choiceContent;
    }

    /**
     * @param choiceContent the choiceContent to set
     */
    public void setChoiceContent(String choiceContent) {
        this.choiceContent = choiceContent;
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
    
}
