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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import com.nostra13.example.universalimageloader.Constants.Extra;
import com.nostra13.universalimageloader.utils.L;

import java.io.*;

import static com.nostra13.example.universalimageloader.Constants.IMAGES;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class HomeActivity extends BaseActivity {

    private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);

        File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
        if (!testImageOnSdCard.exists()) {
            copyTestImageToSdCard(testImageOnSdCard);
        }
        init();
    }

    private void init() {


        LocalImage localImage = new LocalImage(getApplicationContext());
        localImage.get();
        File file = localImage.file;
        try {

            files(file);
        } catch (Exception w) {
            w.printStackTrace();
        }


    }

    private void files(final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getAbsolutePath(),options);
                int w = options.outWidth;
                int h = options.outHeight;
                Log.i("changhong", "" + w + h);
                options.inJustDecodeBounds = false;
                options.inSampleSize = 1;
                Bitmap bitmap = BitmapFactory.
                        decodeFile(file.getAbsolutePath(), options);

                Log.i("changhong", "" + bitmap.getWidth() + bitmap.getHeight());

                File outfile = Environment.getExternalStorageDirectory();
                outfile = new File(outfile, "aaaa.jpg");
                BufferedOutputStream outputStream = null;


                try {
                    outputStream = new BufferedOutputStream(new FileOutputStream(outfile));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 1, outputStream
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    public void onImageListClick(View view) {
        Intent intent = new Intent(this, ImageListActivity.class);
        intent.putExtra(Extra.IMAGES, IMAGES);
        startActivity(intent);
    }

    public void onImageGridClick(View view) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(Extra.IMAGES, IMAGES);
        startActivity(intent);
    }

    public void onImagePagerClick(View view) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(Extra.IMAGES, IMAGES);
        startActivity(intent);
    }

    public void onImageGalleryClick(View view) {
        Intent intent = new Intent(this, ImageGalleryActivity.class);
        intent.putExtra(Extra.IMAGES, IMAGES);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        imageLoader.stop();
        super.onBackPressed();
    }

    private void copyTestImageToSdCard(final File testImageOnSdCard) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getAssets().open(TEST_FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
                    byte[] buffer = new byte[8192];
                    int read;
                    try {
                        while ((read = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, read);
                        }
                    } finally {
                        fos.flush();
                        fos.close();
                        is.close();
                    }
                } catch (IOException e) {
                    L.w("Can't copy test image onto SD card");
                }
            }
        }).start();
    }
}