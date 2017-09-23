package jp.ac.dendai.c.jtp.shootingsample;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.Random;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;

public class MainActivity extends Activity {
    private View view;
    private Thread mainThread;
    private static MainActivity instance = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenType();
        setContentView(R.layout.activity_main);
        view = (View)findViewById(R.id.view);

        instance = this;
    }
    @Override
    public void onStart(){
        super.onStart();
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        view.init();
    }
    @Override
    public void onResume(){
        super.onResume();
        setScreenType();
        view.start();
    }
    @Override
    public void onStop(){
        super.onStop();
        while (mainThread != null && mainThread.isAlive()) {
            try {
                view.shutdown = true;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplaySizeCheck.getDisplaySize(this);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void SetContentView(View view){
        setContentView(view);
    }

    private void setScreenType()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //縦固定
        android.view.View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION    //下の戻るボタンとかの非表示
                | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN | android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //上の時計とかを非表示 | 一定時間後に再び非表示
    }
}