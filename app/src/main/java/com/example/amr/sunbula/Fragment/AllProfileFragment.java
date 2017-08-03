package com.example.amr.sunbula.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amr.sunbula.Adapters.All_inProfileFragmentAdapter;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.DBFlowModels.AllCauses;
import com.example.amr.sunbula.R;
import com.example.amr.sunbula.RetrofitAPIs.APIService;
import com.example.amr.sunbula.RetrofitAPIs.ApiUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProfileFragment extends Fragment {

    private ListView listView;
    String UserID;
    APIService mAPIService;
    private ProgressDialog pdialog;
    AllCauses n;
    List<UserDetailsResponse.AllCasesListBean> allCasesListBeen;
    private All_inProfileFragmentAdapter adapter;

    public AllProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_profile, container, false);

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mAPIService = ApiUtils.getAPIService();

        FlowManager.init(getActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "null");

        listView = (ListView) v.findViewById(R.id.list_item_all);

        UserDetailsPost(UserID);


        return v;
    }

    public void UserDetailsPost(String UserId) {
        pdialog.show();
        mAPIService.UserDetails(UserId).enqueue(new Callback<UserDetailsResponse>() {

            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                if (response.isSuccessful()) {
                    allCasesListBeen = new ArrayList<UserDetailsResponse.AllCasesListBean>();

                    UserDetailsResponse userDetailsResponse = response.body();
                    allCasesListBeen = userDetailsResponse.getAllCasesList();

                    List<AllCauses> list = (new Select().from(AllCauses.class).queryList());

                    if (list.size() > 0) {
                        Delete.table(AllCauses.class);
                    }

                    for (int i = 0; i < allCasesListBeen.size(); i++) {
                        n = new AllCauses();
                        if (allCasesListBeen.size() > 0) {

                            n.setCaseName(allCasesListBeen.get(i).getCaseName());
                            n.setCaseDescription(allCasesListBeen.get(i).getCaseDescription());
                            n.setJoined(allCasesListBeen.get(i).isIsJoined());
                            n.setOwner(allCasesListBeen.get(i).isIsOwner());
                            n.setAmount(allCasesListBeen.get(i).getAmount());
                            n.setCauseID(allCasesListBeen.get(i).getCauseID());
                            n.setEndDate(allCasesListBeen.get(i).getEndDate());
                            n.setIMG(allCasesListBeen.get(i).getIMG());
                            n.setNumberofjoins(allCasesListBeen.get(i).getNumberofjoins());
                            n.setStatus(allCasesListBeen.get(i).getStatus());

                            n.save();
                        }
                    }

                    adapter = new All_inProfileFragmentAdapter(getActivity(), R.layout.item_in_bottom_profile, list);
                    listView.setAdapter(adapter);
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

                List<AllCauses> list = (new Select().from(AllCauses.class).queryList());

                if (list.size() > 0) {
                    adapter = new All_inProfileFragmentAdapter(getActivity(), R.layout.item_in_bottom_profile, list);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        });
    }
}
