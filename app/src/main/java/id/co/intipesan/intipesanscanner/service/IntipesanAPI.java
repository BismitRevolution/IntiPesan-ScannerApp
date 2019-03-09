package id.co.intipesan.intipesanscanner.service;

import id.co.intipesan.intipesanscanner.ResponseActivity;
import id.co.intipesan.intipesanscanner.ResultActivity;
import id.co.intipesan.intipesanscanner.data.BaseResponse;
import id.co.intipesan.intipesanscanner.data.RegistrantData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntipesanAPI {

//    public final static String DEV_URL = "http://127.0.0.1:8000";
//    public final static String BASE_URL = "http://intipesan.cymonevo.com";
    public final static String PROD_URL = "http://ujicoba.intipesan.co.id";

    public static IntipesanService service = new Retrofit.Builder()
            .baseUrl(PROD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IntipesanService.class);

    public static IntipesanService getService() {
        return service;
    }

    public static void verify(final ResponseActivity<RegistrantData> activity, String code) {
        Call<BaseResponse<RegistrantData>> call = service.verify(code);
        call.enqueue(new Callback<BaseResponse<RegistrantData>>() {
            @Override
            public void onResponse(Call<BaseResponse<RegistrantData>> call, Response<BaseResponse<RegistrantData>> response) {
                if (response.isSuccessful()) {
                    activity.onActivityResponse(API.REGISTRATION_CODE_VERIFY, response.body().getStatus(), response.body().getPayload());
                } else {
                    activity.onActivityResponse(API.REGISTRATION_CODE_VERIFY, API.ERROR_INTERNAL_SERVER, null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegistrantData>> call, Throwable t) {
                t.printStackTrace();
                activity.onActivityResponse(API.REGISTRATION_CODE_VERIFY, API.ERROR_UNKNOWN, null);
            }
        });
    }
}
