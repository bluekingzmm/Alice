package co.aiml;

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
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Win {

	private static String text;
	private static List<Map<String, Object>> list;
	private static JSONArray array;

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
		String id=null;
		String type=null;
		String name=null;
		String extName=null;
		String path=null;

		String url;
		try {
			url = "http://localhost:8080/cbdq/resourceInfo/findContent?search=zmm";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getJSONObject("result").getString("ResourceInfoList");
			if (json == "error") {
				text = "用户未登录!";
			} else {
				array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					name = array.getJSONObject(i).getString("name");
					type = array.getJSONObject(i).getString("type");
					extName = array.getJSONObject(i).getString("extName");
					path = array.getJSONObject(i).getString("path");
					id = array.getJSONObject(i).getString("id");

				}
			}

			if (array.length() == 0) {
				text = "查询无结果！";
			} else {
				text = "<a onclick='showImg('"+id+"')' href='#'>${raw("+name+")}.${"+extName+"}</a>";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
