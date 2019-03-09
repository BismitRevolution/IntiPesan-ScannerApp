package id.co.intipesan.intipesanscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
        final String name = intent.getStringExtra(EXTRA_NAME);
        ((TextView) findViewById(R.id.detail_name)).setText(name);
        String position = intent.getStringExtra(EXTRA_POSITION);
        ((TextView) findViewById(R.id.detail_position)).setText(position);
        String company = intent.getStringExtra(EXTRA_COMPANY);
        ((TextView) findViewById(R.id.detail_company)).setText(company);

        findViewById(R.id.btn_print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printQueue(name);
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void printQueue(final String name) {
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
                startPrint(view, name);
            }
        });
        view.loadUrl("http://intipesan.cymonevo.com/print/" + name + "/peserta");
    }

    private void startPrint(WebView webView, String name) {
        PrintManager manager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter(name);
        manager.print(name, adapter, new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A6).build());
    }
}
