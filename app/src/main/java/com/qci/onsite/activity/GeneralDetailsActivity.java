package com.qci.onsite.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qci.onsite.R;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.GeneralDetailsPojo;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

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

public class GeneralDetailsActivity extends BaseActivity {

    @BindView(R.id.image_mark_location)
    ImageView image_mark_location;

    @BindView(R.id.ed_name)
    EditText ed_name;

    @BindView(R.id.image_selfie)
    ImageView image_selfie;

    @BindView(R.id.ed_name_hospital)
    EditText ed_name_hospital;

    @BindView(R.id.ed_hospital_address)
    EditText ed_hospital_address;

    @BindView(R.id.image_hospital_board)
    ImageView image_hospital_board;

    @BindView(R.id.ed_name_authorised_person)
    EditText ed_name_authorised_person;

    @BindView(R.id.ed_designation_authorised)
    EditText ed_designation_authorised;

    @BindView(R.id.ed_contact_number)
    EditText ed_contact_number;

    @BindView(R.id.tv_latLong)
    TextView tv_latLong;

    @BindView(R.id.image_authorised_person)
    ImageView image_authorised_person;

    @BindView(R.id.image_front_hospital)
    ImageView image_front_hospital;

    @BindView(R.id.image_back_hospital)
    ImageView image_back_hospital;

    @BindView(R.id.image_side_view1)
    ImageView image_side_view1;

    @BindView(R.id.image_side_view2)
    ImageView image_side_view2;
    @BindView(R.id.btnSave)
    Button btnSave;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String img_selfie = "",img_hospital_board = "",img_authorised_person="",img_front_hospital="",img_back_hospital = "",
            img_side_view1 = "",img_side_view2="";
    private String img_selfie_url= "",img_hospital_board_url= "",img_authorised_person_url="",img_front_hospital_url="",img_back_hospital_url = "",
            img_side_view1_url = "",img_side_view2_url = "";

    private Dialog dialogLogout;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    GeneralDetailsPojo pojo;

    String location_status = "";

    private String Hospital_id;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private DatabaseHandler databaseHandler;
    
    int check;
    CountDownLatch latch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_details);

        ButterKnife.bind(this);

        setDrawerbackIcon("General Details");

        mAPIService = ApiUtils.getAPIService();

        databaseHandler = DatabaseHandler.getInstance(this);

        pojo_dataSync = new DataSyncRequest();

        pojo = new GeneralDetailsPojo();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        getData();

    }

    private void getData(){
        pojo = getPojo();

        if (pojo != null){
            if (pojo.getLocation_lat() != null && pojo.getLocation_long() != null){
                tv_latLong.setText(pojo.getLocation_lat() + "," + pojo.getLocation_long());

                location_status = pojo.getLocation_lat() + "," + pojo.getLocation_long();
            }
            if (pojo.getAssessor_name() != null){
                ed_name.setText(pojo.getAssessor_name());
            }
            if (pojo.getImage_assessor_selfie().length() > 0){
                img_selfie_url = pojo.getImage_assessor_selfie();
                image_selfie.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_image_assessor_selfie().length() > 0){
                img_selfie = pojo.getLocal_image_assessor_selfie();

                image_selfie.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getHospital_name() != null){
                ed_name_hospital.setText(pojo.getHospital_name());
            }
            if (pojo.getHospital_address() != null){
                ed_hospital_address.setText(pojo.getHospital_address());
            }
            if (pojo.getImage_hospital_board().length() > 0){
                img_hospital_board_url = pojo.getImage_hospital_board();
                image_hospital_board.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_image_hospital_board().length() > 0){
                img_hospital_board = pojo.getLocal_image_hospital_board();

                image_hospital_board.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getName_authorised_person() != null){
                ed_name_authorised_person.setText(pojo.getName_authorised_person());
            }
            if (pojo.getDesignation_authorised_person() != null){
                ed_designation_authorised .setText(pojo.getDesignation_authorised_person());
            }
            if (pojo.getContact_number() != null){
                ed_contact_number.setText(pojo.getContact_number());
            }
            if (pojo.getAuthperson_image().length() > 0){
                img_authorised_person_url = pojo.getAuthperson_image();
                image_authorised_person.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_authperson_image().length() > 0){
                img_authorised_person = pojo.getLocal_authperson_image();

                image_authorised_person.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getHospitalbuildingfrontface_image().length() > 0){
                img_front_hospital_url = pojo.getHospitalbuildingfrontface_image();
                image_front_hospital.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_hospitalbuildingfrontface_image().length() > 0){
                img_front_hospital = pojo.getLocal_hospitalbuildingfrontface_image();

                image_front_hospital.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getHospitalbuildingbackview_image().length() > 0){
                img_back_hospital_url = pojo.getHospitalbuildingbackview_image();
                image_back_hospital.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_hospitalbuildingbackview_image().length() > 0){
                img_back_hospital = pojo.getLocal_hospitalbuildingbackview_image();

                image_back_hospital.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getHospitalbuilding1sideface_image().length() > 0){
                img_side_view1_url = pojo.getHospitalbuilding1sideface_image();
                image_side_view1.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_hospitalbuilding1sideface_image().length() > 0){
                img_side_view1 = pojo.getLocal_hospitalbuilding1sideface_image();

                image_side_view1.setImageResource(R.mipmap.camera_selected);
            }
            if (pojo.getHospitalbuilding2sideface_image().length() > 0){
                img_side_view2_url = pojo.getHospitalbuilding2sideface_image();
                image_side_view2.setImageResource(R.mipmap.camera_selected);

            }
            if (pojo.getLocal_hospitalbuilding2sideface_image().length() > 0){
                img_side_view2 = pojo.getLocal_hospitalbuilding2sideface_image();

                image_side_view2.setImageResource(R.mipmap.camera_selected);
            }
        }else {
            pojo = new GeneralDetailsPojo();
        }
    }

    @OnClick({R.id.btnSave,R.id.image_mark_location,R.id.image_selfie,R.id.image_hospital_board,R.id.image_authorised_person,R.id.image_front_hospital,
            R.id.image_back_hospital,R.id.image_side_view1,R.id.image_side_view2,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_mark_location:
                location_status = getFromPrefs(AppConstant.Latitude) + "," + getFromPrefs(AppConstant.Longitude);

                Toast.makeText(GeneralDetailsActivity.this,"Your location saved successfully",Toast.LENGTH_LONG).show();
                tv_latLong.setText(getFromPrefs(AppConstant.Latitude) + "," + getFromPrefs(AppConstant.Longitude));
                break;
            case R.id.image_selfie:
                if (img_selfie.length() > 0) {
                    showImageDialog(img_selfie, 2);
                } else {
                    captureImage(2);
                }
                break;

            case R.id.image_hospital_board:
                if (img_hospital_board.length() > 0) {
                    showImageDialog(img_hospital_board, 3);
                } else {
                    captureImage(3);
                }
                break;

            case R.id.image_authorised_person:
                if (img_authorised_person.length() > 0) {
                    showImageDialog(img_authorised_person, 4);
                } else {
                    captureImage(4);
                }
                break;

            case R.id.image_front_hospital:

                if (img_front_hospital.length() > 0) {
                    showImageDialog(img_front_hospital, 5);
                } else {
                    captureImage(5);
                }
                break;

            case R.id.image_back_hospital:
                if (img_back_hospital.length() > 0) {
                    showImageDialog(img_back_hospital, 6);
                } else {
                    captureImage(6);
                }

                break;

            case R.id.image_side_view1:
                if (img_side_view1.length() > 0) {
                    showImageDialog(img_side_view1, 7);
                } else {
                    captureImage(7);
                }

                break;


            case R.id.image_side_view2:

                if (img_side_view2.length() > 0) {
                    showImageDialog(img_side_view2, 8);
                } else {
                    captureImage(8);
                }
                break;
            case R.id.btnSave:
                SaveGeneralData();

                Intent intent = new Intent(GeneralDetailsActivity.this,HospitalListActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnSync:
                new PostDataTask().execute();
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GeneralDetailsActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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
          if (requestCode == 2) {
             if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"selfie");

                }
            }else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    SaveImage(image2,"hospital_board");

                }
            }
          else if (requestCode == 4) {
              if (picUri != null) {
                  Uri uri = picUri;
                  String image2 = compressImage(uri.toString());
                  //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                  SaveImage(image2,"authorised_person");

              }
          }

          else if (requestCode == 5) {
              if (picUri != null) {
                  Uri uri = picUri;
                  String image2 = compressImage(uri.toString());
                  //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                  SaveImage(image2,"front_hospital");

              }
          }
          else if (requestCode == 6) {
              if (picUri != null) {
                  Uri uri = picUri;
                  String image2 = compressImage(uri.toString());
                  //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                  SaveImage(image2,"back_hospital");

              }
          }

          else if (requestCode == 7) {
              if (picUri != null) {
                  Uri uri = picUri;
                  String image2 = compressImage(uri.toString());
                  //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                  SaveImage(image2,"side_view1");

              }
          }

          else if (requestCode == 8) {
              if (picUri != null) {
                  Uri uri = picUri;
                  String image2 = compressImage(uri.toString());
                  //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                  SaveImage(image2,"side_view2");

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

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                //d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(GeneralDetailsActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(GeneralDetailsActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("selfie")){
                                img_selfie_url = response.body().getMessage();
                                //img_selfie = image_path;
                                image_selfie.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("hospital_board")){
                                img_hospital_board_url = response.body().getMessage();
                                //img_hospital_board = image_path;
                                image_hospital_board.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("authorised_person")){
                                img_authorised_person_url = response.body().getMessage();
                                //img_authorised_person = image_path;
                                image_authorised_person.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("front_hospital")){
                                img_front_hospital_url = response.body().getMessage();
                                //img_front_hospital = image_path;
                                image_front_hospital.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("back_hospital")){
                                img_back_hospital_url = response.body().getMessage();
                                //img_back_hospital = image_path;
                                image_back_hospital.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("side_view1")){
                                img_side_view1_url = response.body().getMessage();
                                //img_side_view1 = image_path;
                                image_side_view1.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("side_view2")){
                                img_side_view2_url = response.body().getMessage();
                                //img_side_view2 = image_path;
                                image_side_view2.setImageResource(R.mipmap.camera_selected);
                            }
                            check = 1;
                            latch.countDown();
                            //Toast.makeText(GeneralDetailsActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");
                check = 0;
                latch.countDown();
                //d.cancel();

                //Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("selfie")){
            img_selfie = image_path;
            image_selfie.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("hospital_board")){
            img_hospital_board = image_path;
            image_hospital_board.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("authorised_person")){
            img_authorised_person = image_path;
            image_authorised_person.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("front_hospital")){
            img_front_hospital = image_path;
            image_front_hospital.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("back_hospital")){
            img_back_hospital = image_path;
            image_back_hospital.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("side_view1")){
            img_side_view1 = image_path;
            image_side_view1.setImageResource(R.mipmap.camera_selected);
        }else if (from.equalsIgnoreCase("side_view2")){
            img_side_view2 = image_path;
            image_side_view2.setImageResource(R.mipmap.camera_selected);
        }

        Toast.makeText(GeneralDetailsActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();

    }

    public void showImageDialog(final String path, final int position) {
        dialogLogout = new Dialog(GeneralDetailsActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                captureImage(position);
            }
        });

        try {
            Glide.with(GeneralDetailsActivity.this).load(path)
                    //           .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveGeneralData(){

        pojo.setLocation_lat(getFromPrefs(AppConstant.Latitude));
        pojo.setLocation_long(getFromPrefs(AppConstant.Longitude));
        pojo.setAssessor_name(ed_name.getText().toString());
        pojo.setImage_assessor_selfie(img_selfie_url);
        pojo.setLocal_image_assessor_selfie(img_selfie);
        pojo.setHospital_name(ed_name_hospital.getText().toString());
        pojo.setHospital_address(ed_hospital_address.getText().toString());
        pojo.setImage_hospital_board(img_hospital_board_url);
        pojo.setLocal_image_hospital_board(img_hospital_board);
        pojo.setName_authorised_person(ed_name_authorised_person.getText().toString());
        pojo.setDesignation_authorised_person(ed_designation_authorised.getText().toString());
        pojo.setContact_number(ed_contact_number.getText().toString());
        pojo.setAuthperson_image(img_authorised_person_url);
        pojo.setLocal_authperson_image(img_authorised_person);
        pojo.setHospitalbuildingfrontface_image(img_front_hospital_url);
        pojo.setLocal_hospitalbuildingfrontface_image(img_front_hospital);
        pojo.setHospitalbuildingbackview_image(img_back_hospital_url);
        pojo.setLocal_hospitalbuildingbackview_image(img_back_hospital);
        pojo.setHospitalbuilding1sideface_image(img_side_view1_url);
        pojo.setLocal_hospitalbuilding1sideface_image(img_side_view1);
        pojo.setHospitalbuilding2sideface_image(img_side_view2_url);
        pojo.setLocal_hospitalbuilding2sideface_image(img_side_view2);


        savePojoData(pojo);

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        AssessmentStatusPojo pojo = new AssessmentStatusPojo();
        pojo.setHospital_id(assessement_list.get(0).getHospital_id());
        pojo.setAssessement_name("General Details");
        pojo.setAssessement_status("Draft");
        pojo.setLocal_id(assessement_list.get(0).getLocal_id());

        databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
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

        if (location_status.length() > 0 && ed_name.getText().toString().length() > 0 && img_selfie.length() > 0 && ed_name_hospital.getText().toString().length() > 0
                && ed_hospital_address.getText().toString().length() > 0 && img_hospital_board.length() > 0 && ed_name_authorised_person.getText().toString().length() > 0
        && ed_designation_authorised.getText().toString().length() > 0 && ed_contact_number.getText().toString().length() > 0
        && img_authorised_person.length() > 0 && img_front_hospital.length() > 0 && img_back_hospital.length() > 0
        && img_side_view1.length() > 0 && img_side_view2.length() > 0){
            check = 1;
            if(!img_selfie.equals(img_selfie_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_selfie,"selfie");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_authorised_person.equals(img_authorised_person_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_authorised_person,"authorised_person");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_back_hospital.equals(img_back_hospital_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_back_hospital,"back_hospital");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_front_hospital.equals(img_front_hospital_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_front_hospital,"front_hospital");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_hospital_board.equals(img_hospital_board_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_hospital_board,"hospital_board");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_side_view1.equals(img_side_view1_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_side_view1,"side_view1");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
            if(!img_side_view2.equals(img_side_view2_url))
            {
                latch = new CountDownLatch(1);
                UploadImage(img_side_view2,"side_view2");
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
                            Toast.makeText(GeneralDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }



                pojo_dataSync.setTabName("GeneralInfo");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                pojo.setLocation_lat(getFromPrefs(AppConstant.Latitude));
                pojo.setLocation_long(getFromPrefs(AppConstant.Longitude));
                pojo.setAssessor_name(ed_name.getText().toString());
                pojo.setImage_assessor_selfie(img_selfie_url);
                pojo.setLocal_image_assessor_selfie(img_selfie);
                pojo.setHospital_name(ed_name_hospital.getText().toString());
                pojo.setHospital_address(ed_hospital_address.getText().toString());
                pojo.setImage_hospital_board(img_hospital_board_url);
                pojo.setLocal_image_hospital_board(img_hospital_board);
                pojo.setName_authorised_person(ed_name_authorised_person.getText().toString());
                pojo.setDesignation_authorised_person(ed_designation_authorised.getText().toString());
                pojo.setContact_number(ed_contact_number.getText().toString());
                pojo.setAuthperson_image(img_authorised_person_url);
                pojo.setLocal_authperson_image(img_authorised_person);
                pojo.setHospitalbuildingfrontface_image(img_front_hospital_url);
                pojo.setLocal_hospitalbuildingfrontface_image(img_front_hospital);
                pojo.setHospitalbuildingbackview_image(img_back_hospital_url);
                pojo.setLocal_hospitalbuildingbackview_image(img_back_hospital);
                pojo.setHospitalbuilding1sideface_image(img_side_view1_url);
                pojo.setLocal_hospitalbuilding1sideface_image(img_side_view1);
                pojo.setHospitalbuilding2sideface_image(img_side_view2_url);
                pojo.setLocal_hospitalbuilding2sideface_image(img_side_view2);

               savePojoData(pojo);

                pojo_dataSync.setGeneral(pojo);

                //final ProgressDialog d = AppDialog.showLoading(GeneralDetailsActivity.this);
                //d.setCanceledOnTouchOutside(false);
                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        //d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(GeneralDetailsActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(GeneralDetailsActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(GeneralDetailsActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });

                                    saveIntoPrefs(AppConstant.General_status,"submit");

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(0).getHospital_id());
                                    pojo.setAssessement_name("General Details");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(0).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GeneralDetailsActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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
                        //d.dismiss();
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
                    Toast.makeText(GeneralDetailsActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();

        SaveGeneralData();

        Intent intent = new Intent(GeneralDetailsActivity.this,HospitalListActivity.class);
        startActivity(intent);
        finish();




    }

    private void BackDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GeneralDetailsActivity.this);
        // set title
        alertDialogBuilder.setTitle("ALERT");
        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.back_alert))
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {


                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void savePojoData(GeneralDetailsPojo pojo){
        SharedPreferences settings = getSharedPreferences(AppConstant.PREF_NAME, 0);
        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pojo); // myObject - instance of MyObject
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();
    }

    public GeneralDetailsPojo getPojo(){
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        GeneralDetailsPojo obj = gson.fromJson(json, GeneralDetailsPojo.class);

        return obj;


    }

}
