package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.RecieveMassegeResponse;
import com.example.amr.sunbula.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class MessagesInboxAdapter extends ArrayAdapter<RecieveMassegeResponse.MSgsBean> {

    private Context activity;
    private List<RecieveMassegeResponse.MSgsBean> mSgsBeanList;
    String ImageURL;

    public MessagesInboxAdapter(Context context, List<RecieveMassegeResponse.MSgsBean> objects, String ImageURL) {
        super(context, R.layout.item_in_recieve_message, objects);
        this.activity = context;
        this.mSgsBeanList = objects;
        this.ImageURL = ImageURL;
    }

    @Override
    public int getCount() {
        return mSgsBeanList.size();
    }

    @Override
    public RecieveMassegeResponse.MSgsBean getItem(int position) {
        return mSgsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMessages holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (mSgsBeanList.get(position).isIsMine())
            convertView = inflater.inflate(R.layout.item_in_send_message, parent, false);
        else
            convertView = inflater.inflate(R.layout.item_in_recieve_message, parent, false);

        holder = new ViewHolderMessages(convertView);
        convertView.setTag(holder);

        holder.recieve_message.setText(mSgsBeanList.get(position).getMSGBoody());
        holder.recieve_time.setText(mSgsBeanList.get(position).getDate());

        Picasso.with(activity).load(ImageURL).into(holder.message_recieve_pic);

        return convertView;
    }

    private class ViewHolderMessages {
        private TextView recieve_message, recieve_time;
        de.hdodenhof.circleimageview.CircleImageView message_recieve_pic;

        private ViewHolderMessages(View v) {
            recieve_message = (TextView) v.findViewById(R.id.recieve_message);
            recieve_time = (TextView) v.findViewById(R.id.recieve_time);
            message_recieve_pic = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.message_recieve_pic);
        }
    }
}