package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Activities.ChatActivity;
import com.example.amr.sunbula.Adapters.MessagesFragmentAdapter;
import com.example.amr.sunbula.Models.APIResponses.InboxResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    APIService mAPIService;
    String UserID, JSONObjectMessages;
    Gson gson;
    private ListView listView;
    private List<InboxResponse.ListOfMassegesBean> listOfMassegesBeen;
    private MessagesFragmentAdapter adapter;
    private ProgressDialog pdialog;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);

        FirebaseCrash.log("Here comes the exception!");
        FirebaseCrash.report(new Exception("oops!"));

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        gson = new Gson();
        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        listView = (ListView) v.findViewById(R.id.list_item_messages);

        InboxPost(UserID);
        return v;
    }

    public void InboxPost(String UserId) {
        pdialog.show();
        mAPIService.Inbox(UserId).enqueue(new Callback<InboxResponse>() {

            @Override
            public void onResponse(Call<InboxResponse> call, Response<InboxResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isIsSuccess()) {
                        InboxResponse inboxResponse = response.body();

                        if (inboxResponse.getListOfMasseges().size() > 0) {
                            listOfMassegesBeen = new ArrayList<InboxResponse.ListOfMassegesBean>();
                            listOfMassegesBeen = inboxResponse.getListOfMasseges();

                            JSONObjectMessages = gson.toJson(listOfMassegesBeen);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("JSONObjectMessages", JSONObjectMessages);
                            editor.apply();

                            adapter = new MessagesFragmentAdapter(getActivity(), R.layout.item_in_messages, listOfMassegesBeen);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(getActivity(), ChatActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("ThreadID", listOfMassegesBeen.get(pos).getThreadID());
                                    b.putString("FromID", listOfMassegesBeen.get(pos).getFromID());
                                    b.putString("ImageUSER", listOfMassegesBeen.get(pos).getImg());
                                    i.putExtras(b);
                                    getActivity().startActivity(i);
                                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            });
                        }
                    } else
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<InboxResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                JSONObjectMessages = sharedPreferences.getString("JSONObjectMessages", "");

                Type type = new TypeToken<List<InboxResponse.ListOfMassegesBean>>() {
                }.getType();
                listOfMassegesBeen = gson.fromJson(JSONObjectMessages, type);

                adapter = new MessagesFragmentAdapter(getActivity(), R.layout.item_in_messages, listOfMassegesBeen);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), R.string.string_internet_connection_warning, Toast.LENGTH_SHORT).show();
                    }
                });
                pdialog.dismiss();
            }
        });
    }
}
