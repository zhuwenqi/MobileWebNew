package com.example.sensoclient.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TempDataSet {
	private String dataID;
	private int dataLength;
	
	private List dataList;
	
	public TempDataSet(String newID){
		dataID = newID;
		dataList= new ArrayList();
		dataLength = 0;
		
	}

	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	public int getDataLength() {
		return dataLength;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
	public void addData(TempData newData){
		dataList.add(newData);
		dataLength++;
	}
	
	public void sortData()
	{
		Collections.sort(dataList);
	}
	
	
}
