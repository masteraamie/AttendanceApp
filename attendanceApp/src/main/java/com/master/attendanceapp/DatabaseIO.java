package com.master.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseIO
{
	// Student table columns
	public static final String ROW_ID = "id";
	public static final String NAME = "name";
	public static final String PARENT = "parentage";
	public static final String REG_NO = "reg_no";
	public static final String SEMESTER = "semester";
	public static final String SECTION = "section";
	public static final String ROLL_NO = "roll_no";
	public static final String MOBILE = "mobile_no";
	public static final String GUARDIAN = "guard_no";
	public static final String EMAIL = "email";
	public static final String PRESENT = "present";

	// Semester Classes Table columns
	public static final String CLASSES = "classes";

	// Admin table columns
	public static final String USERNAME = "username";
	public static final String PASS = "password";

	public static final String STATUS = "status";

	private static final int DATABSE_VERSION = 1;
	private static final String DATABASE_NAME = "db_attendance_app";
	public static final String TABLE_STUDENTS = "tbl_student";
	public static final String TABLE_ADMIN = "tbl_admin";
	public static final String TABLE_CLASSES = "tbl_classes";

	private DbHelper dbHelper;
	private final Context ourContext;
	private SQLiteDatabase database;

	String[] data;

	public DatabaseIO(Context c) {
		ourContext = c;
	}

	public void openDB() {
		dbHelper = new DbHelper(ourContext);
		database = dbHelper.getWritableDatabase();
	}

	public void closeDB() {
		dbHelper.close();
	}

	public boolean checkUser() {

		String query = "SELECT * FROM " + TABLE_ADMIN;

		Cursor c = database.rawQuery(query, null);

		if (c.getCount() > 0)
			return true;

		return false;

	}

	public long createStudent(String n, String p, String reg, int sem,
			String sec, String roll, String mob, String em, String guard) {

		ContentValues cv = new ContentValues();
		cv.put(NAME, n);
		cv.put(PARENT, p);
		cv.put(REG_NO, reg);
		cv.put(SEMESTER, sem);
		cv.put(SECTION, sec);
		cv.put(ROLL_NO, roll);
		cv.put(MOBILE, mob);
		cv.put(GUARDIAN, guard);
		cv.put(EMAIL, em);
		cv.put(PRESENT, "0");
		return database.insert(TABLE_STUDENTS, null, cv);

	}

	public long createUser(String un, String pass) {

		ContentValues cv = new ContentValues();
		cv.put(USERNAME, un);
		cv.put(PASS, pass);

		return database.insert(TABLE_ADMIN, null, cv);

	}

	public boolean checkLogin(String un, String pass) {

		String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE (" + USERNAME
				+ "='" + un + "' and " + PASS + "='" + pass + "');";

		Cursor c = database.rawQuery(query, null);
		if (c.getCount() > 0)
			return true;

		return false;

	}

	public long doAttendanceStudent(int id) {

		int count = getStudentsAttendance(id);

		count++;

		ContentValues cv = new ContentValues();
		cv.put(PRESENT, count);

		return database.update(TABLE_STUDENTS, cv, "id=" + id, null);

	}

	private int getStudentsAttendance(int id) {
		// TODO Auto-generated method stub

		String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE id = " + id;

		Cursor c = database.rawQuery(query, null);

		int count = 0;

		if (c.getCount() > 0) {
			int index = c.getColumnIndex(PRESENT);
			c.moveToFirst();
			count = c.getInt(index);
			return count;
		} else {
			return 0;
		}

	}

	public int getClasses(int sem) {

		String query = "SELECT * FROM " + TABLE_CLASSES + " WHERE semester = "
				+ sem;

		Cursor c = database.rawQuery(query, null);

		int data = 0;

		if (c.getCount() > 0) {
			int index = c.getColumnIndex(CLASSES);
			c.moveToFirst();
			data = c.getInt(index);
		}
		return data;
	}

	public int getCount(String table) {
		String[] columns = new String[] { ROW_ID, NAME, SEMESTER, ROLL_NO };

		Cursor c = database.query(TABLE_STUDENTS, columns, null, null, null,
				null, null);
		return c.getCount();
	}

	public String[] getAttendanceBySem(String column, int value) {

		String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE semester = "
				+ value;

		Cursor c = database.rawQuery(query, null);

		String[] data = new String[c.getCount()];

		int index = c.getColumnIndex(column);

		int i = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			data[i] = c.getString(index);
			i++;
		}
		return data;
	}

	public String getStudentsWhere(String column, int value) {

		String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE id = "
				+ value;

		Cursor c = database.rawQuery(query, null);

		String data = "";

		int index = c.getColumnIndex(column);
		c.moveToFirst();
		data = c.getString(index);

		return data;		
	}

	public String[] getStudentsInfo(String column) {

		String query = "SELECT " + column + " FROM " + TABLE_STUDENTS;
		Cursor c = database.rawQuery(query, null);

		String[] data = new String[c.getCount()];

		int index = c.getColumnIndex(column);
		int i = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			data[i] = c.getString(index);
			i++;

		}
		return data;
	}

	public String[] getStudentsShortage(String column, int sem) {

		int cls = getClasses(sem);

		String query = "SELECT * FROM " + TABLE_STUDENTS
				+ " WHERE semester =" + sem;

		Cursor c = database.rawQuery(query, null);

		String[] data = new String[c.getCount()];

		int index = c.getColumnIndex(column);
		int attended = c.getColumnIndex(PRESENT);
		int i = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			float percent = (c.getInt(attended) * 100) / cls;
			if (percent < 75) {
				data[i] = c.getString(index);
				i++;
			}
		}
		return data;
	}

	public long setClasses(int sem, int cl) {
		// TODO Auto-generated method stub

		int ex = getClasses(sem);
		ContentValues cv = new ContentValues();

		if (ex == 0) {
			cv.put(SEMESTER, sem);
			cv.put(CLASSES, cl);
			return database.insert(TABLE_CLASSES, null, cv);
		} else {
			cv.put(SEMESTER, sem);
			return database.update(TABLE_CLASSES, cv, "semester=" + sem, null);
		}
	}

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABSE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			String sql = "CREATE TABLE " + TABLE_ADMIN + " ( " + USERNAME
					+ " VARCHAR(30) PRIMARY KEY ," + PASS
					+ " VARCHAR(30) NOT NULL " + " ); ";

			db.execSQL(sql);

			sql = "CREATE TABLE " + TABLE_CLASSES + " ( " + SEMESTER
					+ " INTEGER PRIMARY KEY ," + CLASSES + " INTEGER NOT NULL "
					+ " ); ";

			db.execSQL(sql);

			sql = "CREATE TABLE " + TABLE_STUDENTS + " ( " + ROW_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + NAME
					+ " VARCHAR(100) NOT NULL , " + PARENT
					+ " VARCHAR(100) NOT NULL , " + REG_NO
					+ " VARCHAR(50) NOT NULL , " + SEMESTER
					+ " INTEGER NOT NULL , " + SECTION
					+ " VARCHAR(30) NOT NULL , " + ROLL_NO
					+ " INTEGER  NOT NULL , " + MOBILE
					+ " VARCHAR(50) NOT NULL , " + GUARDIAN
					+ " VARCHAR(50) NOT NULL ," + EMAIL
					+ " VARCHAR(100) NOT NULL ," + PRESENT
					+ " INTEGER NOT NULL" + " ); ";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub


			String sql = "DROP TABLE IF EXISTS " + TABLE_STUDENTS;
			db.execSQL(sql);

			sql = "DROP TABLE IF EXISTS " + TABLE_ADMIN;
			db.execSQL(sql);

			sql = "DROP TABLE IF EXISTS " + TABLE_CLASSES;
			db.execSQL(sql);

			onCreate(db);
		}

	}
}
