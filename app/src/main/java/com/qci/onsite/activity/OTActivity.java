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
import com.qci.onsite.pojo.LaboratoryPojo;
import com.qci.onsite.pojo.OTPojo;
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

/**
 * Created by Ankit on 11-02-2019.
 */

public class OTActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.anaesthetist_yes)
    RadioButton anaesthetist_yes;

    @BindView(R.id.anaesthetist_no)
    RadioButton anaesthetist_no;

    @BindView(R.id.remark_OT_anaesthetist)
    ImageView remark_OT_anaesthetist;

    @BindView(R.id.nc_OT_anaesthetist)
    ImageView nc_OT_anaesthetist;

    @BindView(R.id.documented_anaesthesia_yes)
    RadioButton documented_anaesthesia_yes;

    @BindView(R.id.documented_anaesthesia_no)
    RadioButton documented_anaesthesia_no;

    @BindView(R.id.remark_documented_anaesthesia)
    ImageView remark_documented_anaesthesia;

    @BindView(R.id.nc_documented_anaesthesia)
    ImageView nc_documented_anaesthesia;

    @BindView(R.id.immediate_preoperative_yes)
    RadioButton immediate_preoperative_yes;

    @BindView(R.id.immediate_preoperative_no)
    RadioButton immediate_preoperative_no;

    @BindView(R.id.remark_immediate_preoperative)
    ImageView remark_immediate_preoperative;

    @BindView(R.id.nc_immediate_preoperative)
    ImageView nc_immediate_preoperative;

    @BindView(R.id.anaesthesia_monitoring_yes)
    RadioButton anaesthesia_monitoring_yes;

    @BindView(R.id.anaesthesia_monitoring_no)
    RadioButton anaesthesia_monitoring_no;

    @BindView(R.id.remark_anaesthesia_monitoring)
    ImageView remark_anaesthesia_monitoring;

    @BindView(R.id.nc_anaesthesia_monitoring)
    ImageView nc_anaesthesia_monitoring;

    @BindView(R.id.post_anaesthesia_monitoring_yes)
    RadioButton post_anaesthesia_monitoring_yes;

    @BindView(R.id.post_anaesthesia_monitoring_no)
    RadioButton post_anaesthesia_monitoring_no;

    @BindView(R.id.remark_post_anaesthesia_monitoring)
    ImageView remark_post_anaesthesia_monitoring;

    @BindView(R.id.nc_post_anaesthesia_monitoring)
    ImageView nc_post_anaesthesia_monitoring;

    @BindView(R.id.WHO_Patient_Safety_yes)
    RadioButton WHO_Patient_Safety_yes;

    @BindView(R.id.WHO_Patient_Safety_no)
    RadioButton WHO_Patient_Safety_no;

    @BindView(R.id.remark_WHO_Patient_Safety)
    ImageView remark_WHO_Patient_Safety;

    @BindView(R.id.nc_WHO_Patient_Safety)
    ImageView nc_WHO_Patient_Safety;

    @BindView(R.id.Image_WHO_Patient_Safety)
    ImageView Image_WHO_Patient_Safety;


    @BindView(R.id.OT_Zoning_yes)
    RadioButton OT_Zoning_yes;

    @BindView(R.id.OT_Zoning_no)
    RadioButton OT_Zoning_no;

    @BindView(R.id.remark_OT_Zoning)
    ImageView remark_OT_Zoning;

    @BindView(R.id.nc_OT_Zoning)
    ImageView nc_OT_Zoning;

    @BindView(R.id.video_OT_Zoning)
    ImageView video_OT_Zoning;

    @BindView(R.id.infection_control_yes)
    RadioButton infection_control_yes;

    @BindView(R.id.infection_control_no)
    RadioButton infection_control_no;

    @BindView(R.id.remark_infection_control)
    ImageView remark_infection_control;

    @BindView(R.id.nc_infection_control)
    ImageView nc_infection_control;

    @BindView(R.id.image_infection_control)
    ImageView image_infection_control;

    @BindView(R.id.narcotic_drugs_yes)
    RadioButton narcotic_drugs_yes;

    @BindView(R.id.narcotic_drugs_no)
    RadioButton narcotic_drugs_no;

    @BindView(R.id.remark_narcotic_drugs)
    ImageView remark_narcotic_drugs;

    @BindView(R.id.nc_narcotic_drugs)
    ImageView nc_narcotic_drugs;

    @BindView(R.id.image_narcotic_drugs)
    ImageView image_narcotic_drugs;

    @BindView(R.id.administration_disposal_yes)
    RadioButton administration_disposal_yes;

    @BindView(R.id.administration_disposal_no)
    RadioButton administration_disposal_no;

    @BindView(R.id.remark_administration_disposal)
    ImageView remark_administration_disposal;

    @BindView(R.id.nc_administration_disposal)
    ImageView nc_administration_disposal;

    @BindView(R.id.image_administration_disposal)
    ImageView image_administration_disposal;

    @BindView(R.id.hand_wash_facility_yes)
    RadioButton hand_wash_facility_yes;

    @BindView(R.id.hand_wash_facility_no)
    RadioButton hand_wash_facility_no;

    @BindView(R.id.remark_hand_wash_facility)
    ImageView remark_hand_wash_facility;

    @BindView(R.id.nc_hand_wash_facility)
    ImageView nc_hand_wash_facility;

    @BindView(R.id.image_hand_wash_facility)
    ImageView image_hand_wash_facility;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    @BindView(R.id.ll_anaesthetist)
    LinearLayout ll_anaesthetist;

    @BindView(R.id.ll_documented_anaesthesia)
    LinearLayout ll_documented_anaesthesia;

    @BindView(R.id.ll_immediate_preoperative)
    LinearLayout ll_immediate_preoperative;

    @BindView(R.id.ll_anaesthesia_monitoring)
    LinearLayout ll_anaesthesia_monitoring;

    @BindView(R.id.ll_post_anaesthesia_monitoring)
    LinearLayout ll_post_anaesthesia_monitoring;


    @BindView(R.id.ll_OT_Zoning)
    LinearLayout ll_OT_Zoning;

    int Bed_no = 0;





    private String remark1, remark2, remark3, remark4, remark5,remark6,remark7,remark8,remark9,remark10,remark11;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private ArrayList<String>infection_control_List;
    private ArrayList<String>WHO_Patient_Safety_List;
    private ArrayList<String>Local_WHO_Patient_Safety_List;
    private ArrayList<String>Local_infection_control_List;
    private ArrayList<String>Local_OT_Zoning_List;
    private ArrayList<String>OT_Zoning_List;
    private ArrayList<String>narcotic_drugs_list;
    private ArrayList<String>Local_narcotic_drugs_list;
    private ArrayList<String>administration_disposal_list;
    private ArrayList<String>Local_administration_disposal_list;
    private ArrayList<String>hand_wash_facility_list;
    private ArrayList<String>Local_hand_wash_facility_list;


    private String nc1, nc2, nc3, nc4, nc5, nc6, nc7, nc8, nc9, nc10,nc11;
    private String radio_status1, radio_status2, radio_status3, radio_status4, radio_status5,
            radio_status6, radio_status7, radio_status8, radio_status9, radio_status10,radio_status11;

    private DatabaseHandler databaseHandler;

    private String image6,image7,image8,image9,image10,image11;
    private String Local_image6,Local_image7,Local_image8,Local_image9,Local_image10,Local_image11;

    private File outputVideoFile;

    private OTPojo pojo;

    private APIService mAPIService;

    private String anaesthetist_status="",documented_anaesthesia_status="",immediate_preoperative_status="",anaesthesia_monitoring_status="",post_anaesthesia_monitoring="",
            WHO_Patient_Safety_status="",OT_Zoning_status="",infection_control_status="",narcotic_drugs_status="",administration_disposal_status="",hand_wash_facility_status="";


    Uri videoUri;

    public Boolean sql_status = false;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    String WHO_Patient_Safety = "", infection_control = "",narcotic_drugs ="",administration_disposal="",hand_wash_facility="",
    OT_ZOING;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ot);

        ButterKnife.bind(this);

        mAPIService = ApiUtils.getAPIService();

        assessement_list = new ArrayList<>();

        setDrawerbackIcon("OT/ICU");

        pojo = new OTPojo();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        databaseHandler = DatabaseHandler.getInstance(this);

        infection_control_List = new ArrayList<>();
        WHO_Patient_Safety_List = new ArrayList<>();
        Local_WHO_Patient_Safety_List = new ArrayList<>();
        Local_infection_control_List = new ArrayList<>();
        Local_OT_Zoning_List = new ArrayList<>();
        OT_Zoning_List = new ArrayList<>();
        narcotic_drugs_list = new ArrayList<>();
        Local_narcotic_drugs_list = new ArrayList<>();
        administration_disposal_list = new ArrayList<>();
        Local_administration_disposal_list = new ArrayList<>();
        hand_wash_facility_list = new ArrayList<>();
        Local_hand_wash_facility_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_anaesthetist.setVisibility(View.GONE);
            ll_documented_anaesthesia.setVisibility(View.GONE);
            ll_immediate_preoperative.setVisibility(View.GONE);
            ll_anaesthesia_monitoring.setVisibility(View.GONE);
            ll_post_anaesthesia_monitoring.setVisibility(View.GONE);
            ll_OT_Zoning.setVisibility(View.GONE);
        }else {
            ll_anaesthetist.setVisibility(View.VISIBLE);
            ll_documented_anaesthesia.setVisibility(View.VISIBLE);
            ll_immediate_preoperative.setVisibility(View.VISIBLE);
            ll_anaesthesia_monitoring.setVisibility(View.VISIBLE);
            ll_post_anaesthesia_monitoring.setVisibility(View.VISIBLE);
            ll_OT_Zoning.setVisibility(View.VISIBLE);
        }

        getOTData();
    }

    public void getOTData(){

        pojo = databaseHandler.getOtPojo("6");

        if (pojo != null) {
            sql_status = true;
            if (pojo.getAnaesthetist_status() != null) {
                anaesthetist_status = pojo.getAnaesthetist_status();
                if (pojo.getAnaesthetist_status().equalsIgnoreCase("Yes")) {
                    anaesthetist_yes.setChecked(true);
                } else if (pojo.getAnaesthetist_status().equalsIgnoreCase("No")) {
                    anaesthetist_no.setChecked(true);
                }
            }
            if (pojo.getDocumented_anaesthesia_status() != null) {
                documented_anaesthesia_status = pojo.getDocumented_anaesthesia_status();
                if (pojo.getDocumented_anaesthesia_status().equalsIgnoreCase("Yes")) {
                    documented_anaesthesia_yes.setChecked(true);
                } else if (pojo.getDocumented_anaesthesia_status().equalsIgnoreCase("No")) {
                    documented_anaesthesia_no.setChecked(true);
                }
            }
            if (pojo.getImmediate_preoperative_status() != null) {
                immediate_preoperative_status = pojo.getImmediate_preoperative_status();
                if (pojo.getImmediate_preoperative_status().equalsIgnoreCase("Yes")) {
                    immediate_preoperative_yes.setChecked(true);
                } else if (pojo.getImmediate_preoperative_status().equalsIgnoreCase("No")) {
                    immediate_preoperative_yes.setChecked(true);
                }
            }
            if (pojo.getAnaesthesia_monitoring_status() != null) {
                anaesthesia_monitoring_status = pojo.getAnaesthesia_monitoring_status();
                if (pojo.getAnaesthesia_monitoring_status().equalsIgnoreCase("Yes")) {
                    anaesthesia_monitoring_yes.setChecked(true);
                } else if (pojo.getAnaesthesia_monitoring_status().equalsIgnoreCase("No")) {
                    anaesthesia_monitoring_no.setChecked(true);
                }
            }
            if (pojo.getPost_anaesthesia_monitoring_status() != null) {
                post_anaesthesia_monitoring = pojo.getPost_anaesthesia_monitoring_status();
                if (pojo.getPost_anaesthesia_monitoring_status().equalsIgnoreCase("Yes")) {
                    post_anaesthesia_monitoring_yes.setChecked(true);
                } else if (pojo.getPost_anaesthesia_monitoring_status().equalsIgnoreCase("No")) {
                    post_anaesthesia_monitoring_no.setChecked(true);
                }
            }
            if (pojo.getWHO_Patient_Safety_status() != null) {
                WHO_Patient_Safety_status = pojo.getWHO_Patient_Safety_status();
                if (pojo.getWHO_Patient_Safety_status().equalsIgnoreCase("Yes")) {
                    WHO_Patient_Safety_yes.setChecked(true);
                } else if (pojo.getWHO_Patient_Safety_status().equalsIgnoreCase("No")) {
                    WHO_Patient_Safety_no.setChecked(true);
                }
            }
            if (pojo.getOT_Zoning_status() != null) {
                OT_Zoning_status = pojo.getOT_Zoning_status();
                if (pojo.getOT_Zoning_status().equalsIgnoreCase("Yes")) {
                    OT_Zoning_yes.setChecked(true);
                } else if (pojo.getOT_Zoning_status().equalsIgnoreCase("No")) {
                    OT_Zoning_no.setChecked(true);
                }
            }
            if (pojo.getInfection_control_status() != null) {
                infection_control_status = pojo.getInfection_control_status();
                if (pojo.getInfection_control_status().equalsIgnoreCase("Yes")) {
                    infection_control_yes.setChecked(true);
                } else if (pojo.getInfection_control_status().equalsIgnoreCase("No")) {
                    infection_control_no.setChecked(true);
                }
            }
            if (pojo.getNarcotic_drugs_status() != null) {
                narcotic_drugs_status = pojo.getNarcotic_drugs_status();
                if (pojo.getNarcotic_drugs_status().equalsIgnoreCase("Yes")) {
                    narcotic_drugs_yes.setChecked(true);
                } else if (pojo.getNarcotic_drugs_status().equalsIgnoreCase("No")) {
                    narcotic_drugs_no.setChecked(true);
                }
            }
            if (pojo.getAdministration_disposal_status() != null) {
                administration_disposal_status = pojo.getAdministration_disposal_status();
                if (pojo.getAdministration_disposal_status().equalsIgnoreCase("Yes")) {
                    administration_disposal_yes.setChecked(true);
                } else if (pojo.getAdministration_disposal_status().equalsIgnoreCase("No")) {
                    administration_disposal_no.setChecked(true);
                }
            }

            if (pojo.getHand_wash_facility_status() != null) {
                hand_wash_facility_status = pojo.getHand_wash_facility_status();
                if (pojo.getHand_wash_facility_status().equalsIgnoreCase("Yes")) {
                    hand_wash_facility_yes.setChecked(true);
                } else if (pojo.getHand_wash_facility_status().equalsIgnoreCase("No")) {
                    hand_wash_facility_no.setChecked(true);
                }
            }

            if (pojo.getRemark_OT_anaesthetist() != null) {
                remark_OT_anaesthetist.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getRemark_OT_anaesthetist();
            }
            if (pojo.getNc_OT_anaesthetist() != null) {
                nc1 = pojo.getNc_OT_anaesthetist();

                if (nc1.equalsIgnoreCase("close")){
                    nc_OT_anaesthetist.setImageResource(R.mipmap.nc);
                }else {
                    nc_OT_anaesthetist.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getRemark_documented_anaesthesia() != null) {
                remark2 = pojo.getRemark_documented_anaesthesia();

                remark_documented_anaesthesia.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_documented_anaesthesia() != null) {
                nc2 = pojo.getNc_documented_anaesthesia();

                if (nc2.equalsIgnoreCase("close")){
                    nc_documented_anaesthesia.setImageResource(R.mipmap.nc);
                }else {
                    nc_documented_anaesthesia.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getRemark_immediate_preoperative() != null) {
                remark3 = pojo.getRemark_immediate_preoperative();

                remark_immediate_preoperative.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_immediate_preoperative() != null) {
                nc3 = pojo.getNc_immediate_preoperative();

                if (nc3.equalsIgnoreCase("close")){
                    nc_immediate_preoperative.setImageResource(R.mipmap.nc);
                }else {
                    nc_immediate_preoperative.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getRemark_anaesthesia_monitoring() != null) {
                remark4 = pojo.getRemark_anaesthesia_monitoring();

                remark_anaesthesia_monitoring.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_anaesthesia_monitoring() != null) {
                nc4 = pojo.getNc_anaesthesia_monitoring();

                if (nc4.equalsIgnoreCase("close")){
                    nc_anaesthesia_monitoring.setImageResource(R.mipmap.nc);
                }else {
                    nc_anaesthesia_monitoring.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getRemark_post_anaesthesia_monitoring() != null) {
                remark5 = pojo.getRemark_post_anaesthesia_monitoring();

                remark_post_anaesthesia_monitoring.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_post_anaesthesia_monitoring() != null) {
                nc5 = pojo.getNc_post_anaesthesia_monitoring();

                if (nc5.equalsIgnoreCase("close")){
                    nc_post_anaesthesia_monitoring.setImageResource(R.mipmap.nc);
                }else {
                    nc_post_anaesthesia_monitoring.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getRemark_WHO_Patient_Safety() != null) {
                remark6 = pojo.getRemark_WHO_Patient_Safety();

                remark_WHO_Patient_Safety.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_WHO_Patient_Safety() != null) {
                nc6 = pojo.getNc_WHO_Patient_Safety();

                if (nc6.equalsIgnoreCase("close")){
                    nc_WHO_Patient_Safety.setImageResource(R.mipmap.nc);
                }else {
                    nc_WHO_Patient_Safety.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getRemark_OT_Zoning() != null) {
                remark7 = pojo.getRemark_OT_Zoning();

                remark_OT_Zoning.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_OT_Zoning() != null) {
                nc7 = pojo.getNc_OT_Zoning();

                if (nc7.equalsIgnoreCase("close")){
                    nc_OT_Zoning.setImageResource(R.mipmap.nc);
                }else {
                    nc_OT_Zoning.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getLocal_video_OT_Zoning() != null){
                video_OT_Zoning.setImageResource(R.mipmap.camera_selected);

                Local_image7 = pojo.getLocal_video_OT_Zoning();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image7);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_OT_Zoning_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getVideo_OT_Zoning() != null){

                image7 = pojo.getVideo_OT_Zoning();

                JSONObject json = null;
                try {
                    json = new JSONObject(image7);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            OT_Zoning_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getRemark_infection_control() != null) {
                remark8 = pojo.getRemark_infection_control();

                remark_infection_control.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_infection_control() != null) {
                nc8 = pojo.getNc_infection_control();

                if (nc8.equalsIgnoreCase("close")){
                    nc_infection_control.setImageResource(R.mipmap.nc);
                }else {
                    nc_infection_control.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getRemark_narcotic_drugs() != null) {
                remark9 = pojo.getRemark_narcotic_drugs();

                remark_narcotic_drugs.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_narcotic_drugs() != null) {
                nc9 = pojo.getNc_narcotic_drugs();

                if (nc9.equalsIgnoreCase("close")){
                    nc_narcotic_drugs.setImageResource(R.mipmap.nc);
                }else {
                    nc_narcotic_drugs.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getRemark_administration_disposal() != null) {
                remark10 = pojo.getRemark_administration_disposal();

                remark_administration_disposal.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_administration_disposal() != null) {
                nc10 = pojo.getNc_administration_disposal();

                if (nc10.equalsIgnoreCase("close")){
                    nc_administration_disposal.setImageResource(R.mipmap.nc);
                }else {
                    nc_administration_disposal.setImageResource(R.mipmap.nc_selected);
                }

            }
            if (pojo.getRemark_hand_wash_facility() != null){
                remark11 = pojo.getRemark_hand_wash_facility();

                remark_hand_wash_facility.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getNc_hand_wash_facility() != null) {
                nc11 = pojo.getNc_hand_wash_facility();

                if (nc11.equalsIgnoreCase("close")){
                    nc_hand_wash_facility.setImageResource(R.mipmap.nc);
                }else {
                    nc_hand_wash_facility.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getImage_WHO_Patient_Safety_day1() != null){
                Image_WHO_Patient_Safety.setImageResource(R.mipmap.camera_selected);

                image6 = pojo.getImage_WHO_Patient_Safety_day1();

                JSONObject json = null;
                try {
                    json = new JSONObject(image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                           WHO_Patient_Safety_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_image_WHO_Patient_Safety_day1() != null){
                Image_WHO_Patient_Safety.setImageResource(R.mipmap.camera_selected);

                Local_image6 = pojo.getLocal_image_WHO_Patient_Safety_day1();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_WHO_Patient_Safety_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getImage_infection_control() != null){
                image_infection_control.setImageResource(R.mipmap.camera_selected);

                image8 = pojo.getImage_infection_control();

                JSONObject json = null;
                try {
                    json = new JSONObject(image8);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            infection_control_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_image_infection_control() != null){
                image_infection_control.setImageResource(R.mipmap.camera_selected);

                Local_image9 = pojo.getLocal_image_infection_control();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image9);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_infection_control_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getImage_hand_wash_facility() != null){
                image_hand_wash_facility.setImageResource(R.mipmap.camera_selected);

                image11 = pojo.getImage_hand_wash_facility();

                JSONObject json = null;
                try {
                    json = new JSONObject(image11);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            hand_wash_facility_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_image_hand_wash_facility()!= null){
                image_hand_wash_facility.setImageResource(R.mipmap.camera_selected);

                Local_image11 = pojo.getLocal_image_hand_wash_facility();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image11);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_hand_wash_facility_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getImage_narcotic_drugs() != null){
                image_narcotic_drugs.setImageResource(R.mipmap.camera_selected);

                image9 = pojo.getImage_narcotic_drugs();

                JSONObject json = null;
                try {
                    json = new JSONObject(image9);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            narcotic_drugs_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_image_narcotic_drugs() != null){
                image_infection_control.setImageResource(R.mipmap.camera_selected);

                Local_image9 = pojo.getLocal_image_narcotic_drugs();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image9);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_narcotic_drugs_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getImage_administration_disposal() != null){
                image_administration_disposal.setImageResource(R.mipmap.camera_selected);

                image10 = pojo.getImage_administration_disposal();

                JSONObject json = null;
                try {
                    json = new JSONObject(image10);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            administration_disposal_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_image_administration_disposal() != null){
                image_infection_control.setImageResource(R.mipmap.camera_selected);

                Local_image10 = pojo.getLocal_image_administration_disposal();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image10);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_administration_disposal_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else {
            pojo = new OTPojo();
        }

    }



    @OnClick({R.id.remark_OT_anaesthetist,R.id.nc_OT_anaesthetist,R.id.remark_documented_anaesthesia,R.id.nc_documented_anaesthesia,R.id.remark_immediate_preoperative,
            R.id.nc_immediate_preoperative,R.id.remark_anaesthesia_monitoring,R.id.nc_anaesthesia_monitoring,R.id.remark_post_anaesthesia_monitoring,R.id.nc_post_anaesthesia_monitoring,
            R.id.remark_WHO_Patient_Safety,R.id.nc_WHO_Patient_Safety,R.id.Image_WHO_Patient_Safety,R.id.remark_OT_Zoning,R.id.nc_OT_Zoning,R.id.video_OT_Zoning,R.id.remark_infection_control,R.id.nc_infection_control,
            R.id.image_infection_control,R.id.remark_narcotic_drugs,R.id.nc_narcotic_drugs,R.id.image_narcotic_drugs,R.id.remark_administration_disposal,
            R.id.nc_administration_disposal,R.id.image_administration_disposal,R.id.remark_hand_wash_facility,
            R.id.nc_hand_wash_facility,R.id.image_hand_wash_facility,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_OT_anaesthetist:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_OT_anaesthetist:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_documented_anaesthesia:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_documented_anaesthesia:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_immediate_preoperative:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_immediate_preoperative:
                displayNCDialog("NC", 3);
                break;

            case R.id.remark_anaesthesia_monitoring:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_anaesthesia_monitoring:
                displayNCDialog("NC", 4);
                break;

            case R.id.remark_post_anaesthesia_monitoring:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_post_anaesthesia_monitoring:
                displayNCDialog("NC", 5);
                break;

            case R.id.remark_WHO_Patient_Safety:
                displayCommonDialogWithHeader("Remark", 6);
                break;
            case R.id.nc_WHO_Patient_Safety:
                displayNCDialog("NC", 6);
                break;

            case R.id.Image_WHO_Patient_Safety:
                if (Local_WHO_Patient_Safety_List.size() > 0){
                    showImageListDialog(Local_WHO_Patient_Safety_List,6,"WHO_Patient_Safety");
                }else {
                    captureImage(6);
                }
                break;

            case R.id.remark_OT_Zoning:
                displayCommonDialogWithHeader("Remark", 7);
                break;

            case R.id.nc_OT_Zoning:
                displayNCDialog("NC", 7);
                break;
            case R.id.video_OT_Zoning:
                if (Local_OT_Zoning_List.size() > 0){
                    showImageListDialog(Local_OT_Zoning_List,7,"OT_Zoning");
                }else {
                    captureImage(7);
                }
                break;


            case R.id.remark_infection_control:

                displayCommonDialogWithHeader("Remark", 8);
                break;

            case R.id.nc_infection_control:
                displayNCDialog("NC", 8);
                break;

            case R.id.image_infection_control:
                if (Local_infection_control_List.size() > 0){
                    showImageListDialog(Local_infection_control_List,8,"infection_control");
                }else {
                    captureImage(8);
                }
                break;

            case R.id.remark_narcotic_drugs:
                displayCommonDialogWithHeader("Remark", 9);
                break;

            case R.id.nc_narcotic_drugs:
                displayNCDialog("NC", 9);
                break;

            case R.id.image_narcotic_drugs:
                if (Local_narcotic_drugs_list.size() > 0){
                    showImageListDialog(Local_narcotic_drugs_list,9,"narcotic_drugs");
                }else {
                    captureImage(9);
                }
                break;

            case R.id.remark_administration_disposal:
                displayCommonDialogWithHeader("Remark", 10);
                break;

            case R.id.nc_administration_disposal:
                displayNCDialog("NC", 10);
                break;

            case R.id.image_administration_disposal:
                if (Local_administration_disposal_list.size() > 0){
                    showImageListDialog(Local_administration_disposal_list,10,"administration_disposal");
                }else {
                    captureImage(10);
                }

                break;
            case R.id.remark_hand_wash_facility:
                displayCommonDialogWithHeader("Remark", 11);
                break;
            case R.id.nc_hand_wash_facility:
                displayNCDialog("NC", 11);
                break;

            case R.id.image_hand_wash_facility:
                if (Local_hand_wash_facility_list.size() > 0){
                    showImageListDialog(Local_hand_wash_facility_list,11,"hand_wash_facility");
                }else {
                    captureImage(11);
                }
                break;
            case R.id.btnSave:
                SaveLaboratoryData("save");
                break;

            case R.id.btnSync:
                if (Bed_no < 51){
                    Post_SHCO_LaboratoryData();
                }else {
                    PostLaboratoryData();
                }

                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.anaesthetist_yes:
                if (checked)
                    anaesthetist_status = "Yes";
                break;

            case R.id.anaesthetist_no:
                if (checked)
                    anaesthetist_status = "No";
                break;

            case R.id.documented_anaesthesia_yes:
                if (checked)
                    documented_anaesthesia_status = "Yes";
                break;
            case R.id.documented_anaesthesia_no:
                if (checked)
                    documented_anaesthesia_status = "No";
                break;


            case R.id.immediate_preoperative_yes:
                if (checked)
                    immediate_preoperative_status = "Yes";
                break;
            case R.id.immediate_preoperative_no:
                if (checked)
                    immediate_preoperative_status = "No";
                break;

            case R.id.anaesthesia_monitoring_yes:
                if (checked)
                    anaesthesia_monitoring_status = "Yes";
                break;
            case R.id.anaesthesia_monitoring_no:
                if (checked)
                    anaesthesia_monitoring_status = "No";
                break;

            case R.id.post_anaesthesia_monitoring_yes:
                if (checked)
                    post_anaesthesia_monitoring = "Yes";
                break;
            case R.id.post_anaesthesia_monitoring_no:
                if (checked)
                    post_anaesthesia_monitoring = "No";
                break;

            case R.id.WHO_Patient_Safety_yes:
                if (checked)
                    WHO_Patient_Safety_status = "Yes";
                break;

            case R.id.WHO_Patient_Safety_no:
                if (checked)
                    WHO_Patient_Safety_status = "No";
                break;

            case R.id.OT_Zoning_yes:
                if (checked)
                    OT_Zoning_status = "Yes";
                break;

            case R.id.OT_Zoning_no:
                if (checked)
                    OT_Zoning_status = "No";
                break;

            case R.id.infection_control_yes:
                if (checked)
                    infection_control_status = "Yes";
                break;

            case R.id.infection_control_no:
                if (checked)
                    infection_control_status = "No";
                break;

            case R.id.narcotic_drugs_yes:
                if (checked)
                    narcotic_drugs_status = "Yes";
                break;

            case R.id.narcotic_drugs_no:
                if (checked)
                    narcotic_drugs_status = "No";
                break;

            case R.id.administration_disposal_yes:
                if (checked)
                    administration_disposal_status = "Yes";
                break;

            case R.id.administration_disposal_no:
                if (checked)
                    administration_disposal_status = "No";
                break;

            case R.id.hand_wash_facility_yes:
                if (checked)
                    hand_wash_facility_status = "Yes";
                break;

            case R.id.hand_wash_facility_no:
                if (checked)
                    hand_wash_facility_status = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(OTActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(OTActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, 7);
    }

    public void showVideoDialog(final String path,final int pos) {
        dialogLogout = new Dialog(OTActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(OTActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
                startActivityForResult(intent, 7);
            }
        });

        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
        imageView.setImageBitmap(bmThumbnail);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 6) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    ImageUpload(image2,"WHO_Patient_Safety");

                }

            }
            if (requestCode == 7) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    ImageUpload(image2,"OT_Zoning");

                }

            }
            if (requestCode == 8) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    ImageUpload(image2,"infection_control");

                }

            }
            else if (requestCode == 9) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                    ImageUpload(image3,"narcotic_drugs");

                }

            }
            else if (requestCode == 10) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    ImageUpload(image4,"administration_disposal");

                }
            }   else if (requestCode == 11) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    ImageUpload(image4,"hand_wash_facility");

                }
            }


        }
    }

   /* private void VideoUpload(final String image_path, final String from){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(OTActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(OTActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();

                        if (from.equalsIgnoreCase("case_of_grievances")){
                            video7 = response.body().getMessage();
                            Local_video7 = image_path;
                            video_OT_Zoning.setImageResource(R.mipmap.camera_selected);
                        }


                    }else {
                        Toast.makeText(OTActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(OTActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(OTActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }*/

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(OTActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_OT_anaesthetist.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_OT_anaesthetist.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        } else if (position == 2) {

                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_documented_anaesthesia.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_documented_anaesthesia.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_immediate_preoperative.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_immediate_preoperative.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_anaesthesia_monitoring.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_anaesthesia_monitoring.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                    } else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_post_anaesthesia_monitoring.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_post_anaesthesia_monitoring.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {

                                nc_WHO_Patient_Safety.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_WHO_Patient_Safety.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 7) {
                        if (radio_status7 != null) {

                            if (radio_status7.equalsIgnoreCase("close")) {

                                nc_OT_Zoning.setImageResource(R.mipmap.nc);

                                nc7 = radio_status7;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status7.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_OT_Zoning.setImageResource(R.mipmap.nc_selected);

                                    nc7 = radio_status7 + "," + edit_text.getText().toString();

                                    radio_status7 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 8) {
                        if (radio_status8 != null) {

                            if (radio_status8.equalsIgnoreCase("close")) {

                                nc_infection_control.setImageResource(R.mipmap.nc);

                                nc8 = radio_status8;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status8.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_infection_control.setImageResource(R.mipmap.nc_selected);

                                    nc8 = radio_status8 + "," + edit_text.getText().toString();

                                    radio_status8 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 9) {
                        if (radio_status9 != null) {

                            if (radio_status9.equalsIgnoreCase("close")) {

                                nc_narcotic_drugs.setImageResource(R.mipmap.nc);

                                nc9 = radio_status9;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status9.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {

                                    nc_narcotic_drugs.setImageResource(R.mipmap.nc_selected);

                                    nc9 = radio_status9 + "," + edit_text.getText().toString();

                                    radio_status9 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 10) {
                        if (radio_status10 != null) {

                            if (radio_status10.equalsIgnoreCase("close")) {

                                nc_administration_disposal.setImageResource(R.mipmap.nc);

                                nc10 = radio_status10;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status10.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_administration_disposal.setImageResource(R.mipmap.nc_selected);

                                    nc10 = radio_status10 + "," + edit_text.getText().toString();

                                    radio_status10 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 11) {
                        if (radio_status11 != null) {

                            if (radio_status11.equalsIgnoreCase("close")) {

                                nc_hand_wash_facility.setImageResource(R.mipmap.nc);

                                nc11 = radio_status11;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status11.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {


                                    nc_hand_wash_facility.setImageResource(R.mipmap.nc_selected);

                                    nc11 = radio_status11 + "," + edit_text.getText().toString();

                                    radio_status11 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(OTActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(OTActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_OT_anaesthetist.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_documented_anaesthesia.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_immediate_preoperative.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_anaesthesia_monitoring.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_post_anaesthesia_monitoring.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_WHO_Patient_Safety.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }else if (position == 7) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark7 = edit_text.getText().toString();
                            remark_OT_Zoning.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }else if (position == 8) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark8 = edit_text.getText().toString();
                            remark_infection_control.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }else if (position == 9) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark9 = edit_text.getText().toString();
                            remark_narcotic_drugs.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }else if (position == 10) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark10 = edit_text.getText().toString();
                            remark_administration_disposal.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }
                    else if (position == 11) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark11 = edit_text.getText().toString();
                            remark_hand_wash_facility.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        Toast.makeText(OTActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(OTActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(OTActivity.this,list,from,"OT");
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
        pojo.setHospital_id(6);
        if (getFromPrefs("OT_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("OT_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }

        pojo.setAnaesthetist_status(anaesthetist_status);
        pojo.setDocumented_anaesthesia_status(documented_anaesthesia_status);
        pojo.setImmediate_preoperative_status(immediate_preoperative_status);
        pojo.setAnaesthesia_monitoring_status(anaesthesia_monitoring_status);
        pojo.setPost_anaesthesia_monitoring_status(post_anaesthesia_monitoring);
        pojo.setWHO_Patient_Safety_status(WHO_Patient_Safety_status);
        pojo.setOT_Zoning_status(OT_Zoning_status);
        pojo.setInfection_control_status(infection_control_status);
        pojo.setNarcotic_drugs_status(narcotic_drugs_status);
        pojo.setAdministration_disposal_status(administration_disposal_status);
        pojo.setHand_wash_facility_status(hand_wash_facility_status);

        pojo.setRemark_OT_anaesthetist(remark1);
        pojo.setNc_OT_anaesthetist(nc1);


        pojo.setRemark_documented_anaesthesia(remark2);
        pojo.setNc_documented_anaesthesia(nc2);

        pojo.setRemark_immediate_preoperative(remark3);
        pojo.setNc_immediate_preoperative(nc3);


        pojo.setRemark_anaesthesia_monitoring(remark4);
        pojo.setNc_anaesthesia_monitoring(nc4);


        pojo.setRemark_post_anaesthesia_monitoring(remark5);
        pojo.setNc_post_anaesthesia_monitoring(nc5);

        pojo.setRemark_WHO_Patient_Safety(remark6);
        pojo.setNc_WHO_Patient_Safety(nc6);

        JSONObject json = new JSONObject();

        if (WHO_Patient_Safety_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(WHO_Patient_Safety_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image6 = json.toString();
        }else {
            image6 = null;
        }

        if (Local_WHO_Patient_Safety_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_WHO_Patient_Safety_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image6 = json.toString();
        }else {
            Local_image6 = null;
        }
        pojo.setImage_WHO_Patient_Safety_day1(image6);
        pojo.setLocal_image_WHO_Patient_Safety_day1(Local_image6);

        if (OT_Zoning_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(OT_Zoning_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image7 = json.toString();
        }else {
            image7 = null;
        }

        if (Local_OT_Zoning_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_OT_Zoning_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image7 = json.toString();
        }else {
            Local_image7 = null;
        }



        pojo.setRemark_OT_Zoning(remark7);
        pojo.setNc_OT_Zoning(nc7);
        pojo.setVideo_OT_Zoning(image7);


        pojo.setLocal_video_OT_Zoning(Local_image7);


        pojo.setRemark_infection_control(remark8);
        pojo.setNc_infection_control(nc8);


        if (infection_control_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(infection_control_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image8 = json.toString();
        }else {
            image8 = null;
        }



        if (Local_infection_control_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_infection_control_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image8 = json.toString();
        }else {
            Local_image8 = null;
        }

        pojo.setImage_infection_control(image8);
        pojo.setLocal_image_infection_control(Local_image8);

        pojo.setRemark_narcotic_drugs(remark9);
        pojo.setNc_narcotic_drugs(nc9);

        if (narcotic_drugs_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(narcotic_drugs_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image9 = json.toString();
        }else {
            image9 = null;
        }


        if (Local_narcotic_drugs_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_narcotic_drugs_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image9 = json.toString();
        }else {
            Local_image9 = null;
        }

        pojo.setImage_narcotic_drugs(image9);
        pojo.setLocal_image_narcotic_drugs(Local_image9);


        pojo.setRemark_administration_disposal(remark10);
        pojo.setNc_administration_disposal(nc10);


        if (administration_disposal_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(administration_disposal_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image10 = json.toString();
        }else {
            image10 = null;
        }



        if (Local_administration_disposal_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_administration_disposal_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image10 = json.toString();
        }else {
            Local_image10 = null;
        }

        pojo.setImage_administration_disposal(image10);
        pojo.setLocal_image_administration_disposal(Local_image10);

        pojo.setRemark_hand_wash_facility(remark11);
        pojo.setNc_hand_wash_facility(nc11);

        if (hand_wash_facility_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(hand_wash_facility_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image11 = json.toString();
        }else {
            image11 = null;
        }



        if (Local_hand_wash_facility_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_hand_wash_facility_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image11 = json.toString();
        }else {
            Local_image11 = null;
        }

        pojo.setImage_hand_wash_facility(image11);
        pojo.setLocal_image_hand_wash_facility(Local_image11);


        if (sql_status){
            databaseHandler.UPDATE_OT(pojo);
        }else {
            boolean status = databaseHandler.INSERT_OT(pojo);
            System.out.println(status);
        }

        if (!from.equalsIgnoreCase("sync")){

            assessement_list = databaseHandler.getAssessmentList(Hospital_id);

            AssessmentStatusPojo pojo = new AssessmentStatusPojo();
            pojo.setHospital_id(assessement_list.get(6).getHospital_id());
            pojo.setAssessement_name(assessement_list.get(6).getAssessement_name());
            pojo.setAssessement_status("Draft");
            pojo.setLocal_id(assessement_list.get(6).getLocal_id());

            databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

            Toast.makeText(OTActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(OTActivity.this,HospitalListActivity.class);
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

        final ProgressDialog d = ImageDialog.showLoading(OTActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(OTActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(OTActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("infection_control")){
                                infection_control_List.add(response.body().getMessage());
                                Local_infection_control_List.add(image_path);
                                image_infection_control.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("narcotic_drugs")){

                                narcotic_drugs_list.add(response.body().getMessage());
                                Local_narcotic_drugs_list.add(image_path);
                                image_narcotic_drugs.setImageResource(R.mipmap.camera_selected);
                            }
                            else if (from.equalsIgnoreCase("administration_disposal")){

                                administration_disposal_list.add(response.body().getMessage());
                                Local_administration_disposal_list.add(image_path);
                                image_administration_disposal.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("hand_wash_facility")){
                                hand_wash_facility_list.add(response.body().getMessage());
                                Local_hand_wash_facility_list.add(image_path);
                                image_hand_wash_facility.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("WHO_Patient_Safety")){
                                WHO_Patient_Safety_List.add(response.body().getMessage());
                                Local_WHO_Patient_Safety_List.add(image_path);
                                Image_WHO_Patient_Safety.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("OT_Zoning")){
                                OT_Zoning_List.add(response.body().getMessage());
                                Local_OT_Zoning_List.add(image_path);
                                video_OT_Zoning.setImageResource(R.mipmap.camera_selected);
                            }


                            Toast.makeText(OTActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(OTActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(OTActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(OTActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void PostLaboratoryData(){

        SaveLaboratoryData("sync");

        if (anaesthetist_status.length() > 0 && documented_anaesthesia_status.length() > 0 && immediate_preoperative_status.length() >0 && anaesthesia_monitoring_status.length() > 0 && post_anaesthesia_monitoring.length() > 0 &&
                WHO_Patient_Safety_status.length() > 0 && OT_Zoning_status.length() > 0 && infection_control_status.length() > 0 && narcotic_drugs_status.length() > 0 && administration_disposal_status.length() > 0
        && hand_wash_facility_status.length() > 0){

            if (image6 != null && image8 != null && image9 != null  && image10 != null && image11 != null){
                pojo_dataSync.setTabName("oticu");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<WHO_Patient_Safety_List.size();i++){
                    String value_WHO_Patient_Safety= WHO_Patient_Safety_List.get(i);

                    WHO_Patient_Safety = value_WHO_Patient_Safety + WHO_Patient_Safety;
                }
                pojo.setImage_WHO_Patient_Safety_day1(WHO_Patient_Safety);

                for (int i=0;i<infection_control_List.size();i++){
                    String value_rail = infection_control_List.get(i);

                    infection_control = value_rail + infection_control;
                }
                pojo.setImage_infection_control(infection_control);

                for (int i=0;i<narcotic_drugs_list.size();i++){
                    String value_transported = narcotic_drugs_list.get(i);

                    narcotic_drugs = value_transported + narcotic_drugs;
                }

                pojo.setImage_narcotic_drugs(narcotic_drugs);

                for (int i=0;i<administration_disposal_list.size();i++){
                    String value_transported = administration_disposal_list.get(i);

                    administration_disposal = value_transported + administration_disposal;
                }

                pojo.setImage_administration_disposal(administration_disposal);

                for (int i=0;i<hand_wash_facility_list.size();i++){
                    String value_hand_wash_facility = hand_wash_facility_list.get(i);

                    hand_wash_facility = value_hand_wash_facility + hand_wash_facility;
                }
                pojo.setImage_hand_wash_facility(hand_wash_facility);


                for (int i=0;i<OT_Zoning_List.size();i++){
                    String value_ot_zoing = OT_Zoning_List.get(i);

                    OT_ZOING = value_ot_zoing + OT_ZOING;
                }

                pojo.setVideo_OT_Zoning(OT_ZOING);


                pojo_dataSync.setOticu(pojo);

                final ProgressDialog d = AppDialog.showLoading(OTActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(OTActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(OTActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(OTActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("OT_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(6).getHospital_id());
                                    pojo.setAssessement_name(assessement_list.get(6).getAssessement_name());
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(6).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(OTActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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
                Toast.makeText(OTActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(OTActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
        }
    }

    private void Post_SHCO_LaboratoryData(){

        SaveLaboratoryData("sync");

        if (WHO_Patient_Safety_status.length() > 0 && infection_control_status.length() > 0 && narcotic_drugs_status.length() > 0 && administration_disposal_status.length() > 0
                && hand_wash_facility_status.length() > 0){

            if (image6 != null && image8 != null && image9 != null  && image10 != null && image11 != null){
                pojo_dataSync.setTabName("oticu");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                for (int i=0;i<WHO_Patient_Safety_List.size();i++){
                    String value_WHO_Patient_Safety= WHO_Patient_Safety_List.get(i);

                    WHO_Patient_Safety = value_WHO_Patient_Safety + WHO_Patient_Safety;
                }
                pojo.setImage_WHO_Patient_Safety_day1(WHO_Patient_Safety);

                for (int i=0;i<infection_control_List.size();i++){
                    String value_rail = infection_control_List.get(i);

                    infection_control = value_rail + infection_control;
                }
                pojo.setImage_infection_control(infection_control);

                for (int i=0;i<narcotic_drugs_list.size();i++){
                    String value_transported = narcotic_drugs_list.get(i);

                    narcotic_drugs = value_transported + narcotic_drugs;
                }

                pojo.setImage_narcotic_drugs(narcotic_drugs);

                for (int i=0;i<administration_disposal_list.size();i++){
                    String value_transported = administration_disposal_list.get(i);

                    administration_disposal = value_transported + administration_disposal;
                }

                pojo.setImage_administration_disposal(administration_disposal);

                for (int i=0;i<hand_wash_facility_list.size();i++){
                    String value_hand_wash_facility = hand_wash_facility_list.get(i);

                    hand_wash_facility = value_hand_wash_facility + hand_wash_facility;
                }
                pojo.setImage_hand_wash_facility(hand_wash_facility);

                for (int i=0;i<OT_Zoning_List.size();i++){
                    String value_ot_zoing = OT_Zoning_List.get(i);

                    OT_ZOING = value_ot_zoing + OT_ZOING;
                }

                pojo.setVideo_OT_Zoning(OT_ZOING);


                pojo_dataSync.setOticu(pojo);

                final ProgressDialog d = AppDialog.showLoading(OTActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(OTActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(OTActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(OTActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("OT_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(6).getHospital_id());
                                    pojo.setAssessement_name(assessement_list.get(6).getAssessement_name());
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(6).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(OTActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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
                Toast.makeText(OTActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(OTActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("infection_control")){
                infection_control_List.remove(position);
                Local_infection_control_List.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (infection_control_List.size() == 0){
                    image_infection_control.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("narcotic_drugs")){
                narcotic_drugs_list.remove(position);
                Local_narcotic_drugs_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (narcotic_drugs_list.size() == 0){
                    image_narcotic_drugs.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("administration_disposal")){
                administration_disposal_list.remove(position);
                Local_administration_disposal_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_administration_disposal_list.size() == 0){
                    image_administration_disposal.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("hand_wash_facility")){
                hand_wash_facility_list.remove(position);
                Local_hand_wash_facility_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_hand_wash_facility_list.size() == 0){
                    image_hand_wash_facility.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            else if (from.equalsIgnoreCase("WHO_Patient_Safety")){
                WHO_Patient_Safety_List.remove(position);
                Local_WHO_Patient_Safety_List.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_WHO_Patient_Safety_List.size() == 0){
                    Image_WHO_Patient_Safety.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("OT_Zoning")){
                OT_Zoning_List.remove(position);
                Local_OT_Zoning_List.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_OT_Zoning_List.size() == 0){
                    video_OT_Zoning.setImageResource(R.mipmap.camera);

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
