package com.qci.onsite.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.qci.onsite.R;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ScopeOfServicePojo;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScopeOfServiceActivity extends BaseActivity {

    @BindView(R.id.clinical_anaesthesiology)
    CheckBox clinical_anaesthesiology;

    @BindView(R.id.clinical_dermatology_venereology)
    CheckBox clinical_dermatology_venereology;

    @BindView(R.id.clinical_emergency_medicine)
    CheckBox clinical_emergency_medicine;

    @BindView(R.id.clinical_family_medicine)
    CheckBox clinical_family_medicine;

    @BindView(R.id.clinical_general_medicine)
    CheckBox clinical_general_medicine;

    @BindView(R.id.clinical_general_surgery)
    CheckBox clinical_general_surgery;

    @BindView(R.id.clinical_obstetrics_gynecology)
    CheckBox clinical_obstetrics_gynecology;

    @BindView(R.id.clinical_ophthalmology)
    CheckBox clinical_ophthalmology;

    @BindView(R.id.clinical_orthopaedic_surgery)
    CheckBox clinical_orthopaedic_surgery;

    @BindView(R.id.clinical_otorhinolaryngology)
    CheckBox clinical_otorhinolaryngology;

    @BindView(R.id.clinical_paediatrics)
    CheckBox clinical_paediatrics;

    @BindView(R.id.clinical_Psychiatry)
    CheckBox clinical_Psychiatry;

    @BindView(R.id.clinical_respiratory_medicine)
    CheckBox clinical_respiratory_medicine;

    @BindView(R.id.clinical_day_care_services)
    CheckBox clinical_day_care_services;


    @BindView(R.id.clinical_cardiac_anaesthesia)
    CheckBox clinical_cardiac_anaesthesia;

    @BindView(R.id.clinical_cardiology)
    CheckBox clinical_cardiology;

    @BindView(R.id.clinical_cardiothoracic_surgery)
    CheckBox clinical_cardiothoracic_surgery;

    @BindView(R.id.clinical_clinical_haematology)
    CheckBox clinical_clinical_haematology;

    @BindView(R.id.clinical_critical_care)
    CheckBox clinical_critical_care;

    @BindView(R.id.clinical_endocrinology)
    CheckBox clinical_endocrinology;

    @BindView(R.id.clinical_hepatology)
    CheckBox clinical_hepatology;

    @BindView(R.id.clinical_immunology)
    CheckBox clinical_immunology;

    @BindView(R.id.clinical_medical_gastroenterology)
    CheckBox clinical_medical_gastroenterology;

    @BindView(R.id.clinical_neonatology)
    CheckBox clinical_neonatology;

    @BindView(R.id.clinical_nephrology)
    CheckBox clinical_nephrology;

    @BindView(R.id.clinical_Neurology)
    CheckBox clinical_Neurology;

    @BindView(R.id.clinical_Neurosurgery)
    CheckBox clinical_Neurosurgery;

    @BindView(R.id.clinical_Oncology)
    CheckBox clinical_Oncology;

    @BindView(R.id.clinical_paediatric_cardiology)
    CheckBox clinical_paediatric_cardiology;

    @BindView(R.id.clinical_paediatric_surgery)
    CheckBox clinical_paediatric_surgery;

    @BindView(R.id.clinical_plastic_reconstructive)
    CheckBox clinical_plastic_reconstructive;

    @BindView(R.id.clinical_surgical_gastroenterology)
    CheckBox clinical_surgical_gastroenterology;

    @BindView(R.id.clinical_urology)
    CheckBox clinical_urology;

    @BindView(R.id.clinical_transplantation_service)
    CheckBox clinical_transplantation_service;

    @BindView(R.id.diagnostic_ct_scanning)
    CheckBox diagnostic_ct_scanning;

    @BindView(R.id.diagnostic_mammography)
    CheckBox diagnostic_mammography;

    @BindView(R.id.diagnostic_mri)
    CheckBox diagnostic_mri;

    @BindView(R.id.diagnostic_ultrasound)
    CheckBox diagnostic_ultrasound;

    @BindView(R.id.diagnostic_x_ray)
    CheckBox diagnostic_x_ray;

    @BindView(R.id.diagnostic_2d_echo)
    CheckBox diagnostic_2d_echo;

    @BindView(R.id.diagnostic_eeg)
    CheckBox diagnostic_eeg;

    @BindView(R.id.diagnostic_holter_monitoring)
    CheckBox diagnostic_holter_monitoring;

    @BindView(R.id.diagnostic_spirometry)
    CheckBox diagnostic_spirometry;

    @BindView(R.id.diagnostic_tread_mill_testing)
    CheckBox diagnostic_tread_mill_testing;

    @BindView(R.id.diagnostic_urodynamic_studies)
    CheckBox diagnostic_urodynamic_studies;

    @BindView(R.id.diagnostic_bone_densitometry)
    CheckBox diagnostic_bone_densitometry;

    @BindView(R.id.laboratory_clinical_bio_chemistry)
    CheckBox laboratory_clinical_bio_chemistry;

    @BindView(R.id.laboratory_clinical_microbiology)
    CheckBox laboratory_clinical_microbiology;

    @BindView(R.id.laboratory_clinical_pathology)
    CheckBox laboratory_clinical_pathology;

    @BindView(R.id.laboratory_cytopathology)
    CheckBox laboratory_cytopathology;

    @BindView(R.id.laboratory_haematology)
    CheckBox laboratory_haematology;

    @BindView(R.id.laboratory_histopathology)
    CheckBox laboratory_histopathology;

    @BindView(R.id.pharmacy_dispensary)
    CheckBox pharmacy_dispensary;

    @BindView(R.id.transfusions_blood_transfusions)
    CheckBox transfusions_blood_transfusions;

    @BindView(R.id.transfusions_blood_bank)
    CheckBox transfusions_blood_bank;

    @BindView(R.id.professions_allied_ambulance)
    CheckBox professions_allied_ambulance;

    @BindView(R.id.professions_dietetics)
    CheckBox professions_dietetics;

    @BindView(R.id.professions_physiotherapy)
    CheckBox professions_physiotherapy;

    @BindView(R.id.professions_occupational_therapy)
    CheckBox professions_occupational_therapy;

    @BindView(R.id.professions_speech_language)
    CheckBox professions_speech_language;

    @BindView(R.id.professions_psychology)
    CheckBox professions_psychology;

    @BindView(R.id.btnSave_scope)
    Button btnSave_scope;

    @BindView(R.id.btnSync)
    Button btnSync;

    public Boolean sql_status = false;

    private DatabaseHandler databaseHandler;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    private ScopeOfServicePojo pojo;

    private String st_clinical_anaesthesiology = "",st_clinical_dermatology_venereology = "",st_clinical_emergency_medicine = "",
    st_clinical_family_medicine = "",st_clinical_general_medicine = "",st_clinical_general_surgery = "",st_clinical_obstetrics_gynecology = "",
    st_clinical_ophthalmology = "",st_clinical_orthopaedic_surgery = "",st_clinical_otorhinolaryngology = "",st_clinical_paediatrics = "",
    st_clinical_Psychiatry = "",st_clinical_respiratory_medicine = "",st_clinical_day_care_services = "",st_clinical_cardiac_anaesthesia = "",
    st_clinical_cardiology = "",st_clinical_cardiothoracic_surgery = "",st_clinical_clinical_haematology = "",st_clinical_critical_care = "",
    st_clinical_endocrinology = "",st_clinical_hepatology = "",st_clinical_immunology = "",st_clinical_medical_gastroenterology = "",
    st_clinical_neonatology = "",st_clinical_nephrology = "",st_clinical_Neurology = "",st_clinical_Neurosurgery = "",st_clinical_Oncology = "",
    st_clinical_paediatric_cardiology = "",st_clinical_paediatric_surgery = "",st_clinical_plastic_reconstructive = "",st_clinical_surgical_gastroenterology = "",
    st_clinical_urology = "",st_clinical_transplantation_service = "",st_diagnostic_ct_scanning = "",st_diagnostic_mammography = "",
    st_diagnostic_mri = "",st_diagnostic_ultrasound = "",st_diagnostic_x_ray = "",st_diagnostic_2d_echo = "",st_diagnostic_eeg = "",
    st_diagnostic_holter_monitoring = "",st_diagnostic_spirometry = "",st_diagnostic_tread_mill_testing = "",st_diagnostic_urodynamic_studies = "",
    st_diagnostic_bone_densitometry = "",st_laboratory_clinical_bio_chemistry = "",st_laboratory_clinical_microbiology = "",
    st_laboratory_clinical_pathology = "",st_laboratory_cytopathology = "",st_laboratory_haematology = "",st_laboratory_histopathology = "",
    st_pharmacy_dispensary = "",st_transfusions_blood_transfusions = "",st_transfusions_blood_bank = "",st_professions_allied_ambulance = "",
    st_professions_dietetics = "",st_professions_physiotherapy = "",st_professions_occupational_therapy = "",st_professions_speech_language = "",
    st_professions_psychology = "";

    DataSyncRequest pojo_dataSync;


    private APIService mAPIService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope_service);

        ButterKnife.bind(this);

        setDrawerbackIcon("Scope of Service");

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        pojo = new ScopeOfServicePojo();

        pojo_dataSync = new DataSyncRequest();

        mAPIService = ApiUtils.getAPIService();


        databaseHandler = DatabaseHandler.getInstance(this);

        btnSave_scope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clinical_anaesthesiology.isChecked() || clinical_dermatology_venereology.isChecked()  || clinical_emergency_medicine.isChecked() ||
                        clinical_family_medicine.isChecked() || clinical_general_medicine.isChecked()  || clinical_general_surgery.isChecked() || clinical_obstetrics_gynecology.isChecked() ||
                        clinical_ophthalmology.isChecked() || clinical_orthopaedic_surgery.isChecked() || clinical_otorhinolaryngology.isChecked()  || clinical_paediatrics.isChecked() ||
                        clinical_Psychiatry.isChecked() || clinical_respiratory_medicine.isChecked()  || clinical_day_care_services.isChecked() || clinical_cardiac_anaesthesia.isChecked() ||
                        clinical_cardiology.isChecked() || clinical_cardiothoracic_surgery.isChecked() || clinical_clinical_haematology.isChecked() || clinical_critical_care.isChecked() ||
                        clinical_endocrinology.isChecked() || clinical_hepatology.isChecked() || clinical_immunology.isChecked() || clinical_medical_gastroenterology.isChecked() ||
                        clinical_neonatology.isChecked() || clinical_nephrology.isChecked() || clinical_Neurology.isChecked() ||clinical_Neurosurgery.isChecked() || clinical_Oncology.isChecked() ||
                        clinical_paediatric_cardiology.isChecked() || clinical_paediatric_surgery.isChecked() || clinical_plastic_reconstructive.isChecked()  || clinical_surgical_gastroenterology.isChecked() ||
                        clinical_urology.isChecked() || clinical_transplantation_service.isChecked() || diagnostic_ct_scanning.isChecked() || diagnostic_mammography.isChecked() ||
                        diagnostic_mri.isChecked()  || diagnostic_ultrasound.isChecked()  || diagnostic_x_ray.isChecked()  || diagnostic_2d_echo.isChecked() || diagnostic_eeg.isChecked() ||
                        diagnostic_holter_monitoring.isChecked()  || diagnostic_spirometry.isChecked() || diagnostic_tread_mill_testing.isChecked() || diagnostic_urodynamic_studies.isChecked() ||
                        diagnostic_bone_densitometry.isChecked()  || laboratory_clinical_bio_chemistry.isChecked() || laboratory_clinical_microbiology.isChecked() ||
                        laboratory_clinical_pathology.isChecked()   || laboratory_cytopathology.isChecked()  || laboratory_haematology.isChecked() || laboratory_histopathology.isChecked() ||
                        pharmacy_dispensary.isChecked()  || transfusions_blood_transfusions.isChecked()  || transfusions_blood_bank.isChecked()  || professions_allied_ambulance.isChecked() ||
                        professions_dietetics.isChecked() || professions_physiotherapy.isChecked()  || professions_occupational_therapy.isChecked()  || professions_speech_language.isChecked() ||
                        professions_psychology.isChecked()){
                    SaveScopeService("save");
                }else {
                    Toast.makeText(ScopeOfServiceActivity.this, "Please select at least one service", Toast.LENGTH_LONG).show();
                }
            }

        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clinical_anaesthesiology.isChecked() || clinical_dermatology_venereology.isChecked()  || clinical_emergency_medicine.isChecked() ||
                        clinical_family_medicine.isChecked() || clinical_general_medicine.isChecked()  || clinical_general_surgery.isChecked() || clinical_obstetrics_gynecology.isChecked() ||
                        clinical_ophthalmology.isChecked() || clinical_orthopaedic_surgery.isChecked() || clinical_otorhinolaryngology.isChecked()  || clinical_paediatrics.isChecked() ||
                        clinical_Psychiatry.isChecked() || clinical_respiratory_medicine.isChecked()  || clinical_day_care_services.isChecked() || clinical_cardiac_anaesthesia.isChecked() ||
                        clinical_cardiology.isChecked() || clinical_cardiothoracic_surgery.isChecked() || clinical_clinical_haematology.isChecked() || clinical_critical_care.isChecked() ||
                        clinical_endocrinology.isChecked() || clinical_hepatology.isChecked() || clinical_immunology.isChecked() || clinical_medical_gastroenterology.isChecked() ||
                        clinical_neonatology.isChecked() || clinical_nephrology.isChecked() || clinical_Neurology.isChecked() ||clinical_Neurosurgery.isChecked() || clinical_Oncology.isChecked() ||
                        clinical_paediatric_cardiology.isChecked() || clinical_paediatric_surgery.isChecked() || clinical_plastic_reconstructive.isChecked()  || clinical_surgical_gastroenterology.isChecked() ||
                        clinical_urology.isChecked() || clinical_transplantation_service.isChecked() || diagnostic_ct_scanning.isChecked() || diagnostic_mammography.isChecked() ||
                        diagnostic_mri.isChecked()  || diagnostic_ultrasound.isChecked()  || diagnostic_x_ray.isChecked()  || diagnostic_2d_echo.isChecked() || diagnostic_eeg.isChecked() ||
                        diagnostic_holter_monitoring.isChecked()  || diagnostic_spirometry.isChecked() || diagnostic_tread_mill_testing.isChecked() || diagnostic_urodynamic_studies.isChecked() ||
                        diagnostic_bone_densitometry.isChecked()  || laboratory_clinical_bio_chemistry.isChecked() || laboratory_clinical_microbiology.isChecked() ||
                        laboratory_clinical_pathology.isChecked()   || laboratory_cytopathology.isChecked()  || laboratory_haematology.isChecked() || laboratory_histopathology.isChecked() ||
                        pharmacy_dispensary.isChecked()  || transfusions_blood_transfusions.isChecked()  || transfusions_blood_bank.isChecked()  || professions_allied_ambulance.isChecked() ||
                        professions_dietetics.isChecked() || professions_physiotherapy.isChecked()  || professions_occupational_therapy.isChecked()  || professions_speech_language.isChecked() ||
                        professions_psychology.isChecked()){
                    SaveScopeService("sync");
                }else {
                    Toast.makeText(ScopeOfServiceActivity.this,"Please select at least one service",Toast.LENGTH_LONG).show();
                }



            }
        });

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getScopeService();

    }
    private void getScopeService(){
        pojo = databaseHandler.getScopeService("21");

        if (pojo != null){
            sql_status = true;
            if (pojo.getClinical_anaesthesiology() != null){
                st_clinical_anaesthesiology = pojo.getClinical_anaesthesiology();
                if (pojo.getClinical_anaesthesiology().equalsIgnoreCase("yes")){
                    clinical_anaesthesiology.setChecked(true);
                }else {
                    clinical_anaesthesiology.setChecked(false);
                }
            }
            if (pojo.getClinical_dermatology_venereology() != null){
                st_clinical_dermatology_venereology = pojo.getClinical_dermatology_venereology();
                if (pojo.getClinical_dermatology_venereology().equalsIgnoreCase("yes")){
                    clinical_dermatology_venereology.setChecked(true);
                }else {
                    clinical_dermatology_venereology.setChecked(false);
                }
            }
            if (pojo.getClinical_emergency_medicine() != null){
                st_clinical_emergency_medicine = pojo.getClinical_emergency_medicine();
                if (pojo.getClinical_emergency_medicine().equalsIgnoreCase("yes")){
                    clinical_emergency_medicine.setChecked(true);
                }else {
                    clinical_emergency_medicine.setChecked(false);
                }
            }
            if (pojo.getClinical_family_medicine() != null){
                st_clinical_family_medicine = pojo.getClinical_family_medicine();
                if (pojo.getClinical_family_medicine().equalsIgnoreCase("yes")){
                    clinical_family_medicine.setChecked(true);
                }else {
                    clinical_family_medicine.setChecked(false);
                }
            }
            if (pojo.getClinical_general_medicine() != null){
                st_clinical_general_medicine = pojo.getClinical_general_medicine();

                if (pojo.getClinical_general_medicine().equalsIgnoreCase("yes")){
                    clinical_general_medicine.setChecked(true);
                }else {
                    clinical_general_medicine.setChecked(false);
                }
            }
            if (pojo.getClinical_general_surgery() != null){
                st_clinical_general_surgery = pojo.getClinical_general_surgery();

                if (pojo.getClinical_general_surgery().equalsIgnoreCase("yes")){
                    clinical_general_surgery.setChecked(true);
                }else {
                    clinical_general_surgery.setChecked(false);
                }
            }
            if (pojo.getClinical_obstetrics_gynecology() != null){
                st_clinical_obstetrics_gynecology = pojo.getClinical_obstetrics_gynecology();

                if (pojo.getClinical_obstetrics_gynecology().equalsIgnoreCase("yes")){
                    clinical_obstetrics_gynecology.setChecked(true);
                }else {
                    clinical_obstetrics_gynecology.setChecked(false);
                }
            }
            if (pojo.getClinical_ophthalmology() != null){
                st_clinical_ophthalmology = pojo.getClinical_ophthalmology();

                if (pojo.getClinical_ophthalmology().equalsIgnoreCase("yes")){
                    clinical_ophthalmology.setChecked(true);
                }else {
                    clinical_ophthalmology.setChecked(false);
                }
            }
            if (pojo.getClinical_orthopaedic_surgery() != null){
                st_clinical_orthopaedic_surgery = pojo.getClinical_orthopaedic_surgery();

                if (pojo.getClinical_orthopaedic_surgery().equalsIgnoreCase("yes")){
                    clinical_orthopaedic_surgery.setChecked(true);
                }else {
                    clinical_orthopaedic_surgery.setChecked(false);
                }
            }
            if (pojo.getClinical_otorhinolaryngology() != null){
                st_clinical_otorhinolaryngology = pojo.getClinical_otorhinolaryngology();

                if (pojo.getClinical_otorhinolaryngology().equalsIgnoreCase("yes")){
                    clinical_otorhinolaryngology.setChecked(true);
                }else {
                    clinical_otorhinolaryngology.setChecked(false);
                }
            }
            if (pojo.getClinical_paediatrics() != null){
                st_clinical_paediatrics = pojo.getClinical_paediatrics();

                if(pojo.getClinical_paediatrics().equalsIgnoreCase("yes")){
                    clinical_paediatrics.setChecked(true);
                }else {
                    clinical_paediatrics.setChecked(false);
                }
            }
            if (pojo.getClinical_Psychiatry() != null){
                st_clinical_Psychiatry = pojo.getClinical_Psychiatry();

                if (pojo.getClinical_Psychiatry().equalsIgnoreCase("yes")){
                    clinical_Psychiatry.setChecked(true);
                }else {
                    clinical_Psychiatry.setChecked(false);
                }
            }
            if (pojo.getClinical_respiratory_medicine() != null){
                st_clinical_respiratory_medicine = pojo.getClinical_respiratory_medicine();

                if (pojo.getClinical_respiratory_medicine().equalsIgnoreCase("yes")){
                    clinical_respiratory_medicine.setChecked(true);
                }else {
                    clinical_respiratory_medicine.setChecked(false);
                }
            }
            if (pojo.getClinical_day_care_services() != null){
                st_clinical_day_care_services = pojo.getClinical_day_care_services();

                if (pojo.getClinical_day_care_services().equalsIgnoreCase("yes")){
                    clinical_day_care_services.setChecked(true);
                }else {
                    clinical_day_care_services.setChecked(false);
                }
            }
            if (pojo.getClinical_cardiac_anaesthesia() != null){
                st_clinical_cardiac_anaesthesia = pojo.getClinical_cardiac_anaesthesia();

                if (pojo.getClinical_cardiac_anaesthesia().equalsIgnoreCase("yes")){
                    clinical_cardiac_anaesthesia.setChecked(true);
                }else {
                    clinical_cardiac_anaesthesia.setChecked(false);
                }
            }
            if (pojo.getClinical_cardiology() != null){
                st_clinical_cardiology = pojo.getClinical_cardiology();

                if (pojo.getClinical_cardiology().equalsIgnoreCase("yes")){
                    clinical_cardiology.setChecked(true);
                }else {
                    clinical_cardiology.setChecked(false);
                }
            }
            if (pojo.getClinical_cardiothoracic_surgery() != null){
                st_clinical_cardiothoracic_surgery = pojo.getClinical_cardiothoracic_surgery();

                if (pojo.getClinical_cardiothoracic_surgery().equalsIgnoreCase("yes")){
                    clinical_cardiothoracic_surgery.setChecked(true);
                }else {
                    clinical_cardiothoracic_surgery.setChecked(false);
                }
            }
            if (pojo.getClinical_clinical_haematology() != null){
                st_clinical_clinical_haematology = pojo.getClinical_clinical_haematology();

                if (pojo.getClinical_clinical_haematology().equalsIgnoreCase("yes")){
                    clinical_clinical_haematology.setChecked(true);
                }else {
                    clinical_clinical_haematology.setChecked(false);
                }
            }
            if (pojo.getClinical_critical_care() != null){
                st_clinical_critical_care = pojo.getClinical_critical_care();

                if (pojo.getClinical_critical_care().equalsIgnoreCase("yes")){
                    clinical_critical_care.setChecked(true);
                }else {
                    clinical_critical_care.setChecked(false);
                }
            }
            if (pojo.getClinical_endocrinology() != null){
                st_clinical_endocrinology = pojo.getClinical_endocrinology();

                if (pojo.getClinical_endocrinology().equalsIgnoreCase("yes")){
                    clinical_endocrinology.setChecked(true);
                }else {
                    clinical_endocrinology.setChecked(false);
                }
            }
            if (pojo.getClinical_hepatology() != null){
                st_clinical_hepatology = pojo.getClinical_hepatology();

                if (pojo.getClinical_hepatology().equalsIgnoreCase("yes")){
                    clinical_hepatology.setChecked(true);
                }else {
                    clinical_hepatology.setChecked(false);
                }
            }
            if (pojo.getClinical_immunology() != null){
                st_clinical_immunology = pojo.getClinical_immunology();

                if (pojo.getClinical_immunology().equalsIgnoreCase("yes")){
                    clinical_immunology.setChecked(true);
                }else {
                    clinical_immunology.setChecked(false);
                }
            }
            if (pojo.getClinical_medical_gastroenterology() != null){
                st_clinical_medical_gastroenterology = pojo.getClinical_medical_gastroenterology();

                if (pojo.getClinical_medical_gastroenterology().equalsIgnoreCase("yes")){
                    clinical_medical_gastroenterology.setChecked(true);
                }else {
                    clinical_medical_gastroenterology.setChecked(false);
                }
            }
            if (pojo.getClinical_Neurology() != null){
                st_clinical_neonatology = pojo.getClinical_Neurology();

                if (pojo.getClinical_Neurology().equalsIgnoreCase("yes")){
                    clinical_neonatology.setChecked(true);
                }else {
                    clinical_neonatology.setChecked(false);
                }
            }
            if (pojo.getClinical_nephrology() != null){
                st_clinical_nephrology = pojo.getClinical_nephrology();

                if (pojo.getClinical_nephrology().equalsIgnoreCase("yes")){
                    clinical_nephrology.setChecked(true);
                }else {
                    clinical_nephrology.setChecked(false);
                }
            }
            if (pojo.getClinical_Neurology() != null){
                st_clinical_Neurology = pojo.getClinical_Neurology();

                if (pojo.getClinical_Neurology().equalsIgnoreCase("yes")){
                    clinical_Neurology.setChecked(true);
                }else {
                    clinical_Neurology.setChecked(false);
                }
            }
            if (pojo.getClinical_Neurosurgery() != null){
                st_clinical_Neurosurgery = pojo.getClinical_Neurosurgery();

                if (pojo.getClinical_Neurosurgery().equalsIgnoreCase("yes")){
                    clinical_Neurosurgery.setChecked(true);
                }else {
                    clinical_Neurosurgery.setChecked(false);
                }
            }
            if (pojo.getClinical_Oncology() != null){
                st_clinical_Oncology = pojo.getClinical_Oncology();

                if (pojo.getClinical_Oncology().equalsIgnoreCase("yes")){
                    clinical_Oncology.setChecked(true);
                }else {
                    clinical_Oncology.setChecked(false);
                }
            }
            if (pojo.getClinical_paediatric_cardiology() != null){
                st_clinical_paediatric_cardiology = pojo.getClinical_paediatric_cardiology();

                if (pojo.getClinical_paediatric_cardiology().equalsIgnoreCase("yes")){
                    clinical_paediatric_cardiology.setChecked(true);
                }else {
                    clinical_paediatric_cardiology.setChecked(false);
                }
            }
            if (pojo.getClinical_paediatric_surgery() != null){
                st_clinical_paediatric_surgery = pojo.getClinical_paediatric_surgery();

                if (pojo.getClinical_paediatric_surgery().equalsIgnoreCase("yes")){
                    clinical_paediatric_surgery.setChecked(true);
                }else {
                    clinical_paediatric_surgery.setChecked(false);
                }
            }
            if (pojo.getClinical_plastic_reconstructive() !=  null){
                st_clinical_plastic_reconstructive = pojo.getClinical_plastic_reconstructive();

                if (pojo.getClinical_plastic_reconstructive().equalsIgnoreCase("yes")){
                    clinical_plastic_reconstructive.setChecked(true);
                }else {
                    clinical_plastic_reconstructive.setChecked(false);
                }
            }
            if (pojo.getClinical_surgical_gastroenterology() != null){
                st_clinical_surgical_gastroenterology = pojo.getClinical_surgical_gastroenterology();

                if (pojo.getClinical_surgical_gastroenterology().equalsIgnoreCase("yes")){
                    clinical_surgical_gastroenterology.setChecked(true);
                }else {
                    clinical_surgical_gastroenterology.setChecked(false);
                }
            }
            if (pojo.getClinical_urology() != null){
                st_clinical_urology = pojo.getClinical_urology();

                if (pojo.getClinical_urology().equalsIgnoreCase("yes")){
                    clinical_urology.setChecked(true);
                }else {
                    clinical_urology.setChecked(false);
                }
            }
            if (pojo.getClinical_transplantation_service() != null){
                st_clinical_transplantation_service = pojo.getClinical_transplantation_service();

                if (pojo.getClinical_transplantation_service().equalsIgnoreCase("yes")){
                    clinical_transplantation_service.setChecked(true);
                }else {
                    clinical_transplantation_service.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_ct_scanning() != null){
                st_diagnostic_ct_scanning = pojo.getDiagnostic_ct_scanning();

                if (pojo.getDiagnostic_ct_scanning().equalsIgnoreCase("yes")){
                    diagnostic_ct_scanning.setChecked(true);
                }else {
                    diagnostic_ct_scanning.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_mammography() != null){
                st_diagnostic_mammography = pojo.getDiagnostic_mammography();

                if (pojo.getDiagnostic_mammography() .equalsIgnoreCase("yes")){
                    diagnostic_mammography.setChecked(true);
                }else {
                    diagnostic_mammography.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_mri() != null){
                st_diagnostic_mri = pojo.getDiagnostic_mri();

                if (pojo.getDiagnostic_mri().equalsIgnoreCase("yes")){
                    diagnostic_mri.setChecked(true);
                }else {
                    diagnostic_mri.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_ultrasound() != null){
                st_diagnostic_ultrasound = pojo.getDiagnostic_ultrasound();

                if (pojo.getDiagnostic_ultrasound().equalsIgnoreCase("yes")){
                    diagnostic_ultrasound.setChecked(true);
                }else {
                    diagnostic_ultrasound.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_x_ray() != null){
                st_diagnostic_x_ray = pojo.getDiagnostic_x_ray();

                if (pojo.getDiagnostic_x_ray().equalsIgnoreCase("yes")){
                    diagnostic_x_ray.setChecked(true);
                }else {
                    diagnostic_x_ray.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_2d_echo() != null){
                st_diagnostic_2d_echo = pojo.getDiagnostic_2d_echo();

                if (pojo.getDiagnostic_2d_echo().equalsIgnoreCase("yes")){
                    diagnostic_2d_echo.setChecked(true);
                }else {
                    diagnostic_2d_echo.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_eeg() != null){
                st_diagnostic_eeg = pojo.getDiagnostic_eeg();

                if (pojo.getDiagnostic_eeg().equalsIgnoreCase("yes")){
                    diagnostic_eeg.setChecked(true);
                }else {
                    diagnostic_eeg.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_holter_monitoring() != null){
                st_diagnostic_holter_monitoring = pojo.getDiagnostic_holter_monitoring();

                if (pojo.getDiagnostic_holter_monitoring().equalsIgnoreCase("yes")){
                    diagnostic_holter_monitoring.setChecked(true);
                }else {
                    diagnostic_holter_monitoring.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_spirometry() != null){
                st_diagnostic_spirometry = pojo.getDiagnostic_spirometry();

                if (pojo.getDiagnostic_spirometry().equalsIgnoreCase("yes")){
                    diagnostic_spirometry.setChecked(true);
                }else {
                    diagnostic_spirometry.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_tread_mill_testing() != null){
                st_diagnostic_tread_mill_testing = pojo.getDiagnostic_tread_mill_testing();

                if (pojo.getDiagnostic_tread_mill_testing().equalsIgnoreCase("yes")){
                    diagnostic_tread_mill_testing.setChecked(true);
                }else {
                    diagnostic_tread_mill_testing.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_urodynamic_studies() != null){
                st_diagnostic_urodynamic_studies = pojo.getDiagnostic_urodynamic_studies();

                if (pojo.getDiagnostic_urodynamic_studies().equalsIgnoreCase("yes")){
                    diagnostic_urodynamic_studies.setChecked(true);
                }else {
                    diagnostic_urodynamic_studies.setChecked(false);
                }
            }
            if (pojo.getDiagnostic_bone_densitometry() != null){
                st_diagnostic_bone_densitometry = pojo.getDiagnostic_bone_densitometry();

                if (pojo.getDiagnostic_bone_densitometry().equalsIgnoreCase("yes")){
                    diagnostic_bone_densitometry.setChecked(true);
                }else {
                    diagnostic_bone_densitometry.setChecked(false);
                }
            }
            if (pojo.getLaboratory_clinical_bio_chemistry() != null){
                st_laboratory_clinical_bio_chemistry = pojo.getLaboratory_clinical_bio_chemistry();

                if (pojo.getLaboratory_clinical_bio_chemistry().equalsIgnoreCase("yes")){
                    laboratory_clinical_bio_chemistry.setChecked(true);
                }else {
                    laboratory_clinical_bio_chemistry.setChecked(false);
                }
            }
            if (pojo.getLaboratory_clinical_microbiology() != null){
                st_laboratory_clinical_microbiology = pojo.getLaboratory_clinical_microbiology();

                if (pojo.getLaboratory_clinical_microbiology().equalsIgnoreCase("yes")){
                    laboratory_clinical_microbiology.setChecked(true);
                }else {
                    laboratory_clinical_microbiology.setChecked(false);
                }
            }
            if (pojo.getLaboratory_clinical_pathology() != null){
                st_laboratory_clinical_pathology = pojo.getLaboratory_clinical_pathology();

                if (pojo.getLaboratory_clinical_pathology().equalsIgnoreCase("yes")){
                    laboratory_clinical_pathology.setChecked(true);
                }else {
                    laboratory_clinical_pathology.setChecked(false);
                }
            }
            if (pojo.getLaboratory_cytopathology() != null){
                st_laboratory_cytopathology = pojo.getLaboratory_cytopathology();

                if (pojo.getLaboratory_cytopathology().equalsIgnoreCase("yes")){
                    laboratory_cytopathology.setChecked(true);
                }else {
                    laboratory_cytopathology.setChecked(false);
                }
            }
            if (pojo.getLaboratory_haematology() != null){
                st_laboratory_haematology = pojo.getLaboratory_haematology();

                if (pojo.getLaboratory_haematology().equalsIgnoreCase("yes")){
                    laboratory_haematology.setChecked(true);
                }else {
                    laboratory_haematology.setChecked(false);
                }
            }
            if (pojo.getLaboratory_histopathology() != null){
                st_laboratory_histopathology = pojo.getLaboratory_histopathology();

                if (pojo.getLaboratory_histopathology().equalsIgnoreCase("yes")){
                    laboratory_histopathology.setChecked(true);
                }else {
                    laboratory_histopathology.setChecked(false);
                }
            }

            if (pojo.getPharmacy_dispensary() != null){
                st_pharmacy_dispensary = pojo.getPharmacy_dispensary();

                if (pojo.getPharmacy_dispensary().equalsIgnoreCase("yes")){
                    pharmacy_dispensary.setChecked(true);
                }else {
                    pharmacy_dispensary.setChecked(false);
                }
            }
            if (pojo.getTransfusions_blood_transfusions() !=null){
                st_transfusions_blood_transfusions = pojo.getTransfusions_blood_transfusions();

                if (pojo.getTransfusions_blood_transfusions().equalsIgnoreCase("yes")){
                    transfusions_blood_transfusions.setChecked(true);
                }else {
                    transfusions_blood_transfusions.setChecked(false);
                }
            }
            if (pojo.getTransfusions_blood_bank() != null){
                st_transfusions_blood_bank = pojo.getTransfusions_blood_bank();

                if (pojo.getTransfusions_blood_bank().equalsIgnoreCase("yes")){
                    transfusions_blood_bank.setChecked(true);
                }else {
                    transfusions_blood_bank.setChecked(false);
                }
            }
            if (pojo.getProfessions_allied_ambulance() != null){
                st_professions_allied_ambulance = pojo.getProfessions_allied_ambulance();

                if (pojo.getProfessions_allied_ambulance().equalsIgnoreCase("yes")){
                    professions_allied_ambulance.setChecked(true);
                }else {
                    professions_allied_ambulance.setChecked(false);
                }
            }
            if (pojo.getProfessions_dietetics() != null){
                st_professions_dietetics = pojo.getProfessions_dietetics();

                if (pojo.getProfessions_dietetics().equalsIgnoreCase("yes")){
                    professions_dietetics.setChecked(true);
                }else {
                    professions_dietetics.setChecked(false);
                }
            }
            if (pojo.getProfessions_physiotherapy() != null){
                st_professions_physiotherapy = pojo.getProfessions_physiotherapy();

                if (pojo.getProfessions_physiotherapy().equalsIgnoreCase("yes")){
                    professions_physiotherapy.setChecked(true);
                }else {
                    professions_physiotherapy.setChecked(false);
                }
            }
            if (pojo.getProfessions_occupational_therapy() != null){
                st_professions_occupational_therapy = pojo.getProfessions_occupational_therapy();

                if (pojo.getProfessions_occupational_therapy().equalsIgnoreCase("yes")){
                    professions_occupational_therapy.setChecked(true);
                }else {
                    professions_occupational_therapy.setChecked(false);
                }
            }
            if (pojo.getProfessions_speech_language() != null){
                st_professions_speech_language = pojo.getProfessions_speech_language();

                if (pojo.getProfessions_speech_language().equalsIgnoreCase("yes")){
                    professions_speech_language.setChecked(true);
                }else {
                    professions_speech_language.setChecked(false);
                }
            }
            if (pojo.getProfessions_psychology() != null){
                st_professions_psychology = pojo.getProfessions_psychology();

                if (pojo.getProfessions_psychology().equalsIgnoreCase("yes")){
                    professions_psychology.setChecked(true);
                }else {
                    professions_psychology.setChecked(false);
                }
            }

        }else {
            pojo = new ScopeOfServicePojo();
        }
    }
    private void SaveScopeService(String from){
        if (clinical_anaesthesiology.isChecked()) {
            st_clinical_anaesthesiology = "yes";
        } else {
            st_clinical_anaesthesiology = "no";
        }

        if (clinical_dermatology_venereology.isChecked()) {
            st_clinical_dermatology_venereology = "yes";
        } else {
            st_clinical_dermatology_venereology = "no";
        }
        if (clinical_emergency_medicine.isChecked()) {
            st_clinical_emergency_medicine = "yes";
        } else {
            st_clinical_emergency_medicine = "no";
        }
        if (clinical_family_medicine.isChecked()) {
            st_clinical_family_medicine = "yes";
        } else {
            st_clinical_family_medicine = "no";
        }
        if (clinical_general_medicine.isChecked()) {
            st_clinical_general_medicine = "yes";
        } else {
            st_clinical_general_medicine = "no";
        }

        if (clinical_general_surgery.isChecked()) {
            st_clinical_general_surgery = "yes";
        } else {
            st_clinical_general_surgery = "no";
        }
        if (clinical_obstetrics_gynecology.isChecked()) {
            st_clinical_obstetrics_gynecology = "yes";
        } else {
            st_clinical_obstetrics_gynecology = "no";
        }

        if (clinical_ophthalmology.isChecked()) {
            st_clinical_ophthalmology = "yes";
        } else {
            st_clinical_ophthalmology = "no";
        }
        if (clinical_orthopaedic_surgery.isChecked()) {
            st_clinical_orthopaedic_surgery = "yes";
        } else {
            st_clinical_orthopaedic_surgery = "no";
        }
        if (clinical_otorhinolaryngology.isChecked()) {
            st_clinical_otorhinolaryngology = "yes";
        } else {
            st_clinical_otorhinolaryngology = "no";
        }
        if (clinical_paediatrics.isChecked()) {
            st_clinical_paediatrics = "yes";
        } else {
            st_clinical_paediatrics = "no";
        }
        if (clinical_Psychiatry.isChecked()) {
            st_clinical_Psychiatry = "yes";
        } else {
            st_clinical_Psychiatry = "no";
        }
        if (clinical_respiratory_medicine.isChecked()) {
            st_clinical_respiratory_medicine = "yes";
        } else {
            st_clinical_respiratory_medicine = "no";
        }

        if (clinical_day_care_services.isChecked()) {
            st_clinical_day_care_services = "yes";
        } else {
            st_clinical_day_care_services = "no";
        }
        if (clinical_cardiac_anaesthesia.isChecked()) {
            st_clinical_cardiac_anaesthesia = "yes";
        } else {
            st_clinical_cardiac_anaesthesia = "no";
        }
        if (clinical_cardiology.isChecked()) {
            st_clinical_cardiology = "yes";
        } else {
            st_clinical_cardiology = "no";
        }
        if (clinical_cardiothoracic_surgery.isChecked()) {
            st_clinical_cardiology = "yes";
        } else {
            st_clinical_cardiology = "no";
        }
        if (clinical_clinical_haematology.isChecked()) {
            st_clinical_clinical_haematology = "yes";
        } else {
            st_clinical_clinical_haematology = "no";
        }
        if (clinical_critical_care.isChecked()) {
            st_clinical_critical_care = "yes";
        } else {
            st_clinical_critical_care = "no";
        }
        if (clinical_endocrinology.isChecked()) {
            st_clinical_endocrinology = "yes";
        } else {
            st_clinical_endocrinology = "no";
        }
        if (clinical_hepatology.isChecked()) {
            st_clinical_hepatology = "yes";
        } else {
            st_clinical_hepatology = "no";
        }
        if (clinical_immunology.isChecked()) {
            st_clinical_immunology = "yes";
        } else {
            st_clinical_immunology = "no";
        }
        if (clinical_medical_gastroenterology.isChecked()) {
            st_clinical_medical_gastroenterology = "yes";
        } else {
            st_clinical_medical_gastroenterology = "no";
        }
        if (clinical_neonatology.isChecked()) {
            st_clinical_neonatology = "yes";
        } else {
            st_clinical_neonatology = "no";
        }
        if (clinical_nephrology.isChecked()) {
            st_clinical_nephrology = "yes";
        } else {
            st_clinical_nephrology = "no";
        }
        if (clinical_Neurology.isChecked()) {
            st_clinical_Neurology = "yes";
        } else {
            st_clinical_Neurology = "no";
        }
        if (clinical_Neurosurgery.isChecked()) {
            st_clinical_Neurosurgery = "yes";
        } else {
            st_clinical_Neurosurgery = "no";
        }
        if (clinical_Oncology.isChecked()) {
            st_clinical_Oncology = "yes";
        } else {
            st_clinical_Oncology = "no";
        }
        if (clinical_paediatric_cardiology.isChecked()) {
            st_clinical_paediatric_cardiology = "yes";
        } else {
            st_clinical_paediatric_cardiology = "no";
        }
        if (clinical_paediatric_surgery.isChecked()) {
            st_clinical_paediatric_surgery = "yes";
        } else {
            st_clinical_paediatric_surgery = "no";
        }
        if (clinical_plastic_reconstructive.isChecked()) {
            st_clinical_plastic_reconstructive = "yes";
        } else {
            st_clinical_plastic_reconstructive = "no";
        }
        if (clinical_surgical_gastroenterology.isChecked()) {
            st_clinical_surgical_gastroenterology = "yes";
        } else {
            st_clinical_surgical_gastroenterology = "no";
        }
        if (clinical_urology.isChecked()) {
            st_clinical_urology = "yes";
        } else {
            st_clinical_urology = "no";
        }
        if (clinical_transplantation_service.isChecked()) {
            st_clinical_transplantation_service = "yes";
        } else {
            st_clinical_transplantation_service = "no";
        }
        if (diagnostic_ct_scanning.isChecked()) {
            st_diagnostic_ct_scanning = "yes";
        } else {
            st_diagnostic_ct_scanning = "no";
        }

        if (diagnostic_mammography.isChecked()) {
            st_diagnostic_mammography = "yes";
        } else {
            st_diagnostic_mammography = "no";
        }

        if (diagnostic_mri.isChecked()) {
            st_diagnostic_mri = "yes";
        } else {
            st_diagnostic_mri = "no";
        }

        if (diagnostic_ultrasound.isChecked()) {
            st_diagnostic_ultrasound = "yes";
        } else {
            st_diagnostic_ultrasound = "no";
        }

        if (diagnostic_x_ray.isChecked()) {
            st_diagnostic_x_ray = "yes";
        } else {
            st_diagnostic_x_ray = "no";
        }
        if (diagnostic_2d_echo.isChecked()) {
            st_diagnostic_2d_echo = "yes";
        } else {
            st_diagnostic_2d_echo = "no";
        }
        if (diagnostic_eeg.isChecked()) {
            st_diagnostic_eeg = "yes";
        } else {
            st_diagnostic_eeg = "no";
        }
        if (diagnostic_holter_monitoring.isChecked()) {
            st_diagnostic_holter_monitoring = "yes";
        } else {
            st_diagnostic_holter_monitoring = "no";
        }
        if (diagnostic_spirometry.isChecked()) {
            st_diagnostic_spirometry = "yes";
        } else {
            st_diagnostic_spirometry = "no";
        }
        if (diagnostic_tread_mill_testing.isChecked()) {
            st_diagnostic_tread_mill_testing = "yes";
        } else {
            st_diagnostic_tread_mill_testing = "no";
        }
        if (diagnostic_urodynamic_studies.isChecked()) {
            st_diagnostic_urodynamic_studies = "yes";
        } else {
            st_diagnostic_urodynamic_studies = "no";
        }

        if (diagnostic_bone_densitometry.isChecked()) {
            st_diagnostic_bone_densitometry = "yes";
        } else {
            st_diagnostic_bone_densitometry = "no";
        }

        if (laboratory_clinical_bio_chemistry.isChecked()) {
            st_laboratory_clinical_bio_chemistry = "yes";
        } else {
            st_laboratory_clinical_bio_chemistry = "no";
        }
        if (laboratory_clinical_microbiology.isChecked()) {
            st_laboratory_clinical_microbiology = "yes";
        } else {
            st_laboratory_clinical_microbiology = "no";
        }
        if (laboratory_clinical_pathology.isChecked()) {
            st_laboratory_clinical_pathology = "yes";
        } else {
            st_laboratory_clinical_pathology = "no";
        }
        if (laboratory_cytopathology.isChecked()) {
            st_laboratory_cytopathology = "yes";
        } else {
            st_laboratory_cytopathology = "no";
        }
        if (laboratory_haematology.isChecked()) {
            st_laboratory_haematology = "yes";
        } else {
            st_laboratory_haematology = "no";
        }
        if (laboratory_histopathology.isChecked()) {
            st_laboratory_histopathology = "yes";
        } else {
            st_laboratory_histopathology = "no";
        }
        if (pharmacy_dispensary.isChecked()) {
            st_pharmacy_dispensary = "yes";
        } else {
            st_pharmacy_dispensary = "no";
        }
        if (transfusions_blood_transfusions.isChecked()) {
            st_transfusions_blood_transfusions = "yes";
        } else {
            st_transfusions_blood_transfusions = "no";
        }
        if (transfusions_blood_bank.isChecked()) {
            st_transfusions_blood_bank = "yes";
        } else {
            st_transfusions_blood_bank = "no";
        }
        if (professions_allied_ambulance.isChecked()) {
            st_professions_allied_ambulance = "yes";
        } else {
            st_professions_allied_ambulance = "no";
        }
        if (professions_dietetics.isChecked()) {
            st_professions_dietetics = "yes";
        } else {
            st_professions_dietetics = "no";
        }
        if (professions_physiotherapy.isChecked()) {
            st_professions_physiotherapy = "yes";
        } else {
            st_professions_physiotherapy = "no";
        }
        if (professions_occupational_therapy.isChecked()) {
            st_professions_occupational_therapy = "yes";
        } else {
            st_professions_occupational_therapy = "no";
        }
        if (professions_speech_language.isChecked()) {
            st_professions_speech_language = "yes";
        } else {
            st_professions_speech_language = "no";
        }
        if (professions_psychology.isChecked()) {
            st_professions_psychology = "yes";
        } else {
            st_professions_psychology = "no";
        }

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(21);
        pojo.setClinical_anaesthesiology(st_clinical_anaesthesiology);
        pojo.setClinical_dermatology_venereology(st_clinical_dermatology_venereology);
        pojo.setClinical_emergency_medicine(st_clinical_emergency_medicine);
        pojo.setClinical_family_medicine(st_clinical_family_medicine);
        pojo.setClinical_general_medicine(st_clinical_general_medicine);
        pojo.setClinical_general_surgery(st_clinical_general_surgery);
        pojo.setClinical_obstetrics_gynecology(st_clinical_obstetrics_gynecology);
        pojo.setClinical_ophthalmology(st_clinical_ophthalmology);
        pojo.setClinical_orthopaedic_surgery(st_clinical_orthopaedic_surgery);
        pojo.setClinical_otorhinolaryngology(st_clinical_otorhinolaryngology);
        pojo.setClinical_paediatrics(st_clinical_paediatrics);
        pojo.setClinical_Psychiatry(st_clinical_Psychiatry);
        pojo.setClinical_respiratory_medicine(st_clinical_respiratory_medicine);
        pojo.setClinical_day_care_services(st_clinical_day_care_services);
        pojo.setClinical_cardiac_anaesthesia(st_clinical_cardiac_anaesthesia);
        pojo.setClinical_cardiology(st_clinical_cardiology);
        pojo.setClinical_cardiothoracic_surgery(st_clinical_cardiothoracic_surgery);
        pojo.setClinical_clinical_haematology(st_clinical_clinical_haematology);
        pojo.setClinical_critical_care(st_clinical_critical_care);
        pojo.setClinical_endocrinology(st_clinical_endocrinology);
        pojo.setClinical_hepatology(st_clinical_hepatology);
        pojo.setClinical_immunology(st_clinical_immunology);
        pojo.setClinical_medical_gastroenterology(st_clinical_medical_gastroenterology);
        pojo.setClinical_Neurology(st_clinical_neonatology);
        pojo.setClinical_nephrology(st_clinical_nephrology);
        pojo.setClinical_Neurosurgery(st_clinical_Neurosurgery);
        pojo.setClinical_Oncology(st_clinical_Oncology);
        pojo.setClinical_paediatric_cardiology(st_clinical_paediatric_cardiology);
        pojo.setClinical_paediatric_surgery(st_clinical_paediatric_surgery);
        pojo.setClinical_plastic_reconstructive(st_clinical_plastic_reconstructive);
        pojo.setClinical_surgical_gastroenterology(st_clinical_surgical_gastroenterology);
        pojo.setClinical_urology(st_clinical_urology);
        pojo.setClinical_transplantation_service(st_clinical_transplantation_service);
        pojo.setDiagnostic_ct_scanning(st_diagnostic_ct_scanning);
        pojo.setDiagnostic_mammography(st_diagnostic_mammography);
        pojo.setDiagnostic_mri(st_diagnostic_mri);
        pojo.setDiagnostic_ultrasound(st_diagnostic_ultrasound);
        pojo.setDiagnostic_x_ray(st_diagnostic_x_ray);
        pojo.setDiagnostic_2d_echo(st_diagnostic_2d_echo);
        pojo.setDiagnostic_eeg(st_diagnostic_eeg);
        pojo.setDiagnostic_holter_monitoring(st_diagnostic_holter_monitoring);
        pojo.setDiagnostic_spirometry(st_diagnostic_spirometry);
        pojo.setDiagnostic_tread_mill_testing(st_diagnostic_tread_mill_testing);
        pojo.setDiagnostic_urodynamic_studies(st_diagnostic_urodynamic_studies);
        pojo.setDiagnostic_bone_densitometry(st_diagnostic_bone_densitometry);
        pojo.setLaboratory_clinical_bio_chemistry(st_laboratory_clinical_bio_chemistry);
        pojo.setLaboratory_clinical_microbiology(st_laboratory_clinical_microbiology);
        pojo.setLaboratory_clinical_pathology(st_laboratory_clinical_pathology);
        pojo.setLaboratory_cytopathology(st_laboratory_cytopathology);
        pojo.setLaboratory_haematology(st_laboratory_haematology);
        pojo.setLaboratory_histopathology(st_laboratory_histopathology);
        pojo.setPharmacy_dispensary(st_pharmacy_dispensary);
        pojo.setTransfusions_blood_transfusions(st_transfusions_blood_transfusions);
        pojo.setTransfusions_blood_bank(st_transfusions_blood_bank);
        pojo.setProfessions_allied_ambulance(st_professions_allied_ambulance);
        pojo.setProfessions_dietetics(st_professions_dietetics);
        pojo.setProfessions_physiotherapy(st_professions_physiotherapy);
        pojo.setProfessions_occupational_therapy(st_professions_occupational_therapy);
        pojo.setProfessions_speech_language(st_professions_speech_language);
        pojo.setProfessions_psychology(st_professions_psychology);



        if (sql_status) {
            boolean sp_status = databaseHandler.UPDATE_SCOPE_SERVICE(pojo);

            if (sp_status){
                if (!from.equalsIgnoreCase("sync")) {
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(21).getHospital_id());
                    pojo.setAssessement_name("Scope of Services");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(21).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(ScopeOfServiceActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ScopeOfServiceActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progreesDialog();
                    PostScopeData();
                }
            }
        } else {
            boolean sp_status = databaseHandler.INSERT_SCOPE_SERVICE(pojo);

            if (sp_status){
                if (!from.equalsIgnoreCase("sync")) {
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(21).getHospital_id());
                    pojo.setAssessement_name("Scope of Services");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(21).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(ScopeOfServiceActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ScopeOfServiceActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progreesDialog();
                    PostScopeData();
                }
            }
        }


    }

    private void PostScopeData(){

            pojo_dataSync.setTabName("scopeofservice");
            pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
            pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
            if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
            }else {
                pojo_dataSync.setAssessment_id(0);
            }
             pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));


            pojo_dataSync.setScopeofservice(pojo);

            mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                @Override
                public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                    System.out.println("xxx sucess");

                    CloseProgreesDialog();

                    if (response.message().equalsIgnoreCase("Unauthorized")) {
                        Intent intent = new Intent(ScopeOfServiceActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        Toast.makeText(ScopeOfServiceActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                    }else {
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                Intent intent = new Intent(ScopeOfServiceActivity.this,HospitalListActivity.class);
                                startActivity(intent);
                                finish();

                                saveIntoPrefs("Pharmacy_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                pojo.setHospital_id(assessement_list.get(21).getHospital_id());
                                pojo.setAssessement_name("Scope of Services");
                                pojo.setAssessement_status("Done");
                                pojo.setLocal_id(assessement_list.get(21).getLocal_id());

                                databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                Toast.makeText(ScopeOfServiceActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataSyncResponse> call, Throwable t) {
                    System.out.println("xxx failed");

                    CloseProgreesDialog();
                }
            });
    }

    @Override
    public void onBackPressed() {
   //     super.onBackPressed();

        if (!assessement_list.get(21).getAssessement_status().equalsIgnoreCase("Done")){

            if (clinical_anaesthesiology.isChecked() || clinical_dermatology_venereology.isChecked()  || clinical_emergency_medicine.isChecked() ||
                    clinical_family_medicine.isChecked() || clinical_general_medicine.isChecked()  || clinical_general_surgery.isChecked() || clinical_obstetrics_gynecology.isChecked() ||
                    clinical_ophthalmology.isChecked() || clinical_orthopaedic_surgery.isChecked() || clinical_otorhinolaryngology.isChecked()  || clinical_paediatrics.isChecked() ||
                    clinical_Psychiatry.isChecked() || clinical_respiratory_medicine.isChecked()  || clinical_day_care_services.isChecked() || clinical_cardiac_anaesthesia.isChecked() ||
                    clinical_cardiology.isChecked() || clinical_cardiothoracic_surgery.isChecked() || clinical_clinical_haematology.isChecked() || clinical_critical_care.isChecked() ||
                    clinical_endocrinology.isChecked() || clinical_hepatology.isChecked() || clinical_immunology.isChecked() || clinical_medical_gastroenterology.isChecked() ||
                    clinical_neonatology.isChecked() || clinical_nephrology.isChecked() || clinical_Neurology.isChecked() ||clinical_Neurosurgery.isChecked() || clinical_Oncology.isChecked() ||
                    clinical_paediatric_cardiology.isChecked() || clinical_paediatric_surgery.isChecked() || clinical_plastic_reconstructive.isChecked()  || clinical_surgical_gastroenterology.isChecked() ||
                    clinical_urology.isChecked() || clinical_transplantation_service.isChecked() || diagnostic_ct_scanning.isChecked() || diagnostic_mammography.isChecked() ||
                    diagnostic_mri.isChecked()  || diagnostic_ultrasound.isChecked()  || diagnostic_x_ray.isChecked()  || diagnostic_2d_echo.isChecked() || diagnostic_eeg.isChecked() ||
                    diagnostic_holter_monitoring.isChecked()  || diagnostic_spirometry.isChecked() || diagnostic_tread_mill_testing.isChecked() || diagnostic_urodynamic_studies.isChecked() ||
                    diagnostic_bone_densitometry.isChecked()  || laboratory_clinical_bio_chemistry.isChecked() || laboratory_clinical_microbiology.isChecked() ||
                    laboratory_clinical_pathology.isChecked()   || laboratory_cytopathology.isChecked()  || laboratory_haematology.isChecked() || laboratory_histopathology.isChecked() ||
                    pharmacy_dispensary.isChecked()  || transfusions_blood_transfusions.isChecked()  || transfusions_blood_bank.isChecked()  || professions_allied_ambulance.isChecked() ||
                    professions_dietetics.isChecked() || professions_physiotherapy.isChecked()  || professions_occupational_therapy.isChecked()  || professions_speech_language.isChecked() ||
                    professions_psychology.isChecked()){
                SaveScopeService("save");
            }else {
                Toast.makeText(ScopeOfServiceActivity.this, "Please select at least one service", Toast.LENGTH_LONG).show();
            }
        }else {
            Intent intent = new Intent(ScopeOfServiceActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
