package co.aiml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Perimetersearch {

	private static String text;
	private static List<Map<String, Object>> list;

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
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			String url = "http://api.avatardata.cn/Near/Search?key=0b11cdbfc5e44f0ca84f0fe741195fed&keyWord=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&location=116.305145,39.982368&cityName=%E5%8C%97%E4%BA%AC&radius=3000m&number=0&page=1&coord_type=bd09ll&out_coord_type=bd09ll";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			if (!reason.equals("Succes")) {
				text = "��������ȷ��";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				jo=new JSONObject(array);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String name = array.getJSONObject(i).getString("name");
					String cityName = array.getJSONObject(i).getString("cityName");
					String address = array.getJSONObject(i).getString("address");
					String type = array.getJSONObject(i).getString("type");
					String additionalInformation = array.getJSONObject(i).getJSONObject("additionalInformation").getString("tag");
					map.put("additionalInformation", additionalInformation);
					map.put("name", name);
					map.put("cityName", cityName);
					map.put("address", address);
					map.put("type", type);
					list.add(map);
				}
				
			}
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> wMap = list.get(j);
				sb.append("��������" + wMap.get("cityName") + "\t�ط���" + wMap.get("name")+ "\t��ַ��" + wMap.get("address")+ "\t���ͣ�" + wMap.get("type")+ "\t��־��" + wMap.get("additionalInformation"));
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
