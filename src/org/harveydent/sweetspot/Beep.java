package org.harveydent.sweetspot;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.media.AudioManager;
import android.util.Log;

public class Beep {

	private SoundPool soundpool;
	private int beepSoundId;
	private int musicSoundId;
	
	public Beep(Context myContext) {
		AssetManager mngr = myContext.getAssets();
		soundpool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		try {
			AssetFileDescriptor beepafd = mngr.openFd("SweetSpot-Sound.mp3");
			//AssetFileDescriptor musicafd = mngr.openFd("music.mp3");
			beepSoundId = soundpool.load(beepafd, 1);
			//musicSoundId = soundpool.load(musicafd, 1);
		} catch (IOException e) {
			Log.e("Beep", "could not find asset\n");
		}
	}
	
	/**
	 * 
	 * @param volume 0 - 1.0
	 */
	public void playBeep(float volume)
	{
		// beep is higher priority than music
		soundpool.play(beepSoundId, volume, volume, 0, 0, 1.0f);
	}
	
	/**
	 * 
	 * @param volume 0 - 1.0
	 */
	/*
	public void playMusic(float volume)
	{
		// music is lower priority than beep
		soundpool.play(musicSoundId, volume, volume, 1, 0, 1.0f);
	}
	*/
}
