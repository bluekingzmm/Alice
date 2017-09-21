package co.aiml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
	private String minTmp;
	private String maxTmp;
	private String hum; // now 湿锟斤拷
	private String tmp; // now 锟铰讹拷
	private String cond; // now cond txt 锟斤拷锟斤拷状态锟斤拷锟斤拷锟狡ｏ拷
	private String city;// basic 锟斤拷锟斤拷
	private String qlty; // aqi city 锟斤拷染锟教讹拷
	private String drsg;// 锟斤拷锟斤拷 txt
	private String trav; // 锟斤拷锟斤拷 trav txt
	private String uv; // 锟斤拷锟斤拷锟斤拷 uv txt
	private String flu; // 锟斤拷锟叫伙拷锟斤拷 flu txt
	private String sport; // 锟剿讹拷 txt
	private String day;

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getCond() {
		return cond;
	}

	public void setCond(String cond) {
		this.cond = cond;
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
		return "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷"+day+" [锟斤拷锟斤拷:" + city + "锟铰讹拷:" + tmp + ",湿锟斤拷:" + hum + ",锟斤拷锟斤拷:" + cond + ", 锟斤拷锟斤拷锟斤拷锟斤拷:" + qlty+ ", 锟斤拷锟斤拷露锟�:" + minTmp+ ", 锟斤拷锟斤拷露锟�:" + maxTmp + "\n 锟斤拷锟斤拷:" + drsg
				+ ", 锟斤拷锟斤拷:" + trav + "\n 锟斤拷锟斤拷锟斤拷强锟斤拷:" + uv + ", 锟斤拷锟叫硷拷锟斤拷:" + flu + ", 锟剿讹拷:" + sport + "]";
	}

	public static void main(String[] args) throws Exception {
		List<Weather> weaList = null;
		Weather wea = null;
		String w = URLEncoder.encode("锟斤拷锟斤拷", "UTF-8");
		try {
			String url = "https://way.jd.com/he/freeweather?city=" + w + "&appkey=c82b7e981b7fa077ac7ff39ebe3c206b";
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
			weaList = new ArrayList<Weather>();
			
			for (int i = 0; i < array.length(); i++) {
				wea = new Weather();
				JSONArray a=array.getJSONObject(i).getJSONArray("daily_forecast");	
				for (int j = 0; j < a.length(); j++) {
					wea.setDay(a.getJSONObject(0).get("date").toString());
					wea.setHum(a.getJSONObject(0).get("hum").toString());
					wea.setMinTmp(a.getJSONObject(0).getJSONObject("tmp").getString("min"));
					wea.setMaxTmp(a.getJSONObject(0).getJSONObject("tmp").getString("max"));
					wea.setCond(a.getJSONObject(0).getJSONObject("cond").get("txt_n").toString());		
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

			}

		} catch (Exception e) {

		}
		System.out.println(wea.toString());

	}

}
