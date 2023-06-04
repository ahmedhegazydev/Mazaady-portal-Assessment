package com.mazaady.portal.ui.static_screen

import androidx.lifecycle.ViewModel
import com.mazaady.portal.network.repository.MazaadyPortalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
/* This is a Kotlin class definition for a ViewModel called `ScreenTwoStaticViewModel`. The
`@HiltViewModel` annotation indicates that this ViewModel will be injected by Hilt, a dependency
injection library for Android. The `@Inject` annotation on the constructor indicates that the
`mazaadyPortalRepository` parameter will be injected by Hilt. */
class ScreenTwoStaticViewModel @Inject constructor(
    private val mazaadyPortalRepository: MazaadyPortalRepository
) : ViewModel() {

}