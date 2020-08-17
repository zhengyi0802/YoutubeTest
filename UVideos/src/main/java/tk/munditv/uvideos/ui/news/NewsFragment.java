package tk.munditv.uvideos.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tk.munditv.uvideos.MainActivity;
import tk.munditv.uvideos.R;
import tk.munditv.uvideos.database.VideosDatabase;
import tk.munditv.uvideos.database.VideosTable;
import tk.munditv.uvideos.utils.VideoInfo;
import tk.munditv.uvideos.utils.YoutubeAdapter;

public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        VideosDatabase.initialize(getActivity());
        mRecyclerView = root.findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String condition="catagoryid=3";
        showVideosData(condition);
        String title=getString(R.string.menu_news);
        ((MainActivity)getActivity()).setActionBarTitle(title);
        return root;
    }

    private void showVideosData(String condition) {
        ArrayList<VideosTable> data = VideosDatabase.queryVideosTable(condition);
        ArrayList<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
        for(int i=0; i < data.size(); i++) {
            VideosTable videosTable = data.get(i);
            VideoInfo videoInfo = new VideoInfo(videosTable.getVideoid(), videosTable.getTitle(),
                    null, videosTable.getDescriptions(), videosTable.getThumbnailurl(),
                    null);
            videoInfos.add(videoInfo);
        }
        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(getActivity(), videoInfos);
        mRecyclerView.setAdapter(youtubeAdapter);
    }
}
