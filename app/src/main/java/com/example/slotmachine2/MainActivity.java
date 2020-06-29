package com.example.slotmachine2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText nPlayer;
    TextView oPlayer, oCredit, oWin, nCredit, nWin; // old and new
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences token = getSharedPreferences("player",MODE_PRIVATE);
        String name = token.getString("name","");
        String credit = token.getString("credit","5000");
        String win = token.getString("win","0");

        Button btn1 = (Button)findViewById(R.id.btn1);
        Button btn2 = (Button)findViewById(R.id.btn2);
        //되돌아갈떄 finish()로 보내면 된다.

        oPlayer = (TextView)findViewById(R.id.player);
        oCredit = (TextView)findViewById(R.id.getCredit);
        oWin = (TextView)findViewById(R.id.getWin);

        nPlayer = (EditText) findViewById(R.id.newPlayer);
        nCredit = (TextView)findViewById(R.id.newGetCredit);
        nWin = (TextView)findViewById(R.id.newGetWin);

        oPlayer.setText(name);
        oCredit.setText(credit);
        oWin.setText(win);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oPlayer.getText().toString() == ""){ //자료 없을경우
                    return;
                }
                Intent it = new Intent(MainActivity.this,slot_Machine_View.class);
                it.putExtra("name",oPlayer.getText().toString());
                it.putExtra("credit",oCredit.getText().toString());
                it.putExtra("win",oWin.getText().toString());
                startActivity(it);
                finish();
                //// finish()로 화면 종료 후 전달
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,slot_Machine_View.class);
                it.putExtra("name",nPlayer.getText().toString());
                it.putExtra("credit",nCredit.getText().toString());
                it.putExtra("win",nWin.getText().toString());
                startActivity(it);
                finish();
                //// finish()로 화면 종료 후 전달
            }
        });

    }
}
