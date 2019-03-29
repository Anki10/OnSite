package com.qci.onsite.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.qci.onsite.MainActivity;
import com.qci.onsite.R;
import com.qci.onsite.adapter.HospitalAdapter;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.AssessmentStatusPojo;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.LaboratoryPojo;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 21-01-2019.
 */

public class HospitalListActivity extends BaseActivity implements View.OnClickListener , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private ArrayList<String> list;
    @BindView(R.id.hospital_recycler_view)
    RecyclerView hospital_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private HospitalAdapter adapter;

     @BindView(R.id.hospital_name)
      TextView hospital_name;

     @BindView(R.id.tv_final_Submit)
     TextView tv_final_Submit;

     String Hospital_id;

    private DatabaseHandler databaseHandler;

    private ArrayList<AssessmentStatusPojo> assessement_list;

    private APIService mAPIService;

    DataSyncRequest pojo_dataSync;

    private Boolean Assess_Status = false;

    protected static final String TAG = "location-updates-sample";
    /**
     * 10秒間隔で位置情報を更新。実際には多少頻度が多くなるかもしれない。
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * 最速の更新間隔。この値より頻繁に更新されることはない。
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;


    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String LOCATION_KEY = "location-key";
    private final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 10;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    private String latitude;
    private String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        setDrawerbackIcon("Assessment Sections");

        ButterKnife.bind(this);

        databaseHandler = DatabaseHandler.getInstance(this);

        hospital_recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(HospitalListActivity.this);
        hospital_recycler_view.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();

        pojo_dataSync = new DataSyncRequest();

        mAPIService = ApiUtils.getAPIService();


        mRequestingLocationUpdates = false;


        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();


        Hospital_id = getFromPrefs(AppConstant.Hospital_ID);

        saveIntoPrefs(AppConstant.ASSESSSMENT_STATUS,Hospital_id);

        hospital_name.setText(getFromPrefs(AppConstant.Hospital_Name));

        startUpdatesButtonHandler();

        tv_final_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i= 0;i<assessement_list.size();i++){
                    if (assessement_list.get(i).getAssessement_status().equalsIgnoreCase("Done")){
                        Assess_Status = true;
                    }else {
                        Assess_Status = false;
                    }
                }
                if (Assess_Status){
                    PostAssessmentData();
                }else {
                    Toast.makeText(HospitalListActivity.this,"Please fill and sync all the above sections before marking the assessment as complete",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void getDBSET() {

        String[] Assessement_Service = getResources().getStringArray(R.array.assessement_service);

        list = new ArrayList<>(Arrays.asList(Assessement_Service));


        AssessmentStatusPojo pojo;

        String assessment_status = getFromPrefs(AppConstant.ASSESSMENT_STATUS);

        System.out.println("xxx" + assessment_status);
        assessement_list = databaseHandler.getAssessmentList(Hospital_id);

        if (assessement_list.size() == 0) {
            for (int i = 0; i < list.size(); i++) {

                pojo = new AssessmentStatusPojo();
                pojo.setHospital_id(Integer.parseInt(Hospital_id));
                pojo.setAssessement_name(list.get(i));
                pojo.setAssessement_status("Start");

                assessement_list.add(pojo);

                saveIntoPrefs(AppConstant.ASSESSMENT_STATUS, "Start");
            }

            Boolean status = databaseHandler.INSERT_ASSESSMENT_STATUS(assessement_list);

            System.out.println("xxx" + status);
        }

        adapter = new HospitalAdapter(HospitalListActivity.this,assessement_list);
        hospital_recycler_view.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_hospital:

                int position = (int) view.getTag(R.string.key_hospital);

                String hospital_name = list.get(position);

                if (hospital_name.equalsIgnoreCase("General Details")) {
                    if (getFromPrefs(AppConstant.General_status).length() == 0 ){
                        Intent intent = new Intent(HospitalListActivity.this, GeneralDetailsActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(HospitalListActivity.this,"You have already sync the data of this session",Toast.LENGTH_LONG).show();
                    }

                }else {
                        if (hospital_name.equalsIgnoreCase("Laboratory")){
                                Intent intent = new Intent(HospitalListActivity.this,LaboratoryActivity.class);
                                intent.putExtra("Hospital_id",Hospital_id);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Radiology / Imaging")){
                                Intent intent = new Intent(HospitalListActivity.this,RadiologyActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Emergency")){
                                Intent intent = new Intent(HospitalListActivity.this,EmergencyActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("High dependency Areas")){
                                Intent intent = new Intent(HospitalListActivity.this,HighDependencyActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Obstetric Ward NICU Paediatric")){
                                Intent intent = new Intent(HospitalListActivity.this,ObstetricWardActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("OT/ICU")){
                                Intent intent = new Intent(HospitalListActivity.this,OTActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Wards and Pharmacy")){
                                Intent intent = new Intent(HospitalListActivity.this,PharmacyActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Patient/Staff Interview")){
                                Intent intent = new Intent(HospitalListActivity.this,PatientStaffInterviewActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (hospital_name.equalsIgnoreCase("Wards, OT, ICU, OPD, Emergency")) {
                                Intent intent = new Intent(HospitalListActivity.this,Ward_OT_EmergencyActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (hospital_name.equalsIgnoreCase("HRM")) {
                                Intent intent = new Intent(HospitalListActivity.this,HRMActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (hospital_name.equalsIgnoreCase("MRD")) {
                                Intent intent = new Intent(HospitalListActivity.this,MRDActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (hospital_name.equalsIgnoreCase("Housekeeping")) {
                                Intent intent = new Intent(HospitalListActivity.this,HouseKeepingActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Sterilization Area")){
                                Intent intent = new Intent(HospitalListActivity.this,SterilizationAreaActivity.class);
                                startActivity(intent);
                                finish();
                            }else if(hospital_name.equalsIgnoreCase("Management")){
                                Intent intent = new Intent(HospitalListActivity.this,ManagementActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Maintenance/Bio-medical engineering")){
                                Intent intent = new Intent(HospitalListActivity.this,BioMedicalEngineeringActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Maintenance/Facility Checks")){
                                Intent intent = new Intent(HospitalListActivity.this,FacilityChecksActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Safety Management")){
                                Intent intent = new Intent(HospitalListActivity.this,SafetyManagementActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Uniform Signage")){
                                Intent intent = new Intent(HospitalListActivity.this,UniformSignageActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Ambulance Accessibility")){
                                Intent intent = new Intent(HospitalListActivity.this,AmbulanceAccessibilityActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (hospital_name.equalsIgnoreCase("Documentation")){
                                Intent intent = new Intent(HospitalListActivity.this,DocumentationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                }


                break;
        }
    }

    private void PostAssessmentData(){

            pojo_dataSync.setTabName("assessmentcompleted");
            pojo_dataSync.setHospital_id(Integer.parseInt(Hospital_id));
            pojo_dataSync.setAssessor_id(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));
            if (getFromPrefs("asmtId"+Hospital_id).length() > 0){
                pojo_dataSync.setAssessment_id(Integer.parseInt(getFromPrefs("asmtId"+Hospital_id)));
            }else {
                pojo_dataSync.setAssessment_id(0);
            }


            final ProgressDialog d = AppDialog.showLoading(HospitalListActivity.this);
            d.setCanceledOnTouchOutside(false);

            mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),pojo_dataSync).enqueue(new Callback<DataSyncResponse>() {
                @Override
                public void onResponse(Call<DataSyncResponse> call, Response<DataSyncResponse> response) {
                    System.out.println("xxx sucess");

                    d.dismiss();

                    if (response.message().equalsIgnoreCase("Unauthorized")) {
                        Intent intent = new Intent(HospitalListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(HospitalListActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                    }else {
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                Intent intent = new Intent(HospitalListActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                                saveIntoPrefs(AppConstant.ASSESSSMENT_STATUS,"");

                                databaseHandler.removeAll();
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
        }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }


        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startUpdatesButtonHandler() {
        if (!isPlayServicesAvailable(this)) return;
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
        } else {
            return;
        }

        if (Build.VERSION.SDK_INT < 23) {
            startLocationUpdates();
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRationaleDialog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }

    public void stopUpdatesButtonHandler() {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates");

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        // 現在位置の取得の前に位置情報の設定が有効になっているか確認する
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // 設定が有効になっているので現在位置を取得する
                        if (ContextCompat.checkSelfPermission(HospitalListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, HospitalListActivity.this);
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // 設定が有効になっていないのでダイアログを表示する
                        try {
                            status.startResolutionForResult(HospitalListActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
    protected void stopLocationUpdates() {
        Log.i(TAG, "stopLocationUpdates");
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        mRequestingLocationUpdates = false;
                        Toast.makeText(HospitalListActivity.this, "このアプリの機能を有効にするには端末の設定画面からアプリの位置情報パーミッションを有効にして下さい。", Toast.LENGTH_SHORT).show();
                    } else {
                        showRationaleDialog();
                    }
                }
                break;
            }
        }
    }

    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setPositiveButton("許可する", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HospitalListActivity.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                })
                .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HospitalListActivity.this, "位置情報パーミッションが許可されませんでした。", Toast.LENGTH_SHORT).show();
                        mRequestingLocationUpdates = false;
                    }
                })
                .setCancelable(false)
                .setMessage("このアプリは位置情報の利用を許可する必要があります。")
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();

        getDBSET();

        try {
            isPlayServicesAvailable(this);

            // Within {@code onPause()}, we pause location updates, but leave the
            // connection to GoogleApiClient intact.  Here, we resume receiving
            // location updates if the user has requested them.



            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean isPlayServicesAvailable(Context context) {
        // Google Play Service APKが有効かどうかチェックする
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog((Activity) context, resultCode, 2).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        try {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {

        try {
            stopUpdatesButtonHandler();

            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }


        super.onStop();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged");
        mCurrentLocation = location;

        latitude = String.valueOf(mCurrentLocation.getLatitude());
        longitude = String.valueOf(mCurrentLocation.getLongitude());

        saveIntoPrefs(AppConstant.Latitude, String.valueOf(mCurrentLocation.getLatitude()));
        saveIntoPrefs(AppConstant.Longitude, String.valueOf(mCurrentLocation.getLongitude()));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
