package com.master.attendanceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AttendanceMenu extends Activity implements OnClickListener {

	Button btnCheck, btnShortage, btnAttend , btnClasses;

	TextView banner1, banner2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_menu);


		haveStoragePermission();

		btnCheck = (Button) findViewById(R.id.check);
		btnShortage = (Button) findViewById(R.id.shortage);
		btnAttend = (Button) findViewById(R.id.doAttend);
		btnClasses = (Button) findViewById(R.id.classes);
		
		Typeface economica = Typeface.createFromAsset(getAssets(),
				"fonts/economica.otf");
		Typeface calibri = Typeface.createFromAsset(getAssets(),
				"fonts/calibri.ttf");
		Typeface segoe = Typeface.createFromAsset(getAssets(),
				"fonts/segoeui.ttf");

		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);

		btnAttend.setTypeface(segoe);
		btnShortage.setTypeface(segoe);
		btnCheck.setTypeface(segoe);
		btnClasses.setTypeface(segoe);
		
		btnAttend.setOnClickListener(this);
		btnShortage.setOnClickListener(this);
		btnCheck.setOnClickListener(this);
		btnClasses.setOnClickListener(this);


		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.doAttend:
			Intent attend = new Intent(this, Attendance.class);
			startActivity(attend);
			break;

		case R.id.check:
			Intent semi = new Intent(this, GetAttendance.class);

			startActivity(semi);
			break;
		case R.id.shortage:
			Intent idGo = new Intent(this, GetShortage.class);

			startActivity(idGo);
			break;
		case R.id.classes:
			Intent classes = new Intent(this, ManageClasses.class);

			startActivity(classes);
			break;
		}
	}

	public  boolean haveStoragePermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
					== PackageManager.PERMISSION_GRANTED) {
				Log.e("Permission error","You have permission");
				return true;
			} else {

				Log.e("Permission error","You have asked for permission");
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
				return false;
			}
		}
		else { //you dont need to worry about these stuff below api level 23
			Log.e("Permission error","You already have the permission");
			return true;
		}
	}
}
