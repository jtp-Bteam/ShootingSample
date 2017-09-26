package jp.ac.dendai.c.jtp.shootingsample;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Score {
    private int score;
    private Paint paint = new Paint();
    public Score() {
        paint.setColor(Color.WHITE);
        paint.setTextSize(96);
    }
    public void decrease(int point) {
        score -= point;
    }
    public void draw(Canvas canvas) {
        String sc = "000000000" + score;
        Typeface typeface;
        typeface = FontUtility.getTypefaceFromAssets(MainActivity.getInstance(), "Valkyrie-Extended.ttf");
        paint.setTypeface(typeface);
        canvas.drawText(sc.substring(sc.length() - 10), 0, paint.getTextSize(), paint);
    }
    public void add(int point) {
        score += point;
    }

    public int getScore(){return score;}
}