//このクラスの存在意義は、端末の解像度ごとに違う要素を同じものとするため（移動速度など）
//バリバリグローバル変数を使っているところが反省点

package jp.ac.dendai.c.jtp.shootingsample;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

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
