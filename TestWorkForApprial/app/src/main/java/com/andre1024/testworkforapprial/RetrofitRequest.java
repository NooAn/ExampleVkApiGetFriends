package com.andre1024.testworkforapprial;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by An on 29.10.2015.
 */
public interface RetrofitRequest {
    @Headers("Cache-Control: max-age=14400")
    @FormUrlEncoded
    @POST("/friends.get")
    void getFriends(@Field("user_id") String user_id,
                    @Field("order") String order,
                    @Field("list_id") String list_id,
                    @Field("count") String count,
                    @Field("offset") String offset,
                    @Field("fields") String fields,
                    @Field("name_case") String name_case,
                    Callback<ModelListFiends> callback);
}
