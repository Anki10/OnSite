package com.qci.onsite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.qci.onsite.activity.BaseActivity;
import com.qci.onsite.activity.HospitalListActivity;
import com.qci.onsite.activity.LaboratoryActivity;
import com.qci.onsite.activity.LoginActivity;
import com.qci.onsite.adapter.HospitalAdapter;
import com.qci.onsite.adapter.MainHospitalListAdapter;
import com.qci.onsite.api.APIService;
import com.qci.onsite.api.ApiUtils;
import com.qci.onsite.pojo.AllocatedHospitalListPojo;
import com.qci.onsite.pojo.AllocatedHospitalResponse;
import com.qci.onsite.pojo.AssesorAccept_RejectResponse;
import com.qci.onsite.util.AppConstant;
import com.qci.onsite.util.AppDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<AllocatedHospitalListPojo> list_hospital;
    @BindView(R.id.hospital_list_recycler_view)
    RecyclerView hospital_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainHospitalListAdapter adapter;

    private AllocatedHospitalListPojo hospitallistPojo;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        hospital_list_recycler_view.setHasFixedSize(true);
        setDrawerAndToolbar("Hospital List");

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        hospital_list_recycler_view.setLayoutManager(mLayoutManager);

        hospitallistPojo = new AllocatedHospitalListPojo();

        list_hospital = new ArrayList<>();

        mAPIService = ApiUtils.getAPIService();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getHospitalList();
    }

    private void getHospitalList(){

        final ProgressDialog d = AppDialog.showLoading(MainActivity.this);
        d.setCanceledOnTouchOutside(false);

        String token = getFromPrefs(AppConstant.ACCESS_Token);

        String Ass_id = getFromPrefs(AppConstant.ASSESSOR_ID);

        System.out.println("xxx"+Ass_id + token);

        mAPIService.getHospitalList("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),ApiUtils.BASE_URL + "assessors/"+getFromPrefs(AppConstant.ASSESSOR_ID)+"/hospital").enqueue(new Callback<AllocatedHospitalResponse>() {
            @Override
            public void onResponse(Call<AllocatedHospitalResponse> call, Response<AllocatedHospitalResponse> response) {

                d.cancel();
                if (response.message().equalsIgnoreCase("Unauthorized")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                    saveIntoPrefs(AppConstant.Login_status,"logout");

                    Toast.makeText(MainActivity.this, "Application seems to be logged in using some other device also. Please login again to upload pictures.", Toast.LENGTH_LONG).show();
                }else {
                    if (response.body() != null){
                        if (response.body().getRows() != null){
                            list_hospital = response.body().getRows();

                            adapter = new MainHospitalListAdapter(MainActivity.this,list_hospital);
                            hospital_list_recycler_view.setAdapter(adapter);
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<AllocatedHospitalResponse> call, Throwable t) {
              d.cancel();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_hospital_main:

                int position = (int) view.getTag(R.string.key_hospital_main);

                String hospital_id = String.valueOf(list_hospital.get(position).getHospitalid());

                int hospital_bedNo = list_hospital.get(position).getHospitalnoOfBed();

                SAVEINTPrefs("Hospital_bed",hospital_bedNo);

                String date = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(new Date());

                String Server_date =  getDate(list_hospital.get(position).getAssessmentdate(),"MM/dd/yyyy");

                System.out.println("xxx"+date);

                Date date1 = null;
                Date date2 = null;

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    date1 = df.parse(Server_date);
                    date2 = df.parse(date);
                    long diff = Math.abs(date1.getTime() - date2.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    System.out.println(diffDays);

                    String date_staus = String.valueOf(diffDays);

                    saveIntoPrefs(AppConstant.Hospital_Name,list_hospital.get(position).getHospitalname());

                    saveIntoPrefs(AppConstant.Hospital_ID, String.valueOf(list_hospital.get(position).getHospitalid()));

                    if (!list_hospital.get(position).getHospitalstage().equalsIgnoreCase("Onsite Assessor Allocated")){
                        if (date1.before(date2)){
                            if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).length() > 0){
                                if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).equalsIgnoreCase(hospital_id)){
                                    Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
                                    startActivity(intent);

                                    saveIntoPrefs("asmtId" + hospital_id, String.valueOf(list_hospital.get(position).getAssessment_id()));




                                }else {
                                    Toast.makeText(MainActivity.this,"Please complete and sync assessment which is currently 'In progress'. Once this assessment is completed, then you can start with new assessment.",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                if (date_staus.equalsIgnoreCase("1")){
                                    Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
                                    startActivity(intent);

                                    saveIntoPrefs("asmtId" + hospital_id, String.valueOf(list_hospital.get(position).getAssessment_id()));
                                }else if (date_staus.equalsIgnoreCase("2")){
                                    Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
                                    startActivity(intent);

                                    saveIntoPrefs("asmtId" + hospital_id, String.valueOf(list_hospital.get(position).getAssessment_id()));
                                }else {
                                    Toast.makeText(MainActivity.this,"You can't start assessment",Toast.LENGTH_LONG).show();
                                }

                            }

                        }else {
                          if (date_staus.equalsIgnoreCase("0")){
                                if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).length() > 0){
                                    if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).equalsIgnoreCase(hospital_id)){
                                        Intent intent = new Intent(MainActivity.this,HospitalListActivity.class);
                                        intent.putExtra("Hospital_id",hospital_id);
                                        startActivity(intent);

                                        saveIntoPrefs("asmtId"+hospital_id, String.valueOf(list_hospital.get(position).getAssessment_id()));

                                        saveIntoPrefs(AppConstant.Hospital_Name,list_hospital.get(position).getHospitalname());
                                    }else {
                                        Toast.makeText(MainActivity.this,"Please complete and sync assessment which is currently 'In progress'. Once this assessment is completed, then you can start with new assessment.",Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    Intent intent = new Intent(MainActivity.this,HospitalListActivity.class);
                                    intent.putExtra("Hospital_id",hospital_id);
                                    startActivity(intent);

                                    saveIntoPrefs("asmtId"+hospital_id, String.valueOf(list_hospital.get(position).getAssessment_id()));

                                    saveIntoPrefs(AppConstant.Hospital_Name,list_hospital.get(position).getHospitalname());
                                }


                            }else {
                                Toast.makeText(MainActivity.this,"Assessment alloted to the assessor shall not start before the alloted date - Assessment can be initiated on Allocated date of assessment only.",Toast.LENGTH_LONG).show();

                            }
                        }
                    }else {
                        Toast.makeText(MainActivity.this,"You can't start assessment",Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;


            case R.id.iv_hospital_accept:

                int pos_accept = (int) view.getTag(R.string.key_hospital_accept);

                Accept_Dialog(pos_accept);

                break;

            case R.id.iv_hospital_reject:

                int pos_reject = (int) view.getTag(R.string.key_hospital_reject);

                Reject_Dialog(pos_reject);

                break;
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }



    public void Accept_RejectApi(String status,int pos){
        hospitallistPojo = list_hospital.get(pos);
        hospitallistPojo.setStatuschangedby(Integer.parseInt(getFromPrefs(AppConstant.ASSESSOR_ID)));

        hospitallistPojo.setStatus(status);


        final ProgressDialog d = AppDialog.showLoading(MainActivity.this);
        d.setCanceledOnTouchOutside(false);


        mAPIService.PostAssessorResponse("application/json", "Bearer " + getFromPrefs(AppConstant.ACCESS_Token),hospitallistPojo).enqueue(new Callback<AssesorAccept_RejectResponse>() {
            @Override
            public void onResponse(Call<AssesorAccept_RejectResponse> call, Response<AssesorAccept_RejectResponse> response) {
                System.out.println("xxx response");

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        list_hospital.clear();
                        getHospitalList();
                        Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        list_hospital.clear();
                        getHospitalList();
                    }
                }
                d.dismiss();
            }

            @Override
            public void onFailure(Call<AssesorAccept_RejectResponse> call, Throwable t) {
                System.out.println("xxxx fail");

                d.dismiss();
            }
        });
    }

    private void Accept_Dialog(final int pos_accept ){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        // set title
        alertDialogBuilder.setTitle("ACCEPT");
        // set dialog message
        alertDialogBuilder
                .setMessage("Thanks for accepting the assessment of allocated hospital. Hospital name will be made visible one day before the assessment. Click on 'ok' to continue")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();

                        Accept_RejectApi("Assessment accepted",pos_accept);
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

    private void Reject_Dialog(final int pos_reject){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        // set title
        alertDialogBuilder.setTitle("REJECT");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure to reject the allocated assessment. Your request will be sent to NABH Admin to cancel the assignment")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();

                        Accept_RejectApi("Assessment rejected by assessor",pos_reject);

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
