package com.example.ankitraj.myrecycleview;

public class ListItem {

    private String heading;
    private String desc;
    private String imageurl;

    public String getHeading() {
        return heading;
    }

    public ListItem(String heading, String desc,String imageurl) {
        this.heading = heading;
        this.desc = desc;
        this.imageurl=imageurl;
    }


    public String getDesc() {
        return desc;
    }

    public String getImageurl() {
        return imageurl;
    }
}
