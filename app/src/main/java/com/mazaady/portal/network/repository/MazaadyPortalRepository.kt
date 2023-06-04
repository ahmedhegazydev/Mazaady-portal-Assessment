package com.mazaady.portal.network.repository

import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import com.mazaady.portal.network.api.ApiHelper
import com.mazaady.portal.state.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/* The MazaadyPortalRepository class is an implementation of the IMazaadyPortalRepository interface
that retrieves data from a remote data source and returns it as a NetworkState object. */
class MazaadyPortalRepository @Inject constructor(
    private val remoteDataSource: ApiHelper,
) : IMazaadyPortalRepository {

    /**
     * This is a suspend function that retrieves a list of all categories from a remote data source and
     * returns a NetworkState object indicating whether the operation was successful or not.
     *
     * @return A `NetworkState` object containing either a `Success` state with the
     * `AllCategoriesResponse` data if the API call was successful, or an `Error` state with an error
     * message if there was an error during the API call.
     */
    override suspend fun getAllCategories(
    ): NetworkState<AllCategoriesResponse> {
        return try {
            val response = remoteDataSource.getAllCategoriesList()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                NetworkState.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /**
     * This is a suspend function that retrieves a list of sub-properties based on a given category ID
     * and returns a NetworkState object indicating success or failure.
     *
     * @param categoryId categoryId is an optional parameter of type Int, which represents the ID of a
     * category. It is used as a filter to retrieve a list of sub-properties that belong to a specific
     * category. If categoryId is null, then all sub-properties will be retrieved regardless of their
     * category.
     * @return a `NetworkState` object that contains either a `Success` state with the
     * `AllSubPropertiesResponse` data if the API call was successful, or an `Error` state with an
     * error message if there was an error during the API call.
     */
    override suspend fun getAllSubProperties(categoryId: Int?): NetworkState<AllSubPropertiesResponse> {
        return try {
            val response = remoteDataSource.getAllSubPropertiesList(categoryId)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                NetworkState.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /**
     * This is a suspend function that retrieves a list of options based on a subcategory ID and
     * returns a NetworkState object indicating success or failure.
     *
     * @param subCategoryId subCategoryId is an optional parameter of type Int. It is used as a filter
     * to retrieve a specific subset of options from the remote data source. If it is null, then all
     * options will be retrieved.
     * @return This function returns a `NetworkState` object that contains either a `Success` state
     * with the `AllOptionsResponse` data if the API call was successful, or an `Error` state with an
     * error message if an exception occurred or the API call was not successful.
     */
    override suspend fun getOptions(subCategoryId: Int?): NetworkState<AllOptionsResponse> {
        return try {
            val response = remoteDataSource.getAllOptionsList(subCategoryId)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                NetworkState.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }


}


