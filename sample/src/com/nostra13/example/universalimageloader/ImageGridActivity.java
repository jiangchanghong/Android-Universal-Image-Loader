/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nostra13.example.universalimageloader;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.changhong_practice.imageloaderforfile.core.DisplayImageOptions;
import com.nostra13.example.changhong.AlubumUtil;
import com.nostra13.example.universalimageloader.Constants.Extra;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ImageGridActivity extends AbsListViewBaseActivity {

    String[] imageUrls;
    List<String> list = new ArrayList<String>();
    DisplayImageOptions options;
    String bid = "";
    Handler handler;
    static final String[] PROJECTION = AlubumUtil.PROJECTION;
    static final String SELECTION = MediaStore.Images.ImageColumns.BUCKET_ID + " = " + "?";


    public void ongetcursor() {
        list.clear();
        String[] str = new String[]{bid};
        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION, SELECTION, str, null);
        if (cursor == null) {
            return;
        }
        while (cursor.moveToNext()) {

            String tem = cursor.getString(2);
            list.add(tem);
        }
        str = new String[list.size()];
      int  i=0;
        for (String s : list) {
            str[i++] = s;
        }
        imageUrls = str;
        Constants.IMAGES = str;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_grid);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ImageAdapter imageAdapter = new ImageAdapter();
                ((GridView) listView).setAdapter(imageAdapter);
                return false;
            }
        });

        Bundle bundle = getIntent().getExtras();
        bid = bundle.getString("bid");
//        imageUrls = bundle.getStringArray(Extra.IMAGES);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageOnFail(R.drawable.ic_error)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        listView = (GridView) findViewById(R.id.gridview);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });
       new Thread(new Runnable() {
           @Override
           public void run() {
               ongetcursor();
               handler.sendEmptyMessage(2);
           }
       }).start();
    }

    private void startImagePagerActivity(int position) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(Extra.IMAGES, imageUrls);
        intent.putExtra(Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }

    public class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            imageLoader.displayImage(list.get(position),
                    holder.imageView, options, false);


            return view;
        }

        class ViewHolder {
            ImageView imageView;
            ProgressBar progressBar;
        }
    }


}