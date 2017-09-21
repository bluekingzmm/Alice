package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Traintomorrownext extends TemplateElement {
	/*
	 * 后天火车票余票
	 */
	private static String yw;// 硬卧
	private static String yz;// 硬座
	private static String wz;// 无座
	private static String rw;// 软卧

	private static String yd;
	private static String ed;

	private int index;
	private int indextwo;
	private ArrayList<Map<String, Object>> list;
	private String url;
	private String t;
	private String dateString;

	public int getIndextwo() {
		return indextwo;
	}

	public void setIndextwo(int indextwo) {
		this.indextwo = indextwo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Traintomorrownext(int index, int indextwo) {
		this.index = index;
		this.indextwo = indextwo;
	}

	public Traintomorrownext() {

	}

	public Traintomorrownext(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
		String value1 = attributes.getValue(1);
		indextwo = (value1 != null ? Integer.parseInt(value1) : 2);

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
		StringBuilder sb = null;
		String text = null;

		try {
			String w1 = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String w2 = URLEncoder.encode(match.wildcard(PATTERN, indextwo), "UTF-8");
			Date date = new Date();// 取时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 3);
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateString = formatter.format(date);
			String t1 = w1.replace("+", "");
			String t2 = w2.replace("+", "");
			
				sb = new StringBuilder();
				url = "http://api.jisuapi.com/train/ticket?appkey=38d79f3a989e19a3%20&start=" + t1 + "&end=" + t2 + "&date="
						+ dateString + "";
				URL url1 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
				connection.setConnectTimeout(2000);
				connection.setReadTimeout(2000);
				InputStream inputStream = connection.getInputStream();
				String json = streamToString(inputStream);
				JSONObject jo = new JSONObject(json);
				String msg = jo.getString("msg");
				json = jo.getString("result");

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
				sb.append("类型：" + list.get(0).get("type") + ",车次:" + list.get(0).get("trainno") + ",出发站:" + list.get(0).get("station")
				+ "到达站:" + list.get(0).get("endstation") + ",出发时间:" + list.get(0).get("starttime") + ",到达时间:"
				+ list.get(0).get("endtime") + "\n用时:" + list.get(0).get("costtime") + ",硬座:" + list.get(0).get("yz") + ",一等座:"
				+ list.get(0).get("yd") + ",二等座:" + list.get(0).get("ed") + ",软卧:" + list.get(0).get("rw") + ",硬卧:" + list.get(0).get("yw")
				+ ",无座:" + list.get(0).get("wz") + "\n\n");
				
				sb.append("类型：" + list.get(1).get("type") + ",车次:" + list.get(1).get("trainno") + ",出发站:" + list.get(1).get("station")
						+ "到达站:" + list.get(1).get("endstation") + ",出发时间:" + list.get(1).get("starttime") + ",到达时间:"
						+ list.get(1).get("endtime") + "\n用时:" + list.get(1).get("costtime") + ",硬座:" + list.get(1).get("yz") + ",一等座:"
						+ list.get(1).get("yd") + ",二等座:" + list.get(1).get("ed") + ",软卧:" + list.get(1).get("rw") + ",硬卧:" + list.get(1).get("yw")
						+ ",无座:" + list.get(1).get("wz") + "\n\n");
			
				sb.append("请进入官网看看：http://trains.ctrip.com/TrainBooking/SearchTrain.aspx###");
				text = sb.toString();
			
			
		} catch (Exception e) {
			return "可能出现了异常哦，请进入官网看看：http://trains.ctrip.com/TrainBooking/SearchTrain.aspx###";
		}
		return dateString +text;
	}

}
