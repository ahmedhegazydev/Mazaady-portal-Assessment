package com.mazaady.portal.web

import com.mazaady.portal.data.model.*
import com.mazaady.portal.data.model.screen1.*
import com.mazaady.portal.state.NetworkState

/* The above code is a Kotlin object that contains three functions that return fake network state
objects with mock responses for different categories and options. The
`getFakeMainCategoriesResponse()` function returns a fake network state object containing a mock
response for all main categories. The `getFakeSubCategoriesResponse()` function returns a fake
network state object containing a list of subcategory items with various properties. The
`getFakeSubCategoryOptionsResponse()` function returns a successful network state with a fake
response containing a list of options. These functions are likely used for testing or development
purposes. */
object FakeDataUtil {

    /**
     * The function returns a fake network state object containing a mock response for all main
     * categories.
     *
     * @return a `NetworkState` object with a `Success` state and an `AllCategoriesResponse` object as
     * its data.
     */
    fun getFakeMainCategoriesResponse(): NetworkState<AllCategoriesResponse> {
        val allCategoriesResponse = AllCategoriesResponse(
            code = 200,
            msg = "Success",
            data = DataCategories(
                categories = mutableListOf(
                    MainCategoryItem(
                        isSelected = false,
                        id = 1,
                        name = "Category 1",
                        description = "Description 1",
                        image = "image_url_1",
                        slug = "category_1",
                        children = mutableListOf(
                            Children(
                                id = 11,
                                name = "Child 1",
                                description = "Child Description 1",
                                image = "child_image_url_1",
                                slug = "child_1"
                            ),
                            Children(
                                id = 12,
                                name = "Child 2",
                                description = "Child Description 2",
                                image = "child_image_url_2",
                                slug = "child_2"
                            )
                        ),
                        circleIcon = "circle_icon_url_1",
                        disableShipping = 0
                    ),
                    MainCategoryItem(
                        isSelected = true,
                        id = 2,
                        name = "Category 2",
                        description = "Description 2",
                        image = "image_url_2",
                        slug = "category_2",
                        children = mutableListOf(),
                        circleIcon = "circle_icon_url_2",
                        disableShipping = 1
                    )
                ),
                statistics = Statistics(
                    // statistics data here
                ),
                adsBanners = mutableListOf(
                    AdsBanners(
                        // ads banner data here
                    )
                ),
                iosVersion = "1.0.0",
                iosLatestVersion = "1.2.0",
                googleVersion = "1.0.0",
                huaweiVersion = "1.0.0"
            )
        )
        return NetworkState.Success(allCategoriesResponse)
    }

    /**
     * The function returns a fake network state object containing a list of subcategory items with
     * various properties.
     *
     * @return a `NetworkState` object with a `Success` state and an `AllSubPropertiesResponse` object
     * as its data. The `AllSubPropertiesResponse` object contains a list of `SubCategoryItem` objects
     * with various properties such as `id`, `name`, `description`, `options`, etc.
     */
    fun getFakeSubCategoriesResponse(): NetworkState<AllSubPropertiesResponse> {
        val allSubCategoryItem = AllSubPropertiesResponse(
            code = 200,
            msg = "Success",
            data = mutableListOf(
                SubCategoryItem(
                    isSelected = false,
                    id = 1,
                    name = "Subcategory 1",
                    description = "Description 1",
                    slug = "subcategory_1",
                    parent = "parent_category_1",
                    list = true,
                    type = "type_1",
                    value = "value_1",
                    otherValue = "other_value_1",
                    options = mutableListOf(
                        Options(
                            id = 111,
                            name = "Option 1",
                            slug = "option_1"
                        ),
                        Options(
                            id = 112,
                            name = "Option 2",
                            slug = "option_2"
                        )
                    )
                ),
                SubCategoryItem(
                    isSelected = true,
                    id = 2,
                    name = "Subcategory 2",
                    description = "Description 2",
                    slug = "subcategory_2",
                    parent = "parent_category_2",
                    list = false,
                    type = "type_2",
                    value = "value_2",
                    otherValue = "other_value_2",
                    options = mutableListOf()
                )
            )
        )

        return NetworkState.Success(allSubCategoryItem)
    }

    /**
     * The function returns a successful network state with a fake response containing a list of
     * options.
     *
     * @return a `NetworkState` object with a `Success` state and an `AllOptionsResponse` object as its
     * data. The `AllOptionsResponse` object contains a list of `Options` objects with their respective
     * `id`, `name`, and `slug` properties.
     */
    fun getFakeSubCategoryOptionsResponse(): NetworkState<AllOptionsResponse> {
        val allOptionsResponse = AllOptionsResponse(
            code = 200,
            msg = "Success",
            data = arrayListOf(
                Options(
                    id = 1,
                    name = "Option 1",
                    slug = "option_1"
                ),
                Options(
                    id = 2,
                    name = "Option 2",
                    slug = "option_2"
                ),
                Options(
                    id = 3,
                    name = "Option 3",
                    slug = "option_3"
                )
            )
        )

        return NetworkState.Success(allOptionsResponse)
    }

}