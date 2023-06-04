package com.mazaady.portal.data.model.screen2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * The above code defines a data class named GalleryItem with a nullable image property and implements
 * the Parcelable interface.
 * @property {String?} image - The `GalleryItem` data class has only one property called `image` which
 * is a nullable `String`. It represents the image URL or path of the gallery item. The `Parcelable`
 * interface is implemented to allow the object to be passed between activities or fragments in an
 * Android application.
 */
data class GalleryItem(
    var image: String? = null,
    ) : Parcelable

