package tk.munditv.uvideos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import tk.munditv.uvideos.R;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private Context mContext;

    public DBHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DBHelper");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate()");
        String sql = "CREATE TABLE IF NOT EXISTS videos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "videoid VARCHAR(20) UNIQUE, "
                + "title VARCHAR(200), "
                + "catagoryid INTEGER, "
                + "groupid INTEGER, "
                + "descriptions TEXT, "
                + "thumbnailurl VARCHAR(200));";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS catagory ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(50) UNIQUE, "
                + "descriptions TEXT);";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS groups ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(50) UNIQUE, "
                + "descriptions TEXT);";
        sqLiteDatabase.execSQL(sql);
        ContentValues values = null;
/*
        values = new ContentValues();
        values.put("name", "chinese");
        values.put("descriptions", "Chinese");
        sqLiteDatabase.insert("groups", null, values);
        values = new ContentValues();
        values.put("name", "taiwanese");
        values.put("descriptions", "Taiwanese");
        sqLiteDatabase.insert("groups", null, values);
        values = new ContentValues();
        values.put("name", "cantonese");
        values.put("descriptions", "Cantonese");
        sqLiteDatabase.insert("groups", null, values);
        values = new ContentValues();
        values.put("name", "english");
        values.put("descriptions", "English");
        sqLiteDatabase.insert("groups", null, values);
        values = new ContentValues();
        values.put("name", "japanese");
        values.put("descriptions", "Japanese");
        sqLiteDatabase.insert("groups", null, values);
        values = new ContentValues();
        values.put("name", "korean");
        values.put("descriptions", "Korean");
        sqLiteDatabase.insert("groups", null, values);
*/
        values = new ContentValues();
        values.put("name", mContext.getString(R.string.title_videos));
        values.put("descriptions", mContext.getString(R.string.title_videos));
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", mContext.getString(R.string.title_musics));
        values.put("descriptions", mContext.getString(R.string.title_musics));
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", mContext.getString(R.string.title_news));
        values.put("descriptions", mContext.getString(R.string.title_news));
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", mContext.getString(R.string.title_live));
        values.put("descriptions", mContext.getString(R.string.title_live));
        sqLiteDatabase.insert("catagory", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade()");
        String sql = "DROP TABLE videos";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE catagory";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE groups";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
