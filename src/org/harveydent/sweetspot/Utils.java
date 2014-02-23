package org.harveydent.sweetspot;

public class Utils {
    public static double getMagnitudeDifference(double measured_x, 
                                                double measured_y, 
                                                double measured_z, 
                                                double target_x, 
                                                double target_y, 
                                                double target_z) {
        return Math.pow(Math.abs(measured_x - target_x), 2) + 
               Math.pow(Math.abs(measured_y - target_y), 2) + 
               Math.pow(Math.abs(measured_z - target_z), 2);
    }
}
