package org.harveydent.sweetspot;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Score {

	private static long currentScore = 0;
	private static Date startDate = getDateTime();
	private static Date endDate = null;
	public static final String FORMAT_DATETIME = "yyyy/MM/dd_HH:mm:ss";
	

    public static Date getDateTime() 
    { 
        Calendar c = Calendar.getInstance();  
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATETIME, Locale.US); 
        return c.getTime();
    }

	/*
	 * To be called ONLY when a sweet spot is hit
	 */
    public static void updateScore()
	{
		endDate = getDateTime();
		long elapsed = endDate.getTime() - startDate.getTime();
		long elapsedSec = elapsed / 1000;
		if (elapsedSec <= 10) {
			currentScore += 200;
		}
		else if (elapsedSec <= 30) {
			currentScore += 100;
		}
		else if (elapsedSec <= 60) {
			currentScore += 50;
		}
		else if (elapsedSec <= 120) {
			currentScore += 20;
		}
		else {
			currentScore += 10;
		}
	}
	
	public static long getCurrentScore()
	{
		return currentScore;
	}
	
	public static void resetScore()
	{
		currentScore = 0;
	}
}
