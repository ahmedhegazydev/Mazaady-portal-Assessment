package com.mazaady.portal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * This is a data class representing AdsBanners with properties for image, media type, and duration,
 * and implementing the Parcelable interface in Kotlin.
 * @property {String?} img - The `img` property is a string that represents the URL or file path of the
 * image file for the banner ad.
 * @property {String?} mediaType - The `mediaType` property is a string that represents the type of
 * media that the banner ad is displaying. It could be an image, a video, or some other type of media.
 * @property {Int?} duration - The "duration" property is an integer that represents the length of time
 * in seconds that the banner ad should be displayed.
 */
data class AdsBanners(
    @SerializedName("img") var img: String? = null,
    @SerializedName("media_type") var mediaType: String? = null,
    @SerializedName("duration") var duration: Int? = null
) : Parcelable

@Parcelize
/**
 * This is a data class representing children with various properties such as id, name, description,
 * image, slug, children, circle icon, and disable shipping.
 * @property {Boolean?} isSelected - A boolean value indicating whether the children item is currently
 * selected or not.
 * @property {Int?} id - The unique identifier for the Children object. It is of type Int and is
 * annotated with @SerializedName("id") to specify the name of the corresponding JSON property during
 * serialization and deserialization.
 * @property {String?} name - The name of the children.
 * @property {String?} description - A string property that holds the description of a Children object.
 * It provides additional information about the Children object.
 * @property {String?} image - The image property is a string that represents the URL or file path of
 * an image associated with the Children object.
 * @property {String?} slug - The "slug" property is a string that represents a short label or
 * identifier for the object. It is often used in URLs to identify a specific resource or page. In this
 * case, it likely refers to a unique identifier for a particular child object within a larger data
 * set.
 * @property {String?} children - The "children" property is a string that represents the children of
 * the current object. It is not clear from the code what the format of this string is or how it is
 * used.
 * @property {String?} circleIcon - A string property that represents the URL of the circle icon image
 * associated with the Children object.
 * @property {Int?} disableShipping - It is an integer property that indicates whether shipping is
 * disabled for this particular item. A value of 1 means that shipping is disabled, while a value of 0
 * means that shipping is enabled.
 */
data class Children(
    var isSelected: Boolean? = false,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("children") var children: String? = null,
    @SerializedName("circle_icon") var circleIcon: String? = null,
    @SerializedName("disable_shipping") var disableShipping: Int? = null

) : Parcelable

@Parcelize
/**
 * This is a Kotlin data class representing statistics with the number of auctions, users, and
 * products, which can be serialized and deserialized using the Gson library.
 * @property {Int?} auctions - The "auctions" property is an integer that represents the total number
 * of auctions in a system or application. It is a part of the "Statistics" data class, which is used
 * to store and retrieve statistical data related to auctions, users, and products.
 * @property {Int?} users - The "users" property is an integer that represents the total number of
 * users in a system or application. It is one of the properties of the "Statistics" data class, which
 * is used to store and retrieve statistical data related to auctions, users, and products.
 * @property {Int?} products - The "products" property is an integer that represents the total number
 * of products in the system. It is a part of the "Statistics" data class, which also includes the
 * "auctions" and "users" properties. The "@SerializedName" annotation is used to specify the name of
 * the JSON field
 */
data class Statistics(
    @SerializedName("auctions") var auctions: Int? = null,
    @SerializedName("users") var users: Int? = null,
    @SerializedName("products") var products: Int? = null
) : Parcelable


@Parcelize
/**
 * This is a data class in Kotlin representing options with various properties including selection
 * status, ID, name, slug, parent, child, and nested options.
 * @property {Boolean?} isSelected - A boolean value indicating whether this option is currently
 * selected or not.
 * @property {Int?} id - The id property is an integer value that represents the unique identifier of
 * an option.
 * @property {String?} name - The name of an option.
 * @property {String?} specifyHere - `specifyHere` is a nullable String property of the `Options` data
 * class. It is used to store additional information or details related to the option, if required.
 * @property {String?} slug - The "slug" property is a string that represents a URL-friendly version of
 * the "name" property. It is often used in web development to create clean and readable URLs for pages
 * or resources.
 * @property {Int?} parent - The "parent" property in the Options data class represents the ID of the
 * parent option, if this option is a child option. It is used to create a hierarchical structure of
 * options, where child options are nested under their parent options.
 * @property {Boolean?} child - The "child" property is a Boolean value that indicates whether this
 * option has any child options. If it is set to true, it means that this option has child options, and
 * if it is set to false, it means that this option does not have any child options.
 * @property {MutableList<Options>} options - The "options" property is a mutable list of "Options"
 * objects. This means that each "Options" object can have its own list of child options. This is
 * useful for creating hierarchical structures or nested options.
 */
data class Options(
    var isSelected: Boolean? = false,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    var specifyHere: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("parent") var parent: Int? = null,
    @SerializedName("child") var child: Boolean? = null,
    @SerializedName("options") var options: MutableList<Options> = arrayListOf()

) : Parcelable

