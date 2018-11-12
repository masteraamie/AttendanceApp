package com.master.attendanceapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	
	Button signup , attend , enlist , about;
	TextView banner1 , banner2;
	public static final String DEFAULT_TITLE = "Confirm";
	public static final String DEFAULT_MESSAGE = "Do You Want To Exit ?";
	public static final String DEFAULT_YES = "Yes";
	public static final String DEFAULT_NO = "No";	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		signup = (Button) findViewById(R.id.signup);
		attend = (Button) findViewById(R.id.attend);
		enlist = (Button) findViewById(R.id.exit);
		about = (Button) findViewById(R.id.about);

		Typeface economica = Typeface.createFromAsset(getAssets(), "fonts/economica.otf");
		Typeface calibri = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
		
		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);
		
		signup.setTypeface(economica);
		attend.setTypeface(economica);
		enlist.setTypeface(economica);
		
		signup.setOnClickListener(this);
		attend.setOnClickListener(this);
		enlist.setOnClickListener(this);
		about.setOnClickListener(this);
		
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.signup:
			Intent signIntent = new Intent(this , Signup.class);
			startActivity(signIntent);
			break;
		case R.id.attend:
			Intent intent = new Intent(this , AttendanceMenu.class);
			startActivity(intent);
			break;
		case R.id.about:
			//create your own activity
			Intent iAbout = new Intent(this , AboutUs.class);
			startActivity(iAbout);
			break;
		case R.id.exit:
			showExitDialog();
			break;		
		}
	}
	
	private AlertDialog showExitDialog() {
		final AlertDialog.Builder downloadDialog = new AlertDialog.Builder(MainActivity.this);
		downloadDialog.setTitle(DEFAULT_TITLE);
		downloadDialog.setMessage(DEFAULT_MESSAGE);
		downloadDialog.setPositiveButton(DEFAULT_YES,
				new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						finishAffinity();						
					}
				});
		downloadDialog.setNegativeButton(DEFAULT_NO,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {						
					}
				});
		return downloadDialog.show();
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
