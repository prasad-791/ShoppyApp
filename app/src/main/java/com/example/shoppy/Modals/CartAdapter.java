package com.example.shoppy.Modals;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.ProductDetailsActivity;
import com.example.shoppy.R;

import java.util.List;

public class    CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_details_layout, parent, false);
                return new TotalItemViewHolder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                int resource = cartItemModelList.get(position).getProduct_img();
                String title = cartItemModelList.get(position).getProduct_title();
                int freeCoupons = cartItemModelList.get(position).getFree_coupons();
                String productPrice = cartItemModelList.get(position).getProduct_price();
                String cuttedPrice = cartItemModelList.get(position).getCutted_price();
                int offersApplied = cartItemModelList.get(position).getOffers_applied();

                ((CartItemViewHolder)holder).setItemDetails(resource,title,freeCoupons,productPrice,cuttedPrice,offersApplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = cartItemModelList.get(position).getTotal_items();
                String totalPrice = cartItemModelList.get(position).getTotal_items_price();
                String deliveryPrice = cartItemModelList.get(position).getDelivery_price();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSaved_price();

                ((TotalItemViewHolder)holder).setTotalPriceDetails(totalItems,totalPrice,deliveryPrice,totalAmount,savedAmount);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImg;
        private ImageView freeCouponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView productCuttedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.cart_product_img);
            freeCouponIcon = itemView.findViewById(R.id.free_coupon_icon);
            productTitle = itemView.findViewById(R.id.cart_product_title);
            freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
            productPrice = itemView.findViewById(R.id.tv_cart_product_price);
            productCuttedPrice = itemView.findViewById(R.id.cart_cutted_price);
            offersApplied = itemView.findViewById(R.id.tv_cart_offers_applied);
            couponsApplied = itemView.findViewById(R.id.tv_cart_coupons_applied);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
        }

        private void setItemDetails(int resource, String title, int freeCouponsNo, String price, String cuttedPrice, int OffersAppliedNo) {
            productImg.setImageResource(resource);
            productTitle.setText(title);
            if (freeCouponsNo > 0) {
                freeCouponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                freeCoupons.setText("free " + freeCouponsNo + " Coupon/s");
            } else {
                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(price);
            productCuttedPrice.setText(cuttedPrice);
            if (OffersAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(OffersAppliedNo + " Offers Applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);

                    EditText quantityNo = quantityDialog.findViewById(R.id.dialog_count_tv);
                    Button cancelBtn = quantityDialog.findViewById(R.id.dialog_cancel_btn);
                    Button okBtn = quantityDialog.findViewById(R.id.dialog_ok_btn);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!quantityNo.getText().toString().equals("")){
                                productQuantity.setText("Qty: "+quantityNo.getText().toString());
                            }
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });
        }
    }

    class TotalItemViewHolder extends RecyclerView.ViewHolder {
        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public TotalItemViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.tv_total_items);
            totalItemsPrice = itemView.findViewById(R.id.tv_total_items_price);
            deliveryPrice = itemView.findViewById(R.id.tv_delivery_price);
            totalAmount = itemView.findViewById(R.id.tv_total_amount);
            savedAmount = itemView.findViewById(R.id.tv_saved_amount);
        }

        private void setTotalPriceDetails(int totalItemNo, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText("Price ("+ totalItemNo + " item)");
            totalItemsPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText("You saved "+savedAmountText+" on this purchase");
        }
    }
}
