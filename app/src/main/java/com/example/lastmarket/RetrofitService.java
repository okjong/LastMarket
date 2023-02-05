package com.example.lastmarket;

import com.example.lastmarket.item.Home_Recycler01_item;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RetrofitService {

    @Multipart
    @POST("/Market/insertDB.php")
    Call<String> postDataToServer(@PartMap Map<String, String> dataPart,
                                  @Part MultipartBody.Part filePart);

    //서버에서 데이터를 json으로 파싱하여 가조은 추상메소드
    @GET("/Market/loadDB.php")
    Call<ArrayList<Home_Recycler01_item>> loadDataFromServer();

    @PUT("/Market/{fileName}")
    Call<Home_Recycler01_item> updateData(@Path("fileName") String fileName, @Body Home_Recycler01_item item);
}
