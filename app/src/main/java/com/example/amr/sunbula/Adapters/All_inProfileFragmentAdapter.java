package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class All_inProfileFragmentAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<String> list_name_cause;
    int resource;

    public All_inProfileFragmentAdapter(Context context, int resource, List<String> list_name_cause) {
        super(context, resource, list_name_cause);
        this.activity = context;
        this.resource = resource;
        this.list_name_cause = list_name_cause;
    }

    @Override
    public int getCount() {
        return list_name_cause.size();
    }

    @Override
    public String getItem(int position) {
        return list_name_cause.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderNotifications holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderNotifications(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderNotifications) convertView.getTag();
        }

        holder.text_name_cause.setText(getItem(position));

        holder.image_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.text_details_cause.setVisibility(View.VISIBLE);
                holder.image_edit.setVisibility(View.VISIBLE);
                holder.image_close1.setVisibility(View.VISIBLE);
                holder.image_delete.setVisibility(View.VISIBLE);
                holder.image_switch.setVisibility(View.GONE);
            }
        });
        holder.text_details_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.text_details_cause.setVisibility(View.GONE);
                holder.image_edit.setVisibility(View.GONE);
                holder.image_close1.setVisibility(View.GONE);
                holder.image_delete.setVisibility(View.GONE);
                holder.image_switch.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    private class ViewHolderNotifications {
        private TextView text_name_cause, text_details_cause;
        private ImageView image_switch, image_edit, image_close1, image_delete;

        public ViewHolderNotifications(View v) {
            text_name_cause = (TextView) v.findViewById(R.id.text_name_cause);
            text_details_cause = (TextView) v.findViewById(R.id.text_details_cause);

            image_switch = (ImageView) v.findViewById(R.id.image_switch);
            image_edit = (ImageView) v.findViewById(R.id.image_edit);
            image_close1 = (ImageView) v.findViewById(R.id.image_close1);
            image_delete = (ImageView) v.findViewById(R.id.image_delete);
        }
    }
}