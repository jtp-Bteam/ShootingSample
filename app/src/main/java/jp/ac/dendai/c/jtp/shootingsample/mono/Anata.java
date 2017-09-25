package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import jp.ac.dendai.c.jtp.shootingsample.Debug;
import jp.ac.dendai.c.jtp.shootingsample.DisplaySizeCheck;
import jp.ac.dendai.c.jtp.shootingsample.HanteiList;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Sound;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public class Anata extends AbstractShooter implements Mikata {
    private static final int[] ids = {R.drawable.player1,R.drawable.player2,R.drawable.player3,R.drawable.player2};
    private double shootperiod;
    private static final Vect tamadp = new Vect(0, -30);
    private double shoottic;
    private Context context;
    public Anata(Context context, HanteiList<Shootable> tamalist) {
        super(context, ids, tamalist, new Tama(context));
        shoottic = 0;
        shootperiod = 100;
        this.context = context;
        LinearLayout layout = (LinearLayout)((Activity)context).findViewById(R.id.zanki);
        for(int i =0;i<zanki;i++)
        {
            AppCompatImageView imageView= new AppCompatImageView(context);
            imageView.setImageResource(ids[0]);
            layout.addView(imageView);
        }
    }

    public void draw(Canvas canvas) {

        int delta = (int) (period / getInterval());
        clock = (clock + delta) % cycle;
        period -= delta * getInterval();
        if(!muteki)        canvas.drawBitmap(gazou[clock], p.getIntX(), p.getIntY(), null);
        else
        {
            Paint paint = new Paint();
            canvas.drawBitmap(gazou[clock], p.getIntX(), p.getIntY(), paint);
        }
    }

    @Override
    public void move(int width, int height) {
        if (p.getX() > width-this.width) p.setX(width-this.width); //右
        else if (p.getX() < 0) p.setX(0); //左
//        if (p.getY() > height - (250.0 * DisplaySizeCheck.y)) p.setY(height - (250.0 * DisplaySizeCheck.y)); //下
        if(p.getY() > height-this.height) p.setY(height-this.height);
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
        if(muteki)
        {
            counter++;
            if (counter >= mutekiTime) {
                counter = 0;
                muteki = false;
            }
        }
        else
        {
            shoottic += t;
            while (shoottic > shootperiod) {
                shoot(tamadp);
                shoottic -= shootperiod;
            }
        }
    }

    @Override
    public void add(float x, float y) {
        p.setX(p.getX()+x);
        p.setY(p.getY()+y);
    }

    @Override
    public void powerUp(){
        Sound.getInstance().playFromSoundPool(R.raw.decision4);
        if(shootperiod > 50) shootperiod -= 10;
        //else if()
    }

    public void ultimate(){
        shootperiod = 10;
    }

    @Override
    public boolean hasZanki(Handler handler) {
        if(zanki >= 1)
        {
            muteki = true;
            zanki--;
            handler.post(new Runnable() {       //Layout側の残機表示を無理やり消してる
                @Override
                public void run() {
                    LinearLayout layout = (LinearLayout)((Activity)context).findViewById(R.id.zanki);
                    layout.removeViewAt(0);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void startMuteki() { //被弾時
        muteki = true;
    }

    @Override
    public boolean isMuteki() {
        return muteki;
    }

}