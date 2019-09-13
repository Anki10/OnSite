package com.qci.onsite.pojo;

public class DataSyncResponse {

    private Boolean isSuccess;
    private String message;
    private int tabId;
    private int asmtId;
    private Boolean asmtdonebyasr2;


    public Boolean getAsmtdonebyasr2() {
        return asmtdonebyasr2;
    }

    public void setAsmtdonebyasr2(Boolean asmtdonebyasr2) {
        this.asmtdonebyasr2 = asmtdonebyasr2;
    }



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

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public int getAsmtId() {
        return asmtId;
    }

    public void setAsmtId(int asmtId) {
        this.asmtId = asmtId;
    }




}
