package com.cattleguru.shopping.Model;

public class Users {
    private String name, phone, image, address, referralCode;
    public Users()
    {

    }

    public Users(String name, String phone, String image, String address, String referralCode) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.referralCode = referralCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
