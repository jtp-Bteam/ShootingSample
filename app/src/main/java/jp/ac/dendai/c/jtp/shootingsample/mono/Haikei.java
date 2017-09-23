package jp.ac.dendai.c.jtp.shootingsample.mono;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

public class Haikei extends AbstractMono {
    private static final int[] ids = {R.drawable.haikei};
    int width,height;
    float scrollspeed = 10;
    Bitmap bitmap;

    public Haikei(Context context,int width,int height){
        super(context,ids);
        this.width = width;
        this.height = height;

        bitmap = BitmapFactory.decodeResource(context.getResources(),ids[0]);
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
    }
    @Override
    public void move(int width, int height) {
    }

    @Override
    public void step(double t, int width, int height) {
        if(height > p.getY()) p.setY(p.getY()+scrollspeed);
        else p.setY(0);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,p.getIntX(),p.getIntY(),null);
        canvas.drawBitmap(bitmap,p.getIntX(),p.getIntY()-height,null);
    }
}