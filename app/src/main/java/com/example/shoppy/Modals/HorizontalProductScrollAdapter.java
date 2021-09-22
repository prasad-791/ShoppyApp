package com.example.shoppy.Modals;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppy.ProductDetailsActivity;
import com.example.shoppy.R;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImg();
        String title = horizontalProductScrollModelList.get(position).getProductTitle();
        String description = horizontalProductScrollModelList.get(position).getProductDescription();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();
        String id = horizontalProductScrollModelList.get(position).getProductID();
        holder.setData(id,resource,title,description,price);
    }

    @Override
    public int getItemCount() {
        if(horizontalProductScrollModelList.size() > 8){
            return 8;
        }
        return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.hs_product_img);
            productTitle = itemView.findViewById(R.id.hs_product_title);
            productDescription = itemView.findViewById(R.id.hs_product_description);
            productPrice = itemView.findViewById(R.id.hs_product_price);

        }
        private void setData(String ProductID,String resource,String title,String description,String price){

            Glide.with(itemView.getContext()).load(resource)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_placeholder_image)).into(productImg);

            productTitle.setText(title);
            productDescription.setText(description);
            productPrice.setText("Rs."+price+"/-");

            if(!title.equals("")){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        intent.putExtra("ProductID",ProductID);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
        }
    }
}
