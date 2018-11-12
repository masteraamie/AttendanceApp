package com.master.attendanceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CheckAttendanceByID extends Activity {

	String name, reg, mobile;
	int id, attend;
	TextView tvName, tvReg, tvMob, tvAttend;
	Typeface economica , calibri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_attendance);

		Bundle b = getIntent().getExtras();

		id = b.getInt("id");
		economica = Typeface.createFromAsset(getAssets(),
				"fonts/economica.otf");
		calibri = Typeface.createFromAsset(getAssets(),
				"fonts/calibri.ttf");


		tvName = (TextView) findViewById(R.id.name);
		tvReg = (TextView) findViewById(R.id.reg);
		tvMob = (TextView) findViewById(R.id.mobile);
		tvAttend = (TextView) findViewById(R.id.presents);

		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

		
		new CheckAttendance().execute();
	}

	private class CheckAttendance extends AsyncTask<String, Integer, String> {

		ProgressDialog progressDialog;

		// String error;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(CheckAttendanceByID.this);
			progressDialog.setMax(100);
			progressDialog.setMessage("Loading . . .");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			for (int i = 0; i < 10; i++) {

				publishProgress(10);

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				DatabaseIO dbIO = new DatabaseIO(CheckAttendanceByID.this);
				dbIO.openDB();

				name = dbIO.getStudentsWhere(DatabaseIO.NAME, id);
				reg = dbIO.getStudentsWhere(DatabaseIO.REG_NO, id);
				mobile = dbIO.getStudentsWhere(DatabaseIO.MOBILE, id);
				// guardian = dbIO.getStudentsWhere(DatabaseIO.NAME, id);
				attend = Integer.parseInt(dbIO.getStudentsWhere(
						DatabaseIO.PRESENT, id));

				dbIO.closeDB();

			} catch (Exception ex) {
				// error = ex.toString();
			}
			progressDialog.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				tvName.setText(name);
				tvReg.setText(reg);
				tvMob.setText(mobile);
				tvAttend.setText("" + attend);
				
				tvName.setTypeface(calibri);
				tvReg.setTypeface(calibri);
				tvMob.setTypeface(calibri);
				tvAttend.setTypeface(calibri);
				
			} catch (Exception ex) {

				Toast.makeText(CheckAttendanceByID.this, ex.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	
}
