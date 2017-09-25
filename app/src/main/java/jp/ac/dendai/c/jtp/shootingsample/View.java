package jp.ac.dendai.c.jtp.shootingsample;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.shootingsample.mono.Effect;
import jp.ac.dendai.c.jtp.shootingsample.mono.Explosion;
import jp.ac.dendai.c.jtp.shootingsample.mono.Haikei;
import jp.ac.dendai.c.jtp.shootingsample.mono.Anata;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mikata;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.PowerUpEffect;
import jp.ac.dendai.c.jtp.shootingsample.mono.PowerUpMono;
import jp.ac.dendai.c.jtp.shootingsample.mono.Shootable;

import static java.lang.Thread.sleep;

public class View extends SurfaceView {
    final static long tic = 10;
    public volatile boolean shutdown;
    private int width;
    private int height;
    private DrawList drawList;
    private Mikata mikata;
    private HanteiList<Mono> tekiList;
    private HanteiList<Shootable> tamaList;
    private HanteiList<PowerUpMono> itemList;
    private ArrayList<Effect> effectList;
    private Context context;
    private DrawThread drawThread;
    private MoveThread moveThread;
    private Score score;
    private TekiLogic tekiLogic;
    private ItemLogic itemLogic;
    private final Object lock;
    private Handler handler;
    private Controller controller;
    Stick stick;


    public View(Context context, AttributeSet attr) {
        super(context,attr);
        this.context = context;

        Point size = new Point();
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        display.getRealSize(size);

        width = size.x;
        height =size.y;
        handler = new Handler();
        lock = new Object();
    }
//    public View(Context context, int width, int height) {
//        super(context);
//        this.context = context;
//        this.width = width;
//        this.height = height;
//        lock = new Object();
//    }
    public void init() {
        drawList = new DrawList();
        score = new Score();
        drawList.addScore(score);
        drawList.add(new Haikei(context,width,height));

        tamaList = new HanteiList<>();
        mikata = new Anata(context, tamaList);
        mikata.set(width / 2, height * 3 / 4);
        drawList.add(mikata);

        tekiList = new HanteiList<>();
        tekiLogic = new TekiLogic(context, tekiList);

        itemList = new HanteiList<>();
        itemLogic = new ItemLogic(context, itemList);

        effectList = new ArrayList<>();

        drawList.addList(tekiList);
        drawList.addList(tamaList);
        drawList.addList(itemList);
        drawList.addList(effectList);

        destroyThread(drawThread);
        destroyThread(moveThread);
        drawThread = new DrawThread();
        moveThread = new MoveThread();

        controller = new Controller();

        stick = controller.getStick(0);

        setOnTouchListener(controller);
    }
    public void start(){
        shutdown = false;
        drawThread.start();
        moveThread.start();
    }
    private void destroyThread(Thread t) {
        if (t != null) {
            shutdown = true;
            while (t.isAlive()) {
                System.out.println(t.getClass().getName()+":"+t.getState());
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private void draw() {
        synchronized (lock) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas == null) return;
            drawList.draw(canvas);
            if(!(stick.originX == 0 && stick.originY == 0)) controller.draw(canvas);    //ここ消せばパッド表示だけ消えるよ
            getHolder().unlockCanvasAndPost(canvas); // 描画を終了
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mikata.setDirection(event, width, height);
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                mikata.stop();
//                if (shutdown) {
//                    init();
//                    start();
//                }
//                performClick();
//                break;
//        }
        return true;
    }
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }


    class MoveThread extends Thread {
        @Override
        public void run() {
            double previous = (double) System.currentTimeMillis();
            double now;
            while (!shutdown || effectList.size()!=0) {
                System.out.println("move");
                Debug.append("tamasize", "" + tamaList.size());
                if(!shutdown)
                {
                    synchronized (lock) {
                        now = System.currentTimeMillis();
                        double tstep = (now - previous) / tic;
                        //    Debug.append("tstep",""+tstep);
                        mikata.add((int)(stick.fdx / 10 * DisplaySizeCheck.x), (int)(stick.fdy / 10 * DisplaySizeCheck.y));
                        drawList.step(tstep, width, height);
                        tekiLogic.step(tstep, width, height);
                        itemLogic.step(tstep);
                    }
                    previous = now;
                }

                score.add(1);
                for (Shootable s : tamaList) { //敵の死の判定
                    Mono m = tekiList.atari(s.getRect());
                    if (m != null) {
                        score.add(m.getScore());
                        effectList.add(new Explosion(context,m.getRect().centerX(),m.getRect().centerY()));
                        s.dead();
                        m.dead();
                    }
                }
                synchronized (lock) {
                    drawList.update();
                }

                if (tekiList.atari(mikata.getRect()) != null && !mikata.isMuteki() && !shutdown) { //自分への衝突判定
                    if(!mikata.hasZanki(handler))
                    {
                        drawList.stop();
                        shutdown = true;
                        effectList.add(new Explosion(context,mikata.getRect().centerX(),mikata.getRect().centerY()));
                        Sound.getInstance().playFromSoundPool(R.raw.bakuhatsu);
                        mikata.add(1000,1000);
                    }
                    else
                    {
                        Sound.getInstance().playFromSoundPool(R.raw.bakuhatsu);
                        effectList.add(new Explosion(context,mikata.getRect().centerX(),mikata.getRect().centerY()));
                        mikata.startMuteki();
                    }
                }

                if(!mikata.isMuteki())  //無敵状態でアイテムを取らせない
                {
                    PowerUpMono pum = itemList.atari(mikata.getRect()); //アイテム取得判定
                    if(pum != null){
                        score.add(pum.getScore());
                        mikata.powerUp();
                        effectList.add(new PowerUpEffect(context,mikata,false));
                        pum.dead();
                    }
                }


                try {
                    sleep((long) tic);
                } catch (InterruptedException e) {
                    shutdown = true;
                }
            }
        }
    }
    class DrawThread extends Thread {
        @Override
        public void run() {
            while (!shutdown || effectList.size()!=0) {
                System.out.println("draw");
                draw();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    shutdown = true;
                }
            }
            handler.post(new GameOver());
        }
    }

    class GameOver implements Runnable {    //ここにshutdownがtrueになったときの処理を書けば動くよ！
        @Override
        public void run() {
            Intent intent  = new Intent(((Activity)context).getApplication(),ResultActivity.class);
            intent.putExtra("Score",score.getScore());
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }
}