package com.mazaady.portal.data.model.screen2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * This is a data class in Kotlin called AvailablePricesItem that has a nullable String property called
 * value and implements the Parcelable interface.
 * @property {String?} value - The `AvailablePricesItem` data class has only one property named `value`
 * which is of type `String?`. The `?` symbol indicates that the value can be null. This data class
 * also implements the `Parcelable` interface which allows instances of this class to be passed between
 * different components of
 */
data class AvailablePricesItem(
   var value: String? = null,
) : Parcelable

