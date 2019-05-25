package work.touchstr.manhua.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;

import work.touchstr.manhua.DAO.CurrentComicDAO;
import work.touchstr.manhua.Model.Base.Episode;
import work.touchstr.manhua.Model.Base.EpisodeInfo;
import work.touchstr.manhua.View.Task.Instance;
import work.touchstr.manhua.R;

class EpisodePagerAdapter extends PagerAdapter {
    LinkedList<View> viewList = new LinkedList<>();
    public void addView(View view) {
        viewList.add(view);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        // TODO Auto-generated method stub
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
}
//负责显示漫画
public class  EpisodeActivity extends Activity {
    EpisodeInfo episodeInfo;
    Episode episode;
    Handler handler;
    ViewPager pageView;
    EpisodePagerAdapter adapter;
    Bundle savedInstanceState;
    ArrayList<View> viewList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.episode_layout);
        pageView= (ViewPager) findViewById(R.id.pageView);
        adapter=new EpisodePagerAdapter();
        pageView.setAdapter(adapter);
        episodeInfo=Instance.episodeInfo;
        Instance.episodeInfo=null;
        Log.d("EpisodePagerAdapter","episodeInfo="+episodeInfo);
        Log.d("EpisodePagerAdapter","onCreate<-initEpisode");
        setResult(RESULT_OK,new Intent());

        //初始化
        initEpisode();

        //保存续看
        String class_name=this.episodeInfo.getComic().getClass().getName();
        String comic_name=this.episodeInfo.getComic().getComicName();
        CurrentComicDAO.getInstance().set(class_name,comic_name,episodeInfo.getName(),this);
    }
    public void initEpisode()
    {
        new AsyncTask<EpisodeInfo,Integer,Episode>()
        {
            @Override
            protected Episode doInBackground(EpisodeInfo... episodeInfos) {
                return episodeInfos[0].getEpisode();
            }

            @Override
            protected void onPostExecute(Episode episode) {
                super.onPostExecute(episode);
                EpisodeActivity.this.episode=episode;
                EpisodeActivity.this.initialize();
            }
        }.execute(episodeInfo);
    }

    //在这里设置已经好了
    public void initialize()
    {
        Log.d("EpisodeActivity","initialize");
        setNextImageView();
        setNextImageView();
        pageView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int lastPage=0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("EpisodeActivity","onPageSelected"+position);
                if(position>lastPage)//翻了下一页
                {
                    try
                    {
                        //最后一页时,加载下一页
                        if(adapter.getCount()-1==position)
                        {
                            setNextImageView();
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
                lastPage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setNextImageView()
    {
        new AsyncTask<Integer,Integer,String>()
        {

            @Override
            protected String doInBackground(Integer... integers) {
                //在这里获得当前图片的下一张图片
                try
                {
                    return episode.next();
                }
                catch (Exception e)
                {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String nextImageUrl) {
                super.onPostExecute(nextImageUrl);
                if(nextImageUrl==null)
                {
                    setEndView();
                }
                else
                {
                    setNewImageView(nextImageUrl);
                }
            }
        }.execute(0);

    }
    public void setNewImageView(String url)
    {
        ImageView imageView =new ImageView(this);
        Glide.with(this).load(url).into(imageView);
        adapter.addView(imageView);
    }

    //保证只会设置一个页面
    boolean hasEndView=false;
    public void setEndView()
    {
        EpisodeInfo info=episode.getNextEpisodeInfo();
        if(hasEndView)
        {
            return;
        }
        if(info==null)
        {
            //没有下一页
            adapter.addView(View.inflate(EpisodeActivity.this,R.layout.dont_has_next_episode_layout,null));
        }
        else
        {
            //有下一页
            Instance.episodeInfo=info;
            View view=View.inflate(EpisodeActivity.this,R.layout.has_next_episode_layout,null);
            Button button=(Button)view.findViewById(R.id.nextButton);
            Log.d("EpisodeActivity","nextButton->"+button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("EpisodeActivity","Instance.episodeInfo");
                    Intent intent=new Intent(EpisodeActivity.this,EpisodeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            adapter.addView(view);
        }
        hasEndView=true;

    }


}
