package com.example.icu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class enrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
    }

    public void OnClickHandler(View view) throws JSONException {
        Intent intent = getIntent();
        String serverIP = intent.getStringExtra("serverIP");
        String phoneNum = intent.getStringExtra("phoneNum");

        EditText text_id = (EditText)findViewById(R.id.enroll_name);
        String id = text_id.getText().toString();

        JSONObject object = new JSONObject();
        object.put("name", id);
        object.put("number", phoneNum);
        object.put("cmd", 0);
        final String json = object.toString();

        String url = "http://".concat(serverIP).concat("/UserEnroll");

        NetworkTask networkTask = new NetworkTask(url,json);
        networkTask.execute();


        //sendJson(json, url);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ICU").setMessage("전송되었습니다.");
        builder.setPositiveButton("확인", EnrollCheckListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    private DialogInterface.OnClickListener EnrollCheckListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(getApplicationContext(), enroll_wait_Activity.class);
            startActivity(intent);
        }
    };
    /*public void popup(View v){
        Intent intent = new Intent(this, enroll_submit_popupActivity.class);
    }
    public void onClick_login(View v) {
        EditText text_id = (EditText)findViewById(R.id.enroll_name);
        EditText text_phone_num = (EditText)findViewById(R.id.enroll_name);
        String id = text_id.getText().toString();
        String phone_num = text_id.getText().toString();*/


        //Intent intent_submit = new Intent(getApplicationContext(), SubAct)
    //}
}
