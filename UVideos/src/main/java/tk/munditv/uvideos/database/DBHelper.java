package tk.munditv.uvideos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public DBHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS videos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "videoid VARCHAR(20), "
                + "title VARCHAR(200), "
                + "catagoryid INTEGER, "
                + "groupid INTEGER, "
                + "descriptions TEXT);";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS catagory ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(50), "
                + "desctiptions TEXT);";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS group ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(50), "
                + "desctiptions TEXT);";
        sqLiteDatabase.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put("name", "videos");
        values.put("descriptions", "Videos");
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", "musics");
        values.put("descriptions", "Musics");
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", "news");
        values.put("descriptions", "News");
        sqLiteDatabase.insert("catagory", null, values);
        values = new ContentValues();
        values.put("name", "live");
        values.put("descriptions", "Live");
        sqLiteDatabase.insert("catagory", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE videos";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE catagory";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE group";
        sqLiteDatabase.execSQL(sql);
    }
}
