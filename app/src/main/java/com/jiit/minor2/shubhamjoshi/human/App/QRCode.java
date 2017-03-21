package com.jiit.minor2.shubhamjoshi.human.App;

import android.graphics.PointF;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.jiit.minor2.shubhamjoshi.human.R;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class QRCode extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private QRCodeReaderView mydecoderview;

    private void showText(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        mydecoderview.setBackCamera();

        Log.e("SHubham oncreate","SJ");

    }

    private void sendToServer(String accessToken, String qrStr) {

        HttpClient httpClient = new DefaultHttpClient();


        HttpPost httpPost = new HttpPost("http://192.168.1.9:1000/Human-Web-/gencode/auth");

        String jsonStr = "{\"uuid\":\"" + qrStr + "\",\"access_token\":\"" + accessToken + "\"}";

        Log.v("##", " JSON to post:" + jsonStr);
        try {

            StringEntity se = new StringEntity(jsonStr);
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);

            Log.v("###", "Respnse:" + response.toString());
            showText("Successfully posted token");

        } catch (ClientProtocolException e) {
            // Log exception
            showText("Unablet to Post Token");

            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {

            showText("Unablet to Post Token");
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            showText("Unablet to Post Token");

            e.printStackTrace();
        }

    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.e("SHubham QRREEAD","SJ");

        sendToServer("SJ", "HEllo");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }
}
