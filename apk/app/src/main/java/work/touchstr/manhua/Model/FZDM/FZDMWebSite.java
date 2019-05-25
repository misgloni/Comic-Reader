package work.touchstr.manhua.Model.FZDM;

import android.util.Log;

import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.WebSite;
import work.touchstr.manhua.Model.Web.ConnectionException;
import work.touchstr.manhua.Model.Web.Html;
import work.touchstr.manhua.Model.Web.HtmlConnection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FZDMWebSite extends WebSite
{
	public FZDMWebSite()
	{
		super("风之动漫");
	}

	//必须返回一个漫画信息数组
	@Override
	public LinkedList<ComicInfo> search(String key)
	{
		String baseStr="https://manhua.fzdm.com/";
		LinkedList<ComicInfo> comicInfoArr=new LinkedList<ComicInfo>();

		//在这里实现搜索效果
		String regex="<div class=\"round\"><li><a href=\"([0-9]+?)/\" title=\"(.+?)\"><img src=\"(.+?)\" alt=\"(.+?)\"></a></li><li><a href=\"([0-9]+?)/\" title=\"(.+?)\">(.+?)</a></li></div>";
		Pattern p=Pattern.compile(regex);
		HtmlConnection htmlConnection=new Html(baseStr).connect();

		String content="";
		Log.e("search",content);
		//如果出现异常,增加log,并直接返回一个空的LinkedList
		try
		{
			content=htmlConnection.getContent();
		} catch (ConnectionException e) {
			Log.d("FZDMWebSite","异常出错:无法连接至 搜索 服务器,错误: "+ e.getError());
			return comicInfoArr;
		}
		Matcher m=p.matcher(content);
		while(m.find())
		{
			String url=baseStr+m.group(1)+"//";
			String name=m.group(7);
			Pattern tempPattern=Pattern.compile("(.+)?"+key+"(.+)?");
			Matcher tempMatcher=tempPattern.matcher(name);
			if(tempMatcher.find())
			{
				Log.d("FZDMWebSite","\nurl:"+url+"\n"+"name:"+name);
				comicInfoArr.add(new ComicInfo(this,name,new Html(url)));
			}
		}
		return comicInfoArr;
	}

	@Override
	public Comic getComic(ComicInfo comicInfo)
	{
		return new FZDMComic(comicInfo);
	}

}
