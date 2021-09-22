package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shoppy.Modals.CartAdapter;
import com.example.shoppy.Modals.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeAddressBtn;
    public static final int SELECT_ADDRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerView = findViewById(R.id.delivery_recycler_view);
        changeAddressBtn = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<CartItemModel>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,2,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,0,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_eg,2,3,1,2,"Realme 3 pro (128GB Black)","Rs. 17999/-","Rs. 19999/-"));

        cartItemModelList.add(new CartItemModel(1,3,"Rs. 69999/-","free","Rs. 69999/-","Rs. 6000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeAddressBtn.setVisibility(View.VISIBLE);
        changeAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                myAddressIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}