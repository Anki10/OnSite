package com.qci.onsite.pojo;

/**
 * Created by raj on 3/23/2018.
 */

public class AssessmentStatusPojo {

    public long getLocal_id() {
        return Local_id;
    }

    public void setLocal_id(long local_id) {
        Local_id = local_id;
    }


    private long Local_id;

    public int getHospital_id() {
        return Hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        Hospital_id = hospital_id;
    }

    private int Hospital_id;
    private String Assessement_name;
    private String Assessement_status;



    public String getAssessement_name() {
        return Assessement_name;
    }

    public void setAssessement_name(String assessement_name) {
        Assessement_name = assessement_name;
    }

    public String getAssessement_status() {
        return Assessement_status;
    }

    public void setAssessement_status(String assessement_status) {
        Assessement_status = assessement_status;
    }


}
