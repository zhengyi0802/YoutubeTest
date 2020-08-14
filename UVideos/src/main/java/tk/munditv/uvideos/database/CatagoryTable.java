package tk.munditv.uvideos.database;

import com.google.gson.Gson;

public class CatagoryTable {
    private int id;
    private String name;
    private String descriptions;

    public CatagoryTable(int id, String name, String descriptions) {
        this.id = id;
        this.name = name;
        this.descriptions = descriptions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String toJson() {
        return new Gson().toJson(this, GroupTable.class);
    }

    public boolean equals(CatagoryTable other) {
        if (this.id == other.getId()) return true;
        if (this.name.equals(other.getName())) return true;
        return false;
    }

}
