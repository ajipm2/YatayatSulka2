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
import android.widget.TextView;
import android.widget.Toast;

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
        atStartPlace.setThreshold(1);
        getStartLocation("");
        getEndLocation("");
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
//                getStartLocation(s.toString());
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
                    calculateFare(atStartPlace.getText().toString(), atEndPlace.getText().toString());
                }
            }
        });
    }

    private void showCalculatedFare(String normal, String student) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_fare, null);

        TextView textView = (TextView) mView.findViewById(R.id.fare);
        TextView tvStudent = (TextView) mView.findViewById(R.id.tv_student_fare);
        textView.setText("Normal Fare: " + normal);
        tvStudent.setText("Student Fare: " + student);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void getStartLocation(String s) {
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

    private void getEndLocation(String s) {
        ApiService service = RetrofitSingleton.getApiService();
        final Call<FromResponseModel> fromResponseModelCall = service.getFromPlaces("");
        fromResponseModelCall.enqueue(new Callback<FromResponseModel>() {
            @Override
            public void onResponse(Call<FromResponseModel> call, Response<FromResponseModel> response) {
                if (response.isSuccessful()) {
                    ArrayList<FromResponseModel.AllFromRoute> fromResponseModels = new ArrayList<FromResponseModel.AllFromRoute>();
                    fromResponseModels = response.body().allFromRoutes;
                    populateEndList(fromResponseModels);

                }
            }

            @Override
            public void onFailure(Call<FromResponseModel> call, Throwable t) {

            }
        });
    }

    private void populateEndList(ArrayList<FromResponseModel.AllFromRoute> fromResponseModels) {
        ArrayList<String> endLocation = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            endLocation.add(fromResponseModels.get(i).from);
        }
//        for(int i = 0; i< 10;i++){
//            endLocation.add("Test"+i);
//        }
        arrayAdapter = new AutocompleteArrayAdapter(this, endLocation);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, endLocation);
        atEndPlace.setAdapter(arrayAdapter);
    }

    private void calculateFare(String from, String to) {
        ApiService service = RetrofitSingleton.getApiService();
        final CalculateRequestModel requestModel = new CalculateRequestModel();
        requestModel.fromPlace = from;
        requestModel.toPlace = to;
        Call<CalculateResponseModel> responseModelCall = service.calculateFare(requestModel);
        responseModelCall.enqueue(new Callback<CalculateResponseModel>() {
            @Override
            public void onResponse(Call<CalculateResponseModel> call, Response<CalculateResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().routeDetail != null && response.body().routeDetail.size() != 0)
                        showCalculatedFare(response.body().routeDetail.get(0).normalFare.toString(), response.body().routeDetail.get(0).studentCardFare.toString());
                    else
                        showToastMessage();
                }
            }

            @Override
            public void onFailure(Call<CalculateResponseModel> call, Throwable t) {

            }
        });
    }

    private void showToastMessage() {
        Toast.makeText(this, "No route found", Toast.LENGTH_SHORT).show();
    }

    private void populateList(ArrayList<FromResponseModel.AllFromRoute> fromResponseModels) {
        ArrayList<String> startLocation = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            startLocation.add(fromResponseModels.get(i).from);
        }
//        for(int i = 0; i< 10;i++){
//            startLocation.add("Test"+i);
//        }
        arrayAdapter = new AutocompleteArrayAdapter(this, startLocation);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, startLocation);
        atStartPlace.setAdapter(arrayAdapter);
//        atStartPlace.setThreshold(1);
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
