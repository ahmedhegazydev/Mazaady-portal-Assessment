package com.mazaady.portal.data.model.screen1

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mazaady.portal.data.model.Options
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * This is a data class representing a response containing a code, message, and a list of subcategory
 * items.
 * @property {Int?} code - The "code" property is an integer value that represents the status code of
 * the response. It is typically used to indicate whether the request was successful or not.
 * @property {String?} msg - The "msg" property in the "AllSubPropertiesResponse" data class is a
 * string variable that represents a message associated with the response. It can be used to provide
 * additional information about the response or to indicate any errors or warnings that occurred during
 * the processing of the request.
 * @property {MutableList<SubCategoryItem>?} data - The `data` property is a mutable list of
 * `SubCategoryItem` objects. It is annotated with `@SerializedName("data")` which means that when this
 * class is serialized or deserialized from JSON, the property name in the JSON will be mapped to this
 * property. The initial value of this
 */
data class AllSubPropertiesResponse(

    @SerializedName("code") var code: Int? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: MutableList<SubCategoryItem>? = mutableListOf()
) : Parcelable

@Parcelize

/**
 * This is a data class representing a subcategory item with various properties and options.
 * @property {Boolean?} isSelected - A boolean value indicating whether the subcategory item is
 * currently selected or not.
 * @property {Boolean?} isTempForRestingRecycler - This property is a boolean flag that is used to
 * indicate whether the item is temporary and should be displayed in a resting recycler view.
 * @property {Int?} id - The ID of the subcategory item.
 * @property {String?} name - The name of the subcategory item.
 * @property {String?} description - The description of the subcategory item. It provides additional
 * information about the item.
 * @property {String?} slug - The "slug" property is a string that represents a user-friendly and
 * SEO-friendly version of the category or subcategory name. It is often used in URLs to identify the
 * category or subcategory.
 * @property {String?} parent - The parent property is a String that represents the parent category of
 * the subcategory item. It is used to organize and group subcategories under a specific category.
 * @property {Boolean?} list - The "list" property is a Boolean value that indicates whether this
 * subcategory item has a list of options or not. If it is true, then the "options" property will
 * contain a list of available options for this subcategory item.
 * @property {String?} type - The "type" property is a String that represents the type of the
 * subcategory item. It could be used to differentiate between different types of subcategory items,
 * such as "color", "size", "material", etc.
 * @property {String?} value - The value property is a String that holds a value related to the
 * SubCategoryItem.
 * @property {String?} otherValue - `otherValue` is a property of type `String` in the
 * `SubCategoryItem` data class. It is annotated with `@SerializedName("other_value")`, which means
 * that it is mapped to the JSON key "other_value" when the object is serialized or deserialized using
 * a JSON parser
 * @property {MutableList<Options>} options - The "options" property is a mutable list of "Options"
 * objects. It is used to store a list of options for a particular subcategory item. Each "Options"
 * object contains information about a single option, such as its name and value. This property can be
 * used to display a list of
 * @property {String?} selectedOption - This property is a string that represents the currently
 * selected option for the subcategory item. It is used in conjunction with the "options" property,
 * which is a list of possible options for the subcategory item. When the user selects an option, the
 * "selectedOption" property is updated to reflect their
 */
data class SubCategoryItem(
    var isSelected: Boolean? = false,
    var isTempForRestingRecycler: Boolean? = false,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("parent") var parent: String? = null,
    @SerializedName("list") var list: Boolean? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("other_value") var otherValue: String? = null,
    @SerializedName("options") var options: MutableList<Options> = mutableListOf(),
    var selectedOption: String? = null,
    ) : Parcelable

