package org.harveydent.sweetspot;

import java.util.Timer;
import java.util.TimerTask;

public class Timeout {

	int value;
	
	public Timeout()
	{
		value = 60;
		Timer newTimer = new Timer();
		newTimer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	value--;
	        }
		}, 1000, 1000);	
	}
	
	public int getValue()
	{
		return value;
	}
}
