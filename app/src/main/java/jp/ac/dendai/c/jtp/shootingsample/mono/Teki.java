package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.content.Context;
import android.util.Log;

import java.util.Random;

import jp.ac.dendai.c.jtp.shootingsample.DisplaySizeCheck;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public class Teki extends AbstractMono {
    private static final int[] ids = {R.drawable.enemy1, R.drawable.enemy2,R.drawable.enemy3,R.drawable.enemy2};
    private int dpindex;
    Random rand = new Random();
    private final int x = (int)(3 - (rand.nextInt(7)) * DisplaySizeCheck.x);
    private final int y = (int)(5 - (rand.nextInt(3)) * DisplaySizeCheck.y);
    private Vect[] dps = {new Vect(x, y), new Vect(x, y)};
    private double dpcycle = 0;//200 + rand.nextInt(300); //500から1000の間にしてない
    private double dpcounter;
    public Teki(Context context) {
        super(context, ids);
    }
    public Teki(Context context, int x, int y) {
        super(context, ids);
        set(x, y);
        dp.set(dps[0]);
        dpindex = 0;
        dpcounter = 0;
    }
    @Override
    public void move(int width, int height) {
        if (p.getX() > width) p.setX(-this.width);
        else if (p.getX() < -this.width) p.setX(width);
        if (p.getY() > height) p.setY(-this.height);
        else if (p.getY() < -this.height) p.setY(height);
    }
    @Override
    public double getInterval() {
        return 23;
    }
    @Override
    public int getScore() {
        return 1000;
       }
    @Override
    public void step(double t, int width, int height) {
        period += t;
        if (dpcounter + t > dpcycle) {
            p.add(dpcycle - dpcounter, dps[dpindex]);
            dpindex = (dpindex + 1) % dps.length;
            dpcounter = dpcounter + t - dpcycle;
            p.add(dpcounter, dps[dpindex]);
        } else {
            p.add(t, dps[dpindex]);
            dpcounter += t;
        }
        move(width, height);
        setRect();
    }
}