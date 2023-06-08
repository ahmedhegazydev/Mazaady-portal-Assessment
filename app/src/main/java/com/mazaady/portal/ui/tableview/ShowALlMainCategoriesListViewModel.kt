package com.mazaady.portal.ui.tableview

import androidx.lifecycle.ViewModel
import com.mazaady.portal.di.CoroutinesDispatcherProvider
import com.mazaady.portal.network.repository.MazaadyPortalRepository
import com.mazaady.portal.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "TableViewShowSelectedVM"

@HiltViewModel
class TableViewShowSelectedViewModel @Inject constructor(
    private val mazaadyPortalRepository: MazaadyPortalRepository,
    private val networkHelper: NetworkHelper,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider


) : ViewModel() {

}