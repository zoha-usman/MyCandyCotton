package com.zohausman.mycandycotton.InterfaceApi_z;

import com.zohausman.mycandycotton.model.index_response_model;
import com.zohausman.mycandycotton.model.login_response_model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface apiset {


    @FormUrlEncoded
    @POST("index.php")
    Call<index_response_model> getregister(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<login_response_model> getlogin(@Field("email") String email, @Field("password") String password);

}
