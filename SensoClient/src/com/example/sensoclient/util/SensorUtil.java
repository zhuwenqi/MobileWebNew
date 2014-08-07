package com.example.sensoclient.util;

import java.net.HttpURLConnection;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SensorUtil {

	public static void writeDebug(String info) {
		if (SensorConstants.DEBUG_MODE) {
			String s = getCurrentTimeStr() + info + "\n";
			FileUtil.writeContent(SensorConstants.DEBUG_FILE_NAME, s);
		}
	}

	public static String getCurrentTimeStr() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
	
	public static void submitData(Map<String, String> map, String path)
	{
		try{
			SensorUtil.writeDebug("In submitData");
        // 1. ���һ���൱�����������HttpClient��ʹ������ӿڵ�ʵ��������������DefaultHttpClient
        HttpClient hc = new DefaultHttpClient();
        // DoPost��ʽ�����ʱ���������󣬹ؼ���·��
        HttpPost request = new HttpPost(path);
        // 2. Ϊ�����������������Ҳ���ǽ�Ҫ�ϴ���web�������ϵĲ���
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            NameValuePair nameValuePairs = new BasicNameValuePair(entry.getKey(), entry.getValue());
            parameters.add(nameValuePairs);
        }
        // ����ʵ��HttpEntityҲ��һ���ӿڣ�����������ʵ����UrlEncodedFormEntity����������ע�����һ��String���͵Ĳ���������ָ�������
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        request.setEntity(entity);
        // 3. ִ������
        HttpResponse response = hc.execute(request);
        // 4. ͨ�����������ж�����ɹ����
        SensorUtil.writeDebug("In submitData: "+response.getStatusLine().getStatusCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public static String getStringFromWeb(String url) {

		HttpClient client = new DefaultHttpClient();

		HttpPost request;
		try {
			writeDebug("in getStringFromWeb");
			writeDebug("url="+url);
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);
			//writeDebug("response="+response.getStatusLine().getStatusCode());
			// �ж������Ƿ�ɹ�
			if (response.getStatusLine().getStatusCode() == 200) { // 200��ʾ����ɹ�
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String beanListToJson = EntityUtils.toString(entity, "GBK");
					writeDebug("beanListToJson="+beanListToJson);
					return beanListToJson;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			writeDebug("Exception="+e.toString());
			e.printStackTrace();
		}
		return null;
	}

}
