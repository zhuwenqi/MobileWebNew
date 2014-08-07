package com.example.sensoclient.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import android.os.Environment;

public class TestFileUtil {

	private File sdCardDir;
	private FileInputStream fis;
	private BufferedReader br = null;

	private boolean isReading = false;

	public boolean readReady() {
		if (!isReading) {
			try {
				isReading = true;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					sdCardDir = Environment.getExternalStorageDirectory();
					fis = new FileInputStream(sdCardDir.getCanonicalPath()
							+ SensorConstants.INPUT_FILE_NAME);
					br = new BufferedReader(new InputStreamReader(fis));
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		return false;
	}

	public void readEnd() {
		isReading = false;
	}

	public String readOneLine() {
		if (isReading && br != null) {
			try {
				return br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public ArrayList readLines() {
		ArrayList results = null;
		if (!isReading) {
			try {

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					results = new ArrayList();
					sdCardDir = Environment.getExternalStorageDirectory();
					fis = new FileInputStream(sdCardDir.getCanonicalPath()
							+ SensorConstants.INPUT_FILE_NAME);
					br = new BufferedReader(new InputStreamReader(fis));

					String line = null;
					while ((line = br.readLine()) != null) {
						results.add(line);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public void writeContent(String content)
	{
		try {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDirOut = Environment.getExternalStorageDirectory();
				File targetFileOut =new File(sdCardDirOut.getCanonicalPath()+SensorConstants.OUTPUT_FILE_NAME);
				FileOutputStream fos = new FileOutputStream(targetFileOut,true);
				fos.write(content.getBytes());
				fos.close();
				/*
				RandomAccessFile raf = new RandomAccessFile(targetFileOut,"rw");
				raf.seek(targetFileOut.length());
				raf.write(content.getBytes());
				raf.close();
				*/

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
