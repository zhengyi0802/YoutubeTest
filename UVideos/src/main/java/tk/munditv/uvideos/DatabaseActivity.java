package tk.munditv.uvideos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tk.munditv.uvideos.database.CatagoryAdapter;
import tk.munditv.uvideos.database.CatagoryTable;
import tk.munditv.uvideos.database.GroupTable;
import tk.munditv.uvideos.database.GroupsAdapter;
import tk.munditv.uvideos.database.VideosDatabase;

public class DatabaseActivity extends AppCompatActivity {

    private ListView mListDatabase;
    private Spinner mSpinnerTable;
    private String[] tables = {"catagory", "groups", "videos"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_database);
        mListDatabase = findViewById(R.id.listview_database);
        mSpinnerTable = findViewById(R.id.spinner_table);
        VideosDatabase.initialize(this);
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, tables);
        mSpinnerTable.setAdapter(mSpinnerAdapter);
        mSpinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        showCatagoryData();
                        break;
                    case 1:
                        showGroupsData();
                        break;
                    case 2:
                        showVideosData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showCatagoryData();
            }
        });
    }

    private void showCatagoryData() {
        ArrayList<CatagoryTable> data = VideosDatabase.queryCatagoryTable(null);
        CatagoryAdapter mListViewAdapter = new CatagoryAdapter(this, R.layout.item_listcatagory, data);
        mListDatabase.setAdapter(mListViewAdapter);
    }

    private void showGroupsData() {
        ArrayList<GroupTable> data = VideosDatabase.queryGroupTable(null);
        GroupsAdapter mListViewAdapter = new GroupsAdapter(this, R.layout.item_listgroup, data);
        mListDatabase.setAdapter(mListViewAdapter);
    }

    private void showVideosData() {

    }


}
