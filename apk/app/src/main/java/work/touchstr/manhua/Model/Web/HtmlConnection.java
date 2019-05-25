package work.touchstr.manhua.Model.Web;

import android.util.Log;

import work.touchstr.manhua.Model.Web.Html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.TreeMap;

public class HtmlConnection {
	CookieManager cookieManager = null;
	String url;
	Html.Type type;
	String[] variableArr = null;
	String encode="utf-8";
	Map<String,String> requestPropertys=new TreeMap<String,String>();

	public void addRequestProperty(String key,String value)
	{
		requestPropertys.put(key,value);
	}

	public HtmlConnection(String url, Html.Type type) {
		this(url, type, null);
	}
	public void setEncode(String encode)
	{
		this.encode=encode;
	}

	public HtmlConnection(String url, Html.Type type, String... variableArr) {

		this.url = url;
		this.type = type;
		this.variableArr = variableArr;
	}


	public String post()
	{
		return null;
	}

	//可以获得被get转换后的url地址效果
	public String getGetUrl() {
		String actualUrl = url;
		if (variableArr != null) {
			actualUrl += "?";
			for (String variable : variableArr) {
				actualUrl = actualUrl + variable + "&";
			}
			actualUrl = actualUrl.substring(0, actualUrl.length() - 1);
		}
		return actualUrl;
	}

	public String get() throws ConnectionException {
		BufferedReader in = null;
		try {
			String result = "";
			URL realUrl = new URL(getGetUrl());

			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			//在这里加载特殊的头结构
			for(Map.Entry<String,String> data:requestPropertys.entrySet())
			{
				connection.setRequestProperty(data.getKey(),data.getValue());
			}
			connection.connect();
			if (connection.getResponseCode() == 200) {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
				String line;
				while ((line = in.readLine()) != null) {
					result+=line+"\n";
				}
				return result;
			} else {
				throw new ConnectionException(connection.getResponseCode());
			}
		} catch (ConnectionException e) {
			throw e;
		} catch (Exception e) {
			Log.e("HtmlConnection","未知错误");
			return "";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					Log.e("HtmlConnection","未知错误");
				}
			}
		}

	}

	public String getContent() throws ConnectionException {
		if (type == Html.Type.Post) {
			return post();
		} else {
			return get();
		}
	}

}