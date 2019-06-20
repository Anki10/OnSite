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
import com.qci.onsite.pojo.HousekeepingPojo;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.MRDPojo;
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

public class HouseKeepingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.infected_patient_room_yes)
    RadioButton infected_patient_room_yes;

    @BindView(R.id.infected_patient_room_no)
    RadioButton infected_patient_room_no;

    @BindView(R.id.remark_infected_patient_room)
    ImageView remark_infected_patient_room;

    @BindView(R.id.nc_infected_patient_room)
    ImageView nc_infected_patient_room;

    @BindView(R.id.procedure_cleaning_room_yes)
    RadioButton procedure_cleaning_room_yes;

    @BindView(R.id.procedure_cleaning_room_no)
    RadioButton procedure_cleaning_room_no;

    @BindView(R.id.remark_procedure_cleaning_room)
    ImageView remark_procedure_cleaning_room;

    @BindView(R.id.nc_procedure_cleaning_room)
    ImageView nc_procedure_cleaning_room;

    @BindView(R.id.procedure_cleaning_blood_spill_yes)
    RadioButton procedure_cleaning_blood_spill_yes;

    @BindView(R.id.procedure_cleaning_blood_spill_no)
    RadioButton procedure_cleaning_blood_spill_no;

    @BindView(R.id.remark_procedure_cleaning_blood_spill)
    ImageView remark_procedure_cleaning_blood_spill;

    @BindView(R.id.nc_procedure_cleaning_blood_spill)
    ImageView nc_procedure_cleaning_blood_spill;

    @BindView(R.id.image_procedure_cleaning_blood_spill)
    ImageView image_procedure_cleaning_blood_spill;

    @BindView(R.id.Biomedical_Waste_regulations_yes)
    RadioButton Biomedical_Waste_regulations_yes;

    @BindView(R.id.Biomedical_Waste_regulations_no)
    RadioButton Biomedical_Waste_regulations_no;

    @BindView(R.id.remark_Biomedical_Waste_regulations)
    ImageView remark_Biomedical_Waste_regulations;

    @BindView(R.id.nc_Biomedical_Waste_regulations)
    ImageView nc_Biomedical_Waste_regulations;

    @BindView(R.id.image_Biomedical_Waste_regulations)
    ImageView image_Biomedical_Waste_regulations;

    @BindView(R.id.cleaning_washing_blood_stained_yes)
    RadioButton cleaning_washing_blood_stained_yes;

    @BindView(R.id.cleaning_washing_blood_stained_no)
    RadioButton cleaning_washing_blood_stained_no;

    @BindView(R.id.remark_cleaning_washing_blood_stained)
    ImageView remark_cleaning_washing_blood_stained;

    @BindView(R.id.nc_cleaning_washing_blood_stained)
    ImageView nc_cleaning_washing_blood_stained;

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

    private String infected_patient_room = "",procedure_cleaning_room ="",procedure_cleaning_blood_spill="",Biomedical_Waste_regulations = "",cleaning_washing_blood_stained="";


    private ArrayList<String>staffs_personal_files_maintained_list;
    private ArrayList<String>Local_staffs_personal_files_maintained_list;


    private ArrayList<String>procedure_cleaning_blood_spill_list;
    private ArrayList<String>Local_procedure_cleaning_blood_spill_list;

    private HousekeepingPojo pojo;

    private String image1,image2;
    private String Local_image1,Local_image2;

    private File outputVideoFile;

    Uri videoUri;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    String admissions_discharge_home_view = "",procedure_cleaning_blood_spill_view = "";


    DataSyncRequest pojo_dataSync;
    
    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housekeeping);

        ButterKnife.bind(this);

        setDrawerbackIcon("Housekeeping");

        databaseHandler = DatabaseHandler.getInstance(this);

        staffs_personal_files_maintained_list = new ArrayList<>();
        Local_staffs_personal_files_maintained_list = new ArrayList<>();

        procedure_cleaning_blood_spill_list = new ArrayList<>();
        Local_procedure_cleaning_blood_spill_list = new ArrayList<>();



        pojo_dataSync = new DataSyncRequest();

        pojo = new HousekeepingPojo();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();

    }

    public void getPharmacyData(){

        pojo = databaseHandler.getHouseKeeping("12");

        if (pojo != null){
            sql_status = true;
        if (pojo.getInfected_patient_room() != null){
            infected_patient_room = pojo.getInfected_patient_room();
                if (pojo.getInfected_patient_room().equalsIgnoreCase("Yes")){
                    infected_patient_room_yes.setChecked(true);
                }else if (pojo.getInfected_patient_room().equalsIgnoreCase("No")){
                    infected_patient_room_no.setChecked(true);
                }
            }
            if (pojo.getProcedure_cleaning_room() != null){
                procedure_cleaning_room = pojo.getProcedure_cleaning_room();
                if (pojo.getProcedure_cleaning_room().equalsIgnoreCase("Yes")){
                    procedure_cleaning_room_yes.setChecked(true);
                }else if (pojo.getProcedure_cleaning_room().equalsIgnoreCase("No")){
                    procedure_cleaning_room_no.setChecked(true);
                }
            }
            if (pojo.getProcedure_cleaning_blood_spill() != null){
                procedure_cleaning_blood_spill = pojo.getProcedure_cleaning_blood_spill();
                if (pojo.getProcedure_cleaning_blood_spill().equalsIgnoreCase("Yes")){
                    procedure_cleaning_blood_spill_yes.setChecked(true);
                }else if (pojo.getProcedure_cleaning_blood_spill().equalsIgnoreCase("No")){
                    procedure_cleaning_blood_spill_no.setChecked(true);
                }
            } if (pojo.getBiomedical_Waste_regulations() != null){
                Biomedical_Waste_regulations = pojo.getBiomedical_Waste_regulations();
                if (pojo.getBiomedical_Waste_regulations().equalsIgnoreCase("Yes")){
                    Biomedical_Waste_regulations_yes.setChecked(true);
                }else if (pojo.getBiomedical_Waste_regulations().equalsIgnoreCase("No")){
                    Biomedical_Waste_regulations_no.setChecked(true);
                }
            }
            if (pojo.getCleaning_washing_blood_stained() != null){
                cleaning_washing_blood_stained = pojo.getCleaning_washing_blood_stained();
                if (pojo.getCleaning_washing_blood_stained().equalsIgnoreCase("Yes")){
                    cleaning_washing_blood_stained_yes.setChecked(true);
                }else if (pojo.getCleaning_washing_blood_stained().equalsIgnoreCase("No")){
                    cleaning_washing_blood_stained_no.setChecked(true);
                }
            }




            if (pojo.getInfected_patient_room_remark() != null){
                remark_infected_patient_room.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getInfected_patient_room_remark();
            }
            if (pojo.getInfected_patient_room_nc() != null){
                nc1 = pojo.getInfected_patient_room_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_infected_patient_room.setImageResource(R.mipmap.nc);
                }else {
                    nc_infected_patient_room.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getProcedure_cleaning_room_remark() != null){
                remark2 = pojo.getProcedure_cleaning_room_remark();

                remark_procedure_cleaning_room.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getProcedure_cleaning_room_nc() != null){
                nc2 = pojo.getProcedure_cleaning_room_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_procedure_cleaning_room.setImageResource(R.mipmap.nc);
                }else {
                    nc_procedure_cleaning_room.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getProcedure_cleaning_blood_spill_remark() != null){
                remark3 = pojo.getProcedure_cleaning_blood_spill_remark();

                remark_procedure_cleaning_blood_spill.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getProcedure_cleaning_blood_spill_nc() != null){
                nc3 = pojo.getProcedure_cleaning_blood_spill_nc();


                if (nc3.equalsIgnoreCase("close")){
                    nc_procedure_cleaning_blood_spill.setImageResource(R.mipmap.nc);
                }else {
                    nc_procedure_cleaning_blood_spill.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getProcedure_cleaning_blood_spill_video() != null){
                image_procedure_cleaning_blood_spill.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getProcedure_cleaning_blood_spill_video();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            procedure_cleaning_blood_spill_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_procedure_cleaning_blood_spill_video() != null){

                Local_image2 = pojo.getLocal_procedure_cleaning_blood_spill_video();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_procedure_cleaning_blood_spill_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getBiomedical_Waste_regulations_remark() != null){
                remark4 = pojo.getBiomedical_Waste_regulations_remark();

                remark_Biomedical_Waste_regulations.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getBiomedical_Waste_regulations_nc() != null){
                nc4 = pojo.getBiomedical_Waste_regulations_nc();


                if (nc4.equalsIgnoreCase("close")){
                    nc_Biomedical_Waste_regulations.setImageResource(R.mipmap.nc);
                }else {
                    nc_Biomedical_Waste_regulations.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getCleaning_washing_blood_stained_remark() != null){
                remark5 = pojo.getCleaning_washing_blood_stained_remark();

                remark_cleaning_washing_blood_stained.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getCleaning_washing_blood_stained_nc() != null){
                nc5 = pojo.getCleaning_washing_blood_stained_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_cleaning_washing_blood_stained.setImageResource(R.mipmap.nc);
                }else {
                    nc_cleaning_washing_blood_stained.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getBiomedical_Waste_regulations_image() != null){
                image_Biomedical_Waste_regulations.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getBiomedical_Waste_regulations_image();

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

            if (pojo.getLocal_Biomedical_Waste_regulations_image() != null){
                image_Biomedical_Waste_regulations.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_Biomedical_Waste_regulations_image();

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


        }else {
            pojo = new HousekeepingPojo();
        }

    }


    @OnClick({R.id.remark_infected_patient_room,R.id.nc_infected_patient_room,R.id.remark_procedure_cleaning_room,R.id.nc_procedure_cleaning_room,R.id.remark_procedure_cleaning_blood_spill,R.id.nc_procedure_cleaning_blood_spill,
            R.id.remark_Biomedical_Waste_regulations,R.id.nc_Biomedical_Waste_regulations,R.id.remark_cleaning_washing_blood_stained,
            R.id.nc_cleaning_washing_blood_stained,R.id.image_procedure_cleaning_blood_spill,R.id.image_Biomedical_Waste_regulations,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_infected_patient_room:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_infected_patient_room:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_procedure_cleaning_room:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_procedure_cleaning_room:
                displayNCDialog("NC", 2);
                break;

            case R.id.remark_procedure_cleaning_blood_spill:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_procedure_cleaning_blood_spill:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_procedure_cleaning_blood_spill:
                if (Local_procedure_cleaning_blood_spill_list.size() > 0){
                    showImageListDialog(Local_procedure_cleaning_blood_spill_list,2,"procedure_cleaning_blood_spill");
                }else {
                    captureImage(2);
                }
                break;


            case R.id.remark_Biomedical_Waste_regulations:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_Biomedical_Waste_regulations:
                displayNCDialog("NC", 4);
                break;

            case R.id.image_Biomedical_Waste_regulations:
                if (Local_staffs_personal_files_maintained_list.size() > 0){
                    showImageListDialog(Local_staffs_personal_files_maintained_list,1,"radiology_defined_turnaround");
                }else {
                    captureImage(1);
                }

                break;

            case R.id.remark_cleaning_washing_blood_stained:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_cleaning_washing_blood_stained:
                displayNCDialog("NC", 5);
                break;

            case R.id.btnSave:
                SavePharmacyData("save");
                break;

            case R.id.btnSync:
                if (infected_patient_room.length() > 0 && procedure_cleaning_room.length() > 0 && procedure_cleaning_blood_spill.length() > 0 && Biomedical_Waste_regulations.length() > 0 && cleaning_washing_blood_stained.length() > 0){
                    if (Local_image1 != null){
                        SavePharmacyData("sync");
                    }else {
                        Toast.makeText(HouseKeepingActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(HouseKeepingActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }

                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.infected_patient_room_yes:
                if (checked)
                    infected_patient_room = "Yes";
                break;

            case R.id.infected_patient_room_no:
                if (checked)
                    infected_patient_room = "No";
                break;

            case R.id.procedure_cleaning_room_yes:
                procedure_cleaning_room = "Yes";
                break;

            case R.id.procedure_cleaning_room_no:
                procedure_cleaning_room = "No";
                break;

            case R.id.procedure_cleaning_blood_spill_yes:
                if (checked)
                    procedure_cleaning_blood_spill = "Yes";
                break;
            case R.id.procedure_cleaning_blood_spill_no:
                if (checked)
                    procedure_cleaning_blood_spill = "No";
                break;


            case R.id.Biomedical_Waste_regulations_yes:
                if (checked)
                    Biomedical_Waste_regulations = "Yes";
                break;
            case R.id.Biomedical_Waste_regulations_no:
                if (checked)
                    Biomedical_Waste_regulations = "No";
                break;

            case R.id.cleaning_washing_blood_stained_yes:
                cleaning_washing_blood_stained = "Yes";
                break;

            case R.id.cleaning_washing_blood_stained_no:
                cleaning_washing_blood_stained = "No";
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HouseKeepingActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
        startActivityForResult(intent, 2);
    }

    public void showVideoDialog(final String path) {
        dialogLogout = new Dialog(HouseKeepingActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HouseKeepingActivity.this, getApplicationContext().getPackageName() + ".provider", outputVideoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20971520L);
                startActivityForResult(intent, 2);
            }
        });

        Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND);
        imageView.setImageBitmap(bmThumbnail);

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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HouseKeepingActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    SaveImage(image2,"Biomedical_Waste_regulations");
                }

            } if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"procedure_cleaning_blood_spill_list");
                }
            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(HouseKeepingActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

                            if (radio_status1.equalsIgnoreCase("close")) {
                                nc_infected_patient_room.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_infected_patient_room.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HouseKeepingActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_procedure_cleaning_room.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_procedure_cleaning_room.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HouseKeepingActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_procedure_cleaning_blood_spill.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_procedure_cleaning_blood_spill.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HouseKeepingActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_Biomedical_Waste_regulations.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_Biomedical_Waste_regulations.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(HouseKeepingActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")) {
                                nc_cleaning_washing_blood_stained.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_cleaning_washing_blood_stained.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(HouseKeepingActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(HouseKeepingActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_infected_patient_room.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HouseKeepingActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_procedure_cleaning_room.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HouseKeepingActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_procedure_cleaning_blood_spill.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HouseKeepingActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_Biomedical_Waste_regulations.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HouseKeepingActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_cleaning_washing_blood_stained.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(HouseKeepingActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }


                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(HouseKeepingActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(HouseKeepingActivity.this,list,from,"HouseKeeping");
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
                    Toast toast = Toast.makeText(HouseKeepingActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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



    public void SavePharmacyData(String from){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(12);

        pojo.setInfected_patient_room(infected_patient_room);
        pojo.setProcedure_cleaning_room(procedure_cleaning_room);
        pojo.setProcedure_cleaning_blood_spill(procedure_cleaning_blood_spill);
        pojo.setBiomedical_Waste_regulations(Biomedical_Waste_regulations);
        pojo.setCleaning_washing_blood_stained(cleaning_washing_blood_stained);



        pojo.setInfected_patient_room_remark(remark1);
        pojo.setInfected_patient_room_nc(nc1);

        pojo.setProcedure_cleaning_room_remark(remark2);
        pojo.setProcedure_cleaning_room_nc(nc2);



        pojo.setProcedure_cleaning_blood_spill_remark(remark3);
        pojo.setProcedure_cleaning_blood_spill_nc(nc3);

        JSONObject json = new JSONObject();

        if (procedure_cleaning_blood_spill_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(procedure_cleaning_blood_spill_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_procedure_cleaning_blood_spill_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_procedure_cleaning_blood_spill_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }

        pojo.setProcedure_cleaning_blood_spill_video(image2);
        pojo.setLocal_procedure_cleaning_blood_spill_video(Local_image2);


        pojo.setBiomedical_Waste_regulations_remark(remark4);
        pojo.setBiomedical_Waste_regulations_nc(nc4);



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

        pojo.setBiomedical_Waste_regulations_image(image1);
        pojo.setLocal_Biomedical_Waste_regulations_image(Local_image1);


        pojo.setCleaning_washing_blood_stained_remark(remark5);
        pojo.setCleaning_washing_blood_stained_nc(nc5);

        if (sql_status){
            boolean se_status = databaseHandler.UPDATE_HOUSEKEEPING(pojo);

            if (se_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(12).getHospital_id());
                    pojo.setAssessement_name("Housekeeping");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(12).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(HouseKeepingActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HouseKeepingActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    new PostDataTask().execute();
                }
            }
        }else {
            boolean se_status = databaseHandler.INSERT_HOUSEKEEPING(pojo);

            if (se_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(12).getHospital_id());
                    pojo.setAssessement_name("Housekeeping");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(12).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(HouseKeepingActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HouseKeepingActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
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
        for(int i=procedure_cleaning_blood_spill_list.size(); i<Local_procedure_cleaning_blood_spill_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_procedure_cleaning_blood_spill_list.get(i) + "procedure_cleaning_blood_spill_list");
            UploadImage(Local_procedure_cleaning_blood_spill_list.get(i),"procedure_cleaning_blood_spill_list");
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
                        Toast.makeText(HouseKeepingActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HouseKeepingActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i = staffs_personal_files_maintained_list.size(); i< Local_staffs_personal_files_maintained_list.size() ;i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_staffs_personal_files_maintained_list.get(i)+ "Biomedical_Waste_regulations");
            UploadImage(Local_staffs_personal_files_maintained_list.get(i),"Biomedical_Waste_regulations");
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
                        Toast.makeText(HouseKeepingActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HouseKeepingActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }


             pojo_dataSync.setTabName("housekeeping");
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

             pojo.setBiomedical_Waste_regulations_image(admissions_discharge_home_view);

             for (int i=0;i<procedure_cleaning_blood_spill_list.size();i++){
                 String value = procedure_cleaning_blood_spill_list.get(i);

                 procedure_cleaning_blood_spill_view = value + procedure_cleaning_blood_spill_view;
             }
              pojo.setProcedure_cleaning_blood_spill_video(procedure_cleaning_blood_spill_view);

             pojo_dataSync.setHousekeeping(pojo);

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
                                 Intent intent = new Intent(HouseKeepingActivity.this, LoginActivity.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent);
                                 finish();

                                 Toast.makeText(HouseKeepingActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                             }
                         });
                     }else {
                         if (response.body() != null){
                             if (response.body().getSuccess()){
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Intent intent = new Intent(HouseKeepingActivity.this,HospitalListActivity.class);
                                         startActivity(intent);
                                         finish();
                                     }
                                 });

                                 saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                 saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                 assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                 AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                 pojo.setHospital_id(assessement_list.get(12).getHospital_id());
                                 pojo.setAssessement_name("Housekeeping");
                                 pojo.setAssessement_status("Done");
                                 pojo.setLocal_id(assessement_list.get(12).getLocal_id());
                                 databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(HouseKeepingActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

  /*  private void VideoUpload(final String image_path){
        File videoFile = new File(image_path);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);

        final ProgressDialog d = VideoDialog.showLoading(HouseKeepingActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),vFile).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                System.out.println("xxxx sucess");

                d.cancel();

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        Toast.makeText(HouseKeepingActivity.this,"Video upload successfully",Toast.LENGTH_LONG).show();


                        image_procedure_cleaning_blood_spill.setImageResource(R.mipmap.camera_selected);

                    }else {
                        Toast.makeText(HouseKeepingActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HouseKeepingActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxxx faill");

                Toast.makeText(HouseKeepingActivity.this,"Video upload failed",Toast.LENGTH_LONG).show();

                d.cancel();
            }
        });
    }
*/

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("Biomedical_Waste_regulations")){
            //staffs_personal_files_maintained_list.add(response.body().getMessage());
            Local_staffs_personal_files_maintained_list.add(image_path);
            image_Biomedical_Waste_regulations.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "Biomedical_Waste_regulations";

        }else if (from.equalsIgnoreCase("procedure_cleaning_blood_spill_list")){
            //procedure_cleaning_blood_spill_list.add(response.body().getMessage());
            Local_procedure_cleaning_blood_spill_list.add(image_path);

            image_procedure_cleaning_blood_spill.setImageResource(R.mipmap.camera_selected);

        }

        Toast.makeText(HouseKeepingActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();
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
                            Intent intent = new Intent(HouseKeepingActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(HouseKeepingActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("Biomedical_Waste_regulations")){
                                staffs_personal_files_maintained_list.add(response.body().getMessage());
                                //Local_staffs_personal_files_maintained_list.add(image_path);
                                image_Biomedical_Waste_regulations.setImageResource(R.mipmap.camera_selected);

                                image1 = "Biomedical_Waste_regulations";

                            }else if (from.equalsIgnoreCase("procedure_cleaning_blood_spill_list")){
                                procedure_cleaning_blood_spill_list.add(response.body().getMessage());
                                //Local_procedure_cleaning_blood_spill_list.add(image_path);

                                image_procedure_cleaning_blood_spill.setImageResource(R.mipmap.camera_selected);

                            }

                            //Toast.makeText(HouseKeepingActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();
                            check =1;
                            latch.countDown();
                        }else {
                            check =0;
                            latch.countDown();
                            //Toast.makeText(HouseKeepingActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {//
                        check =0;
                        latch.countDown();
                        //Toast.makeText(HouseKeepingActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");
                check =0;
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
            if (from.equalsIgnoreCase("radiology_defined_turnaround")){
                staffs_personal_files_maintained_list.remove(position);
                Local_staffs_personal_files_maintained_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_staffs_personal_files_maintained_list.size() == 0){
                    image_Biomedical_Waste_regulations.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("procedure_cleaning_blood_spill_list")){
                procedure_cleaning_blood_spill_list.remove(position);
                Local_procedure_cleaning_blood_spill_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_procedure_cleaning_blood_spill_list.size() == 0){
                    image_procedure_cleaning_blood_spill.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(12).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save");
        }else {
            Intent intent = new Intent(HouseKeepingActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
