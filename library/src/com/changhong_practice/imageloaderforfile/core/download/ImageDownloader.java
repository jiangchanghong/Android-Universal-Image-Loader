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
package com.changhong_practice.imageloaderforfile.core.download;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides retrieving of {@link InputStream} of image by URI.<br />
 * Implementations have to be thread-safe.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.4.0
 */
public interface ImageDownloader {
	/**
	 * Retrieves {@link InputStream} of image by URI.
	 *
	 *                 DisplayImageOptions.extraForDownloader(Object)}; can be null
	 * @return {@link InputStream} of image
	 * @throws IOException                   if some I/O error occurs during getting image stream
	 * @throws UnsupportedOperationException if image URI has unsupported scheme(protocol)
	 */
	InputStream getStream(String imageUri) throws IOException;

}
