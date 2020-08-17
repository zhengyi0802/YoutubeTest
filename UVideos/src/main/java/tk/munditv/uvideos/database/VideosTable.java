package tk.munditv.uvideos.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class VideosTable {
    private int id;
    private String videoid;
    private String title;
    private int catagoryid;
    private int groupid;
    private String descriptions;
    private String thumbnailurl;

    public VideosTable(int id, @NonNull String videoid, @NonNull String title,
                       int catagoryid, int groupid,
                       @Nullable String descriptions, @Nullable String thumbnailurl) {
        this.id = id;
        this.videoid = videoid;
        this.title = title;
        this.catagoryid = catagoryid;
        this.groupid = groupid;
        this.descriptions = descriptions;
        this.thumbnailurl = thumbnailurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVideoid(@NonNull String videoid) {
        this.videoid = videoid;
    }

    @NonNull
    public String getVideoid() {
        return videoid;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setCatagoryId(int catagoryid) {
        this.catagoryid = catagoryid;
    }

    public int getCatagoryId() {
        return catagoryid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    @Nullable
    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public String toJson() {
        return new Gson().toJson(this, VideosTable.class);
    }

    public boolean equal(VideosTable videos) {
        if (this.id == videos.getId()) return true;
        if (this.videoid.equals(videos.getVideoid())) return true;
        return false;
    }

}
