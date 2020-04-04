package com.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.covid19application.adapters.StateAdapter;
import com.covid19application.network.Retrofit.ResponseModels.dashboard.Statewise;

import java.util.List;

public class StatesActivity extends AppCompatActivity implements StateAdapter.OnItemClickListener {

    private RecyclerView stateRecycler;
    private StateAdapter stateAdapter;
    List<Statewise> statewiseList;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statewiseList = (List<Statewise>) getIntent().getSerializableExtra("StateList");
        type = getIntent().getStringExtra("type");
        statewiseList.remove(0);

        stateRecycler = findViewById(R.id.stateRecycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        stateRecycler.setLayoutManager(mLayoutManager);
        stateRecycler.setItemAnimator(new DefaultItemAnimator());

        stateAdapter = new StateAdapter(statewiseList, StatesActivity.this, this, type);

        stateRecycler.setAdapter(stateAdapter);

        setTitle("Statewise " + type + " Cases");
    }

    @Override
    public void onItemClick(Statewise statewise) {
        if (type.equalsIgnoreCase("Confirmed")) {
            Intent intent = new Intent(StatesActivity.this, DistrictActivity.class);
            intent.putExtra("state", statewise.getState());
            startActivity(intent);
            //toast(statewise.getState());
        } else {
            if (type.equals("Active"))
                toast("Total " + type + " cases in " + statewise.getState() + " district is " + statewise.getActive());
            else if (type.equals("Recovered"))
                toast("Total " + type + " cases in " + statewise.getState() + " district is " + statewise.getRecovered());
            else if (type.equals("Death"))
                toast("Total " + type + " cases in " + statewise.getState() + " district is " + statewise.getDeaths());
        }
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
