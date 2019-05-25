package work.touchstr.manhua.Model.FuManHua;

import android.provider.DocumentsContract;
import android.util.Log;
import android.webkit.WebView;




import java.net.URL;

import work.touchstr.manhua.Model.Base.Episode;
import work.touchstr.manhua.Model.Base.EpisodeInfo;
import work.touchstr.manhua.Model.Web.ConnectionException;
import work.touchstr.manhua.Model.Web.Html;
import work.touchstr.manhua.Model.Web.HtmlConnection;


//由于该网站搜索图片到最后一张后,会跳到第一张,所以,只要对比下一页是否和第一页的图片相同 就可以确定是否已经是最后一页
public class FuManHuaEpisode extends Episode {
    int p = 0;
    String baseStr = "";
    String firstImageUrl;
    public FuManHuaEpisode(EpisodeInfo episodeInfo) {
        super(episodeInfo);
        baseStr = episodeInfo.getHtml().getUrl();
        Log.d("a",baseStr);
    }

    @Override
    public String next() throws Exception {
        if (isTail()) {
            return null;
        }
        p++;
        String url = baseStr + "?p=" + p+"&f=http://comic.sfacg.com/";
        Log.d(p+"p是",url);
        HtmlConnection htmlConnection = new Html(url).connect();
        htmlConnection.setEncode("GB2312");
        String content = "";
        try {
            content = htmlConnection.getContent();
            System.out.println(content);
        } catch (Exception e) {
            return null;
        }
        try {
            String imageUrl = getImageUrl(htmlConnection.getGetUrl());
            //非空则判断是否第一页的图片,若为空只需要设置第一页就行
            if(firstImageUrl!=null)
            {
                if(imageUrl.equals(firstImageUrl))
                {
                    setIsTail();
                    return null;
                }
            }
            else if (firstImageUrl == null)
            {
                firstImageUrl=imageUrl;
            }
            return imageUrl;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public String getImageUrl(String url)throws Exception
    {
            return "";
    }

}
