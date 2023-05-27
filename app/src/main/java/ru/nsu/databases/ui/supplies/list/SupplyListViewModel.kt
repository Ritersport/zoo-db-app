package ru.nsu.databases.ui.supplies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.feed_supplies.FeedSuppliesDao
import ru.nsu.databases.domain.model.zoo.FeedSupply
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SupplyListViewModel @Inject constructor(
    private val suppliesDao: FeedSuppliesDao,
) : BaseViewModel() {

    private val _supplies: MutableLiveData<List<FeedSupply>> = MutableLiveData()
    val supplies: LiveData<List<FeedSupply>> = _supplies

    private val _navEvent = SingleLiveEvent<SupplyFragmentDirections>()
    val navEvent: LiveData<SupplyFragmentDirections> = _navEvent

    init {
        loadSuppliesList()
    }

    private fun loadSuppliesList() {
        suppliesDao.getAll()
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onSupplyResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    fun refreshSupplies() {
        suppliesDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onSupplyResult,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onSupplyResult(supplies: List<FeedSupply>) =
        _supplies.update { supplies.sortedByDescending { it.supplyDate } }

    fun onAddSupply() = _navEvent.update { SupplyFragmentDirections.ToAddSupply }

}