package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.SafetManagementPojo;
import com.qci.onsite.pojo.UniformSignagePojo;
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

public class UniformSignageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.scope_services_present_yes)
    RadioButton scope_services_present_yes;

    @BindView(R.id.scope_services_present_no)
    RadioButton scope_services_present_no;

    @BindView(R.id.remark_scope_services_present)
    ImageView remark_scope_services_present;

    @BindView(R.id.nc_scope_services_present)
    ImageView nc_scope_services_present;

    @BindView(R.id.image_scope_services_present)
    ImageView image_scope_services_present;

    @BindView(R.id.Patients_responsibility_displayed_yes)
    RadioButton Patients_responsibility_displayed_yes;

    @BindView(R.id.Patients_responsibility_displayed_no)
    RadioButton Patients_responsibility_displayed_no;

    @BindView(R.id.remark_Patients_responsibility_displayed)
    ImageView remark_Patients_responsibility_displayed;

    @BindView(R.id.nc_Patients_responsibility_displayed)
    ImageView nc_Patients_responsibility_displayed;

    @BindView(R.id.image_Patients_responsibility_displayed)
    ImageView image_Patients_responsibility_displayed;

    @BindView(R.id.fire_exit_signage_present_yes)
    RadioButton fire_exit_signage_present_yes;

    @BindView(R.id.fire_exit_signage_present_no)
    RadioButton fire_exit_signage_present_no;

    @BindView(R.id.remark_fire_exit_signage_present)
    ImageView remark_fire_exit_signage_present;

    @BindView(R.id.nc_fire_exit_signage_present)
    ImageView nc_fire_exit_signage_present;

    @BindView(R.id.image_fire_exit_signage_present)
    ImageView image_fire_exit_signage_present;

    @BindView(R.id.directional_signages_present_yes)
    RadioButton directional_signages_present_yes;

    @BindView(R.id.directional_signages_present_no)
    RadioButton directional_signages_present_no;

    @BindView(R.id.remark_directional_signages_present)
    ImageView remark_directional_signages_present;

    @BindView(R.id.nc_directional_signages_present)
    ImageView nc_directional_signages_present;

    @BindView(R.id.image_directional_signages_present)
    ImageView image_directional_signages_present;

    @BindView(R.id.departmental_signages_present_yes)
    RadioButton departmental_signages_present_yes;

    @BindView(R.id.departmental_signages_present_no)
    RadioButton departmental_signages_present_no;

    @BindView(R.id.remark_departmental_signages_present)
    ImageView remark_departmental_signages_present;

    @BindView(R.id.nc_departmental_signages_present)
    ImageView nc_departmental_signages_present;

    @BindView(R.id.image_departmental_signages_present)
    ImageView image_departmental_signages_present;

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

    private String scope_services_present_view = "",Patients_responsibility_displayed_view ="",fire_exit_signage_present_view = "",
            directional_signages_present_view = "",departmental_signages_present_view = "";

    private UniformSignagePojo pojo;

    private String scope_services_present = "",Patients_responsibility_displayed = "",fire_exit_signage_present = "",
            directional_signages_present = "",departmental_signages_present = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_signage);


        ButterKnife.bind(this);

        setDrawerbackIcon("Uniform Signage");

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

        pojo = new UniformSignagePojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        laboratory_hospital_name.setText(getFromPrefs(AppConstant.Hospital_Name));

        getLaboratoryData();

    }

    public void getLaboratoryData(){

        pojo = databaseHandler.getUniformSignagePojo("19");

        if (pojo != null){
            sql_status = true;
            if (pojo.getScope_services_present() != null){
                scope_services_present = pojo.getScope_services_present();
                if (pojo.getScope_services_present().equalsIgnoreCase("Yes")){
                    scope_services_present_yes.setChecked(true);
                }else if (pojo.getScope_services_present().equalsIgnoreCase("No")){
                    scope_services_present_no.setChecked(true);
                }
            }
            if (pojo.getPatients_responsibility_displayed() != null){
                Patients_responsibility_displayed = pojo.getPatients_responsibility_displayed();
                if (pojo.getPatients_responsibility_displayed().equalsIgnoreCase("Yes")){
                    Patients_responsibility_displayed_yes.setChecked(true);
                }else if (pojo.getPatients_responsibility_displayed().equalsIgnoreCase("No")){
                    Patients_responsibility_displayed_no.setChecked(true);
                }
            } if (pojo.getFire_exit_signage_present() != null){
                fire_exit_signage_present = pojo.getFire_exit_signage_present();
                if (pojo.getFire_exit_signage_present().equalsIgnoreCase("Yes")){
                    fire_exit_signage_present_yes.setChecked(true);
                }else if (pojo.getFire_exit_signage_present().equalsIgnoreCase("No")){
                    fire_exit_signage_present_no.setChecked(true);
                }
            } if (pojo.getDirectional_signages_present() != null){
                directional_signages_present = pojo.getDirectional_signages_present();
                if (pojo.getDirectional_signages_present().equalsIgnoreCase("Yes")){
                    directional_signages_present_yes.setChecked(true);
                }else if (pojo.getDirectional_signages_present().equalsIgnoreCase("No")){
                    directional_signages_present_no.setChecked(true);
                }
            } if (pojo.getDepartmental_signages_present() != null){
                departmental_signages_present = pojo.getDepartmental_signages_present();
                if (pojo.getDepartmental_signages_present().equalsIgnoreCase("Yes")){
                    departmental_signages_present_yes.setChecked(true);
                }else if (pojo.getDepartmental_signages_present().equalsIgnoreCase("No")){
                    departmental_signages_present_no.setChecked(true);
                }
            }

            if (pojo.getScope_services_present_remark() != null){
                remark_scope_services_present.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getScope_services_present_remark();
            }
            if (pojo.getScope_services_present_nc() != null){
                nc1 = pojo.getScope_services_present_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_scope_services_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_scope_services_present.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getScope_services_present_image() != null){
                image_scope_services_present.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getScope_services_present_image();

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

            if (pojo.getLocal_scope_services_present_image() != null){

                Local_image1 = pojo.getLocal_scope_services_present_image();

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

            if (pojo.getPatients_responsibility_displayed_remark() != null){
                remark2 = pojo.getPatients_responsibility_displayed_remark();

                remark_Patients_responsibility_displayed.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatients_responsibility_displayed_nc() != null){
                nc2 = pojo.getPatients_responsibility_displayed_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_Patients_responsibility_displayed.setImageResource(R.mipmap.nc);
                }else {
                    nc_Patients_responsibility_displayed.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getPatients_responsibility_displayed_image() != null){
                image_Patients_responsibility_displayed.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getPatients_responsibility_displayed_image();

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

            if (pojo.getLocal_Patients_responsibility_displayed_image() != null){

                Local_image2 = pojo.getLocal_Patients_responsibility_displayed_image();

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

            if (pojo.getFire_exit_signage_present_remark() != null){
                remark3 = pojo.getFire_exit_signage_present_remark();

                remark_fire_exit_signage_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getFire_exit_signage_present_nc() != null){
                nc3 = pojo.getFire_exit_signage_present_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_fire_exit_signage_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_fire_exit_signage_present.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getFire_exit_signage_present_image() != null){
                image_fire_exit_signage_present.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getFire_exit_signage_present_image();

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

            if (pojo.getLocal_fire_exit_signage_present_image() != null){

                Local_image3 = pojo.getLocal_fire_exit_signage_present_image();

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
            if (pojo.getDirectional_signages_present_remark() != null){
                remark4 = pojo.getDirectional_signages_present_remark();

                remark_directional_signages_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDirectional_signages_present_Nc() != null){
                nc4 = pojo.getDirectional_signages_present_Nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_directional_signages_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_directional_signages_present.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDirectional_signages_present_image() != null){
                image_directional_signages_present.setImageResource(R.mipmap.camera_selected);

                image4 = pojo.getDirectional_signages_present_image();

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

            if (pojo.getLocal_directional_signages_present_image() != null){

                Local_image4 = pojo.getLocal_directional_signages_present_image();

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



            if (pojo.getDepartmental_signages_present_remark() != null){
                remark5 = pojo.getDepartmental_signages_present_remark();

                remark_departmental_signages_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDepartmental_signages_present_nc() != null){
                nc5 = pojo.getDepartmental_signages_present_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_departmental_signages_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_departmental_signages_present.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDepartmental_signages_present_image() != null){
                image_departmental_signages_present.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getDepartmental_signages_present_image();

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

            if (pojo.getLocal_departmental_signages_present_image() != null){

                Local_image5 = pojo.getLocal_departmental_signages_present_image();

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
            pojo = new UniformSignagePojo();
        }

    }



    @OnClick({R.id.remark_scope_services_present,R.id.nc_scope_services_present,R.id.image_scope_services_present,R.id.remark_Patients_responsibility_displayed,R.id.nc_Patients_responsibility_displayed,
            R.id.image_Patients_responsibility_displayed,R.id.remark_fire_exit_signage_present,R.id.nc_fire_exit_signage_present,R.id.image_fire_exit_signage_present,R.id.remark_directional_signages_present,
            R.id.nc_directional_signages_present,R.id.image_directional_signages_present,R.id.remark_departmental_signages_present,R.id.nc_departmental_signages_present,R.id.image_departmental_signages_present,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_scope_services_present:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_scope_services_present:
                displayNCDialog("NC", 1);
                break;

            case R.id.image_scope_services_present:
                if (Local_safety_device_lab_list.size() > 0){
                    showImageListDialog(Local_safety_device_lab_list,1,"safety_device_lab");
                }else {
                    captureImage(1);
                }

                break;

            case R.id.remark_Patients_responsibility_displayed:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_Patients_responsibility_displayed:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_Patients_responsibility_displayed:
                if (Local_body_parts_staff_patients_list.size() > 0){
                    showImageListDialog(Local_body_parts_staff_patients_list,2,"body_parts_staff_patients");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_fire_exit_signage_present:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_fire_exit_signage_present:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_fire_exit_signage_present:
                if (Local_staff_member_radiation_area_list.size() > 0){
                    showImageListDialog(Local_staff_member_radiation_area_list,3,"staff_member_radiation_area");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.remark_directional_signages_present:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_directional_signages_present:
                displayNCDialog("NC", 4);
                break;

            case R.id.image_directional_signages_present:
                if (Local_standardised_colur_coding_list.size() > 0){
                    showImageListDialog(Local_standardised_colur_coding_list,4,"standardised_colur_coding");
                }else {
                    captureImage(4);
                }
                break;


            case R.id.remark_departmental_signages_present:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_departmental_signages_present:
                displayNCDialog("NC", 5);
                break;

            case R.id.image_departmental_signages_present:
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

                PostLaboratoryData();


                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.scope_services_present_yes:
                if (checked)
                    scope_services_present = "Yes";
                break;

            case R.id.scope_services_present_no:
                if (checked)
                    scope_services_present = "No";
                break;

            case R.id.Patients_responsibility_displayed_yes:
                if (checked)
                    Patients_responsibility_displayed = "Yes";
                break;
            case R.id.Patients_responsibility_displayed_no:
                if (checked)
                    Patients_responsibility_displayed = "No";
                break;


            case R.id.fire_exit_signage_present_yes:
                if (checked)
                    fire_exit_signage_present = "Yes";
                break;
            case R.id.fire_exit_signage_present_no:
                if (checked)
                    fire_exit_signage_present = "No";
                break;

            case R.id.directional_signages_present_yes:
                if (checked)
                    directional_signages_present = "Yes";
                break;
            case R.id.directional_signages_present_no:
                if (checked)
                    directional_signages_present = "No";
                break;

            case R.id.departmental_signages_present_yes:
                if (checked)
                    departmental_signages_present = "Yes";
                break;

            case R.id.departmental_signages_present_no:
                if (checked)
                    departmental_signages_present = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(UniformSignageActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                ImageUpload(image2,"safety_device_lab");


            }

        }
        else if (requestCode == 2) {
            if (picUri != null) {
                Uri uri = picUri;
                String image3 = compressImage(uri.toString());
                //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                ImageUpload(image3,"body_parts_staff_patients");
            }

        }
        else if (requestCode == 3) {
            if (picUri != null) {
                Uri uri = picUri;
                String image4 = compressImage(uri.toString());
                //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                ImageUpload(image4,"staff_member_radiation_area");

            }
        } else if (requestCode == 4) {
            if (picUri != null) {
                Uri uri = picUri;
                String image4 = compressImage(uri.toString());
                //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                ImageUpload(image4,"standardised_colur_coding");

            }
        } else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    ImageUpload(image4, "safe_storage_medical");

                }
            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(UniformSignageActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_scope_services_present.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_scope_services_present.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(UniformSignageActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {

                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_Patients_responsibility_displayed.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_Patients_responsibility_displayed.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(UniformSignageActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_fire_exit_signage_present.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_fire_exit_signage_present.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(UniformSignageActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_directional_signages_present.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_directional_signages_present.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(UniformSignageActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_departmental_signages_present.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_departmental_signages_present.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(UniformSignageActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(UniformSignageActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_scope_services_present.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(UniformSignageActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_Patients_responsibility_displayed.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(UniformSignageActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_fire_exit_signage_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(UniformSignageActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_directional_signages_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(UniformSignageActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_departmental_signages_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(UniformSignageActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(UniformSignageActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(UniformSignageActivity.this,list,from,"UniformSignage");
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



    public void SaveLaboratoryData(String from){
        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(19);
        pojo.setScope_services_present(scope_services_present);
        pojo.setPatients_responsibility_displayed(Patients_responsibility_displayed);
        pojo.setFire_exit_signage_present(fire_exit_signage_present);
        pojo.setDirectional_signages_present(directional_signages_present);
        pojo.setDepartmental_signages_present(departmental_signages_present);

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


        pojo.setScope_services_present_remark(remark1);
        pojo.setScope_services_present_nc(nc1);
        pojo.setScope_services_present_image(image1);
        pojo.setLocal_scope_services_present_image(Local_image1);


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


        pojo.setPatients_responsibility_displayed_remark(remark2);
        pojo.setPatients_responsibility_displayed_nc(nc2);
        pojo.setPatients_responsibility_displayed_image(image2);
        pojo.setLocal_Patients_responsibility_displayed_image(Local_image2);

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


        pojo.setFire_exit_signage_present_remark(remark3);
        pojo.setFire_exit_signage_present_nc(nc3);
        pojo.setFire_exit_signage_present_image(image3);
        pojo.setLocal_fire_exit_signage_present_image(Local_image3);

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



        pojo.setDirectional_signages_present_remark(remark4);
        pojo.setDirectional_signages_present_Nc(nc4);
        pojo.setDirectional_signages_present_image(image4);
        pojo.setLocal_directional_signages_present_image(Local_image4);

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



        pojo.setDepartmental_signages_present_remark(remark5);
        pojo.setDepartmental_signages_present_nc(nc5);
        pojo.setDepartmental_signages_present_image(image5);
        pojo.setLocal_departmental_signages_present_image(Local_image5);


        if (sql_status){
            databaseHandler.UPDATE_Uniform_Signage(pojo);
        }else {
            boolean status = databaseHandler.INSERT_Uniform_Signage(pojo);
            System.out.println(status);
        }

        if (!from.equalsIgnoreCase("sync")){
            assessement_list = databaseHandler.getAssessmentList(Hospital_id);


            AssessmentStatusPojo pojo = new AssessmentStatusPojo();
            pojo.setHospital_id(assessement_list.get(19).getHospital_id());
            pojo.setAssessement_name("Uniform Signage");
            pojo.setAssessement_status("Draft");
            pojo.setLocal_id(assessement_list.get(19).getLocal_id());

            databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

            Toast.makeText(UniformSignageActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(UniformSignageActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();

        }

    }
    private void ImageUpload(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        final ProgressDialog d = ImageDialog.showLoading(UniformSignageActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(UniformSignageActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(UniformSignageActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("safety_device_lab")){
                                safety_device_lab_list.add(response.body().getMessage());
                                Local_safety_device_lab_list.add(image_path);
                                image_scope_services_present.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("body_parts_staff_patients")){
                                body_parts_staff_patients_list.add(response.body().getMessage());
                                Local_body_parts_staff_patients_list.add(image_path);
                                image_Patients_responsibility_displayed.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("staff_member_radiation_area")){
                                staff_member_radiation_area_list.add(response.body().getMessage());
                                Local_staff_member_radiation_area_list.add(image_path);
                                image_fire_exit_signage_present.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("standardised_colur_coding")){
                                standardised_colur_coding_list.add(response.body().getMessage());
                                Local_standardised_colur_coding_list.add(image_path);
                                image_directional_signages_present.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("safe_storage_medical")){
                                safe_storage_medical_list.add(response.body().getMessage());
                                Local_safe_storage_medical_list.add(image_path);
                                image_departmental_signages_present.setImageResource(R.mipmap.camera_selected);
                            }

                            Toast.makeText(UniformSignageActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(UniformSignageActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(UniformSignageActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(UniformSignageActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void PostLaboratoryData(){

        SaveLaboratoryData("sync");

        if (scope_services_present.length() > 0 && Patients_responsibility_displayed.length() > 0 && fire_exit_signage_present.length() > 0 && directional_signages_present.length() > 0
                && departmental_signages_present.length() > 0){

            if (image1 != null && image2 != null  && image3 != null  && image4 != null  && image5 != null ){
                pojo_dataSync.setTabName("UniformSignage");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<safety_device_lab_list.size();i++){
                    String value_rail = safety_device_lab_list.get(i);

                    scope_services_present_view = value_rail + scope_services_present_view;
                }
                pojo.setScope_services_present_image(scope_services_present_view);

                for (int i=0;i<body_parts_staff_patients_list.size();i++){
                    String value_transported = body_parts_staff_patients_list.get(i);

                    Patients_responsibility_displayed_view = value_transported + Patients_responsibility_displayed_view;
                }
                pojo.setPatients_responsibility_displayed_image(Patients_responsibility_displayed_view);

                for (int i=0;i<staff_member_radiation_area_list.size();i++){
                    String value_specimen = staff_member_radiation_area_list.get(i);

                    fire_exit_signage_present_view = value_specimen + fire_exit_signage_present_view;
                }

                pojo.setFire_exit_signage_present_image(fire_exit_signage_present_view);

                for (int i=0;i<standardised_colur_coding_list.size();i++){
                    String value_specimen = standardised_colur_coding_list.get(i);

                    directional_signages_present_view = value_specimen + directional_signages_present_view;
                }
                pojo.setDirectional_signages_present_image(directional_signages_present_view);


                for (int i=0;i<safe_storage_medical_list.size();i++){
                    String value_specimen = safe_storage_medical_list.get(i);

                    departmental_signages_present_view = value_specimen + departmental_signages_present_view;
                }
                pojo.setDepartmental_signages_present_image(departmental_signages_present_view);



                pojo_dataSync.setUniformsignage(pojo);

                final ProgressDialog d = AppDialog.showLoading(UniformSignageActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(UniformSignageActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(UniformSignageActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(UniformSignageActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("Laboratory_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(19).getHospital_id());
                                    pojo.setAssessement_name("Uniform Signage");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(19).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(UniformSignageActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DataSyncResponse> call, Throwable t) {
                        System.out.println("xxx failed");

                        d.dismiss();
                    }
                });
            }else {
                Toast.makeText(UniformSignageActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(UniformSignageActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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
                    image_scope_services_present.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("body_parts_staff_patients")){
                body_parts_staff_patients_list.remove(position);
                Local_body_parts_staff_patients_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_body_parts_staff_patients_list.size() == 0){
                    image_Patients_responsibility_displayed.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("staff_member_radiation_area")){
                staff_member_radiation_area_list.remove(position);
                Local_staff_member_radiation_area_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_staff_member_radiation_area_list.size() == 0){
                    image_fire_exit_signage_present.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }
            }
            else if (from.equalsIgnoreCase("standardised_colur_coding")){
                standardised_colur_coding_list.remove(position);
                Local_standardised_colur_coding_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_standardised_colur_coding_list.size() == 0){
                    image_directional_signages_present.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }
            }
            else if (from.equalsIgnoreCase("safe_storage_medical")){
                safe_storage_medical_list.remove(position);
                Local_safe_storage_medical_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_safe_storage_medical_list.size() == 0){
                    image_departmental_signages_present.setImageResource(R.mipmap.camera);

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

        SaveLaboratoryData("save");
    }
}
