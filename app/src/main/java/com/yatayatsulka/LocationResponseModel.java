package com.yatayatsulka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 4/22/2017.
 */

public class LocationResponseModel {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("streets")
    @Expose
    public ArrayList<String> streets = null;
}
