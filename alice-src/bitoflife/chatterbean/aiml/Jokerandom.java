package bitoflife.chatterbean.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Jokerandom extends TemplateElement {

	private String url;
	private String text;
	private String content = null;
	public Jokerandom() {
	}

	public Jokerandom(Attributes attributes) {
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
			Random random = new Random();
			int pick = random.nextInt(10000) + 1;
			url = "http://api.avatardata.cn/Joke/NewstJoke?key=f35a8191729e4f37af4b85b4981a8c1e&page=" + pick
					+ "&rows=1";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			if (!reason.equals("Succes")) {
				text = "网好像不给力哦！";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
			
				for (int i = 0; i < array.length(); i++) {
					content = array.getJSONObject(i).getString("content");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		text = content;
		return text;
	}

}
