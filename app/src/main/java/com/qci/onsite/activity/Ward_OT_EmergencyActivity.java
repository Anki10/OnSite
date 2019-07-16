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
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.Patient_Staff_InterviewPojo;
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

public class Ward_OT_EmergencyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.hospital_maintain_cleanliness_yes)
    RadioButton hospital_maintain_cleanliness_yes;

    @BindView(R.id.hospital_maintain_cleanliness_no)
    RadioButton hospital_maintain_cleanliness_no;

    @BindView(R.id.remark_hospital_maintain_cleanliness)
    ImageView remark_hospital_maintain_cleanliness;

    @BindView(R.id.nc_hospital_maintain_cleanliness)
    ImageView nc_hospital_maintain_cleanliness;

    @BindView(R.id.hospital_adhere_standard_yes)
    RadioButton hospital_adhere_standard_yes;

    @BindView(R.id.hospital_adhere_standard_no)
    RadioButton hospital_adhere_standard_no;

    @BindView(R.id.remark_hospital_adhere_standard)
    ImageView remark_hospital_adhere_standard;

    @BindView(R.id.nc_hospital_adhere_standard)
    ImageView nc_hospital_adhere_standard;

    @BindView(R.id.hospital_provide_adequate_gloves_yes)
    RadioButton hospital_provide_adequate_gloves_yes;

    @BindView(R.id.hospital_provide_adequate_gloves_no)
    RadioButton hospital_provide_adequate_gloves_no;

    @BindView(R.id.remark_hospital_provide_adequate_gloves)
    ImageView remark_hospital_provide_adequate_gloves;

    @BindView(R.id.nc_hospital_provide_adequate_gloves)
    ImageView nc_hospital_provide_adequate_gloves;

    @BindView(R.id.admissions_discharge_home_yes)
    RadioButton admissions_discharge_home_yes;

    @BindView(R.id.admissions_discharge_home_no)
    RadioButton admissions_discharge_home_no;

    @BindView(R.id.remark_admissions_discharge_home)
    ImageView remark_admissions_discharge_home;

    @BindView(R.id.nc_admissions_discharge_home)
    ImageView nc_admissions_discharge_home;

    @BindView(R.id.Video_admissions_discharge_home)
    ImageView Video_admissions_discharge_home;

    @BindView(R.id.staff_present_in_ICU_yes)
    RadioButton staff_present_in_ICU_yes;

    @BindView(R.id.staff_present_in_ICU_no)
    RadioButton staff_present_in_ICU_no;

    @BindView(R.id.remark_staff_present_in_ICU)
    ImageView remark_staff_present_in_ICU;

    @BindView(R.id.nc_staff_present_in_ICU)
    ImageView nc_staff_present_in_ICU;

    @BindView(R.id.hospital__center)
    TextView hospital__center;


    private String remark1, remark2, remark3,remark4,remark5;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3,nc4,nc5;
    private String radio_status1, radio_status2, radio_status3,radio_status4,radio_status5;

    private DatabaseHandler databaseHandler;

    private APIService mAPIService;

    private File outputVideoFile;

    private String hospital_maintain_cleanliness = "",hospital_adhere_standard ="",hospital_provide_adequate_gloves="",admissions_discharge_home = "",staff_present_in_ICU="";


    private ArrayList<String>admissions_discharge_home_list;
    private ArrayList<String>Local_admissions_discharge_home_list;

    private Ward_Ot_EmergencyPojo pojo;

    private String image1;
    private String Local_image1;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    @BindView(R.id.ll_hospital_adhere_standard)
    LinearLayout ll_hospital_adhere_standard;

    @BindView(R.id.ll_hospital_provide_adequate_gloves)
    LinearLayout ll_hospital_provide_adequate_gloves;

    int Bed_no = 0;
    
    int check;
    CountDownLatch latch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_ot_emergency);

        ButterKnife.bind(this);

        setDrawerbackIcon("Wards,OT,ICU,OPD,Emergency");

        databaseHandler = DatabaseHandler.getInstance(this);

        admissions_discharge_home_list = new ArrayList<>();
        Local_admissions_discharge_home_list = new ArrayList<>();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital__center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        pojo = new Ward_Ot_EmergencyPojo();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_hospital_adhere_standard.setVisibility(View.GONE);
            ll_hospital_provide_adequate_gloves.setVisibility(View.GONE);
        }else {
            ll_hospital_adhere_standard.setVisibility(View.VISIBLE);
            ll_hospital_provide_adequate_gloves.setVisibility(View.VISIBLE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);



        getPharmacyData();

    }


    public void getPharmacyData(){

        pojo = databaseHandler.getWard_EmergencyPojo("9");

        if (pojo != null){
            sql_status = true;
            if (pojo.getHospital_maintain_cleanliness() != null){
                hospital_maintain_cleanliness = pojo.getHospital_maintain_cleanliness();
                if (pojo.getHospital_maintain_cleanliness().equalsIgnoreCase("Yes")){
                    hospital_maintain_cleanliness_yes.setChecked(true);
                }else if (pojo.getHospital_maintain_cleanliness().equalsIgnoreCase("No")){
                    hospital_maintain_cleanliness_no.setChecked(true);
                }
            }
            if (pojo.getHospital_adhere_standard() != null){
                hospital_adhere_standard = pojo.getHospital_adhere_standard();
                if (pojo.getHospital_adhere_standard().equalsIgnoreCase("Yes")){
                    hospital_adhere_standard_yes.setChecked(true);
                }else if (pojo.getHospital_adhere_standard().equalsIgnoreCase("No")){
                    hospital_adhere_standard_no.setChecked(true);
                }
            }
            if (pojo.getHospital_provide_adequate_gloves() != null){
                hospital_provide_adequate_gloves = pojo.getHospital_provide_adequate_gloves();
                if (pojo.getHospital_provide_adequate_gloves().equalsIgnoreCase("Yes")){
                    hospital_provide_adequate_gloves_yes.setChecked(true);
                }else if (pojo.getHospital_provide_adequate_gloves().equalsIgnoreCase("No")){
                    hospital_provide_adequate_gloves_no.setChecked(true);
                }
            } if (pojo.getAdmissions_discharge_home() != null){
                admissions_discharge_home = pojo.getAdmissions_discharge_home();
                if (pojo.getAdmissions_discharge_home().equalsIgnoreCase("Yes")){
                    admissions_discharge_home_yes.setChecked(true);
                }else if (pojo.getAdmissions_discharge_home().equalsIgnoreCase("No")){
                    admissions_discharge_home_no.setChecked(true);
                }
            }
            if (pojo.getStaff_present_in_ICU() != null){
                staff_present_in_ICU = pojo.getStaff_present_in_ICU();
                if (pojo.getStaff_present_in_ICU().equalsIgnoreCase("Yes")){
                    staff_present_in_ICU_yes.setChecked(true);
                }else if (pojo.getStaff_present_in_ICU().equalsIgnoreCase("No")){
                    staff_present_in_ICU_no.setChecked(true);
                }
            }




            if (pojo.getHospital_maintain_cleanliness_remark() != null){
                remark_hospital_maintain_cleanliness.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getHospital_maintain_cleanliness_remark();
            }
            if (pojo.getHospital_maintain_cleanliness_nc() != null){
                nc1 = pojo.getHospital_maintain_cleanliness_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_hospital_maintain_cleanliness.setImageResource(R.mipmap.nc);
                }else {
                    nc_hospital_maintain_cleanliness.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getHospital_adhere_standard_remark() != null){
                remark2 = pojo.getHospital_adhere_standard_remark();

                remark_hospital_adhere_standard.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getHospital_adhere_standard_nc() != null){
                nc2 = pojo.getHospital_adhere_standard_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_hospital_adhere_standard.setImageResource(R.mipmap.nc);
                }else {
                    nc_hospital_adhere_standard.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getHospital_provide_adequate_gloves_remark() != null){
                remark3 = pojo.getHospital_provide_adequate_gloves_remark();

                remark_hospital_provide_adequate_gloves.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getHospital_provide_adequate_gloves_nc() != null){
                nc3 = pojo.getHospital_provide_adequate_gloves_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_hospital_provide_adequate_gloves.setImageResource(R.mipmap.nc);
                }else {
                    nc_hospital_provide_adequate_gloves.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getAdmissions_discharge_home_remark() != null){
                remark4 = pojo.getAdmissions_discharge_home_remark();

                remark_admissions_discharge_home.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getAdmissions_discharge_home_nc() != null){
                nc4 = pojo.getAdmissions_discharge_home_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_admissions_discharge_home.setImageResource(R.mipmap.nc);
                }else {
                    nc_admissions_discharge_home.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getAdmissions_discharge_home_image() != null){
                Video_admissions_discharge_home.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getAdmissions_discharge_home_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            admissions_discharge_home_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_admissions_discharge_home_image() != null){
                Video_admissions_discharge_home.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_admissions_discharge_home_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_admissions_discharge_home_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getStaff_present_in_ICU_remark() != null){
                remark5 = pojo.getStaff_present_in_ICU_remark();

                remark_staff_present_in_ICU.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getStaff_present_in_ICU_nc() != null){
                nc5 = pojo.getStaff_present_in_ICU_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_staff_present_in_ICU.setImageResource(R.mipmap.nc);
                }else {
                    nc_staff_present_in_ICU.setImageResource(R.mipmap.nc_selected);
                }



            }


        }else {
            pojo = new Ward_Ot_EmergencyPojo();
        }

    }


    @OnClick({R.id.remark_hospital_maintain_cleanliness,R.id.nc_hospital_maintain_cleanliness,R.id.remark_hospital_adhere_standard,R.id.nc_hospital_adhere_standard,R.id.remark_hospital_provide_adequate_gloves,R.id.nc_hospital_provide_adequate_gloves,
            R.id.remark_admissions_discharge_home,R.id.nc_admissions_discharge_home,R.id.remark_staff_present_in_ICU,
            R.id.nc_staff_present_in_ICU,R.id.Video_admissions_discharge_home,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_hospital_maintain_cleanliness:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_hospital_maintain_cleanliness:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_hospital_adhere_standard:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_hospital_adhere_standard:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_hospital_provide_adequate_gloves:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_hospital_provide_adequate_gloves:
                displayNCDialog("NC", 3);
                break;


            case R.id.remark_admissions_discharge_home:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_admissions_discharge_home:
                displayNCDialog("NC", 4);
                break;


            case R.id.Video_admissions_discharge_home:
                if (Local_admissions_discharge_home_list.size() > 0){
                    showImageListDialog(Local_admissions_discharge_home_list,1,"admissions_discharge_home");
                }else {
                    captureImage(1);
                }

                break;

            case R.id.remark_staff_present_in_ICU:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_staff_present_in_ICU:
                displayNCDialog("NC", 5);
                break;

            case R.id.btnSave:
                SavePharmacyData("save","");
                break;

            case R.id.btnSync:

                if (Bed_no < 51){
                    if (hospital_maintain_cleanliness.length() > 0 && admissions_discharge_home.length() > 0 && staff_present_in_ICU.length() > 0 ){
                        if (Local_image1 != null){
                            SavePharmacyData("sync","shco");
                        }else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                    }

                }else {

                    if (hospital_maintain_cleanliness.length() > 0 && hospital_adhere_standard.length() > 0 && hospital_provide_adequate_gloves.length() > 0 && admissions_discharge_home.length() > 0 && staff_present_in_ICU.length() > 0 ){
                        if (Local_image1 != null){
                            SavePharmacyData("sync","hco");
                        }else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

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
            case R.id.hospital_maintain_cleanliness_yes:
                if (checked)
                    hospital_maintain_cleanliness = "Yes";
                break;

            case R.id.hospital_maintain_cleanliness_no:
                if (checked)
                    hospital_maintain_cleanliness = "No";
                break;

            case R.id.hospital_adhere_standard_yes:
                hospital_adhere_standard = "Yes";
                break;

            case R.id.hospital_adhere_standard_no:
                hospital_adhere_standard = "No";
                break;

            case R.id.hospital_provide_adequate_gloves_yes:
                if (checked)
                    hospital_provide_adequate_gloves = "Yes";
                break;
            case R.id.hospital_provide_adequate_gloves_no:
                if (checked)
                    hospital_provide_adequate_gloves = "No";
                break;


            case R.id.admissions_discharge_home_yes:
                if (checked)
                    admissions_discharge_home = "Yes";
                break;
            case R.id.admissions_discharge_home_no:
                if (checked)
                    admissions_discharge_home = "No";
                break;

            case R.id.staff_present_in_ICU_yes:
                staff_present_in_ICU = "Yes";
                break;

            case R.id.staff_present_in_ICU_no:
                staff_present_in_ICU = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(Ward_OT_EmergencyActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    SaveImage(image2,"admissions_discharge_home");

                }

            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(Ward_OT_EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_hospital_maintain_cleanliness.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_hospital_maintain_cleanliness.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_hospital_adhere_standard.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_hospital_adhere_standard.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_hospital_provide_adequate_gloves.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_hospital_provide_adequate_gloves.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_admissions_discharge_home.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_admissions_discharge_home.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")){
                                nc_staff_present_in_ICU.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_staff_present_in_ICU.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(Ward_OT_EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_hospital_maintain_cleanliness.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_hospital_adhere_standard.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_hospital_provide_adequate_gloves.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_admissions_discharge_home.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_staff_present_in_ICU.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }


                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(Ward_OT_EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(Ward_OT_EmergencyActivity.this,list,from,"Ward_Emergency");
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
                    Toast toast = Toast.makeText(Ward_OT_EmergencyActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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
        pojo.setHospital_id(9);
        if (getFromPrefs("WardsEmergency_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("WardsEmergency_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }
        pojo.setHospital_maintain_cleanliness(hospital_maintain_cleanliness);
        pojo.setHospital_adhere_standard(hospital_adhere_standard);
        pojo.setHospital_provide_adequate_gloves(hospital_provide_adequate_gloves);
        pojo.setAdmissions_discharge_home(admissions_discharge_home);
        pojo.setStaff_present_in_ICU(staff_present_in_ICU);



        pojo.setHospital_maintain_cleanliness_remark(remark1);
        pojo.setHospital_maintain_cleanliness_nc(nc1);

        pojo.setHospital_adhere_standard_remark(remark2);
        pojo.setHospital_adhere_standard_nc(nc2);


        pojo.setHospital_provide_adequate_gloves_remark(remark3);
        pojo.setHospital_provide_adequate_gloves_nc(nc3);



        pojo.setAdmissions_discharge_home_remark(remark4);
        pojo.setAdmissions_discharge_home_nc(nc4);

        JSONObject json = new JSONObject();

        if (admissions_discharge_home_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(admissions_discharge_home_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_admissions_discharge_home_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_admissions_discharge_home_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }

        pojo.setAdmissions_discharge_home_image(image1);
        pojo.setLocal_admissions_discharge_home_image(Local_image1);


        pojo.setStaff_present_in_ICU_remark(remark5);
        pojo.setStaff_present_in_ICU_nc(nc5);

        if (sql_status){
            boolean sp_status = databaseHandler.UPDATE_Ward_Ot_Emergency(pojo);

            if (sp_status){
                if (!from.equalsIgnoreCase("sync")){

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(9).getHospital_id());
                    pojo.setAssessement_name("Wards, OT, ICU, OPD, Emergency");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(9).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(Ward_OT_EmergencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Ward_OT_EmergencyActivity.this,HospitalListActivity.class);
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
            boolean sp_status = databaseHandler.INSERT_Ward_Ot_Emergency(pojo);
            if (sp_status){
                if (!from.equalsIgnoreCase("sync")){

                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(9).getHospital_id());
                    pojo.setAssessement_name("Wards, OT, ICU, OPD, Emergency");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(9).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(Ward_OT_EmergencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Ward_OT_EmergencyActivity.this,HospitalListActivity.class);
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

    private void PostLaboratoryData(){
        check = 1;
        for(int i=admissions_discharge_home_list.size(); i<Local_admissions_discharge_home_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_admissions_discharge_home_list.get(i) + "admissions_discharge_home");
            UploadImage(Local_admissions_discharge_home_list.get(i),"admissions_discharge_home");
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
                        Toast.makeText(Ward_OT_EmergencyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if (hospital_maintain_cleanliness.length() > 0 && hospital_adhere_standard.length() > 0 && hospital_provide_adequate_gloves.length() > 0 && admissions_discharge_home.length() > 0 && staff_present_in_ICU.length() > 0 ){

            if (image1 != null){
                pojo_dataSync.setTabName("wardsotemergency");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                String admissions_discharge_home_view = "";

                for (int i=0;i<admissions_discharge_home_list.size();i++){
                    String value = admissions_discharge_home_list.get(i);

                    admissions_discharge_home_view = value + admissions_discharge_home_view;
                }

                pojo.setAdmissions_discharge_home_image(admissions_discharge_home_view);

                pojo_dataSync.setWardOtEmergency(pojo);

                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Ward_OT_EmergencyActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(Ward_OT_EmergencyActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(9).getHospital_id());
                                    pojo.setAssessement_name("Wards, OT, ICU, OPD, Emergency");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(9).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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
            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                    }
                });
            }

        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void Post_SHCO_LaboratoryData(){
        check = 1;
        for(int i=admissions_discharge_home_list.size(); i<Local_admissions_discharge_home_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_admissions_discharge_home_list.get(i) + "admissions_discharge_home");
            UploadImage(Local_admissions_discharge_home_list.get(i),"admissions_discharge_home");
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
                        Toast.makeText(Ward_OT_EmergencyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("wardsotemergency");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                String admissions_discharge_home_view = "";

                for (int i=0;i<admissions_discharge_home_list.size();i++){
                    String value = admissions_discharge_home_list.get(i);

                    admissions_discharge_home_view = value + admissions_discharge_home_view;
                }

                pojo.setAdmissions_discharge_home_image(admissions_discharge_home_view);

                pojo_dataSync.setWardOtEmergency(pojo);
                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Ward_OT_EmergencyActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(Ward_OT_EmergencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(Ward_OT_EmergencyActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));


                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(9).getHospital_id());
                                    pojo.setAssessement_name("Wards, OT, ICU, OPD, Emergency");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(9).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Ward_OT_EmergencyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("admissions_discharge_home")){
            //admissions_discharge_home_list.add(response.body().getMessage());
            Local_admissions_discharge_home_list.add(image_path);
            Video_admissions_discharge_home.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "admissions_discharge_home";

        }


        Toast.makeText(Ward_OT_EmergencyActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();
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
                            Intent intent = new Intent(Ward_OT_EmergencyActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(Ward_OT_EmergencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("admissions_discharge_home")){
                                admissions_discharge_home_list.add(response.body().getMessage());
                                //Local_admissions_discharge_home_list.add(image_path);
                                Video_admissions_discharge_home.setImageResource(R.mipmap.camera_selected);

                                image1 = "admissions_discharge_home";

                            }

                            check  = 1;
                            latch.countDown();
                            //Toast.makeText(Ward_OT_EmergencyActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check  = 0;
                            latch.countDown();
                            //Toast.makeText(Ward_OT_EmergencyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check  = 0;
                        latch.countDown();
                        //Toast.makeText(Ward_OT_EmergencyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                check  = 0;
                latch.countDown();
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
            if (from.equalsIgnoreCase("admissions_discharge_home")){
                Local_admissions_discharge_home_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_admissions_discharge_home_list.size() == 0){
                    Video_admissions_discharge_home.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(9).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save","");
        }else {
            Intent intent = new Intent(Ward_OT_EmergencyActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
