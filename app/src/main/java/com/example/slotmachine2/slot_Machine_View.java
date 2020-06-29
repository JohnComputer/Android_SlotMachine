package com.example.slotmachine2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

// 2019-11-15 화면 구성 완료 - 다음페이지 성함, Credit, 승 수 등 여러정보 표시 나두고 시간되면 Credit if 문으로 0원 검출하자!!
public class slot_Machine_View extends AppCompatActivity {
    Button startBtn, upBtn, downBtn;
    TextView betValue, creditValue;
    int cnt;
    static int creditValueInt = 0;
    static int betValueInt = 0;

    @SuppressLint("ClickableViewAccessibility") //상위버전
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot__machine__view);


        startBtn = (Button) findViewById(R.id.start);
        upBtn = (Button)findViewById(R.id.up);
        downBtn = (Button)findViewById(R.id.down);

        Intent it = getIntent();
        final String s = it.getStringExtra("name");  // 타입 일치가 필요하다.
        final String s1 = it.getStringExtra("credit");
        final String s2 = it.getStringExtra("win");

            cnt = Integer.parseInt(s2);

        betValue = (TextView)findViewById(R.id.betValue);
        creditValue = (TextView)findViewById(R.id.creditValue);

        TextView text = (TextView) findViewById(R.id.name);
        text.setText("    Player : "+ s);

        creditValue.setText(s1);
        Log.i("tag","three");
//------------------ image View 생성 -----------------------------
        final ImageView[] iv = new ImageView[3];

        iv[0] = (ImageView)findViewById(R.id.img1);
        iv[1] = (ImageView)findViewById(R.id.img2);
        iv[2] = (ImageView)findViewById(R.id.img3);




//------------------- StartButton ----------------------------------------
        startBtn.setOnTouchListener(new View.OnTouchListener() {
            int[] arrTempValue = null;
            public Handler mHandler;
            @Override
            // 키를 눌렀을때 0.2초간의 딜레이를 주며 무한 반복문을 돌며 키를 때엇을 때 멈추고 계산되어진 값이 화면에 비춘다. ( postDelayed/Handler/Tread 공부 필요. 북마크 참조해서 확인)
            public boolean onTouch(View v, MotionEvent event) {
                creditValueInt = Integer.parseInt(creditValue.getText().toString());   //(charSequence)toString으로 텍스트 가져와 Int로 형변환
                betValueInt = Integer.parseInt(betValue.getText().toString());   //(charSequence)toString으로 텍스트 가져와 Int로 형변환
                int setTypeint = 0;
                String setTypeString = null;
                if (creditValueInt < betValueInt)
                    return false;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 200);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        setTypeint = calc(betValueInt,arrTempValue);
                        setTypeint = creditValueInt + setTypeint;
                        setTypeString = String.valueOf(setTypeint);
                        if(setTypeint > creditValueInt){
                            cnt++;
                        }
                        creditValue.setText(setTypeString);
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override public void run() {
                    arrTempValue = getRand(iv);
                    mHandler.postDelayed(this, 200);
                }
            };
        });

        Button ending = (Button)findViewById(R.id.gotoEnding);

        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent its = new Intent(slot_Machine_View.this, ending.class);
                its.putExtra("name",s);
                its.putExtra("credit",creditValue.getText().toString());
                its.putExtra("win",String.valueOf(cnt));
                startActivity(its);
                finish();
                // 피니쉬로 화면 끊고 전달
            }
        });

        Button close = (Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void mBetting(View v){
        TextView betValue = (TextView)findViewById(R.id.betValue);
        int betValueInt = Integer.parseInt(betValue.getText().toString());
        if(v.getId() == R.id.up){
            betValueInt += 50;
        }else{
            if(betValueInt >50){
                betValueInt -= 50;
            }
        }
        betValue.setText(String.valueOf(betValueInt));
    }
//------------- 주사위 랜덤값 삽입 및 이미지 변화 ------------------------------------
    public int[] getRand(ImageView iv[]){
        int temp;
        int[] arrTemp = new int[3];
        Random rand = new Random();
        for(int i=0;i<3;i++){
            temp = (int)rand.nextInt(1000);
            if(temp >= 0 && temp < 300){    //  30%
                arrTemp[i] = 1;
            }else if(temp >= 300 && temp < 550){ // 25%
                arrTemp[i] = 2;
            }else if(temp >= 550 && temp < 750){  // 20%
                arrTemp[i] = 3;
            }else if(temp >= 750 && temp < 900){ // 15%
                arrTemp[i] = 4;
            }else if(temp >= 900 && temp < 1000){ // 10%
                arrTemp[i] = 5;
            }
        }
        for(int i = 0; i < 3; i++){
            if(arrTemp[i] == 1){
                iv[i].setImageResource(R.drawable.dice_1);
            }else if(arrTemp[i] == 2){
                iv[i].setImageResource(R.drawable.dice_2);
            }else if(arrTemp[i] == 3){
                iv[i].setImageResource(R.drawable.dice_3);
            }else if(arrTemp[i] == 4){
                iv[i].setImageResource(R.drawable.dice_4);
            }else if(arrTemp[i] == 5) {
                iv[i].setImageResource(R.drawable.dice_5);
            }
        }
        return arrTemp;
    }

//---------------------- 계산 값 반환 --------------------------------------
    public int calc(int betValueInt,int arrTemp[]){
        if(arrTemp[0] == arrTemp[1] && arrTemp[1] == arrTemp[2]){
            switch (arrTemp[0]){
                case 1:
                    betValueInt *= 3;
                    break;
                case 2:
                    betValueInt *= 4;
                    break;
                case 3:
                    betValueInt *= 5;
                    break;
                case 4:
                    betValueInt *= 7;
                    break;
                case 5:
                    betValueInt *= 10;
                    break;
            }
        }else{
            betValueInt *= -1;
        }
        return betValueInt;
    }

}
