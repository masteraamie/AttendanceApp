package com.master.attendanceapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateSuccess extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_success);
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	}
	
	public void login(View v)
	{
		Intent success = new Intent(this , Login.class);								
		startActivity(success);
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
