package work.touchstr.manhua.Model.Base;

import java.util.LinkedList;

import work.touchstr.manhua.Model.Web.Html;

public abstract class WebSite
{
	String webSiteName;
	public String getWebSiteName()
	{
		return webSiteName;
	}
	public WebSite(String webSiteName)
	{
		this.webSiteName =webSiteName;
	}
	abstract public LinkedList<ComicInfo> search(String key);
	abstract public Comic getComic(ComicInfo comicInfo);
	public ComicInfo getComicInfo(String comicName,String comicUrl)
	{
		return new ComicInfo(this,comicName,new Html(comicUrl));
	}
}
