package com.example.sensoclient.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.example.sensoclient.dao.SensorDao;
import com.example.sensoclient.model.*;
import com.example.sensoclient.util.SensorConstants;
import com.example.sensoclient.util.SensorUtil;
import com.example.sensoclient.util.TestFileUtil;
import com.google.gson.Gson;

public class ClientManager {
	private static ClientManager cm = null;
	private Context con = null;
	private SensorDao dao= null;
	
	TestFileUtil fu =null;
	//private HashMap dataSets=null;
	
	private ClientManager()
	{
		//dataSets = new HashMap();
		fu = new TestFileUtil();
	}
	
	
	
	
	public static ClientManager getInstance(Context c)
	{
		if(cm==null){
			cm = new ClientManager();
		}
		if(c!=null){
			cm.setContext(c);
		}
		return cm;
	}
	
	public void setContext(Context c)
	{
		con = c;
		dao = new SensorDao(c);
	}
	
	public void onDestroy()
	{
		dao.onDestroy();
	}
	
	public void deleteAll()
	{
		dao.deleteAll();
	}
	
	public void loadDataToDB()
	{
		String line;
		fu.readReady();
		while ((line = fu.readOneLine()) != null) {
			String s[] = line.split(";");
			dao.save(s[0], s[1], s[2]);
		}
		fu.readEnd();
	}
	
	public void loadDataFromWeb()
	{
		String dataStr = SensorUtil.getStringFromWeb(SensorConstants.SERVER_URL+SensorConstants.ACTION_GET_ALL_DATA);
		
		SensorUtil.writeDebug("back to loadDataFromWeb");
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<TempDataBean>>() {
		}.getType();
		Gson gson = new Gson();
		List<TempDataBean>  list  = gson.fromJson(dataStr, type);
		SensorUtil.writeDebug("back to loadDataFromWeb2 "+dataStr);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			TempDataBean bean =(TempDataBean)it.next();
			dao.save(bean.getSid(),Integer.toString(bean.getsTime()),Double.toString(bean.getsData()));
		}
		
	}
	
	
	/*
	public void loadDataFromFile()
	{
		String line;
		fu.readReady();
		while ((line = fu.readOneLine()) != null) {
			String s[] = line.split(";");
			if(dataSets.containsKey(s[0])){
				TempDataSet ds = (TempDataSet)dataSets.get(s[0]);
				TempData d = new TempData(Integer.parseInt(s[1]),Double.parseDouble(s[2]));
				ds.addData(d);
			}
			else{
				TempDataSet ds = new TempDataSet(s[0]);
				TempData d = new TempData(Integer.parseInt(s[1]),Double.parseDouble(s[2]));
				ds.addData(d);
				dataSets.put(s[0],ds);
			}
		}
	}
	*/
	
	public String[] getAllDataIDs()
	{
		return dao.getAllDataIDs();
	}
	
	public TempDataSet getDataSet(String id)
	{
		return dao.getDataSet(id);
	}
	
	public void writeDataToFile()
	{
		HashMap dataSets = dao.getAllDataSets();
		
		Iterator it = dataSets.keySet().iterator();
		StringBuilder sb = new StringBuilder("");
		while(it.hasNext())
		{
			String key = (String)it.next();
			TempDataSet ds = (TempDataSet)dataSets.get(key);
			sb.append("DataSet: ");
			sb.append(key);
			sb.append("\n");

			Iterator it2 = ds.getDataList().iterator();
			while(it2.hasNext())
			{
				TempData d = (TempData)it2.next();
				sb.append("Time: "+d.getSensorTime()+"--"+"Temp: "+d.getSensorTemp());
				sb.append("\n");
			}
		}
		fu.writeContent(sb.toString());
	}
	
	public void saveDataToWeb()
	{
		
		SensorUtil.writeDebug("in saveDataToWeb");
		HashMap dataSets = dao.getAllDataSets();
		
		List<TempDataBean> beanList = new ArrayList<TempDataBean>();
		
		
		Iterator it = dataSets.keySet().iterator();
		StringBuilder sb = new StringBuilder("");
		while(it.hasNext())
		{
			
			String key = (String)it.next();
			TempDataSet ds = (TempDataSet)dataSets.get(key);
			Iterator it2 = ds.getDataList().iterator();
			while(it2.hasNext())
			{
				TempDataBean bean = new TempDataBean();
				bean.setSid(key);
				TempData d = (TempData)it2.next();
				
				bean.setsTime(d.getSensorTime());
				bean.setsData(d.getSensorTemp());
				
				beanList.add(bean);
			}
		}
		
		Gson gson = new  Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<TempDataBean>>() {
		}.getType();
		String beanListToJson = gson.toJson(beanList,type);
		SensorUtil.writeDebug("in saveDataToWeb beanListToJson = "+beanListToJson);
		HashMap map = new HashMap();
		map.put("GsonDataList", beanListToJson);
		SensorUtil.submitData(map, SensorConstants.SERVER_URL+SensorConstants.ACTION_SAVE_DATA_LIST);
		
		
	}

}
