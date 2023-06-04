package com.mazaady.portal.network.api

import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import retrofit2.Response
import javax.inject.Inject

/* The ApiHelperImpl class implements the ApiHelper interface and provides methods to retrieve data
from the MazaadyPortalApi. */
class ApiHelperImpl @Inject constructor(private val mazaadyPortalApi: MazaadyPortalApi) : ApiHelper {

    /**
     * This function retrieves a response containing all main categories from an API in Kotlin.
     */
    override suspend fun getAllCategoriesList(): Response<AllCategoriesResponse> =
        mazaadyPortalApi.getAllMainCategories()

    /**
     * This function retrieves a list of all sub-properties for a given category ID using an API call.
     *
     * @param categoryId categoryId is an optional parameter of type Int. It is used to filter the
     * sub-properties based on the category id. If categoryId is null, then all sub-properties will be
     * returned without any filtering.
     */
    override suspend fun getAllSubPropertiesList(categoryId: Int?): Response<AllSubPropertiesResponse>  =
        mazaadyPortalApi.getSubCategories(categoryId)

    /**
     * This function retrieves all options for a given subcategory ID from an API using Kotlin and
     * returns a response.
     *
     * @param subCategoryId The subCategoryId parameter is an integer value that represents the ID of a
     * subcategory for which we want to retrieve all the available options. This function is a suspend
     * function that returns a Response object containing an AllOptionsResponse object. The function
     * calls an API method named getAllOptionsForAllSubCatId()
     */
    override suspend fun getAllOptionsList(subCategoryId: Int?): Response<AllOptionsResponse> =
        mazaadyPortalApi.getAllOptionsForAllSubCatId(subCategoryId)

}