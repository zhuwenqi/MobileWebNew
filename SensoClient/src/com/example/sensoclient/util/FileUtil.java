package com.example.sensoclient.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Environment;

public class FileUtil {

	public static ArrayList readLines(String fileName)
	{
		File sdCardDir;
		FileInputStream fis;
		BufferedReader br = null;
		ArrayList results = null;
		try {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				results = new ArrayList();
				sdCardDir = Environment.getExternalStorageDirectory();
				fis = new FileInputStream(sdCardDir.getCanonicalPath()
						+ fileName);
				br = new BufferedReader(new InputStreamReader(fis));

				String line = null;
				while ((line = br.readLine()) != null) {
					results.add(line);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static void writeContent(String fileName,String content)
	{
		try {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDirOut = Environment.getExternalStorageDirectory();
				File targetFileOut =new File(sdCardDirOut.getCanonicalPath()+fileName);
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
