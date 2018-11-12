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

public class GetShortage extends Activity implements OnClickListener {

	EditText etSem, etID;
	TextView banner1, banner2;
	Button btnSem, btnID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_attendance);

		btnSem = (Button) findViewById(R.id.semGo);
		btnID = (Button) findViewById(R.id.idGo);

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

		etID = (EditText) findViewById(R.id.studentID);
		etSem = (EditText) findViewById(R.id.semester);

		btnID.setTypeface(segoe);
		btnSem.setTypeface(segoe);

		etID.setHintTextColor(Color.LTGRAY);
		etSem.setHintTextColor(Color.LTGRAY);

		etID.setTypeface(segoe);
		etSem.setTypeface(segoe);

		btnID.setOnClickListener(this);
		btnSem.setOnClickListener(this);
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.semGo:

			try {
				int sem = Integer.parseInt(etSem.getText().toString());
				Bundle b1 = new Bundle();
				b1.putInt("sem", sem);

				Intent semi = new Intent(this, CheckShortageBySem.class);
				semi.putExtras(b1);
				startActivity(semi);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "Invalid Input "+e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.idGo:

			try {
				int id = Integer.parseInt(etID.getText().toString());
				Bundle b = new Bundle();
				b.putInt("id", id);
				Intent idGo = new Intent(this, CheckShortageByID.class);
				idGo.putExtras(b);
				startActivity(idGo);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "Invalid Input"+e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

}
