package com.ngys3.icu_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {


    private VideoView videoView;

    public void saveNameStid(View view){
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
            videoView.setVideoURI(videoUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
