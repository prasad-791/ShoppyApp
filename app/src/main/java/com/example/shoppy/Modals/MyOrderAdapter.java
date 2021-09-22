package com.example.shoppy.Modals;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.OrderDetailsActivity;
import com.example.shoppy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @NotNull
    @Override
    public MyOrderAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyOrderAdapter.Viewholder holder, int position) {
        int resource = myOrderItemModelList.get(position).getProductImg();
        int rating = myOrderItemModelList.get(position).getRating();
        String title = myOrderItemModelList.get(position).getProductTitle();
        String status = myOrderItemModelList.get(position).getDeliveryStatus();

        holder.setData(resource,title,status,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder{
        private TextView productTitle;
        private TextView deliveryStatus;
        private ImageView productImg;
        private ImageView orderIndicator;
        private LinearLayout rateNowContainer;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.my_order_product_title);
            productImg = itemView.findViewById(R.id.my_order_product_img);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
            rateNowContainer = itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });
        }
        private void setData(int resource,String title,String status,int rating)
        {
            productImg.setImageResource(resource);
            productTitle.setText(title);
            if(status.toUpperCase().equals("CANCELLED") || status.toUpperCase().equals("FAILED")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.failure)));
            }else{
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.success)));
            }
            deliveryStatus.setText(status);

            setRating(rating-1);
            for(int i=0;i<rateNowContainer.getChildCount();i++) {
                final int starPosition = i;
                rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }

        }
        private void setRating(int starPosition) {
            for(int i=0;i<rateNowContainer.getChildCount();i++){
                ImageView starbtn = (ImageView)rateNowContainer.getChildAt(i);
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#838080")));
                if(i<=starPosition){
                    starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F8A80C")));
                }
            }
        }
    }
}
