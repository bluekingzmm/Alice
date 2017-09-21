package bitoflife.chatterbean.aiml;

import static bitoflife.chatterbean.Match.Section.PATTERN;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.Match;

public class Cs extends TemplateElement {
	private int index;
	private String t;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Cs() {
	}

	public Cs(int index) {
		this.index = index;
	}

	public Cs(Attributes attributes) {
		String value1 = attributes.getValue(0);
		index = (value1 != null ? Integer.parseInt(value1) : 1);

	}

	public int hashCode() {
		return 131072;
	}

	public String process(Match match) {
		String w;
		try {
			w = URLEncoder.encode(match.wildcard(PATTERN, index), "UTF-8");
			t = w.replace("+", "");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "我在测试自定义标签，哈哈。"+t;
	}

}
