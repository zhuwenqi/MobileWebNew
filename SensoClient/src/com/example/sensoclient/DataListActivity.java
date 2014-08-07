package com.example.sensoclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.sensoclient.charts.IDemoChart;
import com.example.sensoclient.charts.SensorValuesChart;
import com.example.sensoclient.manager.ClientManager;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DataListActivity extends ListActivity{
	
	String dataIDs[];
	
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    dataIDs = ClientManager.getInstance(DataListActivity.this).getAllDataIDs();
	    
	    
	    setListAdapter(new SimpleAdapter(this, getListValues(), android.R.layout.simple_list_item_2,
	        new String[] { IDemoChart.NAME, IDemoChart.DESC }, new int[] { android.R.id.text1,
	            android.R.id.text2 }));
	  }

	  private List<Map<String, String>> getListValues() {
	    List<Map<String, String>> values = new ArrayList<Map<String, String>>();
	    /*
	    int length = mMenuText.length;
	    for (int i = 0; i < length; i++) {
	      Map<String, String> v = new HashMap<String, String>();
	      v.put(IDemoChart.NAME, mMenuText[i]);
	      v.put(IDemoChart.DESC, mMenuSummary[i]);
	      values.add(v);
	    }
	    */
	    String[] datas = ClientManager.getInstance(DataListActivity.this).getAllDataIDs();

	    for(int i=0;i<datas.length;i++)
	    {
	    	String dataId = datas[i];
		    Map<String, String> v = new HashMap<String, String>();
		    v.put(IDemoChart.NAME, dataId);
		    v.put(IDemoChart.DESC, "Data ID: "+dataId);
		    values.add(v);
	    }
	    return values;
	  }

	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    Intent intent = null;
	    /*
	    if (position == 0) {
	      intent = new Intent(this, XYChartBuilder.class);
	    } else if (position == 1) {
	      intent = new Intent(this, PieChartBuilder.class);
	    } else if (position <= mCharts.length + 1) {
	      intent = mCharts[position - 2].execute(this);
	    } else {
	      intent = new Intent(this, GeneratedChartDemo.class);
	    }
	    */
	    //startActivity(intent);
	    
	    SensorValuesChart chart = new SensorValuesChart();
	    chart.setDataID(dataIDs[position]);
	    intent = chart.execute(DataListActivity.this);
	    startActivity(intent);
	  }

}
