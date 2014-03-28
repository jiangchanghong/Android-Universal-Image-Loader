/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nostra13.example.universalimageloader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// LocalImage represents an image in the local storage.
public class LocalImage  {
    public static final int THUMBNAIL_TARGET_SIZE = 640;
    public static final int MICROTHUMBNAIL_TARGET_SIZE = 200;

    public static final String TAG = "LocalImage";

    ContentResolver contentResolver;
    Context context;
    LocalImage(Context context) {
        this.context = context;

    }

    public void get() {
        Log.i("changhong", "count:");
        contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION, null, null, null);
        if (cursor == null) {
            return;
        }
//        ArrayList<String> list = new ArrayList<String>();
        String[] str;
        List<String> list = new ArrayList<String>();
        int i = 0;
        cursor.moveToNext();
        while (cursor.moveToNext()) {

            i++;
            String tem = cursor.getString(INDEX_DATA);
            list.add(tem);
            if (i>500) break;
            Log.i("changhong", tem);
        }
        str = new String[list.size()];
        i=0;
        for (String s : list) {
            str[i++] = s;
        }

        Constants.IMAGES = str;
        cursor.close();
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

    static final String[] PROJECTION =  {
            ImageColumns._ID,           // 0
            ImageColumns.TITLE,         // 1
            ImageColumns.MIME_TYPE,     // 2
            ImageColumns.LATITUDE,      // 3
            ImageColumns.LONGITUDE,     // 4
            ImageColumns.DATE_TAKEN,    // 5
            ImageColumns.DATE_ADDED,    // 6
            ImageColumns.DATE_MODIFIED, // 7
            ImageColumns.DATA,          // 8
            ImageColumns.ORIENTATION,   // 9
            ImageColumns.BUCKET_ID,     // 10
            ImageColumns.SIZE,          // 11
            // These should be changed to proper names after they are made public.
//            ImageColumns.WIDTH,         // 12
//              ImageColumns.HEIGHT         // 13
    };
}
