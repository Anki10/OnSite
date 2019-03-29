package com.qci.onsite.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.qci.onsite.R;
import com.qci.onsite.pojo.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by quepplin1 on 8/29/2016.
 */
public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){

        this.context = context;
        this.navDrawerItems = navDrawerItems;

    }
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int i) {
        return navDrawerItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView tv_title = (TextView) view.findViewById(R.id.titleMenu);

        tv_title.setText(navDrawerItems.get(i).getTitle());
        return view;
    }


}
