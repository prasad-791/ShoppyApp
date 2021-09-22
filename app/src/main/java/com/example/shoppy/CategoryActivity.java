package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shoppy.Modals.CategoryAdapter;
import com.example.shoppy.Modals.CategoryModel;
import com.example.shoppy.Modals.HomePageAdapter;
import com.example.shoppy.Modals.HomePageModel;
import com.example.shoppy.Modals.HorizontalProductScrollModel;
import com.example.shoppy.Modals.SliderModel;
import com.example.shoppy.Modals.WishlistItemModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.shoppy.Modals.DBqueries.lists;
import static com.example.shoppy.Modals.DBqueries.loadFragmentData;
import static com.example.shoppy.Modals.DBqueries.loadedCategoriesNames;


public class CategoryActivity extends AppCompatActivity {

    private RecyclerView particularCategoryRecyclerView;
    private HomePageAdapter homePageAdapter;

    private List<HomePageModel> homePageModelPlaceholderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setting action bar color
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#555BED"));
        actionBar.setBackgroundDrawable(colorDrawable);

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting title as category name
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);

        particularCategoryRecyclerView = findViewById(R.id.particular_category_recycler_view);

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

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        particularCategoryRecyclerView.setLayoutManager(testingLayoutManager);

        homePageAdapter = new HomePageAdapter(homePageModelPlaceholderList);

        int listPosition = 0;
        for (int i=0;i<loadedCategoriesNames.size();i++){
            if(loadedCategoriesNames.get(i).equals(title.toUpperCase())){
                listPosition = i;
            }
        }

        if(listPosition == 0){
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(particularCategoryRecyclerView,this,loadedCategoriesNames.size()-1,title);
        }else{
            homePageAdapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
            homePageAdapter.notifyDataSetChanged();
        }
        particularCategoryRecyclerView .setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_search){
            // todo: appbar action search
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}