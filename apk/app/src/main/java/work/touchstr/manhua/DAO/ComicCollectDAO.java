package work.touchstr.manhua.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;


public class ComicCollectDAO {

    private static ComicCollectDAO instance=null;
    public static ComicCollectDAO getInstance()
    {
        if(instance==null)
        {
            instance=new ComicCollectDAO();
        }
        return instance;
    }
    public SQLiteDatabase initSQL(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor cursor = db.query("sqlite_master", null, "name=?", new String[]{"comic_collect"}, null, null, null);
        if (cursor.getCount() == 0) {
            db.execSQL("create table comic_collect(id INTEGER primary key autoincrement,webSiteClassName text,comicName text,comicUrl text)");
        }
        return db;
    }
    public ArrayList<ComicCollectData> get(Context context)
    {
        SQLiteDatabase db=initSQL(context);
        Cursor cursor=db.query("comic_collect",null,null,null,null,null,null,null);
        LinkedList<ComicCollectData> list=new LinkedList<>();
        if(cursor.moveToFirst()==false)
        {
            new ArrayList<>(list);
        }
        for(int i=0;i<cursor.getCount();i++)
        {
            cursor.move(i);
            String webSiteClassName= cursor.getString(cursor.getColumnIndexOrThrow("webSiteClassName"));
            String comicName= cursor.getString(cursor.getColumnIndexOrThrow("comicName"));
            String comicUrl= cursor.getString(cursor.getColumnIndexOrThrow("comicUrl"));
            ComicCollectData data=new ComicCollectData(webSiteClassName,comicName,comicUrl);
            list.add(data);
        }
        db.close();
        return new ArrayList<>(list);
    }
    public boolean isCollect(String webSiteClassName,String comicName,Context context)
    {
        Log.d("isCollect",webSiteClassName+comicName);
        SQLiteDatabase db=initSQL(context);
        Cursor cursor=db.query("comic_collect",null,"webSiteClassName=? and comicName =?",new String[]{webSiteClassName,comicName},null,null,null,null);
        if(cursor.getCount()==0)
        {
            db.close();
            return false;
        }
        else
        {
            db.close();
            return true;
        }
    }
    public void add(String webSiteClassName,String comicName,String comicUrl,Context context)
    {
        Log.d("add",webSiteClassName+comicName+comicUrl);
        ContentValues cv=new ContentValues();
        cv.put("webSiteClassName",webSiteClassName);
        cv.put("comicName",comicName);
        cv.put("comicUrl",comicUrl);
        SQLiteDatabase db=initSQL(context);
        db.insert("comic_collect",null,cv);
        db.close();
    }
    public void delete(String webSiteClassName,String comicName,Context context)
    {
        Log.d("delete",webSiteClassName+comicName);
        SQLiteDatabase db=initSQL(context);
        db.delete("comic_collect","webSiteClassName = ? and comicName = ?",new String[]{webSiteClassName,comicName});

        db.close();
    }
}
