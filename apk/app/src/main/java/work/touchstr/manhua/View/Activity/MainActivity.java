package work.touchstr.manhua.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.lang.reflect.Method;

import work.touchstr.manhua.DAO.ComicCollectDAO;
import work.touchstr.manhua.DAO.ComicCollectData;
import work.touchstr.manhua.DAO.CurrentComicDAO;
import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.Model.Base.WebsiteManager;
import work.touchstr.manhua.R;
import work.touchstr.manhua.View.Adapter.CollectComicAdapter;
import work.touchstr.manhua.View.Adapter.ComicInfoAdapter;
import work.touchstr.manhua.View.Task.Instance;

public class MainActivity extends Activity {
    EditText searchEditText;
    Button searchButton;
    ListView listView;
    protected void initiate()
    {
        updataCollectListView();
        Button searchButton=(Button)findViewById(R.id.searchButton);
        searchEditText=(EditText)findViewById(R.id.searchEditText);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);

                String comicKey=searchEditText.getText().toString();
                intent.putExtra("comicKey",comicKey);
                startActivityForResult(intent,1);
            }
        });

        //配置收藏界面的点击触发效果
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ComicCollectData data=(ComicCollectData)adapterView.getAdapter().getItem(i);
                ComicInfo comicInfo=WebsiteManager.getInstance().getComicInfoFromWebSiteClass(data.getWebSiteClassName(),data.getComicName(),data.getComicUrl());
                Instance.comicInfo=comicInfo;
                Intent intent =new Intent(MainActivity.this,ComicActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("MainActivity","onActivityResult");
        updataCollectListView();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton=(Button)findViewById(R.id.searchButton);
        searchEditText=(EditText)findViewById(R.id.searchEditText);
        listView=(ListView)findViewById(R.id.collectListView);
        initiate();
    }

    public void updataCollectListView()
    {
        listView.setAdapter(new CollectComicAdapter(this,ComicCollectDAO.getInstance().get(this)));
    }
}
