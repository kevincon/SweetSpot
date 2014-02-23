package org.harveydent.sweetspot;

import java.io.IOException;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;

public class Fanfare {
	
	private SoundPool soundpool;
	private int fanfareSoundId;
	
	public Fanfare(Context myContext, SoundPool mSoundPool) {
		AssetManager mngr = myContext.getAssets();
		this.soundpool = mSoundPool;
		try {
			AssetFileDescriptor fanafd = mngr.openFd("Fanfare-sound.mp3");
			fanfareSoundId = soundpool.load(fanafd, 1);
		} catch (IOException e) {
			Log.e("Fanfare", "could not find asset\n");
		}
	}
	
	/**
	 * 
	 * @param volume 0 - 1.0
	 */
	public void play(float volume)
	{
		// beep is higher priority than music
		soundpool.play(fanfareSoundId, volume, volume, 0, 0, 1.0f);
	}
}