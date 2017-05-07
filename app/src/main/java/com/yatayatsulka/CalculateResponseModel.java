package com.yatayatsulka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 4/30/2017.
 */

public class CalculateResponseModel {

    @SerializedName("routeDetail")
    @Expose
    public ArrayList<RouteDetail> routeDetail = null;

    public class RouteDetail {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("RouteStart")
        @Expose
        public String routeStart;
        @SerializedName("RouteEnd")
        @Expose
        public String routeEnd;
        @SerializedName("NormalFare")
        @Expose
        public Integer normalFare;
        @SerializedName("StudentCardFare")
        @Expose
        public Integer studentCardFare;
    }
}
