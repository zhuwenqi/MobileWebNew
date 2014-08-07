/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sensoclient.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

import com.example.sensoclient.manager.ClientManager;
import com.example.sensoclient.model.TempData;
import com.example.sensoclient.model.TempDataSet;
import com.example.sensoclient.util.SensorConstants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Temperature sensor demo chart.
 */
public class SensorValuesChart extends AbstractDemoChart {
	private static final long HOUR = 3600 * 1000;

	private static final long DAY = HOUR * 24;

	private static final int HOURS = 24;

	private String dataID;

	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Sensor data";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The temperature, as read from an outside and an inside sensors";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		
		TempDataSet ds = ClientManager.getInstance(context).getDataSet(this.getDataID());
		String[] titles = new String[] {"Data "+this.getDataID()};
		List<int[]>x = new ArrayList<int[]>();
		List<double[]> values = new ArrayList<double[]>();
		int[] time= new int[ds.getDataLength()];
		double[] temp = new double[ds.getDataLength()];
		int i=0;
		Iterator it = ds.getDataList().iterator();
		while(it.hasNext())
		{
			TempData t =(TempData)it.next();
			time[i]=t.getSensorTime();
			temp[i]=t.getSensorTemp();
			i++;
		}
		x.add(time);
		values.add(temp);
		

		int[] colors = new int[] { Color.GREEN};
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int j = 0; j < length; j++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(j))
					.setFillPoints(true);
		}
		
		int minTime =x.get(0)[0];
		int maxTime =x.get(0)[ds.getDataLength()-1];
		
		setChartSettings(renderer, "Sensor temperature", "Hour",
				"Celsius degrees", minTime,
				maxTime, SensorConstants.MIN_TEMP, SensorConstants.MAX_TEMP, Color.LTGRAY,
				Color.LTGRAY);
		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		Intent intent = ChartFactory.getTimeChartIntent(context,
				buildDateDatasetSingle(titles, x, values), renderer, "hh");
		return intent;
	}

}
