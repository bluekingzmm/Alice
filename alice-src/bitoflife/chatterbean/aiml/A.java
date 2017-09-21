package bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.Match;

public class A extends TemplateElement {
	  /*
	  Attributes
	  */
	  
	  private String name;
	  
	  /*
	  Constructors
	  */

	  public A(Attributes attributes)
	  {
	    name = attributes.getValue(0);
	  }
	  
	  public A(String name, Object... children)
	  {
	    super(children);
	    this.name = name;
	  }
	  
	  /*
	  Methods
	  */
	  
	  public boolean equals(Object obj)
	  {
	    if (obj == null) return false;
	    A compared = (A) obj;
	    if (!name.equals(compared.name)) return false;
	    return super.equals(compared);
	  }
	    
	  public String process(Match match)
	  {
	    String output = super.process(match);
	    if (match != null)
	      output = "<a href=\"" + name + "\">" + output + "</a>";
	    else
	    {
	      AliceBot bot = match.getCallback();
	      Context context = (bot != null ? bot.getContext() : null);
	      if (context != null) context.property("predicate." + name, output);
	    }
	    
	    return output;
	  }

}