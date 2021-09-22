package com.example.shoppy.Modals;

public class RewardsItemModel {
    private String rewardTitle;
    private String validDate;
    private String rewardBody;

    public RewardsItemModel(String rewardTitle, String validDate, String rewardBody) {
        this.rewardTitle = rewardTitle;
        this.validDate = validDate;
        this.rewardBody = rewardBody;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }
    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }
    public String getValidDate() {
        return validDate;
    }
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
    public String getRewardBody() {
        return rewardBody;
    }
    public void setRewardBody(String rewardBody) {
        this.rewardBody = rewardBody;
    }
}
