package com.qci.onsite.pojo;

/**
 * Created by Ankit on 02-12-2018.
 */

public class ImageUploadResponse {

    private Boolean isSuccess;
    private String message;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
