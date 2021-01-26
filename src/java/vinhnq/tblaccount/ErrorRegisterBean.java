/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tblaccount;

/**
 *
 * @author Vinh
 */
public class ErrorRegisterBean {
    private String errorEmail;
    private String errorPassword;
    private String errorConfirmPassword;
    private String errorMatching;
    private String errorName;
    private String isDuplicate;

    public String getIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(String isDuplicate) {
        this.isDuplicate = isDuplicate;
    }
    
    public String getErrorConfirmPassword() {
        return errorConfirmPassword;
    }

    public ErrorRegisterBean() {
    }

    /**
     * @return the errorEmail
     */
    public String getErrorEmail() {
        return errorEmail;
    }

    /**
     * @param errorEmail the errorEmail to set
     */
    public void setErrorEmail(String errorEmail) {
        this.errorEmail = errorEmail;
    }

    public String getErrorMatching() {
        return errorMatching;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getErrorPassword() {
        return errorPassword;
    }

    public void setErrorConfirmPassword(String errorConfirmPassword) {
        this.errorConfirmPassword = errorConfirmPassword;
    }

    public void setErrorMatching(String errorMatching) {
        this.errorMatching = errorMatching;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public void setErrorPassword(String errorPassword) {
        this.errorPassword = errorPassword;
    }

    /**
     * @param errorConfirmPassword the errorConfirmPassword to set
     */
    
    
}
