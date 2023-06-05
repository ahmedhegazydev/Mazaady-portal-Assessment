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
 * @property {Boolean?} isSelected - A Boolean variable that indicates whether the subcategory item is
 * currently selected or not.
 * @property {Int?} id - The unique identifier of the subcategory item.
 * @property {String?} name - The name of the subcategory item.
 * @property {String?} description - The description of the subcategory item. It provides additional
 * information about the item and can be used to give more context to the user.
 * @property {String?} slug - The "slug" property is a string that represents a user-friendly and
 * SEO-friendly version of the subcategory item's name. It is often used in URLs to identify the
 * subcategory item.
 * @property {String?} parent - The "parent" property in the "SubCategoryItem" data class represents
 * the parent category of the subcategory. It is a string type property.
 * @property {Boolean?} list - The "list" property is a Boolean value that indicates whether the
 * subcategory item has a list of options or not. If it is set to true, it means that the subcategory
 * item has a list of options, and the "options" property will contain a list of available options. If
 * it
 * @property {String?} type - The "type" property in the "SubCategoryItem" data class is a string that
 * represents the type of the subcategory item. It could be used to differentiate between different
 * types of subcategory items, such as "color", "size", "material", etc.
 * @property {String?} value - The value property is a String that represents the value of the
 * subcategory item. It can be used to store any relevant information about the subcategory item, such
 * as its price, size, color, or any other attribute that distinguishes it from other subcategory
 * items.
 * @property {String?} otherValue - `otherValue` is a property of type `String` in the
 * `SubCategoryItem` data class. It is annotated with `@SerializedName("other_value")`, which means
 * that it is mapped to the JSON key "other_value" when the object is serialized or deserialized using
 * a JSON parser
 * @property {MutableList<Options>} options - The "options" property is a mutable list of "Options"
 * objects. It is used to store a list of options for a particular subcategory item. Each "Options"
 * object contains information about a single option, such as its name and value. This property can be
 * used to display a list of
 * @property {String?} selectedOption - This is a nullable property of type String that represents the
 * currently selected option for the subcategory item. It is used in conjunction with the "options"
 * property, which is a mutable list of "Options" objects that represent the available options for the
 * subcategory item. When an option is selected, its
 */
data class SubCategoryItem(
    var isSelected: Boolean? = false,
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

