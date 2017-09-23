package jp.ac.dendai.c.jtp.shootingsample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sound {

    private static Context context;
    private static SoundPool mSoundPool;
    private static int mSoundId1; //爆発音
    private static int mSoundId2; //射撃音
    private static int mSoundId3; //瞬間移動奴音
    private static MediaPlayer mediaPlayer;

    public Sound(Context context){
        this.context = context;
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mSoundId1 = mSoundPool.load(context, R.raw.bakuhatsu, 0);
        mSoundId2 = mSoundPool.load(context, R.raw.shot3, 0);
        mSoundId3 = mSoundPool.load(context, R.raw.shyuin, 0);
    }

    public static void playFromSoundPool(int name) {
        // 再生
        if(name == R.raw.bakuhatsu) mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
        else if(name == R.raw.shot3) mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
        else if(name == R.raw.shyuin) mSoundPool.play(mSoundId3, 1.0F, 1.0F, 1, 0, 1.0F);
    }

    public static void playFromMediaPlayer() {
        mediaPlayer = MediaPlayer.create(context, R.raw.polp);
        mediaPlayer.start();
    }

    public static void soundRelease(){
        mSoundPool.release();
        mediaPlayer.release();
    }

}
