package com.nostra13.example.changhong;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by changhong on 14-3-28.
 */
public class AlubumUtil {
    private static AlubumUtil util;
    public List<Aulbum> maulbums = new ArrayList<Aulbum>();
    public List<String> maulbums_bucketID = new ArrayList<String>();
    HashMap<String, Aulbum> hashMap = new HashMap<String, Aulbum>();

    //    public List<String> mAll_directory = new ArrayList<String>();
    private AlubumUtil() {

    }

    public static AlubumUtil getInstance() {
        if (util == null) {
            util = new AlubumUtil();
        }
        return util;

    }

    public void init_All_aulbum(Context context) {
        Log.i("changhong", "init begin");
        maulbums.clear();
        maulbums_bucketID.clear();
        Cursor cursor = getcursor(context);
        if (cursor == null) return;
        while (cursor.moveToNext()) {
            int size = cursor.getInt(4);
            if (size < 200 * 1024) continue;

            String bid = cursor.getString(3);
            if (hashMap.keySet().contains(bid)) {
                continue;
            }
            String path = cursor.getString(2);
//                String caption = cursor.getString(1);
            Image image = new Image(FileUtil.getdirectory(path),
                    path, bid);
            Aulbum aulbum = new Aulbum(1, bid, image);
            hashMap.put(bid, aulbum);

        }
        for (Map.Entry<String, Aulbum> entry : hashMap.entrySet()) {
            maulbums_bucketID.add(entry.getKey());
            maulbums.add(entry.getValue());
        }
        cursor.close();
        Log.i("changhong", "init success");
    }

    private Cursor getcursor(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION, null, null, null);
        if (cursor == null) {
            return null;
        }
        return cursor;

    }

    // Must preserve order between these indices and the order of the terms in
    // the following PROJECTION array.
    public static final int INDEX_ID = 0;
    public static final int INDEX_CAPTION = 1;
    public static final int INDEX_MIME_TYPE = 2;
    public static final int INDEX_LATITUDE = 3;
    public static final int INDEX_LONGITUDE = 4;
    public static final int INDEX_DATE_TAKEN = 5;
    public static final int INDEX_DATE_ADDED = 6;
    public static final int INDEX_DATE_MODIFIED = 7;
    public static final int INDEX_DATA = 8;
    public static final int INDEX_ORIENTATION = 9;
    public static final int INDEX_BUCKET_ID = 10;
    public static final int INDEX_SIZE_ID = 11;
    public static final int INDEX_WIDTH = 12;
    public static final int INDEX_HEIGHT = 13;

    public static final String[] PROJECTION = {
            MediaStore.Images.ImageColumns._ID,           // 0
            MediaStore.Images.ImageColumns.TITLE,         // 1
//            MediaStore.Images.ImageColumns.MIME_TYPE,     // 2
//            MediaStore.Images.ImageColumns.LATITUDE,      // 3
//            MediaStore.Images.ImageColumns.LONGITUDE,     // 4
//            MediaStore.Images.ImageColumns.DATE_TAKEN,    // 5
//            MediaStore.Images.ImageColumns.DATE_ADDED,    // 6
//            MediaStore.Images.ImageColumns.DATE_MODIFIED, // 7
            MediaStore.Images.ImageColumns.DATA,          // 8
//            MediaStore.Images.ImageColumns.ORIENTATION,   // 9
            MediaStore.Images.ImageColumns.BUCKET_ID,     // 10
            MediaStore.Images.ImageColumns.SIZE,          // 11
            // These should be changed to proper names after they are made public.
//            ImageColumns.WIDTH,         // 12
//              ImageColumns.HEIGHT         // 13
    };
}
