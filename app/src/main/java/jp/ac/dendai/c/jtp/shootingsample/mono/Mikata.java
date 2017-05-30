package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.view.MotionEvent;
public interface Mikata extends Shooter {
    void setDirection(MotionEvent event, int width, int height);
}