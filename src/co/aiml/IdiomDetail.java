package co.aiml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class IdiomDetail {

	private static String id;

	public static String streamToString(InputStream in) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

		StringBuilder builder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();

	}

	public static void main(String[] args) throws Exception {
		System.out.println(Name());
		try {
			String url = " http://api.avatardata.cn/ChengYu/LookUp?key=e357ed8d1627419e8eca05668524358f&id=d420b457-4b86-4ab1-b824-cb84440131fc";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getString("result");
			jo = new JSONObject(json);
			String name = jo.getString("name");
			String pinying = jo.getString("spell");
			String content = jo.getString("content");
			String derivation = jo.getString("derivation");
			if(derivation==null||derivation.equals("")){
				derivation="��";
			}
			System.out.println("����:" + name + ",ƴ��:" + pinying + ",\t����:" + content + "\t����:" + derivation);
		} catch (Exception e) {

		}

	}
	
	public static String Name() throws Exception {
		  String text=null;
		try {
			String url = "http://api.avatardata.cn/ChengYu/Search?key=e357ed8d1627419e8eca05668524358f&keyWord=%E9%BE%99";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			json = jo.getString("result");
			if (!reason.equals("Succes")) {
              text="��������ȷ��";
			}else{
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
				text = array.getJSONObject(i).getString("id");
				
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;

	}

}
