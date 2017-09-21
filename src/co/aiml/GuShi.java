package co.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class GuShi {
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
		  String text=null;
		try {		
			String url = "http://api.avatardata.cn/TangShiSongCi/Search?key=aac65830fc9e4d0baa45749aae042843&keyWord=%E7%A7%8B%E5%85%B4";
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
                text="��������ȷ��";
			}else{
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
				String 	name=array.getJSONObject(i).getString("name");
				System.out.println(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
