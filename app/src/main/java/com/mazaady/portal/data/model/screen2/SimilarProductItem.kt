package com.mazaady.portal.data.model.screen2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * This is a data class in Kotlin representing a similar product item with image, title, start after
 * and start price properties.
 * @property {String?} image - The image property is a string that represents the URL or file path of
 * the image associated with the similar product item.
 * @property {String?} title - The title property is a string that represents the name or title of a
 * product.
 * @property {String?} startAfter - `startAfter` is a property of the `SimilarProductItem` data class.
 * It is a `String` variable that represents the time when the similar product starts after the
 * original product. For example, if the original product starts at 9:00 AM and the similar product
 * starts 30 minutes
 * @property {String?} startPrice - startPrice is a property of the SimilarProductItem data class. It
 * is a String type variable that represents the starting price of the product.
 */
data class SimilarProductItem(
    var image: String? = null,
    var title: String? = null,
    var startAfter: String? = null,
    var startPrice: String? = null,
) : Parcelable

