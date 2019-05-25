package work.touchstr.manhua.Model.Base;

import android.util.Log;

import work.touchstr.manhua.Model.Web.Html;

abstract public class Episode
{
	EpisodeInfo episodeInfo;

	boolean isTail=false;
	public boolean isTail()
	{
		return isTail;
	}
	protected void setIsTail()
	{
		isTail=true;
	}
	public Episode(EpisodeInfo episodeInfo)
	{
		this.episodeInfo=episodeInfo;
	}
	public EpisodeInfo getEpisodeInfo()
	{
		return episodeInfo;
	}
	public Html getEpisodeHtml()
	{
		return episodeInfo.getHtml();
	}
	public String getEpisodeName()
	{
		return episodeInfo.getName();
	}
	public String getComicName()
	{
		return episodeInfo.getComic().getComicName();
	}
	//public abstract boolean next() throws Exception;
	public abstract String next() throws Exception;

	public EpisodeInfo getNextEpisodeInfo()
	{
		EpisodeInfo info=null;
		int id=getEpisodeInfo().getComic().getEpisodeId(episodeInfo);
		Log.d("Episode",id+"");
		if(id!=-1)
		{
			info=getEpisodeInfo().getComic().getEpisodeInfoFrowId(id-1);
		}
		return info;
	}
//	public abstract boolean last() throws Exception;
//	public abstract String getNextImageUrl() throws Exception;
//	public abstract String getLastImageUrl() throws Exception;
//	public abstract String getImageUrl() throws Exception;
}
