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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qci.onsite.MainActivity;
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

/**
 * Created by Ankit on 21-01-2019.
 */

public class LaboratoryActivity extends BaseActivity implements View.OnClickListener  {

    @BindView(R.id.remark_collected)
    ImageView remark_collected;
    @BindView(R.id.nc_collected)
    ImageView nc_collected;
    @BindView(R.id.video_collected)
    ImageView video_collected;

    @BindView(R.id.remark_Identified)
    ImageView remark_Identified;
    @BindView(R.id.nc_Identified)
    ImageView nc_Identified;
    @BindView(R.id.image_Identified)
    ImageView image_Identified;

    @BindView(R.id.remark_transported)
    ImageView remark_transported;
    @BindView(R.id.nc_transported)
    ImageView nc_transported;
    @BindView(R.id.image_transported)
    ImageView image_transported;

    @BindView(R.id.remark_specimen)
    ImageView remark_specimen;
    @BindView(R.id.nc_specimen)
    ImageView nc_specimen;
    @BindView(R.id.image_specimen)
    ImageView image_specimen;

    @BindView(R.id.remark_equipment)
    ImageView remark_equipment;
    @BindView(R.id.nc_equipment)
    ImageView nc_equipment;
    @BindView(R.id.image_equipment)
    ImageView image_equipment;

    private LaboratoryPojo pojo;

    private String properly_status = "",identified_status = "",transported_status = "",specimen_status = "",
            appropriate_status = "",laboratory_defined_turnaround ="",shco_specimen_done_status;

    @BindView(R.id.properly_yes)
    RadioButton properly_yes;
    @BindView(R.id.properly_no)
    RadioButton properly_no;

    @BindView(R.id.identified_yes)
    RadioButton identified_yes;
    @BindView(R.id.identified_no)
    RadioButton identified_no;

    @BindView(R.id.transported_yes)
    RadioButton transported_yes;
    @BindView(R.id.transported_no)
    RadioButton transported_no;

    @BindView(R.id.specimen_yes)
    RadioButton specimen_yes;
    @BindView(R.id.specimen_no)
    RadioButton specimen_no;

    @BindView(R.id.appropriate_yes)
    RadioButton appropriate_yes;
    @BindView(R.id.appropriate_no)
    RadioButton appropriate_no;

    @BindView(R.id.laboratory_defined_turnaround_yes)
    RadioButton laboratory_defined_turnaround_yes;

    @BindView(R.id.laboratory_defined_turnaround_no)
    RadioButton laboratory_defined_turnaround_no;

    @BindView(R.id.remark_laboratory_defined_turnaround)
    ImageView remark_laboratory_defined_turnaround;

    @BindView(R.id.nc_laboratory_defined_turnaround)
    ImageView nc_laboratory_defined_turnaround;

    @BindView(R.id.image_laboratory_defined_turnaround)
    ImageView image_laboratory_defined_turnaround;

    @BindView(R.id.ll_collected_properly)
    LinearLayout ll_collected_properly;

    @BindView(R.id.ll_identified_properly)
    LinearLayout ll_identified_properly;

    @BindView(R.id.ll_transported)
    LinearLayout ll_transported;

    @BindView(R.id.ll_specimen)
    LinearLayout ll_specimen;

    @BindView(R.id.ll_laboratory_defined_turnaround)
    LinearLayout ll_laboratory_defined_turnaround;

    @BindView(R.id.ll_appropriate)
    LinearLayout ll_appropriate;



    @BindView(R.id.btnSave)
    Button btnSave;

    private String remark1, remark2, remark3, remark4, remark5,remark6,remark7;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private ArrayList<String>Identified_List;
    private ArrayList<String>transported_list;
    private ArrayList<String>specimen_list;
    private ArrayList<String>equipment_list;
    private ArrayList<String>laboratory_defined_turnaround_list;

    private ArrayList<String>Local_Identified_List;
    private ArrayList<String>Local_transported_list;
    private ArrayList<String>Local_specimen_list;
    private ArrayList<String>Local_equipment_list;
    private ArrayList<String>Local_laboratory_defined_turnaround_list;

    private ArrayList<String>shco_specimen_done_list;
    private ArrayList<String>Local_shco_specimen_done_list;

    private String nc1, nc2, nc3, nc4, nc5,nc6,nc7;
    private String radio_status1, radio_status2, radio_status3, radio_status4, radio_status5,radio_status6,radio_status7;

    private DatabaseHandler databaseHandler;

    private String video1,image2,image3,image4,image5,image6;
    private String Local_video1,Local_image2,Local_image3,Local_image4,Local_image5,Local_image6;

    private File outputVideoFile;

    Uri videoUri;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    @BindView(R.id.laboratory_hospital_name)
    TextView laboratory_hospital_name;

    private String Identified = "",transported ="",specimen = "",equipment ="",laboratory_defined_turnaround_view ="";

    int Bed_no = 0;

    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);

        ButterKnife.bind(this);

        setDrawerbackIcon("Laboratory");

        Identified_List = new ArrayList<>();
        transported_list = new ArrayList<>();
        specimen_list = new ArrayList<>();
        equipment_list = new ArrayList<>();
        laboratory_defined_turnaround_list = new ArrayList<>();

        Local_Identified_List = new ArrayList<>();
        Local_transported_list = new ArrayList<>();
        Local_specimen_list = new ArrayList<>();
        Local_equipment_list = new ArrayList<>();
        Local_laboratory_defined_turnaround_list = new ArrayList<>();

        databaseHandler = DatabaseHandler.getInstance(this);

        pojo_dataSync = new DataSyncRequest();

        pojo = new LaboratoryPojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_collected_properly.setVisibility(View.GONE);
            ll_identified_properly.setVisibility(View.GONE);
            ll_transported.setVisibility(View.GONE);
            ll_laboratory_defined_turnaround.setVisibility(View.GONE);
            ll_appropriate.setVisibility(View.GONE);
        }

        laboratory_hospital_name.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getLaboratoryData();
    }

    public void getLaboratoryData(){

        pojo = databaseHandler.getLaboratoryPojo("1");

        if (pojo != null){
            sql_status = true;
            if (pojo.getLABORATORY_collected_properly() != null){
                properly_status = pojo.getLABORATORY_collected_properly();
              if (pojo.getLABORATORY_collected_properly().equalsIgnoreCase("Yes")){
                  properly_yes.setChecked(true);
              }else if (pojo.getLABORATORY_collected_properly().equalsIgnoreCase("No")){
                  properly_no.setChecked(true);
              }
            }
            if (pojo.getLABORATORY_identified_properly() != null){
                identified_status = pojo.getLABORATORY_identified_properly();
                if (pojo.getLABORATORY_identified_properly().equalsIgnoreCase("Yes")){
                    identified_yes.setChecked(true);
                }else if (pojo.getLABORATORY_identified_properly().equalsIgnoreCase("No")){
                    identified_no.setChecked(true);
                }
            } if (pojo.getLABORATORY_transported_safe_manner() != null){
                transported_status = pojo.getLABORATORY_transported_safe_manner();
                if (pojo.getLABORATORY_transported_safe_manner().equalsIgnoreCase("Yes")){
                    transported_yes.setChecked(true);
                }else if (pojo.getLABORATORY_transported_safe_manner().equalsIgnoreCase("No")){
                    transported_no.setChecked(true);
                }
            } if (pojo.getLABORATORY_Specimen_safe_manner() != null){
                specimen_status = pojo.getLABORATORY_Specimen_safe_manner();
                if (pojo.getLABORATORY_Specimen_safe_manner().equalsIgnoreCase("Yes")){
                    specimen_yes.setChecked(true);
                }else if (pojo.getLABORATORY_Specimen_safe_manner().equalsIgnoreCase("No")){
                    specimen_no.setChecked(true);
                }
            } if (pojo.getLABORATORY_Appropriate_safety_equipment() != null){
                appropriate_status = pojo.getLABORATORY_Appropriate_safety_equipment();
                if (pojo.getLABORATORY_Appropriate_safety_equipment().equalsIgnoreCase("Yes")){
                    appropriate_yes.setChecked(true);
                }else if (pojo.getLABORATORY_Appropriate_safety_equipment().equalsIgnoreCase("No")){
                    appropriate_no.setChecked(true);
                }
            }

            if (pojo.getLaboratory_defined_turnaround() != null){
                laboratory_defined_turnaround = pojo.getLaboratory_defined_turnaround();
                if (pojo.getLaboratory_defined_turnaround().equalsIgnoreCase("Yes")){
                    laboratory_defined_turnaround_yes.setChecked(true);
                }else if (pojo.getLaboratory_defined_turnaround().equalsIgnoreCase("No")){
                    laboratory_defined_turnaround_no.setChecked(true);
                }
            }

            if (pojo.getCollected_properly_remark() != null){
                remark_collected.setImageResource(R.mipmap.remark_selected);
               remark1 = pojo.getCollected_properly_remark();
            }
            if (pojo.getCollected_properly_NC() != null){
                nc1 = pojo.getCollected_properly_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_collected.setImageResource(R.mipmap.nc);
                }else {
                    nc_collected.setImageResource(R.mipmap.nc_selected);
                }
            }
            if (pojo.getCollected_properly_video() != null){
                video_collected.setImageResource(R.mipmap.camera_selected);
                video1 = pojo.getCollected_properly_video();
            }

            if (pojo.getLocal_Collected_properly_video() != null){
                video_collected.setImageResource(R.mipmap.camera_selected);
                Local_video1 = pojo.getLocal_Collected_properly_video();
            }

            if (pojo.getIdentified_properly_remark() != null){
                remark2 = pojo.getIdentified_properly_remark();

                remark_Identified.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getIdentified_properly_NC() != null){
                nc2 = pojo.getIdentified_properly_NC();

                if (nc2.equalsIgnoreCase("close")){
                    nc_Identified.setImageResource(R.mipmap.nc);
                }else {
                    nc_Identified.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getIdentified_properly_image() != null){
                image_Identified.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getIdentified_properly_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Identified_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_Identified_properly_image() != null){
                image_Identified.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_Identified_properly_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_Identified_List.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getTransported_safe_manner_remark() != null){
                remark3 = pojo.getTransported_safe_manner_remark();

                remark_transported.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getTransported_safe_manner_NC() != null){
                nc3 = pojo.getTransported_safe_manner_NC();

                if (nc3.equalsIgnoreCase("close")){
                    nc_transported.setImageResource(R.mipmap.nc);
                }else {
                    nc_transported.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getTransported_safe_manner_image() != null){
                image_transported.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getTransported_safe_manner_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            transported_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_Transported_safe_manner_image() != null){
                image_transported.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_Transported_safe_manner_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_transported_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getSpecimen_safe_manner_remark() != null){
                remark4 = pojo.getSpecimen_safe_manner_remark();

                remark_specimen.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getSpecimen_safe_manner_nc() != null){
                nc4 = pojo.getSpecimen_safe_manner_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_specimen.setImageResource(R.mipmap.nc);
                }else {
                    nc_specimen.setImageResource(R.mipmap.nc_selected);
                }
            }
            if (pojo.getSpecimen_safe_image() != null){

                image_specimen.setImageResource(R.mipmap.camera_selected);

                image4 = pojo.getSpecimen_safe_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            specimen_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_Specimen_safe_image() != null){

                image_specimen.setImageResource(R.mipmap.camera_selected);

                Local_image4 = pojo.getLocal_Specimen_safe_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image4);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_specimen_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (pojo.getAppropriate_safety_equipment_remark() != null){
                remark5 = pojo.getAppropriate_safety_equipment_remark();

                remark_equipment.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getAppropriate_safety_equipment_NC() != null){
                nc5 = pojo.getAppropriate_safety_equipment_NC();

                if (nc5.equalsIgnoreCase("close")){
                    nc_equipment.setImageResource(R.mipmap.nc);
                }else {
                    nc_equipment.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getAppropriate_safety_equipment_image() != null){
                image_equipment.setImageResource(R.mipmap.camera_selected);

                image5 = pojo.getAppropriate_safety_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            equipment_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getLocal_Appropriate_safety_equipment_image() != null){
                image_equipment.setImageResource(R.mipmap.camera_selected);

                Local_image5 = pojo.getLocal_Appropriate_safety_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image5);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_equipment_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pojo.getLaboratory_defined_turnaround_Remark() != null){
                remark6 = pojo.getLaboratory_defined_turnaround_Remark();

                remark_laboratory_defined_turnaround.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getLaboratory_defined_turnaround_Nc() != null){
                nc6 = pojo.getLaboratory_defined_turnaround_Nc();

                if (nc6.equalsIgnoreCase("close")){
                    nc_laboratory_defined_turnaround.setImageResource(R.mipmap.nc);
                }else {
                    nc_laboratory_defined_turnaround.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getLaboratory_defined_turnaround_image() != null){
                image_laboratory_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                image6 = pojo.getLaboratory_defined_turnaround_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            laboratory_defined_turnaround_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_laboratory_defined_turnaround_image() != null){
                image_laboratory_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                Local_image6 = pojo.getLocal_laboratory_defined_turnaround_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image6);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_laboratory_defined_turnaround_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }else {
            pojo = new LaboratoryPojo();
        }

    }



    @OnClick({R.id.remark_collected,R.id.video_collected,R.id.remark_Identified,R.id.image_Identified,R.id.remark_transported,
    R.id.image_transported,R.id.remark_specimen,R.id.image_specimen,R.id.remark_equipment,R.id.image_equipment,
    R.id.nc_collected,R.id.nc_Identified,R.id.nc_transported,R.id.nc_specimen,R.id.nc_equipment,R.id.remark_laboratory_defined_turnaround,
            R.id.nc_laboratory_defined_turnaround,R.id.image_laboratory_defined_turnaround,R.id.btnSave,R.id.btnSync,
   })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_collected:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_collected:
                displayNCDialog("NC", 1);
                break;

            case R.id.video_collected:
                if (Local_video1 != null){
                    showVideoDialog(Local_video1);
                }else {
                    captureVideo();
                }

                break;

            case R.id.remark_Identified:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_Identified:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_Identified:
                if (Local_Identified_List.size() > 0){
                    showImageListDialog(Local_Identified_List,2,"Identified");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_transported:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_transported:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_transported:
                if (Local_transported_list.size() > 0){
                    showImageListDialog(Local_transported_list,3,"transported");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.remark_specimen:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_specimen:
                displayNCDialog("NC", 4);
                break;

            case R.id.image_specimen:
                if (Local_specimen_list.size() > 0){
                    showImageListDialog(Local_specimen_list,4,"specimen");
                }else {
                    captureImage(4);
                }
                break;
            case R.id.remark_equipment:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_equipment:
                displayNCDialog("NC", 5);
                break;


            case R.id.image_equipment:
                if (Local_equipment_list.size() > 0){
                    showImageListDialog(Local_equipment_list,5,"equipment");
                }else {
                    captureImage(5);
                }
                break;

            case R.id.remark_laboratory_defined_turnaround:
                displayCommonDialogWithHeader("Remark", 6);
                break;

            case R.id.nc_laboratory_defined_turnaround :
                displayNCDialog("NC", 6);
                break;

            case R.id.image_laboratory_defined_turnaround:
                if (Local_laboratory_defined_turnaround_list.size() > 0){
                    showImageListDialog(Local_laboratory_defined_turnaround_list,6,"laboratory_defined_turnaround");
                }else {
                    captureImage(6);
                }

                break;
                case R.id.btnSave:
                SaveLaboratoryData("save","");
                break;

            case R.id.btnSync:

                if (Bed_no < 51){

                    if ( specimen_status.length() > 0 ){
                        if ( Local_image4 != null){
                            SaveLaboratoryData("sync","shco");
                        }else {
                            Toast.makeText(LaboratoryActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(LaboratoryActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
                    }

                }else {
                    if (identified_status.length() > 0 && transported_status.length() > 0 && specimen_status.length() > 0
                            && appropriate_status.length() > 0 && laboratory_defined_turnaround.length() >0){

                        if (Local_image3 != null && Local_image4 != null && Local_image5 != null && Local_image6 != null){
                            SaveLaboratoryData("sync","hco");
                        }else {
                            Toast.makeText(LaboratoryActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(LaboratoryActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

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
            case R.id.properly_yes:
                if (checked)
                    properly_status = "Yes";
                break;

            case R.id.properly_no:
                if (checked)
                    properly_status = "No";
                break;

            case R.id.identified_yes:
                if (checked)
                    identified_status = "Yes";
                break;
            case R.id.identified_no:
                if (checked)
                    identified_status = "No";
                break;


            case R.id.transported_yes:
                if (checked)
                    transported_status = "Yes";
                break;
            case R.id.transported_no:
                if (checked)
                    transported_status = "No";
                break;

            case R.id.specimen_yes:
                if (checked)
                    specimen_status = "Yes";
                break;
            case R.id.specimen_no:
                if (checked)
                    specimen_status = "No";
                break;

            case R.id.appropriate_yes:
                if (checked)
                    appropriate_status = "Yes";
                break;

            case R.id.appropriate_no:
                if (checked)
                    appropriate_status = "No";
                break;

            case R.id.laboratory_defined_turnaround_yes:
                laboratory_defined_turnaround = "Yes";
                break;

            case R.id.laboratory_defined_turnaround_no:
                laboratory_defined_turnaround = "No";
                break;


        }
    }

    private void captureVideo(){
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(LaboratoryActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, 1);
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(LaboratoryActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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
                if (resultCode == RESULT_OK) {

                    VideoUpload(String.valueOf(outputVideoFile));

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }
            }else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"Identified");


                }

            }
            else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                    SaveImage(image3,"transported");
                }

            }
            else if (requestCode == 4) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image4 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_Registration,image4);


                    SaveImage(image4,"specimen");

                }

            }
            else if (requestCode == 5) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image5 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_Registration_MTP,image5);


                    SaveImage(image5,"equipment");

                }

            }

            else if (requestCode == 6) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image6 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_Registration_MTP,image5);

                    SaveImage(image6,"laboratory_defined_turnaround");

                }

            }

            else if (requestCode == 7) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image7 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_Registration_MTP,image5);

                    SaveImage(image7,"shco_specimen_done");

                }

            }
        }
    }

    private void VideoUpload(final String image_path){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(LaboratoryActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(LaboratoryActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();

                        video1 = response.body().getMessage();
                        Local_video1 = image_path;
                        video_collected.setImageResource(R.mipmap.camera_selected);

                    }else {
                        Toast.makeText(LaboratoryActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LaboratoryActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(LaboratoryActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(LaboratoryActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                    }
                    if (position == 7) {
                        radio_status7 = "close";
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
                                nc_collected.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_collected.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {

                        if (radio_status2 != null) {

                                if (radio_status2.equalsIgnoreCase("close")){
                                    nc_Identified.setImageResource(R.mipmap.nc);

                                    nc2 = radio_status2 ;

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                        nc_Identified.setImageResource(R.mipmap.nc_selected);

                                        nc2 = radio_status2 + "," + edit_text.getText().toString();

                                        radio_status2 = "";

                                        DialogLogOut.dismiss();

                                        getWindow().setSoftInputMode(
                                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                    }else {
                                        Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                    }
                                }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_transported.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_transported.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_specimen.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_specimen.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_equipment.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_equipment.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 6) {
                        if (radio_status6 != null) {

                            if (radio_status6.equalsIgnoreCase("close")) {
                                nc_laboratory_defined_turnaround.setImageResource(R.mipmap.nc);

                                nc6 = radio_status6;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status6.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_laboratory_defined_turnaround.setImageResource(R.mipmap.nc_selected);

                                    nc6 = radio_status6 + "," + edit_text.getText().toString();

                                    radio_status6 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(LaboratoryActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(LaboratoryActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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




            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_collected.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_Identified.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_transported.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_specimen.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_equipment.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }



                    } else if (position == 6) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark6 = edit_text.getText().toString();
                            remark_laboratory_defined_turnaround.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(LaboratoryActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(LaboratoryActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(LaboratoryActivity.this,list,from,"Laboratory");
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
                    Toast toast = Toast.makeText(LaboratoryActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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



    public void SaveLaboratoryData(String from,String hospital_status){
        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(1);

        pojo.setLABORATORY_collected_properly(properly_status);
        pojo.setLABORATORY_identified_properly(identified_status);
        pojo.setLABORATORY_transported_safe_manner(transported_status);
        pojo.setLABORATORY_Specimen_safe_manner(specimen_status);
        pojo.setLABORATORY_Appropriate_safety_equipment(appropriate_status);
        pojo.setLaboratory_defined_turnaround(laboratory_defined_turnaround);

        pojo.setCollected_properly_remark(remark1);
        pojo.setCollected_properly_NC(nc1);
        pojo.setCollected_properly_video(video1);
        pojo.setLocal_Collected_properly_video(Local_video1);

        JSONObject json = new JSONObject();

        if (Identified_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Identified_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_Identified_List.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_Identified_List));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }


        pojo.setIdentified_properly_remark(remark2);
        pojo.setIdentified_properly_NC(nc2);
        pojo.setIdentified_properly_image(image2);
        pojo.setLocal_Identified_properly_image(Local_image2);

        if (transported_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(transported_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_transported_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_transported_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }


        pojo.setTransported_safe_manner_remark(remark3);
        pojo.setTransported_safe_manner_NC(nc3);
        pojo.setTransported_safe_manner_image(image3);
        pojo.setLocal_Transported_safe_manner_image(Local_image3);

        if (specimen_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(specimen_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image4 = json.toString();
        }else {
            image4 = null;
        }

        if (Local_specimen_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_specimen_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image4 = json.toString();
        }else {
            Local_image4 = null;
        }


        pojo.setSpecimen_safe_manner_remark(remark4);
        pojo.setSpecimen_safe_manner_nc(nc4);
        pojo.setSpecimen_safe_image(image4);
        pojo.setLocal_Specimen_safe_image(Local_image4);

        if (equipment_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(equipment_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
             image5 = json.toString();
        }else {
            image5 = null;
        }

        if (Local_equipment_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_equipment_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image5 = json.toString();
        }else {
            Local_image5 = null;
        }

        pojo.setAppropriate_safety_equipment_remark(remark5);
        pojo.setAppropriate_safety_equipment_NC(nc5);
        pojo.setAppropriate_safety_equipment_image(image5);
        pojo.setLocal_Appropriate_safety_equipment_image(Local_image5);

        pojo.setLaboratory_defined_turnaround_Remark(remark6);
        pojo.setLaboratory_defined_turnaround_Nc(nc6);


        if (laboratory_defined_turnaround_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(laboratory_defined_turnaround_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image6 = json.toString();
        }else {
            image6 = null;
        }


        if (Local_laboratory_defined_turnaround_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_laboratory_defined_turnaround_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image6 = json.toString();
        }else {
            Local_image6 = null;
        }


        pojo.setLaboratory_defined_turnaround_image(image6);
        pojo.setLocal_laboratory_defined_turnaround_image(Local_image6);


        if (sql_status){
             boolean sqlite_status = databaseHandler.UPDATE_LABORATORY(pojo);

             if (sqlite_status){
                 if (!from.equalsIgnoreCase("sync")){
                     assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                     AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                     pojo.setHospital_id(assessement_list.get(1).getHospital_id());
                     pojo.setAssessement_name("Laboratory");
                     pojo.setAssessement_status("Draft");
                     pojo.setLocal_id(assessement_list.get(1).getLocal_id());

                     databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                     Toast.makeText(LaboratoryActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                     Intent intent = new Intent(LaboratoryActivity.this,HospitalListActivity.class);
                     startActivity(intent);
                     finish();

                 }else {
                     if (hospital_status.equalsIgnoreCase("shco")){
                         new PostSHCODataTask().execute();
                     }else {
                         new PostDataTask().execute();
                     }

                 }
             }
        }else {
            boolean sqlite_status = databaseHandler.INSERT_LABORATORY(pojo);
            if (sqlite_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);


                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(1).getHospital_id());
                    pojo.setAssessement_name("Laboratory");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(1).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(LaboratoryActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LaboratoryActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    if (hospital_status.equalsIgnoreCase("shco")){
                        new PostSHCODataTask().execute();
                    }else {
                        new PostDataTask().execute();
                    }
                }
            }
        }



    }
    public void showVideoDialog(final String path) {
        dialogLogout = new Dialog(LaboratoryActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                File dirVideo = new File(Environment.getExternalStorageDirectory(), "Hope/videos/");
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(LaboratoryActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                startActivityForResult(intent, 1);
            }
        });

        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
        imageView.setImageBitmap(bmThumbnail);

        /*try {
            Glide.with(QualityImprovementActivity.this).load(path)
                    //           .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("Identified")){
            //Identified_List.add(response.body().getMessage());
            Local_Identified_List.add(image_path);
            image_Identified.setImageResource(R.mipmap.camera_selected);

            Local_image2 = "Identified";

        }else if (from.equalsIgnoreCase("transported")){
            //transported_list.add(response.body().getMessage());
            Local_transported_list.add(image_path);
            image_transported.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "transported";

        }else if (from.equalsIgnoreCase("specimen")){
            //specimen_list.add(response.body().getMessage());
            Local_specimen_list.add(image_path);
            image_specimen.setImageResource(R.mipmap.camera_selected);

            Local_image4 = "specimen";

        }else if (from.equalsIgnoreCase("equipment")){
            //equipment_list.add(response.body().getMessage());
            Local_equipment_list.add(image_path);
            image_equipment.setImageResource(R.mipmap.camera_selected);

            Local_image5 = "equipment";

        }else if (from.equalsIgnoreCase("laboratory_defined_turnaround")){
            //laboratory_defined_turnaround_list.add(response.body().getMessage());
            Local_laboratory_defined_turnaround_list.add(image_path);
            image_laboratory_defined_turnaround.setImageResource(R.mipmap.camera_selected);

            Local_image6 = "laboratory_defined_turnaround";
        }


        Toast.makeText(LaboratoryActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();

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
                            Intent intent = new Intent(LaboratoryActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(LaboratoryActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("Identified")){
                                Identified_List.add(response.body().getMessage());
                                //Local_Identified_List.add(image_path);
                                image_Identified.setImageResource(R.mipmap.camera_selected);

                                image2 = "Identified";

                            }else if (from.equalsIgnoreCase("transported")){
                                transported_list.add(response.body().getMessage());
                                //Local_transported_list.add(image_path);
                                image_transported.setImageResource(R.mipmap.camera_selected);

                                image3 = "transported";

                            }else if (from.equalsIgnoreCase("specimen")){
                                specimen_list.add(response.body().getMessage());
                                //Local_specimen_list.add(image_path);
                                image_specimen.setImageResource(R.mipmap.camera_selected);

                                image4 = "specimen";

                            }else if (from.equalsIgnoreCase("equipment")){
                                equipment_list.add(response.body().getMessage());
                                //Local_equipment_list.add(image_path);
                                image_equipment.setImageResource(R.mipmap.camera_selected);

                                image5 = "equipment";

                            }else if (from.equalsIgnoreCase("laboratory_defined_turnaround")){
                                laboratory_defined_turnaround_list.add(response.body().getMessage());
                                //Local_laboratory_defined_turnaround_list.add(image_path);
                                image_laboratory_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                                image6 = "laboratory_defined_turnaround";
                            }
                            check = 1;
                            latch.countDown();

                            //Toast.makeText(LaboratoryActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            //Toast.makeText(LaboratoryActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                            check = 0;
                            latch.countDown();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
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
        for(int i=equipment_list.size(); i<Local_equipment_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_equipment_list.get(i) + "Equip");
            UploadImage(Local_equipment_list.get(i),"equipment");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = Identified_List.size(); i< Local_Identified_List.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_Identified_List.get(i)+ "Identi");
            UploadImage(Local_Identified_List.get(i),"Identified");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = laboratory_defined_turnaround_list.size();i < Local_laboratory_defined_turnaround_list.size() ;i++ )
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_laboratory_defined_turnaround_list.get(i)+ "Labora");
            UploadImage(Local_laboratory_defined_turnaround_list.get(i),"laboratory_defined_turnaround");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i= transported_list.size(); i< Local_transported_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_transported_list.get(i)+ "Transport");
            UploadImage(Local_transported_list.get(i),"transported");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = specimen_list.size() ; i<  Local_specimen_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_specimen_list.get(i)+"Speciemrn");
            UploadImage(Local_specimen_list.get(i),"specimen");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        pojo_dataSync.setTabName("laboratory");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<Identified_List.size();i++){
                    String value_rail = Identified_List.get(i);

                    Identified = value_rail + Identified;
                }
                pojo.setIdentified_properly_image(Identified);

                for (int i=0;i<transported_list.size();i++){
                    String value_transported = transported_list.get(i);

                    transported = value_transported + transported;
                }
                pojo.setTransported_safe_manner_image(transported);

                for (int i=0;i<specimen_list.size();i++){
                    String value_specimen = specimen_list.get(i);

                    specimen = value_specimen + specimen;
                }

                pojo.setSpecimen_safe_image(specimen);

                for (int i=0;i<equipment_list.size();i++){
                    String value_equipment = equipment_list.get(i);

                    equipment = value_equipment + equipment;
                }

                pojo.setAppropriate_safety_equipment_image(equipment);

                for (int i=0;i<laboratory_defined_turnaround_list.size();i++){
                    String value_laboratory_defined_turnaround_view = laboratory_defined_turnaround_list.get(i);

                    laboratory_defined_turnaround_view = value_laboratory_defined_turnaround_view + laboratory_defined_turnaround_view;
                }

                pojo.setLaboratory_defined_turnaround_image(laboratory_defined_turnaround_view);


                pojo_dataSync.setLaboratory(pojo);
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
                                    Intent intent = new Intent(LaboratoryActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(LaboratoryActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(LaboratoryActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("Laboratory_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(1).getHospital_id());
                                    pojo.setAssessement_name("Laboratory");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(1).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LaboratoryActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private class PostSHCODataTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog d;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreesDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostSHCO_LaboratoryData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CloseProgreesDialog();
        }
    }

    private void PostSHCO_LaboratoryData(){
        check = 1;
        for(int i=equipment_list.size(); i<Local_equipment_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_equipment_list.get(i) + "Equip");
            UploadImage(Local_equipment_list.get(i),"equipment");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = Identified_List.size(); i< Local_Identified_List.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_Identified_List.get(i)+ "Identi");
            UploadImage(Local_Identified_List.get(i),"Identified");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        for(int i = laboratory_defined_turnaround_list.size();i < Local_laboratory_defined_turnaround_list.size() ;i++ )
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_laboratory_defined_turnaround_list.get(i)+ "Labora");
            UploadImage(Local_laboratory_defined_turnaround_list.get(i),"laboratory_defined_turnaround");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i= transported_list.size(); i< Local_transported_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_transported_list.get(i)+ "Transport");
            UploadImage(Local_transported_list.get(i),"transported");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = specimen_list.size() ; i<  Local_specimen_list.size();i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_specimen_list.get(i)+"Speciemrn");
            UploadImage(Local_specimen_list.get(i),"specimen");
            try {
                latch.await();
            }
            catch(Exception ex)
            {
                Log.e("Upload",ex.getMessage());
            }
            if(check==0)
            {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LaboratoryActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("laboratory");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }



                for (int i=0;i<specimen_list.size();i++){
                    String value_specimen = specimen_list.get(i);

                    specimen = value_specimen + specimen;
                }

                pojo.setSpecimen_safe_image(specimen);


                pojo_dataSync.setLaboratory(pojo);


                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(LaboratoryActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(LaboratoryActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(LaboratoryActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("Laboratory_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(1).getHospital_id());
                                    pojo.setAssessement_name("Laboratory");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(1).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LaboratoryActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("Identified")){
                Local_Identified_List.remove(position);
                Identified_List.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Identified_List.size() == 0){
                    image_Identified.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("transported")){
                Local_transported_list.remove(position);
                transported_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (transported_list.size() == 0){
                    image_transported.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("specimen")){
                Local_specimen_list.remove(position);
                specimen_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (specimen_list.size() == 0){
                    image_specimen.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }


            }else if (from.equalsIgnoreCase("equipment")){
                Local_equipment_list.remove(position);
                equipment_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (equipment_list.size() == 0){
                    image_equipment.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("laboratory_defined_turnaround")){
                Local_laboratory_defined_turnaround_list.remove(position);
                laboratory_defined_turnaround_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (laboratory_defined_turnaround_list.size() == 0){
                    image_laboratory_defined_turnaround.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(1).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("save","");
        }else {
            Intent intent = new Intent(LaboratoryActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }



    }
}
