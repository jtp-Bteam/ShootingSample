package jp.ac.dendai.c.jtp.shootingsample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

        Intent intent = getIntent();
        int score = intent.getIntExtra("Score",0);
        TextView textView = (TextView)findViewById(R.id.textView2);
        if(textView == null) System.out.println("null");
        textView.setText(score+"");
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
            Intent intent = new Intent(getApplication(),MainActivity.class);
            startActivity(intent);

        }
        return super.onTouchEvent(event);

    }
}
