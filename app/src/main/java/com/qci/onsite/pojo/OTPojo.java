package com.qci.onsite.pojo;

import java.lang.ref.SoftReference;

/**
 * Created by Ankit on 11-02-2019.
 */

public class OTPojo {

    private long id;
    private long local_id;
    private String Hospital_name;
    private int Hospital_id;
    private String anaesthetist_status;
    private String remark_OT_anaesthetist;
    private String nc_OT_anaesthetist;
    private String documented_anaesthesia_status;
    private String remark_documented_anaesthesia;
    private String nc_documented_anaesthesia;
    private String immediate_preoperative_status;
    private String remark_immediate_preoperative;
    private String nc_immediate_preoperative;
    private String anaesthesia_monitoring_status;
    private String remark_anaesthesia_monitoring;
    private String nc_anaesthesia_monitoring;
    private String post_anaesthesia_monitoring_status;
    private String remark_post_anaesthesia_monitoring;
    private String nc_post_anaesthesia_monitoring;
    private String WHO_Patient_Safety_status;
    private String remark_WHO_Patient_Safety;
    private String nc_WHO_Patient_Safety;
    private String image_WHO_Patient_Safety_day1;
    private String Local_image_WHO_Patient_Safety_day1;
    private String image_WHO_Patient_Safety_day2;
    private String Local_image_WHO_Patient_Safety_day2;
    private String image_WHO_Patient_Safety_day3;
    private String Local_image_WHO_Patient_Safety_day3;
    private String OT_Zoning_status;
    private String remark_OT_Zoning;
    private String nc_OT_Zoning;
    private String video_OT_Zoning;
    private String Local_video_OT_Zoning;
    private String infection_control_status;
    private String remark_infection_control;
    private String nc_infection_control;
    private String image_infection_control;
    private String Local_image_infection_control;
    private String narcotic_drugs_status;
    private String remark_narcotic_drugs;
    private String nc_narcotic_drugs;
    private String image_narcotic_drugs;
    private String Local_image_narcotic_drugs;
    private String administration_disposal_status;
    private String remark_administration_disposal;
    private String nc_administration_disposal;
    private String image_administration_disposal;
    private String hand_wash_facility_status;
    private String remark_hand_wash_facility;
    private String nc_hand_wash_facility;
    private String image_hand_wash_facility;

    public String getLocal_image_hand_wash_facility() {
        return Local_image_hand_wash_facility;
    }

    public void setLocal_image_hand_wash_facility(String local_image_hand_wash_facility) {
        Local_image_hand_wash_facility = local_image_hand_wash_facility;
    }

    private String Local_image_hand_wash_facility;

    public String getHand_wash_facility_status() {
        return hand_wash_facility_status;
    }

    public void setHand_wash_facility_status(String hand_wash_facility_status) {
        this.hand_wash_facility_status = hand_wash_facility_status;
    }

    public String getRemark_hand_wash_facility() {
        return remark_hand_wash_facility;
    }

    public void setRemark_hand_wash_facility(String remark_hand_wash_facility) {
        this.remark_hand_wash_facility = remark_hand_wash_facility;
    }

    public String getNc_hand_wash_facility() {
        return nc_hand_wash_facility;
    }

    public void setNc_hand_wash_facility(String nc_hand_wash_facility) {
        this.nc_hand_wash_facility = nc_hand_wash_facility;
    }

    public String getImage_hand_wash_facility() {
        return image_hand_wash_facility;
    }

    public void setImage_hand_wash_facility(String image_hand_wash_facility) {
        this.image_hand_wash_facility = image_hand_wash_facility;
    }



    public String getVideo_OT_Zoning() {
        return video_OT_Zoning;
    }

    public void setVideo_OT_Zoning(String video_OT_Zoning) {
        this.video_OT_Zoning = video_OT_Zoning;
    }

    public String getLocal_video_OT_Zoning() {
        return Local_video_OT_Zoning;
    }

    public void setLocal_video_OT_Zoning(String local_video_OT_Zoning) {
        Local_video_OT_Zoning = local_video_OT_Zoning;
    }



    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }

    public String getLocal_image_WHO_Patient_Safety_day1() {
        return Local_image_WHO_Patient_Safety_day1;
    }

    public void setLocal_image_WHO_Patient_Safety_day1(String local_image_WHO_Patient_Safety_day1) {
        Local_image_WHO_Patient_Safety_day1 = local_image_WHO_Patient_Safety_day1;
    }

    public String getLocal_image_WHO_Patient_Safety_day2() {
        return Local_image_WHO_Patient_Safety_day2;
    }

    public void setLocal_image_WHO_Patient_Safety_day2(String local_image_WHO_Patient_Safety_day2) {
        Local_image_WHO_Patient_Safety_day2 = local_image_WHO_Patient_Safety_day2;
    }

    public String getLocal_image_WHO_Patient_Safety_day3() {
        return Local_image_WHO_Patient_Safety_day3;
    }

    public void setLocal_image_WHO_Patient_Safety_day3(String local_image_WHO_Patient_Safety_day3) {
        Local_image_WHO_Patient_Safety_day3 = local_image_WHO_Patient_Safety_day3;
    }

    public String getLocal_image_infection_control() {
        return Local_image_infection_control;
    }

    public void setLocal_image_infection_control(String local_image_infection_control) {
        Local_image_infection_control = local_image_infection_control;
    }

    public String getLocal_image_narcotic_drugs() {
        return Local_image_narcotic_drugs;
    }

    public void setLocal_image_narcotic_drugs(String local_image_narcotic_drugs) {
        Local_image_narcotic_drugs = local_image_narcotic_drugs;
    }

    public String getLocal_image_administration_disposal() {
        return Local_image_administration_disposal;
    }

    public void setLocal_image_administration_disposal(String local_image_administration_disposal) {
        Local_image_administration_disposal = local_image_administration_disposal;
    }

    private String Local_image_administration_disposal;


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

    public String getAnaesthetist_status() {
        return anaesthetist_status;
    }

    public void setAnaesthetist_status(String anaesthetist_status) {
        this.anaesthetist_status = anaesthetist_status;
    }

    public String getRemark_OT_anaesthetist() {
        return remark_OT_anaesthetist;
    }

    public void setRemark_OT_anaesthetist(String remark_OT_anaesthetist) {
        this.remark_OT_anaesthetist = remark_OT_anaesthetist;
    }

    public String getNc_OT_anaesthetist() {
        return nc_OT_anaesthetist;
    }

    public void setNc_OT_anaesthetist(String nc_OT_anaesthetist) {
        this.nc_OT_anaesthetist = nc_OT_anaesthetist;
    }

    public String getDocumented_anaesthesia_status() {
        return documented_anaesthesia_status;
    }

    public void setDocumented_anaesthesia_status(String documented_anaesthesia_status) {
        this.documented_anaesthesia_status = documented_anaesthesia_status;
    }

    public String getRemark_documented_anaesthesia() {
        return remark_documented_anaesthesia;
    }

    public void setRemark_documented_anaesthesia(String remark_documented_anaesthesia) {
        this.remark_documented_anaesthesia = remark_documented_anaesthesia;
    }

    public String getNc_documented_anaesthesia() {
        return nc_documented_anaesthesia;
    }

    public void setNc_documented_anaesthesia(String nc_documented_anaesthesia) {
        this.nc_documented_anaesthesia = nc_documented_anaesthesia;
    }

    public String getImmediate_preoperative_status() {
        return immediate_preoperative_status;
    }

    public void setImmediate_preoperative_status(String immediate_preoperative_status) {
        this.immediate_preoperative_status = immediate_preoperative_status;
    }

    public String getRemark_immediate_preoperative() {
        return remark_immediate_preoperative;
    }

    public void setRemark_immediate_preoperative(String remark_immediate_preoperative) {
        this.remark_immediate_preoperative = remark_immediate_preoperative;
    }

    public String getNc_immediate_preoperative() {
        return nc_immediate_preoperative;
    }

    public void setNc_immediate_preoperative(String nc_immediate_preoperative) {
        this.nc_immediate_preoperative = nc_immediate_preoperative;
    }

    public String getAnaesthesia_monitoring_status() {
        return anaesthesia_monitoring_status;
    }

    public void setAnaesthesia_monitoring_status(String anaesthesia_monitoring_status) {
        this.anaesthesia_monitoring_status = anaesthesia_monitoring_status;
    }

    public String getRemark_anaesthesia_monitoring() {
        return remark_anaesthesia_monitoring;
    }

    public void setRemark_anaesthesia_monitoring(String remark_anaesthesia_monitoring) {
        this.remark_anaesthesia_monitoring = remark_anaesthesia_monitoring;
    }

    public String getNc_anaesthesia_monitoring() {
        return nc_anaesthesia_monitoring;
    }

    public void setNc_anaesthesia_monitoring(String nc_anaesthesia_monitoring) {
        this.nc_anaesthesia_monitoring = nc_anaesthesia_monitoring;
    }

    public String getPost_anaesthesia_monitoring_status() {
        return post_anaesthesia_monitoring_status;
    }

    public void setPost_anaesthesia_monitoring_status(String post_anaesthesia_monitoring_status) {
        this.post_anaesthesia_monitoring_status = post_anaesthesia_monitoring_status;
    }

    public String getRemark_post_anaesthesia_monitoring() {
        return remark_post_anaesthesia_monitoring;
    }

    public void setRemark_post_anaesthesia_monitoring(String remark_post_anaesthesia_monitoring) {
        this.remark_post_anaesthesia_monitoring = remark_post_anaesthesia_monitoring;
    }

    public String getNc_post_anaesthesia_monitoring() {
        return nc_post_anaesthesia_monitoring;
    }

    public void setNc_post_anaesthesia_monitoring(String nc_post_anaesthesia_monitoring) {
        this.nc_post_anaesthesia_monitoring = nc_post_anaesthesia_monitoring;
    }

    public String getWHO_Patient_Safety_status() {
        return WHO_Patient_Safety_status;
    }

    public void setWHO_Patient_Safety_status(String WHO_Patient_Safety_status) {
        this.WHO_Patient_Safety_status = WHO_Patient_Safety_status;
    }

    public String getRemark_WHO_Patient_Safety() {
        return remark_WHO_Patient_Safety;
    }

    public void setRemark_WHO_Patient_Safety(String remark_WHO_Patient_Safety) {
        this.remark_WHO_Patient_Safety = remark_WHO_Patient_Safety;
    }

    public String getNc_WHO_Patient_Safety() {
        return nc_WHO_Patient_Safety;
    }

    public void setNc_WHO_Patient_Safety(String nc_WHO_Patient_Safety) {
        this.nc_WHO_Patient_Safety = nc_WHO_Patient_Safety;
    }

    public String getImage_WHO_Patient_Safety_day1() {
        return image_WHO_Patient_Safety_day1;
    }

    public void setImage_WHO_Patient_Safety_day1(String image_WHO_Patient_Safety_day1) {
        this.image_WHO_Patient_Safety_day1 = image_WHO_Patient_Safety_day1;
    }

    public String getImage_WHO_Patient_Safety_day2() {
        return image_WHO_Patient_Safety_day2;
    }

    public void setImage_WHO_Patient_Safety_day2(String image_WHO_Patient_Safety_day2) {
        this.image_WHO_Patient_Safety_day2 = image_WHO_Patient_Safety_day2;
    }

    public String getImage_WHO_Patient_Safety_day3() {
        return image_WHO_Patient_Safety_day3;
    }

    public void setImage_WHO_Patient_Safety_day3(String image_WHO_Patient_Safety_day3) {
        this.image_WHO_Patient_Safety_day3 = image_WHO_Patient_Safety_day3;
    }

    public String getOT_Zoning_status() {
        return OT_Zoning_status;
    }

    public void setOT_Zoning_status(String OT_Zoning_status) {
        this.OT_Zoning_status = OT_Zoning_status;
    }

    public String getRemark_OT_Zoning() {
        return remark_OT_Zoning;
    }

    public void setRemark_OT_Zoning(String remark_OT_Zoning) {
        this.remark_OT_Zoning = remark_OT_Zoning;
    }

    public String getNc_OT_Zoning() {
        return nc_OT_Zoning;
    }

    public void setNc_OT_Zoning(String nc_OT_Zoning) {
        this.nc_OT_Zoning = nc_OT_Zoning;
    }

    public String getInfection_control_status() {
        return infection_control_status;
    }

    public void setInfection_control_status(String infection_control_status) {
        this.infection_control_status = infection_control_status;
    }

    public String getRemark_infection_control() {
        return remark_infection_control;
    }

    public void setRemark_infection_control(String remark_infection_control) {
        this.remark_infection_control = remark_infection_control;
    }

    public String getNc_infection_control() {
        return nc_infection_control;
    }

    public void setNc_infection_control(String nc_infection_control) {
        this.nc_infection_control = nc_infection_control;
    }

    public String getImage_infection_control() {
        return image_infection_control;
    }

    public void setImage_infection_control(String image_infection_control) {
        this.image_infection_control = image_infection_control;
    }

    public String getNarcotic_drugs_status() {
        return narcotic_drugs_status;
    }

    public void setNarcotic_drugs_status(String narcotic_drugs_status) {
        this.narcotic_drugs_status = narcotic_drugs_status;
    }

    public String getRemark_narcotic_drugs() {
        return remark_narcotic_drugs;
    }

    public void setRemark_narcotic_drugs(String remark_narcotic_drugs) {
        this.remark_narcotic_drugs = remark_narcotic_drugs;
    }

    public String getNc_narcotic_drugs() {
        return nc_narcotic_drugs;
    }

    public void setNc_narcotic_drugs(String nc_narcotic_drugs) {
        this.nc_narcotic_drugs = nc_narcotic_drugs;
    }

    public String getImage_narcotic_drugs() {
        return image_narcotic_drugs;
    }

    public void setImage_narcotic_drugs(String image_narcotic_drugs) {
        this.image_narcotic_drugs = image_narcotic_drugs;
    }

    public String getAdministration_disposal_status() {
        return administration_disposal_status;
    }

    public void setAdministration_disposal_status(String administration_disposal_status) {
        this.administration_disposal_status = administration_disposal_status;
    }

    public String getRemark_administration_disposal() {
        return remark_administration_disposal;
    }

    public void setRemark_administration_disposal(String remark_administration_disposal) {
        this.remark_administration_disposal = remark_administration_disposal;
    }

    public String getNc_administration_disposal() {
        return nc_administration_disposal;
    }

    public void setNc_administration_disposal(String nc_administration_disposal) {
        this.nc_administration_disposal = nc_administration_disposal;
    }

    public String getImage_administration_disposal() {
        return image_administration_disposal;
    }

    public void setImage_administration_disposal(String image_administration_disposal) {
        this.image_administration_disposal = image_administration_disposal;
    }



}
