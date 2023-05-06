package com.zohausman.mycandycotton;

import com.zohausman.mycandycotton.InterfaceApi_z.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apicontroller {

//    http://localhost/ecommapi/index.php
    static final String url="http://192.168.1.11/ecommapi/";
//    static final String url="http://192.168.100.26/ecommapi/";
//    object of this class apicontroller
    private static apicontroller clientobject;
//    object of retrofit
    private static Retrofit retrofit;
// constructor
    apicontroller()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

 //runtime object
    public static synchronized apicontroller getInstance()
    {
        if(clientobject==null)
            clientobject=new apicontroller();
        return clientobject;
    }

//    api (which api we are going to used)
    public apiset getapiSet()
    {
        return retrofit.create(apiset.class);
    }
}
