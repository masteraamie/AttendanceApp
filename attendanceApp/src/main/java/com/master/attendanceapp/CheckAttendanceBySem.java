package com.master.attendanceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CheckAttendanceBySem extends Activity
{
	String[] name, reg, ids, attend;
	int id;
	TextView tvID, tvName, tvReg, tvPresent;
	TextView banner1, banner2;
	TableLayout table;
	Typeface economica , calibri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_attendance_sem);

		Bundle b = getIntent().getExtras();

		id = b.getInt("sem");

		economica = Typeface.createFromAsset(getAssets(),
				"fonts/economica.otf");
		calibri = Typeface.createFromAsset(getAssets(),
				"fonts/calibri.ttf");

		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);


		table = (TableLayout) findViewById(R.id.table);

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
			progressDialog = new ProgressDialog(CheckAttendanceBySem.this);
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
				DatabaseIO dbIO = new DatabaseIO(CheckAttendanceBySem.this);
				dbIO.openDB();

				name = dbIO.getAttendanceBySem(DatabaseIO.NAME, id);
				reg = dbIO.getAttendanceBySem(DatabaseIO.REG_NO, id);
				ids = dbIO.getAttendanceBySem(DatabaseIO.ROW_ID, id);
				// guardian = dbIO.getStudentsWhere(DatabaseIO.NAME, id);
				attend = dbIO.getAttendanceBySem(DatabaseIO.PRESENT, id);

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

				android.widget.TableLayout.LayoutParams params = new android.widget.TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(10, 10, 10, 10);



				for (int i = 0; i < ids.length; i++) {
					TableRow tr = new TableRow(CheckAttendanceBySem.this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

					tvID = new TextView(CheckAttendanceBySem.this);
					tvID.setText(ids[i]);
					tvID.setTextColor(Color.WHITE);
					tvID.setTextSize(18);
					tvID.setPadding(5, 5, 5, 5);
					tvID.setGravity(Gravity.CENTER);
					tvID.setTypeface(calibri);
					
					tr.addView(tvID);

					tvName = new TextView(CheckAttendanceBySem.this);
					tvName.setText(name[i]);
					tvName.setTextColor(Color.WHITE);
					tvName.setTextSize(18);
					tvName.setPadding(5, 5, 5, 5);
					tvName.setGravity(Gravity.CENTER);
					tvName.setTypeface(calibri);
					tr.addView(tvName);

					tvReg = new TextView(CheckAttendanceBySem.this);
					tvReg.setText(reg[i]);
					tvReg.setTextColor(Color.WHITE);
					tvReg.setPadding(5, 5, 5, 5);
					tvReg.setGravity(Gravity.CENTER);
					tvReg.setTextSize(18);
					tvReg.setTypeface(calibri);
					tr.addView(tvReg);

					tvPresent = new TextView(CheckAttendanceBySem.this);
					tvPresent.setText(attend[i]);
					tvPresent.setTypeface(calibri);
					tvPresent.setGravity(Gravity.CENTER);
					tvPresent.setTextColor(Color.WHITE);
					tvPresent.setPadding(5, 5, 5, 5);
					tvPresent.setTextSize(18);
					tr.addView(tvPresent);


					table.addView(tr, params);
					table.setStretchAllColumns(true);
				}

				TableRow tr = new TableRow(CheckAttendanceBySem.this);
				tvID = new TextView(CheckAttendanceBySem.this);
				tvID.setText("");
				tr.addView(tvID);

				tvName = new TextView(CheckAttendanceBySem.this);
				tvName.setText("");
				tr.addView(tvName);

				tvPresent = new TextView(CheckAttendanceBySem.this);
				tvPresent.setText("");
				tr.addView(tvPresent);

				tvReg = new TextView(CheckAttendanceBySem.this);
				tvReg.setText("");
				tr.addView(tvReg);

				table.addView(tr, params);

				table.setStretchAllColumns(true);

			} catch (Exception ex) {

				Toast.makeText(CheckAttendanceBySem.this, ex.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
