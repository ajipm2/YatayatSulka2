package com.yatayatsulka.retrofit;

import com.yatayatsulka.CalculateRequestModel;
import com.yatayatsulka.CalculateResponseModel;
import com.yatayatsulka.FromResponseModel;
import com.yatayatsulka.LocationResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Dell on 4/22/2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("location/auto_suggest")
    Call<LocationResponseModel> getLocation(@Field("suburb") String suburb);

    @GET("placeAPI/readFromPlace")
    Call<FromResponseModel> getFromPlaces(@Query("") String query);

    @POST("calculate/getDetails")
    Call<CalculateResponseModel> calculateFare(@Body CalculateRequestModel requestModel);

}
