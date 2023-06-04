package com.mazaady.portal.data.model.screen1

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mazaady.portal.data.model.Options
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * The above type is a Kotlin data class representing a response containing an integer code, a string
 * message, and an array list of options.
 * @property {Int?} code - The code property is an integer value that represents the status code of the
 * response. It is used to indicate whether the request was successful or not.
 * @property {String?} msg - msg is a String property that represents a message associated with the
 * response. It can be used to provide additional information about the response or to indicate any
 * errors that occurred during the processing of the request.
 * @property {ArrayList<Options>} data - "data" is a property of the data class "AllOptionsResponse".
 * It is of type ArrayList<Options> and is initialized with an empty ArrayList. This property holds a
 * list of "Options" objects that are returned as a response from an API call.
 */
data class AllOptionsResponse(
    @SerializedName("code" ) var code : Int?            = null,
    @SerializedName("msg"  ) var msg  : String?         = null,
    @SerializedName("data" ) var data : ArrayList<Options> = arrayListOf()
):Parcelable

