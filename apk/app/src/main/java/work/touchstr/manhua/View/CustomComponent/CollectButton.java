package work.touchstr.manhua.View.CustomComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import work.touchstr.manhua.DAO.ComicCollectDAO;

public class CollectButton extends android.support.v7.widget.AppCompatButton {
    boolean isCollect=false;
    String webSiteClassName="";
    String comicName="";
    String comicUrl="";
    public void setIsCollect(boolean collect) {

        isCollect = collect;
        if(isCollect)
        {
            setText("取消收藏");
        }
        else
            setText("收藏");
    }

    public boolean isCollect() {
        return isCollect;
    }
    public void configurate(String webSiteClassName,String comicName,String comicUrl)
    {
        this.comicName=comicName;
        this.webSiteClassName=webSiteClassName;
        this.comicUrl=comicUrl;
    }
    public void update()
    {
        if(ComicCollectDAO.getInstance().isCollect(webSiteClassName,comicName,getContext()))
        {
            setIsCollect(true);
        }
        else
        {
            setIsCollect(false);
        }
    }
    public CollectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectButton button=(CollectButton)view;
                if(isCollect())
                {

                    //若已经收藏，则取消收藏
                    Log.d("CollectButton","取消收藏");
                    ComicCollectDAO.getInstance().delete(webSiteClassName,comicName,getContext());
                    setIsCollect(false);
                }
                else
                {
                    //反之，添加收藏
                    Log.d("CollectButton","添加收藏");
                    ComicCollectDAO.getInstance().add(webSiteClassName,comicName,comicUrl,getContext());
                    setIsCollect(true);
                }
            }
        });
    }
}
