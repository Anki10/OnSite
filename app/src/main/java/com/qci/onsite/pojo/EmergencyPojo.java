package com.qci.onsite.pojo;

/**
 * Created by Ankit on 30-01-2019.
 */

public class EmergencyPojo {

    private long id;
    private long local_id;
    private String Hospital_name;
    private int Hospital_id;
    private String EMERGENCY_down_Norms;
    private String EMERGENCY_down_Norms_remark;
    private String EMERGENCY_down_Norms_NC;
    private String EMERGENCY_down_Norms_video;

    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }

    public String getLocal_EMERGENCY_down_Norms_video() {
        return Local_EMERGENCY_down_Norms_video;
    }

    public void setLocal_EMERGENCY_down_Norms_video(String local_EMERGENCY_down_Norms_video) {
        Local_EMERGENCY_down_Norms_video = local_EMERGENCY_down_Norms_video;
    }

    private String Local_EMERGENCY_down_Norms_video;

    public String getEMERGENCY_down_Norms_NC() {
        return EMERGENCY_down_Norms_NC;
    }

    public void setEMERGENCY_down_Norms_NC(String EMERGENCY_down_Norms_NC) {
        this.EMERGENCY_down_Norms_NC = EMERGENCY_down_Norms_NC;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHospital_name() {
        return Hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        Hospital_name = hospital_name;
    }

    public int getHospital_id() {
        return Hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        Hospital_id = hospital_id;
    }

    public String getEMERGENCY_down_Norms() {
        return EMERGENCY_down_Norms;
    }

    public void setEMERGENCY_down_Norms(String EMERGENCY_down_Norms) {
        this.EMERGENCY_down_Norms = EMERGENCY_down_Norms;
    }

    public String getEMERGENCY_down_Norms_remark() {
        return EMERGENCY_down_Norms_remark;
    }

    public void setEMERGENCY_down_Norms_remark(String EMERGENCY_down_Norms_remark) {
        this.EMERGENCY_down_Norms_remark = EMERGENCY_down_Norms_remark;
    }

    public String getEMERGENCY_down_Norms_video() {
        return EMERGENCY_down_Norms_video;
    }

    public void setEMERGENCY_down_Norms_video(String EMERGENCY_down_Norms_video) {
        this.EMERGENCY_down_Norms_video = EMERGENCY_down_Norms_video;
    }




}
