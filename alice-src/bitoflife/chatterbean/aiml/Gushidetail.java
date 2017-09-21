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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Gushidetail extends TemplateElement {
	/*
	 * 古诗详情输入古诗名称
	 */
	private String id;
	private String biaoti;
	private String jieshao;
	private String zuozhe;
	private String neirong;
	private String reason;
	private int index;
	private String url;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Gushidetail() {
	}

	public Gushidetail(Attributes attributes) {
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
		String text=null;
		try {
			String id=Id(match);
			url = " http://api.avatardata.cn/TangShiSongCi/LookUp?key=aac65830fc9e4d0baa45749aae042843&id="+id+"";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			reason = jo.getString("reason");
			json = jo.getString("result");
			jo = new JSONObject(json);
			if (!reason.equals("Succes")) {
				text = "参数不正确！";
			} else {
				neirong = jo.getString("neirong");
				zuozhe = jo.getString("zuozhe");
				jieshao = jo.getString("jieshao");
				biaoti = jo.getString("biaoti");
				
			}
			text="古诗:" + biaoti + ",介绍:" + jieshao + ",\t作者:" + zuozhe + "\t内容:" + neirong;
					
		} catch (Exception e) {

		}

		
		return text;
	}

	public String Id(Match match) throws Exception {
		String text = null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			String url = " http://api.avatardata.cn/TangShiSongCi/Search?key=aac65830fc9e4d0baa45749aae042843&keyWord=" + t
					+ "";
			URL url1 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			String json = streamToString(inputStream);
			JSONObject jo = new JSONObject(json);
			String reason = jo.getString("reason");
			if (!reason.equals("Succes")) {
                text="参数错误";
			}else{
				json = jo.getString("result");
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					id=array.getJSONObject(i).getString("id");
				}
				if(id==null||id.equals("")){
					 text="无此古诗!";	
				}else{
					text=id;

				}
					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;

	}

}
