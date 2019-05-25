package work.touchstr.manhua.Model.FuManHua;

import android.util.Log;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.WebSite;
import work.touchstr.manhua.Model.Web.ConnectionException;
import work.touchstr.manhua.Model.Web.Html;
import work.touchstr.manhua.Model.Web.HtmlConnection;

public class FuManHuaWebSite extends WebSite {
    public FuManHuaWebSite() {
        super("腐漫画");
    }

    @Override
    public LinkedList<ComicInfo> search(String key) {
        LinkedList<ComicInfo> list=new LinkedList<>();
        String url ="http://mh.fmhua.com/search.asp?key=";
        byte[] bytes=null;
        try
        {
            url+=URLEncoder.encode(key,"GB2312");
        }catch (Exception e)
        {
            Log.d("FuManHuaWebSite","转换失败");
        }
        Log.d("FuManHuaWebSite","url:"+url);
        Html searchHtml=new Html(url);
        String regex="<dt><a href=\"([^\\s]+?)\" title=\"([^\\s]+?)\">([^\\s]+?)</a></dt>";
        Pattern p=Pattern.compile(regex);
        String content="";
        HtmlConnection connection=searchHtml.connect();
        connection.setEncode("GB2312");//设置返回编码
        try
        {
            content=connection.getContent();
        } catch (ConnectionException e) {
            Log.d("FuManHuaWebSite","异常出错:无法连接至 搜索 服务器,错误: "+ e.getError());
            return list;
        }
        Log.d("FuManHuaWebSite",content);
        Matcher m=p.matcher(content);
        while(m.find())
        {
            String comicUrl="http://mh.fmhua.com"+m.group(1);
            String comicName=m.group(3);
            list.add(new ComicInfo(this,comicName,new Html(comicUrl)));
        }
        return list;
    }

    @Override
    public Comic getComic(ComicInfo comicInfo) {
        return new FuManHuaComic(comicInfo);
    }
}
