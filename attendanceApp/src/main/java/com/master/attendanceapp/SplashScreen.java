package com.master.attendanceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

	int i = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		final ProgressBar load = (ProgressBar) findViewById(R.id.loading);
		load.setMax(100);
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					for (int i = 0; i <= 100; i++) {
						load.setProgress(i);
						sleep(50);
						if (i == 100) {

							DatabaseIO dbIO = new DatabaseIO(SplashScreen.this);
							dbIO.openDB();

							boolean check = dbIO.checkUser();

							dbIO.closeDB();

							if (check) {
								Intent login = new Intent(SplashScreen.this,
										Login.class);
								startActivity(login);
							}
							else {
								Intent signup = new Intent(SplashScreen.this,
										CreateUser.class);
								startActivity(signup);
							}												
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		t.start();
	}




	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}
