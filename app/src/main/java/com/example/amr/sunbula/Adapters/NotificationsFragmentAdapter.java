package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class NotificationsFragmentAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<String> list_friends_name;
    private int resource;

    public NotificationsFragmentAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.list_friends_name = objects;
    }

    @Override
    public int getCount() {
        return list_friends_name.size();
    }

    @Override
    public String getItem(int position) {
        return list_friends_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderNotifications holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderNotifications(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderNotifications) convertView.getTag();
        }

        holder.friends_name.setText(getItem(position));

        return convertView;
    }

    private class ViewHolderNotifications {
        private TextView friends_name;

        private ViewHolderNotifications(View v) {
            friends_name = (TextView) v.findViewById(R.id.friends_name);
        }
    }
}