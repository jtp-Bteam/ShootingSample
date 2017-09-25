package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.os.Handler;
import android.view.MotionEvent;
public interface Mikata extends Shooter {
    void setDirection(MotionEvent event, int width, int height);
    void add(float x,float y);
    void powerUp();
    boolean hasZanki(Handler handler);
    void startMuteki();
    boolean isMuteki();
}