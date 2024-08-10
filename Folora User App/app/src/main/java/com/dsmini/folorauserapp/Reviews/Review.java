package com.dsmini.folorauserapp.Reviews;


public class Review {

    String name,review;


    Review()
    {

    }


    public Review(String name, String review) {
        this.name = name;
        this.review = review;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}
