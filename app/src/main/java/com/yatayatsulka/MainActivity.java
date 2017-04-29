package com.yatayatsulka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.yatayatsulka.retrofit.ApiService;
import com.yatayatsulka.retrofit.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> startLocation;
    ArrayList<String> endLocation;
    AutocompleteArrayAdapter arrayAdapter;
    AutoCompleteTextView atStartPlace;
    AutoCompleteTextView atEndPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atStartPlace = (AutoCompleteTextView) findViewById(R.id.start);
        startLocation = new ArrayList<>();
        atStartPlace.setThreshold(2);
        getLocation("");
        atStartPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        atEndPlace = (AutoCompleteTextView) findViewById(R.id.end);
        endLocation = new ArrayList<>();
        atEndPlace.setThreshold(2);
        atEndPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int end, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int end, int before, int count) {
                getLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button mShowDialog = (Button) findViewById(R.id.btn);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = atStartPlace.getText().toString();
                if (test != null && !test.equalsIgnoreCase("")) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_fare, null);

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            }
        });
    }

    private void getLocation(String s) {
        ApiService service = RetrofitSingleton.getApiService();
        final Call<FromResponseModel> fromResponseModelCall = service.getFromPlaces("");
        fromResponseModelCall.enqueue(new Callback<FromResponseModel>() {
            @Override
            public void onResponse(Call<FromResponseModel> call, Response<FromResponseModel> response) {
                if (response.isSuccessful()) {
                    ArrayList<FromResponseModel.AllFromRoute> fromResponseModels = new ArrayList<FromResponseModel.AllFromRoute>();
                    fromResponseModels = response.body().allFromRoutes;
                    populateList(fromResponseModels);

                }
            }

            @Override
            public void onFailure(Call<FromResponseModel> call, Throwable t) {

            }
        });
    }

    private void populateList(ArrayList<FromResponseModel.AllFromRoute> fromResponseModels) {
        ArrayList<String> startLocation = new ArrayList<>();
        for (int i = 0; i < fromResponseModels.size(); i++) {
            startLocation.add(fromResponseModels.get(i).from);
        }
        arrayAdapter = new AutocompleteArrayAdapter(this, startLocation);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, startLocation);
        atStartPlace.setAdapter(arrayAdapter);
        atStartPlace.setThreshold(1);
//        arrayAdapter = new AutocompleteArrayAdapter(this,endLocation);
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, endLocation);
//        atEndPlace.setAdapter(arrayAdapter);
//        atEndPlace.setThreshold(1);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void mapRoute(View view) {
        Intent nextPage = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(nextPage);
    }

    public void infoPage(View view) {
        Intent nextPage = new Intent(MainActivity.this, info.class);
        startActivity(nextPage);
    }
}
