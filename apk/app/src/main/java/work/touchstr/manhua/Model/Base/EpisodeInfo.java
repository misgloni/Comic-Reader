package work.touchstr.manhua.Model.Base;

import java.io.Serializable;

import work.touchstr.manhua.Model.Web.Html;

public class EpisodeInfo implements Serializable
{
	Comic comic;
	String name;
	Html html;

	public EpisodeInfo(Comic comic, String episodeName, Html html)
	{
		this.comic=comic;
		this.name=episodeName;
		this.html=html;
	}
	public String getName()
	{
		return name;
	}
	public Comic getComic()
	{
		return comic;
	}
	public Html getHtml()
	{
		return html;
	}
	public void setHtml(Html html)
	{
		this.html=html;

	}
	public Episode getEpisode()
	{
		return comic.getEpisode(this);
	}
}
