package work.touchstr.manhua.Model.Base;

import android.util.Log;

import work.touchstr.manhua.Model.Web.Html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

abstract public class Comic
{
	ArrayList<EpisodeInfo> list=new ArrayList<EpisodeInfo>();
	ComicInfo comicInfo;
	public Comic(ComicInfo comicInfo)
	{
		this.comicInfo=comicInfo;
	}


	public ComicInfo getComicInfo()
	{
		return comicInfo;
	}
	public String getComicName()
	{
		return comicInfo.getComicName();
	}
	public String getWebSiteName()
	{
		return comicInfo.getWebSiteName();
	}
	public Html getComicHtml()
	{
		return comicInfo.getComicHtml();
	}
	public ArrayList<EpisodeInfo> getAllEpisodeInfo()
	{
		return list;
	}
	public void setAllEpisodeInfo(Collection<EpisodeInfo> infos)
	{
		this.list=new ArrayList<EpisodeInfo>(infos);
	}
	public EpisodeInfo getEpisodeInfoFromName(String episodeName)
	{
		for(int i=0;i<list.size();i++)
		{
			Log.d(list.get(i).getName(),episodeName);
			if(list.get(i).getName().equals(episodeName))
			{
				return list.get(i);
			}
		}
		return null;
	}
	public EpisodeInfo getEpisodeInfoFrowId(int id)
	{
		if(id<list.size()&&id>=0)
		{
			return list.get(id);
		}
		else
		{
			return null;
		}
	}
	public int getEpisodeId(EpisodeInfo info)
	{
		for(int i=0;i<list.size();i++)
		{
			if(info==list.get(i))
			{
				return i;
			}
		}
		return -1;
	}
	abstract public Episode getEpisode(EpisodeInfo episodeInfo);
}
