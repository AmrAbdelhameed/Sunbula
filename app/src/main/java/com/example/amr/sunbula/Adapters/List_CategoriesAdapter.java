package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.amr.sunbula.CausesInOneCategoryActivity;
import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.SwipeLayout;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class List_CategoriesAdapter extends ArrayAdapter<AllCategoriesResponse.AllCategoriesBean> {

    private Context activity;
    private List<AllCategoriesResponse.AllCategoriesBean> allCategoriesBeen;
    private List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> allCasesBeen;
    private int resource;

    public List_CategoriesAdapter(Context context, int resource, List<AllCategoriesResponse.AllCategoriesBean> objects) {
        super(context, resource);
        this.activity = context;
        this.resource = resource;
        this.allCategoriesBeen = objects;
        allCasesBeen = new ArrayList<AllCategoriesResponse.AllCategoriesBean.AllCasesBean>();
    }

    @Override
    public int getCount() {
        return allCategoriesBeen.size();
    }

    @Override
    public AllCategoriesResponse.AllCategoriesBean getItem(int position) {
        return allCategoriesBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderCategories holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderCategories(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderCategories) convertView.getTag();
        }

        holder.textView.setText(allCategoriesBeen.get(position).getCategoryName());

        holder.wrapper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                holder.mSwipe.addVelocityTracker(ev);

                int leftButton2Position = holder.buttonHead2.getRight();

                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (ev.getX() < leftButton2Position) {
                            holder.buttonHead2.setPressed(true);
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        holder.mSwipe.doMove(ev);
                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        // put the button action here
                        if (holder.buttonHead2.isPressed()) {
                            allCasesBeen = allCategoriesBeen.get(position).getAllCases();
                            Intent GOTOCauses = new Intent(activity, CausesInOneCategoryActivity.class);
                            Bundle bundle = new Bundle();
                            Gson gson = new Gson();
                            String jsonCars = gson.toJson(allCasesBeen);
                            bundle.putString("NameCategory", allCategoriesBeen.get(position).getCategoryName());
                            bundle.putString("Causes", jsonCars);
                            GOTOCauses.putExtras(bundle);
                            activity.startActivity(GOTOCauses);
                        }

                        holder.mSwipe.computeVelocityTracker();
                        holder.mSwipe.doReset();
                        holder.mSwipe.releaseTracker();

                        return true;
                }

                return false;
            }
        });

        return convertView;
    }

    private class ViewHolderCategories {
        private TextView textView;
        private Button buttonHead2;
        private LinearLayout wrapper;
        private SwipeLayout mSwipe;

        private ViewHolderCategories(View v) {
            textView = (TextView) v.findViewById(R.id.category_name);
            buttonHead2 = (Button) v.findViewById(R.id.buttonHead2);
            wrapper = (LinearLayout) v.findViewById(R.id.wrapper);
            mSwipe = (SwipeLayout) v.findViewById(R.id.swipeLayout);
        }
    }
}