package id.co.intipesan.intipesanscanner.data;

public class BaseResponse<T> {

    private int status;
    private String message;
    private T payload;

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public T getPayload() {
        return this.payload;
    }
}
