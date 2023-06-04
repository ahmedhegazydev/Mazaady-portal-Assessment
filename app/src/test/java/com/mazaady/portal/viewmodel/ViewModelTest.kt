
package com.mazaady.portal.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mazaady.portal.web.FakeDataUtil
import com.mazaady.portal.MainCoroutineRule
import com.mazaady.portal.network.repository.MazaadyPortalRepository
import com.mazaady.portal.provideFakeCoroutinesDispatcherProvider
import com.mazaady.portal.runBlockingTest
import com.mazaady.portal.state.NetworkState
import com.mazaady.portal.ui.list.ShowALlMainCategoriesListViewModel
import com.mazaady.portal.util.NetworkHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
/* This is a Kotlin test class for a ViewModel that tests various scenarios for retrieving data from a
repository. */
class ViewModelTest {
    @get:Rule
    /* The above code is declaring a variable named `instantTaskExecutorRule` and assigning it an
    instance of the `InstantTaskExecutorRule` class. This class is typically used in Android testing
    to ensure that tasks are executed synchronously on the main thread. */
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    /* `var coroutineRule = MainCoroutineRule()` is creating an instance of the `MainCoroutineRule`
    class, which is a JUnit rule that swaps the default dispatcher used by coroutines with a
    TestCoroutineDispatcher. This allows for testing of coroutines in a synchronous and controlled
    manner. The `coroutineRule` variable is then used in the tests to provide the
    TestCoroutineDispatcher to the coroutinesDispatcherProvider. */
    var coroutineRule = MainCoroutineRule()

    @Mock
    /* The above code is declaring a private property `networkHelper` of type `NetworkHelper` in
    Kotlin. The `lateinit` keyword is used to indicate that the property will be initialized later,
    before it is used. This property is likely to be used to handle network-related operations in
    the code. */
    private lateinit var networkHelper: NetworkHelper

    @Mock
    /* `private lateinit var mazaadyPortalRepository: MazaadyPortalRepository` is declaring a private
    property `mazaadyPortalRepository` of type `MazaadyPortalRepository` that is marked with the
    `lateinit` modifier. This means that the property will be initialized at a later time and the
    compiler will not enforce initialization before usage. The property is used in the `setUp()`
    function to initialize the `viewModel` with the `MazaadyPortalRepository` dependency. */
    private lateinit var mazaadyPortalRepository: MazaadyPortalRepository

    /* `private val testDispatcher = coroutineRule.testDispatcher` is creating a private property
    `testDispatcher` of type `TestCoroutineDispatcher` and initializing it with the `testDispatcher`
    property of the `coroutineRule` instance. The `testDispatcher` property is a
    TestCoroutineDispatcher that is used to provide a controlled and synchronous environment for
    testing coroutines. This property is used in the tests to provide the TestCoroutineDispatcher to
    the coroutinesDispatcherProvider. */
    private val testDispatcher = coroutineRule.testDispatcher

    /* `private lateinit var viewModel: ShowALlMainCategoriesListViewModel` is declaring a private
    property `viewModel` of type `ShowALlMainCategoriesListViewModel` that is marked with the
    `lateinit` modifier. This means that the property will be initialized at a later time and the
    compiler will not enforce initialization before usage. The property is used in the `setUp()`
    function to initialize the `viewModel` with the `ShowALlMainCategoriesListViewModel` dependency. */
    private lateinit var viewModel: ShowALlMainCategoriesListViewModel

    @Before
    /**
     * This function sets up a test environment for a Kotlin class that uses Mockito and initializes a
     * view model with dependencies.
     */
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ShowALlMainCategoriesListViewModel(
            mazaadyPortalRepository = mazaadyPortalRepository,
            networkHelper = networkHelper,
            coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher)
        )
    }

    @Test
    /**
     * This is a unit test function written in Kotlin that checks if the view model returns a loading
     * state for main categories when called.
     */
    fun `when calling for results then return loading state main categories`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            `when`(mazaadyPortalRepository.getAllCategories())
                .thenReturn(NetworkState.Loading())

            //When
            viewModel.getAllMainCategoriesListShowing()

            //Then
            assertThat(viewModel.mainCatResponse.value).isNotNull()
            assertThat(viewModel.mainCatResponse.value.data).isNull()
            assertThat(viewModel.mainCatResponse.value.message).isNull()
        }
    }

    @Test
    /**
     * This is a unit test function written in Kotlin that tests the getAllMainCategoriesListShowing()
     * function of a ViewModel by mocking network and repository responses and asserting that the
     * returned main categories are not empty.
     */
    fun `when calling for results then return results main categories`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)

            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getAllCategories())
                .thenAnswer { (FakeDataUtil.getFakeMainCategoriesResponse()) }

            //When
            viewModel.getAllMainCategoriesListShowing()

            //then
            assertThat(viewModel.mainCatResponse.value).isNotNull()
            val categories = viewModel.mainCatResponse.value.data?.data?.categories
            assertThat(categories?.isNotEmpty())
        }
    }

    @Test
    /**
     * This is a unit test function that checks if an error message is returned when calling a function
     * to get all main categories and there is an error in the network or repository.
     */
    fun `when calling for results then return error main categories`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getAllCategories())
                .thenAnswer { NetworkState.Error("Error occurred", null) }

            //When
            viewModel.getAllMainCategoriesListShowing()

            //then
            val response = viewModel.mainCatResponse.value
            assertThat(response.message).isNotNull()
            assertThat(response.message).isEqualTo("Error occurred")
        }
    }

    @Test
    /**
     * This is a unit test function in Kotlin that checks if the sub properties loading state sub
     * properties are returned when calling for results.
     */
    fun `when calling for results then return loading state sub properties`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            `when`(mazaadyPortalRepository.getAllSubProperties(categoryId = 13))
                .thenReturn(NetworkState.Loading())

            //When
            viewModel.getAllSubCategoriesListShowing(categoryId = 13)

            //Then
            assertThat(viewModel.subPropertiesResponse.value).isNotNull()
            assertThat(viewModel.subPropertiesResponse.value.data).isNull()
            assertThat(viewModel.subPropertiesResponse.value.message).isNull()
        }
    }

    @Test
    /**
     * This function tests if a ViewModel returns sub properties when calling a repository method with
     * a specific category ID.
     */
    fun `when calling for results then return results sub properties`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)

            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getAllSubProperties(categoryId = 13))
                .thenAnswer { (FakeDataUtil.getFakeSubCategoriesResponse()) }

            //When
            viewModel.getAllSubCategoriesListShowing(categoryId = 13)

            //then
            assertThat(viewModel.subPropertiesResponse.value).isNotNull()
            val categories = viewModel.subPropertiesResponse.value.data?.data
            assertThat(categories?.isNotEmpty())
        }
    }

    @Test
    /**
     * This function tests that when calling for results, an error message is returned for sub
     * categories.
     */
    fun `when calling for results then return error sub categories`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getAllSubProperties(categoryId = 13))
                .thenAnswer { NetworkState.Error("Error occurred", null) }

            //When
            viewModel.getAllSubCategoriesListShowing(categoryId = 13)

            //then
            val response = viewModel.mainCatResponse.value
            assertThat(response.message).isNotNull()
            assertThat(response.message).isEqualTo("Error occurred")
        }
    }


    @Test
    /**
     * This is a unit test function that checks if the view model returns a loading state for sub
     * categories options when called.
     */
    fun `when calling for results then return loading state sub categories options`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            `when`(mazaadyPortalRepository.getOptions(subCategoryId = 55))
                .thenReturn(NetworkState.Loading())

            //When
            viewModel.getAllOptionsListShowing(subCategoryId = 55)

            //Then
            assertThat(viewModel.optionsResponse.value).isNotNull()
            assertThat(viewModel.optionsResponse.value.data).isNull()
            assertThat(viewModel.optionsResponse.value.message).isNull()
        }
    }

    @Test
    /**
     * This function tests if the ViewModel returns sub category options when getAllOptionsListShowing
     * is called.
     */
    fun `when calling for results then return results sub categories options`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)

            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getOptions(subCategoryId  = 55))
                .thenAnswer { (FakeDataUtil.getFakeSubCategoryOptionsResponse()) }

            //When
            viewModel.getAllOptionsListShowing(subCategoryId = 55)

            //then
            assertThat(viewModel.optionsResponse.value).isNotNull()
            val categories = viewModel.optionsResponse.value.data?.data
            assertThat(categories?.isNotEmpty())
        }
    }

    @Test
    /**
     * This is a unit test function in Kotlin that tests if an error message is returned when calling a
     * function to get sub category options.
     */
    fun `when calling for results then return error sub categories options`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake favorites
            `when`(mazaadyPortalRepository.getOptions(subCategoryId  = 55))
                .thenAnswer { NetworkState.Error("Error occurred", null) }

            //When
            viewModel.getAllOptionsListShowing(subCategoryId = 55)

            //then
            val response = viewModel.optionsResponse.value
            assertThat(response.message).isNotNull()
            assertThat(response.message).isEqualTo("Error occurred")
        }
    }

    @After
    /**
     * The function clears inline mocks in the Mockito framework.
     */
    fun release() {
        Mockito.framework().clearInlineMocks()
    }
}