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
package com.changhong_practice.imageloaderforfile.core.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.changhong_practice.imageloaderforfile.core.ImageLoaderConfiguration;
import com.changhong_practice.imageloaderforfile.utils.IoUtils;
import com.changhong_practice.imageloaderforfile.core.assist.ImageSize;
import com.changhong_practice.imageloaderforfile.utils.ImageSizeUtils;
import com.changhong_practice.imageloaderforfile.utils.L;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decodes images to {@link Bitmap}, scales them to needed size
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see ImageDecodingInfo
 * @since 1.8.3
 */
public class BaseImageDecoder implements ImageDecoder {

    protected static final String ERROR_CANT_DECODE_IMAGE = "Image can't be decoded [%s]";

    protected final boolean loggingEnabled;

    /**
     *                       ImageLoaderConfiguration.Builder#writeDebugLogs()
     *                       ImageLoaderConfiguration.writeDebugLogs()}
     */
    public BaseImageDecoder(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    /**
     * Decodes image from URI into {@link Bitmap}. Image is scaled close to incoming {@linkplain ImageSize target size}
     * during decoding (depend on incoming parameters).
     *
     * @return Decoded bitmap
     * @throws IOException                   if some I/O exception occurs during image reading
     * @throws UnsupportedOperationException if image URI has unsupported scheme(protocol)
     */
    public Bitmap decode(ImageDecodingInfo decodingInfo, boolean isbig)
            throws IOException {
        Bitmap decodedBitmap;
        InputStream imageStream = getImageStream(decodingInfo);
        try {
            imageStream = resetStream(imageStream, decodingInfo);
            Options options = new Options();
            options.inSampleSize = getsample(decodingInfo, isbig);
            decodedBitmap = BitmapFactory.
                    decodeStream(imageStream, null, options);
        } finally {
            IoUtils.closeSilently(imageStream);
        }

        if (decodedBitmap == null) {
            L.e(ERROR_CANT_DECODE_IMAGE, decodingInfo.getImageKey());
        }
        return decodedBitmap;
    }

    protected InputStream getImageStream(ImageDecodingInfo decodingInfo) throws IOException {
        return decodingInfo.getDownloader().getStream(decodingInfo.getImageUri()
        );
    }


    protected int getsample(

            ImageDecodingInfo decodingInfo, boolean isbig) {

        int scale;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(decodingInfo.getImageUri(), options);

        ImageSize srcsize = new ImageSize(options.outWidth, options.outHeight);
        ImageSize tarsize;
        if (isbig == true) {
            tarsize = new ImageSize(ImageLoaderConfiguration.ImageWidthForBigDiscCache,
                    ImageLoaderConfiguration.ImageHeightForBigDiscCache
            );
        } else {
            tarsize = new ImageSize(ImageLoaderConfiguration.ImageWidthForSmallDiscCache,
                    ImageLoaderConfiguration.ImageHeightForSmallDiscCache
            );
        }

        scale = ImageSizeUtils.computeImageSampleSize(srcsize, tarsize, false);
        return scale;
    }

    protected InputStream resetStream(InputStream imageStream,
                                      ImageDecodingInfo decodingInfo) throws IOException {
        try {
            imageStream.reset();
        } catch (IOException e) {
            IoUtils.closeSilently(imageStream);
            imageStream = getImageStream(decodingInfo);
        }
        return imageStream;
    }
}