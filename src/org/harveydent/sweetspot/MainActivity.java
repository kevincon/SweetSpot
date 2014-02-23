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
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private final static float THRESHOLD = 4.0f;
    private static float target_x;
    private static float target_y;
    private static float target_z;
    
    private static float current_x = 0;
    private static float current_y = 0;
    private static float current_z = 0;
    
    private SoundPool mSoundPool;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private VC vc;
    private Fanfare fanfare;
    
    private Timeout timeout;
    
    TextView tvX;
    TextView tvY;
    TextView tvZ;
    TextView ssAchieved;
    TextView tvScore;
    TextView tvTimer;
    
    private void setNewTargets()
    {
        float targets[] = Utils.generateRandomSweetSpot();
        target_x = targets[0];
        target_y = targets[1];
        target_z = targets[2];
        Log.i("Main", "Targets:");
        Log.i("Main", Float.toString(target_x));
        Log.i("Main", Float.toString(target_y));
        Log.i("Main", Float.toString(target_z));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        tvX= (TextView)findViewById(R.id.x_axis);
        tvY= (TextView)findViewById(R.id.y_axis);
        tvZ= (TextView)findViewById(R.id.z_axis);
        tvScore= (TextView)findViewById(R.id.score);
        tvTimer = (TextView)findViewById(R.id.timer);
        ssAchieved = (TextView)findViewById(R.id.SweetSpotAchievedTextView);
        
        tvX.setText("0.0");
        tvY.setText("0.0");
        tvZ.setText("0.0");
        
        setNewTargets();
        
		mSoundPool  = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        fanfare = new Fanfare(getApplicationContext(), mSoundPool);
        
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vc = new VC(v, new Beep(getApplicationContext(), mSoundPool));
        vc.vibrate();
        
		Timer newTimer = new Timer();
		timeout = new Timeout();
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
   		if (timeout.getValue() < 0)
		{
			return;
		}
        final boolean reachedSweetSpot;
        long slowest = vc.getSpeedRange()[0];
        long fastest = vc.getSpeedRange()[1];
        float magnitude_diff = (float)Utils.getMagnitudeDifference(current_x, current_y, current_z, target_x, target_y, target_z);
        float speed = (long)Utils.magnitudeDifferenceToSpeed(magnitude_diff, slowest, fastest);
        
        Log.i("Main", Float.toString(speed));
        vc.setSpeed((long)speed);

    	if ( magnitude_diff < THRESHOLD) {
    		reachedSweetSpot = true;
			vc.win();
			fanfare.play(1.0f);
			Score.updateScore();
			setNewTargets();
			//timeout = new Timeout();
    	} else {
    		reachedSweetSpot = false;

    		if (timeout.getValue() <= 0)
    		{
    			vc.stop();
    		}
    	}
    	
    	runOnUiThread(new Runnable() {
    		@Override
    		public void run() {
    			tvX.setText(Float.toString(current_x));
    			tvY.setText(Float.toString(current_y));
    			tvZ.setText(Float.toString(current_z));           //stuff that updates ui
    			if ( timeout.getValue() <= 0 )
    			{
    				tvTimer.setText("GAME OVER");
    			}
    			else
    			{    		
    				tvTimer.setText(Integer.toString(timeout.getValue()));
    			}

    			if ( reachedSweetSpot )
    			{
    				ssAchieved.setText("Sweet spot achieved!");
    			}
    			else
    			{
    				ssAchieved.setText("");
    			}
    			tvScore.setText(Long.toString(Score.getCurrentScore()));
    		}
    	});
    }
}
