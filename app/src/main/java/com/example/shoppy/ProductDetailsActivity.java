package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppy.Modals.DBqueries;
import com.example.shoppy.Modals.ProductDetailsAdapter;
import com.example.shoppy.Modals.ProductImagesAdapter;
import com.example.shoppy.Modals.ProductSpecificationModel;
import com.example.shoppy.Modals.RewardsAdapter;
import com.example.shoppy.Modals.RewardsItemModel;
import com.example.shoppy.Modals.WishlistItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shoppy.MainActivity.showCart;
import static com.example.shoppy.RegisterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean RUNNING_WISHLIST_QUERY = false;

    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    public static FloatingActionButton addToWishListBtn;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;

    private LinearLayout coupenRedemptionLayout;

    private Button buyNowBtn;
    private Button redeemBtn;
    private LinearLayout addToCartBtn;

    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private TextView codIndicatorTV;
    private ImageView codIndicatorIV;

    private TextView rewardTitle;
    private TextView rewardDescription;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private ConstraintLayout productDetailsTitleOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private String productDescription;
    private String productOtherDetails;

    private TextView productDetailsOnlyDescriptionTV;

    ///////////// Coupen Dialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView dialogCoupenRecyclerView;
    private static LinearLayout selectedCoupenContainer;
    ///////////// Coupen Dialog

    private Dialog signInDialog;
    private Dialog loadingDialog;

    ///////////// Ratings Layout
    LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNumberContainer;
    private TextView totalRatingFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
    ///////////// Ratings Layout

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    public static String productID;

    private DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#555BED"));
        actionBar.setBackgroundDrawable(colorDrawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishListBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.btn_buy_now);
        redeemBtn = findViewById(R.id.coupon_redemption_btn);
        addToCartBtn = findViewById(R.id.btn_add_to_cart);

        coupenRedemptionLayout = findViewById(R.id.coupon_redemption_layout);

        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_mini_view);
        totalRatingMiniView = findViewById(R.id.total_ratings_mini_view);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        codIndicatorTV = findViewById(R.id.tv_cod_indicator);
        codIndicatorIV = findViewById(R.id.cod_indicator_imageview);

        rewardTitle = findViewById(R.id.reward_title);
        rewardDescription = findViewById(R.id.reward_description);

        productDetailsTitleOnlyContainer = findViewById(R.id.product_details_title_only_container);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);

        productDetailsOnlyDescriptionTV = findViewById(R.id.product_details_body);

        totalRatings = findViewById(R.id.tv_total_ratings);
        ratingsNumberContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progress_bar_container);
        averageRating = findViewById(R.id.average_rating);

        ///////////// loading dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_bg));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        loadingDialog.show();
        ///////////// loading dialog

        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("ProductID");
        firebaseFirestore.collection("PRODUCTS").document(productID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            documentSnapshot = task.getResult();
                            for(long i=1;i<=(long)documentSnapshot.get("no_of_product_images");i++){
                                productImages.add(documentSnapshot.get("product_image_"+i).toString());
                            }
                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);

                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                            totalRatingMiniView.setText((long)documentSnapshot.get("total_ratings")+" ratings");
                            productPrice.setText("Rs."+documentSnapshot.get("product_price").toString()+"/-");
                            cuttedPrice.setText("Rs."+documentSnapshot.get("cutted_price").toString()+"/-");

                            if((boolean)documentSnapshot.get("COD")){
                                codIndicatorIV.setVisibility(View.VISIBLE);
                                codIndicatorTV.setVisibility(View.VISIBLE);
                            }else{
                                codIndicatorIV.setVisibility(View.INVISIBLE);
                                codIndicatorTV.setVisibility(View.INVISIBLE);
                            }
                            rewardTitle.setText((long)documentSnapshot.get("free_coupens")+" "+documentSnapshot.get("free_coupen_title").toString());
                            rewardDescription.setText(documentSnapshot.get("free_coupen_body").toString());

                            if((boolean)documentSnapshot.get("use_tab_layout")){
                                productDetailsTabsContainer.setVisibility(View.VISIBLE);
                                productDetailsTitleOnlyContainer.setVisibility(View.GONE);
                                productDescription = documentSnapshot.get("product_description").toString();


                                for(long i=1;i<=(long)documentSnapshot.get("total_spec_titles");i++){
                                    productSpecificationModelList.add(new ProductSpecificationModel(
                                            0,documentSnapshot.get("spec_title_"+i).toString()));
                                    for(long j=1;j<=(long)documentSnapshot.get("spec_title_"+i+"_total_fields");j++){
                                        productSpecificationModelList.add(new ProductSpecificationModel(
                                                documentSnapshot.get("spec_title_"+i+"_field_"+j+"_name").toString(),
                                                documentSnapshot.get("spec_title_"+i+"_field_"+j+"_value").toString(),
                                                1));
                                    }
                                }
                                productOtherDetails = documentSnapshot.get("product_other_details").toString();
                            }else{
                                productDetailsTabsContainer.setVisibility(View.GONE);
                                productDetailsTitleOnlyContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyDescriptionTV.setText(documentSnapshot.get("product_description").toString());
                            }

                            totalRatings.setText((long)documentSnapshot.get("total_ratings")+" ratings");

                            for (int i=0;i<5;i++){
                                TextView starRating = (TextView)ratingsNumberContainer.getChildAt(i);
                                starRating.setText(String.valueOf((long)documentSnapshot.get((5-i)+"_star")));

                                ProgressBar progressBar = (ProgressBar)ratingsProgressBarContainer.getChildAt(i);
                                int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                                progressBar.setMax(maxProgress);
                                int currentProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-i)+"_star")));
                                progressBar.setProgress(currentProgress);
                            }
                            totalRatingFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                            averageRating.setText(documentSnapshot.get("average_rating").toString());
                            productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));

                            if(currentUser!=null) {
                                if (DBqueries.wishList.size() == 0) {
                                    DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog,false);
                                } else {
                                    loadingDialog.dismiss();
                                }
                            }else{
                                loadingDialog.dismiss();
                            }
                            if(DBqueries.wishList.contains(productID)){
                                ALREADY_ADDED_TO_WISHLIST = true;
                                addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
                            }else{
                                addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#828782")));
                                ALREADY_ADDED_TO_WISHLIST = false;
                            }
                        }else{
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        viewPagerIndicator.setupWithViewPager(productImagesViewPager,true);
        if(ALREADY_ADDED_TO_WISHLIST){
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
        }else{
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#828782")));
        }

        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                }else {
//                    addToWishListBtn.setEnabled(false);
                    if(!RUNNING_WISHLIST_QUERY) {
                        RUNNING_WISHLIST_QUERY = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBqueries.wishList.indexOf(productID);
                            DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#828782")));
                        } else {
                            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
                            Map<String, Object> proID = new HashMap<>();
                            proID.put("product_ID_" + String.valueOf(DBqueries.wishList.size()), productID);

                            firebaseFirestore.collection("Users").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .set(proID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Map<String, Object> sizeOfList = new HashMap<>();
                                        sizeOfList.put("list_size", (long) DBqueries.wishList.size() + 1);

                                        firebaseFirestore.collection("Users").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                .update(sizeOfList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DBqueries.wishlistItemModelList.size() != 0) {
                                                        DBqueries.wishlistItemModelList.add(new WishlistItemModel(
                                                                productID,
                                                                documentSnapshot.get("product_image").toString(),
                                                                documentSnapshot.get("product_title").toString(),
                                                                (long) documentSnapshot.get("free_coupens"),
                                                                documentSnapshot.get("average_ratings").toString(),
                                                                (long) documentSnapshot.get("total_ratings"),
                                                                documentSnapshot.get("product_price").toString(),
                                                                documentSnapshot.get("cutted_price").toString(),
                                                                (boolean) documentSnapshot.get("COD")
                                                        ));
                                                    }
                                                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
                                                    ALREADY_ADDED_TO_WISHLIST = true;
                                                    DBqueries.wishList.add(productID);
                                                } else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, error + " ERROR_1", Toast.LENGTH_SHORT).show();
                                                }
//                                                addToWishListBtn.setEnabled(true);
                                                RUNNING_WISHLIST_QUERY = false;
                                            }
                                        });
                                    } else {
//                                        addToWishListBtn.setEnabled(true);
                                        RUNNING_WISHLIST_QUERY = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error + " ERROR_2", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                }
            }
        });

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ///////////// Ratings Layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for(int i=0;i<rateNowContainer.getChildCount();i++) {
            final int starPosition = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser == null){
                        signInDialog.show();
                    }else{
                        setRating(starPosition);
                    }
                }
            });
        }
        ///////////// Ratings Layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    signInDialog.show();
                }else{
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    signInDialog.show();
                }else{
                    // todo: add to cart btn
                }
            }
        });


        //////////// Coupen Dialog
        final Dialog coupenRedemptionDialog = new Dialog(ProductDetailsActivity.this);
        coupenRedemptionDialog.setContentView(R.layout.coupen_redemption_dialog);
        coupenRedemptionDialog.setCancelable(true);
        coupenRedemptionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView openRecycleCoupenViewBtn = coupenRedemptionDialog.findViewById(R.id.dialog_toggle_recycler_view);
        dialogCoupenRecyclerView = coupenRedemptionDialog.findViewById(R.id.dialog_coupens_recycler_view);
        selectedCoupenContainer = coupenRedemptionDialog.findViewById(R.id.dialog_selected_coupen);

        coupenTitle = selectedCoupenContainer.findViewById(R.id.rewards_coupon_title);
        coupenExpiryDate = selectedCoupenContainer.findViewById(R.id.rewards_coupon_validity_tv);
        coupenBody = selectedCoupenContainer.findViewById(R.id.rewards_coupon_body_tv);

        TextView originalPrice = coupenRedemptionDialog.findViewById(R.id.dialog_original_price);
        TextView reducedPrice = coupenRedemptionDialog.findViewById(R.id.dialog_reduced_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialogCoupenRecyclerView.setLayoutManager(layoutManager);

        List<RewardsItemModel> rewardsItemModelList = new ArrayList<>();
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Rewards","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("CashBack","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));
        rewardsItemModelList.add(new RewardsItemModel("Discount","valid till Mon, 24th May 2021",
                "Get 50% off on any product above Rs. 500/- and below Rs.2000/-"));

        RewardsAdapter adapter = new RewardsAdapter(rewardsItemModelList,true);
        dialogCoupenRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        openRecycleCoupenViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRecyclerView();
            }
        });
        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupenRedemptionDialog.show();
            }
        });
        //////////// Coupen Dialog

        /////////// signin dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.dialog_signin_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.dialog_signup_btn);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this,RegisterActivity.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        ////////////// sigin dialog

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            coupenRedemptionLayout.setVisibility(View.GONE);
        }else{
            coupenRedemptionLayout.setVisibility(View.VISIBLE);
        }

        if(currentUser!=null) {
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog,false);
            } else {
                loadingDialog.dismiss();
            }
        }else{
            loadingDialog.dismiss();
        }
        if(DBqueries.wishList.contains(productID)){
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#555BED")));
        }else{
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#828782")));
            ALREADY_ADDED_TO_WISHLIST = false;
        }
    }

    public static void toggleRecyclerView(){
        if (dialogCoupenRecyclerView.getVisibility() == View.GONE) {
            dialogCoupenRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupenContainer.setVisibility(View.GONE);
        }else{
            dialogCoupenRecyclerView.setVisibility(View.GONE);
            selectedCoupenContainer.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }else if(id==R.id.action_search) {
            // todo: appbar action search
            return true;
        }else if(id == R.id.action_cart){

            if(currentUser == null){
                signInDialog.show();
                return true;
            }else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
}