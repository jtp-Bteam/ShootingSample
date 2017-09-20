//このクラスの存在意義は、端末の解像度ごとに違う要素を同じものとするため（移動速度など）

package jp.ac.dendai.c.jtp.shootingsample;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;

/**
 * Created by xxx on 2017/09/19.
 */

public class DisplaySizeCheck {
    public static double x, y;
    public static Point getDisplaySize(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        x = point.x / 1080.0;
        y = point.y / 1920.0;
        return point;
    }
}
