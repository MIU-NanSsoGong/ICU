package com.example.icu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String filename = "logfile.json";

    String phoneNum;
    Button enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number();
        if(phoneNum.startsWith("+82"))
        {
            phoneNum = phoneNum.replace("+82", "0");
        }
        EditText text_id = (EditText)findViewById(R.id.ip_address);
        final String serverIP = text_id.getText().toString();

        enroll = (Button)findViewById(R.id.btn_enroll);
        enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int state = 0;
                try {
                    state = IsRegistered(phoneNum, serverIP);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if(state == -1) {
                        Intent intent = new Intent(getApplicationContext(), enrollActivity.class);
                        intent.putExtra("serverIP", serverIP);
                        intent.putExtra("phoneNum", phoneNum);
                        startActivity(intent);
                        finish();
                    }
                    else if(state == 0) {
                        Intent intent = new Intent(getApplicationContext(), enroll_wait_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(state == 1) {
                        return;
                    }
                    else{
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int IsRegistered(String PhoneNum, String serverIP) throws JSONException, ExecutionException, InterruptedException {
        JSONObject object = new JSONObject();
        object.put("number", PhoneNum);
        object.put("cmd", 1);
        final String json = object.toString();

        String url = "http://".concat(serverIP).concat("/ServerAccess");

        NetworkTask networkTask = new NetworkTask(url,json);
        int result = Integer.parseInt(networkTask.execute().get());
        return result;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String url;
        String json;

        NetworkTask(String url, String json){
            this.url = url;
            this.json = json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url,json);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}

