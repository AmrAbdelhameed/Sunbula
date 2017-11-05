package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.SearchPeopleResponse;
import com.example.amr.sunbula.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class SearchPeople_Adapter extends ArrayAdapter<SearchPeopleResponse.SearchedPepoleBean> {

    private Context activity;
    private List<SearchPeopleResponse.SearchedPepoleBean> searchedPepoleBeen;
    private int resource;

    public SearchPeople_Adapter(Context context, int resource, List<SearchPeopleResponse.SearchedPepoleBean> objects) {
        super(context, resource);
        this.activity = context;
        this.resource = resource;
        this.searchedPepoleBeen = objects;
    }

    @Override
    public int getCount() {
        return searchedPepoleBeen.size();
    }

    @Override
    public SearchPeopleResponse.SearchedPepoleBean getItem(int position) {
        return searchedPepoleBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderSearchCauses_People holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderSearchCauses_People(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderSearchCauses_People) convertView.getTag();
        }

        holder.people_name.setText(searchedPepoleBeen.get(position).getName());

        String imageUrl = searchedPepoleBeen.get(position).getImgURL();
        if (imageUrl != null && imageUrl.isEmpty())
            imageUrl = null;
        Picasso.with(activity).load(imageUrl).into(holder.people_image);
        return convertView;
    }

    private class ViewHolderSearchCauses_People {
        de.hdodenhof.circleimageview.CircleImageView people_image;
        private TextView people_name;

        private ViewHolderSearchCauses_People(View v) {
            people_name = (TextView) v.findViewById(R.id.people_name);
            people_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.people_image);
        }
    }
}