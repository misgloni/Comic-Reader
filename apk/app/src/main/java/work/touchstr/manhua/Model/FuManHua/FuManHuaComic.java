package work.touchstr.manhua.Model.FuManHua;

import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.Episode;
import work.touchstr.manhua.Model.Base.EpisodeInfo;
import work.touchstr.manhua.Model.Web.ConnectionException;
import work.touchstr.manhua.Model.Web.Html;
import work.touchstr.manhua.Model.Web.HtmlConnection;

public class FuManHuaComic extends Comic {
    String content="";
    public FuManHuaComic(ComicInfo comicInfo) {
        super(comicInfo);
        HtmlConnection connection=comicInfo.getComicHtml().connect();
        connection.setEncode("GB2312");
        try
        {
            content=connection.getContent();
        } catch (ConnectionException e) {
            Log.e("FuManHuaComic","异常出错:无法连接至 搜索 服务器,错误: "+ e.getError());
        }
        setInfos();
    }

    public void setInfos()
    {
        String baseStr="http://mh.fmhua.com";
        LinkedList<EpisodeInfo> list=new  LinkedList<EpisodeInfo>();
        //"<li  class=\"new\"><a href=\"/manhua/11057/302986.html\" title=\"第156话\" target=\"_blank\">第156话<img src=\"../../template/skin4_20110501/images/s4/n.gif\" /></a></li>"
        String regex1="<li\\s+?class=\"new\"><a href=\"([^\\s]+?)\" title=\"([^\\s]+?)\" target=\"_blank\">([^\\s]+?)<img src=\"../../template/skin4_20110501/images/s4/n.gif\" /></a></li>";
        String regex2="<li ><a href=\"([^\\s]+?)\" title=\"([^\\s]+?)\" target=\"_blank\"([^\\s]+?)</a></li>";
        Pattern p1=Pattern.compile(regex1);
        Pattern p2=Pattern.compile(regex2);
        Matcher m=p1.matcher(content);
        if(m.find())
        {
            list.add(new EpisodeInfo(this,m.group(2),new Html(baseStr+m.group(1))));
        }
        m=p2.matcher(content);
        while(m.find())
        {
            for(int i=0;i<m.groupCount();i++)
            {
                list.add(new EpisodeInfo(this,m.group(2),new Html(baseStr+m.group(1))));
            }
        }
        setAllEpisodeInfo(list);
    }


    @Override
    public Episode getEpisode(EpisodeInfo episodeInfo) {
        return new FuManHuaEpisode(episodeInfo);
    }
}
