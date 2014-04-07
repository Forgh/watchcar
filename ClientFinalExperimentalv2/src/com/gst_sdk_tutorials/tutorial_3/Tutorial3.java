package com.gst_sdk_tutorials.tutorial_3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gstreamer.GStreamer;

public class Tutorial3 extends Activity implements SurfaceHolder.Callback, SensorEventListener {
    private native void nativeInit();     // Initialize native code, build pipeline, etc
    private native void nativeFinalize(); // Destroy pipeline and shutdown native code
    private native void nativePlay();     // Set pipeline to PLAYING
    private static native boolean nativeClassInit(); // Initialize native class: cache Method IDs for callbacks
    private native void nativeSurfaceInit(Object surface);
    private native void nativeSurfaceFinalize();
    private native void nativeSetIpserver(String ip);
    private long native_custom_data;      // Native code will use this to keep private data
    
    private Socket soc;
	private PrintWriter outs;
	
	private SensorManager mManager;
    private Sensor mAccelerometer;
    
    private float posDepX = 0f;
    private float posDepY = 0f;
    
    private String lastCommand = "";
    
    private static boolean hand = true;
    
    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //Full screen app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Initialize the connection for the commands
        Intent intent = getIntent();
		String address = intent.getStringExtra(AddressActivity.EXTRA_MESSAGE);
		
		mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mAccelerometer = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    mManager.registerListener(this, mAccelerometer, 500);
		nativeSetIpserver(address);
		// Allows to use sockets without StrictMode errors
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		connection(address);
        

        // Initialize GStreamer and warn if it fails
        try {
            GStreamer.init(this);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish(); 
            return;
        }

        //setContentView(R.layout.main_right);
        changeLayout(this.getCurrentFocus());
        

        
    }
    
    //When the screen is rotated nothing must change
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  //setContentView(R.layout.main);
	}

    protected void onDestroy() {
        nativeFinalize();
        this.outs.close();
		try {
			this.soc.close();
		} catch (IOException e) {
			Log.d("Main activity","The connection to server could not be closed");
		}
        super.onDestroy();
    }

    // Called from native code. This sets the content of the TextView from the UI thread.
    private void setMessage(final String message) {
    }

    // Called from native code. Native code calls this once it has created its pipeline and
    // the main loop is running, so it is ready to accept commands.
    private void onGStreamerInitialized () {
        nativePlay();
    }

    static {
        System.loadLibrary("gstreamer_android");
        System.loadLibrary("tutorial-3");
        nativeClassInit();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        Log.d("GStreamer", "Surface changed to format " + format + " width "
                + width + " height " + height);
        nativeSurfaceInit (holder.getSurface());
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("GStreamer", "Surface created: " + holder.getSurface());
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("GStreamer", "Surface destroyed");
        nativeSurfaceFinalize ();
    }
    
    public void connection(String address) {
		try {
			this.soc = new Socket(address,6020);
			this.outs = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true) ;
		} catch (UnknownHostException e) {
			Log.d("Main activity","Server not found.");
		} catch (IOException e) {
			Log.d("Main activity","Error input/output with the server connection.");
		}
	}
    
    public void changeLayout(View v) {
    	if (Tutorial3.hand) {
    		hand = false;
    		setContentView(R.layout.main_right);
    	}
    	else {
    		hand = true;
    		setContentView(R.layout.main_left);
    	}
    	
    	nativePlay();

        SurfaceView sv = (SurfaceView) this.findViewById(R.id.surface_video);
        SurfaceHolder sh = sv.getHolder();
        sh.addCallback(this);
        
        nativeInit();
    	
    }
    
    public void turnLeft(View v) {
		outs.println("left");
		Log.d("Main activity","We are turning to left");
	}
	
	public void turnRight(View v) {
		outs.println("right");
		Log.d("Main activity","We are turning to right");
	}
	
	public void stopMoving(View v) {
		outs.println("stop");
		Log.d("Main activity","We stop moving");
	}
	
	public void driveForward(View v) {
		outs.println("forward");
		Log.d("Main activity","We are driving forward");
	}
	
	public void driftRight(View v) {
		outs.println("drift right");
		Log.d("Main activity","We are drifting right");
	}
	
	public void driftLeft(View v) {
		outs.println("drift left");
		Log.d("Main activity","We are drifting left");
	}
	
	public void driftBackLeft(View v) {
		outs.println("drift back left");
		Log.d("Main activity","We are drifting back-left");
	}

	public void driftBackRight(View v) {
		outs.println("drift back right");
		Log.d("Main activity","We are drifting back-right");
	}
	
	
	public void driveBackward(View v) {
		outs.println("backward");
		Log.d("Main activity","We are driving backward");
	}
	
	public void rotateLeft(View v) {
		outs.println("rotate left");
		Log.d("Main activity","We are rotating to left");
	}
	
	public void rotateRight(View v) {
		outs.println("rotate right");
		Log.d("Main activity","We are rotating to right");
	}
    
    final SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];
            
            if(x < posDepX-1){
            	if(y < posDepY-1){
            		if (!lastCommand.equals("left")) {
            			outs.println("left");
            			Log.d("Main activity","We are drifting left by rotation");
            			lastCommand = "left";
            		}
            	}
            	else if(y > posDepY+1){
            		if (!lastCommand.equals("right")) {
	            		outs.println("right");
	            		Log.d("Main activity","We are drifting right by rotation");
	            		lastCommand = "right";
            		}
            	}
            	else {
            		if (!lastCommand.equals("forward")) {
	            		outs.println("forward");
	            		Log.d("Main activity","We are driving forward by rotation");
	            		lastCommand = "forward";
            		}
            	}
            }
            
            else if (x > posDepX+1){
            	if(y < posDepY-1){
            		if (!lastCommand.equals("drift back left")) {
	            		outs.println("drift back left");
	            		Log.d("Main activity","We are drifting back left by rotation");
	            		lastCommand = "drift back left";
            		}
            	}
            	else if(y > posDepY+1){
            		if (!lastCommand.equals("drift back right")) {
	            		outs.println("drift back right");
	            		Log.d("Main activity","We are drifting back right by rotation");
	            		lastCommand = "drift back right";
            		}
            	}
            	else {
            		if (!lastCommand.equals("backward")) {
	            		outs.println("backward");
	            		Log.d("Main activity","We are driving backward by rotation");
	            		lastCommand = "backward";
            		}
            	}
            }
            
            else if(y < posDepY-1){
            	if (!lastCommand.equals("rotate left")) {
	            	outs.println("rotate left");
	            	Log.d("Main activity","We are turning left by rotation");
	            	lastCommand = "rotate left";
            	}
            }
            
            else if(y > posDepY+1){
            	if (!lastCommand.equals("rotate right")) {
	            	outs.println("rotate right");
	            	Log.d("Main activity","We are turning right by rotation");
	            	lastCommand = "rotate right";
            	}
            }
            
            else if (x > posDepX-1 && x < posDepX+1 && y > posDepY-1 && y < posDepY+1) {
            	if (!lastCommand.equals("stop")) {
	            	outs.println("stop");
	            	Log.d("Main activity","We are stopping by rotation");
	            	lastCommand = "stop";
            	}
            }
        }
        
        @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {

        }
	};
	
	@Override
	protected void onResume() {
	  super.onResume();
	  mManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
	  super.onPause();
	  mManager.unregisterListener(mSensorEventListener, mAccelerometer);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

}
