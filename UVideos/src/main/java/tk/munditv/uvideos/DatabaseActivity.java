package tk.munditv.uvideos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tk.munditv.uvideos.database.CatagoryAdapter;
import tk.munditv.uvideos.database.CatagoryTable;
import tk.munditv.uvideos.database.GroupTable;
import tk.munditv.uvideos.database.GroupsAdapter;
import tk.munditv.uvideos.database.VideosAdapter;
import tk.munditv.uvideos.database.VideosDatabase;
import tk.munditv.uvideos.database.VideosTable;

import static android.view.View.GONE;

public class DatabaseActivity extends AppCompatActivity {

    private ListView mListDatabase;
    private Spinner mSpinnerTable;
    private EditText mNewGroupName;
    private Context mContext;
    private String[] tables = new String[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activiy_database);
        mListDatabase = findViewById(R.id.listview_database);
        mSpinnerTable = findViewById(R.id.spinner_table);
        VideosDatabase.initialize(this);
        tables[0] = getString(R.string.text_catagory);
        tables[1] = getString(R.string.text_groups);
        tables[2] = getString(R.string.text_videos);
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
                        showVideosData(null);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showCatagoryData();
            }
        });

        mNewGroupName = findViewById(R.id.new_group_name);
    }

    private void showCatagoryData() {
        ArrayList<CatagoryTable> data = VideosDatabase.queryCatagoryTable(null);
        CatagoryAdapter mListViewAdapter = new CatagoryAdapter(this, R.layout.item_listcatagory, data);
        mListDatabase.setAdapter(mListViewAdapter);
        mListDatabase.setOnItemClickListener(null);
        mNewGroupName.setVisibility(View.GONE);
        mListDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CatagoryTable catagoryTable = (CatagoryTable) view.getTag();
                String mCondition = "catagoryid=" + catagoryTable.getId();
                showVideosData(mCondition);
            }
        });
    }

    private void showGroupsData() {
        ArrayList<GroupTable> data = VideosDatabase.queryGroupTable(null);
        GroupsAdapter mListViewAdapter = new GroupsAdapter(this, R.layout.item_listgroup, data);
        mListDatabase.setAdapter(mListViewAdapter);
        mListDatabase.setOnItemClickListener(null);
        mNewGroupName.setVisibility(View.VISIBLE);
        mNewGroupName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String name = textView.getText().toString();
                    GroupTable groupTable = new GroupTable(0, name, name);
                    VideosDatabase.insertGroupData(groupTable);
                    data.add(groupTable);
                    mListViewAdapter.notifyDataSetChanged();
                    textView.setText(null);
                }
                return false;
            }
        });
        mListDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupTable groupTable = (GroupTable) view.getTag();
                String mCondition = "groupid="+ groupTable.getId();
                showVideosData(mCondition);
            }
        });
    }

    private void showVideosData(String condition) {
        ArrayList<VideosTable> data = VideosDatabase.queryVideosTable(condition);
        VideosAdapter mListViewAdapter = new VideosAdapter(this, R.layout.item_listvideos, data);
        mListDatabase.setAdapter(mListViewAdapter);
        mListDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideosTable videosTable = (VideosTable) view.getTag();
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("VIDEO_ID", videosTable.getVideoid());
                intent.putExtra("VIDEO_TITLE", videosTable.getTitle());
                intent.putExtra("VIDEO_DESC", videosTable.getDescriptions());
                intent.putExtra("THUMBNAILURL", videosTable.getThumbnailurl());
                startActivity(intent);
            }
        });
        mNewGroupName.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideosDatabase.closeDatabase();
    }

    @Override
    public void onBackPressed() {
        showCatagoryData();
    }
}
