package org.harveydent.sweetspot;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private final static double THRESHOLD = 1.00;
    private static double target_x = 9.8;
    private static double target_y = 0;
    private static double target_z = 0;
    
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean mInitialized;
    private Beep beep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        beep = new Beep(getApplicationContext());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { return; }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tvX= (TextView)findViewById(R.id.x_axis);
        TextView tvY= (TextView)findViewById(R.id.y_axis);
        TextView tvZ= (TextView)findViewById(R.id.z_axis);
        TextView ssAchieved = (TextView)findViewById(R.id.SweetSpotAchievedTextView);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            tvX.setText("0.0");
            tvY.setText("0.0");
            tvZ.setText("0.0");
            mInitialized = true;
        } else {
            tvX.setText(Float.toString(x));
            tvY.setText(Float.toString(y));
            tvZ.setText(Float.toString(z));
            beep.playBeep(1.0f);
            if (Utils.getMagnitudeDifference(x, y, z, target_x, target_y, target_z) < THRESHOLD) {
                ssAchieved.setText("Sweet spot achieved!");
            } else {
                ssAchieved.setText("");
            }
        }
    }
}
