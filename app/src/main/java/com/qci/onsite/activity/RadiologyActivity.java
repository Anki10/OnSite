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
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageDialog;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.LaboratoryPojo;
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
 * Created by Ankit on 07-02-2019.
 */

public class RadiologyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.radiology_yes)
    RadioButton radiology_yes;

    @BindView(R.id.radiology_no)
    RadioButton radiology_no;

    @BindView(R.id.remark_radiology)
    ImageView remark_radiology;

    @BindView(R.id.nc_radiology)
    ImageView nc_radiology;

    @BindView(R.id.image_radiology)
    ImageView image_radiology;

    @BindView(R.id.radiology_defined_turnaround_yes)
    RadioButton radiology_defined_turnaround_yes;

    @BindView(R.id.radiology_defined_turnaround_no)
    RadioButton radiology_defined_turnaround_no;

    @BindView(R.id.remark_radiology_defined_turnaround)
    ImageView remark_radiology_defined_turnaround;

    @BindView(R.id.nc_radiology_defined_turnaround)
    ImageView nc_radiology_defined_turnaround;

    @BindView(R.id.image_radiology_defined_turnaround)
    ImageView image_radiology_defined_turnaround;

    @BindView(R.id.hospiatl_center)
    TextView hospiatl_center;

    @BindView(R.id.btnSave)
    Button btnSave;

    private String remark1,remark2;

    private Dialog dialogLogout;

    private ImageShowAdapter image_adapter;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String nc1,nc2;
    private String radiology_status="",radiology_defined_turnaround="";
    private String radio_status1,radio_status2;

    private DatabaseHandler databaseHandler;

    private String Image1;

    private RadioLogyPojo pojo;

    private ArrayList<String> radiology_list;
    private ArrayList<String> Local_radiology_list;
    private ArrayList<String>radiology_defined_turnaround_list;
    private ArrayList<String>Local_radiology_defined_turnaround_list;

    private String image1,image2;
    private String Local_image1,Local_image2;

    private APIService mAPIService;

    public Boolean sql_status = false;

    private String Hospital_id;

    DataSyncRequest pojo_dataSync;

    private String radiology = "",radiology_defined_turnaround_view = "";

    private ArrayList<AssessmentStatusPojo> assessement_list;
    
    int check;
    CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiology);

        ButterKnife.bind(this);

        setDrawerbackIcon("Radiology/Imaging");

        databaseHandler = DatabaseHandler.getInstance(this);

        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        pojo = new RadioLogyPojo();

        mAPIService = ApiUtils.getAPIService();

        radiology_list = new ArrayList<>();
        Local_radiology_list = new ArrayList<>();
        radiology_defined_turnaround_list = new ArrayList<>();
        Local_radiology_defined_turnaround_list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        assessement_list = new ArrayList<>();

        hospiatl_center.setText(getFromPrefs(AppConstant.Hospital_Name));

        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        getLaboratoryData();

    }

    public void getLaboratoryData(){

        pojo = databaseHandler.getRadioLogyPojo("2");

        if (pojo != null){
            sql_status = true;
            if (pojo.getRADIOLOGY_Appropriate_safety_equipment() != null){
                radiology_status = pojo.getRADIOLOGY_Appropriate_safety_equipment();
                if (pojo.getRADIOLOGY_Appropriate_safety_equipment().equalsIgnoreCase("Yes")){
                    radiology_yes.setChecked(true);
                }else if (pojo.getRADIOLOGY_Appropriate_safety_equipment().equalsIgnoreCase("No")){
                    radiology_no.setChecked(true);
                }
            }

            if (pojo.getRADIOLOGY_defined_turnaround() != null){
                radiology_defined_turnaround = pojo.getRADIOLOGY_defined_turnaround();
                if (pojo.getRADIOLOGY_defined_turnaround().equalsIgnoreCase("Yes")){
                    radiology_defined_turnaround_yes.setChecked(true);
                }else if (pojo.getRADIOLOGY_defined_turnaround().equalsIgnoreCase("No")){
                    radiology_defined_turnaround_no.setChecked(true);
                }
            }

            if (pojo.getRADIOLOGY_Appropriate_safety_equipment_remark() != null){
                remark1 = pojo.getRADIOLOGY_Appropriate_safety_equipment_remark();

                remark_radiology.setImageResource(R.mipmap.remark_selected);
            }

            if (pojo.getRADIOLOGY_defined_turnaround_remark() != null){
                remark2 = pojo.getRADIOLOGY_defined_turnaround_remark();

                remark_radiology_defined_turnaround.setImageResource(R.mipmap.remark_selected);
            }
            if (pojo.getRADIOLOGY_Appropriate_safety_equipment_NC() != null){
                nc1 = pojo.getRADIOLOGY_Appropriate_safety_equipment_NC();

                if (nc1.equalsIgnoreCase("close")){
                    nc_radiology.setImageResource(R.mipmap.nc);
                }else {
                    nc_radiology.setImageResource(R.mipmap.nc_selected);
                }

            }

            if (pojo.getRADIOLOGY_defined_turnaround_nc() != null){
                nc2 = pojo.getRADIOLOGY_defined_turnaround_nc();

                if (nc2.equalsIgnoreCase("close")){
                    nc_radiology_defined_turnaround.setImageResource(R.mipmap.nc);
                }else {
                    nc_radiology_defined_turnaround.setImageResource(R.mipmap.nc_selected);
                }


            }


            if (pojo.getRADIOLOGY_Appropriate_safety_equipment_image() != null){
                image_radiology.setImageResource(R.mipmap.camera_selected);

                image1 = pojo.getRADIOLOGY_Appropriate_safety_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            radiology_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_RADIOLOGY_Appropriate_safety_equipment_image() != null){
                image_radiology.setImageResource(R.mipmap.camera_selected);

                Local_image1 = pojo.getLocal_RADIOLOGY_Appropriate_safety_equipment_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image1);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_radiology_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getRADIOLOGY_defined_turnaround_image() != null){
                image_radiology_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                image2 = pojo.getRADIOLOGY_defined_turnaround_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            radiology_defined_turnaround_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (pojo.getLocal_RADIOLOGY_defined_turnaround_image() != null){
                image_radiology_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                Local_image2 = pojo.getLocal_RADIOLOGY_defined_turnaround_image();

                JSONObject json = null;
                try {
                    json = new JSONObject(Local_image2);
                    JSONArray jArray = json.optJSONArray("uniqueArrays");
                    if (jArray != null){
                        for (int i=0;i<jArray.length();i++){
                            Local_radiology_defined_turnaround_list.add(jArray.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else {
            pojo = new RadioLogyPojo();
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(RadiologyActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
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


                    SaveImage(image2,"radiology");

                }
            }
            if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);


                    SaveImage(image2,"radiology_defined_turnaround");

                }
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radiology_yes:
                if (checked)
                    radiology_status = "Yes";
                break;

            case R.id.radiology_no:
                if (checked)
                    radiology_status = "No";
                break;

            case R.id.radiology_defined_turnaround_yes:
                if (checked)
                    radiology_defined_turnaround = "Yes";
                break;

            case R.id.radiology_defined_turnaround_no:
                if (checked)
                    radiology_defined_turnaround = "No";
                break;

        }
    }


    public void showImageListDialog(ArrayList<String> list, final int position, String from) {
        dialogLogout = new Dialog(RadiologyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);
        Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
        Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
        ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
        RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

        dialogLogout.show();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        image_recycler_view.setLayoutManager(gridLayoutManager);

        image_adapter = new ImageShowAdapter(RadiologyActivity.this,list,from,"Radiology");
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
                    Toast toast = Toast.makeText(RadiologyActivity.this, "You cannot upload more than 2 images.", Toast.LENGTH_SHORT);
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

    public void displayNCDialog(final String header, final int position) {
        Button OkButtonLogout;
        final Dialog DialogLogOut = new Dialog(RadiologyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

                            if (radio_status1.equalsIgnoreCase("close")){
                                nc_radiology.setImageResource(R.mipmap.nc);

                                nc1 = radio_status1 ;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }else {
                                if (radio_status1.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0){
                                    nc_radiology.setImageResource(R.mipmap.nc_selected);

                                    nc1 = radio_status1 + "," + edit_text.getText().toString();

                                    radio_status1 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                }else {
                                    Toast.makeText(RadiologyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }else if (position == 2) {
                        if (radio_status2 != null) {

                            if (radio_status2.equalsIgnoreCase("close")) {
                                nc_radiology_defined_turnaround.setImageResource(R.mipmap.nc);

                                nc2 = radio_status2;

                                DialogLogOut.dismiss();

                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            } else {
                                if (radio_status2.equalsIgnoreCase("Yes") & edit_text.getText().toString().length() > 0) {
                                    nc_radiology_defined_turnaround.setImageResource(R.mipmap.nc_selected);

                                    nc2 = radio_status2 + "," + edit_text.getText().toString();

                                    radio_status2 = "";

                                    DialogLogOut.dismiss();

                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                } else {
                                    Toast.makeText(RadiologyActivity.this, "Please capture NC details", Toast.LENGTH_LONG).show();
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
        final Dialog DialogLogOut = new Dialog(RadiologyActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                            remark_radiology.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(RadiologyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                    if (position == 2) {
                        if (edit_text.getText().toString().length() > 0) {
                            remark2 = edit_text.getText().toString();
                            remark_radiology_defined_turnaround.setImageResource(R.mipmap.remark_selected);

                            DialogLogOut.dismiss();

                            getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        } else {
                            Toast.makeText(RadiologyActivity.this, "Please capture Remark details", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            });
            DialogLogOut.show();
        }
    }

    @OnClick({R.id.remark_radiology,R.id.image_radiology,R.id.nc_radiology,R.id.remark_radiology_defined_turnaround,
            R.id.nc_radiology_defined_turnaround,R.id.image_radiology_defined_turnaround,R.id.btnSave,R.id.btnSync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remark_radiology:
                displayCommonDialogWithHeader("Remark", 1);
                break;
            case R.id.nc_radiology:
                displayNCDialog("NC", 1);
                break;

            case R.id.image_radiology:
                if (Local_radiology_list.size() > 0){
                    showImageListDialog(Local_radiology_list,1,"radiology");
                }else {
                    captureImage(1);
                }

                break;
            case R.id.remark_radiology_defined_turnaround:
                displayCommonDialogWithHeader("Remark", 2);
                break;

            case R.id.nc_radiology_defined_turnaround:
                displayNCDialog("NC", 2);
                break;

            case R.id.image_radiology_defined_turnaround:
                if (Local_radiology_defined_turnaround_list.size() > 0){
                    showImageListDialog(Local_radiology_defined_turnaround_list,2,"radiology_defined_turnaround");
                }else {
                    captureImage(2);
                }

                break;
            case R.id.btnSave:
                SaveRadioLogyData("save");
                break;

            case R.id.btnSync:
                if (radiology_status.length() > 0 && radiology_defined_turnaround.length() > 0){
                    if (Local_image1 != null && Local_image2 != null){
                        SaveRadioLogyData("sync");
                    }else {
                        Toast.makeText(RadiologyActivity.this,AppConstant.Image_Missing,Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(RadiologyActivity.this,AppConstant.Question_Missing,Toast.LENGTH_LONG).show();

                }

                break;
        }
    }

    private void SaveRadioLogyData(String from){
     pojo.setHospital_name("Hospital1");
     pojo.setHospital_id("2");
         if (getFromPrefs("Radiology_tabId"+Hospital_id).length() > 0){
             pojo.setId(Long.parseLong(getFromPrefs("Radiology_tabId"+Hospital_id)));
         }else {
             pojo.setId(0);
         }

     pojo.setRADIOLOGY_Appropriate_safety_equipment(radiology_status);
     pojo.setRADIOLOGY_Appropriate_safety_equipment_remark(remark1);
     pojo.setRADIOLOGY_Appropriate_safety_equipment_NC(nc1);

        JSONObject json = new JSONObject();

        if (radiology_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(radiology_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image1 = json.toString();
        }else {
            image1 = null;
        }

        if (Local_radiology_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_radiology_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image1 = json.toString();
        }else {
            Local_image1 = null;
        }
        pojo.setRADIOLOGY_Appropriate_safety_equipment_image(image1);
        pojo.setLocal_RADIOLOGY_Appropriate_safety_equipment_image(Local_image1);



        pojo.setRADIOLOGY_defined_turnaround(radiology_defined_turnaround);
        pojo.setRADIOLOGY_defined_turnaround_remark(remark2);
        pojo.setRADIOLOGY_defined_turnaround_nc(nc2);


        if (radiology_defined_turnaround_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(radiology_defined_turnaround_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            image2 = json.toString();
        }else {
            image2 = null;
        }

        if (Local_radiology_defined_turnaround_list.size() > 0){
            try {
                json.put("uniqueArrays", new JSONArray(Local_radiology_defined_turnaround_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Local_image2 = json.toString();
        }else {
            Local_image2 = null;
        }

        pojo.setRADIOLOGY_defined_turnaround_image(image2);
        pojo.setLocal_RADIOLOGY_defined_turnaround_image(Local_image2);

        if(sql_status){
            boolean sqlite_status = databaseHandler.UPDATE_RADIOLOGy(pojo);

            if (sqlite_status){
                if (!from.equalsIgnoreCase("Sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(2).getHospital_id());
                    pojo.setAssessement_name("Radiology / Imaging");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(2).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(RadiologyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RadiologyActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    new PostDataTask().execute();
                }
            }
        }else {
            boolean sqlite_status = databaseHandler.INSERT_RADIOLOGy(pojo);

            if (sqlite_status){
                if (!from.equalsIgnoreCase("Sync")){
                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                    pojo.setHospital_id(assessement_list.get(2).getHospital_id());
                    pojo.setAssessement_name("Radiology / Imaging");
                    pojo.setAssessement_status("Draft");
                    pojo.setLocal_id(assessement_list.get(2).getLocal_id());

                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);

                    Toast.makeText(RadiologyActivity.this,"Your data saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RadiologyActivity.this,HospitalListActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    new PostDataTask().execute();

                }
            }

        }


    }

    private void SaveImage(final String image_path,final String from){
        if (from.equalsIgnoreCase("radiology")){
            //radiology_list.add(response.body().getMessage());
            Local_radiology_list.add(image_path);
            image_radiology.setImageResource(R.mipmap.camera_selected);

            Local_image1 = "radiology";

        }else if (from.equalsIgnoreCase("radiology_defined_turnaround")){

            //radiology_defined_turnaround_list.add(response.body().getMessage());
            Local_radiology_defined_turnaround_list.add(image_path);
            image_radiology_defined_turnaround.setImageResource(R.mipmap.camera_selected);

            Local_image2 = "radiology_defined_turnaround";
        }


        Toast.makeText(RadiologyActivity.this,"Image saved locally",Toast.LENGTH_LONG).show();

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
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RadiologyActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(RadiologyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                        }
                    });
                }else {
                    if (response.body() != null){
                        if (response.body().getSuccess()){

                            System.out.println("xxx scucess");

                            if (from.equalsIgnoreCase("radiology")){
                                radiology_list.add(response.body().getMessage());
                                //Local_radiology_list.add(image_path);
                                image_radiology.setImageResource(R.mipmap.camera_selected);

                                image1 = "radiology";

                            }else if (from.equalsIgnoreCase("radiology_defined_turnaround")){

                                radiology_defined_turnaround_list.add(response.body().getMessage());
                                //Local_radiology_defined_turnaround_list.add(image_path);
                                image_radiology_defined_turnaround.setImageResource(R.mipmap.camera_selected);

                                image2 = "radiology_defined_turnaround";
                            }

                            check = 1;
                            latch.countDown();
                        }else {
                            check = 0;
                            latch.countDown();
                        }
                    }else {
                        check = 0;
                        latch.countDown();
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


    private void DeleteList(int position,String from){
        try {
            if (from.equalsIgnoreCase("radiology")){
                Local_radiology_list.remove(position);
                radiology_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (radiology_list.size() == 0){
                    image_radiology.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }else if (from.equalsIgnoreCase("radiology_defined_turnaround")){
                Local_radiology_defined_turnaround_list.remove(position);
                radiology_defined_turnaround_list.remove(position);

                image_adapter.notifyItemRemoved(position);
                image_adapter.notifyDataSetChanged();

                if (radiology_defined_turnaround_list.size() == 0){
                    image_radiology_defined_turnaround.setImageResource(R.mipmap.camera);

                    dialogLogout.dismiss();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PostLaboratoryData(){
        check = 1;
        for(int i=radiology_list.size(); i<Local_radiology_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_radiology_list.get(i) + "radiology");
            UploadImage(Local_radiology_list.get(i),"radiology");
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
                        Toast.makeText(RadiologyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RadiologyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        for(int i=radiology_defined_turnaround_list.size(); i<Local_radiology_defined_turnaround_list.size(); i++)
        {
            latch = new CountDownLatch(1);
            Log.e("UploadImage",Local_radiology_defined_turnaround_list.get(i) + "radiology_defined_turnaround");
            UploadImage(Local_radiology_defined_turnaround_list.get(i),"radiology_defined_turnaround");
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
                        Toast.makeText(RadiologyActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
        if(check==0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RadiologyActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
                pojo_dataSync.setTabName("radiology");
                pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
                pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
                if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                    pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
                }else {
                    pojo_dataSync.setAssessment_id(0);
                }


                for (int i=0;i<radiology_list.size();i++){
                    String value_rail = radiology_list.get(i);

                    radiology = value_rail + radiology;
                }
                pojo.setRADIOLOGY_Appropriate_safety_equipment_image(radiology);

                for (int i=0;i<radiology_defined_turnaround_list.size();i++){
                    String value_transported = radiology_defined_turnaround_list.get(i);

                    radiology_defined_turnaround_view = value_transported + radiology_defined_turnaround_view;
                }

                pojo.setRADIOLOGY_defined_turnaround_image(radiology_defined_turnaround_view);


                pojo_dataSync.setRadioLogy(pojo);

                latch = new CountDownLatch(1);
                mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                    @Override
                    public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                        System.out.println("xxx sucess");

                        if (response.message().equalsIgnoreCase("Unauthorized")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(RadiologyActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(RadiologyActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else {
                            if (response.body() != null){
                                if (response.body().getSuccess()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(RadiologyActivity.this,HospitalListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    assessement_list = databaseHandler.getAssessmentList(Hospital_id);

                                    saveIntoPrefs("Radiology_tabId"+Hospital_id, String.valueOf(response.body().getTabId()));

                                    saveIntoPrefs("asmtId"+Hospital_id, String.valueOf(response.body().getAsmtId()));

                                    AssessmentStatusPojo pojo = new AssessmentStatusPojo();
                                    pojo.setHospital_id(assessement_list.get(2).getHospital_id());
                                    pojo.setAssessement_name("Radiology / Imaging");
                                    pojo.setAssessement_status("Done");
                                    pojo.setLocal_id(assessement_list.get(2).getLocal_id());

                                    databaseHandler.UPDATE_ASSESSMENT_STATUS(pojo);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RadiologyActivity.this,AppConstant.SYNC_MESSAGE,Toast.LENGTH_LONG).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!assessement_list.get(2).getAssessement_status().equalsIgnoreCase("Done")){
            SaveRadioLogyData("save");
        }else {
            Intent intent = new Intent(RadiologyActivity.this,HospitalListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
