package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class HisUserCausesAdapter extends ArrayAdapter<HisCausesPeopleWrapper> {

    private Context activity;
    private List<HisCausesPeopleWrapper> hisCausesPeopleWrappers;
    int resource;

    public HisUserCausesAdapter(Context context, int resource, List<HisCausesPeopleWrapper> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.hisCausesPeopleWrappers = objects;
    }

    @Override
    public int getCount() {
        return hisCausesPeopleWrappers.size();
    }

    @Override
    public HisCausesPeopleWrapper getItem(int position) {
        return hisCausesPeopleWrappers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderNotifications holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderNotifications(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderNotifications) convertView.getTag();
        }

        holder.text_name_cause.setText(hisCausesPeopleWrappers.get(position).getCaseName());
        holder.text_details_cause.setText(hisCausesPeopleWrappers.get(position).getCaseDescription());

        if (hisCausesPeopleWrappers.get(position).isSelected()) {
            holder.text_details_cause.setVisibility(View.VISIBLE);
            holder.image_message.setVisibility(View.VISIBLE);
            holder.image_switch.setVisibility(View.GONE);

        } else {
            holder.text_details_cause.setVisibility(View.GONE);
            holder.image_message.setVisibility(View.GONE);
            holder.image_switch.setVisibility(View.VISIBLE);
        }

        holder.image_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hisCausesPeopleWrappers.get(position).setSelected(true);

                holder.text_details_cause.setVisibility(View.VISIBLE);
                holder.image_message.setVisibility(View.VISIBLE);
                holder.image_switch.setVisibility(View.GONE);

            }
        });
        holder.text_details_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hisCausesPeopleWrappers.get(position).setSelected(false);

                holder.text_details_cause.setVisibility(View.GONE);
                holder.image_message.setVisibility(View.GONE);
                holder.image_switch.setVisibility(View.VISIBLE);

            }
        });

        return convertView;
    }

    private class ViewHolderNotifications {
        private TextView text_name_cause, text_details_cause;
        private ImageView image_switch, image_message;

        public ViewHolderNotifications(View v) {
            text_name_cause = (TextView) v.findViewById(R.id.text_name_cause);
            text_details_cause = (TextView) v.findViewById(R.id.text_details_cause);

            image_switch = (ImageView) v.findViewById(R.id.image_switch);
            image_message = (ImageView) v.findViewById(R.id.image_message);
        }
    }
}