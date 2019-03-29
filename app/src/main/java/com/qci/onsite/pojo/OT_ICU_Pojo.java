package com.qci.onsite.pojo;

/**
 * Created by Ankit on 12-02-2019.
 */

public class OT_ICU_Pojo {

    private String Hospital_name;
    private int Hospital_id;
    private String OT_wash_up;
    private String OT_wash_Remark;
    private String OT_wash_up_Nc;
    private String OT_wash_up_photo;

    public String getLocal_OT_wash_up_photo() {
        return Local_OT_wash_up_photo;
    }

    public void setLocal_OT_wash_up_photo(String local_OT_wash_up_photo) {
        Local_OT_wash_up_photo = local_OT_wash_up_photo;
    }

    private String Local_OT_wash_up_photo;


    private long id;

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

    public String getOT_wash_up() {
        return OT_wash_up;
    }

    public void setOT_wash_up(String OT_wash_up) {
        this.OT_wash_up = OT_wash_up;
    }

    public String getOT_wash_Remark() {
        return OT_wash_Remark;
    }

    public void setOT_wash_Remark(String OT_wash_Remark) {
        this.OT_wash_Remark = OT_wash_Remark;
    }

    public String getOT_wash_up_Nc() {
        return OT_wash_up_Nc;
    }

    public void setOT_wash_up_Nc(String OT_wash_up_Nc) {
        this.OT_wash_up_Nc = OT_wash_up_Nc;
    }

    public String getOT_wash_up_photo() {
        return OT_wash_up_photo;
    }

    public void setOT_wash_up_photo(String OT_wash_up_photo) {
        this.OT_wash_up_photo = OT_wash_up_photo;
    }




}
