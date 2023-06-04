package com.mazaady.portal.network.repository

import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import com.mazaady.portal.state.NetworkState

/* The IMazaadyPortalRepository interface defines methods for retrieving categories, sub-properties,
and options from a network source. */
interface IMazaadyPortalRepository {
    /**
     * This is a suspend function that returns a NetworkState object containing an
     * AllCategoriesResponse.
     */
    suspend fun getAllCategories(): NetworkState<AllCategoriesResponse>
    /**
     * This is a Kotlin function that returns a NetworkState object containing AllSubPropertiesResponse
     * data when given an optional categoryId parameter.
     *
     * @param categoryId categoryId is an optional integer parameter that represents the ID of a
     * category. It is used as a filter to retrieve all sub-properties that belong to a specific
     * category. If categoryId is null, then all sub-properties from all categories will be retrieved.
     */
    suspend fun getAllSubProperties(categoryId: Int?): NetworkState<AllSubPropertiesResponse>
    /**
     * This is a Kotlin function that suspends and returns a NetworkState object containing an
     * AllOptionsResponse object, with an optional subCategoryId parameter.
     *
     * @param subCategoryId subCategoryId is an optional integer parameter that represents the
     * sub-category ID for which the options are to be fetched. If a valid sub-category ID is provided,
     * the function will return the options for that sub-category. If the parameter is null, the
     * function will return options for all sub-categories.
     */
    suspend fun getOptions(subCategoryId: Int?): NetworkState<AllOptionsResponse>

}
