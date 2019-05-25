package work.touchstr.manhua.Model.Web;

import java.net.CookieManager;

public class Html
{
	public enum Type{Post,Get};
	String url;
	Type type;
	String[] variableArr=null;
	public Type getType()
	{
		return type;
	}
	public String getUrl()
	{
		return url;
	}
	public Html(String url)
	{
		this(url,Type.Get);
	}
	public Html(String url,Type type)
	{
		this.url=url;
		this.type=type;
	}
	public Html(String url,Type type,String... variableArr)
	{
		this(url,type);
		this.variableArr=variableArr;
	}
	public HtmlConnection connect()
	{
		if(variableArr!=null)
		{
			return new HtmlConnection(url,type,variableArr);
		}
		else
		{
			return new HtmlConnection(url,type);
		}
	}
}
