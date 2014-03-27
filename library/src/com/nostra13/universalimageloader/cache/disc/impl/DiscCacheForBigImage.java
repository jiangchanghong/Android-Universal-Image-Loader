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
package com.nostra13.universalimageloader.cache.disc.impl;

import com.nostra13.universalimageloader.cache.disc.BaseDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;

import java.io.File;

/**
 * Default implementation of {@linkplain com.nostra13.universalimageloader.cache.disc.DiscCacheAware disc cache}. Cache size is unlimited.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see com.nostra13.universalimageloader.cache.disc.BaseDiscCache
 * @since 1.0.0
 */
public class DiscCacheForBigImage extends BaseDiscCache {

	/** @param cacheDir Directory for file caching */
	public DiscCacheForBigImage(File cacheDir) {
		this(cacheDir, DefaultConfigurationFactory.createFileNameGenerator());
	}

	/**
	 * @param cacheDir          Directory for file caching
	 * @param fileNameGenerator Name generator for cached files
	 */
	public DiscCacheForBigImage(File cacheDir, FileNameGenerator fileNameGenerator) {
		super(cacheDir, fileNameGenerator);
	}

	@Override
	public void put(String key, File file) {
		// Do nothing
	}
}
