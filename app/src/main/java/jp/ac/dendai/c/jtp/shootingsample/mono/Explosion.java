package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;
import android.graphics.Canvas;

import jp.ac.dendai.c.jtp.shootingsample.R;

/**
 * Created by PCUser on 2017/09/23.
 */

public class Explosion extends Effect {

    public Explosion(Context context,float x,float y) {
        super(context,x,y);
    }

    @Override
    int getFrameRate() {
        return 0;
    }

    @Override
    int[] getIDs() {
        return new int[]{R.drawable.explosion1,R.drawable.explosion2,R.drawable.explosion3,R.drawable.explosion4,
                         R.drawable.explosion5,R.drawable.explosion6,R.drawable.explosion7,R.drawable.explosion8,R.drawable.explosion9};
    }
}
