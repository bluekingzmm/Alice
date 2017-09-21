package bitoflife.chatterbean.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Idiomrandom extends TemplateElement {
	private String name;
	private String pinying;
	private String content;
	private String derivation;

	public Idiomrandom() {
	}

	public Idiomrandom(Attributes attributes) {
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
			String url = "http://api.avatardata.cn/ChengYu/Random?key=e357ed8d1627419e8eca05668524358f";
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
			if(derivation==null||derivation.equals("")){
				derivation="无";
			}
		} catch (Exception e) {

		}

		return "成语:" + name + ",拼音:" + pinying + ",\t内容:" + content + "\t来自:" + derivation;
	}

}
