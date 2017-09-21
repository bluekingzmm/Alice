package co.aiml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Cal {

	public static int dayForWeek(String pTime) throws Throwable {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date tmpDate = format.parse(pTime);

		Calendar cal = new GregorianCalendar();

		cal.set(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDay());

		return cal.get(Calendar.DAY_OF_WEEK);
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
	public static void main(String[] args) {
		Date date = new Date();
		// 今天是几号
		int day = date.getDate();
		System.out.println("Today is :" + day + "号");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 今天是这个星期的第几天
		int week = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(week-1);
		System.out.println("week:" + c.get(Calendar.DAY_OF_WEEK));
		// 当前月的最后一天是几号
		int lastday = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("这个月最后一天是:" + lastday + "号");
		
		String d=getWeekOfDate(date);
		System.out.println(d);
	}

}
