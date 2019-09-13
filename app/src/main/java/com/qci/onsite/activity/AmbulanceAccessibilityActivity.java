package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import com.qci.onsite.pojo.AmbulanceAccessibilityPojo;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.ManagementPojo;
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
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbulanceAccessibilityActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ambulance_patient_drop_vehicle_access)
    RadioButton ambulance_patient_drop_vehicle_access;

    @BindView(R.id.ambulance_patient_drop_vehicle_drop)
    RadioButton ambulance_patient_drop_vehicle_drop;

    @BindView(R.id.ambulance_patient_drop_vehicle_shifted)
    RadioButton ambulance_patient_drop_vehicle_shifted;

    @BindView(R.id.remark_access_ambulance_patient_drop)
    ImageView remark_access_ambulance_patient_drop;

    @BindView(R.id.nc_access_ambulance_patient_drop)
    ImageView nc_access_ambulance_patient_drop;

    @BindView(R.id.ownership_the_ambulance_organization)
    RadioButton ownership_the_ambulance_organization;

    @BindView(R.id.ownership_the_ambulance_Outsourced)
    RadioButton ownership_the_ambulance_Outsourced;

    @BindView(R.id.remark_ownership_the_ambulance)
    ImageView remark_ownership_the_ambulance;

    @BindView(R.id.nc_ownership_the_ambulance)
    ImageView nc_ownership_the_ambulance;

    @BindView(R.id.ed_total_number_ambulance_available)
    EditText ed_total_number_ambulance_available;

    @BindView(R.id.remark_total_number_ambulance_available)
    ImageView remark_total_number_ambulance_available;

    @BindView(R.id.nc_total_number_ambulance_available)
    ImageView nc_total_number_ambulance_available;

    @BindView(R.id.image_total_number_ambulance_available)
    ImageView image_total_number_ambulance_available;

    @BindView(R.id.ambulance_appropriately_equiped_yes)
    RadioButton ambulance_appropriately_equiped_yes;

    @BindView(R.id.ambulance_appropriately_equiped_no)
    RadioButton ambulance_appropriately_equiped_no;

    @BindView(R.id.remark_ambulance_appropriately_equiped)
    ImageView remark_ambulance_appropriately_equiped;

    @BindView(R.id.nc_ambulance_appropriately_equiped)
    ImageView nc_ambulance_appropriately_equiped;

    @BindView(R.id.image_ambulance_appropriately_equiped)
    ImageView image_ambulance_appropriately_equiped;

    @BindView(R.id.ed_drivers_ambulances_available)
    EditText ed_drivers_ambulances_available;

    @BindView(R.id.remark_drivers_ambulances_available)
    ImageView remark_drivers_ambulances_available;

    @BindView(R.id.nc_drivers_ambulances_available)
    ImageView nc_drivers_ambulances_available;

    @BindView(R.id.image_drivers_ambulances_available)
    ImageView image_drivers_ambulances_available;

    @BindView(R.id.ed_doctors_available_ambulances)
    EditText ed_doctors_available_ambulances;

    @BindView(R.id.remark_doctors_available_ambulances)
    ImageView remark_doctors_available_ambulances;

    @BindView(R.id.nc_doctors_available_ambulances)
    ImageView nc_doctors_available_ambulances;

    @BindView(R.id.image_doctors_available_ambulances)
    ImageView image_doctors_available_ambulances;

    @BindView(R.id.ed_nurses_available_ambulances)
    EditText ed_nurses_available_ambulances;

    @BindView(R.id.remark_nurses_available_ambulances)
    ImageView remark_nurses_available_ambulances;

    @BindView(R.id.nc_nurses_available_ambulances)
    ImageView nc_nurses_available_ambulances;

    @BindView(R.id.image_nurses_available_ambulances)
    ImageView image_nurses_available_ambulances;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    @BindView(R.id.ll_ownership_the_ambulance)
    LinearLayout ll_ownership_the_ambulance;

    @BindView(R.id.ll_ambulance_available)
    LinearLayout ll_ambulance_available;

    @BindView(R.id.ll_appropriately_equiped)
    LinearLayout ll_appropriately_equiped;

    @BindView(R.id.ll_driver_ambulances_available)
    LinearLayout ll_driver_ambulances_available;

    @BindView(R.id.ll_doctor_available)
    LinearLayout ll_doctor_available;

    @BindView(R.id.ll_nurses_available)
    LinearLayout ll_nurses_available;

    int Bed_no = 0;





    private String remark1, remark2, remark3,remark4,remark5,remark6,remark8;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5,nc6,nc8;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status6,radio_status8;

    private DatabaseHandler databaseHandler;

    private APIService mAPIService;

    private String ambulance_patient_drop = "",ownership_the_ambulance ="",ambulance_appropriately_equiped="";


    private ArrayList<String>hospital_mission_present_list;
    private ArrayList<String>Local_hospital_mission_present_list;

    private ArrayList<String>patient_maintained_OPD_list;
    private ArrayList<String>Local_patient_maintained_OPD_list;

    private ArrayList<String>patient_maintained_IPD_list;
    private ArrayList<String>Local_patient_maintained_IPD_list;

    private ArrayList<String>patient_maintained_Emergency_list;
    private ArrayList<String>Local_patient_maintained_Emergency_list;


    private ArrayList<String>nurses_available_ambulances_list;
    private ArrayList<String>Local_nurses_available_ambulances_list;

    private String hospital_mission_present_view = "",patient_maintained_OPD_view = "",patient_maintained_IPD_view = "",
            patient_maintained_Emergency_view = "",basic_Tariff_List_view = "",nurses_available_ambulances_view = "";

    private AmbulanceAccessibilityPojo pojo;

    private String image3,image4,image5,image6,image8;
    private String Local_image3,Local_image4,Local_image5,Local_image6,Local_image8;


    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    Uri videoUri;

    public Boolean sql_status = false;

    DataSyncRequest pojo_dataSync;

    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_accessibility);

        ButterKnife.bind(this);

        setDrawerbackIcon("Ambulance Accessibility");

        mAPIService = ApiUtils.getAPIService();

        databaseHandler = DatabaseHandler.getInstance(this);

        hospital_mission_present_list = new ArrayList<>();
        Local_hospital_mission_present_list = new ArrayList<>();

        patient_maintained_OPD_list = new ArrayList<>();
        Local_patient_maintained_OPD_list = new ArrayList<>();

        patient_maintained_IPD_list = new ArrayList<>();
        Local_patient_maintained_IPD_list = new ArrayList<>();

        patient_maintained_Emergency_list = new ArrayList<>();
        Local_patient_maintained_Emergency_list = new ArrayList<>();

        nurses_available_ambulances_list = new ArrayList<>();
        Local_nurses_available_ambulances_list = new ArrayList<>();



        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        pojo = new AmbulanceAccessibilityPojo();

        Bed_no = getINTFromPrefs("Hospital_bed");


        if (Bed_no < 51){
            ll_ownership_the_ambulance.setVisibility(View.GONE);
            ll_ambulance_available.setVisibility(View.GONE);
            ll_appropriately_equiped.setVisibility(View.GONE);
            ll_driver_ambulances_available.setVisibility(View.GONE);
            ll_doctor_available.setVisibility(View.GONE);
            ll_nurses_available.setVisibility(View.GONE);
        }else {
            ll_ownership_the_ambulance.setVisibility(View.VISIBLE);
            ll_ambulance_available.setVisibility(View.VISIBLE);
            ll_appropriately_equiped.setVisibility(View.VISIBLE);
            ll_driver_ambulances_available.setVisibility(View.VISIBLE);
            ll_doctor_available.setVisibility(View.VISIBLE);
            ll_nurses_available.setVisibility(View.VISIBLE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();

    }

    public void getPharmacyData(){

        pojo = databaseHandler.getAmbulanceAccessibilityPojo("18");

        if (pojo != null){
            sql_status = true;
            if (pojo.getAccess_ambulance_patient_drop() != null){
                ambulance_patient_drop = pojo.getAccess_ambulance_patient_drop();
                if (pojo.getAccess_ambulance_patient_drop().equalsIgnoreCase("Vehicle can access the compound area")){
                    ambulance_patient_drop_vehicle_access.setChecked(true);
                }else if (pojo.getAccess_ambulance_patient_drop().equalsIgnoreCase("Vehicle can drop patient up to the door/lift of the premises")){
                    ambulance_patient_drop_vehicle_drop.setChecked(true);
                }else if (pojo.getAccess_ambulance_patient_drop().equalsIgnoreCase("Patient can be shifted on trolley directly from ambulance to emergency/ward bed")){
                    ambulance_patient_drop_vehicle_shifted.setChecked(true);
                }
            }
            if (pojo.getOwnership_the_ambulance() != null){
                ownership_the_ambulance = pojo.getOwnership_the_ambulance();
                if (pojo.getOwnership_the_ambulance().equalsIgnoreCase("Owned by organization")){
                    ownership_the_ambulance_organization.setChecked(true);
                }else if (pojo.getOwnership_the_ambulance().equalsIgnoreCase("Outsourced ")){
                    ownership_the_ambulance_Outsourced.setChecked(true);
                }
            }
            if (pojo.getTotal_number_ambulance_available() != null){
                ed_total_number_ambulance_available.setText(pojo.getTotal_number_ambulance_available());

            } if (pojo.getAmbulance_appropriately_equiped() != null){
                ambulance_appropriately_equiped = pojo.getAmbulance_appropriately_equiped();
                if (pojo.getAmbulance_appropriately_equiped().equalsIgnoreCase("Yes")){
                    ambulance_appropriately_equiped_yes.setChecked(true);
                }else if (pojo.getAmbulance_appropriately_equiped().equalsIgnoreCase("No")){
                    ambulance_appropriately_equiped_no.setChecked(true);
                }
            }
            if (pojo.getDrivers_ambulances_available() != null){
                ed_drivers_ambulances_available.setText(pojo.getDrivers_ambulances_available());

            }

            if (pojo.getDoctors_available_ambulances() != null){
                ed_doctors_available_ambulances.setText(pojo.getDoctors_available_ambulances());

            }


            if (pojo.getNurses_available_ambulances() != null){
                ed_nurses_available_ambulances.setText(pojo.getNurses_available_ambulances());

            }

            if (pojo.getAccess_ambulance_patient_drop_remark() != null){
                remark_access_ambulance_patient_drop.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getAccess_ambulance_patient_drop_remark();
            }
            if (pojo.getAccess_ambulance_patient_drop_nc() != null){
                nc1 = pojo.getAccess_ambulance_patient_drop_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_access_ambulance_patient_drop.setImageResource(R.mipmap.nc);
                }else {
                    nc_access_ambulance_patient_drop.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getOwnership_the_ambulance_remark() != null){
                remark2 = pojo.getOwnership_the_ambulance_remark();

                remark_ownership_the_ambulance.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getOwnership_the_ambulance_nc() != null){
                nc2 = pojo.getOwnership_the_ambulance_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_ownership_the_ambulance.setImageResource(R.mipmap.nc);
                }else {
                    nc_ownership_the_ambulance.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getTotal_number_ambulance_available_remark() != null){
                remark3 = pojo.getTotal_number_ambulance_available_remark();

                remark_total_number_ambulance_available.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getTotal_number_ambulance_available_nc() != null){
                nc3 = pojo.getTotal_number_ambulance_available_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_total_number_ambulance_available.setImageResource(R.mipmap.nc);
                }else {
                    nc_total_number_ambulance_available.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getTotal_number_ambulance_available_image() != null){
                image_total_number_ambulance_available.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getTotal_number_ambulance_available_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            hospital_mission_present_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_total_number_ambulance_available_image() != null){
                image_total_number_ambulance_available.setImageResource(R.mipmap.camera_selected);
                Local_image3 = pojo.getLocal_total_number_ambulance_available_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_hospital_mission_present_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getAmbulance_appropriately_equiped_remark() != null){
                remark4 = pojo.getAmbulance_appropriately_equiped_remark();

                remark_ambulance_appropriately_equiped.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getAmbulance_appropriately_equiped_nc() != null){
                nc4 = pojo.getAmbulance_appropriately_equiped_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_ambulance_appropriately_equiped.setImageResource(R.mipmap.nc);
                }else {
                    nc_ambulance_appropriately_equiped.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getAmbulance_appropriately_equiped_image() != null){

                image_ambulance_appropriately_equiped.setImageResource(R.mipmap.camera_selected);

                image4 = pojo.getAmbulance_appropriately_equiped_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            patient_maintained_OPD_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_ambulance_appropriately_equiped_image() != null){
                image_ambulance_appropriately_equiped.setImageResource(R.mipmap.camera_selected);

                Local_image4 = pojo.getLocal_ambulance_appropriately_equiped_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_patient_maintained_OPD_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getDrivers_ambulances_available_remark() != null){
                remark5 = pojo.getDrivers_ambulances_available_remark();

                remark_drivers_ambulances_available.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDrivers_ambulances_available_nc() != null){
                nc5 = pojo.getDrivers_ambulances_available_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_drivers_ambulances_available.setImageResource(R.mipmap.nc);
                }else {
                    nc_drivers_ambulances_available.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDrivers_ambulances_available_image() != null){
                image_drivers_ambulances_available.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getDrivers_ambulances_available_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            patient_maintained_IPD_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getLocal_drivers_ambulances_available_image() != null){
                image_drivers_ambulances_available.setImageResource(R.mipmap.camera_selected);

                Local_image5 = pojo.getLocal_drivers_ambulances_available_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_patient_maintained_IPD_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getDoctors_available_ambulances_remark() != null){
                remark6 = pojo.getDoctors_available_ambulances_remark();

                remark_doctors_available_ambulances.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDoctors_available_ambulances_nc() != null){
                nc6 = pojo.getDoctors_available_ambulances_nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_doctors_available_ambulances.setImageResource(R.mipmap.nc);
                }else {
                    nc_doctors_available_ambulances.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDoctors_available_ambulances_image() != null){
                image_doctors_available_ambulances.setImageResource(R.mipmap.camera_selected);

                image6 = pojo.getDoctors_available_ambulances_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            patient_maintained_Emergency_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_doctors_available_ambulances_image() != null){
                image_doctors_available_ambulances.setImageResource(R.mipmap.camera_selected);

                Local_image6 = pojo.getLocal_doctors_available_ambulances_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_patient_maintained_Emergency_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getNurses_available_ambulances_remark() != null){
                remark8 = pojo.getNurses_available_ambulances_remark();

                remark_nurses_available_ambulances.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNurses_available_ambulances_nc() != null){
                nc8 = pojo.getNurses_available_ambulances_nc();

                if (nc8.equalsIgnoreCase("close")){
                    nc_nurses_available_ambulances.setImageResource(R.mipmap.nc);
                }else {
                    nc_nurses_available_ambulances.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getNurses_available_ambulances_image() != null){
                image_nurses_available_ambulances.setImageResource(R.mipmap.camera_selected);

                image8 = pojo.getNurses_available_ambulances_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image8);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            nurses_available_ambulances_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_nurses_available_ambulances_image() != null){
                image_nurses_available_ambulances.setImageResource(R.mipmap.camera_selected);

                Local_image8 = pojo.getLocal_nurses_available_ambulances_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image8);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_nurses_available_ambulances_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else {
            pojo = new AmbulanceAccessibilityPojo();
        }

    }


    @OnClick({R.id.remark_access_ambulance_patient_drop,R.id.nc_access_ambulance_patient_drop,R.id.remark_ownership_the_ambulance,R.id.nc_ownership_the_ambulance,R.id.remark_total_number_ambulance_available,R.id.nc_total_number_ambulance_available,
            R.id.image_total_number_ambulance_available,R.id.remark_ambulance_appropriately_equiped,R.id.nc_ambulance_appropriately_equiped,
            R.id.image_ambulance_appropriately_equiped,R.id.remark_drivers_ambulances_available, R.id.nc_drivers_ambulances_available,R.id.image_drivers_ambulances_available,R.id.remark_doctors_available_ambulances,R.id.nc_doctors_available_ambulances,
            R.id.image_doctors_available_ambulances,R.id.remark_nurses_available_ambulances,
            R.id.nc_nurses_available_ambulances,R.id.image_nurses_available_ambulances,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_access_ambulance_patient_drop:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_access_ambulance_patient_drop:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_ownership_the_ambulance:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_ownership_the_ambulance:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_total_number_ambulance_available:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_total_number_ambulance_available:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_total_number_ambulance_available:
                if (Local_hospital_mission_present_list.size() > 0){
                    showImageListDialog(Local_hospital_mission_present_list,3,"hospital_mission_present");
                }else {
                    captureImage(3);
                }
                break;


            case R.id.remark_ambulance_appropriately_equiped:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_ambulance_appropriately_equiped:
                displayNCDialog("NC", 4);
                break;

            case R.id.image_ambulance_appropriately_equiped:
                if (Local_patient_maintained_OPD_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_OPD_list,4,"patient_maintained_OPD");
                }else {
                    captureImage(4);
                }
                break;


            case R.id.remark_drivers_ambulances_available:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_drivers_ambulances_available:
                displayNCDialog("NC", 5);
                break;

            case R.id.image_drivers_ambulances_available:
                if (Local_patient_maintained_IPD_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_IPD_list,5,"patient_maintained_IPD");
                }else {
                    captureImage(5);
                }
                break;

            case R.id.remark_doctors_available_ambulances:
                displayCommonDialogWithHeader("Remark", 6);
                break;

            case R.id.nc_doctors_available_ambulances:
                displayNCDialog("NC", 6);
                break;

            case R.id.image_doctors_available_ambulances:
                if (Local_patient_maintained_Emergency_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_Emergency_list,6,"patient_maintained_Emergency");
                }else {
                    captureImage(6);
                }
                break;



            case R.id.remark_nurses_available_ambulances:
                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_nurses_available_ambulances:
                displayNCDialog("NC", 8);
                break;

            case R.id.image_nurses_available_ambulances:

                if (Local_nurses_available_ambulances_list.size() > 0){
                    showImageListDialog(Local_nurses_available_ambulances_list,8,"nurses_available_ambulances");
                }else {
                    captureImage(8);
                }
                break;

            case R.id.btnSave:
                SavePharmacyData("save","");
                break;

            case R.id.btnSync:
                if (Bed_no < 51){
                    if (ambulance_patient_drop.length() > 0 ){
                        SavePharmacyData("sync","shco");
                    }else {
                        Toast.makeText(AmbulanceAccessibilityActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {

                    if (ambulance_patient_drop.length() > 0 && ownership_the_ambulance.length() > 0 && ambulance_appropriately_equiped.length() > 0 && ed_total_number_ambulance_available.getText().toString().length() > 0
                            && ed_drivers_ambulances_available.getText().toString().length() > 0 && ed_doctors_available_ambulances.getText().toString().length() > 0 && ed_nurses_available_ambulances.getText().toString().length() > 0){

                        if (Local_image3 != null && Local_image4 != null && Local_image5 != null && Local_image6 != null && Local_image8 != null){
                            SavePharmacyData("sync","hco");
                        }else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(AmbulanceAccessibilityActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

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
            case R.id.ambulance_patient_drop_vehicle_access:
                if (checked)
                    ambulance_patient_drop = "Vehicle can access the compound area";
                break;

            case R.id.ambulance_patient_drop_vehicle_drop:
                if (checked)
                    ambulance_patient_drop = "Vehicle can drop patient up to the door/lift of the premises";
                break;

            case R.id.ambulance_patient_drop_vehicle_shifted:
                if (checked)
                    ambulance_patient_drop = "Patient can be shifted on trolley directly from ambulance to emergency/ward bed";
                break;

            case R.id.ownership_the_ambulance_organization:
                ownership_the_ambulance = "Owned by organization";
                break;

            case R.id.ownership_the_ambulance_Outsourced:
                ownership_the_ambulance = "Outsourced ";
                break;

            case R.id.ambulance_appropriately_equiped_yes:
                if (checked)
                    ambulance_appropriately_equiped = "Yes";
                break;
            case R.id.ambulance_appropriately_equiped_no:
                if (checked)
                    ambulance_appropriately_equiped = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(AmbulanceAccessibilityActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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
            if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"hospital_mission_present");

                }

            } else if (requestCode == 4) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"patient_maintained_OPD");

                }
            }else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"patient_maintained_IPD");

                }
            }else if (requestCode == 6) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"patient_maintained_Emergency");

                }
            }else if (requestCode == 7) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"basic_Tariff_List");

                }
            }else if (requestCode == 8){
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"nurses_available_ambulances");

                }

            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(AmbulanceAccessibilityActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                    if (position == 8) {
                        radio_status8 = "Yes";
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
                    if (position == 8) {
                        radio_status8 = "close";

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
                                nc_access_ambulance_patient_drop.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_access_ambulance_patient_drop.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_ownership_the_ambulance.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_ownership_the_ambulance.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_total_number_ambulance_available.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_total_number_ambulance_available.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_ambulance_appropriately_equiped.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_ambulance_appropriately_equiped.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_drivers_ambulances_available.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_drivers_ambulances_available.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_doctors_available_ambulances.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_doctors_available_ambulances.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }



                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_nurses_available_ambulances.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_nurses_available_ambulances.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(AmbulanceAccessibilityActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

            if (position == 8) {
                if (remark8 != null) {
                    edit_text.setText(remark8);
                }
            }



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_access_ambulance_patient_drop.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_ownership_the_ambulance.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_total_number_ambulance_available.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_ambulance_appropriately_equiped.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_drivers_ambulances_available.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_doctors_available_ambulances.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_nurses_available_ambulances.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(AmbulanceAccessibilityActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(AmbulanceAccessibilityActivity.this,list,from,"AmbulanceAccessibilty");
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

        if(list.size()==2)
        {
            btn_add_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(AmbulanceAccessibilityActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
        else
        {
            btn_add_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLogout.dismiss();
                    captureImage(position);
                }
            });
        }
    }


    public void SavePharmacyData(String from,String hospital_status){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(18);
        pojo.setAccess_ambulance_patient_drop(ambulance_patient_drop);
        pojo.setOwnership_the_ambulance(ownership_the_ambulance);
        pojo.setTotal_number_ambulance_available(ed_total_number_ambulance_available.getText().toString());
        pojo.setAmbulance_appropriately_equiped(ambulance_appropriately_equiped);
        pojo.setDrivers_ambulances_available(ed_drivers_ambulances_available.getText().toString());
        pojo.setDoctors_available_ambulances(ed_doctors_available_ambulances.getText().toString());
        pojo.setNurses_available_ambulances(ed_nurses_available_ambulances.getText().toString());


        pojo.setAccess_ambulance_patient_drop_remark(remark1);
        pojo.setAccess_ambulance_patient_drop_nc(nc1);

        pojo.setOwnership_the_ambulance_remark(remark2);
        pojo.setOwnership_the_ambulance_nc(nc2);


        pojo.setTotal_number_ambulance_available_remark(remark3);
        pojo.setTotal_number_ambulance_available_nc(nc3);

        JSONObject json = new JSONObject();

        if (hospital_mission_present_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(hospital_mission_present_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_hospital_mission_present_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_hospital_mission_present_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }

        pojo.setTotal_number_ambulance_available_image(image3);
        pojo.setLocal_total_number_ambulance_available_image(Local_image3);

        pojo.setAmbulance_appropriately_equiped_remark(remark4);
        pojo.setAmbulance_appropriately_equiped_nc(nc4);

        if (patient_maintained_OPD_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(patient_maintained_OPD_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image4 = json.toString();
        }else {
            image4 = null;
        }

        if (Local_patient_maintained_OPD_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_patient_maintained_OPD_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image4 = json.toString();
        }else {
            Local_image4 = null;
        }
        pojo.setAmbulance_appropriately_equiped_image(image4);
        pojo.setLocal_ambulance_appropriately_equiped_image(Local_image4);


        pojo.setDrivers_ambulances_available_remark(remark5);
        pojo.setDrivers_ambulances_available_nc(nc5);

        if (patient_maintained_IPD_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(patient_maintained_IPD_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image5 = json.toString();
        }else {
            image5 = null;
        }

        if (Local_patient_maintained_IPD_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_patient_maintained_IPD_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image5 = json.toString();
        }else {
            Local_image5 = null;
        }
        pojo.setDrivers_ambulances_available_image(image5);
        pojo.setLocal_drivers_ambulances_available_image(Local_image5);



        pojo.setDoctors_available_ambulances_remark(remark6);
        pojo.setDoctors_available_ambulances_nc(nc6);

        if (patient_maintained_Emergency_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(patient_maintained_Emergency_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image6 = json.toString();
        }else {
            image6 = null;
        }

        if (Local_patient_maintained_Emergency_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_patient_maintained_Emergency_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image6 = json.toString();
        }else {
            Local_image6 = null;
        }

        pojo.setDoctors_available_ambulances_image(image6);
        pojo.setLocal_doctors_available_ambulances_image(Local_image6);

        pojo.setNurses_available_ambulances_remark(remark8);
        pojo.setNurses_available_ambulances_nc(nc8);

        if (nurses_available_ambulances_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(nurses_available_ambulances_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image8 = json.toString();
        }else {
            image8 = null;
        }

        if (Local_nurses_available_ambulances_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_nurses_available_ambulances_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image8 = json.toString();
        }else {
            Local_image8 = null;
        }

        pojo.setNurses_available_ambulances_image(image8);
        pojo.setLocal_nurses_available_ambulances_image(Local_image8);


        if (sql_status){
            boolean st_status = databaseHandler.UPDATE_Ambulance_Accessibility(pojo);

            if (st_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(18).getHospital_id());
                    pojo.setAssessement_name("Ambulance Accessibility");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(18).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(AmbulanceAccessibilityActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AmbulanceAccessibilityActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                }  else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        //progreesDialog();

                        new PostSHCODataTask().execute();
                    }else {
                        //progreesDialog();

                        new PostDataTask().execute();
                    }
                }
            }
        }else {
            boolean st_status = databaseHandler.INSERT_Ambulance_Accessibility(pojo);

            if (st_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(18).getHospital_id());
                    pojo.setAssessement_name("Ambulance Accessibility");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(18).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(AmbulanceAccessibilityActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AmbulanceAccessibilityActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        //progreesDialog();

                        new PostSHCODataTask().execute();
                    }else {
                        //progreesDialog();

                        new PostDataTask().execute();
                    }
                }
            }
        }


    }

    private class PostDataTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog d;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreesDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostLaboratoryData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CloseProgreesDialog();
        }
    }

    private void PostLaboratoryData(){

        check =1;
        for(int i = hospital_mission_present_list.size() ; i<  Local_hospital_mission_present_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_hospital_mission_present_list.get(i)+"hospital_mission_present");
            UploadImage(Local_hospital_mission_present_list.get(i),"hospital_mission_present");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = patient_maintained_OPD_list.size() ; i<  Local_patient_maintained_OPD_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_OPD_list.get(i)+"patient_maintained_OPD");
            UploadImage(Local_patient_maintained_OPD_list.get(i),"patient_maintained_OPD");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = patient_maintained_IPD_list.size() ; i<  Local_patient_maintained_IPD_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_IPD_list.get(i)+"patient_maintained_IPD");
            UploadImage(Local_patient_maintained_IPD_list.get(i),"patient_maintained_IPD");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = patient_maintained_Emergency_list.size() ; i<  Local_patient_maintained_Emergency_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_Emergency_list.get(i)+"patient_maintained_Emergency");
            UploadImage(Local_patient_maintained_Emergency_list.get(i),"patient_maintained_Emergency");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = nurses_available_ambulances_list.size() ; i<  Local_nurses_available_ambulances_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_nurses_available_ambulances_list.get(i)+"nurses_available_ambulances");
            UploadImage(Local_nurses_available_ambulances_list.get(i),"nurses_available_ambulances");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

              pojo_dataSync.setTabName("AmbulanceAccessibility");
              pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
              pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
              if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                  pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
              }else {
                  pojo_dataSync.setAssessment_id(0);
              }

             pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));


              for (int i=0;i<hospital_mission_present_list.size();i++){
                  String value_rail = hospital_mission_present_list.get(i);

                  hospital_mission_present_view = value_rail + hospital_mission_present_view;
              }
              pojo.setTotal_number_ambulance_available_image(hospital_mission_present_view);

              for (int i=0;i<patient_maintained_OPD_list.size();i++){
                  String value_transported = patient_maintained_OPD_list.get(i);

                  patient_maintained_OPD_view = value_transported + patient_maintained_OPD_view;
              }
              pojo.setAmbulance_appropriately_equiped_image(patient_maintained_OPD_view);

              for (int i=0;i<patient_maintained_IPD_list.size();i++){
                  String value_specimen = patient_maintained_IPD_list.get(i);

                  patient_maintained_IPD_view = value_specimen + patient_maintained_IPD_view;
              }

              pojo.setDrivers_ambulances_available_image(patient_maintained_IPD_view);

              for (int i=0;i<patient_maintained_Emergency_list.size();i++){
                  String value_specimen = patient_maintained_Emergency_list.get(i);

                  patient_maintained_Emergency_view = value_specimen + patient_maintained_Emergency_view;
              }
              pojo.setDoctors_available_ambulances_image(patient_maintained_Emergency_view);


              for (int i=0;i<nurses_available_ambulances_list.size();i++){
                  String value_specimen = nurses_available_ambulances_list.get(i);

                  nurses_available_ambulances_view = value_specimen + nurses_available_ambulances_view;
              }
              pojo.setNurses_available_ambulances_image(nurses_available_ambulances_view);


              pojo_dataSync.setAmbulanceaccessibility(pojo);

            latch = new CountDownLatch(1);
            check = 0;
              mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                  @Override
                  public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                      System.out.println("xxx sucess");

                     //CloseProgreesDialog();

                      if (response.message().equalsIgnoreCase("Unauthorized")) {
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Intent intent = new Intent(AmbulanceAccessibilityActivity.this, LoginActivity.class);
                                  startActivity(intent);
                                  finish();

                                  Toast.makeText(AmbulanceAccessibilityActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                              }
                          });
                      }else {
                          if (response.body() != null){
                              if (response.body().getSuccess()){
                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(AmbulanceAccessibilityActivity.this,HospitalListActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  });

                                  saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                  assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                  AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                  pojo.setHospital_id(assessement_list.get(18).getHospital_id());
                                  pojo.setAssessement_name("Ambulance Accessibility");
                                  pojo.setAssessement_status("Done");
                                  pojo.setLocal_id(assessement_list.get(18).getLocal_id());

                                  databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          Toast.makeText(AmbulanceAccessibilityActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
                                      }
                                  });
                              }

                          }
                          latch.countDown();
                      }
                  }

                  @Override
                  public void onFailure(Call<DataSyncResponse> call, Throwable t) {
                      System.out.println("xxx failed");
                      latch.countDown();
                      //CloseProgreesDialog();
                  }
              });
        try {
            latch.await();
        }
        catch(Exception e)
        {
            Log.e("Upload",e.getMessage());
        }
    }

    private class PostSHCODataTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog d;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreesDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Post_SHCO_LaboratoryData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CloseProgreesDialog();
        }
    }

    private void Post_SHCO_LaboratoryData(){
        check =1;
        for(int i = hospital_mission_present_list.size() ; i<  Local_hospital_mission_present_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_hospital_mission_present_list.get(i)+"hospital_mission_present");
            UploadImage(Local_hospital_mission_present_list.get(i),"hospital_mission_present");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = patient_maintained_OPD_list.size() ; i<  Local_patient_maintained_OPD_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_OPD_list.get(i)+"patient_maintained_OPD");
            UploadImage(Local_patient_maintained_OPD_list.get(i),"patient_maintained_OPD");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = patient_maintained_IPD_list.size() ; i<  Local_patient_maintained_IPD_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_IPD_list.get(i)+"patient_maintained_IPD");
            UploadImage(Local_patient_maintained_IPD_list.get(i),"patient_maintained_IPD");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = patient_maintained_Emergency_list.size() ; i<  Local_patient_maintained_Emergency_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_patient_maintained_Emergency_list.get(i)+"patient_maintained_Emergency");
            UploadImage(Local_patient_maintained_Emergency_list.get(i),"patient_maintained_Emergency");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = nurses_available_ambulances_list.size() ; i<  Local_nurses_available_ambulances_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_nurses_available_ambulances_list.get(i)+"nurses_available_ambulances");
            UploadImage(Local_nurses_available_ambulances_list.get(i),"nurses_available_ambulances");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AmbulanceAccessibilityActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("AmbulanceAccessibility");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }
               pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));

                for (int i=0;i<hospital_mission_present_list.size();i++){
                    String value_rail = hospital_mission_present_list.get(i);

                    hospital_mission_present_view = value_rail + hospital_mission_present_view;
                }
                pojo.setTotal_number_ambulance_available_image(hospital_mission_present_view);

                for (int i=0;i<patient_maintained_OPD_list.size();i++){
                    String value_transported = patient_maintained_OPD_list.get(i);

                    patient_maintained_OPD_view = value_transported + patient_maintained_OPD_view;
                }
                pojo.setAmbulance_appropriately_equiped_image(patient_maintained_OPD_view);

                for (int i=0;i<patient_maintained_IPD_list.size();i++){
                    String value_specimen = patient_maintained_IPD_list.get(i);

                    patient_maintained_IPD_view = value_specimen + patient_maintained_IPD_view;
                }

                pojo.setDrivers_ambulances_available_image(patient_maintained_IPD_view);

                for (int i=0;i<patient_maintained_Emergency_list.size();i++){
                    String value_specimen = patient_maintained_Emergency_list.get(i);

                    patient_maintained_Emergency_view = value_specimen + patient_maintained_Emergency_view;
                }
                pojo.setDoctors_available_ambulances_image(patient_maintained_Emergency_view);


                for (int i=0;i<nurses_available_ambulances_list.size();i++){
                    String value_specimen = nurses_available_ambulances_list.get(i);

                    nurses_available_ambulances_view = value_specimen + nurses_available_ambulances_view;
                }
                pojo.setNurses_available_ambulances_image(nurses_available_ambulances_view);


                pojo_dataSync.setAmbulanceaccessibility(pojo);
                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(AmbulanceAccessibilityActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(AmbulanceAccessibilityActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(AmbulanceAccessibilityActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });


                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(18).getHospital_id());
                                    pojo.setAssessement_name("Ambulance Accessibility");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(18).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AmbulanceAccessibilityActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                            }
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataSyncResponse> call, Throwable t) {
                        System.out.println("xxx failed");
                        latch.countDown();
                        //CloseProgreesDialog();
                    }
                });
        try {
            latch.await();
        }
        catch(Exception e)
        {
            Log.e("Upload",e.getMessage());
        }

    }



    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("hospital_mission_present")){
            //hospital_mission_present_list.add(response.body().getMessage());
            Local_hospital_mission_present_list.add(image_path);
            image_total_number_ambulance_available.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "hospital_mission_present";

        }else if (from.equalsIgnoreCase("patient_maintained_OPD")){
            //patient_maintained_OPD_list.add(response.body().getMessage());
            Local_patient_maintained_OPD_list.add(image_path);
            image_ambulance_appropriately_equiped.setImageResource(R.mipmap.camera_selected);

            Local_image4 = "patient_maintained_OPD";

        }else if (from.equalsIgnoreCase("patient_maintained_IPD")){
            //patient_maintained_IPD_list.add(response.body().getMessage());
            Local_patient_maintained_IPD_list.add(image_path);
            image_drivers_ambulances_available.setImageResource(R.mipmap.camera_selected);

            Local_image5 = "patient_maintained_IPD";

        }else if (from.equalsIgnoreCase("patient_maintained_Emergency")){
            //patient_maintained_Emergency_list.add(response.body().getMessage());
            Local_patient_maintained_Emergency_list.add(image_path);
            image_doctors_available_ambulances.setImageResource(R.mipmap.camera_selected);

            Local_image6 = "patient_maintained_Emergency";

        }else if (from.equalsIgnoreCase("nurses_available_ambulances")){
            //nurses_available_ambulances_list.add(response.body().getMessage());
            Local_nurses_available_ambulances_list.add(image_path);
            image_nurses_available_ambulances.setImageResource(R.mipmap.camera_selected);

            Local_image8= "nurses_available_ambulances";
        }

        Toast.makeText(AmbulanceAccessibilityActivity.this,"Image Saved Locally",Toast.LENGTH_LONG).show();
    }

    private void UploadImage(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        //final ProgressDialog d = ImageDialog.showLoading(AmbulanceAccessibilityActivity.this);
        //d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                //d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(AmbulanceAccessibilityActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(AmbulanceAccessibilityActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("hospital_mission_present")){
                                hospital_mission_present_list.add(response.body().getMessage());
                                //Local_hospital_mission_present_list.add(image_path);
                                image_total_number_ambulance_available.setImageResource(R.mipmap.camera_selected);

                                image3 = "hospital_mission_present";

                            }else if (from.equalsIgnoreCase("patient_maintained_OPD")){
                                patient_maintained_OPD_list.add(response.body().getMessage());
                                //Local_patient_maintained_OPD_list.add(image_path);
                                image_ambulance_appropriately_equiped.setImageResource(R.mipmap.camera_selected);

                                image4 = "patient_maintained_OPD";

                            }else if (from.equalsIgnoreCase("patient_maintained_IPD")){
                                patient_maintained_IPD_list.add(response.body().getMessage());
                                //Local_patient_maintained_IPD_list.add(image_path);
                                image_drivers_ambulances_available.setImageResource(R.mipmap.camera_selected);

                                image5 = "patient_maintained_IPD";

                            }else if (from.equalsIgnoreCase("patient_maintained_Emergency")){
                                patient_maintained_Emergency_list.add(response.body().getMessage());
                               // Local_patient_maintained_Emergency_list.add(image_path);
                                image_doctors_available_ambulances.setImageResource(R.mipmap.camera_selected);

                                image6 = "patient_maintained_Emergency";

                            }else if (from.equalsIgnoreCase("nurses_available_ambulances")){
                                nurses_available_ambulances_list.add(response.body().getMessage());
                                //Local_nurses_available_ambulances_list.add(image_path);
                                image_nurses_available_ambulances.setImageResource(R.mipmap.camera_selected);

                                image8= "nurses_available_ambulances";
                            }
                            check = 1;
                            latch.countDown();
                            //Toast.makeText(AmbulanceAccessibilityActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(AmbulanceAccessibilityActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(AmbulanceAccessibilityActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                //d.cancel();
                check = 0;
                latch.countDown();
                //Toast.makeText(AmbulanceAccessibilityActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("hospital_mission_present")){
                Local_hospital_mission_present_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_hospital_mission_present_list.size() == 0){
                    image_total_number_ambulance_available.setImageResource(R.mipmap.camera);

                    Local_image3 = null;

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("patient_maintained_OPD")){
                Local_patient_maintained_OPD_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_OPD_list.size() == 0){
                    image_ambulance_appropriately_equiped.setImageResource(R.mipmap.camera);

                    Local_image4 = null;

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("patient_maintained_IPD")){
                Local_patient_maintained_IPD_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_IPD_list.size() == 0){
                    image_drivers_ambulances_available.setImageResource(R.mipmap.camera);

                    Local_image5 = null;

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("patient_maintained_Emergency")){
                Local_patient_maintained_Emergency_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_Emergency_list.size() == 0){
                    image_doctors_available_ambulances.setImageResource(R.mipmap.camera);

                    Local_image6 = null;

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("nurses_available_ambulances")){
                Local_nurses_available_ambulances_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_nurses_available_ambulances_list.size() == 0){
                    image_nurses_available_ambulances.setImageResource(R.mipmap.camera);

                    Local_image8 = null;

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

        if (!assessement_list.get(18).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save","");
        }else {
            Intent intent = new Intent(AmbulanceAccessibilityActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
