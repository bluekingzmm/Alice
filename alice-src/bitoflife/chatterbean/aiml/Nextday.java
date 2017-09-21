package bitoflife.chatterbean.aiml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Nextday extends TemplateElement {
	/*
	 * 明天的时间
	 */
	public Nextday() {
	}

	public Nextday(Attributes attributes) {
	}

	public String process(Match match) {
		 Date date=new Date();//取时间
	      Calendar calendar = new GregorianCalendar();
	      calendar.setTime(date);
	      calendar.add(calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
	      date=calendar.getTime(); 
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd EEEE");
	     String dateString = formatter.format(date);
		return "明天是："+dateString;
	}

}
