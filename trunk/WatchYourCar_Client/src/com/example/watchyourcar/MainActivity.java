package com.example.watchyourcar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	Socket soc;
	PrintWriter outs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String address = intent.getStringExtra(AddressActivity.EXTRA_MESSAGE);
		
		// Alows to use sockets without StrictMode errors
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		connection(address);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  setContentView(R.layout.activity_main);
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.outs.close();
		try {
			this.soc.close();
		} catch (IOException e) {
			Log.d("Main activity","The connection to server could not be closed");
		}
	}
}
