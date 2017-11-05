package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amr.sunbula.Models.DBFlowWrappers.NewsFeedWrapper;
import com.example.amr.sunbula.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class HomeFragmentAdapter extends ArrayAdapter<NewsFeedWrapper> {

    private Context activity;
    private List<NewsFeedWrapper> List_item_in_home;

    public HomeFragmentAdapter(Context context, List<NewsFeedWrapper> List_item_in_home) {
        super(context, R.layout.item_in_home);
        this.activity = context;
        this.List_item_in_home = List_item_in_home;
    }

    @Override
    public int getCount() {
        return List_item_in_home.size();
    }

    @Override
    public NewsFeedWrapper getItem(int position) {
        return List_item_in_home.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderHome holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_in_home, parent, false);
            holder = new ViewHolderHome(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHome) convertView.getTag();
        }
        if (List_item_in_home.get(position).isExpanded()) {
            holder.linearLayout12.setVisibility(View.VISIBLE);

        } else {
            holder.linearLayout12.setVisibility(View.GONE);
        }
        String imageUrl = List_item_in_home.get(position).getIMG();
        if (imageUrl != null && imageUrl.isEmpty())
            imageUrl = null;
        Picasso.with(activity).load(imageUrl).into(holder.image_in_item_home);

        holder.text_item_in_home.setText(List_item_in_home.get(position).getCaseName() + "\n" + List_item_in_home.get(position).getCaseDescription());
        holder.number_joined.setText("Joined (" + List_item_in_home.get(position).getNumberofjoins() + ")");
        holder.amount.setText("Amount (" + List_item_in_home.get(position).getAmount() + ")");
        holder.end_date.setText(List_item_in_home.get(position).getEndDate());

        holder.image_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (List_item_in_home.get(position).isExpanded()) {
                    holder.linearLayout12.setVisibility(View.GONE);
                    List_item_in_home.get(position).setExpanded(false);
                } else {
                    holder.linearLayout12.setVisibility(View.VISIBLE);
                    List_item_in_home.get(position).setExpanded(true);
                }
            }
        });
        return convertView;
    }

    private class ViewHolderHome {
        private ImageView image_in_item_home;
        private Button text_item_in_home;
        private ImageButton image_switch;
        private LinearLayout linearLayout12;
        private TextView number_joined, amount, end_date;

        private ViewHolderHome(View v) {
            image_in_item_home = (ImageView) v.findViewById(R.id.image_in_item_home);
            text_item_in_home = (Button) v.findViewById(R.id.text_item_in_home);
            image_switch = (ImageButton) v.findViewById(R.id.image_switch);
            linearLayout12 = (LinearLayout) v.findViewById(R.id.linearLayout12);

            number_joined = (TextView) v.findViewById(R.id.number_joined);
            amount = (TextView) v.findViewById(R.id.amount);
            end_date = (TextView) v.findViewById(R.id.end_date);
        }
    }
}