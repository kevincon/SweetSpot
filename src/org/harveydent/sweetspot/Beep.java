package org.harveydent.sweetspot;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;

public class Beep {
	
	private SoundPool soundpool;
	private int beepSoundId;
	private Timer currentTimer = null;
	
	public Beep(Context myContext, SoundPool mSoundPool) {
		AssetManager mngr = myContext.getAssets();
		this.soundpool = mSoundPool;
		try {
			AssetFileDescriptor beepafd = mngr.openFd("SweetSpot-Sound.mp3");
			beepSoundId = soundpool.load(beepafd, 1);
		} catch (IOException e) {
			Log.e("Beep", "could not find asset\n");
		}
	}
	
	/**
	 * 
	 * @param volume 0 - 1.0
	 */
	private void playBeep(float volume)
	{
		// beep is higher priority than music
		soundpool.play(beepSoundId, volume, volume, 0, 0, 1.0f);
	}

	/**
	 * 
	 * @param l - period in milliseconds
	 */
	public void setSpeed(long l) {
		Timer newTimer = new Timer();
		if ( this.currentTimer != null )
		{
			currentTimer.cancel();
		}
		newTimer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	playBeep(1.0f);
	        }
		}, 0, l);	
		
		currentTimer = newTimer;
	}
}
