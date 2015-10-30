package com.andre1024.testworkforapprial;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by An on 29.10.2015.
 */
public class MainActivity extends AppCompatActivity {
    private static final String VK_COM = "https://api.vk.com/method/";
    private static final String AUTH_URL = "https://oauth.vk.com/authorize?client_id=1&scope=friends&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&v=5.37&response_type=token&revoke=1";
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebView();
        //loadFriends(number_test);
    }

    private void initWebView() {

        if (new Utils().isConnecting(getApplicationContext())) {
            mWebView = (WebView) this.findViewById(R.id.auth_webView);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSaveFormData(true);
            mWebView.setHorizontalScrollBarEnabled(false);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mWebView.setWebViewClient(new VkWebViewClient());
            mWebView.loadUrl(AUTH_URL);

        } else
            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.no_network, Snackbar.LENGTH_LONG).show();
    }

    public class VkWebViewClient extends WebViewClient {
        private ProgressDialog mDialog;

        public VkWebViewClient() {
            mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading));
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            if (url.contains("oauth.vk.com/blank.html#")) {
                if (url.contains("error")) {
                    Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    String ahrore = url.substring(url.indexOf("#") + 1);

                    String acess = "access_token=";
                    String expires_in = "expires_in=";
                    String user_id = "user_id=";

                    String id = ahrore.substring(ahrore.indexOf(user_id) + user_id.length(), ahrore.length());
                    String token = ahrore.substring(ahrore.indexOf(acess) + acess.length(), ahrore.indexOf(expires_in) - 1);
                    String exIn = ahrore.substring(ahrore.indexOf(expires_in) + expires_in.length(), ahrore.indexOf(user_id) - 1);

                    Log.e("LOG token ", token);
                    Log.e("LOG in ", exIn);
                    Log.e("LOG id ", id);
                    try {

                        loadFriends(id); // Начинаем загружать друзей в фоне по id
                        mWebView.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Toast.makeText(getApplication(), "Error of Connection", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void loadFriends(String id) {
        RestAdapter retrofit = new RestAdapter.Builder()
               // .setLogLevel(RestAdapter.LogLevel.FULL) //For debug
                .setEndpoint(VK_COM).build();
        RetrofitRequest requestService = retrofit.create(RetrofitRequest.class);
        requestService.getFriends(id, "hints", "", "", "", "photo_50", "nom", new Callback<ModelListFiends>() {
            @Override
            public void success(ModelListFiends response, Response response2) {
                Log.e("LOG", " response message: " + response2.getReason().toString());

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_frame, ItemFragment.newInstance(response))
                        .commit();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("LOG", " response message: " + error == null ? " error=null " : error.getMessage());

            }
        });

    }

}
