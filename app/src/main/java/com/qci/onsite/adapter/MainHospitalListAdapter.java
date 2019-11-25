package com.qci.onsite.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qci.onsite.MainActivity;
import com.qci.onsite.R;
import com.qci.onsite.pojo.AllocatedHospitalListPojo;
import com.qci.onsite.pojo.AllocatedHospitalResponse;
import com.qci.onsite.util.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 22-01-2019.
 */

public class MainHospitalListAdapter extends RecyclerView.Adapter<MainHospitalListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<AllocatedHospitalListPojo>mainhospital_list;

    public MainHospitalListAdapter(Context context,ArrayList<AllocatedHospitalListPojo>list){
        this.mContext = context;
        this.mainhospital_list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_hospital_row_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AllocatedHospitalListPojo pojo = mainhospital_list.get(position);

        if (pojo.getHospitalstage().equalsIgnoreCase("QC Review Stage_1")){
            holder.ll_hospital_main.setVisibility(View.GONE);
        }else {
            holder.ll_hospital_main.setVisibility(View.VISIBLE);
            if (pojo.getHospitalstage().equalsIgnoreCase("Assessment accepted")){
                holder.iv_hospital_accept.setVisibility(View.GONE);
                holder.iv_hospital_reject.setVisibility(View.GONE);

                if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS) != null){
                    if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).equalsIgnoreCase(String.valueOf(pojo.getHospitalid()))){
                        holder.iv_hospital_accept.setVisibility(View.GONE);
                        holder.iv_hospital_reject.setVisibility(View.GONE);

                        holder.iv_progress.setVisibility(View.VISIBLE);

                    }
                }
            }else if (pojo.getHospitalstage().equalsIgnoreCase("Assessment accepted by Assessor")){
                holder.iv_hospital_accept.setVisibility(View.GONE);
                holder.iv_hospital_reject.setVisibility(View.GONE);

                if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS) != null){
                    if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).equalsIgnoreCase(String.valueOf(pojo.getHospitalid()))){
                        holder.iv_hospital_accept.setVisibility(View.GONE);
                        holder.iv_hospital_reject.setVisibility(View.GONE);

                        holder.iv_progress.setVisibility(View.VISIBLE);
                    }
                }
            }
            else if (pojo.getHospitalstage().equalsIgnoreCase("Assessment in progress")){
                holder.iv_hospital_accept.setVisibility(View.GONE);
                holder.iv_hospital_reject.setVisibility(View.GONE);

                if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS) != null){
                    if (getFromPrefs(AppConstant.ASSESSSMENT_STATUS).equalsIgnoreCase(String.valueOf(pojo.getHospitalid()))){
                        holder.iv_hospital_accept.setVisibility(View.GONE);
                        holder.iv_hospital_reject.setVisibility(View.GONE);

                        holder.iv_progress.setVisibility(View.VISIBLE);
                    }
                }
            }else {
                if (pojo.getLoggedin_asrtype() != null){
                    if (pojo.getLoggedin_asrtype().length() > 0){
                        if (pojo.getLoggedin_asrtype().equalsIgnoreCase("j")){
                            holder.iv_hospital_accept.setVisibility(View.GONE);
                            holder.iv_hospital_reject.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }

        holder.tv_State.setText(pojo.getHospitalstate());

        holder.tv_city.setText(pojo.getHospitaldistrict());

        holder.tv_Assessment_Date.setText(getDate(pojo.getAssessmentdate(),"MM/dd/yyyy"));

        String date = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(new Date());


        String Server_date =  getDate(pojo.getAssessmentdate(),"MM/dd/yyyy");

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

            if (date_staus.equalsIgnoreCase("1")){
                holder.tv_hospital_main_name.setText(mainhospital_list.get(position).getHospitalname());

            }else if (date_staus.equalsIgnoreCase("0")){
                holder.tv_hospital_main_name.setText(mainhospital_list.get(position).getHospitalname());

            }else {
                holder.tv_hospital_main_name.setText("XXXX");

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.ll_hospital_main.setOnClickListener((MainActivity)mContext);
        holder.ll_hospital_main.setTag(R.string.key_hospital_main,position);

        holder.iv_hospital_accept.setOnClickListener((MainActivity)mContext);
        holder.iv_hospital_accept.setTag(R.string.key_hospital_accept,position);

        holder.iv_hospital_reject.setOnClickListener((MainActivity)mContext);
        holder.iv_hospital_reject.setTag(R.string.key_hospital_reject,position);

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


    @Override
    public int getItemCount() {
        return mainhospital_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_hospital_accept)
        ImageView iv_hospital_accept;

        @BindView(R.id.iv_hospital_reject)
        ImageView iv_hospital_reject;

        @BindView(R.id.iv_progress)
        ImageView iv_progress;

        @BindView(R.id.ll_hospital_main)
        LinearLayout ll_hospital_main;

        @BindView(R.id.ll_main_hospital)
        LinearLayout ll_main_hospital;

        @BindView(R.id.tv_hospital_main_name)
        TextView tv_hospital_main_name;


        @BindView(R.id.tv_Assessment_Date)
        TextView tv_Assessment_Date;

        @BindView(R.id.tv_State)
        TextView tv_State;

        @BindView(R.id.tv_city)
        TextView tv_city;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


    public String getFromPrefs(String key) {
        SharedPreferences prefs = mContext.getSharedPreferences(AppConstant.PREF_NAME,mContext.MODE_PRIVATE);
        return prefs.getString(key, AppConstant.DEFAULT_VALUE);
    }
}
