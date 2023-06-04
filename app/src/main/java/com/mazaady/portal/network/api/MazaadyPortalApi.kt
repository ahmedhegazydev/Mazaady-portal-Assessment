package com.mazaady.portal.network.api

import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import com.mazaady.portal.util.Constants.Companion.API_KEY
import com.mazaady.portal.util.Constants.Companion.ENDPOINT_URL_GET_ALL_CATEGORIES
import com.mazaady.portal.util.Constants.Companion.ENDPOINT_URL_GET_ALL_OPTIONS
import com.mazaady.portal.util.Constants.Companion.ENDPOINT_URL_GET_ALL_SUB_CATEGORIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/* The MazaadyPortalApi interface contains functions for making API requests to retrieve categories,
sub-categories, and options. */
interface MazaadyPortalApi {

    /**
     * This is a Kotlin function that makes a GET request to retrieve all main categories using an API
     * key as a header.
     */
    @Headers("private-key: $API_KEY")
    @GET(ENDPOINT_URL_GET_ALL_CATEGORIES)
    suspend fun getAllMainCategories(
    ): Response<AllCategoriesResponse>

    /**
     * This Kotlin function uses a private API key to make a GET request to retrieve all sub-categories
     * with an optional category ID parameter.
     *
     * @param catId catId is a query parameter that is used to filter the subcategories based on the
     * category ID. In this case, the default value of catId is set to 13, which means that the API
     * will return all subcategories that belong to the category with ID 13. However, this value
     */
    @Headers("private-key: $API_KEY")
    @GET(ENDPOINT_URL_GET_ALL_SUB_CATEGORIES)
    suspend fun getSubCategories(
        @Query("cat") catId: Int? = 13
    ): Response<AllSubPropertiesResponse>


    /**
     * This is a Kotlin function that uses Retrofit to make a GET request to retrieve all options for a
     * given subcategory ID, with an optional default value of 50.
     *
     * @param subCatId subCatId is a path parameter in the GET request URL. It represents the ID of a
     * subcategory for which all options are being requested. The default value of subCatId is 50 if no
     * value is provided.
     */
    @Headers("private-key: $API_KEY")
    @GET(ENDPOINT_URL_GET_ALL_OPTIONS)
    suspend fun getAllOptionsForAllSubCatId(
        @Path("subCatId") subCatId: Int? = 50
    ): Response<AllOptionsResponse>


}