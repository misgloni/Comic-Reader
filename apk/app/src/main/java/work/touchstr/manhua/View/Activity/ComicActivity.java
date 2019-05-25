package work.touchstr.manhua.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;


import work.touchstr.manhua.DAO.CurrentComicDAO;
import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.View.CustomComponent.CollectButton;
import work.touchstr.manhua.View.CustomComponent.StringButton;
import work.touchstr.manhua.View.Task.EpisodeListUpdate;
import work.touchstr.manhua.View.Task.Instance;
import work.touchstr.manhua.R;

public class ComicActivity extends Activity{

    ViewGroup buttonLayout;
    StringButton currentButton;
    CollectButton collectButton;
    ComicInfo comicInfo;
    Comic comic=null;
    public void setComic(Comic comic)
    {
        this.comic=comic;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Instance.comicInfo!=null)
        {
            comicInfo=Instance.comicInfo;
            Instance.comicInfo=null;
        }
        Log.d("ComicActivity","comicInfoName=-"+comicInfo.getComicName());
        setContentView(R.layout.comic_layout);
        buttonLayout=(ViewGroup)findViewById(R.id.buttonLayout);
        Log.d("ComicActivity","buttonLayout="+buttonLayout);
        currentButton=(StringButton)findViewById(R.id.currentButton);
        EpisodeListUpdate update=new EpisodeListUpdate(buttonLayout,this,currentButton);

        //触发上一个活动的返回效果
        setResult(RESULT_OK,new Intent());

        //初始化按钮
        collectButton=(CollectButton) findViewById(R.id.collectButton);

        update.execute(comicInfo);
    }

    //更新收藏按钮
    public void updateCollectButton()
    {
        if(comic==null)
        {
            Log.e("ComicActivity","更新错误");
            return;
        }
        collectButton.configurate(comic.getComicInfo().getWebsite().getClass().getName(),comic.getComicName(),comic.getComicInfo().getComicHtml().getUrl());
        collectButton.update();
    }

    //刷新续看按钮
    public void updataCurrentEpisodeButton()
    {
        String comic_name=CurrentComicDAO.getInstance().get(this.comic.getClass().getName(),this.comic.getComicName(),this);
        if(comic_name!=null)
        {
            currentButton.setString(comic_name);
            currentButton.setText(comic_name);
        }
    }

    //在返回后更新续看
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("ComicActivity","onActivityResult");
        updataCurrentEpisodeButton();
    }

}
