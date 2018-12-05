package id.co.intipesan.intipesanscanner;

public interface ResponseActivity<T> {
    void onActivityResponse(int responseCode, int resultCode, T response);
}
