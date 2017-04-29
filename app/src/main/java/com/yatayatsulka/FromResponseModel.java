package com.yatayatsulka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 4/29/2017.
 */

public class FromResponseModel {

    @SerializedName("AllFromRoutes")
    @Expose
    public ArrayList<AllFromRoute> allFromRoutes = null;

    public class AllFromRoute {

        @SerializedName("From")
        @Expose
        public String from;

    }

}

