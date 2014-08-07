package com.example.sensoclient.model;

public class TempData implements Comparable {

	private int sensorTime;
	private double sensorTemp;
	
	public TempData(int time,double temp)
	{
		sensorTime = time;
		sensorTemp = temp;
	}
	public int getSensorTime() {
		return sensorTime;
	}
	public void setSensorTime(int sensorTime) {
		this.sensorTime = sensorTime;
	}
	public double getSensorTemp() {
		return sensorTemp;
	}
	public void setSensorTemp(double sensorTemp) {
		this.sensorTemp = sensorTemp;
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return this.sensorTime-((TempData)arg0).sensorTime;
	}
	
	
	
}
