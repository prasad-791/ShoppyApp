    package com.example.shoppy;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppy.Modals.CategoryAdapter;
import com.example.shoppy.Modals.CategoryModel;
import com.example.shoppy.Modals.DBqueries;
import com.example.shoppy.Modals.GridProductLayoutAdapter;
import com.example.shoppy.Modals.HomePageAdapter;
import com.example.shoppy.Modals.HomePageModel;
import com.example.shoppy.Modals.HorizontalProductScrollAdapter;
import com.example.shoppy.Modals.HorizontalProductScrollModel;
import com.example.shoppy.Modals.SliderAdapter;
import com.example.shoppy.Modals.SliderModel;
import com.example.shoppy.Modals.WishlistItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.shoppy.Modals.DBqueries.categoryModelList;
import static com.example.shoppy.Modals.DBqueries.lists;
import static com.example.shoppy.Modals.DBqueries.loadCategories;
import static com.example.shoppy.Modals.DBqueries.loadFragmentData;
import static com.example.shoppy.Modals.DBqueries.loadedCategoriesNames;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelPlaceholderList = new ArrayList<>();

    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelPlaceholderList = new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private ImageView noInternetConnectionImg;

    public static SwipeRefreshLayout swipeRefreshLayout;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryBtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.purple_200),
                getContext().getResources().getColor(R.color.purple_200),
                getContext().getResources().getColor(R.color.purple_200));

        noInternetConnectionImg = view.findViewById(R.id.no_internet_connection_image);
        retryBtn = view.findViewById(R.id.retry_btn);

        recyclerView = view.findViewById(R.id.category_recycler_view);
        homePageRecyclerView  = view.findViewById(R.id.home_page_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        ///////////////////// categories placeholder list
        categoryModelPlaceholderList.add(new CategoryModel("null",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        categoryModelPlaceholderList.add(new CategoryModel("",""));
        ///////////////////// categories placeholder list

        ///////////////////// home placeholder list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));


        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));

        homePageModelPlaceholderList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelPlaceholderList.add(new HomePageModel(1,"","#dfdfdf"));
        homePageModelPlaceholderList.add(new HomePageModel(2,"","#dfdfdf",horizontalProductScrollModelFakeList,new ArrayList<WishlistItemModel>()));
        homePageModelPlaceholderList.add(new HomePageModel(3,"","#dfdfdf",horizontalProductScrollModelFakeList));
        ///////////////////// home placeholder list

        categoryAdapter = new CategoryAdapter(categoryModelPlaceholderList);

        homePageAdapter = new HomePageAdapter(homePageModelPlaceholderList);

        connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() == true){
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetConnectionImg.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if(categoryModelList.size() == 0){
                loadCategories(recyclerView,getContext());
            }else{
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            recyclerView.setAdapter(categoryAdapter);

            if(lists.size() == 0){
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"Home");
            }else{
                homePageAdapter = new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);
        }else{
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            recyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_gif).into(noInternetConnectionImg);
            noInternetConnectionImg.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        ///////////////// refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });
        ///////////////// refresh layout

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });
        return view;
    }
    private void reloadPage(){
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetConnectionImg.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModelPlaceholderList);
            recyclerView.setAdapter(categoryAdapter);

            homePageAdapter = new HomePageAdapter(homePageModelPlaceholderList);
            homePageRecyclerView.setAdapter(homePageAdapter);

            loadCategories(recyclerView,getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView,getContext(),0,"Home");
        }else{
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            Glide.with(getContext()).load(R.drawable.no_internet_gif).into(noInternetConnectionImg);
            noInternetConnectionImg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}