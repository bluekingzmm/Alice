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
public class TrainTime {
	private static String yw;// Ӳ��
	private static String yz;// Ӳ��
	private static String wz;// ����
	private static String rw;// ����

	private static String yd;
	private static String ed;

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
			String url = "http://api.jisuapi.com/train/ticket?appkey=38d79f3a989e19a3%20&start=%E4%B8%8A%E6%B5%B7&end=%E5%90%88%E8%82%A5&date=2017-06-02";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String msg = jo.getString("msg");
			json = jo.getString("result");
			String text;
			if (!msg.equals("ok")) {
				text = "��������ȷ��";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String trainno = array.getJSONObject(i).getString("trainno");// ����
					String type = array.getJSONObject(i).getString("type");// ����
					String station = array.getJSONObject(i).getString("station");// ����վ
					String endstation = array.getJSONObject(i).getString("endstation");// ����վ
					String departuretime = array.getJSONObject(i).getString("starttime");// ����ʱ��
					String arrivaltime = array.getJSONObject(i).getString("endtime");// ����ʱ��
					String costtime = array.getJSONObject(i).getString("costtime");// ��ʱ

					rw = array.getJSONObject(i).getString("rw");
					yz = array.getJSONObject(i).getString("yz");
					yw = array.getJSONObject(i).getString("yw");
					wz = array.getJSONObject(i).getString("wz");

					yd = array.getJSONObject(i).getString("yd");
					ed = array.getJSONObject(i).getString("ed");

					map.put("station", station);
					map.put("trainno", trainno);
					map.put("starttime", departuretime);
					map.put("endtime", arrivaltime);
					map.put("costtime", costtime);
					map.put("endstation", endstation);
					map.put("yz", yz);
					map.put("rw", rw);
					map.put("yw", yw);
					map.put("wz", wz);
					map.put("yd", yd);
					map.put("ed", ed);
					map.put("type", type);
					list.add(map);
				}
			}
			for (int j = 0; j < list.size(); j++) {
				System.out.println("ss"+j);
				Map<String, Object> wMap = list.get(j);
				sb.append("���ͣ�" + wMap.get("type") + ",����:" + wMap.get("trainno") + ",����վ:"
						+ wMap.get("station") + "����վ:" + wMap.get("endstation") + ",����ʱ��:" + wMap.get("starttime")
						+ ",����ʱ��:" + wMap.get("endtime") + "\n��ʱ:" + wMap.get("costtime") + ",Ӳ��:"
						+ wMap.get("yz") + ",һ����:" + wMap.get("yd") + ",������:" + wMap.get("ed") +",����:" + wMap.get("rw") + ",Ӳ��:" + wMap.get("yw")+ ",����:" + wMap.get("wz")+"\n");
			}
			text = sb.toString();
			System.out.println(text);
			System.out.println(text.length());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
