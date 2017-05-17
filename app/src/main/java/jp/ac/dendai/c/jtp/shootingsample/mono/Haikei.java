package jp.ac.dendai.c.jtp.shootingsample.mono;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
import android.content.Context;
import android.graphics.Canvas;
public class Haikei extends AbstractMono {
    private static final int[] ids = {R.drawable.haikei};
    public Haikei(Context context){
        super(context,ids);
        p.set(0,12);
    }
    @Override
    public void move(int width, int height) {
    }
}