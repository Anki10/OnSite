package com.qci.onsite.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.qci.onsite.R;
import com.qci.onsite.activity.LoginActivity;
import com.qci.onsite.adapter.NavDrawerListAdapter;
import com.qci.onsite.database.DatabaseHandler;
import com.qci.onsite.pojo.NavDrawerItem;
import com.qci.onsite.util.AppConstant;

import java.util.ArrayList;

/**
 * Created by raj on 3/22/2018.
 */

public class DrawerFragment extends Fragment {

    private View view;
    private String[] navMenuTitles;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ListView list_slidermenu;
    private int selectedPos = -1;
    private DrawerLayout drawerlayout;
    private ImageView iView_cross;
    public NavDrawerListAdapter adapter;
    public ActionBarDrawerToggle mDrawerToggle;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.left_slide_list_layout, container, false);


        databaseHandler = DatabaseHandler.getInstance(getActivity());

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navDrawerItems = new ArrayList<NavDrawerItem>();


            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));


        list_slidermenu = (ListView) view.findViewById(R.id.list_slidermenu);
        list_slidermenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPos = i;
                drawerlayout.closeDrawers();
            }
        });

        iView_cross = (ImageView) view.findViewById(R.id.iview_cross);

        iView_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawers();
            }
        });

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getActivity(),
                navDrawerItems);
        list_slidermenu.setAdapter(adapter);

        return view;
    }

    @SuppressLint("NewApi")
    public void setUp(final DrawerLayout drawerlayout) {

        this.drawerlayout = drawerlayout;
        if (adapter != null)
            adapter.notifyDataSetChanged();

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                switch (selectedPos) {
                    case 0:
      //                  datbase();
                        break;
                    case 1:
                        logout();
                        break;

                    case 2:
                        Reset();
                        break;

                }
            }
        };

        drawerlayout.setDrawerListener(mDrawerToggle);
        drawerlayout.post(new Runnable() {

            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    private void Reset(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // set title
        alertDialogBuilder.setTitle("Data Reset");
        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.Data_reset))
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        databaseHandler.removeAll();

                        DeleteSharedPrfernce();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), getResources().getString(R.string.logout_sucessfuly), Toast.LENGTH_SHORT).show();
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

    private void logout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // set title
        alertDialogBuilder.setTitle("Logout");
        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.logout_alert))
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), getResources().getString(R.string.logout_sucessfuly), Toast.LENGTH_SHORT).show();

                        saveIntoPrefs(AppConstant.Login_status,"logout");
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

    public void saveIntoPrefs(String key, String value) {
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstant.PREF_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void DeleteSharedPrfernce() {
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstant.PREF_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }

}
