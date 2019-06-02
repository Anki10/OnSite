package com.qci.onsite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qci.onsite.R;
import com.qci.onsite.activity.AmbulanceAccessibilityActivity;
import com.qci.onsite.activity.BioMedicalEngineeringActivity;
import com.qci.onsite.activity.DocumentationActivity;
import com.qci.onsite.activity.FacilityChecksActivity;
import com.qci.onsite.activity.HRMActivity;
import com.qci.onsite.activity.HighDependencyActivity;
import com.qci.onsite.activity.HouseKeepingActivity;
import com.qci.onsite.activity.LaboratoryActivity;
import com.qci.onsite.activity.MRDActivity;
import com.qci.onsite.activity.ManagementActivity;
import com.qci.onsite.activity.OTActivity;
import com.qci.onsite.activity.ObstetricWardActivity;
import com.qci.onsite.activity.PatientStaffInterviewActivity;
import com.qci.onsite.activity.PharmacyActivity;
import com.qci.onsite.activity.RadiologyActivity;
import com.qci.onsite.activity.SafetyManagementActivity;
import com.qci.onsite.activity.SterilizationAreaActivity;
import com.qci.onsite.activity.UniformSignageActivity;
import com.qci.onsite.activity.Ward_OT_EmergencyActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 03-01-2019.
 */

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder> {

    private ArrayList<String> image;
    private Context mcontext;
    private String from_name;
    private String activity_name;

    public ImageShowAdapter(Context context, ArrayList<String>list, String from, String name){
        this.image = list;
        this.mcontext = context;
        from_name = from;
        this.activity_name = name;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_show_row_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            Glide.with(mcontext).load(image.get(position))
                    //           .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.camera_view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (activity_name.equalsIgnoreCase("Laboratory")){
            holder.iv_delete.setOnClickListener((LaboratoryActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("Radiology")){
            holder.iv_delete.setOnClickListener((RadiologyActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("HighDependency")){
            holder.iv_delete.setOnClickListener((HighDependencyActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("Pharmacy")){
            holder.iv_delete.setOnClickListener((PharmacyActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("OT")){
            holder.iv_delete.setOnClickListener((OTActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("obstetric")){
            holder.iv_delete.setOnClickListener((ObstetricWardActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("patientStaff")){
            holder.iv_delete.setOnClickListener((PatientStaffInterviewActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("FacilityCheck")){
            holder.iv_delete.setOnClickListener((FacilityChecksActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("SafetyManagement")){
            holder.iv_delete.setOnClickListener((SafetyManagementActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("UniformSignage")){
            holder.iv_delete.setOnClickListener((UniformSignageActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("HRM")){
            holder.iv_delete.setOnClickListener((HRMActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("MRD")){
            holder.iv_delete.setOnClickListener((MRDActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("HouseKeeping")){
            holder.iv_delete.setOnClickListener((HouseKeepingActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("SterilizationArea")){
            holder.iv_delete.setOnClickListener((SterilizationAreaActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("Management")){
            holder.iv_delete.setOnClickListener((ManagementActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("BioMedicalEngineering")){
            holder.iv_delete.setOnClickListener((BioMedicalEngineeringActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("AmbulanceAccessibilty")){
            holder.iv_delete.setOnClickListener((AmbulanceAccessibilityActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("Ward_Emergency")){
            holder.iv_delete.setOnClickListener((Ward_OT_EmergencyActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }else if (activity_name.equalsIgnoreCase("Documentation")){
            holder.iv_delete.setOnClickListener((DocumentationActivity)mcontext);
            holder.iv_delete.setTag(R.string.key_image_delete,position);
            holder.iv_delete.setTag(R.string.key_from_name,from_name);
        }

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.camera_view)
        ImageView camera_view;

        @BindView(R.id.iv_delete)
        ImageView iv_delete;



        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
