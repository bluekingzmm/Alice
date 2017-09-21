package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Weather extends TemplateElement {
	/*
	 * 今天天气
	 */
	
	private int index;
	private String minTmp;
	private String maxTmp;
	private String hum; // now 湿度
	private String tmp; // now 温度
	private String cond1;
	private String cond2;
	// now cond txt 天气状态（多云）
	private String city;// basic 城市
	private String qlty; // aqi city 污染程度
	private String drsg;// 衣着 txt
	private String trav; // 旅行 trav txt
	private String uv; // 紫外线 uv txt
	private String flu; // 流感机率 flu txt
	private String sport; // 运动 txt
	private String date;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMinTmp() {
		return minTmp;
	}

	public void setMinTmp(String minTmp) {
		this.minTmp = minTmp;
	}

	public String getMaxTmp() {
		return maxTmp;
	}

	public void setMaxTmp(String maxTmp) {
		this.maxTmp = maxTmp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public String getCond1() {
		return cond1;
	}

	public void setCond2(String cond2) {
		this.cond2 = cond2;
	}
	public String getCond2() {
		return cond2;
	}

	public void setCond1(String cond1) {
		this.cond1 = cond1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getQlty() {
		return qlty;
	}

	public void setQlty(String qlty) {
		this.qlty = qlty;
	}

	public String getDrsg() {
		return drsg;
	}

	public void setDrsg(String drsg) {
		this.drsg = drsg;
	}

	public String getTrav() {
		return trav;
	}

	public void setTrav(String trav) {
		this.trav = trav;
	}

	public String getUv() {
		return uv;
	}

	public void setUv(String uv) {
		this.uv = uv;
	}

	public String getFlu() {
		return flu;
	}

	public void setFlu(String flu) {
		this.flu = flu;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public Weather(int index) {
		this.index = index;
	}

	public Weather() {

	}

	public Weather(Attributes attributes) {
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

	public String toString() {
		return "今天天气：" + date + " [城市:" + city + ",温度:" + tmp + ",湿度:" + hum + ",天气:" + cond1+"转"+cond2 + ", 空气质量:" + qlty
				+ ", 最低温度:" + minTmp + ", 最高温度:" + maxTmp + "\n 穿衣:" + drsg + ", 旅行:" + trav + "\n 紫外线强度:" + uv
				+ ", 流行疾病:" + flu + ", 运动:" + sport + "]";
	}


	public String process(Match match) {
		Weather wea = null;
		String url = "";
		try {

			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			url = "https://way.jd.com/he/freeweather?city=" + t + "&appkey=c82b7e981b7fa077ac7ff39ebe3c206b";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			json = jo.getString("result");
			jo = new JSONObject(json);
			json = jo.getString("HeWeather5");
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				wea = new Weather();
				JSONArray a = array.getJSONObject(i).getJSONArray("daily_forecast");
				for (int j = 0; j < a.length(); j++) {
					wea.setDate(a.getJSONObject(0).get("date").toString());
					wea.setHum(a.getJSONObject(0).get("hum").toString());
					wea.setMinTmp(a.getJSONObject(0).getJSONObject("tmp").getString("min"));
					wea.setMaxTmp(a.getJSONObject(0).getJSONObject("tmp").getString("max"));
					wea.setCond1(a.getJSONObject(0).getJSONObject("cond").get("txt_n").toString());
					wea.setCond2(a.getJSONObject(0).getJSONObject("cond").get("txt_d").toString());
				}
				wea.setTmp(array.getJSONObject(i).getJSONObject("now").getString("tmp"));

				wea.setCity(array.getJSONObject(i).getJSONObject("basic").get("city").toString());
				wea.setQlty(array.getJSONObject(i).getJSONObject("aqi").getJSONObject("city").get("qlty").toString());
				wea.setDrsg(
						array.getJSONObject(i).getJSONObject("suggestion").getJSONObject("drsg").get("txt").toString());
				wea.setFlu(
						array.getJSONObject(i).getJSONObject("suggestion").getJSONObject("flu").get("txt").toString());
				wea.setUv(array.getJSONObject(i).getJSONObject("suggestion").getJSONObject("uv").get("txt").toString());
				wea.setSport(array.getJSONObject(i).getJSONObject("suggestion").getJSONObject("sport").get("txt")
						.toString());
				wea.setTrav(array.getJSONObject(i).getJSONObject("suggestion").getJSONObject("trav").get("txt").toString());


			}
			inputStream.close();
		} catch (Exception e) {

		}
		return (wea.getCity() != null ? wea.toString().trim() : "不太明白哦！");
	}

}
