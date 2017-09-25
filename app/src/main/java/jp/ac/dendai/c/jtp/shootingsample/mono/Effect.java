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
    Mono mono = null;
    boolean loop;
    int offsetX,offsetY;

    public Effect(Context context,float x,float y,boolean loop)
    {
        this.context = context;
        this.x = x;
        this.y = y;
        this.loop = loop;
    }

    public Effect(Context context,Mono m,boolean loop)
    {
        this.context = context;
        mono = m;
        this.loop = loop;
    }


    public void draw(Canvas canvas)
    {
        if(mono == null)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),getIDs()[index]);
            canvas.drawBitmap(bitmap,x-bitmap.getWidth()/2,y-bitmap.getHeight()/2,null);
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),getIDs()[index]);
            canvas.drawBitmap(bitmap,(mono.getRect().centerX()-bitmap.getWidth()/2)+offsetX,(mono.getRect().centerY()-bitmap.getHeight()/2)+offsetY,null);
        }
        step();
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
        else if(loop) index = 0;
        else isAlive = false;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    abstract int getFrameRate();

    abstract int[] getIDs();

    public void stopLoop(){loop = false;}



}
