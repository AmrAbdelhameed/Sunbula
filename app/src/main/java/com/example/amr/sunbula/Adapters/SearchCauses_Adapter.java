package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class SearchCauses_Adapter extends ArrayAdapter<SearchCausesResponse.SearchedCasesBean> {

    private Context activity;
    private List<SearchCausesResponse.SearchedCasesBean> searchedCasesBeen;
    private int resource;

    public SearchCauses_Adapter(Context context, int resource, List<SearchCausesResponse.SearchedCasesBean> objects) {
        super(context, resource);
        this.activity = context;
        this.resource = resource;
        this.searchedCasesBeen = objects;
    }

    @Override
    public int getCount() {
        return searchedCasesBeen.size();
    }

    @Override
    public SearchCausesResponse.SearchedCasesBean getItem(int position) {
        return searchedCasesBeen.get(position);
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

        holder.item1.setText(searchedCasesBeen.get(position).getCaseName());

        String imageUrl = searchedCasesBeen.get(position).getIMG();
        if (imageUrl != null && imageUrl.isEmpty())
            imageUrl = null;
        Picasso.with(activity).load(imageUrl).into(holder.message_user_pic);

        return convertView;
    }

    private class ViewHolderSearchCauses_People {
        private TextView item1;
        private de.hdodenhof.circleimageview.CircleImageView message_user_pic;

        private ViewHolderSearchCauses_People(View v) {
            item1 = (TextView) v.findViewById(R.id.item1);
            message_user_pic = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.message_user_pic);
        }
    }
}