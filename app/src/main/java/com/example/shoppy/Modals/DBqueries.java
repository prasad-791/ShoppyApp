package com.example.shoppy.Modals;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shoppy.HomeFragment;
import com.example.shoppy.MyWishlistFragment;
import com.example.shoppy.ProductDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBqueries {


    public static FirebaseFirestore  firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();


    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistItemModel> wishlistItemModelList = new ArrayList<>();


    public static void loadCategories(final RecyclerView categoryRecyclerView, Context context){
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                categoryModelList.add(new CategoryModel(queryDocumentSnapshot.get("icon").toString(),
                                        queryDocumentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Log.i("categoryFirebaseError",error);
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(final RecyclerView homePageRecyclerView, Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES").document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                if((long)queryDocumentSnapshot.get("view_type") == 0){
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long)queryDocumentSnapshot.get("no_of_banners");
                                    for (long i=1;i<=no_of_banners;i++){
                                        sliderModelList.add(new SliderModel(queryDocumentSnapshot.get("banner_"+i).toString()
                                                ,queryDocumentSnapshot.get("banner_"+i+"_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0,sliderModelList));
                                }else if((long)queryDocumentSnapshot.get("view_type") == 1){

                                    lists.get(index).add(new HomePageModel(1,
                                            queryDocumentSnapshot.get("strip_ad_banner").toString(),
                                            queryDocumentSnapshot.get("background").toString()));
                                }else if((long)queryDocumentSnapshot.get("view_type") == 2){

                                    List<WishlistItemModel> viewAllProductList = new ArrayList<>();

                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
                                    for(long i=1;i<=no_of_products;i++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                                queryDocumentSnapshot.get("product_ID_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_image_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_title_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_subtitle_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_price_"+i).toString()));

                                        viewAllProductList.add(new WishlistItemModel(
                                                queryDocumentSnapshot.get("product_ID_"+i).toString(),
                                                queryDocumentSnapshot.get("product_image_"+i).toString(),
                                                queryDocumentSnapshot.get("product_full_title_"+i).toString(),
                                                (long)queryDocumentSnapshot.get("free_coupens_"+i),
                                                queryDocumentSnapshot.get("average_ratings_"+i).toString(),
                                                (long)queryDocumentSnapshot.get("total_ratings_"+i),
                                                queryDocumentSnapshot.get("product_price_"+i).toString(),
                                                queryDocumentSnapshot.get("cutted_price_"+i).toString(),
                                                (boolean)queryDocumentSnapshot.get("COD_"+i)
                                        ));
                                    }
                                    lists.get(index).add(new HomePageModel(2,
                                            queryDocumentSnapshot.get("layout_title").toString(),
                                            queryDocumentSnapshot.get("layout_background").toString()
                                            ,horizontalProductScrollModelList,viewAllProductList));

                                }else if((long)queryDocumentSnapshot.get("view_type") == 3){

                                    List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long)queryDocumentSnapshot.get("no_of_products");
                                    for(long i=1;i<=no_of_products;i++){
                                        gridLayoutModelList.add(new HorizontalProductScrollModel(
                                                queryDocumentSnapshot.get("product_ID_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_image_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_title_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_subtitle_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_price_"+i).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(3,
                                            queryDocumentSnapshot.get("layout_title").toString(),
                                            queryDocumentSnapshot.get("layout_background").toString()
                                            ,gridLayoutModelList));
                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        }else{
                            String error = task.getException().getMessage();
                            Log.i("homeFirebaseError",error);
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishlist(final Context context, Dialog dialog,final boolean loadProductData){
        wishList.clear();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(long i = 0;i<(long)task.getResult().get("list_size");i++){
                        wishList.add(task.getResult().get("product_ID_"+i).toString());

                        if(DBqueries.wishList.contains(ProductDetailsActivity.productID)){
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                            if(ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
                            }
                        }else{
                            if(ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#828782")));
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        }

                        if(loadProductData) {
                            wishlistItemModelList.clear();
                            String prodid = task.getResult().get("product_ID_" + i).toString();
                            firebaseFirestore.collection("PRODUCTS").document(prodid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishlistItemModelList.add(new WishlistItemModel(
                                                prodid,
                                                task.getResult().get("product_image_1").toString(),
                                                task.getResult().get("product_title").toString(),
                                                (long) task.getResult().get("free_coupens"),
                                                task.getResult().get("average_rating").toString(),
                                                (long) task.getResult().get("total_ratings"),
                                                task.getResult().get("product_price").toString(),
                                                task.getResult().get("cutted_price").toString(),
                                                (boolean) task.getResult().get("COD")
                                        ));
                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishlist(int index,final Context context){
        wishList.remove(index);
        Map<String,Object> updateWishlist = new HashMap<>();

        for(int i=0;i<wishList.size();i++){
            updateWishlist.put("product_ID_"+i,wishList.get(i));
        }
        updateWishlist.put("list_size",(long)wishList.size());

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(wishlistItemModelList.size()!=0){
                        wishlistItemModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                }else{
                    if(ProductDetailsActivity.addToWishListBtn!=null) {
                        ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
//                if(ProductDetailsActivity.addToWishListBtn != null) {
//                    ProductDetailsActivity.addToWishListBtn.setEnabled(true);
//                }
                ProductDetailsActivity.RUNNING_WISHLIST_QUERY = false;
            }
        });
    }

    public static void clearData(){
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishList.clear();
        wishlistItemModelList.clear();
    }
}
