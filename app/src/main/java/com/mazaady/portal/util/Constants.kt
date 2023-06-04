package com.mazaady.portal.util


/* The Constants class contains static variables for API key, base URL, and endpoint URLs for a
specific API. */
class Constants {
    companion object {
        /* `const val API_KEY = "3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16"` is declaring a constant
        variable named `API_KEY` with a value of `"3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16"`. This
        variable is used to store the API key for accessing the Mazaady portal API. */
        const val API_KEY = "3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16"
        /* `const val BASE_URL = " https://staging.mazaady.com/api/v1/"` is declaring a constant
        variable named `BASE_URL` with a value of `" https://staging.mazaady.com/api/v1/"`. This
        variable is used as the base URL for making API requests to the Mazaady portal. */
        const val BASE_URL = " https://staging.mazaady.com/api/v1/"


        const val ENDPOINT_URL_GET_ALL_CATEGORIES = "get_all_cats"
        const val ENDPOINT_URL_GET_ALL_SUB_CATEGORIES = "properties"
        const val ENDPOINT_URL_GET_ALL_OPTIONS = "get-options-child/{subCatId}"

    }
}