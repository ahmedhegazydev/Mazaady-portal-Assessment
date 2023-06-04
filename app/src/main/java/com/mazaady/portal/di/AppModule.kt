package com.mazaady.portal.di

import android.content.Context
import com.mazaady.portal.MazaadyPortalApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/* This is a Dagger Hilt module named `AppModule` that provides dependencies for the application. */
object AppModule {

    @Singleton
    @Provides
    /**
     * This Kotlin function returns an instance of the MazaadyPortalApplication using the provided
     * application context.
     *
     * @param app The parameter `app` is of type `Context` and is annotated with `@ApplicationContext`.
     * This annotation is used to indicate that the `Context` passed in should be the application
     * context rather than an activity context. The `provideApplication` function returns an instance
     * of `MazaadyPortalApplication`,
     * @return an instance of the `MazaadyPortalApplication` class, which is obtained by casting the
     * `@ApplicationContext` parameter `app` to the `MazaadyPortalApplication` type.
     */
    fun provideApplication(@ApplicationContext app: Context): MazaadyPortalApplication {
        return app as MazaadyPortalApplication
    }


    @ApplicationScope
    @Provides
    @Singleton
    /**
     * The function returns a CoroutineScope with a SupervisorJob for application-level coroutines.
     *
     * @return A `CoroutineScope` object is being returned. The `CoroutineScope` is created with a
     * `SupervisorJob`, which is a type of job that allows child coroutines to fail independently of
     * their parent coroutine. This function is likely used to provide a scope for coroutines that are
     * related to the application and need to be managed independently of other coroutines.
     */
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
/* The class defines an annotation for marking a scope as an application scope. */
annotation class ApplicationScope

