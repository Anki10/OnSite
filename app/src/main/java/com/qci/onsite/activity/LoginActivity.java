package com.qci.onsite.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qci.onsite.MainActivity;
import com.qci.onsite.R;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.pojo.LoginRequest;
import com.qci.onsite.pojo.LoginResponse;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;
import com.qci.onsite.util.ConnectionDetector;
import com.qci.onsite.util.PermissionResultCallback;
import com.qci.onsite.util.PermissionUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 21-01-2019.
 */

public class LoginActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback {

    @BindView(R.id.login_userName)
    EditText login_userName;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    private TextView tv_app_version;


    private APIService mAPIService;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    PermissionUtils permissionUtils;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    ArrayList<String> permissions=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        mAPIService = ApiUtils.getAPIService();



        permissionUtils=new PermissionUtils(LoginActivity.this);

        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        permissionUtils.check_permission(permissions,"All the permissions are required to access the app functionality",1);


        if (getFromPrefs(AppConstant.Login_status).length() > 0){
            if (getFromPrefs(AppConstant.Login_status).equalsIgnoreCase("Login")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        tv_app_version = (TextView) findViewById(R.id.tv_app_version);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tv_app_version.setText("App Version : "+ version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        /*login_userName.setText("ahana@gmail.com");
        login_password.setText("Password1");*/

      /*  login_userName.setText("qciassesser@gmail.com");
        login_password.setText("Password1");*/
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked(){
        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent){
            if (login_userName.getText().toString().length() == 0){
                Toast.makeText(LoginActivity.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
            } else if (login_password.getText().toString().length() == 0){
                Toast.makeText(LoginActivity.this,"Please enter the password",Toast.LENGTH_LONG).show();
            } else {
                LoginApi();
            }

        } else {
            Toast.makeText(LoginActivity.this, AppConstant.NO_INTERNET_CONNECTED,Toast.LENGTH_LONG).show();
        }
    }


    private void LoginApi(){
        LoginRequest request = new LoginRequest();
        request.setUserName(login_userName.getText().toString());
        request.setPassword(login_password.getText().toString());
        request.setGrant_type("password");
        request.setRefreshToken("");

        final ProgressDialog d = AppDialog.showLoading(LoginActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.loginrequest("application/json",request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                d.dismiss();

                try {
                    if (response.body() != null){
                        if (response.body().getAccessToken() != null){
                            saveIntoPrefs(AppConstant.ACCESS_Token,response.body().getAccessToken());
                        }

                        saveIntoPrefs(AppConstant.ASSESSOR_ID, String.valueOf(response.body().getAssessor().getId()));


                        saveIntoPrefs(AppConstant.Login_status,"Login");

                        System.out.println("xxx"+response.body().getAccessToken());
                    }

                    if (response.body() != null){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"Please try again",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"Please try again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                d.dismiss();
            }
        });
    }

    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

}
