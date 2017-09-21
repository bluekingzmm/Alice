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
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Constellation extends TemplateElement {
	/*
	 * 星座查询
	 */
	private int index;
	private String resultcode;
	private String text;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Constellation(int index) {
		this.index = index;
	}

	public Constellation() {

	}

	public Constellation(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
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
		String url = "";
		StringBuilder sb = null;
		try {

			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			t = w.replace("+", "");
			List<String> lists = getResult(a, t);

			for (String s : lists) {
				t = s;
			}

			url = "http://api.avatardata.cn/Constellation/Query?key=1477836bce3f43458e185d134bba0554&consName=" + t
					+ "&type=today";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getString("result1");
			jo = new JSONObject(json);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", jo.getString("name").toString());// 星座名称
			map.put("datetime", jo.getString("datetime").toString());// 日期时间
			map.put("color", jo.getString("color").toString());// 幸运颜色
			map.put("QFriend", jo.getString("QFriend").toString());// 配偶星座
			map.put("summary", jo.getString("summary").toString());// 建议
			resultcode = jo.getString("resultcode").toString();
			list.add(map);
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> wMap = list.get(j);
				sb.append("你的星座：" + wMap.get("name") + ",\t日期:" + wMap.get("datetime") + ",\t幸运颜色:" + wMap.get("color")
						+ ",\t配偶星座:" + wMap.get("QFriend") + ",\t建议:" + wMap.get("summary") + "\n");
			}
			inputStream.close();
		} catch (Exception e) {

		}
		if (!resultcode.equals("200")) {
			text = "星座输入不正确哦！";
		} else {
			text = sb.toString();
		}
		return text;
	}

//	static String[] a = { "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座", "水瓶座", "双鱼座" };
	private String t;
	static String[] a = { "%E7%99%BD%E7%BE%8A%E5%BA%A7", "%E9%87%91%E7%89%9B%E5%BA%A7", "%E5%8F%8C%E5%AD%90%E5%BA%A7", "%E5%B7%A8%E8%9F%B9%E5%BA%A7", "%E7%8B%AE%E5%AD%90%E5%BA%A7", "%E5%A4%84%E5%A5%B3%E5%BA%A7", "%E5%A4%A9%E7%A7%A4%E5%BA%A7", "%E5%A4%A9%E8%9D%8E%E5%BA%A7", "%E5%B0%84%E6%89%8B%E5%BA%A7", "%E9%AD%94%E7%BE%AF%E5%BA%A7", "%E6%B0%B4%E7%93%B6%E5%BA%A7", "%E5%8F%8C%E9%B1%BC%E5%BA%A7" };

	/*
	 * 集合中完成模糊查询
	 */
	private static List<String> getResult(String[] arrays, String inputStr) {

		List<String> list = new ArrayList<String>();

		for (String s : arrays) {
			if (s.matches(".*" + inputStr + ".*")) {
				list.add(s);
			}
		}

		return list;
	}

}
