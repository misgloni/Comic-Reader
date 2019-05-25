package work.touchstr.manhua.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import work.touchstr.manhua.View.Task.SearchInit;
import work.touchstr.manhua.R;


public class SearchActivity extends Activity{
    


    ListView comicListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        comicListView =(ListView)SearchActivity.this.findViewById(R.id.comicList);
        String comicKey=getIntent().getStringExtra("comicKey");
        new SearchInit(comicListView,this).execute(comicKey);
    }


}
