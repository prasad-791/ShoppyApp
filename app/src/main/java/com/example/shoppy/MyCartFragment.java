package com.example.shoppy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoppy.Modals.CartAdapter;
import com.example.shoppy.Modals.CartItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView cartItemsRecylerView;

    private Button continuebtn;

    public MyCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
        View view=  inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemsRecylerView = view.findViewById(R.id.cart_items_recycler_view);
        continuebtn = view.findViewById(R.id.btn_cart_continue);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecylerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<CartItemModel>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,2,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,0,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,2,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));

        cartItemModelList.add(new CartItemModel(1,3,"Rs. 69999/-","free","Rs. 69999/-","Rs. 6000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecylerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent = new Intent(view.getContext(),AddAddressActivity.class);
                view.getContext().startActivity(addressIntent);
            }
        });

        return view;
    }
}