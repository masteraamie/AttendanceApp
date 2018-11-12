package com.master.attendanceapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CheckShortageBySem extends Activity {

	String name[], mobile[], ids[], attended[];
	int id, sem, classes;
	TextView banner1, banner2;
	float percent;
	TextView tvID, tvName, tvMob, tvPercent, tvClasses;
	Button btnReport;
	TableLayout table;
	Typeface economica, calibri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_shortage_sem);

		Bundle b = getIntent().getExtras();

		id = b.getInt("sem");

		economica = Typeface
				.createFromAsset(getAssets(), "fonts/economica.otf");
		calibri = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

		banner1 = (TextView) findViewById(R.id.banner1);
		banner2 = (TextView) findViewById(R.id.banner2);

		banner1.setTypeface(economica);
		banner2.setTypeface(calibri);

		tvClasses = (TextView) findViewById(R.id.classes);

		table = (TableLayout) findViewById(R.id.table);

		banner2.setTypeface(economica);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		new CheckAttendance().execute();
	}

	private class CheckAttendance extends AsyncTask<String, Integer, String> {

		ProgressDialog progressDialog;

		// String error;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(CheckShortageBySem.this);
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
				DatabaseIO dbIO = new DatabaseIO(CheckShortageBySem.this);
				dbIO.openDB();

				name = dbIO.getStudentsShortage(DatabaseIO.NAME, id);

				mobile = dbIO.getStudentsShortage(DatabaseIO.GUARDIAN, id);

				ids = dbIO.getStudentsShortage(DatabaseIO.ROW_ID, id);

				attended = dbIO.getStudentsShortage(DatabaseIO.PRESENT, id);

				classes = dbIO.getClasses(id);

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

				tvClasses.setText("TOTAL CLASSES : " + classes);

				android.widget.TableLayout.LayoutParams params = new android.widget.TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(10, 10, 10, 10);

				for (int i = 0; i < ids.length; i++) {
					TableRow tr = new TableRow(CheckShortageBySem.this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

					tvID = new TextView(CheckShortageBySem.this);
					tvID.setText(ids[i]);
					tvID.setTextColor(Color.WHITE);
					tvID.setPadding(5, 5, 5, 5);
					tvID.setGravity(Gravity.CENTER);
					tvID.setTextSize(18);
					tvID.setTypeface(calibri);
					tr.addView(tvID);

					tvName = new TextView(CheckShortageBySem.this);
					tvName.setText(name[i]);
					tvName.setTypeface(calibri);
					tvName.setTextColor(Color.WHITE);
					tvName.setTextSize(18);
					tvName.setPadding(5, 5, 5, 5);
					tvName.setGravity(Gravity.CENTER);
					tr.addView(tvName);

					if (attended[i] != null && mobile[i] != null) {
						
						int a = attended[i] != null ? Integer
								.parseInt(attended[i]) : 0;
						float percent = (a * 100) / classes;
						tvPercent = new TextView(CheckShortageBySem.this);
						tvPercent.setText("" + percent);
						tvPercent.setTypeface(calibri);
						tvPercent.setGravity(Gravity.CENTER);
						tvPercent.setTextColor(Color.WHITE);
						tvPercent.setTextSize(18);
						tvPercent.setPadding(5, 5, 5, 5);
						tr.addView(tvPercent);

						tvMob = new TextView(CheckShortageBySem.this);
						tvMob.setText(mobile[i]);
						tvMob.setTypeface(calibri);
						tvMob.setBackgroundColor(Color.RED);
						tvMob.setTextColor(Color.WHITE);
						tvMob.setPadding(5, 5, 5, 5);
						tvMob.setTextSize(18);
						tvMob.setGravity(Gravity.CENTER);
						final String mob = mobile[i];



						tvMob.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String message = "The Message Will Go Here";

								Intent intent = new Intent(Intent.ACTION_VIEW,
										Uri.parse("sms:" + mob));
								intent.putExtra("sms_body", message);
								startActivity(intent);
							}
						});
						tr.addView(tvMob);

					}

					

					table.addView(tr, params);

				}

				TableRow tr = new TableRow(CheckShortageBySem.this);
				tvID = new TextView(CheckShortageBySem.this);
				tvID.setText("__________");
				tr.addView(tvID);

				tvName = new TextView(CheckShortageBySem.this);
				tvName.setText("__________");
				tr.addView(tvName);

				tvPercent = new TextView(CheckShortageBySem.this);
				tvPercent.setText("__________");
				tr.addView(tvPercent);

				tvMob = new TextView(CheckShortageBySem.this);
				tvMob.setText("__________");
				tr.addView(tvMob);

				table.addView(tr, params);

				table.setStretchAllColumns(true);

			} catch (Exception ex) {

				Toast.makeText(CheckShortageBySem.this, ex.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

}
