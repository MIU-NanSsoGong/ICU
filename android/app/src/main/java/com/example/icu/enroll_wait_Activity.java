package com.example.icu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class enroll_wait_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_wait_);
    }

    public void onBackPressed() {      // 뒤로가기 버튼 : 종료확인 메시지
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}