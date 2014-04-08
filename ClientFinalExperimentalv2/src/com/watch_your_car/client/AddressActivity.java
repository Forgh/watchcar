package com.watch_your_car.client;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddressActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.watchyourcar.IP_ADRESS";
	public static final String KEY_HOSTNAME = "hostname";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		
		// load stored data
		SharedPreferences sp = this.getPreferences( MODE_PRIVATE);
		String hostname = sp.getString( KEY_HOSTNAME, this.getString( R.string.defaultHostName));
		// set stored parameters to the contents
		EditText et = (EditText)findViewById( R.id.edit_message);
		et.setText(hostname);
	}
	
	public void launch(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

}
