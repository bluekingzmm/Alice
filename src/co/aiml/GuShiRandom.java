package co.aiml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class GuShiRandom {


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
			String url = "http://api.avatardata.cn/TangShiSongCi/Random?key=aac65830fc9e4d0baa45749aae042843";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			json = jo.getString("result");
			jo = new JSONObject(json);
		
            System.out.println(reason);
			String text;
			String neirong = null;
			String zuozhe = null;
			String jieshao = null;
			String biaoti = null;
			if (!reason.equals("Succes")) {
				text = "��������ȷ��";
			} else {
				neirong = jo.getString("neirong");
				zuozhe = jo.getString("zuozhe");
				jieshao = jo.getString("jieshao");
				biaoti = jo.getString("biaoti");
				
			}
			text="��ʫ:" + biaoti + ",����:" + jieshao + ",\t����:" + zuozhe + "\t����:" + neirong;
			System.out.println(text);		
		} catch (Exception e) {
e.printStackTrace();
		}

	}

}
