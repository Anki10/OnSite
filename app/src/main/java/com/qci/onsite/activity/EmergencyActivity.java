package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import com.qci.onsite.pojo.EmergencyPojo;
import com.qci.onsite.pojo.HighDependencyPojo;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.RadioLogyPojo;
import com.qci.onsite.pojo.VideoDialog;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
 * Created by Ankit on 07-02-2019.
 */

public class EmergencyActivity extends BaseActivity {

    @BindView(R.id.emergency_yes)
    RadioButton emergency_yes;

    @BindView(R.id.emergency_no)
    RadioButton emergency_no;

    @BindView(R.id.remark_emergency)
    ImageView remark_emergency;

    @BindView(R.id.nc_emergency)
    ImageView nc_emergency;

    @BindView(R.id.video_emergency)
    ImageView video_emergency;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    private String remark1;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String nc1;
    private String radiology_status="";
    private String radio_status1;

    private DatabaseHandler databaseHandler;

    private String video1,Local_video1;

    private EmergencyPojo pojo;

    private APIService mAPIService;


    private File outputVideoFile;

    Uri videoUri;

    public Boolean sql_status = false;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ButterKnife.bind(this);

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        mAPIService = ApiUtils.getAPIService();

        setDrawerbackIcon("Emergency");

        databaseHandler = DatabaseHandler.getInstance(this);

        pojo_dataSync = new DataSyncRequest();

        pojo = new EmergencyPojo();

        assessement_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getEmergencyData();


      }

    public void getEmergencyData(){

        pojo = databaseHandler.getEmergencyPojo("3");

        if (pojo != null){
            sql_status = true;
            if (pojo.getEMERGENCY_down_Norms() != null){
                radiology_status = pojo.getEMERGENCY_down_Norms();
                if (pojo.getEMERGENCY_down_Norms().equalsIgnoreCase("Yes")){
                    emergency_yes.setChecked(true);
                }else if (pojo.getEMERGENCY_down_Norms().equalsIgnoreCase("No")){
                    emergency_no.setChecked(true);
                }
            }

            if (pojo.getEMERGENCY_down_Norms_remark() != null){
                remark_emergency.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getEMERGENCY_down_Norms_remark();
            }
            if (pojo.getEMERGENCY_down_Norms_NC() != null){
                nc1 = pojo.getEMERGENCY_down_Norms_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_emergency.setImageResource(R.mipmap.nc);
                }else {
                    nc_emergency.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getEMERGENCY_down_Norms_video() != null){
                video_emergency.setImageResource(R.mipmap.camera_selected);
                video1 = pojo.getEMERGENCY_down_Norms_video();
            }

            if (pojo.getLocal_EMERGENCY_down_Norms_video() != null){
                video_emergency.setImageResource(R.mipmap.camera_selected);
                Local_video1 = pojo.getLocal_EMERGENCY_down_Norms_video();
            }

        }else {
            pojo = new EmergencyPojo();
        }

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.emergency_yes:
                if (checked)
                    radiology_status = "Yes";
                break;

            case R.id.emergency_no:
                if (checked)
                    radiology_status = "No";
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(EmergencyActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, 1);
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
            }

        }
    }

    private void VideoUpload(String image_path){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(EmergencyActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(EmergencyActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();

                        video1 = response.body().getMessage();
                        Local_video1 = String.valueOf(outputVideoFile);
                        video_emergency.setImageResource(R.mipmap.camera_selected);

                    }else {
                        Toast.makeText(EmergencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(EmergencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(EmergencyActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }


    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                }
            });
            rd_miner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 1) {
                        radio_status1 = "close";

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
                                nc_emergency.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_emergency.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(EmergencyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_emergency.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(EmergencyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }

    @OnClick({R.id.remark_emergency,R.id.video_emergency,R.id.nc_emergency,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_emergency:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_emergency:
                displayNCDialog("NC", 1);
                break;

            case R.id.video_emergency:
                if (Local_video1 != null){
                    showVideoDialog(Local_video1);
                }else {
                    captureVideo();
                }
                break;
            case R.id.btnSave:
                SaveEmergencyData("save");
                break;

            case R.id.btnSync:
                if (radiology_status.length() > 0){
                    SaveEmergencyData("sync");
                }else {
                    Toast.makeText(EmergencyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }
                break;

        }
    }
    public void showVideoDialog(final String path) {
        dialogLogout = new Dialog(EmergencyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(EmergencyActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
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

    private void SaveEmergencyData(String from){
         pojo.setHospital_name("Hospital1");
         pojo.setHospital_id(3);
        if (getFromPrefs("Emergency_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("Emergency_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }
         pojo.setEMERGENCY_down_Norms(radiology_status);
         pojo.setEMERGENCY_down_Norms_remark(remark1);
         pojo.setEMERGENCY_down_Norms_NC(nc1);
         pojo.setEMERGENCY_down_Norms_video(video1);
         pojo.setLocal_EMERGENCY_down_Norms_video(Local_video1);

         if (sql_status){
            boolean sqlite_status =  databaseHandler.UPDATE_EMERGENCY(pojo);

            if (sqlite_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(3).getHospital_id());
                    pojo.setAssessement_name("Emergency");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(3).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(EmergencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(EmergencyActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progreesDialog();

                    PostLaboratoryData();
                }

            }
         }else {
             boolean s_status = databaseHandler.INSERT_EMERGENCY(pojo);

             if (s_status){
                 if (!from.equalsIgnoreCase("sync")){
                     assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                     AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                     pojo.setHospital_id(assessement_list.get(3).getHospital_id());
                     pojo.setAssessement_name("Emergency");
                     pojo.setAssessement_status("Draft");
                     pojo.setLocal_id(assessement_list.get(3).getLocal_id());

                     databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                     Toast.makeText(EmergencyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                     Intent intent = new Intent(EmergencyActivity.this,HospitalListActivity.class);
                     startActivity(intent);
                     finish();
                 }else {
                     progreesDialog();

                     PostLaboratoryData();
                 }

             }

         }




    }

    private void PostLaboratoryData(){

            pojo_dataSync.setTabName("emergency");
            pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
            pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
            if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
            }else {
                pojo_dataSync.setAssessment_id(0);
            }
             pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));

            pojo_dataSync.setEmergency(pojo);


            mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                @Override
                public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                    System.out.println("xxx sucess");

                    CloseProgreesDialog();

                    if (response.message().equalsIgnoreCase("Unauthorized")) {
                        Intent intent = new Intent(EmergencyActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        Toast.makeText(EmergencyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                    }else {
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                Intent intent = new Intent(EmergencyActivity.this,HospitalListActivity.class);
                                startActivity(intent);
                                finish();

                                saveIntoPrefs("Emergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                pojo.setHospital_id(assessement_list.get(3).getHospital_id());
                                pojo.setAssessement_name("Emergency");
                                pojo.setAssessement_status("Done");
                                pojo.setLocal_id(assessement_list.get(3).getLocal_id());

                                databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                Toast.makeText(EmergencyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        super.onBackPressed();

        if (!assessement_list.get(3).getAssessement_status().equalsIgnoreCase("Done")){
            SaveEmergencyData("save");
        }else {
            Intent intent = new Intent(EmergencyActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }

    }
    }
