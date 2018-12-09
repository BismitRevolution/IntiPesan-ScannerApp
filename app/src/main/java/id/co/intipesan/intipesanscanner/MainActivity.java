package id.co.intipesan.intipesanscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import id.co.intipesan.intipesanscanner.data.RegistrantData;
import id.co.intipesan.intipesanscanner.service.API;
import id.co.intipesan.intipesanscanner.service.IntipesanAPI;

public class MainActivity extends AppCompatActivity implements ResponseActivity<RegistrantData> {
    private final AppCompatActivity activity = this;
    private CodeScannerView scannerView;
    private CodeScanner scanner;

    private final int PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannerView = findViewById(R.id.scanner);
        scanner = new CodeScanner(this, scannerView);
        scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activate(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
    }

    private void startScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA }, PERMISSION_REQUEST);
            }
        } else {
            scanner.startPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanner.startPreview();
                } else {
                    errorPermissionDenied();
                }
                break;
        }
    }

    private void verifySuccess(RegistrantData data) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.EXTRA_VERIFIED, API.IS_SUCCESS);
        intent.putExtra(PostActivity.EXTRA_REGISTRATION_CODE, data.getRegistrationCode());
        intent.putExtra(PostActivity.EXTRA_NAME, data.getName());
        intent.putExtra(PostActivity.EXTRA_POSITION, data.getPosition());
        intent.putExtra(PostActivity.EXTRA_COMPANY, data.getCompany());
        startActivity(intent);
//        Toast.makeText(getApplicationContext(), getResources().getString(R.string.verify_success), Toast.LENGTH_LONG).show();
    }

    private void alreadyVerified(RegistrantData data) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.EXTRA_VERIFIED, API.ALREADY_VERIFIED);
        intent.putExtra(PostActivity.EXTRA_REGISTRATION_CODE, data.getRegistrationCode());
        intent.putExtra(PostActivity.EXTRA_NAME, data.getName());
        intent.putExtra(PostActivity.EXTRA_POSITION, data.getPosition());
        intent.putExtra(PostActivity.EXTRA_COMPANY, data.getCompany());
        startActivity(intent);
//        Toast.makeText(getApplicationContext(), getResources().getString(R.string.verify_already), Toast.LENGTH_LONG).show();
    }

    private void errorInternalServer() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
    }

    private void errorConnection() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
    }

    private void errorPermissionDenied() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_permission_denied), Toast.LENGTH_LONG).show();
    }

    private void activate(String data) {
        IntipesanAPI.verify(this, data);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.sent_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScan();
    }

    @Override
    protected void onPause() {
        scanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onActivityResponse(int responseCode, int resultCode, RegistrantData data) {
        switch (responseCode) {
            case API.REGISTRATION_CODE_VERIFY:
                if (resultCode == API.IS_SUCCESS) {
                    verifySuccess(data);
                } else if (resultCode == API.ALREADY_VERIFIED) {
                    alreadyVerified(data);
                } else if (resultCode == API.ERROR_INTERNAL_SERVER) {
                    errorInternalServer();
                } else {
                    errorConnection();
                }
        }
    }
}
