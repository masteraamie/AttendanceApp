package com.master.attendanceapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	Button login;
	EditText un, pass;
	TextView banner1 , banner2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = (Button) findViewById(R.id.login);
		un = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);
		
		Typeface economica = Typeface.createFromAsset(getAssets(), "fonts/economica.otf");
		Typeface calibri = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
		
		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);
		
		Typeface segoe = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");	
		
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);


		
		un.setHintTextColor(Color.LTGRAY);
		pass.setHintTextColor(Color.LTGRAY);
		un.setTypeface(segoe);
		pass.setTypeface(segoe);
		login.setTypeface(segoe);
		login.setOnClickListener(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String p = pass.getText().toString();
		String us = un.getText().toString();
		
		try {

			DatabaseIO dbIO = new DatabaseIO(this);
			dbIO.openDB();

			boolean i = dbIO.checkLogin(us , p);

			dbIO.closeDB();
			
			if(i)
			{
				Toast.makeText(this, "User Login Successful", Toast.LENGTH_LONG).show();
				Intent signIntent = new Intent(this , MainActivity.class);
				startActivity(signIntent);
			}
			else
				Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
