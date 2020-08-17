package tk.munditv.uvideos;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.ArrayList;

import tk.munditv.uvideos.database.CatagoryTable;
import tk.munditv.uvideos.database.GroupTable;
import tk.munditv.uvideos.database.VideosDatabase;
import tk.munditv.uvideos.database.VideosTable;
import tk.munditv.uvideos.utils.FullScreenHelper;


public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";

    private YouTubePlayerView youTubePlayerView;
    private FullScreenHelper fullScreenHelper = new FullScreenHelper(this);
    private String videoID;
    private String title;
    private String descriptions;
    private String thumbnailurl;

    // a list of videos not available in some countries, to test if they're handled gracefully.
    // private String[] nonPlayableVideoIds = { "sop2V_MREEI" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        youTubePlayerView = findViewById(R.id.player_view);

        initYouTubePlayerView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        youTubePlayerView.getPlayerUiController().getMenu().dismiss();
    }

    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen())
            youTubePlayerView.exitFullScreen();
        else
            super.onBackPressed();
    }

    private void initYouTubePlayerView() {
        initPlayerMenu();

        //initialising various descriptive data in the UI and player
        TextView video_title = (TextView)findViewById(R.id.player_title);
        TextView video_desc = (TextView)findViewById(R.id.player_description);
        TextView video_id = (TextView)findViewById(R.id.player_id);

        //setting text of each View form UI
        //setText method used to change the text shown in the view
        //getIntent method returns the object of current Intent
        //of which getStringExtra returns the string which was passed while calling the intent
        //by using the name that was associated during call
        videoID = getIntent().getStringExtra("VIDEO_ID");
        title = getIntent().getStringExtra("VIDEO_TITLE");
        descriptions = getIntent().getStringExtra("VIDEO_DESC");
        thumbnailurl = getIntent().getStringExtra("VIDEO_THUMBNAILURL");
        video_title.setText(title);
        video_id.setText("Video ID : " + videoID);
        video_desc.setText(descriptions);

        // The player will automatically release itself when the activity is destroyed.
        // The player will automatically pause when the activity is stopped
        // If you don't add YouTubePlayerView as a lifecycle observer, you will have to release it manually.
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        videoID,
                        0f
                );

                addFullScreenListenerToPlayer();
            }
        });
        VideosDatabase.initialize(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideosDatabase.closeDatabase();
    }

    /**
     * Shows the menu button in the player and adds an item to it.
     */
    private void initPlayerMenu() {
        youTubePlayerView.getPlayerUiController()
                .showMenuButton(true)
                .getMenu()
                .addItem(new MenuItem("favorite", R.drawable.favicon,
                        AddFavorite));
    }

    private View.OnClickListener AddFavorite = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "AddFavorite - OnClick()");
            showPopupWindow();
            //saveDatabase();
            youTubePlayerView.getPlayerUiController().getMenu().dismiss();
        }
    };

    private int groupsid;
    private int catagoryid;
    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.vidoe_popupwindow, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        TextView mLabelCatagory = popupView.findViewById(R.id.label_catagory);
        TextView mLabelGroups = popupView.findViewById(R.id.label_groups);
        Button mButtonSave = popupView.findViewById(R.id.btn_save);
        Button mButtonCancel = popupView.findViewById(R.id.btn_cancel);
        Spinner mSpinnerCatagory = popupView.findViewById(R.id.spinner_catagory);
        Spinner mSpinnerGroups = popupView.findViewById(R.id.spinner_groups);
        mLabelCatagory.setText(getString(R.string.text_catagory));
        mLabelGroups.setText(getString(R.string.text_groups));
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDatabase(catagoryid, groupsid);
                popupWindow.dismiss();
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        ArrayList<CatagoryTable> catagorydata = VideosDatabase.queryCatagoryTable(null);
        String[] names = new String[catagorydata.size()];
        for(int i=0; i<catagorydata.size(); i++) {
            names[i] = catagorydata.get(i).getName();
        }
        ArrayAdapter<String> mSpinnerCatagoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, names);
        mSpinnerCatagory.setAdapter(mSpinnerCatagoryAdapter);
        mSpinnerCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catagoryid = catagorydata.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                catagoryid = catagorydata.get(0).getId();
            }
        });

        ArrayList<GroupTable> groupdata = VideosDatabase.queryGroupTable(null);
        names = new String[groupdata.size()];
        for(int i=0; i<groupdata.size(); i++) {
            names[i] = groupdata.get(i).getName();
        }
        ArrayAdapter<String> mSpinnerGroupsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, names);
        mSpinnerGroups.setAdapter(mSpinnerGroupsAdapter);
        mSpinnerGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupsid = groupdata.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                groupsid = groupdata.get(0).getId();
            }
        });


        popupWindow.setTouchable(true);
        //popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void saveDatabase(int catagoryId, int groupId) {
        VideosTable videosTable = new VideosTable(-1, videoID, title,
                catagoryId, groupId, descriptions, thumbnailurl);
        VideosDatabase.insertVideoData(videosTable);
    }

    private void addFullScreenListenerToPlayer() {
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreenHelper.enterFullScreen();

                addCustomActionsToPlayer();
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenHelper.exitFullScreen();

                removeCustomActionsFromPlayer();
            }
        });
    }

    /**
     * This method adds a new custom action to the player.
     * Custom actions are shown next to the Play/Pause button in the middle of the player.
     */
    private void addCustomActionsToPlayer() {
        Drawable customAction1Icon = ContextCompat.getDrawable(this, R.drawable.ic_fast_rewind_white_24dp);
        Drawable customAction2Icon = ContextCompat.getDrawable(this, R.drawable.ic_fast_forward_white_24dp);
        assert customAction1Icon != null;
        assert customAction2Icon != null;

        youTubePlayerView.getPlayerUiController().setCustomAction1(customAction1Icon, view ->
                Toast.makeText(this, "custom action1 clicked", Toast.LENGTH_SHORT).show());

        youTubePlayerView.getPlayerUiController().setCustomAction2(customAction2Icon, view ->
                Toast.makeText(this, "custom action1 clicked", Toast.LENGTH_SHORT).show());
    }

    private void removeCustomActionsFromPlayer() {
        youTubePlayerView.getPlayerUiController().showCustomAction1(false);
        youTubePlayerView.getPlayerUiController().showCustomAction2(false);
    }

}
