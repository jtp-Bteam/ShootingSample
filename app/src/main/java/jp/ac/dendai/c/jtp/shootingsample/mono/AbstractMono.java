package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import jp.ac.dendai.c.jtp.shootingsample.MainActivity;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Sound;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public abstract class AbstractMono implements Mono {
    protected final int width;
    protected final int height;
    public Vect p;
    //protected Vect p;
    protected Vect dp;
    protected double period;
    protected Rect rect;
    protected boolean alive;
    protected int cycle;
    protected int clock;
    protected Bitmap[] gazou;
    protected boolean muteki;
    protected int mutekiTime = 100;
    protected int counter;
    protected int zanki = 3;
    public AbstractMono(Context context, int[] ids) {
        p = new Vect();
        dp = new Vect();
        rect = new Rect();
        cycle = ids.length;
        gazou = new Bitmap[cycle];
        for (int i = 0; i < cycle; i++) {
            gazou[i] = BitmapFactory.decodeResource(context.getResources(), ids[i]);
        }
        width = gazou[0].getWidth();
        height = gazou[0].getHeight();
        clock = 0;
        period = 0;
        alive = true;
    }
    @Override
    public void set(int i, int j) {
        p.set(i, j);
    }
    @Override
    public int getScore() {
        return 0;
    }
    @Override
    public void draw(Canvas canvas) {
        int delta = (int) (period / getInterval());
        clock = (clock + delta) % cycle;
        period -= delta * getInterval();
        canvas.drawBitmap(gazou[clock], p.getIntX(), p.getIntY(), null);
    }
    @Override
    public double getInterval() {
        return 1;
    }
    @Override
    public void step(double t, int width, int height) {
        period += t;
        p.add(t, dp);
        move(width, height);
        setRect();
    }
    @Override
    public void stop() {
        dp.set(0, 0);
    }
    @Override
    public void setRect() {
        rect.set(p.getIntX(),
                p.getIntY(),
                p.getIntX() + width,
                p.getIntY() + height);
    }
    @Override
    public Rect getRect() {
        return rect;
    }
    @Override
    public boolean intersect(Mono m) {
        return rect.intersect(m.getRect());
    }
    @Override
    public boolean isAlive() {
        return alive;
    }
    @Override
    public void dead() {
        alive = false;
        Sound.getInstance().playFromSoundPool(R.raw.bakuhatsu);
    }
}