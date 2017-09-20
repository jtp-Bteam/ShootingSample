package jp.ac.dendai.c.jtp.shootingsample;

/**
 * Created by PCUser on 2017/07/17.
 */

//入力情報
public class Stick
{
    //タッチした位置の座標
    public float originX,originY;
    //移動後の座標
    public float x,y;
    //原点からの距離
    public float dx,dy;
    //遊びの範囲 MIN_DIST,最大の範囲 MAX_DISTを考慮した原点からの距離
    public float fdx,fdy;
    //タッチしてる状態かどうか
    public boolean isTouching = false;

    //遊び範囲
    public int MIN_DIST = 50;
    //最大の範囲
    public int MAX_DIST = 200;

    //原点をセットするよ
    public void setOrigin(float x,float y)
    {
        originX = x;
        originY = y;

        isTouching = true;
    }

    //xyを更新するよ
    public void setXY(float x,float y)
    {
        //座標更新
        this.x = x;
        this.y = y;

        //原点からの距離の計算
        dx = x-originX;
        dy = y-originY;

        //原点からの距離の修正
        fix();
    }


    //MAX_DIST　MIN_DISTを反映させた入力結果を作るよ
    private void fix()
    {
        double radian = Math.atan2(dy,dx);

        double maxX = MAX_DIST*Math.cos(radian);
        double maxY = MAX_DIST*Math.sin(radian);
        double minX = MIN_DIST*Math.cos(radian);
        double minY = MIN_DIST*Math.sin(radian);

        if(Math.abs(dx) > Math.abs(maxX)) fdx = (float)maxX;
        else if(Math.abs(dx) < Math.abs(minX)) fdx = 0;
        else fdx = dx;

        if(Math.abs(dy) > Math.abs(maxY)) fdy = (float)maxY;
        else if(Math.abs(dy) < Math.abs(minY)) fdy = 0;
        else fdy = dy;
    }

    //とりあえず全部消すよ
    public void reset()
    {
        originX=0;
        originY=0;
        x=0;
        y=0;
        dx=0;
        dy=0;
        fdx = 0;
        fdy = 0;

        isTouching = false;
    }
}
