package co.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Constellaction {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
//	 public static void find(){
//		 List list = Arrays.asList(a);
//		 for (int i = 0; i < list.size(); i++) {
//			  
//		System.out.println(list.get(i));	
//		}
//		
//	 }
	static  String []a={"锟斤拷锟斤拷锟斤拷","锟斤拷牛锟斤拷","双锟斤拷锟斤拷","锟斤拷蟹锟斤拷","狮锟斤拷锟斤拷","锟斤拷女锟斤拷","锟斤拷锟斤拷锟�","锟斤拷蝎锟斤拷","锟斤拷锟斤拷锟斤拷","魔锟斤拷锟斤拷","水瓶锟斤拷","双锟斤拷锟斤拷"};

	  private static List<String> getResult(String[] arrays, String inputStr) {
		  
	        List<String> list = new ArrayList<String>();
	 
	        for (String s : arrays) {
	            if (s.matches(".*" + inputStr + ".*")) {
	                list.add(s);
	            }
	        }
	 
	        return list;
	    }
//	 public static void find() {
//			Constellaction c = new Constellaction();
//			c.setName("锟斤拷锟斤拷锟斤拷");
//			c.setName("锟斤拷牛锟斤拷");
//			c.setName("双锟斤拷锟斤拷");
//			c.setName("锟斤拷蟹锟斤拷");
//			c.setName("狮锟斤拷锟斤拷");
//			c.setName("锟斤拷女锟斤拷");
//			c.setName("锟斤拷锟斤拷锟�");
//			c.setName("锟斤拷蝎锟斤拷");
//			c.setName("锟斤拷锟斤拷锟斤拷");
//			c.setName("魔锟斤拷锟斤拷");
//			c.setName("水瓶锟斤拷");
//			c.setName("双锟斤拷锟斤拷");
//			List<Constellaction> cList=new ArrayList<Constellaction>();
//			cList.add(c);
//			for (int i = 0; i < cList.size(); i++) {
//				System.out.println(cList.get(i).getName());
//			}
//			
//
//		}
//	ArrayList<Student> sList = new ArrayList<Student>();
//	Iterator<Student> iterable = DataBase.getsList().iterator();
//	while (iterable.hasNext()) {
//		Student stu =  iterable.next();
//		switch (key) {
//		case 1:
//			if (stu.getName().indexOf(keyname) == 0) {
//				sList.add(stu);
//			}
//
//			break;
//		case 2:
//			if (stu.getNum().indexOf(keyname) == 0) {
//				sList.add(stu);
//			}
//			break;
//
//		default:
//			
//			break;
//		}
//
//	}
	
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String url = "";
//		StringBuilder sb = null;
//		try {
//
//			url = "http://api.avatardata.cn/Constellation/Query?key=1477836bce3f43458e185d134bba0554&consName=%E7%8B%AE%E5%AD%90%E5%BA%A7&type=today";
//			URL url1 = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
//			connection.setConnectTimeout(3000);
//			connection.setReadTimeout(3000);
//			InputStream inputStream = connection.getInputStream();
//			String json = streamToString(inputStream);
//			JSONObject jo = new JSONObject(json);
//			json = jo.getString("result1");
//			jo = new JSONObject(json);
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			sb = new StringBuilder();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("name", jo.getString("name").toString());// 锟斤拷锟斤拷锟斤拷锟斤拷
//			map.put("datetime", jo.getString("datetime").toString());// 锟斤拷锟斤拷时锟斤拷
//			map.put("color", jo.getString("color").toString());// 锟斤拷锟斤拷锟斤拷色
//			map.put("QFriend", jo.getString("QFriend").toString());// 锟斤拷偶锟斤拷锟斤拷
//			map.put("summary", jo.getString("summary").toString());// 锟斤拷锟斤拷
//			list.add(map);
//			  for(int j=0;j<list.size();j++){
//		           Map<String,Object> wMap = list.get(j);
//		           sb.append("锟斤拷锟斤拷锟斤拷锟斤拷锟�"+wMap.get("name")+"\t锟斤拷锟斤拷:"+wMap.get("datetime")+"\t锟斤拷锟斤拷锟斤拷色:"+wMap.get("color")+"\t锟斤拷偶锟斤拷锟斤拷:"
//		                   +wMap.get("QFriend")+"\t锟斤拷锟斤拷:"+wMap.get("summary")+"\n");
//		       }
//			inputStream.close();
//		} catch (Exception e) {
//
//		}
//	       System.out.println(sb.toString());
		 System.out.println("锟斤拷锟斤拷锟斤拷锟窖拷锟斤拷荩锟�");
	        Scanner sc = new Scanner(System.in);
	        String inputStr = sc.nextLine();
	 
	        List<String> list = getResult(a, inputStr);
	 
	        System.out.println("" + '\n' + "锟斤拷询锟斤拷锟斤拷锟�");
	 
	        if (list.isEmpty()) {
	            System.out.println("没锟叫诧拷询锟斤拷锟斤拷应锟侥斤拷锟斤拷锟�");
	        } else {
	            for (String s : list) {
	                System.out.println(s);
	            }
	        }

	}

}
