package com.mazaady.portal.data

import android.os.Parcelable
import com.mazaady.portal.data.model.Children
import com.mazaady.portal.data.model.Options
import com.mazaady.portal.data.model.screen1.MainCategoryItem
import com.mazaady.portal.data.model.screen1.SubCategoryItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionCatItem(
    var mainCategoryItem: MainCategoryItem? = null,
    var children: Children?= null,
    var properties: MutableList<SubCategoryItem>? = null,
    var options: MutableList<Options>? = null,
): Parcelable