package id.co.intipesan.intipesanscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import id.co.intipesan.intipesanscanner.data.RegistrantData;
import id.co.intipesan.intipesanscanner.service.API;
import id.co.intipesan.intipesanscanner.service.IntipesanAPI;

public class TestActivity extends AppCompatActivity implements ResponseActivity<RegistrantData> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private void test() {
        IntipesanAPI.verify(this, "TES0001");
    }

    private void verifySuccess(int code) {
        ((TextView) findViewById(R.id.tv_status)).setText("" + code);
        ((TextView) findViewById(R.id.tv_message)).setText("Verify Success");
        ((TextView) findViewById(R.id.tv_payload)).setText("Success");
    }

    private void alreadyVerified(int code) {
        ((TextView) findViewById(R.id.tv_status)).setText("" + code);
        ((TextView) findViewById(R.id.tv_message)).setText("Verify Success");
        ((TextView) findViewById(R.id.tv_payload)).setText("Already Verified");
    }

    private void errorInternalServer(int code) {
        ((TextView) findViewById(R.id.tv_status)).setText("" + code);
        ((TextView) findViewById(R.id.tv_message)).setText("Error Internal Server");
        ((TextView) findViewById(R.id.tv_payload)).setText("Null");
    }

    private void errorConnection(int code) {
        ((TextView) findViewById(R.id.tv_status)).setText("" + code);
        ((TextView) findViewById(R.id.tv_message)).setText("Error connection");
        ((TextView) findViewById(R.id.tv_payload)).setText("Null");
    }

    @Override
    public void onActivityResponse(int requestCode, int resultCode, RegistrantData data) {
        switch (requestCode) {
            case API.REGISTRATION_CODE_VERIFY:
                if (resultCode == API.IS_SUCCESS) {
                    verifySuccess(resultCode);
                } else if (resultCode == API.ALREADY_VERIFIED) {
                    alreadyVerified(resultCode);
                } else if (resultCode == API.ERROR_INTERNAL_SERVER) {
                    errorInternalServer(resultCode);
                } else {
                    errorConnection(resultCode);
                }
                break;
        }
    }
}
