package jp.ac.dendai.c.jtp.shootingsample;
import android.content.Context;
<<<<<<< HEAD

import java.util.Random;

import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.RareTeki;
import jp.ac.dendai.c.jtp.shootingsample.mono.Teki;
public class TekiLogic {
    private static double period = 100;
    private final Context context;
    private final HanteiList<Mono> list;
    private double tic;
    Random rand = new Random();
=======
import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.Teki;
public class TekiLogic {
    private static double period = 1000;
    private final Context context;
    private final HanteiList<Mono> list;
    private double tic;
>>>>>>> 235a13045a9c0651c467207ba41048e0bb82926d
    public TekiLogic(Context context, HanteiList<Mono> list) {
        this.context = context;
        this.list = list;
        tic = 0;
        list.add(createTeki());
    }
    private Mono createTeki() {
<<<<<<< HEAD
        if(rand.nextInt(10) != 0) {
            return new Teki(context, rand.nextInt(1000), 30);
        }
        else {
            return new RareTeki(context, rand.nextInt(1000), 30);
        }
=======
        return new Teki(context, 200, 30);
>>>>>>> 235a13045a9c0651c467207ba41048e0bb82926d
    }
    public void step(double tstep, int width, int height) {
        tic += tstep;
        while (tic > period) {
            list.add(createTeki());
            tic -= period;
        }
    }
}