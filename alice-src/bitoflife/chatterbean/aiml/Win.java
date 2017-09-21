package bitoflife.chatterbean.aiml;

import bitoflife.chatterbean.Match;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static bitoflife.chatterbean.Match.Section.PATTERN;

public class Win extends TemplateElement {
    /*
     * 输入关键字查找词语
     */
    private int index;
    private String text;
    private String name;

    public Win() {
    }

    public Win(Attributes attributes) {
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
        String url = null;
        List<String> sList = null;
        String id;
        String type; //文档类型:文档、视频、图片、其它
        String name; //上传文件名
        String extName;  //原始文件扩展名
        String convertName;  //上传文件改名
        String path ;   //文件路径
        String pdfPath ;
        String preImgPath; //元数据
        String fileSize; //文件大小
        String dateCreated; //文件创建的时间
        String userId;   //用户Id
        String ownerId;  //所有者Id(记录该文件的上一级)
        String common;//常用

        try {
            String w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
            String t = w.replace("+", "");
            url = "http://localhost:8080/cbdq/resourceInfo/findContent?search=" + t + "";
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            InputStream inputStream = connection.getInputStream();
            String json = streamToString(inputStream);
            JSONObject jo = new JSONObject(json);
            json = jo.getJSONObject("result").getString("ResourceInfoList");
            if(json=="error"){
                text = "用户未登录!";

            }else{
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    name = array.getJSONObject(i).getString("name");
                    type = array.getJSONObject(i).getString("type");
                    extName = array.getJSONObject(i).getString("extName");
                    path = array.getJSONObject(i).getString("path");
                    id = array.getJSONObject(i).getString("id");

                }
            }

            if (sList.size() == 0) {
                text = "查询无结果!";
            } else {
                text ="<a onclick='showImg('${resourceInfoInstance.id}')' href='#'>${raw(resourceInfoInstance.name)}.${resourceInfoInstance.extName}</a>";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return text;
    }

}
