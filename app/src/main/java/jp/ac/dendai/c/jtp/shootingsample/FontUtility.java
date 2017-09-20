package jp.ac.dendai.c.jtp.shootingsample;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by xxx on 2017/09/20.
 */

public class FontUtility {
    /**
     * フォントを assets から読み込みます。
     *
     * @param context コンテキスト。
     * @param path    フォント ファイルを示す assets フォルダからの相対パス。
     *
     * @return 成功時は Typeface インスタンス。それ以外は null。
     */
    public static Typeface getTypefaceFromAssets(Context context, String path ) {
        return Typeface.createFromAsset( context.getAssets(), path );
    }
}
