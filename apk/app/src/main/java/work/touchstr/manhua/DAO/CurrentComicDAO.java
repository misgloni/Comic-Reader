package work.touchstr.manhua.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeMap;

import work.touchstr.manhua.Model.Base.EpisodeInfo;

import static android.content.Context.MODE_PRIVATE;


//不同的className可以代表不同的网站,而不同的url/name可以代表存储的网站
public class CurrentComicDAO implements Serializable {
    static final String filePath = "aaa.txt";
    static CurrentComicDAO instance = null;

    public static CurrentComicDAO getInstance() {
        if(instance==null)
        {
            instance=new CurrentComicDAO();
        }
        return instance;
    }


    public void set(String comicClassName, String comicName, String episodeName, Context context) {
        SQLiteDatabase db = initSQL(context);

        ContentValues contentValues=new ContentValues();
        contentValues.put("episodeName",episodeName);

        //先尝试更新，防止数据重复
        int result_count=db.update("current_comic",contentValues,"comicClassName=? and comicName=?",new String[]{comicClassName,comicName});
        if(result_count!=0)//即更新成功
        {
            db.close();
            return;
        }

        //进入插入环节
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("comicClassName", comicClassName);
        contentValues2.put("comicName", comicName);
        contentValues2.put("episodeName", episodeName);
        db.insert("current_comic", null, contentValues2);


        db.close();
    }

    public String get(String comicClassName, String comicName, Context context) {
        SQLiteDatabase db = initSQL(context);
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query("current_comic", null, "comicClassName=? and comicName=?", new String[]{comicClassName,comicName}, null, null, null);
        cursor.move(0);
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndexOrThrow("episodeName");
            db.close();
            return cursor.getString(index);
        } else {
            db.close();
            return null;
        }
    }

    public SQLiteDatabase initSQL(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        Cursor cursor = db.query("sqlite_master", null, "name=?", new String[]{"current_comic"}, null, null, null);
        if (cursor.getCount() == 0) {
            db.execSQL("create table current_comic(id INTEGER primary key autoincrement,comicClassName text,comicName text,episodeName text)");
        }

        return db;
    }

}