package com.covid19application.network.Callback;

import android.os.Bundle;

public interface NetworkCallback {
    void onSuccess(Bundle msg);

    void onFailure(Bundle msg);
}