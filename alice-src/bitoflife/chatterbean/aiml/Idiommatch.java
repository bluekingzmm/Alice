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

public class Idiommatch extends TemplateElement {
	/*
	 * 输入关键字查找词语
	 */
	private int index;
	private String text;
	private String name;

	public Idiommatch() {
	}

	public Idiommatch(Attributes attributes) {
		String value = attributes.getValue(0);
		index = (value != null ? Integer.parseInt(value) : 1);
	}
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
		String url=null;
		List<String> sList=null;
		try {
			String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			String t = w.replace("+", "");
			 url = "http://api.avatardata.cn/ChengYu/Search?key=e357ed8d1627419e8eca05668524358f&keyWord="+t+"";
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
			    sList=new ArrayList<String>();
				for (int i = 0; i < array.length(); i++) {
					name=array.getJSONObject(i).getString("name");
					sList.add(name);
				}
				if(sList.size()==0){
					 text="无此词语！（退出查询成语模式）";	
				}else{
					text=sList.toString();

				}
					
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}



		return text;
	}

}
