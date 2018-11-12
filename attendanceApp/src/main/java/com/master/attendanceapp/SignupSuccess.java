package com.master.attendanceapp;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class SignupSuccess extends Activity implements OnClickListener {

	 long lastID;
	 String Name, Parent, Reg, Mob;
	 
	 TextView tvID , tvName , tvReg;
	 LinearLayout ivQR , main;
	 Button btnSave;
	 BarcodeGenerator bcg;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_success);
		
		
		Bundle b = getIntent().getExtras();
		
		lastID = b.getLong("id");
		Name = b.getString("name");
		Parent = b.getString("parent");
		Reg = b.getString("reg");
		Mob = b.getString("mob");
		
		
		tvID = (TextView) findViewById(R.id.StudentID);
		tvName = (TextView) findViewById(R.id.name);
		tvReg = (TextView) findViewById(R.id.reg);
		
		ivQR = (LinearLayout) findViewById(R.id.code);
		main = (LinearLayout) findViewById(R.id.details);
		
		btnSave = (Button) findViewById(R.id.save);
		
		tvID.setText("Student ID : " + lastID);
		tvName.setText("Name : " + Name);
		tvReg.setText("Reg No : " + Reg);
		
		DisplayMetrics metrics = new DisplayMetrics();		
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);


		Typeface economica = Typeface.createFromAsset(getAssets(), "fonts/economica.otf");
		Typeface segoe = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");	
		
		tvID.setTypeface(economica);
		tvName.setTypeface(economica);
		tvReg.setTypeface(economica);
		
		
		bcg = new BarcodeGenerator(this, lastID,
				Name, Parent, Reg, Mob , metrics);
		
		btnSave.setTypeface(segoe);
		ivQR.addView(bcg);
		
		LayoutParams params = new LayoutParams(metrics.widthPixels , metrics.widthPixels);
		params.gravity = Gravity.CENTER;
		ivQR.setLayoutParams(params);

		//Toast.makeText(this, bcg.getWidth(), Toast.LENGTH_LONG).show();
		
		btnSave.setOnClickListener(this);
			
	}
	protected Bitmap ScreenToBitmap(LinearLayout v) {
		
	
		v.setDrawingCacheEnabled(true);
		Bitmap bmp = null;
		if(v.getDrawingCache(true) != null)
		{			
			bmp = Bitmap.createBitmap(v.getWidth() , v.getHeight() , Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bmp);
			v.draw(c);
		}
		else {
			Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show();
			
		}
		v.setDrawingCacheEnabled(false);


		return bmp;

    }
	
	private void saveToInternalStorage(Bitmap bitmap) {
		try {

			File dir = new File(android.os.Environment.getExternalStorageDirectory()+"/QR_Codes");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir , Name + lastID + ".png");
			
			if(file.exists())
				file.delete();
				

			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 85 , ostream);
			ostream.flush();
			ostream.close();

			shareImage(file);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void shareImage(File file){
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");

		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
		intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		try {
			startActivity(Intent.createChooser(intent, "Share Screenshot"));
		} catch (ActivityNotFoundException e) {
			Toast.makeText(SignupSuccess.this, "No App Available", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		btnSave.setVisibility(View.GONE);
		Bitmap bmp = ScreenToBitmap(main);
		saveToInternalStorage(bmp);
		Toast.makeText(this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
	}
}
