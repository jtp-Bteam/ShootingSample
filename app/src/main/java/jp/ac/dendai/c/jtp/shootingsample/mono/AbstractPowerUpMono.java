package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;

import jp.ac.dendai.c.jtp.shootingsample.Vect;

public abstract class AbstractPowerUpMono extends AbstractMono implements PowerUpMono {
    public final Vect itemdp = new Vect(0, 1);
    public AbstractPowerUpMono(Context context, int[] ids) {
        super(context, ids);
    }
}
