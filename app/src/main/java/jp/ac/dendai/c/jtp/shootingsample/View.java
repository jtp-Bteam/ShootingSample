package jp.ac.dendai.c.jtp.shootingsample;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.shootingsample.mono.Effect;
import jp.ac.dendai.c.jtp.shootingsample.mono.Explosion;
import jp.ac.dendai.c.jtp.shootingsample.mono.Haikei;
import jp.ac.dendai.c.jtp.shootingsample.mono.Anata;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mikata;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.PowerUpMono;
import jp.ac.dendai.c.jtp.shootingsample.mono.Shootable;
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
    Stick stick;


    public View(Context context, Point p) {
        super(context);
        this.context = context;
        width = p.x;
        height = p.y;
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
        drawList.add(new Haikei(context));

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

        Controller controller = new Controller();

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
                try {
                    Thread.sleep(10);
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
            getHolder().unlockCanvasAndPost(canvas); // 描画を終了
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        System.out.println("waowao");
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
            while (!shutdown) {
                Debug.append("tamasize", "" + tamaList.size());
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
                if (tekiList.atari(mikata.getRect()) != null) { //自分への衝突判定
                    drawList.stop();
                    shutdown = true;
                    break;
                }

                PowerUpMono pum = itemList.atari(mikata.getRect()); //アイテム取得判定
                if(pum != null){
                    score.add(pum.getScore());
                    mikata.powerUp();
                    pum.dead();
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
            while (!shutdown) {
                draw();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    shutdown = true;
                }
            }
        }
    }
}