package co.aiml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class CookBook {

	private static String text;
	private static List<Map<String, Object>> list;

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
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			String url = "http://api.avatardata.cn/Cook/Name?key=c2b260baec604f15aab47f521747d213&name=%E7%95%AA%E8%8C%84%E7%82%92%E9%B8%A1%E8%9B%8B";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(6000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			json = jo.getString("result");
			if (!reason.equals("Succes")) {
				text = "��������ȷ��";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String name = array.getJSONObject(i).getString("name");
					String message = array.getJSONObject(i).getString("message");
					map.put("name", name);
					map.put("message", message);
					list.add(map);
				}
			}
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> wMap = list.get(j);
				sb.append("������" + wMap.get("name") + "\n" + wMap.get("message"));
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
