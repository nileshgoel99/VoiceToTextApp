package com.example.cool.fidhack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    protected static final int REQUEST_OK = 1;
    EditText mEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(0xff068037);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Fidelity Investments");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // findViewById(R.id.button1).setOnClickListener(this);


    }

    public void getVoice(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    public void sendNotification(View v){
        mEdit   = (EditText)findViewById(R.id.editText);
        String url_val = mEdit.getText().toString();

        /*try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            String params_val = "{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"GUI.ShowNotification\", \"params\": {\"title\":\"Stock Alert\",\"message\":\"AAPL stock check\"}}";
            String finUrl = String.format("http://%s:8080/jsonrpc?request=%s", url_val, URLEncoder.encode(params_val));
            Log.d(finUrl, "Testing the Value");

            HttpGet request = new HttpGet();
            request.setURI(URI.create(finUrl));
            HttpResponse response = httpclient.execute(request);
            response.getStatusLine().getStatusCode();

            /*HttpPost httpost = new HttpPost(finUrl);

            httpost.setHeader("Content-Type", "application/json");


            List <NameValuePair> nvps_k = new ArrayList<NameValuePair>();
            nvps_k.add(new BasicNameValuePair("title", "Stock Alerts"));
            nvps_k.add(new BasicNameValuePair("message", "AAPL Stock Check"));

            List <NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("id", "1"   ));
            nvps.add(new BasicNameValuePair("jsonrpc", "2.0"));
            nvps.add(new BasicNameValuePair("method", "GUI.ShowNotification"));
            nvps.add(new BasicNameValuePair("params", String.valueOf(nvps_k)));


            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            String eu = EntityUtils.toString(entity).toString();
            Log.d(eu, "Response BACK!!!!!");

        }catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));

            sendVoiceNotification(thingsYouSaid.get(0));



            /*if(first_val.toUpperCase().equals("OK") && second_val.toUpperCase().equals("FIDELITY")){
                String final_string = thingsYouSaid.get(0).substring(0, thingsYouSaid.get(0).length());
                ((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            }
            else{
                ((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            }*/
        }
    }

    public void sendVoiceNotification(String voice){

        Log.d(voice, "Testing the Value");
        mEdit   = (EditText)findViewById(R.id.editText);
        String url_val = mEdit.getText().toString();

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            String params_val = "{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"GUI.ShowNotification\", \"params\": {\"title\":\"Stock Alert\",\"message\":\"%s\"}}";
            String params_val_final = String.format(params_val,voice);
            String finUrl = String.format("http://%s:8080/jsonrpc?request=%s", url_val, URLEncoder.encode(params_val_final));
            Log.d(finUrl, "Testing the Value");

            HttpGet request = new HttpGet();
            request.setURI(URI.create(finUrl));
            HttpResponse response = httpclient.execute(request);
            response.getStatusLine().getStatusCode();


        }catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
