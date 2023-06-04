package com.mazaady.portal.ui.list

import androidx.lifecycle.*
import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.data.model.screen1.AllOptionsResponse
import com.mazaady.portal.data.model.screen1.AllSubPropertiesResponse
import com.mazaady.portal.di.CoroutinesDispatcherProvider
import com.mazaady.portal.network.repository.MazaadyPortalRepository
import com.mazaady.portal.util.NetworkHelper
import com.mazaady.portal.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ScreenOneCatVM"

@HiltViewModel
/* This is a ViewModel class in Kotlin that handles the retrieval and handling of data related to main
categories, subcategories, and options from a repository using coroutines and network states. */
class ShowALlMainCategoriesListViewModel @Inject constructor(
    private val mazaadyPortalRepository: MazaadyPortalRepository,
    private val networkHelper: NetworkHelper,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider


) : ViewModel() {

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    private val _mainCatResponse =
        MutableStateFlow<NetworkState<AllCategoriesResponse>>(NetworkState.Empty())
    val mainCatResponse: StateFlow<NetworkState<AllCategoriesResponse>>
        get() = _mainCatResponse

    private val _subPropertiesResponse =
        MutableStateFlow<NetworkState<AllSubPropertiesResponse>>(NetworkState.Empty())
    val subPropertiesResponse: StateFlow<NetworkState<AllSubPropertiesResponse>>
        get() = _subPropertiesResponse

    private val _optionsResponse =
        MutableStateFlow<NetworkState<AllOptionsResponse>>(NetworkState.Empty())
    val optionsResponse: StateFlow<NetworkState<AllOptionsResponse>>
        get() = _optionsResponse

    /* `init` is a special block in Kotlin that is executed when an instance of the class is created.
    In this case, `init` is calling the function `getAllMainCategoriesListShowing()` when an
    instance of the `ShowALlMainCategoriesListViewModel` class is created. This function retrieves a
    list of main categories from a remote server and handles the response accordingly, while also
    checking for internet connectivity. So, `init` is initializing the ViewModel by fetching the
    main categories list. */
    init {
        getAllMainCategoriesListShowing()
    }

    /**
     * This function retrieves a list of main categories from a remote server and handles the response
     * accordingly, while also checking for internet connectivity.
     */
    fun getAllMainCategoriesListShowing() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _mainCatResponse.value = NetworkState.Loading()
                when (val response = mazaadyPortalRepository.getAllCategories()) {
                    is NetworkState.Success -> {
                        _mainCatResponse.value = handleAllMainCatsResponse(response)
                    }
                    is NetworkState.Error -> {
                        _mainCatResponse.value =
                            NetworkState.Error(
                                response.message ?: "Error"
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    /**
     * The function handles the response of a network call for all main categories and returns a
     * network state object.
     *
     * @param response The parameter `response` is of type `NetworkState<AllCategoriesResponse>`, which
     * is a sealed class that represents the different states of a network request. It can be one of
     * the following:
     * @return A NetworkState object of type AllCategoriesResponse is being returned.
     */
    private fun handleAllMainCatsResponse(response: NetworkState<AllCategoriesResponse>): NetworkState<AllCategoriesResponse> {
        return when (response) {
            is NetworkState.Success -> {
                if (response.data?.code == 200) {
                    NetworkState.Success(response.data)
                } else {
                    NetworkState.Error(message = "No data found")
                }
            }
            is NetworkState.Error -> NetworkState.Error(message = response.message ?: "Error")
            else -> NetworkState.Empty()
        }
    }

    /**
     * This function retrieves a list of sub-properties for a given category ID, handling network
     * connectivity and error states.
     *
     * @param categoryId An integer value representing the ID of a category for which the
     * sub-categories are to be fetched.
     */
    fun getAllSubCategoriesListShowing(categoryId: Int?) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _subPropertiesResponse.value = NetworkState.Loading()
                when (val response = mazaadyPortalRepository.getAllSubProperties(categoryId)) {
                    is NetworkState.Success -> {
                        _subPropertiesResponse.value = handleSubCategoriesResponse(response)
                    }
                    is NetworkState.Error -> {
                        _subPropertiesResponse.value =
                            NetworkState.Error(
                                response.message ?: "Error"
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    /**
     * The function handles the response of subcategories API call and returns a network state object.
     *
     * @param response The parameter `response` is of type `NetworkState<AllSubPropertiesResponse>`,
     * which is a sealed class that represents the different states of a network request. It can be one
     * of the following:
     * @return A NetworkState object of type AllSubPropertiesResponse is being returned.
     */
    private fun handleSubCategoriesResponse(response: NetworkState<AllSubPropertiesResponse>): NetworkState<AllSubPropertiesResponse> {
        return when (response) {
            is NetworkState.Success -> {
                if (response.data?.code == 200) {
                    NetworkState.Success(response.data)
                } else {
                    NetworkState.Error(message = "No data found")
                }
            }
            is NetworkState.Error -> NetworkState.Error(message = response.message ?: "Error")
            else -> NetworkState.Empty()
        }
    }

    /**
     * This function retrieves a list of options based on a subcategory ID and handles the response
     * accordingly, while also checking for network connectivity.
     *
     * @param subCategoryId subCategoryId is an optional integer parameter that represents the ID of a
     * subcategory. It is used as a parameter to fetch a list of options related to that subcategory.
     * If it is null, then the function will fetch all options available.
     */
    fun getAllOptionsListShowing(subCategoryId: Int?) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _optionsResponse.value = NetworkState.Loading()
                when (val response = mazaadyPortalRepository.getOptions(subCategoryId)) {
                    is NetworkState.Success -> {
                        _optionsResponse.value = handleOptionsListResponse(response)
                    }
                    is NetworkState.Error -> {
                        _optionsResponse.value =
                            NetworkState.Error(
                                response.message ?: "Error"
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    /**
     * The function handles the response of an options list API call and returns a network state object
     * indicating success, error, or empty response.
     *
     * @param response The parameter "response" is of type "NetworkState<AllOptionsResponse>", which
     * means it is an object that represents the current state of a network request for retrieving a
     * list of options. It can have one of three possible states: Success, Error, or Empty. The Success
     * state contains the retrieved
     * @return The function `handleOptionsListResponse` returns a `NetworkState` object that contains
     * either a `Success` state with the `AllOptionsResponse` data if the response code is 200, an
     * `Error` state with a message "No data found" if the response code is not 200, an `Error` state
     * with a custom message if the input `response` is already an `
     */
    private fun handleOptionsListResponse(response: NetworkState<AllOptionsResponse>): NetworkState<AllOptionsResponse> {
        return when (response) {
            is NetworkState.Success -> {
                if (response.data?.code == 200) {
                    NetworkState.Success(response.data)
                } else {
                    NetworkState.Error(message = "No data found")
                }
            }
            is NetworkState.Error -> NetworkState.Error(message = response.message ?: "Error")
            else -> NetworkState.Empty()
        }
    }

}