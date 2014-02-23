package org.harveydent.sweetspot;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private final static double THRESHOLD = 4.00;
    private static float target_x = 7;
    private static float target_y = 2;
    private static float target_z = -2;
    
    private static float current_x = 0;
    private static float current_y = 0;
    private static float current_z = 0;
    
    private SoundPool mSoundPool;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private VC vc;
    private Fanfare fanfare;
    
    TextView tvX;
    TextView tvY;
    TextView tvZ;
    TextView ssAchieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvX= (TextView)findViewById(R.id.x_axis);
        tvY= (TextView)findViewById(R.id.y_axis);
        tvZ= (TextView)findViewById(R.id.z_axis);
        ssAchieved = (TextView)findViewById(R.id.SweetSpotAchievedTextView);
        
        tvX.setText("0.0");
        tvY.setText("0.0");
        tvZ.setText("0.0");
        
		mSoundPool  = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        fanfare = new Fanfare(getApplicationContext(), mSoundPool);
        
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vc = new VC(v, new Beep(getApplicationContext(), mSoundPool));
        vc.vibrate();
        
		Timer newTimer = new Timer();
		newTimer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	gameLoop();
	        }
		}, 0, 901);	
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { return; }

    @Override
    public void onSensorChanged(SensorEvent event) {
        current_x = event.values[0];
        current_y = event.values[1];
        current_z = event.values[2];
    }
    
    void gameLoop()
    {
        final boolean reachedSweetSpot;
        long slowest = vc.getSpeedRange()[0];
        long fastest = vc.getSpeedRange()[1];
        double magnitude_diff = Utils.getMagnitudeDifference(current_x, current_y, current_z, target_x, target_y, target_z);
        double speed = (long)Utils.magnitudeDifferenceToSpeed(magnitude_diff, slowest, fastest);
        Log.i("Main", Double.toString(speed));
        vc.setSpeed((long)speed);

    	if ( magnitude_diff < THRESHOLD) {
    		reachedSweetSpot = true;
    	} else {
    		reachedSweetSpot = false;
    	}

    	runOnUiThread(new Runnable() {
    		@Override
    		public void run() {
    			tvX.setText(Float.toString(current_x));
    			tvY.setText(Float.toString(current_y));
    			tvZ.setText(Float.toString(current_z));           //stuff that updates ui

    			if ( reachedSweetSpot )
    			{
    				ssAchieved.setText("Sweet spot achieved!");
    				vc.win();
    				fanfare.play(1.0f);
    			}
    			else
    			{
    				ssAchieved.setText("");
    			}
    		}
    	});
    }
}
