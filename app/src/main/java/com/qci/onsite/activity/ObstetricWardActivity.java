package com.qci.onsite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.qci.onsite.pojo.OBSTETRIC_WARD_Pojo;
import com.qci.onsite.pojo.RadioLogyPojo;
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

/**
 * Created by Ankit on 11-02-2019.
 */

public class ObstetricWardActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.neonate_abduction_yes)
    RadioButton neonate_abduction_yes;

    @BindView(R.id.neonate_abduction_no)
    RadioButton neonate_abduction_no;

    @BindView(R.id.remark_obstetricWard)
    ImageView remark_obstetricWard;

    @BindView(R.id.nc_obstetricWard)
    ImageView nc_obstetricWard;

    @BindView(R.id.image_obstetricWard)
    ImageView image_obstetricWard;

    @BindView(R.id.hospital_center)
    TextView hospital_center;

    @BindView(R.id.SHCO_Obstetrics_ward_yes)
    RadioButton SHCO_Obstetrics_ward_yes;

    @BindView(R.id.SHCO_Obstetrics_ward__no)
    RadioButton SHCO_Obstetrics_ward__no;

    @BindView(R.id.remark_SHCO_Obstetrics_ward)
    ImageView remark_SHCO_Obstetrics_ward;

    @BindView(R.id.nc_SHCO_Obstetrics_ward)
    ImageView nc_SHCO_Obstetrics_ward;

    @BindView(R.id.image_SHCO_Obstetrics_ward)
    ImageView image_SHCO_Obstetrics_ward;

    @BindView(R.id.ll_SHCO_Obstetrics_ward)
    LinearLayout ll_SHCO_Obstetrics_ward;



    private String remark1,remark2;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String nc1,nc2;
    private String radiology_status = "",SHCO_Obstetrics_ward_status = "";
    private String radio_status1,radio_status2;

    private DatabaseHandler databaseHandler;

    private OBSTETRIC_WARD_Pojo pojo;

    private ArrayList<String> identification_list,SHCO_Obstetrics_ward_list;
    private ArrayList<String> Local_identification_list,Local_SHCO_Obstetrics_ward_listt;


    private String image1,image2;
    private String Local_image1,Local_image2;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    DataSyncRequest pojo_dataSync;

    private String Hospital_id;

    private String identification = "",WARD_abuse = "";

    int Bed_no = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obstetricward_activity);

        ButterKnife.bind(this);

        setDrawerbackIcon("Obstetric Ward NICU Paediatric");

        mAPIService = ApiUtils.getAPIService();

        databaseHandler = DatabaseHandler.getInstance(this);

        identification_list = new ArrayList<>();
        Local_identification_list = new ArrayList<>();

        SHCO_Obstetrics_ward_list = new ArrayList<>();
        Local_SHCO_Obstetrics_ward_listt = new ArrayList<>();

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        hospital_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        Bed_no = getINTFromPrefs("Hospital_bed");

        if (Bed_no < 51){
            ll_SHCO_Obstetrics_ward.setVisibility(View.VISIBLE);
        }else {
            ll_SHCO_Obstetrics_ward.setVisibility(View.GONE);
        }

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getObstetricWardData();

    }

    public void getObstetricWardData(){

        pojo = databaseHandler.getObstetricPojo("5");

        if (pojo != null){
            sql_status = true;
            if (pojo.getOBSTETRIC_WARD_abuse() != null){
                radiology_status = pojo.getOBSTETRIC_WARD_abuse();
                if (pojo.getOBSTETRIC_WARD_abuse().equalsIgnoreCase("Yes")){
                    neonate_abduction_yes.setChecked(true);
                }else if (pojo.getOBSTETRIC_WARD_abuse().equalsIgnoreCase("No")){
                    neonate_abduction_no.setChecked(true);
                }
            }
            if (pojo.getShco_obstetrics_ward() != null){
                SHCO_Obstetrics_ward_status = pojo.getShco_obstetrics_ward();

                if (pojo.getShco_obstetrics_ward().equalsIgnoreCase("Yes")){
                    SHCO_Obstetrics_ward_yes.setChecked(true);
                }else if (pojo.getShco_obstetrics_ward().equalsIgnoreCase("No")){
                    SHCO_Obstetrics_ward__no.setChecked(true);
                }
            }

            if (pojo.getOBSTETRIC_WARD_abuse_remark() != null){
                remark1 = pojo.getOBSTETRIC_WARD_abuse_remark();

                remark_obstetricWard.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getShco_obstetrics_ward_remark() != null){
                remark2 = pojo.getShco_obstetrics_ward_remark();

                remark_SHCO_Obstetrics_ward.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getOBSTETRIC_WARD_abuse_NC() != null){
                nc1 = pojo.getOBSTETRIC_WARD_abuse_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_obstetricWard.setImageResource(R.mipmap.nc);
                }else {
                    nc_obstetricWard.setImageResource(R.mipmap.nc_selected);
                }
            }
            if (pojo.getShco_obstetrics_ward_nc() != null){
                nc2 = pojo.getShco_obstetrics_ward_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_SHCO_Obstetrics_ward.setImageResource(R.mipmap.nc);
                }else {
                    nc_SHCO_Obstetrics_ward.setImageResource(R.mipmap.nc_selected);
                }
            }


            if (pojo.getOBSTETRIC_WARD_abuse_image_identification() != null){
                image_obstetricWard.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getOBSTETRIC_WARD_abuse_image_identification();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            identification_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_OBSTETRIC_WARD_abuse_image_identification() != null){
                image_obstetricWard.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_OBSTETRIC_WARD_abuse_image_identification();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_identification_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getShco_obstetrics_ward_image() != null){
                image_SHCO_Obstetrics_ward.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getShco_obstetrics_ward_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            SHCO_Obstetrics_ward_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_shco_obstetrics_ward_image() != null){
                image_SHCO_Obstetrics_ward.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_shco_obstetrics_ward_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_SHCO_Obstetrics_ward_listt.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else {
            pojo = new OBSTETRIC_WARD_Pojo();
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(ObstetricWardActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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

    // // Local_SHCO_Obstetrics_ward_listt

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2, "obstetricWard");
                }

            }
            if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUpload(image2, "SHCO_Obstetrics_ward");
                }

            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.neonate_abduction_yes:
                if (checked)
                    radiology_status = "Yes";
                break;

            case R.id.neonate_abduction_no:
                if (checked)
                    radiology_status = "No";
                break;

            case R.id.SHCO_Obstetrics_ward_yes:
                 if (checked)
                     SHCO_Obstetrics_ward_status = "Yes";
                break;

            case R.id.SHCO_Obstetrics_ward__no:
                  if (checked)
                      SHCO_Obstetrics_ward_status = "No";
                break;


        }
    }


    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(ObstetricWardActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(ObstetricWardActivity.this,list,from,"obstetric");
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

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(ObstetricWardActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                                nc_obstetricWard.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_obstetricWard.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ObstetricWardActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }
                         }
                        }

                    if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")) {
                                nc_SHCO_Obstetrics_ward.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_SHCO_Obstetrics_ward.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(ObstetricWardActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(ObstetricWardActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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



            OkButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark1 = edit_text.getText().toString();
                            remark_obstetricWard.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ObstetricWardActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_SHCO_Obstetrics_ward.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(ObstetricWardActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }

    @OnClick({R.id.remark_obstetricWard,R.id.image_obstetricWard,R.id.nc_obstetricWard,R.id.btnSave,R.id.btnSync,
    R.id.remark_SHCO_Obstetrics_ward,R.id.nc_SHCO_Obstetrics_ward,R.id.image_SHCO_Obstetrics_ward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_obstetricWard:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_obstetricWard:
                displayNCDialog("NC", 1);
                break;

            case R.id.image_obstetricWard:
                if (Local_identification_list.size() > 0){
                    showImageListDialog(Local_identification_list,1,"Obstetric_Ward");
                }else {
                    captureImage(1);
                }
                break;

            case R.id.remark_SHCO_Obstetrics_ward:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_SHCO_Obstetrics_ward:
                displayNCDialog("NC", 2);
                break;

            case R.id.image_SHCO_Obstetrics_ward:
                if (Local_SHCO_Obstetrics_ward_listt.size() > 0){
                    showImageListDialog(Local_SHCO_Obstetrics_ward_listt,2,"SHCO_Obstetrics_ward");
                }else {
                    captureImage(2);
                }
                break;

            case R.id.btnSave:
                SaveRadioLogyData("save");
                break;

            case R.id.btnSync:
                if (Bed_no < 51){
                    PostSHCO_LaboratoryData();
                }else {
                    PostLaboratoryData();
                }

                break;
        }
    }

    private void SaveRadioLogyData(String from){
        pojo.setHospital_name("Hospital1");
        pojo.setHospital_id(5);
        if (getFromPrefs("Obstetric_tabId"+Hospital_id).length() > 0){
            pojo.setId(Long.parseLong(getFromPrefs("Obstetric_tabId"+Hospital_id)));
        }else {
            pojo.setId(0);
        }
        pojo.setOBSTETRIC_WARD_abuse(radiology_status);
        pojo.setOBSTETRIC_WARD_abuse_remark(remark1);
        pojo.setOBSTETRIC_WARD_abuse_NC(nc1);

        pojo.setShco_obstetrics_ward(SHCO_Obstetrics_ward_status);
        pojo.setShco_obstetrics_ward_remark(remark2);
        pojo.setShco_obstetrics_ward_nc(nc2);

        JSONObject json = new JSONObject();

        if (identification_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(identification_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }
        if (Local_identification_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_identification_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }
        pojo.setOBSTETRIC_WARD_abuse_image_identification(image1);
        pojo.setLocal_OBSTETRIC_WARD_abuse_image_identification(Local_image1);


        if (SHCO_Obstetrics_ward_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(SHCO_Obstetrics_ward_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }
        if (Local_SHCO_Obstetrics_ward_listt.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_SHCO_Obstetrics_ward_listt));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }

        pojo.setShco_obstetrics_ward_image(image2);
        pojo.setLocal_shco_obstetrics_ward_image(Local_image2);



        if (sql_status){
            databaseHandler.UPDATE_OBSTETRIC_WARD(pojo);
        }else {
            boolean status = databaseHandler.INSERT_OBSTETRIC_WARD(pojo);
            System.out.println(status);
        }

        if (!from.equalsIgnoreCase("sync")){
            assessement_list = databaseHandler.getAssessmentList(Hospital_id);

            AssessmentStatusPojo pojo = new AssessmentStatusPojo();
            pojo.setHospital_id(assessement_list.get(5).getHospital_id());
            pojo.setAssessement_name("Obstetric Ward NICU Paediatric");
            pojo.setAssessement_status("Draft");
            pojo.setLocal_id(assessement_list.get(5).getLocal_id());

            databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);


            Toast.makeText(ObstetricWardActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ObstetricWardActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void PostLaboratoryData(){

        SaveRadioLogyData("sync");

        if (radiology_status.length() > 0 ){

            if (image1 != null){
                pojo_dataSync.setTabName("obstetricward");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<identification_list.size();i++){
                    String value_rail = identification_list.get(i);

                    identification = value_rail + identification;
                }
                pojo.setOBSTETRIC_WARD_abuse_image_identification(identification);



                pojo_dataSync.setObstetricWard(pojo);

                final ProgressDialog d = AppDialog.showLoading(ObstetricWardActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(ObstetricWardActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(ObstetricWardActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(ObstetricWardActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("Obstetric_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(5).getHospital_id());
                                    pojo.setAssessement_name("Obstetric Ward NICU Paediatric");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(5).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(ObstetricWardActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
                Toast.makeText(ObstetricWardActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(ObstetricWardActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
        }
    }

    private void PostSHCO_LaboratoryData(){

        SaveRadioLogyData("sync");

        if (radiology_status.length() > 0 && SHCO_Obstetrics_ward_status.length() > 0){

            if (image1 != null && image2 != null){
                pojo_dataSync.setTabName("obstetricward");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<identification_list.size();i++){
                    String value_rail = identification_list.get(i);

                    identification = value_rail + identification;
                }
                pojo.setOBSTETRIC_WARD_abuse_image_identification(identification);

                for (int i=0;i<SHCO_Obstetrics_ward_list.size();i++){

                   String value_ward = SHCO_Obstetrics_ward_list.get(i);

                    WARD_abuse = value_ward + WARD_abuse;
                }

                pojo.setShco_obstetrics_ward_image(WARD_abuse);



                pojo_dataSync.setObstetricWard(pojo);

                final ProgressDialog d = AppDialog.showLoading(ObstetricWardActivity.this);
                d.setCanceledOnTouchOutside(false);

                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        d.dismiss();

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            Intent intent = new Intent(ObstetricWardActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(ObstetricWardActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    Intent intent = new Intent(ObstetricWardActivity.this,HospitalListActivity.class);
                                    startActivity(intent);
                                    finish();

                                    saveIntoPrefs("Obstetric_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(5).getHospital_id());
                                    pojo.setAssessement_name("Obstetric Ward NICU Paediatric");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(5).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                                    Toast.makeText(ObstetricWardActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();
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
                Toast.makeText(ObstetricWardActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(ObstetricWardActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();
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

        final ProgressDialog d = ImageDialog.showLoading(ObstetricWardActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstant.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(ObstetricWardActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ObstetricWardActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("obstetricWard")){
                                identification_list.add(response.body().getMessage());
                                Local_identification_list.add(image_path);
                                image_obstetricWard.setImageResource(R.mipmap.camera_selected);
                            }else if (from.equalsIgnoreCase("SHCO_Obstetrics_ward")){
                                SHCO_Obstetrics_ward_list.add(response.body().getMessage());
                                Local_SHCO_Obstetrics_ward_listt.add(image_path);
                                image_SHCO_Obstetrics_ward.setImageResource(R.mipmap.camera_selected);
                            }

                            Toast.makeText(ObstetricWardActivity.this,"Image upload successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(ObstetricWardActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(ObstetricWardActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                System.out.println("xxx fail");

                d.cancel();

                Toast.makeText(ObstetricWardActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
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
            if (from.equalsIgnoreCase("Obstetric_Ward")){
                identification_list.remove(position);
                Local_identification_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (identification_list.size() == 0){
                    image_obstetricWard.setImageResource(R.mipmap.camera);

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

        if (!assessement_list.get(5).getAssessement_status().equalsIgnoreCase("Done")){
            SaveRadioLogyData("save");
        }else {
            Intent intent = new Intent(ObstetricWardActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
