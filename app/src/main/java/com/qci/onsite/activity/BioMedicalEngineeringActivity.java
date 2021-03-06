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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qci.onsite.R;
import com.qci.onsite.adapter.ImageShowAdapter;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.BioMedicalEngineeringPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.SterilizationAreaPojo;
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

public class BioMedicalEngineeringActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.Maintenance_staff_contactable_yes)
    RadioButton Maintenance_staff_contactable_yes;

    @BindView(R.id.Maintenance_staff_contactable_no)
    RadioButton Maintenance_staff_contactable_no;

    @BindView(R.id.remark_Maintenance_staff_contactable)
    ImageView remark_Maintenance_staff_contactable;

    @BindView(R.id.nc_Maintenance_staff_contactable)
    ImageView nc_Maintenance_staff_contactable;

    @BindView(R.id.equipment_accordance_services_yes)
    RadioButton equipment_accordance_services_yes;

    @BindView(R.id.equipment_accordance_services_no)
    RadioButton equipment_accordance_services_no;

    @BindView(R.id.remark_equipment_accordance_services)
    ImageView remark_equipment_accordance_services;

    @BindView(R.id.nc_equipment_accordance_services)
    ImageView nc_equipment_accordance_services;

    @BindView(R.id.BioMedicalEngineeringPojo_yes)
    RadioButton BioMedicalEngineeringPojo_yes;

    @BindView(R.id.BioMedicalEngineeringPojo_no)
    RadioButton BioMedicalEngineeringPojo_no;

    @BindView(R.id.remark_BioMedicalEngineeringPojo)
    ImageView remark_BioMedicalEngineeringPojo;

    @BindView(R.id.nc_BioMedicalEngineeringPojo)
    ImageView nc_BioMedicalEngineeringPojo;

    @BindView(R.id.image_BioMedicalEngineeringPojo)
    ImageView image_BioMedicalEngineeringPojo;

    private String remark1, remark2, remark3;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;


    private String nc1, nc2, nc3;
    private String radio_status1, radio_status2, radio_status3;

    private DatabaseHandler databaseHandler;

    private String image3;
    private String Local_image3;

    private File outputVideoFile;

    Uri videoUri;

    private String Maintenance_staff_contactable = "",equipment_accordance_services="",BioMedicalEngineeringPojo = "";


    private ArrayList<String>DoHigh_imageList;
    private ArrayList<String>Local_DoHigh_imageList;

    private BioMedicalEngineeringPojo pojo;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;

    String DoHigh_view = "";

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_medical);

        ButterKnife.bind(this);

        setDrawerbackIcon("Maintenance/Bio-medical engineering");

        mAPIService = ApiUtils.getAPIService();

        DoHigh_imageList = new ArrayList<>();
        Local_DoHigh_imageList = new ArrayList<>();

        databaseHandler = DatabaseHandler.getInstance(this);

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));


        pojo_dataSync = new DataSyncRequest();

        pojo = new BioMedicalEngineeringPojo();

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getHighDeppendencyData();

    }

    public void getHighDeppendencyData(){

        pojo = databaseHandler.getBioMedicalEngineering("15");

        if (pojo != null){
            sql_status = true;
            if (pojo.getMaintenance_staff_contactable() != null){
                Maintenance_staff_contactable = pojo.getMaintenance_staff_contactable();
                if (pojo.getMaintenance_staff_contactable().equalsIgnoreCase("Yes")){
                    Maintenance_staff_contactable_yes.setChecked(true);
                }else if (pojo.getMaintenance_staff_contactable().equalsIgnoreCase("No")){
                    Maintenance_staff_contactable_no.setChecked(true);
                }
            }
            if (pojo.getEquipment_accordance_services() != null){
                equipment_accordance_services = pojo.getEquipment_accordance_services();
                if (pojo.getEquipment_accordance_services().equalsIgnoreCase("Yes")){
                    equipment_accordance_services_yes.setChecked(true);
                }else if (pojo.getEquipment_accordance_services().equalsIgnoreCase("No")){
                    equipment_accordance_services_no.setChecked(true);
                }
            } if (pojo.getDocumented_operational_maintenance() != null){
                BioMedicalEngineeringPojo = pojo.getDocumented_operational_maintenance();
                if (pojo.getDocumented_operational_maintenance().equalsIgnoreCase("Yes")){
                    BioMedicalEngineeringPojo_yes.setChecked(true);
                }else if (pojo.getDocumented_operational_maintenance().equalsIgnoreCase("No")){
                    BioMedicalEngineeringPojo_no.setChecked(true);
                }
            }
            if (pojo.getMaintenance_staff_contactable_remark() != null){
                remark_Maintenance_staff_contactable.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getMaintenance_staff_contactable_remark();
            }
            if (pojo.getMaintenance_staff_contactable_nc() != null){
                nc1 = pojo.getMaintenance_staff_contactable_nc();

                if (nc1.equalsIgnoreCase("close")) {
                    nc_Maintenance_staff_contactable.setImageResource(R.mipmap.nc);
                }else {
                    nc_Maintenance_staff_contactable.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getEquipment_accordance_services_remark() != null){
                remark2 = pojo.getEquipment_accordance_services_remark();

                remark_equipment_accordance_services.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getEquipment_accordance_services_nc() != null) {
                nc2 = pojo.getEquipment_accordance_services_nc();

                if (nc2.equalsIgnoreCase("close")) {
                    nc_equipment_accordance_services.setImageResource(R.mipmap.nc);
                } else {
                    nc_equipment_accordance_services.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getDocumented_operational_maintenance_remark() != null){
                remark3 = pojo.getDocumented_operational_maintenance_remark();

                remark_BioMedicalEngineeringPojo.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocumented_operational_maintenance_nc() != null){
                nc3 = pojo.getDocumented_operational_maintenance_nc();

                if (nc3.equalsIgnoreCase("close")) {
                    nc_BioMedicalEngineeringPojo.setImageResource(R.mipmap.nc);
                } else {
                    nc_BioMedicalEngineeringPojo.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getDocumented_operational_maintenance_image() != null){
                image_BioMedicalEngineeringPojo.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getDocumented_operational_maintenance_image();

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

            if (pojo.getLocal_documented_operational_maintenance_image() != null){
                image_BioMedicalEngineeringPojo.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_documented_operational_maintenance_image();

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
            pojo = new BioMedicalEngineeringPojo();
        }

    }


    @OnClick({R.id.remark_Maintenance_staff_contactable,R.id.nc_Maintenance_staff_contactable,R.id.remark_equipment_accordance_services,R.id.nc_equipment_accordance_services,R.id.remark_BioMedicalEngineeringPojo,
            R.id.nc_BioMedicalEngineeringPojo,R.id.image_BioMedicalEngineeringPojo,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_Maintenance_staff_contactable:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_Maintenance_staff_contactable:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_equipment_accordance_services:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_equipment_accordance_services:
                displayNCDialog("NC", 2);
                break;


            case R.id.remark_BioMedicalEngineeringPojo:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_BioMedicalEngineeringPojo:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_BioMedicalEngineeringPojo:
                if (Local_DoHigh_imageList.size() > 0){
                    showImageListDialog(Local_DoHigh_imageList,3,"BioMedicalEngineering");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.btnSave:
                SaveLaboratoryData("");
                break;

            case R.id.btnSync:
                if (Maintenance_staff_contactable.length() > 0 && equipment_accordance_services.length() > 0 && BioMedicalEngineeringPojo.length() > 0){
                    if (Local_image3 != null){
                        SaveLaboratoryData("sync");
                    }else {
                        Toast.makeText(BioMedicalEngineeringActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(BioMedicalEngineeringActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }

                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.Maintenance_staff_contactable_yes:
                if (checked)
                    Maintenance_staff_contactable = "Yes";
                break;

            case R.id.Maintenance_staff_contactable_no:
                if (checked)
                    Maintenance_staff_contactable = "No";
                break;

            case R.id.equipment_accordance_services_yes:
                if (checked)
                    equipment_accordance_services = "Yes";
                break;
            case R.id.equipment_accordance_services_no:
                if (checked)
                    equipment_accordance_services = "No";
                break;


            case R.id.BioMedicalEngineeringPojo_yes:
                if (checked)
                    BioMedicalEngineeringPojo = "Yes";
                break;
            case R.id.BioMedicalEngineeringPojo_no:
                if (checked)
                    BioMedicalEngineeringPojo = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(BioMedicalEngineeringActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);


                    SaveImage(image3,"do_high");

                }

            }
        }
    }


    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(BioMedicalEngineeringActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

                            if (radio_status1.equalsIgnoreCase("close")) {
                                nc_Maintenance_staff_contactable.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_Maintenance_staff_contactable.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_equipment_accordance_services.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_equipment_accordance_services.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")) {
                                nc_BioMedicalEngineeringPojo.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_BioMedicalEngineeringPojo.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(BioMedicalEngineeringActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_Maintenance_staff_contactable.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_equipment_accordance_services.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_BioMedicalEngineeringPojo.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(BioMedicalEngineeringActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(BioMedicalEngineeringActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(BioMedicalEngineeringActivity.this,list,from,"BioMedicalEngineering");
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
                    Toast toast = Toast.makeText(BioMedicalEngineeringActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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
        pojo.setHospital_id(15);
        pojo.setMaintenance_staff_contactable(Maintenance_staff_contactable);
        pojo.setEquipment_accordance_services(equipment_accordance_services);
        pojo.setDocumented_operational_maintenance(BioMedicalEngineeringPojo);


        pojo.setMaintenance_staff_contactable_remark(remark1);
        pojo.setMaintenance_staff_contactable_nc(nc1);

        JSONObject json = new JSONObject();


        pojo.setEquipment_accordance_services_remark(remark2);
        pojo.setEquipment_accordance_services_nc(nc2);

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

        pojo.setDocumented_operational_maintenance_remark(remark3);
        pojo.setDocumented_operational_maintenance_nc(nc3);
        pojo.setDocumented_operational_maintenance_image(image3);
        pojo.setLocal_documented_operational_maintenance_image(Local_image3);

        if (sql_status){
            boolean s_status = databaseHandler.UPDATE_Bio_medical_engineering(pojo);

            if (s_status){
                if (!from.equalsIgnoreCase("sync")) {
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(15).getHospital_id());
                    pojo.setAssessement_name("Maintenance/Bio-medical engineering");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(15).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(BioMedicalEngineeringActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(BioMedicalEngineeringActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //progreesDialog();
                    new PostDataTask().execute();
                }
            }
        }else {
            boolean s_status = databaseHandler.INSERT_Bio_medical_engineering(pojo);

            if (s_status){
                if (!from.equalsIgnoreCase("sync")) {
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(15).getHospital_id());
                    pojo.setAssessement_name("Maintenance/Bio-medical engineering");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(15).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


                    Toast.makeText(BioMedicalEngineeringActivity.this, "Your data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(BioMedicalEngineeringActivity.this, HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //progreesDialog();
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
        for(int i=DoHigh_imageList.size(); i<Local_DoHigh_imageList.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_DoHigh_imageList.get(i) + "do_high");
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
                        Toast.makeText(BioMedicalEngineeringActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BioMedicalEngineeringActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("maintenancebiomedical");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }
                 pojo_dataSync.setAssessortype(getFromPrefs("assessor_status"));

                for (int i=0;i<DoHigh_imageList.size();i++){
                    String value = DoHigh_imageList.get(i);

                    DoHigh_view = value + DoHigh_view;
                }


                pojo.setDocumented_operational_maintenance_image(DoHigh_view);

                pojo_dataSync.setMaintenancebio(pojo);
        latch = new CountDownLatch(1);
        check = 0;
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        //CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(BioMedicalEngineeringActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(BioMedicalEngineeringActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(BioMedicalEngineeringActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });


                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(15).getHospital_id());
                                    pojo.setAssessement_name("Maintenance/Bio-medical engineering");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(15).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(BioMedicalEngineeringActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("do_high")){

            //DoHigh_imageList.add(response.body().getMessage());
            Local_DoHigh_imageList.add(image_path);
            image_BioMedicalEngineeringPojo.setImageResource(R.mipmap.camera_selected);

            Local_image3 = "do_high";
        }


        Toast.makeText(BioMedicalEngineeringActivity.this,"Image Saved Locally",Toast.LENGTH_LONG).show();

    }

    private void UploadImage(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        //final ProgressDialog d = ImageDialog.showLoading(BioMedicalEngineeringActivity.this);
        //d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                //d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BioMedicalEngineeringActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(BioMedicalEngineeringActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                           if (from.equalsIgnoreCase("do_high")){

                                DoHigh_imageList.add(response.body().getMessage());
                                //Local_DoHigh_imageList.add(image_path);
                               image_BioMedicalEngineeringPojo.setImageResource(R.mipmap.camera_selected);

                               image3 = "do_high";
                            }

                            check = 1;
                            latch.countDown();
                           // Toast.makeText(BioMedicalEngineeringActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            check = 0;
                            latch.countDown();
                            //Toast.makeText(BioMedicalEngineeringActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
                        //Toast.makeText(BioMedicalEngineeringActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");
                check = 0;
                latch.countDown();
                //d.cancel();

                //Toast.makeText(BioMedicalEngineeringActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("BioMedicalEngineering")){
                Local_DoHigh_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_DoHigh_imageList.size() == 0){
                    image_BioMedicalEngineeringPojo.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(15).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("");
        }else {
            Intent intent = new Intent(BioMedicalEngineeringActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
