package com.example.donth.crudtsf;

public class userdata {
    private String mName;
    private String mLocation;
    private String mLinks;
    private String mMobile;

    public userdata(String mName, String mLocation, String mLinks, String mMobile) {
        this.mName = mName;
        this.mLocation = mLocation;
        this.mLinks = mLinks;
        this.mMobile = mMobile;
    }

    public String getName() {
        return mName;
    }

    public String getLoc() {
        return mLocation;
    }

    public String getmLinks() {
        return mLinks;
    }

    public String getmMobile() {
        return mMobile;
    }
}
