package org.harveydent.sweetspot;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VC extends Activity {
	
	private Vibrator v;
	private long[] pattern; // Array of size 2, 0 = off speed, 1 = on speed
	private static final long FASTEST = 5;
	private static final long SLOWEST = 1500;
	private static final long ON_DURATION_SLOW = 80;
	private static final long ON_DURATION_FAST = 130;
	private Beep beep;

    public VC (Vibrator lv, Beep beep)
    {
    	v = lv;
    	pattern  = new long[2];
    	setOnSpeed(ON_DURATION_SLOW);
    	setOffSpeed(SLOWEST);
    	this.beep = beep;
    }
    
    private void setOnSpeed(long os)
    {
    	pattern[1] = os;
    }
    
    private void setOffSpeed(long ofs)
    {
    	pattern[0] = ofs;
    }
    
    public void setSpeed(long speed)
    {
    	setOffSpeed(speed);
    	if (speed <= 500) {
    		setOnSpeed(ON_DURATION_FAST);
    	}
    	beep.setSpeed(pattern[0]+pattern[1]);
    }
    
    /*
     * Vibrate away!
     */
    public void vibrate()
    {
    	beep.setSpeed(pattern[0]+pattern[1]);
    	v.vibrate(pattern, 0);
    }
    
    public void stop()
    {
    	v.cancel();
    }
    
    /*
     * Returns the tuple of long  integers corresponding to
     * the [slowest,fastest] vibration speeds in milliseconds
     */
    public long[] getSpeedRange()
    {
    	long[] speedRange = new long[2];
    	speedRange[0] = SLOWEST;
    	speedRange[1] = FASTEST;
    	return speedRange;
    }
    
    /*
     * Returns the tuple of long  integers corresponding to
     * the [offtime,ontime] vibration speeds in milliseconds 
     */
    public long[] getCurrentSpeed()
    {
    	return pattern;    	
    }
    
}


/*
Thread t = new Thread() {
@Override
public void run() {
	Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	for (int i=20; i >= 1; i--) {
    	long[] pattern = new long[2];
    	pattern[0] = i;
    	pattern[1] = 50;
    	v.vibrate(pattern, 0);
		try {
			synchronized (this) {
				wait (1000);	
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		v.cancel();
		try {
			synchronized (this) {
				wait (500);	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
};

t.run();
*/