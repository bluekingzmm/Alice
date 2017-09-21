package bitoflife.chatterbean.aiml;


import org.xml.sax.Attributes;


import bitoflife.chatterbean.Match;


public class P extends TemplateElement
{
  /*
  Constructors
  */

  public P(Attributes attributes)
  {
  }

  public P(Object... children)
  {
    super(children);
  }

  /*
  Methods
  */

  public String process(Match match)
  {
    return "";
  }
}