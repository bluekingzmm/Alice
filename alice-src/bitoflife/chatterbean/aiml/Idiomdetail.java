package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Idiomdetail extends TemplateElement {
	private String name;
	private String pinying;
	private String content;
	private String derivation;
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Idiomdetail() {
	}

	public Idiomdetail(Attributes attributes) {
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
		try {
			String id=Id(match);
			String url = " http://api.avatardata.cn/ChengYu/LookUp?key=e357ed8d1627419e8eca05668524358f&id="+id+"";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getString("result");
			jo = new JSONObject(json);
			name = jo.getString("name");
			pinying = jo.getString("spell");
			content = jo.getString("content");
			derivation = jo.getString("derivation");
			if (derivation == null || derivation.equals("")) {
				derivation = "无";
			}
		} catch (Exception e) {

		}

		return "成语:" + name + ",拼音:" + pinying + ",\t内容:" + content + "\t来自:" + derivation;
	}

	public String Id(Match match) throws Exception {
		String text = null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			String url = "http://api.avatardata.cn/ChengYu/Search?key=e357ed8d1627419e8eca05668524358f&keyWord=" + t
					+ "";
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
				text = "参数不正确！";
			} else {
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
