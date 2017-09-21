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

public class Gushirandom extends TemplateElement {
	/*
	 * 古诗欣赏（随机）
	 */
	private String biaoti;
	private String jieshao;
	private String zuozhe;
	private String neirong;
	private String text;
	private String url;
	private String reason;
	private JSONObject jo;

	public Gushirandom() {
	}

	public Gushirandom(Attributes attributes) {
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
			url = "http://api.avatardata.cn/TangShiSongCi/Random?key=aac65830fc9e4d0baa45749aae042843";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			jo = new JSONObject(json);
			reason = jo.getString("reason");
			json = jo.getString("result");
			jo = new JSONObject(json);
			if (!reason.equals("Succes")) {
				text = "参数不正确！";
			} else {
				neirong = jo.getString("neirong");
				zuozhe = jo.getString("zuozhe");
				jieshao = jo.getString("jieshao");
				biaoti = jo.getString("biaoti");
				
			}
					
		} catch (Exception e) {

		}
		text="古诗:" + biaoti + ",介绍:" + jieshao + ",\t作者:" + zuozhe + "\t内容:" + neirong;

		return text;
	}

}
