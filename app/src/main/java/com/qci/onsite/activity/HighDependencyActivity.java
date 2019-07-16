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
import com.qci.onsite.pojo.HighDependencyPojo;
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
 * Created by Ankit on 08-02-2019.
 */

public class HighDependencyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.high_patient_care_yes)
    RadioButton high_patient_care_yes;

    @BindView(R.id.high_patient_care_no)
    RadioButton high_patient_care_no;

    @BindView(R.id.remark_patient_care)
    ImageView remark_patient_care;

    @BindView(R.id.nc_patient_care)
    ImageView nc_patient_care;

    @BindView(R.id.video_patient_care)
    ImageView video_patient_care;

    @BindView(R.id.high_Are_staff_yes)
    RadioButton high_Are_staff_yes;

    @BindView(R.id.high_Are_staff_no)
    RadioButton high_Are_staff_no;

    @BindView(R.id.remark_Are_staff)
    ImageView remark_Are_staff;

    @BindView(R.id.nc_Are_staff)
    ImageView nc_Are_staff;

    @BindView(R.id.image_Are_staff)
    ImageView image_Are_staff;

    @BindView(R.id.high_do_high_yes)
    RadioButton high_do_high_yes;

    @BindView(R.id.high_do_high_no)
    RadioButton high_do_high_no;

    @BindView(R.id.remark_do_high)
    ImageView remark_do_high;

    @BindView(R.id.nc_do_high)
    ImageView nc_do_high;

    @BindView(R.id.image_do_high)
    ImageView image_do_high;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    private String remark1, remark2, remark3;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3;
    private String radio_status1, radio_status2, radio_status3;

    private DatabaseHandler databaseHandler;

    private String video1,image2,image3;
    private String Local_video1,Local_image2,Local_image3;

    private File outputVideoFile;

    Uri videoUri;

    private String high_patient_care = "",high_Are_staff="",high_do_high = "";

    private ArrayList<String>AreStaff_imageList;
    private ArrayList<String>Local_AreStaff_imageList;
    private ArrayList<String>DoHigh_imageList;
    private ArrayList<String>Local_DoHigh_imageList;

    private HighDependencyPojo pojo;

    private APIService mAPIService;

    public Boolean sql_status = false;

    DataSyncRequest pojo_dataSync;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    private String AreStaff = "",DoHigh = "";

    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_dependency);

        ButterKnife.bind(this);

        setDrawerbackIcon("High dependency Areas");

        mAPIService = ApiUtils.getAPIService();


        AreStaff_imageList = new ArrayList<>();
        Local_AreStaff_imageList = new ArrayList<>();
        DoHigh_imageList = new ArrayList<>();
        Local_DoHigh_imageList = new ArrayList<>();

        databaseHandler = DatabaseHandler.getInstance(this);

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        pojo = new HighDependencyPojo();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getHighDeppendencyData();
    }

    public void getHighDeppendencyData(){

        pojo = databaseHandler.getHighDependency("4");

        if (pojo != null){
            sql_status = true;
            if (pojo.getHIGH_DEPENDENCY_documented_procedure() != null){
                high_patient_care = pojo.getHIGH_DEPENDENCY_documented_procedure();
                if (pojo.getHIGH_DEPENDENCY_documented_procedure().equalsIgnoreCase("Yes")){
                    high_patient_care_yes.setChecked(true);
                }else if (pojo.getHIGH_DEPENDENCY_documented_procedure().equalsIgnoreCase("No")){
                    high_patient_care_no.setChecked(true);
                }
            }
            if (pojo.getHIGH_DEPENDENCY_Areas_adequate() != null){
                high_Are_staff = pojo.getHIGH_DEPENDENCY_Areas_adequate();
                if (pojo.getHIGH_DEPENDENCY_Areas_adequate().equalsIgnoreCase("Yes")){
                    high_Are_staff_yes.setChecked(true);
                }else if (pojo.getHIGH_DEPENDENCY_Areas_adequate().equalsIgnoreCase("No")){
                    high_Are_staff_no.setChecked(true);
                }
            } if (pojo.getHIGH_DEPENDENCY_adequate_equipment() != null){
                high_do_high = pojo.getHIGH_DEPENDENCY_adequate_equipment();
                if (pojo.getHIGH_DEPENDENCY_adequate_equipment().equalsIgnoreCase("Yes")){
                    high_do_high_yes.setChecked(true);
                }else if (pojo.getHIGH_DEPENDENCY_adequate_equipment().equalsIgnoreCase("No")){
                    high_do_high_no.setChecked(true);
                }
            }
            if (pojo.getHIGH_DEPENDENCY_documented_procedure_remark() != null){
                remark_patient_care.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getHIGH_DEPENDENCY_documented_procedure_remark();
            }
            if (pojo.getHIGH_DEPENDENCY_documented_procedure_NC() != null){
                nc1 = pojo.getHIGH_DEPENDENCY_documented_procedure_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_patient_care.setImageResource(R.mipmap.nc);
                }else {
                    nc_patient_care.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getHIGH_DEPENDENCY_documented_procedure_video() != null){
                video_patient_care.setImageResource(R.mipmap.camera_selected);
                video1 = pojo.getHIGH_DEPENDENCY_documented_procedure_video();
            }

            if (pojo.getLocal_HIGH_DEPENDENCY_documented_procedure_video() != null){
                video_patient_care.setImageResource(R.mipmap.camera_selected);
                Local_video1 = pojo.getLocal_HIGH_DEPENDENCY_documented_procedure_video();
            }

            if (pojo.getHIGH_DEPENDENCY_Areas_adequate_remark() != null){
                remark2 = pojo.getHIGH_DEPENDENCY_Areas_adequate_remark();

                remark_Are_staff.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getHIGH_DEPENDENCY_Areas_adequate_NC() != null){
                nc2 = pojo.getHIGH_DEPENDENCY_Areas_adequate_NC();

                if (nc2.equalsIgnoreCase("close")){
                    nc_Are_staff.setImageResource(R.mipmap.nc);
                }else {
                    nc_Are_staff.setImageResource(R.mipmap.nc_selected);
                }

            }
            if (pojo.getHIGH_DEPENDENCY_Areas_adequate_image() != null){
                image_Are_staff.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getHIGH_DEPENDENCY_Areas_adequate_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            AreStaff_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getLocal_HIGH_DEPENDENCY_Areas_adequate_image() != null){
                image_Are_staff.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_HIGH_DEPENDENCY_Areas_adequate_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_AreStaff_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (pojo.getHIGH_DEPENDENCY_adequate_equipment_remark() != null){
                remark3 = pojo.getHIGH_DEPENDENCY_adequate_equipment_remark();

                remark_do_high.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getHIGH_DEPENDENCY_adequate_equipment_Nc() != null){
                nc3 = pojo.getHIGH_DEPENDENCY_adequate_equipment_Nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_do_high.setImageResource(R.mipmap.nc);
                }else {
                    nc_do_high.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getHIGH_DEPENDENCY_adequate_equipment_image() != null){
                image_do_high.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getHIGH_DEPENDENCY_adequate_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            DoHigh_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_HIGH_DEPENDENCY_adequate_equipment_image() != null){
                image_do_high.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_HIGH_DEPENDENCY_adequate_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image3);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_DoHigh_imageList.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else {
            pojo = new HighDependencyPojo();
        }

    }


    @OnClick({R.id.remark_patient_care,R.id.video_patient_care,R.id.remark_Are_staff,R.id.image_Are_staff,R.id.remark_do_high,
            R.id.image_do_high,R.id.nc_patient_care,R.id.nc_Are_staff,R.id.nc_do_high,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_patient_care:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_patient_care:
                displayNCDialog("NC", 1);
                break;

            case R.id.video_patient_care:
                if (Local_video1 != null){
                    showVideoDialog(Local_video1);
                }else {
                    captureVideo();
                }

                break;

            case R.id.remark_Are_staff:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_Are_staff:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_Are_staff:
                if (Local_AreStaff_imageList.size() > 0){
                    showImageListDialog(Local_AreStaff_imageList,2,"Are_staff");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_do_high:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_do_high:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_do_high:
                if (Local_DoHigh_imageList.size() > 0){
                    showImageListDialog(Local_DoHigh_imageList,3,"do_high");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.btnSave:
                SaveLaboratoryData("save");
                break;

            case R.id.btnSync:

                if (high_patient_care.length() > 0 && high_Are_staff.length() > 0 && high_do_high.length() > 0){
                    if (Local_image2 != null && Local_image3 != null){
                        SaveLaboratoryData("sync");
                    }else {
                        Toast.makeText(HighDependencyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(HighDependencyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }

                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.high_patient_care_yes:
                if (checked)
                    high_patient_care = "Yes";
                break;

            case R.id.high_patient_care_no:
                if (checked)
                    high_patient_care = "No";
                break;

            case R.id.high_Are_staff_yes:
                if (checked)
                    high_Are_staff = "Yes";
                break;
            case R.id.high_Are_staff_no:
                if (checked)
                    high_Are_staff = "No";
                break;


            case R.id.high_do_high_yes:
                if (checked)
                    high_do_high = "Yes";
                break;
            case R.id.high_do_high_no:
                if (checked)
                    high_do_high = "No";
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HighDependencyActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HighDependencyActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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


                    SaveImage(image2,"Are_staff");

                }

            }
            else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                    SaveImage(image3,"do_high");

                }

            }
        }
    }

    private void VideoUpload(final String image_path){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(HighDependencyActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(HighDependencyActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();


                             video1 = response.body().getMessage();
                             Local_video1 = image_path;
                             video_patient_care.setImageResource(R.mipmap.camera_selected);



                    }else {
                        Toast.makeText(HighDependencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HighDependencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(HighDependencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(HighDependencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_patient_care.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_patient_care.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HighDependencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_Are_staff.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_Are_staff.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HighDependencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")) {
                                nc_do_high.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_do_high.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HighDependencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(HighDependencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_patient_care.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HighDependencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_Are_staff.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HighDependencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_do_high.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HighDependencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(HighDependencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(HighDependencyActivity.this,list,from,"HighDependency");
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
                    Toast toast = Toast.makeText(HighDependencyActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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



    public void SaveLaboratoryData(String from){
        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(4);
        if (getFromPrefs("HighDependency_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("HighDependency_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }
        pojo.setHIGH_DEPENDENCY_documented_procedure(high_patient_care);
        pojo.setHIGH_DEPENDENCY_Areas_adequate(high_Are_staff);
        pojo.setHIGH_DEPENDENCY_adequate_equipment(high_do_high);


        pojo.setHIGH_DEPENDENCY_documented_procedure_remark(remark1);
        pojo.setHIGH_DEPENDENCY_documented_procedure_NC(nc1);
        pojo.setHIGH_DEPENDENCY_documented_procedure_video(video1);
        pojo.setLocal_HIGH_DEPENDENCY_documented_procedure_video(Local_video1);

        JSONObject json = new JSONObject();

        if (AreStaff_imageList.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(AreStaff_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_AreStaff_imageList.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_AreStaff_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }


        pojo.setHIGH_DEPENDENCY_Areas_adequate_remark(remark2);
        pojo.setHIGH_DEPENDENCY_Areas_adequate_NC(nc2);
        pojo.setHIGH_DEPENDENCY_Areas_adequate_image(image2);
        pojo.setLocal_HIGH_DEPENDENCY_Areas_adequate_image(Local_image2);

        if (DoHigh_imageList.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(DoHigh_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image3 = json.toString();
        }else {
            image3 = null;
        }

        if (Local_DoHigh_imageList.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_DoHigh_imageList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image3 = json.toString();
        }else {
            Local_image3 = null;
        }

        pojo.setHIGH_DEPENDENCY_adequate_equipment_remark(remark3);
        pojo.setHIGH_DEPENDENCY_adequate_equipment_Nc(nc3);
        pojo.setHIGH_DEPENDENCY_adequate_equipment_image(image3);
        pojo.setLocal_HIGH_DEPENDENCY_adequate_equipment_image(Local_image3);

        if (sql_status){
            boolean sqlite_status = databaseHandler.UPDATE_HighDependency(pojo);

            if (sqlite_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(4).getHospital_id());
                    pojo.setAssessement_name("High dependency Areas");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(4).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(HighDependencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HighDependencyActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    new PostDataTask().execute();
                }
            }
        }else {
            boolean sqlit_status = databaseHandler.INSERT_HighDependency(pojo);

            if (sqlit_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(4).getHospital_id());
                    pojo.setAssessement_name("High dependency Areas");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(4).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(HighDependencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HighDependencyActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    new PostDataTask().execute();
                }
            }

        }
    }
    public void showVideoDialog(final String path) {
        dialogLogout = new Dialog(HighDependencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HighDependencyActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
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
                            Intent intent = new Intent(HighDependencyActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(HighDependencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("Are_staff")){
                                AreStaff_imageList.add(response.body().getMessage());
                                //Local_AreStaff_imageList.add(image_path);
                                image_Are_staff.setImageResource(R.mipmap.camera_selected);

                                image2 = "Are_staff";

                            }else if (from.equalsIgnoreCase("do_high")){

                                DoHigh_imageList.add(response.body().getMessage());
                                //Local_DoHigh_imageList.add(image_path);
                                image_do_high.setImageResource(R.mipmap.camera_selected);

                                image3 = "do_high";
                            }

                            check = 1;
                            latch.countDown();
                            //Toast.makeText(HighDependencyActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(HighDependencyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(HighDependencyActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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

        if (from.equalsIgnoreCase("Are_staff")){
            Local_AreStaff_imageList.add(image_path);
            image_Are_staff.setImageResource(R.mipmap.camera_selected);

            Local_image2 = "Are_staff";

        }else if (from.equalsIgnoreCase("do_high")){

            Local_DoHigh_imageList.add(image_path);
            image_do_high.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "do_high";
        }


        Toast.makeText(HighDependencyActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();
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
        for(int i=AreStaff_imageList.size(); i<Local_AreStaff_imageList.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_AreStaff_imageList.get(i) + "Are_staff");
            UploadImage(Local_AreStaff_imageList.get(i),"Are_staff");
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
                        Toast.makeText(HighDependencyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HighDependencyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = DoHigh_imageList.size(); i< Local_DoHigh_imageList.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_DoHigh_imageList.get(i)+ "do_high");
            UploadImage(Local_DoHigh_imageList.get(i),"do_high");
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
                        Toast.makeText(HighDependencyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HighDependencyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("highdependency");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<AreStaff_imageList.size();i++){
                    String value_rail = AreStaff_imageList.get(i);

                    AreStaff = value_rail + AreStaff;
                }
                pojo.setHIGH_DEPENDENCY_Areas_adequate_image(AreStaff);

                for (int i=0;i<DoHigh_imageList.size();i++){
                    String value_transported = DoHigh_imageList.get(i);

                    DoHigh = value_transported + DoHigh;
                }

                pojo.setHIGH_DEPENDENCY_adequate_equipment_image(DoHigh);

                pojo_dataSync.setHighDependency(pojo);

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
                                    Intent intent = new Intent(HighDependencyActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(HighDependencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(HighDependencyActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    saveIntoPrefs("HighDependency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(4).getHospital_id());
                                    pojo.setAssessement_name("High dependency Areas");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(4).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(HighDependencyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private void DeleteList(int position,String from){
        try {
            if (from.equalsIgnoreCase("Are_staff")){
                Local_AreStaff_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_AreStaff_imageList.size() == 0){
                    image_Are_staff.setImageResource(R.mipmap.camera);

                    Local_image2 = null;

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("do_high")){
                Local_DoHigh_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_DoHigh_imageList.size() == 0){
                    image_do_high.setImageResource(R.mipmap.camera);

                    Local_image3 = null;

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

        if (!assessement_list.get(4).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("save");
        }else {
            Intent intent = new Intent(HighDependencyActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
