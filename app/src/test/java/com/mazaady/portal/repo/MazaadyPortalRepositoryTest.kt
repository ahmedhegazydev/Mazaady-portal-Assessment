package com.mazaady.portal.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.mazaady.portal.MainCoroutineRule
import com.mazaady.portal.web.MockWebServerBaseTest
import com.mazaady.portal.network.api.MazaadyPortalApi
import com.mazaady.portal.network.api.ApiHelper
import com.mazaady.portal.network.api.ApiHelperImpl
import com.mazaady.portal.network.repository.MazaadyPortalRepository
import com.google.common.truth.Truth.assertThat
import com.mazaady.portal.data.model.screen1.AllCategoriesResponse
import com.mazaady.portal.state.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
/* This is a test class for the MazaadyPortalRepository, which tests various scenarios for fetching
data from an API. */
class MazaadyPortalRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    /* `val instantTaskExecutorRule = InstantTaskExecutorRule()` is creating an instance of the
    `InstantTaskExecutorRule` class, which is a JUnit rule that ensures that the LiveData runs on
    the same thread in unit tests as it does in the app. This is necessary because LiveData is
    designed to run on the main thread, and running it on a background thread can cause issues. By
    using this rule, the LiveData is automatically set up to run on the same thread as the test. */
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    /* `var coroutineRule = MainCoroutineRule()` is creating an instance of the `MainCoroutineRule`
    class, which is a JUnit rule that swaps the default dispatcher used by Kotlin coroutines with a
    TestCoroutineDispatcher. This allows for testing of coroutines in a synchronous and controlled
    manner. The `coroutineRule` variable is then used in the test functions to run coroutines in a
    controlled environment. */
    var coroutineRule = MainCoroutineRule()
    /* `private lateinit var mazaadyPortalRepository: MazaadyPortalRepository` is declaring a private
    property `mazaadyPortalRepository` of type `MazaadyPortalRepository` and marking it with the
    `lateinit` keyword. This means that the property will be initialized later, before it is used,
    and the compiler will not enforce that it is initialized before use. This is useful for cases
    where the initialization of the property is complex or depends on other factors that cannot be
    determined at compile time. */
    private lateinit var mazaadyPortalRepository: MazaadyPortalRepository
    /* `private lateinit var apiHelper: ApiHelper` is declaring a private property `apiHelper` of type
    `ApiHelper` and marking it with the `lateinit` keyword. This means that the property will be
    initialized later, before it is used, and the compiler will not enforce that it is initialized
    before use. This is useful for cases where the initialization of the property is complex or
    depends on other factors that cannot be determined at compile time. */
    private lateinit var apiHelper: ApiHelper
    /* `private lateinit var mazaadyPortalApi: MazaadyPortalApi` is declaring a private property
    `mazaadyPortalApi` of type `MazaadyPortalApi` and marking it with the `lateinit` keyword. This
    means that the property will be initialized later, before it is used, and the compiler will not
    enforce that it is initialized before use. This is useful for cases where the initialization of
    the property is complex or depends on other factors that cannot be determined at compile time. */
    private lateinit var mazaadyPortalApi: MazaadyPortalApi
    /* `private lateinit var apiHelperImpl: ApiHelperImpl` is declaring a private property
    `apiHelperImpl` of type `ApiHelperImpl` and marking it with the `lateinit` keyword. This means
    that the property will be initialized later, before it is used, and the compiler will not
    enforce that it is initialized before use. This is useful for cases where the initialization of
    the property is complex or depends on other factors that cannot be determined at compile time. */
    private lateinit var apiHelperImpl: ApiHelperImpl
    /* `private lateinit var responseObserver: Observer<List<AllCategoriesResponse>>` is declaring a
    private property `responseObserver` of type `Observer<List<AllCategoriesResponse>>` and marking
    it with the `lateinit` keyword. This means that the property will be initialized later, before
    it is used, and the compiler will not enforce that it is initialized before use. This property
    is used to observe changes in a LiveData object that contains a list of `AllCategoriesResponse`
    objects. It is initialized in the `setup()` function and is used in the test functions to verify
    the expected behavior of the repository methods. */
    private lateinit var responseObserver: Observer<List<AllCategoriesResponse>>

    /**
     * This function returns a boolean value indicating whether the mock server is enabled or not.
     */
    override fun isMockServerEnabled(): Boolean = true

    @Before
    /**
     * The function sets up necessary components for testing an API service in a Kotlin application.
     */
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mazaadyPortalApi = provideTestApiService()
        apiHelperImpl = ApiHelperImpl(mazaadyPortalApi)
        apiHelper = apiHelperImpl
        mazaadyPortalRepository = MazaadyPortalRepository(apiHelper)
        responseObserver = Observer { }
    }

    @Test
    /**
     * The function tests if a list of main categories is returned when fetching results from an API
     * with a successful response.
     */
    fun `given response ok when fetching results then return a list with elements main categories`() {
        runBlocking {
            mockHttpResponse("all_main_categories_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getAllCategories()

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data?.categories).hasSize(13)
        }
    }

    @Test
    /**
     * This function tests that when fetching empty results, an empty list of main categories is
     * returned.
     */
    fun `given response ok when fetching empty results then return an empty list main categories`() {
        runBlocking {
            mockHttpResponse("main_cat_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getAllCategories()
            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data?.categories).hasSize(0)
        }
    }

    @Test
    /**
     * This function tests if an exception is returned when there is a response failure while fetching
     * results from an API.
     */
    fun `given response failure when fetching results then return exception main categories`() {
        runBlocking {
            mockHttpResponse(502)
            val apiResponse = mazaadyPortalRepository.getAllCategories()

            Assert.assertNotNull(apiResponse)
            val expectedValue = NetworkState.Error("An error occurred", null)
            assertThat(expectedValue.message).isEqualTo(apiResponse.message)
        }
    }

    @Test
    /**
     * This function tests if a list of sub properties is returned when fetching results with a
     * successful HTTP response.
     */
    fun `given response ok when fetching results then return a list with elements sub properties`() {
        runBlocking {
            mockHttpResponse("properties_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getAllSubProperties(categoryId = 13)

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data).hasSize(13)
        }
    }

    @Test
    /**
     * This function tests that an empty list is returned when fetching sub properties with an empty
     * result.
     */
    fun `given response ok when fetching empty results then return an empty list sub properties`() {
        runBlocking {
            mockHttpResponse("properties_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getAllSubProperties(categoryId = 13)
            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data).hasSize(0)
        }
    }

    @Test
    /**
     * This function tests if an exception is returned with specific properties when there is a
     * response failure while fetching results.
     */
    fun `given response failure when fetching results then return exception sub properties`() {
        runBlocking {
            mockHttpResponse(502)
            val apiResponse = mazaadyPortalRepository.getAllSubProperties(categoryId = 13)

            Assert.assertNotNull(apiResponse)
            val expectedValue = NetworkState.Error("An error occurred", null)
            assertThat(expectedValue.message).isEqualTo(apiResponse.message)
        }
    }

    @Test
    /**
     * This function tests if a list with elements sub options is returned when fetching results with a
     * successful response.
     */
    fun `given response ok when fetching results then return a list with elements sub options`() {
        runBlocking {
            mockHttpResponse("options_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getOptions(subCategoryId = 55)

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data).hasSize(1)
        }
    }

    @Test
    /**
     * This function tests that when fetching empty results, an empty list of options is returned.
     */
    fun `given response ok when fetching empty results then return an empty list options`() {
        runBlocking {
            mockHttpResponse("options_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = mazaadyPortalRepository.getOptions(subCategoryId = 55)
            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.data).hasSize(0)
        }
    }

    @Test
    /**
     * This function tests the error handling of a repository method that fetches options from an API.
     */
    fun `given response failure when fetching results then return exception options`() {
        runBlocking {
            mockHttpResponse(502)
            val apiResponse = mazaadyPortalRepository.getOptions(subCategoryId = 55)

            Assert.assertNotNull(apiResponse)
            val expectedValue = NetworkState.Error("An error occurred", null)
            assertThat(expectedValue.message).isEqualTo(apiResponse.message)
        }
    }

    @After
    /**
     * The function "release" is empty and does not perform any actions.
     */
    fun release() {
    }
}