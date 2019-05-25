package work.touchstr.manhua.Model.Base;
import java.io.Serializable;

import work.touchstr.manhua.Model.Web.Html;

public class ComicInfo implements Serializable {
	WebSite website;
	String name;
	Html url;
	public ComicInfo(WebSite website,String comicName,Html comicUrl)
	{
		this.website=website;
		this.name=comicName;
		this.url=comicUrl;
	}
	public WebSite getWebsite()
	{
		return website;
	}
	public String getComicName()
	{
		return name;
	}
	public Html getComicHtml()
	{
		return url;
	}
	public String getWebSiteName()
	{
		return website.getWebSiteName();
	}
	public Comic getComic()
	{
		return website.getComic(this);
	}
}
