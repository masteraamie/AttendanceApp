package com.master.attendanceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckShortageByID extends Activity implements OnClickListener {

	String name, reg, mobile;
	int id, attended, sem, classes, roll;
	float percent;
	TextView tvAttended, tvTotal, tvPercent, tvInfo, tvShort;
	Button btnReport;
	Typeface economica, calibri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_shortage);

		Bundle b = getIntent().getExtras();

		id = b.getInt("id");
		economica = Typeface
				.createFromAsset(getAssets(), "fonts/economica.otf");
		calibri = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

		tvAttended = (TextView) findViewById(R.id.attended);
		tvTotal = (TextView) findViewById(R.id.total);
		tvPercent = (TextView) findViewById(R.id.percent);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		tvShort = (TextView) findViewById(R.id.tvShortage);

		tvInfo.setTypeface(calibri);
		tvShort.setTypeface(economica);

		btnReport = (Button) findViewById(R.id.report);
		btnReport.setOnClickListener(this);
		btnReport.setEnabled(false);
		btnReport.setTypeface(calibri);

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
			progressDialog = new ProgressDialog(CheckShortageByID.this);
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
				DatabaseIO dbIO = new DatabaseIO(CheckShortageByID.this);
				dbIO.openDB();

				name = dbIO.getStudentsWhere(DatabaseIO.NAME, id);
				reg = dbIO.getStudentsWhere(DatabaseIO.REG_NO, id);
				mobile = dbIO.getStudentsWhere(DatabaseIO.GUARDIAN, id);
				sem = Integer.parseInt(dbIO.getStudentsWhere(
						DatabaseIO.SEMESTER, id));
				roll = Integer.parseInt(dbIO.getStudentsWhere(
						DatabaseIO.ROLL_NO, id));
				attended = Integer.parseInt(dbIO.getStudentsWhere(
						DatabaseIO.PRESENT, id));

				classes = dbIO.getClasses(sem);

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

			if (classes > 0) {

				percent = ((attended * 100) / (classes));

				tvInfo.setText("NAME : " + name + "			SEMESTER : " + sem
						+ "\n\nROLL NO : " + roll + "			GUARDIAN CONTACT : "
						+ mobile);

				if (percent > 75) {

					tvShort.setText("THIS STUDENT DOES NOT HAVE SHORTAGE");
					tvShort.setBackgroundColor(Color.rgb(0, 166, 81));
					btnReport.setVisibility(-1);
					tvAttended.setText("" + attended);
					tvTotal.setText("" + classes);
					tvPercent.setText(" " + percent);

				} else {
					tvAttended.setText("" + attended);
					tvShort.setText("THIS STUDENT HAS SHORTAGE");
					tvTotal.setText("" + classes);
					btnReport.setEnabled(true);
					tvPercent.setText(" " + percent);
				}

				tvInfo.setTypeface(calibri);
				tvAttended.setTypeface(calibri);
				tvShort.setTypeface(calibri);
				tvTotal.setTypeface(calibri);
				tvPercent.setTypeface(calibri);
				

			} else {
				Toast.makeText(CheckShortageByID.this,
						"Classes for this semester not set", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		
		String message = "The Message Will Go Here";
		
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mobile));     
		intent.putExtra("sms_body", message); 
		startActivity(intent);
	}

}
