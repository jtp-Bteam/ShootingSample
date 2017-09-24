package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;

import jp.ac.dendai.c.jtp.shootingsample.R;

/**
 * Created by PCUser on 2017/09/24.
 */

public class PowerUpEffect extends Effect {

    public PowerUpEffect(Context context, Mono m, boolean loop) {
        super(context, m, loop);
        offsetX = 100;

    }

    @Override
    int getFrameRate() {
        return 1;
    }

    @Override
    int[] getIDs() {
        return new int[]{R.drawable.powerup1,R.drawable.powerup2,R.drawable.powerup3,R.drawable.powerup4,R.drawable.powerup5,
                         R.drawable.powerup6,R.drawable.powerup7,R.drawable.powerup8,R.drawable.powerup9,R.drawable.powerup10};
    }
}
