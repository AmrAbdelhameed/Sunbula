package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amr.sunbula.Models.DBFlowModels.NewsFeed;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class HomeFragmentAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<NewsFeed> List_item_in_home;

    public HomeFragmentAdapter(Context context, List<NewsFeed> List_item_in_home) {
        super(context, R.layout.item_in_home);
        this.activity = context;
        this.List_item_in_home = List_item_in_home;
    }

    @Override
    public int getCount() {
        return List_item_in_home.size();
    }

    @Override
    public String getItem(int position) {
        return List_item_in_home.get(position).getCaseName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderHome holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_in_home, parent, false);
            holder = new ViewHolderHome(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHome) convertView.getTag();
        }

        holder.text_item_in_home.setText(List_item_in_home.get(position).getCaseName() + "\n" + List_item_in_home.get(position).getCaseDescription());
        holder.image_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout12.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    private class ViewHolderHome {
        private Button text_item_in_home;
        private ImageButton image_switch;
        private LinearLayout linearLayout12;

        public ViewHolderHome(View v) {
            text_item_in_home = (Button) v.findViewById(R.id.text_item_in_home);
            image_switch = (ImageButton) v.findViewById(R.id.image_switch);
            linearLayout12 = (LinearLayout) v.findViewById(R.id.linearLayout12);

        }
    }
}