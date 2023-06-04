package com.mazaady.portal.network.api

import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import retrofit2.Response

/* The `ApiHelper` interface defines methods for retrieving lists of categories, sub-properties, and
options from an API. */
interface ApiHelper {
    /**
     * This is a Kotlin function that returns a response containing a list of all categories.
     */
    suspend fun getAllCategoriesList(): Response<AllCategoriesResponse>
    /**
     * This is a Kotlin function that returns a response containing a list of all sub-properties for a
     * given category ID.
     *
     * @param categoryId categoryId is an optional integer parameter that represents the ID of a
     * category. It is used as a filter to retrieve a list of all sub-properties that belong to the
     * specified category. If categoryId is null, then all sub-properties from all categories will be
     * returned.
     */
    suspend fun getAllSubPropertiesList(categoryId: Int?): Response<AllSubPropertiesResponse>
    /**
     * This is a Kotlin function that returns a response containing all options for a given subcategory
     * ID.
     *
     * @param subCategoryId The parameter `subCategoryId` is an integer value that represents the ID of
     * a subcategory. It is an optional parameter, which means that it can be null. This function
     * returns a response object of type `AllOptionsResponse`.
     */
    suspend fun getAllOptionsList(subCategoryId: Int?): Response<AllOptionsResponse>
}