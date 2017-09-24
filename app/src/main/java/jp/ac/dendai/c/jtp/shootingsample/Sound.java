package jp.ac.dendai.c.jtp.shootingsample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sound {

    private Context context = MainActivity.getInstance();
    private SoundPool mSoundPool;
    private int mSoundId1; //爆発音
    private int mSoundId2; //射撃音
    private int mSoundId3; //瞬間移動奴音
    private int mSoundId4; //アイテム取得音
    private int mSoundId5; //ボス撃破音
    private MediaPlayer mediaPlayer;

    private static Sound sound = new Sound();

    public Sound(){

//        this.context = MainActivity.getInstance();
//        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//        mSoundId1 = mSoundPool.load(context, R.raw.bakuhatsu, 0);
//        mSoundId2 = mSoundPool.load(context, R.raw.shot3, 0);
//        mSoundId3 = mSoundPool.load(context, R.raw.shyuin, 0);
//        mSoundId4 = mSoundPool.load(context, R.raw.decision4, 0);
    }

    public void init(){
        this.context = MainActivity.getInstance();
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        mSoundId1 = mSoundPool.load(context, R.raw.bakuhatsu, 0);
        mSoundId2 = mSoundPool.load(context, R.raw.shot3, 0);
        mSoundId3 = mSoundPool.load(context, R.raw.shyuin, 0);
        mSoundId4 = mSoundPool.load(context, R.raw.decision4, 0);
        mSoundId5 = mSoundPool.load(context, R.raw.bakuhatsubig, 0);
    }

    public void playFromSoundPool(int name) {
        //System.out.println(mSoundId1);
        // 再生
        if(name == R.raw.bakuhatsu) mSoundPool.play(mSoundId1, 1.0F, 1.0F, 0, 0, 1.0F);
        else if(name == R.raw.shot3) mSoundPool.play(mSoundId2, 1.0F, 1.0F, 0, 0, 1.0F);
        else if(name == R.raw.shyuin) mSoundPool.play(mSoundId3, 1.0F, 1.0F, 1, 0, 1.0F);
        else if(name == R.raw.decision4) mSoundPool.play(mSoundId4, 1.0F, 1.0F, 1, 0, 1.0F);
        else if(name == R.raw.bakuhatsubig) mSoundPool.play(mSoundId5, 1.0F, 1.0F, 1, 0, 1.0F);
    }

    public void playFromMediaPlayer() {
        mediaPlayer = MediaPlayer.create(context, R.raw.polp);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void soundRelease(){
        if(mSoundPool != null) mSoundPool.release();
        if(mediaPlayer != null) mediaPlayer.release();
    }

    public static Sound getInstance() {
        return sound;
    }

}
