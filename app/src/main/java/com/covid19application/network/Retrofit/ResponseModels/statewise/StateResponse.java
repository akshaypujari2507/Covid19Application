
package com.covid19application.network.Retrofit.ResponseModels.statewise;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {

    @SerializedName("districtData")
    private List<DistrictDatum> mDistrictData;
    @SerializedName("state")
    private String mState;

    public List<DistrictDatum> getDistrictData() {
        return mDistrictData;
    }

    public void setDistrictData(List<DistrictDatum> districtData) {
        mDistrictData = districtData;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

}
