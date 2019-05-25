package work.touchstr.manhua.View.Task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import work.touchstr.manhua.View.Activity.ComicActivity;
import work.touchstr.manhua.View.Activity.EpisodeActivity;
import work.touchstr.manhua.View.CustomComponent.EpisodeButton;
import work.touchstr.manhua.Model.Base.Comic;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.EpisodeInfo;
import work.touchstr.manhua.View.CustomComponent.StringButton;

public class EpisodeListUpdate extends AsyncTask<ComicInfo,Integer,ArrayList<EpisodeInfo>> {
    ViewGroup buttonLayout;
    Context context;
    StringButton currentButton;
    Comic comic;
    public EpisodeListUpdate(ViewGroup buttonLayout, Context context, StringButton currentButton)
    {
        this.buttonLayout=buttonLayout;
        this.context=context;
        this.currentButton=currentButton;
    }
    @Override
    protected ArrayList<EpisodeInfo> doInBackground(ComicInfo... comicInfos) {
        ComicInfo comicInfo=comicInfos[0];
        comic=comicInfo.getComic();

        //反向配置comic,以加入一些其他的功能
        ComicActivity activity=(ComicActivity)context;
        activity.setComic(comic);

        ArrayList<EpisodeInfo> episodeInfoList= comic.getAllEpisodeInfo();
        return episodeInfoList;
    }

    @Override
    protected void onPostExecute(ArrayList<EpisodeInfo> episodeInfoList) {
        super.onPostExecute(episodeInfoList);

        //为按钮设置跳转功能
        View.OnClickListener listener=new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                EpisodeButton button=(EpisodeButton)view;
                Instance.episodeInfo=button.getEpisideInfo();
                Intent intent=new Intent(context,EpisodeActivity.class);

                //context.startActivity(intent);
                ((Activity)context).startActivityForResult(intent,1);
            }
        };


        //循环创建按钮并绑定listener
        for(EpisodeInfo info:episodeInfoList)
        {
            //在这里加载选择  按钮并且让按钮获得另外的属性
            EpisodeButton button=new EpisodeButton(context,null);
            button.setEpisodeInfo(info);
            button.setOnClickListener(listener);

            button.setText(info.getName());
            buttonLayout.addView(button);
            LinearLayout.LayoutParams buttonParams=(LinearLayout.LayoutParams)button.getLayoutParams();
            buttonParams.width=ViewGroup.LayoutParams.MATCH_PARENT;
            buttonParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            buttonParams.weight=1;
        }
        ((ComicActivity)context).updataCurrentEpisodeButton();

        //设置续看按钮效果
        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringButton button=(StringButton)view;
                Instance.episodeInfo=comic.getEpisodeInfoFromName(button.getString());
                Intent intent=new Intent(context,EpisodeActivity.class);

                //context.startActivity(intent);
                ((Activity)context).startActivityForResult(intent,1);
            }
        });

        //更新收藏按钮
        ((ComicActivity)context).updateCollectButton();


    }
}
