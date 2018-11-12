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

public class CreateUser extends Activity implements OnClickListener {

	Button create;
	EditText un, pass, repass;
	TextView banner1, banner2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		un = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);
		repass = (EditText) findViewById(R.id.repass);

		create = (Button) findViewById(R.id.create);

		Typeface economica = Typeface.createFromAsset(getAssets(),
				"fonts/economica.otf");
		Typeface calibri = Typeface.createFromAsset(getAssets(),
				"fonts/calibri.ttf");

		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);

		Typeface segoe = Typeface.createFromAsset(getAssets(),
				"fonts/segoeui.ttf");

		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

		un.setHintTextColor(Color.LTGRAY);
		pass.setHintTextColor(Color.LTGRAY);
		repass.setHintTextColor(Color.LTGRAY);
		un.setTypeface(segoe);
		pass.setTypeface(segoe);
		create.setTypeface(segoe);
		repass.setTypeface(segoe);

		create.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String p = pass.getText().toString();
		String rp = repass.getText().toString();
		String us = un.getText().toString();

		if (us.equals("") || p.equals("") || rp.equals("")) {
			Toast.makeText(this, "Fields cannot be left empty",
					Toast.LENGTH_LONG).show();
		} else {

			if (p.equals(rp)) {

				try {

					if (p.length() < 6) {
						
						Toast.makeText(this, "Password should be greater than 6 characters",
								Toast.LENGTH_LONG).show();
						
					} else {

						DatabaseIO dbIO = new DatabaseIO(this);
						dbIO.openDB();

						long i = dbIO.createUser(us, p);

						dbIO.closeDB();

						if (i > 0) {
							Toast.makeText(this, "User Created Successfully",
									Toast.LENGTH_LONG).show();

							Intent success = new Intent(this,
									CreateSuccess.class);
							startActivity(success);
						} else
							Toast.makeText(this, "Error Occured",
									Toast.LENGTH_LONG).show();
					}
				} catch (Exception ex) {
					Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG)
							.show();
				}

			} else {
				Toast.makeText(this, "Passwords do not match",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
