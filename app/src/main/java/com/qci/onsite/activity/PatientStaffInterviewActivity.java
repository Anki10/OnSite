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
import com.qci.onsite.pojo.Patient_Staff_InterviewPojo;
import com.qci.onsite.pojo.VideoDialog;
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

public class PatientStaffInterviewActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.privacy_maintained_yes)
    RadioButton privacy_maintained_yes;

    @BindView(R.id.privacy_maintained_no)
    RadioButton privacy_maintained_no;

    @BindView(R.id.remark_privacy_maintained)
    ImageView remark_privacy_maintained;

    @BindView(R.id.nc_privacy_maintained)
    ImageView nc_privacy_maintained;

    @BindView(R.id.Image_privacy_maintained)
    ImageView Image_privacy_maintained;

    @BindView(R.id.patients_protected_physical_abuse_yes)
    RadioButton patients_protected_physical_abuse_yes;

    @BindView(R.id.patients_protected_physical_abuse_no)
    RadioButton patients_protected_physical_abuse_no;

    @BindView(R.id.remark_patients_protected_physical_abuse)
    ImageView remark_patients_protected_physical_abuse;

    @BindView(R.id.nc_patients_protected_physical_abuse)
    ImageView nc_patients_protected_physical_abuse;

    @BindView(R.id.patient_information_confidential_yes)
    RadioButton patient_information_confidential_yes;

    @BindView(R.id.patient_information_confidential_no)
    RadioButton patient_information_confidential_no;

    @BindView(R.id.remark_patient_information_confidential)
    ImageView remark_patient_information_confidential;

    @BindView(R.id.nc_patient_information_confidential)
    ImageView nc_patient_information_confidential;

    @BindView(R.id.patients_consent_carrying_yes)
    RadioButton patients_consent_carrying_yes;

    @BindView(R.id.patients_consent_carrying_no)
    RadioButton patients_consent_carrying_no;

    @BindView(R.id.remark_patients_consent_carrying)
    ImageView remark_patients_consent_carrying;

    @BindView(R.id.nc_patients_consent_carrying)
    ImageView nc_patients_consent_carrying;

    @BindView(R.id.patient_voice_complaint_yes)
    RadioButton patient_voice_complaint_yes;

    @BindView(R.id.patient_voice_complaint_no)
    RadioButton patient_voice_complaint_no;


    @BindView(R.id.remark_patient_voice_complaint)
    ImageView remark_patient_voice_complaint;

    @BindView(R.id.nc_patient_voice_complaint)
    ImageView nc_patient_voice_complaint;

    @BindView(R.id.patients_cost_treatment_yes)
    RadioButton patients_cost_treatment_yes;

    @BindView(R.id.patients_cost_treatment_no)
    RadioButton patients_cost_treatment_no;

    @BindView(R.id.remark_patients_cost_treatment)
    ImageView remark_patients_cost_treatment;

    @BindView(R.id.nc_patients_cost_treatment)
    ImageView nc_patients_cost_treatment;


    @BindView(R.id.patient_clinical_records_yes)
    RadioButton patient_clinical_records_yes;

    @BindView(R.id.patient_clinical_records_no)
    RadioButton patient_clinical_records_no;

    @BindView(R.id.remark_patient_clinical_records)
    ImageView remark_patient_clinical_records;

    @BindView(R.id.nc_patient_clinical_records)
    ImageView nc_patient_clinical_records;

    @BindView(R.id.patient_aware_plan_care_yes)
    RadioButton patient_aware_plan_care_yes;

    @BindView(R.id.patient_aware_plan_care_no)
     RadioButton patient_aware_plan_care_no;

    @BindView(R.id.remark_patient_aware_plan_care)
    ImageView remark_patient_aware_plan_care;

    @BindView(R.id.nc_patient_aware_plan_care)
    ImageView nc_patient_aware_plan_care;

    @BindView(R.id.Video_patient_aware_plan_care)
    ImageView Video_patient_aware_plan_care;

    @BindView(R.id.hospital__center)
    TextView hospital__center;


    private String remark1, remark2, remark3,remark4,remark5,remark6,remark7,remark8;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5,nc6,nc7,nc8;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status6,radio_status7,
            radio_status8;

    private DatabaseHandler databaseHandler;

    private String image1,video2;
    private String Local_image1,Local_video2;

    private File outputVideoFile;

    private String privacy_maintained = "",patients_protected_physical_abuse ="",patient_information_confidential="",patients_consent_carrying = "",patient_voice_complaint="",
            patients_cost_treatment = "",patient_clinical_records  = "",patient_aware_plan_care = "";

    private ArrayList<String>privacy_maintained_list;
    private ArrayList<String>Local_privacy_maintained_list;

    private Patient_Staff_InterviewPojo pojo;

    private APIService mAPIService;


    Uri videoUri;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    String privacy_maintained_view = "";

    @BindView(R.id.ll_patient_aware_plan_care)
    LinearLayout ll_patient_aware_plan_care;

    int Bed_no = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_staff);

        ButterKnife.bind(this);

        setDrawerbackIcon("Patient/Staff Interview");

        databaseHandler = DatabaseHandler.getInstance(this);

        pojo = new Patient_Staff_InterviewPojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital__center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();


        privacy_maintained_list = new ArrayList<>();
        Local_privacy_maintained_list = new ArrayList<>();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_patient_aware_plan_care.setVisibility(View.GONE);
        }else {
            ll_patient_aware_plan_care.setVisibility(View.VISIBLE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);


        getPharmacyData();

    }

    public void getPharmacyData(){

        pojo = databaseHandler.getPatientStaffPojo("8");

        if (pojo != null){
            sql_status = true;
            if (pojo.getPrivacy_maintained() != null){
                privacy_maintained = pojo.getPrivacy_maintained();
                if (pojo.getPrivacy_maintained().equalsIgnoreCase("Yes")){
                    privacy_maintained_yes.setChecked(true);
                }else if (pojo.getPrivacy_maintained().equalsIgnoreCase("No")){
                    privacy_maintained_no.setChecked(true);
                }
            }
            if (pojo.getPatients_protected_physical_abuse() != null){
                patients_protected_physical_abuse = pojo.getPatients_protected_physical_abuse();
                if (pojo.getPatients_protected_physical_abuse().equalsIgnoreCase("Yes")){
                    patients_protected_physical_abuse_yes.setChecked(true);
                }else if (pojo.getPatients_protected_physical_abuse().equalsIgnoreCase("No")){
                    patients_protected_physical_abuse_no.setChecked(true);
                }
            }
            if (pojo.getPatient_information_confidential() != null){
                patient_information_confidential = pojo.getPatient_information_confidential();
                if (pojo.getPatient_information_confidential().equalsIgnoreCase("Yes")){
                    patient_information_confidential_yes.setChecked(true);
                }else if (pojo.getPatient_information_confidential().equalsIgnoreCase("No")){
                    patient_information_confidential_no.setChecked(true);
                }
            } if (pojo.getPatients_consent_carrying() != null){
                patients_consent_carrying = pojo.getPatients_consent_carrying();
                if (pojo.getPatients_consent_carrying().equalsIgnoreCase("Yes")){
                    patients_consent_carrying_yes.setChecked(true);
                }else if (pojo.getPatients_consent_carrying().equalsIgnoreCase("No")){
                    patients_consent_carrying_no.setChecked(true);
                }
            }
            if (pojo.getPatient_voice_complaint() != null){
                patient_voice_complaint = pojo.getPatient_voice_complaint();
                if (pojo.getPatient_voice_complaint().equalsIgnoreCase("Yes")){
                    patient_voice_complaint_yes.setChecked(true);
                }else if (pojo.getPatient_voice_complaint().equalsIgnoreCase("No")){
                    patient_voice_complaint_no.setChecked(true);
                }
            }

            if (pojo.getPatients_cost_treatment() != null){
                patients_cost_treatment = pojo.getPatients_cost_treatment();
                if (pojo.getPatients_cost_treatment().equalsIgnoreCase("Yes")){
                    patients_cost_treatment_yes.setChecked(true);
                }else if (pojo.getPatients_cost_treatment().equalsIgnoreCase("No")){
                    patients_cost_treatment_no.setChecked(true);
                }
            }

            if (pojo.getPatient_clinical_records() != null){
                patient_clinical_records = pojo.getPatient_clinical_records();
                if (pojo.getPatient_clinical_records().equalsIgnoreCase("Yes")){
                    patient_clinical_records_yes.setChecked(true);
                }else if (pojo.getPatient_clinical_records().equalsIgnoreCase("No")){
                    patient_clinical_records_no.setChecked(true);
                }
            }

            if (pojo.getPatient_aware_plan_care() != null){
                patient_aware_plan_care = pojo.getPatient_aware_plan_care();
                if (pojo.getPatient_aware_plan_care().equalsIgnoreCase("Yes")){
                    patient_aware_plan_care_yes.setChecked(true);
                }else if (pojo.getPatient_aware_plan_care().equalsIgnoreCase("No")){
                    patient_aware_plan_care_no.setChecked(true);
                }
            }



            if (pojo.getPrivacy_maintained_remark() != null){
                remark_privacy_maintained.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getPrivacy_maintained_remark();
            }
            if (pojo.getPrivacy_maintained_nc() != null){
                nc1 = pojo.getPrivacy_maintained_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_privacy_maintained.setImageResource(R.mipmap.nc);
                }else {
                    nc_privacy_maintained.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getPrivacy_maintained_image() != null){
                Image_privacy_maintained.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getPrivacy_maintained_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            privacy_maintained_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_privacy_maintained_image() != null){
                Image_privacy_maintained.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_privacy_maintained_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_privacy_maintained_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getPatients_protected_physical_abuse_remark() != null){
                remark2 = pojo.getPatients_protected_physical_abuse_remark();

                remark_patients_protected_physical_abuse.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatients_protected_physical_abuse_nc() != null){
                nc2 = pojo.getPatients_protected_physical_abuse_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_patients_protected_physical_abuse.setImageResource(R.mipmap.nc);
                }else {
                    nc_patients_protected_physical_abuse.setImageResource(R.mipmap.nc_selected);
                }


            }


            if (pojo.getPatient_information_confidential_remark() != null){
                remark3 = pojo.getPatient_information_confidential_remark();

                remark_patient_information_confidential.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_information_confidential_nc() != null){
                nc3 = pojo.getPatient_information_confidential_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_patient_information_confidential.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_information_confidential.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getPatients_consent_carrying_remark() != null){
                remark4 = pojo.getPatients_consent_carrying_remark();

                remark_patients_consent_carrying.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatients_consent_carrying_nc() != null){
                nc4 = pojo.getPatients_consent_carrying_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_patients_consent_carrying.setImageResource(R.mipmap.nc);
                }else {
                    nc_patients_consent_carrying.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getPatient_voice_complaint_remark() != null){
                remark5 = pojo.getPatient_voice_complaint_remark();

                remark_patient_voice_complaint.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_voice_complaint_nc() != null){
                nc5 = pojo.getPatient_voice_complaint_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_patients_consent_carrying.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_voice_complaint.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getPatients_cost_treatment_remark() != null){
                remark6 = pojo.getPatients_cost_treatment_remark();

                remark_patients_cost_treatment.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatients_cost_treatment_nc() != null){
                nc6 = pojo.getPatients_cost_treatment_nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_patients_cost_treatment.setImageResource(R.mipmap.nc);
                }else {
                    nc_patients_cost_treatment.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getPatient_clinical_records_remark() != null){
                remark7 = pojo.getPatient_clinical_records_remark();

                remark_patient_clinical_records.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_clinical_records_nc() != null){
                nc7 = pojo.getPatient_clinical_records_nc();

                if (nc7.equalsIgnoreCase("close")){
                    nc_patient_clinical_records.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_clinical_records.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getPatient_aware_plan_care_remark() != null){
                remark8 = pojo.getPatient_aware_plan_care_remark();

                remark_patient_aware_plan_care.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_aware_plan_care_nc() != null){
                nc8 = pojo.getPatient_aware_plan_care_nc();

                if (nc8.equalsIgnoreCase("close")){
                    nc_patient_aware_plan_care.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_aware_plan_care.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getPatient_aware_plan_care_video() != null){
                video2 = pojo.getPatient_aware_plan_care_video();

                Video_patient_aware_plan_care.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getLocal_patient_aware_plan_care_video() != null){
                Local_video2 = pojo.getLocal_patient_aware_plan_care_video();

                Video_patient_aware_plan_care.setImageResource(R.mipmap.camera_selected);
            }

        }else {
            pojo = new Patient_Staff_InterviewPojo();
        }

    }


    @OnClick({R.id.remark_privacy_maintained,R.id.nc_privacy_maintained,R.id.Image_privacy_maintained,R.id.remark_patients_protected_physical_abuse,R.id.nc_patients_protected_physical_abuse,R.id.remark_patient_information_confidential,R.id.nc_patient_information_confidential,
            R.id.remark_patients_consent_carrying,R.id.nc_patients_consent_carrying,R.id.remark_patient_voice_complaint,
            R.id.nc_patient_voice_complaint,R.id.remark_patients_cost_treatment,R.id.nc_patients_cost_treatment,
            R.id.remark_patient_clinical_records,R.id.nc_patient_clinical_records,R.id.remark_patient_aware_plan_care,R.id.nc_patient_aware_plan_care,R.id.Video_patient_aware_plan_care,
           R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_privacy_maintained:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_privacy_maintained:
                displayNCDialog("NC", 1);
                break;

            case R.id.Image_privacy_maintained:
                if (Local_privacy_maintained_list.size() > 0){
                    showImageListDialog(Local_privacy_maintained_list,1,"privacy_maintained");
                }else {
                    captureImage(1);
                }

                break;
            case R.id.remark_patients_protected_physical_abuse:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_patients_protected_physical_abuse:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_patient_information_confidential:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_patient_information_confidential:
                displayNCDialog("NC", 3);
                break;


            case R.id.remark_patients_consent_carrying:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_patients_consent_carrying:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_patient_voice_complaint:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_patient_voice_complaint:
                displayNCDialog("NC", 5);
                break;


            case R.id.remark_patients_cost_treatment:
                displayCommonDialogWithHeader("Remark", 6);
                break;

            case R.id.nc_patients_cost_treatment:
                displayNCDialog("NC", 6);
                break;


            case R.id.remark_patient_clinical_records:
                displayCommonDialogWithHeader("Remark", 7);
                break;

            case R.id.nc_patient_clinical_records:
                displayNCDialog("NC", 7);
                break;

            case R.id.remark_patient_aware_plan_care:
                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_patient_aware_plan_care:
                displayNCDialog("NC", 8);
                break;

            case R.id.Video_patient_aware_plan_care:
                if (Local_video2 != null){
                    showVideoDialog(Local_video2,2);
                }else {
                    captureVideo(2);
                }

                break;

            case R.id.btnSave:
                SavePharmacyData("save","");
                break;

            case R.id.btnSync:
                if (Bed_no < 51){
                    if (privacy_maintained.length() > 0 && patients_protected_physical_abuse.length() > 0 && patient_information_confidential.length() > 0 && patients_consent_carrying.length() > 0 && patient_voice_complaint.length() > 0 &&
                            patients_cost_treatment.length() > 0 && patient_clinical_records.length() > 0 ){

                        if (image1 != null){
                            SavePharmacyData("sync","shco");
                        }else {
                            Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }

                }else {
                    if (privacy_maintained.length() > 0 && patients_protected_physical_abuse.length() > 0 && patient_information_confidential.length() > 0 && patients_consent_carrying.length() > 0 && patient_voice_complaint.length() > 0 &&
                            patients_cost_treatment.length() > 0 && patient_clinical_records.length() > 0 && patient_aware_plan_care.length() > 0){

                        if (image1 != null){
                            SavePharmacyData("sync","hco");
                        }else {
                            Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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
            case R.id.privacy_maintained_yes:
                if (checked)
                    privacy_maintained = "Yes";
                break;

            case R.id.privacy_maintained_no:
                if (checked)
                    privacy_maintained = "No";
                break;

            case R.id.patients_protected_physical_abuse_yes:
                patients_protected_physical_abuse = "Yes";
                break;

            case R.id.patients_protected_physical_abuse_no:
                patients_protected_physical_abuse = "No";
                break;

            case R.id.patient_information_confidential_yes:
                if (checked)
                    patient_information_confidential = "Yes";
                break;
            case R.id.patient_information_confidential_no:
                if (checked)
                    patient_information_confidential = "No";
                break;


            case R.id.patients_consent_carrying_yes:
                if (checked)
                    patients_consent_carrying = "Yes";
                break;
            case R.id.patients_consent_carrying_no:
                if (checked)
                    patients_consent_carrying = "No";
                break;

            case R.id.patient_voice_complaint_yes:
                patient_voice_complaint = "Yes";
                break;

            case R.id.patient_voice_complaint_no:
                patient_voice_complaint = "No";
                break;


            case R.id.patients_cost_treatment_yes:
                patients_cost_treatment = "Yes";
                break;

            case R.id.patients_cost_treatment_no:
                patients_cost_treatment = "No";
                break;

            case R.id.patient_clinical_records_yes:
                patient_clinical_records = "Yes";
                break;

            case R.id.patient_clinical_records_no:
                patient_clinical_records = "No";
                break;

            case R.id.patient_aware_plan_care_yes:
                patient_aware_plan_care = "Yes";
                break;

            case R.id.patient_aware_plan_care_no:
                patient_aware_plan_care = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(PatientStaffInterviewActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

    private void captureVideo(int position){
        File dirVideo = new File(Environment.getExternalStorageDirectory(), "OnSite/videos/");
        if (!dirVideo.exists()) {
            dirVideo.mkdirs();
        }
        outputVideoFile = new File(dirVideo.getAbsolutePath() + System.currentTimeMillis() + ".mp4");
        if (!outputVideoFile.exists()) {
            try {
                outputVideoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        videoUri = Uri.fromFile(outputVideoFile);


        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(PatientStaffInterviewActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, 2);
    }

    public void showVideoDialog(final String path,final int pos) {
        dialogLogout = new Dialog(PatientStaffInterviewActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.video_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button ll_retry = (Button) dialogLogout.findViewById(R.id.btn_yes_retry);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        final ImageView imageView = (ImageView) dialogLogout.findViewById(R.id.camera_view);
        dialogLogout.show();

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

        ll_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
                File dirVideo = new File(Environment.getExternalStorageDirectory(), "OnSite/videos/");
                if (!dirVideo.exists()) {
                    dirVideo.mkdirs();
                }
                outputVideoFile = new File(dirVideo.getAbsolutePath() + System.currentTimeMillis() + ".mp4");
                if (!outputVideoFile.exists()) {
                    try {
                        outputVideoFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                videoUri = Uri.fromFile(outputVideoFile);


                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(PatientStaffInterviewActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
                startActivityForResult(intent, 2);
            }
        });

        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
        imageView.setImageBitmap(bmThumbnail);

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    ImageUpload(image2,"privacy_maintained");

                }

            }

            else if (requestCode == 2) {
                if (resultCode == RESULT_OK) {

                    VideoUpload(String.valueOf(outputVideoFile),"case_of_grievances");

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(PatientStaffInterviewActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_privacy_maintained.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_privacy_maintained.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {

                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_patients_protected_physical_abuse.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patients_protected_physical_abuse.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_patient_information_confidential.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patient_information_confidential.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_patients_consent_carrying.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patients_consent_carrying.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_patient_voice_complaint.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patient_voice_complaint.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_patients_cost_treatment.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_patients_cost_treatment.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 7) {
                        if (radio_status7 != null) {

                            if (radio_status7.equalsIgnoreCase("close")) {

                                nc_patient_clinical_records.setImageResource(R.mipmap.nc);

                                nc7 = radio_status7;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status7.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_patient_clinical_records.setImageResource(R.mipmap.nc_selected);

                                    nc7 = radio_status7 + "," + edit_text.getText().toString();

                                    radio_status7 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_patient_aware_plan_care.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_patient_aware_plan_care.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(PatientStaffInterviewActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(PatientStaffInterviewActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_privacy_maintained.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_patients_protected_physical_abuse.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_patient_information_confidential.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_patients_consent_carrying.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_patient_voice_complaint.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_patients_cost_treatment.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 7) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark7 = edit_text.getText().toString();
                            remark_patient_clinical_records.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_patient_aware_plan_care.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(PatientStaffInterviewActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }



                }
            });
            DialogLogOut.show();
        }
    }

    private void VideoUpload(String image_path,final String from){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(PatientStaffInterviewActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(PatientStaffInterviewActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();

                        if (from.equalsIgnoreCase("case_of_grievances")){
                            video2 = response.body().getMessage();
                            Local_video2 = String.valueOf(outputVideoFile);
                            Video_patient_aware_plan_care.setImageResource(R.mipmap.camera_selected);
                        }


                    }else {
                        Toast.makeText(PatientStaffInterviewActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(PatientStaffInterviewActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(PatientStaffInterviewActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(PatientStaffInterviewActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(PatientStaffInterviewActivity.this,list,from,"patientStaff");
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



    public void SavePharmacyData(String from,String hospital_status){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(8);
        if (getFromPrefs("PatientStaff_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("PatientStaff_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }

        pojo.setPrivacy_maintained(privacy_maintained);
        pojo.setPatients_protected_physical_abuse(patients_protected_physical_abuse);
        pojo.setPatient_information_confidential(patient_information_confidential);
        pojo.setPatients_consent_carrying(patients_consent_carrying);
        pojo.setPatient_voice_complaint(patient_voice_complaint);
        pojo.setPatients_cost_treatment(patients_cost_treatment);
        pojo.setPatient_clinical_records(patient_clinical_records);
        pojo.setPatient_aware_plan_care(patient_aware_plan_care);


        pojo.setPrivacy_maintained_remark(remark1);
        pojo.setPrivacy_maintained_nc(nc1);


        JSONObject json = new JSONObject();

        if (privacy_maintained_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(privacy_maintained_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_privacy_maintained_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_privacy_maintained_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }

        pojo.setPrivacy_maintained_image(image1);
        pojo.setLocal_privacy_maintained_image(Local_image1);

        pojo.setPatients_protected_physical_abuse_remark(remark2);
        pojo.setPatients_protected_physical_abuse_nc(nc2);


        pojo.setPatient_information_confidential_remark(remark3);
        pojo.setPatient_information_confidential_nc(nc3);



        pojo.setPatients_consent_carrying_remark(remark4);
        pojo.setPatients_consent_carrying_nc(nc4);

        pojo.setPatient_voice_complaint_remark(remark5);
        pojo.setPatient_voice_complaint_nc(nc5);


        pojo.setPatients_cost_treatment_remark(remark6);
        pojo.setPatients_cost_treatment_nc(nc6);


        pojo.setPatient_clinical_records_remark(remark7);
        pojo.setPatient_clinical_records_nc(nc7);

        pojo.setPatient_aware_plan_care_remark(remark8);
        pojo.setPatient_aware_plan_care_nc(nc8);

        pojo.setPatient_aware_plan_care_video(video2);
        pojo.setLocal_patient_aware_plan_care_video(Local_video2);


        if (sql_status){
            boolean st_status = databaseHandler.UPDATE_PATIENT_STAFF(pojo);

            if (st_status){
                if (!from.equalsIgnoreCase("sync")){

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(8).getHospital_id());
                    pojo.setAssessement_name("Patient/Staff Interview");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(8).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                    Toast.makeText(PatientStaffInterviewActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PatientStaffInterviewActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        progreesDialog();

                        PostLaboratory_SHCO_Data();
                    }else {
                        progreesDialog();

                        PostLaboratoryData();
                    }
                }
            }
        }else {
            boolean st_status = databaseHandler.INSERT_PATIENT_STAFF(pojo);
            if (st_status){
                if (!from.equalsIgnoreCase("sync")){

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(8).getHospital_id());
                    pojo.setAssessement_name("Patient/Staff Interview");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(8).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                    Toast.makeText(PatientStaffInterviewActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PatientStaffInterviewActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        progreesDialog();

                        PostLaboratory_SHCO_Data();
                    }else {
                        progreesDialog();

                        PostLaboratoryData();
                    }
                }
            }
        }



    }

    private void PostLaboratoryData(){

                pojo_dataSync.setTabName("patientstaffinterview");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<privacy_maintained_list.size();i++){
                    String value = privacy_maintained_list.get(i);

                    privacy_maintained_view = value + privacy_maintained_view;
                }

                pojo.setPrivacy_maintained_image(privacy_maintained_view);

                pojo_dataSync.setPatientStaffInterview(pojo);


                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(PatientStaffInterviewActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(PatientStaffInterviewActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(PatientStaffInterviewActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("PatientStaff_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(8).getHospital_id());
                                    pojo.setAssessement_name("Patient/Staff Interview");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(8).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private void PostLaboratory_SHCO_Data(){

                pojo_dataSync.setTabName("patientstaffinterview");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<privacy_maintained_list.size();i++){
                    String value = privacy_maintained_list.get(i);

                    privacy_maintained_view = value + privacy_maintained_view;
                }

                pojo.setPrivacy_maintained_image(privacy_maintained_view);

                pojo_dataSync.setPatientStaffInterview(pojo);



                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(PatientStaffInterviewActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(PatientStaffInterviewActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(PatientStaffInterviewActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("PatientStaff_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(8).getHospital_id());
                                    pojo.setAssessement_name("Patient/Staff Interview");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(8).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(PatientStaffInterviewActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

        final ProgressDialog d = ImageDialog.showLoading(PatientStaffInterviewActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(PatientStaffInterviewActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(PatientStaffInterviewActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("privacy_maintained")){
                                privacy_maintained_list.add(response.body().getMessage());
                                Local_privacy_maintained_list.add(image_path);
                                Image_privacy_maintained.setImageResource(R.mipmap.camera_selected);

                                image1 = "privacy_maintained";
                            }

                            Toast.makeText(PatientStaffInterviewActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(PatientStaffInterviewActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(PatientStaffInterviewActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(PatientStaffInterviewActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("privacy_maintained")){
                Local_privacy_maintained_list.remove(position);
                privacy_maintained_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (privacy_maintained_list.size() == 0){
                    Image_privacy_maintained.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(8).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save","");
        }else {
            Intent intent = new Intent(PatientStaffInterviewActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
