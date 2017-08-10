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

public class MessagesFragmentAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<String> list_user_message;
    private int resource;

    public MessagesFragmentAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.list_user_message = objects;
    }

    @Override
    public int getCount() {
        return list_user_message.size();
    }

    @Override
    public String getItem(int position) {
        return list_user_message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMessages holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderMessages(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderMessages) convertView.getTag();
        }

        holder.user_message.setText(getItem(position));

        return convertView;
    }

    private class ViewHolderMessages {
        private TextView user_message;

        private ViewHolderMessages(View v) {
            user_message = (TextView) v.findViewById(R.id.user_message);
        }
    }
}