package com.example.shoppy.Modals;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppy.CategoryActivity;
import com.example.shoppy.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private int lastPosition = -1;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String icon = categoryModelList.get(position).getIcon_link();
        String name = categoryModelList.get(position).getCategory_name();
        holder.setCategory(name,position);
        holder.setCategory_img(icon);

//        if(lastPosition<position){
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),);
//            holder.itemView.setAnimation(animation);
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView category_img;
        private TextView category_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_img = itemView.findViewById(R.id.category_icon);
            category_tv = itemView.findViewById(R.id.category_name);
        }

        private void setCategory_img(String iconURL){
            // todo: set category icon
            if(iconURL.equals("null")){
                category_img.setImageResource(R.drawable.ic_baseline_home_24);
                return ;
            }
            Glide.with(itemView.getContext()).load(iconURL).apply(new RequestOptions()
                    .placeholder(R.drawable.ic_placeholder_image))
                    .into(category_img);
        }
        private void setCategory(final String name,final int position){
            category_tv.setText(name);
            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent intent = new Intent(itemView.getContext(), CategoryActivity.class);
                            intent.putExtra("CategoryName", name);
                            itemView.getContext().startActivity(intent);
                        }
                    }
                });
            }
        }

    }
}
