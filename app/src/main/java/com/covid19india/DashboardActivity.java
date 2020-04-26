package com.covid19india;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.covid19india.network.ConnectionDetector;
import com.covid19india.network.Retrofit.RFInterface;
import com.covid19india.network.Retrofit.ResponseModels.dashboard.DashboardResponse;
import com.covid19india.network.Utility;

import java.io.Serializable;

import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private CardView cvConfirmed, cvActive, cvRecovered, cvDeath, cvTestYourSelf, cvSymptoms;
    private TextView tvConfirmedCt, tvActiveCt, tvRecoveredCt, tvDeathCt;
    private TextView tvConfirmed, tvActive, tvRecovered, tvDeath, tvTestYourSelf, tvSymptoms;
    DashboardResponse dashboardResponse;
    Intent intent;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onResume() {
        super.onResume();

        if (!connectionDetector.isNetworkConnected() && !connectionDetector.internetIsConnected()){
            startActivity(new Intent(DashboardActivity.this, ErrorActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isNetworkConnected() && connectionDetector.internetIsConnected()){

            new DownloadLatestUpdate(getString(R.string.base_url)).execute("");
        } else {
            startActivity(new Intent(DashboardActivity.this, ErrorActivity.class));
            finish();
        }


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
                intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                startActivity(intent);
                //toast("Symptoms");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refreshDashboard:
                if (connectionDetector.isNetworkConnected() && connectionDetector.internetIsConnected()) {
                    new DownloadLatestUpdate(getString(R.string.base_url)).execute("");
                } else {
                    startActivity(new Intent(DashboardActivity.this, ErrorActivity.class));
                    finish();
                }
                return true;
            case R.id.shareDashboard:
                shareData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareData(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.covid19india");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


}
