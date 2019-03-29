package com.qci.onsite.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String img_selfie = "",img_hospital_board = "";
    private String img_selfie_url= "",img_hospital_board_url= "";

    private Dialog dialogLogout;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    GeneralDetailsPojo pojo;

    String location_status = "";

    private String Hospital_id;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private DatabaseHandler databaseHandler;

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

    }

    @OnClick({R.id.image_mark_location,R.id.image_selfie,R.id.image_hospital_board,R.id.btnSync})
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

            case R.id.btnSync:
                PostLaboratoryData();
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

                    ImageUpload(image2,"selfie");

                }
            }else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2,"hospital_board");

                }
            }
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

        final ProgressDialog d = ImageDialog.showLoading(GeneralDetailsActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(GeneralDetailsActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(GeneralDetailsActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("selfie")){
                                img_selfie_url = response.body().getMessage();
                                img_selfie = image_path;
                                image_selfie.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("hospital_board")){
                                img_hospital_board_url = response.body().getMessage();
                                img_hospital_board = image_path;
                                image_hospital_board.setImageResource(R.mipmap.camera_selected);
                            }

                            Toast.makeText(GeneralDetailsActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(GeneralDetailsActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });
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

    private void PostLaboratoryData(){

        if (location_status.length() > 0 && ed_name.getText().toString().length() > 0 && img_selfie_url.length() > 0 && ed_name_hospital.getText().toString().length() > 0
                && ed_hospital_address.getText().toString().length() > 0 && img_hospital_board_url.length() > 0 && ed_name_authorised_person.getText().toString().length() > 0
        && ed_designation_authorised.getText().toString().length() > 0 && ed_contact_number.getText().toString().length() > 0){

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
                pojo.setHospital_name(ed_name_hospital.getText().toString());
                pojo.setHospital_address(ed_hospital_address.getText().toString());
                pojo.setImage_hospital_board(img_hospital_board_url);
                pojo.setName_authorised_person(ed_name_authorised_person.getText().toString());
                pojo.setDesignation_authorised_person(ed_designation_authorised.getText().toString());
                pojo.setContact_number(ed_contact_number.getText().toString());

                pojo_dataSync.setGeneral(pojo);

                final ProgressDialog d = AppDialog.showLoading(GeneralDetailsActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(GeneralDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(GeneralDetailsActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(GeneralDetailsActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs(AppConstant.General_status,"submit");

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(0).getHospital_id());
                                    pojo.setAssessement_name("General Details");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(0).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(GeneralDetailsActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
            Toast.makeText(GeneralDetailsActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
   //     super.onBackPressed();

        BackDialog();

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

                        Intent intent = new Intent(GeneralDetailsActivity.this,HospitalListActivity.class);
                        startActivity(intent);
                        finish();
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

}
