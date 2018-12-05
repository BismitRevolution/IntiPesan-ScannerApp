package id.co.intipesan.intipesanscanner.service;

import id.co.intipesan.intipesanscanner.data.BaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IntipesanService {

    @GET("verify/{code}")
    Call<BaseResponse<String>> verify(@Path("code") String code);
}
