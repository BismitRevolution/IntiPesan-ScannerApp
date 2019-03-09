package id.co.intipesan.intipesanscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import id.co.intipesan.intipesanscanner.data.RegistrantData;
import id.co.intipesan.intipesanscanner.service.API;
import id.co.intipesan.intipesanscanner.service.IntipesanAPI;

public class TestActivity extends AppCompatActivity implements ResponseActivity<RegistrantData> {
    private WebView webView;

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
//        IntipesanAPI.verify(this, "TES0001");
        doPrint();
    }

    private void doPrint() {
        WebView view = new WebView(this);
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                createPrintJob(view);
                webView = null;
            }
        });
        view.loadUrl("http://intipesan.cymonevo.com/print/" + "Aji Imawan Omi" + "/peserta");
        webView = view;
    }

    private void createPrintJob(WebView view) {
        PrintManager manager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        String jobName = "PRINT AJI IMAWAN OMI";
        PrintDocumentAdapter adapter = view.createPrintDocumentAdapter(jobName);
        manager.print(jobName, adapter, new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A6).build());
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
