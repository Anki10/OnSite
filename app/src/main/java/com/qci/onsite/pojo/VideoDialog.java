package com.qci.onsite.pojo;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Ankit on 23-01-2019.
 */

public class VideoDialog {

    public static ProgressDialog showLoading(Activity activity) {
        @SuppressWarnings("deprecation") ProgressDialog mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Video uploading");
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        return mProgressDialog;
    }
}
