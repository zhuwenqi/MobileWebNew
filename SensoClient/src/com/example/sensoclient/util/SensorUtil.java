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
        // 1. 获得一个相当于浏览器对象HttpClient，使用这个接口的实现类来创建对象，DefaultHttpClient
        HttpClient hc = new DefaultHttpClient();
        // DoPost方式请求的时候设置请求，关键是路径
        HttpPost request = new HttpPost(path);
        // 2. 为请求设置请求参数，也即是将要上传到web服务器上的参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            NameValuePair nameValuePairs = new BasicNameValuePair(entry.getKey(), entry.getValue());
            parameters.add(nameValuePairs);
        }
        // 请求实体HttpEntity也是一个接口，我们用它的实现类UrlEncodedFormEntity来创建对象，注意后面一个String类型的参数是用来指定编码的
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        request.setEntity(entity);
        // 3. 执行请求
        HttpResponse response = hc.execute(request);
        // 4. 通过返回码来判断请求成功与否
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
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == 200) { // 200表示请求成功
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
