package com.example.shoppy.Modals;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppy.ProductDetailsActivity;
import com.example.shoppy.R;
import com.example.shoppy.ViewAllActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition = -1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomePageModel.STRIP_AD_BANNER:
                View stripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewholder(stripAdView);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewholder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewholder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder) holder).setBanner_slider_view_pager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String bgColor = homePageModelList.get(position).getBgColor();
                ((StripAdBannerViewholder) holder).setStripAd(resource, bgColor);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                String horizontalProductLayoutTitle = homePageModelList.get(position).getTitle();
                String color = homePageModelList.get(position).getBgColor();
                List<WishlistItemModel> viewAllProductList = homePageModelList.get(position).getViewAllProductList();
                ((HorizontalProductViewholder) holder).setHorizontalLayout(horizontalProductScrollModelList, horizontalProductLayoutTitle,color,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                List<HorizontalProductScrollModel> gridProductModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                String gridLayoutTitle = homePageModelList.get(position).getTitle();
                String gridBgColor = homePageModelList.get(position).getBgColor();
                ((GridProductViewholder) holder).setGridProductLayout(gridProductModelList, gridLayoutTitle,gridBgColor);
                break;
            default:
                return;
        }
//        if(lastPosition<position){
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),);
//            holder.itemView.setAnimation(animation);
//            lastPosition = position;
//        }
        if(lastPosition<position){
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder {

        private ViewPager banner_slider_view_pager;
        private int current_page;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            banner_slider_view_pager = itemView.findViewById(R.id.banner_slider_view_pager);
        }

        private void setBanner_slider_view_pager(final List<SliderModel> sliderModelList) {
            current_page = 2;
            if(timer != null){
                timer.cancel();
            }

            arrangedList = new ArrayList<>();
            for(int i=0;i<sliderModelList.size();i++){
                arrangedList.add(i,sliderModelList.get(i));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));

            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
            banner_slider_view_pager.setAdapter(sliderAdapter);
            banner_slider_view_pager.setClipToPadding(false);
            banner_slider_view_pager.setPageMargin(20);

            banner_slider_view_pager.setCurrentItem(current_page);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    current_page = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pager_looper(arrangedList);
                    }
                }
            };
            banner_slider_view_pager.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideShow(arrangedList);

            banner_slider_view_pager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pager_looper(arrangedList);
                    stopBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (current_page >= sliderModelList.size()) {
                        current_page = 1;
                    }
                    banner_slider_view_pager.setCurrentItem(current_page++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }

        private void pager_looper(final List<SliderModel> sliderModelList) {
            if (current_page == sliderModelList.size() - 2) {
                current_page = 2;
                banner_slider_view_pager.setCurrentItem(current_page, false);
            }
            if (current_page == 1) {
                current_page = sliderModelList.size() - 3;
                banner_slider_view_pager.setCurrentItem(current_page, false);
            }
        }
    }

    public class StripAdBannerViewholder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewholder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_img);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource, String bgColor) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_placeholder_image)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(bgColor));
        }
    }

    public class HorizontalProductViewholder extends RecyclerView.ViewHolder {
        private ConstraintLayout horizontalProductScrollContainer;
        private TextView horizontallayoutTitle;
        private Button horizontalviewAllBtn;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            horizontalProductScrollContainer = itemView.findViewById(R.id.horizontal_product_scroll_container);
            horizontallayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalviewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_view_all_btn);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recycler_view);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title,String bgColor,final List<WishlistItemModel> viewAllProductList) {
            horizontalProductScrollContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgColor)));
            horizontallayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 8) {
                horizontalviewAllBtn.setVisibility(View.VISIBLE);
                horizontalviewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistItemModelList = viewAllProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("Title",title);
                        viewAllIntent.putExtra("LayoutCode",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalviewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager1);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewholder extends RecyclerView.ViewHolder{
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;
        private ConstraintLayout gridProductContainer;

        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);
            gridProductContainer = itemView.findViewById(R.id.gridProductContainer);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewAll_btn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }
        public void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String bgColor){
            gridProductContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgColor)));
            gridLayoutTitle.setText(title);

            for(int i=0;i<4;i++){
                ImageView productImg = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_img);
                TextView productTitle = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_title);
                TextView productDescription = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_description);
                TextView productPrice = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(i).getProductImg())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_placeholder_image)).into(productImg);

                productTitle.setText(horizontalProductScrollModelList.get(i).getProductTitle());
                productDescription.setText(horizontalProductScrollModelList.get(i).getProductDescription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(i).getProductPrice()+"/-");

                gridProductLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

                if(!title.equals("")){
                    int finalI = i;
                    gridProductLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                            productDetailsIntent.putExtra("ProductID",horizontalProductScrollModelList.get(finalI).getProductID());
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });
                }
            }
            if(!title.equals("")){
                gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("Title",title);
                        viewAllIntent.putExtra("LayoutCode",1);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }
        }
    }

}
