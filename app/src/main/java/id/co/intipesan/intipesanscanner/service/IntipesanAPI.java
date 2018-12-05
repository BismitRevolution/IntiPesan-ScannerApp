package id.co.intipesan.intipesanscanner.service;

import id.co.intipesan.intipesanscanner.ResultActivity;
import id.co.intipesan.intipesanscanner.data.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IntipesanAPI {

    public final static String BASE_URL = "http://localhost:8000";

    public static IntipesanService service = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(IntipesanService.class);

    public static IntipesanService getService() {
        return service;
    }

    public static void verify(final ResultActivity activity, String code) {
        Call<BaseResponse<String>> call = service.verify(code);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful()) {
                    activity.onActivityResult(API.REGISTRATION_CODE_VERIFY, API.IS_SUCCESS);
                } else {
                    activity.onActivityResult(API.REGISTRATION_CODE_VERIFY, API.ERROR_INTERNAL_SERVER);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                activity.onActivityResult(API.REGISTRATION_CODE_VERIFY, API.ERROR_UNKNOWN);
            }
        });
    }
}
