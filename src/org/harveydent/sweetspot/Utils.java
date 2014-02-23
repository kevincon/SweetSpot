package org.harveydent.sweetspot;

import java.util.Random;

public class Utils {
	
	private final static double MAX_MAGNITUDE_DIFF = 13.0;
	private final static double MIN_MAGNITUDE_DIFF = 1.0;
	
    public static double getMagnitudeDifference(double measured_x, 
                                                double measured_y, 
                                                double measured_z, 
                                                double target_x, 
                                                double target_y, 
                                                double target_z) {
        return Math.sqrt( Math.pow((measured_x - target_x), 2) + 
               Math.pow((measured_y - target_y), 2) + 
               Math.pow((measured_z - target_z), 2) );
    }
    
    public static double magnitudeDifferenceToSpeed( double magnitudeDiff, double slowest, double fastest) {
    	return magnitudeDiff*(slowest-fastest)/(MAX_MAGNITUDE_DIFF - MIN_MAGNITUDE_DIFF);
    }
    
    public static float[] generateRandomSweetSpot()
    {
    	final float ACCEPTABLE_RANGE = 2.0f;
    	final float GRAVITY = 9.8f;
    	Random r = new Random();
    	float[] val = new float[3];
    	do {
    	val[0] = r.nextFloat()*GRAVITY * (r.nextBoolean() ? 1.0f : -1.0f);
    	val[1] = r.nextFloat()*GRAVITY * (r.nextBoolean() ? 1.0f : -1.0f);
    	val[2] = r.nextFloat()*GRAVITY * (r.nextBoolean() ? 1.0f : -1.0f);
    	} while (Math.abs(Math.sqrt( Math.pow(val[0], 2)+Math.pow(val[1], 2)+Math.pow(val[2],2) ) - GRAVITY) > ACCEPTABLE_RANGE);
    	// BEST CODE EVAR
		return val;
    }
}
