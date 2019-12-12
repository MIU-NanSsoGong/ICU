package com.ngys3.icu_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String phoneNum = "01032244433";
    String serverIP = "192.168.0.22:5002";

    private VideoView videoView;

    public void saveNameStid(View view) throws InterruptedException, ExecutionException, JSONException {
        /*AndroidUploader uploader = new AndroidUploader();
        String path = Environment.getExternalStorageDirectory()+"/DCIM/Camera/20191211_103002.jpg";
        uploader.uploadPicture(path);*/

        String path = Environment.getExternalStorageDirectory()+"/DCIM/Camera/20191212_200102.mp4";
        NetworkTask2 uploader = new NetworkTask2(serverIP,path);
        int result = Integer.parseInt(uploader.execute().get());

        IsRegistered(phoneNum, serverIP);
        EditText UserNameEditText = (EditText)findViewById(R.id.nameText);
        String newUserName = UserNameEditText.getText().toString();
        EditText UserStIdEditText = (EditText)findViewById(R.id.stidText);
        String newUserStId = UserStIdEditText.getText().toString();
        //입력된 이름과 학번을 String 변수에 저장함.
        //이제 여기서 이 String변수의 값을 json파일로 서버로 이름과 학번 전송해야됨

        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        setContentView(R.layout.takevideo);// 카메라로 동영상 찍는 엑티비티로 넘어감
    }

    public void takeVideo(View view){
        dispatchTakeVideoIntent();
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int IsRegistered(String PhoneNum, String serverIP) throws JSONException, ExecutionException, InterruptedException {
        JSONObject object = new JSONObject();
        object.put("number", PhoneNum);
        object.put("cmd", 1);
        final String json = object.toString();

        String url = "http://".concat(serverIP).concat("/");

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

    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        String url;
        String path;

        NetworkTask2(String url, String path){
            this.url = url;
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            AndroidUploader uploader = new AndroidUploader();
            uploader.uploadPicture(path);
            return "1"; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
