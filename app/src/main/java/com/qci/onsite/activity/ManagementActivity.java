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
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.qci.onsite.pojo.ManagementPojo;
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

public class ManagementActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.requisite_fee_BMW_yes)
    RadioButton requisite_fee_BMW_yes;

    @BindView(R.id.requisite_fee_BMW_no)
    RadioButton requisite_fee_BMW_no;

    @BindView(R.id.remark_requisite_fee_BMW)
    ImageView remark_requisite_fee_BMW;

    @BindView(R.id.nc_requisite_fee_BMW)
    ImageView nc_requisite_fee_BMW;

    @BindView(R.id.management_guide_organization_yes)
    RadioButton management_guide_organization_yes;

    @BindView(R.id.management_guide_organization_no)
    RadioButton management_guide_organization_no;

    @BindView(R.id.remark_management_guide_organization)
    ImageView remark_management_guide_organization;

    @BindView(R.id.nc_management_guide_organization)
    ImageView nc_management_guide_organization;

    @BindView(R.id.hospital_mission_present_yes)
    RadioButton hospital_mission_present_yes;

    @BindView(R.id.hospital_mission_present_no)
    RadioButton hospital_mission_present_no;

    @BindView(R.id.remark_hospital_mission_present)
    ImageView remark_hospital_mission_present;

    @BindView(R.id.nc_hospital_mission_present)
    ImageView nc_hospital_mission_present;

    @BindView(R.id.image_hospital_mission_present)
    ImageView image_hospital_mission_present;

    @BindView(R.id.patient_maintained_OPD_yes)
    RadioButton patient_maintained_OPD_yes;

    @BindView(R.id.patient_maintained_OPD_no)
    RadioButton patient_maintained_OPD_no;

    @BindView(R.id.remark_patient_maintained_OPD)
    ImageView remark_patient_maintained_OPD;

    @BindView(R.id.nc_patient_maintained_OPD)
    ImageView nc_patient_maintained_OPD;

    @BindView(R.id.Image_patient_maintained_OPD)
    ImageView Image_patient_maintained_OPD;


    @BindView(R.id.patient_maintained_IPD_yes)
    RadioButton patient_maintained_IPD_yes;

    @BindView(R.id.patient_maintained_IPD_no)
    RadioButton patient_maintained_IPD_no;

    @BindView(R.id.remark_patient_maintained_IPD)
    ImageView remark_patient_maintained_IPD;

    @BindView(R.id.nc_patient_maintained_IPD)
    ImageView nc_patient_maintained_IPD;

    @BindView(R.id.Image_patient_maintained_IPD)
    ImageView Image_patient_maintained_IPD;

    @BindView(R.id.patient_maintained_Emergency_yes)
    RadioButton patient_maintained_Emergency_yes;

    @BindView(R.id.patient_maintained_Emergency_no)
    RadioButton patient_maintained_Emergency_no;

    @BindView(R.id.remark_patient_maintained_Emergency)
    ImageView remark_patient_maintained_Emergency;

    @BindView(R.id.nc_patient_maintained_Emergency)
    ImageView nc_patient_maintained_Emergency;

    @BindView(R.id.Image_patient_maintained_Emergency)
    ImageView Image_patient_maintained_Emergency;

    @BindView(R.id.basic_Tariff_List_yes)
    RadioButton basic_Tariff_List_yes;

    @BindView(R.id.basic_Tariff_List_no)
    RadioButton basic_Tariff_List_no;

    @BindView(R.id.remark_basic_Tariff_List)
    ImageView remark_basic_Tariff_List;

    @BindView(R.id.nc_basic_Tariff_List)
    ImageView nc_basic_Tariff_List;

    @BindView(R.id.Image_basic_Tariff_List)
    ImageView Image_basic_Tariff_List;

    @BindView(R.id.parameter_patient_identification_yes)
    CheckBox parameter_patient_identification_yes;

    @BindView(R.id.parameter_patient_identification_no)
    CheckBox parameter_patient_identification_no;

    @BindView(R.id.remark_parameter_patient_identification)
    ImageView remark_parameter_patient_identification;

    @BindView(R.id.nc_parameter_patient_identification)
    ImageView nc_parameter_patient_identification;


    @BindView(R.id.quality_improvement_programme_yes)
    RadioButton quality_improvement_programme_yes;

    @BindView(R.id.quality_improvement_programme_no)
    RadioButton quality_improvement_programme_no;

    @BindView(R.id.remark_quality_improvement_programme)
    ImageView remark_quality_improvement_programme;

    @BindView(R.id.nc_quality_improvement_programme)
    ImageView nc_quality_improvement_programme;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    private String remark1, remark2, remark3,remark4,remark5,remark6,remark7,remark8,remark9;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5,nc6,nc7,nc8,nc9;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5,radio_status6,radio_status7,radio_status8,radio_status9;

    private DatabaseHandler databaseHandler;

    private APIService mAPIService;

    private String requisite_fee_BMW = "",management_guide_organization ="",hospital_mission_present="",patient_maintained_OPD = "",patient_maintained_IPD="",patient_maintained_Emergency="",

    basic_Tariff_List = "",parameter_patient_identification ="",quality_improvement_programme="";

    String Check_box1="",Check_box2="";


    private ArrayList<String>hospital_mission_present_list;
    private ArrayList<String>Local_hospital_mission_present_list;

    private ArrayList<String>patient_maintained_OPD_list;
    private ArrayList<String>Local_patient_maintained_OPD_list;

    private ArrayList<String>patient_maintained_IPD_list;
    private ArrayList<String>Local_patient_maintained_IPD_list;

    private ArrayList<String>patient_maintained_Emergency_list;
    private ArrayList<String>Local_patient_maintained_Emergency_list;

    private ArrayList<String>basic_Tariff_List_list;
    private ArrayList<String>Local_basic_Tariff_List_list;

    private ManagementPojo pojo;

    private String image3,image4,image5,image6,image7;
    private String Local_image3,Local_image4,Local_image5,Local_image6,Local_image7;


    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    Uri videoUri;

    public Boolean sql_status = false;

    DataSyncRequest pojo_dataSync;

    String hospital_mission_present_view = "",patient_maintained_OPD_view ="",patient_maintained_IPD_view = "",patient_maintained_Emergency_view = "",
            basic_Tariff_List_view = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        ButterKnife.bind(this);

        setDrawerbackIcon("Management");

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

        basic_Tariff_List_list = new ArrayList<>();
        Local_basic_Tariff_List_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        pojo = new ManagementPojo();

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();
    }

    public void getPharmacyData(){

        pojo = databaseHandler.getManagementPojo("14");

        if (pojo != null){
            sql_status = true;
            if (pojo.getRequisite_fee_BMW() != null){
                requisite_fee_BMW = pojo.getRequisite_fee_BMW();
                if (pojo.getRequisite_fee_BMW().equalsIgnoreCase("Yes")){
                    requisite_fee_BMW_yes.setChecked(true);
                }else if (pojo.getRequisite_fee_BMW().equalsIgnoreCase("No")){
                    requisite_fee_BMW_no.setChecked(true);
                }
            }
            if (pojo.getManagement_guide_organization() != null){
                management_guide_organization = pojo.getManagement_guide_organization();
                if (pojo.getManagement_guide_organization().equalsIgnoreCase("Yes")){
                    management_guide_organization_yes.setChecked(true);
                }else if (pojo.getManagement_guide_organization().equalsIgnoreCase("No")){
                    management_guide_organization_no.setChecked(true);
                }
            }
            if (pojo.getHospital_mission_present() != null){
                hospital_mission_present = pojo.getHospital_mission_present();
                if (pojo.getHospital_mission_present().equalsIgnoreCase("Yes")){
                    hospital_mission_present_yes.setChecked(true);
                }else if (pojo.getHospital_mission_present().equalsIgnoreCase("No")){
                    hospital_mission_present_no.setChecked(true);
                }
            } if (pojo.getPatient_maintained_OPD() != null){
                patient_maintained_OPD = pojo.getPatient_maintained_OPD();
                if (pojo.getPatient_maintained_OPD().equalsIgnoreCase("Yes")){
                    patient_maintained_OPD_yes.setChecked(true);
                }else if (pojo.getPatient_maintained_OPD().equalsIgnoreCase("No")){
                    patient_maintained_OPD_no.setChecked(true);
                }
            }
            if (pojo.getPatient_maintained_IPD() != null){
                patient_maintained_IPD = pojo.getPatient_maintained_IPD();
                if (pojo.getPatient_maintained_IPD().equalsIgnoreCase("Yes")){
                    patient_maintained_IPD_yes.setChecked(true);
                }else if (pojo.getPatient_maintained_IPD().equalsIgnoreCase("No")){
                    patient_maintained_IPD_no.setChecked(true);
                }
            }

            if (pojo.getPatient_maintained_Emergency() != null){
                patient_maintained_Emergency = pojo.getPatient_maintained_Emergency();
                if (pojo.getPatient_maintained_Emergency().equalsIgnoreCase("Yes")){
                    patient_maintained_Emergency_yes.setChecked(true);
                }else if (pojo.getPatient_maintained_Emergency().equalsIgnoreCase("No")){
                    patient_maintained_Emergency_no.setChecked(true);
                }
            }

            if (pojo.getBasic_Tariff_List() != null){
                basic_Tariff_List = pojo.getBasic_Tariff_List();
                if (pojo.getBasic_Tariff_List().equalsIgnoreCase("Yes")){
                    basic_Tariff_List_yes.setChecked(true);
                }else if (pojo.getBasic_Tariff_List().equalsIgnoreCase("No")){
                    basic_Tariff_List_no.setChecked(true);
                }
            }

            if (pojo.getParameter_patient_identification() != null){
                parameter_patient_identification = pojo.getParameter_patient_identification();
                if (pojo.getParameter_patient_identification().equalsIgnoreCase("By Name")){
                    parameter_patient_identification_yes.setChecked(true);
                }else if (pojo.getParameter_patient_identification().equalsIgnoreCase("By Unique Identifier")){
                    parameter_patient_identification_no.setChecked(true);
                }else if (pojo.getParameter_patient_identification().equalsIgnoreCase("By Name,By Unique Identifier")){
                    parameter_patient_identification_yes.setChecked(true);
                    parameter_patient_identification_no.setChecked(true);
                }
            }

            if (pojo.getQuality_improvement_programme() != null){
                quality_improvement_programme = pojo.getQuality_improvement_programme();
                if (pojo.getQuality_improvement_programme().equalsIgnoreCase("Yes")){
                    quality_improvement_programme_yes.setChecked(true);
                }else if (pojo.getQuality_improvement_programme().equalsIgnoreCase("No")){
                    quality_improvement_programme_no.setChecked(true);
                }
            }


            if (pojo.getRequisite_fee_BMW_remark() != null){
                remark_requisite_fee_BMW.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getRequisite_fee_BMW_remark();
            }
            if (pojo.getRequisite_fee_BMW_nc() != null){
                nc1 = pojo.getRequisite_fee_BMW_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_requisite_fee_BMW.setImageResource(R.mipmap.nc);
                }else {
                    nc_requisite_fee_BMW.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getManagement_guide_organization_remark() != null){
                remark2 = pojo.getManagement_guide_organization_remark();

                remark_management_guide_organization.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getManagement_guide_organization_nc() != null){
                nc2 = pojo.getManagement_guide_organization_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_management_guide_organization.setImageResource(R.mipmap.nc);
                }else {
                    nc_management_guide_organization.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getHospital_mission_present_remark() != null){
                remark3 = pojo.getHospital_mission_present_remark();

                remark_hospital_mission_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getHospital_mission_present_nc() != null){
                nc3 = pojo.getHospital_mission_present_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_hospital_mission_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_hospital_mission_present.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getHospital_mission_present_Image() != null){
                image_hospital_mission_present.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getHospital_mission_present_Image();

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

            if (pojo.getLocal_hospital_mission_present_Image() != null){

                Local_image3 = pojo.getLocal_hospital_mission_present_Image();

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

            if (pojo.getPatient_maintained_OPD_remark() != null){
                remark4 = pojo.getPatient_maintained_OPD_remark();

                remark_patient_maintained_OPD.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_maintained_OPD_nc() != null){
                nc4 = pojo.getPatient_maintained_OPD_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_patient_maintained_OPD.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_maintained_OPD.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getPatient_maintained_OPD_image() != null){

                Image_patient_maintained_OPD.setImageResource(R.mipmap.camera_selected);

                image4 = pojo.getPatient_maintained_OPD_image();

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

            if (pojo.getLocal_patient_maintained_OPD_image() != null){

                Local_image4 = pojo.getLocal_patient_maintained_OPD_image();

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


            if (pojo.getPatient_maintained_IPD_remark() != null){
                remark5 = pojo.getPatient_maintained_IPD_remark();

                remark_patient_maintained_IPD.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_maintained_IPD_nc() != null){
                nc5 = pojo.getPatient_maintained_IPD_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_patient_maintained_IPD.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_maintained_IPD.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getPatient_maintained_IPD_image() != null){
                Image_patient_maintained_IPD.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getPatient_maintained_IPD_image();

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
            if (pojo.getLocal_patient_maintained_IPD_image() != null){

                Local_image5 = pojo.getLocal_patient_maintained_IPD_image();

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

            if (pojo.getPatient_maintained_Emergency_remark() != null){
                remark6 = pojo.getPatient_maintained_Emergency_remark();

                remark_patient_maintained_Emergency.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getPatient_maintained_Emergency_nc() != null){
                nc6 = pojo.getPatient_maintained_Emergency_nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_patient_maintained_Emergency.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_maintained_Emergency.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getPatient_maintained_Emergency_image() != null){
                Image_patient_maintained_Emergency.setImageResource(R.mipmap.camera_selected);

                image6 = pojo.getPatient_maintained_Emergency_image();

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

            if (pojo.getLocal_patient_maintained_Emergency_image() != null){

                Local_image6 = pojo.getLocal_patient_maintained_Emergency_image();

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

            if (pojo.getBasic_Tariff_List_remark() != null){
                remark7 = pojo.getBasic_Tariff_List_remark();

                remark_basic_Tariff_List.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getBasic_Tariff_List_nc() != null){
                nc7 = pojo.getBasic_Tariff_List_nc();

                if (nc7.equalsIgnoreCase("close")){
                    nc_basic_Tariff_List.setImageResource(R.mipmap.nc);
                }else {
                    nc_basic_Tariff_List.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getBasic_Tariff_List_image() != null){
                Image_basic_Tariff_List.setImageResource(R.mipmap.camera_selected);

                image7 = pojo.getBasic_Tariff_List_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image7);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            basic_Tariff_List_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_basic_Tariff_List_image() != null){

                Local_image7 = pojo.getLocal_basic_Tariff_List_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image7);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_basic_Tariff_List_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getParameter_patient_identification_remark() != null){
                remark8 = pojo.getParameter_patient_identification_remark();

                remark_parameter_patient_identification.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getParameter_patient_identification_nc() != null){
                nc8 = pojo.getParameter_patient_identification_nc();

                if (nc8.equalsIgnoreCase("close")){
                    nc_parameter_patient_identification.setImageResource(R.mipmap.nc);
                }else {
                    nc_parameter_patient_identification.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getQuality_improvement_programme_remark() != null){
                remark9 = pojo.getQuality_improvement_programme_remark();

                remark_quality_improvement_programme.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getQuality_improvement_programme_nc() != null){
                nc9 = pojo.getQuality_improvement_programme_nc();

                if (nc9.equalsIgnoreCase("close")){
                    nc_quality_improvement_programme.setImageResource(R.mipmap.nc);
                }else {
                    nc_quality_improvement_programme.setImageResource(R.mipmap.nc_selected);
                }
            }


        }else {
            pojo = new ManagementPojo();
        }

    }


    @OnClick({R.id.remark_requisite_fee_BMW,R.id.nc_requisite_fee_BMW,R.id.remark_management_guide_organization,R.id.nc_management_guide_organization,R.id.remark_hospital_mission_present,R.id.nc_hospital_mission_present,
            R.id.remark_patient_maintained_OPD,R.id.nc_patient_maintained_OPD,R.id.remark_patient_maintained_IPD,
            R.id.nc_patient_maintained_IPD,R.id.Image_patient_maintained_IPD, R.id.remark_patient_maintained_Emergency,R.id.nc_patient_maintained_Emergency,R.id.Image_patient_maintained_Emergency,R.id.remark_basic_Tariff_List,
            R.id.nc_basic_Tariff_List,R.id.Image_basic_Tariff_List,R.id.remark_parameter_patient_identification,R.id.nc_parameter_patient_identification,R.id.Image_patient_maintained_OPD,
            R.id.remark_quality_improvement_programme,R.id.nc_quality_improvement_programme,R.id.image_hospital_mission_present,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_requisite_fee_BMW:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_requisite_fee_BMW:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_management_guide_organization:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_management_guide_organization:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_hospital_mission_present:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_hospital_mission_present:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_hospital_mission_present:
                if (Local_hospital_mission_present_list.size() > 0){
                    showImageListDialog(Local_hospital_mission_present_list,3,"hospital_mission_present");
                }else {
                    captureImage(3);
                }


                break;


            case R.id.remark_patient_maintained_OPD:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_patient_maintained_OPD:
                displayNCDialog("NC", 4);
                break;

            case R.id.Image_patient_maintained_OPD:
                if (Local_patient_maintained_OPD_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_OPD_list,4,"patient_maintained_OPD");
                }else {
                    captureImage(4);
                }
                break;


            case R.id.remark_patient_maintained_IPD:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_patient_maintained_IPD:
                displayNCDialog("NC", 5);
                break;

            case R.id.Image_patient_maintained_IPD:
                if (Local_patient_maintained_IPD_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_IPD_list,5,"patient_maintained_IPD");
                }else {
                    captureImage(5);
                }
                break;

            case R.id.remark_patient_maintained_Emergency:
                displayCommonDialogWithHeader("Remark", 6);
                break;



            case R.id.nc_patient_maintained_Emergency:
                displayNCDialog("NC", 6);
                break;

            case R.id.Image_patient_maintained_Emergency:
                if (Local_patient_maintained_Emergency_list.size() > 0){
                    showImageListDialog(Local_patient_maintained_Emergency_list,6,"patient_maintained_Emergency");
                }else {
                    captureImage(6);
                }
                break;

            case R.id.remark_basic_Tariff_List:
                displayCommonDialogWithHeader("Remark", 7);
                break;

            case R.id.nc_basic_Tariff_List:
                displayNCDialog("NC", 7);
                break;

            case R.id.Image_basic_Tariff_List:
                if (Local_basic_Tariff_List_list.size() > 0){
                    showImageListDialog(Local_basic_Tariff_List_list,7,"basic_Tariff_List");
                }else {
                    captureImage(7);
                }
                break;

            case R.id.remark_parameter_patient_identification:
                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_parameter_patient_identification:
                displayNCDialog("NC", 8);
                break;

            case R.id.remark_quality_improvement_programme:
                displayCommonDialogWithHeader("Remark", 9);
                break;

            case R.id.nc_quality_improvement_programme:
                displayNCDialog("NC", 9);
                break;


            case R.id.btnSave:
                 SavePharmacyData("save");
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
            case R.id.requisite_fee_BMW_yes:
                if (checked)
                    requisite_fee_BMW = "Yes";
                break;

            case R.id.requisite_fee_BMW_no:
                if (checked)
                    requisite_fee_BMW = "No";
                break;

            case R.id.management_guide_organization_yes:
                management_guide_organization = "Yes";
                break;

            case R.id.management_guide_organization_no:
                management_guide_organization = "No";
                break;

            case R.id.hospital_mission_present_yes:
                if (checked)
                    hospital_mission_present = "Yes";
                break;
            case R.id.hospital_mission_present_no:
                if (checked)
                    hospital_mission_present = "No";
                break;


            case R.id.patient_maintained_OPD_yes:
                if (checked)
                    patient_maintained_OPD = "Yes";
                break;
            case R.id.patient_maintained_OPD_no:
                if (checked)
                    patient_maintained_OPD = "No";
                break;

            case R.id.patient_maintained_IPD_yes:
                patient_maintained_IPD = "Yes";
                break;

            case R.id.patient_maintained_IPD_no:
                patient_maintained_IPD = "No";
                break;

            case R.id.patient_maintained_Emergency_yes:
                patient_maintained_Emergency = "Yes";
                break;

            case R.id.patient_maintained_Emergency_no:
                patient_maintained_Emergency = "No";
                break;

            case R.id.basic_Tariff_List_yes:
                basic_Tariff_List = "Yes";

                break;

            case R.id.basic_Tariff_List_no:
                basic_Tariff_List = "No";
                break;



            case R.id.quality_improvement_programme_yes:
                quality_improvement_programme = "Yes";
                break;

            case R.id.quality_improvement_programme_no:
                quality_improvement_programme = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(ManagementActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    ImageUpload(image2,"hospital_mission_present");

                }

            } else if (requestCode == 4) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2,"patient_maintained_OPD");

                }
            }else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2,"patient_maintained_IPD");

                }
            }else if (requestCode == 6) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2,"patient_maintained_Emergency");

                }
            }else if (requestCode == 7) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2,"basic_Tariff_List");

                }
            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(ManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_requisite_fee_BMW.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_requisite_fee_BMW.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_management_guide_organization.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_management_guide_organization.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_hospital_mission_present.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_hospital_mission_present.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_patient_maintained_OPD.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patient_maintained_OPD.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_patient_maintained_IPD.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patient_maintained_IPD.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_patient_maintained_Emergency.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_patient_maintained_Emergency.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 7) {
                        if (radio_status7 != null) {

                            if (radio_status7.equalsIgnoreCase("close")) {

                                nc_basic_Tariff_List.setImageResource(R.mipmap.nc);

                                nc7 = radio_status7;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status7.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_basic_Tariff_List.setImageResource(R.mipmap.nc_selected);

                                    nc7 = radio_status7 + "," + edit_text.getText().toString();

                                    radio_status7 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_parameter_patient_identification.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_parameter_patient_identification.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 9) {
                        if (radio_status9 != null) {

                            if (radio_status9.equalsIgnoreCase("close")) {

                                nc_quality_improvement_programme.setImageResource(R.mipmap.nc);

                                nc9 = radio_status9;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status9.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_quality_improvement_programme.setImageResource(R.mipmap.nc_selected);

                                    nc9 = radio_status9 + "," + edit_text.getText().toString();

                                    radio_status9 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ManagementActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(ManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_requisite_fee_BMW.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_management_guide_organization.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_hospital_mission_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_patient_maintained_OPD.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_patient_maintained_IPD.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_patient_maintained_Emergency.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 7) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark7 = edit_text.getText().toString();
                            remark_basic_Tariff_List.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_parameter_patient_identification.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 9) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark9 = edit_text.getText().toString();
                            remark_quality_improvement_programme.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ManagementActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }


                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(ManagementActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(ManagementActivity.this,list,from,"Management");
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




    public void SavePharmacyData(String from){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(14);
        pojo.setRequisite_fee_BMW(requisite_fee_BMW);
        pojo.setManagement_guide_organization(management_guide_organization);
        pojo.setHospital_mission_present(hospital_mission_present);
        pojo.setPatient_maintained_OPD(patient_maintained_OPD);
        pojo.setPatient_maintained_IPD(patient_maintained_IPD);
        pojo.setPatient_maintained_Emergency(patient_maintained_Emergency);
        pojo.setBasic_Tariff_List(basic_Tariff_List);

        pojo.setQuality_improvement_programme(quality_improvement_programme);

        if (parameter_patient_identification_yes.isChecked()){
            Check_box1 = "By Name";
        }
        if (parameter_patient_identification_no.isChecked()){
            Check_box2 = "By Unique Identifier";
        }

        if (Check_box1.length() > 0 && Check_box2.length() > 0){
            parameter_patient_identification  = Check_box1 + "," + Check_box2;
        }else if (Check_box1.length() > 0){
            parameter_patient_identification = Check_box1;
        }else if (Check_box2.length() > 0){
            parameter_patient_identification = Check_box2;
        }

        pojo.setParameter_patient_identification(parameter_patient_identification);


        pojo.setRequisite_fee_BMW_remark(remark1);
        pojo.setRequisite_fee_BMW_nc(nc1);

        pojo.setManagement_guide_organization_remark(remark2);
        pojo.setManagement_guide_organization_nc(nc2);


        pojo.setHospital_mission_present_remark(remark3);
        pojo.setHospital_mission_present_nc(nc3);

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

        pojo.setHospital_mission_present_Image(image3);
        pojo.setLocal_hospital_mission_present_Image(Local_image3);

        pojo.setPatient_maintained_OPD_remark(remark4);
        pojo.setPatient_maintained_OPD_nc(nc4);

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
        pojo.setPatient_maintained_OPD_image(image4);
        pojo.setLocal_patient_maintained_OPD_image(Local_image4);


        pojo.setPatient_maintained_IPD_remark(remark5);
        pojo.setPatient_maintained_IPD_nc(nc5);

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
        pojo.setPatient_maintained_IPD_image(image5);
        pojo.setLocal_patient_maintained_IPD_image(Local_image5);



        pojo.setPatient_maintained_Emergency_remark(remark6);
        pojo.setPatient_maintained_Emergency_nc(nc6);

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

        pojo.setPatient_maintained_Emergency_image(image6);
        pojo.setLocal_patient_maintained_Emergency_image(Local_image6);


        pojo.setBasic_Tariff_List_remark(remark7);
        pojo.setBasic_Tariff_List_nc(nc7);

        if (basic_Tariff_List_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(basic_Tariff_List_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image7 = json.toString();
        }else {
            image7 = null;
        }

        if (Local_basic_Tariff_List_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_basic_Tariff_List_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image7 = json.toString();
        }else {
            Local_image7 = null;
        }

        pojo.setBasic_Tariff_List_image(image7);
        pojo.setLocal_basic_Tariff_List_image(Local_image7);


        pojo.setParameter_patient_identification_remark(remark8);
        pojo.setParameter_patient_identification_nc(nc8);

        pojo.setQuality_improvement_programme_remark(remark9);
        pojo.setQuality_improvement_programme_nc(nc9);

        if (sql_status){
            databaseHandler.UPDATE_Management(pojo);
        }else {
            boolean status = databaseHandler.INSERT_Management(pojo);
            System.out.println(status);
        }

        if (!from.equalsIgnoreCase("sync")){
            assessement_list = databaseHandler.getAssessmentList(Hospital_id);

            AssessmentStatusPojo pojo = new AssessmentStatusPojo();
            pojo.setHospital_id(assessement_list.get(14).getHospital_id());
            pojo.setAssessement_name("Management");
            pojo.setAssessement_status("Draft");
            pojo.setLocal_id(assessement_list.get(14).getLocal_id());

            databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


            Toast.makeText(ManagementActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ManagementActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void PostLaboratoryData(){

        SavePharmacyData("sync");

        if (requisite_fee_BMW.length() > 0 && management_guide_organization.length() > 0 && hospital_mission_present.length() > 0 && patient_maintained_OPD.length() > 0 && patient_maintained_OPD.length() > 0
        && patient_maintained_IPD.length() > 0 && patient_maintained_Emergency.length() > 0 && basic_Tariff_List.length() > 0 && parameter_patient_identification.length() > 0 && quality_improvement_programme.length() > 0){

            if (image3 != null && image4 != null && image5 != null && image6 != null && image7 != null){
                pojo_dataSync.setTabName("management");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }



                for (int i=0;i<hospital_mission_present_list.size();i++){
                    String value = hospital_mission_present_list.get(i);

                    hospital_mission_present_view = value + hospital_mission_present_view;
                }

                for (int i=0;i<patient_maintained_OPD_list.size();i++){
                    String value = patient_maintained_OPD_list.get(i);

                    patient_maintained_OPD_view = value + patient_maintained_OPD_view;
                }

                for (int i=0;i<patient_maintained_IPD_list.size();i++){
                    String value = patient_maintained_IPD_list.get(i);

                    patient_maintained_IPD_view = value + patient_maintained_IPD_view;
                }

                for (int i=0;i<patient_maintained_Emergency_list.size();i++){
                    String value = patient_maintained_Emergency_list.get(i);

                    patient_maintained_Emergency_view = value + patient_maintained_Emergency_view;
                }

                for (int i=0;i<basic_Tariff_List_list.size();i++){
                    String value = basic_Tariff_List_list.get(i);

                    basic_Tariff_List_view = value + basic_Tariff_List_view;
                }


                pojo.setHospital_mission_present_Image(hospital_mission_present_view);
                pojo.setPatient_maintained_OPD_image(patient_maintained_OPD_view);
                pojo.setPatient_maintained_IPD_image(patient_maintained_IPD_view);
                pojo.setPatient_maintained_Emergency_image(patient_maintained_Emergency_view);
                pojo.setBasic_Tariff_List_image(basic_Tariff_List_view);

                pojo_dataSync.setManagement(pojo);

                final ProgressDialog d = AppDialog.showLoading(ManagementActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(ManagementActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(ManagementActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(ManagementActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(14).getHospital_id());
                                    pojo.setAssessement_name("Management");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(14).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(ManagementActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
                Toast.makeText(ManagementActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ManagementActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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

        final ProgressDialog d = ImageDialog.showLoading(ManagementActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(ManagementActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ManagementActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("hospital_mission_present")){
                                hospital_mission_present_list.add(response.body().getMessage());
                                Local_hospital_mission_present_list.add(image_path);
                                image_hospital_mission_present.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("patient_maintained_OPD")){
                                patient_maintained_OPD_list.add(response.body().getMessage());
                                Local_patient_maintained_OPD_list.add(image_path);
                                Image_patient_maintained_OPD.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("patient_maintained_IPD")){
                                patient_maintained_IPD_list.add(response.body().getMessage());
                                Local_patient_maintained_IPD_list.add(image_path);
                                Image_patient_maintained_IPD.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("patient_maintained_Emergency")){
                                patient_maintained_Emergency_list.add(response.body().getMessage());
                                Local_patient_maintained_Emergency_list.add(image_path);
                                Image_patient_maintained_Emergency.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("basic_Tariff_List")){
                                basic_Tariff_List_list.add(response.body().getMessage());
                                Local_basic_Tariff_List_list.add(image_path);
                                Image_basic_Tariff_List.setImageResource(R.mipmap.camera_selected);
                            }

                            Toast.makeText(ManagementActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(ManagementActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(ManagementActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(ManagementActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
                hospital_mission_present_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_hospital_mission_present_list.size() == 0){
                    image_hospital_mission_present.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("patient_maintained_OPD")){
                Local_patient_maintained_OPD_list.remove(position);
                patient_maintained_OPD_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_OPD_list.size() == 0){
                    Image_patient_maintained_OPD.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("patient_maintained_IPD")){
                Local_patient_maintained_IPD_list.remove(position);
                patient_maintained_IPD_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_IPD_list.size() == 0){
                    Image_patient_maintained_IPD.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("patient_maintained_Emergency")){
                Local_patient_maintained_Emergency_list.remove(position);
                patient_maintained_Emergency_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_patient_maintained_Emergency_list.size() == 0){
                    Image_patient_maintained_Emergency.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("basic_Tariff_List")){
                Local_basic_Tariff_List_list.remove(position);
                basic_Tariff_List_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_basic_Tariff_List_list.size() == 0){
                    Image_basic_Tariff_List.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(14).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save");
        }else {
            Intent intent = new Intent(ManagementActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
