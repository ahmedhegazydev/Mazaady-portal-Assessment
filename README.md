# Mazaady Portal App

The App is using the [Mazaady Portal Api](https://staging.mazaady.com/api/v1) to
add a new product.

- Kotlin
- Hilt-Dagger
- Retrofit
- Coroutines
- LiveData
- ViewModel
- ViewBinding
- Jetpack Navigation
- Glide

### Project Architecture

This app uses **MVVM (Model View View-Model)** architecture.
MVVM provides better separation of concern, easier testing, lifecycle awareness, etc.

## Clean Architecture

Clean architecture promotes separation of concerns, making the code loosely coupled. This results in
a more testable and flexible code. This approach divides the project into 3 modules: presentation,
data, and domain.

* __Presentation__: Layer with the Android Framework, the MVVM pattern and the DI module. Depends on
  the domain to access the use cases and on di, to inject dependencies.
* __Domain__: Layer with the business logic. Contains the use cases, in charge of calling the
  correct repository or data member.
* __Data__: Layer with the responsibility of selecting the proper data source for the domain layer.
  It contains the implementations of the repositories declared in the domain layer.

## Functionality

The app's functionality includes:

1. Fetch All Available Main/Sub Categories data from https://staging.mazaady.com/api/v1 & show them in `RecylerView` appeared inside the `MyBottomSheetDialogFragment`. 
2. When an main-category item is selected from `RecyclerView` it will load the children of related sub-categories.
3. Every item inside the `RecyclerView` is a single-option selectable with a check image icon. 
4. After selecting a sub-category item a list of sub-properties and options loaded for this selected parent item. 
5. Then after finishing selecting all desired data, You can click the submit button for showing all selected data as  Table.
6. On Clicking the second tab of bottom navigation item, The user can show the 2nd screen of static UI.


### UI

The UI consists of two parts

1. `View` - Activity screen, Host the navigation component fragments.
2. `Fragment` - Contains three fragments:

   a) `ScreenOneAllCatsListFragment` - Enabling the user for adding a new product by selecting the Main/Sub Categories and all options he likes.
   
   b) `ScreenTwoStaticFragment` - Show a static UI.

### Model

Model is generated from `JSON` data into a Kotlin data class.
saving/retrieving custom object data.

### ViewModel

`ShowALlMainCategoriesListViewModel.kt`

Used for fetching all available Main/Sub Categories, Options, Properties & update states. Also send out the status of the network call like
Loading, Success, Error using `sealed` class.

### Dependency Injection

The app uses `Dagger-hilt` as a dependency injection library.

The `ApplicationModule.kt` class provides  `Singleton` reference for `Retrofit`, `OkHttpClient`
, `Repository` etc.

### Network

The network layer is composed of Repository, ApiService.
`MazaadyPortalApi` - Is an interface containing the suspend functions for retrofit API call.

`MazaadyPortalRepository` - Holds the definition of the remote repository call.

## Building

In-order to successfully run & test the application you will need an `api key`.

Now Go to - `app/src/main/java/utils/Constants.kt`

And replace

`const val API_KEY = "YOUR_API_KEY"`

You can open the project in Android studio and press run.

Gradle plugin used in the project will require `Java 11.0` to run.

you can set the gradle jdk in `Preferences->Build Tools->Gradle->Gradle JDK`

## Tech Stack

1. [Android appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
   , [KTX](https://developer.android.com/kotlin/ktx)
   , [Constraint layout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
   , [Material Support](https://material.io/develop/android/docs/getting-started).
2. [Android View Binding](https://developer.android.com/topic/libraries/view-binding)
3. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency
   injection.
4. [Retrofit](https://square.github.io/retrofit/) for REST API communication
5. [Coroutine](https://developer.android.com/kotlin/coroutines) for Network call
6. [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
   , [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
7. [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
8. [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
   for supporting navigation through the app.
9. [Glide](https://github.com/bumptech/glide) for image loading.
10. [Mockito](https://developer.android.com/training/testing/local-tests)
    & [Junit](https://developer.android.com/training/testing/local-tests) for Unit testing.
11. [Robolectric](http://robolectric.org/) for Instrumentation testing.
12. [Truth](https://truth.dev/) for Assertion in testing.
13. [Espresso](https://developer.android.com/training/testing/espresso) for UI testing.

## Testing

Unit testing has been added for `ShowALlMainCategoriesListViewModel` & `MazaadyPortalRepository`.

### `ViewModelTest.kt`

Test the viewmodel of the app using `CoroutineRule` & `Stateflow value`.

The test cases comprise of testing different states like Loading, Success, Error with fake data for
testing Main/Sub-Categories Response.

### `MazaadyPortalRepositoryTest.kt`

Test the Repository of the app using `Robolectric`.

[Mock Webserver](https://github.com/square/okhttp/tree/master/mockwebserver) is used to test the
Network api response in case of successful data, empty, failed case.

### Project Github Repo

[Repo Here]()