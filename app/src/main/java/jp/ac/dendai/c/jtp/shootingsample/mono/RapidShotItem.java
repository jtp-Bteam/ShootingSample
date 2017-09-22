package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;

import jp.ac.dendai.c.jtp.shootingsample.R;

public class RapidShotItem extends AbstractPowerUpMono {
    private static final int[] ids = {R.drawable.tama1, R.drawable.tama2};
    private Context context;
    public RapidShotItem(Context context) {
        super(context, ids);
    }
    public RapidShotItem(Context context, int x, int y) {
        super(context, ids);
        set(x, y);
        this.context = context;
        dp.set(itemdp.getX(), itemdp.getY());
    }

    @Override
    public void move(int width, int height) {
        if (p.getX() > width) alive = false;
        else if (p.getX() < -this.width) alive = false;
        if (p.getY() > height) alive = false;
        else if (p.getY() < -this.height) alive = false;
    }

    @Override
    public int getScore() {
        return 100;
    }

    public PowerUpMono getInstance() {
        return new RapidShotItem(context);
    }

    @Override
    public double getInterval() {
        return 10;
    }
}
