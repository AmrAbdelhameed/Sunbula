
package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchPeopleResponse;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class SearchPeople_Adapter extends ArrayAdapter<String> {

    private Context activity;
    private List<SearchPeopleResponse.SearchedPepoleBean> searchedPepoleBeen;
    int resource;

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
    public String getItem(int position) {
        return searchedPepoleBeen.get(position).getName();
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

        holder.item1.setText(searchedPepoleBeen.get(position).getName());

        return convertView;
    }

    private class ViewHolderSearchCauses_People {
        private TextView item1;

        public ViewHolderSearchCauses_People(View v) {
            item1 = (TextView) v.findViewById(R.id.people_name);
        }
    }
}