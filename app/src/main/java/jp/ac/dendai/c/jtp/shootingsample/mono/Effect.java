package jp.ac.dendai.c.jtp.shootingsample.mono;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by PCUser on 2017/09/23.
 */

public abstract class Effect{

    Context context;
    float x,y;
    int index,count;
    boolean isAlive = true;

    public Effect(Context context,float x,float y)
    {
        this.context = context;
        this.x = x;
        this.y = y;
    }


    public void draw(Canvas canvas)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),getIDs()[index]);
        canvas.drawBitmap(bitmap,x-bitmap.getWidth()/2,y-bitmap.getHeight()/2,null);
    }

    public void step()
    {
        if(getIDs().length > index+1)
        {
            if(count >= getFrameRate())
            {
                index++;
                count=0;
            }
            else count++;
        }
        else
        {
            isAlive = false;
        }
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    abstract int getFrameRate();

    abstract int[] getIDs();



}
