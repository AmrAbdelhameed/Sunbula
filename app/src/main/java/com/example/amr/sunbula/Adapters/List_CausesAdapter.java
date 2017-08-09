package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.R;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class List_CausesAdapter extends ArrayAdapter<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> {

    private Context activity;
    private List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> list_friends_name;
    int resource;

    public List_CausesAdapter(Context context, int resource, List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.list_friends_name = objects;
    }

    @Override
    public int getCount() {
        return list_friends_name.size();
    }

    @Override
    public AllCategoriesResponse.AllCategoriesBean.AllCasesBean getItem(int position) {
        return list_friends_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderList_Causes holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderList_Causes(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderList_Causes) convertView.getTag();
        }

        holder.cause_user_name.setText(list_friends_name.get(position).getCaseName());

        return convertView;
    }

    private class ViewHolderList_Causes {
        private TextView cause_user_name;

        private ViewHolderList_Causes(View v) {
            cause_user_name = (TextView) v.findViewById(R.id.cause_user_name);
        }
    }
}