package jp.ac.dendai.c.jtp.shootingsample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by PCUser on 2017/09/25.
 */

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenType();
        setContentView(R.layout.activity_result);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Valkyrie-BoldExtended.ttf");

        Intent intent = getIntent();
        int score = intent.getIntExtra("Score",0);
        TextView textView = (TextView)findViewById(R.id.textView2);
        if(textView == null) System.out.println("null");
        textView.setText("Score: " + score);
        textView.setTypeface(typeface);

        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        int useId = pref.getInt("score", 0);
        TextView tv5 = (TextView)findViewById(R.id.textView5);
        tv5.setText("HighScore: " + useId);
        tv5.setTypeface(typeface);

        TextView tv7 = (TextView)findViewById(R.id.textView7);
        typeface = Typeface.createFromAsset(getAssets(), "FenixStd.otf");
        tv7.setTypeface(typeface);
    }

    @Override
    public void onResume(){
        super.onResume();

        Sound.getInstance().init(this);
        Sound.getInstance().playFromMediaPlayer(R.raw.madoka);
    }

    @Override
    public void onPause(){
        super.onPause();
        //リリース
        Sound.getInstance().soundRelease();
    }

    private void setScreenType() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //縦固定
        android.view.View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION    //下の戻るボタンとかの非表示
                | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN | android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //上の時計とかを非表示 | 一定時間後に再び非表示
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            //Intent intent = new Intent(getApplication(),MainActivity.class);
            Intent intent = new Intent(getApplication(),LaunchActivity.class);
            startActivity(intent);
        }
        return super.onTouchEvent(event);

    }
}
