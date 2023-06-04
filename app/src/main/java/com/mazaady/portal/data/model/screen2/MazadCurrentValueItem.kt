package com.mazaady.portal.data.model.screen2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * The above code defines a data class named MazadCurrentValueItem with two nullable properties, title
 * and value, that implements the Parcelable interface in Kotlin.
 * @property {String?} title - The title property is a String variable that represents the title of a
 * MazadCurrentValueItem object.
 * @property {String?} value - The `value` property is a string that represents the current value of an
 * item in a Mazad (auction) system. It is nullable, meaning it can be assigned a null value. This
 * property is a part of a data class called `MazadCurrentValueItem` which implements the `Parcelable
 */
data class MazadCurrentValueItem(
    var title: String? = null,
    var value: String? = null,
) : Parcelable

