package jp.ac.dendai.c.jtp.shootingsample.mono;
import android.content.Context;
import jp.ac.dendai.c.jtp.shootingsample.HanteiList;
import jp.ac.dendai.c.jtp.shootingsample.R;
import jp.ac.dendai.c.jtp.shootingsample.Sound;
import jp.ac.dendai.c.jtp.shootingsample.Vect;
public abstract class AbstractShooter extends AbstractMono implements Shooter {
    private HanteiList<Shootable> list;
    private Shootable tama;
    public AbstractShooter(Context context, int[] ids, HanteiList<Shootable> list, Shootable tama) {
        super(context, ids);
        this.list = list;
        this.tama = tama;
        tama.setRect();
    }
    @Override
    public void shoot(Vect dp) {
        Shootable newtama = tama.getInstance();
        Vect ve = p.front(tama.getRect());
        ve.add(new Vect((getRect().centerX()-p.getX())/2,0));
        newtama.init(ve, dp);
        list.add(newtama);
        Sound.getInstance().playFromSoundPool(R.raw.shot3);
    }
}