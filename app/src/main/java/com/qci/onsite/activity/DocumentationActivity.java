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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qci.onsite.R;
import com.qci.onsite.adapter.ImageShowAdapter;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.DocumentationPojo;
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

public class DocumentationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_document_related_procedure)
    LinearLayout ll_document_related_procedure;

    @BindView(R.id.document_related_procedure_yes)
    RadioButton document_related_procedure_yes;

    @BindView(R.id.document_related_procedure_no)
    RadioButton document_related_procedure_no;

    @BindView(R.id.remark_document_related_procedure)
    ImageView remark_document_related_procedure;

    @BindView(R.id.nc_document_related_procedure)
    ImageView nc_document_related_procedure;

    @BindView(R.id.ll_document_showing_process)
    LinearLayout ll_document_showing_process;

    @BindView(R.id.document_showing_process_yes)
    RadioButton document_showing_process_yes;

    @BindView(R.id.document_showing_process_no)
    RadioButton document_showing_process_no;

    @BindView(R.id.remark_document_showing_process)
    ImageView remark_document_showing_process;

    @BindView(R.id.nc_document_showing_process)
    ImageView nc_document_showing_process;

    @BindView(R.id.ll_document_showing_care_patients)
    LinearLayout ll_document_showing_care_patients;

    @BindView(R.id.document_showing_care_patients_yes)
    RadioButton document_showing_care_patients_yes;

    @BindView(R.id.document_showing_care_patients_no)
    RadioButton document_showing_care_patients_no;

    @BindView(R.id.remark_document_showing_care_patients)
    ImageView remark_document_showing_care_patients;

    @BindView(R.id.nc_document_showing_care_patients)
    ImageView nc_document_showing_care_patients;

    @BindView(R.id.ll_document_showing_policies)
    LinearLayout ll_document_showing_policies;

    @BindView(R.id.document_showing_policies_yes)
    RadioButton document_showing_policies_yes;

    @BindView(R.id.document_showing_policies_no)
    RadioButton document_showing_policies_no;

    @BindView(R.id.remark_document_showing_policies)
    ImageView remark_document_showing_policies;

    @BindView(R.id.nc_document_showing_policies)
    ImageView nc_document_showing_policies;

    @BindView(R.id.ll_document_showing_procedures)
    LinearLayout ll_document_showing_procedures;

    @BindView(R.id.document_showing_procedures_yes)
    RadioButton document_showing_procedures_yes;

    @BindView(R.id.document_showing_procedures_no)
    RadioButton document_showing_procedures_no;

    @BindView(R.id.remark_document_showing_procedures)
    ImageView remark_document_showing_procedures;

    @BindView(R.id.nc_document_showing_procedures)
    ImageView nc_document_showing_procedures;

    @BindView(R.id.ll_document_showing_procedure_administration)
    LinearLayout ll_document_showing_procedure_administration;

    @BindView(R.id.document_showing_procedure_administration_yes)
    RadioButton document_showing_procedure_administration_yes;

    @BindView(R.id.document_showing_procedure_administration_no)
    RadioButton document_showing_procedure_administration_no;

    @BindView(R.id.remark_document_showing_procedure_administration)
    ImageView remark_document_showing_procedure_administration;

    @BindView(R.id.nc_document_showing_procedure_administration)
    ImageView nc_document_showing_procedure_administration;

    @BindView(R.id.ll_document_showing_defined_criteria)
    LinearLayout ll_document_showing_defined_criteria;

    @BindView(R.id.document_showing_defined_criteria_yes)
    RadioButton document_showing_defined_criteria_yes;

    @BindView(R.id.document_showing_defined_criteria_no)
    RadioButton document_showing_defined_criteria_no;

    @BindView(R.id.remark_document_showing_defined_criteria)
    ImageView remark_document_showing_defined_criteria;

    @BindView(R.id.nc_document_showing_defined_criteria)
    ImageView nc_document_showing_defined_criteria;

    @BindView(R.id.ll_document_showing_procedure_prevention)
    LinearLayout ll_document_showing_procedure_prevention;

    @BindView(R.id.document_showing_procedure_prevention_yes)
    RadioButton document_showing_procedure_prevention_yes;

    @BindView(R.id.document_showing_procedure_prevention_no)
    RadioButton document_showing_procedure_prevention_no;

    @BindView(R.id.remark_document_showing_procedure_prevention)
    ImageView remark_document_showing_procedure_prevention;

    @BindView(R.id.nc_document_showing_procedure_prevention)
    ImageView nc_document_showing_procedure_prevention;

    @BindView(R.id.ll_document_showing_procedure_incorporating)
    LinearLayout ll_document_showing_procedure_incorporating;

    @BindView(R.id.document_showing_procedure_incorporating_yes)
    RadioButton document_showing_procedure_incorporating_yes;

    @BindView(R.id.document_showing_procedure_incorporating_no)
    RadioButton document_showing_procedure_incorporating_no;

    @BindView(R.id.remark_document_showing_procedure_incorporating)
    ImageView remark_document_showing_procedure_incorporating;

    @BindView(R.id.nc_document_showing_procedure_incorporating)
    ImageView nc_document_showing_procedure_incorporating;

    @BindView(R.id.ll_document_showing_procedure_address)
    LinearLayout ll_document_showing_procedure_address;

    @BindView(R.id.document_showing_procedure_address_yes)
    RadioButton document_showing_procedure_address_yes;

    @BindView(R.id.document_showing_procedure_address_no)
    RadioButton document_showing_procedure_address_no;

    @BindView(R.id.remark_document_showing_procedure_address)
    ImageView remark_document_showing_procedure_address;

    @BindView(R.id.nc_document_showing_procedure_address)
    ImageView nc_document_showing_procedure_address;

    @BindView(R.id.ll_document_showing_policies_procedure)
    LinearLayout ll_document_showing_policies_procedure;

    @BindView(R.id.document_showing_policies_procedure_yes)
    RadioButton document_showing_policies_procedure_yes;

    @BindView(R.id.document_showing_policies_procedure_no)
    RadioButton document_showing_policies_procedure_no;

    @BindView(R.id.remark_document_showing_policies_procedure)
    ImageView remark_document_showing_policies_procedure;

    @BindView(R.id.nc_document_showing_policies_procedure)
    ImageView nc_document_showing_policies_procedure;

    @BindView(R.id.ll_document_showing_drugs_available)
    LinearLayout ll_document_showing_drugs_available;

    @BindView(R.id.document_showing_drugs_available_yes)
    RadioButton document_showing_drugs_available_yes;

    @BindView(R.id.document_showing_drugs_available_no)
    RadioButton document_showing_drugs_available_no;

    @BindView(R.id.remark_document_showing_drugs_available)
    ImageView remark_document_showing_drugs_available;

    @BindView(R.id.nc_document_showing_drugs_available)
    ImageView nc_document_showing_drugs_available;

    @BindView(R.id.ll_document_showing_safe_storage)
    LinearLayout ll_document_showing_safe_storage;

    @BindView(R.id.document_showing_safe_storage_yes)
    RadioButton document_showing_safe_storage_yes;

    @BindView(R.id.document_showing_safe_storage_no)
    RadioButton document_showing_safe_storage_no;

    @BindView(R.id.remark_document_showing_safe_storage)
    ImageView remark_document_showing_safe_storage;

    @BindView(R.id.nc_document_showing_safe_storage)
    ImageView nc_document_showing_safe_storage;

    @BindView(R.id.ll_Infection_control_manual_showing)
    LinearLayout ll_Infection_control_manual_showing;

    @BindView(R.id.Infection_control_manual_showing_yes)
    RadioButton Infection_control_manual_showing_yes;

    @BindView(R.id.Infection_control_manual_showing_no)
    RadioButton Infection_control_manual_showing_no;

    @BindView(R.id.remark_Infection_control_manual_showing)
    ImageView remark_Infection_control_manual_showing;

    @BindView(R.id.nc_Infection_control_manual_showing)
    ImageView nc_Infection_control_manual_showing;

    @BindView(R.id.ll_document_showing_operational_maintenance)
    LinearLayout ll_document_showing_operational_maintenance;

    @BindView(R.id.document_showing_operational_maintenance_yes)
    RadioButton document_showing_operational_maintenance_yes;

    @BindView(R.id.document_showing_operational_maintenance_no)
    RadioButton document_showing_operational_maintenance_no;

    @BindView(R.id.remark_document_showing_operational_maintenance)
    ImageView remark_document_showing_operational_maintenance;

    @BindView(R.id.nc_document_showing_operational_maintenance)
    ImageView nc_document_showing_operational_maintenance;

    @BindView(R.id.ll_document_showing_safe_exit_plan)
    LinearLayout ll_document_showing_safe_exit_plan;

    @BindView(R.id.document_showing_safe_exit_plan_yes)
    RadioButton document_showing_safe_exit_plan_yes;

    @BindView(R.id.document_showing_safe_exit_plan_no)
    RadioButton document_showing_safe_exit_plan_no;

    @BindView(R.id.remark_document_showing_safe_exit_plan)
    ImageView remark_document_showing_safe_exit_plan;

    @BindView(R.id.nc_document_showing_safe_exit_plan)
    ImageView nc_document_showing_safe_exit_plan;

    @BindView(R.id.ll_document_showing_well_defined_staff)
    LinearLayout ll_document_showing_well_defined_staff;

    @BindView(R.id.document_showing_well_defined_staff_yes)
    RadioButton document_showing_well_defined_staff_yes;

    @BindView(R.id.document_showing_well_defined_staff_no)
    RadioButton document_showing_well_defined_staff_no;

    @BindView(R.id.remark_document_showing_well_defined_staff)
    ImageView remark_document_showing_well_defined_staff;

    @BindView(R.id.nc_document_showing_well_defined_staff)
    ImageView nc_document_showing_well_defined_staff;

    @BindView(R.id.ll_document_showing_disciplinary_grievance)
    LinearLayout ll_document_showing_disciplinary_grievance;

    @BindView(R.id.document_showing_disciplinary_grievance_yes)
    RadioButton document_showing_disciplinary_grievance_yes;

    @BindView(R.id.document_showing_disciplinary_grievance_no)
    RadioButton document_showing_disciplinary_grievance_no;

    @BindView(R.id.remark_document_showing_disciplinary_grievance)
    ImageView remark_document_showing_disciplinary_grievance;

    @BindView(R.id.nc_document_showing_disciplinary_grievance)
    ImageView nc_document_showing_disciplinary_grievance;

    @BindView(R.id.ll_document_showing_policies_procedures)
    LinearLayout ll_document_showing_policies_procedures;

    @BindView(R.id.document_showing_policies_procedures_yes)
    RadioButton document_showing_policies_procedures_yes;

    @BindView(R.id.document_showing_policies_procedures_no)
    RadioButton document_showing_policies_procedures_no;

    @BindView(R.id.remark_document_showing_policies_procedures)
    ImageView remark_document_showing_policies_procedures;

    @BindView(R.id.nc_document_showing_policies_procedures)
    ImageView nc_document_showing_policies_procedures;

    @BindView(R.id.ll_document_showing_retention_time)
    LinearLayout ll_document_showing_retention_time;

    @BindView(R.id.document_showing_retention_time_yes)
    RadioButton document_showing_retention_time_yes;

    @BindView(R.id.document_showing_retention_time_no)
    RadioButton document_showing_retention_time_no;

    @BindView(R.id.remark_mdocument_showing_retention_time)
    ImageView remark_mdocument_showing_retention_time;

    @BindView(R.id.nc_document_showing_retention_time)
    ImageView nc_document_showing_retention_time;

    @BindView(R.id.ll_document_showing_define_process)
    LinearLayout ll_document_showing_define_process;

    @BindView(R.id.document_showing_define_process_yes)
    RadioButton document_showing_define_process_yes;

    @BindView(R.id.document_showing_define_process_no)
    RadioButton document_showing_define_process_no;

    @BindView(R.id.remark_document_showing_define_process)
    ImageView remark_document_showing_define_process;

    @BindView(R.id.nc_document_showing_define_process)
    ImageView nc_document_showing_define_process;

    @BindView(R.id.ll_document_showing_medical_records)
    LinearLayout ll_document_showing_medical_records;

    @BindView(R.id.document_showing_medical_records_yes)
    RadioButton document_showing_medical_records_yes;

    @BindView(R.id.document_showing_medical_records_no)
    RadioButton document_showing_medical_records_no;

    @BindView(R.id.remark_document_showing_medical_records)
    ImageView remark_document_showing_medical_records;

    @BindView(R.id.nc_document_showing_medical_records)
    ImageView nc_document_showing_medical_records;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    @BindView(R.id.image_signed_document)
    ImageView image_signed_document;

    @BindView(R.id.image_signed_general_duty)
    ImageView image_signed_general_duty;

    @BindView(R.id.image_signed_nursesl)
    ImageView image_signed_nursesl;

    @BindView(R.id.image_signed_paramedical)
    ImageView image_signed_paramedical;

    @BindView(R.id.image_signed_administrativ)
    ImageView image_signed_administrativ;

    @BindView(R.id.nc_signed_document)
    ImageView nc_signed_document;

    @BindView(R.id.nc_signed_general_duty)
    ImageView nc_signed_general_duty;

    @BindView(R.id.nc_signed_nursesl)
    ImageView nc_signed_nursesl;

    @BindView(R.id.nc_signed_paramedical)
    ImageView nc_signed_paramedical;

    @BindView(R.id.nc_signed_administrativ)
    ImageView nc_signed_administrativ;



    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private ArrayList<String>img_signed_document_list;
    private ArrayList<String>img_signed_general_duty_list;
    private ArrayList<String> img_signed_nursesl_list;
    private ArrayList<String> img_signed_paramedicall_list;
    private ArrayList<String>img_signed_administrativ_list;

    private ArrayList<String>img_signed_document_url_list;
    private ArrayList<String>img_signed_general_duty_url_list;
    private ArrayList<String>img_signed_nursesl_url_list;
    private ArrayList<String>img_signed_paramedical_url_list;
    private ArrayList<String>img_signed_administrativ_url_list;


    private String remark1, remark2, remark3,remark4,remark5,remark6,remark7,remark8,remark9,remark10,remark11,
    remark12,remark13,remark14,remark15,remark16,remark17,remark18,remark19,remark20,remark21,remark22;

    private Dialog dialogLogout;

    int Bed_no = 0;



    private String nc1, nc2, nc3,nc4,nc5,nc6,nc7,nc8,nc9,nc10,nc11,nc12,nc13,nc14,nc15,nc16,nc17,nc18,nc19,nc20,nc21,nc22,nc23,nc24,nc25,nc26,nc27;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status6,radio_status7,
            radio_status8,radio_status9,radio_status10,radio_status11,radio_status12,radio_status13,radio_status14,radio_status15,
            radio_status16,radio_status17,radio_status18,radio_status19,radio_status20,radio_status21,radio_status22,
            radio_status23,radio_status24,radio_status25,radio_status26,radio_status27;

    private DatabaseHandler databaseHandler;


    private File outputVideoFile;

    private ImageShowAdapter image_adapter;

    private String document_related_procedure = "",document_showing_process ="",document_showing_care_patients="",document_showing_policies = "",document_showing_procedures="",
            document_showing_procedure_administration = "",document_showing_defined_criteria  = "",document_showing_procedure_prevention = "",document_showing_procedure_incorporating="",
    document_showing_procedure_address = "",document_showing_policies_procedure="",document_showing_drugs_available = "",document_showing_safe_storage ="",Infection_control_manual_showing="",document_showing_operational_maintenance = "",document_showing_safe_exit_plan ="",
            document_showing_well_defined_staff = "",document_showing_disciplinary_grievance  = "",document_showing_policies_procedures = "",document_showing_retention_time="",
            document_showing_define_process = "",document_showing_medical_records="";


    private DocumentationPojo pojo;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;

    String st_signed_document = "",st_signed_general_duty = "",st_signed_nursesl = "",st_signed_paramedical = "",
            st_signed_administrativ = "";

    String image1 = "",Local_image1 = "",image2 = "",Local_image2,image3 = "",Local_image3 = "",image4 = "",Local_image4 = "",image5 = "",Local_image5 = "";

    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        ButterKnife.bind(this);

        setDrawerbackIcon("Documentation");

        img_signed_document_list = new ArrayList<>();
        img_signed_general_duty_list = new ArrayList<>();
        img_signed_nursesl_list = new ArrayList<>();
        img_signed_paramedicall_list = new ArrayList<>();
        img_signed_administrativ_list = new ArrayList<>();

        img_signed_document_url_list = new ArrayList<>();
        img_signed_general_duty_url_list = new ArrayList<>();
        img_signed_nursesl_url_list = new ArrayList<>();
        img_signed_paramedical_url_list = new ArrayList<>();
        img_signed_administrativ_url_list = new ArrayList<>();



        assessement_list = new ArrayList<>();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        pojo_dataSync = new DataSyncRequest();


        databaseHandler = DatabaseHandler.getInstance(this);

        pojo = new DocumentationPojo();

        mAPIService = ApiUtils.getAPIService();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_document_showing_procedures.setVisibility(View.GONE);
            ll_document_showing_defined_criteria.setVisibility(View.GONE);
            ll_document_showing_policies_procedure.setVisibility(View.GONE);
            ll_document_showing_drugs_available.setVisibility(View.GONE);
            ll_document_showing_safe_storage.setVisibility(View.GONE);
            ll_document_showing_well_defined_staff.setVisibility(View.GONE);
        }else {
            ll_document_showing_procedures.setVisibility(View.VISIBLE);
            ll_document_showing_defined_criteria.setVisibility(View.VISIBLE);
            ll_document_showing_policies_procedure.setVisibility(View.VISIBLE);
            ll_document_showing_drugs_available.setVisibility(View.VISIBLE);
            ll_document_showing_safe_storage.setVisibility(View.VISIBLE);
            ll_document_showing_well_defined_staff.setVisibility(View.VISIBLE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();

    }


    public void getPharmacyData(){

        pojo = databaseHandler.getDocumentationPojo("20");

        if (pojo != null){
            sql_status = true;
            if (pojo.getDocument_related_procedure() != null){
                document_related_procedure = pojo.getDocument_related_procedure();
                if (pojo.getDocument_related_procedure().equalsIgnoreCase("Yes")){
                    document_related_procedure_yes.setChecked(true);
                }else if (pojo.getDocument_related_procedure().equalsIgnoreCase("No")){
                    document_related_procedure_no.setChecked(true);
                }
            }
            if (pojo.getDocument_showing_process() != null){
                document_showing_process = pojo.getDocument_showing_process();
                if (pojo.getDocument_showing_process().equalsIgnoreCase("Yes")){
                    document_showing_process_yes.setChecked(true);
                }else if (pojo.getDocument_showing_process().equalsIgnoreCase("No")){
                    document_showing_process_no.setChecked(true);
                }
            }
            if (pojo.getDocument_showing_care_patients() != null){
                document_showing_care_patients = pojo.getDocument_showing_care_patients();
                if (pojo.getDocument_showing_care_patients().equalsIgnoreCase("Yes")){
                    document_showing_care_patients_yes.setChecked(true);
                }else if (pojo.getDocument_showing_care_patients().equalsIgnoreCase("No")){
                    document_showing_care_patients_no.setChecked(true);
                }
            } if (pojo.getDocument_showing_policies() != null){
                document_showing_policies = pojo.getDocument_showing_policies();
                if (pojo.getDocument_showing_policies().equalsIgnoreCase("Yes")){
                    document_showing_policies_yes.setChecked(true);
                }else if (pojo.getDocument_showing_policies().equalsIgnoreCase("No")){
                    document_showing_policies_no.setChecked(true);
                }
            }
            if (pojo.getDocument_showing_procedures() != null){
                document_showing_procedures = pojo.getDocument_showing_procedures();
                if (pojo.getDocument_showing_procedures().equalsIgnoreCase("Yes")){
                    document_showing_procedures_yes.setChecked(true);
                }else if (pojo.getDocument_showing_procedures().equalsIgnoreCase("No")){
                    document_showing_procedures_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_procedure_administration() != null){
                document_showing_procedure_administration = pojo.getDocument_showing_procedure_administration();
                if (pojo.getDocument_showing_procedure_administration().equalsIgnoreCase("Yes")){
                    document_showing_procedure_administration_yes.setChecked(true);
                }else if (pojo.getDocument_showing_procedure_administration().equalsIgnoreCase("No")){
                    document_showing_procedure_administration_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_defined_criteria() != null){
                document_showing_defined_criteria = pojo.getDocument_showing_defined_criteria();
                if (pojo.getDocument_showing_defined_criteria().equalsIgnoreCase("Yes")){
                    document_showing_defined_criteria_yes.setChecked(true);
                }else if (pojo.getDocument_showing_defined_criteria().equalsIgnoreCase("No")){
                    document_showing_defined_criteria_yes.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_procedure_prevention() != null){
                document_showing_procedure_prevention = pojo.getDocument_showing_procedure_prevention();
                if (pojo.getDocument_showing_procedure_prevention().equalsIgnoreCase("Yes")){
                    document_showing_procedure_prevention_yes.setChecked(true);
                }else if (pojo.getDocument_showing_procedure_prevention().equalsIgnoreCase("No")){
                    document_showing_procedure_prevention_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_procedure_incorporating() != null){
                document_showing_procedure_incorporating = pojo.getDocument_showing_procedure_incorporating();
                if (pojo.getDocument_showing_procedure_incorporating().equalsIgnoreCase("Yes")){
                    document_showing_procedure_incorporating_yes.setChecked(true);
                }else if (pojo.getDocument_showing_procedure_incorporating().equalsIgnoreCase("No")){
                    document_showing_procedure_incorporating_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_procedure_address() != null){
                document_showing_procedure_address = pojo.getDocument_showing_procedure_address();
                if (pojo.getDocument_showing_procedure_address().equalsIgnoreCase("Yes")){
                    document_showing_procedure_address_yes.setChecked(true);
                }else if (pojo.getDocument_showing_procedure_address().equalsIgnoreCase("No")){
                    document_showing_procedure_address_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_policies_procedure() != null){
                document_showing_policies_procedure = pojo.getDocument_showing_policies_procedure();
                if (pojo.getDocument_showing_policies_procedure().equalsIgnoreCase("Yes")){
                    document_showing_policies_procedure_yes.setChecked(true);
                }else if (pojo.getDocument_showing_policies_procedure().equalsIgnoreCase("No")){
                    document_showing_policies_procedure_no.setChecked(true);
                }
            }

            if (pojo.getDocument_showing_drugs_available() != null){
                document_showing_drugs_available = pojo.getDocument_showing_drugs_available();
                if (pojo.getDocument_showing_drugs_available().equalsIgnoreCase("Yes")){
                    document_showing_drugs_available_yes.setChecked(true);
                }else if (pojo.getDocument_showing_drugs_available().equalsIgnoreCase("No")){
                    document_showing_drugs_available_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_safe_storage() != null){
                document_showing_safe_storage = pojo.getDocument_showing_safe_storage();
                if (pojo.getDocument_showing_safe_storage().equalsIgnoreCase("Yes")){
                    document_showing_safe_storage_yes.setChecked(true);
                }else if (pojo.getDocument_showing_safe_storage().equalsIgnoreCase("No")){
                    document_showing_safe_storage_no.setChecked(true);
                }
            }


            if (pojo.getInfection_control_manual_showing() != null){
                Infection_control_manual_showing = pojo.getInfection_control_manual_showing();
                if (pojo.getInfection_control_manual_showing().equalsIgnoreCase("Yes")){
                    Infection_control_manual_showing_yes.setChecked(true);
                }else if (pojo.getInfection_control_manual_showing().equalsIgnoreCase("No")){
                    Infection_control_manual_showing_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_operational_maintenance() != null){
                document_showing_operational_maintenance = pojo.getDocument_showing_operational_maintenance();
                if (pojo.getDocument_showing_operational_maintenance().equalsIgnoreCase("Yes")){
                    document_showing_operational_maintenance_yes.setChecked(true);
                }else if (pojo.getDocument_showing_operational_maintenance().equalsIgnoreCase("No")){
                    document_showing_operational_maintenance_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_safe_exit_plan() != null){
                document_showing_safe_exit_plan = pojo.getDocument_showing_safe_exit_plan();
                if (pojo.getDocument_showing_safe_exit_plan().equalsIgnoreCase("Yes")){
                    document_showing_safe_exit_plan_yes.setChecked(true);
                }else if (pojo.getDocument_showing_safe_exit_plan().equalsIgnoreCase("No")){
                    document_showing_safe_exit_plan_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_well_defined_staff() != null){
                document_showing_well_defined_staff = pojo.getDocument_showing_well_defined_staff();
                if (pojo.getDocument_showing_well_defined_staff().equalsIgnoreCase("Yes")){
                    document_showing_well_defined_staff_yes.setChecked(true);
                }else if (pojo.getDocument_showing_well_defined_staff().equalsIgnoreCase("No")){
                    document_showing_well_defined_staff_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_disciplinary_grievance() != null){
                document_showing_disciplinary_grievance = pojo.getDocument_showing_disciplinary_grievance();
                if (pojo.getDocument_showing_disciplinary_grievance().equalsIgnoreCase("Yes")){
                    document_showing_disciplinary_grievance_yes.setChecked(true);
                }else if (pojo.getDocument_showing_disciplinary_grievance().equalsIgnoreCase("No")){
                    document_showing_disciplinary_grievance_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_policies_procedures() != null){
                document_showing_policies_procedures = pojo.getDocument_showing_policies_procedures();
                if (pojo.getDocument_showing_policies_procedures().equalsIgnoreCase("Yes")){
                    document_showing_policies_procedures_yes.setChecked(true);
                }else if (pojo.getDocument_showing_policies_procedures().equalsIgnoreCase("No")){
                    document_showing_policies_procedures_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_retention_time() != null){
                document_showing_retention_time = pojo.getDocument_showing_retention_time();
                if (pojo.getDocument_showing_retention_time().equalsIgnoreCase("Yes")){
                    document_showing_retention_time_yes.setChecked(true);
                }else if (pojo.getDocument_showing_retention_time().equalsIgnoreCase("No")){
                    document_showing_retention_time_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_define_process() != null){
                document_showing_define_process = pojo.getDocument_showing_define_process();
                if (pojo.getDocument_showing_define_process().equalsIgnoreCase("Yes")){
                    document_showing_define_process_yes.setChecked(true);
                }else if (pojo.getDocument_showing_define_process().equalsIgnoreCase("No")){
                    document_showing_define_process_no.setChecked(true);
                }
            }


            if (pojo.getDocument_showing_medical_records() != null){
                document_showing_medical_records = pojo.getDocument_showing_medical_records();
                if (pojo.getDocument_showing_medical_records().equalsIgnoreCase("Yes")){
                    document_showing_medical_records_yes.setChecked(true);
                }else if (pojo.getDocument_showing_medical_records().equalsIgnoreCase("No")){
                    document_showing_medical_records_no.setChecked(true);
                }
            }



            if (pojo.getDocument_related_procedure_remark() != null){
                remark_document_related_procedure.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getDocument_related_procedure_remark();
            }
            if (pojo.getDocument_related_procedure_nc() != null){
                nc1 = pojo.getDocument_related_procedure_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_document_related_procedure.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_related_procedure.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getDocument_showing_process_remark() != null){
                remark2 = pojo.getDocument_showing_process_remark();

                remark_document_showing_process.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_process_nc() != null){
                nc2 = pojo.getDocument_showing_process_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_document_showing_process.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_process.setImageResource(R.mipmap.nc_selected);
                }

            }



            if (pojo.getDocument_showing_care_patients_remark() != null){
                remark3 = pojo.getDocument_showing_care_patients_remark();

                remark_document_showing_care_patients.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_care_patients_nc() != null){
                nc3 = pojo.getDocument_showing_care_patients_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_document_showing_care_patients.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_care_patients.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDocument_showing_policies_remark() != null){
                remark4 = pojo.getDocument_showing_policies_remark();

                remark_document_showing_policies.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_policies_nc() != null){
                nc4 = pojo.getDocument_showing_policies_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_document_showing_policies.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_policies.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getDocument_showing_procedures_remark() != null){
                remark5 = pojo.getDocument_showing_procedures_remark();

                remark_document_showing_procedures.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_procedures_nc() != null){
                nc5 = pojo.getDocument_showing_procedures_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_document_showing_procedures.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_procedures.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_procedure_administration_remark() != null){
                remark6 = pojo.getDocument_showing_procedure_administration_remark();

                remark_document_showing_procedure_administration.setImageResource(R.mipmap.remark_selected);


            }
            if (pojo.getDocument_showing_procedure_administration_nc() != null){
                nc6 = pojo.getDocument_showing_procedure_administration_nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_document_showing_procedure_administration.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_procedure_administration.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getDocument_showing_defined_criteria_remark() != null){
                remark7 = pojo.getDocument_showing_defined_criteria_remark();

                remark_document_showing_defined_criteria.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_defined_criteria_nc() != null){
                nc7 = pojo.getDocument_showing_defined_criteria_nc();

                if (nc7.equalsIgnoreCase("close")){
                    nc_document_showing_defined_criteria.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_defined_criteria.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDocument_showing_procedure_prevention_remark() != null){
                remark8 = pojo.getDocument_showing_procedure_prevention_remark();

                remark_document_showing_procedure_prevention.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_procedure_prevention_nc() != null){
                nc8 = pojo.getDocument_showing_procedure_prevention_nc();

                if (nc8.equalsIgnoreCase("close")){
                    nc_document_showing_procedure_prevention.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_procedure_prevention.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_procedure_incorporating_remark() != null){
                remark9 = pojo.getDocument_showing_procedure_incorporating_remark();

                remark_document_showing_procedure_incorporating.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_procedure_incorporating_nc() != null){
                nc9 = pojo.getDocument_showing_procedure_incorporating_nc();

                if (nc9.equalsIgnoreCase("close")){
                    nc_document_showing_procedure_incorporating.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_procedure_incorporating.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_procedure_address_remark() != null){
                remark10 = pojo.getDocument_showing_procedure_address_remark();

                remark_document_showing_procedure_address.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_procedure_address_nc() != null){
                nc10 = pojo.getDocument_showing_procedure_address_nc();


                if (nc10.equalsIgnoreCase("close")){
                    nc_document_showing_procedure_address.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_procedure_address.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getDocument_showing_policies_procedure_remark() != null){
                remark11 = pojo.getDocument_showing_policies_procedure_remark();

                remark_document_showing_policies_procedure.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_policies_procedure_nc() != null){
                nc11 = pojo.getDocument_showing_policies_procedure_nc();

                if (nc11.equalsIgnoreCase("close")){
                    nc_document_showing_policies_procedure.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_policies_procedure.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDocument_showing_drugs_available_remark() != null){
                remark_document_showing_drugs_available.setImageResource(R.mipmap.remark_selected);
                remark12 = pojo.getDocument_showing_drugs_available_remark();
            }
            if (pojo.getDocument_showing_drugs_available_nc() != null){
                nc12 = pojo.getDocument_showing_drugs_available_nc();

                if (nc12.equalsIgnoreCase("close")){
                    nc_document_showing_drugs_available.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_drugs_available.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getDocument_showing_safe_storage_remark() != null){
                remark13 = pojo.getDocument_showing_safe_storage_remark();

                remark_document_showing_safe_storage.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_safe_storage_nc() != null){
                nc13 = pojo.getDocument_showing_safe_storage_nc();

                if (nc13.equalsIgnoreCase("close")){
                    nc_document_showing_safe_storage.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_safe_storage.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getInfection_control_manual_showing_remark() != null){
                remark14 = pojo.getInfection_control_manual_showing_remark();

                remark_Infection_control_manual_showing.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getInfection_control_manual_showing_nc() != null){
                nc14 = pojo.getInfection_control_manual_showing_nc();

                if (nc14.equalsIgnoreCase("close")){
                    nc_Infection_control_manual_showing.setImageResource(R.mipmap.nc);
                }else {
                    nc_Infection_control_manual_showing.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDocument_showing_operational_maintenance_remark() != null){
                remark15 = pojo.getDocument_showing_operational_maintenance_remark();

                remark_document_showing_operational_maintenance.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_operational_maintenance_nc() != null){
                nc15 = pojo.getDocument_showing_operational_maintenance_nc();

                if (nc15.equalsIgnoreCase("close")){
                    nc_document_showing_operational_maintenance.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_operational_maintenance.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getDocument_showing_safe_exit_plan_remark() != null){
                remark16 = pojo.getDocument_showing_safe_exit_plan_remark();

                remark_document_showing_safe_exit_plan.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_safe_exit_plan_nc() != null){
                nc16 = pojo.getDocument_showing_safe_exit_plan_nc();

                if (nc16.equalsIgnoreCase("close")){
                    nc_document_showing_safe_exit_plan.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_safe_exit_plan.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_well_defined_staff_remark() != null){
                remark17 = pojo.getDocument_showing_well_defined_staff_remark();

                remark_document_showing_well_defined_staff.setImageResource(R.mipmap.remark_selected);


            }
            if (pojo.getDocument_showing_well_defined_staff_nc() != null){
                nc17 = pojo.getDocument_showing_well_defined_staff_nc();

                if (nc17.equalsIgnoreCase("close")){
                    nc_document_showing_well_defined_staff.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_well_defined_staff.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getDocument_showing_disciplinary_grievance_remark() != null){
                remark18 = pojo.getDocument_showing_disciplinary_grievance_remark();

                remark_document_showing_disciplinary_grievance.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_disciplinary_grievance_nc() != null){
                nc18 = pojo.getDocument_showing_disciplinary_grievance_nc();

                if (nc18.equalsIgnoreCase("close")){
                    nc_document_showing_disciplinary_grievance.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_disciplinary_grievance.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDocument_showing_policies_procedures_remark() != null){
                remark19 = pojo.getDocument_showing_policies_procedures_remark();

                remark_document_showing_policies_procedures.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_policies_procedures_nc() != null){
                nc19 = pojo.getDocument_showing_policies_procedures_nc();

                if (nc19.equalsIgnoreCase("close")){
                    nc_document_showing_policies_procedures.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_policies_procedures.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_retention_time_remark() != null){
                remark20 = pojo.getDocument_showing_retention_time_remark();

                remark_mdocument_showing_retention_time.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_retention_time_nc() != null){
                nc20 = pojo.getDocument_showing_retention_time_nc();

                if (nc20.equalsIgnoreCase("close")){
                    nc_document_showing_retention_time.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_retention_time.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocument_showing_define_process_remark() != null){
                remark21 = pojo.getDocument_showing_define_process_remark();

                remark_document_showing_define_process.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_define_process_nc() != null){
                nc21 = pojo.getDocument_showing_define_process_nc();

                if (nc21.equalsIgnoreCase("close")){
                    nc_document_showing_define_process.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_define_process.setImageResource(R.mipmap.nc_selected);
                }
            }



            if (pojo.getDocument_showing_medical_records_remark() != null){
                remark22 = pojo.getDocument_showing_medical_records_remark();

                remark_document_showing_medical_records.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocument_showing_medical_records_nc() != null){
                nc22 = pojo.getDocument_showing_medical_records_nc();

                if (nc22.equalsIgnoreCase("close")){
                    nc_document_showing_medical_records.setImageResource(R.mipmap.nc);
                }else {
                    nc_document_showing_medical_records.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSigndocscopeofservices_nc() != null){
                nc23 = pojo.getSigndocscopeofservices_nc();

                if (nc23.equalsIgnoreCase("close")){
                    nc_signed_document.setImageResource(R.mipmap.nc);
                }else {
                    nc_signed_document.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSignlistgendutymedoffcr_nc() != null){
                nc24 = pojo.getSignlistgendutymedoffcr_nc();

                if (nc24.equalsIgnoreCase("close")){
                    nc_signed_general_duty.setImageResource(R.mipmap.nc);
                }else {
                    nc_signed_general_duty.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSignlistnurses_nc() != null){
                nc25 = pojo.getSignlistnurses_nc();

                if (nc25.equalsIgnoreCase("close")){
                    nc_signed_nursesl.setImageResource(R.mipmap.nc);
                }else {
                    nc_signed_nursesl.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSignlistparamedstaff_nc() != null){
                nc26 = pojo.getSignlistparamedstaff_nc();

                if (nc26.equalsIgnoreCase("close")){
                    nc_signed_paramedical.setImageResource(R.mipmap.nc);
                }else {
                    nc_signed_paramedical.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getSignlistadminsupportstaff_nc() != null){
                nc27 = pojo.getSignlistadminsupportstaff_nc();

                if (nc27.equalsIgnoreCase("close")){
                    nc_signed_administrativ.setImageResource(R.mipmap.nc);
                }else {
                    nc_signed_administrativ.setImageResource(R.mipmap.nc_selected);
                }
            }


            if(getFromPrefs(AppConstant.scopeofservices).length() > 0){
                image1 = getFromPrefs(AppConstant.scopeofservices);

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_document_url_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                image_signed_document.setImageResource(R.mipmap.camera_selected);
            }

            if (getFromPrefs(AppConstant.scopeofservices_local).length() > 0){
                Local_image1 = getFromPrefs(AppConstant.scopeofservices_local);

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_document_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                image_signed_document.setImageResource(R.mipmap.camera_selected);

            }

            if(getFromPrefs(AppConstant.gendutymedoffcr).length() > 0){
                image2 = getFromPrefs(AppConstant.gendutymedoffcr);

                image_signed_general_duty.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_general_duty_url_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (getFromPrefs(AppConstant.gendutymedoffcr_local).length() > 0){
                Local_image2 = getFromPrefs(AppConstant.gendutymedoffcr_local);
                image_signed_general_duty.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_general_duty_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(getFromPrefs(AppConstant.nurses).length() > 0){
                image3 = getFromPrefs(AppConstant.nurses);

                image_signed_nursesl.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_nursesl_url_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            if (getFromPrefs(AppConstant.nurses_local).length() > 0){
                Local_image3 = getFromPrefs(AppConstant.nurses_local);
                image_signed_nursesl.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_nursesl_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(getFromPrefs(AppConstant.paramedstaff).length() > 0){
                image4 = getFromPrefs(AppConstant.paramedstaff);

                image_signed_paramedical.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_paramedical_url_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (getFromPrefs(AppConstant.paramedstaff_local).length() > 0){
                Local_image4 = getFromPrefs(AppConstant.paramedstaff_local);
                image_signed_paramedical.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_paramedicall_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(getFromPrefs(AppConstant.adminsupportstaff).length() > 0){
                image5 = getFromPrefs(AppConstant.adminsupportstaff);

                image_signed_administrativ.setImageResource(R.mipmap.camera_selected);


                JSONObject json = null;
                try {
                    json = new JSONObject(image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_administrativ_url_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (getFromPrefs(AppConstant.adminsupportstaff_local).length() > 0){
                Local_image5 = getFromPrefs(AppConstant.adminsupportstaff_local);
                image_signed_administrativ.setImageResource(R.mipmap.camera_selected);

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            img_signed_administrativ_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else {
            pojo = new DocumentationPojo();
        }

    }


    @OnClick({R.id.remark_document_related_procedure,R.id.nc_document_related_procedure,R.id.remark_document_showing_process,R.id.nc_document_showing_process,R.id.remark_document_showing_care_patients,R.id.nc_document_showing_care_patients,R.id.remark_document_showing_policies,R.id.nc_document_showing_policies,
            R.id.remark_document_showing_procedures,R.id.nc_document_showing_procedures,R.id.remark_document_showing_procedure_administration,R.id.nc_document_showing_procedure_administration,
            R.id.remark_document_showing_defined_criteria,R.id.nc_document_showing_defined_criteria,R.id.remark_document_showing_procedure_prevention,R.id.nc_document_showing_procedure_prevention,R.id.remark_document_showing_procedure_incorporating,
            R.id.nc_document_showing_procedure_incorporating,R.id.remark_document_showing_procedure_address,R.id.nc_document_showing_procedure_address,R.id.remark_document_showing_policies_procedure,R.id.nc_document_showing_policies_procedure,
            R.id.remark_document_showing_drugs_available,R.id.nc_document_showing_drugs_available,R.id.remark_document_showing_safe_storage,R.id.nc_document_showing_safe_storage,
            R.id.remark_Infection_control_manual_showing,R.id.nc_Infection_control_manual_showing,R.id.remark_document_showing_operational_maintenance,R.id.nc_document_showing_operational_maintenance,R.id.remark_document_showing_safe_exit_plan,
            R.id.nc_document_showing_safe_exit_plan,R.id.remark_document_showing_well_defined_staff,R.id.nc_document_showing_well_defined_staff,R.id.remark_document_showing_disciplinary_grievance,R.id.nc_document_showing_disciplinary_grievance,
            R.id.remark_document_showing_policies_procedures,R.id.nc_document_showing_policies_procedures,R.id.remark_mdocument_showing_retention_time,R.id.nc_document_showing_retention_time,R.id.remark_document_showing_define_process,R.id.nc_document_showing_define_process,
            R.id.remark_document_showing_medical_records,R.id.nc_document_showing_medical_records,R.id.btnSave,R.id.btnSync,
    R.id.image_signed_document,R.id.image_signed_general_duty,R.id.image_signed_nursesl,R.id.image_signed_paramedical,
    R.id.image_signed_administrativ,R.id.nc_signed_document,R.id.nc_signed_general_duty,R.id.nc_signed_nursesl,R.id.nc_signed_paramedical,
    R.id.nc_signed_administrativ})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_document_related_procedure:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_document_related_procedure:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_document_showing_process:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_document_showing_process:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_document_showing_care_patients:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_document_showing_care_patients:
                displayNCDialog("NC", 3);
                break;

            case R.id.remark_document_showing_policies:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_document_showing_policies:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_document_showing_procedures:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_document_showing_procedures:
                displayNCDialog("NC", 5);
                break;

            case R.id.remark_document_showing_procedure_administration:
                displayCommonDialogWithHeader("Remark", 6);
                break;

            case R.id.nc_document_showing_procedure_administration:
                displayNCDialog("NC", 6);
                break;


            case R.id.remark_document_showing_defined_criteria:
                displayCommonDialogWithHeader("Remark", 7);
                break;

            case R.id.nc_document_showing_defined_criteria:
                displayNCDialog("NC", 7);
                break;

            case R.id.remark_document_showing_procedure_prevention:
                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_document_showing_procedure_prevention:
                displayNCDialog("NC", 8);
                break;

            case R.id.remark_document_showing_procedure_incorporating:
                displayCommonDialogWithHeader("Remark", 9);
                break;

            case R.id.nc_document_showing_procedure_incorporating:
                displayNCDialog("NC", 9);
                break;

            case R.id.remark_document_showing_procedure_address:
                displayCommonDialogWithHeader("Remark", 10);
                break;

            case R.id.nc_document_showing_procedure_address:
                displayNCDialog("NC", 10);
                break;


            case R.id.remark_document_showing_policies_procedure:
                displayCommonDialogWithHeader("Remark", 11);
                break;

            case R.id.nc_document_showing_policies_procedure:
                displayNCDialog("NC", 11);
                break;

            case R.id.remark_document_showing_drugs_available:
                displayCommonDialogWithHeader("Remark", 12);
                break;
            case R.id.nc_document_showing_drugs_available:
                displayNCDialog("NC", 12);
                break;

            case R.id.remark_document_showing_safe_storage:
                displayCommonDialogWithHeader("Remark", 13);
                break;
            case R.id.nc_document_showing_safe_storage:
                displayNCDialog("NC", 13);
                break;


            case R.id.remark_Infection_control_manual_showing:
                displayCommonDialogWithHeader("Remark", 14);
                break;

            case R.id.nc_Infection_control_manual_showing:
                displayNCDialog("NC", 14);
                break;

            case R.id.remark_document_showing_operational_maintenance:
                displayCommonDialogWithHeader("Remark", 15);
                break;

            case R.id.nc_document_showing_operational_maintenance:
                displayNCDialog("NC", 15);
                break;

            case R.id.remark_document_showing_safe_exit_plan:
                displayCommonDialogWithHeader("Remark", 16);
                break;

            case R.id.nc_document_showing_safe_exit_plan:
                displayNCDialog("NC", 16);
                break;

            case R.id.remark_document_showing_well_defined_staff:
                displayCommonDialogWithHeader("Remark", 17);
                break;

            case R.id.nc_document_showing_well_defined_staff:
                displayNCDialog("NC", 17);
                break;


            case R.id.remark_document_showing_disciplinary_grievance:
                displayCommonDialogWithHeader("Remark", 18);
                break;

            case R.id.nc_document_showing_disciplinary_grievance:
                displayNCDialog("NC", 18);
                break;

            case R.id.remark_document_showing_policies_procedures:
                displayCommonDialogWithHeader("Remark", 19);
                break;

            case R.id.nc_document_showing_policies_procedures:
                displayNCDialog("NC", 19);
                break;

            case R.id.remark_mdocument_showing_retention_time:
                displayCommonDialogWithHeader("Remark", 20);
                break;

            case R.id.nc_document_showing_retention_time:
                displayNCDialog("NC", 20);
                break;

            case R.id.remark_document_showing_define_process:
                displayCommonDialogWithHeader("Remark", 21);
                break;

            case R.id.nc_document_showing_define_process:
                displayNCDialog("NC", 21);
                break;


            case R.id.remark_document_showing_medical_records:
                displayCommonDialogWithHeader("Remark", 22);
                break;

            case R.id.nc_document_showing_medical_records:
                displayNCDialog("NC", 22);
                break;

            case R.id.image_signed_document:

                if (img_signed_document_list.size() > 0) {
                    showImageListDialog(img_signed_document_list, 1,"signed_document");
                } else {
                    captureImage(1);
                }
                break;

            case R.id.image_signed_general_duty:
                if (img_signed_general_duty_list.size()> 0) {
                    showImageListDialog(img_signed_general_duty_list, 2,"signed_general_duty");
                } else {
                    captureImage(2);
                }

                break;

            case R.id.image_signed_nursesl:
                if (img_signed_nursesl_list.size()> 0) {
                    showImageListDialog(img_signed_nursesl_list, 3,"signed_nursesl");
                } else {
                    captureImage(3);
                }

                break;

            case R.id.image_signed_paramedical:
                if (img_signed_paramedicall_list.size() > 0) {
                    showImageListDialog(img_signed_paramedicall_list, 4,"signed_paramedicall");
                } else {
                    captureImage(4);
                }
                break;

            case R.id.image_signed_administrativ:

                if (img_signed_administrativ_list.size() > 0) {
                    showImageListDialog(img_signed_administrativ_list, 5,"signed_administrativ");
                } else {
                    captureImage(5);
                }

                break;

            case R.id.nc_signed_document:
                displayNCDialog("NC", 23);
                break;


            case R.id.nc_signed_general_duty:
                displayNCDialog("NC", 24);
                break;


            case R.id.nc_signed_nursesl:
                displayNCDialog("NC", 25);
                break;

            case R.id.nc_signed_paramedical:

                displayNCDialog("NC", 26);
                break;

            case R.id.nc_signed_administrativ:
                displayNCDialog("NC", 27);
                break;


            case R.id.btnSave:
                SavePharmacyData("save","");
                break;

            case R.id.btnSync:

                if(Bed_no < 51){
                    if (document_related_procedure.length() > 0 && document_showing_process.length() > 0 && document_showing_care_patients.length() >0 && document_showing_policies.length() > 0 &&
                            document_showing_procedure_administration.length() > 0 && document_showing_procedure_prevention.length() > 0 && document_showing_procedure_incorporating.length() > 0 &&
                            document_showing_procedure_address.length() > 0 &&
                            Infection_control_manual_showing.length() > 0 && document_showing_operational_maintenance.length() > 0 && document_showing_safe_exit_plan.length() > 0 && document_showing_disciplinary_grievance.length() > 0 &&
                            document_showing_policies_procedures.length() > 0 && document_showing_retention_time.length() > 0 && document_showing_define_process.length() > 0 && document_showing_medical_records.length() > 0
                            && img_signed_document_list.size() > 0 && img_signed_general_duty_list.size() > 0 && img_signed_nursesl_list.size() > 0 &&  img_signed_paramedicall_list.size() > 0 && img_signed_administrativ_list.size() > 0){
                        SavePharmacyData("sync","shco");
                    }else {
                        Toast.makeText(DocumentationActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {
                    if (document_related_procedure.length() > 0 && document_showing_process.length() > 0 && document_showing_care_patients.length() >0 && document_showing_policies.length() > 0 &&
                            document_showing_procedures.length() > 0 && document_showing_procedure_administration.length() > 0 && document_showing_defined_criteria.length() > 0 && document_showing_procedure_prevention.length() > 0 && document_showing_procedure_incorporating.length() > 0 &&
                            document_showing_procedure_address.length() > 0 && document_showing_policies_procedure.length() > 0 && document_showing_drugs_available.length() >0 && document_showing_safe_storage.length() > 0 &&
                            Infection_control_manual_showing.length() > 0 && document_showing_operational_maintenance.length() > 0 && document_showing_safe_exit_plan.length() > 0 && document_showing_well_defined_staff.length() > 0 && document_showing_disciplinary_grievance.length() > 0 &&
                            document_showing_policies_procedures.length() > 0 && document_showing_retention_time.length() > 0 && document_showing_define_process.length() > 0 && document_showing_medical_records.length() > 0
                            && img_signed_document_list.size() > 0 && img_signed_general_duty_list.size() > 0 && img_signed_nursesl_list.size() > 0 &&  img_signed_paramedicall_list.size() > 0 && img_signed_administrativ_list.size() > 0){
                        SavePharmacyData("sync","hco");
                    }else {
                        Toast.makeText(DocumentationActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

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
            case R.id.document_related_procedure_yes:
                if (checked)
                    document_related_procedure = "Yes";
                break;

            case R.id.document_related_procedure_no:
                if (checked)
                    document_related_procedure = "No";
                break;

            case R.id.document_showing_process_yes:
                document_showing_process = "Yes";
                break;

            case R.id.document_showing_process_no:
                document_showing_process = "No";
                break;

            case R.id.document_showing_care_patients_yes:
                if (checked)
                    document_showing_care_patients = "Yes";
                break;
            case R.id.document_showing_care_patients_no:
                if (checked)
                    document_showing_care_patients = "No";
                break;


            case R.id.document_showing_policies_yes:
                if (checked)
                    document_showing_policies = "Yes";
                break;
            case R.id.document_showing_policies_no:
                if (checked)
                    document_showing_policies = "No";
                break;

            case R.id.document_showing_procedures_yes:
                document_showing_procedures = "Yes";
                break;

            case R.id.document_showing_procedures_no:
                document_showing_procedures = "No";
                break;


            case R.id.document_showing_procedure_administration_yes:
                document_showing_procedure_administration = "Yes";
                break;

            case R.id.document_showing_procedure_administration_no:
                document_showing_procedure_administration = "No";
                break;

            case R.id.document_showing_defined_criteria_yes:
                document_showing_defined_criteria = "Yes";
                break;

            case R.id.document_showing_defined_criteria_no:
                document_showing_defined_criteria = "No";
                break;

            case R.id.document_showing_procedure_prevention_yes:
                document_showing_procedure_prevention = "Yes";
                break;

            case R.id.document_showing_procedure_prevention_no:
                document_showing_procedure_prevention = "No";
                break;

            case R.id.document_showing_procedure_incorporating_yes:
                document_showing_procedure_incorporating = "Yes";
                break;

            case R.id.document_showing_procedure_incorporating_no:
                document_showing_procedure_incorporating = "No";
                break;

            case R.id.document_showing_procedure_address_yes:
                document_showing_procedure_address = "Yes";

                break;

            case R.id.document_showing_procedure_address_no:
                document_showing_procedure_address = "No";
                break;

            case R.id.document_showing_policies_procedure_yes:
                document_showing_policies_procedure = "Yes";
                break;

            case R.id.document_showing_policies_procedure_no:
                document_showing_policies_procedure = "No";
                break;

            case R.id.document_showing_drugs_available_yes:
                if (checked)
                    document_showing_drugs_available = "Yes";
                break;

            case R.id.document_showing_drugs_available_no:
                if (checked)
                    document_showing_drugs_available = "No";
                break;

            case R.id.document_showing_safe_storage_yes:
                document_showing_safe_storage = "Yes";
                break;

            case R.id.document_showing_safe_storage_no:
                document_showing_safe_storage = "No";
                break;

            case R.id.Infection_control_manual_showing_yes:
                if (checked)
                    Infection_control_manual_showing = "Yes";
                break;
            case R.id.Infection_control_manual_showing_no:
                if (checked)
                    Infection_control_manual_showing = "No";
                break;


            case R.id.document_showing_operational_maintenance_yes:
                if (checked)
                    document_showing_operational_maintenance = "Yes";
                break;
            case R.id.document_showing_operational_maintenance_no:
                if (checked)
                    document_showing_operational_maintenance = "No";
                break;

            case R.id.document_showing_safe_exit_plan_yes:
                document_showing_safe_exit_plan = "Yes";
                break;

            case R.id.document_showing_safe_exit_plan_no:
                document_showing_safe_exit_plan = "No";
                break;


            case R.id.document_showing_well_defined_staff_yes:
                document_showing_well_defined_staff = "Yes";
                break;

            case R.id.document_showing_well_defined_staff_no:
                document_showing_well_defined_staff = "No";
                break;

            case R.id.document_showing_disciplinary_grievance_yes:
                document_showing_disciplinary_grievance = "Yes";
                break;

            case R.id.document_showing_disciplinary_grievance_no:
                document_showing_disciplinary_grievance = "No";
                break;

            case R.id.document_showing_policies_procedures_yes:
                document_showing_policies_procedures = "Yes";
                break;

            case R.id.document_showing_policies_procedures_no:
                document_showing_policies_procedures = "No";
                break;

            case R.id.document_showing_retention_time_yes:
                document_showing_retention_time = "Yes";
                break;

            case R.id.document_showing_retention_time_no:
                document_showing_retention_time = "No";
                break;

            case R.id.document_showing_define_process_yes:
                document_showing_define_process = "Yes";

                break;

            case R.id.document_showing_define_process_no:
                document_showing_define_process = "No";
                break;

            case R.id.document_showing_medical_records_yes:
                document_showing_medical_records = "Yes";
                break;

            case R.id.document_showing_medical_records_no:
                document_showing_medical_records = "No";
                break;

        }
    }



    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(DocumentationActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                    if (position == 12) {
                        radio_status12 = "Yes";
                    }
                    if (position == 13) {
                        radio_status13 = "Yes";
                    }
                    if (position == 14) {
                        radio_status14 = "Yes";
                    }
                    if (position == 15) {
                        radio_status15 = "Yes";
                    }
                    if (position == 16) {
                        radio_status16 = "Yes";
                    }
                    if (position == 17) {
                        radio_status17 = "Yes";
                    }
                    if (position == 18) {
                        radio_status18 = "Yes";
                    }
                    if (position == 19) {
                        radio_status19 = "Yes";
                    }
                    if (position == 20) {
                        radio_status20 = "Yes";
                    }
                    if (position == 21) {
                        radio_status21 = "Yes";
                    }
                    if (position == 22) {
                        radio_status22 = "Yes";
                    }
                    if (position == 23) {
                        radio_status23 = "Yes";
                    }
                    if (position == 24) {
                        radio_status24 = "Yes";
                    }
                    if (position == 25) {
                        radio_status25 = "Yes";
                    }
                    if (position == 26) {
                        radio_status26 = "Yes";
                    }
                    if (position == 27) {
                        radio_status27 = "Yes";
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
                    if (position == 12) {
                        radio_status12 = "close";

                        edit_text.setText("");
                    }
                    if (position == 13) {
                        radio_status13 = "close";

                        edit_text.setText("");
                    }
                    if (position == 14) {
                        radio_status14 = "close";

                        edit_text.setText("");
                    }
                    if (position == 15) {
                        radio_status15 = "close";

                        edit_text.setText("");
                    }
                    if (position == 16) {
                        radio_status16 = "close";

                        edit_text.setText("");
                    }
                    if (position == 17) {
                        radio_status17 = "close";

                        edit_text.setText("");
                    }
                    if (position == 18) {
                        radio_status18 = "close";

                        edit_text.setText("");
                    }
                    if (position == 19) {
                        radio_status19 = "close";

                        edit_text.setText("");
                    }
                    if (position == 20) {
                        radio_status20 = "close";

                        edit_text.setText("");
                    }
                    if (position == 21) {
                        radio_status21 = "close";

                        edit_text.setText("");
                    }
                    if (position == 22) {
                        radio_status22 = "close";

                        edit_text.setText("");
                    }
                    if (position == 23) {
                        radio_status23 = "close";

                        edit_text.setText("");
                    }
                    if (position == 24) {
                        radio_status24 = "close";

                        edit_text.setText("");
                    }
                    if (position == 25) {
                        radio_status25 = "close";

                        edit_text.setText("");
                    }
                    if (position == 26) {
                        radio_status26 = "close";

                        edit_text.setText("");
                    }
                    if (position == 27) {
                        radio_status27 = "close";

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

            if (position == 12) {
                if (nc12 != null) {
                    try {
                        String nc_total = nc12;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status12 = radio;
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
            if (position == 13) {
                if (nc13 != null) {
                    try {
                        String nc_total = nc13;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status13 = radio;
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

            if (position == 14) {
                if (nc14 != null) {
                    try {
                        String nc_total = nc14;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status14 = radio;
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

            if (position == 15) {
                if (nc15 != null) {
                    try {
                        String nc_total = nc15;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status15 = radio;
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

            if (position == 16) {
                if (nc16 != null) {
                    try {
                        String nc_total = nc16;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status16 = radio;
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

            if (position == 17) {
                if (nc17 != null) {
                    try {
                        String nc_total = nc17;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status17 = radio;
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

            if (position == 18) {
                if (nc18 != null) {
                    try {
                        String nc_total = nc18;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status18 = radio;
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

            if (position == 19) {
                if (nc19 != null) {
                    try {
                        String nc_total = nc19;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status19 = radio;
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

            if (position == 20) {
                if (nc20 != null) {
                    try {
                        String nc_total = nc20;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status20 = radio;
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

            if (position == 21) {
                if (nc21 != null) {
                    try {
                        String nc_total = nc21;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status21 = radio;
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

            if (position == 22) {
                if (nc22 != null) {
                    try {
                        String nc_total = nc22;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status22 = radio;
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

            if (position == 23) {
                if (nc23 != null) {
                    try {
                        String nc_total = nc23;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status23 = radio;
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
            if (position == 24) {
                if (nc24 != null) {
                    try {
                        String nc_total = nc24;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status24 = radio;
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

            if (position == 25) {
                if (nc25 != null) {
                    try {
                        String nc_total = nc25;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status25 = radio;
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

            if (position == 26) {
                if (nc26 != null) {
                    try {
                        String nc_total = nc26;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status26 = radio;
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
            if (position == 27) {
                if (nc27 != null) {
                    try {
                        String nc_total = nc27;
                        String[] separated = nc_total.split(",", 2);
                        String radio = separated[0];
                        radio_status27 = radio;
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
                                nc_document_related_procedure.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_document_related_procedure.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_document_showing_process.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_process.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_document_showing_care_patients.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_care_patients.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_document_showing_policies.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_policies.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_document_showing_procedures.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_procedures.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_document_showing_procedure_administration.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_procedure_administration.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 7) {
                        if (radio_status7 != null) {

                            if (radio_status7.equalsIgnoreCase("close")) {

                                nc_document_showing_defined_criteria.setImageResource(R.mipmap.nc);

                                nc7 = radio_status7;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status7.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_defined_criteria.setImageResource(R.mipmap.nc_selected);

                                    nc7 = radio_status7 + "," + edit_text.getText().toString();

                                    radio_status7 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_document_showing_procedure_prevention.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_procedure_prevention.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 9) {
                        if (radio_status9 != null) {

                            if (radio_status9.equalsIgnoreCase("close")) {

                                nc_document_showing_procedure_incorporating.setImageResource(R.mipmap.nc);

                                nc9 = radio_status9;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status9.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_procedure_incorporating.setImageResource(R.mipmap.nc_selected);

                                    nc9 = radio_status9 + "," + edit_text.getText().toString();

                                    radio_status9 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 10) {
                        if (radio_status10 != null) {

                            if (radio_status10.equalsIgnoreCase("close")) {

                                nc_document_showing_procedure_address.setImageResource(R.mipmap.nc);

                                nc10 = radio_status10;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status10.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_document_showing_procedure_address.setImageResource(R.mipmap.nc_selected);

                                    nc10 = radio_status10 + "," + edit_text.getText().toString();

                                    radio_status10 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 11) {
                        if (radio_status11 != null) {

                            if (radio_status11.equalsIgnoreCase("close")) {

                                nc_document_showing_policies_procedure.setImageResource(R.mipmap.nc);

                                nc11 = radio_status11;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status11.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_document_showing_policies_procedure.setImageResource(R.mipmap.nc_selected);

                                    nc11 = radio_status11 + "," + edit_text.getText().toString();

                                    radio_status11 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    if (position == 12) {
                        if (radio_status12 != null) {

                            if (radio_status12.equalsIgnoreCase("close")) {
                                nc_document_showing_drugs_available.setImageResource(R.mipmap.nc);

                                nc12 = radio_status12;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status12.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_document_showing_drugs_available.setImageResource(R.mipmap.nc_selected);

                                    nc12 = radio_status12 + "," + edit_text.getText().toString();

                                    radio_status12 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 13) {
                        if (radio_status13 != null) {

                            if (radio_status13.equalsIgnoreCase("close")){
                                nc_document_showing_safe_storage.setImageResource(R.mipmap.nc);

                                nc13 = radio_status13 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status13.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_safe_storage.setImageResource(R.mipmap.nc_selected);

                                    nc13 = radio_status13 + "," + edit_text.getText().toString();

                                    radio_status13 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 14) {
                        if (radio_status14 != null) {

                            if (radio_status14.equalsIgnoreCase("close")){
                                nc_Infection_control_manual_showing.setImageResource(R.mipmap.nc);

                                nc14 = radio_status14 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status14.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_Infection_control_manual_showing.setImageResource(R.mipmap.nc_selected);

                                    nc14 = radio_status14 + "," + edit_text.getText().toString();

                                    radio_status14 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 15) {
                        if (radio_status15 != null) {

                            if (radio_status15.equalsIgnoreCase("close")){
                                nc_document_showing_operational_maintenance.setImageResource(R.mipmap.nc);

                                nc15 = radio_status15 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status15.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_operational_maintenance.setImageResource(R.mipmap.nc_selected);

                                    nc15 = radio_status15 + "," + edit_text.getText().toString();

                                    radio_status15 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 16) {
                        if (radio_status16 != null) {

                            if (radio_status16.equalsIgnoreCase("close")){
                                nc_document_showing_safe_exit_plan.setImageResource(R.mipmap.nc);

                                nc16 = radio_status16 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status16.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_document_showing_safe_exit_plan.setImageResource(R.mipmap.nc_selected);

                                    nc16 = radio_status16 + "," + edit_text.getText().toString();

                                    radio_status16 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 17) {
                        if (radio_status17 != null) {

                            if (radio_status17.equalsIgnoreCase("close")) {

                                nc_document_showing_well_defined_staff.setImageResource(R.mipmap.nc);

                                nc17 = radio_status17;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status17.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_well_defined_staff.setImageResource(R.mipmap.nc_selected);

                                    nc17 = radio_status17 + "," + edit_text.getText().toString();

                                    radio_status17 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 18) {
                        if (radio_status18 != null) {

                            if (radio_status18.equalsIgnoreCase("close")) {

                                nc_document_showing_disciplinary_grievance.setImageResource(R.mipmap.nc);

                                nc18 = radio_status18;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status18.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_disciplinary_grievance.setImageResource(R.mipmap.nc_selected);

                                    nc18 = radio_status18 + "," + edit_text.getText().toString();

                                    radio_status18 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 19) {
                        if (radio_status19 != null) {

                            if (radio_status19.equalsIgnoreCase("close")) {

                                nc_document_showing_policies_procedures.setImageResource(R.mipmap.nc);

                                nc19 = radio_status19;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status19.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_policies_procedures.setImageResource(R.mipmap.nc_selected);

                                    nc19 = radio_status19 + "," + edit_text.getText().toString();

                                    radio_status19 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 20) {
                        if (radio_status20 != null) {

                            if (radio_status20.equalsIgnoreCase("close")) {

                                nc_document_showing_retention_time.setImageResource(R.mipmap.nc);

                                nc20 = radio_status20;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status20.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_retention_time.setImageResource(R.mipmap.nc_selected);

                                    nc20 = radio_status20 + "," + edit_text.getText().toString();

                                    radio_status20 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 21) {
                        if (radio_status21 != null) {

                            if (radio_status21.equalsIgnoreCase("close")) {

                                nc_document_showing_define_process.setImageResource(R.mipmap.nc);

                                nc21 = radio_status21;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status21.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_document_showing_define_process.setImageResource(R.mipmap.nc_selected);

                                    nc21 = radio_status21 + "," + edit_text.getText().toString();

                                    radio_status21 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 22) {
                        if (radio_status22 != null) {

                            if (radio_status22.equalsIgnoreCase("close")) {

                                nc_document_showing_medical_records.setImageResource(R.mipmap.nc);

                                nc22 = radio_status22;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status22.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_document_showing_medical_records.setImageResource(R.mipmap.nc_selected);

                                    nc22 = radio_status22 + "," + edit_text.getText().toString();

                                    radio_status22 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 23) {
                        if (radio_status23 != null) {

                            if (radio_status23.equalsIgnoreCase("close")) {

                                nc_signed_document.setImageResource(R.mipmap.nc);

                                nc23 = radio_status23;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status23.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_signed_document.setImageResource(R.mipmap.nc_selected);

                                    nc23 = radio_status23 + "," + edit_text.getText().toString();

                                    radio_status23 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 24) {
                        if (radio_status24 != null) {

                            if (radio_status24.equalsIgnoreCase("close")) {

                                nc_signed_general_duty.setImageResource(R.mipmap.nc);

                                nc24 = radio_status24;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status24.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_signed_general_duty.setImageResource(R.mipmap.nc_selected);

                                    nc24 = radio_status24 + "," + edit_text.getText().toString();

                                    radio_status24 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 25) {
                        if (radio_status25 != null) {

                            if (radio_status25.equalsIgnoreCase("close")) {

                                nc_signed_nursesl.setImageResource(R.mipmap.nc);

                                nc25 = radio_status25;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status25.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_signed_nursesl.setImageResource(R.mipmap.nc_selected);

                                    nc25 = radio_status25 + "," + edit_text.getText().toString();

                                    radio_status25 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 26) {
                        if (radio_status26 != null) {

                            if (radio_status26.equalsIgnoreCase("close")) {

                                nc_signed_paramedical.setImageResource(R.mipmap.nc);

                                nc26 = radio_status26;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status26.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_signed_paramedical.setImageResource(R.mipmap.nc_selected);

                                    nc26 = radio_status26 + "," + edit_text.getText().toString();

                                    radio_status26 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 27) {
                        if (radio_status27 != null) {

                            if (radio_status27.equalsIgnoreCase("close")) {

                                nc_signed_administrativ.setImageResource(R.mipmap.nc);

                                nc27 = radio_status27;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status27.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_signed_administrativ.setImageResource(R.mipmap.nc_selected);

                                    nc27 = radio_status27 + "," + edit_text.getText().toString();

                                    radio_status27 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(DocumentationActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(DocumentationActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

            if (position == 12) {
                if (remark12 != null) {
                    edit_text.setText(remark12);
                }
            }
            if (position == 13) {
                if (remark13 != null) {
                    edit_text.setText(remark13);
                }
            }
            if (position == 14) {
                if (remark14 != null) {
                    edit_text.setText(remark14);
                }
            }
            if (position == 15) {
                if (remark15 != null) {
                    edit_text.setText(remark15);
                }
            }
            if (position == 16) {
                if (remark16 != null) {
                    edit_text.setText(remark16);
                }
            }

            if (position == 17) {
                if (remark17 != null) {
                    edit_text.setText(remark17);
                }
            }

            if (position == 18) {
                if (remark18 != null) {
                    edit_text.setText(remark18);
                }
            }

            if (position == 19) {
                if (remark19 != null) {
                    edit_text.setText(remark19);
                }
            }

            if (position == 20) {
                if (remark20 != null) {
                    edit_text.setText(remark20);
                }
            }

            if (position == 21) {
                if (remark21 != null) {
                    edit_text.setText(remark21);
                }
            }

            if (position == 22) {
                if (remark22 != null) {
                    edit_text.setText(remark22);
                }
            }

            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_document_related_procedure.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_document_showing_process.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_document_showing_care_patients.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_document_showing_policies.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_document_showing_procedures.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_document_showing_procedure_administration.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 7) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark7 = edit_text.getText().toString();
                            remark_document_showing_defined_criteria.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_document_showing_procedure_prevention.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 9) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark9 = edit_text.getText().toString();
                            remark_document_showing_procedure_incorporating.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 10) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark10 = edit_text.getText().toString();
                            remark_document_showing_procedure_address.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 11) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark11 = edit_text.getText().toString();
                            remark_document_showing_policies_procedure.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    if (position == 12) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark12 = edit_text.getText().toString();
                            remark_document_showing_drugs_available.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 13) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark13 = edit_text.getText().toString();
                            remark_document_showing_safe_storage.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 14) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark14 = edit_text.getText().toString();
                            remark_Infection_control_manual_showing.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 15) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark15 = edit_text.getText().toString();
                            remark_document_showing_operational_maintenance.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 16) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark16 = edit_text.getText().toString();
                            remark_document_showing_safe_exit_plan.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 17) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark17 = edit_text.getText().toString();
                            remark_document_showing_well_defined_staff.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 18) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark18 = edit_text.getText().toString();
                            remark_document_showing_disciplinary_grievance.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 19) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark19 = edit_text.getText().toString();
                            remark_document_showing_policies_procedures.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 20) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark20 = edit_text.getText().toString();
                            remark_mdocument_showing_retention_time.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 21) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark21 = edit_text.getText().toString();
                            remark_document_showing_define_process.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 22) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark22 = edit_text.getText().toString();
                            remark_document_showing_medical_records.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(DocumentationActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }


    public void SavePharmacyData(String from,String hospital_status) {

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(20);
        pojo.setDocument_related_procedure(document_related_procedure);
        pojo.setDocument_showing_process(document_showing_process);
        pojo.setDocument_showing_care_patients(document_showing_care_patients);
        pojo.setDocument_showing_policies(document_showing_policies);
        pojo.setDocument_showing_procedures(document_showing_procedures);
        pojo.setDocument_showing_procedure_administration(document_showing_procedure_administration);
        pojo.setDocument_showing_defined_criteria(document_showing_defined_criteria);
        pojo.setDocument_showing_procedure_prevention(document_showing_procedure_prevention);
        pojo.setDocument_showing_procedure_incorporating(document_showing_procedure_incorporating);

        pojo.setDocument_showing_procedure_address(document_showing_procedure_address);
        pojo.setDocument_showing_policies_procedure(document_showing_policies_procedure);
        pojo.setDocument_showing_drugs_available(document_showing_drugs_available);
        pojo.setDocument_showing_safe_storage(document_showing_safe_storage);

        pojo.setInfection_control_manual_showing(Infection_control_manual_showing);
        pojo.setDocument_showing_operational_maintenance(document_showing_operational_maintenance);

        pojo.setDocument_showing_safe_exit_plan(document_showing_safe_exit_plan);
        pojo.setDocument_showing_well_defined_staff(document_showing_well_defined_staff);
        pojo.setDocument_showing_disciplinary_grievance(document_showing_disciplinary_grievance);

        pojo.setDocument_showing_policies_procedures(document_showing_policies_procedures);
        pojo.setDocument_showing_retention_time(document_showing_retention_time);
        pojo.setDocument_showing_define_process(document_showing_define_process);
        pojo.setDocument_showing_medical_records(document_showing_medical_records);


        pojo.setDocument_related_procedure_remark(remark1);
        pojo.setDocument_related_procedure_nc(nc1);


        pojo.setDocument_showing_process_remark(remark2);
        pojo.setDocument_showing_process_nc(nc2);


        pojo.setDocument_showing_care_patients_remark(remark3);
        pojo.setDocument_showing_care_patients_nc(nc3);


        pojo.setDocument_showing_policies_remark(remark4);
        pojo.setDocument_showing_policies_nc(nc4);

        pojo.setDocument_showing_procedures_remark(remark5);
        pojo.setDocument_showing_procedures_nc(nc5);


        pojo.setDocument_showing_procedure_administration_remark(remark6);
        pojo.setDocument_showing_procedure_administration_nc(nc6);


        pojo.setDocument_showing_defined_criteria_remark(remark7);
        pojo.setDocument_showing_defined_criteria_nc(nc7);

        pojo.setDocument_showing_procedure_prevention_remark(remark8);
        pojo.setDocument_showing_procedure_prevention_nc(nc8);


        pojo.setDocument_showing_procedure_incorporating_remark(remark9);
        pojo.setDocument_showing_procedure_incorporating_nc(nc9);

        pojo.setDocument_showing_procedure_address_remark(remark10);
        pojo.setDocument_showing_procedure_address_nc(nc10);



        pojo.setDocument_showing_policies_procedure_remark(remark11);
        pojo.setDocument_showing_policies_procedure_nc(nc11);

        pojo.setDocument_showing_drugs_available_remark(remark12);
        pojo.setDocument_showing_drugs_available_nc(nc12);

        pojo.setDocument_showing_safe_storage_remark(remark13);
        pojo.setDocument_showing_safe_storage_nc(nc13);

        pojo.setInfection_control_manual_showing_remark(remark14);
        pojo.setInfection_control_manual_showing_nc(nc14);

        pojo.setDocument_showing_operational_maintenance_remark(remark15);
        pojo.setDocument_showing_operational_maintenance_nc(nc15);

        pojo.setDocument_showing_safe_exit_plan_remark(remark16);
        pojo.setDocument_showing_safe_exit_plan_nc(nc16);

        pojo.setDocument_showing_well_defined_staff_remark(remark17);
        pojo.setDocument_showing_well_defined_staff_nc(nc17);

        pojo.setDocument_showing_disciplinary_grievance_remark(remark18);
        pojo.setDocument_showing_disciplinary_grievance_nc(nc18);

        pojo.setDocument_showing_policies_procedures_remark(remark19);
        pojo.setDocument_showing_policies_procedures_nc(nc19);

        pojo.setDocument_showing_retention_time_remark(remark20);
        pojo.setDocument_showing_retention_time_nc(nc20);

        pojo.setDocument_showing_define_process_remark(remark21);
        pojo.setDocument_showing_define_process_nc(nc21);

        pojo.setDocument_showing_medical_records_remark(remark22);
        pojo.setDocument_showing_medical_records_nc(nc22);

        pojo.setSigndocscopeofservices_nc(nc23);
        pojo.setSignlistgendutymedoffcr_nc(nc24);
        pojo.setSignlistnurses_nc(nc25);
        pojo.setSignlistparamedstaff_nc(nc26);
        pojo.setSignlistadminsupportstaff_nc(nc27);


        JSONObject json = new JSONObject();

        if (img_signed_document_url_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_document_url_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }


        if (img_signed_document_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_document_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }

        if (img_signed_general_duty_url_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_general_duty_url_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }


        if (img_signed_general_duty_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_general_duty_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }

        if (img_signed_nursesl_url_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_nursesl_url_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }


        if (img_signed_nursesl_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_nursesl_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }

        if (img_signed_paramedical_url_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_paramedical_url_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image4 = json.toString();
        }else {
            image4 = null;
        }


        if (img_signed_paramedicall_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_paramedicall_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image4 = json.toString();
        }else {
            Local_image4 = null;
        }

        if (img_signed_administrativ_url_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_administrativ_url_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image5 = json.toString();
        }else {
            image5 = null;
        }


        if (img_signed_administrativ_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(img_signed_administrativ_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image5 = json.toString();
        }else {
            Local_image5 = null;
        }



        saveIntoPrefs(AppConstant.scopeofservices,image1);
        saveIntoPrefs(AppConstant.scopeofservices_local,Local_image1);

        saveIntoPrefs(AppConstant.gendutymedoffcr,image2);
        saveIntoPrefs(AppConstant.gendutymedoffcr_local,Local_image2);

        saveIntoPrefs(AppConstant.nurses,image3);
        saveIntoPrefs(AppConstant.nurses_local,Local_image3);

        saveIntoPrefs(AppConstant.paramedstaff,image4);
        saveIntoPrefs(AppConstant.paramedstaff_local,Local_image4);

        saveIntoPrefs(AppConstant.adminsupportstaff,image5);
        saveIntoPrefs(AppConstant.adminsupportstaff_local,Local_image5);


        if (sql_status) {
            boolean sh_status = databaseHandler.UPDATE_DOCUMENTATION(pojo);

            if (sh_status){
                if (!from.equalsIgnoreCase("sync")) {

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(20).getHospital_id());
                    pojo.setAssessement_name("Documentation");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(20).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(DocumentationActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(DocumentationActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        //progreesDialog();

                        new PostSHCODataTask().execute();
                    }else {
                        //progreesDialog();
                        new PostDataTask().execute();

                    }
                }
            }
        } else {
            boolean sh_status = databaseHandler.INSERT_Documentation(pojo);

            if (sh_status){
                if (!from.equalsIgnoreCase("sync")) {

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(20).getHospital_id());
                    pojo.setAssessement_name("Documentation");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(20).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(DocumentationActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(DocumentationActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (hospital_status.equalsIgnoreCase("shco")){

                        //progreesDialog();
                        new PostSHCODataTask().execute();
                        //Post_SHCO_LaboratoryData();
                    }else {
                        //progreesDialog();
                        new PostDataTask().execute();
                        //PostLaboratoryData();
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
        check = 1;
        for(int i=img_signed_document_url_list.size(); i<img_signed_document_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_document_list.get(i) + "selfie");
            UploadImage(img_signed_document_list.get(i),"selfie");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_general_duty_url_list.size(); i< img_signed_general_duty_list.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_general_duty_list.get(i)+ "hospital_board");
            UploadImage(img_signed_general_duty_list.get(i),"hospital_board");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_nursesl_url_list.size();i < img_signed_nursesl_list.size() ;i++ )
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_nursesl_list.get(i)+ "authorised_person");
            UploadImage(img_signed_nursesl_list.get(i),"authorised_person");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i= img_signed_paramedical_url_list.size(); i< img_signed_paramedicall_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_paramedicall_list.get(i)+ "front_hospital");
            UploadImage(img_signed_paramedicall_list.get(i),"front_hospital");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_administrativ_url_list.size() ; i<  img_signed_administrativ_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_administrativ_list.get(i)+"back_hospital");
            UploadImage(img_signed_administrativ_list.get(i),"back_hospital");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
            pojo_dataSync.setTabName("Documentation");
            pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
            pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
            if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
            }else {
                pojo_dataSync.setAssessment_id(0);
            }
             pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));


            for (int i=0;i<img_signed_document_url_list.size();i++){
                String value_signed_do = img_signed_document_url_list.get(i);

                st_signed_document = value_signed_do + st_signed_document;
            }

            for (int i = 0;i<img_signed_general_duty_url_list.size();i++){
                String value_general_duty = img_signed_general_duty_url_list.get(i);

                st_signed_general_duty = value_general_duty + st_signed_general_duty;
            }

            for (int i=0;i<img_signed_nursesl_url_list.size();i++){
                String value_signed_nursesl = img_signed_nursesl_url_list.get(i);

                st_signed_nursesl = value_signed_nursesl + st_signed_nursesl;
            }
            for (int i=0;i< img_signed_paramedical_url_list.size();i++){
                String value_signed_paramedical = img_signed_paramedical_url_list.get(i);

                st_signed_paramedical = value_signed_paramedical + st_signed_paramedical;
            }

            for (int i=0;i<img_signed_administrativ_url_list.size();i++){
                String value_signed_administrativ = img_signed_administrativ_url_list.get(i);

                st_signed_administrativ = value_signed_administrativ + st_signed_administrativ;

            }

            pojo.setSigndocscopeofservices_image(st_signed_document);
            pojo.setSignlistgendutymedoffcr_image(st_signed_general_duty);
            pojo.setSignlistnurses_image(st_signed_nursesl);
            pojo.setSignlistparamedstaff_image(st_signed_paramedical);
            pojo.setSignlistadminsupportstaff_image(st_signed_administrativ);


            pojo_dataSync.setDocumentation(pojo);


            latch = new CountDownLatch(1);
            mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                @Override
                public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                    System.out.println("xxx sucess");

                    //CloseProgreesDialog();

                    if (response.message().equalsIgnoreCase("Unauthorized")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(DocumentationActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                                Toast.makeText(DocumentationActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                            }
                        });
                    }else {
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(DocumentationActivity.this,HospitalListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                saveIntoPrefs("Pharmacy_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                pojo.setHospital_id(assessement_list.get(20).getHospital_id());
                                pojo.setAssessement_name("Documentation");
                                pojo.setAssessement_status("Done");
                                pojo.setLocal_id(assessement_list.get(20).getLocal_id());

                                databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DocumentationActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
        check = 1;
        for(int i=img_signed_document_url_list.size(); i<img_signed_document_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_document_list.get(i) + "selfie");
            UploadImage(img_signed_document_list.get(i),"selfie");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_general_duty_url_list.size(); i< img_signed_general_duty_list.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_general_duty_list.get(i)+ "hospital_board");
            UploadImage(img_signed_general_duty_list.get(i),"hospital_board");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_nursesl_url_list.size();i < img_signed_nursesl_list.size() ;i++ )
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_nursesl_list.get(i)+ "authorised_person");
            UploadImage(img_signed_nursesl_list.get(i),"authorised_person");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i= img_signed_paramedical_url_list.size(); i< img_signed_paramedicall_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_paramedicall_list.get(i)+ "front_hospital");
            UploadImage(img_signed_paramedicall_list.get(i),"front_hospital");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = img_signed_administrativ_url_list.size() ; i<  img_signed_administrativ_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",img_signed_administrativ_list.get(i)+"back_hospital");
            UploadImage(img_signed_administrativ_list.get(i),"back_hospital");
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
                        Toast.makeText(DocumentationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DocumentationActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
            pojo_dataSync.setTabName("Documentation");
            pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
            pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
            if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
            }else {
                pojo_dataSync.setAssessment_id(0);
            }
            pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));

            for (int i=0;i<img_signed_document_url_list.size();i++){
                String value_signed_do = img_signed_document_url_list.get(i);

                st_signed_document = value_signed_do + st_signed_document;
            }

            for (int i = 0;i<img_signed_general_duty_url_list.size();i++){
                String value_general_duty = img_signed_general_duty_url_list.get(i);

                st_signed_general_duty = value_general_duty + st_signed_general_duty;
            }

            for (int i=0;i<img_signed_nursesl_url_list.size();i++){
                String value_signed_nursesl = img_signed_nursesl_url_list.get(i);

                st_signed_nursesl = value_signed_nursesl + st_signed_nursesl;
            }
            for (int i=0;i< img_signed_paramedical_url_list.size();i++){
                String value_signed_paramedical = img_signed_paramedical_url_list.get(i);

                st_signed_paramedical = value_signed_paramedical + st_signed_paramedical;
            }

            for (int i=0;i<img_signed_administrativ_url_list.size();i++){
                String value_signed_administrativ = img_signed_administrativ_url_list.get(i);

                st_signed_administrativ = value_signed_administrativ + st_signed_administrativ;

            }

            pojo.setSigndocscopeofservices_image(st_signed_document);
            pojo.setSignlistgendutymedoffcr_image(st_signed_general_duty);
            pojo.setSignlistnurses_image(st_signed_nursesl);
            pojo.setSignlistparamedstaff_image(st_signed_paramedical);
            pojo.setSignlistadminsupportstaff_image(st_signed_administrativ);


            pojo_dataSync.setDocumentation(pojo);

            latch = new CountDownLatch(1);
            mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                @Override
                public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                    System.out.println("xxx sucess");

                    //CloseProgreesDialog();

                    if (response.message().equalsIgnoreCase("Unauthorized")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(DocumentationActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                                Toast.makeText(DocumentationActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(DocumentationActivity.this,HospitalListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                saveIntoPrefs("Pharmacy_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                pojo.setHospital_id(assessement_list.get(20).getHospital_id());
                                pojo.setAssessement_name("Documentation");
                                pojo.setAssessement_status("Done");
                                pojo.setLocal_id(assessement_list.get(20).getLocal_id());

                                databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DocumentationActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(DocumentationActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    SaveImage(image2,"selfie");

                }
            }else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"hospital_board");

                }
            }
            else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"authorised_person");

                }
            }

            else if (requestCode == 4) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"front_hospital");

                }
            }
            else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"back_hospital");

                }
            }

        }
    }
    private void UploadImage(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        //final ProgressDialog d = ImageDialog.showLoading(DocumentationActivity.this);
        //d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                //d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DocumentationActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(DocumentationActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("selfie")){
                                img_signed_document_url_list.add(response.body().getMessage());
                                //img_signed_document_list.add(image_path);
                                image_signed_document.setImageResource(R.mipmap.camera_selected);

                                image1 = "selfie";

                            }else if (from.equalsIgnoreCase("hospital_board")){
                                img_signed_general_duty_url_list.add(response.body().getMessage());
                                //img_signed_general_duty_list.add(image_path);
                                image_signed_general_duty.setImageResource(R.mipmap.camera_selected);

                                image2 = "hospital_board";

                            }else if (from.equalsIgnoreCase("authorised_person")){
                                img_signed_nursesl_url_list.add(response.body().getMessage());
                                //img_signed_nursesl_list.add(image_path);
                                image_signed_nursesl.setImageResource(R.mipmap.camera_selected);

                                image3 = "authorised_person";


                            }else if (from.equalsIgnoreCase("front_hospital")){
                                img_signed_paramedical_url_list.add(response.body().getMessage());
                                //img_signed_paramedicall_list.add(image_path);
                                image_signed_paramedical.setImageResource(R.mipmap.camera_selected);

                                image4 = "front_hospital";

                            }else if (from.equalsIgnoreCase("back_hospital")){
                                img_signed_administrativ_url_list.add(response.body().getMessage());
                                //img_signed_administrativ_list.add(image_path);
                                image_signed_administrativ.setImageResource(R.mipmap.camera_selected);

                                image5 = "back_hospital";

                            }
                            check = 1;
                            latch.countDown();
                            //Toast.makeText(DocumentationActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(DocumentationActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(DocumentationActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("selfie")){
            //img_signed_document_url_list.add(response.body().getMessage());
            img_signed_document_list.add(image_path);
            image_signed_document.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "selfie";

        }else if (from.equalsIgnoreCase("hospital_board")){
            //img_signed_general_duty_url_list.add(response.body().getMessage());
            img_signed_general_duty_list.add(image_path);
            image_signed_general_duty.setImageResource(R.mipmap.camera_selected);

            Local_image2 = "hospital_board";

        }else if (from.equalsIgnoreCase("authorised_person")){
            //img_signed_nursesl_url_list.add(response.body().getMessage());
            img_signed_nursesl_list.add(image_path);
            image_signed_nursesl.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "authorised_person";


        }else if (from.equalsIgnoreCase("front_hospital")){
            //img_signed_paramedical_url_list.add(response.body().getMessage());
            img_signed_paramedicall_list.add(image_path);
            image_signed_paramedical.setImageResource(R.mipmap.camera_selected);

            Local_image4 = "front_hospital";

        }else if (from.equalsIgnoreCase("back_hospital")){
            //img_signed_administrativ_url_list.add(response.body().getMessage());
            img_signed_administrativ_list.add(image_path);
            image_signed_administrativ.setImageResource(R.mipmap.camera_selected);

            Local_image5 = "back_hospital";

        }

        Toast.makeText(DocumentationActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();
    }

    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(DocumentationActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(DocumentationActivity.this,list,from,"Documentation");
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
                    Toast toast = Toast.makeText(DocumentationActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!assessement_list.get(20).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save","");
        }else {
            Intent intent = new Intent(DocumentationActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_delete:

                int pos = (int) v.getTag(R.string.key_image_delete);

                String from = (String)v.getTag(R.string.key_from_name);

                DeleteList(pos,from);

                break;
        }
    }

    private void DeleteList(int position,String from){
        try {
            if (from.equalsIgnoreCase("signed_document")){
                img_signed_document_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (img_signed_document_list.size() == 0){
                    image_signed_document.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("signed_general_duty")){
                img_signed_general_duty_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (img_signed_general_duty_list.size() == 0){
                    image_signed_general_duty.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("signed_nursesl")){
                img_signed_nursesl_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (img_signed_nursesl_list.size() == 0){
                    image_signed_nursesl.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("signed_paramedicall")){
                img_signed_paramedicall_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (img_signed_paramedicall_list.size() == 0){
                    image_signed_paramedical.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("nurses_available_ambulances")){
                img_signed_administrativ_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (img_signed_administrativ_list.size() == 0){
                    image_signed_administrativ.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
