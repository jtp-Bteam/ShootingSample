package jp.ac.dendai.c.jtp.shootingsample;

import android.content.Context;

import java.util.Random;

import jp.ac.dendai.c.jtp.shootingsample.mono.PowerUpMono;
import jp.ac.dendai.c.jtp.shootingsample.mono.RapidShotItem;

public class ItemLogic { //ほとんどTekiLogicをコピペしただけ

    private static double period = 100;
    private final Context context;
    private final HanteiList<PowerUpMono> list;
    private double tic;
    Random rand = new Random();
    public ItemLogic(Context context, HanteiList<PowerUpMono> list) {
        this.context = context;
        this.list = list;
        tic = 0;
        //list.add(createItem());
    }

    private PowerUpMono createItem() {
        return new RapidShotItem(context, (int)(rand.nextInt(1000) * DisplaySizeCheck.x), 30);
    }

    public void step(double tstep) {
        tic += tstep;
        while (tic > period) {
            if(rand.nextInt(20) == 0) list.add(createItem());
            tic -= period;
        }
    }
}
