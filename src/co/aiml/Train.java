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
public class Train {
	private static String priceyz;
	private static String priceyd;
	private static String priceed;

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
			String url = "http://api.jisuapi.com/train/station2s?appkey=38d79f3a989e19a3%20&start=%E6%BB%81%E5%B7%9E&end=%E4%B8%8A%E6%B5%B7&ishigh=0";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(6000);
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
					String departuretime = array.getJSONObject(i).getString("departuretime");// 出发时间
					String arrivaltime = array.getJSONObject(i).getString("arrivaltime");// 到达时间
					String costtime = array.getJSONObject(i).getString("costtime");// 用时
					if(type.equals("快速")||type.equals("普快")){
						priceyz = array.getJSONObject(i).getString("priceyz");
					}else{
						priceyd = array.getJSONObject(i).getString("priceyd");
						priceed = array.getJSONObject(i).getString("priceed");
					}
					map.put("station", station);
					map.put("trainno", trainno);
					map.put("departuretime", departuretime);
					map.put("arrivaltime", arrivaltime);
					map.put("costtime", costtime);
					map.put("endstation", endstation);
					map.put("priceyz", priceyz);
					map.put("priceyd", priceyd);
					map.put("priceed", priceed);
					map.put("type", type);
					list.add(map);
				}
			}
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> wMap = list.get(j);
				sb.append((j+1)+".类型：" + wMap.get("type") + ",车次:" + wMap.get("trainno") + ",出发站:" + wMap.get("station")
						+ "到达站:" + wMap.get("endstation") + ",出发时间:" + wMap.get("departuretime") + ",到达时间:"
						+ wMap.get("arrivaltime") + "\n用时:" + wMap.get("costtime") + ",硬座:" + wMap.get("priceyz")
						+ ",一等座:" + wMap.get("priceyd") + ",二等座:" + wMap.get("priceed")+"\n\n");
			}
			text = sb.toString();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
