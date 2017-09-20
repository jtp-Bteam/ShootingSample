package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;

import java.util.Random;

import jp.ac.dendai.c.jtp.shootingsample.DisplaySizeCheck;
import jp.ac.dendai.c.jtp.shootingsample.MainActivity;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Vect;

/**
 * Created by xxx on 2017/09/20.
 */

public class RareTeki extends AbstractMono{
    private static final int[] ids = {R.drawable.hiragana_34_te, R.drawable.hiragana_12_ki};
    private int dpindex;
    Random rand = new Random();
    private final int x = (int)(3 * DisplaySizeCheck.x);
    private final int y = (int)(20 * DisplaySizeCheck.y);
    private Vect[] dps = {new Vect(x, y), new Vect(-x, y)};
    private double dpcycle = 10;//200 + rand.nextInt(300); //500から1000の間にしてない
    private double dpcounter;
    public RareTeki(Context context) {
        super(context, ids);
    }
    public RareTeki(Context context, int x, int y) {
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
        if (p.getY() > height){
            p.setX(rand.nextInt(1000)* DisplaySizeCheck.x);
            p.setY(-this.height);
        }
        else if (p.getY() < -this.height) p.setY(height);
    }
    @Override
    public double getInterval() {
        return 23;
    }
    @Override
    public int getScore() {
        return 5000;
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
