package com.example.amr.sunbula.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amr on 22/07/2017.
 */

public class List_CausesAdapter extends ArrayAdapter<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> {

    String UserID;
    private Context activity;
    private List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> list_friends_name;
    private int resource;
    private APIService mAPIService;
    private ProgressDialog pdialog;

    public List_CausesAdapter(String UserID, Context context, int resource, List<AllCategoriesResponse.AllCategoriesBean.AllCasesBean> objects) {
        super(context, resource, objects);

        this.UserID = UserID;
        this.activity = context;
        this.resource = resource;
        this.list_friends_name = objects;

        pdialog = new ProgressDialog(activity);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        holder.btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMassegePost(UserID, list_friends_name.get(position).getCauseID(), "I'd like to connect with you");
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

    private class ViewHolderList_Causes {
        private TextView cause_user_name;
        private Button btn_donate;

        private ViewHolderList_Causes(View v) {
            cause_user_name = (TextView) v.findViewById(R.id.cause_user_name);
            btn_donate = (Button) v.findViewById(R.id.btn_donate);
        }
    }
}