package jp.ac.dendai.c.jtp.shootingsample;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
<<<<<<< HEAD
import android.graphics.Typeface;

=======
>>>>>>> 235a13045a9c0651c467207ba41048e0bb82926d
public class Score {
    private int score;
    private Paint paint = new Paint();
    public Score() {
        paint.setColor(Color.WHITE);
<<<<<<< HEAD
        paint.setTextSize(96);
=======
>>>>>>> 235a13045a9c0651c467207ba41048e0bb82926d
    }
    public void decrease(int point) {
        score -= point;
    }
    public void draw(Canvas canvas) {
        String sc = "000000000" + score;
<<<<<<< HEAD
        Typeface typeface;
        typeface = FontUtility.getTypefaceFromAssets(MainActivity.getInstance(), "Valkyrie-Extended.ttf");
        paint.setTypeface(typeface);
=======
>>>>>>> 235a13045a9c0651c467207ba41048e0bb82926d
        canvas.drawText(sc.substring(sc.length() - 10), 0, paint.getTextSize(), paint);
    }
    public void add(int point) {
        score += point;
    }
}