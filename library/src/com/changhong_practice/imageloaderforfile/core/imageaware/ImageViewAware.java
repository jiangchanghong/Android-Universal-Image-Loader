/*******************************************************************************
 * Copyright 2013 Sergey Tarasevich
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
package com.changhong_practice.imageloaderforfile.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;
import com.changhong_practice.imageloaderforfile.utils.L;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Wrapper for Android {@link android.widget.ImageView ImageView}. Keeps weak reference of ImageView to prevent memory
 * leaks.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.9.0
 */
public class ImageViewAware implements ImageAware {

	public static final String WARN_CANT_SET_DRAWABLE = "Can't set a drawable into view. You should call ImageLoader on UI thread for it.";
	public static final String WARN_CANT_SET_BITMAP = "Can't set a bitmap into view. You should call ImageLoader on UI thread for it.";

	protected Reference<ImageView> imageViewRef;
	protected boolean checkActualViewSize;

	/**
	 * Constructor. <br />
	 * References {@link #ImageViewAware(android.widget.ImageView, boolean) ImageViewAware(imageView, true)}.
	 *
	 * @param imageView {@link android.widget.ImageView ImageView} to work with
	 */
	public ImageViewAware(ImageView imageView) {
		this(imageView, true);
	}

	/**
	 * Constructor
	 *
	 * @param imageView           {@link android.widget.ImageView ImageView} to work with
	 * @param checkActualViewSize <b>true</b> - then {@link #getWidth()} and {@link #getHeight()} will check actual
	 *                            size of ImageView. It can cause known issues like
	 *                            <a href="https://github.com/nostra13/Android-Universal-Image-Loader/issues/376">this</a>.
	 *                            But it helps to save memory because memory cache keeps bitmaps of actual (less in
	 *                            general) size.
	 *                            <p/>
	 *                            <b>false</b> - then {@link #getWidth()} and {@link #getHeight()} will <b>NOT</b>
	 *                            consider actual size of ImageView, just layout parameters. <br /> If you set 'false'
	 *                            it's recommended 'android:layout_width' and 'android:layout_height' (or
	 *                            'android:maxWidth' and 'android:maxHeight') are set with concrete values. It helps to
	 *                            save memory.
	 *                            <p/>
	 */
	public ImageViewAware(ImageView imageView, boolean checkActualViewSize) {
		this.imageViewRef = new WeakReference<ImageView>(imageView);
		this.checkActualViewSize = checkActualViewSize;
	}
	@Override
	public ImageView getWrappedView() {
		return imageViewRef.get();
	}

	@Override
	public boolean isCollected() {
		return imageViewRef.get() == null;
	}

	@Override
	public int getId() {
		ImageView imageView = imageViewRef.get();
		return imageView == null ? super.hashCode() : imageView.hashCode();
	}

	@Override
	public boolean setImageDrawable(Drawable drawable) {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			ImageView imageView = imageViewRef.get();
			if (imageView != null) {
				imageView.setImageDrawable(drawable);
				return true;
			}
		} else {
			L.w(WARN_CANT_SET_DRAWABLE);
		}
		return false;
	}

	@Override
	public boolean setImageBitmap(Bitmap bitmap) {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			ImageView imageView = imageViewRef.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
				return true;
			}
		} else {
			L.w(WARN_CANT_SET_BITMAP);
		}
		return false;
	}
}
