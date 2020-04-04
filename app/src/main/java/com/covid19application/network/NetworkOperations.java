package com.covid19application.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.covid19application.network.Callback.NetworkCallback;

public abstract class NetworkOperations implements NetworkCallback {
    private ProgressDialog progressDialog;
    private boolean isProgrssDialogVisible;

    protected NetworkOperations(boolean isProgrssDialogVisible) {
        this.isProgrssDialogVisible = isProgrssDialogVisible;
    }

    void onStart(Context context, String msg) {
        if (isProgrssDialogVisible) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    void onComplete() {
        if (isProgrssDialogVisible && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}