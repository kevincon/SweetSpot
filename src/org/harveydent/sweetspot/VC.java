package org.harveydent.sweetspot;


import android.os.Vibrator;

public class VC {
	
	private Vibrator v;
	private long[] pattern; // Array of size 2, 0 = off speed, 1 = on speed
	private static final long FASTEST = 5;
	private static final long SLOWEST = 900;
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
    	else
    	{
    		setOnSpeed(ON_DURATION_SLOW);
    	}
    	this.stop();
    	this.vibrate();
    }
    
    /*
     * Vibrate away!
     */
    public void vibrate()
    {
    	beep.setSpeed(pattern[0]+pattern[1]);
    	v.vibrate(pattern, 0);
    }
    
    public void win()
    {
    	beep.kill();
    }
    
    public void stop()
    {
    	v.cancel();
    	beep.kill();
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