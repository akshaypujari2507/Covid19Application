
package com.covid19application.network.Retrofit.ResponseModels.statewise;

import com.google.gson.annotations.SerializedName;

public class Delta {

    @SerializedName("confirmed")
    private Long mConfirmed;

    public Long getConfirmed() {
        return mConfirmed;
    }

    public void setConfirmed(Long confirmed) {
        mConfirmed = confirmed;
    }

}
