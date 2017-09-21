package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Traintime extends TemplateElement {
	/*
	 * 输入时间火车票余票
	 */
	private static String yw;// 硬卧
	private static String yz;// 硬座
	private static String wz;// 无座
	private static String rw;// 软卧

	private static String yd;
	private static String ed;

	private int index;
	private int indextwo;
	private ArrayList<Map<String, Object>> list=null;
	private String text;
	private String url;
	private int indexthree;
	private String t;
	private JSONObject jo;
	private JSONArray array;
	private String trainno;
	private String type;
	private String station;
	private String endstation;
	private String starttime;
	private String endtime;
	private String costtime;

	public int getIndexthree() {
		return indexthree;
	}

	public void setIndexthree(int indexthree) {
		this.indexthree = indexthree;
	}

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

	public Traintime(int index, int indextwo, int indexthree) {
		this.index = index;
		this.indextwo = indextwo;
		this.indexthree = indexthree;
	}

	public Traintime() {

	}

	public Traintime(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
		String value1 = attributes.getValue(1);
		indextwo = (value1 != null ? Integer.parseInt(value1) : 2);

		String value2 = attributes.getValue(2);
		indexthree = (value2 != null ? Integer.parseInt(value2) : 3);
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
		StringBuilder sb1 = null;

		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String w1 = URLEncoder.encode(match.wildcard(PATTERN, indextwo), "UTF-8");
			String w2 = URLEncoder.encode(match.wildcard(PATTERN, indexthree), "UTF-8");
			t = w.replace("+", "");
			String t1 = w1.replace("+", "");
			String t2 = w2.replace("+", "");
		
				sb = new StringBuilder();
				sb1 = new StringBuilder();

				url = "http://api.jisuapi.com/train/ticket?appkey=38d79f3a989e19a3%20&start=" + t1 + "&end=" + t2 + "&date="
						+ t + "";
				URL url1 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
				connection.setConnectTimeout(2000);
				connection.setReadTimeout(2000);
				InputStream inputStream = connection.getInputStream();
				String json = streamToString(inputStream);
				jo = new JSONObject(json);
				String msg = jo.getString("msg");
				json = jo.getString("result");
				if (!msg.equals("ok")) {
					text = "参数不正确！";
				} else {
					json = jo.getString("result");
					array = new JSONArray(json);
					list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = null;
					for (int i = 0; i < array.length(); i++) {
						map = new HashMap<String, Object>();
						trainno = array.getJSONObject(i).getString("trainno");
						type = array.getJSONObject(i).getString("type");
						station = array.getJSONObject(i).getString("station");
						endstation = array.getJSONObject(i).getString("endstation");
						starttime = array.getJSONObject(i).getString("starttime");
						endtime = array.getJSONObject(i).getString("endtime");
						costtime = array.getJSONObject(i).getString("costtime");

						rw = array.getJSONObject(i).getString("rw");
						yz = array.getJSONObject(i).getString("yz");
						yw = array.getJSONObject(i).getString("yw");
						wz = array.getJSONObject(i).getString("wz");

						yd = array.getJSONObject(i).getString("yd");
						ed = array.getJSONObject(i).getString("ed");

						map.put("station", station);
						map.put("trainno", trainno);
						map.put("starttime", starttime);
						map.put("endtime", endtime);
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
				Map<String, Object> wMap =null;
					for (int j = 0; j < list.size(); j++) {
					 wMap = list.get(j);
							sb.append("类型：" + wMap.get("type") + ",车次:" + wMap.get("trainno") + ",出发站:" + wMap.get("station")
							+ "到达站:" + wMap.get("endstation") + ",出发时间:" + wMap.get("starttime") + ",到达时间:"
							+ wMap.get("endtime") + "\n用时:" + wMap.get("costtime") + ",硬座:" + wMap.get("yz") + ",一等座:"
							+ wMap.get("yd") + ",二等座:" + wMap.get("ed") + ",软卧:" + wMap.get("rw") + ",硬卧:" + wMap.get("yw")
							+ ",无座:" + wMap.get("wz") + "\n\n");
						}	
				
				text = sb.toString();
		
			
		} catch (Exception e) {
			return "可能出现了异常哦，请进入官网看看：http://trains.ctrip.com/TrainBooking/SearchTrain.aspx###";
		
		}
		return t+":"+text;
	}

}
