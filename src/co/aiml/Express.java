/**
 * 
 */
package co.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class Express {
	private static String url;
	private static JSONArray array;
	private int index;


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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, Object>> list = null;
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			url = "http://api.avatardata.cn/ExpressNumber/Company?key=4b0abb0b0fe4428d9e9ca835c837a1f8";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(6000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String msg = jo.getString("reason");
			json = jo.getString("result");
			String text;
			if (!msg.equals("Succes")) {
				text = "参数不正确！";
			} else {
				json = jo.getString("result");
				array = new JSONArray(json);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String name = array.getJSONObject(i).getString("name");
					String tel = array.getJSONObject(i).getString("tel");
					String web = array.getJSONObject(i).getString("web");
					map.put("web", web);
					map.put("tel", tel);
					map.put("name", name);
					list.add(map);
				}
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> wMap = list.get(j);
					sb.append("快递名称:" + wMap.get("name") + ",电话:" + wMap.get("tel") + ",网址:" + wMap.get("web")
							+ "\n");
				}
			}
			
			text = sb.toString();
			System.out.println(array.toString());
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
