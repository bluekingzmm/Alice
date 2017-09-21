package bitoflife.chatterbean.aiml;

import java.util.Calendar;
import java.util.Date;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Day extends TemplateElement {
	/*
	 * 当前月的最后一天是几号
	 */
	public Day() {
	}

	public Day(Attributes attributes) {
	}

	public String process(Match match) {
		Date date = new Date();
		// 今天是几号
		int day = date.getDate();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// 当前月的最后一天是几号
		int lastday = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		return "这个月最后一天是:" + lastday + "号";
	}

}
