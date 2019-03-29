package com.qci.onsite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qci.onsite.R;
import com.qci.onsite.activity.HospitalListActivity;
import com.qci.onsite.pojo.AssessmentStatusPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 21-01-2019.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<AssessmentStatusPojo>hospital_list;

    public HospitalAdapter(Context context,ArrayList<AssessmentStatusPojo>list){
        this.mContext = context;
        this.hospital_list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_row_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final AssessmentStatusPojo pojo = hospital_list.get(position);


        holder.tv_hospital_name.setText(pojo.getAssessement_name());

        if (pojo.getAssessement_status().equalsIgnoreCase("Start")) {
            holder.iv_assessment.setImageResource(R.mipmap.start);
        } else if (pojo.getAssessement_status().equalsIgnoreCase("Draft")) {
            holder.iv_assessment.setImageResource(R.mipmap.draft);
        } else if (pojo.getAssessement_status().equalsIgnoreCase("Done")) {
            holder.iv_assessment.setImageResource(R.mipmap.completed);
        }

        holder.ll_hospital.setOnClickListener((HospitalListActivity)mContext);
        holder.ll_hospital.setTag(R.string.key_hospital,position);


    }

    @Override
    public int getItemCount() {
        return hospital_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_hospital)
        LinearLayout ll_hospital;

        @BindView(R.id.tv_hospital_name)
        TextView tv_hospital_name;

        @BindView(R.id.iv_assessment)
        ImageView iv_assessment;

      public ViewHolder(View itemView) {
          super(itemView);

          ButterKnife.bind(this, itemView);
      }
  }
}
