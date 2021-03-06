package jp.ac.dendai.c.jtp.shootingsample;
import android.content.Context;

import java.util.Random;

import jp.ac.dendai.c.jtp.shootingsample.mono.BossTeki;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.RareTeki;
import jp.ac.dendai.c.jtp.shootingsample.mono.Teki;
public class TekiLogic {
    private static double period = 100;
    private final Context context;
    private final HanteiList<Mono> list;
    private double tic;
    Random rand = new Random();
    public TekiLogic(Context context, HanteiList<Mono> list) {
        this.context = context;
        this.list = list;
        tic = 0;
        list.add(createTeki());
    }
    private Mono createTeki() {
        if(rand.nextInt(20) == 0){
            return new BossTeki(context, (int)(rand.nextInt(1000)* DisplaySizeCheck.x), 0);
        }
        else if(rand.nextInt(10) == 0) {
            return new RareTeki(context, (int)(rand.nextInt(1000) * DisplaySizeCheck.x), 30);
        }
        else {
            return new Teki(context, (int)(rand.nextInt(1000) * DisplaySizeCheck.x), 30);
        }
    }
    public void step(double tstep, int width, int height) {
        tic += tstep;
        while (tic > period) {
            list.add(createTeki());
            tic -= period;
        }
    }
}