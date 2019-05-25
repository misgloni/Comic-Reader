package work.touchstr.manhua.Model.FZDM;

import android.util.Log;

import work.touchstr.manhua.Model.Base.Episode;
import work.touchstr.manhua.Model.Base.EpisodeInfo;


import work.touchstr.manhua.Model.Web.Html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FZDMEpisode extends Episode
{
	String root="";
	Html currentUrl;
	public FZDMEpisode(EpisodeInfo info)
	{
		super(info);
		Html thisHtml=info.getHtml();
		root=thisHtml.getUrl();
	}

	//每一次调用都会向下翻一页,并且返回下一页的图片,如果currentUrl为null，则返回根链接，代表进入第一页如果已经是结尾了,则会返回null,如果是无法获得图片,则返回Exception
	public String next() throws Exception
	{
		if(isTail())
	{
		return null;
	}
		if(currentUrl ==null)
		{
			currentUrl =new Html(root);
		}else
		{
			try{
				currentUrl =getNextUrl();}
			catch (Exception e){setIsTail();return null;}
		}

		try{return getImageUrl();}
		catch (Exception e)
		{
			throw e;
		}
	}
	//获取下一个页面的URL链接
    public Html getNextUrl() throws Exception
    {

		String regex="<a href=\"([^\\s]+?)\" class=\"pure-button pure-button-primary\">下一页</a>";
		Pattern p=Pattern.compile(regex);
		String content=currentUrl.connect().getContent();
		Matcher m=p.matcher(content);
		if(m.find()) {
			String nextUrl = root + m.group(1);
			return new Html(nextUrl);
		}
		else
		{
			Log.d("FZDMEpisode","没有下一页");
			throw new Exception(this.getClass()+"没有下一页");
		}
    }

    //通过当前页获得url文本
	public String getImageUrl() throws Exception
	{
		if(currentUrl ==null)
		{
			return null;
		}
		else
		{
			try
			{
				String imageUrl= getImageUrl(currentUrl.connect().getContent());
				Log.d("FZDMEpisode","imageUrl="+imageUrl);
				return imageUrl;
			}catch (Exception e)
			{
				throw e;
			}
		}
	}

	//通过html文本获得imageUrl
    public String getImageUrl(String content) throws Exception
    {
    	String baseStr="http://p0.xiaoshidi.net/";
    	String regex="var mhurl=\"(.+?)\"";
    	Pattern p=Pattern.compile(regex);
    	Matcher m=p.matcher(content);
    	if(m.find())
    	{
    		baseStr+=m.group(1);
    		return baseStr;
    	}
    	else
		{
			Log.e("FZDMEpisode","获取图片失败");
			throw new java.lang.Exception(this.getClass()+":获取该页图片失败");
		}
    }



}
