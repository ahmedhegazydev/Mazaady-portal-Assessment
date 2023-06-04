package com.mazaady.portal.di

import android.util.Log
import com.mazaady.portal.BuildConfig
import com.mazaady.portal.network.api.MazaadyPortalApi
import com.mazaady.portal.network.api.ApiHelper
import com.mazaady.portal.network.api.ApiHelperImpl
import com.mazaady.portal.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/* This code defines a Dagger module named `ApiModule` that provides dependencies related to network
API calls. */
object ApiModule {

    /* `private const val TAG = "MazaadyPortalApp"` is declaring a constant string variable named `TAG`
    with a value of "MazaadyPortalApp". This variable is used for logging purposes in the
    `provideOkHttpClient()` function, where it is passed as a tag to the `Log.d()` method to log
    messages with the tag "MazaadyPortalApp". This helps in identifying the source of log messages
    in the application's logcat output. */
    private const val TAG = "MazaadyPortalApp"

    @Provides
    @Singleton
    /**
     * This function provides an instance of OkHttpClient with a logging interceptor if the app is in
     * debug mode.
     */
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, message)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    /* This code defines a function named `provideRetrofit` that provides an instance of the Retrofit
    class. The function takes an instance of the `OkHttpClient` class as a parameter. The
    `@Provides` annotation indicates that this function is used to provide a dependency. The
    `@Singleton` annotation indicates that only one instance of the `Retrofit` class will be created
    and shared throughout the application. The function creates a new instance of the
    `Retrofit.Builder` class, sets the base URL to `Constants.BASE_URL`, adds a
    `GsonConverterFactory` to convert JSON responses to Kotlin objects, sets the `OkHttpClient`
    instance, and finally builds and returns the `Retrofit` instance. */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()

    /* This code defines a function named `provideMazaadyPortalApi` that provides an instance of the
    `MazaadyPortalApi` interface. The function takes an instance of the `Retrofit` class as a
    parameter. The `@Provides` annotation indicates that this function is used to provide a
    dependency. The `@Singleton` annotation indicates that only one instance of the
    `MazaadyPortalApi` interface will be created and shared throughout the application. The function
    creates a new instance of the `MazaadyPortalApi` interface using the `retrofit.create()` method
    and returns it. */
    @Provides
    @Singleton
    fun provideMazaadyPortalApi(retrofit: Retrofit): MazaadyPortalApi = retrofit.create(MazaadyPortalApi::class.java)

    /* This code defines a function named `provideApiHelper` that provides an instance of the
    `ApiHelper` interface. The function takes an instance of the `ApiHelperImpl` class as a
    parameter. The `@Provides` annotation indicates that this function is used to provide a
    dependency. The `@Singleton` annotation indicates that only one instance of the `ApiHelper`
    interface will be created and shared throughout the application. The function simply returns the
    `ApiHelperImpl` instance that was passed as a parameter. This allows other parts of the
    application to use the `ApiHelper` interface without having to worry about creating an instance
    of `ApiHelperImpl` themselves. */
    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}