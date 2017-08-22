package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.amr.sunbula.Models.APIResponses.InboxResponse;
import com.example.amr.sunbula.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Amr on 22/07/2017.
 */

public class MessagesFragmentAdapter extends ArrayAdapter<InboxResponse.ListOfMassegesBean> {

    private Context activity;
    private List<InboxResponse.ListOfMassegesBean> list_user_message;
    private int resource;

    public MessagesFragmentAdapter(Context context, int resource, List<InboxResponse.ListOfMassegesBean> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.list_user_message = objects;
    }

    @Override
    public int getCount() {
        return list_user_message.size();
    }

    @Override
    public InboxResponse.ListOfMassegesBean getItem(int position) {
        return list_user_message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMessages holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderMessages(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderMessages) convertView.getTag();
        }

        holder.user_message.setText(list_user_message.get(position).getName());

        if (list_user_message.get(position).isIsMine())
            holder.message_details.setText("You: " + list_user_message.get(position).getMSGBoody());
        else
            holder.message_details.setText(list_user_message.get(position).getMSGBoody());

        if (!list_user_message.get(position).isIsSeen()) {
            holder.counter_of_messages.setText(String.valueOf(list_user_message.get(position).getCounter()));
            holder.counter_of_messages.setVisibility(View.VISIBLE);
        }

        Picasso.with(activity).load(list_user_message.get(position).getImg()).into(holder.message_user_pic);

        return convertView;
    }

    private class ViewHolderMessages {
        private TextView user_message, message_details;
        de.hdodenhof.circleimageview.CircleImageView message_user_pic;
        Button counter_of_messages;

        private ViewHolderMessages(View v) {
            user_message = (TextView) v.findViewById(R.id.user_message);
            message_details = (TextView) v.findViewById(R.id.message_details);
            message_user_pic = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.message_user_pic);
            counter_of_messages = (Button) v.findViewById(R.id.counter_of_messages);
        }
    }
}