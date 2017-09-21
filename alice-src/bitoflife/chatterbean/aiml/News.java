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
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class News extends TemplateElement {
	/*
	 * 输入关键字查找新闻
	 */
	private int index;
	private String text;

	public News() {
	}

	public News(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
		String url = null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			t = w.replace("+", "");
			List<String> lists = getResult(a, t);

			for (String s : lists) {
				t = s;
			}
			
			String n = getCityId(t);
			if (n == null) {
				text = "没有该项哦！";
			}
			url = "http://wangyi.butterfly.mopaasapp.com/news/api?type=" + n + "&page=1&limit=5";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getString("list");
			JSONArray array = jo.getJSONArray("list");	
			for (int i = 0; i < array.length(); i++) {
				title = array.getJSONObject(i).getString("title");
				time = array.getJSONObject(i).getString("time");
				docurl = array.getJSONObject(i).getString("docurl");
				imgurl = array.getJSONObject(i).getString("imgurl");

			}
			text = "标题:" + title + "/n时间:" + time + "/n链接:" + docurl + "/n" + imgurl;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	// static String[] a = { "%E5%86%9B%E4%BA%8B", "%E4%BD%93%E8%82%B2",
	// "%E7%A7%91%E6%8A%80", "%E6%95%99%E8%82%B2",
	// "%E5%A8%B1%E4%B9%90", "%E8%B4%A2%E7%BB%8F", "%E8%82%A1%E7%A5%A8",
	// "%E6%97%85%E6%B8%B8",
	// "%E5%A5%B3%E4%BA%BA" };
	private String t;
	private String title;
	private String time;
	private String imgurl;
	private String docurl;

	private static String getCityId(String cityName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("%E5%86%9B%E4%BA%8B", "war");
		map.put("%E4%BD%93%E8%82%B2", "sport");
		map.put("%E7%A7%91%E6%8A%80", "tech");
		map.put("%E6%95%99%E8%82%B2", "edu");
		map.put("%E5%A8%B1%E4%B9%90", "ent");
		map.put("%E8%B4%A2%E7%BB%8F", "money");
		map.put("%E8%82%A1%E7%A5%A8", "gupiao");
		map.put("%E6%97%85%E6%B8%B8", "travel");
		map.put("%E5%A5%B3%E4%BA%BA", "lady");
		return map.get(cityName.trim());
	}
	static String[] a = { "%E5%86%9B%E4%BA%8B", "%E4%BD%93%E8%82%B2", "%E7%A7%91%E6%8A%80", "%E6%95%99%E8%82%B2", "%E5%A8%B1%E4%B9%90", "%E8%B4%A2%E7%BB%8F", "%E8%82%A1%E7%A5%A8", "%E6%97%85%E6%B8%B8", "%E5%A5%B3%E4%BA%BA"};

	/*
	 * 集合中完成模糊查询
	 */
	private static List<String> getResult(String[] arrays, String inputStr) {

		List<String> list = new ArrayList<String>();

		for (String s : arrays) {
			if (s.matches(".*" + inputStr + ".*")) {
				list.add(s);
			}
		}

		return list;
	}
}

