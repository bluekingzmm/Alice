package bitoflife.chatterbean.aiml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Preday extends TemplateElement {
	/*
	 * 大前天的时间
	 */
	public Preday() {
	}

	public Preday(Attributes attributes) {
	}


	public String process(Match match) {
		 Date date=new Date();//取时间
	      Calendar calendar = new GregorianCalendar();
	      calendar.setTime(date);
	      calendar.add(calendar.DATE,-2);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
	      date=calendar.getTime(); 
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd EEEE");
	     String dateString = formatter.format(date);
		return "大前天是："+dateString;
	}

}
