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

public class Peripheralquery extends TemplateElement {
	/*
	 * 周边查询
	 */
	
	private int index;
	private int indextwo;
	private ArrayList<Map<String, Object>> list;
	private String text;
	private String url;

	public int getIndextwo() {
		return indextwo;
	}

	public void setIndextwo(int indextwo) {
		this.indextwo = indextwo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	public Peripheralquery(int index,int indextwo) {
		this.index = index;
		this.indextwo=indextwo;
	}

	public Peripheralquery() {

	}

	public Peripheralquery(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
		String value1 = attributes.getValue(1);
		indextwo = (value1 != null ? Integer.parseInt(value1) : 2);
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
			String w1 = URLEncoder.encode(match.wildcard(PATTERN, indextwo), "UTF-8");
			String t1 = w1.replace("+", "");
			sb = new StringBuilder();
			url = "http://api.avatardata.cn/Near/Search?key=0b11cdbfc5e44f0ca84f0fe741195fed&keyWord="+t1+"&location=116.305145,39.982368&cityName="+t+"&radius=3000m&number=0&page=1&coord_type=bd09ll&out_coord_type=bd09ll";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			if (!reason.equals("Succes")) {
				text = "参数不正确！";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				jo=new JSONObject(array);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String name = array.getJSONObject(i).getString("name");
					String cityName = array.getJSONObject(i).getString("cityName");
					String address = array.getJSONObject(i).getString("address");
					String type = array.getJSONObject(i).getString("type");
					String additionalInformation = array.getJSONObject(i).getJSONObject("additionalInformation").getString("tag");
					map.put("additionalInformation", additionalInformation);
					map.put("name", name);
					map.put("cityName", cityName);
					map.put("address", address);
					map.put("type", type);
					list.add(map);
				}
				
			}
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> wMap = list.get(j);
				sb.append("城市名：" + wMap.get("cityName") + "\t地方：" + wMap.get("name")+ "\t地址：" + wMap.get("address")+ "\t类型：" + wMap.get("type")+ "\t标志：" + wMap.get("additionalInformation"));
			}
			text=sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
