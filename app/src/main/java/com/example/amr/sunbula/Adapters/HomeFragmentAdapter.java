package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;
import com.example.amr.sunbula.Models.NewsfeedResponse;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class HomeFragmentAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<NewsFeed> List_item_in_home;
    int resource;

    public HomeFragmentAdapter(Context context, int resource, List<NewsFeed> List_item_in_home) {
        super(context, resource);
        this.activity = context;
        this.resource = resource;
        this.List_item_in_home = List_item_in_home;
    }

    @Override
    public int getCount() {
        return List_item_in_home.size();
    }

    @Override
    public String getItem(int position) {
        return List_item_in_home.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderHome holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderHome(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHome) convertView.getTag();
        }

        holder.text_item_in_home.setText(List_item_in_home.get(position).getName() + "\n" + List_item_in_home.get(position).getDescription());

        return convertView;
    }

    private class ViewHolderHome {
        private TextView text_item_in_home;

        public ViewHolderHome(View v) {
            text_item_in_home = (TextView) v.findViewById(R.id.text_item_in_home);
        }
    }
}