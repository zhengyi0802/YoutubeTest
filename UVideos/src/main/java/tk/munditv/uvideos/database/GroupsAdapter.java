package tk.munditv.uvideos.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tk.munditv.uvideos.R;

public class GroupsAdapter extends BaseAdapter {

    private ArrayList<GroupTable> mLists;
    private Context mContext;
    private int mResource;

    public GroupsAdapter(@NonNull Context context, int resource,
                         @NonNull ArrayList<GroupTable> objects) {
        this.mContext = context;
        this.mLists = objects;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rawView = inflater.inflate(mResource, null, true);
        if(convertView == null) {
            TextView textId = rawView.findViewById(R.id.data_id);
            TextView textName = rawView.findViewById(R.id.data_name);
            TextView textDescriptions = rawView.findViewById(R.id.data_descriptions);
            GroupTable groupTable = mLists.get(position);
            textId.setText(Integer.toString(groupTable.getId()));
            textName.setText(groupTable.getName());
            textDescriptions.setText(groupTable.getDescriptions());
        } else {
            rawView = convertView;
        }
        return rawView;
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

}