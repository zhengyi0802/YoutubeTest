package tk.munditv.uvideos.database;

import com.google.gson.Gson;

public class VideosTable {
    private int id;
    private String videoid;
    private String title;
    private int catagoryid;
    private int groupid;
    private String descriptions;

    public VideosTable(int id, String videoid, String title,
                       int catagoryid, int groupid, String descriptions) {
        this.id = id;
        this.videoid = videoid;
        this.title = title;
        this.catagoryid = catagoryid;
        this.groupid = groupid;
        this.descriptions = descriptions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String toJson() {
        return new Gson().toJson(this, VideosTable.class);
    }

    public boolean equal(VideosTable videos) {
        if (this.id == videos.getId()) return true;
        if (this.videoid.equals(videos.getVideoid())) return true;
        return false;
    }

}
