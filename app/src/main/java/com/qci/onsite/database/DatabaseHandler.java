package com.qci.onsite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qci.onsite.pojo.AmbulanceAccessibilityPojo;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.BioMedicalEngineeringPojo;
import com.qci.onsite.pojo.DocumentationPojo;
import com.qci.onsite.pojo.EmergencyPojo;
import com.qci.onsite.pojo.FacilityChecksPojo;
import com.qci.onsite.pojo.HRMPojo;
import com.qci.onsite.pojo.HighDependencyPojo;
import com.qci.onsite.pojo.HousekeepingPojo;
import com.qci.onsite.pojo.LaboratoryPojo;
import com.qci.onsite.pojo.MRDPojo;
import com.qci.onsite.pojo.ManagementPojo;
import com.qci.onsite.pojo.OBSTETRIC_WARD_Pojo;
import com.qci.onsite.pojo.OTPojo;
import com.qci.onsite.pojo.OT_ICU_Pojo;
import com.qci.onsite.pojo.Patient_Staff_InterviewPojo;
import com.qci.onsite.pojo.RadioLogyPojo;
import com.qci.onsite.pojo.SafetManagementPojo;
import com.qci.onsite.pojo.ScopeOfServicePojo;
import com.qci.onsite.pojo.SterilizationAreaPojo;
import com.qci.onsite.pojo.UniformSignagePojo;
import com.qci.onsite.pojo.Ward_Ot_EmergencyPojo;
import com.qci.onsite.pojo.Wards_PharmacyPojo;

import java.util.ArrayList;

/**
 * Created by Ankit on 21-01-2019.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;

    // Database Version
    private static final int DATABASE_VERSION = 23;

    // Database Name
    private static final String DATABASE_NAME = "OnSite_db";

    private static final String KEY_ID = "id";
    private static final String KEY_Local_id = "Local_id";
    private static final String KEY_HOSPITAL_NAME = "Hospital_name";
    private static final String KEY_HOSPITAL_ID = "Hospital_id";
    private static final String KEY_ASSESSOR_ID = "Assessor_id";


    // Assessment status table

    private static final String TABLE_ASSESSEMENT_STATUS = "Assessement_status";

    private static final String KEY_ASSESSEMENT_NAME = "Assessement_name";
    private static final String KEY_ASSESSEMENT_STATUS = "Assessement_status";


    // LABORATORY

    private static final String TABLE_LABORATORY = "Laboratory";
    private static final String LABORATORY_collected_properly = "LABORATORY_collected_properly";
    private static final String Collected_properly_remark = "Collected_properly_remark";
    private static final String Collected_properly_NC = "Collected_properly_nc";
    private static final String Collected_properly_video = "Collected_properly_video";
    private static final String Local_Collected_properly_video = "Local_Collected_properly_video";
    private static final String LABORATORY_identified_properly = "LABORATORY_identified_properly";
    private static final String Identified_properly_remark = "Identified_properly_remark";
    private static final String Identified_properly_NC = "Identified_properly_nc";
    private static final String Identified_properly_image = "Identified_properly_image";
    private static final String Local_Identified_properly_image = "Local_Identified_properly_image";
    private static final String LABORATORY_transported_safe_manner = "LABORATORY_transported_safe_manner";
    private static final String Transported_safe_manner_remark = "Transported_safe_manner_remark";
    private static final String Transported_safe_manner_NC = "Transported_safe_manner_nc";
    private static final String Transported_safe_manner_image = "Transported_safe_manner_image";
    private static final String Local_Transported_safe_manner_image = "Local_Transported_safe_manner_image";
    private static final String LABORATORY_Specimen_safe_manner = "LABORATORY_Specimen_safe_manner";
    private static final String Specimen_safe_manner_remark = "Specimen_safe_manner_remark";
    private static final String Specimen_safe_manner_nc = "Specimen_safe_manner_nc";
    private static final String Specimen_safe_image = "Specimen_safe_image";
    private static final String Local_Specimen_safe_image = "Local_Specimen_safe_image";
    private static final String LABORATORY_Appropriate_safety_equipment = "LABORATORY_Appropriate_safety_equipment";
    private static final String Appropriate_safety_equipment_remark = "Appropriate_safety_equipment_remark";
    private static final String Appropriate_safety_equipment_NC = "Appropriate_safety_equipment_nc";
    private static final String Appropriate_safety_equipment_image = "Appropriate_safety_equipment_image";
    private static final String Local_Appropriate_safety_equipment_image = "Local_Appropriate_safety_equipment_image";
    private static final String Laboratory_defined_turnaround = "laboratory_defined_turnaround";
    private static final String Laboratory_defined_turnaround_Remark = "laboratory_defined_turnaround_remark";
    private static final String Laboratory_defined_turnaround_Nc = "laboratory_defined_turnaround_nc";
    private static final String Laboratory_defined_turnaround_Image = "laboratory_defined_turnaround_image";
    private static final String Local_Laboratory_defined_turnaround_Image = "Local_laboratory_defined_turnaround_image";




    // Radiology

    private static final String TABLE_RADIOLOGY = "Radiology";
    private static final String RADIOLOGY_Appropriate_safety_equipment = "RADIOLOGY_Appropriate_safety_equipment";
    private static final String RADIOLOGY_Appropriate_safety_equipment_remark = "RADIOLOGY_Appropriate_safety_equipment_remark";
    private static final String RADIOLOGY_Appropriate_safety_equipment_NC = "RADIOLOGY_Appropriate_safety_equipment_NC";
    private static final String RADIOLOGY_Appropriate_safety_equipment_image = "RADIOLOGY_Appropriate_safety_equipment_image";
    private static final String Local_RADIOLOGY_Appropriate_safety_equipment_image = "Local_RADIOLOGY_Appropriate_safety_equipment_image";
    private static final String RADIOLOGY_defined_turnaround = "RADIOLOGY_defined_turnaround";
    private static final String RADIOLOGY_defined_turnaround_Remark = "RADIOLOGY_defined_turnaround_remark";
    private static final String RADIOLOGY_defined_turnaround_Nc = "RADIOLOGY_defined_turnaround_nc";
    private static final String RADIOLOGY_defined_turnaround_Image = "RADIOLOGY_defined_turnaround_image";
    private static final String Local_RADIOLOGY_defined_turnaround_Image = "Local_RADIOLOGY_defined_turnaround_image";

    // Emergency


    private static final String TABLE_EMERGENCY = "EMERGENCY";
    private static final String EMERGENCY_down_Norms = "EMERGENCY_down_Norms";
    private static final String EMERGENCY_down_Norms_remark = "EMERGENCY_down_Norms_remark";
    private static final String EMERGENCY_down_Norms_NC = "EMERGENCY_down_Norms_nc";
    private static final String EMERGENCY_down_Norms_video = "EMERGENCY_down_Norms_video";
    private static final String Local_EMERGENCY_down_Norms_video = "Local_EMERGENCY_down_Norms_video";

    // High dependency Areas

    private static final String TABLE_HIGH_DEPENDENCY = "HIGH_DEPENDENCY";
    private static final String HIGH_DEPENDENCY_documented_procedure = "HIGH_DEPENDENCY_documented_procedure";
    private static final String HIGH_DEPENDENCY_documented_procedure_remark = "HIGH_DEPENDENCY_documented_procedure_remark";
    private static final String HIGH_DEPENDENCY_documented_procedure_NC = "HIGH_DEPENDENCY_documented_procedure_nc";
    private static final String HIGH_DEPENDENCY_documented_procedure_video = "HIGH_DEPENDENCY_documented_procedure_video";
    private static final String Local_HIGH_DEPENDENCY_documented_procedure_video = "Local_HIGH_DEPENDENCY_documented_procedure_video";

    private static final String HIGH_DEPENDENCY_Areas_adequate = "HIGH_DEPENDENCY_Areas_adequate";
    private static final String HIGH_DEPENDENCY_Areas_adequate_remark = "HIGH_DEPENDENCY_Areas_adequate_remark";
    private static final String HIGH_DEPENDENCY_Areas_adequate_NC = "HIGH_DEPENDENCY_Areas_adequate_nc";
    private static final String HIGH_DEPENDENCY_Areas_adequate_image = "HIGH_DEPENDENCY_Areas_adequate_image";
    private static final String Local_HIGH_DEPENDENCY_Areas_adequate_image = "Local_HIGH_DEPENDENCY_Areas_adequate_image";

    private static final String HIGH_DEPENDENCY_adequate_equipment  = "HIGH_DEPENDENCY_adequate_equipment";
    private static final String HIGH_DEPENDENCY_adequate_equipment_remark = "HIGH_DEPENDENCY_adequate_equipment_remark";
    private static final String HIGH_DEPENDENCY_adequate_equipment_Nc = "HIGH_DEPENDENCY_adequate_equipment_nc";
    private static final String HIGH_DEPENDENCY_adequate_equipment_image = "HIGH_DEPENDENCY_adequate_equipment_image";
    private static final String Local_HIGH_DEPENDENCY_adequate_equipment_image = "Local_HIGH_DEPENDENCY_adequate_equipment_image";

    // Obstetric Ward NICU Paediatric

    private static final String TABLE_OBSTETRIC_WARD = "OBSTETRIC_WARD";
    private static final String OBSTETRIC_WARD_abuse = "OBSTETRIC_WARD_abuse";
    private static final String OBSTETRIC_WARD_abuse_remark = "EMERGENCY_down_Norms_remark";
    private static final String OBSTETRIC_WARD_abuse_NC = "EMERGENCY_down_Norms_nc";
    private static final String OBSTETRIC_WARD_abuse_image_identification = "OBSTETRIC_WARD_abuse_image_identification";
    private static final String Local_OBSTETRIC_WARD_abuse_image_identification = "Local_OBSTETRIC_WARD_abuse_image_identification";
    private static final String OBSTETRIC_WARD_abuse_image_surveillance = "OBSTETRIC_WARD_abuse_image_surveillance";
    private static final String Local_OBSTETRIC_WARD_abuse_image_surveillance = "Local_OBSTETRIC_WARD_abuse_image_surveillance";
    private static final String shco_obstetrics_ward = "shco_obstetrics_ward";
    private static final String shco_obstetrics_ward_remark = "shco_obstetrics_ward_remark";
    private static final String shco_obstetrics_ward_nc = "shco_obstetrics_ward_nc";
    private static final String shco_obstetrics_ward_image = "shco_obstetrics_ward_image";
    private static final String Local_shco_obstetrics_ward_image = "Local_shco_obstetrics_ward_image";

    // OT

    private static final String TABLE_OT = "OT";
    private static final String KEY_Anaesthetist_status = "anaesthetist_status";
    private static final String KEY_Remark_OT_anaesthetist = "remark_OT_anaesthetist";
    private static final String KEY_Nc_OT_anaesthetist = "nc_OT_anaesthetist";
    private static final String KEY_Documented_anaesthesia_status = "documented_anaesthesia_status";
    private static final String KEY_Remark_documented_anaesthesia = "remark_documented_anaesthesia";
    private static final String KEY_Nc_documented_anaesthesia = "nc_documented_anaesthesia";
    private static final String KEY_Immediate_preoperative_status = "immediate_preoperative_status";
    private static final String KEY_Remark_immediate_preoperative = "remark_immediate_preoperative";
    private static final String KEY_Nc_immediate_preoperative = "nc_immediate_preoperative";
    private static final String KEY_Anaesthesia_monitoring_status = "anaesthesia_monitoring_status";
    private static final String KEY_Remark_anaesthesia_monitoring = "remark_anaesthesia_monitoring";
    private static final String KEY_Nc_anaesthesia_monitoring = "nc_anaesthesia_monitoring";
    private static final String KEY_Post_anaesthesia_monitoring_status = "post_anaesthesia_monitoring_status";
    private static final String KEY_Remark_post_anaesthesia_monitoring = "remark_post_anaesthesia_monitoring";
    private static final String KEY_Nc_post_anaesthesia_monitoring = "nc_post_anaesthesia_monitoring";
    private static final String KEY_WHO_Patient_Safety_status = "WHO_Patient_Safety_status";
    private static final String KEY_Remark_WHO_Patient_Safety = "remark_WHO_Patient_Safety";
    private static final String KEY_Nc_WHO_Patient_Safety = "nc_WHO_Patient_Safety";
    private static final String KEY_Image_WHO_Patient_Safety_day1 = "image_WHO_Patient_Safety_day1";
    private static final String Local_KEY_Image_WHO_Patient_Safety_day1 = "Local_image_WHO_Patient_Safety_day1";
    private static final String KEY_Image_WHO_Patient_Safety_day2 = "image_WHO_Patient_Safety_day2";
    private static final String Local_KEY_Image_WHO_Patient_Safety_day2 = "Local_image_WHO_Patient_Safety_day2";
    private static final String KEY_Image_WHO_Patient_Safety_day3 = "image_WHO_Patient_Safety_day3";
    private static final String Local_KEY_Image_WHO_Patient_Safety_day3 = "Local_image_WHO_Patient_Safety_day3";
    private static final String KEY_OT_Zoning_status = "OT_Zoning_status";
    private static final String KEY_Remark_OT_Zoning = "remark_OT_Zoning";
    private static final String KEY_Nc_OT_Zoning = "nc_OT_Zoning";
    private static final String KEY_Video_OT_Zoning = "video_OT_Zoning";
    private static final String KEY_Local_video_OT_Zoning = "Local_video_OT_Zoning";
    private static final String KEY_Infection_control_status = "infection_control_status";
    private static final String KEY_Remark_infection_control = "remark_infection_control";
    private static final String KEY_Nc_infection_control = "nc_infection_control";
    private static final String KEY_Image_infection_control = "image_infection_control";
    private static final String Local_KEY_Image_infection_control = "Local_image_infection_control";
    private static final String KEY_Narcotic_drugs_status = "narcotic_drugs_status";
    private static final String KEY_Remark_narcotic_drugs = "remark_narcotic_drugs";
    private static final String KEY_Nc_narcotic_drugs = "nc_narcotic_drugs";
    private static final String KEY_Image_narcotic_drugs = "image_narcotic_drugs";
    private static final String Local_KEY_Image_narcotic_drugs = "Local_image_narcotic_drugs";
    private static final String KEY_Administration_disposal_status = "administration_disposal_status";
    private static final String KEY_Remark_administration_disposal = "remark_administration_disposal";
    private static final String KEY_Nc_administration_disposal = "nc_administration_disposal";
    private static final String KEY_Image_administration_disposal = "image_administration_disposal";
    private static final String Local_KEY_Image_administration_disposal = "Local_image_administration_disposal";
    private static final String Key_hand_wash_facility_status = "hand_wash_facility_status";
    private static final String Key_remark_hand_wash_facility = "remark_hand_wash_facility";
    private static final String Key_nc_hand_wash_facility = "nc_hand_wash_facility";
    private static final String Key_image_hand_wash_facility = "image_hand_wash_facility";
    private static final String Key_local_image_hand_wash_facility = "local_image_hand_wash_facility";

    //  Wards & Pharmacy

    private static final String TABLE_Ward_Pharmacy = "Ward_Pharmacy";
    private static final String Key_patient_care_area = "patient_care_area";
    private static final String Key_patient_care_area_Remark = "patient_care_area_Remark";
    private static final String Key_patient_care_area_NC = "patient_care_area_NC";
    private static final String Key_patient_care_area_Image = "patient_care_area_Image";
    private static final String Local_Key_patient_care_area_Image = "Local_patient_care_area_Image";
    private static final String Key_pharmacyStores_present = "pharmacyStores_present";
    private static final String Key_pharmacyStores_present_Remark = "pharmacyStores_present_remark";
    private static final String Key_pharmacyStores_present_Nc = "pharmacyStores_present_nc";
    private static final String Key_pharmacyStores_present_Image = "pharmacyStores_present_image";
    private static final String Local_Key_pharmacyStores_present_Image = "Local_pharmacyStores_present_image";
    private static final String Key_expired_drugs = "expired_drugs";
    private static final String Key_expired_drugs_Remark = "expired_drugs_remark";
    private static final String Key_expired_drugs_NC = "expired_drugs_nc";
    private static final String Key_expired_drugs_image = "expired_drugs_image";
    private static final String Local_Key_expired_drugs_image = "Local_expired_drugs_image";
    private static final String Key_expiry_date_checked = "expiry_date_checked";
    private static final String Key_expiry_date_checked_Remark = "expiry_date_checked_Remark";
    private static final String Key_expiry_date_checked_NC = "expiry_date_checked_NC";
    private static final String Key_emergency_medications = "emergency_medications";
    private static final String Key_emergency_medications_Remark = "emergency_medications_Remark";
    private static final String Key_emergency_medications_Nc = "emergency_medications_Nc";
    private static final String Key_emergency_medications_Image = "emergency_medications_Image";
    private static final String Local_Key_emergency_medications_Image = "Local_emergency_medications_Image";
    private static final String Key_risk_medications = "risk_medications";
    private static final String Key_risk_medications_Remark = "risk_medications_remark";
    private static final String Key_risk_medications_Nc = "risk_medications_nc";
    private static final String Key_risk_medications_Image = "risk_medications_Image";
    private static final String Local_Key_risk_medications_Image = "Local_risk_medications_Image";
    private static final String Key_medications_dispensing = "medications_dispensing";
    private static final String Key_medications_dispensing_Remark = "medications_dispensing_remark";
    private static final String Key_medications_dispensing_Nc = "medications_dispensing_nc";
    private static final String Key_labelling_of_drug = "labelling_of_drug";
    private static final String Key_labelling_of_drug_Remark = "labelling_of_drug_remark";
    private static final String Key_labelling_of_drug_NC = "labelling_of_drug_nc";
    private static final String Key_labelling_of_drug_Image = "labelling_of_drug_image";
    private static final String Local_Key_labelling_of_drug_Image = "Local_labelling_of_drug_image";
    private static final String Key_medication_order_checked = "medication_order_checked";
    private static final String Key_medication_order_checked_Remark = "medication_order_checked_remark";
    private static final String Key_medication_order_checked_Nc = "medication_order_checked_nc";
    private static final String Key_medication_order_checked_Image = "medication_order_checked_image";
    private static final String Local_Key_medication_order_checked_Image = "Local_medication_order_checked_image";
    private static final String Key_medication_administration_documented = "medication_administration_documented";
    private static final String Key_medication_administration_documented_Remark = "medication_administration_documented_remark";
    private static final String Key_medication_administration_documented_Nc = "medication_administration_documented_nc";
    private static final String Key_medication_administration_documented_Image = "medication_administration_documented_image";
    private static final String Local_Key_medication_administration_documented_Image = "Local_medication_administration_documented_image";
    private static final String Key_fridge_temperature_record = "fridge_temperature_record";
    private static final String Key_fridge_temperature_record_Remark = "fridge_temperature_record_remark";
    private static final String Key_fridge_temperature_record_Nc = "fridge_temperature_record_nc";
    private static final String Key_fridge_temperature_record_Image = "fridge_temperature_record_Image";
    private static final String Local_Key_fridge_temperature_record_Image = "Local_fridge_temperature_record_Image";

    // Patient/Staff Interview

    private static final String TABLE_Patient_Staff_Interview = "Patient_Staff_Interview";
    private static final String Key_privacy_maintained = "privacy_maintained";
    private static final String Key_privacy_maintained_Remark = "privacy_maintained_remark";
    private static final String Key_privacy_maintained_Nc = "privacy_maintained_nc";
    private static final String Key_privacy_maintained_Image = "privacy_maintained_image";
    private static final String Local_Key_privacy_maintained_Image = "Local_privacy_maintained_image";
    private static final String Key_Patients_protected_physical_abuse = "patients_protected_physical_abuse";
    private static final String Key_Patients_protected_physical_Remark = "patients_protected_physical_abuse_remark";
    private static final String Key_Patients_protected_physical_abuse_Nc = "patients_protected_physical_abuse_nc";
    private static final String Key_patient_information_confidential = "patient_information_confidential";
    private static final String Key_patient_information_confidential_Remark = "patient_information_confidential_remark";
    private static final String Key_patient_information_confidential_Nc = "patient_information_confidential_nc";
    private static final String Key_patients_consent_carrying = "patients_consent_carrying";
    private static final String Key_patients_consent_carrying_Remark = "patients_consent_carrying_remark";
    private static final String Key_patients_consent_carrying_Nc = "patients_consent_carrying_nc";
    private static final String Key_patient_voice_complaint = "patient_voice_complaint";
    private static final String Key_patient_voice_complaint_Remark = "patient_voice_complaint_remark";
    private static final String Key_patient_voice_complaint_Nc = "patient_voice_complaint_nc";
    private static final String Key_patients_cost_treatment = "patients_cost_treatment";
    private static final String Key_patients_cost_treatment_Remark = "patients_cost_treatment_remark";
    private static final String Key_patients_cost_treatment_Nc = "patients_cost_treatment_nc";
    private static final String Key_patient_clinical_records = "patient_clinical_records";
    private static final String Key_patient_clinical_records_Remark = "patient_clinical_records_remark";
    private static final String Key_patient_clinical_records_Nc = "patient_clinical_records_nc";
    private static final String Key_patient_aware_plan_care = "patient_aware_plan_care";
    private static final String Key_patient_aware_plan_care_Remark = "patient_aware_plan_care_remark";
    private static final String Key_patient_aware_plan_care_Nc = "patient_aware_plan_care_nc";
    private static final String Key_patient_aware_plan_care_Video = "patient_aware_plan_care_video";
    private static final String Local_Key_patient_aware_plan_care_Video = "Local_patient_aware_plan_care_video";
    private static final String Key_case_of_grievances = "case_of_grievances";
    private static final String Key_case_of_grievances_Remark = "case_of_grievances_remark";
    private static final String Key_case_of_grievances_Nc = "case_of_grievances_nc";
    private static final String Key_case_of_grievances_Image = "case_of_grievances_image";
    private static final String Local_Key_case_of_grievances_Image = "Local_case_of_grievances_image";
    private static final String Key_staff_disciplinary_procedure = "staff_disciplinary_procedure";
    private static final String Key_staff_disciplinary_procedure_Remark = "staff_disciplinary_procedure_remark";
    private static final String Key_staff_disciplinary_procedure_Nc = "staff_disciplinary_procedure_nc";
    private static final String Key_staff_disciplinary_procedure_Video = "staff_disciplinary_procedure_video";
    private static final String Local_Key_staff_disciplinary_procedure_Video = "Local_staff_disciplinary_procedure_video";
    private static final String Key_staff_able_to_demonstrate = "staff_able_to_demonstrate";
    private static final String Key_staff_able_to_demonstrate_Remark = "staff_able_to_demonstrate_remark";
    private static final String Key_staff_able_to_demonstrate_Nc = "staff_able_to_demonstrate_nc";

    // Wards, OT, ICU, OPD, Emergency

    private static final String Table_Ward_Ot_Emergency = "Ward_Ot_Emergency";
    private static final String Key_hospital_maintain_cleanliness = "hospital_maintain_cleanliness";
    private static final String Key_hospital_maintain_cleanliness_Remark = "hospital_maintain_cleanliness_remark";
    private static final String Key_hospital_maintain_cleanliness_Nc = "hospital_maintain_cleanliness_nc";
    private static final String Key_hospital_adhere_standard = "hospital_adhere_standard";
    private static final String Key_hospital_adhere_standard_Remark = "hospital_adhere_standard_remark";
    private static final String Key_hospital_adhere_standard_Nc = "hospital_adhere_standard_nc";
    private static final String Key_hospital_provide_adequate_gloves = "hospital_provide_adequate_gloves";
    private static final String Key_hospital_provide_adequate_gloves_Remark = "hospital_provide_adequate_gloves_remark";
    private static final String Key_hospital_provide_adequate_gloves_Nc = "hospital_provide_adequate_gloves_nc";
    private static final String Key_admissions_discharge_home = "admissions_discharge_home";
    private static final String Key_admissions_discharge_home_Remark = "admissions_discharge_home_remark";
    private static final String Key_admissions_discharge_home_Nc = "admissions_discharge_home_nc";
    private static final String Key_admissions_discharge_home_Video = "admissions_discharge_home_video";
    private static final String Local_Key_admissions_discharge_home_Video = "Local_admissions_discharge_home_video";
    private static final String Key_staff_present_in_ICU = "staff_present_in_ICU";
    private static final String Key_staff_present_in_Remark = "staff_present_in_ICU_remark";
    private static final String Key_staff_present_in_ICU_Nc = "staff_present_in_ICU_nc";

    // HRM

    private static final String Table_HRM = "HRM";
    private static final String Key_staff_health_related_issues = "staff_health_related_issues";
    private static final String Key_staff_health_related_issues_Remark = "staff_health_related_issues_remark";
    private static final String Key_staff_health_related_issues_Nc = "staff_health_related_issues_nc";
    private static final String Key_staffs_personal_files_maintained = "staffs_personal_files_maintained";
    private static final String Key_staffs_personal_files_maintained_Remark = "staffs_personal_files_maintained_remark";
    private static final String Key_staffs_personal_files_maintained_Nc = "staffs_personal_files_maintained_nc";
    private static final String Key_staffs_personal_files_maintained_image = "staffs_personal_files_maintained_image";
    private static final String Local_Key_staffs_personal_files_maintained_image = "Local_staffs_personal_files_maintained_image";
    private static final String Key_occupational_health_hazards = "occupational_health_hazards";
    private static final String Key_occupational_health_hazards_Remark = "occupational_health_hazards_remark";
    private static final String Key_occupational_health_hazards_Nc = "occupational_health_hazards_nc";
    private static final String Key_training_responsibility_changes = "training_responsibility_changes";
    private static final String Key_training_responsibility_changes_Remark = "training_responsibility_changes_remark";
    private static final String Key_training_responsibility_changes_Nc = "training_responsibility_changes_nc";
    private static final String Key_medical_records_doctors_retrievable = "medical_records_doctors_retrievable";
    private static final String Key_medical_records_doctors_retrievable_Remark = "medical_records_doctors_retrievable_remark";
    private static final String Key_medical_records_doctors_retrievable_Nc = "medical_records_doctors_retrievable_nc";

    // MRD

    private static final String Table_MRD = "MRD";
    private static final String Key_medical_record_death_certificate = "medical_record_death_certificate";
    private static final String Key_medical_record_death_certificate_Remark = "medical_record_death_certificate_remark";
    private static final String Key_medical_record_death_certificate_Nc = "medical_record_death_certificate_nc";
    private static final String Key_documented_maintaining_confidentiality = "documented_maintaining_confidentiality";
    private static final String Key_documented_maintaining_confidentiality_Remark = "documented_maintaining_confidentiality_remark";
    private static final String Key_documented_maintaining_confidentiality_Nc = "documented_maintaining_confidentiality_nc";
    private static final String Key_any_information_disclosed = "any_information_disclosed";
    private static final String Key_any_information_disclosed_Remark = "any_information_disclosed_remark";
    private static final String Key_any_information_disclosed_Nc = "any_information_disclosed_nc";
    private static final String Key_destruction_medical_records = "destruction_medical_records";
    private static final String Key_destruction_medical_records_Remark = "destruction_medical_records_remark";
    private static final String Key_destruction_medical_records_Nc = "destruction_medical_records_nc";
    private static final String Key_fire_extinguisher_present = "fire_extinguisher_present";
    private static final String Key_fire_extinguisher_present_Remark = "fire_extinguisher_present_remark";
    private static final String Key_fire_extinguisher_present_Nc = "fire_extinguisher_present_nc";
    private static final String Key_fire_extinguisher_present_Image = "fire_extinguisher_present_image";
    private static final String Local_Key_fire_extinguisher_present_Image = "Local_fire_extinguisher_present_image";


  // Housekeeping

    private static final String Table_Housekeeping = "Housekeeping";
    private static final String Key_infected_patient_room = "infected_patient_room";
    private static final String Key_infected_patient_room_Remark = "infected_patient_room_remark";
    private static final String Key_infected_patient_room_Nc = "infected_patient_room_nc";
    private static final String Key_procedure_cleaning_room = "procedure_cleaning_room";
    private static final String Key_procedure_cleaning_room_Remark = "procedure_cleaning_room_remark";
    private static final String Key_procedure_cleaning_room_Nc = "procedure_cleaning_room_nc";
    private static final String Key_procedure_cleaning_blood_spill = "procedure_cleaning_blood_spill";
    private static final String Key_procedure_cleaning_blood_spill_Remark = "procedure_cleaning_blood_spill_remark";
    private static final String Key_procedure_cleaning_blood_spill_Nc = "procedure_cleaning_blood_spill_nc";
    private static final String Key_procedure_cleaning_blood_spill_Video = "procedure_cleaning_blood_spill_video";
    private static final String Local_Key_procedure_cleaning_blood_spill_Video = "Local_procedure_cleaning_blood_spill_video";
    private static final String Key_Biomedical_Waste_regulations = "Biomedical_Waste_regulations";
    private static final String Key_Biomedical_Waste_regulations_Remark = "Biomedical_Waste_regulations_remark";
    private static final String Key_Biomedical_Waste_regulations_Nc = "Biomedical_Waste_regulations_nc";
    private static final String Key_Biomedical_Waste_regulations_Image = "Biomedical_Waste_regulations_image";
    private static final String Local_Key_Biomedical_Waste_regulations_Image = "Local_Biomedical_Waste_regulations_image";
    private static final String Key_cleaning_washing_blood_stained = "cleaning_washing_blood_stained";
    private static final String Key_cleaning_washing_blood_stained_Remark = "cleaning_washing_blood_stained_remark";
    private static final String Key_cleaning_washing_blood_stained_Nc = "cleaning_washing_blood_stained_nc";

    // Sterilization Area

    private static final String Table_Sterilization_Area = "Sterilization_Area";
    private static final String Key_sterilisation_practices_adhered = "sterilisation_practices_adhered";
    private static final String Key_sterilisation_practices_adhered_Remark = "sterilisation_practices_adhered_remark";
    private static final String Key_sterilisation_practices_adhered_Nc = "sterilisation_practices_adhered_nc";
    private static final String Key_monitor_effectiveness_sterilization_process = "monitor_effectiveness_sterilization_process";
    private static final String Key_monitor_effectiveness_sterilization_process_Remark = "monitor_effectiveness_sterilization_process_remark";
    private static final String Key_monitor_effectiveness_sterilization_process_Nc = "monitor_effectiveness_sterilization_process_nc";
    private static final String Key_monitor_effectiveness_sterilization_process_Image = "monitor_effectiveness_sterilization_process_image";
    private static final String Local_Key_monitor_effectiveness_sterilization_process_Image = "local_monitor_effectiveness_sterilization_process_image";
    private static final String Key_sterilized_drums_trays = "sterilized_drums_trays";
    private static final String Key_sterilized_drums_trays_Remark = "sterilized_drums_trays_remark";
    private static final String Key_sterilized_drums_trays_Nc = "sterilized_drums_trays_nc";
    private static final String Key_sterilized_drums_trays_Image = "sterilized_drums_trays_image";
    private static final String Local_Key_sterilized_drums_trays_Image = "local_sterilized_drums_trays_image";


    // Management
    private static final String Table_Management = "Management";
    private static final String Key_requisite_fee_BMW = "requisite_fee_BMW";
    private static final String Key_requisite_fee_BMW_Remark = "requisite_fee_BMW_remark";
    private static final String Key_requisite_fee_BMW_Nc = "requisite_fee_BMW_nc";
    private static final String Key_management_guide_organization = "management_guide_organization";
    private static final String Key_management_guide_organization_Remark = "management_guide_organization_remark";
    private static final String Key_management_guide_organization_Nc = "management_guide_organization_nc";
    private static final String Key_hospital_mission_present = "hospital_mission_present";
    private static final String Key_hospital_mission_present_Remark = "hospital_mission_present_remark";
    private static final String Key_hospital_mission_present_Nc = "hospital_mission_present_nc";
    private static final String Key_hospital_mission_present_Image = "hospital_mission_present_Image";
    private static final String Local_Key_hospital_mission_present_Image = "local_hospital_mission_present_Image";
    private static final String Key_patient_maintained_OPD = "patient_maintained_OPD";
    private static final String Key_patient_maintained_OPD_Remark = "patient_maintained_OPD_remark";
    private static final String Key_patient_maintained_OPD_Nc = "patient_maintained_OPD_nc";
    private static final String Key_patient_maintained_OPD_Image = "patient_maintained_OPD_image";
    private static final String Local_Key_patient_maintained_OPD_Image = "local_patient_maintained_OPD_image";
    private static final String Key_patient_maintained_IPD = "patient_maintained_IPD";
    private static final String Key_patient_maintained_IPD_Remark = "patient_maintained_IPD_remark";
    private static final String Key_patient_maintained_IPD_Nc = "patient_maintained_IPD_nc";
    private static final String Key_patient_maintained_IPD_Image = "patient_maintained_IPD_image";
    private static final String Local_Key_patient_maintained_IPD_Image = "local_patient_maintained_IPD_image";
    private static final String Key_patient_maintained_Emergency = "patient_maintained_Emergency";
    private static final String Key_patient_maintained_Emergency_Remark = "patient_maintained_Emergency_remark";
    private static final String Key_patient_maintained_Emergency_Nc = "patient_maintained_Emergency_nc";
    private static final String Key_patient_maintained_Emergency_Image = "patient_maintained_Emergency_image";
    private static final String Local_Key_patient_maintained_Emergency_Image = "local_patient_maintained_Emergency_image";
    private static final String Key_basic_Tariff_List = "basic_Tariff_List";
    private static final String Key_basic_Tariff_List_Remark = "basic_Tariff_List_remark";
    private static final String Key_basic_Tariff_List_Nc = "basic_Tariff_List_nc";
    private static final String Key_basic_Tariff_List_Image = "basic_Tariff_List_image";
    private static final String Local_Key_basic_Tariff_List_Image = "local_basic_Tariff_List_image";
    private static final String Key_parameter_patient_identification = "parameter_patient_identification";
    private static final String Key_parameter_patient_identification_Remark = "parameter_patient_identification_remark";
    private static final String Key_parameter_patient_identification_Nc = "parameter_patient_identification_nc";
    private static final String key_quality_improvement_programme = "quality_improvement_programme";
    private static final String key_quality_improvement_programme_Remark = "quality_improvement_programme_remark";
    private static final String key_quality_improvement_programme_Nc = "quality_improvement_programme_nc";

    // Maintenance/Bio-medical engineering

    private static final String Table_Bio_medical_engineering = "Bio_medical_engineering";
    private static final String Key_Maintenance_staff_contactable = "Maintenance_staff_contactable";
    private static final String Key_Maintenance_staff_contactable_Remark = "Maintenance_staff_contactable_remark";
    private static final String Key_Maintenance_staff_contactable_Nc = "Maintenance_staff_contactable_nc";
    private static final String key_equipment_accordance_services = "equipment_accordance_services";
    private static final String key_equipment_accordance_services_Remark = "equipment_accordance_services_remark";
    private static final String key_equipment_accordance_services_Nc = "equipment_accordance_services_nc";
    private static final String Key_documented_operational_maintenance = "documented_operational_maintenance";
    private static final String Key_documented_operational_maintenance_Remark = "documented_operational_maintenance_remark";
    private static final String Key_documented_operational_maintenance_Nc = "documented_operational_maintenance_nc";
    private static final String Key_documented_operational_maintenance_Image = "documented_operational_maintenance_image";
    private static final String Local_Key_documented_operational_maintenance_Image = "local_documented_operational_maintenance_image";

    // Maintenance/Facility Checks

    private static final String Table_Facility_Checks = "Facility_Checks";
    private static final String Key_medical_gas_cylinders = "medical_gas_cylinders";
    private static final String Key_medical_gas_cylinders_Remark = "medical_gas_cylinders_remark";
    private static final String Key_medical_gas_cylinders_Nc = "medical_gas_cylinders_nc";
    private static final String Key_medical_gas_cylinders_Image = "medical_gas_cylinders_image";
    private static final String Local_Key_medical_gas_cylinders_Image = "local_medical_gas_cylinders_image";
    private static final String Key_smoke_detectors_installed = "smoke_detectors_installed";
    private static final String Key_smoke_detectors_installed_Remark = "smoke_detectors_installed_remark";
    private static final String Key_smoke_detectors_installed_Nc = "smoke_detectors_installed_nc";
    private static final String Key_smoke_detectors_installed_Image = "smoke_detectors_installed_image";
    private static final String Local_Key_smoke_detectors_installed_Image = "local_smoke_detectors_installed_image";
    private static final String Key_extinguisher_present_patient = "extinguisher_present_patient";
    private static final String Key_extinguisher_present_patient_Remark = "extinguisher_present_patient_remark";
    private static final String Key_extinguisher_present_patient_Nc = "extinguisher_present_patient_nc";
    private static final String Key_extinguisher_present_patient_Image = "extinguisher_present_patient_image";
    private static final String Local_Key_extinguisher_present_patient_Image = "local_extinguisher_present_patient_image";
    private static final String Key_fire_fighting_equipment = "fire_fighting_equipment";
    private static final String Key_fire_fighting_equipment_Remark = "fire_fighting_equipment_remark";
    private static final String Key_fire_fighting_equipment_Nc = "fire_fighting_equipment_nc";
    private static final String Key_safe_exit_plan = "safe_exit_plan";
    private static final String Key_safe_exit_plan_Remark = "safe_exit_plan_remark";
    private static final String Key_safe_exit_plan_Nc = "safe_exit_plan_nc";

    // Safety Management

    private static final String Table_Safety_Management = "Safety_Management";
    private static final String Key_safety_device_lab = "safety_device_lab";
    private static final String Key_safety_device_lab_Remark = "safety_device_lab_remark";
    private static final String Key_safety_device_lab_Nc = "safety_device_lab_nc";
    private static final String Key_safety_device_lab_Image = "safety_device_lab_image";
    private static final String Local_Key_safety_device_lab_Image = "local_safety_device_lab_image";
    private static final String Key_body_parts_staff_patients = "body_parts_staff_patients";
    private static final String Key_body_parts_staff_patients_Remark = "body_parts_staff_patients_remark";
    private static final String Key_body_parts_staff_patients_Nc = "body_parts_staff_patients_nc";
    private static final String Key_body_parts_staff_patients_Image = "body_parts_staff_patients_image";
    private static final String Local_Key_body_parts_staff_patients_Image = "local_body_parts_staff_patients_image";
    private static final String Key_staff_member_radiation_area = "staff_member_radiation_area";
    private static final String Key_staff_member_radiation_area_Remark = "staff_member_radiation_area_remark";
    private static final String Key_staff_member_radiation_area_Nc = "staff_member_radiation_area_nc";
    private static final String Key_staff_member_radiation_area_Image = "staff_member_radiation_area_image";
    private static final String Local_Key_staff_member_radiation_area_Image = "local_staff_member_radiation_area_image";
    private static final String Key_standardised_colur_coding = "standardised_colur_coding";
    private static final String Key_standardised_colur_coding_Remark = "standardised_colur_coding_remark";
    private static final String Key_standardised_colur_coding_Nc = "standardised_colur_coding_nc";
    private static final String Key_standardised_colur_coding_Image = "standardised_colur_coding_image";
    private static final String Local_Key_standardised_colur_coding_Image = "local_standardised_colur_coding_image";
    private static final String Key_safe_storage_medical = "safe_storage_medical";
    private static final String Key_safe_storage_medical_Remark = "safe_storage_medical_remark";
    private static final String Key_safe_storage_medical_Nc = "safe_storage_medical_nc";
    private static final String Key_safe_storage_medical_Image = "safe_storage_medical_image";
    private static final String Local_Key_safe_storage_medical_Image = "local_safe_storage_medical_image";

    // Ambulance Accessibility

    private static final String Table_Ambulance_Accessibility = "Ambulance_Accessibility";
    private static final String Key_access_ambulance_patient_drop = "access_ambulance_patient_drop";
    private static final String Key_access_ambulance_patient_drop_remark = "access_ambulance_patient_drop_remark";
    private static final String Key_access_ambulance_patient_drop_nc = "access_ambulance_patient_drop_nc";
    private static final String Key_ownership_the_ambulance = "ownership_the_ambulance";
    private static final String Key_ownership_the_ambulance_remark = "ownership_the_ambulance_remark";
    private static final String Key_ownership_the_ambulance_Nc = "ownership_the_ambulance_Nc";
    private static final String Key_total_number_ambulance_available = "total_number_ambulance_available";
    private static final String Key_total_number_ambulance_available_Remark = "total_number_ambulance_available_remark";
    private static final String Key_total_number_ambulance_available_Nc = "total_number_ambulance_available_nc";
    private static final String Key_total_number_ambulance_available_Image = "total_number_ambulance_available_image";
    private static final String Local_Key_total_number_ambulance_available_Image = "local_total_number_ambulance_available_image";
    private static final String Key_ambulance_appropriately_equiped = "ambulance_appropriately_equiped";
    private static final String Key_ambulance_appropriately_equiped_Remark = "ambulance_appropriately_equiped_remark";
    private static final String Key_ambulance_appropriately_equiped_Nc = "ambulance_appropriately_equiped_nc";
    private static final String Key_ambulance_appropriately_equiped_Image = "ambulance_appropriately_equiped_image";
    private static final String Local_Key_ambulance_appropriately_equiped_Image = "local_ambulance_appropriately_equiped_image";
    private static final String Key_drivers_ambulances_available = "drivers_ambulances_available";
    private static final String Key_drivers_ambulances_available_Remark = "drivers_ambulances_available_remark";
    private static final String Key_drivers_ambulances_available_Nc = "drivers_ambulances_available_nc";
    private static final String Key_drivers_ambulances_available_Image = "drivers_ambulances_available_image";
    private static final String Local_Key_drivers_ambulances_available_Image = "local_drivers_ambulances_available_image";
    private static final String Key_doctors_available_ambulances = "doctors_available_ambulances";
    private static final String Key_doctors_available_ambulances_Remark = "doctors_available_ambulances_remark";
    private static final String Key_doctors_available_ambulances_Nc = "doctors_available_ambulances_nc";
    private static final String Key_doctors_available_ambulances_Image = "doctors_available_ambulances_image";
    private static final String Local_Key_doctors_available_ambulances_Image = "local_doctors_available_ambulances_image";
    private static final String Key_nurses_available_ambulances = "nurses_available_ambulances";
    private static final String Key_nurses_available_ambulances_Remark = "nurses_available_ambulances_remark";
    private static final String Key_nurses_available_ambulances_Nc = "nurses_available_ambulances_nc";
    private static final String Key_nurses_available_ambulances_Image = "nurses_available_ambulances_image";
    private static final String Local_Key_nurses_available_ambulances_Image = "local_nurses_available_ambulances_image";


    // Uniform Signage
    private static final String Table_Uniform_Signage = "Uniform_Signage";
    private static final String Key_scope_services_present = "scope_services_present";
    private static final String Key_scope_services_present_Remark = "scope_services_present_remark";
    private static final String Key_scope_services_present_Nc = "scope_services_present_nc";
    private static final String Key_scope_services_present_Image = "scope_services_present_image";
    private static final String Local_Key_scope_services_present_Image = "local_scope_services_present_image";
    private static final String Key_Patients_responsibility_displayed = "Patients_responsibility_displayed";
    private static final String Key_Patients_responsibility_displayed_Remark = "Patients_responsibility_displayed_remark";
    private static final String Key_Patients_responsibility_displayed_Nc = "Patients_responsibility_displayed_nc";
    private static final String Key_Patients_responsibility_displayed_Image = "Patients_responsibility_displayed_image";
    private static final String Local_Key_Patients_responsibility_displayed_Image = "local_Patients_responsibility_displayed_image";
    private static final String Key_fire_exit_signage_present = "fire_exit_signage_present";
    private static final String Key_fire_exit_signage_present_Remark = "fire_exit_signage_present_remark";
    private static final String Key_fire_exit_signage_present_Nc = "fire_exit_signage_present_nc";
    private static final String Key_fire_exit_signage_present_Image = "fire_exit_signage_present_image";
    private static final String Local_Key_fire_exit_signage_present_Image = "local_fire_exit_signage_present_image";
    private static final String Key_directional_signages_present = "directional_signages_present";
    private static final String Key_directional_signages_present_Remark = "directional_signages_present_remark";
    private static final String Key_directional_signages_present_Nc = "directional_signages_present_Nc";
    private static final String Key_directional_signages_present_Image = "directional_signages_present_image";
    private static final String Local_Key_directional_signages_present_Image = "local_directional_signages_present_image";
    private static final String Key_departmental_signages_present = "departmental_signages_present";
    private static final String Key_departmental_signages_present_Remark = "departmental_signages_present_remark";
    private static final String Key_departmental_signages_present_Nc = "departmental_signages_present_nc";
    private static final String Key_departmental_signages_present_Image = "departmental_signages_present_image";
    private static final String Local_Key_departmental_signages_present_Image = "local_departmental_signages_present_image";

    // Documentation

    private static final String Table_Documentation = "Documentation";
    private static final String Key_document_related_procedure = "document_related_procedure";
    private static final String Key_document_related_procedure_remark = "document_related_procedure_remark";
    private static final String Key_document_related_procedure_nc = "document_related_procedure_nc";

    private static final String Key_document_showing_process = "document_showing_process";
    private static final String Key_document_showing_process_remark = "document_showing_process_remark";
    private static final String Key_document_showing_process_nc = "document_showing_process_nc";

    private static final String Key_document_showing_care_patients = "document_showing_care_patients";
    private static final String Key_document_showing_care_patients_remark = "document_showing_care_patients_remark";
    private static final String Key_document_showing_care_patients_nc = "document_showing_care_patients_nc";

    private static final String Key_document_showing_policies = "document_showing_policies";
    private static final String Key_document_showing_policies_remark = "document_showing_policies_remark";
    private static final String Key_document_showing_policies_nc = "document_showing_policies_nc";

    private static final String Key_document_showing_procedures = "document_showing_procedures";
    private static final String Key_document_showing_procedures_remark = "document_showing_procedures_remark";
    private static final String Key_document_showing_procedures_nc = "document_showing_procedures_nc";

    private static final String Key_document_showing_procedure_administration = "document_showing_procedure_administration";
    private static final String Key_document_showing_procedure_administration_remark = "document_showing_procedure_administration_remark";
    private static final String Key_document_showing_procedure_administration_nc = "document_showing_procedure_administration_nc";

    private static final String Key_document_showing_defined_criteria = "document_showing_defined_criteria";
    private static final String Key_document_showing_defined_criteria_remark = "document_showing_defined_criteria_remark";
    private static final String Key_document_showing_defined_criteria_nc = "document_showing_defined_criteria_nc";

    private static final String Key_document_showing_procedure_prevention = "document_showing_procedure_prevention";
    private static final String Key_document_showing_procedure_prevention_remark = "document_showing_procedure_prevention_remark";
    private static final String Key_document_showing_procedure_prevention_nc = "document_showing_procedure_prevention_nc";

    private static final String Key_document_showing_procedure_incorporating = "document_showing_procedure_incorporating";
    private static final String Key_document_showing_procedure_incorporating_remark = "document_showing_procedure_incorporating_remark";
    private static final String Key_document_showing_procedure_incorporating_nc = "document_showing_procedure_incorporating_nc";

    private static final String Key_document_showing_procedure_address = "document_showing_procedure_address";
    private static final String Key_document_showing_procedure_address_remark = "document_showing_procedure_address_remark";
    private static final String Key_document_showing_procedure_address_nc = "document_showing_procedure_address_nc";

    private static final String Key_document_showing_policies_procedure = "document_showing_policies_procedure";
    private static final String Key_document_showing_policies_procedure_remark = "document_showing_policies_procedure_remark";
    private static final String Key_document_showing_policies_procedure_nc = "document_showing_policies_procedure_nc";

    private static final String Key_document_showing_drugs_available = "document_showing_drugs_available";
    private static final String Key_document_showing_drugs_available_remark = "document_showing_drugs_available_remark";
    private static final String Key_document_showing_drugs_available_nc = "document_showing_drugs_available_nc";

    private static final String key_document_showing_safe_storage = "document_showing_safe_storage";
    private static final String key_document_showing_safe_storage_remark = "document_showing_safe_storage_remark";
    private static final String key_document_showing_safe_storage_nc = "document_showing_safe_storage_nc";

    private static final String Key_Infection_control_manual_showing = "Infection_control_manual_showing";
    private static final String Key_Infection_control_manual_showing_remark = "Infection_control_manual_showing_remark";
    private static final String Key_Infection_control_manual_showing_nc = "Infection_control_manual_showing_nc";

    private static final String Key_document_showing_operational_maintenance = "document_showing_operational_maintenance";
    private static final String Key_document_showing_operational_maintenance_remark = "document_showing_operational_maintenance_remark";
    private static final String Key_document_showing_operational_maintenance_nc = "document_showing_operational_maintenance_nc";

    private static final String Key_document_showing_safe_exit_plan = "document_showing_safe_exit_plan";
    private static final String Key_document_showing_safe_exit_plan_remark = "document_showing_safe_exit_plan_remark";
    private static final String Key_document_showing_safe_exit_plan_nc = "document_showing_safe_exit_plan_nc";

    private static final String Key_document_showing_well_defined_staff = "document_showing_well_defined_staff";
    private static final String Key_document_showing_well_defined_staff_remark = "document_showing_well_defined_staff_remark";
    private static final String Key_document_showing_well_defined_staff_nc = "document_showing_well_defined_staff_nc";

    private static final String Key_document_showing_disciplinary_grievance = "document_showing_disciplinary_grievance";
    private static final String Key_document_showing_disciplinary_grievance_remark = "document_showing_disciplinary_grievance_remark";
    private static final String Key_document_showing_disciplinary_grievance_nc = "document_showing_disciplinary_grievance_nc";

    private static final String Key_document_showing_policies_procedures = "document_showing_policies_procedures";
    private static final String Key_document_showing_policies_procedures_remark = "document_showing_policies_procedures_remark";
    private static final String Key_document_showing_policies_procedures_nc = "document_showing_policies_procedures_nc";

    private static final String Key_document_showing_retention_time = "document_showing_retention_time";
    private static final String Key_document_showing_retention_time_remark = "document_showing_retention_time_remark";
    private static final String Key_document_showing_retention_time_nc = "document_showing_retention_time_nc";

    private static final String Key_document_showing_define_process = "document_showing_define_process";
    private static final String Key_document_showing_define_process_remark = "document_showing_define_process_remark";
    private static final String Key_document_showing_define_process_nc = "document_showing_define_process_nc";

    private static final String Key_document_showing_medical_records = "document_showing_medical_records";
    private static final String Key_document_showing_medical_records_remark = "document_showing_medical_records_remark";
    private static final String Key_document_showing_medical_records_nc = "document_showing_medical_records_nc";


    // Scope of Service

    private static final String Table_scope_service = "Scopea_of_Serive";
    private static final String Key_clinical_anaesthesiology = "clinical_anaesthesiology";
    private static final String Key_clinical_dermatology_venereology = "clinical_dermatology_venereology";
    private static final String Key_clinical_emergency_medicine = "clinical_emergency_medicine";
    private static final String Key_clinical_family_medicine = "clinical_clinical_family_medicine";
    private static final String Key_clinical_general_medicine = "clinical_clinical_general_medicine";
    private static final String Key_clinical_general_surgery = "clinical_general_surgery";
    private static final String Key_clinical_obstetrics_gynecology = "clinical_obstetrics_gynecology";
    private static final String Key_clinical_ophthalmology = "clinical_ophthalmology";
    private static final String Key_clinical_orthopaedic_surgery = "clinical_orthopaedic_surgery";
    private static final String Key_clinical_otorhinolaryngology = "clinical_otorhinolaryngology";
    private static final String Key_clinical_paediatrics = "clinical_paediatrics";
    private static final String Key_clinical_Psychiatry = "clinical_Psychiatry";
    private static final String Key_clinical_respiratory_medicine = "clinical_respiratory_medicine";
    private static final String Key_clinical_day_care_services = "clinical_day_care_services";
    private static final String Key_clinical_cardiac_anaesthesia = "clinical_cardiac_anaesthesia";
    private static final String Key_clinical_cardiology = "clinical_cardiology";
    private static final String Key_clinical_cardiothoracic_surgery = "clinical_cardiothoracic_surgery";
    private static final String Key_clinical_clinical_haematology = "clinical_clinical_haematology";
    private static final String Key_clinical_critical_care = "clinical_critical_care";
    private static final String Key_clinical_endocrinology = "clinical_endocrinology";
    private static final String Key_clinical_hepatology = "clinical_hepatology";
    private static final String Key_clinical_immunology = "clinical_immunology";
    private static final String Key_clinical_medical_gastroenterology = "clinical_medical_gastroenterology";
    private static final String Key_clinical_neonatology = "clinical_neonatology";
    private static final String Key_clinical_nephrology = "clinical_nephrology";
    private static final String Key_clinical_Neurology = "clinical_Neurology";
    private static final String Key_clinical_Neurosurgery = "clinical_Neurosurgery";
    private static final String Key_clinical_Oncology = "clinical_Oncology";
    private static final String Key_clinical_paediatric_cardiology = "clinical_paediatric_cardiology";
    private static final String Key_clinical_paediatric_surgery = "clinical_paediatric_surgery";
    private static final String Key_clinical_plastic_reconstructive = "clinical_plastic_reconstructive";
    private static final String Key_clinical_surgical_gastroenterology = "clinical_surgical_gastroenterology";
    private static final String Key_clinical_urology = "clinical_urology";
    private static final String Key_clinical_transplantation_service = "clinical_transplantation_service";
    private static final String Key_diagnostic_ct_scanning = "diagnostic_ct_scanning";
    private static final String Key_diagnostic_mammography = "diagnostic_mammography";
    private static final String Key_diagnostic_mri = "diagnostic_mri";
    private static final String Key_diagnostic_ultrasound = "diagnostic_ultrasound";
    private static final String Key_diagnostic_x_ray= "diagnostic_x_ray";
    private static final String Key_diagnostic_2d_echo = "diagnostic_2d_echo";
    private static final String Key_diagnostic_eeg = "diagnostic_eeg";
    private static final String Key_diagnostic_holter_monitoring = "diagnostic_holter_monitoring";
    private static final String Key_diagnostic_spirometry = "diagnostic_spirometry";
    private static final String Key_diagnostic_tread_mill_testing = "diagnostic_tread_mill_testing";
    private static final String Key_diagnostic_urodynamic_studies = "diagnostic_urodynamic_studies";
    private static final String Key_diagnostic_bone_densitometry = "diagnostic_bone_densitometry";
    private static final String Key_laboratory_clinical_bio_chemistry = "laboratory_clinical_bio_chemistry";
    private static final String Key_laboratory_clinical_microbiology = "laboratory_clinical_microbiology";
    private static final String Key_laboratory_clinical_pathology = "laboratory_clinical_pathology";
    private static final String Key_laboratory_cytopathology = "laboratory_cytopathology";
    private static final String Key_laboratory_haematology = "laboratory_haematology";
    private static final String Key_laboratory_histopathology = "laboratory_histopathology";
    private static final String Key_pharmacy_dispensary = "pharmacy_dispensary";
    private static final String Key_transfusions_blood_transfusions = "transfusions_blood_transfusions";
    private static final String Key_transfusions_blood_bank = "transfusions_blood_bank";
    private static final String Key_professions_allied_ambulance = "professions_allied_ambulance";
    private static final String Key_professions_dietetics = "professions_dietetics";
    private static final String Key_professions_physiotherapy = "professions_physiotherapy";
    private static final String Key_professions_occupational_therapy = "professions_occupational_therapy";
    private static final String Key_professions_speech_language = "professions_speech_language";
    private static final String Key_professions_psychology = "professions_psychology";



    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_Laboratory_TABLE);
        sqLiteDatabase.execSQL(CREATE_Radiology_TABLE);
        sqLiteDatabase.execSQL(CREATE_Emergency_TABLE);
        sqLiteDatabase.execSQL(CREATE_High_Dependency_TABLE);
        sqLiteDatabase.execSQL(CREATE_Obstetric_Ward_TABLE);
        sqLiteDatabase.execSQL(CREATE_OT_TABLE);
        sqLiteDatabase.execSQL(CREATE_Wards_Pharmacy_TABLE);
        sqLiteDatabase.execSQL(CREATE_Patient_Staff_Interview_TABLE);
        sqLiteDatabase.execSQL(CREATE_Ward_Ot_Emergency_TABLE);
        sqLiteDatabase.execSQL(CREATE_HRM_TABLE);
        sqLiteDatabase.execSQL(CREATE_MRD_TABLE);
        sqLiteDatabase.execSQL(CREATE_Housekeeping_TABLE);
        sqLiteDatabase.execSQL(CREATE_Sterilization_Area_TABLE);
        sqLiteDatabase.execSQL(CREATE_ASSESSMENT_STATUS_TABLE);
        sqLiteDatabase.execSQL(CREATE_Bio_medical_engineering_TABLE);
        sqLiteDatabase.execSQL(CREATE_Facility_Checks_TABLE);
        sqLiteDatabase.execSQL(CREATE_Management_TABLE);
        sqLiteDatabase.execSQL(CREATE_Safety_Management_TABLE);
        sqLiteDatabase.execSQL(CREATE_Uniform_Signage_TABLE);
        sqLiteDatabase.execSQL(CREATE_Ambulance_Accessibility_TABLE);
        sqLiteDatabase.execSQL(CREATE_Documentation_TABLE);
        sqLiteDatabase.execSQL(CREATE_Scope_Service_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Laboratory_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Radiology_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Emergency_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_High_Dependency_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Obstetric_Ward_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_OT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Wards_Pharmacy_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Patient_Staff_Interview_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Ward_Ot_Emergency_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_HRM_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_MRD_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Housekeeping_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Sterilization_Area_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_ASSESSMENT_STATUS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Bio_medical_engineering_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Facility_Checks_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Management_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Safety_Management_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Uniform_Signage_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Ambulance_Accessibility_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Documentation_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_Scope_Service_TABLE);

    }

    String CREATE_ASSESSMENT_STATUS_TABLE = "CREATE TABLE "
            + TABLE_ASSESSEMENT_STATUS + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + KEY_ASSESSEMENT_NAME + " TEXT,"
            + KEY_ASSESSEMENT_STATUS + " TEXT" + ")";


    String CREATE_Laboratory_TABLE = "CREATE TABLE "
            + TABLE_LABORATORY + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + LABORATORY_collected_properly + " TEXT,"
            + Collected_properly_remark + " TEXT,"
            + Collected_properly_NC + " TEXT,"
            + Collected_properly_video + " TEXT,"
            + Local_Collected_properly_video + " TEXT,"
            + LABORATORY_identified_properly + " TEXT,"
            + Identified_properly_remark + " TEXT,"
            + Identified_properly_NC + " TEXT,"
            + Identified_properly_image + " TEXT,"
            + Local_Identified_properly_image + " TEXT,"
            + LABORATORY_transported_safe_manner + " TEXT,"
            + Transported_safe_manner_remark + " TEXT,"
            + Transported_safe_manner_NC + " TEXT,"
            + Transported_safe_manner_image + " TEXT,"
            + Local_Transported_safe_manner_image + " TEXT,"
            + LABORATORY_Specimen_safe_manner + " TEXT,"
            + Specimen_safe_manner_remark + " TEXT,"
            + Specimen_safe_manner_nc + " TEXT,"
            + Specimen_safe_image + " TEXT,"
            + Local_Specimen_safe_image + " TEXT,"
            + LABORATORY_Appropriate_safety_equipment + " TEXT,"
            + Appropriate_safety_equipment_remark + " TEXT,"
            + Appropriate_safety_equipment_NC + " TEXT,"
            + Appropriate_safety_equipment_image + " TEXT,"
            + Local_Appropriate_safety_equipment_image + " TEXT,"
            + Laboratory_defined_turnaround + " TEXT,"
            + Laboratory_defined_turnaround_Remark + " TEXT,"
            + Laboratory_defined_turnaround_Nc + " TEXT,"
            + Laboratory_defined_turnaround_Image + " TEXT,"
            + Local_Laboratory_defined_turnaround_Image + " TEXT" + ")";

    String CREATE_Radiology_TABLE = "CREATE TABLE "
            + TABLE_RADIOLOGY + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + RADIOLOGY_Appropriate_safety_equipment + " TEXT,"
            + RADIOLOGY_Appropriate_safety_equipment_remark + " TEXT,"
            + RADIOLOGY_Appropriate_safety_equipment_NC + " TEXT,"
            + RADIOLOGY_Appropriate_safety_equipment_image + " TEXT,"
            + Local_RADIOLOGY_Appropriate_safety_equipment_image + " TEXT,"
            + RADIOLOGY_defined_turnaround + " TEXT,"
            + RADIOLOGY_defined_turnaround_Remark + " TEXT,"
            + RADIOLOGY_defined_turnaround_Nc + " TEXT,"
            + RADIOLOGY_defined_turnaround_Image + " TEXT,"
            + Local_RADIOLOGY_defined_turnaround_Image + " TEXT" + ")";

    String CREATE_Emergency_TABLE = "CREATE TABLE "
            + TABLE_EMERGENCY + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + EMERGENCY_down_Norms + " TEXT,"
            + EMERGENCY_down_Norms_remark + " TEXT,"
            + EMERGENCY_down_Norms_NC + " TEXT,"
            + EMERGENCY_down_Norms_video + " TEXT,"
            + Local_EMERGENCY_down_Norms_video + " TEXT" + ")";

    String CREATE_High_Dependency_TABLE = "CREATE TABLE "
            + TABLE_HIGH_DEPENDENCY + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + HIGH_DEPENDENCY_documented_procedure + " TEXT,"
            + HIGH_DEPENDENCY_documented_procedure_remark + " TEXT,"
            + HIGH_DEPENDENCY_documented_procedure_NC + " TEXT,"
            + HIGH_DEPENDENCY_documented_procedure_video + " TEXT,"
            + Local_HIGH_DEPENDENCY_documented_procedure_video + " TEXT,"
            + HIGH_DEPENDENCY_Areas_adequate + " TEXT,"
            + HIGH_DEPENDENCY_Areas_adequate_remark + " TEXT,"
            + HIGH_DEPENDENCY_Areas_adequate_NC + " TEXT,"
            + HIGH_DEPENDENCY_Areas_adequate_image + " TEXT,"
            + Local_HIGH_DEPENDENCY_Areas_adequate_image + " TEXT,"
            + HIGH_DEPENDENCY_adequate_equipment + " TEXT,"
            + HIGH_DEPENDENCY_adequate_equipment_remark + " TEXT,"
            + HIGH_DEPENDENCY_adequate_equipment_Nc + " TEXT,"
            + HIGH_DEPENDENCY_adequate_equipment_image + " TEXT,"
            + Local_HIGH_DEPENDENCY_adequate_equipment_image + " TEXT" + ")";

    String CREATE_Obstetric_Ward_TABLE = "CREATE TABLE "
            + TABLE_OBSTETRIC_WARD + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + OBSTETRIC_WARD_abuse + " TEXT,"
            + OBSTETRIC_WARD_abuse_remark + " TEXT,"
            + OBSTETRIC_WARD_abuse_NC + " TEXT,"
            + OBSTETRIC_WARD_abuse_image_identification + " TEXT,"
            + Local_OBSTETRIC_WARD_abuse_image_identification + " TEXT,"
            + OBSTETRIC_WARD_abuse_image_surveillance + " TEXT,"
            + Local_OBSTETRIC_WARD_abuse_image_surveillance + " TEXT,"
            + shco_obstetrics_ward + " TEXT,"
            + shco_obstetrics_ward_remark + " TEXT,"
            + shco_obstetrics_ward_nc + " TEXT,"
            + shco_obstetrics_ward_image + " TEXT,"
            + Local_shco_obstetrics_ward_image + " TEXT" + ")";

    String CREATE_OT_TABLE = "CREATE TABLE "
            + TABLE_OT + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + KEY_Anaesthetist_status + " TEXT,"
            + KEY_Remark_OT_anaesthetist + " TEXT,"
            + KEY_Nc_OT_anaesthetist + " TEXT,"
            + KEY_Documented_anaesthesia_status + " TEXT,"
            + KEY_Remark_documented_anaesthesia + " TEXT,"
            + KEY_Nc_documented_anaesthesia + " TEXT,"
            + KEY_Immediate_preoperative_status + " TEXT,"
            + KEY_Remark_immediate_preoperative + " TEXT,"
            + KEY_Nc_immediate_preoperative + " TEXT,"
            + KEY_Anaesthesia_monitoring_status + " TEXT,"
            + KEY_Remark_anaesthesia_monitoring + " TEXT,"
            + KEY_Nc_anaesthesia_monitoring + " TEXT,"
            + KEY_Post_anaesthesia_monitoring_status + " TEXT,"
            + KEY_Remark_post_anaesthesia_monitoring + " TEXT,"
            + KEY_Nc_post_anaesthesia_monitoring + " TEXT,"
            + KEY_WHO_Patient_Safety_status + " TEXT,"
            + KEY_Remark_WHO_Patient_Safety + " TEXT,"
            + KEY_Nc_WHO_Patient_Safety + " TEXT,"
            + KEY_Image_WHO_Patient_Safety_day1 + " TEXT,"
            + Local_KEY_Image_WHO_Patient_Safety_day1 + " TEXT,"
            + KEY_Image_WHO_Patient_Safety_day2 + " TEXT,"
            + Local_KEY_Image_WHO_Patient_Safety_day2 + " TEXT,"
            + KEY_Image_WHO_Patient_Safety_day3 + " TEXT,"
            + Local_KEY_Image_WHO_Patient_Safety_day3 + " TEXT,"
            + KEY_OT_Zoning_status + " TEXT,"
            + KEY_Remark_OT_Zoning + " TEXT,"
            + KEY_Nc_OT_Zoning + " TEXT,"
            + KEY_Video_OT_Zoning + " TEXT,"
            + KEY_Local_video_OT_Zoning + " TEXT,"
            + KEY_Infection_control_status + " TEXT,"
            + KEY_Remark_infection_control + " TEXT,"
            + KEY_Nc_infection_control + " TEXT,"
            + KEY_Image_infection_control + " TEXT,"
            + Local_KEY_Image_infection_control + " TEXT,"
            + KEY_Narcotic_drugs_status + " TEXT,"
            + KEY_Remark_narcotic_drugs + " TEXT,"
            + KEY_Nc_narcotic_drugs + " TEXT,"
            + KEY_Image_narcotic_drugs + " TEXT,"
            + Local_KEY_Image_narcotic_drugs + " TEXT,"
            + KEY_Administration_disposal_status + " TEXT,"
            + KEY_Remark_administration_disposal + " TEXT,"
            + KEY_Nc_administration_disposal + " TEXT,"
            + KEY_Image_administration_disposal + " TEXT,"
            + Local_KEY_Image_administration_disposal + " TEXT,"
            + Key_hand_wash_facility_status + " TEXT,"
            + Key_remark_hand_wash_facility + " TEXT,"
            + Key_nc_hand_wash_facility + " TEXT,"
            + Key_image_hand_wash_facility + " TEXT,"
            + Key_local_image_hand_wash_facility + " TEXT" + ")";




    String CREATE_Wards_Pharmacy_TABLE = "CREATE TABLE "
            + TABLE_Ward_Pharmacy + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_patient_care_area + " TEXT,"
            + Key_patient_care_area_Remark + " TEXT,"
            + Key_patient_care_area_NC + " TEXT,"
            + Key_patient_care_area_Image + " TEXT,"
            + Local_Key_patient_care_area_Image + " TEXT,"
            + Key_pharmacyStores_present + " TEXT,"
            + Key_pharmacyStores_present_Remark + " TEXT,"
            + Key_pharmacyStores_present_Nc + " TEXT,"
            + Key_pharmacyStores_present_Image + " TEXT,"
            + Local_Key_pharmacyStores_present_Image + " TEXT,"
            + Key_expired_drugs + " TEXT,"
            + Key_expired_drugs_Remark + " TEXT,"
            + Key_expired_drugs_NC + " TEXT,"
            + Key_expired_drugs_image + " TEXT,"
            + Local_Key_expired_drugs_image + " TEXT,"
            + Key_expiry_date_checked + " TEXT,"
            + Key_expiry_date_checked_Remark + " TEXT,"
            + Key_expiry_date_checked_NC + " TEXT,"
            + Key_emergency_medications + " TEXT,"
            + Key_emergency_medications_Remark + " TEXT,"
            + Key_emergency_medications_Nc + " TEXT,"
            + Key_emergency_medications_Image + " TEXT,"
            + Local_Key_emergency_medications_Image + " TEXT,"
            + Key_risk_medications + " TEXT,"
            + Key_risk_medications_Remark + " TEXT,"
            + Key_risk_medications_Nc + " TEXT,"
            + Key_risk_medications_Image + " TEXT,"
            + Local_Key_risk_medications_Image + " TEXT,"
            + Key_medications_dispensing + " TEXT,"
            + Key_medications_dispensing_Remark + " TEXT,"
            + Key_medications_dispensing_Nc + " TEXT,"
            + Key_labelling_of_drug + " TEXT,"
            + Key_labelling_of_drug_Remark + " TEXT,"
            + Key_labelling_of_drug_NC + " TEXT,"
            + Key_labelling_of_drug_Image + " TEXT,"
            + Local_Key_labelling_of_drug_Image + " TEXT,"
            + Key_medication_order_checked + " TEXT,"
            + Key_medication_order_checked_Remark + " TEXT,"
            + Key_medication_order_checked_Nc + " TEXT,"
            + Key_medication_order_checked_Image + " TEXT,"
            + Local_Key_medication_order_checked_Image + " TEXT,"
            + Key_medication_administration_documented + " TEXT,"
            + Key_medication_administration_documented_Remark + " TEXT,"
            + Key_medication_administration_documented_Nc + " TEXT,"
            + Key_medication_administration_documented_Image + " TEXT,"
            + Local_Key_medication_administration_documented_Image + " TEXT,"
            + Key_fridge_temperature_record + " TEXT,"
            + Key_fridge_temperature_record_Remark + " TEXT,"
            + Key_fridge_temperature_record_Nc + " TEXT,"
            + Key_fridge_temperature_record_Image + " TEXT,"
            + Local_Key_fridge_temperature_record_Image + " TEXT" + ")";

    String CREATE_Patient_Staff_Interview_TABLE = "CREATE TABLE "
            + TABLE_Patient_Staff_Interview + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_privacy_maintained + " TEXT,"
            + Key_privacy_maintained_Remark + " TEXT,"
            + Key_privacy_maintained_Nc + " TEXT,"
            + Key_privacy_maintained_Image + " TEXT,"
            + Local_Key_privacy_maintained_Image + " TEXT,"
            + Key_Patients_protected_physical_abuse + " TEXT,"
            + Key_Patients_protected_physical_Remark + " TEXT,"
            + Key_Patients_protected_physical_abuse_Nc + " TEXT,"
            + Key_patient_information_confidential + " TEXT,"
            + Key_patient_information_confidential_Remark + " TEXT,"
            + Key_patient_information_confidential_Nc + " TEXT,"
            + Key_patients_consent_carrying + " TEXT,"
            + Key_patients_consent_carrying_Remark + " TEXT,"
            + Key_patients_consent_carrying_Nc + " TEXT,"
            + Key_patient_voice_complaint + " TEXT,"
            + Key_patient_voice_complaint_Remark + " TEXT,"
            + Key_patient_voice_complaint_Nc + " TEXT,"
            + Key_patients_cost_treatment + " TEXT,"
            + Key_patients_cost_treatment_Remark + " TEXT,"
            + Key_patients_cost_treatment_Nc + " TEXT,"
            + Key_patient_clinical_records + " TEXT,"
            + Key_patient_clinical_records_Remark + " TEXT,"
            + Key_patient_clinical_records_Nc + " TEXT,"
            + Key_patient_aware_plan_care + " TEXT,"
            + Key_patient_aware_plan_care_Remark + " TEXT,"
            + Key_patient_aware_plan_care_Nc + " TEXT,"
            + Key_patient_aware_plan_care_Video + " TEXT,"
            + Local_Key_patient_aware_plan_care_Video + " TEXT" + ")";


    String CREATE_Ward_Ot_Emergency_TABLE = "CREATE TABLE "
            + Table_Ward_Ot_Emergency + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_hospital_maintain_cleanliness + " TEXT,"
            + Key_hospital_maintain_cleanliness_Remark + " TEXT,"
            + Key_hospital_maintain_cleanliness_Nc + " TEXT,"
            + Key_hospital_adhere_standard + " TEXT,"
            + Key_hospital_adhere_standard_Remark + " TEXT,"
            + Key_hospital_adhere_standard_Nc + " TEXT,"
            + Key_hospital_provide_adequate_gloves + " TEXT,"
            + Key_hospital_provide_adequate_gloves_Remark + " TEXT,"
            + Key_hospital_provide_adequate_gloves_Nc + " TEXT,"
            + Key_admissions_discharge_home + " TEXT,"
            + Key_admissions_discharge_home_Remark + " TEXT,"
            + Key_admissions_discharge_home_Nc + " TEXT,"
            + Key_admissions_discharge_home_Video + " TEXT,"
            + Local_Key_admissions_discharge_home_Video + " TEXT,"
            + Key_staff_present_in_ICU + " TEXT,"
            + Key_staff_present_in_Remark + " TEXT,"
            + Key_staff_present_in_ICU_Nc + " TEXT" + ")";

    String CREATE_HRM_TABLE = "CREATE TABLE "
            + Table_HRM + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_staff_health_related_issues + " TEXT,"
            + Key_staff_health_related_issues_Remark + " TEXT,"
            + Key_staff_health_related_issues_Nc + " TEXT,"
            + Key_staffs_personal_files_maintained + " TEXT,"
            + Key_staffs_personal_files_maintained_Remark + " TEXT,"
            + Key_staffs_personal_files_maintained_Nc + " TEXT,"
            + Key_staffs_personal_files_maintained_image + " TEXT,"
            + Local_Key_staffs_personal_files_maintained_image + " TEXT,"
            + Key_occupational_health_hazards + " TEXT,"
            + Key_occupational_health_hazards_Remark + " TEXT,"
            + Key_occupational_health_hazards_Nc + " TEXT,"
            + Key_training_responsibility_changes + " TEXT,"
            + Key_training_responsibility_changes_Remark + " TEXT,"
            + Key_training_responsibility_changes_Nc + " TEXT,"
            + Key_medical_records_doctors_retrievable + " TEXT,"
            + Key_medical_records_doctors_retrievable_Remark + " TEXT,"
            + Key_medical_records_doctors_retrievable_Nc + " TEXT,"
            + Key_case_of_grievances + " TEXT,"
            + Key_case_of_grievances_Remark + " TEXT,"
            + Key_case_of_grievances_Nc + " TEXT,"
            + Key_case_of_grievances_Image + " TEXT,"
            + Local_Key_case_of_grievances_Image + " TEXT,"
            + Key_staff_disciplinary_procedure + " TEXT,"
            + Key_staff_disciplinary_procedure_Remark + " TEXT,"
            + Key_staff_disciplinary_procedure_Nc + " TEXT,"
            + Key_staff_disciplinary_procedure_Video + " TEXT,"
            + Local_Key_staff_disciplinary_procedure_Video + " TEXT,"
            + Key_staff_able_to_demonstrate + " TEXT,"
            + Key_staff_able_to_demonstrate_Remark + " TEXT,"
            + Key_staff_able_to_demonstrate_Nc + " TEXT" + ")";

    String CREATE_MRD_TABLE = "CREATE TABLE "
            + Table_MRD + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_medical_record_death_certificate + " TEXT,"
            + Key_medical_record_death_certificate_Remark + " TEXT,"
            + Key_medical_record_death_certificate_Nc + " TEXT,"
            + Key_documented_maintaining_confidentiality + " TEXT,"
            + Key_documented_maintaining_confidentiality_Remark + " TEXT,"
            + Key_documented_maintaining_confidentiality_Nc + " TEXT,"
            + Key_any_information_disclosed + " TEXT,"
            + Key_any_information_disclosed_Remark + " TEXT,"
            + Key_any_information_disclosed_Nc + " TEXT,"
            + Key_destruction_medical_records + " TEXT,"
            + Key_destruction_medical_records_Remark + " TEXT,"
            + Key_destruction_medical_records_Nc + " TEXT,"
            + Key_fire_extinguisher_present + " TEXT,"
            + Key_fire_extinguisher_present_Remark + " TEXT,"
            + Key_fire_extinguisher_present_Nc + " TEXT,"
            + Key_fire_extinguisher_present_Image + " TEXT,"
            + Local_Key_fire_extinguisher_present_Image + " TEXT" + ")";


    String CREATE_Housekeeping_TABLE = "CREATE TABLE "
            + Table_Housekeeping + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_infected_patient_room + " TEXT,"
            + Key_infected_patient_room_Remark + " TEXT,"
            + Key_infected_patient_room_Nc + " TEXT,"
            + Key_procedure_cleaning_room + " TEXT,"
            + Key_procedure_cleaning_room_Remark + " TEXT,"
            + Key_procedure_cleaning_room_Nc + " TEXT,"
            + Key_procedure_cleaning_blood_spill + " TEXT,"
            + Key_procedure_cleaning_blood_spill_Remark + " TEXT,"
            + Key_procedure_cleaning_blood_spill_Nc + " TEXT,"
            + Key_procedure_cleaning_blood_spill_Video + " TEXT,"
            + Local_Key_procedure_cleaning_blood_spill_Video + " TEXT,"
            + Key_Biomedical_Waste_regulations + " TEXT,"
            + Key_Biomedical_Waste_regulations_Remark + " TEXT,"
            + Key_Biomedical_Waste_regulations_Nc + " TEXT,"
            + Key_Biomedical_Waste_regulations_Image + " TEXT,"
            + Local_Key_Biomedical_Waste_regulations_Image + " TEXT,"
            + Key_cleaning_washing_blood_stained + " TEXT,"
            + Key_cleaning_washing_blood_stained_Remark + " TEXT,"
            + Key_cleaning_washing_blood_stained_Nc + " TEXT" + ")";

    String CREATE_Sterilization_Area_TABLE = "CREATE TABLE "
            + Table_Sterilization_Area + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_sterilisation_practices_adhered + " TEXT,"
            + Key_sterilisation_practices_adhered_Remark + " TEXT,"
            + Key_sterilisation_practices_adhered_Nc + " TEXT,"
            + Key_monitor_effectiveness_sterilization_process + " TEXT,"
            + Key_monitor_effectiveness_sterilization_process_Remark + " TEXT,"
            + Key_monitor_effectiveness_sterilization_process_Nc + " TEXT,"
            + Key_monitor_effectiveness_sterilization_process_Image + " TEXT,"
            + Local_Key_monitor_effectiveness_sterilization_process_Image + " TEXT,"
            + Key_sterilized_drums_trays + " TEXT,"
            + Key_sterilized_drums_trays_Remark + " TEXT,"
            + Key_sterilized_drums_trays_Nc + " TEXT,"
            + Key_sterilized_drums_trays_Image + " TEXT,"
            + Local_Key_sterilized_drums_trays_Image + " TEXT" + ")";


    String CREATE_Management_TABLE = "CREATE TABLE "
            + Table_Management + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_requisite_fee_BMW + " TEXT,"
            + Key_requisite_fee_BMW_Remark + " TEXT,"
            + Key_requisite_fee_BMW_Nc + " TEXT,"
            + Key_management_guide_organization + " TEXT,"
            + Key_management_guide_organization_Remark + " TEXT,"
            + Key_management_guide_organization_Nc + " TEXT,"
            + Key_hospital_mission_present + " TEXT,"
            + Key_hospital_mission_present_Remark + " TEXT,"
            + Key_hospital_mission_present_Nc + " TEXT,"
            + Key_hospital_mission_present_Image + " TEXT,"
            + Local_Key_hospital_mission_present_Image + " TEXT,"
            + Key_patient_maintained_OPD + " TEXT,"
            + Key_patient_maintained_OPD_Remark + " TEXT,"
            + Key_patient_maintained_OPD_Nc + " TEXT,"
            + Key_patient_maintained_OPD_Image + " TEXT,"
            + Local_Key_patient_maintained_OPD_Image + " TEXT,"
            + Key_patient_maintained_IPD + " TEXT,"
            + Key_patient_maintained_IPD_Remark + " TEXT,"
            + Key_patient_maintained_IPD_Nc + " TEXT,"
            + Key_patient_maintained_IPD_Image + " TEXT,"
            + Local_Key_patient_maintained_IPD_Image + " TEXT,"
            + Key_patient_maintained_Emergency + " TEXT,"
            + Key_patient_maintained_Emergency_Remark + " TEXT,"
            + Key_patient_maintained_Emergency_Nc + " TEXT,"
            + Key_patient_maintained_Emergency_Image + " TEXT,"
            + Local_Key_patient_maintained_Emergency_Image + " TEXT,"
            + Key_basic_Tariff_List + " TEXT,"
            + Key_basic_Tariff_List_Remark + " TEXT,"
            + Key_basic_Tariff_List_Nc + " TEXT,"
            + Key_basic_Tariff_List_Image + " TEXT,"
            + Local_Key_basic_Tariff_List_Image + " TEXT,"
            + Key_parameter_patient_identification + " TEXT,"
            + Key_parameter_patient_identification_Remark + " TEXT,"
            + Key_parameter_patient_identification_Nc + " TEXT,"
            + key_quality_improvement_programme + " TEXT,"
            + key_quality_improvement_programme_Remark + " TEXT,"
            + key_quality_improvement_programme_Nc + " TEXT" + ")";

    String CREATE_Bio_medical_engineering_TABLE = "CREATE TABLE "
            + Table_Bio_medical_engineering + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_Maintenance_staff_contactable + " TEXT,"
            + Key_Maintenance_staff_contactable_Remark + " TEXT,"
            + Key_Maintenance_staff_contactable_Nc + " TEXT,"
            + key_equipment_accordance_services + " TEXT,"
            + key_equipment_accordance_services_Remark + " TEXT,"
            + key_equipment_accordance_services_Nc + " TEXT,"
            + Key_documented_operational_maintenance + " TEXT,"
            + Key_documented_operational_maintenance_Remark + " TEXT,"
            + Key_documented_operational_maintenance_Nc + " TEXT,"
            + Key_documented_operational_maintenance_Image + " TEXT,"
            + Local_Key_documented_operational_maintenance_Image + " TEXT" + ")";

    String CREATE_Facility_Checks_TABLE = "CREATE TABLE "
            + Table_Facility_Checks + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_medical_gas_cylinders + " TEXT,"
            + Key_medical_gas_cylinders_Remark + " TEXT,"
            + Key_medical_gas_cylinders_Nc + " TEXT,"
            + Key_medical_gas_cylinders_Image + " TEXT,"
            + Local_Key_medical_gas_cylinders_Image + " TEXT,"
            + Key_smoke_detectors_installed + " TEXT,"
            + Key_smoke_detectors_installed_Remark + " TEXT,"
            + Key_smoke_detectors_installed_Nc + " TEXT,"
            + Key_smoke_detectors_installed_Image + " TEXT,"
            + Local_Key_smoke_detectors_installed_Image + " TEXT,"
            + Key_extinguisher_present_patient + " TEXT,"
            + Key_extinguisher_present_patient_Remark + " TEXT,"
            + Key_extinguisher_present_patient_Nc + " TEXT,"
            + Key_extinguisher_present_patient_Image + " TEXT,"
            + Local_Key_extinguisher_present_patient_Image + " TEXT,"
            + Key_fire_fighting_equipment + " TEXT,"
            + Key_fire_fighting_equipment_Remark + " TEXT,"
            + Key_fire_fighting_equipment_Nc + " TEXT,"
            + Key_safe_exit_plan + " TEXT,"
            + Key_safe_exit_plan_Remark + " TEXT,"
            + Key_safe_exit_plan_Nc + " TEXT" + ")";

    String CREATE_Safety_Management_TABLE = "CREATE TABLE "
            + Table_Safety_Management + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_safety_device_lab + " TEXT,"
            + Key_safety_device_lab_Remark + " TEXT,"
            + Key_safety_device_lab_Nc + " TEXT,"
            + Key_safety_device_lab_Image + " TEXT,"
            + Local_Key_safety_device_lab_Image + " TEXT,"
            + Key_body_parts_staff_patients + " TEXT,"
            + Key_body_parts_staff_patients_Remark + " TEXT,"
            + Key_body_parts_staff_patients_Nc + " TEXT,"
            + Key_body_parts_staff_patients_Image + " TEXT,"
            + Local_Key_body_parts_staff_patients_Image + " TEXT,"
            + Key_staff_member_radiation_area + " TEXT,"
            + Key_staff_member_radiation_area_Remark + " TEXT,"
            + Key_staff_member_radiation_area_Nc + " TEXT,"
            + Key_staff_member_radiation_area_Image + " TEXT,"
            + Local_Key_staff_member_radiation_area_Image + " TEXT,"
            + Key_standardised_colur_coding + " TEXT,"
            + Key_standardised_colur_coding_Remark + " TEXT,"
            + Key_standardised_colur_coding_Nc + " TEXT,"
            + Key_standardised_colur_coding_Image + " TEXT,"
            + Local_Key_standardised_colur_coding_Image + " TEXT,"
            + Key_safe_storage_medical + " TEXT,"
            + Key_safe_storage_medical_Remark + " TEXT,"
            + Key_safe_storage_medical_Nc + " TEXT,"
            + Key_safe_storage_medical_Image + " TEXT,"
            + Local_Key_safe_storage_medical_Image + " TEXT" + ")";

    String CREATE_Ambulance_Accessibility_TABLE = "CREATE TABLE "
            + Table_Ambulance_Accessibility + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_access_ambulance_patient_drop + " TEXT,"
            + Key_access_ambulance_patient_drop_remark + " TEXT,"
            + Key_access_ambulance_patient_drop_nc + " TEXT,"
            + Key_ownership_the_ambulance + " TEXT,"
            + Key_ownership_the_ambulance_remark + " TEXT,"
            + Key_ownership_the_ambulance_Nc + " TEXT,"
            + Key_total_number_ambulance_available + " TEXT,"
            + Key_total_number_ambulance_available_Remark + " TEXT,"
            + Key_total_number_ambulance_available_Nc + " TEXT,"
            + Key_total_number_ambulance_available_Image + " TEXT,"
            + Local_Key_total_number_ambulance_available_Image + " TEXT,"
            + Key_ambulance_appropriately_equiped + " TEXT,"
            + Key_ambulance_appropriately_equiped_Remark + " TEXT,"
            + Key_ambulance_appropriately_equiped_Nc + " TEXT,"
            + Key_ambulance_appropriately_equiped_Image + " TEXT,"
            + Local_Key_ambulance_appropriately_equiped_Image + " TEXT,"
            + Key_drivers_ambulances_available + " TEXT,"
            + Key_drivers_ambulances_available_Remark + " TEXT,"
            + Key_drivers_ambulances_available_Nc + " TEXT,"
            + Key_drivers_ambulances_available_Image + " TEXT,"
            + Local_Key_drivers_ambulances_available_Image + " TEXT,"
            + Key_doctors_available_ambulances + " TEXT,"
            + Key_doctors_available_ambulances_Remark + " TEXT,"
            + Key_doctors_available_ambulances_Nc + " TEXT,"
            + Key_doctors_available_ambulances_Image + " TEXT,"
            + Local_Key_doctors_available_ambulances_Image + " TEXT,"
            + Key_nurses_available_ambulances + " TEXT,"
            + Key_nurses_available_ambulances_Remark + " TEXT,"
            + Key_nurses_available_ambulances_Nc + " TEXT,"
            + Key_nurses_available_ambulances_Image + " TEXT,"
            + Local_Key_nurses_available_ambulances_Image + " TEXT" + ")";

    String CREATE_Uniform_Signage_TABLE = "CREATE TABLE "
            + Table_Uniform_Signage + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_scope_services_present + " TEXT,"
            + Key_scope_services_present_Remark + " TEXT,"
            + Key_scope_services_present_Nc + " TEXT,"
            + Key_scope_services_present_Image + " TEXT,"
            + Local_Key_scope_services_present_Image + " TEXT,"
            + Key_Patients_responsibility_displayed + " TEXT,"
            + Key_Patients_responsibility_displayed_Remark + " TEXT,"
            + Key_Patients_responsibility_displayed_Nc + " TEXT,"
            + Key_Patients_responsibility_displayed_Image + " TEXT,"
            + Local_Key_Patients_responsibility_displayed_Image + " TEXT,"
            + Key_fire_exit_signage_present + " TEXT,"
            + Key_fire_exit_signage_present_Remark + " TEXT,"
            + Key_fire_exit_signage_present_Nc + " TEXT,"
            + Key_fire_exit_signage_present_Image + " TEXT,"
            + Local_Key_fire_exit_signage_present_Image + " TEXT,"
            + Key_directional_signages_present + " TEXT,"
            + Key_directional_signages_present_Remark + " TEXT,"
            + Key_directional_signages_present_Nc + " TEXT,"
            + Key_directional_signages_present_Image + " TEXT,"
            + Local_Key_directional_signages_present_Image + " TEXT,"
            + Key_departmental_signages_present + " TEXT,"
            + Key_departmental_signages_present_Remark + " TEXT,"
            + Key_departmental_signages_present_Nc + " TEXT,"
            + Key_departmental_signages_present_Image + " TEXT,"
            + Local_Key_departmental_signages_present_Image + " TEXT" + ")";

    String CREATE_Documentation_TABLE = "CREATE TABLE "
            + Table_Documentation + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_document_related_procedure + " TEXT,"
            + Key_document_related_procedure_remark + " TEXT,"
            + Key_document_related_procedure_nc + " TEXT,"
            + Key_document_showing_process + " TEXT,"
            + Key_document_showing_process_remark + " TEXT,"
            + Key_document_showing_process_nc + " TEXT,"
            + Key_document_showing_care_patients + " TEXT,"
            + Key_document_showing_care_patients_remark + " TEXT,"
            + Key_document_showing_care_patients_nc + " TEXT,"
            + Key_document_showing_policies + " TEXT,"
            + Key_document_showing_policies_remark + " TEXT,"
            + Key_document_showing_policies_nc + " TEXT,"
            + Key_document_showing_procedures + " TEXT,"
            + Key_document_showing_procedures_remark + " TEXT,"
            + Key_document_showing_procedures_nc + " TEXT,"
            + Key_document_showing_procedure_administration + " TEXT,"
            + Key_document_showing_procedure_administration_remark + " TEXT,"
            + Key_document_showing_procedure_administration_nc + " TEXT,"
            + Key_document_showing_defined_criteria + " TEXT,"
            + Key_document_showing_defined_criteria_remark + " TEXT,"
            + Key_document_showing_defined_criteria_nc + " TEXT,"
            + Key_document_showing_procedure_prevention + " TEXT,"
            + Key_document_showing_procedure_prevention_remark + " TEXT,"
            + Key_document_showing_procedure_prevention_nc + " TEXT,"
            + Key_document_showing_procedure_incorporating + " TEXT,"
            + Key_document_showing_procedure_incorporating_remark + " TEXT,"
            + Key_document_showing_procedure_incorporating_nc + " TEXT,"
            + Key_document_showing_procedure_address + " TEXT,"
            + Key_document_showing_procedure_address_remark + " TEXT,"
            + Key_document_showing_procedure_address_nc + " TEXT,"
            + Key_document_showing_policies_procedure + " TEXT,"
            + Key_document_showing_policies_procedure_remark + " TEXT,"
            + Key_document_showing_policies_procedure_nc + " TEXT,"
            + Key_document_showing_drugs_available + " TEXT,"
            + Key_document_showing_drugs_available_remark + " TEXT,"
            + Key_document_showing_drugs_available_nc + " TEXT,"
            + key_document_showing_safe_storage + " TEXT,"
            + key_document_showing_safe_storage_remark + " TEXT,"
            + key_document_showing_safe_storage_nc + " TEXT,"
            + Key_Infection_control_manual_showing + " TEXT,"
            + Key_Infection_control_manual_showing_remark + " TEXT,"
            + Key_Infection_control_manual_showing_nc + " TEXT,"
            + Key_document_showing_operational_maintenance + " TEXT,"
            + Key_document_showing_operational_maintenance_remark + " TEXT,"
            + Key_document_showing_operational_maintenance_nc + " TEXT,"
            + Key_document_showing_safe_exit_plan + " TEXT,"
            + Key_document_showing_safe_exit_plan_remark + " TEXT,"
            + Key_document_showing_safe_exit_plan_nc + " TEXT,"
            + Key_document_showing_well_defined_staff + " TEXT,"
            + Key_document_showing_well_defined_staff_remark + " TEXT,"
            + Key_document_showing_well_defined_staff_nc + " TEXT,"
            + Key_document_showing_disciplinary_grievance + " TEXT,"
            + Key_document_showing_disciplinary_grievance_remark + " TEXT,"
            + Key_document_showing_disciplinary_grievance_nc + " TEXT,"
            + Key_document_showing_policies_procedures + " TEXT,"
            + Key_document_showing_policies_procedures_remark + " TEXT,"
            + Key_document_showing_policies_procedures_nc + " TEXT,"
            + Key_document_showing_retention_time + " TEXT,"
            + Key_document_showing_retention_time_remark + " TEXT,"
            + Key_document_showing_retention_time_nc + " TEXT,"
            + Key_document_showing_define_process + " TEXT,"
            + Key_document_showing_define_process_remark + " TEXT,"
            + Key_document_showing_define_process_nc + " TEXT,"
            + Key_document_showing_medical_records + " TEXT,"
            + Key_document_showing_medical_records_remark + " TEXT,"
            + Key_document_showing_medical_records_nc + " TEXT" + ")";

    String CREATE_Scope_Service_TABLE = "CREATE TABLE "
            + Table_scope_service + "("
            + KEY_Local_id + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_HOSPITAL_ID + " TEXT,"
            + Key_clinical_anaesthesiology + " TEXT,"
            + Key_clinical_dermatology_venereology + " TEXT,"
            + Key_clinical_emergency_medicine + " TEXT,"
            + Key_clinical_family_medicine + " TEXT,"
            + Key_clinical_general_medicine + " TEXT,"
            + Key_clinical_general_surgery + " TEXT,"
            + Key_clinical_obstetrics_gynecology + " TEXT,"
            + Key_clinical_ophthalmology + " TEXT,"
            + Key_clinical_orthopaedic_surgery + " TEXT,"
            + Key_clinical_otorhinolaryngology + " TEXT,"
            + Key_clinical_paediatrics + " TEXT,"
            + Key_clinical_Psychiatry + " TEXT,"
            + Key_clinical_respiratory_medicine + " TEXT,"
            + Key_clinical_day_care_services + " TEXT,"
            + Key_clinical_cardiac_anaesthesia + " TEXT,"
            + Key_clinical_cardiology + " TEXT,"
            + Key_clinical_cardiothoracic_surgery + " TEXT,"
            + Key_clinical_clinical_haematology + " TEXT,"
            + Key_clinical_critical_care + " TEXT,"
            + Key_clinical_endocrinology + " TEXT,"
            + Key_clinical_hepatology + " TEXT,"
            + Key_clinical_immunology + " TEXT,"
            + Key_clinical_medical_gastroenterology + " TEXT,"
            + Key_clinical_neonatology + " TEXT,"
            + Key_clinical_nephrology + " TEXT,"
            + Key_clinical_Neurology + " TEXT,"
            + Key_clinical_Neurosurgery + " TEXT,"
            + Key_clinical_Oncology + " TEXT,"
            + Key_clinical_paediatric_cardiology + " TEXT,"
            + Key_clinical_paediatric_surgery + " TEXT,"
            + Key_clinical_plastic_reconstructive + " TEXT,"
            + Key_clinical_surgical_gastroenterology + " TEXT,"
            + Key_clinical_urology + " TEXT,"
            + Key_clinical_transplantation_service + " TEXT,"
            + Key_diagnostic_ct_scanning + " TEXT,"
            + Key_diagnostic_mammography + " TEXT,"
            + Key_diagnostic_mri + " TEXT,"
            + Key_diagnostic_ultrasound + " TEXT,"
            + Key_diagnostic_x_ray + " TEXT,"
            + Key_diagnostic_2d_echo + " TEXT,"
            + Key_diagnostic_eeg + " TEXT,"
            + Key_diagnostic_holter_monitoring + " TEXT,"
            + Key_diagnostic_spirometry + " TEXT,"
            + Key_diagnostic_tread_mill_testing + " TEXT,"
            + Key_diagnostic_urodynamic_studies + " TEXT,"
            + Key_diagnostic_bone_densitometry + " TEXT,"
            + Key_laboratory_clinical_bio_chemistry + " TEXT,"
            + Key_laboratory_clinical_microbiology + " TEXT,"
            + Key_laboratory_clinical_pathology + " TEXT,"
            + Key_laboratory_cytopathology + " TEXT,"
            + Key_laboratory_haematology + " TEXT,"
            + Key_laboratory_histopathology + " TEXT,"
            + Key_pharmacy_dispensary + " TEXT,"
            + Key_transfusions_blood_transfusions + " TEXT,"
            + Key_transfusions_blood_bank + " TEXT,"
            + Key_professions_allied_ambulance + " TEXT,"
            + Key_professions_dietetics + " TEXT,"
            + Key_professions_physiotherapy + " TEXT,"
            + Key_professions_occupational_therapy + " TEXT,"
            + Key_professions_speech_language + " TEXT,"
            + Key_professions_psychology + " TEXT" + ")";


    public boolean INSERT_Documentation(DocumentationPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_document_related_procedure,pojo.getDocument_related_procedure());
        values.put(Key_document_related_procedure_remark,pojo.getDocument_related_procedure_remark());
        values.put(Key_document_related_procedure_nc,pojo.getDocument_related_procedure_nc());
        values.put(Key_document_showing_process,pojo.getDocument_showing_process());
        values.put(Key_document_showing_process_remark,pojo.getDocument_showing_process_remark());
        values.put(Key_document_showing_process_nc,pojo.getDocument_showing_process_nc());
        values.put(Key_document_showing_care_patients,pojo.getDocument_showing_care_patients());
        values.put(Key_document_showing_care_patients_remark,pojo.getDocument_showing_care_patients_remark());
        values.put(Key_document_showing_care_patients_nc,pojo.getDocument_showing_care_patients_nc());
        values.put(Key_document_showing_policies,pojo.getDocument_showing_policies());
        values.put(Key_document_showing_policies_remark,pojo.getDocument_showing_policies_remark());
        values.put(Key_document_showing_policies_nc,pojo.getDocument_showing_policies_nc());
        values.put(Key_document_showing_procedures,pojo.getDocument_showing_procedures());
        values.put(Key_document_showing_procedures_remark,pojo.getDocument_showing_procedures_remark());
        values.put(Key_document_showing_procedures_nc,pojo.getDocument_showing_procedures_nc());
        values.put(Key_document_showing_procedure_administration,pojo.getDocument_showing_procedure_administration());
        values.put(Key_document_showing_procedure_administration_remark,pojo.getDocument_showing_procedure_administration_remark());
        values.put(Key_document_showing_procedure_administration_nc,pojo.getDocument_showing_procedure_administration_nc());
        values.put(Key_document_showing_defined_criteria,pojo.getDocument_showing_defined_criteria());
        values.put(Key_document_showing_defined_criteria_remark,pojo.getDocument_showing_defined_criteria_remark());
        values.put(Key_document_showing_defined_criteria_nc,pojo.getDocument_showing_defined_criteria_nc());
        values.put(Key_document_showing_procedure_prevention,pojo.getDocument_showing_procedure_prevention());
        values.put(Key_document_showing_procedure_prevention_remark,pojo.getDocument_showing_procedure_prevention_remark());
        values.put(Key_document_showing_procedure_prevention_nc,pojo.getDocument_showing_procedure_prevention_nc());
        values.put(Key_document_showing_procedure_incorporating,pojo.getDocument_showing_procedure_incorporating());
        values.put(Key_document_showing_procedure_incorporating_remark,pojo.getDocument_showing_procedure_incorporating_remark());
        values.put(Key_document_showing_procedure_incorporating_nc,pojo.getDocument_showing_procedure_incorporating_nc());
        values.put(Key_document_showing_procedure_address,pojo.getDocument_showing_procedure_address());
        values.put(Key_document_showing_procedure_address_remark,pojo.getDocument_showing_procedure_address_remark());
        values.put(Key_document_showing_procedure_address_nc,pojo.getDocument_showing_procedure_address_nc());
        values.put(Key_document_showing_policies_procedure,pojo.getDocument_showing_policies_procedure());
        values.put(Key_document_showing_policies_procedure_remark,pojo.getDocument_showing_policies_procedure_remark());
        values.put(Key_document_showing_policies_procedure_nc,pojo.getDocument_showing_policies_procedure_nc());
        values.put(Key_document_showing_drugs_available,pojo.getDocument_showing_drugs_available());
        values.put(Key_document_showing_drugs_available_remark,pojo.getDocument_showing_drugs_available_remark());
        values.put(Key_document_showing_drugs_available_nc,pojo.getDocument_showing_drugs_available_nc());
        values.put(key_document_showing_safe_storage,pojo.getDocument_showing_safe_storage());
        values.put(key_document_showing_safe_storage_remark,pojo.getDocument_showing_safe_storage_remark());
        values.put(key_document_showing_safe_storage_nc,pojo.getDocument_showing_safe_storage_nc());
        values.put(Key_Infection_control_manual_showing,pojo.getInfection_control_manual_showing());
        values.put(Key_Infection_control_manual_showing_remark,pojo.getInfection_control_manual_showing_remark());
        values.put(Key_Infection_control_manual_showing_nc,pojo.getInfection_control_manual_showing_nc());
        values.put(Key_document_showing_operational_maintenance,pojo.getDocument_showing_operational_maintenance());
        values.put(Key_document_showing_operational_maintenance_remark,pojo.getDocument_showing_operational_maintenance_remark());
        values.put(Key_document_showing_operational_maintenance_nc,pojo.getDocument_showing_operational_maintenance_nc());
        values.put(Key_document_showing_safe_exit_plan,pojo.getDocument_showing_safe_exit_plan());
        values.put(Key_document_showing_safe_exit_plan_remark,pojo.getDocument_showing_safe_exit_plan_remark());
        values.put(Key_document_showing_safe_exit_plan_nc,pojo.getDocument_showing_safe_exit_plan_nc());
        values.put(Key_document_showing_well_defined_staff,pojo.getDocument_showing_well_defined_staff());
        values.put(Key_document_showing_well_defined_staff_remark,pojo.getDocument_showing_well_defined_staff_remark());
        values.put(Key_document_showing_well_defined_staff_nc,pojo.getDocument_showing_well_defined_staff_nc());
        values.put(Key_document_showing_disciplinary_grievance,pojo.getDocument_showing_disciplinary_grievance());
        values.put(Key_document_showing_disciplinary_grievance_remark,pojo.getDocument_showing_disciplinary_grievance_remark());
        values.put(Key_document_showing_disciplinary_grievance_nc,pojo.getDocument_showing_disciplinary_grievance_nc());
        values.put(Key_document_showing_policies_procedures,pojo.getDocument_showing_policies_procedures());
        values.put(Key_document_showing_policies_procedures_remark,pojo.getDocument_showing_policies_procedures_remark());
        values.put(Key_document_showing_policies_procedures_nc,pojo.getDocument_showing_policies_procedures_nc());
        values.put(Key_document_showing_retention_time,pojo.getDocument_showing_retention_time());
        values.put(Key_document_showing_retention_time_remark,pojo.getDocument_showing_retention_time_remark());
        values.put(Key_document_showing_retention_time_nc,pojo.getDocument_showing_retention_time_nc());
        values.put(Key_document_showing_define_process,pojo.getDocument_showing_define_process());
        values.put(Key_document_showing_define_process_remark,pojo.getDocument_showing_define_process_remark());
        values.put(Key_document_showing_define_process_nc,pojo.getDocument_showing_define_process_nc());
        values.put(Key_document_showing_medical_records,pojo.getDocument_showing_medical_records());
        values.put(Key_document_showing_medical_records_remark,pojo.getDocument_showing_medical_records_remark());
        values.put(Key_document_showing_medical_records_nc,pojo.getDocument_showing_medical_records_nc());

        database.insert(Table_Documentation,null,values);


        return true;
    }

    public boolean UPDATE_DOCUMENTATION(DocumentationPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_document_related_procedure,pojo.getDocument_related_procedure());
        values.put(Key_document_related_procedure_remark,pojo.getDocument_related_procedure_remark());
        values.put(Key_document_related_procedure_nc,pojo.getDocument_related_procedure_nc());
        values.put(Key_document_showing_process,pojo.getDocument_showing_process());
        values.put(Key_document_showing_process_remark,pojo.getDocument_showing_process_remark());
        values.put(Key_document_showing_process_nc,pojo.getDocument_showing_process_nc());
        values.put(Key_document_showing_care_patients,pojo.getDocument_showing_care_patients());
        values.put(Key_document_showing_care_patients_remark,pojo.getDocument_showing_care_patients_remark());
        values.put(Key_document_showing_care_patients_nc,pojo.getDocument_showing_care_patients_nc());
        values.put(Key_document_showing_policies,pojo.getDocument_showing_policies());
        values.put(Key_document_showing_policies_remark,pojo.getDocument_showing_policies_remark());
        values.put(Key_document_showing_policies_nc,pojo.getDocument_showing_policies_nc());
        values.put(Key_document_showing_procedures,pojo.getDocument_showing_procedures());
        values.put(Key_document_showing_procedures_remark,pojo.getDocument_showing_procedures_remark());
        values.put(Key_document_showing_procedures_nc,pojo.getDocument_showing_procedures_nc());
        values.put(Key_document_showing_procedure_administration,pojo.getDocument_showing_procedure_administration());
        values.put(Key_document_showing_procedure_administration_remark,pojo.getDocument_showing_procedure_administration_remark());
        values.put(Key_document_showing_procedure_administration_nc,pojo.getDocument_showing_procedure_administration_nc());
        values.put(Key_document_showing_defined_criteria,pojo.getDocument_showing_defined_criteria());
        values.put(Key_document_showing_defined_criteria_remark,pojo.getDocument_showing_defined_criteria_remark());
        values.put(Key_document_showing_defined_criteria_nc,pojo.getDocument_showing_defined_criteria_nc());
        values.put(Key_document_showing_procedure_prevention,pojo.getDocument_showing_procedure_prevention());
        values.put(Key_document_showing_procedure_prevention_remark,pojo.getDocument_showing_procedure_prevention_remark());
        values.put(Key_document_showing_procedure_prevention_nc,pojo.getDocument_showing_procedure_prevention_nc());
        values.put(Key_document_showing_procedure_incorporating,pojo.getDocument_showing_procedure_incorporating());
        values.put(Key_document_showing_procedure_incorporating_remark,pojo.getDocument_showing_procedure_incorporating_remark());
        values.put(Key_document_showing_procedure_incorporating_nc,pojo.getDocument_showing_procedure_incorporating_nc());
        values.put(Key_document_showing_procedure_address,pojo.getDocument_showing_procedure_address());
        values.put(Key_document_showing_procedure_address_remark,pojo.getDocument_showing_procedure_address_remark());
        values.put(Key_document_showing_procedure_address_nc,pojo.getDocument_showing_procedure_address_nc());
        values.put(Key_document_showing_policies_procedure,pojo.getDocument_showing_policies_procedure());
        values.put(Key_document_showing_policies_procedure_remark,pojo.getDocument_showing_policies_procedure_remark());
        values.put(Key_document_showing_policies_procedure_nc,pojo.getDocument_showing_policies_procedure_nc());
        values.put(Key_document_showing_drugs_available,pojo.getDocument_showing_drugs_available());
        values.put(Key_document_showing_drugs_available_remark,pojo.getDocument_showing_drugs_available_remark());
        values.put(Key_document_showing_drugs_available_nc,pojo.getDocument_showing_drugs_available_nc());
        values.put(key_document_showing_safe_storage,pojo.getDocument_showing_safe_storage());
        values.put(key_document_showing_safe_storage_remark,pojo.getDocument_showing_safe_storage_remark());
        values.put(key_document_showing_safe_storage_nc,pojo.getDocument_showing_safe_storage_nc());
        values.put(Key_Infection_control_manual_showing,pojo.getInfection_control_manual_showing());
        values.put(Key_Infection_control_manual_showing_remark,pojo.getInfection_control_manual_showing_remark());
        values.put(Key_Infection_control_manual_showing_nc,pojo.getInfection_control_manual_showing_nc());
        values.put(Key_document_showing_operational_maintenance,pojo.getDocument_showing_operational_maintenance());
        values.put(Key_document_showing_operational_maintenance_remark,pojo.getDocument_showing_operational_maintenance_remark());
        values.put(Key_document_showing_operational_maintenance_nc,pojo.getDocument_showing_operational_maintenance_nc());
        values.put(Key_document_showing_safe_exit_plan,pojo.getDocument_showing_safe_exit_plan());
        values.put(Key_document_showing_safe_exit_plan_remark,pojo.getDocument_showing_safe_exit_plan_remark());
        values.put(Key_document_showing_safe_exit_plan_nc,pojo.getDocument_showing_safe_exit_plan_nc());
        values.put(Key_document_showing_well_defined_staff,pojo.getDocument_showing_well_defined_staff());
        values.put(Key_document_showing_well_defined_staff_remark,pojo.getDocument_showing_well_defined_staff_remark());
        values.put(Key_document_showing_well_defined_staff_nc,pojo.getDocument_showing_well_defined_staff_nc());
        values.put(Key_document_showing_disciplinary_grievance,pojo.getDocument_showing_disciplinary_grievance());
        values.put(Key_document_showing_disciplinary_grievance_remark,pojo.getDocument_showing_disciplinary_grievance_remark());
        values.put(Key_document_showing_disciplinary_grievance_nc,pojo.getDocument_showing_disciplinary_grievance_nc());
        values.put(Key_document_showing_policies_procedures,pojo.getDocument_showing_policies_procedures());
        values.put(Key_document_showing_policies_procedures_remark,pojo.getDocument_showing_policies_procedures_remark());
        values.put(Key_document_showing_policies_procedures_nc,pojo.getDocument_showing_policies_procedures_nc());
        values.put(Key_document_showing_retention_time,pojo.getDocument_showing_retention_time());
        values.put(Key_document_showing_retention_time_remark,pojo.getDocument_showing_retention_time_remark());
        values.put(Key_document_showing_retention_time_nc,pojo.getDocument_showing_retention_time_nc());
        values.put(Key_document_showing_define_process,pojo.getDocument_showing_define_process());
        values.put(Key_document_showing_define_process_remark,pojo.getDocument_showing_define_process_remark());
        values.put(Key_document_showing_define_process_nc,pojo.getDocument_showing_define_process_nc());
        values.put(Key_document_showing_medical_records,pojo.getDocument_showing_medical_records());
        values.put(Key_document_showing_medical_records_remark,pojo.getDocument_showing_medical_records_remark());
        values.put(Key_document_showing_medical_records_nc,pojo.getDocument_showing_medical_records_nc());

        database.update(Table_Documentation,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }



    public boolean INSERT_STERIALIZATION(SterilizationAreaPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_sterilisation_practices_adhered,pojo.getSterilisation_practices_adhered());
        values.put(Key_sterilisation_practices_adhered_Remark,pojo.getSterilisation_practices_adhered_remark());
        values.put(Key_sterilisation_practices_adhered_Nc,pojo.getSterilisation_practices_adhered_nc());
        values.put(Key_monitor_effectiveness_sterilization_process,pojo.getMonitor_effectiveness_sterilization_process());
        values.put(Key_monitor_effectiveness_sterilization_process_Remark,pojo.getMonitor_effectiveness_sterilization_process_remark());
        values.put(Key_monitor_effectiveness_sterilization_process_Nc,pojo.getMonitor_effectiveness_sterilization_process_nc());
        values.put(Key_monitor_effectiveness_sterilization_process_Image,pojo.getMonitor_effectiveness_sterilization_process_image());
        values.put(Local_Key_monitor_effectiveness_sterilization_process_Image,pojo.getLocal_monitor_effectiveness_sterilization_process_image());
        values.put(Key_sterilized_drums_trays,pojo.getSterilized_drums_trays());
        values.put(Key_sterilized_drums_trays_Remark,pojo.getSterilized_drums_trays_remark());
        values.put(Key_sterilized_drums_trays_Nc,pojo.getSterilized_drums_trays_nc());
        values.put(Key_sterilized_drums_trays_Image,pojo.getSterilized_drums_trays_image());
        values.put(Local_Key_sterilized_drums_trays_Image,pojo.getLocal_sterilized_drums_trays_image());

        database.insert(Table_Sterilization_Area,null,values);


        return true;
    }

    public boolean UPDATE_STERIALIZATION(SterilizationAreaPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_sterilisation_practices_adhered,pojo.getSterilisation_practices_adhered());
        values.put(Key_sterilisation_practices_adhered_Remark,pojo.getSterilisation_practices_adhered_remark());
        values.put(Key_sterilisation_practices_adhered_Nc,pojo.getSterilisation_practices_adhered_nc());
        values.put(Key_monitor_effectiveness_sterilization_process,pojo.getMonitor_effectiveness_sterilization_process());
        values.put(Key_monitor_effectiveness_sterilization_process_Remark,pojo.getMonitor_effectiveness_sterilization_process_remark());
        values.put(Key_monitor_effectiveness_sterilization_process_Nc,pojo.getMonitor_effectiveness_sterilization_process_nc());
        values.put(Key_monitor_effectiveness_sterilization_process_Image,pojo.getMonitor_effectiveness_sterilization_process_image());
        values.put(Local_Key_monitor_effectiveness_sterilization_process_Image,pojo.getLocal_monitor_effectiveness_sterilization_process_image());
        values.put(Key_sterilized_drums_trays,pojo.getSterilized_drums_trays());
        values.put(Key_sterilized_drums_trays_Remark,pojo.getSterilized_drums_trays_remark());
        values.put(Key_sterilized_drums_trays_Nc,pojo.getSterilized_drums_trays_nc());
        values.put(Key_sterilized_drums_trays_Image,pojo.getSterilized_drums_trays_image());
        values.put(Local_Key_sterilized_drums_trays_Image,pojo.getLocal_sterilized_drums_trays_image());

        database.update(Table_Sterilization_Area,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }


    public boolean INSERT_Management(ManagementPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_requisite_fee_BMW,pojo.getRequisite_fee_BMW());
        values.put(Key_requisite_fee_BMW_Remark,pojo.getRequisite_fee_BMW_remark());
        values.put(Key_requisite_fee_BMW_Nc,pojo.getRequisite_fee_BMW_nc());
        values.put(Key_management_guide_organization,pojo.getManagement_guide_organization());
        values.put(Key_management_guide_organization_Remark,pojo.getManagement_guide_organization_remark());
        values.put(Key_management_guide_organization_Nc,pojo.getManagement_guide_organization_nc());
        values.put(Key_hospital_mission_present,pojo.getHospital_mission_present());
        values.put(Key_hospital_mission_present_Remark,pojo.getHospital_mission_present_remark());
        values.put(Key_hospital_mission_present_Nc,pojo.getHospital_mission_present_nc());
        values.put(Key_hospital_mission_present_Image,pojo.getHospital_mission_present_Image());
        values.put(Local_Key_hospital_mission_present_Image,pojo.getLocal_hospital_mission_present_Image());
        values.put(Key_patient_maintained_OPD,pojo.getPatient_maintained_OPD());
        values.put(Key_patient_maintained_OPD_Remark,pojo.getPatient_maintained_OPD_remark());
        values.put(Key_patient_maintained_OPD_Nc,pojo.getPatient_maintained_OPD_nc());
        values.put(Key_patient_maintained_OPD_Image,pojo.getPatient_maintained_OPD_image());
        values.put(Local_Key_patient_maintained_OPD_Image,pojo.getLocal_patient_maintained_OPD_image());
        values.put(Key_patient_maintained_IPD,pojo.getPatient_maintained_IPD());
        values.put(Key_patient_maintained_IPD_Remark,pojo.getPatient_maintained_IPD_remark());
        values.put(Key_patient_maintained_IPD_Nc,pojo.getPatient_maintained_IPD_nc());
        values.put(Key_patient_maintained_IPD_Image,pojo.getPatient_maintained_IPD_image());
        values.put(Local_Key_patient_maintained_IPD_Image,pojo.getLocal_patient_maintained_IPD_image());
        values.put(Key_patient_maintained_Emergency,pojo.getPatient_maintained_Emergency());
        values.put(Key_patient_maintained_Emergency_Remark,pojo.getPatient_maintained_Emergency_remark());
        values.put(Key_patient_maintained_Emergency_Nc,pojo.getPatient_maintained_Emergency_nc());
        values.put(Key_patient_maintained_Emergency_Image,pojo.getPatient_maintained_Emergency_image());
        values.put(Local_Key_patient_maintained_Emergency_Image,pojo.getLocal_patient_maintained_Emergency_image());
        values.put(Key_basic_Tariff_List,pojo.getBasic_Tariff_List());
        values.put(Key_basic_Tariff_List_Remark,pojo.getBasic_Tariff_List_remark());
        values.put(Key_basic_Tariff_List_Nc,pojo.getBasic_Tariff_List_nc());
        values.put(Key_basic_Tariff_List_Image,pojo.getBasic_Tariff_List_image());
        values.put(Local_Key_basic_Tariff_List_Image,pojo.getLocal_basic_Tariff_List_image());
        values.put(Key_parameter_patient_identification,pojo.getParameter_patient_identification());
        values.put(Key_parameter_patient_identification_Remark,pojo.getParameter_patient_identification_remark());
        values.put(Key_parameter_patient_identification_Nc,pojo.getParameter_patient_identification_nc());
        values.put(key_quality_improvement_programme,pojo.getQuality_improvement_programme());
        values.put(key_quality_improvement_programme_Remark,pojo.getQuality_improvement_programme_remark());
        values.put(key_quality_improvement_programme_Nc,pojo.getQuality_improvement_programme_nc());

        database.insert(Table_Management,null,values);

        return true;

    }

    public boolean UPDATE_Management(ManagementPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_requisite_fee_BMW,pojo.getRequisite_fee_BMW());
        values.put(Key_requisite_fee_BMW_Remark,pojo.getRequisite_fee_BMW_remark());
        values.put(Key_requisite_fee_BMW_Nc,pojo.getRequisite_fee_BMW_nc());
        values.put(Key_management_guide_organization,pojo.getManagement_guide_organization());
        values.put(Key_management_guide_organization_Remark,pojo.getManagement_guide_organization_remark());
        values.put(Key_management_guide_organization_Nc,pojo.getManagement_guide_organization_nc());
        values.put(Key_hospital_mission_present,pojo.getHospital_mission_present());
        values.put(Key_hospital_mission_present_Remark,pojo.getHospital_mission_present_remark());
        values.put(Key_hospital_mission_present_Nc,pojo.getHospital_mission_present_nc());
        values.put(Key_hospital_mission_present_Image,pojo.getHospital_mission_present_Image());
        values.put(Local_Key_hospital_mission_present_Image,pojo.getLocal_hospital_mission_present_Image());
        values.put(Key_patient_maintained_OPD,pojo.getPatient_maintained_OPD());
        values.put(Key_patient_maintained_OPD_Remark,pojo.getPatient_maintained_OPD_remark());
        values.put(Key_patient_maintained_OPD_Nc,pojo.getPatient_maintained_OPD_nc());
        values.put(Key_patient_maintained_OPD_Image,pojo.getPatient_maintained_OPD_image());
        values.put(Local_Key_patient_maintained_OPD_Image,pojo.getLocal_patient_maintained_OPD_image());
        values.put(Key_patient_maintained_IPD,pojo.getPatient_maintained_IPD());
        values.put(Key_patient_maintained_IPD_Remark,pojo.getPatient_maintained_IPD_remark());
        values.put(Key_patient_maintained_IPD_Nc,pojo.getPatient_maintained_IPD_nc());
        values.put(Key_patient_maintained_IPD_Image,pojo.getPatient_maintained_IPD_image());
        values.put(Local_Key_patient_maintained_IPD_Image,pojo.getLocal_patient_maintained_IPD_image());
        values.put(Key_patient_maintained_Emergency,pojo.getPatient_maintained_Emergency());
        values.put(Key_patient_maintained_Emergency_Remark,pojo.getPatient_maintained_Emergency_remark());
        values.put(Key_patient_maintained_Emergency_Nc,pojo.getPatient_maintained_Emergency_nc());
        values.put(Key_patient_maintained_Emergency_Image,pojo.getPatient_maintained_Emergency_image());
        values.put(Local_Key_patient_maintained_Emergency_Image,pojo.getLocal_patient_maintained_Emergency_image());
        values.put(Key_basic_Tariff_List,pojo.getBasic_Tariff_List());
        values.put(Key_basic_Tariff_List_Remark,pojo.getBasic_Tariff_List_remark());
        values.put(Key_basic_Tariff_List_Nc,pojo.getBasic_Tariff_List_nc());
        values.put(Key_basic_Tariff_List_Image,pojo.getBasic_Tariff_List_image());
        values.put(Local_Key_basic_Tariff_List_Image,pojo.getLocal_basic_Tariff_List_image());
        values.put(Key_parameter_patient_identification,pojo.getParameter_patient_identification());
        values.put(Key_parameter_patient_identification_Remark,pojo.getParameter_patient_identification_remark());
        values.put(Key_parameter_patient_identification_Nc,pojo.getParameter_patient_identification_nc());
        values.put(key_quality_improvement_programme,pojo.getQuality_improvement_programme());
        values.put(key_quality_improvement_programme_Remark,pojo.getQuality_improvement_programme_remark());
        values.put(key_quality_improvement_programme_Nc,pojo.getQuality_improvement_programme_nc());

        database.update(Table_Management,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;

    }


    public boolean INSERT_Bio_medical_engineering(BioMedicalEngineeringPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_Maintenance_staff_contactable,pojo.getMaintenance_staff_contactable());
        values.put(Key_Maintenance_staff_contactable_Remark,pojo.getMaintenance_staff_contactable_remark());
        values.put(Key_Maintenance_staff_contactable_Nc,pojo.getMaintenance_staff_contactable_nc());
        values.put(key_equipment_accordance_services,pojo.getEquipment_accordance_services());
        values.put(key_equipment_accordance_services_Remark,pojo.getEquipment_accordance_services_remark());
        values.put(key_equipment_accordance_services_Nc,pojo.getEquipment_accordance_services_nc());
        values.put(Key_documented_operational_maintenance,pojo.getDocumented_operational_maintenance());
        values.put(Key_documented_operational_maintenance_Remark,pojo.getDocumented_operational_maintenance_remark());
        values.put(Key_documented_operational_maintenance_Nc,pojo.getDocumented_operational_maintenance_nc());
        values.put(Key_documented_operational_maintenance_Image,pojo.getDocumented_operational_maintenance_image());
        values.put(Local_Key_documented_operational_maintenance_Image,pojo.getLocal_documented_operational_maintenance_image());

        database.insert(Table_Bio_medical_engineering,null,values);

        return true;
    }

    public boolean UPDATE_Bio_medical_engineering(BioMedicalEngineeringPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_Maintenance_staff_contactable,pojo.getMaintenance_staff_contactable());
        values.put(Key_Maintenance_staff_contactable_Remark,pojo.getMaintenance_staff_contactable_remark());
        values.put(Key_Maintenance_staff_contactable_Nc,pojo.getMaintenance_staff_contactable_nc());
        values.put(key_equipment_accordance_services,pojo.getEquipment_accordance_services());
        values.put(key_equipment_accordance_services_Remark,pojo.getEquipment_accordance_services_remark());
        values.put(key_equipment_accordance_services_Nc,pojo.getEquipment_accordance_services_nc());
        values.put(Key_documented_operational_maintenance,pojo.getDocumented_operational_maintenance());
        values.put(Key_documented_operational_maintenance_Remark,pojo.getDocumented_operational_maintenance_remark());
        values.put(Key_documented_operational_maintenance_Nc,pojo.getDocumented_operational_maintenance_nc());
        values.put(Key_documented_operational_maintenance_Image,pojo.getDocumented_operational_maintenance_image());
        values.put(Local_Key_documented_operational_maintenance_Image,pojo.getLocal_documented_operational_maintenance_image());

        database.update(Table_Bio_medical_engineering,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }

    public boolean INSERT_Facility_Checks(FacilityChecksPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_medical_gas_cylinders,pojo.getMedical_gas_cylinders());
        values.put(Key_medical_gas_cylinders_Remark,pojo.getMedical_gas_cylinders_remark());
        values.put(Key_medical_gas_cylinders_Nc,pojo.getMedical_gas_cylinders_nc());
        values.put(Key_medical_gas_cylinders_Image,pojo.getMedical_gas_cylinders_image());
        values.put(Local_Key_medical_gas_cylinders_Image,pojo.getLocal_medical_gas_cylinders_image());
        values.put(Key_smoke_detectors_installed,pojo.getSmoke_detectors_installed());
        values.put(Key_smoke_detectors_installed_Remark,pojo.getSmoke_detectors_installed_remark());
        values.put(Key_smoke_detectors_installed_Nc,pojo.getSmoke_detectors_installed_nc());
        values.put(Key_smoke_detectors_installed_Image,pojo.getSmoke_detectors_installed_image());
        values.put(Local_Key_smoke_detectors_installed_Image,pojo.getLocal_smoke_detectors_installed_image());
        values.put(Key_extinguisher_present_patient,pojo.getExtinguisher_present_patient());
        values.put(Key_extinguisher_present_patient_Remark,pojo.getExtinguisher_present_patient_remark());
        values.put(Key_extinguisher_present_patient_Nc,pojo.getExtinguisher_present_patient_nc());
        values.put(Key_extinguisher_present_patient_Image,pojo.getExtinguisher_present_patient_image());
        values.put(Local_Key_extinguisher_present_patient_Image,pojo.getLocal_extinguisher_present_patient_image());
        values.put(Key_fire_fighting_equipment,pojo.getFire_fighting_equipment());
        values.put(Key_fire_fighting_equipment_Remark,pojo.getFire_fighting_equipment_remark());
        values.put(Key_fire_fighting_equipment_Nc,pojo.getFire_fighting_equipment_nc());
        values.put(Key_safe_exit_plan,pojo.getSafe_exit_plan());
        values.put(Key_safe_exit_plan_Remark,pojo.getSafe_exit_plan_remark());
        values.put(Key_safe_exit_plan_Nc,pojo.getSafe_exit_plan_nc());

        database.insert(Table_Facility_Checks,null,values);

        return true;

    }

    public boolean UPDATE_Facility_Checks(FacilityChecksPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_medical_gas_cylinders,pojo.getMedical_gas_cylinders());
        values.put(Key_medical_gas_cylinders_Remark,pojo.getMedical_gas_cylinders_remark());
        values.put(Key_medical_gas_cylinders_Nc,pojo.getMedical_gas_cylinders_nc());
        values.put(Key_medical_gas_cylinders_Image,pojo.getMedical_gas_cylinders_image());
        values.put(Local_Key_medical_gas_cylinders_Image,pojo.getLocal_medical_gas_cylinders_image());
        values.put(Key_smoke_detectors_installed,pojo.getSmoke_detectors_installed());
        values.put(Key_smoke_detectors_installed_Remark,pojo.getSmoke_detectors_installed_remark());
        values.put(Key_smoke_detectors_installed_Nc,pojo.getSmoke_detectors_installed_nc());
        values.put(Key_smoke_detectors_installed_Image,pojo.getSmoke_detectors_installed_image());
        values.put(Local_Key_smoke_detectors_installed_Image,pojo.getLocal_smoke_detectors_installed_image());
        values.put(Key_extinguisher_present_patient,pojo.getExtinguisher_present_patient());
        values.put(Key_extinguisher_present_patient_Remark,pojo.getExtinguisher_present_patient_remark());
        values.put(Key_extinguisher_present_patient_Nc,pojo.getExtinguisher_present_patient_nc());
        values.put(Key_extinguisher_present_patient_Image,pojo.getExtinguisher_present_patient_image());
        values.put(Local_Key_extinguisher_present_patient_Image,pojo.getLocal_extinguisher_present_patient_image());
        values.put(Key_fire_fighting_equipment,pojo.getFire_fighting_equipment());
        values.put(Key_fire_fighting_equipment_Remark,pojo.getFire_fighting_equipment_remark());
        values.put(Key_fire_fighting_equipment_Nc,pojo.getFire_fighting_equipment_nc());
        values.put(Key_safe_exit_plan,pojo.getSafe_exit_plan());
        values.put(Key_safe_exit_plan_Remark,pojo.getSafe_exit_plan_remark());
        values.put(Key_safe_exit_plan_Nc,pojo.getSafe_exit_plan_nc());

        database.update(Table_Facility_Checks,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;

    }

    public boolean INSERT_SAFETY_MANAGEMENT(SafetManagementPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_safety_device_lab,pojo.getSafety_device_lab());
        values.put(Key_safety_device_lab_Remark,pojo.getSafety_device_lab_remark());
        values.put(Key_safety_device_lab_Nc,pojo.getSafety_device_lab_nc());
        values.put(Key_safety_device_lab_Image,pojo.getSafety_device_lab_image());
        values.put(Local_Key_safety_device_lab_Image,pojo.getLocal_safety_device_lab_image());
        values.put(Key_body_parts_staff_patients,pojo.getBody_parts_staff_patients());
        values.put(Key_body_parts_staff_patients_Remark,pojo.getBody_parts_staff_patients_remark());
        values.put(Key_body_parts_staff_patients_Nc,pojo.getBody_parts_staff_patients_nc());
        values.put(Key_body_parts_staff_patients_Image,pojo.getBody_parts_staff_patients_image());
        values.put(Local_Key_body_parts_staff_patients_Image,pojo.getLocal_body_parts_staff_patients_image());
        values.put(Key_staff_member_radiation_area,pojo.getStaff_member_radiation_area());
        values.put(Key_staff_member_radiation_area_Remark,pojo.getStaff_member_radiation_area_remark());
        values.put(Key_staff_member_radiation_area_Nc,pojo.getStaff_member_radiation_area_nc());
        values.put(Key_staff_member_radiation_area_Image,pojo.getStaff_member_radiation_area_image());
        values.put(Local_Key_staff_member_radiation_area_Image,pojo.getLocal_staff_member_radiation_area_image());
        values.put(Key_standardised_colur_coding,pojo.getStandardised_colur_coding());
        values.put(Key_standardised_colur_coding_Remark,pojo.getStandardised_colur_coding_remark());
        values.put(Key_standardised_colur_coding_Nc,pojo.getStandardised_colur_coding_nc());
        values.put(Key_standardised_colur_coding_Image,pojo.getStandardised_colur_coding_image());
        values.put(Local_Key_standardised_colur_coding_Image,pojo.getLocal_standardised_colur_coding_image());
        values.put(Key_safe_storage_medical,pojo.getSafe_storage_medical());
        values.put(Key_safe_storage_medical_Remark,pojo.getSafe_storage_medical_remark());
        values.put(Key_safe_storage_medical_Nc,pojo.getSafe_storage_medical_nc());
        values.put(Key_safe_storage_medical_Image,pojo.getSafe_storage_medical_image());
        values.put(Local_Key_safe_storage_medical_Image,pojo.getLocal_safe_storage_medical_image());


        database.insert(Table_Safety_Management,null,values);

        return true;

    }

    public boolean UPDATE_SAFETY_MANAGEMENT(SafetManagementPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_safety_device_lab,pojo.getSafety_device_lab());
        values.put(Key_safety_device_lab_Remark,pojo.getSafety_device_lab_remark());
        values.put(Key_safety_device_lab_Nc,pojo.getSafety_device_lab_nc());
        values.put(Key_safety_device_lab_Image,pojo.getSafety_device_lab_image());
        values.put(Local_Key_safety_device_lab_Image,pojo.getLocal_safety_device_lab_image());
        values.put(Key_body_parts_staff_patients,pojo.getBody_parts_staff_patients());
        values.put(Key_body_parts_staff_patients_Remark,pojo.getBody_parts_staff_patients_remark());
        values.put(Key_body_parts_staff_patients_Nc,pojo.getBody_parts_staff_patients_nc());
        values.put(Key_body_parts_staff_patients_Image,pojo.getBody_parts_staff_patients_image());
        values.put(Local_Key_body_parts_staff_patients_Image,pojo.getLocal_body_parts_staff_patients_image());
        values.put(Key_staff_member_radiation_area,pojo.getStaff_member_radiation_area());
        values.put(Key_staff_member_radiation_area_Remark,pojo.getStaff_member_radiation_area_remark());
        values.put(Key_staff_member_radiation_area_Nc,pojo.getStaff_member_radiation_area_nc());
        values.put(Key_staff_member_radiation_area_Image,pojo.getStaff_member_radiation_area_image());
        values.put(Local_Key_staff_member_radiation_area_Image,pojo.getLocal_staff_member_radiation_area_image());
        values.put(Key_standardised_colur_coding,pojo.getStandardised_colur_coding());
        values.put(Key_standardised_colur_coding_Remark,pojo.getStandardised_colur_coding_remark());
        values.put(Key_standardised_colur_coding_Nc,pojo.getStandardised_colur_coding_nc());
        values.put(Key_standardised_colur_coding_Image,pojo.getStandardised_colur_coding_image());
        values.put(Local_Key_standardised_colur_coding_Image,pojo.getLocal_standardised_colur_coding_image());
        values.put(Key_safe_storage_medical,pojo.getSafe_storage_medical());
        values.put(Key_safe_storage_medical_Remark,pojo.getSafe_storage_medical_remark());
        values.put(Key_safe_storage_medical_Nc,pojo.getSafe_storage_medical_nc());
        values.put(Key_safe_storage_medical_Image,pojo.getSafe_storage_medical_image());
        values.put(Local_Key_safe_storage_medical_Image,pojo.getLocal_safe_storage_medical_image());


        database.update(Table_Safety_Management,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;

    }


    public boolean INSERT_Ambulance_Accessibility(AmbulanceAccessibilityPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_access_ambulance_patient_drop,pojo.getAccess_ambulance_patient_drop());
        values.put(Key_access_ambulance_patient_drop_remark,pojo.getAccess_ambulance_patient_drop_remark());
        values.put(Key_access_ambulance_patient_drop_nc,pojo.getAccess_ambulance_patient_drop_nc());
        values.put(Key_ownership_the_ambulance,pojo.getOwnership_the_ambulance());
        values.put(Key_ownership_the_ambulance_remark,pojo.getOwnership_the_ambulance_remark());
        values.put(Key_ownership_the_ambulance_Nc,pojo.getOwnership_the_ambulance_nc());
        values.put(Key_total_number_ambulance_available,pojo.getTotal_number_ambulance_available());
        values.put(Key_total_number_ambulance_available_Remark,pojo.getTotal_number_ambulance_available_remark());
        values.put(Key_total_number_ambulance_available_Nc,pojo.getTotal_number_ambulance_available_nc());
        values.put(Key_total_number_ambulance_available_Image,pojo.getTotal_number_ambulance_available_image());
        values.put(Local_Key_total_number_ambulance_available_Image,pojo.getLocal_total_number_ambulance_available_image());
        values.put(Key_ambulance_appropriately_equiped,pojo.getAmbulance_appropriately_equiped());
        values.put(Key_ambulance_appropriately_equiped_Remark,pojo.getAmbulance_appropriately_equiped_remark());
        values.put(Key_ambulance_appropriately_equiped_Nc,pojo.getAmbulance_appropriately_equiped_nc());
        values.put(Key_ambulance_appropriately_equiped_Image,pojo.getAmbulance_appropriately_equiped_image());
        values.put(Local_Key_ambulance_appropriately_equiped_Image,pojo.getLocal_ambulance_appropriately_equiped_image());
        values.put(Key_drivers_ambulances_available,pojo.getDrivers_ambulances_available());
        values.put(Key_drivers_ambulances_available_Remark,pojo.getDrivers_ambulances_available_remark());
        values.put(Key_drivers_ambulances_available_Nc,pojo.getDrivers_ambulances_available_nc());
        values.put(Key_drivers_ambulances_available_Image,pojo.getDrivers_ambulances_available_image());
        values.put(Local_Key_drivers_ambulances_available_Image,pojo.getLocal_drivers_ambulances_available_image());
        values.put(Key_doctors_available_ambulances,pojo.getDoctors_available_ambulances());
        values.put(Key_doctors_available_ambulances_Remark,pojo.getDoctors_available_ambulances_remark());
        values.put(Key_doctors_available_ambulances_Nc,pojo.getDoctors_available_ambulances_nc());
        values.put(Key_doctors_available_ambulances_Image,pojo.getDoctors_available_ambulances_image());
        values.put(Local_Key_doctors_available_ambulances_Image,pojo.getLocal_doctors_available_ambulances_image());
        values.put(Key_nurses_available_ambulances,pojo.getNurses_available_ambulances());
        values.put(Key_nurses_available_ambulances_Remark,pojo.getNurses_available_ambulances_remark());
        values.put(Key_nurses_available_ambulances_Nc,pojo.getNurses_available_ambulances_nc());
        values.put(Key_nurses_available_ambulances_Image,pojo.getNurses_available_ambulances_image());
        values.put(Local_Key_nurses_available_ambulances_Image,pojo.getLocal_nurses_available_ambulances_image());


        database.insert(Table_Ambulance_Accessibility,null,values);

        return true;
    }


    public boolean UPDATE_Ambulance_Accessibility(AmbulanceAccessibilityPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_access_ambulance_patient_drop,pojo.getAccess_ambulance_patient_drop());
        values.put(Key_access_ambulance_patient_drop_remark,pojo.getAccess_ambulance_patient_drop_remark());
        values.put(Key_access_ambulance_patient_drop_nc,pojo.getAccess_ambulance_patient_drop_nc());
        values.put(Key_ownership_the_ambulance,pojo.getOwnership_the_ambulance());
        values.put(Key_ownership_the_ambulance_remark,pojo.getOwnership_the_ambulance_remark());
        values.put(Key_ownership_the_ambulance_Nc,pojo.getOwnership_the_ambulance_nc());
        values.put(Key_total_number_ambulance_available,pojo.getTotal_number_ambulance_available());
        values.put(Key_total_number_ambulance_available_Remark,pojo.getTotal_number_ambulance_available_remark());
        values.put(Key_total_number_ambulance_available_Nc,pojo.getTotal_number_ambulance_available_nc());
        values.put(Key_total_number_ambulance_available_Image,pojo.getTotal_number_ambulance_available_image());
        values.put(Local_Key_total_number_ambulance_available_Image,pojo.getLocal_total_number_ambulance_available_image());
        values.put(Key_ambulance_appropriately_equiped,pojo.getAmbulance_appropriately_equiped());
        values.put(Key_ambulance_appropriately_equiped_Remark,pojo.getAmbulance_appropriately_equiped_remark());
        values.put(Key_ambulance_appropriately_equiped_Nc,pojo.getAmbulance_appropriately_equiped_nc());
        values.put(Key_ambulance_appropriately_equiped_Image,pojo.getAmbulance_appropriately_equiped_image());
        values.put(Local_Key_ambulance_appropriately_equiped_Image,pojo.getLocal_ambulance_appropriately_equiped_image());
        values.put(Key_drivers_ambulances_available,pojo.getDrivers_ambulances_available());
        values.put(Key_drivers_ambulances_available_Remark,pojo.getDrivers_ambulances_available_remark());
        values.put(Key_drivers_ambulances_available_Nc,pojo.getDrivers_ambulances_available_nc());
        values.put(Key_drivers_ambulances_available_Image,pojo.getDrivers_ambulances_available_image());
        values.put(Local_Key_drivers_ambulances_available_Image,pojo.getLocal_drivers_ambulances_available_image());
        values.put(Key_doctors_available_ambulances,pojo.getDoctors_available_ambulances());
        values.put(Key_doctors_available_ambulances_Remark,pojo.getDoctors_available_ambulances_remark());
        values.put(Key_doctors_available_ambulances_Nc,pojo.getDoctors_available_ambulances_nc());
        values.put(Key_doctors_available_ambulances_Image,pojo.getDoctors_available_ambulances_image());
        values.put(Local_Key_doctors_available_ambulances_Image,pojo.getLocal_doctors_available_ambulances_image());
        values.put(Key_nurses_available_ambulances,pojo.getNurses_available_ambulances());
        values.put(Key_nurses_available_ambulances_Remark,pojo.getNurses_available_ambulances_remark());
        values.put(Key_nurses_available_ambulances_Nc,pojo.getNurses_available_ambulances_nc());
        values.put(Key_nurses_available_ambulances_Image,pojo.getNurses_available_ambulances_image());
        values.put(Local_Key_nurses_available_ambulances_Image,pojo.getLocal_nurses_available_ambulances_image());


        database.update(Table_Ambulance_Accessibility,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }


    public boolean INSERT_Uniform_Signage(UniformSignagePojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_scope_services_present,pojo.getScope_services_present());
        values.put(Key_scope_services_present_Remark,pojo.getScope_services_present_remark());
        values.put(Key_scope_services_present_Nc,pojo.getScope_services_present_nc());
        values.put(Key_scope_services_present_Image,pojo.getScope_services_present_image());
        values.put(Local_Key_scope_services_present_Image,pojo.getLocal_scope_services_present_image());
        values.put(Key_Patients_responsibility_displayed,pojo.getPatients_responsibility_displayed());
        values.put(Key_Patients_responsibility_displayed_Remark,pojo.getPatients_responsibility_displayed_remark());
        values.put(Key_Patients_responsibility_displayed_Nc,pojo.getPatients_responsibility_displayed_nc());
        values.put(Key_Patients_responsibility_displayed_Image,pojo.getPatients_responsibility_displayed_image());
        values.put(Local_Key_Patients_responsibility_displayed_Image,pojo.getLocal_Patients_responsibility_displayed_image());
        values.put(Key_fire_exit_signage_present,pojo.getFire_exit_signage_present());
        values.put(Key_fire_exit_signage_present_Remark,pojo.getFire_exit_signage_present_remark());
        values.put(Key_fire_exit_signage_present_Nc,pojo.getFire_exit_signage_present_nc());
        values.put(Key_fire_exit_signage_present_Image,pojo.getFire_exit_signage_present_image());
        values.put(Local_Key_fire_exit_signage_present_Image,pojo.getLocal_fire_exit_signage_present_image());
        values.put(Key_directional_signages_present,pojo.getDirectional_signages_present());
        values.put(Key_directional_signages_present_Remark,pojo.getDirectional_signages_present_remark());
        values.put(Key_directional_signages_present_Nc,pojo.getDirectional_signages_present_Nc());
        values.put(Key_directional_signages_present_Image,pojo.getDirectional_signages_present_image());
        values.put(Local_Key_directional_signages_present_Image,pojo.getLocal_directional_signages_present_image());
        values.put(Key_departmental_signages_present,pojo.getDepartmental_signages_present());
        values.put(Key_departmental_signages_present_Remark,pojo.getDepartmental_signages_present_remark());
        values.put(Key_departmental_signages_present_Nc,pojo.getDepartmental_signages_present_nc());
        values.put(Key_departmental_signages_present_Image,pojo.getDepartmental_signages_present_image());
        values.put(Local_Key_departmental_signages_present_Image,pojo.getLocal_departmental_signages_present_image());


        database.insert(Table_Uniform_Signage,null,values);

        return true;
    }

    public boolean UPDATE_Uniform_Signage(UniformSignagePojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_scope_services_present,pojo.getScope_services_present());
        values.put(Key_scope_services_present_Remark,pojo.getScope_services_present_remark());
        values.put(Key_scope_services_present_Nc,pojo.getScope_services_present_nc());
        values.put(Key_scope_services_present_Image,pojo.getScope_services_present_image());
        values.put(Local_Key_scope_services_present_Image,pojo.getLocal_scope_services_present_image());
        values.put(Key_Patients_responsibility_displayed,pojo.getPatients_responsibility_displayed());
        values.put(Key_Patients_responsibility_displayed_Remark,pojo.getPatients_responsibility_displayed_remark());
        values.put(Key_Patients_responsibility_displayed_Nc,pojo.getPatients_responsibility_displayed_nc());
        values.put(Key_Patients_responsibility_displayed_Image,pojo.getPatients_responsibility_displayed_image());
        values.put(Local_Key_Patients_responsibility_displayed_Image,pojo.getLocal_Patients_responsibility_displayed_image());
        values.put(Key_fire_exit_signage_present,pojo.getFire_exit_signage_present());
        values.put(Key_fire_exit_signage_present_Remark,pojo.getFire_exit_signage_present_remark());
        values.put(Key_fire_exit_signage_present_Nc,pojo.getFire_exit_signage_present_nc());
        values.put(Key_fire_exit_signage_present_Image,pojo.getFire_exit_signage_present_image());
        values.put(Local_Key_fire_exit_signage_present_Image,pojo.getLocal_fire_exit_signage_present_image());
        values.put(Key_directional_signages_present,pojo.getDirectional_signages_present());
        values.put(Key_directional_signages_present_Remark,pojo.getDirectional_signages_present_remark());
        values.put(Key_directional_signages_present_Nc,pojo.getDirectional_signages_present_Nc());
        values.put(Key_directional_signages_present_Image,pojo.getDirectional_signages_present_image());
        values.put(Local_Key_directional_signages_present_Image,pojo.getLocal_directional_signages_present_image());
        values.put(Key_departmental_signages_present,pojo.getDepartmental_signages_present());
        values.put(Key_departmental_signages_present_Remark,pojo.getDepartmental_signages_present_remark());
        values.put(Key_departmental_signages_present_Nc,pojo.getDepartmental_signages_present_nc());
        values.put(Key_departmental_signages_present_Image,pojo.getDepartmental_signages_present_image());
        values.put(Local_Key_departmental_signages_present_Image,pojo.getLocal_departmental_signages_present_image());


        database.update(Table_Uniform_Signage,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }

    public boolean INSERT_ASSESSMENT_STATUS(ArrayList<AssessmentStatusPojo> list){

        SQLiteDatabase database = this.getWritableDatabase();

        for (int i=0; i< list.size();i++){
            ContentValues values = new ContentValues();
            values.put(KEY_HOSPITAL_ID,list.get(i).getHospital_id());
            values.put(KEY_ASSESSEMENT_NAME,list.get(i).getAssessement_name());
            values.put(KEY_ASSESSEMENT_STATUS,list.get(i).getAssessement_status());


            database.insert(TABLE_ASSESSEMENT_STATUS,null,values);
        }

        return true;
    }



    public boolean UPDATE_ASSESSMENT_STATUS(AssessmentStatusPojo pojo){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(KEY_ASSESSEMENT_NAME,pojo.getAssessement_name());
        values.put(KEY_ASSESSEMENT_STATUS,pojo.getAssessement_status());

        database.update(TABLE_ASSESSEMENT_STATUS,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }



    public boolean INSERT_HRM(HRMPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_staff_health_related_issues,pojo.getStaff_health_related_issues());
        values.put(Key_staff_health_related_issues_Remark,pojo.getStaff_health_related_issues_remark());
        values.put(Key_staff_health_related_issues_Nc,pojo.getStaff_health_related_issues_nc());
        values.put(Key_staffs_personal_files_maintained,pojo.getStaffs_personal_files_maintained());
        values.put(Key_staffs_personal_files_maintained_Remark,pojo.getStaffs_personal_files_maintained_remark());
        values.put(Key_staffs_personal_files_maintained_Nc,pojo.getStaffs_personal_files_maintained_nc());
        values.put(Key_staffs_personal_files_maintained_image,pojo.getStaffs_personal_files_maintained_image());
        values.put(Local_Key_staffs_personal_files_maintained_image,pojo.getLocal_staffs_personal_files_maintained_image());
        values.put(Key_occupational_health_hazards,pojo.getOccupational_health_hazards());
        values.put(Key_occupational_health_hazards_Remark,pojo.getOccupational_health_hazards_remark());
        values.put(Key_occupational_health_hazards_Nc,pojo.getOccupational_health_hazards_nc());
        values.put(Key_training_responsibility_changes,pojo.getTraining_responsibility_changes());
        values.put(Key_training_responsibility_changes_Remark,pojo.getTraining_responsibility_changes_remark());
        values.put(Key_training_responsibility_changes_Nc,pojo.getTraining_responsibility_changes_nc());
        values.put(Key_medical_records_doctors_retrievable,pojo.getMedical_records_doctors_retrievable());
        values.put(Key_medical_records_doctors_retrievable_Remark,pojo.getMedical_records_doctors_retrievable_remark());
        values.put(Key_medical_records_doctors_retrievable_Nc,pojo.getMedical_records_doctors_retrievable_nc());
        values.put(Key_case_of_grievances,pojo.getCase_of_grievances());
        values.put(Key_case_of_grievances_Remark,pojo.getCase_of_grievances_remark());
        values.put(Key_case_of_grievances_Nc,pojo.getCase_of_grievances_nc());
        values.put(Key_case_of_grievances_Image,pojo.getCase_of_grievances_video());
        values.put(Local_Key_case_of_grievances_Image,pojo.getLocal_case_of_grievances_image());
        values.put(Key_staff_disciplinary_procedure,pojo.getStaff_disciplinary_procedure());
        values.put(Key_staff_disciplinary_procedure_Remark,pojo.getStaff_disciplinary_procedure_remark());
        values.put(Key_staff_disciplinary_procedure_Nc,pojo.getStaff_disciplinary_procedure_nc());
        values.put(Key_staff_disciplinary_procedure_Video,pojo.getStaff_disciplinary_procedure_video());
        values.put(Local_Key_staff_disciplinary_procedure_Video,pojo.getLocal_staff_disciplinary_procedure_video());
        values.put(Key_staff_able_to_demonstrate,pojo.getStaff_able_to_demonstrate());
        values.put(Key_staff_able_to_demonstrate_Remark,pojo.getStaff_able_to_demonstrate_remark());
        values.put(Key_staff_able_to_demonstrate_Nc,pojo.getStaff_able_to_demonstrate_nc());


        database.insert(Table_HRM,null,values);


        return true;
    }

    public boolean UPDATE_HRM(HRMPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_staff_health_related_issues,pojo.getStaff_health_related_issues());
        values.put(Key_staff_health_related_issues_Remark,pojo.getStaff_health_related_issues_remark());
        values.put(Key_staff_health_related_issues_Nc,pojo.getStaff_health_related_issues_nc());
        values.put(Key_staffs_personal_files_maintained,pojo.getStaffs_personal_files_maintained());
        values.put(Key_staffs_personal_files_maintained_Remark,pojo.getStaffs_personal_files_maintained_remark());
        values.put(Key_staffs_personal_files_maintained_Nc,pojo.getStaffs_personal_files_maintained_nc());
        values.put(Key_staffs_personal_files_maintained_image,pojo.getStaffs_personal_files_maintained_image());
        values.put(Local_Key_staffs_personal_files_maintained_image,pojo.getLocal_staffs_personal_files_maintained_image());
        values.put(Key_occupational_health_hazards,pojo.getOccupational_health_hazards());
        values.put(Key_occupational_health_hazards_Remark,pojo.getOccupational_health_hazards_remark());
        values.put(Key_occupational_health_hazards_Nc,pojo.getOccupational_health_hazards_nc());
        values.put(Key_training_responsibility_changes,pojo.getTraining_responsibility_changes());
        values.put(Key_training_responsibility_changes_Remark,pojo.getTraining_responsibility_changes_remark());
        values.put(Key_training_responsibility_changes_Nc,pojo.getTraining_responsibility_changes_nc());
        values.put(Key_medical_records_doctors_retrievable,pojo.getMedical_records_doctors_retrievable());
        values.put(Key_medical_records_doctors_retrievable_Remark,pojo.getMedical_records_doctors_retrievable_remark());
        values.put(Key_medical_records_doctors_retrievable_Nc,pojo.getMedical_records_doctors_retrievable_nc());
        values.put(Key_case_of_grievances,pojo.getCase_of_grievances());
        values.put(Key_case_of_grievances_Remark,pojo.getCase_of_grievances_remark());
        values.put(Key_case_of_grievances_Nc,pojo.getCase_of_grievances_nc());
        values.put(Key_case_of_grievances_Image,pojo.getCase_of_grievances_video());
        values.put(Local_Key_case_of_grievances_Image,pojo.getLocal_case_of_grievances_image());
        values.put(Key_staff_disciplinary_procedure,pojo.getStaff_disciplinary_procedure());
        values.put(Key_staff_disciplinary_procedure_Remark,pojo.getStaff_disciplinary_procedure_remark());
        values.put(Key_staff_disciplinary_procedure_Nc,pojo.getStaff_disciplinary_procedure_nc());
        values.put(Key_staff_disciplinary_procedure_Video,pojo.getStaff_disciplinary_procedure_video());
        values.put(Local_Key_staff_disciplinary_procedure_Video,pojo.getLocal_staff_disciplinary_procedure_video());
        values.put(Key_staff_able_to_demonstrate,pojo.getStaff_able_to_demonstrate());
        values.put(Key_staff_able_to_demonstrate_Remark,pojo.getStaff_able_to_demonstrate_remark());
        values.put(Key_staff_able_to_demonstrate_Nc,pojo.getStaff_able_to_demonstrate_nc());

        database.update(Table_HRM,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;


    }

    public boolean INSERT_HOUSEKEEPING(HousekeepingPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_infected_patient_room,pojo.getInfected_patient_room());
        values.put(Key_infected_patient_room_Remark,pojo.getInfected_patient_room_remark());
        values.put(Key_infected_patient_room_Nc,pojo.getInfected_patient_room_nc());
        values.put(Key_procedure_cleaning_room,pojo.getProcedure_cleaning_room());
        values.put(Key_procedure_cleaning_room_Remark,pojo.getProcedure_cleaning_room_remark());
        values.put(Key_procedure_cleaning_room_Nc,pojo.getProcedure_cleaning_room_nc());
        values.put(Key_procedure_cleaning_blood_spill,pojo.getProcedure_cleaning_blood_spill());
        values.put(Key_procedure_cleaning_blood_spill_Remark,pojo.getProcedure_cleaning_blood_spill_remark());
        values.put(Key_procedure_cleaning_blood_spill_Nc,pojo.getProcedure_cleaning_blood_spill_nc());
        values.put(Key_procedure_cleaning_blood_spill_Video,pojo.getProcedure_cleaning_blood_spill_video());
        values.put(Local_Key_procedure_cleaning_blood_spill_Video,pojo.getLocal_procedure_cleaning_blood_spill_video());
        values.put(Key_Biomedical_Waste_regulations,pojo.getBiomedical_Waste_regulations());
        values.put(Key_Biomedical_Waste_regulations_Remark,pojo.getBiomedical_Waste_regulations_remark());
        values.put(Key_Biomedical_Waste_regulations_Nc,pojo.getBiomedical_Waste_regulations_nc());
        values.put(Key_Biomedical_Waste_regulations_Image,pojo.getBiomedical_Waste_regulations_image());
        values.put(Local_Key_Biomedical_Waste_regulations_Image,pojo.getLocal_Biomedical_Waste_regulations_image());
        values.put(Key_cleaning_washing_blood_stained,pojo.getCleaning_washing_blood_stained());
        values.put(Key_cleaning_washing_blood_stained_Remark,pojo.getCleaning_washing_blood_stained_remark());
        values.put(Key_cleaning_washing_blood_stained_Nc,pojo.getCleaning_washing_blood_stained_nc());


        database.insert(Table_Housekeeping,null,values);


        return true;

    }

    public boolean UPDATE_HOUSEKEEPING(HousekeepingPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_infected_patient_room,pojo.getInfected_patient_room());
        values.put(Key_infected_patient_room_Remark,pojo.getInfected_patient_room_remark());
        values.put(Key_infected_patient_room_Nc,pojo.getInfected_patient_room_nc());
        values.put(Key_procedure_cleaning_room,pojo.getProcedure_cleaning_room());
        values.put(Key_procedure_cleaning_room_Remark,pojo.getProcedure_cleaning_room_remark());
        values.put(Key_procedure_cleaning_room_Nc,pojo.getProcedure_cleaning_room_nc());
        values.put(Key_procedure_cleaning_blood_spill,pojo.getProcedure_cleaning_blood_spill());
        values.put(Key_procedure_cleaning_blood_spill_Remark,pojo.getProcedure_cleaning_blood_spill_remark());
        values.put(Key_procedure_cleaning_blood_spill_Nc,pojo.getProcedure_cleaning_blood_spill_nc());
        values.put(Key_procedure_cleaning_blood_spill_Video,pojo.getProcedure_cleaning_blood_spill_video());
        values.put(Local_Key_procedure_cleaning_blood_spill_Video,pojo.getLocal_procedure_cleaning_blood_spill_video());
        values.put(Key_Biomedical_Waste_regulations,pojo.getBiomedical_Waste_regulations());
        values.put(Key_Biomedical_Waste_regulations_Remark,pojo.getBiomedical_Waste_regulations_remark());
        values.put(Key_Biomedical_Waste_regulations_Nc,pojo.getBiomedical_Waste_regulations_nc());
        values.put(Key_Biomedical_Waste_regulations_Image,pojo.getBiomedical_Waste_regulations_image());
        values.put(Local_Key_Biomedical_Waste_regulations_Image,pojo.getLocal_Biomedical_Waste_regulations_image());
        values.put(Key_cleaning_washing_blood_stained,pojo.getCleaning_washing_blood_stained());
        values.put(Key_cleaning_washing_blood_stained_Remark,pojo.getCleaning_washing_blood_stained_remark());
        values.put(Key_cleaning_washing_blood_stained_Nc,pojo.getCleaning_washing_blood_stained_nc());

        database.update(Table_Housekeeping,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;

    }


    public boolean INSERT_MRD(MRDPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_medical_record_death_certificate,pojo.getMedical_record_death_certificate());
        values.put(Key_medical_record_death_certificate_Remark,pojo.getMedical_record_death_certificate_remark());
        values.put(Key_medical_record_death_certificate_Nc,pojo.getMedical_record_death_certificate_nc());
        values.put(Key_documented_maintaining_confidentiality,pojo.getDocumented_maintaining_confidentiality());
        values.put(Key_documented_maintaining_confidentiality_Remark,pojo.getDocumented_maintaining_confidentiality_remark());
        values.put(Key_documented_maintaining_confidentiality_Nc,pojo.getDocumented_maintaining_confidentiality_nc());
        values.put(Key_any_information_disclosed,pojo.getAny_information_disclosed());
        values.put(Key_any_information_disclosed_Remark,pojo.getAny_information_disclosed_remark());
        values.put(Key_any_information_disclosed_Nc,pojo.getAny_information_disclosed_nc());
        values.put(Key_destruction_medical_records,pojo.getDestruction_medical_records());
        values.put(Key_destruction_medical_records_Remark,pojo.getDestruction_medical_records_remark());
        values.put(Key_destruction_medical_records_Nc,pojo.getDestruction_medical_records_nc());
        values.put(Key_fire_extinguisher_present,pojo.getFire_extinguisher_present());
        values.put(Key_fire_extinguisher_present_Remark,pojo.getFire_extinguisher_present_remark());
        values.put(Key_fire_extinguisher_present_Nc,pojo.getFire_extinguisher_present_nc());
        values.put(Key_fire_extinguisher_present_Image,pojo.getFire_extinguisher_present_image());
        values.put(Local_Key_fire_extinguisher_present_Image,pojo.getLocal_fire_extinguisher_present_image());

        database.insert(Table_MRD,null,values);


        return true;

    }

    public boolean UPDATE_MRD(MRDPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_medical_record_death_certificate,pojo.getMedical_record_death_certificate());
        values.put(Key_medical_record_death_certificate_Remark,pojo.getMedical_record_death_certificate_remark());
        values.put(Key_medical_record_death_certificate_Nc,pojo.getMedical_record_death_certificate_nc());
        values.put(Key_documented_maintaining_confidentiality,pojo.getDocumented_maintaining_confidentiality());
        values.put(Key_documented_maintaining_confidentiality_Remark,pojo.getDocumented_maintaining_confidentiality_remark());
        values.put(Key_documented_maintaining_confidentiality_Nc,pojo.getDocumented_maintaining_confidentiality_nc());
        values.put(Key_any_information_disclosed,pojo.getAny_information_disclosed());
        values.put(Key_any_information_disclosed_Remark,pojo.getAny_information_disclosed_remark());
        values.put(Key_any_information_disclosed_Nc,pojo.getAny_information_disclosed_nc());
        values.put(Key_destruction_medical_records,pojo.getDestruction_medical_records());
        values.put(Key_destruction_medical_records_Remark,pojo.getDestruction_medical_records_remark());
        values.put(Key_destruction_medical_records_Nc,pojo.getDestruction_medical_records_nc());
        values.put(Key_fire_extinguisher_present,pojo.getFire_extinguisher_present());
        values.put(Key_fire_extinguisher_present_Remark,pojo.getFire_extinguisher_present_remark());
        values.put(Key_fire_extinguisher_present_Nc,pojo.getFire_extinguisher_present_nc());
        values.put(Key_fire_extinguisher_present_Image,pojo.getFire_extinguisher_present_image());
        values.put(Local_Key_fire_extinguisher_present_Image,pojo.getLocal_fire_extinguisher_present_image());

        database.update(Table_MRD,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;

    }


    public boolean INSERT_Ward_Ot_Emergency(Ward_Ot_EmergencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_hospital_maintain_cleanliness,pojo.getHospital_maintain_cleanliness());
        values.put(Key_hospital_maintain_cleanliness_Remark,pojo.getHospital_maintain_cleanliness_remark());
        values.put(Key_hospital_maintain_cleanliness_Nc,pojo.getHospital_maintain_cleanliness_nc());
        values.put(Key_hospital_adhere_standard,pojo.getHospital_adhere_standard());
        values.put(Key_hospital_adhere_standard_Remark,pojo.getHospital_adhere_standard_remark());
        values.put(Key_hospital_adhere_standard_Nc,pojo.getHospital_adhere_standard_nc());
        values.put(Key_hospital_provide_adequate_gloves,pojo.getHospital_provide_adequate_gloves());
        values.put(Key_hospital_provide_adequate_gloves_Remark,pojo.getHospital_provide_adequate_gloves_remark());
        values.put(Key_hospital_provide_adequate_gloves_Nc,pojo.getHospital_provide_adequate_gloves_nc());
        values.put(Key_admissions_discharge_home,pojo.getAdmissions_discharge_home());
        values.put(Key_admissions_discharge_home_Remark,pojo.getAdmissions_discharge_home_remark());
        values.put(Key_admissions_discharge_home_Nc,pojo.getAdmissions_discharge_home_nc());
        values.put(Key_admissions_discharge_home_Video,pojo.getAdmissions_discharge_home_image());
        values.put(Local_Key_admissions_discharge_home_Video,pojo.getLocal_admissions_discharge_home_image());
        values.put(Key_staff_present_in_ICU,pojo.getStaff_present_in_ICU());
        values.put(Key_staff_present_in_Remark,pojo.getStaff_present_in_ICU_remark());
        values.put(Key_staff_present_in_ICU_Nc,pojo.getStaff_present_in_ICU_nc());

        database.insert(Table_Ward_Ot_Emergency,null,values);


        return true;
    }

    public boolean UPDATE_Ward_Ot_Emergency(Ward_Ot_EmergencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_hospital_maintain_cleanliness,pojo.getHospital_maintain_cleanliness());
        values.put(Key_hospital_maintain_cleanliness_Remark,pojo.getHospital_maintain_cleanliness_remark());
        values.put(Key_hospital_maintain_cleanliness_Nc,pojo.getHospital_maintain_cleanliness_nc());
        values.put(Key_hospital_adhere_standard,pojo.getHospital_adhere_standard());
        values.put(Key_hospital_adhere_standard_Remark,pojo.getHospital_adhere_standard_remark());
        values.put(Key_hospital_adhere_standard_Nc,pojo.getHospital_adhere_standard_nc());
        values.put(Key_hospital_provide_adequate_gloves,pojo.getHospital_provide_adequate_gloves());
        values.put(Key_hospital_provide_adequate_gloves_Remark,pojo.getHospital_provide_adequate_gloves_remark());
        values.put(Key_hospital_provide_adequate_gloves_Nc,pojo.getHospital_provide_adequate_gloves_nc());
        values.put(Key_admissions_discharge_home,pojo.getAdmissions_discharge_home());
        values.put(Key_admissions_discharge_home_Remark,pojo.getAdmissions_discharge_home_remark());
        values.put(Key_admissions_discharge_home_Nc,pojo.getAdmissions_discharge_home_nc());
        values.put(Key_admissions_discharge_home_Video,pojo.getAdmissions_discharge_home_image());
        values.put(Local_Key_admissions_discharge_home_Video,pojo.getLocal_admissions_discharge_home_image());
        values.put(Key_staff_present_in_ICU,pojo.getStaff_present_in_ICU());
        values.put(Key_staff_present_in_Remark,pojo.getStaff_present_in_ICU_remark());
        values.put(Key_staff_present_in_ICU_Nc,pojo.getStaff_present_in_ICU_nc());

        database.update(Table_Ward_Ot_Emergency,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }


    public boolean INSERT_PATIENT_STAFF(Patient_Staff_InterviewPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_privacy_maintained,pojo.getPrivacy_maintained());
        values.put(Key_privacy_maintained_Remark,pojo.getPrivacy_maintained_remark());
        values.put(Key_privacy_maintained_Nc,pojo.getPrivacy_maintained_nc());
        values.put(Key_privacy_maintained_Image,pojo.getPrivacy_maintained_image());
        values.put(Local_Key_privacy_maintained_Image,pojo.getLocal_privacy_maintained_image());
        values.put(Key_Patients_protected_physical_abuse,pojo.getPatients_protected_physical_abuse());
        values.put(Key_Patients_protected_physical_Remark,pojo.getPatients_protected_physical_abuse_remark());
        values.put(Key_Patients_protected_physical_abuse_Nc,pojo.getPatients_protected_physical_abuse_nc());
        values.put(Key_patient_information_confidential,pojo.getPatient_information_confidential());
        values.put(Key_patient_information_confidential_Remark,pojo.getPatient_information_confidential_remark());
        values.put(Key_patient_information_confidential_Nc,pojo.getPatient_information_confidential_nc());
        values.put(Key_patients_consent_carrying,pojo.getPatients_consent_carrying());
        values.put(Key_patients_consent_carrying_Remark,pojo.getPatients_consent_carrying_remark());
        values.put(Key_patients_consent_carrying_Nc,pojo.getPatients_consent_carrying_nc());
        values.put(Key_patient_voice_complaint,pojo.getPatient_voice_complaint());
        values.put(Key_patient_voice_complaint_Remark,pojo.getPatient_voice_complaint_remark());
        values.put(Key_patient_voice_complaint_Nc,pojo.getPatient_voice_complaint_nc());
        values.put(Key_patients_cost_treatment,pojo.getPatients_cost_treatment());
        values.put(Key_patients_cost_treatment_Remark,pojo.getPatients_cost_treatment_remark());
        values.put(Key_patients_cost_treatment_Nc,pojo.getPatients_cost_treatment_nc());
        values.put(Key_patient_clinical_records,pojo.getPatient_clinical_records());
        values.put(Key_patient_clinical_records_Remark,pojo.getPatient_clinical_records_remark());
        values.put(Key_patient_clinical_records_Nc,pojo.getPatient_clinical_records_nc());
        values.put(Key_patient_aware_plan_care,pojo.getPatient_aware_plan_care());
        values.put(Key_patient_aware_plan_care_Remark,pojo.getPatient_aware_plan_care_remark());
        values.put(Key_patient_aware_plan_care_Nc,pojo.getPatient_aware_plan_care_nc());
        values.put(Key_patient_aware_plan_care_Video,pojo.getPatient_aware_plan_care_video());
        values.put(Local_Key_patient_aware_plan_care_Video,pojo.getLocal_patient_aware_plan_care_video());

        database.insert(TABLE_Patient_Staff_Interview,null,values);


        return true;

    }

    public boolean UPDATE_PATIENT_STAFF(Patient_Staff_InterviewPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_privacy_maintained,pojo.getPrivacy_maintained());
        values.put(Key_privacy_maintained_Remark,pojo.getPrivacy_maintained_remark());
        values.put(Key_privacy_maintained_Nc,pojo.getPrivacy_maintained_nc());
        values.put(Key_privacy_maintained_Image,pojo.getPrivacy_maintained_image());
        values.put(Local_Key_privacy_maintained_Image,pojo.getLocal_privacy_maintained_image());
        values.put(Key_Patients_protected_physical_abuse,pojo.getPatients_protected_physical_abuse());
        values.put(Key_Patients_protected_physical_Remark,pojo.getPatients_protected_physical_abuse_remark());
        values.put(Key_Patients_protected_physical_abuse_Nc,pojo.getPatients_protected_physical_abuse_nc());
        values.put(Key_patient_information_confidential,pojo.getPatient_information_confidential());
        values.put(Key_patient_information_confidential_Remark,pojo.getPatient_information_confidential_remark());
        values.put(Key_patient_information_confidential_Nc,pojo.getPatient_information_confidential_nc());
        values.put(Key_patients_consent_carrying,pojo.getPatients_consent_carrying());
        values.put(Key_patients_consent_carrying_Remark,pojo.getPatients_consent_carrying_remark());
        values.put(Key_patients_consent_carrying_Nc,pojo.getPatients_consent_carrying_nc());
        values.put(Key_patient_voice_complaint,pojo.getPatient_voice_complaint());
        values.put(Key_patient_voice_complaint_Remark,pojo.getPatient_voice_complaint_remark());
        values.put(Key_patient_voice_complaint_Nc,pojo.getPatient_voice_complaint_nc());
        values.put(Key_patients_cost_treatment,pojo.getPatients_cost_treatment());
        values.put(Key_patients_cost_treatment_Remark,pojo.getPatients_cost_treatment_remark());
        values.put(Key_patients_cost_treatment_Nc,pojo.getPatients_cost_treatment_nc());
        values.put(Key_patient_clinical_records,pojo.getPatient_clinical_records());
        values.put(Key_patient_clinical_records_Remark,pojo.getPatient_clinical_records_remark());
        values.put(Key_patient_clinical_records_Nc,pojo.getPatient_clinical_records_nc());
        values.put(Key_patient_aware_plan_care,pojo.getPatient_aware_plan_care());
        values.put(Key_patient_aware_plan_care_Remark,pojo.getPatient_aware_plan_care_remark());
        values.put(Key_patient_aware_plan_care_Nc,pojo.getPatient_aware_plan_care_nc());
        values.put(Key_patient_aware_plan_care_Video,pojo.getPatient_aware_plan_care_video());
        values.put(Local_Key_patient_aware_plan_care_Video,pojo.getLocal_patient_aware_plan_care_video());

        database.update(TABLE_Patient_Staff_Interview,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;

    }



    public boolean INSERT_LABORATORY(LaboratoryPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(LABORATORY_collected_properly,pojo.getLABORATORY_collected_properly());
        values.put(Collected_properly_remark,pojo.getCollected_properly_remark());
        values.put(Collected_properly_NC,pojo.getCollected_properly_NC());
        values.put(Collected_properly_video,pojo.getCollected_properly_video());
        values.put(Local_Collected_properly_video,pojo.getLocal_Collected_properly_video());
        values.put(LABORATORY_identified_properly ,pojo.getLABORATORY_identified_properly());
        values.put(Identified_properly_remark ,pojo.getIdentified_properly_remark());
        values.put(Identified_properly_NC ,pojo.getIdentified_properly_NC());
        values.put(Identified_properly_image ,pojo.getIdentified_properly_image());
        values.put(Local_Identified_properly_image ,pojo.getLocal_Identified_properly_image());
        values.put(LABORATORY_transported_safe_manner ,pojo.getLABORATORY_transported_safe_manner());
        values.put(Transported_safe_manner_remark ,pojo.getTransported_safe_manner_remark());
        values.put(Transported_safe_manner_NC ,pojo.getTransported_safe_manner_NC());
        values.put(Transported_safe_manner_image ,pojo.getTransported_safe_manner_image());
        values.put(Local_Transported_safe_manner_image ,pojo.getLocal_Transported_safe_manner_image());
        values.put(LABORATORY_Specimen_safe_manner ,pojo.getLABORATORY_Specimen_safe_manner());
        values.put(Specimen_safe_manner_remark ,pojo.getSpecimen_safe_manner_remark());
        values.put(Specimen_safe_manner_nc ,pojo.getSpecimen_safe_manner_nc());
        values.put(Specimen_safe_image ,pojo.getSpecimen_safe_image());
        values.put(Local_Specimen_safe_image ,pojo.getLocal_Specimen_safe_image());
        values.put(LABORATORY_Appropriate_safety_equipment ,pojo.getLABORATORY_Appropriate_safety_equipment());
        values.put(Appropriate_safety_equipment_remark ,pojo.getAppropriate_safety_equipment_remark());
        values.put(Appropriate_safety_equipment_NC ,pojo.getAppropriate_safety_equipment_NC());
        values.put(Appropriate_safety_equipment_image ,pojo.getAppropriate_safety_equipment_image());
        values.put(Local_Appropriate_safety_equipment_image ,pojo.getLocal_Appropriate_safety_equipment_image());
        values.put(Laboratory_defined_turnaround,pojo.getLaboratory_defined_turnaround());
        values.put(Laboratory_defined_turnaround_Remark,pojo.getLaboratory_defined_turnaround_Remark());
        values.put(Laboratory_defined_turnaround_Nc,pojo.getLaboratory_defined_turnaround_Nc());
        values.put(Laboratory_defined_turnaround_Image,pojo.getLaboratory_defined_turnaround_image());
        values.put(Local_Laboratory_defined_turnaround_Image,pojo.getLocal_laboratory_defined_turnaround_image());


        database.insert(TABLE_LABORATORY,null,values);


        return true;
    }


    public boolean UPDATE_LABORATORY(LaboratoryPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(LABORATORY_collected_properly,pojo.getLABORATORY_collected_properly());
        values.put(Collected_properly_remark,pojo.getCollected_properly_remark());
        values.put(Collected_properly_NC,pojo.getCollected_properly_NC());
        values.put(Collected_properly_video,pojo.getCollected_properly_video());
        values.put(Local_Collected_properly_video,pojo.getLocal_Collected_properly_video());
        values.put(LABORATORY_identified_properly ,pojo.getLABORATORY_identified_properly());
        values.put(Identified_properly_remark ,pojo.getIdentified_properly_remark());
        values.put(Identified_properly_NC ,pojo.getIdentified_properly_NC());
        values.put(Identified_properly_image ,pojo.getIdentified_properly_image());
        values.put(Local_Identified_properly_image ,pojo.getLocal_Identified_properly_image());
        values.put(LABORATORY_transported_safe_manner ,pojo.getLABORATORY_transported_safe_manner());
        values.put(Transported_safe_manner_remark ,pojo.getTransported_safe_manner_remark());
        values.put(Transported_safe_manner_NC ,pojo.getTransported_safe_manner_NC());
        values.put(Transported_safe_manner_image ,pojo.getTransported_safe_manner_image());
        values.put(Local_Transported_safe_manner_image ,pojo.getLocal_Transported_safe_manner_image());
        values.put(LABORATORY_Specimen_safe_manner ,pojo.getLABORATORY_Specimen_safe_manner());
        values.put(Specimen_safe_manner_remark ,pojo.getSpecimen_safe_manner_remark());
        values.put(Specimen_safe_manner_nc ,pojo.getSpecimen_safe_manner_nc());
        values.put(Specimen_safe_image ,pojo.getSpecimen_safe_image());
        values.put(Local_Specimen_safe_image ,pojo.getLocal_Specimen_safe_image());
        values.put(LABORATORY_Appropriate_safety_equipment ,pojo.getLABORATORY_Appropriate_safety_equipment());
        values.put(Appropriate_safety_equipment_remark ,pojo.getAppropriate_safety_equipment_remark());
        values.put(Appropriate_safety_equipment_NC ,pojo.getAppropriate_safety_equipment_NC());
        values.put(Appropriate_safety_equipment_image ,pojo.getAppropriate_safety_equipment_image());
        values.put(Local_Appropriate_safety_equipment_image ,pojo.getLocal_Appropriate_safety_equipment_image());
        values.put(Laboratory_defined_turnaround,pojo.getLaboratory_defined_turnaround());
        values.put(Laboratory_defined_turnaround_Remark,pojo.getLaboratory_defined_turnaround_Remark());
        values.put(Laboratory_defined_turnaround_Nc,pojo.getLaboratory_defined_turnaround_Nc());
        values.put(Laboratory_defined_turnaround_Image,pojo.getLaboratory_defined_turnaround_image());
        values.put(Local_Laboratory_defined_turnaround_Image,pojo.getLocal_laboratory_defined_turnaround_image());


        database.update(TABLE_LABORATORY,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }


    public boolean INSERT_RADIOLOGy(RadioLogyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(RADIOLOGY_Appropriate_safety_equipment,pojo.getRADIOLOGY_Appropriate_safety_equipment());
        values.put(RADIOLOGY_Appropriate_safety_equipment_remark,pojo.getRADIOLOGY_Appropriate_safety_equipment_remark());
        values.put(RADIOLOGY_Appropriate_safety_equipment_NC,pojo.getRADIOLOGY_Appropriate_safety_equipment_NC());
        values.put(RADIOLOGY_Appropriate_safety_equipment_image,pojo.getRADIOLOGY_Appropriate_safety_equipment_image());
        values.put(Local_RADIOLOGY_Appropriate_safety_equipment_image,pojo.getLocal_RADIOLOGY_Appropriate_safety_equipment_image());
        values.put(RADIOLOGY_defined_turnaround,pojo.getRADIOLOGY_defined_turnaround());
        values.put(RADIOLOGY_defined_turnaround_Remark,pojo.getRADIOLOGY_defined_turnaround_remark());
        values.put(RADIOLOGY_defined_turnaround_Nc,pojo.getRADIOLOGY_defined_turnaround_nc());
        values.put(RADIOLOGY_defined_turnaround_Image,pojo.getRADIOLOGY_defined_turnaround_image());
        values.put(Local_RADIOLOGY_defined_turnaround_Image,pojo.getLocal_RADIOLOGY_defined_turnaround_image());


        database.insert(TABLE_RADIOLOGY,null,values);

        return true;
    }

    public boolean UPDATE_RADIOLOGy(RadioLogyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(RADIOLOGY_Appropriate_safety_equipment,pojo.getRADIOLOGY_Appropriate_safety_equipment());
        values.put(RADIOLOGY_Appropriate_safety_equipment_remark,pojo.getRADIOLOGY_Appropriate_safety_equipment_remark());
        values.put(RADIOLOGY_Appropriate_safety_equipment_NC,pojo.getRADIOLOGY_Appropriate_safety_equipment_NC());
        values.put(RADIOLOGY_Appropriate_safety_equipment_image,pojo.getRADIOLOGY_Appropriate_safety_equipment_image());
        values.put(Local_RADIOLOGY_Appropriate_safety_equipment_image,pojo.getLocal_RADIOLOGY_Appropriate_safety_equipment_image());
        values.put(RADIOLOGY_defined_turnaround,pojo.getRADIOLOGY_defined_turnaround());
        values.put(RADIOLOGY_defined_turnaround_Remark,pojo.getRADIOLOGY_defined_turnaround_remark());
        values.put(RADIOLOGY_defined_turnaround_Nc,pojo.getRADIOLOGY_defined_turnaround_nc());
        values.put(RADIOLOGY_defined_turnaround_Image,pojo.getRADIOLOGY_defined_turnaround_image());
        values.put(Local_RADIOLOGY_defined_turnaround_Image,pojo.getLocal_RADIOLOGY_defined_turnaround_image());


        database.update(TABLE_RADIOLOGY,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }

    public boolean INSERT_EMERGENCY(EmergencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(EMERGENCY_down_Norms,pojo.getEMERGENCY_down_Norms());
        values.put(EMERGENCY_down_Norms_remark,pojo.getEMERGENCY_down_Norms_remark());
        values.put(EMERGENCY_down_Norms_NC,pojo.getEMERGENCY_down_Norms_NC());
        values.put(EMERGENCY_down_Norms_video,pojo.getEMERGENCY_down_Norms_video());
        values.put(Local_EMERGENCY_down_Norms_video,pojo.getLocal_EMERGENCY_down_Norms_video());

        database.insert(TABLE_EMERGENCY,null,values);

        return true;
    }

    public boolean UPDATE_EMERGENCY(EmergencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(EMERGENCY_down_Norms,pojo.getEMERGENCY_down_Norms());
        values.put(EMERGENCY_down_Norms_remark,pojo.getEMERGENCY_down_Norms_remark());
        values.put(EMERGENCY_down_Norms_NC,pojo.getEMERGENCY_down_Norms_NC());
        values.put(EMERGENCY_down_Norms_video,pojo.getEMERGENCY_down_Norms_video());
        values.put(Local_EMERGENCY_down_Norms_video,pojo.getLocal_EMERGENCY_down_Norms_video());

        database.update(TABLE_EMERGENCY,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }

    public boolean INSERT_HighDependency(HighDependencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(HIGH_DEPENDENCY_documented_procedure,pojo.getHIGH_DEPENDENCY_documented_procedure());
        values.put(HIGH_DEPENDENCY_documented_procedure_remark,pojo.getHIGH_DEPENDENCY_documented_procedure_remark());
        values.put(HIGH_DEPENDENCY_documented_procedure_NC,pojo.getHIGH_DEPENDENCY_documented_procedure_NC());
        values.put(HIGH_DEPENDENCY_documented_procedure_video,pojo.getHIGH_DEPENDENCY_documented_procedure_video());
        values.put(Local_HIGH_DEPENDENCY_documented_procedure_video,pojo.getLocal_HIGH_DEPENDENCY_documented_procedure_video());

        values.put(HIGH_DEPENDENCY_Areas_adequate,pojo.getHIGH_DEPENDENCY_Areas_adequate());
        values.put(HIGH_DEPENDENCY_Areas_adequate_remark,pojo.getHIGH_DEPENDENCY_Areas_adequate_remark());
        values.put(HIGH_DEPENDENCY_Areas_adequate_NC,pojo.getHIGH_DEPENDENCY_Areas_adequate_NC());
        values.put(HIGH_DEPENDENCY_Areas_adequate_image,pojo.getHIGH_DEPENDENCY_Areas_adequate_image());
        values.put(Local_HIGH_DEPENDENCY_Areas_adequate_image,pojo.getLocal_HIGH_DEPENDENCY_Areas_adequate_image());

        values.put(HIGH_DEPENDENCY_adequate_equipment,pojo.getHIGH_DEPENDENCY_adequate_equipment());
        values.put(HIGH_DEPENDENCY_adequate_equipment_remark,pojo.getHIGH_DEPENDENCY_adequate_equipment_remark());
        values.put(HIGH_DEPENDENCY_adequate_equipment_Nc,pojo.getHIGH_DEPENDENCY_adequate_equipment_Nc());
        values.put(HIGH_DEPENDENCY_adequate_equipment_image,pojo.getHIGH_DEPENDENCY_adequate_equipment_image());
        values.put(Local_HIGH_DEPENDENCY_adequate_equipment_image,pojo.getLocal_HIGH_DEPENDENCY_adequate_equipment_image());

        database.insert(TABLE_HIGH_DEPENDENCY,null,values);

        return true;
    }

    public boolean UPDATE_HighDependency(HighDependencyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(HIGH_DEPENDENCY_documented_procedure,pojo.getHIGH_DEPENDENCY_documented_procedure());
        values.put(HIGH_DEPENDENCY_documented_procedure_remark,pojo.getHIGH_DEPENDENCY_documented_procedure_remark());
        values.put(HIGH_DEPENDENCY_documented_procedure_NC,pojo.getHIGH_DEPENDENCY_documented_procedure_NC());
        values.put(HIGH_DEPENDENCY_documented_procedure_video,pojo.getHIGH_DEPENDENCY_documented_procedure_video());
        values.put(Local_HIGH_DEPENDENCY_documented_procedure_video,pojo.getLocal_HIGH_DEPENDENCY_documented_procedure_video());

        values.put(HIGH_DEPENDENCY_Areas_adequate,pojo.getHIGH_DEPENDENCY_Areas_adequate());
        values.put(HIGH_DEPENDENCY_Areas_adequate_remark,pojo.getHIGH_DEPENDENCY_Areas_adequate_remark());
        values.put(HIGH_DEPENDENCY_Areas_adequate_NC,pojo.getHIGH_DEPENDENCY_Areas_adequate_NC());
        values.put(HIGH_DEPENDENCY_Areas_adequate_image,pojo.getHIGH_DEPENDENCY_Areas_adequate_image());
        values.put(Local_HIGH_DEPENDENCY_Areas_adequate_image,pojo.getLocal_HIGH_DEPENDENCY_Areas_adequate_image());

        values.put(HIGH_DEPENDENCY_adequate_equipment,pojo.getHIGH_DEPENDENCY_adequate_equipment());
        values.put(HIGH_DEPENDENCY_adequate_equipment_remark,pojo.getHIGH_DEPENDENCY_adequate_equipment_remark());
        values.put(HIGH_DEPENDENCY_adequate_equipment_Nc,pojo.getHIGH_DEPENDENCY_adequate_equipment_Nc());
        values.put(HIGH_DEPENDENCY_adequate_equipment_image,pojo.getHIGH_DEPENDENCY_adequate_equipment_image());
        values.put(Local_HIGH_DEPENDENCY_adequate_equipment_image,pojo.getLocal_HIGH_DEPENDENCY_adequate_equipment_image());

        database.update(TABLE_HIGH_DEPENDENCY,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;
    }


    public boolean INSERT_OBSTETRIC_WARD(OBSTETRIC_WARD_Pojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(OBSTETRIC_WARD_abuse,pojo.getOBSTETRIC_WARD_abuse());
        values.put(OBSTETRIC_WARD_abuse_remark,pojo.getOBSTETRIC_WARD_abuse_remark());
        values.put(OBSTETRIC_WARD_abuse_NC,pojo.getOBSTETRIC_WARD_abuse_NC());
        values.put(OBSTETRIC_WARD_abuse_image_identification,pojo.getOBSTETRIC_WARD_abuse_image_identification());
        values.put(Local_OBSTETRIC_WARD_abuse_image_identification,pojo.getLocal_OBSTETRIC_WARD_abuse_image_identification());
        values.put(OBSTETRIC_WARD_abuse_image_surveillance,pojo.getOBSTETRIC_WARD_abuse_image_surveillance());
        values.put(Local_OBSTETRIC_WARD_abuse_image_surveillance,pojo.getLocal_OBSTETRIC_WARD_abuse_image_surveillance());
        values.put(shco_obstetrics_ward,pojo.getShco_obstetrics_ward());
        values.put(shco_obstetrics_ward_remark,pojo.getShco_obstetrics_ward_remark());
        values.put(shco_obstetrics_ward_nc,pojo.getShco_obstetrics_ward_nc());
        values.put(shco_obstetrics_ward_image,pojo.getShco_obstetrics_ward_image());
        values.put(Local_shco_obstetrics_ward_image,pojo.getLocal_shco_obstetrics_ward_image());

        database.insert(TABLE_OBSTETRIC_WARD,null,values);

        return true;

    }


    public boolean UPDATE_OBSTETRIC_WARD(OBSTETRIC_WARD_Pojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(OBSTETRIC_WARD_abuse,pojo.getOBSTETRIC_WARD_abuse());
        values.put(OBSTETRIC_WARD_abuse_remark,pojo.getOBSTETRIC_WARD_abuse_remark());
        values.put(OBSTETRIC_WARD_abuse_NC,pojo.getOBSTETRIC_WARD_abuse_NC());
        values.put(OBSTETRIC_WARD_abuse_image_identification,pojo.getOBSTETRIC_WARD_abuse_image_identification());
        values.put(Local_OBSTETRIC_WARD_abuse_image_identification,pojo.getLocal_OBSTETRIC_WARD_abuse_image_identification());
        values.put(OBSTETRIC_WARD_abuse_image_surveillance,pojo.getOBSTETRIC_WARD_abuse_image_surveillance());
        values.put(Local_OBSTETRIC_WARD_abuse_image_surveillance,pojo.getLocal_OBSTETRIC_WARD_abuse_image_surveillance());
        values.put(shco_obstetrics_ward,pojo.getShco_obstetrics_ward());
        values.put(shco_obstetrics_ward_remark,pojo.getShco_obstetrics_ward_remark());
        values.put(shco_obstetrics_ward_nc,pojo.getShco_obstetrics_ward_nc());
        values.put(shco_obstetrics_ward_image,pojo.getShco_obstetrics_ward_image());
        values.put(Local_shco_obstetrics_ward_image,pojo.getLocal_shco_obstetrics_ward_image());

        database.update(TABLE_OBSTETRIC_WARD,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);

        return true;

    }

    public boolean INSERT_Ward_Pharmacy(Wards_PharmacyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_patient_care_area,pojo.getPatient_care_area());
        values.put(Key_patient_care_area_Remark,pojo.getPatient_care_area_Remark());
        values.put(Key_patient_care_area_NC,pojo.getPatient_care_area_NC());
        values.put(Key_patient_care_area_Image,pojo.getPatient_care_area_Image());
        values.put(Local_Key_patient_care_area_Image,pojo.getLocal_patient_care_area_Image());
        values.put(Key_pharmacyStores_present,pojo.getPharmacyStores_present());
        values.put(Key_pharmacyStores_present_Remark,pojo.getPharmacyStores_present_Remark());
        values.put(Key_pharmacyStores_present_Nc,pojo.getPharmacyStores_present_NC());
        values.put(Key_pharmacyStores_present_Image,pojo.getPharmacyStores_present_Image());
        values.put(Local_Key_pharmacyStores_present_Image,pojo.getLocal_pharmacyStores_present_Image());
        values.put(Key_expired_drugs,pojo.getExpired_drugs());
        values.put(Key_expired_drugs_Remark,pojo.getExpired_drugs_remark());
        values.put(Key_expired_drugs_NC,pojo.getExpired_drugs_nc());
        values.put(Key_expired_drugs_image,pojo.getExpired_drugs_image());
        values.put(Local_Key_expired_drugs_image,pojo.getLocal_expired_drugs_image());
        values.put(Key_expiry_date_checked,pojo.getExpiry_date_checked());
        values.put(Key_expiry_date_checked_Remark,pojo.getExpiry_date_checked_Remark());
        values.put(Key_expiry_date_checked_NC,pojo.getExpiry_date_checked_NC());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(Key_emergency_medications,pojo.getEmergency_medications());
        values.put(Key_emergency_medications_Remark,pojo.getEmergency_medications_Remark());
        values.put(Key_emergency_medications_Nc,pojo.getEmergency_medications_Nc());
        values.put(Key_emergency_medications_Image,pojo.getEmergency_medications_Image());
        values.put(Local_Key_emergency_medications_Image,pojo.getLocal_emergency_medications_Image());
        values.put(Key_risk_medications,pojo.getRisk_medications());
        values.put(Key_risk_medications_Remark,pojo.getRisk_medications_remark());
        values.put(Key_risk_medications_Nc,pojo.getRisk_medications_nc());
        values.put(Key_risk_medications_Image,pojo.getRisk_medications_image());
        values.put(Local_Key_risk_medications_Image,pojo.getLocal_risk_medications_image());
        values.put(Key_medications_dispensing,pojo.getMedications_dispensing());
        values.put(Key_medications_dispensing_Remark,pojo.getMedications_dispensing_remark());
        values.put(Key_medications_dispensing_Nc,pojo.getMedications_dispensing_nc());
        values.put(Key_labelling_of_drug,pojo.getLabelling_of_drug());
        values.put(Key_labelling_of_drug_Remark,pojo.getLabelling_of_drug_remark());
        values.put(Key_labelling_of_drug_NC,pojo.getLabelling_of_drug_nc());
        values.put(Key_labelling_of_drug_Image,pojo.getLabelling_of_drug_image());
        values.put(Local_Key_labelling_of_drug_Image,pojo.getLocal_labelling_of_drug_image());
        values.put(Key_medication_order_checked,pojo.getMedication_order_checked());
        values.put(Key_medication_order_checked_Remark,pojo.getMedication_order_checked_remark());
        values.put(Key_medication_order_checked_Nc,pojo.getMedication_order_checked_nc());
        values.put(Key_medication_order_checked_Image,pojo.getMedication_order_checked_image());
        values.put(Local_Key_medication_order_checked_Image,pojo.getLocal_medication_order_checked_image());
        values.put(Key_medication_administration_documented,pojo.getMedication_administration_documented());
        values.put(Key_medication_administration_documented_Remark,pojo.getMedication_administration_documented_remark());
        values.put(Key_medication_administration_documented_Nc,pojo.getMedication_administration_documented_nc());
        values.put(Key_medication_administration_documented_Image,pojo.getMedication_administration_documented_image());
        values.put(Local_Key_medication_administration_documented_Image,pojo.getLocal_medication_administration_documented_image());
        values.put(Key_fridge_temperature_record,pojo.getFridge_temperature_record());
        values.put(Key_fridge_temperature_record_Remark,pojo.getFridge_temperature_record_Remark());
        values.put(Key_fridge_temperature_record_Nc,pojo.getFridge_temperature_record_Nc());
        values.put(Key_fridge_temperature_record_Image,pojo.getFridge_temperature_record_Image());
        values.put(Local_Key_fridge_temperature_record_Image,pojo.getLocal_fridge_temperature_record_Image());

        database.insert(TABLE_Ward_Pharmacy,null,values);


        return true;

    }

    public boolean UPDATE_Ward_Pharmacy(Wards_PharmacyPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_patient_care_area,pojo.getPatient_care_area());
        values.put(Key_patient_care_area_Remark,pojo.getPatient_care_area_Remark());
        values.put(Key_patient_care_area_NC,pojo.getPatient_care_area_NC());
        values.put(Key_patient_care_area_Image,pojo.getPatient_care_area_Image());
        values.put(Local_Key_patient_care_area_Image,pojo.getLocal_patient_care_area_Image());
        values.put(Key_pharmacyStores_present,pojo.getPharmacyStores_present());
        values.put(Key_pharmacyStores_present_Remark,pojo.getPharmacyStores_present_Remark());
        values.put(Key_pharmacyStores_present_Nc,pojo.getPharmacyStores_present_NC());
        values.put(Key_pharmacyStores_present_Image,pojo.getPharmacyStores_present_Image());
        values.put(Local_Key_pharmacyStores_present_Image,pojo.getLocal_pharmacyStores_present_Image());
        values.put(Key_expired_drugs,pojo.getExpired_drugs());
        values.put(Key_expired_drugs_Remark,pojo.getExpired_drugs_remark());
        values.put(Key_expired_drugs_NC,pojo.getExpired_drugs_nc());
        values.put(Key_expired_drugs_image,pojo.getExpired_drugs_image());
        values.put(Local_Key_expired_drugs_image,pojo.getLocal_expired_drugs_image());
        values.put(Key_expiry_date_checked,pojo.getExpiry_date_checked());
        values.put(Key_expiry_date_checked_Remark,pojo.getExpiry_date_checked_Remark());
        values.put(Key_expiry_date_checked_NC,pojo.getExpiry_date_checked_NC());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(Key_emergency_medications,pojo.getEmergency_medications());
        values.put(Key_emergency_medications_Remark,pojo.getEmergency_medications_Remark());
        values.put(Key_emergency_medications_Nc,pojo.getEmergency_medications_Nc());
        values.put(Key_emergency_medications_Image,pojo.getEmergency_medications_Image());
        values.put(Local_Key_emergency_medications_Image,pojo.getLocal_emergency_medications_Image());
        values.put(Key_risk_medications,pojo.getRisk_medications());
        values.put(Key_risk_medications_Remark,pojo.getRisk_medications_remark());
        values.put(Key_risk_medications_Nc,pojo.getRisk_medications_nc());
        values.put(Key_risk_medications_Image,pojo.getRisk_medications_image());
        values.put(Local_Key_risk_medications_Image,pojo.getLocal_risk_medications_image());
        values.put(Key_medications_dispensing,pojo.getMedications_dispensing());
        values.put(Key_medications_dispensing_Remark,pojo.getMedications_dispensing_remark());
        values.put(Key_medications_dispensing_Nc,pojo.getMedications_dispensing_nc());
        values.put(Key_labelling_of_drug,pojo.getLabelling_of_drug());
        values.put(Key_labelling_of_drug_Remark,pojo.getLabelling_of_drug_remark());
        values.put(Key_labelling_of_drug_NC,pojo.getLabelling_of_drug_nc());
        values.put(Key_labelling_of_drug_Image,pojo.getLabelling_of_drug_image());
        values.put(Local_Key_labelling_of_drug_Image,pojo.getLocal_labelling_of_drug_image());
        values.put(Key_medication_order_checked,pojo.getMedication_order_checked());
        values.put(Key_medication_order_checked_Remark,pojo.getMedication_order_checked_remark());
        values.put(Key_medication_order_checked_Nc,pojo.getMedication_order_checked_nc());
        values.put(Key_medication_order_checked_Image,pojo.getMedication_order_checked_image());
        values.put(Local_Key_medication_order_checked_Image,pojo.getLocal_medication_order_checked_image());
        values.put(Key_medication_administration_documented,pojo.getMedication_administration_documented());
        values.put(Key_medication_administration_documented_Remark,pojo.getMedication_administration_documented_remark());
        values.put(Key_medication_administration_documented_Nc,pojo.getMedication_administration_documented_nc());
        values.put(Key_medication_administration_documented_Image,pojo.getMedication_administration_documented_image());
        values.put(Local_Key_medication_administration_documented_Image,pojo.getLocal_medication_administration_documented_image());
        values.put(Key_fridge_temperature_record,pojo.getFridge_temperature_record());
        values.put(Key_fridge_temperature_record_Remark,pojo.getFridge_temperature_record_Remark());
        values.put(Key_fridge_temperature_record_Nc,pojo.getFridge_temperature_record_Nc());
        values.put(Key_fridge_temperature_record_Image,pojo.getFridge_temperature_record_Image());
        values.put(Local_Key_fridge_temperature_record_Image,pojo.getLocal_fridge_temperature_record_Image());

        database.update(TABLE_Ward_Pharmacy,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;

    }

    public boolean INSERT_SCOPE_SERVICE(ScopeOfServicePojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_clinical_anaesthesiology,pojo.getClinical_anaesthesiology());
        values.put(Key_clinical_dermatology_venereology,pojo.getClinical_dermatology_venereology());
        values.put(Key_clinical_emergency_medicine,pojo.getClinical_emergency_medicine());
        values.put(Key_clinical_family_medicine,pojo.getClinical_family_medicine());
        values.put(Key_clinical_general_medicine,pojo.getClinical_general_medicine());
        values.put(Key_clinical_general_surgery,pojo.getClinical_general_surgery());
        values.put(Key_clinical_obstetrics_gynecology,pojo.getClinical_obstetrics_gynecology());
        values.put(Key_clinical_ophthalmology,pojo.getClinical_ophthalmology());
        values.put(Key_clinical_orthopaedic_surgery,pojo.getClinical_orthopaedic_surgery());
        values.put(Key_clinical_otorhinolaryngology,pojo.getClinical_otorhinolaryngology());
        values.put(Key_clinical_paediatrics,pojo.getClinical_paediatrics());;
        values.put(Key_clinical_Psychiatry,pojo.getClinical_Psychiatry());
        values.put(Key_clinical_respiratory_medicine,pojo.getClinical_respiratory_medicine());
        values.put(Key_clinical_day_care_services,pojo.getClinical_day_care_services());
        values.put(Key_clinical_cardiac_anaesthesia,pojo.getClinical_cardiac_anaesthesia());
        values.put(Key_clinical_cardiology,pojo.getClinical_cardiology());
        values.put(Key_clinical_cardiothoracic_surgery,pojo.getClinical_cardiothoracic_surgery());
        values.put(Key_clinical_clinical_haematology,pojo.getClinical_clinical_haematology());
        values.put(Key_clinical_critical_care,pojo.getClinical_critical_care());
        values.put(Key_clinical_endocrinology,pojo.getClinical_endocrinology());
        values.put(Key_clinical_hepatology,pojo.getClinical_hepatology());
        values.put(Key_clinical_immunology,pojo.getClinical_immunology());
        values.put(Key_clinical_medical_gastroenterology,pojo.getClinical_medical_gastroenterology());
        values.put(Key_clinical_neonatology,pojo.getClinical_Neurology());
        values.put(Key_clinical_nephrology,pojo.getClinical_nephrology());
        values.put(Key_clinical_Neurology,pojo.getClinical_Neurology());
        values.put(Key_clinical_Neurosurgery,pojo.getClinical_Neurosurgery());
        values.put(Key_clinical_Oncology,pojo.getClinical_Oncology());
        values.put(Key_clinical_paediatric_cardiology,pojo.getClinical_paediatric_cardiology());
        values.put(Key_clinical_paediatric_surgery,pojo.getClinical_paediatric_surgery());
        values.put(Key_clinical_plastic_reconstructive,pojo.getClinical_plastic_reconstructive());
        values.put(Key_clinical_surgical_gastroenterology,pojo.getClinical_surgical_gastroenterology());
        values.put(Key_clinical_urology,pojo.getClinical_urology());
        values.put(Key_clinical_transplantation_service,pojo.getClinical_transplantation_service());
        values.put(Key_diagnostic_ct_scanning,pojo.getDiagnostic_ct_scanning());
        values.put(Key_diagnostic_mammography,pojo.getDiagnostic_mammography());
        values.put(Key_diagnostic_mri,pojo.getDiagnostic_mri());
        values.put(Key_diagnostic_ultrasound,pojo.getDiagnostic_ultrasound());
        values.put(Key_diagnostic_x_ray,pojo.getDiagnostic_x_ray());
        values.put(Key_diagnostic_2d_echo,pojo.getDiagnostic_2d_echo());
        values.put(Key_diagnostic_eeg,pojo.getDiagnostic_eeg());
        values.put(Key_diagnostic_holter_monitoring,pojo.getDiagnostic_holter_monitoring());
        values.put(Key_diagnostic_spirometry,pojo.getDiagnostic_spirometry());
        values.put(Key_diagnostic_tread_mill_testing,pojo.getDiagnostic_tread_mill_testing());
        values.put(Key_diagnostic_urodynamic_studies,pojo.getDiagnostic_urodynamic_studies());
        values.put(Key_diagnostic_bone_densitometry,pojo.getDiagnostic_bone_densitometry());
        values.put(Key_laboratory_clinical_bio_chemistry,pojo.getLaboratory_clinical_bio_chemistry());
        values.put(Key_laboratory_clinical_microbiology,pojo.getLaboratory_clinical_microbiology());
        values.put(Key_laboratory_clinical_pathology,pojo.getLaboratory_clinical_pathology());
        values.put(Key_laboratory_cytopathology,pojo.getLaboratory_cytopathology());
        values.put(Key_laboratory_haematology,pojo.getLaboratory_haematology());
        values.put(Key_laboratory_histopathology,pojo.getLaboratory_histopathology());
        values.put(Key_pharmacy_dispensary,pojo.getPharmacy_dispensary());
        values.put(Key_transfusions_blood_transfusions,pojo.getTransfusions_blood_transfusions());
        values.put(Key_transfusions_blood_bank,pojo.getTransfusions_blood_bank());
        values.put(Key_professions_allied_ambulance,pojo.getProfessions_allied_ambulance());
        values.put(Key_professions_dietetics,pojo.getProfessions_dietetics());
        values.put(Key_professions_physiotherapy,pojo.getProfessions_physiotherapy());
        values.put(Key_professions_occupational_therapy,pojo.getProfessions_occupational_therapy());
        values.put(Key_professions_speech_language,pojo.getProfessions_speech_language());
        values.put(Key_professions_psychology,pojo.getProfessions_psychology());

        database.insert(Table_scope_service,null,values);


        return true;

    }
    public boolean UPDATE_SCOPE_SERVICE(ScopeOfServicePojo pojo){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(Key_clinical_anaesthesiology,pojo.getClinical_anaesthesiology());
        values.put(Key_clinical_dermatology_venereology,pojo.getClinical_dermatology_venereology());
        values.put(Key_clinical_emergency_medicine,pojo.getClinical_emergency_medicine());
        values.put(Key_clinical_family_medicine,pojo.getClinical_family_medicine());
        values.put(Key_clinical_general_medicine,pojo.getClinical_general_medicine());
        values.put(Key_clinical_general_surgery,pojo.getClinical_general_surgery());
        values.put(Key_clinical_obstetrics_gynecology,pojo.getClinical_obstetrics_gynecology());
        values.put(Key_clinical_ophthalmology,pojo.getClinical_ophthalmology());
        values.put(Key_clinical_orthopaedic_surgery,pojo.getClinical_orthopaedic_surgery());
        values.put(Key_clinical_otorhinolaryngology,pojo.getClinical_otorhinolaryngology());
        values.put(Key_clinical_paediatrics,pojo.getClinical_paediatrics());
        values.put(Key_clinical_Psychiatry,pojo.getClinical_Psychiatry());
        values.put(Key_clinical_respiratory_medicine,pojo.getClinical_respiratory_medicine());
        values.put(Key_clinical_day_care_services,pojo.getClinical_day_care_services());
        values.put(Key_clinical_cardiac_anaesthesia,pojo.getClinical_cardiac_anaesthesia());
        values.put(Key_clinical_cardiology,pojo.getClinical_cardiology());
        values.put(Key_clinical_cardiothoracic_surgery,pojo.getClinical_cardiothoracic_surgery());
        values.put(Key_clinical_clinical_haematology,pojo.getClinical_clinical_haematology());
        values.put(Key_clinical_critical_care,pojo.getClinical_critical_care());
        values.put(Key_clinical_endocrinology,pojo.getClinical_endocrinology());
        values.put(Key_clinical_hepatology,pojo.getClinical_hepatology());
        values.put(Key_clinical_immunology,pojo.getClinical_immunology());
        values.put(Key_clinical_medical_gastroenterology,pojo.getClinical_medical_gastroenterology());
        values.put(Key_clinical_neonatology,pojo.getClinical_Neurology());
        values.put(Key_clinical_nephrology,pojo.getClinical_nephrology());
        values.put(Key_clinical_Neurology,pojo.getClinical_Neurology());
        values.put(Key_clinical_Neurosurgery,pojo.getClinical_Neurosurgery());
        values.put(Key_clinical_Oncology,pojo.getClinical_Oncology());
        values.put(Key_clinical_paediatric_cardiology,pojo.getClinical_paediatric_cardiology());
        values.put(Key_clinical_paediatric_surgery,pojo.getClinical_paediatric_surgery());
        values.put(Key_clinical_plastic_reconstructive,pojo.getClinical_plastic_reconstructive());
        values.put(Key_clinical_surgical_gastroenterology,pojo.getClinical_surgical_gastroenterology());
        values.put(Key_clinical_urology,pojo.getClinical_urology());
        values.put(Key_clinical_transplantation_service,pojo.getClinical_transplantation_service());
        values.put(Key_diagnostic_ct_scanning,pojo.getDiagnostic_ct_scanning());
        values.put(Key_diagnostic_mammography,pojo.getDiagnostic_mammography());
        values.put(Key_diagnostic_mri,pojo.getDiagnostic_mri());
        values.put(Key_diagnostic_ultrasound,pojo.getDiagnostic_ultrasound());
        values.put(Key_diagnostic_x_ray,pojo.getDiagnostic_x_ray());
        values.put(Key_diagnostic_2d_echo,pojo.getDiagnostic_2d_echo());
        values.put(Key_diagnostic_eeg,pojo.getDiagnostic_eeg());
        values.put(Key_diagnostic_holter_monitoring,pojo.getDiagnostic_holter_monitoring());
        values.put(Key_diagnostic_spirometry,pojo.getDiagnostic_spirometry());
        values.put(Key_diagnostic_tread_mill_testing,pojo.getDiagnostic_tread_mill_testing());
        values.put(Key_diagnostic_urodynamic_studies,pojo.getDiagnostic_urodynamic_studies());
        values.put(Key_diagnostic_bone_densitometry,pojo.getDiagnostic_bone_densitometry());
        values.put(Key_laboratory_clinical_bio_chemistry,pojo.getLaboratory_clinical_bio_chemistry());
        values.put(Key_laboratory_clinical_microbiology,pojo.getLaboratory_clinical_microbiology());
        values.put(Key_laboratory_clinical_pathology,pojo.getLaboratory_clinical_pathology());
        values.put(Key_laboratory_cytopathology,pojo.getLaboratory_cytopathology());
        values.put(Key_laboratory_haematology,pojo.getLaboratory_haematology());
        values.put(Key_laboratory_histopathology,pojo.getLaboratory_histopathology());
        values.put(Key_pharmacy_dispensary,pojo.getPharmacy_dispensary());
        values.put(Key_transfusions_blood_transfusions,pojo.getTransfusions_blood_transfusions());
        values.put(Key_transfusions_blood_bank,pojo.getTransfusions_blood_bank());
        values.put(Key_professions_allied_ambulance,pojo.getProfessions_allied_ambulance());
        values.put(Key_professions_dietetics,pojo.getProfessions_dietetics());
        values.put(Key_professions_physiotherapy,pojo.getProfessions_physiotherapy());
        values.put(Key_professions_occupational_therapy,pojo.getProfessions_occupational_therapy());
        values.put(Key_professions_speech_language,pojo.getProfessions_speech_language());
        values.put(Key_professions_psychology,pojo.getProfessions_psychology());


        database.update(Table_scope_service,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }


    public boolean INSERT_OT(OTPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(KEY_Anaesthetist_status,pojo.getAnaesthetist_status());
        values.put(KEY_Remark_OT_anaesthetist,pojo.getRemark_OT_anaesthetist());
        values.put(KEY_Nc_OT_anaesthetist,pojo.getNc_OT_anaesthetist());
        values.put(KEY_Documented_anaesthesia_status,pojo.getDocumented_anaesthesia_status());
        values.put(KEY_Remark_documented_anaesthesia,pojo.getRemark_documented_anaesthesia());
        values.put(KEY_Nc_documented_anaesthesia ,pojo.getNc_documented_anaesthesia());
        values.put(KEY_Immediate_preoperative_status,pojo.getImmediate_preoperative_status());
        values.put(KEY_Remark_immediate_preoperative,pojo.getRemark_immediate_preoperative());
        values.put(KEY_Nc_immediate_preoperative,pojo.getNc_immediate_preoperative());
        values.put(KEY_Anaesthesia_monitoring_status,pojo.getAnaesthesia_monitoring_status());
        values.put(KEY_Remark_anaesthesia_monitoring,pojo.getRemark_anaesthesia_monitoring());
        values.put(KEY_Nc_anaesthesia_monitoring,pojo.getNc_anaesthesia_monitoring());
        values.put(KEY_Post_anaesthesia_monitoring_status,pojo.getPost_anaesthesia_monitoring_status());
        values.put(KEY_Remark_post_anaesthesia_monitoring,pojo.getRemark_post_anaesthesia_monitoring());
        values.put(KEY_Nc_post_anaesthesia_monitoring,pojo.getNc_post_anaesthesia_monitoring());
        values.put(KEY_WHO_Patient_Safety_status,pojo.getWHO_Patient_Safety_status());
        values.put(KEY_Remark_WHO_Patient_Safety,pojo.getRemark_WHO_Patient_Safety());
        values.put(KEY_Nc_WHO_Patient_Safety,pojo.getNc_WHO_Patient_Safety());
        values.put(KEY_Image_WHO_Patient_Safety_day1,pojo.getImage_WHO_Patient_Safety_day1());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day1,pojo.getLocal_image_WHO_Patient_Safety_day1());
        values.put(KEY_Image_WHO_Patient_Safety_day2,pojo.getImage_WHO_Patient_Safety_day2());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day2,pojo.getLocal_image_WHO_Patient_Safety_day2());
        values.put(KEY_Image_WHO_Patient_Safety_day3,pojo.getImage_WHO_Patient_Safety_day3());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day3,pojo.getLocal_image_WHO_Patient_Safety_day3());
        values.put(KEY_OT_Zoning_status,pojo.getOT_Zoning_status());
        values.put(KEY_Remark_OT_Zoning,pojo.getRemark_OT_Zoning());
        values.put(KEY_Nc_OT_Zoning,pojo.getNc_OT_Zoning());
        values.put(KEY_Video_OT_Zoning,pojo.getVideo_OT_Zoning());
        values.put(KEY_Local_video_OT_Zoning,pojo.getLocal_video_OT_Zoning());
        values.put(KEY_Infection_control_status,pojo.getInfection_control_status());
        values.put(KEY_Remark_infection_control,pojo.getRemark_infection_control());
        values.put(KEY_Nc_infection_control,pojo.getNc_infection_control());
        values.put(KEY_Image_infection_control,pojo.getImage_infection_control());
        values.put(Local_KEY_Image_infection_control,pojo.getLocal_image_infection_control());
        values.put(KEY_Narcotic_drugs_status,pojo.getNarcotic_drugs_status());
        values.put(KEY_Remark_narcotic_drugs,pojo.getRemark_narcotic_drugs());
        values.put(KEY_Nc_narcotic_drugs,pojo.getNc_narcotic_drugs());
        values.put(KEY_Image_narcotic_drugs,pojo.getImage_narcotic_drugs());
        values.put(Local_KEY_Image_narcotic_drugs,pojo.getLocal_image_narcotic_drugs());
        values.put(KEY_Administration_disposal_status,pojo.getAdministration_disposal_status());
        values.put(KEY_Remark_administration_disposal,pojo.getRemark_administration_disposal());
        values.put(KEY_Nc_administration_disposal,pojo.getNc_administration_disposal());
        values.put(KEY_Image_administration_disposal,pojo.getImage_administration_disposal());
        values.put(Local_KEY_Image_administration_disposal,pojo.getLocal_image_administration_disposal());
        values.put(Key_hand_wash_facility_status,pojo.getHand_wash_facility_status());
        values.put(Key_remark_hand_wash_facility,pojo.getRemark_hand_wash_facility());
        values.put(Key_nc_hand_wash_facility,pojo.getNc_hand_wash_facility());
        values.put(Key_image_hand_wash_facility,pojo.getImage_hand_wash_facility());
        values.put(Key_local_image_hand_wash_facility,pojo.getLocal_image_hand_wash_facility());


        database.insert(TABLE_OT,null,values);


        return true;
    }

    public boolean UPDATE_OT(OTPojo pojo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID,pojo.getId());
        values.put(KEY_HOSPITAL_NAME,pojo.getHospital_name());
        values.put(KEY_HOSPITAL_ID,pojo.getHospital_id());
        values.put(KEY_Anaesthetist_status,pojo.getAnaesthetist_status());
        values.put(KEY_Remark_OT_anaesthetist,pojo.getRemark_OT_anaesthetist());
        values.put(KEY_Nc_OT_anaesthetist,pojo.getNc_OT_anaesthetist());
        values.put(KEY_Documented_anaesthesia_status,pojo.getDocumented_anaesthesia_status());
        values.put(KEY_Remark_documented_anaesthesia,pojo.getRemark_documented_anaesthesia());
        values.put(KEY_Nc_documented_anaesthesia ,pojo.getNc_documented_anaesthesia());
        values.put(KEY_Immediate_preoperative_status,pojo.getImmediate_preoperative_status());
        values.put(KEY_Remark_immediate_preoperative,pojo.getRemark_immediate_preoperative());
        values.put(KEY_Nc_immediate_preoperative,pojo.getNc_immediate_preoperative());
        values.put(KEY_Anaesthesia_monitoring_status,pojo.getAnaesthesia_monitoring_status());
        values.put(KEY_Remark_anaesthesia_monitoring,pojo.getRemark_anaesthesia_monitoring());
        values.put(KEY_Nc_anaesthesia_monitoring,pojo.getNc_anaesthesia_monitoring());
        values.put(KEY_Post_anaesthesia_monitoring_status,pojo.getPost_anaesthesia_monitoring_status());
        values.put(KEY_Remark_post_anaesthesia_monitoring,pojo.getRemark_post_anaesthesia_monitoring());
        values.put(KEY_Nc_post_anaesthesia_monitoring,pojo.getNc_post_anaesthesia_monitoring());
        values.put(KEY_WHO_Patient_Safety_status,pojo.getWHO_Patient_Safety_status());
        values.put(KEY_Remark_WHO_Patient_Safety,pojo.getRemark_WHO_Patient_Safety());
        values.put(KEY_Nc_WHO_Patient_Safety,pojo.getNc_WHO_Patient_Safety());
        values.put(KEY_Image_WHO_Patient_Safety_day1,pojo.getImage_WHO_Patient_Safety_day1());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day1,pojo.getLocal_image_WHO_Patient_Safety_day1());
        values.put(KEY_Image_WHO_Patient_Safety_day2,pojo.getImage_WHO_Patient_Safety_day2());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day2,pojo.getLocal_image_WHO_Patient_Safety_day2());
        values.put(KEY_Image_WHO_Patient_Safety_day3,pojo.getImage_WHO_Patient_Safety_day3());
        values.put(Local_KEY_Image_WHO_Patient_Safety_day3,pojo.getLocal_image_WHO_Patient_Safety_day3());
        values.put(KEY_OT_Zoning_status,pojo.getOT_Zoning_status());
        values.put(KEY_Remark_OT_Zoning,pojo.getRemark_OT_Zoning());
        values.put(KEY_Nc_OT_Zoning,pojo.getNc_OT_Zoning());
        values.put(KEY_Video_OT_Zoning,pojo.getVideo_OT_Zoning());
        values.put(KEY_Local_video_OT_Zoning,pojo.getLocal_video_OT_Zoning());
        values.put(KEY_Infection_control_status,pojo.getInfection_control_status());
        values.put(KEY_Remark_infection_control,pojo.getRemark_infection_control());
        values.put(KEY_Nc_infection_control,pojo.getNc_infection_control());
        values.put(KEY_Image_infection_control,pojo.getImage_infection_control());
        values.put(Local_KEY_Image_infection_control,pojo.getLocal_image_infection_control());
        values.put(KEY_Narcotic_drugs_status,pojo.getNarcotic_drugs_status());
        values.put(KEY_Remark_narcotic_drugs,pojo.getRemark_narcotic_drugs());
        values.put(KEY_Nc_narcotic_drugs,pojo.getNc_narcotic_drugs());
        values.put(KEY_Image_narcotic_drugs,pojo.getImage_narcotic_drugs());
        values.put(Local_KEY_Image_narcotic_drugs,pojo.getLocal_image_narcotic_drugs());
        values.put(KEY_Administration_disposal_status,pojo.getAdministration_disposal_status());
        values.put(KEY_Remark_administration_disposal,pojo.getRemark_administration_disposal());
        values.put(KEY_Nc_administration_disposal,pojo.getNc_administration_disposal());
        values.put(KEY_Image_administration_disposal,pojo.getImage_administration_disposal());
        values.put(Local_KEY_Image_administration_disposal,pojo.getLocal_image_administration_disposal());
        values.put(Key_hand_wash_facility_status,pojo.getHand_wash_facility_status());
        values.put(Key_remark_hand_wash_facility,pojo.getRemark_hand_wash_facility());
        values.put(Key_nc_hand_wash_facility,pojo.getNc_hand_wash_facility());
        values.put(Key_image_hand_wash_facility,pojo.getImage_hand_wash_facility());
        values.put(Key_local_image_hand_wash_facility,pojo.getLocal_image_hand_wash_facility());


        database.update(TABLE_OT,values,KEY_Local_id + "=" + pojo.getLocal_id(),null);


        return true;
    }

    public DocumentationPojo getDocumentationPojo(String Hospital_id){
        DocumentationPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Documentation + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                pojo = new DocumentationPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setDocument_related_procedure(cursor.getString(cursor.getColumnIndex(Key_document_related_procedure)));
                pojo.setDocument_related_procedure_remark(cursor.getString(cursor.getColumnIndex(Key_document_related_procedure_remark)));
                pojo.setDocument_related_procedure_nc(cursor.getString(cursor.getColumnIndex(Key_document_related_procedure_nc)));
                pojo.setDocument_showing_process(cursor.getString(cursor.getColumnIndex(Key_document_showing_process)));
                pojo.setDocument_showing_process_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_process_remark)));
                pojo.setDocument_showing_process_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_process_nc)));
                pojo.setDocument_showing_care_patients(cursor.getString(cursor.getColumnIndex(Key_document_showing_care_patients)));
                pojo.setDocument_showing_care_patients_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_care_patients_remark)));
                pojo.setDocument_showing_care_patients_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_care_patients_nc)));
                pojo.setDocument_showing_policies(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies)));
                pojo.setDocument_showing_policies_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_remark)));
                pojo.setDocument_showing_policies_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_nc)));
                pojo.setDocument_showing_procedures(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedures)));
                pojo.setDocument_showing_procedures_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedures_remark)));
                pojo.setDocument_showing_procedures_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedures_nc)));
                pojo.setDocument_showing_procedure_administration(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_administration)));
                pojo.setDocument_showing_procedure_administration_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_administration_remark)));
                pojo.setDocument_showing_procedure_administration_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_administration_nc)));
                pojo.setDocument_showing_defined_criteria(cursor.getString(cursor.getColumnIndex(Key_document_showing_defined_criteria)));
                pojo.setDocument_showing_defined_criteria_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_defined_criteria_remark)));
                pojo.setDocument_showing_defined_criteria_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_defined_criteria_nc)));
                pojo.setDocument_showing_procedure_prevention(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_prevention)));
                pojo.setDocument_showing_procedure_prevention_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_prevention_remark)));
                pojo.setDocument_showing_procedure_prevention_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_prevention_nc)));
                pojo.setDocument_showing_procedure_incorporating(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_incorporating)));
                pojo.setDocument_showing_procedure_incorporating_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_incorporating_remark)));
                pojo.setDocument_showing_procedure_incorporating_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_incorporating_nc)));
                pojo.setDocument_showing_procedure_address(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_address)));
                pojo.setDocument_showing_procedure_address_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_address_remark)));
                pojo.setDocument_showing_procedure_address_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_procedure_address_nc)));
                pojo.setDocument_showing_policies_procedure(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedure)));
                pojo.setDocument_showing_policies_procedure_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedure_remark)));
                pojo.setDocument_showing_policies_procedure_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedure_nc)));
                pojo.setDocument_showing_drugs_available(cursor.getString(cursor.getColumnIndex(Key_document_showing_drugs_available)));
                pojo.setDocument_showing_drugs_available_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_drugs_available_remark)));
                pojo.setDocument_showing_drugs_available_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_drugs_available_nc)));
                pojo.setDocument_showing_safe_storage(cursor.getString(cursor.getColumnIndex(key_document_showing_safe_storage)));
                pojo.setDocument_showing_safe_storage_remark(cursor.getString(cursor.getColumnIndex(key_document_showing_safe_storage_remark)));
                pojo.setDocument_showing_safe_storage_nc(cursor.getString(cursor.getColumnIndex(key_document_showing_safe_storage_nc)));
                pojo.setInfection_control_manual_showing(cursor.getString(cursor.getColumnIndex(Key_Infection_control_manual_showing)));
                pojo.setInfection_control_manual_showing_remark(cursor.getString(cursor.getColumnIndex(Key_Infection_control_manual_showing_remark)));
                pojo.setInfection_control_manual_showing_nc(cursor.getString(cursor.getColumnIndex(Key_Infection_control_manual_showing_nc)));
                pojo.setDocument_showing_operational_maintenance(cursor.getString(cursor.getColumnIndex(Key_document_showing_operational_maintenance)));
                pojo.setDocument_showing_operational_maintenance_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_operational_maintenance_remark)));
                pojo.setDocument_showing_operational_maintenance_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_operational_maintenance_nc)));
                pojo.setDocument_showing_safe_exit_plan(cursor.getString(cursor.getColumnIndex(Key_document_showing_safe_exit_plan)));
                pojo.setDocument_showing_safe_exit_plan_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_safe_exit_plan_remark)));
                pojo.setDocument_showing_safe_exit_plan_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_safe_exit_plan_nc)));
                pojo.setDocument_showing_well_defined_staff(cursor.getString(cursor.getColumnIndex(Key_document_showing_well_defined_staff)));
                pojo.setDocument_showing_well_defined_staff_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_well_defined_staff_remark)));
                pojo.setDocument_showing_well_defined_staff_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_well_defined_staff_nc)));
                pojo.setDocument_showing_disciplinary_grievance(cursor.getString(cursor.getColumnIndex(Key_document_showing_disciplinary_grievance)));
                pojo.setDocument_showing_disciplinary_grievance_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_disciplinary_grievance_remark)));
                pojo.setDocument_showing_disciplinary_grievance_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_disciplinary_grievance_nc)));
                pojo.setDocument_showing_policies_procedures(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedures)));
                pojo.setDocument_showing_policies_procedures_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedures_remark)));
                pojo.setDocument_showing_policies_procedures_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_policies_procedures_nc)));
                pojo.setDocument_showing_retention_time(cursor.getString(cursor.getColumnIndex(Key_document_showing_retention_time)));
                pojo.setDocument_showing_retention_time_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_retention_time_remark)));
                pojo.setDocument_showing_retention_time_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_retention_time_nc)));
                pojo.setDocument_showing_define_process(cursor.getString(cursor.getColumnIndex(Key_document_showing_define_process)));
                pojo.setDocument_showing_define_process_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_define_process_remark)));
                pojo.setDocument_showing_define_process_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_define_process_nc)));
                pojo.setDocument_showing_medical_records(cursor.getString(cursor.getColumnIndex(Key_document_showing_medical_records)));
                pojo.setDocument_showing_medical_records_remark(cursor.getString(cursor.getColumnIndex(Key_document_showing_medical_records_remark)));
                pojo.setDocument_showing_medical_records_nc(cursor.getString(cursor.getColumnIndex(Key_document_showing_medical_records_nc)));

            }
        }

        cursor.close();
        return pojo;
    }

    public ManagementPojo getManagementPojo(String Hospital_id){

        ManagementPojo pojo = null;


        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Management + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                 cursor.moveToFirst();

                 pojo = new ManagementPojo();
                 pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                 pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                 pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                 pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                 pojo.setRequisite_fee_BMW(cursor.getString(cursor.getColumnIndex(Key_requisite_fee_BMW)));
                 pojo.setRequisite_fee_BMW_remark(cursor.getString(cursor.getColumnIndex(Key_requisite_fee_BMW_Remark)));
                 pojo.setRequisite_fee_BMW_nc(cursor.getString(cursor.getColumnIndex(Key_requisite_fee_BMW_Nc)));
                 pojo.setManagement_guide_organization(cursor.getString(cursor.getColumnIndex(Key_management_guide_organization)));
                 pojo.setManagement_guide_organization_remark(cursor.getString(cursor.getColumnIndex(Key_management_guide_organization_Remark)));
                 pojo.setManagement_guide_organization_nc(cursor.getString(cursor.getColumnIndex(Key_management_guide_organization_Nc)));
                 pojo.setHospital_mission_present(cursor.getString(cursor.getColumnIndex(Key_hospital_mission_present)));
                 pojo.setHospital_mission_present_remark(cursor.getString(cursor.getColumnIndex(Key_hospital_mission_present_Remark)));
                 pojo.setHospital_mission_present_nc(cursor.getString(cursor.getColumnIndex(Key_hospital_mission_present_Nc)));
                 pojo.setHospital_mission_present_Image(cursor.getString(cursor.getColumnIndex(Key_hospital_mission_present_Image)));
                 pojo.setLocal_hospital_mission_present_Image(cursor.getString(cursor.getColumnIndex(Local_Key_hospital_mission_present_Image)));
                 pojo.setPatient_maintained_OPD(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_OPD)));
                 pojo.setPatient_maintained_OPD_remark(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_OPD_Remark)));
                 pojo.setPatient_maintained_OPD_nc(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_OPD_Nc)));
                 pojo.setPatient_maintained_OPD_image(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_OPD_Image)));
                 pojo.setLocal_patient_maintained_OPD_image(cursor.getString(cursor.getColumnIndex(Local_Key_patient_maintained_OPD_Image)));
                 pojo.setPatient_maintained_IPD(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_IPD)));
                 pojo.setPatient_maintained_IPD_remark(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_IPD_Remark)));
                 pojo.setPatient_maintained_IPD_nc(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_IPD_Nc)));
                 pojo.setPatient_maintained_IPD_image(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_IPD_Image)));
                 pojo.setLocal_patient_maintained_IPD_image(cursor.getString(cursor.getColumnIndex(Local_Key_patient_maintained_IPD_Image)));
                 pojo.setPatient_maintained_Emergency(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_Emergency)));
                 pojo.setPatient_maintained_Emergency_remark(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_Emergency_Remark)));
                 pojo.setPatient_maintained_Emergency_nc(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_Emergency_Nc)));
                 pojo.setPatient_maintained_Emergency_image(cursor.getString(cursor.getColumnIndex(Key_patient_maintained_Emergency_Image)));
                 pojo.setLocal_patient_maintained_Emergency_image(cursor.getString(cursor.getColumnIndex(Local_Key_patient_maintained_Emergency_Image)));
                 pojo.setBasic_Tariff_List(cursor.getString(cursor.getColumnIndex(Key_basic_Tariff_List)));
                 pojo.setBasic_Tariff_List_remark(cursor.getString(cursor.getColumnIndex(Key_basic_Tariff_List_Remark)));
                 pojo.setBasic_Tariff_List_nc(cursor.getString(cursor.getColumnIndex(Key_basic_Tariff_List_Nc)));
                 pojo.setBasic_Tariff_List_image(cursor.getString(cursor.getColumnIndex(Key_basic_Tariff_List_Image)));
                 pojo.setLocal_basic_Tariff_List_image(cursor.getString(cursor.getColumnIndex(Local_Key_basic_Tariff_List_Image)));
                 pojo.setParameter_patient_identification(cursor.getString(cursor.getColumnIndex(Key_parameter_patient_identification)));
                 pojo.setParameter_patient_identification_remark(cursor.getString(cursor.getColumnIndex(Key_parameter_patient_identification_Remark)));
                 pojo.setParameter_patient_identification_nc(cursor.getString(cursor.getColumnIndex(Key_parameter_patient_identification_Nc)));
                 pojo.setQuality_improvement_programme(cursor.getString(cursor.getColumnIndex(key_quality_improvement_programme)));
                 pojo.setQuality_improvement_programme_remark(cursor.getString(cursor.getColumnIndex(key_quality_improvement_programme_Remark)));
                 pojo.setQuality_improvement_programme_nc(cursor.getString(cursor.getColumnIndex(key_quality_improvement_programme_Nc)));



            }
        }
        cursor.close();
        return pojo;
    }

    public SterilizationAreaPojo getSterilizationArea(String Hospital_id){
        SterilizationAreaPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Sterilization_Area + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new SterilizationAreaPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setSterilisation_practices_adhered(cursor.getString(cursor.getColumnIndex(Key_sterilisation_practices_adhered)));
                pojo.setSterilisation_practices_adhered_remark(cursor.getString(cursor.getColumnIndex(Key_sterilisation_practices_adhered_Remark)));
                pojo.setSterilisation_practices_adhered_nc(cursor.getString(cursor.getColumnIndex(Key_sterilisation_practices_adhered_Nc)));
                pojo.setMonitor_effectiveness_sterilization_process(cursor.getString(cursor.getColumnIndex(Key_monitor_effectiveness_sterilization_process)));
                pojo.setMonitor_effectiveness_sterilization_process_remark(cursor.getString(cursor.getColumnIndex(Key_monitor_effectiveness_sterilization_process_Remark)));
                pojo.setMonitor_effectiveness_sterilization_process_nc(cursor.getString(cursor.getColumnIndex(Key_monitor_effectiveness_sterilization_process_Nc)));
                pojo.setMonitor_effectiveness_sterilization_process_image(cursor.getString(cursor.getColumnIndex(Key_monitor_effectiveness_sterilization_process_Image)));
                pojo.setLocal_monitor_effectiveness_sterilization_process_image(cursor.getString(cursor.getColumnIndex(Local_Key_monitor_effectiveness_sterilization_process_Image)));
                pojo.setSterilized_drums_trays(cursor.getString(cursor.getColumnIndex(Key_sterilized_drums_trays)));
                pojo.setSterilized_drums_trays_remark(cursor.getString(cursor.getColumnIndex(Key_sterilized_drums_trays_Remark)));
                pojo.setSterilized_drums_trays_nc(cursor.getString(cursor.getColumnIndex(Key_sterilized_drums_trays_Nc)));
                pojo.setSterilized_drums_trays_image(cursor.getString(cursor.getColumnIndex(Key_sterilized_drums_trays_Image)));
                pojo.setLocal_sterilized_drums_trays_image(cursor.getString(cursor.getColumnIndex(Local_Key_sterilized_drums_trays_Image)));

            }
        }

        cursor.close();
        return pojo;
    }

    public BioMedicalEngineeringPojo getBioMedicalEngineering(String Hospital_id){

        BioMedicalEngineeringPojo pojo = null;


        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Bio_medical_engineering + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new BioMedicalEngineeringPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setMaintenance_staff_contactable(cursor.getString(cursor.getColumnIndex(Key_Maintenance_staff_contactable)));
                pojo.setMaintenance_staff_contactable_remark(cursor.getString(cursor.getColumnIndex(Key_Maintenance_staff_contactable_Remark)));
                pojo.setMaintenance_staff_contactable_nc(cursor.getString(cursor.getColumnIndex(Key_Maintenance_staff_contactable_Nc)));
                pojo.setEquipment_accordance_services(cursor.getString(cursor.getColumnIndex(key_equipment_accordance_services)));
                pojo.setEquipment_accordance_services_remark(cursor.getString(cursor.getColumnIndex(key_equipment_accordance_services_Remark)));
                pojo.setEquipment_accordance_services_nc(cursor.getString(cursor.getColumnIndex(key_equipment_accordance_services_Nc)));
                pojo.setDocumented_operational_maintenance(cursor.getString(cursor.getColumnIndex(Key_documented_operational_maintenance)));
                pojo.setDocumented_operational_maintenance_remark(cursor.getString(cursor.getColumnIndex(Key_documented_operational_maintenance_Remark)));
                pojo.setDocumented_operational_maintenance_nc(cursor.getString(cursor.getColumnIndex(Key_documented_operational_maintenance_Nc)));
                pojo.setDocumented_operational_maintenance_image(cursor.getString(cursor.getColumnIndex(Key_documented_operational_maintenance_Image)));
                pojo.setLocal_documented_operational_maintenance_image(cursor.getString(cursor.getColumnIndex(Local_Key_documented_operational_maintenance_Image)));
            }
        }
        cursor.close();
        return pojo;
    }

    public FacilityChecksPojo getFacilityPojo(String Hospital_id){
        FacilityChecksPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Facility_Checks + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new FacilityChecksPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setMedical_gas_cylinders(cursor.getString(cursor.getColumnIndex(Key_medical_gas_cylinders)));
                pojo.setMedical_gas_cylinders_remark(cursor.getString(cursor.getColumnIndex(Key_medical_gas_cylinders_Remark)));
                pojo.setMedical_gas_cylinders_nc(cursor.getString(cursor.getColumnIndex(Key_medical_gas_cylinders_Nc)));
                pojo.setMedical_gas_cylinders_image(cursor.getString(cursor.getColumnIndex(Key_medical_gas_cylinders_Image)));
                pojo.setLocal_medical_gas_cylinders_image(cursor.getString(cursor.getColumnIndex(Local_Key_medical_gas_cylinders_Image)));
                pojo.setSmoke_detectors_installed(cursor.getString(cursor.getColumnIndex(Key_smoke_detectors_installed)));
                pojo.setSmoke_detectors_installed_remark(cursor.getString(cursor.getColumnIndex(Key_smoke_detectors_installed_Remark)));
                pojo.setSmoke_detectors_installed_nc(cursor.getString(cursor.getColumnIndex(Key_smoke_detectors_installed_Nc)));
                pojo.setSmoke_detectors_installed_image(cursor.getString(cursor.getColumnIndex(Key_smoke_detectors_installed_Image)));
                pojo.setLocal_smoke_detectors_installed_image(cursor.getString(cursor.getColumnIndex(Local_Key_smoke_detectors_installed_Image)));
                pojo.setExtinguisher_present_patient(cursor.getString(cursor.getColumnIndex(Key_extinguisher_present_patient)));
                pojo.setExtinguisher_present_patient_remark(cursor.getString(cursor.getColumnIndex(Key_extinguisher_present_patient_Remark)));
                pojo.setExtinguisher_present_patient_nc(cursor.getString(cursor.getColumnIndex(Key_extinguisher_present_patient_Nc)));
                pojo.setExtinguisher_present_patient_image(cursor.getString(cursor.getColumnIndex(Key_extinguisher_present_patient_Image)));
                pojo.setLocal_extinguisher_present_patient_image(cursor.getString(cursor.getColumnIndex(Local_Key_extinguisher_present_patient_Image)));
                pojo.setFire_fighting_equipment(cursor.getString(cursor.getColumnIndex(Key_fire_fighting_equipment)));
                pojo.setFire_fighting_equipment_remark(cursor.getString(cursor.getColumnIndex(Key_fire_fighting_equipment_Remark)));
                pojo.setFire_fighting_equipment_nc(cursor.getString(cursor.getColumnIndex(Key_fire_fighting_equipment_Nc)));
                pojo.setSafe_exit_plan(cursor.getString(cursor.getColumnIndex(Key_safe_exit_plan)));
                pojo.setSafe_exit_plan_remark(cursor.getString(cursor.getColumnIndex(Key_safe_exit_plan_Remark)));
                pojo.setSafe_exit_plan_nc(cursor.getString(cursor.getColumnIndex(Key_safe_exit_plan_Nc)));

            }
        }

        cursor.close();
        return pojo;
    }

    public SafetManagementPojo getSafetManagement(String Hospital_id){
        SafetManagementPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Safety_Management + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                 pojo = new SafetManagementPojo();

                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setSafety_device_lab(cursor.getString(cursor.getColumnIndex(Key_safety_device_lab)));
                pojo.setSafety_device_lab_remark(cursor.getString(cursor.getColumnIndex(Key_safety_device_lab_Remark)));
                pojo.setSafety_device_lab_nc(cursor.getString(cursor.getColumnIndex(Key_safety_device_lab_Nc)));
                pojo.setSafety_device_lab_image(cursor.getString(cursor.getColumnIndex(Key_safety_device_lab_Image)));
                pojo.setLocal_safety_device_lab_image(cursor.getString(cursor.getColumnIndex(Local_Key_safety_device_lab_Image)));
                pojo.setBody_parts_staff_patients(cursor.getString(cursor.getColumnIndex(Key_body_parts_staff_patients)));
                pojo.setBody_parts_staff_patients_remark(cursor.getString(cursor.getColumnIndex(Key_body_parts_staff_patients_Remark)));
                pojo.setBody_parts_staff_patients_nc(cursor.getString(cursor.getColumnIndex(Key_body_parts_staff_patients_Nc)));
                pojo.setBody_parts_staff_patients_image(cursor.getString(cursor.getColumnIndex(Key_body_parts_staff_patients_Image)));
                pojo.setLocal_body_parts_staff_patients_image(cursor.getString(cursor.getColumnIndex(Local_Key_body_parts_staff_patients_Image)));
                pojo.setStaff_member_radiation_area(cursor.getString(cursor.getColumnIndex(Key_staff_member_radiation_area)));
                pojo.setStaff_member_radiation_area_remark(cursor.getString(cursor.getColumnIndex(Key_staff_member_radiation_area_Remark)));
                pojo.setStaff_member_radiation_area_nc(cursor.getString(cursor.getColumnIndex(Key_staff_member_radiation_area_Nc)));
                pojo.setStaff_member_radiation_area_image(cursor.getString(cursor.getColumnIndex(Key_staff_member_radiation_area_Image)));
                pojo.setLocal_staff_member_radiation_area_image(cursor.getString(cursor.getColumnIndex(Local_Key_staff_member_radiation_area_Image)));
                pojo.setStandardised_colur_coding(cursor.getString(cursor.getColumnIndex(Key_standardised_colur_coding)));
                pojo.setStandardised_colur_coding_remark(cursor.getString(cursor.getColumnIndex(Key_standardised_colur_coding_Remark)));
                pojo.setStandardised_colur_coding_nc(cursor.getString(cursor.getColumnIndex(Key_standardised_colur_coding_Nc)));
                pojo.setStandardised_colur_coding_image(cursor.getString(cursor.getColumnIndex(Key_standardised_colur_coding_Image)));
                pojo.setLocal_standardised_colur_coding_image(cursor.getString(cursor.getColumnIndex(Local_Key_standardised_colur_coding_Image)));
                pojo.setSafe_storage_medical(cursor.getString(cursor.getColumnIndex(Key_safe_storage_medical)));
                pojo.setSafe_storage_medical_remark(cursor.getString(cursor.getColumnIndex(Key_safe_storage_medical_Remark)));
                pojo.setSafe_storage_medical_nc(cursor.getString(cursor.getColumnIndex(Key_safe_storage_medical_Nc)));
                pojo.setSafe_storage_medical_image(cursor.getString(cursor.getColumnIndex(Key_safe_storage_medical_Image)));
                pojo.setLocal_safe_storage_medical_image(cursor.getString(cursor.getColumnIndex(Local_Key_safe_storage_medical_Image)));
            }
        }

        cursor.close();
        return pojo;
    }

    public AmbulanceAccessibilityPojo getAmbulanceAccessibilityPojo(String Hospital_id){
        AmbulanceAccessibilityPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Ambulance_Accessibility + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new AmbulanceAccessibilityPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setAccess_ambulance_patient_drop(cursor.getString(cursor.getColumnIndex(Key_access_ambulance_patient_drop)));
                pojo.setAccess_ambulance_patient_drop_remark(cursor.getString(cursor.getColumnIndex(Key_access_ambulance_patient_drop_remark)));
                pojo.setAccess_ambulance_patient_drop_nc(cursor.getString(cursor.getColumnIndex(Key_access_ambulance_patient_drop_nc)));
                pojo.setOwnership_the_ambulance(cursor.getString(cursor.getColumnIndex(Key_ownership_the_ambulance)));
                pojo.setOwnership_the_ambulance_remark(cursor.getString(cursor.getColumnIndex(Key_ownership_the_ambulance_remark)));
                pojo.setOwnership_the_ambulance_nc(cursor.getString(cursor.getColumnIndex(Key_ownership_the_ambulance_Nc)));
                pojo.setTotal_number_ambulance_available(cursor.getString(cursor.getColumnIndex(Key_total_number_ambulance_available)));
                pojo.setTotal_number_ambulance_available_remark(cursor.getString(cursor.getColumnIndex(Key_total_number_ambulance_available_Remark)));
                pojo.setTotal_number_ambulance_available_nc(cursor.getString(cursor.getColumnIndex(Key_total_number_ambulance_available_Nc)));
                pojo.setTotal_number_ambulance_available_image(cursor.getString(cursor.getColumnIndex(Key_total_number_ambulance_available_Image)));
                pojo.setLocal_total_number_ambulance_available_image(cursor.getString(cursor.getColumnIndex(Local_Key_total_number_ambulance_available_Image)));
                pojo.setAmbulance_appropriately_equiped(cursor.getString(cursor.getColumnIndex(Key_ambulance_appropriately_equiped)));
                pojo.setAmbulance_appropriately_equiped_remark(cursor.getString(cursor.getColumnIndex(Key_ambulance_appropriately_equiped_Remark)));
                pojo.setAmbulance_appropriately_equiped_nc(cursor.getString(cursor.getColumnIndex(Key_ambulance_appropriately_equiped_Nc)));
                pojo.setAmbulance_appropriately_equiped_image(cursor.getString(cursor.getColumnIndex(Key_ambulance_appropriately_equiped_Image)));
                pojo.setLocal_ambulance_appropriately_equiped_image(cursor.getString(cursor.getColumnIndex(Local_Key_ambulance_appropriately_equiped_Image)));
                pojo.setDrivers_ambulances_available(cursor.getString(cursor.getColumnIndex(Key_drivers_ambulances_available)));
                pojo.setDrivers_ambulances_available_remark(cursor.getString(cursor.getColumnIndex(Key_drivers_ambulances_available_Remark)));
                pojo.setDrivers_ambulances_available_nc(cursor.getString(cursor.getColumnIndex(Key_drivers_ambulances_available_Nc)));
                pojo.setDrivers_ambulances_available_image(cursor.getString(cursor.getColumnIndex(Key_drivers_ambulances_available_Image)));
                pojo.setLocal_drivers_ambulances_available_image(cursor.getString(cursor.getColumnIndex(Local_Key_drivers_ambulances_available_Image)));
                pojo.setDoctors_available_ambulances(cursor.getString(cursor.getColumnIndex(Key_doctors_available_ambulances)));
                pojo.setDoctors_available_ambulances_remark(cursor.getString(cursor.getColumnIndex(Key_doctors_available_ambulances_Remark)));
                pojo.setDoctors_available_ambulances_nc(cursor.getString(cursor.getColumnIndex(Key_doctors_available_ambulances_Nc)));
                pojo.setDoctors_available_ambulances_image(cursor.getString(cursor.getColumnIndex(Key_doctors_available_ambulances_Image)));
                pojo.setLocal_doctors_available_ambulances_image(cursor.getString(cursor.getColumnIndex(Local_Key_doctors_available_ambulances_Image)));
                pojo.setNurses_available_ambulances(cursor.getString(cursor.getColumnIndex(Key_nurses_available_ambulances)));
                pojo.setNurses_available_ambulances_remark(cursor.getString(cursor.getColumnIndex(Key_nurses_available_ambulances_Remark)));
                pojo.setNurses_available_ambulances_nc(cursor.getString(cursor.getColumnIndex(Key_nurses_available_ambulances_Nc)));
                pojo.setNurses_available_ambulances_image(cursor.getString(cursor.getColumnIndex(Key_nurses_available_ambulances_Image)));
                pojo.setLocal_nurses_available_ambulances_image(cursor.getString(cursor.getColumnIndex(Local_Key_nurses_available_ambulances_Image)));
            }
        }
        cursor.close();
        return pojo;
    }

    public UniformSignagePojo getUniformSignagePojo(String Hospital_id){
        UniformSignagePojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Uniform_Signage + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new UniformSignagePojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setScope_services_present(cursor.getString(cursor.getColumnIndex(Key_scope_services_present)));
                pojo.setScope_services_present_remark(cursor.getString(cursor.getColumnIndex(Key_scope_services_present_Remark)));
                pojo.setScope_services_present_nc(cursor.getString(cursor.getColumnIndex(Key_scope_services_present_Nc)));
                pojo.setScope_services_present_image(cursor.getString(cursor.getColumnIndex(Key_scope_services_present_Image)));
                pojo.setLocal_scope_services_present_image(cursor.getString(cursor.getColumnIndex(Local_Key_scope_services_present_Image)));
                pojo.setPatients_responsibility_displayed(cursor.getString(cursor.getColumnIndex(Key_Patients_responsibility_displayed)));
                pojo.setPatients_responsibility_displayed_remark(cursor.getString(cursor.getColumnIndex(Key_Patients_responsibility_displayed_Remark)));
                pojo.setPatients_responsibility_displayed_nc(cursor.getString(cursor.getColumnIndex(Key_Patients_responsibility_displayed_Nc)));
                pojo.setPatients_responsibility_displayed_image(cursor.getString(cursor.getColumnIndex(Key_Patients_responsibility_displayed_Image)));
                pojo.setLocal_Patients_responsibility_displayed_image(cursor.getString(cursor.getColumnIndex(Local_Key_Patients_responsibility_displayed_Image)));
                pojo.setFire_exit_signage_present(cursor.getString(cursor.getColumnIndex(Key_fire_exit_signage_present)));
                pojo.setFire_exit_signage_present_remark(cursor.getString(cursor.getColumnIndex(Key_fire_exit_signage_present_Remark)));
                pojo.setFire_exit_signage_present_nc(cursor.getString(cursor.getColumnIndex(Key_fire_exit_signage_present_Nc)));
                pojo.setFire_exit_signage_present_image(cursor.getString(cursor.getColumnIndex(Key_fire_exit_signage_present_Image)));
                pojo.setLocal_fire_exit_signage_present_image(cursor.getString(cursor.getColumnIndex(Local_Key_fire_exit_signage_present_Image)));
                pojo.setDirectional_signages_present(cursor.getString(cursor.getColumnIndex(Key_directional_signages_present)));
                pojo.setDirectional_signages_present(cursor.getString(cursor.getColumnIndex(Key_directional_signages_present)));
                pojo.setDirectional_signages_present_remark(cursor.getString(cursor.getColumnIndex(Key_directional_signages_present_Remark)));
                pojo.setDirectional_signages_present_Nc(cursor.getString(cursor.getColumnIndex(Key_directional_signages_present_Nc)));
                pojo.setDirectional_signages_present_image(cursor.getString(cursor.getColumnIndex(Key_directional_signages_present_Image)));
                pojo.setLocal_directional_signages_present_image(cursor.getString(cursor.getColumnIndex(Local_Key_directional_signages_present_Image)));
                pojo.setDepartmental_signages_present(cursor.getString(cursor.getColumnIndex(Key_departmental_signages_present)));
                pojo.setDepartmental_signages_present_remark(cursor.getString(cursor.getColumnIndex(Key_departmental_signages_present_Remark)));
                pojo.setDepartmental_signages_present_nc(cursor.getString(cursor.getColumnIndex(Key_departmental_signages_present_Nc)));
                pojo.setDepartmental_signages_present_image(cursor.getString(cursor.getColumnIndex(Key_departmental_signages_present_Image)));
                pojo.setLocal_departmental_signages_present_image(cursor.getString(cursor.getColumnIndex(Local_Key_departmental_signages_present_Image)));
            }
        }

        cursor.close();
        return pojo;
    }



    public HousekeepingPojo getHouseKeeping(String Hospital_id){
        HousekeepingPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Housekeeping + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new HousekeepingPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setInfected_patient_room(cursor.getString(cursor.getColumnIndex(Key_infected_patient_room)));
                pojo.setInfected_patient_room_remark(cursor.getString(cursor.getColumnIndex(Key_infected_patient_room_Remark)));
                pojo.setInfected_patient_room_nc(cursor.getString(cursor.getColumnIndex(Key_infected_patient_room_Nc)));
                pojo.setProcedure_cleaning_room(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_room)));
                pojo.setProcedure_cleaning_room_remark(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_room_Remark)));
                pojo.setProcedure_cleaning_room_nc(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_room_Nc)));
                pojo.setProcedure_cleaning_blood_spill(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_blood_spill)));
                pojo.setProcedure_cleaning_blood_spill_remark(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_blood_spill_Remark)));
                pojo.setProcedure_cleaning_blood_spill_nc(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_blood_spill_Nc)));
                pojo.setProcedure_cleaning_blood_spill_video(cursor.getString(cursor.getColumnIndex(Key_procedure_cleaning_blood_spill_Video)));
                pojo.setLocal_procedure_cleaning_blood_spill_video(cursor.getString(cursor.getColumnIndex(Local_Key_procedure_cleaning_blood_spill_Video)));
                pojo.setBiomedical_Waste_regulations(cursor.getString(cursor.getColumnIndex(Key_Biomedical_Waste_regulations)));
                pojo.setBiomedical_Waste_regulations_remark(cursor.getString(cursor.getColumnIndex(Key_Biomedical_Waste_regulations_Remark)));
                pojo.setBiomedical_Waste_regulations_nc(cursor.getString(cursor.getColumnIndex(Key_Biomedical_Waste_regulations_Nc)));
                pojo.setBiomedical_Waste_regulations_image(cursor.getString(cursor.getColumnIndex(Key_Biomedical_Waste_regulations_Image)));
                pojo.setLocal_Biomedical_Waste_regulations_image(cursor.getString(cursor.getColumnIndex(Local_Key_Biomedical_Waste_regulations_Image)));
                pojo.setCleaning_washing_blood_stained(cursor.getString(cursor.getColumnIndex(Key_cleaning_washing_blood_stained)));
                pojo.setCleaning_washing_blood_stained_remark(cursor.getString(cursor.getColumnIndex(Key_cleaning_washing_blood_stained_Remark)));
                pojo.setCleaning_washing_blood_stained_nc(cursor.getString(cursor.getColumnIndex(Key_cleaning_washing_blood_stained_Nc)));


            }
        }

        cursor.close();
        return pojo;
    }

    public MRDPojo getMRDPojo(String Hospital_id){
        MRDPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_MRD + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);

        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new MRDPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setMedical_record_death_certificate(cursor.getString(cursor.getColumnIndex(Key_medical_record_death_certificate)));
                pojo.setMedical_record_death_certificate_remark(cursor.getString(cursor.getColumnIndex(Key_medical_record_death_certificate_Remark)));
                pojo.setMedical_record_death_certificate_nc(cursor.getString(cursor.getColumnIndex(Key_medical_record_death_certificate_Nc)));
                pojo.setDocumented_maintaining_confidentiality(cursor.getString(cursor.getColumnIndex(Key_documented_maintaining_confidentiality)));
                pojo.setDocumented_maintaining_confidentiality_remark(cursor.getString(cursor.getColumnIndex(Key_documented_maintaining_confidentiality_Remark)));
                pojo.setDocumented_maintaining_confidentiality_nc(cursor.getString(cursor.getColumnIndex(Key_documented_maintaining_confidentiality_Nc)));
                pojo.setAny_information_disclosed(cursor.getString(cursor.getColumnIndex(Key_any_information_disclosed)));
                pojo.setAny_information_disclosed_remark(cursor.getString(cursor.getColumnIndex(Key_any_information_disclosed_Remark)));
                pojo.setAny_information_disclosed_nc(cursor.getString(cursor.getColumnIndex(Key_any_information_disclosed_Nc)));
                pojo.setDestruction_medical_records(cursor.getString(cursor.getColumnIndex(Key_destruction_medical_records)));
                pojo.setDestruction_medical_records_remark(cursor.getString(cursor.getColumnIndex(Key_destruction_medical_records_Remark)));
                pojo.setDestruction_medical_records_nc(cursor.getString(cursor.getColumnIndex(Key_destruction_medical_records_Nc)));
                pojo.setFire_extinguisher_present(cursor.getString(cursor.getColumnIndex(Key_fire_extinguisher_present)));
                pojo.setFire_extinguisher_present_remark(cursor.getString(cursor.getColumnIndex(Key_fire_extinguisher_present_Remark)));
                pojo.setFire_extinguisher_present_nc(cursor.getString(cursor.getColumnIndex(Key_fire_extinguisher_present_Nc)));
                pojo.setFire_extinguisher_present_image(cursor.getString(cursor.getColumnIndex(Key_fire_extinguisher_present_Image)));
                pojo.setLocal_fire_extinguisher_present_image(cursor.getString(cursor.getColumnIndex(Local_Key_fire_extinguisher_present_Image)));
            }
        }
        cursor.close();
        return pojo;
    }

    public HRMPojo getHRMPojo(String Hospital_id){
        HRMPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_HRM + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new HRMPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setStaff_health_related_issues(cursor.getString(cursor.getColumnIndex(Key_staff_health_related_issues)));
                pojo.setStaff_health_related_issues_remark(cursor.getString(cursor.getColumnIndex(Key_staff_health_related_issues_Remark)));
                pojo.setStaff_health_related_issues_nc(cursor.getString(cursor.getColumnIndex(Key_staff_health_related_issues_Nc)));
                pojo.setStaffs_personal_files_maintained(cursor.getString(cursor.getColumnIndex(Key_staffs_personal_files_maintained)));
                pojo.setStaffs_personal_files_maintained_remark(cursor.getString(cursor.getColumnIndex(Key_staffs_personal_files_maintained_Remark)));
                pojo.setStaffs_personal_files_maintained_nc(cursor.getString(cursor.getColumnIndex(Key_staffs_personal_files_maintained_Nc)));
                pojo.setStaffs_personal_files_maintained_image(cursor.getString(cursor.getColumnIndex(Key_staffs_personal_files_maintained_image)));
                pojo.setLocal_staffs_personal_files_maintained_image(cursor.getString(cursor.getColumnIndex(Local_Key_staffs_personal_files_maintained_image)));
                pojo.setOccupational_health_hazards(cursor.getString(cursor.getColumnIndex(Key_occupational_health_hazards)));
                pojo.setOccupational_health_hazards_remark(cursor.getString(cursor.getColumnIndex(Key_occupational_health_hazards_Remark)));
                pojo.setOccupational_health_hazards_nc(cursor.getString(cursor.getColumnIndex(Key_occupational_health_hazards_Nc)));
                pojo.setTraining_responsibility_changes(cursor.getString(cursor.getColumnIndex(Key_training_responsibility_changes)));
                pojo.setTraining_responsibility_changes_remark(cursor.getString(cursor.getColumnIndex(Key_training_responsibility_changes_Remark)));
                pojo.setTraining_responsibility_changes_nc(cursor.getString(cursor.getColumnIndex(Key_training_responsibility_changes_Nc)));
                pojo.setMedical_records_doctors_retrievable(cursor.getString(cursor.getColumnIndex(Key_medical_records_doctors_retrievable)));
                pojo.setMedical_records_doctors_retrievable_remark(cursor.getString(cursor.getColumnIndex(Key_medical_records_doctors_retrievable_Remark)));
                pojo.setMedical_records_doctors_retrievable_nc(cursor.getString(cursor.getColumnIndex(Key_medical_records_doctors_retrievable_Nc)));
                pojo.setCase_of_grievances(cursor.getString(cursor.getColumnIndex(Key_case_of_grievances)));
                pojo.setCase_of_grievances_remark(cursor.getString(cursor.getColumnIndex(Key_case_of_grievances_Remark)));
                pojo.setCase_of_grievances_nc(cursor.getString(cursor.getColumnIndex(Key_case_of_grievances_Nc)));
                pojo.setCase_of_grievances_video(cursor.getString(cursor.getColumnIndex(Key_case_of_grievances_Image)));
                pojo.setLocal_case_of_grievances_image(cursor.getString(cursor.getColumnIndex(Local_Key_case_of_grievances_Image)));
                pojo.setStaff_disciplinary_procedure(cursor.getString(cursor.getColumnIndex(Key_staff_disciplinary_procedure)));
                pojo.setStaff_disciplinary_procedure_remark(cursor.getString(cursor.getColumnIndex(Key_staff_disciplinary_procedure_Remark)));
                pojo.setStaff_disciplinary_procedure_nc(cursor.getString(cursor.getColumnIndex(Key_staff_disciplinary_procedure_Nc)));
                pojo.setStaff_disciplinary_procedure_video(cursor.getString(cursor.getColumnIndex(Key_staff_disciplinary_procedure_Video)));
                pojo.setLocal_staff_disciplinary_procedure_video(cursor.getString(cursor.getColumnIndex(Local_Key_staff_disciplinary_procedure_Video)));
                pojo.setStaff_able_to_demonstrate(cursor.getString(cursor.getColumnIndex(Key_staff_able_to_demonstrate)));
                pojo.setStaff_able_to_demonstrate_remark(cursor.getString(cursor.getColumnIndex(Key_staff_able_to_demonstrate_Remark)));
                pojo.setStaff_able_to_demonstrate_nc(cursor.getString(cursor.getColumnIndex(Key_staff_able_to_demonstrate_Nc)));


            }
        }
        cursor.close();
        return pojo;
    }
    public Ward_Ot_EmergencyPojo getWard_EmergencyPojo(String Hospital_id){
        Ward_Ot_EmergencyPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_Ward_Ot_Emergency+ " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new Ward_Ot_EmergencyPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setHospital_maintain_cleanliness(cursor.getString(cursor.getColumnIndex(Key_hospital_maintain_cleanliness)));
                pojo.setHospital_maintain_cleanliness_remark(cursor.getString(cursor.getColumnIndex(Key_hospital_maintain_cleanliness_Remark)));
                pojo.setHospital_maintain_cleanliness_nc(cursor.getString(cursor.getColumnIndex(Key_hospital_maintain_cleanliness_Nc)));
                pojo.setHospital_adhere_standard(cursor.getString(cursor.getColumnIndex(Key_hospital_adhere_standard)));
                pojo.setHospital_adhere_standard_remark(cursor.getString(cursor.getColumnIndex(Key_hospital_adhere_standard_Remark)));
                pojo.setHospital_adhere_standard_nc(cursor.getString(cursor.getColumnIndex(Key_hospital_adhere_standard_Nc)));
                pojo.setHospital_provide_adequate_gloves(cursor.getString(cursor.getColumnIndex(Key_hospital_provide_adequate_gloves)));
                pojo.setHospital_provide_adequate_gloves_remark(cursor.getString(cursor.getColumnIndex(Key_hospital_provide_adequate_gloves_Remark)));
                pojo.setHospital_provide_adequate_gloves_nc(cursor.getString(cursor.getColumnIndex(Key_hospital_provide_adequate_gloves_Nc)));
                pojo.setAdmissions_discharge_home(cursor.getString(cursor.getColumnIndex(Key_admissions_discharge_home)));
                pojo.setAdmissions_discharge_home_remark(cursor.getString(cursor.getColumnIndex(Key_admissions_discharge_home_Remark)));
                pojo.setAdmissions_discharge_home_nc(cursor.getString(cursor.getColumnIndex(Key_admissions_discharge_home_Nc)));
                pojo.setAdmissions_discharge_home_image(cursor.getString(cursor.getColumnIndex(Key_admissions_discharge_home_Video)));
                pojo.setLocal_admissions_discharge_home_image(cursor.getString(cursor.getColumnIndex(Local_Key_admissions_discharge_home_Video)));
                pojo.setStaff_present_in_ICU(cursor.getString(cursor.getColumnIndex(Key_staff_present_in_ICU)));
                pojo.setStaff_present_in_ICU_remark(cursor.getString(cursor.getColumnIndex(Key_staff_present_in_Remark)));
                pojo.setStaff_present_in_ICU_nc(cursor.getString(cursor.getColumnIndex(Key_staff_present_in_ICU_Nc)));

            }
        }

        cursor.close();
        return pojo;
    }

    public Patient_Staff_InterviewPojo getPatientStaffPojo(String Hospital_id){
        Patient_Staff_InterviewPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_Patient_Staff_Interview + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new Patient_Staff_InterviewPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setPrivacy_maintained(cursor.getString(cursor.getColumnIndex(Key_privacy_maintained)));
                pojo.setPrivacy_maintained_remark(cursor.getString(cursor.getColumnIndex(Key_privacy_maintained_Remark)));
                pojo.setPrivacy_maintained_nc(cursor.getString(cursor.getColumnIndex(Key_privacy_maintained_Nc)));
                pojo.setPrivacy_maintained_image(cursor.getString(cursor.getColumnIndex(Key_privacy_maintained_Image)));
                pojo.setLocal_privacy_maintained_image(cursor.getString(cursor.getColumnIndex(Local_Key_privacy_maintained_Image)));
                pojo.setPatients_protected_physical_abuse(cursor.getString(cursor.getColumnIndex(Key_Patients_protected_physical_abuse)));
                pojo.setPatients_protected_physical_abuse_remark(cursor.getString(cursor.getColumnIndex(Key_Patients_protected_physical_Remark)));
                pojo.setPatients_protected_physical_abuse_nc(cursor.getString(cursor.getColumnIndex(Key_Patients_protected_physical_abuse_Nc)));
                pojo.setPatient_information_confidential(cursor.getString(cursor.getColumnIndex(Key_patient_information_confidential)));
                pojo.setPatient_information_confidential_remark(cursor.getString(cursor.getColumnIndex(Key_patient_information_confidential_Remark)));
                pojo.setPatient_information_confidential_nc(cursor.getString(cursor.getColumnIndex(Key_patient_information_confidential_Nc)));
                pojo.setPatients_consent_carrying(cursor.getString(cursor.getColumnIndex(Key_patients_consent_carrying)));
                pojo.setPatients_consent_carrying_remark(cursor.getString(cursor.getColumnIndex(Key_patients_consent_carrying_Remark)));
                pojo.setPatients_consent_carrying_nc(cursor.getString(cursor.getColumnIndex(Key_patients_consent_carrying_Nc)));
                pojo.setPatient_voice_complaint(cursor.getString(cursor.getColumnIndex(Key_patient_voice_complaint)));
                pojo.setPatient_voice_complaint_remark(cursor.getString(cursor.getColumnIndex(Key_patient_voice_complaint_Remark)));
                pojo.setPatient_voice_complaint_nc(cursor.getString(cursor.getColumnIndex(Key_patient_voice_complaint_Nc)));
                pojo.setPatients_cost_treatment(cursor.getString(cursor.getColumnIndex(Key_patients_cost_treatment)));
                pojo.setPatients_cost_treatment_remark(cursor.getString(cursor.getColumnIndex(Key_patients_cost_treatment_Remark)));
                pojo.setPatients_cost_treatment_nc(cursor.getString(cursor.getColumnIndex(Key_patients_cost_treatment_Nc)));
                pojo.setPatient_clinical_records(cursor.getString(cursor.getColumnIndex(Key_patient_clinical_records)));
                pojo.setPatient_clinical_records_remark(cursor.getString(cursor.getColumnIndex(Key_patient_clinical_records_Remark)));
                pojo.setPatient_clinical_records_nc(cursor.getString(cursor.getColumnIndex(Key_patient_clinical_records_Nc)));
                pojo.setPatient_aware_plan_care(cursor.getString(cursor.getColumnIndex(Key_patient_aware_plan_care)));
                pojo.setPatient_aware_plan_care_remark(cursor.getString(cursor.getColumnIndex(Key_patient_aware_plan_care_Remark)));
                pojo.setPatient_aware_plan_care_nc(cursor.getString(cursor.getColumnIndex(Key_patient_aware_plan_care_Nc)));
                pojo.setPatient_aware_plan_care_video(cursor.getString(cursor.getColumnIndex(Key_patient_aware_plan_care_Video)));
                pojo.setLocal_patient_aware_plan_care_video(cursor.getString(cursor.getColumnIndex(Local_Key_patient_aware_plan_care_Video)));



            }
        }
        cursor.close();
        return pojo;
    }



    public OTPojo getOtPojo(String Hospital_id){
        OTPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_OT + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new OTPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setAnaesthetist_status(cursor.getString(cursor.getColumnIndex(KEY_Anaesthetist_status)));
                pojo.setRemark_OT_anaesthetist(cursor.getString(cursor.getColumnIndex(KEY_Remark_OT_anaesthetist)));
                pojo.setNc_OT_anaesthetist(cursor.getString(cursor.getColumnIndex(KEY_Nc_OT_anaesthetist)));
                pojo.setDocumented_anaesthesia_status(cursor.getString(cursor.getColumnIndex(KEY_Documented_anaesthesia_status)));
                pojo.setRemark_documented_anaesthesia(cursor.getString(cursor.getColumnIndex(KEY_Remark_documented_anaesthesia)));
                pojo.setNc_documented_anaesthesia(cursor.getString(cursor.getColumnIndex(KEY_Nc_documented_anaesthesia)));
                pojo.setImmediate_preoperative_status(cursor.getString(cursor.getColumnIndex(KEY_Immediate_preoperative_status)));
                pojo.setRemark_immediate_preoperative(cursor.getString(cursor.getColumnIndex(KEY_Remark_immediate_preoperative)));
                pojo.setNc_immediate_preoperative(cursor.getString(cursor.getColumnIndex(KEY_Nc_immediate_preoperative)));
                pojo.setAnaesthesia_monitoring_status(cursor.getString(cursor.getColumnIndex(KEY_Anaesthesia_monitoring_status)));
                pojo.setRemark_anaesthesia_monitoring(cursor.getString(cursor.getColumnIndex(KEY_Remark_anaesthesia_monitoring )));
                pojo.setNc_anaesthesia_monitoring(cursor.getString(cursor.getColumnIndex(KEY_Nc_anaesthesia_monitoring)));
                pojo.setPost_anaesthesia_monitoring_status(cursor.getString(cursor.getColumnIndex(KEY_Post_anaesthesia_monitoring_status)));
                pojo.setRemark_post_anaesthesia_monitoring(cursor.getString(cursor.getColumnIndex(KEY_Remark_post_anaesthesia_monitoring)));
                pojo.setNc_post_anaesthesia_monitoring(cursor.getString(cursor.getColumnIndex(KEY_Nc_post_anaesthesia_monitoring)));
                pojo.setWHO_Patient_Safety_status(cursor.getString(cursor.getColumnIndex(KEY_WHO_Patient_Safety_status)));
                pojo.setRemark_WHO_Patient_Safety(cursor.getString(cursor.getColumnIndex(KEY_Remark_WHO_Patient_Safety)));
                pojo.setNc_WHO_Patient_Safety(cursor.getString(cursor.getColumnIndex(KEY_Nc_WHO_Patient_Safety)));
                pojo.setImage_WHO_Patient_Safety_day1(cursor.getString(cursor.getColumnIndex(KEY_Image_WHO_Patient_Safety_day1)));
                pojo.setLocal_image_WHO_Patient_Safety_day1(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_WHO_Patient_Safety_day1)));
                pojo.setImage_WHO_Patient_Safety_day2(cursor.getString(cursor.getColumnIndex(KEY_Image_WHO_Patient_Safety_day2)));
                pojo.setLocal_image_WHO_Patient_Safety_day2(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_WHO_Patient_Safety_day2)));
                pojo.setImage_WHO_Patient_Safety_day3(cursor.getString(cursor.getColumnIndex(KEY_Image_WHO_Patient_Safety_day3)));
                pojo.setLocal_image_WHO_Patient_Safety_day3(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_WHO_Patient_Safety_day3)));
                pojo.setOT_Zoning_status(cursor.getString(cursor.getColumnIndex(KEY_OT_Zoning_status)));
                pojo.setRemark_OT_Zoning(cursor.getString(cursor.getColumnIndex(KEY_Remark_OT_Zoning)));
                pojo.setNc_OT_Zoning(cursor.getString(cursor.getColumnIndex(KEY_Nc_OT_Zoning)));
                pojo.setVideo_OT_Zoning(cursor.getString(cursor.getColumnIndex(KEY_Video_OT_Zoning)));
                pojo.setLocal_video_OT_Zoning(cursor.getString(cursor.getColumnIndex(KEY_Local_video_OT_Zoning)));
                pojo.setInfection_control_status(cursor.getString(cursor.getColumnIndex(KEY_Infection_control_status)));
                pojo.setRemark_infection_control(cursor.getString(cursor.getColumnIndex(KEY_Remark_infection_control)));
                pojo.setNc_infection_control(cursor.getString(cursor.getColumnIndex(KEY_Nc_infection_control)));
                pojo.setImage_infection_control(cursor.getString(cursor.getColumnIndex(KEY_Image_infection_control)));
                pojo.setLocal_image_infection_control(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_infection_control)));
                pojo.setNarcotic_drugs_status(cursor.getString(cursor.getColumnIndex(KEY_Narcotic_drugs_status)));
                pojo.setRemark_narcotic_drugs(cursor.getString(cursor.getColumnIndex(KEY_Remark_narcotic_drugs)));
                pojo.setNc_narcotic_drugs(cursor.getString(cursor.getColumnIndex(KEY_Nc_narcotic_drugs)));
                pojo.setImage_narcotic_drugs(cursor.getString(cursor.getColumnIndex(KEY_Image_narcotic_drugs)));
                pojo.setLocal_image_narcotic_drugs(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_narcotic_drugs)));
                pojo.setAdministration_disposal_status(cursor.getString(cursor.getColumnIndex(KEY_Administration_disposal_status)));
                pojo.setRemark_administration_disposal(cursor.getString(cursor.getColumnIndex(KEY_Remark_administration_disposal)));
                pojo.setNc_administration_disposal(cursor.getString(cursor.getColumnIndex(KEY_Nc_administration_disposal)));
                pojo.setImage_administration_disposal(cursor.getString(cursor.getColumnIndex(KEY_Image_administration_disposal)));
                pojo.setLocal_image_administration_disposal(cursor.getString(cursor.getColumnIndex(Local_KEY_Image_administration_disposal)));
                pojo.setHand_wash_facility_status(cursor.getString(cursor.getColumnIndex(Key_hand_wash_facility_status)));
                pojo.setRemark_hand_wash_facility(cursor.getString(cursor.getColumnIndex(Key_remark_hand_wash_facility)));
                pojo.setNc_hand_wash_facility(cursor.getString(cursor.getColumnIndex(Key_nc_hand_wash_facility)));
                pojo.setImage_hand_wash_facility(cursor.getString(cursor.getColumnIndex(Key_image_hand_wash_facility)));
                pojo.setLocal_image_hand_wash_facility(cursor.getString(cursor.getColumnIndex(Key_local_image_hand_wash_facility)));

            }
        }
        cursor.close();
        return pojo;
    }

    public OBSTETRIC_WARD_Pojo getObstetricPojo(String Hospital_id){
        OBSTETRIC_WARD_Pojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_OBSTETRIC_WARD + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                pojo = new OBSTETRIC_WARD_Pojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setOBSTETRIC_WARD_abuse(cursor.getString(cursor.getColumnIndex(OBSTETRIC_WARD_abuse)));
                pojo.setOBSTETRIC_WARD_abuse_remark(cursor.getString(cursor.getColumnIndex(OBSTETRIC_WARD_abuse_remark)));
                pojo.setOBSTETRIC_WARD_abuse_NC(cursor.getString(cursor.getColumnIndex(OBSTETRIC_WARD_abuse_NC)));
                pojo.setOBSTETRIC_WARD_abuse_image_identification(cursor.getString(cursor.getColumnIndex(OBSTETRIC_WARD_abuse_image_identification)));
                pojo.setLocal_OBSTETRIC_WARD_abuse_image_identification(cursor.getString(cursor.getColumnIndex(Local_OBSTETRIC_WARD_abuse_image_identification)));
                pojo.setOBSTETRIC_WARD_abuse_image_surveillance(cursor.getString(cursor.getColumnIndex(OBSTETRIC_WARD_abuse_image_surveillance)));
                pojo.setLocal_OBSTETRIC_WARD_abuse_image_surveillance(cursor.getString(cursor.getColumnIndex(Local_OBSTETRIC_WARD_abuse_image_surveillance)));
                pojo.setShco_obstetrics_ward(cursor.getString(cursor.getColumnIndex(shco_obstetrics_ward)));
                pojo.setShco_obstetrics_ward_remark(cursor.getString(cursor.getColumnIndex(shco_obstetrics_ward_remark)));
                pojo.setShco_obstetrics_ward_nc(cursor.getString(cursor.getColumnIndex(shco_obstetrics_ward_nc)));
                pojo.setShco_obstetrics_ward_image(cursor.getString(cursor.getColumnIndex(shco_obstetrics_ward_image)));
                pojo.setLocal_shco_obstetrics_ward_image(cursor.getString(cursor.getColumnIndex(Local_shco_obstetrics_ward_image)));
            }
        }
        cursor.close();
        return pojo;

    }
    public HighDependencyPojo getHighDependency(String Hospital_id){
        HighDependencyPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_HIGH_DEPENDENCY + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new HighDependencyPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setHIGH_DEPENDENCY_documented_procedure(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_documented_procedure)));
                pojo.setHIGH_DEPENDENCY_documented_procedure_remark(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_documented_procedure_remark)));
                pojo.setHIGH_DEPENDENCY_documented_procedure_NC(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_documented_procedure_NC)));
                pojo.setHIGH_DEPENDENCY_documented_procedure_video(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_documented_procedure_video)));
                pojo.setLocal_HIGH_DEPENDENCY_documented_procedure_video(cursor.getString(cursor.getColumnIndex(Local_HIGH_DEPENDENCY_documented_procedure_video)));

                pojo.setHIGH_DEPENDENCY_Areas_adequate(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_Areas_adequate)));
                pojo.setHIGH_DEPENDENCY_Areas_adequate_remark(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_Areas_adequate_remark)));
                pojo.setHIGH_DEPENDENCY_Areas_adequate_NC(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_Areas_adequate_NC)));
                pojo.setHIGH_DEPENDENCY_Areas_adequate_image(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_Areas_adequate_image)));
                pojo.setLocal_HIGH_DEPENDENCY_Areas_adequate_image(cursor.getString(cursor.getColumnIndex(Local_HIGH_DEPENDENCY_Areas_adequate_image)));

                pojo.setHIGH_DEPENDENCY_adequate_equipment(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_adequate_equipment)));
                pojo.setHIGH_DEPENDENCY_adequate_equipment_remark(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_adequate_equipment_remark)));
                pojo.setHIGH_DEPENDENCY_adequate_equipment_Nc(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_adequate_equipment_Nc)));
                pojo.setHIGH_DEPENDENCY_adequate_equipment_image(cursor.getString(cursor.getColumnIndex(HIGH_DEPENDENCY_adequate_equipment_image)));
                pojo.setLocal_HIGH_DEPENDENCY_adequate_equipment_image(cursor.getString(cursor.getColumnIndex(Local_HIGH_DEPENDENCY_adequate_equipment_image)));
            }
        }
        cursor.close();
        return pojo;
    }


    public EmergencyPojo getEmergencyPojo(String Hospital_id){
        EmergencyPojo pojo = null;
        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_EMERGENCY + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
             cursor.moveToFirst();

             pojo = new EmergencyPojo();
             pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
             pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
             pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
             pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
             pojo.setEMERGENCY_down_Norms(cursor.getString(cursor.getColumnIndex(EMERGENCY_down_Norms)));
             pojo.setEMERGENCY_down_Norms_remark(cursor.getString(cursor.getColumnIndex(EMERGENCY_down_Norms_remark)));
             pojo.setEMERGENCY_down_Norms_NC(cursor.getString(cursor.getColumnIndex(EMERGENCY_down_Norms_NC)));
             pojo.setEMERGENCY_down_Norms_video(cursor.getString(cursor.getColumnIndex(EMERGENCY_down_Norms_video)));
             pojo.setLocal_EMERGENCY_down_Norms_video(cursor.getString(cursor.getColumnIndex(Local_EMERGENCY_down_Norms_video)));
            }
        }
        cursor.close();
        return pojo;
    }

    public Wards_PharmacyPojo getWardPharmcyPojo(String Hospital_id){
        Wards_PharmacyPojo pojo = null;

        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_Ward_Pharmacy + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);

        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new Wards_PharmacyPojo();

                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getLong(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setPatient_care_area(cursor.getString(cursor.getColumnIndex(Key_patient_care_area)));
                pojo.setPatient_care_area_Remark(cursor.getString(cursor.getColumnIndex(Key_patient_care_area_Remark)));
                pojo.setPatient_care_area_NC(cursor.getString(cursor.getColumnIndex(Key_patient_care_area_NC)));
                pojo.setPatient_care_area_Image(cursor.getString(cursor.getColumnIndex(Key_patient_care_area_Image)));
                pojo.setLocal_patient_care_area_Image(cursor.getString(cursor.getColumnIndex(Local_Key_patient_care_area_Image)));
                pojo.setPharmacyStores_present(cursor.getString(cursor.getColumnIndex(Key_pharmacyStores_present)));
                pojo.setPharmacyStores_present_Remark(cursor.getString(cursor.getColumnIndex(Key_pharmacyStores_present_Remark)));
                pojo.setPharmacyStores_present_NC(cursor.getString(cursor.getColumnIndex(Key_pharmacyStores_present_Nc)));
                pojo.setPharmacyStores_present_Image(cursor.getString(cursor.getColumnIndex(Key_pharmacyStores_present_Image)));
                pojo.setLocal_pharmacyStores_present_Image(cursor.getString(cursor.getColumnIndex(Local_Key_pharmacyStores_present_Image)));
                pojo.setExpired_drugs(cursor.getString(cursor.getColumnIndex(Key_expired_drugs)));
                pojo.setExpired_drugs_remark(cursor.getString(cursor.getColumnIndex(Key_expired_drugs_Remark)));
                pojo.setExpired_drugs_nc(cursor.getString(cursor.getColumnIndex(Key_expired_drugs_NC)));
                pojo.setExpired_drugs_image(cursor.getString(cursor.getColumnIndex(Key_expired_drugs_image)));
                pojo.setLocal_expired_drugs_image(cursor.getString(cursor.getColumnIndex(Local_Key_expired_drugs_image)));
                pojo.setExpiry_date_checked(cursor.getString(cursor.getColumnIndex(Key_expiry_date_checked)));
                pojo.setExpiry_date_checked_Remark(cursor.getString(cursor.getColumnIndex(Key_expiry_date_checked_Remark)));
                pojo.setExpiry_date_checked_NC(cursor.getString(cursor.getColumnIndex(Key_expiry_date_checked_NC)));
                pojo.setEmergency_medications(cursor.getString(cursor.getColumnIndex(Key_emergency_medications)));
                pojo.setEmergency_medications_Remark(cursor.getString(cursor.getColumnIndex(Key_emergency_medications_Remark)));
                pojo.setEmergency_medications_Nc(cursor.getString(cursor.getColumnIndex(Key_emergency_medications_Nc)));
                pojo.setEmergency_medications_Image(cursor.getString(cursor.getColumnIndex(Key_emergency_medications_Image)));
                pojo.setLocal_emergency_medications_Image(cursor.getString(cursor.getColumnIndex(Local_Key_emergency_medications_Image)));
                pojo.setRisk_medications(cursor.getString(cursor.getColumnIndex(Key_risk_medications)));
                pojo.setRisk_medications_remark(cursor.getString(cursor.getColumnIndex(Key_risk_medications_Remark)));
                pojo.setRisk_medications_nc(cursor.getString(cursor.getColumnIndex(Key_risk_medications_Nc)));
                pojo.setRisk_medications_image(cursor.getString(cursor.getColumnIndex(Key_risk_medications_Image)));
                pojo.setLocal_risk_medications_image(cursor.getString(cursor.getColumnIndex(Local_Key_risk_medications_Image)));
                pojo.setMedications_dispensing(cursor.getString(cursor.getColumnIndex(Key_medications_dispensing)));
                pojo.setMedications_dispensing_remark(cursor.getString(cursor.getColumnIndex(Key_medications_dispensing_Remark)));
                pojo.setMedications_dispensing_nc(cursor.getString(cursor.getColumnIndex(Key_medications_dispensing_Nc)));
                pojo.setLabelling_of_drug(cursor.getString(cursor.getColumnIndex(Key_labelling_of_drug)));
                pojo.setLabelling_of_drug_remark(cursor.getString(cursor.getColumnIndex(Key_labelling_of_drug_Remark)));
                pojo.setLabelling_of_drug_nc(cursor.getString(cursor.getColumnIndex(Key_labelling_of_drug_NC)));
                pojo.setLabelling_of_drug_image(cursor.getString(cursor.getColumnIndex(Key_labelling_of_drug_Image)));
                pojo.setLocal_labelling_of_drug_image(cursor.getString(cursor.getColumnIndex(Local_Key_labelling_of_drug_Image)));
                pojo.setMedication_order_checked(cursor.getString(cursor.getColumnIndex(Key_medication_order_checked)));
                pojo.setMedication_order_checked_remark(cursor.getString(cursor.getColumnIndex(Key_medication_order_checked_Remark)));
                pojo.setMedication_order_checked_nc(cursor.getString(cursor.getColumnIndex(Key_medication_order_checked_Nc)));
                pojo.setMedication_order_checked_image(cursor.getString(cursor.getColumnIndex(Key_medication_order_checked_Image)));
                pojo.setLocal_medication_order_checked_image(cursor.getString(cursor.getColumnIndex(Local_Key_medication_order_checked_Image)));
                pojo.setMedication_administration_documented(cursor.getString(cursor.getColumnIndex(Key_medication_administration_documented)));
                pojo.setMedication_administration_documented_remark(cursor.getString(cursor.getColumnIndex(Key_medication_administration_documented_Remark)));
                pojo.setMedication_administration_documented_nc(cursor.getString(cursor.getColumnIndex(Key_medication_administration_documented_Nc)));
                pojo.setMedication_administration_documented_image(cursor.getString(cursor.getColumnIndex(Key_medication_administration_documented_Image)));
                pojo.setLocal_medication_administration_documented_image(cursor.getString(cursor.getColumnIndex(Local_Key_medication_administration_documented_Image)));
                pojo.setFridge_temperature_record(cursor.getString(cursor.getColumnIndex(Key_fridge_temperature_record)));
                pojo.setFridge_temperature_record_Remark(cursor.getString(cursor.getColumnIndex(Key_fridge_temperature_record_Remark)));
                pojo.setFridge_temperature_record_Nc(cursor.getString(cursor.getColumnIndex(Key_fridge_temperature_record_Nc)));
                pojo.setFridge_temperature_record_Image(cursor.getString(cursor.getColumnIndex(Key_fridge_temperature_record_Image)));
                pojo.setLocal_fridge_temperature_record_Image(cursor.getString(cursor.getColumnIndex(Local_Key_fridge_temperature_record_Image)));
            }
        }

        cursor.close();
        return pojo;

    }
    public RadioLogyPojo getRadioLogyPojo(String Hospital_id){
        RadioLogyPojo pojo = null;
        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_RADIOLOGY + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);

        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                pojo = new RadioLogyPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setRADIOLOGY_Appropriate_safety_equipment(cursor.getString(cursor.getColumnIndex(RADIOLOGY_Appropriate_safety_equipment)));
                pojo.setRADIOLOGY_Appropriate_safety_equipment_remark(cursor.getString(cursor.getColumnIndex(RADIOLOGY_Appropriate_safety_equipment_remark)));
                pojo.setRADIOLOGY_Appropriate_safety_equipment_NC(cursor.getString(cursor.getColumnIndex(RADIOLOGY_Appropriate_safety_equipment_NC)));
                pojo.setRADIOLOGY_Appropriate_safety_equipment_image(cursor.getString(cursor.getColumnIndex(RADIOLOGY_Appropriate_safety_equipment_image)));
                pojo.setLocal_RADIOLOGY_Appropriate_safety_equipment_image(cursor.getString(cursor.getColumnIndex(Local_RADIOLOGY_Appropriate_safety_equipment_image)));
                pojo.setRADIOLOGY_defined_turnaround(cursor.getString(cursor.getColumnIndex(RADIOLOGY_defined_turnaround)));
                pojo.setRADIOLOGY_defined_turnaround_remark(cursor.getString(cursor.getColumnIndex(RADIOLOGY_defined_turnaround_Remark)));
                pojo.setRADIOLOGY_defined_turnaround_nc(cursor.getString(cursor.getColumnIndex(RADIOLOGY_defined_turnaround_Nc)));
                pojo.setRADIOLOGY_defined_turnaround_image(cursor.getString(cursor.getColumnIndex(RADIOLOGY_defined_turnaround_Image)));
                pojo.setLocal_RADIOLOGY_defined_turnaround_image(cursor.getString(cursor.getColumnIndex(Local_RADIOLOGY_defined_turnaround_Image)));
            }
        }
        cursor.close();
        return pojo;
    }

    public ScopeOfServicePojo getScopeService(String Hospital_id){
        ScopeOfServicePojo pojo = null;
        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + Table_scope_service + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                pojo = new ScopeOfServicePojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setClinical_anaesthesiology(cursor.getString(cursor.getColumnIndex(Key_clinical_anaesthesiology)));
                pojo.setClinical_dermatology_venereology(cursor.getString(cursor.getColumnIndex(Key_clinical_dermatology_venereology)));
                pojo.setClinical_emergency_medicine(cursor.getString(cursor.getColumnIndex(Key_clinical_emergency_medicine)));
                pojo.setClinical_family_medicine(cursor.getString(cursor.getColumnIndex(Key_clinical_family_medicine)));
                pojo.setClinical_general_medicine(cursor.getString(cursor.getColumnIndex(Key_clinical_general_medicine)));
                pojo.setClinical_general_surgery(cursor.getString(cursor.getColumnIndex(Key_clinical_general_surgery)));
                pojo.setClinical_obstetrics_gynecology(cursor.getString(cursor.getColumnIndex(Key_clinical_obstetrics_gynecology)));
                pojo.setClinical_ophthalmology(cursor.getString(cursor.getColumnIndex(Key_clinical_ophthalmology)));
                pojo.setClinical_orthopaedic_surgery(cursor.getString(cursor.getColumnIndex(Key_clinical_orthopaedic_surgery)));
                pojo.setClinical_otorhinolaryngology(cursor.getString(cursor.getColumnIndex(Key_clinical_otorhinolaryngology)));
                pojo.setClinical_paediatrics(cursor.getString(cursor.getColumnIndex(Key_clinical_paediatrics)));
                pojo.setClinical_Psychiatry(cursor.getString(cursor.getColumnIndex(Key_clinical_Psychiatry)));
                pojo.setClinical_respiratory_medicine(cursor.getString(cursor.getColumnIndex(Key_clinical_respiratory_medicine)));
                pojo.setClinical_day_care_services(cursor.getString(cursor.getColumnIndex(Key_clinical_day_care_services)));
                pojo.setClinical_cardiac_anaesthesia(cursor.getString(cursor.getColumnIndex(Key_clinical_cardiac_anaesthesia)));
                pojo.setClinical_cardiology(cursor.getString(cursor.getColumnIndex(Key_clinical_cardiology)));
                pojo.setClinical_cardiothoracic_surgery(cursor.getString(cursor.getColumnIndex(Key_clinical_cardiothoracic_surgery)));
                pojo.setClinical_clinical_haematology(cursor.getString(cursor.getColumnIndex(Key_clinical_clinical_haematology)));
                pojo.setClinical_critical_care(cursor.getString(cursor.getColumnIndex(Key_clinical_critical_care)));
                pojo.setClinical_endocrinology(cursor.getString(cursor.getColumnIndex(Key_clinical_endocrinology)));
                pojo.setClinical_hepatology(cursor.getString(cursor.getColumnIndex(Key_clinical_hepatology)));
                pojo.setClinical_immunology(cursor.getString(cursor.getColumnIndex(Key_clinical_immunology)));
                pojo.setClinical_medical_gastroenterology(cursor.getString(cursor.getColumnIndex(Key_clinical_medical_gastroenterology)));
                pojo.setClinical_Neurology(cursor.getString(cursor.getColumnIndex(Key_clinical_neonatology)));
                pojo.setClinical_nephrology(cursor.getString(cursor.getColumnIndex(Key_clinical_nephrology)));
                pojo.setClinical_Neurosurgery(cursor.getString(cursor.getColumnIndex(Key_clinical_Neurosurgery)));
                pojo.setClinical_Oncology(cursor.getString(cursor.getColumnIndex(Key_clinical_Oncology)));
                pojo.setClinical_paediatric_cardiology(cursor.getString(cursor.getColumnIndex(Key_clinical_paediatric_cardiology)));
                pojo.setClinical_paediatric_surgery(cursor.getString(cursor.getColumnIndex(Key_clinical_paediatric_surgery)));
                pojo.setClinical_plastic_reconstructive(cursor.getString(cursor.getColumnIndex(Key_clinical_plastic_reconstructive)));
                pojo.setClinical_surgical_gastroenterology(cursor.getString(cursor.getColumnIndex(Key_clinical_surgical_gastroenterology)));
                pojo.setClinical_urology(cursor.getString(cursor.getColumnIndex(Key_clinical_urology)));
                pojo.setClinical_transplantation_service(cursor.getString(cursor.getColumnIndex(Key_clinical_transplantation_service)));
                pojo.setDiagnostic_ct_scanning(cursor.getString(cursor.getColumnIndex(Key_diagnostic_ct_scanning)));
                pojo.setDiagnostic_mammography(cursor.getString(cursor.getColumnIndex(Key_diagnostic_mammography)));
                pojo.setDiagnostic_mri(cursor.getString(cursor.getColumnIndex(Key_diagnostic_mri)));
                pojo.setDiagnostic_ultrasound(cursor.getString(cursor.getColumnIndex(Key_diagnostic_ultrasound)));
                pojo.setDiagnostic_x_ray(cursor.getString(cursor.getColumnIndex(Key_diagnostic_x_ray)));
                pojo.setDiagnostic_2d_echo(cursor.getString(cursor.getColumnIndex(Key_diagnostic_2d_echo)));
                pojo.setDiagnostic_eeg(cursor.getString(cursor.getColumnIndex(Key_diagnostic_eeg)));
                pojo.setDiagnostic_holter_monitoring(cursor.getString(cursor.getColumnIndex(Key_diagnostic_holter_monitoring)));
                pojo.setDiagnostic_spirometry(cursor.getString(cursor.getColumnIndex(Key_diagnostic_spirometry)));
                pojo.setDiagnostic_tread_mill_testing(cursor.getString(cursor.getColumnIndex(Key_diagnostic_tread_mill_testing)));
                pojo.setDiagnostic_urodynamic_studies(cursor.getString(cursor.getColumnIndex(Key_diagnostic_urodynamic_studies)));
                pojo.setDiagnostic_bone_densitometry(cursor.getString(cursor.getColumnIndex(Key_diagnostic_bone_densitometry)));
                pojo.setLaboratory_clinical_bio_chemistry(cursor.getString(cursor.getColumnIndex(Key_laboratory_clinical_bio_chemistry)));
                pojo.setLaboratory_clinical_microbiology(cursor.getString(cursor.getColumnIndex(Key_laboratory_clinical_microbiology)));
                pojo.setLaboratory_clinical_pathology(cursor.getString(cursor.getColumnIndex(Key_laboratory_clinical_pathology)));
                pojo.setLaboratory_cytopathology(cursor.getString(cursor.getColumnIndex(Key_laboratory_cytopathology)));
                pojo.setLaboratory_haematology(cursor.getString(cursor.getColumnIndex(Key_laboratory_haematology)));
                pojo.setLaboratory_histopathology(cursor.getString(cursor.getColumnIndex(Key_laboratory_histopathology)));
                pojo.setPharmacy_dispensary(cursor.getString(cursor.getColumnIndex(Key_pharmacy_dispensary)));
                pojo.setTransfusions_blood_transfusions(cursor.getString(cursor.getColumnIndex(Key_transfusions_blood_transfusions)));
                pojo.setTransfusions_blood_bank(cursor.getString(cursor.getColumnIndex(Key_transfusions_blood_bank)));
                pojo.setProfessions_allied_ambulance(cursor.getString(cursor.getColumnIndex(Key_professions_allied_ambulance)));
                pojo.setProfessions_dietetics(cursor.getString(cursor.getColumnIndex(Key_professions_dietetics)));
                pojo.setProfessions_physiotherapy(cursor.getString(cursor.getColumnIndex(Key_professions_physiotherapy)));
                pojo.setProfessions_occupational_therapy(cursor.getString(cursor.getColumnIndex(Key_professions_occupational_therapy)));
                pojo.setProfessions_speech_language(cursor.getString(cursor.getColumnIndex(Key_professions_speech_language)));
                pojo.setProfessions_psychology(cursor.getString(cursor.getColumnIndex(Key_professions_psychology)));


            }
        }
        cursor.close();
        return pojo;
    }




    public LaboratoryPojo getLaboratoryPojo(String Hospital_id){
        LaboratoryPojo pojo = null;
        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_LABORATORY + " where " + KEY_HOSPITAL_ID + " ='" + Hospital_id + "'", null);

        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                pojo = new LaboratoryPojo();
                pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                pojo.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                pojo.setHospital_name(cursor.getString(cursor.getColumnIndex(KEY_HOSPITAL_NAME)));
                pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                pojo.setLABORATORY_collected_properly(cursor.getString(cursor.getColumnIndex(LABORATORY_collected_properly)));
                pojo.setCollected_properly_remark(cursor.getString(cursor.getColumnIndex(Collected_properly_remark)));
                pojo.setCollected_properly_NC(cursor.getString(cursor.getColumnIndex(Collected_properly_NC)));
                pojo.setCollected_properly_video(cursor.getString(cursor.getColumnIndex(Collected_properly_video)));
                pojo.setLocal_Collected_properly_video(cursor.getString(cursor.getColumnIndex(Local_Collected_properly_video)));
                pojo.setLABORATORY_identified_properly(cursor.getString(cursor.getColumnIndex(LABORATORY_identified_properly)));
                pojo.setIdentified_properly_remark(cursor.getString(cursor.getColumnIndex(Identified_properly_remark)));
                pojo.setIdentified_properly_NC(cursor.getString(cursor.getColumnIndex(Identified_properly_NC)));
                pojo.setIdentified_properly_image(cursor.getString(cursor.getColumnIndex(Identified_properly_image)));
                pojo.setLocal_Identified_properly_image(cursor.getString(cursor.getColumnIndex(Local_Identified_properly_image)));
                pojo.setLABORATORY_transported_safe_manner(cursor.getString(cursor.getColumnIndex(LABORATORY_transported_safe_manner)));
                pojo.setTransported_safe_manner_remark(cursor.getString(cursor.getColumnIndex(Transported_safe_manner_remark)));
                pojo.setTransported_safe_manner_NC(cursor.getString(cursor.getColumnIndex(Transported_safe_manner_NC)));
                pojo.setTransported_safe_manner_image(cursor.getString(cursor.getColumnIndex(Transported_safe_manner_image)));
                pojo.setLocal_Transported_safe_manner_image(cursor.getString(cursor.getColumnIndex(Local_Transported_safe_manner_image)));
                pojo.setLABORATORY_Specimen_safe_manner(cursor.getString(cursor.getColumnIndex(LABORATORY_Specimen_safe_manner)));
                pojo.setSpecimen_safe_manner_remark(cursor.getString(cursor.getColumnIndex(Specimen_safe_manner_remark)));
                pojo.setSpecimen_safe_manner_nc(cursor.getString(cursor.getColumnIndex(Specimen_safe_manner_nc)));
                pojo.setSpecimen_safe_image(cursor.getString(cursor.getColumnIndex(Specimen_safe_image)));
                pojo.setLocal_Specimen_safe_image(cursor.getString(cursor.getColumnIndex(Local_Specimen_safe_image)));
                pojo.setLABORATORY_Appropriate_safety_equipment(cursor.getString(cursor.getColumnIndex(LABORATORY_Appropriate_safety_equipment)));
                pojo.setAppropriate_safety_equipment_remark(cursor.getString(cursor.getColumnIndex(Appropriate_safety_equipment_remark)));
                pojo.setAppropriate_safety_equipment_NC(cursor.getString(cursor.getColumnIndex(Appropriate_safety_equipment_NC)));
                pojo.setAppropriate_safety_equipment_image(cursor.getString(cursor.getColumnIndex(Appropriate_safety_equipment_image)));
                pojo.setAppropriate_safety_equipment_image(cursor.getString(cursor.getColumnIndex(Appropriate_safety_equipment_image)));
                pojo.setLocal_Appropriate_safety_equipment_image(cursor.getString(cursor.getColumnIndex(Local_Appropriate_safety_equipment_image)));
                pojo.setLaboratory_defined_turnaround(cursor.getString(cursor.getColumnIndex(Laboratory_defined_turnaround)));
                pojo.setLaboratory_defined_turnaround_Remark(cursor.getString(cursor.getColumnIndex(Laboratory_defined_turnaround_Remark)));
                pojo.setLaboratory_defined_turnaround_Nc(cursor.getString(cursor.getColumnIndex(Laboratory_defined_turnaround_Nc)));
                pojo.setLaboratory_defined_turnaround_image(cursor.getString(cursor.getColumnIndex(Laboratory_defined_turnaround_Image)));
                pojo.setLocal_laboratory_defined_turnaround_image(cursor.getString(cursor.getColumnIndex(Local_Laboratory_defined_turnaround_Image)));


            }
        }
        cursor.close();
        return pojo;
    }

    public ArrayList<AssessmentStatusPojo> getAssessmentList(String hospital_id){
        SQLiteDatabase sqliteDB = this.getReadableDatabase();

        ArrayList<AssessmentStatusPojo> list = new ArrayList<>();

        Cursor cursor = sqliteDB.rawQuery("SELECT * FROM " + TABLE_ASSESSEMENT_STATUS + " where " + KEY_HOSPITAL_ID + " ='" + hospital_id + "'", null);
        try {
            if (cursor.moveToFirst()){
                do {
                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();

                    pojo.setLocal_id(cursor.getLong(cursor.getColumnIndex(KEY_Local_id)));
                    pojo.setHospital_id(cursor.getInt(cursor.getColumnIndex(KEY_HOSPITAL_ID)));
                    pojo.setAssessement_name(cursor.getString(cursor.getColumnIndex(KEY_ASSESSEMENT_NAME)));
                    pojo.setAssessement_status(cursor.getString(cursor.getColumnIndex(KEY_ASSESSEMENT_STATUS)));

                    list.add(pojo);
                }while (cursor.moveToNext());
            }
            cursor.close();
            if (!cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public void removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_ASSESSEMENT_STATUS, null, null);
        db.delete(TABLE_LABORATORY, null, null);
        db.delete(TABLE_RADIOLOGY, null, null);
        db.delete(TABLE_EMERGENCY, null, null);
        db.delete(TABLE_HIGH_DEPENDENCY, null, null);
        db.delete(TABLE_OBSTETRIC_WARD, null, null);
        db.delete(TABLE_OT, null, null);
        db.delete(TABLE_Ward_Pharmacy, null, null);
        db.delete(TABLE_Patient_Staff_Interview, null, null);
        db.delete(Table_Ward_Ot_Emergency, null, null);
        db.delete(Table_HRM, null, null);
        db.delete(Table_MRD, null, null);
        db.delete(Table_Housekeeping, null, null);
        db.delete(Table_Sterilization_Area, null, null);
        db.delete(Table_Management,null,null);
        db.delete(Table_Bio_medical_engineering,null,null);
        db.delete(Table_Facility_Checks,null,null);
        db.delete(Table_Safety_Management,null,null);
        db.delete(Table_Ambulance_Accessibility,null,null);
        db.delete(Table_Uniform_Signage,null,null);
        db.delete(Table_Documentation,null,null);
        db.delete(Table_scope_service,null,null);
    }

}
