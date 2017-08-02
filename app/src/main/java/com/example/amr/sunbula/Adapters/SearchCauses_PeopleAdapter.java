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

public class SearchCauses_PeopleAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<String> list_item1;
    int resource;

    public SearchCauses_PeopleAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.list_item1 = objects;
    }

    @Override
    public int getCount() {
        return list_item1.size();
    }

    @Override
    public String getItem(int position) {
        return list_item1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderSearchCauses_People holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderSearchCauses_People(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderSearchCauses_People) convertView.getTag();
        }

        holder.item1.setText(getItem(position));

        return convertView;
    }

    private class ViewHolderSearchCauses_People {
        private TextView item1;

        public ViewHolderSearchCauses_People(View v) {
            item1 = (TextView) v.findViewById(R.id.item1);
        }
    }
}