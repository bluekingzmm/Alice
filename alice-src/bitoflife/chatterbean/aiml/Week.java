package bitoflife.chatterbean.aiml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Week extends TemplateElement {
	public Week() {
	}

	public Week(Attributes attributes) {
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		
		return weekDays[w];
	}

	public String process(Match match) {
		Date date = new Date();
		String d=getWeekOfDate(date);
		
		return "今天是"+d;
	}

}
