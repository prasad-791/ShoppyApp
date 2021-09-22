package com.example.shoppy.Modals;

public class AddressModel {

    private String fullName;
    private String fullAddress;
    private String pinCode;
    private boolean selected;

    public AddressModel(String fullName, String fullAddress, String pinCode,boolean selected) {
        this.fullName = fullName;
        this.fullAddress = fullAddress;
        this.pinCode = pinCode;
        this.selected = selected;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFullAddress() {
        return fullAddress;
    }
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
