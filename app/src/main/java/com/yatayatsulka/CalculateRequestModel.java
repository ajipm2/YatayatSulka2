package com.yatayatsulka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 4/30/2017.
 */

public class CalculateRequestModel {
    @SerializedName("fromPlace")
    @Expose
    public String fromPlace;
    @SerializedName("toPlace")
    @Expose
    public String toPlace;

}
