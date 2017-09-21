package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Cookbook extends TemplateElement {
	/*
	 * 菜谱
	 */
	
	private int index;
	private ArrayList<Map<String, Object>> list;
	private String text;
	private String url;
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	public Cookbook(int index) {
		this.index = index;
	}

	public Cookbook() {

	}

	public Cookbook(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
	}

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

	


	public String process(Match match) {
		StringBuilder sb = null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			sb = new StringBuilder();
			String url = "http://api.avatardata.cn/Cook/Name?key=c2b260baec604f15aab47f521747d213&name="+t+"";
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
				text = "参数不正确！";
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
				sb.append("菜名：" + wMap.get("name") + "\n" + wMap.get("message"));
			}
			text=sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
