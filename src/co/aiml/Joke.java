package co.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Joke {

	private static String content;
	private static String updateTime;
	private static String text;

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
		try {
			Random random = new Random();
			int pick = random.nextInt(10000) + 1;
			String url = "http://api.avatardata.cn/Joke/NewstJoke?key=f35a8191729e4f37af4b85b4981a8c1e&page=" + pick
					+ "&rows=1";
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
				text = "ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½È·ï¿½ï¿½";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					content = array.getJSONObject(i).getString("content");
					updateTime = array.getJSONObject(i).getString("updatetime");
				}
				text = content + "\n¸üÐÂÊ±¼ä" + updateTime;
				System.out.println(text);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
