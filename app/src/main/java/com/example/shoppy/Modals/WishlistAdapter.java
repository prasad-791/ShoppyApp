package com.example.shoppy.Modals;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppy.ProductDetailsActivity;
import com.example.shoppy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.Viewholder> {

    private List<WishlistItemModel> wishlistItemModelList;
    private boolean wishlist;
    private int lastPostion = -1;

    public WishlistAdapter(List<WishlistItemModel> wishlistItemModelList,boolean wishlist) {
        this.wishlistItemModelList = wishlistItemModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @NotNull
    @Override
    public WishlistAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WishlistAdapter.Viewholder holder,int position) {

        String productID = wishlistItemModelList.get(holder.getAdapterPosition()).getProductID();
        String resource = wishlistItemModelList.get(holder.getAdapterPosition()).getProductImg();
        String title = wishlistItemModelList.get(holder.getAdapterPosition()).getProductTitle();
        long noOfCoupons = wishlistItemModelList.get(holder.getAdapterPosition()).getFreeCoupons();
        String price = wishlistItemModelList.get(holder.getAdapterPosition()).getProductPrice();
        String cutPrice = wishlistItemModelList.get(holder.getAdapterPosition()).getCuttedPrice();
        String ratings = wishlistItemModelList.get(holder.getAdapterPosition()).getRatings();
        long totalRatings = wishlistItemModelList.get(holder.getAdapterPosition()).getTotalRatings();
        boolean payMethod = wishlistItemModelList.get(holder.getAdapterPosition()).isCOD();

        holder.setData(productID,resource,title,noOfCoupons,ratings,totalRatings,price,cutPrice,payMethod,holder.getAdapterPosition());

        if(lastPostion< holder.getAdapterPosition()){
            lastPostion = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return wishlistItemModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView productImg;
        private ImageView couponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private TextView totalRatings;
        private TextView ratings;
        private View priceCutDivider;
        private ImageButton deleteBtn;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.wishlist_product_image);
            couponIcon = itemView.findViewById(R.id.wishlist_coupon_icon);
            productTitle = itemView.findViewById(R.id.wishlist_product_title);
            freeCoupons = itemView.findViewById(R.id.wishlist_free_coupon_tv);
            productPrice = itemView.findViewById(R.id.wishlist_product_price);
            cuttedPrice = itemView.findViewById(R.id.wishlist_cutted_price);
            paymentMethod = itemView.findViewById(R.id.wishlist_payment_method);
            ratings = itemView.findViewById(R.id.tv_product_rating_mini_view);
            totalRatings = itemView.findViewById(R.id.wishlist_total_ratings_tv);
            priceCutDivider = itemView.findViewById(R.id.price_cut_divider);
            deleteBtn = itemView.findViewById(R.id.wishlist_remove_btn);
        }
        private void setData(String productid,String resource,String title,long noOfCoupons,String rating,long totalRating,String price,String cutPrice,boolean COD,int index)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_placeholder_image)).into(productImg);
            productTitle.setText(title);
            if(noOfCoupons!=0){
                couponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setText("Free "+noOfCoupons+" Coupon(s)");
            }else{
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            ratings.setText(rating);
            totalRatings.setText(totalRating+" ratings");
            productPrice.setText("Rs."+price+"/-");
            cuttedPrice.setText("Rs."+cutPrice+"/-");
            if(COD){
                paymentMethod.setVisibility(View.VISIBLE);
            }else{
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else{
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    deleteBtn.setEnabled(false);
                    if(!ProductDetailsActivity.RUNNING_WISHLIST_QUERY) {
                        ProductDetailsActivity.RUNNING_WISHLIST_QUERY = true;
                        DBqueries.removeFromWishlist(index, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("ProductID",productid);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
