package com.fpoly.mob403_lab56;

import com.fpoly.mob403_lab56.DTO.Picture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_Picture {
    public static final String URL = "http://10.24.41.137:9000/api/";

    @GET("picture")
    Call<List<Picture>> GetAll();

    @GET("picture/{id}")
    Call<Picture> GetElement(@Path("id") int id);

    @POST("picture/create")
    Call<Picture> CreateElement(@Body Picture picture);

    @PUT("picture/update/{id}")
    Call<Picture> UpdateElement(@Path("id") String id, @Body Picture picture);

    @PUT("picture/delete/{id}")
    Call<Picture> DeleteElement(@Path("id") String id);


}
