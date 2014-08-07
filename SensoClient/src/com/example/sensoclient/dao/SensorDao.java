package com.example.sensoclient.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.sensoclient.model.TempData;
import com.example.sensoclient.model.TempDataSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SensorDao {
	private SensorDBHelper helper = null;
	
	public SensorDao(Context c)
	{
		helper = new SensorDBHelper(c);
	}
	
	public void onDestroy()
	{
		helper.close();
	}
	
	public void save(String dataID,String time,String temp)
	{
		ContentValues values = new ContentValues();

		values.put("sid", dataID);
		values.put("time", time);
		values.put("temp", temp);
		
		helper.insert(values);
	}
	
	public void deleteAll()
	{
		helper.delAll();
	}
	
	
	public void update(int rid,String dataID,String time,String temp)
	{
		ContentValues values = new ContentValues();

		values.put("sid", dataID);
		values.put("time", time);
		values.put("temp", temp);
		
		helper.update(rid, values);
	}
	
	public String[] getAllDataIDs()
	{
		Cursor c = helper.queryDistinct();
		String[] result = new String[c.getCount()];
		//c.moveToFirst();
		int i=0;
		while (c.moveToNext())
		{
			//result.add(c.getString(0));
			result[i]=c.getString(0);
			i++;
			
		}
		return result;
	}
	
	public TempDataSet getDataSet(String id)
	{
		Cursor c = helper.queryByDataID(id);
		TempDataSet result = null;
		if(c.getCount()>0)
		{
			result = new TempDataSet(id);
			//c.moveToFirst();
			while(c.moveToNext())
			{
				TempData t = new TempData(c.getInt(2),c.getDouble(3));
				result.addData(t);
			}
		}
		result.sortData();
		return result;
	}
	
	public HashMap getAllDataSets()
	{
		HashMap dataSets = new HashMap();
		Cursor c = helper.query();
		while (c.moveToNext()) {
			String dataID =c.getString(1);
			int time = c.getInt(2);
			double temp = c.getDouble(3);
			
			
			if(dataSets.containsKey(dataID)){
				TempDataSet ds = (TempDataSet)dataSets.get(dataID);
				TempData d = new TempData(time,temp);
				ds.addData(d);
			}
			else{
				TempDataSet ds = new TempDataSet(dataID);
				TempData d = new TempData(time,temp);
				ds.addData(d);
				dataSets.put(dataID,ds);
			}
		}
		
		return dataSets;
	}
}
