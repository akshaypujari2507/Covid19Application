package com.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.covid19application.network.Retrofit.RFInterface;
import com.covid19application.network.Retrofit.ResponseModels.dashboard.DashboardResponse;
import com.covid19application.network.Retrofit.ResponseModels.dashboard.Statewise;
import com.covid19application.network.Utility;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private CardView cvConfirmed, cvActive, cvRecovered, cvDeath, cvTestYourSelf, cvSymptoms;
    private TextView tvConfirmedCt, tvActiveCt, tvRecoveredCt, tvDeathCt;
    private TextView tvConfirmed, tvActive, tvRecovered, tvDeath, tvTestYourSelf, tvSymptoms;
    DashboardResponse dashboardResponse;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        new DownloadLatestUpdate("https://api.covid19india.org/").execute("");

    }

    public void initViews() {

        // cardview
        cvConfirmed = findViewById(R.id.cvConfirmed);
        cvActive = findViewById(R.id.cvActive);
        cvRecovered = findViewById(R.id.cvRecovered);
        cvDeath = findViewById(R.id.cvDeath);
        cvTestYourSelf = findViewById(R.id.cvTestYourSelf);
        cvSymptoms = findViewById(R.id.cvSymptoms);

        //textview count
        tvConfirmedCt = findViewById(R.id.tvConfirmedCt);
        tvActiveCt = findViewById(R.id.tvActiveCt);
        tvRecoveredCt = findViewById(R.id.tvRecoveredCt);
        tvDeathCt = findViewById(R.id.tvDeathCt);

        //textview
        tvConfirmed = findViewById(R.id.tvConfirmed);
        tvActive = findViewById(R.id.tvActive);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvDeath = findViewById(R.id.tvDeath);
        tvTestYourSelf = findViewById(R.id.tvTestYourSelf);
        tvSymptoms = findViewById(R.id.tvSymptoms);

    }


    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cvConfirmed:
                intent = new Intent(DashboardActivity.this, StatesActivity.class);
                intent.putExtra("type", "Confirmed");
                intent.putExtra("StateList", (Serializable) dashboardResponse.getStatewise());
                startActivity(intent);
                //toast("Confirmed");
                break;

            case R.id.cvActive:
                intent = new Intent(DashboardActivity.this, StatesActivity.class);
                intent.putExtra("type", "Active");
                intent.putExtra("StateList", (Serializable) dashboardResponse.getStatewise());
                startActivity(intent);
//                toast("Active");
                break;

            case R.id.cvRecovered:
                intent = new Intent(DashboardActivity.this, StatesActivity.class);
                intent.putExtra("type", "Recovered");
                intent.putExtra("StateList", (Serializable) dashboardResponse.getStatewise());
                startActivity(intent);
                //toast("Recovered");
                break;

            case R.id.cvDeath:
                intent = new Intent(DashboardActivity.this, StatesActivity.class);
                intent.putExtra("type", "Death");
                intent.putExtra("StateList", (Serializable) dashboardResponse.getStatewise());
                startActivity(intent);
//                toast("Death");
                break;

            case R.id.cvTestYourSelf:
                toast("TestYourSelf");
                break;

            case R.id.cvSymptoms:
                toast("Symptoms");
                break;


            default:
                toast("Default");
                break;
        }
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    class DownloadLatestUpdate extends AsyncTask<String, String, DashboardResponse> {

        private ProgressDialog uploadingDialog;
        private RFInterface rfInterface;

        DownloadLatestUpdate(String baseUrl) {
            rfInterface = Utility.getRetrofitInterface(baseUrl);
            uploadingDialog = new ProgressDialog(DashboardActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uploadingDialog.setMessage("Loading,Please wait...");
            uploadingDialog.setCancelable(false);
            uploadingDialog.show();
        }

        @Override
        protected DashboardResponse doInBackground(String... strings) {
            try {

//                Response<ResponseBody> responseBody = rfInterface.getStateData("https://api.covid19india.org/state_district_wise.json").execute();
//                if (responseBody.isSuccessful()) {
//                    Gson gson = new Gson();
//                    String a = gson.toJson(responseBody.body());
//                    System.out.println("Akshay " + a);
//                }

                Response<DashboardResponse> responseResult = rfInterface.getDashboardData().execute();

                if (responseResult.isSuccessful()) {
                    if (responseResult.body() != null) {
                        if (responseResult.body().getStatewise() != null) {
                            return responseResult.body();
                        }
                    }
                } else {
                    responseResult.errorBody();
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(DashboardResponse dashboardResponse) {
            super.onPostExecute(dashboardResponse);
            uploadingDialog.dismiss();
            if (dashboardResponse != null) {
                setData(dashboardResponse);
                //toast("success");
            } else {
                toast("failed");
            }
        }
    }

    public void setData(DashboardResponse dashboardResp) {
        this.dashboardResponse = dashboardResp;
        //setTitle("Covid19 India \nLast Update on " + dashboardResponse.getStatewise().get(0).getLastupdatedtime());
        tvConfirmedCt.setText(dashboardResponse.getStatewise().get(0).getConfirmed());
        tvActiveCt.setText(dashboardResponse.getStatewise().get(0).getActive());
        tvRecoveredCt.setText(dashboardResponse.getStatewise().get(0).getRecovered());
        tvDeathCt.setText(dashboardResponse.getStatewise().get(0).getDeaths());
        toast("Last Update : " + dashboardResponse.getStatewise().get(0).getLastupdatedtime());
    }

}
