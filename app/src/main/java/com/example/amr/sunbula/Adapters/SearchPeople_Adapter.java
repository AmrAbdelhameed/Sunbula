
package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.ConfirmEmailActivity;
import com.example.amr.sunbula.LoginActivity;
import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchPeopleResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.ShowDetailsUserActivity;

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

        holder.people_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ShowDetailsUserActivity.class);
                Bundle b = new Bundle();
                b.putString("people_id", searchedPepoleBeen.get(position).getUser_ID());
                i.putExtras(b);
                activity.startActivity(i);
            }
        });

        return convertView;
    }

    private class ViewHolderSearchCauses_People {
        private TextView people_name;

        private ViewHolderSearchCauses_People(View v) {
            people_name = (TextView) v.findViewById(R.id.people_name);
        }
    }
}