package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.qci.onsite.pojo.HRMPojo;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.MRDPojo;
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

public class MRDActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.medical_record_death_certificate_yes)
    RadioButton medical_record_death_certificate_yes;

    @BindView(R.id.medical_record_death_certificate_no)
    RadioButton medical_record_death_certificate_no;

    @BindView(R.id.remark_medical_record_death_certificate)
    ImageView remark_medical_record_death_certificate;

    @BindView(R.id.nc_medical_record_death_certificate)
    ImageView nc_medical_record_death_certificate;

    @BindView(R.id.documented_maintaining_confidentiality_yes)
    RadioButton documented_maintaining_confidentiality_yes;

    @BindView(R.id.documented_maintaining_confidentiality_no)
    RadioButton documented_maintaining_confidentiality_no;

    @BindView(R.id.remark_documented_maintaining_confidentiality)
    ImageView remark_documented_maintaining_confidentiality;

    @BindView(R.id.nc_documented_maintaining_confidentiality)
    ImageView nc_documented_maintaining_confidentiality;

    @BindView(R.id.any_information_disclosed_yes)
    RadioButton any_information_disclosed_yes;

    @BindView(R.id.any_information_disclosed_no)
    RadioButton any_information_disclosed_no;

    @BindView(R.id.remark_any_information_disclosed)
    ImageView remark_any_information_disclosed;

    @BindView(R.id.nc_any_information_disclosed)
    ImageView nc_any_information_disclosed;

    @BindView(R.id.destruction_medical_records_yes)
    RadioButton destruction_medical_records_yes;

    @BindView(R.id.destruction_medical_records_no)
    RadioButton destruction_medical_records_no;

    @BindView(R.id.remark_destruction_medical_records)
    ImageView remark_destruction_medical_records;

    @BindView(R.id.nc_destruction_medical_records)
    ImageView nc_destruction_medical_records;

    @BindView(R.id.fire_extinguisher_present_yes)
    RadioButton fire_extinguisher_present_yes;

    @BindView(R.id.fire_extinguisher_present_no)
    RadioButton fire_extinguisher_present_no;

    @BindView(R.id.remark_fire_extinguisher_present)
    ImageView remark_fire_extinguisher_present;

    @BindView(R.id.nc_fire_extinguisher_present)
    ImageView nc_fire_extinguisher_present;

    @BindView(R.id.image_fire_extinguisher_present)
    ImageView image_fire_extinguisher_present;

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

    private String medical_record_death_certificate = "",documented_maintaining_confidentiality ="",any_information_disclosed="",destruction_medical_records = "",fire_extinguisher_present="";


    private ArrayList<String>staffs_personal_files_maintained_list;
    private ArrayList<String>Local_staffs_personal_files_maintained_list;

    private MRDPojo pojo;

    private String image1,Local_image1;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private String Hospital_id;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    DataSyncRequest pojo_dataSync;

    String admissions_discharge_home_view = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrd);

        ButterKnife.bind(this);

        setDrawerbackIcon("MRD");

        databaseHandler = DatabaseHandler.getInstance(this);

        staffs_personal_files_maintained_list = new ArrayList<>();
        Local_staffs_personal_files_maintained_list = new ArrayList<>();

        mAPIService = ApiUtils.getAPIService();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        assessement_list = new ArrayList<>();

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        pojo_dataSync = new DataSyncRequest();

        pojo = new MRDPojo();

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getPharmacyData();

    }

    public void getPharmacyData(){

        pojo = databaseHandler.getMRDPojo("11");

        if (pojo != null){
            sql_status = true;
            if (pojo.getMedical_record_death_certificate() != null){
                medical_record_death_certificate = pojo.getMedical_record_death_certificate();
                if (pojo.getMedical_record_death_certificate().equalsIgnoreCase("Yes")){
                    medical_record_death_certificate_yes.setChecked(true);
                }else if (pojo.getMedical_record_death_certificate().equalsIgnoreCase("No")){
                    medical_record_death_certificate_no.setChecked(true);
                }
            }
            if (pojo.getDocumented_maintaining_confidentiality() != null){
                documented_maintaining_confidentiality = pojo.getDocumented_maintaining_confidentiality();
                if (pojo.getDocumented_maintaining_confidentiality().equalsIgnoreCase("Yes")){
                    documented_maintaining_confidentiality_yes.setChecked(true);
                }else if (pojo.getDocumented_maintaining_confidentiality().equalsIgnoreCase("No")){
                    documented_maintaining_confidentiality_no.setChecked(true);
                }
            }
            if (pojo.getAny_information_disclosed() != null){
                any_information_disclosed = pojo.getAny_information_disclosed();
                if (pojo.getAny_information_disclosed().equalsIgnoreCase("Yes")){
                    any_information_disclosed_yes.setChecked(true);
                }else if (pojo.getAny_information_disclosed().equalsIgnoreCase("No")){
                    any_information_disclosed_no.setChecked(true);
                }
            } if (pojo.getDestruction_medical_records() != null){
                destruction_medical_records = pojo.getDestruction_medical_records();
                if (pojo.getDestruction_medical_records().equalsIgnoreCase("Yes")){
                    destruction_medical_records_yes.setChecked(true);
                }else if (pojo.getDestruction_medical_records().equalsIgnoreCase("No")){
                    destruction_medical_records_no.setChecked(true);
                }
            }
            if (pojo.getFire_extinguisher_present() != null){
                fire_extinguisher_present = pojo.getFire_extinguisher_present();
                if (pojo.getFire_extinguisher_present().equalsIgnoreCase("Yes")){
                    fire_extinguisher_present_yes.setChecked(true);
                }else if (pojo.getFire_extinguisher_present().equalsIgnoreCase("No")){
                    fire_extinguisher_present_no.setChecked(true);
                }
            }


            if (pojo.getMedical_record_death_certificate_remark() != null){
                remark_medical_record_death_certificate.setImageResource(R.mipmap.remark_selected);
                remark1 = pojo.getMedical_record_death_certificate_remark();
            }
            if (pojo.getMedical_record_death_certificate_nc() != null){
                nc1 = pojo.getMedical_record_death_certificate_nc();

                if (nc1.equalsIgnoreCase("close")){
                    nc_medical_record_death_certificate.setImageResource(R.mipmap.nc);
                }else {
                    nc_medical_record_death_certificate.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getDocumented_maintaining_confidentiality_remark() != null){
                remark2 = pojo.getDocumented_maintaining_confidentiality_remark();

                remark_documented_maintaining_confidentiality.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDocumented_maintaining_confidentiality_nc() != null){
                nc2 = pojo.getDocumented_maintaining_confidentiality_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_documented_maintaining_confidentiality.setImageResource(R.mipmap.nc);
                }else {
                    nc_documented_maintaining_confidentiality.setImageResource(R.mipmap.nc_selected);
                }

            }


            if (pojo.getAny_information_disclosed_remark() != null){
                remark3 = pojo.getAny_information_disclosed_remark();

                remark_any_information_disclosed.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getAny_information_disclosed_nc() != null){
                nc3 = pojo.getAny_information_disclosed_nc();

                if (nc3.equalsIgnoreCase("close")){
                    nc_any_information_disclosed.setImageResource(R.mipmap.nc);
                }else {
                    nc_any_information_disclosed.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getDestruction_medical_records_remark() != null){
                remark4 = pojo.getDestruction_medical_records_remark();

                remark_destruction_medical_records.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getDestruction_medical_records_nc() != null){
                nc4 = pojo.getDestruction_medical_records_nc();

                if (nc4.equalsIgnoreCase("close")){
                    nc_destruction_medical_records.setImageResource(R.mipmap.nc);
                }else {
                    nc_destruction_medical_records.setImageResource(R.mipmap.nc_selected);
                }
            }

            if (pojo.getFire_extinguisher_present_remark() != null){
                remark5 = pojo.getFire_extinguisher_present_remark();

                remark_fire_extinguisher_present.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getFire_extinguisher_present_nc() != null){
                nc5 = pojo.getFire_extinguisher_present_nc();

                if (nc5.equalsIgnoreCase("close")){
                    nc_fire_extinguisher_present.setImageResource(R.mipmap.nc);
                }else {
                    nc_fire_extinguisher_present.setImageResource(R.mipmap.nc_selected);
                }


            }

            if (pojo.getFire_extinguisher_present_image() != null){
                image_fire_extinguisher_present.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getFire_extinguisher_present_image();

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

            if (pojo.getLocal_fire_extinguisher_present_image() != null){
                image_fire_extinguisher_present.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_fire_extinguisher_present_image();

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
            pojo = new MRDPojo();
        }

    }


    @OnClick({R.id.remark_medical_record_death_certificate,R.id.nc_medical_record_death_certificate,R.id.remark_documented_maintaining_confidentiality,R.id.nc_documented_maintaining_confidentiality,R.id.remark_any_information_disclosed,R.id.nc_any_information_disclosed,
            R.id.remark_destruction_medical_records,R.id.nc_destruction_medical_records,R.id.remark_fire_extinguisher_present,
            R.id.nc_fire_extinguisher_present,R.id.image_fire_extinguisher_present,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_medical_record_death_certificate:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_medical_record_death_certificate:
                displayNCDialog("NC", 1);
                break;

            case R.id.remark_documented_maintaining_confidentiality:
                displayCommonDialogWithHeader("Remark", 2);
                break;
            case R.id.nc_documented_maintaining_confidentiality:
                displayNCDialog("NC", 2);
                break;

            case R.id.remark_any_information_disclosed:
                displayCommonDialogWithHeader("Remark", 3);
                break;

            case R.id.nc_any_information_disclosed:
                displayNCDialog("NC", 3);
                break;


            case R.id.remark_destruction_medical_records:
                displayCommonDialogWithHeader("Remark", 4);
                break;

            case R.id.nc_destruction_medical_records:
                displayNCDialog("NC", 4);
                break;


                case R.id.remark_fire_extinguisher_present:
                displayCommonDialogWithHeader("Remark", 5);
                break;

            case R.id.nc_fire_extinguisher_present:
                displayNCDialog("NC", 5);
                break;


            case R.id.image_fire_extinguisher_present:
                if (Local_staffs_personal_files_maintained_list.size() > 0){
                    showImageListDialog(Local_staffs_personal_files_maintained_list,1,"fire_extinguisher_present");
                }else {
                    captureImage(1);
                }
                break;


            case R.id.btnSave:
                SavePharmacyData("save");
                break;

            case R.id.btnSync:
                if (medical_record_death_certificate.length() > 0 && documented_maintaining_confidentiality.length() > 0 && any_information_disclosed.length() > 0 && destruction_medical_records.length() > 0 && fire_extinguisher_present.length() > 0){
                    if (image1 != null){
                        SavePharmacyData("sync");
                    }else {
                        Toast.makeText(MRDActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MRDActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.medical_record_death_certificate_yes:
                if (checked)
                    medical_record_death_certificate = "Yes";
                break;

            case R.id.medical_record_death_certificate_no:
                if (checked)
                    medical_record_death_certificate = "No";
                break;

            case R.id.documented_maintaining_confidentiality_yes:
                documented_maintaining_confidentiality = "Yes";
                break;

            case R.id.documented_maintaining_confidentiality_no:
                documented_maintaining_confidentiality = "No";
                break;

            case R.id.any_information_disclosed_yes:
                if (checked)
                    any_information_disclosed = "Yes";
                break;
            case R.id.any_information_disclosed_no:
                if (checked)
                    any_information_disclosed = "No";
                break;


            case R.id.destruction_medical_records_yes:
                if (checked)
                    destruction_medical_records = "Yes";
                break;
            case R.id.destruction_medical_records_no:
                if (checked)
                    destruction_medical_records = "No";
                break;

            case R.id.fire_extinguisher_present_yes:
                fire_extinguisher_present = "Yes";
                break;

            case R.id.fire_extinguisher_present_no:
                fire_extinguisher_present = "No";
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MRDActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

                    ImageUpload(image2,"fire_extinguisher_present");

                }

            }
        }
    }

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(MRDActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_medical_record_death_certificate.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_medical_record_death_certificate.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(MRDActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")){
                                nc_documented_maintaining_confidentiality.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_documented_maintaining_confidentiality.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(MRDActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    } else if (position == 3) {
                        if (radio_status3 != null) {

                            if (radio_status3.equalsIgnoreCase("close")){
                                nc_any_information_disclosed.setImageResource(R.mipmap.nc);

                                nc3 = radio_status3 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status3.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_any_information_disclosed.setImageResource(R.mipmap.nc_selected);

                                    nc3 = radio_status3 + "," + edit_text.getText().toString();

                                    radio_status3 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(MRDActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                    else if (position == 4) {
                        if (radio_status4 != null) {

                            if (radio_status4.equalsIgnoreCase("close")){
                                nc_destruction_medical_records.setImageResource(R.mipmap.nc);

                                nc4 = radio_status4 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status4.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_destruction_medical_records.setImageResource(R.mipmap.nc_selected);

                                    nc4 = radio_status4 + "," + edit_text.getText().toString();

                                    radio_status4 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(MRDActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    else if (position == 5) {
                        if (radio_status5 != null) {

                            if (radio_status5.equalsIgnoreCase("close")) {
                                nc_fire_extinguisher_present.setImageResource(R.mipmap.nc);

                                nc5 = radio_status5;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status5.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_fire_extinguisher_present.setImageResource(R.mipmap.nc_selected);

                                    nc5 = radio_status5 + "," + edit_text.getText().toString();

                                    radio_status5 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(MRDActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(MRDActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_medical_record_death_certificate.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(MRDActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_documented_maintaining_confidentiality.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(MRDActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    } else if (position == 3) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark3 = edit_text.getText().toString();
                            remark_any_information_disclosed.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(MRDActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 4) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark4 = edit_text.getText().toString();
                            remark_destruction_medical_records.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(MRDActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    else if (position == 5) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark5 = edit_text.getText().toString();
                            remark_fire_extinguisher_present.setImageResource(R.mipmap.remark_selected);
                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(MRDActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }


                }
            });
            DialogLogOut.show();
        }
    }
    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(MRDActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(MRDActivity.this,list,from,"MRD");
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



    public void SavePharmacyData(String from){

        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(11);

        pojo.setMedical_record_death_certificate(medical_record_death_certificate);
        pojo.setDocumented_maintaining_confidentiality(documented_maintaining_confidentiality);
        pojo.setAny_information_disclosed(any_information_disclosed);
        pojo.setDestruction_medical_records(destruction_medical_records);
        pojo.setFire_extinguisher_present(fire_extinguisher_present);

        pojo.setMedical_record_death_certificate_remark(remark1);
        pojo.setMedical_record_death_certificate_nc(nc1);

        pojo.setDocumented_maintaining_confidentiality_remark(remark2);
        pojo.setDocumented_maintaining_confidentiality_nc(nc2);

        pojo.setAny_information_disclosed_remark(remark3);
        pojo.setAny_information_disclosed_nc(nc3);

        pojo.setDestruction_medical_records_remark(remark4);
        pojo.setDestruction_medical_records_nc(nc4);

        pojo.setFire_extinguisher_present_remark(remark5);
        pojo.setFire_extinguisher_present_nc(nc5);

        JSONObject json = new JSONObject();

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


        pojo.setFire_extinguisher_present_image(image1);
        pojo.setLocal_fire_extinguisher_present_image(Local_image1);

        if (sql_status){
            boolean sw_status = databaseHandler.UPDATE_MRD(pojo);

            if (sw_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(11).getHospital_id());
                    pojo.setAssessement_name("MRD");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(11).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(MRDActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MRDActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progreesDialog();
                    PostLaboratoryData();
                }

            }

        }else {
            boolean sw_status = databaseHandler.INSERT_MRD(pojo);
            if (sw_status){
                if (!from.equalsIgnoreCase("sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(11).getHospital_id());
                    pojo.setAssessement_name("MRD");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(11).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(MRDActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MRDActivity.this,HospitalListActivity.class);
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

                pojo_dataSync.setTabName("mrd");
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

                pojo.setFire_extinguisher_present_image(admissions_discharge_home_view);

                pojo_dataSync.setMrd(pojo);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                       CloseProgreesDialog();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(MRDActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(MRDActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(MRDActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("WardsEmergency_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(11).getHospital_id());
                                    pojo.setAssessement_name("MRD");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(11).getLocal_id());
                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(MRDActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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

    private void ImageUpload(final String image_path,final String from){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        final ProgressDialog d = ImageDialog.showLoading(MRDActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(MRDActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(MRDActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("fire_extinguisher_present")){
                                staffs_personal_files_maintained_list.add(response.body().getMessage());
                                Local_staffs_personal_files_maintained_list.add(image_path);
                                image_fire_extinguisher_present.setImageResource(R.mipmap.camera_selected);

                                image1 = "fire_extinguisher_present";
                            }

                            Toast.makeText(MRDActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(MRDActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MRDActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(MRDActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("fire_extinguisher_present")){
                staffs_personal_files_maintained_list.remove(position);
                Local_staffs_personal_files_maintained_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (Local_staffs_personal_files_maintained_list.size() == 0){
                    image_fire_extinguisher_present.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(11).getAssessement_status().equalsIgnoreCase("Done")){
            SavePharmacyData("save");
        }else {
            Intent intent = new Intent(MRDActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
