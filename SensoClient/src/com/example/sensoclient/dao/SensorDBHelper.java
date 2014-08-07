package com.example.sensoclient.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SensorDBHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "sensor.db";
	private static final String TBL_NAME = "SensorData";
	private static final String CREATE_TBL = " create table "
			+ " SensorData(rid integer primary key autoincrement,"+ 
						"sid text," +
						"time integer, " +
						"temp real)";
	
	private SQLiteDatabase db;

	SensorDBHelper(Context c) {
		super(c, DB_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(CREATE_TBL);
	}

	public void insert(ContentValues values) {
		if (db == null)
			db = getWritableDatabase();
		db.insert(TBL_NAME, null, values);
		//db.close();
	}

	public Cursor query() {
		if (db == null)
			db = getWritableDatabase();
		Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);
		//c.moveToFirst();
		return c;
	}
	
	public Cursor queryByDataID(String id)
	{
		if (db == null)
			db = getWritableDatabase();
		Cursor c = db.query(TBL_NAME, null, "sid=?", new String[]{id}, null, null, null);
		//c.moveToFirst();
		return c;
	}
	
	public Cursor queryDistinct()
	{
		if (db == null)
			db = getWritableDatabase();
		Cursor c = db.query(true, TBL_NAME, new String[]{"sid"}, null, null, null, null, null, null);
		//c.moveToFirst();
		return c;
	}

	public void del(int id) {
		if (db == null)
			db = getWritableDatabase();
		db.delete(TBL_NAME, "rid=?", new String[] { String.valueOf(id) });
		//db.close();
	}
	
	public void delAll()
	{
		if (db == null)
			db = getWritableDatabase();
		db.delete(TBL_NAME, null, null);
		//db.close();
	}
	
	public void update(int id,ContentValues values) {
		if (db == null)
			db = getWritableDatabase();
		
		db.update(TBL_NAME,values,"rid=?", new String[] { String.valueOf(id) });
		//db.close();
	}

	public void close() {
		if (db != null)
			db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
