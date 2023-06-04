package com.mazaady.portal.data.model.screen2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * The above code defines a data class called BidderItem with four properties that can be parcelled.
 * @property {String?} profileImage - A string that represents the URL or file path of the bidder's
 * profile image. It is nullable, meaning it can be set to null if there is no profile image available
 * for the bidder.
 * @property {String?} name - The "name" property is a string that represents the name of a bidder.
 * @property {String?} time - The "time" property in the BidderItem data class is a String variable
 * that represents the time at which the bidder placed their bid.
 * @property {String?} counter - The "counter" property is a String variable that represents the number
 * of bids made by the bidder for an item.
 */
data class BidderItem(
    var profileImage: String? = null,
    var name: String? = null,
    var time: String? = null,
    var counter: String? = null,
) : Parcelable

