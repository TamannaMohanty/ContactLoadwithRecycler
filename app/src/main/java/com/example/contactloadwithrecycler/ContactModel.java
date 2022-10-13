package com.example.contactloadwithrecycler;

import android.net.Uri;

public class ContactModel {

    String name,number,image;
    String alertImage, alertName;
    Uri imageUri;

    //URI- Uniform resource identifier to identify resource of image

    public ContactModel(String name, String number, String image, String alertImage, String alertName, Uri imageUri) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.alertImage = alertImage;
        this.alertName = alertName;
        this.imageUri = imageUri;
    }

    public String getAlertImage() {
        return alertImage;
    }

    public void setAlertImage(String alertImage) {
        this.alertImage = alertImage;
    }

    public ContactModel(String name, String number, String image, String alertName, Uri imageUri) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.alertName = alertName;
        this.imageUri = imageUri;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public ContactModel(String name, String number, Uri imageUri) {
        this.name = name;
        this.number = number;
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public ContactModel(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public ContactModel(String name, String number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContactModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
