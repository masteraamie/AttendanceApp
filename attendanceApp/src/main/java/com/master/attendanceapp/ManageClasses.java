package com.master.attendanceapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageClasses extends Activity implements OnClickListener {

	Button btnSet;
	EditText etSem, etClasses;
	TextView banner1, banner2;
	int[] semesters = { 1, 2, 3, 4, 5, 6, 7, 8 };
	long lastID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_classes);

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

		etSem = (EditText) findViewById(R.id.semester);
		etClasses = (EditText) findViewById(R.id.classes);

		etSem.setHintTextColor(Color.LTGRAY);
		etClasses.setHintTextColor(Color.LTGRAY);

		btnSet = (Button) findViewById(R.id.set);
		btnSet.setTypeface(segoe);
		btnSet.setOnClickListener(this);
		
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		try {
			int sem = Integer.parseInt(etSem.getText().toString());
			int classes = Integer.parseInt(etClasses.getText().toString());

			boolean semErr = true;

			for (int i : semesters) {
				if (i == sem) {
					semErr = false;
				}
			}

			if (semErr) {
				Toast.makeText(this, "Invalid semester", Toast.LENGTH_LONG)
						.show();
			} else {

				DatabaseIO dbIO = new DatabaseIO(this);
				dbIO.openDB();

				lastID = dbIO.setClasses(sem, classes);

				dbIO.closeDB();

				Toast.makeText(this, "Classes set successfully",
						Toast.LENGTH_LONG).show();

			}
		} catch (Exception ex) {

			Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
