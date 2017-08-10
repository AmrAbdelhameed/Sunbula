package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowWrappers.HisCausesPeopleWrapper;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.example.amr.sunbula.ShowDetailsUserActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amr on 22/07/2017.
 */

public class HisUserCausesAdapter extends ArrayAdapter<HisCausesPeopleWrapper> {

    private Context activity;
    private List<HisCausesPeopleWrapper> hisCausesPeopleWrappers;
    private int resource;
    private APIService mAPIService;
    private ProgressDialog pdialog;
    private String User_ID, ToID;

    public HisUserCausesAdapter(Context context, int resource, List<HisCausesPeopleWrapper> objects, String User_ID, String ToID) {
        super(context, resource, objects);
        this.activity = context;
        this.resource = resource;
        this.hisCausesPeopleWrappers = objects;
        this.User_ID = User_ID;
        this.ToID = ToID;

        pdialog = new ProgressDialog(activity);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();
    }

    @Override
    public int getCount() {
        return hisCausesPeopleWrappers.size();
    }

    @Override
    public HisCausesPeopleWrapper getItem(int position) {
        return hisCausesPeopleWrappers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderNotifications holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolderNotifications(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderNotifications) convertView.getTag();
        }

        holder.text_name_cause.setText(hisCausesPeopleWrappers.get(position).getCaseName());
        holder.text_details_cause.setText(hisCausesPeopleWrappers.get(position).getCaseDescription());

        if (hisCausesPeopleWrappers.get(position).isSelected()) {
            holder.text_details_cause.setVisibility(View.VISIBLE);
            holder.image_message.setVisibility(View.VISIBLE);
            holder.image_switch.setVisibility(View.GONE);

        } else {
            holder.text_details_cause.setVisibility(View.GONE);
            holder.image_message.setVisibility(View.GONE);
            holder.image_switch.setVisibility(View.VISIBLE);
        }

        holder.image_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you want to join to " + hisCausesPeopleWrappers.get(position).getCaseName() + " ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SendMassegePost(User_ID, ToID, "I'd like to join in " + hisCausesPeopleWrappers.get(position).getCaseName());

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();
            }
        });

        holder.image_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hisCausesPeopleWrappers.get(position).setSelected(true);

                holder.text_details_cause.setVisibility(View.VISIBLE);
                holder.image_message.setVisibility(View.VISIBLE);
                holder.image_switch.setVisibility(View.GONE);

            }
        });
        holder.text_details_cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hisCausesPeopleWrappers.get(position).setSelected(false);

                holder.text_details_cause.setVisibility(View.GONE);
                holder.image_message.setVisibility(View.GONE);
                holder.image_switch.setVisibility(View.VISIBLE);

            }
        });

        return convertView;
    }

    private void SendMassegePost(String User_ID, String ToID, String MSGBody) {
        pdialog.show();
        mAPIService.SendMassege(User_ID, ToID, MSGBody).enqueue(new Callback<SendMassegeResponse>() {

            @Override
            public void onResponse(Call<SendMassegeResponse> call, Response<SendMassegeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        Toast.makeText(activity, "Your message has been sent pending approval by the administrator", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(activity, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<SendMassegeResponse> call, Throwable t) {
                pdialog.dismiss();
            }
        });
    }

    private class ViewHolderNotifications {
        private TextView text_name_cause, text_details_cause;
        private ImageView image_switch, image_message;

        private ViewHolderNotifications(View v) {
            text_name_cause = (TextView) v.findViewById(R.id.text_name_cause);
            text_details_cause = (TextView) v.findViewById(R.id.text_details_cause);

            image_switch = (ImageView) v.findViewById(R.id.image_switch);
            image_message = (ImageView) v.findViewById(R.id.image_message);
        }
    }
}