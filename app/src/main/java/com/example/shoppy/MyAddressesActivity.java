package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shoppy.Modals.AddressModel;
import com.example.shoppy.Modals.AddressesAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.shoppy.MyAccountFragment.MANAGE_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.my_addresses_recycler_view);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();

        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",true));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));
        addressModelList.add(new AddressModel("Eren Jaeger","Maria","791",false));

        int mode = getIntent().getIntExtra("MODE",-1);

        if(mode == MANAGE_ADDRESS){
            deliverHereBtn.setVisibility(View.GONE);
        }else{
            deliverHereBtn.setVisibility(View.VISIBLE);
        }

        addressesAdapter = new AddressesAdapter(addressModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deSelect,int select){
        addressesAdapter.notifyItemChanged(deSelect);
        addressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}