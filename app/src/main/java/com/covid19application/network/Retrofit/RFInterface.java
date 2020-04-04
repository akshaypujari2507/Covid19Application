package com.covid19application.network.Retrofit;

import com.covid19application.network.Retrofit.ResponseModels.dashboard.DashboardResponse;
import com.covid19application.network.Retrofit.ResponseModels.statewise.StateResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RFInterface {

    @GET("data.json")
    Call<DashboardResponse> getDashboardData();

//    @GET("v2/state_district_wise.json")
//    Call<StateResponse> getStateData();

    @GET("v2/state_district_wise.json")
    Call<List<StateResponse>> getStateData();

//    @GET
//    Call<ResponseBody> getStateData(@Url String url);

}