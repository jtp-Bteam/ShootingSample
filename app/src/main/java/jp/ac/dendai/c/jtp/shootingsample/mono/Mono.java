package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.graphics.Canvas;
import android.graphics.Rect;
public interface Mono {
    void set(int i, int j);
    int getScore();
    void draw(Canvas canvas);
    void stop();
    void move(int width, int height);
    void step(double t, int width, int height);
    Rect getRect();
    boolean intersect(Mono m);
    boolean isAlive();
    void dead();
    double getInterval();
    void setRect();
}