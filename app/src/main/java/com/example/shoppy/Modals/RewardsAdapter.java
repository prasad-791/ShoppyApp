package com.example.shoppy.Modals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppy.ProductDetailsActivity;
import com.example.shoppy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.shoppy.ProductDetailsActivity.coupenBody;
import static com.example.shoppy.ProductDetailsActivity.coupenExpiryDate;
import static com.example.shoppy.ProductDetailsActivity.coupenTitle;
import static com.example.shoppy.ProductDetailsActivity.toggleRecyclerView;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.Viewholder> {

   private List<RewardsItemModel> rewardsItemModelList;
   private boolean useMiniLayout = false;

    public RewardsAdapter(List<RewardsItemModel> rewardsItemModelList,boolean useMiniLayout) {
        this.rewardsItemModelList = rewardsItemModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if(useMiniLayout){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout,parent,false);
        }
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
        String title = rewardsItemModelList.get(position).getRewardTitle();
        String validity = rewardsItemModelList.get(position).getValidDate();
        String body = rewardsItemModelList.get(position).getRewardBody();

        holder.setData(title,validity,body);
    }

    @Override
    public int getItemCount() {
        return rewardsItemModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView rewardTitle;
        private TextView validDate;
        private TextView rewardBody;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            rewardTitle = itemView.findViewById(R.id.rewards_coupon_title);
            validDate = itemView.findViewById(R.id.rewards_coupon_validity_tv);
            rewardBody = itemView.findViewById(R.id.rewards_coupon_body_tv);
        }
        private void setData(String title,String validity,String body) {
            rewardTitle.setText(title);
            validDate.setText(validity);
            rewardBody.setText(body);
            if(useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coupenTitle.setText(title);
                        coupenBody.setText(body);
                        coupenExpiryDate.setText(validity);
                        toggleRecyclerView();
                    }
                });
            }
        }
    }
}
