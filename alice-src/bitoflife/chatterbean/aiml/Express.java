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

public class Express extends TemplateElement {
	/*
	 * 快递
	 */

	private String text;
	private int index;
	private static JSONArray a;
	private static String number;
	private static String type;
	private static String issign;
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Express(int index) {
		this.index = index;
	}

	public Express(Attributes attributes) {
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
		ArrayList<Map<String, Object>> list = null;
		StringBuilder sb = null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			sb = new StringBuilder();
			String url = "http://api.jisuapi.com/express/query?appkey=38d79f3a989e19a3&type=auto&number=" + t + "";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String msg = jo.getString("msg");
			if (!msg.equals("ok")) {
				text = "参数不正确！";
			} else {
				json = jo.getString("result");
				jo = new JSONObject(json);
				Map<String, Object> map = null;
				for (int i = 0; i < jo.length(); i++) {
					map = new HashMap<String, Object>();
					number = jo.getString("number");
					type = jo.getString("type");
					issign = jo.getString("issign");
					if (issign.equals("1")) {
						issign = "已签收";
					} else {
						issign = "未签收";
					}

					a = jo.getJSONArray("list");
					
					
					list = new ArrayList<Map<String, Object>>();
					
					
				}
				sb.append("快递单:" +number + ",快递公司:" + type + ",是否签收:" + issign
						+ "\n");
				String time = null;
				String status = null;
				for (int j = 0; j < a.length(); j++) {
					time = a.getJSONObject(j).getString("time");
					status = a.getJSONObject(j).getString("status");
					sb.append("时间:" +time + "\n状态:" + status);
				}
				text = sb.toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

}
