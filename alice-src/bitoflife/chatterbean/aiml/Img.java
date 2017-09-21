package bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.Match;

public class Img extends TemplateElement {
	private String name;

	/*
	 * Constructors
	 */

	public Img(Attributes attributes) {
		String value = attributes.getValue(0);
		if (value == null)
			return;
		name = value;
	}

	public Img(String name) {
		this.name = name;
	}

	/*
	 * Methods
	 */

	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		Img compared = (Img) obj;
		if (!name.equals(compared.name))
			return false;
		return super.equals(compared);
	}

	public String process(Match match) {

		String output = super.process(match);
		if (match != null)
			output = "<img src=\"" + name + "\"/>" + output;
		return output;

	}
}