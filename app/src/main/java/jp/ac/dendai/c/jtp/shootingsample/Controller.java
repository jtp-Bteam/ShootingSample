package jp.ac.dendai.c.jtp.shootingsample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PCUser on 2017/07/17.
 */

public class Controller implements View.OnTouchListener{

    private int mPointerID1,mPointerID2;
    private Stick[] stick = {new Stick(),new Stick()};

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int eventAction = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (eventAction) {
            //最初の1本目がタッチされたときに呼ばれるよ
            case MotionEvent.ACTION_DOWN:
                // 最初の指の設定
                mPointerID1 = pointerId;
                mPointerID2 = -1;
                onDown(0,event.getX(event.findPointerIndex(mPointerID1)),event.getY(event.findPointerIndex(mPointerID1)));
                break;

            //2本目以降がタッチされたときに呼ばれるよ
            case MotionEvent.ACTION_POINTER_DOWN:
                // 2本目の指
                if (mPointerID2 == -1)
                {
                    mPointerID2 = pointerId;
                    onDown(1,event.getX(event.findPointerIndex(mPointerID2)),event.getY(event.findPointerIndex(mPointerID2)));
                }
                //1本目の指が離されたけど2本目は押したままでさらに1本目がもう一度タッチされたとき（言語野崩壊）
                else if (mPointerID1 == -1)
                {
                    mPointerID1 = pointerId;
                    onDown(0,event.getX(event.findPointerIndex(mPointerID1)),event.getY(event.findPointerIndex(mPointerID1)));
                }
                break;

            //指が離れたけどまだ1本以上残ってるときに呼ばれるよ
            case MotionEvent.ACTION_POINTER_UP:
                //1本目の指が離れたよ
                if (mPointerID1 == pointerId)
                {
                    mPointerID1 = -1;
                    onUp(0);
                }
                //2本目の指が離れたよ
                else if (mPointerID2 == pointerId)
                {
                    mPointerID2 = -1;
                    onUp(1);
                }
                //他は無視
                break;

            //指が全部離れたときに呼ばれるよ
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mPointerID1 = -1;
                mPointerID2 = -1;
                onUp(0);
                onUp(1);
                break;

            //指が移動したときに呼ばれるよ
            case MotionEvent.ACTION_MOVE:
                // 指の座標の更新

                //1本目
                if (mPointerID1 >= 0)
                {
                    int ptrIndex = event.findPointerIndex(mPointerID1);
                    onMove(0,event.getX(ptrIndex),event.getY(ptrIndex));
                }
                //2本目
                if (mPointerID2 >= 0)
                {
                    int ptrIndex = event.findPointerIndex(mPointerID2);
                    onMove(1,event.getX(ptrIndex),event.getY(ptrIndex));
                }
                break;
        }
        return true;
    }

    //動いたときにこの関数が呼ばれるようになってるよ index=0なら1本目の指　index=1なら2本目の指　x,yは移動後の座標
    private void onMove(int index,float x,float y){stick[index].setXY(x,y);}
    //指が離れたときに呼ばれるようになってるよ　index=0なら1本目の指　index=1なら2本目の指
    private void onUp(int index){stick[index].reset();}
    //指が押されたに呼ばれるようになってるよ　index=0なら1本目の指　index=1なら2本目の指　x,yは押されたところの座標
    private void onDown(int index,float x,float y){stick[index].setOrigin(x,y);}

    public Stick getStick(int index){return stick[index];};

    public void draw(Canvas canvas)
    {

        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setAlpha(70);
        canvas.drawCircle(stick[0].originX,stick[0].originY,stick[0].MAX_DIST, paint);
        paint.setColor(Color.BLUE);
        paint.setAlpha(70);
        canvas.drawCircle(stick[0].originX+stick[0].fdx,stick[0].originY+stick[0].fdy,50, paint);
        paint.setColor(Color.BLACK);
        paint.setAlpha(70);
        canvas.drawCircle(stick[0].originX,stick[0].originY,stick[0].MIN_DIST, paint);
    }
}
