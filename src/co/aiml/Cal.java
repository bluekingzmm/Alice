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
	 * ��ȡ��ǰ���������ڼ�<br>
	 *
	 * @param dt
	 * @return ��ǰ���������ڼ�
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		
		return weekDays[w];
	}
	public static void main(String[] args) {
		Date date = new Date();
		// �����Ǽ���
		int day = date.getDate();
		System.out.println("Today is :" + day + "��");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// ������������ڵĵڼ���
		int week = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(week-1);
		System.out.println("week:" + c.get(Calendar.DAY_OF_WEEK));
		// ��ǰ�µ����һ���Ǽ���
		int lastday = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("��������һ����:" + lastday + "��");
		
		String d=getWeekOfDate(date);
		System.out.println(d);
	}

}
