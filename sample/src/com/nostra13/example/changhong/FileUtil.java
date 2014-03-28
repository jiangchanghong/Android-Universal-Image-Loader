package com.nostra13.example.changhong;

/**
 * Created by changhong on 14-3-28.
 */
public class FileUtil {
    public static String getdirectory(String filepath) {
        int i = filepath.lastIndexOf("/");
        return filepath.substring(0, i);
    }
}
