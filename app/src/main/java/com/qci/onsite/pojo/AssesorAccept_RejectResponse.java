package com.qci.onsite.pojo;

/**
 * Created by Ankit on 15-02-2019.
 */

public class AssesorAccept_RejectResponse {

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Boolean isSuccess;
    private String message;


}
