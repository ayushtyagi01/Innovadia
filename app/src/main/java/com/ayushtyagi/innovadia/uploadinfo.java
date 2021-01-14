package com.ayushtyagi.innovadia;

public class uploadinfo {

    public String imageName;
    public String imageURL;
    public  String imageCost;
    public  String imagePhone;
    public uploadinfo(){}

    public uploadinfo(String name,String imageCost,String imagePhone, String url) {
        this.imageName = name;
        this.imageURL = url;
        this.imageCost=imageCost;
        this.imagePhone=imagePhone;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageCost() {
        return imageCost;
    }
    public String getImagePhone() {
        return imagePhone;
    }
    public String getImageURL() {
        return imageURL;
    }

}

