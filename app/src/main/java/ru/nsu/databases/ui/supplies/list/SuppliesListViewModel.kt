package ru.nsu.databases.ui.supplies.list

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.feed_supplies.FeedSuppliesDao
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SuppliesListViewModel @Inject constructor(
    private val suppliesDao: FeedSuppliesDao
) : BaseViewModel() {

}