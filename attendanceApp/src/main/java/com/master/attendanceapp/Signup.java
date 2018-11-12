package com.master.attendanceapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends Activity implements OnClickListener {

	EditText etName, etParent, etReg, etSem, etSection, etRoll, etMob, etEmail,
			etGuard;
	String Name, Parent, Reg, Section, Roll, Mob, Email, Guard;
	Button btnSubmit, btnFill;
	long lastID;
	int Sem;
	TextView banner1;
	

	int[] semesters = {1,2,3,4,5,6,7,8};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signup);

		etName = (EditText) findViewById(R.id.name);
		etParent = (EditText) findViewById(R.id.parentage);
		etReg = (EditText) findViewById(R.id.regno);
		etSem = (EditText) findViewById(R.id.sem);
		etSection = (EditText) findViewById(R.id.section);
		etRoll = (EditText) findViewById(R.id.roll);
		etMob = (EditText) findViewById(R.id.mobNo);
		etGuard = (EditText) findViewById(R.id.mobNo2);
		etEmail = (EditText) findViewById(R.id.email);
		banner1 = (TextView) findViewById(R.id.banner1);

		Typeface economica = Typeface.createFromAsset(getAssets(),
				"fonts/economica.otf");
		banner1.setTypeface(economica);

		Typeface segoe = Typeface.createFromAsset(getAssets(),
				"fonts/segoeui.ttf");

		etName.setHintTextColor(Color.LTGRAY);
		etParent.setHintTextColor(Color.LTGRAY);
		etReg.setHintTextColor(Color.LTGRAY);
		etSem.setHintTextColor(Color.LTGRAY);
		etSection.setHintTextColor(Color.LTGRAY);
		etRoll.setHintTextColor(Color.LTGRAY);
		etMob.setHintTextColor(Color.LTGRAY);
		etGuard.setHintTextColor(Color.LTGRAY);
		etEmail.setHintTextColor(Color.LTGRAY);

		etName.setTypeface(segoe);

		etParent.setTypeface(segoe);

		etReg.setTypeface(segoe);

		etSem.setTypeface(segoe);

		etSection.setTypeface(segoe);

		etRoll.setTypeface(segoe);

		etMob.setTypeface(segoe);
		etEmail.setTypeface(segoe);
		etGuard.setTypeface(segoe);

		btnFill = (Button) findViewById(R.id.fill);
		btnSubmit = (Button) findViewById(R.id.submit);
		btnSubmit.setOnClickListener(this);
		btnSubmit.setTypeface(segoe);

		btnFill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				etName.setText("John Adam");
				etParent.setText("Adam Voges");
				etReg.setText("IUST/BTECH/13/1234");
				etSem.setText("6");
				etSection.setText("B");
				etRoll.setText("57");
				etMob.setText("9596454545");
				etGuard.setText("969797987");
				etEmail.setText("master@gmail.com");
			}
		});

		overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		Name = etName.getText().toString();
		Parent = etParent.getText().toString();
		Reg = etReg.getText().toString();
		Sem = etSem.getText().toString().equals("") ? 0:Integer.parseInt(etSem.getText().toString());
		Section = etSection.getText().toString();
		Roll = etRoll.getText().toString();
		Mob = etMob.getText().toString();
		Email = etEmail.getText().toString();
		Guard = etGuard.getText().toString();
		
		boolean isWorking = true;
		boolean Err = validateFields();

		if(Err) {
			try {

				DatabaseIO dbIO = new DatabaseIO(this);
				dbIO.openDB();

				lastID = dbIO.createStudent(Name, Parent, Reg, Sem, Section,
						Roll, Mob, Email , Guard);

				dbIO.closeDB();
			} catch (Exception ex) {
			
				Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT)
				.show();
			} finally {
				if (isWorking && lastID > 0) {
					try {

						Bundle b = new Bundle();
						b.putLong("id", lastID);
						b.putString("name", Name);
						b.putString("parent", Parent);
						b.putString("reg", Reg);
						b.putString("mob", Mob);

						Intent i = new Intent(this, SignupSuccess.class);
						i.putExtras(b);
						startActivity(i);

					} catch (Exception ex) {
						Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Dialog d = new Dialog(this);
					d.setTitle("FAILURE "+lastID );
					TextView tv = new TextView(this);
					d.setContentView(tv);
					d.show();
				}
			}
		}
	}



	public boolean validateFields()
	{
		boolean semErr = true,Err = true;


		if (Name.equals("") || Parent.equals("") || Reg.equals("")
				|| Section.equals("") || Roll.equals("")
				|| Mob.equals("") || Email.equals("") || Guard.equals("")) {
			Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT)
					.show();
			Err = false;
		}

		else {
			boolean n = (Name != "") && Name.matches("[A-Za-z0-9_]+");


			if (n) {
				Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}

			n = (Parent != "") && Parent.matches("[A-Za-z0-9_]+");

			if (n) {
				Toast.makeText(this, "Invalid Parent Name", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}

			String regexStr = "^[0-9]+";

			n =  Roll.matches(regexStr);

			if (!n) {
				Toast.makeText(this, "Invalid Roll Number", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}

			//matches 10-digit numbers only

			regexStr = "^[0-9]{10}$";
			n = Mob.matches(regexStr);


			if (!n) {
				Toast.makeText(this, "Invalid Contact Number", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}


			n = Guard.matches(regexStr);

			if (!n) {
				Toast.makeText(this, "Invalid Guardian Contact", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}

			for (int i : semesters) {
				if (i == Sem) {
					semErr = false;
				}
			}
			if (semErr) {
				Toast.makeText(this, "Invalid semester", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}


			Pattern VALID_EMAIL_ADDRESS_REGEX =
					Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(Email);
			n = matcher.find();

			if (!n) {
				Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT)
						.show();
				Err = false;
			}
		}
		return Err;
	}


	public boolean isNumeric(String s)
	{
		try
		{
			double d = Double.parseDouble(s);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

}
