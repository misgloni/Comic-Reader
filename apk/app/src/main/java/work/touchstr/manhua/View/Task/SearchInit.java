package work.touchstr.manhua.View.Task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import work.touchstr.manhua.Model.Base.WebsiteManager;
import work.touchstr.manhua.View.Activity.ComicActivity;
import work.touchstr.manhua.View.Adapter.ComicInfoAdapter;
import work.touchstr.manhua.Model.Base.ComicInfo;

public class SearchInit extends AsyncTask<String,Integer,ArrayList<ComicInfo>> {

    //需要在结束的时候赋值
    ListView listView;
    ArrayList<ComicInfo> comicInfoList;
    Context context;
    public SearchInit(ListView listView, Context context)
    {
        super();
        this.listView=listView;
        this.context=context;
    }
    @Override
    protected ArrayList<ComicInfo> doInBackground(String... strings) {
        String comicKey=strings[0];
        ArrayList<ComicInfo> comicInfoList=new ArrayList<ComicInfo>(WebsiteManager.getInstance().search(comicKey));
        return comicInfoList;
    }

    @Override
    protected void onPostExecute(ArrayList<ComicInfo> comicInfos) {
        super.onPostExecute(comicInfos);
        comicInfoList=comicInfos;
        //设置adapter
        ComicInfoAdapter comicInfoAdapter=new ComicInfoAdapter(context,comicInfoList);
        listView.setAdapter(comicInfoAdapter);

        //注册listener
        AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Instance.comicInfo=comicInfoList.get(position);
                Intent intent = new Intent(context, ComicActivity.class);
                context.startActivity(intent);
            }
        };
        listView.setOnItemClickListener(listener);
    }
}
