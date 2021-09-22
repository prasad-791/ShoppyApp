package com.example.shoppy.Modals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.shoppy.DeliveryActivity.SELECT_ADDRESS;
import static com.example.shoppy.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.shoppy.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {

    private List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPosition = -1;

    public AddressesAdapter(List<AddressModel> addressModelList,int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
    }

    @NonNull
    @NotNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddressesAdapter.Viewholder holder, int position) {
        String name = addressModelList.get(position).getFullName();
        String address = addressModelList.get(position).getFullAddress();
        String pincode = addressModelList.get(position).getPinCode();
        boolean selected = addressModelList.get(position).isSelected();
        holder.setData(name,address,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView fullAddress;
        private TextView pinCode;
        private ImageView icon;
        private LinearLayout optionsContainer;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.address_item_name);
            fullAddress = itemView.findViewById(R.id.address_item_address);
            pinCode = itemView.findViewById(R.id.address_item_pincode);
            icon = itemView.findViewById(R.id.my_address_icon_view_img);
            optionsContainer = itemView.findViewById(R.id.options_container);
        }
        private void setData(String name,String address,String pincode,boolean selected,int position){
            fullName.setText(name);
            fullAddress.setText(address);
            pinCode.setText(pincode);

            if(MODE == SELECT_ADDRESS){
                icon.setImageResource(R.drawable.ic_checked);
                if(selected){
                    preSelectedPosition = position;
                    icon.setVisibility(View.VISIBLE);
                }else{
                    icon.setVisibility(View.GONE);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(preSelectedPosition != position){
                                addressModelList.get(position).setSelected(true);
                                addressModelList.get(preSelectedPosition).setSelected(false);
                                refreshItem(preSelectedPosition,position);
                                preSelectedPosition = position;
                            }
                        }
                    });
                }
            }else if(MODE == MANAGE_ADDRESS){
                optionsContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.ic_vertical_dots_menu);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }
        }
    }
}
