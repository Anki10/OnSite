package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qci.onsite.R;
import com.qci.onsite.adapter.ImageShowAdapter;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.Wards_PharmacyPojo;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 14-02-2019.
 */

public class PharmacyActivity extends BaseActivity implements View.OnClickListener  {

    @BindView(R.id.patient_care_area_yes)
    RadioButton patient_care_area_yes;

    @BindView(R.id.patient_care_area_no)
    RadioButton patient_care_area_no;

    @BindView(R.id.remark_patient_care_area)
    ImageView remark_patient_care_area;

    @BindView(R.id.nc_patient_care_area)
    ImageView nc_patient_care_area;

    @BindView(R.id.Image_patient_care_area)
    ImageView Image_patient_care_area;

    @BindView(R.id.pharmacyStores_present_yes)
    RadioButton pharmacyStores_present_yes;

    @BindView(R.id.pharmacyStores_present_no)
    RadioButton pharmacyStores_present_no;

    @BindView(R.id.remark_pharmacyStores_present)
    ImageView remark_pharmacyStores_present;

    @BindView(R.id.nc_pharmacyStores_present)
    ImageView nc_pharmacyStores_present;

    @BindView(R.id.Image_pharmacyStores_present)
    ImageView Image_pharmacyStores_present;


    @BindView(R.id.drugs_pharmacy_yes)
    RadioButton drugs_pharmacy_yes;

    @BindView(R.id.drugs_pharmacy_no)
    RadioButton drugs_pharmacy_no;

    @BindView(R.id.remark_drugs_pharmacy)
    ImageView remark_drugs_pharmacy;

    @BindView(R.id.nc_drugs_pharmacy)
    ImageView nc_drugs_pharmacy;

    @BindView(R.id.image_drugs_pharmacy)
    ImageView image_drugs_pharmacy;

    @BindView(R.id.medication_expiry_yes)
    RadioButton medication_expiry_yes;

    @BindView(R.id.medication_expiry_no)
    RadioButton medication_expiry_no;

    @BindView(R.id.remark_medication_expiry)
    ImageView remark_medication_expiry;

    @BindView(R.id.nc_medication_expiry)
    ImageView nc_medication_expiry;

    @BindView(R.id.emergency_medications_yes)
    RadioButton emergency_medications_yes;

    @BindView(R.id.emergency_medications_no)
    RadioButton emergency_medications_no;

    @BindView(R.id.remark_emergency_medications)
    ImageView remark_emergency_medications;

    @BindView(R.id.nc_emergency_medications)
    ImageView nc_emergency_medications;

    @BindView(R.id.Image_emergency_medications)
    ImageView Image_emergency_medications;

    @BindView(R.id.high_risk_medications_yes)
    RadioButton high_risk_medications_yes;

    @BindView(R.id.high_risk_medications_no)
    RadioButton high_risk_medications_no;

    @BindView(R.id.remark_high_risk_medications)
    ImageView remark_high_risk_medications;

    @BindView(R.id.nc_high_risk_medications)
    ImageView nc_high_risk_medications;

    @BindView(R.id.Image_high_risk_medications)
    ImageView Image_high_risk_medications;

    @BindView(R.id.risk_medications_verified_yes)
    RadioButton risk_medications_verified_yes;

    @BindView(R.id.risk_medications_verified_no)
    RadioButton risk_medications_verified_no;

    @BindView(R.id.remark_risk_medications_verified)
    ImageView remark_risk_medications_verified;

    @BindView(R.id.nc_risk_medications_verified)
    ImageView nc_risk_medications_verified;

    @BindView(R.id.labelling_of_drug_yes)
    RadioButton labelling_of_drug_yes;

    @BindView(R.id.labelling_of_drug_no)
    RadioButton labelling_of_drug_no;

    @BindView(R.id.remark_labelling_of_drug)
    ImageView remark_labelling_of_drug;

    @BindView(R.id.nc_labelling_of_drug)
    ImageView nc_labelling_of_drug;

    @BindView(R.id.Image_labelling_of_drug)
    ImageView Image_labelling_of_drug;

    @BindView(R.id.medication_order_checked_yes)
    RadioButton medication_order_checked_yes;

    @BindView(R.id.medication_order_checked_no)
    RadioButton medication_order_checked_no;

    @BindView(R.id.remark_medication_order_checked)
    ImageView remark_medication_order_checked;

    @BindView(R.id.nc_medication_order_checked)
    ImageView nc_medication_order_checked;

    @BindView(R.id.Image_medication_order_checked)
    ImageView Image_medication_order_checked;

    @BindView(R.id.medication_administration_yes)
    RadioButton medication_administration_yes;

    @BindView(R.id.medication_administration_no)
    RadioButton medication_administration_no;

    @BindView(R.id.remark_medication_administration)
    ImageView remark_medication_administration;

    @BindView(R.id.nc_medication_administration)
    ImageView nc_medication_administration;

    @BindView(R.id.Image_medication_administration)
    ImageView Image_medication_administration;

    @BindView(R.id.fridge_temperature_yes)
    RadioButton fridge_temperature_yes;

    @BindView(R.id.fridge_temperature_no)
    RadioButton fridge_temperature_no;

    @BindView(R.id.remark_fridge_temperature)
    ImageView remark_fridge_temperature;

    @BindView(R.id.nc_fridge_temperature)
    ImageView nc_fridge_temperature;

    @BindView(R.id.Image_fridge_temperature)
    ImageView Image_fridge_temperature;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    @BindView(R.id.ll_emergency_medications)
    LinearLayout ll_emergency_medications;

    @BindView(R.id.ll_risk_medications_verified)
    LinearLayout ll_risk_medications_verified;

    int Bed_no = 0;




    private String remark1, remark2, remark3,remark4,remark5,remark6,remark7,remark8,remark9,remark10,remark11;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5,nc6,nc7,nc8,nc9,nc10,nc11;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status6,radio_status7,
            radio_status8,radio_status9,radio_status10,radio_status11;

    private DatabaseHandler databaseHandler;

    private String image1,image2,image3,image5,image6,image8,image9,image10,image11;
    private String Local_image1,Local_image2,Local_image3,Local_image5,Local_image6,Local_image8,Local_image9,Local_image10,Local_image11;

    private File outputVideoFile;

    private String patient_care_area = "",pharmacyStores_present ="",drugs_pharmacy="",medication_expiry = "",emergency_medications="",
            high_risk_medications = "",risk_medications_verified  = "",labelling_of_drug = "",medication_order_checked="",

    medication_administration = "",fridge_temperature="";

    private ArrayList<String>patientCareArea_imageList;
    private ArrayList<String>Local_patientCareArea_imageList;
    private ArrayList<String>pharmacyStores_present_imageList;
    private ArrayList<String>Local_pharmacyStores_present_imageList;
    private ArrayList<String>drugs_pharmacy_imageList;
    private ArrayList<String>Local_drugs_pharmacy_imageList;
    private ArrayList<String>emergency_medications_imageList;
    private ArrayList<String>Local_emergency_medications_imageList;
    private ArrayList<String>high_risk_medications_imageList;
    private ArrayList<String>Local_high_risk_medications_imageList;
    private ArrayList<String>labelling_of_drug_imageList;
    private ArrayList<String>Local_labelling_of_drug_imageList;

    private ArrayList<String>medication_administration_imageList;
    private ArrayList<String>Local_medication_administration_imageList;
    private ArrayList<String>medication_order_checked_imagelist;
    private ArrayList<String>Local_medication_order_checkedt_imagelist;
    private ArrayList<String>fridge_temperature_imageList;
    private ArrayList<String>Local_fridge_temperature_imageList;


    private Wards_PharmacyPojo pojo;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;

    private String patientCareArea = "",pharmacyStores_present_view = "",drugs_pharmacy_view = "",emergency_medications_view = "",high_risk_medications_imageList_view = "",
            labelling_of_drug_imageList_view = "",medication_order_checked_imageList_view = "",medication_administration_imageView = "",fridge_temperature_View = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        ButterKnife.bind(this);

        setDrawerbackIcon("Wards and Pharmacy");


        patientCareArea_imageList = new ArrayList<>();
        Local_patientCareArea_imageList = new ArrayList<>();
        pharmacyStores_present_imageList = new ArrayList<>();
        Local_pharmacyStores_present_imageList = new ArrayList<>();
        drugs_pharmacy_imageList = new ArrayList<>();
        Local_drugs_pharmacy_imageList = new ArrayList<>();
        emergency_medications_imageList = new ArrayList<>();
        Local_emergency_medications_imageList = new ArrayList<>();
        high_risk_medications_imageList = new ArrayList<>();
        Local_high_risk_medications_imageList = new ArrayList<>();
        labelling_of_drug_imageList = new ArrayList<>();
        Local_labelling_of_drug_imageList = new ArrayList<>();
        medication_administration_imageList = new ArrayList<>();
        Local_medication_administration_imageList = new ArrayList<>();
        medication_order_checked_imagelist = new ArrayList<>();
        Local_medication_order_checkedt_imagelist = new ArrayList<>();
        fridge_temperature_imageList = new ArrayList<>();
        Local_fridge_temperature_imageList = new ArrayList<>();

        assessement_list = new ArrayList<>();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        pojo_dataSync = new DataSyncRequest();


        databaseHandler = DatabaseHandler.getInstance(this);

        pojo = new Wards_PharmacyPojo();

        mAPIService = ApiUtils.getAPIService();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_emergency_medications.setVisibility(View.GONE);
            ll_risk_medications_verified.setVisibility(View.GONE);
        }else {
            ll_emergency_medications.setVisibility(View.VISIBLE);
            ll_risk_medications_verified.setVisibility(View.VISIBLE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();

    }

    public void getPharmacyData(){

        pojo = databaseHandler.getWardPharmcyPojo("7");

        if (pojo != null){
            sql_status = true;
            if (pojo.getPatient_care_area() != null){
                patient_care_area = pojo.getPatient_care_area();
                if (pojo.getPatient_care_area().equalsIgnoreCase("Yes")){
                    patient_care_area_yes.setChecked(true);
                }else if (pojo.getPatient_care_area().equalsIgnoreCase("No")){
                    patient_care_area_no.setChecked(true);
                }
            }
            if (pojo.getPharmacyStores_present() != null){
                pharmacyStores_present = pojo.getPharmacyStores_present();
                if (pojo.getPharmacyStores_present().equalsIgnoreCase("Yes")){
                    pharmacyStores_present_yes.setChecked(true);
                }else if (pojo.getPharmacyStores_present().equalsIgnoreCase("No")){
                    pharmacyStores_present_no.setChecked(true);
                }
            }
            if (pojo.getExpired_drugs() != null){
                drugs_pharmacy = pojo.getExpired_drugs();
                if (pojo.getExpired_drugs().equalsIgnoreCase("Yes")){
                    drugs_pharmacy_yes.setChecked(true);
                }else if (pojo.getExpired_drugs().equalsIgnoreCase("No")){
                    drugs_pharmacy_no.setChecked(true);
                }
            } if (pojo.getExpiry_date_checked() != null){
                medication_expiry = pojo.getExpiry_date_checked();
                if (pojo.getExpiry_date_checked().equalsIgnoreCase("Yes")){
                    medication_expiry_yes.setChecked(true);
                }else if (pojo.getExpiry_date_checked().equalsIgnoreCase("No")){
                    medication_expiry_no.setChecked(true);
                }
            }
            if (pojo.getEmergency_medications() != null){
                emergency_medications = pojo.getEmergency_medications();
                if (pojo.getEmergency_medications().equalsIgnoreCase("Yes")){
                    emergency_medications_yes.setChecked(true);
                }else if (pojo.getEmergency_medications().equalsIgnoreCase("No")){
                    emergency_medications_no.setChecked(true);
                }
            }

            if (pojo.getRisk_medications() != null){
                high_risk_medications = pojo.getRisk_medications();
                if (pojo.getRisk_medications().equalsIgnoreCase("Yes")){
                    high_risk_medications_yes.setChecked(true);
                }else if (pojo.getRisk_medications().equalsIgnoreCase("No")){
                    high_risk_medications_no.setChecked(true);
                }
            }

            if (pojo.getMedications_dispensing() != null){
                risk_medications_verified = pojo.getMedications_dispensing();
                if (pojo.getMedications_dispensing().equalsIgnoreCase("Yes")){
                    risk_medications_verified_yes.setChecked(true);
                }else if (pojo.getMedications_dispensing().equalsIgnoreCase("No")){
                    risk_medications_verified_no.setChecked(true);
                }
            }

            if (pojo.getLabelling_of_drug() != null){
                labelling_of_drug = pojo.getLabelling_of_drug();
                if (pojo.getLabelling_of_drug().equalsIgnoreCase("Yes")){
                    labelling_of_drug_yes.setChecked(true);
                }else if (pojo.getLabelling_of_drug().equalsIgnoreCase("No")){
                    labelling_of_drug_no.setChecked(true);
                }
            }

            if (pojo.getMedication_order_checked() != null){
                medication_order_checked = pojo.getMedication_order_checked();
                if (pojo.getMedication_order_checked().equalsIgnoreCase("Yes")){
                    medication_order_checked_yes.setChecked(true);
                }else if (pojo.getMedication_order_checked().equalsIgnoreCase("No")){
                    medication_order_checked_no.setChecked(true);
                }
            }

            if (pojo.getMedication_administration_documented() != null){
                medication_administration = pojo.getMedication_administration_documented();
                if (pojo.getMedication_administration_documented().equalsIgnoreCase("Yes")){
                    medication_administration_yes.setChecked(true);
                }else if (pojo.getMedication_administration_documented().equalsIgnoreCase("No")){
                    medication_administration_no.setChecked(true);
                }
            }

            if (pojo.getFridge_temperature_record() != null){
                fridge_temperature = pojo.getFridge_temperature_record();
                if (pojo.getFridge_temperature_record().equalsIgnoreCase("Yes")){
                    fridge_temperature_yes.setChecked(true);
                }else if (pojo.getFridge_temperature_record().equalsIgnoreCase("No")){
                    fridge_temperature_no.setChecked(true);
                }
            }


            if (pojo.getPatient_care_area_Remark() != null){
                remark_patient_care_area.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getPatient_care_area_Remark();
            }
            if (pojo.getPatient_care_area_NC() != null){
                nc1 = pojo.getPatient_care_area_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_patient_care_area.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_care_area.setImageResource(R.mipmap.nc_selected);
                }

            }
            if (pojo.getPatient_care_area_Image() != null){
                Image_patient_care_area.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getPatient_care_area_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            patientCareArea_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getLocal_patient_care_area_Image() != null){
                Image_patient_care_area.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_patient_care_area_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_patientCareArea_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getPharmacyStores_present_Remark() != null){
                remark2 = pojo.getPharmacyStores_present_Remark();

                remark_pharmacyStores_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPharmacyStores_present_NC() != null){
                nc2 = pojo.getPharmacyStores_present_NC();

                if (nc2.equalsIgnoreCase("close")){
                    nc_pharmacyStores_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_pharmacyStores_present.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getPharmacyStores_present_Image() != null){
                Image_pharmacyStores_present.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getPharmacyStores_present_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            pharmacyStores_present_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_pharmacyStores_present_Image() != null){

                Local_image2 = pojo.getLocal_pharmacyStores_present_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_pharmacyStores_present_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getExpired_drugs_remark() != null){
                remark3 = pojo.getExpired_drugs_remark();

                remark_drugs_pharmacy.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getExpired_drugs_nc() != null){
                nc3 = pojo.getExpired_drugs_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_drugs_pharmacy.setImageResource(R.mipmap.nc);
                }else {
                    nc_drugs_pharmacy.setImageResource(R.mipmap.nc_selected);
                }
            }
            if (pojo.getExpired_drugs_image() != null){
                image_drugs_pharmacy.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getExpired_drugs_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            drugs_pharmacy_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_expired_drugs_image() != null){
                image_drugs_pharmacy.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_expired_drugs_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_drugs_pharmacy_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getExpiry_date_checked_Remark() != null){
                remark4 = pojo.getExpiry_date_checked_Remark();

                remark_medication_expiry.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getExpiry_date_checked_NC() != null){
                nc4 = pojo.getExpiry_date_checked_NC();

                if (nc4.equalsIgnoreCase("close")){
                    nc_medication_expiry.setImageResource(R.mipmap.nc);
                }else {
                    nc_medication_expiry.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getEmergency_medications_Remark() != null){
                remark5 = pojo.getEmergency_medications_Remark();

                remark_emergency_medications.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getEmergency_medications_Nc() != null){
                nc5 = pojo.getEmergency_medications_Nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_emergency_medications.setImageResource(R.mipmap.nc);
                }else {
                    nc_emergency_medications.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getEmergency_medications_Image() != null){
                Image_emergency_medications.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getEmergency_medications_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            emergency_medications_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_emergency_medications_Image() != null){
                Image_emergency_medications.setImageResource(R.mipmap.camera_selected);

                Local_image5 = pojo.getLocal_emergency_medications_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_emergency_medications_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getRisk_medications_remark() != null){
                remark6 = pojo.getRisk_medications_remark();

                remark_high_risk_medications.setImageResource(R.mipmap.remark_selected);


            }
            if (pojo.getRisk_medications_nc() != null){
                nc6 = pojo.getRisk_medications_nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_high_risk_medications.setImageResource(R.mipmap.nc);
                }else {
                    nc_high_risk_medications.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getMedications_dispensing_remark() != null){
                remark7 = pojo.getMedications_dispensing_remark();

                remark_risk_medications_verified.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getMedications_dispensing_nc() != null){
                nc7 = pojo.getMedications_dispensing_nc();

                if (nc7.equalsIgnoreCase("close")){
                    nc_risk_medications_verified.setImageResource(R.mipmap.nc);
                }else {
                    nc_risk_medications_verified.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getLabelling_of_drug_remark() != null){
                remark8 = pojo.getLabelling_of_drug_remark();

                remark_labelling_of_drug.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getLabelling_of_drug_nc() != null){
                nc8 = pojo.getLabelling_of_drug_nc();

                if (nc8.equalsIgnoreCase("close")){
                    nc_labelling_of_drug.setImageResource(R.mipmap.nc);
                }else {
                    nc_labelling_of_drug.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getRisk_medications_image() != null){
                Image_high_risk_medications.setImageResource(R.mipmap.camera_selected);

                image6 = pojo.getRisk_medications_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            high_risk_medications_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_risk_medications_image() != null){
                Image_high_risk_medications.setImageResource(R.mipmap.camera_selected);

                Local_image6 = pojo.getLocal_risk_medications_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_high_risk_medications_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLabelling_of_drug_image() != null){
                Image_labelling_of_drug.setImageResource(R.mipmap.camera_selected);

                image8 = pojo.getLabelling_of_drug_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image8);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            labelling_of_drug_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (pojo.getLocal_labelling_of_drug_image() != null){
                Image_labelling_of_drug.setImageResource(R.mipmap.camera_selected);

                Local_image8 = pojo.getLocal_labelling_of_drug_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image8);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_labelling_of_drug_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getMedication_order_checked_remark() != null){
                remark9 = pojo.getMedication_order_checked_remark();

                remark_medication_order_checked.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getMedication_order_checked_nc() != null){
                nc9 = pojo.getMedication_order_checked_nc();

                if (nc9.equalsIgnoreCase("close")){
                    nc_medication_order_checked.setImageResource(R.mipmap.nc);
                }else {
                    nc_medication_order_checked.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getMedication_administration_documented_remark() != null){
                remark10 = pojo.getMedication_administration_documented_remark();

                remark_medication_administration.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getMedication_administration_documented_nc() != null){
                nc10 = pojo.getMedication_administration_documented_nc();


                if (nc10.equalsIgnoreCase("close")){
                    nc_medication_administration.setImageResource(R.mipmap.nc);
                }else {
                    nc_medication_administration.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getMedication_order_checked_image() != null){
                Image_medication_order_checked.setImageResource(R.mipmap.camera_selected);

                image9 = pojo.getMedication_order_checked_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image9);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            medication_order_checked_imagelist.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_medication_order_checked_image() != null){


                Local_image9 = pojo.getLocal_medication_order_checked_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image9);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_medication_order_checkedt_imagelist.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getMedication_administration_documented_image() != null){
                Image_medication_administration.setImageResource(R.mipmap.camera_selected);

                image10 = pojo.getMedication_administration_documented_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image10);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            medication_administration_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_medication_administration_documented_image() != null){
                Image_medication_administration.setImageResource(R.mipmap.camera_selected);

                Local_image10 = pojo.getLocal_medication_administration_documented_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image10);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_medication_administration_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getFridge_temperature_record_Remark() != null){
                remark11 = pojo.getFridge_temperature_record_Remark();

                remark_fridge_temperature.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getFridge_temperature_record_Nc() != null){
                nc11 = pojo.getFridge_temperature_record_Nc();

                if (nc11.equalsIgnoreCase("close")){
                    nc_fridge_temperature.setImageResource(R.mipmap.nc);
                }else {
                    nc_fridge_temperature.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getFridge_temperature_record_Image() != null){
                Image_fridge_temperature.setImageResource(R.mipmap.camera_selected);

                image11 = pojo.getFridge_temperature_record_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image11);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            fridge_temperature_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_fridge_temperature_record_Image() != null){
                Image_fridge_temperature.setImageResource(R.mipmap.camera_selected);

                Local_image11 = pojo.getLocal_fridge_temperature_record_Image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image11);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_fridge_temperature_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }else {
            pojo = new Wards_PharmacyPojo();
        }

    }


    @OnClick({R.id.remark_patient_care_area,R.id.nc_patient_care_area,R.id.Image_patient_care_area,R.id.remark_pharmacyStores_present,R.id.nc_pharmacyStores_present,R.id.Image_pharmacyStores_present,R.id.remark_drugs_pharmacy,R.id.nc_drugs_pharmacy,
            R.id.image_drugs_pharmacy,R.id.remark_medication_expiry,R.id.nc_medication_expiry,R.id.remark_emergency_medications,
            R.id.nc_emergency_medications,R.id.Image_emergency_medications,R.id.remark_high_risk_medications,R.id.nc_high_risk_medications,R.id.Image_high_risk_medications,
            R.id.remark_risk_medications_verified,R.id.nc_risk_medications_verified,R.id.remark_labelling_of_drug,R.id.nc_labelling_of_drug,R.id.Image_labelling_of_drug,
            R.id.remark_medication_order_checked,R.id.nc_medication_order_checked,R.id.remark_medication_administration,
            R.id.nc_medication_administration,R.id.Image_medication_administration,R.id.remark_fridge_temperature,R.id.nc_fridge_temperature,R.id.Image_fridge_temperature,R.id.btnSave,R.id.btnSync,
    R.id.Image_medication_order_checked})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_patient_care_area:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_patient_care_area:
                displayNCDialog("NC", 1);
                break;

            case R.id.Image_patient_care_area:
                if (Local_patientCareArea_imageList.size() > 0){
                    showImageListDialog(Local_patientCareArea_imageList,1,"patientCareArea");
                }else {
                    captureImage(1);
                }

                break;
            case R.id.remark_pharmacyStores_present:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_pharmacyStores_present:
                displayNCDialog("NC", 2);
                break;

            case R.id.Image_pharmacyStores_present:
                if (Local_pharmacyStores_present_imageList.size() > 0){
                    showImageListDialog(Local_pharmacyStores_present_imageList,2,"pharmacyStores_present");
                }else {
                    captureImage(2);
                }

                break;

            case R.id.remark_drugs_pharmacy:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_drugs_pharmacy:
                displayNCDialog("NC", 3);
                break;


            case R.id.image_drugs_pharmacy:
                if (Local_drugs_pharmacy_imageList.size() > 0){
                    showImageListDialog(Local_drugs_pharmacy_imageList,3,"drugs_pharmacy");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.remark_medication_expiry:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_medication_expiry:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_emergency_medications:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_emergency_medications:
                displayNCDialog("NC", 5);
                break;

            case R.id.Image_emergency_medications:
                if (Local_emergency_medications_imageList.size() > 0){
                    showImageListDialog(Local_emergency_medications_imageList,5,"emergency_medications");
                }else {
                    captureImage(5);
                }

                break;

            case R.id.remark_high_risk_medications:
                displayCommonDialogWithHeader("Remark", 6);
                break;

            case R.id.nc_high_risk_medications:
                displayNCDialog("NC", 6);
                break;

            case R.id.Image_high_risk_medications:
                if (Local_high_risk_medications_imageList.size() > 0){
                    showImageListDialog(Local_high_risk_medications_imageList,6,"high_risk_medications");
                }else {
                    captureImage(6);
                }

                break;

            case R.id.remark_risk_medications_verified:
                displayCommonDialogWithHeader("Remark", 7);
                break;

            case R.id.nc_risk_medications_verified:
                displayNCDialog("NC", 7);
                break;

            case R.id.remark_labelling_of_drug:
                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_labelling_of_drug:
                displayNCDialog("NC", 8);
                break;

            case R.id.Image_labelling_of_drug:
                if (Local_labelling_of_drug_imageList.size() > 0){
                    showImageListDialog(Local_labelling_of_drug_imageList,8,"labelling_of_drug");
                }else {
                    captureImage(8);
                }

                break;

            case R.id.remark_medication_order_checked:
                displayCommonDialogWithHeader("Remark", 9);
                break;

            case R.id.nc_medication_order_checked:
                displayNCDialog("NC", 9);
                break;

            case R.id.Image_medication_order_checked:

                if (Local_medication_order_checkedt_imagelist.size() > 0){
                    showImageListDialog(Local_medication_order_checkedt_imagelist,9,"medication_order_checked");
                }else {
                    captureImage(9);
                }
                break;


            case R.id.remark_medication_administration:
                displayCommonDialogWithHeader("Remark", 10);
                break;

            case R.id.nc_medication_administration:
                displayNCDialog("NC", 10);
                break;

            case R.id.Image_medication_administration:
                if (Local_medication_administration_imageList.size() > 0){
                    showImageListDialog(Local_medication_administration_imageList,10,"medication_administration");
                }else {
                    captureImage(10);
                }

                break;

            case R.id.remark_fridge_temperature:
                displayCommonDialogWithHeader("Remark", 11);
                break;

            case R.id.nc_fridge_temperature:
                displayNCDialog("NC", 11);
                break;

            case R.id.Image_fridge_temperature:
                if (Local_fridge_temperature_imageList.size() > 0){
                    showImageListDialog(Local_fridge_temperature_imageList,11,"fridge_temperature");
                }else {
                    captureImage(11);
                }

                break;


            case R.id.btnSave:
                SavePharmacyData("save","");
                break;

            case R.id.btnSync:

                if (Bed_no < 51){
                    if (patient_care_area.length() > 0 && pharmacyStores_present.length() > 0 && drugs_pharmacy.length() >0 && medication_expiry.length() > 0 &&
                            high_risk_medications.length() > 0 &&  labelling_of_drug.length() > 0 && medication_order_checked.length() > 0
                            && medication_administration.length() > 0 && fridge_temperature.length() > 0){

                        if (image1 != null && image2 != null && image3!= null && image6!= null
                                && image8!= null  && image10 != null && image11!= null){
                            SavePharmacyData("sync","shco");
                        }else {
                            Toast.makeText(PharmacyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(PharmacyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {
                    if (patient_care_area.length() > 0 && pharmacyStores_present.length() > 0 && drugs_pharmacy.length() >0 && medication_expiry.length() > 0 &&
                            emergency_medications.length() > 0 && high_risk_medications.length() > 0 && risk_medications_verified.length() > 0 && labelling_of_drug.length() > 0 && medication_order_checked.length() > 0
                            && medication_administration.length() > 0 && fridge_temperature.length() > 0){

                        if (image1 != null && image2 != null && image3!= null && image5!= null && image6!= null
                                && image8!= null  && image9 != null &&  image10 != null && image11!= null){
                            SavePharmacyData("sync","hco");
                        }else {
                            Toast.makeText(PharmacyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(PharmacyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }
                }

                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.patient_care_area_yes:
                if (checked)
                    patient_care_area = "Yes";
                break;

            case R.id.patient_care_area_no:
                if (checked)
                    patient_care_area = "No";
                break;

            case R.id.pharmacyStores_present_yes:
                pharmacyStores_present = "Yes";
                break;

            case R.id.pharmacyStores_present_no:
                pharmacyStores_present = "No";
                break;

            case R.id.drugs_pharmacy_yes:
                if (checked)
                    drugs_pharmacy = "Yes";
                break;
            case R.id.drugs_pharmacy_no:
                if (checked)
                    drugs_pharmacy = "No";
                break;


            case R.id.medication_expiry_yes:
                if (checked)
                    medication_expiry = "Yes";
                break;
            case R.id.medication_expiry_no:
                if (checked)
                    medication_expiry = "No";
                break;

            case R.id.emergency_medications_yes:
                emergency_medications = "Yes";
                break;

            case R.id.emergency_medications_no:
                emergency_medications = "No";
                break;


            case R.id.high_risk_medications_yes:
                high_risk_medications = "Yes";
                break;

            case R.id.high_risk_medications_no:
                high_risk_medications = "No";
                break;

            case R.id.risk_medications_verified_yes:
                risk_medications_verified = "Yes";
                break;

            case R.id.risk_medications_verified_no:
                risk_medications_verified = "No";
                break;

            case R.id.labelling_of_drug_yes:
                labelling_of_drug = "Yes";
                break;

            case R.id.labelling_of_drug_no:
                labelling_of_drug = "No";
                break;

            case R.id.medication_order_checked_yes:
                medication_order_checked = "Yes";
                break;

            case R.id.medication_order_checked_no:
                medication_order_checked = "No";
                break;

            case R.id.medication_administration_yes:
                medication_administration = "Yes";

                break;

            case R.id.medication_administration_no:
                medication_administration = "No";
                break;

            case R.id.fridge_temperature_yes:
                fridge_temperature = "Yes";
                break;

            case R.id.fridge_temperature_no:
                fridge_temperature = "No";
                break;


        }
    }


    private void captureImage(int CAMERA_REQUEST) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "bmh" + timeStamp + "_";
            File albumF = getAlbumDir();
            imageF = File.createTempFile(imageFileName, "bmh", albumF);
            picUri = Uri.fromFile(imageF);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageF));
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(PharmacyActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraPicture");
            } else {
                storageDir = new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + "CameraPicture");
            }

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        //		Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            //		Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
           if (requestCode == 1) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    ImageUpload(image2,"patient_care_area");

                }

            }
            else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                    ImageUpload(image3,"pharmacyStores_present");

                }

            }
           else if (requestCode == 3) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                   ImageUpload(image3,"drugs_pharmacy");

               }

           }
           else if (requestCode == 5) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                   ImageUpload(image3,"emergency_medications");

               }

           }
           else if (requestCode == 6) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                   ImageUpload(image3,"high_risk_medications");

               }

           }
           else if (requestCode == 8) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                   ImageUpload(image3,"labelling_of_drug");

               }

           }
           else if (requestCode == 9) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                   ImageUpload(image3,"medication_order_checked");

               }

           }
           else if (requestCode == 10) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                   ImageUpload(image3,"medication_administration");

               }

           }
           else if (requestCode == 11) {
               if (picUri != null) {
                   Uri uri = picUri;
                   String image3 = compressImage(uri.toString());
                   //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                   ImageUpload(image3,"fridge_temperature");

               }

           }

        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(PharmacyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        DialogLogOut.setContentView(R.layout.custom_nc_dialog);
        if (!DialogLogOut.isShowing()) {
            final EditText edit_text = (EditText) DialogLogOut.findViewById(R.id.text_exit);
            final RadioButton rd_major = (RadioButton) DialogLogOut.findViewById(R.id.radio_majer);
            final RadioButton rd_miner = (RadioButton) DialogLogOut.findViewById(R.id.radio_minor);
            TextView dialog_header = (TextView) DialogLogOut.findViewById(R.id.dialog_header);
            if (!header.equals(""))
                dialog_header.setVisibility(View.VISIBLE);
            dialog_header.setText(header);
            OkButtonLogout = (Button) DialogLogOut.findViewById(R.id.btn_yes_exit);


            rd_major.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 1) {
                        radio_status1 = "Yes";
                    }
                    if (position == 2) {
                        radio_status2 = "Yes";
                    }
                    if (position == 3) {
                        radio_status3 = "Yes";
                    }
                    if (position == 4) {
                        radio_status4 = "Yes";
                    }
                    if (position == 5) {
                        radio_status5 = "Yes";
                    }
                    if (position == 6) {
                        radio_status6 = "Yes";
                    }
                    if (position == 7) {
                        radio_status7 = "Yes";
                    }
                    if (position == 8) {
                        radio_status8 = "Yes";
                    }
                    if (position == 9) {
                        radio_status9 = "Yes";
                    }
                    if (position == 10) {
                        radio_status10 = "Yes";
                    }
                    if (position == 11) {
                        radio_status11 = "Yes";
                    }


                }
            });
            rd_miner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 1) {
                        radio_status1 = "close";

                        edit_text.setText("");
                    }
                    if (position == 2) {
                        radio_status2 = "close";

                        edit_text.setText("");
                    }
                    if (position == 3) {
                        radio_status3 = "close";

                        edit_text.setText("");
                    }
                    if (position == 4) {
                        radio_status4 = "close";

                        edit_text.setText("");
                    }
                    if (position == 5) {
                        radio_status5 = "close";

                        edit_text.setText("");
                    }
                    if (position == 6) {
                        radio_status6 = "close";

                        edit_text.setText("");
                    }
                    if (position == 7) {
                        radio_status7 = "close";

                        edit_text.setText("");
                    }
                    if (position == 8) {
                        radio_status8 = "close";

                        edit_text.setText("");
                    }
                    if (position == 9) {
                        radio_status9 = "close";

                        edit_text.setText("");
                    }
                    if (position == 10) {
                        radio_status10 = "close";

                        edit_text.setText("");
                    }
                    if (position == 11) {
                        radio_status11 = "close";

                        edit_text.setText("");
                    }

                }
            });

            if (position == 1) {
                if (nc1 != null) {
                    try {
                        String nc_total = nc1;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status1 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 2) {
                if (nc2 != null) {
                    try {
                        String nc_total = nc2;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status2 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 3) {
                if (nc3 != null) {
                    try {
                        String nc_total = nc3;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status3 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 4) {
                if (nc4 != null) {
                    try {
                        String nc_total = nc4;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status4 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 5) {
                if (nc5 != null) {
                    try {
                        String nc_total = nc5;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status5 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            if (position == 6) {
                if (nc6 != null) {
                    try {
                        String nc_total = nc6;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status6 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            if (position == 7) {
                if (nc7 != null) {
                    try {
                        String nc_total = nc7;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status7 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            if (position == 8) {
                if (nc8 != null) {
                    try {
                        String nc_total = nc8;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status8 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 9) {
                if (nc9 != null) {
                    try {
                        String nc_total = nc9;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status9 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 10) {
                if (nc10 != null) {
                    try {
                        String nc_total = nc10;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status10 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            if (position == 11) {
                if (nc11 != null) {
                    try {
                        String nc_total = nc11;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status11 = radio;
                        if (radio.equalsIgnoreCase("Yes"))
                            rd_major.setChecked(true);
                        else if (radio.equalsIgnoreCase("close"))
                            rd_miner.setChecked(true);
                        String nc = separated[1];
                        edit_text.setText(nc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            ImageView dialog_header_cross = (ImageView) DialogLogOut.findViewById(R.id.dialog_header_cross);
            dialog_header_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogLogOut.dismiss();
                }
            });

            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (radio_status1 != null) {

                            if (radio_status1.equalsIgnoreCase("close")) {
                                nc_patient_care_area.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_patient_care_area.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_pharmacyStores_present.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_pharmacyStores_present.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_drugs_pharmacy.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_drugs_pharmacy.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_medication_expiry.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_medication_expiry.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_emergency_medications.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_emergency_medications.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_high_risk_medications.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_high_risk_medications.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 7) {
                        if (radio_status7 != null) {

                            if (radio_status7.equalsIgnoreCase("close")) {

                                nc_risk_medications_verified.setImageResource(R.mipmap.nc);

                                nc7 = radio_status7;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status7.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_risk_medications_verified.setImageResource(R.mipmap.nc_selected);

                                    nc7 = radio_status7 + "," + edit_text.getText().toString();

                                    radio_status7 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_labelling_of_drug.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_labelling_of_drug.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 9) {
                        if (radio_status9 != null) {

                            if (radio_status9.equalsIgnoreCase("close")) {

                                nc_medication_order_checked.setImageResource(R.mipmap.nc);

                                nc9 = radio_status9;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status9.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_medication_order_checked.setImageResource(R.mipmap.nc_selected);

                                    nc9 = radio_status9 + "," + edit_text.getText().toString();

                                    radio_status9 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 10) {
                        if (radio_status10 != null) {

                            if (radio_status10.equalsIgnoreCase("close")) {

                                nc_medication_administration.setImageResource(R.mipmap.nc);

                                nc10 = radio_status10;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status10.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_medication_administration.setImageResource(R.mipmap.nc_selected);

                                    nc10 = radio_status10 + "," + edit_text.getText().toString();

                                    radio_status10 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 11) {
                        if (radio_status11 != null) {

                            if (radio_status11.equalsIgnoreCase("close")) {

                                nc_fridge_temperature.setImageResource(R.mipmap.nc);

                                nc11 = radio_status11;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status11.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_fridge_temperature.setImageResource(R.mipmap.nc_selected);

                                    nc11 = radio_status11 + "," + edit_text.getText().toString();

                                    radio_status11 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PharmacyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                }
            });
            DialogLogOut.show();
        }

    }


    public void displayCommonDialogWithHeader(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(PharmacyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        DialogLogOut.setContentView(R.layout.custom_dialog_small);
        if (!DialogLogOut.isShowing()) {
            final EditText edit_text = (EditText) DialogLogOut.findViewById(R.id.text_exit);
            TextView dialog_header = (TextView) DialogLogOut.findViewById(R.id.dialog_header);
            if (!header.equals(""))
                dialog_header.setVisibility(View.VISIBLE);
            dialog_header.setText(header);
            OkButtonLogout = (Button) DialogLogOut.findViewById(R.id.btn_yes_exit);

            ImageView dialog_header_cross = (ImageView) DialogLogOut.findViewById(R.id.dialog_header_cross);
            dialog_header_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogLogOut.dismiss();
                }
            });

            if (position == 1) {
                if (remark1 != null) {
                    edit_text.setText(remark1);
                }
            }
            if (position == 2) {
                if (remark2 != null) {
                    edit_text.setText(remark2);
                }
            }
            if (position == 3) {
                if (remark3 != null) {
                    edit_text.setText(remark3);
                }
            }
            if (position == 4) {
                if (remark4 != null) {
                    edit_text.setText(remark4);
                }
            }
            if (position == 5) {
                if (remark5 != null) {
                    edit_text.setText(remark5);
                }
            }

            if (position == 6) {
                if (remark6 != null) {
                    edit_text.setText(remark6);
                }
            }

            if (position == 7) {
                if (remark7 != null) {
                    edit_text.setText(remark7);
                }
            }

            if (position == 8) {
                if (remark8 != null) {
                    edit_text.setText(remark8);
                }
            }

            if (position == 9) {
                if (remark9 != null) {
                    edit_text.setText(remark9);
                }
            }

            if (position == 10) {
                if (remark10 != null) {
                    edit_text.setText(remark10);
                }
            }

            if (position == 11) {
                if (remark11 != null) {
                    edit_text.setText(remark11);
                }
            }

            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_patient_care_area.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_pharmacyStores_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_drugs_pharmacy.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_medication_expiry.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_emergency_medications.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_high_risk_medications.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 7) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark7 = edit_text.getText().toString();
                            remark_risk_medications_verified.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_labelling_of_drug.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 9) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark9 = edit_text.getText().toString();
                            remark_medication_order_checked.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 10) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark10 = edit_text.getText().toString();
                            remark_medication_administration.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 11) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark11 = edit_text.getText().toString();
                            remark_fridge_temperature.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PharmacyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(PharmacyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(PharmacyActivity.this,list,from,"Pharmacy");
        image_recycler_view.setAdapter(image_adapter);

        ll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });

        dialog_header_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });

        btn_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
                captureImage(position);
            }
        });
    }



    public void SavePharmacyData(String from,String hospital_status) {

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(7);
        if (getFromPrefs("Pharmacy_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("Pharmacy_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }

        pojo.setPatient_care_area(patient_care_area);
        pojo.setPharmacyStores_present(pharmacyStores_present);
        pojo.setExpired_drugs(drugs_pharmacy);
        pojo.setExpiry_date_checked(medication_expiry);
        pojo.setEmergency_medications(emergency_medications);
        pojo.setRisk_medications(high_risk_medications);
        pojo.setMedications_dispensing(risk_medications_verified);
        pojo.setLabelling_of_drug(labelling_of_drug);
        pojo.setMedication_order_checked(medication_order_checked);
        pojo.setMedication_administration_documented(medication_administration);
        pojo.setFridge_temperature_record(fridge_temperature);


        pojo.setPatient_care_area_Remark(remark1);
        pojo.setPatient_care_area_NC(nc1);


        JSONObject json = new JSONObject();

        if (patientCareArea_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(patientCareArea_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_patientCareArea_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_patientCareArea_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }

        pojo.setPatient_care_area_Image(image1);
        pojo.setLocal_patient_care_area_Image(Local_image1);

        pojo.setPharmacyStores_present_Remark(remark2);
        pojo.setPharmacyStores_present_NC(nc2);

        if (pharmacyStores_present_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(pharmacyStores_present_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_pharmacyStores_present_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_pharmacyStores_present_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }

        pojo.setPharmacyStores_present_Image(image2);
        pojo.setLocal_pharmacyStores_present_Image(Local_image2);

        pojo.setExpired_drugs_remark(remark3);
        pojo.setExpired_drugs_nc(nc3);


        if (drugs_pharmacy_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(drugs_pharmacy_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_drugs_pharmacy_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_drugs_pharmacy_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }


        pojo.setExpired_drugs_image(image3);
        pojo.setLocal_expired_drugs_image(Local_image3);


        pojo.setExpiry_date_checked_Remark(remark4);
        pojo.setExpiry_date_checked_NC(nc4);

        pojo.setEmergency_medications_Remark(remark5);
        pojo.setEmergency_medications_Nc(nc5);


        if (emergency_medications_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(emergency_medications_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image5 = json.toString();
        }else {
            image5 = null;
        }

        if (Local_emergency_medications_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_emergency_medications_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image5 = json.toString();
        }else {
            Local_image5 = null;
        }
        pojo.setEmergency_medications_Image(image5);
        pojo.setLocal_emergency_medications_Image(Local_image5);

        pojo.setRisk_medications_remark(remark6);
        pojo.setRisk_medications_nc(nc6);


        if (high_risk_medications_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(high_risk_medications_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image6 = json.toString();
        }else {
            image6 = null;
        }

        if (Local_high_risk_medications_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_high_risk_medications_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image6 = json.toString();
        }else {
            Local_image6 = null;
        }

        pojo.setRisk_medications_image(image6);
        pojo.setLocal_risk_medications_image(Local_image6);


        // Image_medication_administration


        pojo.setMedications_dispensing_remark(remark7);
        pojo.setMedications_dispensing_nc(nc7);

        pojo.setLabelling_of_drug_remark(remark8);
        pojo.setLabelling_of_drug_nc(nc8);


        if (labelling_of_drug_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(labelling_of_drug_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image8 = json.toString();
        }else {
            image8 = null;
        }

        if (Local_labelling_of_drug_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_labelling_of_drug_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image8 = json.toString();
        }else {
            Local_image8 = null;
        }
        pojo.setLabelling_of_drug_image(image8);
        pojo.setLocal_labelling_of_drug_image(Local_image8);

        pojo.setMedication_order_checked_remark(remark9);
        pojo.setMedication_order_checked_nc(nc9);

        if (medication_order_checked_imagelist.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(medication_order_checked_imagelist));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image9 = json.toString();
        }else {
            image9 = null;
        }

        if (Local_medication_order_checkedt_imagelist.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_medication_order_checkedt_imagelist));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image9 = json.toString();
        }else {
            Local_image9 = null;
        }

        pojo.setMedication_order_checked_image(image9);
        pojo.setLocal_medication_order_checked_image(Local_image9);

        pojo.setMedication_administration_documented_remark(remark10);
        pojo.setMedication_administration_documented_nc(nc10);


        if (medication_administration_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(medication_administration_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image10 = json.toString();
        }else {
            image10 = null;
        }

        if (Local_medication_administration_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_medication_administration_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image10 = json.toString();
        }else {
            Local_image10 = null;
        }

        pojo.setMedication_administration_documented_image(image10);
        pojo.setLocal_medication_administration_documented_image(Local_image10);

        pojo.setFridge_temperature_record_Remark(remark11);
        pojo.setFridge_temperature_record_Nc(nc11);

        if (fridge_temperature_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(fridge_temperature_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image11 = json.toString();
        }else {
            image11 = null;
        }

        if (Local_fridge_temperature_imageList.size() > 0) {
            try {
                json.put("uniqueArrays", new JSONArray(Local_fridge_temperature_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image11 = json.toString();
        }else {
            Local_image11 = null;
        }


        pojo.setFridge_temperature_record_Image(image11);
        pojo.setLocal_fridge_temperature_record_Image(Local_image11);

        if (sql_status) {
            boolean sq_status = databaseHandler.UPDATE_Ward_Pharmacy(pojo);

            if (sq_status){
                if (!from.equalsIgnoreCase("sync")) {

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(7).getHospital_id());
                    pojo.setAssessement_name("Wards and Pharmacy");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(7).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(PharmacyActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PharmacyActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        progreesDialog();

                        Post_SHCO_LaboratoryData();
                    }else {
                        progreesDialog();

                        PostLaboratoryData();
                    }
                }
            }
        } else {
            boolean sqe_status = databaseHandler.INSERT_Ward_Pharmacy(pojo);
            if (sqe_status){
                if (!from.equalsIgnoreCase("sync")) {

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(7).getHospital_id());
                    pojo.setAssessement_name("Wards and Pharmacy");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(7).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(PharmacyActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PharmacyActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        progreesDialog();

                        Post_SHCO_LaboratoryData();
                    }else {
                        progreesDialog();

                        PostLaboratoryData();
                    }
                }
            }

        }


    }
    private void PostLaboratoryData(){

           pojo_dataSync.setTabName("wardspharmacy");
           pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
           pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
           if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
               pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
           }else {
               pojo_dataSync.setAssessment_id(0);
           }

           for (int i=0;i< patientCareArea_imageList.size();i++){
               String value_patientCareArea = patientCareArea_imageList.get(i);

               patientCareArea = value_patientCareArea + patientCareArea;
           }
           pojo.setPatient_care_area_Image(patientCareArea);

           for (int i = 0; i< pharmacyStores_present_imageList.size();i++){
               String value_pharmacyStores_present = pharmacyStores_present_imageList.get(i);

               pharmacyStores_present_view = value_pharmacyStores_present + pharmacyStores_present_view;
           }

           pojo.setPharmacyStores_present_Image(pharmacyStores_present_view);

           for (int i = 0; i< drugs_pharmacy_imageList.size();i++){
               String value_drugs_pharmacy_view = drugs_pharmacy_imageList.get(i);

               drugs_pharmacy_view = value_drugs_pharmacy_view + drugs_pharmacy_view;
           }
           pojo.setExpired_drugs_image(drugs_pharmacy_view);

           for (int i = 0; i< emergency_medications_imageList.size();i++){
               String value_emergency_medications_view = emergency_medications_imageList.get(i);

               emergency_medications_view = value_emergency_medications_view + emergency_medications_view;
           }

           pojo.setEmergency_medications_Image(emergency_medications_view);

           for (int i = 0; i< high_risk_medications_imageList.size();i++){
               String value_high_risk_medications_imageList_view = high_risk_medications_imageList.get(i);

               high_risk_medications_imageList_view = value_high_risk_medications_imageList_view + high_risk_medications_imageList_view;
           }
           pojo.setRisk_medications_image(high_risk_medications_imageList_view);

           for (int i = 0; i< labelling_of_drug_imageList.size();i++){
               String value_labelling_of_drug_imageList_view = labelling_of_drug_imageList.get(i);

               labelling_of_drug_imageList_view = value_labelling_of_drug_imageList_view + labelling_of_drug_imageList_view;
           }
           pojo.setLabelling_of_drug_image(labelling_of_drug_imageList_view);

           for (int i=0;i<medication_order_checked_imagelist.size();i++){
               String value_medication_order_checked = medication_order_checked_imagelist.get(i);

               medication_order_checked_imageList_view = value_medication_order_checked + medication_order_checked_imageList_view;
           }

           pojo.setMedication_order_checked_image(medication_order_checked_imageList_view);

           for (int i = 0; i< medication_administration_imageList.size();i++){
               String value_medication_administration_image = medication_administration_imageList.get(i);

               medication_administration_imageView = value_medication_administration_image + medication_administration_imageView;
           }

           pojo.setMedication_administration_documented_image(medication_administration_imageView);

           for (int i = 0; i< fridge_temperature_imageList.size();i++){
               String value_fridge_temperature_View = fridge_temperature_imageList.get(i);

               fridge_temperature_View = value_fridge_temperature_View + fridge_temperature_View;
           }

           pojo.setFridge_temperature_record_Image(fridge_temperature_View);

           pojo_dataSync.setWardspharmacy(pojo);


           mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
               @Override
               public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                   System.out.println("xxx sucess");

                   CloseProgreesDialog();

                   if (response.message().equalsIgnoreCase("Unauthorized")) {
                       Intent intent = new Intent(PharmacyActivity.this, LoginActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       finish();

                       Toast.makeText(PharmacyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                   }else {
                       if (response.body() != null){
                           if (response.body().getSuccess()){
                               Intent intent = new Intent(PharmacyActivity.this,HospitalListActivity.class);
                               startActivity(intent);
                               finish();

                               saveIntoPrefs("Pharmacy_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                               saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                               assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                               AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                               pojo.setHospital_id(assessement_list.get(7).getHospital_id());
                               pojo.setAssessement_name("Wards and Pharmacy");
                               pojo.setAssessement_status("Done");
                               pojo.setLocal_id(assessement_list.get(7).getLocal_id());

                               databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                               Toast.makeText(PharmacyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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

    private void Post_SHCO_LaboratoryData(){

                pojo_dataSync.setTabName("wardspharmacy");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i< patientCareArea_imageList.size();i++){
                    String value_patientCareArea = patientCareArea_imageList.get(i);

                    patientCareArea = value_patientCareArea + patientCareArea;
                }
                pojo.setPatient_care_area_Image(patientCareArea);

                for (int i = 0; i< pharmacyStores_present_imageList.size();i++){
                    String value_pharmacyStores_present = pharmacyStores_present_imageList.get(i);

                    pharmacyStores_present_view = value_pharmacyStores_present + pharmacyStores_present_view;
                }

                pojo.setPharmacyStores_present_Image(pharmacyStores_present_view);

                for (int i = 0; i< drugs_pharmacy_imageList.size();i++){
                    String value_drugs_pharmacy_view = drugs_pharmacy_imageList.get(i);

                    drugs_pharmacy_view = value_drugs_pharmacy_view + drugs_pharmacy_view;
                }
                pojo.setExpired_drugs_image(drugs_pharmacy_view);

                for (int i = 0; i< emergency_medications_imageList.size();i++){
                    String value_emergency_medications_view = emergency_medications_imageList.get(i);

                    emergency_medications_view = value_emergency_medications_view + emergency_medications_view;
                }

                pojo.setEmergency_medications_Image(emergency_medications_view);

                for (int i = 0; i< high_risk_medications_imageList.size();i++){
                    String value_high_risk_medications_imageList_view = high_risk_medications_imageList.get(i);

                    high_risk_medications_imageList_view = value_high_risk_medications_imageList_view + high_risk_medications_imageList_view;
                }
                pojo.setRisk_medications_image(high_risk_medications_imageList_view);

                for (int i = 0; i< labelling_of_drug_imageList.size();i++){
                    String value_labelling_of_drug_imageList_view = labelling_of_drug_imageList.get(i);

                    labelling_of_drug_imageList_view = value_labelling_of_drug_imageList_view + labelling_of_drug_imageList_view;
                }
                pojo.setLabelling_of_drug_image(labelling_of_drug_imageList_view);

                for (int i=0;i<medication_order_checked_imagelist.size();i++){
                    String value_medication_order_checked = medication_order_checked_imagelist.get(i);

                    medication_order_checked_imageList_view = value_medication_order_checked + medication_order_checked_imageList_view;
                }

                pojo.setMedication_order_checked_image(medication_order_checked_imageList_view);

                for (int i = 0; i< medication_administration_imageList.size();i++){
                    String value_medication_administration_image = medication_administration_imageList.get(i);

                    medication_administration_imageView = value_medication_administration_image + medication_administration_imageView;
                }

                pojo.setMedication_administration_documented_image(medication_administration_imageView);

                for (int i = 0; i< fridge_temperature_imageList.size();i++){
                    String value_fridge_temperature_View = fridge_temperature_imageList.get(i);

                    fridge_temperature_View = value_fridge_temperature_View + fridge_temperature_View;
                }

                pojo.setFridge_temperature_record_Image(fridge_temperature_View);

                pojo_dataSync.setWardspharmacy(pojo);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(PharmacyActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(PharmacyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(PharmacyActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("Pharmacy_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(7).getHospital_id());
                                    pojo.setAssessement_name("Wards and Pharmacy");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(7).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(PharmacyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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

    private void ImageUpload(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        final ProgressDialog d = ImageDialog.showLoading(PharmacyActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(PharmacyActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(PharmacyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("patient_care_area")){
                                patientCareArea_imageList.add(response.body().getMessage());
                                Local_patientCareArea_imageList.add(image_path);
                                Image_patient_care_area.setImageResource(R.mipmap.camera_selected);

                                image1 = "patient_care_area";

                            }else if (from.equalsIgnoreCase("pharmacyStores_present")){
                                pharmacyStores_present_imageList.add(response.body().getMessage());
                                Local_pharmacyStores_present_imageList.add(image_path);
                                Image_pharmacyStores_present.setImageResource(R.mipmap.camera_selected);

                                image2 = "pharmacyStores_present";

                            }else if (from.equalsIgnoreCase("drugs_pharmacy")){

                                drugs_pharmacy_imageList.add(response.body().getMessage());
                                Local_drugs_pharmacy_imageList.add(image_path);
                                image_drugs_pharmacy.setImageResource(R.mipmap.camera_selected);

                                image3 = "drugs_pharmacy";
                            }
                            else if (from.equalsIgnoreCase("emergency_medications")){

                                emergency_medications_imageList.add(response.body().getMessage());
                                Local_emergency_medications_imageList.add(image_path);
                                Image_emergency_medications.setImageResource(R.mipmap.camera_selected);

                                image5 = "emergency_medications";

                            }
                            else if (from.equalsIgnoreCase("high_risk_medications")){

                                high_risk_medications_imageList.add(response.body().getMessage());
                                Local_high_risk_medications_imageList.add(image_path);
                                Image_high_risk_medications.setImageResource(R.mipmap.camera_selected);

                                image6 = "high_risk_medications";
                            }
                            else if (from.equalsIgnoreCase("labelling_of_drug")){

                                labelling_of_drug_imageList.add(response.body().getMessage());
                                Local_labelling_of_drug_imageList.add(image_path);
                                Image_labelling_of_drug.setImageResource(R.mipmap.camera_selected);

                                image8 = "labelling_of_drug";

                            }else if (from.equalsIgnoreCase("medication_order_checked")){
                                medication_order_checked_imagelist.add(response.body().getMessage());
                                Local_medication_order_checkedt_imagelist.add(image_path);

                                Image_medication_order_checked.setImageResource(R.mipmap.camera_selected);

                                image9 = "medication_order_checked";
                            }
                            else if (from.equalsIgnoreCase("medication_administration")){

                                medication_administration_imageList.add(response.body().getMessage());
                                Local_medication_administration_imageList.add(image_path);
                                Image_medication_administration.setImageResource(R.mipmap.camera_selected);

                                image10 = "medication_administration";

                            }else if (from.equalsIgnoreCase("fridge_temperature")){

                                fridge_temperature_imageList.add(response.body().getMessage());
                                Local_fridge_temperature_imageList.add(image_path);
                                Image_fridge_temperature.setImageResource(R.mipmap.camera_selected);

                                image11 = "fridge_temperature";
                            }


                            Toast.makeText(PharmacyActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(PharmacyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(PharmacyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(PharmacyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_delete:

                int pos = (int) view.getTag(R.string.key_image_delete);

                String from = (String)view.getTag(R.string.key_from_name);

                DeleteList(pos,from);

                break;
        }
    }


    private void DeleteList(int position,String from){
        try {
            if (from.equalsIgnoreCase("patientCareArea")){
                Local_patientCareArea_imageList.remove(position);
                patientCareArea_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patientCareArea_imageList.size() == 0){
                    Image_patient_care_area.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("pharmacyStores_present")){
                Local_pharmacyStores_present_imageList.remove(position);
                pharmacyStores_present_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_pharmacyStores_present_imageList.size() == 0){
                    Image_pharmacyStores_present.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("drugs_pharmacy")){
                Local_drugs_pharmacy_imageList.remove(position);
                drugs_pharmacy_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_drugs_pharmacy_imageList.size() == 0){
                    image_drugs_pharmacy.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("emergency_medications")){
                Local_emergency_medications_imageList.remove(position);
                emergency_medications_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_emergency_medications_imageList.size() == 0){
                    Image_emergency_medications.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("high_risk_medications")){
                Local_high_risk_medications_imageList.remove(position);
                high_risk_medications_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_high_risk_medications_imageList.size() == 0){
                    Image_high_risk_medications.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("labelling_of_drug")){
                Local_labelling_of_drug_imageList.remove(position);
                labelling_of_drug_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_labelling_of_drug_imageList.size() == 0){
                    Image_labelling_of_drug.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }

            else if (from.equalsIgnoreCase("medication_administration")){
                Local_medication_administration_imageList.remove(position);
                medication_administration_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_medication_administration_imageList.size() == 0){
                    Image_medication_administration.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("medication_order_checked")){
                Local_medication_order_checkedt_imagelist.remove(position);
                medication_order_checked_imagelist.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_medication_order_checkedt_imagelist.size() == 0){
                    Image_medication_order_checked.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }


            }
            else if (from.equalsIgnoreCase("fridge_temperature")){
                Local_fridge_temperature_imageList.remove(position);
                fridge_temperature_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_fridge_temperature_imageList.size() == 0){
                    Image_fridge_temperature.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!assessement_list.get(7).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save","");
        }else {
            Intent intent = new Intent(PharmacyActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
