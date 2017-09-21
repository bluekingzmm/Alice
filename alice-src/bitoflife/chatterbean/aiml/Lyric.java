package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Lyric extends TemplateElement {
	/*
	 * 输入关键字查找词语
	 */
	private int index;
	private String text;

	public Lyric() {
	}

	public Lyric(Attributes attributes) {
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
			String t = w.replace("+", "");
			url = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=%E6%AD%8C%E8%AF%8D"+t;
			String url2=url.trim();
			URL url1 = new URL(url2);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			text = jo.getString("content");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
