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

import com.example.amr.sunbula.Activities.CausesInOneCategoryActivity;
import com.example.amr.sunbula.Models.DBFlowModels.Categories;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.Others.SwipeLayout;

import java.util.List;

public class List_CategoriesAdapter extends ArrayAdapter<Categories> {

    private Context activity;
    private List<Categories> allCategoriesBeen;
    private int resource;

    public List_CategoriesAdapter(Context context, int resource, List<Categories> objects) {
        super(context, resource);
        this.activity = context;
        this.resource = resource;
        this.allCategoriesBeen = objects;
    }

    @Override
    public int getCount() {
        return allCategoriesBeen.size();
    }

    @Override
    public Categories getItem(int position) {
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

        holder.category_name.setText(allCategoriesBeen.get(position).getCategoryName());
        holder.category_details.setText(allCategoriesBeen.get(position).getCategoryDescription());

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
                            Intent GOTOCauses = new Intent(activity, CausesInOneCategoryActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("NameCategory", allCategoriesBeen.get(position).getCategoryName());
                            bundle.putString("Causes", allCategoriesBeen.get(position).getAllCauses());
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
        de.hdodenhof.circleimageview.CircleImageView category_image;
        private TextView category_name, category_details;
        private Button buttonHead2;
        private LinearLayout wrapper;
        private SwipeLayout mSwipe;

        private ViewHolderCategories(View v) {
            category_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.category_image);
            category_name = (TextView) v.findViewById(R.id.category_name);
            category_details = (TextView) v.findViewById(R.id.category_details);
            buttonHead2 = (Button) v.findViewById(R.id.buttonHead2);
            wrapper = (LinearLayout) v.findViewById(R.id.wrapper);
            mSwipe = (SwipeLayout) v.findViewById(R.id.swipeLayout);
        }
    }
}