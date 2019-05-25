package work.touchstr.manhua.Model.FZDM;

import android.util.Log;

import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.Episode;
import work.touchstr.manhua.Model.Base.EpisodeInfo;
import work.touchstr.manhua.Model.Web.ConnectionException;
import work.touchstr.manhua.Model.Web.Html;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FZDMComic extends Comic {

	public FZDMComic(ComicInfo comicInfo) {
		super(comicInfo);
		Html html = getComicHtml();
		Log.d("FZDMComic", html.getUrl());
		String content = "";
		try {
			content = html.connect().getContent();
		} catch (ConnectionException e) {
			Log.d("FZDMComic", "异常出错:无法连接至 搜索 服务器,错误: " + e.getError());
		}
		setInfos(content);
	}

	//对章节信息进行初始化
	public void setInfos(String content) {
		LinkedList<EpisodeInfo> list = new LinkedList<EpisodeInfo>();
		String str = "<li class=\"pure-u-1-2 pure-u-lg-1-4\"><a href=\"(.+?)\" title=\"(.+?)\">";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(content);
		while (m.find()) {
			Html html = new Html(this.getComicHtml().getUrl() + m.group(1));
			EpisodeInfo info = new EpisodeInfo(this, m.group(2), html);
			list.add(info);
		}
		setAllEpisodeInfo(list);
	}

	//这里同时负责创新新的Episode,同时传入下一个EpisodeInfo,以实现翻下一漫画的效果
	@Override
	public Episode getEpisode(EpisodeInfo episodeInfo) {
		return new FZDMEpisode(episodeInfo);
	}


}
