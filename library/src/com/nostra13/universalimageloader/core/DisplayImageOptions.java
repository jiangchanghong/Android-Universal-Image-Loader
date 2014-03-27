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
package com.nostra13.universalimageloader.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

/**
 * Contains options for image display. Defines:
 * <ul>
 * <li>whether stub image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
 * image aware view} during image loading</li>
 * <li>whether stub image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
 * image aware view} if empty URI is passed</li>
 * <li>whether stub image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
 * image aware view} if image loading fails</li>
 * <li>whether {@link com.nostra13.universalimageloader.core.imageaware.ImageAware image aware view} should be reset
 * before image loading start</li>
 * <li>whether loaded image will be cached in memory</li>
 * <li>whether loaded image will be cached on disc</li>
 * <li>image scale type</li>
 * <li>decoding options (including bitmap decoding configuration)</li>
 * <li>delay before loading of image</li>
 * <li>whether consider EXIF parameters of image</li>
 * <li>auxiliary object which will be passed to {@link ImageDownloader#getStream(String, Object) ImageDownloader}</li>
 * <li>pre-processor for image Bitmap (before caching in memory)</li>
 * <li>post-processor for image Bitmap (after caching in memory, before displaying)</li>
 * <li>how decoded {@link Bitmap} will be displayed</li>
 * </ul>
 * <p/>
 * You can create instance:
 * <ul>
 * <li>with {@link Builder}:<br />
 * <b>i.e.</b> :
 * <code>new {@link DisplayImageOptions}.{@link Builder#Builder() Builder()}.{@link Builder#cacheInMemory() cacheInMemory()}.
 * {@link Builder#showImageOnLoading(int) showImageOnLoading()}.{@link Builder#build() build()}</code><br />
 * </li>
 * <li>or by static method: {@link #createSimple()}</li> <br />
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class DisplayImageOptions {

	private final int imageResOnLoading;
	private final int imageResOnFail;
	private final Drawable imageOnLoading;
	private final Drawable imageOnFail;
	private final boolean resetViewBeforeLoading;
	private final ImageScaleType imageScaleType;
	private final Options decodingOptions;
	private final BitmapDisplayer displayer;
	private final Handler handler;
	private DisplayImageOptions(Builder builder) {
		imageResOnLoading = builder.imageResOnLoading;
		imageResOnFail = builder.imageResOnFail;
		imageOnLoading = builder.imageOnLoading;
		imageOnFail = builder.imageOnFail;
		resetViewBeforeLoading = builder.resetViewBeforeLoading;
		imageScaleType = builder.imageScaleType;
		decodingOptions = builder.decodingOptions;
		displayer = builder.displayer;
		handler = builder.handler;
	}

	public boolean shouldShowImageOnLoading() {
		return imageOnLoading != null || imageResOnLoading != 0;
	}

	public boolean shouldShowImageOnFail() {
		return imageOnFail != null || imageResOnFail != 0;
	}


	public Drawable getImageOnLoading(Resources res) {
		return imageResOnLoading != 0 ? res.getDrawable(imageResOnLoading) : imageOnLoading;
	}

	public Drawable getImageOnFail(Resources res) {
		return imageResOnFail != 0 ? res.getDrawable(imageResOnFail) : imageOnFail;
	}

	public boolean isResetViewBeforeLoading() {
		return resetViewBeforeLoading;
	}

	public ImageScaleType getImageScaleType() {
		return imageScaleType;
	}

	public Options getDecodingOptions() {
		return decodingOptions;
	}




	public BitmapDisplayer getDisplayer() {
		return displayer;
	}

	public Handler getHandler() {
		return handler;
	}



	public static class Builder {
		private int imageResOnLoading = 0;
		private int imageResOnFail = 0;
		private Drawable imageOnLoading = null;
		private Drawable imageOnFail = null;
		private boolean resetViewBeforeLoading = false;
		private ImageScaleType imageScaleType = ImageScaleType.IN_SAMPLE_POWER_OF_2;
		private Options decodingOptions = new Options();
		private BitmapDisplayer displayer = DefaultConfigurationFactory.createBitmapDisplayer();
		private Handler handler = null;

		public Builder() {
//			decodingOptions.inPurgeable = true;
			decodingOptions.inPurgeable = false;
//			decodingOptions.inInputShareable = true;
		}
		/**
		 * Incoming image will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
		 * image aware view} during image loading
		 *
		 */
		public Builder showImageOnLoading(int imageRes) {
			imageResOnLoading = imageRes;
			return this;
		}

		/**
		 * Incoming drawable will be displayed in {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
		 * image aware view} during image loading.
		 * This option will be ignored if {@link DisplayImageOptions.Builder#showImageOnLoading(int)} is set.
		 */
		public Builder showImageOnLoading(Drawable drawable) {
			imageOnLoading = drawable;
			return this;
		}




		public Builder showImageOnFail(int imageRes) {
			imageResOnFail = imageRes;
			return this;
		}


		public Builder showImageOnFail(Drawable drawable) {
			imageOnFail = drawable;
			return this;
		}


		/**
		 * Sets whether {@link com.nostra13.universalimageloader.core.imageaware.ImageAware
		 * image aware view} will be reset (set <b>null</b>) before image loading start
		 */
		public Builder resetViewBeforeLoading(boolean resetViewBeforeLoading) {
			this.resetViewBeforeLoading = resetViewBeforeLoading;
			return this;
		}

		/**
		 * Sets {@linkplain ImageScaleType scale type} for decoding image. This parameter is used while define scale
		 * size for decoding image to Bitmap. Default value - {@link ImageScaleType#IN_SAMPLE_POWER_OF_2}
		 */
		public Builder imageScaleType(ImageScaleType imageScaleType) {
			this.imageScaleType = imageScaleType;
			return this;
		}

		/** Sets {@link Bitmap.Config bitmap config} for image decoding. Default value - {@link Bitmap.Config#ARGB_8888} */
		public Builder bitmapConfig(Bitmap.Config bitmapConfig) {
			if (bitmapConfig == null) throw new IllegalArgumentException("bitmapConfig can't be null");
			decodingOptions.inPreferredConfig = bitmapConfig;
			return this;
		}



		/**
		 * Sets custom {@link BitmapDisplayer displayer} for image loading task. Default value -
		 * {@link DefaultConfigurationFactory#createBitmapDisplayer()}
		 */
		public Builder displayer(BitmapDisplayer displayer) {
			if (displayer == null) throw new IllegalArgumentException("displayer can't be null");
			this.displayer = displayer;
			return this;
		}

		/**
		 * Sets custom {@linkplain Handler handler} for displaying images and firing {@linkplain ImageLoadingListener
		 * listener} events.
		 */
		public Builder handler(Handler handler) {
			this.handler = handler;
			return this;
		}

		/** Sets all options equal to incoming options */
		public Builder cloneFrom(DisplayImageOptions options) {
			imageResOnLoading = options.imageResOnLoading;
			imageResOnFail = options.imageResOnFail;
			imageOnLoading = options.imageOnLoading;
			imageOnFail = options.imageOnFail;
			resetViewBeforeLoading = options.resetViewBeforeLoading;
			imageScaleType = options.imageScaleType;
			decodingOptions = options.decodingOptions;
			displayer = options.displayer;
			handler = options.handler;
			return this;
		}

		/** Builds configured {@link DisplayImageOptions} object */
		public DisplayImageOptions build() {
			return new DisplayImageOptions(this);
		}
	}

	/**
	 * Creates options appropriate for single displaying:
	 * <ul>
	 * <li>View will <b>not</b> be reset before loading</li>
	 * <li>Loaded image will <b>not</b> be cached in memory</li>
	 * <li>Loaded image will <b>not</b> be cached on disc</li>
	 * <li>{@link ImageScaleType#IN_SAMPLE_POWER_OF_2} decoding type will be used</li>
	 * <li>{@link Bitmap.Config#ARGB_8888} bitmap config will be used for image decoding</li>
	 * <li>{@link SimpleBitmapDisplayer} will be used for image displaying</li>
	 * </ul>
	 * <p/>
	 * These option are appropriate for simple single-use image (from drawables or from Internet) displaying.
	 */
	public static DisplayImageOptions createSimple() {
		return new Builder().build();
	}
}
