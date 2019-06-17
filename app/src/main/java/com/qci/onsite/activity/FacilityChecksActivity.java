package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
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
import com.qci.onsite.pojo.LaboratoryPojo;
import com.qci.onsite.pojo.VideoDialog;
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

public class FacilityChecksActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.medical_gas_cylinders_yes)
    RadioButton medical_gas_cylinders_yes;

    @BindView(R.id.medical_gas_cylinders_no)
    RadioButton medical_gas_cylinders_no;

    @BindView(R.id.remark_medical_gas_cylinders)
    ImageView remark_medical_gas_cylinders;

    @BindView(R.id.nc_medical_gas_cylinders)
    ImageView nc_medical_gas_cylinders;

    @BindView(R.id.image_medical_gas_cylinders)
    ImageView image_medical_gas_cylinders;

    @BindView(R.id.smoke_detectors_installed_yes)
    RadioButton smoke_detectors_installed_yes;

    @BindView(R.id.smoke_detectors_installed_no)
    RadioButton smoke_detectors_installed_no;

    @BindView(R.id.remark_smoke_detectors_installed)
    ImageView remark_smoke_detectors_installed;

    @BindView(R.id.nc_smoke_detectors_installed)
    ImageView nc_smoke_detectors_installed;

    @BindView(R.id.image_smoke_detectors_installed)
    ImageView image_smoke_detectors_installed;

    @BindView(R.id.extinguisher_present_patient_yes)
    RadioButton extinguisher_present_patient_yes;

    @BindView(R.id.extinguisher_present_patient_no)
    RadioButton extinguisher_present_patient_no;

    @BindView(R.id.remark_extinguisher_present_patient)
    ImageView remark_extinguisher_present_patient;

    @BindView(R.id.nc_extinguisher_present_patient)
    ImageView nc_extinguisher_present_patient;

    @BindView(R.id.image_extinguisher_present_patient)
    ImageView image_extinguisher_present_patient;

    @BindView(R.id.fire_fighting_equipment_yes)
    RadioButton fire_fighting_equipment_yes;

    @BindView(R.id.fire_fighting_equipment_no)
    RadioButton fire_fighting_equipment_no;

    @BindView(R.id.remark_fire_fighting_equipment)
    ImageView remark_fire_fighting_equipment;

    @BindView(R.id.nc_fire_fighting_equipment)
    ImageView nc_fire_fighting_equipment;

    @BindView(R.id.safe_exit_plan_yes)
    RadioButton safe_exit_plan_yes;

    @BindView(R.id.safe_exit_plan_no)
    RadioButton safe_exit_plan_no;

    @BindView(R.id.remark_safe_exit_plan)
    ImageView remark_safe_exit_plan;

    @BindView(R.id.nc_safe_exit_plan)
    ImageView nc_safe_exit_plan;

    @BindView(R.id.btnSave)
    Button btnSave;

    private String remark1, remark2, remark3, remark4, remark5;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private ArrayList<String>medical_gas_cylinders_list;
    private ArrayList<String>smoke_detectors_installed_list;
    private ArrayList<String>extinguisher_present_patient_list;
    private ArrayList<String>Local_medical_gas_cylinders_list;
    private ArrayList<String>Local_smoke_detectors_installed_list;
    private ArrayList<String>Local_extinguisher_present_patient_list;


    private String nc1, nc2, nc3, nc4, nc5;
    private String radio_status1, radio_status2, radio_status3, radio_status4, radio_status5;

    private DatabaseHandler databaseHandler;

    private String image1,image2,image3;
    private String Local_image1,Local_image2,Local_image3;

    private File outputVideoFile;

    Uri videoUri;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;


    @BindView(R.id.laboratory_hospital_name)
    TextView laboratory_hospital_name;

    private String medical_gas_cylinders_view = "",smoke_detectors_installed_view ="",extinguisher_present_patient_view = "";

    private FacilityChecksPojo pojo;

    private String medical_gas_cylinders = "",smoke_detectors_installed = "",extinguisher_present_patient = "",
            fire_fighting_equipment = "",safe_exit_plan = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_check);

        ButterKnife.bind(this);

        setDrawerbackIcon("Maintenance/Facility Checks");

        medical_gas_cylinders_list = new ArrayList<>();
        smoke_detectors_installed_list = new ArrayList<>();
        extinguisher_present_patient_list = new ArrayList<>();

        Local_medical_gas_cylinders_list = new ArrayList<>();
        Local_smoke_detectors_installed_list = new ArrayList<>();
        Local_extinguisher_present_patient_list = new ArrayList<>();


        databaseHandler = DatabaseHandler.getInstance(this);

        pojo_dataSync = new DataSyncRequest();

        pojo = new FacilityChecksPojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        laboratory_hospital_name.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getLaboratoryData();

    }

    public void getLaboratoryData(){

        pojo = databaseHandler.getFacilityPojo("16");

        if (pojo != null){
            sql_status = true;
            if (pojo.getMedical_gas_cylinders() != null){
                medical_gas_cylinders = pojo.getMedical_gas_cylinders();
                if (pojo.getMedical_gas_cylinders().equalsIgnoreCase("Yes")){
                    medical_gas_cylinders_yes.setChecked(true);
                }else if (pojo.getMedical_gas_cylinders().equalsIgnoreCase("No")){
                    medical_gas_cylinders_no.setChecked(true);
                }
            }
            if (pojo.getSmoke_detectors_installed() != null){
                smoke_detectors_installed = pojo.getSmoke_detectors_installed();
                if (pojo.getSmoke_detectors_installed().equalsIgnoreCase("Yes")){
                    smoke_detectors_installed_yes.setChecked(true);
                }else if (pojo.getSmoke_detectors_installed().equalsIgnoreCase("No")){
                    smoke_detectors_installed_no.setChecked(true);
                }
            } if (pojo.getExtinguisher_present_patient() != null){
                extinguisher_present_patient = pojo.getExtinguisher_present_patient();
                if (pojo.getExtinguisher_present_patient().equalsIgnoreCase("Yes")){
                    extinguisher_present_patient_yes.setChecked(true);
                }else if (pojo.getExtinguisher_present_patient().equalsIgnoreCase("No")){
                    extinguisher_present_patient_no.setChecked(true);
                }
            } if (pojo.getFire_fighting_equipment() != null){
                fire_fighting_equipment = pojo.getFire_fighting_equipment();
                if (pojo.getFire_fighting_equipment().equalsIgnoreCase("Yes")){
                    fire_fighting_equipment_yes.setChecked(true);
                }else if (pojo.getFire_fighting_equipment().equalsIgnoreCase("No")){
                    fire_fighting_equipment_no.setChecked(true);
                }
            } if (pojo.getSafe_exit_plan() != null){
                safe_exit_plan = pojo.getSafe_exit_plan();
                if (pojo.getSafe_exit_plan().equalsIgnoreCase("Yes")){
                    safe_exit_plan_yes.setChecked(true);
                }else if (pojo.getSafe_exit_plan().equalsIgnoreCase("No")){
                    safe_exit_plan_no.setChecked(true);
                }
            }

            if (pojo.getMedical_gas_cylinders_remark() != null){
                remark_medical_gas_cylinders.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getMedical_gas_cylinders_remark();
            }
            if (pojo.getMedical_gas_cylinders_nc() != null){
                nc1 = pojo.getMedical_gas_cylinders_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_medical_gas_cylinders.setImageResource(R.mipmap.nc);
                }else {
                    nc_medical_gas_cylinders.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getMedical_gas_cylinders_image() != null){
                image_medical_gas_cylinders.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getMedical_gas_cylinders_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            medical_gas_cylinders_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_medical_gas_cylinders_image() != null){

                Local_image1 = pojo.getLocal_medical_gas_cylinders_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_medical_gas_cylinders_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getSmoke_detectors_installed_remark() != null){
                remark2 = pojo.getSmoke_detectors_installed_remark();

                remark_smoke_detectors_installed.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getSmoke_detectors_installed_nc() != null){
                nc2 = pojo.getSmoke_detectors_installed_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_smoke_detectors_installed.setImageResource(R.mipmap.nc);
                }else {
                    nc_smoke_detectors_installed.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getSmoke_detectors_installed_image() != null){
                image_smoke_detectors_installed.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getSmoke_detectors_installed_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            smoke_detectors_installed_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_smoke_detectors_installed_image() != null){

                Local_image2 = pojo.getLocal_smoke_detectors_installed_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_smoke_detectors_installed_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getExtinguisher_present_patient_remark() != null){
                remark3 = pojo.getExtinguisher_present_patient_remark();

                remark_extinguisher_present_patient.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getExtinguisher_present_patient_nc() != null){
                nc3 = pojo.getExtinguisher_present_patient_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_extinguisher_present_patient.setImageResource(R.mipmap.nc);
                }else {
                    nc_extinguisher_present_patient.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getExtinguisher_present_patient_image() != null){
                image_extinguisher_present_patient.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getExtinguisher_present_patient_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            extinguisher_present_patient_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_extinguisher_present_patient_image() != null){

                Local_image3 = pojo.getLocal_extinguisher_present_patient_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_extinguisher_present_patient_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getFire_fighting_equipment_remark() != null){
                remark4 = pojo.getFire_fighting_equipment_remark();

                remark_fire_fighting_equipment.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getFire_fighting_equipment_nc() != null){
                nc4 = pojo.getFire_fighting_equipment_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_fire_fighting_equipment.setImageResource(R.mipmap.nc);
                }else {
                    nc_fire_fighting_equipment.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getSafe_exit_plan_remark() != null){
                remark5 = pojo.getSafe_exit_plan_remark();

                remark_safe_exit_plan.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getSafe_exit_plan_nc() != null){
                nc5 = pojo.getSafe_exit_plan_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_safe_exit_plan.setImageResource(R.mipmap.nc);
                }else {
                    nc_safe_exit_plan.setImageResource(R.mipmap.nc_selected);
                }


            }

        }else {
            pojo = new FacilityChecksPojo();
        }

    }



    @OnClick({R.id.remark_medical_gas_cylinders,R.id.nc_medical_gas_cylinders,R.id.image_medical_gas_cylinders,R.id.remark_smoke_detectors_installed,R.id.nc_smoke_detectors_installed,
            R.id.image_smoke_detectors_installed,R.id.remark_extinguisher_present_patient,R.id.nc_extinguisher_present_patient,R.id.image_extinguisher_present_patient,R.id.remark_fire_fighting_equipment,
            R.id.nc_fire_fighting_equipment,R.id.remark_safe_exit_plan,R.id.nc_safe_exit_plan,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_medical_gas_cylinders:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_medical_gas_cylinders:
                displayNCDialog("NC", 1);
                break;

            case R.id.image_medical_gas_cylinders:
                if (Local_medical_gas_cylinders_list.size() > 0){
                    showImageListDialog(Local_medical_gas_cylinders_list,1,"medical_gas_cylinders");
                }else {
                    captureImage(1);
                }

                break;

            case R.id.remark_smoke_detectors_installed:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_smoke_detectors_installed:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_smoke_detectors_installed:
                if (Local_smoke_detectors_installed_list.size() > 0){
                    showImageListDialog(Local_smoke_detectors_installed_list,2,"smoke_detectors_installed");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_extinguisher_present_patient:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_extinguisher_present_patient:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_extinguisher_present_patient:
                if (Local_extinguisher_present_patient_list.size() > 0){
                    showImageListDialog(Local_extinguisher_present_patient_list,3,"extinguisher_present_patient");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.remark_fire_fighting_equipment:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_fire_fighting_equipment:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_safe_exit_plan:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_safe_exit_plan:
                displayNCDialog("NC", 5);
                break;

            case R.id.btnSave:
                SaveLaboratoryData("save");
                break;

            case R.id.btnSync:

                if (medical_gas_cylinders.length() > 0 && smoke_detectors_installed.length() > 0 && extinguisher_present_patient.length() > 0 && fire_fighting_equipment.length() > 0
                        && safe_exit_plan.length() > 0){

                    if (image1 != null && image2 != null && image3 != null){
                        SaveLaboratoryData("sync");
                    }else {
                        Toast.makeText(FacilityChecksActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(FacilityChecksActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.medical_gas_cylinders_yes:
                if (checked)
                    medical_gas_cylinders = "Yes";
                break;

            case R.id.medical_gas_cylinders_no:
                if (checked)
                    medical_gas_cylinders = "No";
                break;

            case R.id.smoke_detectors_installed_yes:
                if (checked)
                    smoke_detectors_installed = "Yes";
                break;
            case R.id.smoke_detectors_installed_no:
                if (checked)
                    smoke_detectors_installed = "No";
                break;


            case R.id.extinguisher_present_patient_yes:
                if (checked)
                    extinguisher_present_patient = "Yes";
                break;
            case R.id.extinguisher_present_patient_no:
                if (checked)
                    extinguisher_present_patient = "No";
                break;

            case R.id.fire_fighting_equipment_yes:
                if (checked)
                    fire_fighting_equipment = "Yes";
                break;
            case R.id.fire_fighting_equipment_no:
                if (checked)
                    fire_fighting_equipment = "No";
                break;

            case R.id.safe_exit_plan_yes:
                if (checked)
                    safe_exit_plan = "Yes";
                break;

            case R.id.safe_exit_plan_no:
                if (checked)
                    safe_exit_plan = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FacilityChecksActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    ImageUpload(image2,"medical_gas_cylinders");


                }

            }
            else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                    ImageUpload(image3,"smoke_detectors_installed");
                }

            }
            else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    ImageUpload(image4, "extinguisher_present_patient");

                }
            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(FacilityChecksActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_medical_gas_cylinders.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_medical_gas_cylinders.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(FacilityChecksActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {

                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_smoke_detectors_installed.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_smoke_detectors_installed.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(FacilityChecksActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_extinguisher_present_patient.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_extinguisher_present_patient.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(FacilityChecksActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_fire_fighting_equipment.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_fire_fighting_equipment.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(FacilityChecksActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_safe_exit_plan.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_safe_exit_plan.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(FacilityChecksActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(FacilityChecksActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_medical_gas_cylinders.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(FacilityChecksActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_smoke_detectors_installed.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(FacilityChecksActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_extinguisher_present_patient.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(FacilityChecksActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_fire_fighting_equipment.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(FacilityChecksActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_safe_exit_plan.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(FacilityChecksActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(FacilityChecksActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(FacilityChecksActivity.this,list,from,"FacilityCheck");
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
        pojo.setHospital_id(16);
        pojo.setMedical_gas_cylinders(medical_gas_cylinders);
        pojo.setSmoke_detectors_installed(smoke_detectors_installed);
        pojo.setExtinguisher_present_patient(extinguisher_present_patient);
        pojo.setFire_fighting_equipment(fire_fighting_equipment);
        pojo.setSafe_exit_plan(safe_exit_plan);

        JSONObject json = new JSONObject();


        if (medical_gas_cylinders_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(medical_gas_cylinders_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_medical_gas_cylinders_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_medical_gas_cylinders_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }


        pojo.setMedical_gas_cylinders_remark(remark1);
        pojo.setMedical_gas_cylinders_nc(nc1);
        pojo.setMedical_gas_cylinders_image(image1);
        pojo.setLocal_medical_gas_cylinders_image(Local_image1);


        if (smoke_detectors_installed_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(smoke_detectors_installed_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_smoke_detectors_installed_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_smoke_detectors_installed_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }


        pojo.setSmoke_detectors_installed_remark(remark2);
        pojo.setSmoke_detectors_installed_nc(nc2);
        pojo.setSmoke_detectors_installed_image(image2);
        pojo.setLocal_smoke_detectors_installed_image(Local_image2);

        if (extinguisher_present_patient_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(extinguisher_present_patient_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_extinguisher_present_patient_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_extinguisher_present_patient_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }


        pojo.setExtinguisher_present_patient_remark(remark3);
        pojo.setExtinguisher_present_patient_nc(nc3);
        pojo.setExtinguisher_present_patient_image(image3);
        pojo.setLocal_extinguisher_present_patient_image(Local_image3);


        pojo.setFire_fighting_equipment_remark(remark4);
        pojo.setFire_fighting_equipment_nc(nc4);


        pojo.setSafe_exit_plan_remark(remark5);
        pojo.setSafe_exit_plan_nc(nc5);


        if (sql_status){
            boolean st_status = databaseHandler.UPDATE_Facility_Checks(pojo);

            if (st_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(16).getHospital_id());
                    pojo.setAssessement_name("Maintenance/Facility Checks");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(16).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(FacilityChecksActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(FacilityChecksActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    progreesDialog();
                    PostLaboratoryData();
                }
            }
        }else {
            boolean st_status = databaseHandler.INSERT_Facility_Checks(pojo);

            if (st_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(16).getHospital_id());
                    pojo.setAssessement_name("Maintenance/Facility Checks");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(16).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(FacilityChecksActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(FacilityChecksActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    progreesDialog();
                    PostLaboratoryData();
                }
            }
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

        final ProgressDialog d = ImageDialog.showLoading(FacilityChecksActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(FacilityChecksActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(FacilityChecksActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("medical_gas_cylinders")){
                                medical_gas_cylinders_list.add(response.body().getMessage());
                                Local_medical_gas_cylinders_list.add(image_path);
                                image_medical_gas_cylinders.setImageResource(R.mipmap.camera_selected);

                                image1 = "medical_gas_cylinders";

                            }else if (from.equalsIgnoreCase("smoke_detectors_installed")){
                                smoke_detectors_installed_list.add(response.body().getMessage());
                                Local_smoke_detectors_installed_list.add(image_path);
                                image_smoke_detectors_installed.setImageResource(R.mipmap.camera_selected);

                                image2 = "smoke_detectors_installed";
                            }else if (from.equalsIgnoreCase("extinguisher_present_patient")){
                                extinguisher_present_patient_list.add(response.body().getMessage());
                                Local_extinguisher_present_patient_list.add(image_path);
                                image_extinguisher_present_patient.setImageResource(R.mipmap.camera_selected);

                                image3 = "extinguisher_present_patient";
                            }

                            Toast.makeText(FacilityChecksActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(FacilityChecksActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(FacilityChecksActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(FacilityChecksActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void PostLaboratoryData(){

                pojo_dataSync.setTabName("maintenancefacility");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<medical_gas_cylinders_list.size();i++){
                    String value_rail = medical_gas_cylinders_list.get(i);

                    medical_gas_cylinders_view = value_rail + medical_gas_cylinders_view;
                }
                pojo.setMedical_gas_cylinders_image(medical_gas_cylinders_view);

                for (int i=0;i<smoke_detectors_installed_list.size();i++){
                    String value_transported = smoke_detectors_installed_list.get(i);

                    smoke_detectors_installed_view = value_transported + smoke_detectors_installed_view;
                }
                pojo.setSmoke_detectors_installed_image(smoke_detectors_installed_view);

                for (int i=0;i<extinguisher_present_patient_list.size();i++){
                    String value_specimen = extinguisher_present_patient_list.get(i);

                    extinguisher_present_patient_view = value_specimen + extinguisher_present_patient_view;
                }

                pojo.setExtinguisher_present_patient_image(extinguisher_present_patient_view);

                pojo_dataSync.setMaintenancefacility(pojo);


                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(FacilityChecksActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(FacilityChecksActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(FacilityChecksActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("Laboratory_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(16).getHospital_id());
                                    pojo.setAssessement_name("Maintenance/Facility Checks");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(16).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(FacilityChecksActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("medical_gas_cylinders")){
                medical_gas_cylinders_list.remove(position);
                Local_medical_gas_cylinders_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_medical_gas_cylinders_list.size() == 0){
                    image_medical_gas_cylinders.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("smoke_detectors_installed")){
                smoke_detectors_installed_list.remove(position);
                Local_smoke_detectors_installed_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_smoke_detectors_installed_list.size() == 0){
                    image_smoke_detectors_installed.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("extinguisher_present_patient")){
                extinguisher_present_patient_list.remove(position);
                Local_extinguisher_present_patient_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_extinguisher_present_patient_list.size() == 0){
                    image_extinguisher_present_patient.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(16).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("save");
        }else {
            Intent intent = new Intent(FacilityChecksActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }
}

