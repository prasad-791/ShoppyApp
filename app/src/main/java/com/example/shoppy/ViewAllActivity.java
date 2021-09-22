package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.shoppy.Modals.GridProductLayoutAdapter;
import com.example.shoppy.Modals.HorizontalProductScrollModel;
import com.example.shoppy.Modals.WishlistAdapter;
import com.example.shoppy.Modals.WishlistItemModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;

    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    public static List<WishlistItemModel> wishlistItemModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        viewAllRecyclerView = findViewById(R.id.viewall_recycler_view);
        viewAllGridView = findViewById(R.id.viewall_grid_view);
        String title = getIntent().getStringExtra("Title");
        int layoutCode = getIntent().getIntExtra("LayoutCode",-1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(layoutCode == 0){
            viewAllGridView.setVisibility(View.GONE);
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            viewAllRecyclerView.setLayoutManager(layoutManager);


            WishlistAdapter adapter = new WishlistAdapter(wishlistItemModelList,false);
            viewAllRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else if(layoutCode == 1){
            viewAllRecyclerView.setVisibility(View.GONE);
            viewAllGridView.setVisibility(View.VISIBLE);



            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            viewAllGridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}