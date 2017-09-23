package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.content.Context;
import android.view.MotionEvent;
import jp.ac.dendai.c.jtp.shootingsample.Debug;
import jp.ac.dendai.c.jtp.shootingsample.DisplaySizeCheck;
import jp.ac.dendai.c.jtp.shootingsample.HanteiList;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public class Anata extends AbstractShooter implements Mikata {
    private static final int[] ids = {R.drawable.anata};
    private double shootperiod;
    private static final Vect tamadp = new Vect(0, -30);
    private double shoottic;
    public Anata(Context context, HanteiList<Shootable> tamalist) {
        super(context, ids, tamalist, new Tama(context));
        shoottic = 0;
        shootperiod = 100;
    }
    @Override
    public void move(int width, int height) {
        if (p.getX() > width-this.width) p.setX(width-this.width); //右
        else if (p.getX() < 0) p.setX(0); //左
        if (p.getY() > height - (250.0 * DisplaySizeCheck.y)) p.setY(height - (250.0 * DisplaySizeCheck.y)); //下
        else if (p.getY() < -this.height) ultimate(); //上
    }
    @Override
    public void setDirection(MotionEvent event, int width, int height) {
        final double delta = 1;
        float px = event.getX();
        Debug.append("position", "" + width + " " + px);
        if (px < width / 2) {
            dp.setX(-delta);
        } else {
            dp.setX(delta);
        }
    }
    @Override
    public void step(double t, int width, int height) {
        super.step(t, width, height);
        shoottic += t;
        while (shoottic > shootperiod) {
            shoot(tamadp);
            shoottic -= shootperiod;
        }
    }

    @Override
    public void add(float x, float y) {
        p.setX(p.getX()+x);
        p.setY(p.getY()+y);
    }

    @Override
    public void powerUp(){
        if(shootperiod > 50) shootperiod -= 10;
        //else if()
    }

    public void ultimate(){
        shootperiod = 10;
    }
}