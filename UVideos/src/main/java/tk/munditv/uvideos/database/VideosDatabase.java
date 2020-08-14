package tk.munditv.uvideos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VideosDatabase {

    private static final String TAG = "VideosDatabase";
    private static final String DBName= "uvideos.db";
    private static final int Version = 1;

    public static final int GROUP_TABLE = 1;
    public static final int CATAGORY_TABLE = 2;
    public static final int VIDEOS_TABLE = 3;

    private static DBHelper mDH;
    private static SQLiteDatabase mDB;

    public static void initialize(Context mContext) {
        mDH = new DBHelper(mContext, DBName, null, Version);
        mDB = mDH.getWritableDatabase();
        return;
    }

    public static void execSQL(String sql) {
        mDB.execSQL(sql);
    }

    public static void insertGroupData(@NonNull GroupTable groupTable) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", groupTable.getName());
        values.put("descriptions", groupTable.getDescriptions());
        mDB.insert("groups", null, values);
    }

    public static void insertCatagoryData(@NonNull CatagoryTable catagoryTable) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", catagoryTable.getName());
        values.put("descriptions", catagoryTable.getDescriptions());
        mDB.insert("catagory", null, values);
    }

    public static void insertVideoData(@NonNull VideosTable videosTable) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("videoid", videosTable.getVideoid());
        values.put("title", videosTable.getTitle());
        values.put("catagoryid", videosTable.getCatagoryId());
        values.put("groupid", videosTable.getGroupid());
        values.put("descriptions", videosTable.getDescriptions());
        mDB.insert("videos", null, values);
    }

    public static void deleteGroupData(@NonNull String name) {
        mDB = mDH.getWritableDatabase();
        String condition = "name=" + name;
        mDB.delete("groups", condition, null);
    }

    public static void deleteCatagoryData(@NonNull String name) {
        mDB = mDH.getWritableDatabase();
        String condition = "name=" + name;
        mDB.delete("catagory", condition, null);
    }

    public static void deleteVideoData(@NonNull String videoid) {
        mDB = mDH.getWritableDatabase();
        String condition = "videoid=" + videoid;
        mDB.delete("videos", condition, null);
    }

    @Nullable
    public static ArrayList<GroupTable> queryGroupTable(@Nullable String condition) {
        mDB = mDH.getReadableDatabase();
        ArrayList<GroupTable> groupTables = new ArrayList<GroupTable>();
        String[] columns = {"id", "name", "descriptions"};
        Cursor cursor = mDB.query("groups", columns, condition,
                null,null, null, null);
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        do {
            GroupTable groupTable = new GroupTable(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2));
            groupTables.add(groupTable);
        } while(cursor.moveToNext());
        return groupTables;
    }

    @Nullable
    public static ArrayList<CatagoryTable> queryCatagoryTable(@Nullable String condition) {
        mDB = mDH.getReadableDatabase();
        ArrayList<CatagoryTable> catagoryTables = new ArrayList<CatagoryTable>();
        String[] columns = {"id", "name", "descriptions"};
        Cursor cursor = mDB.query("catagory", columns, condition,
                null,null, null, null);
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        do {
            CatagoryTable catagoryTable = new CatagoryTable(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2));
            catagoryTables.add(catagoryTable);
        } while(cursor.moveToNext());
        return catagoryTables;
    }

    @Nullable
    public static ArrayList<VideosTable> queryVideosTable(@Nullable String condition) {
        mDB = mDH.getReadableDatabase();
        ArrayList<VideosTable> videosTables = new ArrayList<VideosTable>();
        String[] columns = {"id", "videoid", "title", "catagoryid", "groupid", "descriptions"};
        Cursor cursor = mDB.query("videos", columns, condition,
                null, null, null, null);
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        do {
            VideosTable videosTable =new VideosTable(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
                    cursor.getString(5));
            videosTables.add(videosTable);
        } while (cursor.moveToNext());
        return videosTables;
    }

    public static void updateGroupData(@NonNull GroupTable groupTable, @NonNull String condition) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", groupTable.getName());
        values.put("descriptions", groupTable.getDescriptions());
        mDB.update("groups", values, condition, null);
    }

    public static void updateCatagoryData(@NonNull CatagoryTable catagoryTable, @NonNull String condition) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", catagoryTable.getName());
        values.put("descriptions", catagoryTable.getDescriptions());
        mDB.update("catagory", values, condition, null);
    }

    public static void updateVideosData(@NonNull VideosTable videosTable, @NonNull String condition) {
        mDB = mDH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("videoid", videosTable.getVideoid());
        values.put("title", videosTable.getTitle());
        values.put("catagoryid", videosTable.getCatagoryId());
        values.put("groupid", videosTable.getGroupid());
        values.put("descriptions", videosTable.getDescriptions());
        mDB.update("videos", values, condition, null);
    }

    public static void closeDatabase() {
        mDH.close();
    }

}
