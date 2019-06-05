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
import com.qci.onsite.pojo.SterilizationAreaPojo;
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

public class SterilizationAreaActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.sterilisation_practices_adherede_yes)
    RadioButton sterilisation_practices_adherede_yes;

    @BindView(R.id.sterilisation_practices_adhered_no)
    RadioButton sterilisation_practices_adhered_no;

    @BindView(R.id.remark_sterilisation_practices_adhered)
    ImageView remark_sterilisation_practices_adhered;

    @BindView(R.id.nc_sterilisation_practices_adhered)
    ImageView nc_sterilisation_practices_adhered;

    @BindView(R.id.monitor_effectiveness_sterilization_process_yes)
    RadioButton monitor_effectiveness_sterilization_process_yes;

    @BindView(R.id.monitor_effectiveness_sterilization_process_no)
    RadioButton monitor_effectiveness_sterilization_process_no;

    @BindView(R.id.remark_monitor_effectiveness_sterilization_process)
    ImageView remark_monitor_effectiveness_sterilization_process;

    @BindView(R.id.nc_monitor_effectiveness_sterilization_process)
    ImageView nc_monitor_effectiveness_sterilization_process;

    @BindView(R.id.image_monitor_effectiveness_sterilization_process)
    ImageView image_monitor_effectiveness_sterilization_process;

    @BindView(R.id.sterilized_drums_trays_yes)
    RadioButton sterilized_drums_trays_yes;

    @BindView(R.id.sterilized_drums_trays_no)
    RadioButton sterilized_drums_trays_no;

    @BindView(R.id.remark_sterilized_drums_trays)
    ImageView remark_sterilized_drums_trays;

    @BindView(R.id.nc_sterilized_drums_trays)
    ImageView nc_sterilized_drums_trays;

    @BindView(R.id.image_sterilized_drums_trays)
    ImageView image_sterilized_drums_trays;

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

    private String image2,image3;
    private String Local_image2,Local_image3;

    private File outputVideoFile;

    Uri videoUri;

    private String sterilisation_practices_adherede = "",monitor_effectiveness_sterilization_process="",sterilized_drums_trays = "";

    private ArrayList<String>AreStaff_imageList;
    private ArrayList<String>Local_AreStaff_imageList;
    private ArrayList<String>DoHigh_imageList;
    private ArrayList<String>Local_DoHigh_imageList;

    private SterilizationAreaPojo pojo;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sterilization_area);

        ButterKnife.bind(this);

        setDrawerbackIcon("Sterilization Area");

        mAPIService = ApiUtils.getAPIService();


        AreStaff_imageList = new ArrayList<>();
        Local_AreStaff_imageList = new ArrayList<>();
        DoHigh_imageList = new ArrayList<>();
        Local_DoHigh_imageList = new ArrayList<>();

        databaseHandler = DatabaseHandler.getInstance(this);

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        pojo_dataSync = new DataSyncRequest();

        pojo = new SterilizationAreaPojo();

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getHighDeppendencyData();

    }

    public void getHighDeppendencyData(){

        pojo = databaseHandler.getSterilizationArea("13");

        if (pojo != null){
            sql_status = true;
            if (pojo.getSterilisation_practices_adhered() != null){
                sterilisation_practices_adherede = pojo.getSterilisation_practices_adhered();
                if (pojo.getSterilisation_practices_adhered().equalsIgnoreCase("Yes")){
                    sterilisation_practices_adherede_yes.setChecked(true);
                }else if (pojo.getSterilisation_practices_adhered().equalsIgnoreCase("No")){
                    sterilisation_practices_adhered_no.setChecked(true);
                }
            }
            if (pojo.getMonitor_effectiveness_sterilization_process() != null){
                monitor_effectiveness_sterilization_process = pojo.getMonitor_effectiveness_sterilization_process();
                if (pojo.getMonitor_effectiveness_sterilization_process().equalsIgnoreCase("Yes")){
                    monitor_effectiveness_sterilization_process_yes.setChecked(true);
                }else if (pojo.getMonitor_effectiveness_sterilization_process().equalsIgnoreCase("No")){
                    monitor_effectiveness_sterilization_process_no.setChecked(true);
                }
            } if (pojo.getSterilized_drums_trays() != null){
                sterilized_drums_trays = pojo.getSterilized_drums_trays();
                if (pojo.getSterilized_drums_trays().equalsIgnoreCase("Yes")){
                    sterilized_drums_trays_yes.setChecked(true);
                }else if (pojo.getSterilized_drums_trays().equalsIgnoreCase("No")){
                    sterilized_drums_trays_no.setChecked(true);
                }
            }
            if (pojo.getSterilisation_practices_adhered_remark() != null){

                remark_sterilisation_practices_adhered.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getSterilisation_practices_adhered_remark();
            }
            if (pojo.getSterilisation_practices_adhered_nc() != null){
                nc1 = pojo.getSterilisation_practices_adhered_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_sterilisation_practices_adhered.setImageResource(R.mipmap.nc);
                }else {
                    nc_sterilisation_practices_adhered.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getMonitor_effectiveness_sterilization_process_remark() != null){
                remark2 = pojo.getMonitor_effectiveness_sterilization_process_remark();

                remark_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getMonitor_effectiveness_sterilization_process_nc() != null){
                nc2 = pojo.getMonitor_effectiveness_sterilization_process_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.nc);
                }else {
                    nc_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getMonitor_effectiveness_sterilization_process_image() != null){
                image_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getMonitor_effectiveness_sterilization_process_image();

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

            if (pojo.getLocal_monitor_effectiveness_sterilization_process_image() != null){
                image_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_monitor_effectiveness_sterilization_process_image();

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

            if (pojo.getSterilized_drums_trays_remark() != null){
                remark3 = pojo.getSterilized_drums_trays_remark();

                remark_sterilized_drums_trays.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getSterilized_drums_trays_nc() != null){
                nc3 = pojo.getSterilized_drums_trays_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_sterilized_drums_trays.setImageResource(R.mipmap.nc);
                }else {
                    nc_sterilized_drums_trays.setImageResource(R.mipmap.nc_selected);
                }


            }
            if (pojo.getSterilized_drums_trays_image() != null){
                image_sterilized_drums_trays.setImageResource(R.mipmap.camera_selected);

                image3 = pojo.getSterilized_drums_trays_image();

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

            if (pojo.getLocal_sterilized_drums_trays_image() != null){
                image_sterilized_drums_trays.setImageResource(R.mipmap.camera_selected);

                Local_image3 = pojo.getLocal_sterilized_drums_trays_image();

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
            pojo = new SterilizationAreaPojo();
        }

    }


    @OnClick({R.id.remark_sterilisation_practices_adhered,R.id.nc_sterilisation_practices_adhered,R.id.remark_monitor_effectiveness_sterilization_process,R.id.nc_monitor_effectiveness_sterilization_process,R.id.image_monitor_effectiveness_sterilization_process,
            R.id.remark_sterilized_drums_trays,R.id.nc_sterilized_drums_trays,R.id.image_sterilized_drums_trays,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_sterilisation_practices_adhered:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_sterilisation_practices_adhered:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_monitor_effectiveness_sterilization_process:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_monitor_effectiveness_sterilization_process:
                displayNCDialog("NC", 2);
                break;


            case R.id.image_monitor_effectiveness_sterilization_process:
                if (Local_AreStaff_imageList.size() > 0){
                    showImageListDialog(Local_AreStaff_imageList,2,"AreStaff");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.remark_sterilized_drums_trays:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_sterilized_drums_trays:
                displayNCDialog("NC", 3);
                break;

            case R.id.image_sterilized_drums_trays:
                if (Local_DoHigh_imageList.size() > 0){
                    showImageListDialog(Local_DoHigh_imageList,3,"DoHigh");
                }else {
                    captureImage(3);
                }
                break;

            case R.id.btnSave:
                SaveLaboratoryData("save");
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
            case R.id.sterilisation_practices_adherede_yes:
                if (checked)
                    sterilisation_practices_adherede = "Yes";
                break;

            case R.id.sterilisation_practices_adhered_no:
                if (checked)
                    sterilisation_practices_adherede = "No";
                break;

            case R.id.monitor_effectiveness_sterilization_process_yes:
                if (checked)
                    monitor_effectiveness_sterilization_process = "Yes";
                break;
            case R.id.monitor_effectiveness_sterilization_process_no:
                if (checked)
                    monitor_effectiveness_sterilization_process = "No";
                break;


            case R.id.sterilized_drums_trays_yes:
                if (checked)
                    sterilized_drums_trays = "Yes";
                break;
            case R.id.sterilized_drums_trays_no:
                if (checked)
                    sterilized_drums_trays = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(SterilizationAreaActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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


                    ImageUpload(image2,"Are_staff");

                }

            }
            else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image3 = compressImage(uri.toString());
                    //                  saveIntoPrefs(AppConstant.statutory_PollutionControl,image3);

                    ImageUpload(image3,"do_high");

                }

            }
        }
    }


    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(SterilizationAreaActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_sterilisation_practices_adhered.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_sterilisation_practices_adhered.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(SterilizationAreaActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SterilizationAreaActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_sterilized_drums_trays.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_sterilized_drums_trays.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(SterilizationAreaActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(SterilizationAreaActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_sterilisation_practices_adhered.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SterilizationAreaActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SterilizationAreaActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_sterilized_drums_trays.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(SterilizationAreaActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(SterilizationAreaActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(SterilizationAreaActivity.this,list,from,"SterilizationArea");
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
        pojo.setHospital_id(13);
        pojo.setSterilisation_practices_adhered(sterilisation_practices_adherede);
        pojo.setMonitor_effectiveness_sterilization_process(monitor_effectiveness_sterilization_process);
        pojo.setSterilized_drums_trays(sterilized_drums_trays);


        pojo.setSterilisation_practices_adhered_remark(remark1);
        pojo.setSterilisation_practices_adhered_nc(nc1);

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


        pojo.setMonitor_effectiveness_sterilization_process_remark(remark2);
        pojo.setMonitor_effectiveness_sterilization_process_nc(nc2);
        pojo.setMonitor_effectiveness_sterilization_process_image(image2);
        pojo.setLocal_monitor_effectiveness_sterilization_process_image(Local_image2);

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

        pojo.setSterilized_drums_trays_remark(remark3);
        pojo.setSterilized_drums_trays_nc(nc3);
        pojo.setSterilized_drums_trays_image(image3);
        pojo.setLocal_sterilized_drums_trays_image(Local_image3);

        if (sql_status){
            databaseHandler.UPDATE_STERIALIZATION(pojo);
        }else {
            boolean status = databaseHandler.INSERT_STERIALIZATION(pojo);
            System.out.println(status);
        }

        if (!from.equalsIgnoreCase("sync")){
            assessement_list = databaseHandler.getAssessmentList(Hospital_id);

            AssessmentStatusPojo pojo = new AssessmentStatusPojo();
            pojo.setHospital_id(assessement_list.get(13).getHospital_id());
            pojo.setAssessement_name("Sterilization Area");
            pojo.setAssessement_status("Draft");
            pojo.setLocal_id(assessement_list.get(13).getLocal_id());

            databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


            Toast.makeText(SterilizationAreaActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SterilizationAreaActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void PostLaboratoryData(){

        SaveLaboratoryData("sync");

        if (sterilisation_practices_adherede.length() > 0 && monitor_effectiveness_sterilization_process.length() > 0 && sterilized_drums_trays.length() > 0){

            if (image2 != null && image3 != null){
                pojo_dataSync.setTabName("sterilization");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }

                String AreStaff_view = "",DoHigh_view="";

                for (int i=0;i<AreStaff_imageList.size();i++){
                    String value = AreStaff_imageList.get(i);

                    AreStaff_view = value + AreStaff_view;
                }

                for (int i=0;i<DoHigh_imageList.size();i++){
                    String value = DoHigh_imageList.get(i);

                    DoHigh_view = value + DoHigh_view;
                }


                pojo.setMonitor_effectiveness_sterilization_process_image(AreStaff_view);
                pojo.setSterilized_drums_trays_image(DoHigh_view);

                pojo_dataSync.setSterilization(pojo);

                final ProgressDialog d = AppDialog.showLoading(SterilizationAreaActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(SterilizationAreaActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(SterilizationAreaActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(SterilizationAreaActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(13).getHospital_id());
                                    pojo.setAssessement_name("Sterilization Area");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(13).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(SterilizationAreaActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
                Toast.makeText(SterilizationAreaActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(SterilizationAreaActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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

        final ProgressDialog d = ImageDialog.showLoading(SterilizationAreaActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(SterilizationAreaActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(SterilizationAreaActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("Are_staff")){
                                AreStaff_imageList.add(response.body().getMessage());
                                Local_AreStaff_imageList.add(image_path);
                                image_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("do_high")){

                                DoHigh_imageList.add(response.body().getMessage());
                                Local_DoHigh_imageList.add(image_path);
                                image_sterilized_drums_trays.setImageResource(R.mipmap.camera_selected);
                            }


                            Toast.makeText(SterilizationAreaActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(SterilizationAreaActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(SterilizationAreaActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(SterilizationAreaActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("AreStaff")){
                AreStaff_imageList.remove(position);
                Local_AreStaff_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_AreStaff_imageList.size() == 0){
                    image_monitor_effectiveness_sterilization_process.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
            if (from.equalsIgnoreCase("DoHigh")){
                DoHigh_imageList.remove(position);
                Local_DoHigh_imageList.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_DoHigh_imageList.size() == 0){

                    image_sterilized_drums_trays.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(13).getAssessement_status().equalsIgnoreCase("Done")){
            SaveLaboratoryData("save");
        }else {
            Intent intent = new Intent(SterilizationAreaActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
