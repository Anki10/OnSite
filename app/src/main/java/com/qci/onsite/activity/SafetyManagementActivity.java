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
import com.qci.onsite.pojo.FacilityChecksPojo;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.SafetManagementPojo;
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

public class SafetyManagementActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.safety_device_lab_yes)
    RadioButton safety_device_lab_yes;

    @BindView(R.id.safety_device_lab_no)
    RadioButton safety_device_lab_no;

    @BindView(R.id.remark_safety_device_lab)
    ImageView remark_safety_device_lab;

    @BindView(R.id.nc_safety_device_lab)
    ImageView nc_safety_device_lab;

    @BindView(R.id.image_safety_device_lab)
    ImageView image_safety_device_lab;

    @BindView(R.id.body_parts_staff_patients_yes)
    RadioButton body_parts_staff_patients_yes;

    @BindView(R.id.body_parts_staff_patients_no)
    RadioButton body_parts_staff_patients_no;

    @BindView(R.id.remark_body_parts_staff_patients)
    ImageView remark_body_parts_staff_patients;

    @BindView(R.id.nc_body_parts_staff_patients)
    ImageView nc_body_parts_staff_patients;

    @BindView(R.id.image_body_parts_staff_patients)
    ImageView image_body_parts_staff_patients;

    @BindView(R.id.staff_member_radiation_area_yes)
    RadioButton staff_member_radiation_area_yes;

    @BindView(R.id.staff_member_radiation_area_no)
    RadioButton staff_member_radiation_area_no;

    @BindView(R.id.remark_staff_member_radiation_area)
    ImageView remark_staff_member_radiation_area;

    @BindView(R.id.nc_staff_member_radiation_area)
    ImageView nc_staff_member_radiation_area;

    @BindView(R.id.image_staff_member_radiation_area)
    ImageView image_staff_member_radiation_area;

    @BindView(R.id.standardised_colur_coding_yes)
    RadioButton standardised_colur_coding_yes;

    @BindView(R.id.standardised_colur_coding_no)
    RadioButton standardised_colur_coding_no;

    @BindView(R.id.remark_standardised_colur_coding)
    ImageView remark_standardised_colur_coding;

    @BindView(R.id.nc_standardised_colur_coding)
    ImageView nc_standardised_colur_coding;

    @BindView(R.id.image_standardised_colur_coding)
    ImageView image_standardised_colur_coding;

    @BindView(R.id.safe_storage_medical_yes)
    RadioButton safe_storage_medical_yes;

    @BindView(R.id.safe_storage_medical_no)
    RadioButton safe_storage_medical_no;

    @BindView(R.id.remark_safe_storage_medical)
    ImageView remark_safe_storage_medical;

    @BindView(R.id.nc_safe_storage_medical)
    ImageView nc_safe_storage_medical;

    @BindView(R.id.image_safe_storage_medical)
    ImageView image_safe_storage_medical;

    @BindView(R.id.tv_safety_device)
    TextView tv_safety_device;

    @BindView(R.id.tv_body_parts_staff)
    TextView tv_body_parts_staff;

    int Bed_no = 0;


    private String remark1, remark2, remark3, remark4, remark5;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private ArrayList<String>safety_device_lab_list;
    private ArrayList<String>body_parts_staff_patients_list;
    private ArrayList<String>staff_member_radiation_area_list;
    private ArrayList<String>standardised_colur_coding_list;
    private ArrayList<String>safe_storage_medical_list;
    private ArrayList<String>Local_safety_device_lab_list;
    private ArrayList<String>Local_body_parts_staff_patients_list;
    private ArrayList<String>Local_staff_member_radiation_area_list;
    private ArrayList<String>Local_standardised_colur_coding_list;
    private ArrayList<String>Local_safe_storage_medical_list;


    private String nc1, nc2, nc3, nc4, nc5;
    private String radio_status1, radio_status2, radio_status3, radio_status4, radio_status5;

    private DatabaseHandler databaseHandler;

    private String image1,image2,image3,image4,image5;
    private String Local_image1,Local_image2,Local_image3,Local_image4,Local_image5;

    private File outputVideoFile;

    Uri videoUri;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;


    @BindView(R.id.laboratory_hospital_name)
    TextView laboratory_hospital_name;

    private String safety_device_lab_view = "",body_parts_staff_patients_view ="",staff_member_radiation_area_view = "",
            standardised_colur_coding_view = "",safe_storage_medical_view = "";

    private SafetManagementPojo pojo;

    private String safety_device_lab = "",body_parts_staff_patients = "",staff_member_radiation_area = "",
            standardised_colur_coding = "",safe_storage_medical = "";

    int check;
    CountDownLatch latch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_management);

        ButterKnife.bind(this);

        setDrawerbackIcon("Safety Management");

        safety_device_lab_list = new ArrayList<>();
        body_parts_staff_patients_list = new ArrayList<>();
        staff_member_radiation_area_list = new ArrayList<>();
        standardised_colur_coding_list = new ArrayList<>();
        safe_storage_medical_list = new ArrayList<>();

        Local_safety_device_lab_list = new ArrayList<>();
        Local_body_parts_staff_patients_list = new ArrayList<>();
        Local_staff_member_radiation_area_list = new ArrayList<>();
        Local_standardised_colur_coding_list = new ArrayList<>();
        Local_safe_storage_medical_list = new ArrayList<>();


        databaseHandler = DatabaseHandler.getInstance(this);

        pojo_dataSync = new DataSyncRequest();

        pojo = new SafetManagementPojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        laboratory_hospital_name.setText(getFromPrefs(AppConstant.Hospital_Name));

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            tv_safety_device.setVisibility(View.VISIBLE);
            tv_body_parts_staff.setVisibility(View.VISIBLE);

            image_body_parts_staff_patients.setVisibility(View.VISIBLE);

            image_safety_device_lab.setVisibility(View.VISIBLE);
        }else {
            tv_safety_device.setVisibility(View.GONE);
            tv_body_parts_staff.setVisibility(View.GONE);

            image_body_parts_staff_patients.setVisibility(View.GONE);

            image_safety_device_lab.setVisibility(View.GONE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);


        getLaboratoryData();

    }

    public void getLaboratoryData(){

        pojo = databaseHandler.getSafetManagement("17");

        if (pojo != null){
            sql_status = true;
            if (pojo.getSafety_device_lab() != null){
                safety_device_lab = pojo.getSafety_device_lab();
                if (pojo.getSafety_device_lab().equalsIgnoreCase("Yes")){
                    safety_device_lab_yes.setChecked(true);
                }else if (pojo.getSafety_device_lab().equalsIgnoreCase("No")){
                    safety_device_lab_no.setChecked(true);
                }
            }
            if (pojo.getBody_parts_staff_patients() != null){
                body_parts_staff_patients = pojo.getBody_parts_staff_patients();
                if (pojo.getBody_parts_staff_patients().equalsIgnoreCase("Yes")){
                    body_parts_staff_patients_yes.setChecked(true);
                }else if (pojo.getBody_parts_staff_patients().equalsIgnoreCase("No")){
                    body_parts_staff_patients_no.setChecked(true);
                }
            } if (pojo.getStaff_member_radiation_area() != null){
                staff_member_radiation_area = pojo.getStaff_member_radiation_area();
                if (pojo.getStaff_member_radiation_area().equalsIgnoreCase("Yes")){
                    staff_member_radiation_area_yes.setChecked(true);
                }else if (pojo.getStaff_member_radiation_area().equalsIgnoreCase("No")){
                    staff_member_radiation_area_no.setChecked(true);
                }
            } if (pojo.getStandardised_colur_coding() != null){
                standardised_colur_coding = pojo.getStandardised_colur_coding();
                if (pojo.getStandardised_colur_coding().equalsIgnoreCase("Yes")){
                    standardised_colur_coding_yes.setChecked(true);
                }else if (pojo.getStandardised_colur_coding().equalsIgnoreCase("No")){
                    standardised_colur_coding_no.setChecked(true);
                }
            } if (pojo.getSafe_storage_medical() != null){
                safe_storage_medical = pojo.getSafe_storage_medical();
                if (pojo.getSafe_storage_medical().equalsIgnoreCase("Yes")){
                    safe_storage_medical_yes.setChecked(true);
                }else if (pojo.getSafe_storage_medical().equalsIgnoreCase("No")){
                    safe_storage_medical_no.setChecked(true);
                }
            }

            if (pojo.getSafety_device_lab_remark() != null){
                remark_safety_device_lab.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getSafety_device_lab_remark();
            }
            if (pojo.getSafety_device_lab_nc() != null){
                nc1 = pojo.getSafety_device_lab_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_safety_device_lab.setImageResource(R.mipmap.nc);
                }else {
                    nc_safety_device_lab.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSafety_device_lab_image() != null){
                image_safety_device_lab.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getSafety_device_lab_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            safety_device_lab_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_safety_device_lab_image() != null){
                image_safety_device_lab.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_safety_device_lab_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_safety_device_lab_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getBody_parts_staff_patients_remark() != null){
                remark2 = pojo.getBody_parts_staff_patients_remark();

                remark_body_parts_staff_patients.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getBody_parts_staff_patients_nc() != null){
                nc2 = pojo.getBody_parts_staff_patients_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_body_parts_staff_patients.setImageResource(R.mipmap.nc);
                }else {
                    nc_body_parts_staff_patients.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getBody_parts_staff_patients_image() != null){
                image_body_parts_staff_patients.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getBody_parts_staff_patients_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            body_parts_staff_patients_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_body_parts_staff_patients_image() != null){
                image_body_parts_staff_patients.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_body_parts_staff_patients_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_body_parts_staff_patients_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getStaff_member_radiation_area_remark() != null){
                remark3 = pojo.getStaff_member_radiation_area_remark();

                remark_staff_member_radiation_area.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStaff_member_radiation_area_nc() != null){
                nc3 = pojo.getStaff_member_radiation_area_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_staff_member_radiation_area.setImageResource(R.mipmap.nc);
                }else {
                    nc_staff_member_radiation_area.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getStaff_member_radiation_area_image() != null){
                image_staff_member_radiation_area.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getStaff_member_radiation_area_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            staff_member_radiation_area_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_staff_member_radiation_area_image() != null){
                image_staff_member_radiation_area.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_staff_member_radiation_area_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_staff_member_radiation_area_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getStandardised_colur_coding_remark() != null){
                remark4 = pojo.getStandardised_colur_coding_remark();

                remark_standardised_colur_coding.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStandardised_colur_coding_nc() != null){
                nc4 = pojo.getStandardised_colur_coding_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_standardised_colur_coding.setImageResource(R.mipmap.nc);
                }else {
                    nc_standardised_colur_coding.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getStandardised_colur_coding_image() != null){
                image_standardised_colur_coding.setImageResource(R.mipmap.camera_selected);

                image4 = pojo.getStandardised_colur_coding_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            standardised_colur_coding_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_standardised_colur_coding_image() != null){
                image_standardised_colur_coding.setImageResource(R.mipmap.camera_selected);

                Local_image4 = pojo.getLocal_standardised_colur_coding_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_standardised_colur_coding_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            if (pojo.getSafe_storage_medical_remark() != null){
                remark5 = pojo.getSafe_storage_medical_remark();

                remark_safe_storage_medical.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getSafe_storage_medical_nc() != null){
                nc5 = pojo.getSafe_storage_medical_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_safe_storage_medical.setImageResource(R.mipmap.nc);
                }else {
                    nc_safe_storage_medical.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSafe_storage_medical_image() != null){
                image_safe_storage_medical.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getSafe_storage_medical_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            safe_storage_medical_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_safe_storage_medical_image() != null){
                image_safe_storage_medical.setImageResource(R.mipmap.camera_selected);

                Local_image5 = pojo.getLocal_safe_storage_medical_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_safe_storage_medical_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else {
            pojo = new SafetManagementPojo();
        }

    }



    @OnClick({R.id.remark_safety_device_lab,R.id.nc_safety_device_lab,R.id.image_safety_device_lab,R.id.remark_body_parts_staff_patients,R.id.nc_body_parts_staff_patients,
            R.id.image_body_parts_staff_patients,R.id.remark_staff_member_radiation_area,R.id.nc_staff_member_radiation_area,R.id.image_staff_member_radiation_area,R.id.remark_standardised_colur_coding,
            R.id.nc_standardised_colur_coding,R.id.image_standardised_colur_coding,R.id.remark_safe_storage_medical,R.id.nc_safe_storage_medical,R.id.image_safe_storage_medical,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_safety_device_lab:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_safety_device_lab:
                displayNCDialog("NC", 1);
                break;

            case R.id.image_safety_device_lab:
                if (Local_safety_device_lab_list.size() > 0){
                    showImageListDialog(Local_safety_device_lab_list,1,"safety_device_lab");
                }else {
                    captureImage(1);
                }

                break;

            case R.id.remark_body_parts_staff_patients:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_body_parts_staff_patients:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_body_parts_staff_patients:
                if (Local_body_parts_staff_patients_list.size() > 0){
                    showImageListDialog(Local_body_parts_staff_patients_list,2,"body_parts_staff_patients");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_staff_member_radiation_area:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_staff_member_radiation_area:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_staff_member_radiation_area:
                if (Local_staff_member_radiation_area_list.size() > 0){
                    showImageListDialog(Local_staff_member_radiation_area_list,3,"staff_member_radiation_area");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.remark_standardised_colur_coding:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_standardised_colur_coding:
                displayNCDialog("NC", 4);
                break;

            case R.id.image_standardised_colur_coding:
                if (Local_standardised_colur_coding_list.size() > 0){
                    showImageListDialog(Local_standardised_colur_coding_list,4,"standardised_colur_coding");
                }else {
                    captureImage(4);
                }
                break;


            case R.id.remark_safe_storage_medical:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_safe_storage_medical:
                displayNCDialog("NC", 5);
                break;

            case R.id.image_safe_storage_medical:
                if (Local_safe_storage_medical_list.size() > 0){
                    showImageListDialog(Local_safe_storage_medical_list,5,"safe_storage_medical");
                }else {
                    captureImage(5);
                }

                break;

            case R.id.btnSave:
                SaveLaboratoryData("save");
                break;

            case R.id.btnSync:

                if (safety_device_lab.length() > 0 && body_parts_staff_patients.length() > 0 && staff_member_radiation_area.length() > 0 && standardised_colur_coding.length() > 0
                        && safe_storage_medical.length() > 0){
                    if (Local_image3 != null && Local_image4 != null && Local_image5 != null){
                        SaveLaboratoryData("sync");
                    }else {
                        Toast.makeText(SafetyManagementActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(SafetyManagementActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }

                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.safety_device_lab_yes:
                if (checked)
                    safety_device_lab = "Yes";
                break;

            case R.id.safety_device_lab_no:
                if (checked)
                    safety_device_lab = "No";
                break;

            case R.id.body_parts_staff_patients_yes:
                if (checked)
                    body_parts_staff_patients = "Yes";
                break;
            case R.id.body_parts_staff_patients_no:
                if (checked)
                    body_parts_staff_patients = "No";
                break;


            case R.id.staff_member_radiation_area_yes:
                if (checked)
                    staff_member_radiation_area = "Yes";
                break;
            case R.id.staff_member_radiation_area_no:
                if (checked)
                    staff_member_radiation_area = "No";
                break;

            case R.id.standardised_colur_coding_yes:
                if (checked)
                    standardised_colur_coding = "Yes";
                break;
            case R.id.standardised_colur_coding_no:
                if (checked)
                    standardised_colur_coding = "No";
                break;

            case R.id.safe_storage_medical_yes:
                if (checked)
                    safe_storage_medical = "Yes";
                break;

            case R.id.safe_storage_medical_no:
                if (checked)
                    safe_storage_medical = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(SafetyManagementActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    SaveImage(image2, "safety_device_lab");


                }

            } else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                    SaveImage(image3, "body_parts_staff_patients");
                }

            } else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    SaveImage(image4, "staff_member_radiation_area");

                }
            } else if (requestCode == 4) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    SaveImage(image4, "standardised_colur_coding");

                }
            } else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    SaveImage(image4, "safe_storage_medical");

                }
            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(SafetyManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

                            if (radio_status1.equalsIgnoreCase("close")){
                                nc_safety_device_lab.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_safety_device_lab.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SafetyManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {

                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_body_parts_staff_patients.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_body_parts_staff_patients.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SafetyManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_staff_member_radiation_area.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_staff_member_radiation_area.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SafetyManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_standardised_colur_coding.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_standardised_colur_coding.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SafetyManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_safe_storage_medical.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_safe_storage_medical.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SafetyManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(SafetyManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_safety_device_lab.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SafetyManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_body_parts_staff_patients.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SafetyManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_staff_member_radiation_area.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SafetyManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_standardised_colur_coding.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SafetyManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_safe_storage_medical.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SafetyManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(SafetyManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(SafetyManagementActivity.this,list,from,"SafetyManagement");
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

        if(list.size()==2)
        {
            btn_add_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(SafetyManagementActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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


    public void SaveLaboratoryData(String from){
        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(17);
        pojo.setSafety_device_lab(safety_device_lab);
        pojo.setBody_parts_staff_patients(body_parts_staff_patients);
        pojo.setStaff_member_radiation_area(staff_member_radiation_area);
        pojo.setStandardised_colur_coding(standardised_colur_coding);
        pojo.setSafe_storage_medical(safe_storage_medical);

        JSONObject json = new JSONObject();


        if (safety_device_lab_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(safety_device_lab_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_safety_device_lab_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_safety_device_lab_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }


        pojo.setSafety_device_lab_remark(remark1);
        pojo.setSafety_device_lab_nc(nc1);
        pojo.setSafety_device_lab_image(image1);
        pojo.setLocal_safety_device_lab_image(Local_image1);


        if (body_parts_staff_patients_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(body_parts_staff_patients_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_body_parts_staff_patients_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_body_parts_staff_patients_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }


        pojo.setBody_parts_staff_patients_remark(remark2);
        pojo.setBody_parts_staff_patients_nc(nc2);
        pojo.setBody_parts_staff_patients_image(image2);
        pojo.setLocal_body_parts_staff_patients_image(Local_image2);

        if (staff_member_radiation_area_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(staff_member_radiation_area_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_staff_member_radiation_area_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_staff_member_radiation_area_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }


        pojo.setStaff_member_radiation_area_remark(remark3);
        pojo.setStaff_member_radiation_area_nc(nc3);
        pojo.setStaff_member_radiation_area_image(image3);
        pojo.setLocal_staff_member_radiation_area_image(Local_image3);

        if (standardised_colur_coding_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(standardised_colur_coding_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image4 = json.toString();
        }else {
            image4 = null;
        }

        if (Local_standardised_colur_coding_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_standardised_colur_coding_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image4 = json.toString();
        }else {
            Local_image4 = null;
        }



        pojo.setStandardised_colur_coding_remark(remark4);
        pojo.setStandardised_colur_coding_nc(nc4);
        pojo.setStandardised_colur_coding_image(image4);
        pojo.setLocal_standardised_colur_coding_image(Local_image4);

        if (safe_storage_medical_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(safe_storage_medical_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image5 = json.toString();
        }else {
            image5 = null;
        }

        if (Local_safe_storage_medical_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_safe_storage_medical_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image5 = json.toString();
        }else {
            Local_image5 = null;
        }



        pojo.setSafe_storage_medical_remark(remark5);
        pojo.setSafe_storage_medical_nc(nc5);
        pojo.setSafe_storage_medical_image(image5);
        pojo.setLocal_safe_storage_medical_image(Local_image5);


        if (sql_status){
            boolean sa_status = databaseHandler.UPDATE_SAFETY_MANAGEMENT(pojo);

            if (sa_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(17).getHospital_id());
                    pojo.setAssessement_name("Safety Management");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(17).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(SafetyManagementActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SafetyManagementActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    new PostDataTask().execute();
                }
            }
        }else {
            boolean sa_status = databaseHandler.INSERT_SAFETY_MANAGEMENT(pojo);

            if (sa_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(17).getHospital_id());
                    pojo.setAssessement_name("Safety Management");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(17).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(SafetyManagementActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SafetyManagementActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    new PostDataTask().execute();
                }
            }
        }



    }
    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("safety_device_lab")){
            //safety_device_lab_list.add(response.body().getMessage());
            Local_safety_device_lab_list.add(image_path);
            image_safety_device_lab.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "safety_device_lab";

        }else if (from.equalsIgnoreCase("body_parts_staff_patients")){
            //body_parts_staff_patients_list.add(response.body().getMessage());
            Local_body_parts_staff_patients_list.add(image_path);
            image_body_parts_staff_patients.setImageResource(R.mipmap.camera_selected);

            Local_image2 = "body_parts_staff_patients";
        }else if (from.equalsIgnoreCase("staff_member_radiation_area")){
            //staff_member_radiation_area_list.add(response.body().getMessage());
            Local_staff_member_radiation_area_list.add(image_path);
            image_staff_member_radiation_area.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "staff_member_radiation_area";

        }else if (from.equalsIgnoreCase("standardised_colur_coding")){
            //standardised_colur_coding_list.add(response.body().getMessage());
            Local_standardised_colur_coding_list.add(image_path);
            image_standardised_colur_coding.setImageResource(R.mipmap.camera_selected);

            Local_image4 = "standardised_colur_coding";

        }else if (from.equalsIgnoreCase("safe_storage_medical")){
            //safe_storage_medical_list.add(response.body().getMessage());
            Local_safe_storage_medical_list.add(image_path);
            image_safe_storage_medical.setImageResource(R.mipmap.camera_selected);

            Local_image5 = "safe_storage_medical";
        }

        Toast.makeText(SafetyManagementActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();
    }

    private void UploadImage(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                //d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SafetyManagementActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(SafetyManagementActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("safety_device_lab")){
                                safety_device_lab_list.add(response.body().getMessage());
                                //Local_safety_device_lab_list.add(image_path);
                                image_safety_device_lab.setImageResource(R.mipmap.camera_selected);

                                image1 = "safety_device_lab";

                            }else if (from.equalsIgnoreCase("body_parts_staff_patients")){
                                body_parts_staff_patients_list.add(response.body().getMessage());
                                //Local_body_parts_staff_patients_list.add(image_path);
                                image_body_parts_staff_patients.setImageResource(R.mipmap.camera_selected);

                                image2 = "body_parts_staff_patients";
                            }else if (from.equalsIgnoreCase("staff_member_radiation_area")){
                                staff_member_radiation_area_list.add(response.body().getMessage());
                                //Local_staff_member_radiation_area_list.add(image_path);
                                image_staff_member_radiation_area.setImageResource(R.mipmap.camera_selected);

                                image3 = "staff_member_radiation_area";

                            }else if (from.equalsIgnoreCase("standardised_colur_coding")){
                                standardised_colur_coding_list.add(response.body().getMessage());
                                //Local_standardised_colur_coding_list.add(image_path);
                                image_standardised_colur_coding.setImageResource(R.mipmap.camera_selected);

                                image4 = "standardised_colur_coding";

                            }else if (from.equalsIgnoreCase("safe_storage_medical")){
                                safe_storage_medical_list.add(response.body().getMessage());
                                //Local_safe_storage_medical_list.add(image_path);
                                image_safe_storage_medical.setImageResource(R.mipmap.camera_selected);

                                image5 = "safe_storage_medical";
                            }
                            check = 1;
                            latch.countDown();
                            //Toast.makeText(SafetyManagementActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(SafetyManagementActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(SafetyManagementActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");
                check = 0;
                latch.countDown();
            }
        });
    }

    private void PostLaboratoryData(){
        check = 1;
        for(int i=safety_device_lab_list.size(); i<Local_safety_device_lab_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_safety_device_lab_list.get(i) + "safety_device_lab");
            UploadImage(Local_safety_device_lab_list.get(i),"safety_device_lab");
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
                        Toast.makeText(SafetyManagementActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SafetyManagementActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i=body_parts_staff_patients_list.size(); i<Local_body_parts_staff_patients_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_body_parts_staff_patients_list.get(i) + "body_parts_staff_patients");
            UploadImage(Local_body_parts_staff_patients_list.get(i),"body_parts_staff_patients");
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
                        Toast.makeText(SafetyManagementActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SafetyManagementActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i=staff_member_radiation_area_list.size(); i<Local_staff_member_radiation_area_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_staff_member_radiation_area_list.get(i) + "staff_member_radiation_area");
            UploadImage(Local_staff_member_radiation_area_list.get(i),"staff_member_radiation_area");
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
                        Toast.makeText(SafetyManagementActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SafetyManagementActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i=standardised_colur_coding_list.size(); i<Local_standardised_colur_coding_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_standardised_colur_coding_list.get(i) + "standardised_colur_coding");
            UploadImage(Local_standardised_colur_coding_list.get(i),"standardised_colur_coding");
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
                        Toast.makeText(SafetyManagementActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SafetyManagementActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i=safe_storage_medical_list.size(); i<Local_safe_storage_medical_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_safe_storage_medical_list.get(i) + "safe_storage_medical");
            UploadImage(Local_safe_storage_medical_list.get(i),"safe_storage_medical");
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
                        Toast.makeText(SafetyManagementActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SafetyManagementActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

                pojo_dataSync.setTabName("safetymanagement");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<safety_device_lab_list.size();i++){
                    String value_rail = safety_device_lab_list.get(i);

                    safety_device_lab_view = value_rail + safety_device_lab_view;
                }
                pojo.setSafety_device_lab_image(safety_device_lab_view);

                for (int i=0;i<body_parts_staff_patients_list.size();i++){
                    String value_transported = body_parts_staff_patients_list.get(i);

                    body_parts_staff_patients_view = value_transported + body_parts_staff_patients_view;
                }
                pojo.setBody_parts_staff_patients_image(body_parts_staff_patients_view);

                for (int i=0;i<staff_member_radiation_area_list.size();i++){
                    String value_specimen = staff_member_radiation_area_list.get(i);

                    staff_member_radiation_area_view = value_specimen + staff_member_radiation_area_view;
                }

                pojo.setStaff_member_radiation_area_image(staff_member_radiation_area_view);

                for (int i=0;i<standardised_colur_coding_list.size();i++){
                    String value_specimen = standardised_colur_coding_list.get(i);

                    standardised_colur_coding_view = value_specimen + standardised_colur_coding_view;
                }
                pojo.setStandardised_colur_coding_image(standardised_colur_coding_view);


                for (int i=0;i<safe_storage_medical_list.size();i++){
                    String value_specimen = safe_storage_medical_list.get(i);

                    safe_storage_medical_view = value_specimen + safe_storage_medical_view;
                }
                pojo.setSafe_storage_medical_image(safe_storage_medical_view);


                pojo_dataSync.setSafetymanagement(pojo);

                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SafetyManagementActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(SafetyManagementActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(SafetyManagementActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("Laboratory_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(17).getHospital_id());
                                    pojo.setAssessement_name("Safety Management");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(17).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SafetyManagementActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("safety_device_lab")){
                safety_device_lab_list.remove(position);
                Local_safety_device_lab_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_safety_device_lab_list.size() == 0){
                    image_safety_device_lab.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("body_parts_staff_patients")){
                body_parts_staff_patients_list.remove(position);
                Local_body_parts_staff_patients_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_body_parts_staff_patients_list.size() == 0){
                    image_body_parts_staff_patients.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("staff_member_radiation_area")){
                staff_member_radiation_area_list.remove(position);
                Local_staff_member_radiation_area_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_staff_member_radiation_area_list.size() == 0){
                    image_staff_member_radiation_area.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }
            }
            else if (from.equalsIgnoreCase("standardised_colur_coding")){
                standardised_colur_coding_list.remove(position);
                Local_standardised_colur_coding_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_standardised_colur_coding_list.size() == 0){
                    image_standardised_colur_coding.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }
            }
            else if (from.equalsIgnoreCase("safe_storage_medical")){
                safe_storage_medical_list.remove(position);
                Local_safe_storage_medical_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_safe_storage_medical_list.size() == 0){
                    image_safe_storage_medical.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(17).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("save");
        }else {
            Intent intent = new Intent(SafetyManagementActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
