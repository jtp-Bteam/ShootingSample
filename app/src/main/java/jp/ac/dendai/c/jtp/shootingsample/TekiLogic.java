package jp.ac.dendai.c.jtp.shootingsample;
import android.content.Context;
import jp.ac.dendai.c.jtp.shootingsample.mono.Mono;
import jp.ac.dendai.c.jtp.shootingsample.mono.Teki;
public class TekiLogic {
    private static double period = 1000;
    private final Context context;
    private final HanteiList<Mono> list;
    private double tic;
    public TekiLogic(Context context, HanteiList<Mono> list) {
        this.context = context;
        this.list = list;
        tic = 0;
        list.add(createTeki());
    }
    private Mono createTeki() {
        return new Teki(context, 200, 30);
    }
    public void step(double tstep, int width, int height) {
        tic += tstep;
        while (tic > period) {
            list.add(createTeki());
            tic -= period;
        }
    }
}