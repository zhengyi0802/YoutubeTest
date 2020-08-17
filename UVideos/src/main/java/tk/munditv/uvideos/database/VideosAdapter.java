package tk.munditv.uvideos.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tk.munditv.uvideos.R;

public class VideosAdapter extends BaseAdapter {

    private ArrayList<VideosTable> mLists;
    private Context mContext;
    private int mResource;

    public VideosAdapter(@NonNull Context context, int resource,
                         @NonNull ArrayList<VideosTable> objects) {
        this.mContext = context;
        this.mLists = objects;
        this.mResource = resource;
    }

    @Override
    public int getCount() {
        if(mLists == null) return 0;
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mLists.get(i).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rawView = inflater.inflate(mResource, null, true);
        if (convertView == null) {
            TextView mId = rawView.findViewById(R.id.data_id);
            TextView mVideoId = rawView.findViewById(R.id.data_videoid);
            TextView mTitle = rawView.findViewById(R.id.data_title);
            TextView mDescriptions = rawView.findViewById(R.id.data_descriptions);
            ImageView mThumbnail = rawView.findViewById(R.id.data_thumbnail);
            mId.setText(Integer.toString(mLists.get(position).getId()));
            mVideoId.setText(mLists.get(position).getVideoid());
            mTitle.setText(mLists.get(position).getTitle());
            mDescriptions.setText(mLists.get(position).getDescriptions());
            Picasso.with(mContext)
                    .load(mLists.get(position).getThumbnailurl())
                    .resize(480,270)
                    .centerCrop()
                    .into(mThumbnail);
            rawView.setTag(mLists.get(position));
        } else {
            rawView = convertView;
        }
        return rawView;
    }
}
