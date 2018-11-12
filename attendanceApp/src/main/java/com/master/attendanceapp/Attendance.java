package com.master.attendanceapp;

//import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import java.util.ArrayList;


public class Attendance extends Activity implements OnClickListener {

	TextView tvID;
	TextView banner1, banner2;
	ArrayList<Integer> studentList = new ArrayList<Integer>();
	private static final int ZBAR_SCANNER_REQUEST = 0;
	private static final int ZBAR_QR_SCANNER_REQUEST = 1;

	Button btnScan, btnAttend;
	TextView tvData;

	String details;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance);

		tvData = (TextView) findViewById(R.id.data);
		btnScan = (Button) findViewById(R.id.scan);
		btnAttend = (Button) findViewById(R.id.attend);
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
		btnScan.setTypeface(segoe);
		tvData.setTypeface(calibri);
		btnAttend.setEnabled(false);
		btnScan.setOnClickListener(this);
		btnAttend.setOnClickListener(this);
		
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	}

	public void launchQRScanner() {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			intent.putExtra(ZBarConstants.SCAN_MODES,
					new int[] { 64 });
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	public void launchScanner() {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
		case ZBAR_QR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				String barcode = data.getStringExtra(ZBarConstants.SCAN_RESULT);

				details = barcode;

				String[] lines = details.split(System
						.getProperty("line.separator"));
				int id = Integer.parseInt(lines[0]);


				if(!(studentList.indexOf(id) >= 0))
				{
					studentList.add(id);
				}

				Toast.makeText(this, "Attendance Recorded For Student ID :"+studentList.get(studentList.size()-1), Toast.LENGTH_SHORT).show();
				launchQRScanner();

				btnAttend.setEnabled(true);

			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if (!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
					tvData.setText(error);
				}
			}else{

					String students = "";
					for(int i = 0 ; i < studentList.size() ; i++)
					{
						if(i < studentList.size() - 1)
							students += studentList.get(i)+" , ";
						else
							students += studentList.get(i);
					}
					tvData.setText("Students bearing IDs whose attendance will be recorded : \n" + students);
					Toast.makeText(this, "Attendance Recorded", Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// launchScanner();
		switch (v.getId()) {
		case R.id.scan:
			launchQRScanner();
			break;
		case R.id.attend:
			new doAttendance().execute();
			break;

		}
	}

	private class doAttendance extends AsyncTask<String, Integer, String> {

		ProgressDialog progressDialog;

		long status;
		int id;

		// String error;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(Attendance.this);
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
				DatabaseIO dbIO = new DatabaseIO(Attendance.this);
				dbIO.openDB();

				for(int i = 0 ; i < studentList.size() ; i++)
				{
					status = dbIO.doAttendanceStudent(studentList.get(i));
				}



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

			if (status > 0) {
				Toast.makeText(Attendance.this, "Attendance Done",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(Attendance.this, "Attendance Failed",
						Toast.LENGTH_LONG).show();
			}

		}
	}

}


