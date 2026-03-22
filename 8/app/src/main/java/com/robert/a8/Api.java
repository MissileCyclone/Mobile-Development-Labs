package com.robert.a8;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Api {
    private static ApiService api;
    public static ApiService getApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl("https://nti.urfu.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return api;
    }

    public interface ApiService {
        @POST("ege-calc/json")
        Call<List<SpecialtyDto>> postScores(@Body Map<String, Integer> scores);
    }

    public static class SpecialtyDto {
        @SerializedName("specialty_name") public String specialtyName;
        @SerializedName("math_point") public int mathPoint;
        @SerializedName("rus_point") public int rusPoint;
        @SerializedName("itk_point") public int itkPoint;
        @SerializedName("soc_point") public int socPoint;
        @SerializedName("phys_point") public int physPoint;
        @SerializedName("chem_point") public int chemPoint;
    }
}