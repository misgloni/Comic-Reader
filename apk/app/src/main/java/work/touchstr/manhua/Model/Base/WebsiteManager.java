package work.touchstr.manhua.Model.Base;

import java.util.LinkedList;

import work.touchstr.manhua.Model.FZDM.FZDMWebSite;
import work.touchstr.manhua.Model.FuManHua.FuManHuaWebSite;

public class WebsiteManager {
    private static WebsiteManager instance;
    public static WebsiteManager getInstance()
    {
        if(instance==null)
        {
            instance=new WebsiteManager();
        }
        return instance;
    }
    LinkedList<WebSite> webSiteLinkedList=new LinkedList<>();
    private WebsiteManager()
    {
        webSiteLinkedList.add(new FZDMWebSite());
    }
    public LinkedList<ComicInfo> search(String key)
    {
        LinkedList<ComicInfo> list=new LinkedList<>();
        for(WebSite webSite:webSiteLinkedList)
        {
            list.addAll(webSite.search(key));
        }
        return list;
    }
    public ComicInfo getComicInfoFromWebSiteClass(String className,String comicName,String comicUrl)
    {
        for(WebSite website:webSiteLinkedList)
        {
            if(website.getClass().getName().equals(className))
            {
                return website.getComicInfo(comicName,comicUrl);
            }
        }
        return null;
    }
}
