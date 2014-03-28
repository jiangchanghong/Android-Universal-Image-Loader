package com.nostra13.example.changhong;

/**
 * Created by changhong on 14-3-28.
 */
public class Image {
    public String displayTitle = "noaulbum";
    public String path = null;
    public String bucket_id = "";

    public Image(String displayTitle, String path, String bucket_id) {
        this.displayTitle = displayTitle;
        this.path = path;
        this.bucket_id = bucket_id;
    }
}
