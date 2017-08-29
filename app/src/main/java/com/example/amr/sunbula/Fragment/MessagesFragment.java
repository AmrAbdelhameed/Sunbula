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
import com.example.amr.sunbula.Activities.SearchCauses_People;
import com.example.amr.sunbula.Activities.ShowDetailsUserActivity;
import com.example.amr.sunbula.Adapters.MessagesFragmentAdapter;
import com.example.amr.sunbula.Adapters.SearchCauses_Adapter;
import com.example.amr.sunbula.Adapters.SearchPeople_Adapter;
import com.example.amr.sunbula.Models.APIResponses.InboxResponse;
import com.example.amr.sunbula.Models.APIResponses.InboxResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private ListView listView;
    private List<InboxResponse.ListOfMassegesBean> listOfMassegesBeen;
    private MessagesFragmentAdapter adapter;
    APIService mAPIService;
    private ProgressDialog pdialog;
    String UserID;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    InboxResponse.ListOfMassegesBean listOfMassegesBean;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        listView = (ListView) v.findViewById(R.id.list_item_messages);
        InboxFirebase(UserID);
//        InboxPost(UserID);
        return v;
    }

    public void InboxFirebase(final String body) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        listOfMassegesBeen = new ArrayList<InboxResponse.ListOfMassegesBean>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("Messeges").child("Inbox").child("ListOfMasseges").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                listOfMassegesBeen.clear();
                for (DataSnapshot child : children) {

                    listOfMassegesBean = new InboxResponse.ListOfMassegesBean();
                    String ThreadID = child.getKey();
                    String Counter = child.child("Counter").getValue().toString();
                    String Date = child.child("Date").getValue().toString();
                    String FromID = child.child("FromID").getValue().toString();
                    String Img = child.child("Img").getValue().toString();
                    boolean IsMine = (boolean) child.child("IsMine").getValue();
                    boolean IsSeen = (boolean) child.child("IsSeen").getValue();
                    String MSGBoody = child.child("MSGBoody").getValue().toString();
                    String Name = child.child("Name").getValue().toString();
                    String User_ID = child.child("User_ID").getValue().toString();

                    if (User_ID.contains(body)) {
                        listOfMassegesBean.setThreadID(ThreadID);
                        listOfMassegesBean.setCounter(Integer.parseInt(Counter));
                        listOfMassegesBean.setDate(Date);
                        listOfMassegesBean.setFromID(FromID);
                        listOfMassegesBean.setImg(Img);
                        listOfMassegesBean.setIsMine(IsMine);
                        listOfMassegesBean.setIsSeen(IsSeen);
                        listOfMassegesBean.setMSGBoody(MSGBoody);
                        listOfMassegesBean.setName(Name);

                        listOfMassegesBeen.add(listOfMassegesBean);

                        adapter = new MessagesFragmentAdapter(getActivity(), R.layout.item_in_messages, listOfMassegesBeen);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                // TODO Auto-generated method stub
                                Intent i = new Intent(getActivity(), ChatActivity.class);
                                Bundle b = new Bundle();
                                b.putString("ThreadID", listOfMassegesBeen.get(pos).getThreadID());
                                i.putExtras(b);
                                getActivity().startActivity(i);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                            adapter = new MessagesFragmentAdapter(getActivity(), R.layout.item_in_messages, listOfMassegesBeen);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(getActivity(), ChatActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("ThreadID", listOfMassegesBeen.get(pos).getThreadID());
                                    i.putExtras(b);
                                    getActivity().startActivity(i);
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
                pdialog.dismiss();
            }
        });
    }
}
