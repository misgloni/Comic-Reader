package work.touchstr.manhua.DAO;

public class ComicCollectData {
    String webSiteClassName;
    String comicName;
    String comicUrl;
    public ComicCollectData(String webSiteClassName,String comicName,String comicUrl)
    {
        this.webSiteClassName=webSiteClassName;
        this.comicName=comicName;
        this.comicUrl=comicUrl;
    }

    public String getComicName() {
        return comicName;
    }

    public String getComicUrl() {
        return comicUrl;
    }

    public String getWebSiteClassName() {
        return webSiteClassName;
    }
}
