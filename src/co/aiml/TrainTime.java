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
	private static String yw;// 硬卧
	private static String yz;// 硬座
	private static String wz;// 无座
	private static String rw;// 软卧

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
				text = "参数不正确！";
			} else {
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					String trainno = array.getJSONObject(i).getString("trainno");// 车次
					String type = array.getJSONObject(i).getString("type");// 类型
					String station = array.getJSONObject(i).getString("station");// 出发站
					String endstation = array.getJSONObject(i).getString("endstation");// 到达站
					String departuretime = array.getJSONObject(i).getString("starttime");// 出发时间
					String arrivaltime = array.getJSONObject(i).getString("endtime");// 到达时间
					String costtime = array.getJSONObject(i).getString("costtime");// 用时

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
				sb.append("类型：" + wMap.get("type") + ",车次:" + wMap.get("trainno") + ",出发站:"
						+ wMap.get("station") + "到达站:" + wMap.get("endstation") + ",出发时间:" + wMap.get("starttime")
						+ ",到达时间:" + wMap.get("endtime") + "\n用时:" + wMap.get("costtime") + ",硬座:"
						+ wMap.get("yz") + ",一等座:" + wMap.get("yd") + ",二等座:" + wMap.get("ed") +",软卧:" + wMap.get("rw") + ",硬卧:" + wMap.get("yw")+ ",无座:" + wMap.get("wz")+"\n");
			}
			text = sb.toString();
			System.out.println(text);
			System.out.println(text.length());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
