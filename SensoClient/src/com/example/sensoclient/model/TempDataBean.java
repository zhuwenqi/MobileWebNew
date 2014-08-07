package com.example.sensoclient.model;

public class TempDataBean {
	private int id;
	private String sid;
	private int sTime;
	private double sData;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getsTime() {
		return sTime;
	}
	public void setsTime(int sTime) {
		this.sTime = sTime;
	}
	public double getsData() {
		return sData;
	}
	public void setsData(double sData) {
		this.sData = sData;
	}
}
