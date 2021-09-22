package com.example.shoppy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppy.Modals.RewardsAdapter;
import com.example.shoppy.Modals.RewardsItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRewardsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView myRewardsRecyclerView;

    public MyRewardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRewardsFragment newInstance(String param1, String param2) {
        MyRewardsFragment fragment = new MyRewardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_rewards, container, false);

        myRewardsRecyclerView = view.findViewById(R.id.my_rewards_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardsItemModel> rewardsItemModelList = new ArrayList<>();
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));

        RewardsAdapter rewardsAdapter = new RewardsAdapter(rewardsItemModelList,false);
        myRewardsRecyclerView.setAdapter(rewardsAdapter);
        rewardsAdapter.notifyDataSetChanged();

        return view;
    }
}