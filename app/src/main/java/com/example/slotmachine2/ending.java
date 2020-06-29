package com.example.slotmachine2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ending extends AppCompatActivity {
    String s1;
    String s2;
    String s3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        Intent it = getIntent();
         s1 = it.getStringExtra("name");  // 타입 일치가 필요하다.
         s2 = it.getStringExtra("credit");
         s3 = it.getStringExtra("win");

        TextView text1 = (TextView) findViewById(R.id.playerName);
        TextView text2 = (TextView) findViewById(R.id.endingCredit);
        TextView text3 = (TextView) findViewById(R.id.endingWin);

        text1.setText(s1);
        text2.setText(s2);
        text3.setText(s3);

        Button btn = (Button)findViewById(R.id.endingButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected  void onPause(){
        super.onPause();
        SharedPreferences prefernces = getSharedPreferences("player", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefernces.edit();
        editor.putString("name",s1);
        editor.putString("credit",s2);
        editor.putString("win",s3);
        editor.commit();
    }
}
