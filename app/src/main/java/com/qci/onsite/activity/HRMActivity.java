package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
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
import com.qci.onsite.pojo.HRMPojo;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.VideoDialog;
import com.qci.onsite.pojo.Ward_Ot_EmergencyPojo;
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

public class HRMActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.staff_health_related_issues_yes)
    RadioButton staff_health_related_issues_yes;

    @BindView(R.id.staff_health_related_issues_no)
    RadioButton staff_health_related_issues_no;

    @BindView(R.id.remark_staff_health_related_issues)
    ImageView remark_staff_health_related_issues;

    @BindView(R.id.nc_staff_health_related_issues)
    ImageView nc_staff_health_related_issues;

    @BindView(R.id.staffs_personal_files_maintained_yes)
    RadioButton staffs_personal_files_maintained_yes;

    @BindView(R.id.staffs_personal_files_maintained_no)
    RadioButton staffs_personal_files_maintained_no;

    @BindView(R.id.remark_staffs_personal_files_maintained)
    ImageView remark_staffs_personal_files_maintained;

    @BindView(R.id.nc_staffs_personal_files_maintained)
    ImageView nc_staffs_personal_files_maintained;

    @BindView(R.id.image_staffs_personal_files_maintained)
    ImageView image_staffs_personal_files_maintained;

    @BindView(R.id.occupational_health_hazards_yes)
    RadioButton occupational_health_hazards_yes;

    @BindView(R.id.occupational_health_hazards_no)
    RadioButton occupational_health_hazards_no;

    @BindView(R.id.remark_occupational_health_hazards)
    ImageView remark_occupational_health_hazards;

    @BindView(R.id.nc_occupational_health_hazards)
    ImageView nc_occupational_health_hazards;

    @BindView(R.id.training_responsibility_changes_yes)
    RadioButton training_responsibility_changes_yes;

    @BindView(R.id.training_responsibility_changes_no)
    RadioButton training_responsibility_changes_no;

    @BindView(R.id.remark_training_responsibility_changes)
    ImageView remark_training_responsibility_changes;

    @BindView(R.id.nc_training_responsibility_changes)
    ImageView nc_training_responsibility_changesl;

    @BindView(R.id.medical_records_doctors_retrievable_yes)
    RadioButton medical_records_doctors_retrievable_yes;

    @BindView(R.id.medical_records_doctors_retrievable_no)
    RadioButton medical_records_doctors_retrievable_no;

    @BindView(R.id.remark_medical_records_doctors_retrievable)
    ImageView remark_medical_records_doctors_retrievablel;

    @BindView(R.id.nc_medical_records_doctors_retrievable)
    ImageView nc_medical_records_doctors_retrievable;

    @BindView(R.id.case_of_grievances_yes)
    RadioButton case_of_grievances_yes;

    @BindView(R.id.case_of_grievances_no)
    RadioButton case_of_grievances_no;

    @BindView(R.id.remark_case_of_grievances)
    ImageView remark_case_of_grievances;

    @BindView(R.id.nc_case_of_grievances)
    ImageView nc_case_of_grievances;

    @BindView(R.id.Video_case_of_grievances)
    ImageView Video_case_of_grievances;

    @BindView(R.id.staff_disciplinary_procedure_yes)
    RadioButton staff_disciplinary_procedure_yes;

    @BindView(R.id.staff_disciplinary_procedure_no)
    RadioButton staff_disciplinary_procedure_no;

    @BindView(R.id.remark_staff_disciplinary_procedure)
    ImageView remark_staff_disciplinary_procedure;

    @BindView(R.id.nc_staff_disciplinary_procedure)
    ImageView nc_staff_disciplinary_procedure;

    @BindView(R.id.Video_staff_disciplinary_procedure)
    ImageView Video_staff_disciplinary_procedure;

    @BindView(R.id.staff_able_to_demonstrate_yes)
    RadioButton staff_able_to_demonstrate_yes;

    @BindView(R.id.staff_able_to_demonstrate_no)
    RadioButton staff_able_to_demonstrate_no;

    @BindView(R.id.remark_staff_able_to_demonstrate)
    ImageView remark_staff_able_to_demonstrate;

    @BindView(R.id.nc_staff_able_to_demonstrate)
    ImageView nc_staff_able_to_demonstrate;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    private String remark1, remark2, remark3,remark4,remark5,remark9,remark10,remark11;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5,nc9,nc10,nc11;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status9,radio_status10,radio_status11;

    private DatabaseHandler databaseHandler;

    private APIService mAPIService;

    private String staff_health_related_issues = "",staffs_personal_files_maintained ="",occupational_health_hazards="",training_responsibility_changes = "",medical_records_doctors_retrievable="",case_of_grievances="",

    staff_disciplinary_procedure = "",staff_able_to_demonstrate ="";


    private ArrayList<String>staffs_personal_files_maintained_list;
    private ArrayList<String>Local_staffs_personal_files_maintained_list;

    private HRMPojo pojo;

    private String image1,video2,video3;
    private String Local_image1,Local_video2,Local_video3;


    private File outputVideoFile;

    Uri videoUri;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;

    String admissions_discharge_home_view = "";
    
    int check;
    CountDownLatch latch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrm);

        ButterKnife.bind(this);

        setDrawerbackIcon("HRM");

        mAPIService = ApiUtils.getAPIService();

        databaseHandler = DatabaseHandler.getInstance(this);

        staffs_personal_files_maintained_list = new ArrayList<>();
        Local_staffs_personal_files_maintained_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = new ArrayList<>();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        pojo_dataSync = new DataSyncRequest();

        pojo = new HRMPojo();

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();
    }

    public void getPharmacyData(){

        pojo = databaseHandler.getHRMPojo("10");

        if (pojo != null){
            sql_status = true;
            if (pojo.getStaff_health_related_issues() != null){
                staff_health_related_issues = pojo.getStaff_health_related_issues();
                if (pojo.getStaff_health_related_issues().equalsIgnoreCase("Yes")){
                    staff_health_related_issues_yes.setChecked(true);
                }else if (pojo.getStaff_health_related_issues().equalsIgnoreCase("No")){
                    staff_health_related_issues_no.setChecked(true);
                }
            }
            if (pojo.getStaffs_personal_files_maintained() != null){
                staffs_personal_files_maintained = pojo.getStaffs_personal_files_maintained();
                if (pojo.getStaffs_personal_files_maintained().equalsIgnoreCase("Yes")){
                    staffs_personal_files_maintained_yes.setChecked(true);
                }else if (pojo.getStaffs_personal_files_maintained().equalsIgnoreCase("No")){
                    staffs_personal_files_maintained_no.setChecked(true);
                }
            }
            if (pojo.getOccupational_health_hazards() != null){
                occupational_health_hazards = pojo.getOccupational_health_hazards();
                if (pojo.getOccupational_health_hazards().equalsIgnoreCase("Yes")){
                    occupational_health_hazards_yes.setChecked(true);
                }else if (pojo.getOccupational_health_hazards().equalsIgnoreCase("No")){
                    occupational_health_hazards_no.setChecked(true);
                }
            } if (pojo.getTraining_responsibility_changes() != null){
                training_responsibility_changes = pojo.getTraining_responsibility_changes();
                if (pojo.getTraining_responsibility_changes().equalsIgnoreCase("Yes")){
                    training_responsibility_changes_yes.setChecked(true);
                }else if (pojo.getTraining_responsibility_changes().equalsIgnoreCase("No")){
                    training_responsibility_changes_no.setChecked(true);
                }
            }
            if (pojo.getMedical_records_doctors_retrievable() != null){
                medical_records_doctors_retrievable = pojo.getMedical_records_doctors_retrievable();
                if (pojo.getMedical_records_doctors_retrievable().equalsIgnoreCase("Yes")){
                    medical_records_doctors_retrievable_yes.setChecked(true);
                }else if (pojo.getMedical_records_doctors_retrievable().equalsIgnoreCase("No")){
                    medical_records_doctors_retrievable_no.setChecked(true);
                }
            }

            if (pojo.getCase_of_grievances() != null){
                case_of_grievances = pojo.getCase_of_grievances();
                if (pojo.getCase_of_grievances().equalsIgnoreCase("Yes")){
                    case_of_grievances_yes.setChecked(true);
                }else if (pojo.getCase_of_grievances().equalsIgnoreCase("No")){
                    case_of_grievances_no.setChecked(true);
                }
            }

            if (pojo.getStaff_disciplinary_procedure() != null){
                staff_disciplinary_procedure = pojo.getStaff_disciplinary_procedure();
                if (pojo.getStaff_disciplinary_procedure().equalsIgnoreCase("Yes")){
                    staff_disciplinary_procedure_yes.setChecked(true);
                }else if (pojo.getStaff_disciplinary_procedure().equalsIgnoreCase("No")){
                    staff_disciplinary_procedure_no.setChecked(true);
                }
            }

            if (pojo.getStaff_able_to_demonstrate() != null){
                staff_able_to_demonstrate = pojo.getStaff_able_to_demonstrate();
                if (pojo.getStaff_able_to_demonstrate().equalsIgnoreCase("Yes")){
                    staff_able_to_demonstrate_yes.setChecked(true);
                }else if (pojo.getStaff_able_to_demonstrate().equalsIgnoreCase("No")){
                    staff_able_to_demonstrate_no.setChecked(true);
                }
            }


            if (pojo.getStaff_health_related_issues_remark() != null){
                remark_staff_health_related_issues.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getStaff_health_related_issues_remark();
            }
            if (pojo.getStaff_health_related_issues_nc() != null){
                nc1 = pojo.getStaff_health_related_issues_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_staff_health_related_issues.setImageResource(R.mipmap.nc);
                }else {
                    nc_staff_health_related_issues.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getStaffs_personal_files_maintained_remark() != null){
                remark2 = pojo.getStaffs_personal_files_maintained_remark();

                remark_staffs_personal_files_maintained.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStaffs_personal_files_maintained_nc() != null){
                nc2 = pojo.getStaffs_personal_files_maintained_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_staffs_personal_files_maintained.setImageResource(R.mipmap.nc);
                }else {
                    nc_staffs_personal_files_maintained.setImageResource(R.mipmap.nc_selected);
                }

            }
            if (pojo.getStaffs_personal_files_maintained_image() != null){
                image_staffs_personal_files_maintained.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getStaffs_personal_files_maintained_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            staffs_personal_files_maintained_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_staffs_personal_files_maintained_image() != null){
                image_staffs_personal_files_maintained.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_staffs_personal_files_maintained_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_staffs_personal_files_maintained_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getOccupational_health_hazards_remark() != null){
                remark3 = pojo.getOccupational_health_hazards_remark();

                remark_occupational_health_hazards.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getOccupational_health_hazards_nc() != null){
                nc3 = pojo.getOccupational_health_hazards_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_occupational_health_hazards.setImageResource(R.mipmap.nc);
                }else {
                    nc_occupational_health_hazards.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getTraining_responsibility_changes_remark() != null){
                remark4 = pojo.getTraining_responsibility_changes_remark();

                remark_training_responsibility_changes.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getTraining_responsibility_changes_nc() != null){
                nc4 = pojo.getTraining_responsibility_changes_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_training_responsibility_changesl.setImageResource(R.mipmap.nc);
                }else {
                    nc_training_responsibility_changesl.setImageResource(R.mipmap.nc_selected);
                }
            }



            if (pojo.getMedical_records_doctors_retrievable_remark() != null){
                remark5 = pojo.getMedical_records_doctors_retrievable_remark();

                remark_medical_records_doctors_retrievablel.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getMedical_records_doctors_retrievable_nc() != null){
                nc5 = pojo.getMedical_records_doctors_retrievable_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_medical_records_doctors_retrievable.setImageResource(R.mipmap.nc);
                }else {
                    nc_medical_records_doctors_retrievable.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getCase_of_grievances_remark() != null){
                remark9 = pojo.getCase_of_grievances_remark();

                remark_case_of_grievances.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getCase_of_grievances_nc() != null){
                nc9 = pojo.getCase_of_grievances_nc();

                if (nc9.equalsIgnoreCase("close")){
                    nc_case_of_grievances.setImageResource(R.mipmap.nc);
                }else {
                    nc_case_of_grievances.setImageResource(R.mipmap.nc_selected);
                }
            }
            if (pojo.getCase_of_grievances_video() != null){
                video2 = pojo.getCase_of_grievances_video();

                Video_case_of_grievances.setImageResource(R.mipmap.camera_selected);
            }

            if (pojo.getLocal_case_of_grievances_image() != null){
                Local_video2 = pojo.getLocal_case_of_grievances_image();

            }


            if (pojo.getStaff_disciplinary_procedure_remark() != null){
                remark10 = pojo.getStaff_disciplinary_procedure_remark();

                remark_staff_disciplinary_procedure.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStaff_disciplinary_procedure_nc() != null){
                nc10 = pojo.getStaff_disciplinary_procedure_nc();

                if (nc10.equalsIgnoreCase("close")){
                    nc_staff_disciplinary_procedure.setImageResource(R.mipmap.nc);
                }else {
                    nc_staff_disciplinary_procedure.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getStaff_disciplinary_procedure_video() != null){
                video3 = pojo.getStaff_disciplinary_procedure_video();

                Video_staff_disciplinary_procedure.setImageResource(R.mipmap.camera_selected);
            }

            if (pojo.getLocal_staff_disciplinary_procedure_video() != null){
                Local_video3 = pojo.getLocal_staff_disciplinary_procedure_video();
            }


            if (pojo.getStaff_able_to_demonstrate_remark() != null){
                remark11 = pojo.getStaff_able_to_demonstrate_remark();

                remark_staff_able_to_demonstrate.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStaff_able_to_demonstrate_nc() != null){
                nc11 = pojo.getStaff_able_to_demonstrate_nc();

                if (nc11.equalsIgnoreCase("close")){
                    nc_staff_able_to_demonstrate.setImageResource(R.mipmap.nc_selected);
                }else {
                    nc_staff_able_to_demonstrate.setImageResource(R.mipmap.nc_selected);
                }
            }
        }else {
            pojo = new HRMPojo();
        }

    }


    @OnClick({R.id.remark_staff_health_related_issues,R.id.nc_staff_health_related_issues,R.id.remark_staffs_personal_files_maintained,R.id.nc_staffs_personal_files_maintained,R.id.remark_occupational_health_hazards,R.id.nc_occupational_health_hazards,
            R.id.remark_training_responsibility_changes,R.id.nc_training_responsibility_changes,R.id.remark_medical_records_doctors_retrievable,
            R.id.nc_medical_records_doctors_retrievable,R.id.image_staffs_personal_files_maintained, R.id.remark_case_of_grievances,R.id.nc_case_of_grievances,R.id.Video_case_of_grievances,R.id.remark_staff_disciplinary_procedure,
            R.id.nc_staff_disciplinary_procedure,R.id.Video_staff_disciplinary_procedure,R.id.remark_staff_able_to_demonstrate,R.id.nc_staff_able_to_demonstrate,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_staff_health_related_issues:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_staff_health_related_issues:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_staffs_personal_files_maintained:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_staffs_personal_files_maintained:
                displayNCDialog("NC", 2);
                break;

            case R.id.image_staffs_personal_files_maintained:
                if (Local_staffs_personal_files_maintained_list.size() > 0){
                    showImageListDialog(Local_staffs_personal_files_maintained_list,1,"staffs_personal_files_maintained");
                }else {
                    captureImage(1);
                }

                break;


            case R.id.remark_occupational_health_hazards:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_occupational_health_hazards:
                displayNCDialog("NC", 3);
                break;


            case R.id.remark_training_responsibility_changes:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_training_responsibility_changes:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_medical_records_doctors_retrievable:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_medical_records_doctors_retrievable:
                displayNCDialog("NC", 5);
                break;

            case R.id.remark_case_of_grievances:
                displayCommonDialogWithHeader("Remark", 9);
                break;

            case R.id.nc_case_of_grievances:
                displayNCDialog("NC", 9);
                break;

            case R.id.Video_case_of_grievances:
                if (Local_video2 != null){
                    showVideoDialog(Local_video2,2);
                }else {
                    captureVideo(2);
                }
                break;

            case R.id.remark_staff_disciplinary_procedure:
                displayCommonDialogWithHeader("Remark", 10);
                break;

            case R.id.nc_staff_disciplinary_procedure:
                displayNCDialog("NC", 10);
                break;

            case R.id.Video_staff_disciplinary_procedure:
                if (Local_video3 != null){
                    showVideoDialog(Local_video3,3);
                }else {
                    captureVideo(3);
                }

                break;

            case R.id.remark_staff_able_to_demonstrate:
                displayCommonDialogWithHeader("Remark", 11);
                break;

            case R.id.nc_staff_able_to_demonstrate:
                displayNCDialog("NC", 11);
                break;

            case R.id.btnSave:
                SavePharmacyData("");
                break;

            case R.id.btnSync:

                if (staff_health_related_issues.length() > 0 && staffs_personal_files_maintained.length() > 0 && occupational_health_hazards.length() > 0 && training_responsibility_changes.length() > 0 && medical_records_doctors_retrievable.length() > 0 &&
                        case_of_grievances.length() > 0 && staff_disciplinary_procedure.length() > 0 && staff_able_to_demonstrate.length() > 0){
                    if (Local_image1 != null){
                        SavePharmacyData("sync");
                    }else {
                        Toast.makeText(HRMActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HRMActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.staff_health_related_issues_yes:
                if (checked)
                    staff_health_related_issues = "Yes";
                break;

            case R.id.staff_health_related_issues_no:
                if (checked)
                    staff_health_related_issues = "No";
                break;

            case R.id.staffs_personal_files_maintained_yes:
                staffs_personal_files_maintained = "Yes";
                break;

            case R.id.staffs_personal_files_maintained_no:
                staffs_personal_files_maintained = "No";
                break;

            case R.id.occupational_health_hazards_yes:
                if (checked)
                    occupational_health_hazards = "Yes";
                break;
            case R.id.occupational_health_hazards_no:
                if (checked)
                    occupational_health_hazards = "No";
                break;


            case R.id.training_responsibility_changes_yes:
                if (checked)
                    training_responsibility_changes = "Yes";
                break;
            case R.id.training_responsibility_changes_no:
                if (checked)
                    training_responsibility_changes = "No";
                break;

            case R.id.medical_records_doctors_retrievable_yes:
                medical_records_doctors_retrievable = "Yes";
                break;

            case R.id.medical_records_doctors_retrievable_no:
                medical_records_doctors_retrievable = "No";
                break;

            case R.id.case_of_grievances_yes:
                case_of_grievances = "Yes";
                break;

            case R.id.case_of_grievances_no:
                case_of_grievances = "No";
                break;

            case R.id.staff_disciplinary_procedure_yes:
                staff_disciplinary_procedure = "Yes";

                break;

            case R.id.staff_disciplinary_procedure_no:
                staff_disciplinary_procedure = "No";
                break;

            case R.id.staff_able_to_demonstrate_yes:
                staff_able_to_demonstrate = "Yes";
                break;

            case R.id.staff_able_to_demonstrate_no:
                staff_able_to_demonstrate = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HRMActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    SaveImage(image2,"staffs_personal_files_maintained");

                }

            } else if (requestCode == 2) {
                if (resultCode == RESULT_OK) {

                    VideoUpload(String.valueOf(outputVideoFile),"case_of_grievances");

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }
            }else if (requestCode == 3) {
                if (resultCode == RESULT_OK) {

                    VideoUpload(String.valueOf(outputVideoFile),"staff_disciplinary_procedure");

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
        final Dialog DialogLogOut = new Dialog(HRMActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_staff_health_related_issues.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_staff_health_related_issues.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_staffs_personal_files_maintained.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_staffs_personal_files_maintained.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_occupational_health_hazards.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_occupational_health_hazards.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_training_responsibility_changesl.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_training_responsibility_changesl.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_medical_records_doctors_retrievable.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_medical_records_doctors_retrievable.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 9) {
                        if (radio_status9 != null) {

                            if (radio_status9.equalsIgnoreCase("close")) {

                                nc_case_of_grievances.setImageResource(R.mipmap.nc);

                                nc9 = radio_status9;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status9.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_case_of_grievances.setImageResource(R.mipmap.nc_selected);

                                    nc9 = radio_status9 + "," + edit_text.getText().toString();

                                    radio_status9 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 10) {
                        if (radio_status10 != null) {

                            if (radio_status10.equalsIgnoreCase("close")) {

                                nc_staff_disciplinary_procedure.setImageResource(R.mipmap.nc);

                                nc10 = radio_status10;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status10.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_staff_disciplinary_procedure.setImageResource(R.mipmap.nc_selected);

                                    nc10 = radio_status10 + "," + edit_text.getText().toString();

                                    radio_status10 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 11) {
                        if (radio_status11 != null) {

                            if (radio_status11.equalsIgnoreCase("close")) {

                                nc_staff_able_to_demonstrate.setImageResource(R.mipmap.nc);

                                nc11 = radio_status11;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status11.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_staff_able_to_demonstrate.setImageResource(R.mipmap.nc_selected);

                                    nc11 = radio_status11 + "," + edit_text.getText().toString();

                                    radio_status11 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HRMActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                }
            });
            DialogLogOut.show();
        }

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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HRMActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, position);
    }


    public void displayCommonDialogWithHeader(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(HRMActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_staff_health_related_issues.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_staffs_personal_files_maintained.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_occupational_health_hazards.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_training_responsibility_changes.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_medical_records_doctors_retrievablel.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 9) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark9 = edit_text.getText().toString();
                            remark_case_of_grievances.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 10) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark10 = edit_text.getText().toString();
                            remark_staff_disciplinary_procedure.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 11) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark11 = edit_text.getText().toString();
                            remark_staff_able_to_demonstrate.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HRMActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }


                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(HRMActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(HRMActivity.this,list,from,"HRM");
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
                    Toast toast = Toast.makeText(HRMActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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

    public void showVideoDialog(final String path,final int pos) {
        dialogLogout = new Dialog(HRMActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HRMActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
                startActivityForResult(intent, pos);
            }
        });

        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
        imageView.setImageBitmap(bmThumbnail);

    }



    public void SavePharmacyData(String from){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(10);
        pojo.setStaff_health_related_issues(staff_health_related_issues);
        pojo.setStaffs_personal_files_maintained(staffs_personal_files_maintained);
        pojo.setOccupational_health_hazards(occupational_health_hazards);
        pojo.setTraining_responsibility_changes(training_responsibility_changes);
        pojo.setMedical_records_doctors_retrievable(medical_records_doctors_retrievable);
        pojo.setCase_of_grievances(case_of_grievances);
        pojo.setStaff_disciplinary_procedure(staff_disciplinary_procedure);
        pojo.setStaff_able_to_demonstrate(staff_able_to_demonstrate);

        pojo.setStaff_health_related_issues_remark(remark1);
        pojo.setStaff_health_related_issues_nc(nc1);

        pojo.setStaffs_personal_files_maintained_remark(remark2);
        pojo.setStaffs_personal_files_maintained_nc(nc2);

        JSONObject json = new JSONObject();

        if (staffs_personal_files_maintained_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(staffs_personal_files_maintained_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_staffs_personal_files_maintained_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_staffs_personal_files_maintained_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }


        pojo.setStaffs_personal_files_maintained_image(image1);
        pojo.setLocal_staffs_personal_files_maintained_image(Local_image1);

        pojo.setOccupational_health_hazards_remark(remark3);
        pojo.setOccupational_health_hazards_nc(nc3);

        pojo.setTraining_responsibility_changes_remark(remark4);
        pojo.setTraining_responsibility_changes_nc(nc4);


        pojo.setMedical_records_doctors_retrievable_remark(remark5);
        pojo.setMedical_records_doctors_retrievable_nc(nc5);

        pojo.setCase_of_grievances_remark(remark9);
        pojo.setCase_of_grievances_nc(nc9);

        pojo.setCase_of_grievances_video(video2);
        pojo.setLocal_case_of_grievances_image(Local_video2);

        pojo.setStaff_disciplinary_procedure_remark(remark10);
        pojo.setStaff_disciplinary_procedure_nc(nc10);

        pojo.setStaff_disciplinary_procedure_video(video3);
        pojo.setLocal_staff_disciplinary_procedure_video(Local_video3);

        pojo.setStaff_able_to_demonstrate_remark(remark11);
        pojo.setStaff_able_to_demonstrate_nc(nc11);

        if (sql_status){
             boolean sp_status = databaseHandler.UPDATE_HRM(pojo);

             if (sp_status){
                 if (!from.equalsIgnoreCase("sync")){
                     assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                     AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                     pojo.setHospital_id(assessement_list.get(10).getHospital_id());
                     pojo.setAssessement_name("HRM");
                     pojo.setAssessement_status("Draft");
                     pojo.setLocal_id(assessement_list.get(10).getLocal_id());

                     databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                     Toast.makeText(HRMActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                     Intent intent = new Intent(HRMActivity.this,HospitalListActivity.class);
                     startActivity(intent);
                     finish();
                 }else {
                     new PostDataTask().execute();
                 }

             }
        }else {
            boolean sp_status = databaseHandler.INSERT_HRM(pojo);


            if (sp_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(10).getHospital_id());
                    pojo.setAssessement_name("HRM");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(10).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(HRMActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HRMActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    new PostDataTask().execute();
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
        check = 1;
        for(int i=staffs_personal_files_maintained_list.size(); i<Local_staffs_personal_files_maintained_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_staffs_personal_files_maintained_list.get(i) + "staffs_personal_files_maintained");
            UploadImage(Local_staffs_personal_files_maintained_list.get(i),"staffs_personal_files_maintained");
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
                        Toast.makeText(HRMActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HRMActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("hrm");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<staffs_personal_files_maintained_list.size();i++){
                    String value = staffs_personal_files_maintained_list.get(i);

                    admissions_discharge_home_view = value + admissions_discharge_home_view;
                }

                pojo.setStaffs_personal_files_maintained_image(admissions_discharge_home_view);

                pojo_dataSync.setHrm(pojo);
                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        //CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {Intent intent = new Intent(HRMActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(HRMActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();


                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(HRMActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(10).getHospital_id());
                                    pojo.setAssessement_name("HRM");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(10).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(HRMActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private void VideoUpload(String image_path,final String from){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(HRMActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(HRMActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();

                        if (from.equalsIgnoreCase("case_of_grievances")){
                            video2 = response.body().getMessage();
                            Local_video2 = String.valueOf(outputVideoFile);
                            Video_case_of_grievances.setImageResource(R.mipmap.camera_selected);
                        }else if(from.equalsIgnoreCase("staff_disciplinary_procedure")){
                            video3 = response.body().getMessage();
                            Local_video3 = String.valueOf(outputVideoFile);
                            Video_staff_disciplinary_procedure.setImageResource(R.mipmap.camera_selected);
                        }



                    }else {
                        Toast.makeText(HRMActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HRMActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(HRMActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }


    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("staffs_personal_files_maintained")){
            //staffs_personal_files_maintained_list.add(response.body().getMessage());
            Local_staffs_personal_files_maintained_list.add(image_path);
            image_staffs_personal_files_maintained.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "staffs_personal_files_maintained";
        }

        Toast.makeText(HRMActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();

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
               // d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(HRMActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(HRMActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("staffs_personal_files_maintained")){
                                staffs_personal_files_maintained_list.add(response.body().getMessage());
                                //Local_staffs_personal_files_maintained_list.add(image_path);
                                image_staffs_personal_files_maintained.setImageResource(R.mipmap.camera_selected);

                                image1 = "staffs_personal_files_maintained";
                            }

                            //Toast.makeText(HRMActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();
                            check = 1;
                            latch.countDown();
                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(HRMActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(HRMActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");
                check = 0;
                latch.countDown();//.makeText(HRMActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("staffs_personal_files_maintained")){
                Local_staffs_personal_files_maintained_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_staffs_personal_files_maintained_list.size() == 0){
                    image_staffs_personal_files_maintained.setImageResource(R.mipmap.camera);

                    Local_image1 = null;

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

        if (!assessement_list.get(10).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("");
        }else {
            Intent intent = new Intent(HRMActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
