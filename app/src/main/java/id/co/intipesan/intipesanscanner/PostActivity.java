package id.co.intipesan.intipesanscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import id.co.intipesan.intipesanscanner.service.API;

public class PostActivity extends AppCompatActivity {
    public static String EXTRA_VERIFIED = "status";
    public static String EXTRA_REGISTRATION_CODE = "registration_code";
    public static String EXTRA_NAME = "name";
    public static String EXTRA_POSITION = "position";
    public static String EXTRA_COMPANY = "company";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int status = intent.getIntExtra(EXTRA_VERIFIED, -1);
        if (status == API.ALREADY_VERIFIED) {
            ((TextView) findViewById(R.id.detail_title)).setText(getResources().getString(R.string.verify_already));
        }

        String code = intent.getStringExtra(EXTRA_REGISTRATION_CODE);
        ((TextView) findViewById(R.id.detail_code)).setText(code);
        String name = intent.getStringExtra(EXTRA_NAME);
        ((TextView) findViewById(R.id.detail_name)).setText(name);
        String position = intent.getStringExtra(EXTRA_POSITION);
        ((TextView) findViewById(R.id.detail_position)).setText(position);
        String company = intent.getStringExtra(EXTRA_COMPANY);
        ((TextView) findViewById(R.id.detail_company)).setText(company);

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
