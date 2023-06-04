package com.mazaady.portal.data.model.screen1

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mazaady.portal.data.model.AdsBanners
import com.mazaady.portal.data.model.Children
import com.mazaady.portal.data.model.Statistics
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * This is a data class representing a response containing code, message, and data related to
 * categories in an application, implemented in Kotlin and using the Parcelable interface.
 * @property {Int?} code - The code property is an integer that represents the status code of the
 * response. It is used to indicate whether the request was successful or not.
 * @property {String?} msg - The "msg" property is a String that represents a message or description
 * related to the response. It could be an error message or a success message, depending on the context
 * of the API call.
 * @property {DataCategories?} data - The `data` property is an instance of the `DataCategories` class,
 * which is a custom class that contains the actual data of the response. It is initialized with an
 * empty instance of `DataCategories` in case the response does not contain any data.
 */
data class AllCategoriesResponse(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: DataCategories? = DataCategories()
):Parcelable

@Parcelize
/**
 * The `DataCategories` class is a Parcelable data class that contains a list of main categories,
 * statistics, ads banners, and version information for iOS, Google, and Huawei.
 * @property {MutableList<MainCategoryItem>} categories - A mutable list of MainCategoryItem objects
 * representing the categories of data.
 * @property {Statistics?} statistics - It is a property of type Statistics, which is a data class that
 * contains various statistical data related to the categories. It is initialized with a default value
 * of an empty Statistics object.
 * @property {MutableList<AdsBanners>} adsBanners - A list of AdsBanners objects, which likely contain
 * information about advertisements to be displayed in the app.
 * @property {String?} iosVersion - The version number of the iOS app.
 * @property {String?} iosLatestVersion - iosLatestVersion is a nullable String property that
 * represents the latest version of the iOS app.
 * @property {String?} googleVersion - It is a String property that represents the version of the app
 * for Google Play Store.
 * @property {String?} huaweiVersion - It is a String property that represents the version of the app
 * for Huawei devices.
 */
data class DataCategories(

    @SerializedName("categories") var categories: MutableList<MainCategoryItem> = mutableListOf(),
    @SerializedName("statistics") var statistics: Statistics? = Statistics(),
    @SerializedName("ads_banners") var adsBanners: MutableList<AdsBanners> = mutableListOf(),
    @SerializedName("ios_version") var iosVersion: String? = null,
    @SerializedName("ios_latest_version") var iosLatestVersion: String? = null,
    @SerializedName("google_version") var googleVersion: String? = null,
    @SerializedName("huawei_version") var huaweiVersion: String? = null

):Parcelable

@Parcelize
/**
 * The MainCategoryItem is a data class with properties related to a main category item, including its
 * ID, name, description, image, slug, children, circle icon, and disable shipping status.
 * @property {Boolean?} isSelected - A Boolean variable that indicates whether the main category item
 * is currently selected or not.
 * @property {Int?} id - The unique identifier for the main category item.
 * @property {String?} name - The name of the main category item.
 * @property {String?} description - A string that describes the main category item.
 * @property {String?} image - The image property is a string that represents the URL or file path of
 * the image associated with the MainCategoryItem.
 * @property {String?} slug - The "slug" property is a string that represents a user-friendly URL for
 * the main category item. It is typically a simplified version of the category name, with spaces
 * replaced by hyphens and special characters removed. It is often used in website URLs to help users
 * navigate to the correct page.
 * @property {MutableList<Children>} children - It is a mutable list of objects of type Children. This
 * property represents the subcategories or child categories of the main category item. Each child
 * category has its own set of properties like id, name, description, image, etc.
 * @property {String?} circleIcon - A string property that represents the URL of the circle icon image
 * for the main category item.
 * @property {Int?} disableShipping - It is an integer value that indicates whether shipping is
 * disabled for this main category item. A value of 1 means shipping is disabled, while a value of 0
 * means shipping is enabled.
 */
data class MainCategoryItem(

    var isSelected: Boolean? = false,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("children") var children: MutableList<Children> = mutableListOf(),
    @SerializedName("circle_icon") var circleIcon: String? = null,
    @SerializedName("disable_shipping") var disableShipping: Int? = null

):Parcelable
