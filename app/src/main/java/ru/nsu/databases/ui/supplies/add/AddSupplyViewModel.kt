package ru.nsu.databases.ui.supplies.add

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.feed_supplies.FeedSuppliesDao
import ru.nsu.databases.data.repository.database.daos.vendor.VendorsDao
import ru.nsu.databases.domain.model.zoo.FeedSupply
import ru.nsu.databases.domain.model.zoo.Vendor
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.push
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddSupplyViewModel @Inject constructor(
    private val vendorsDao: VendorsDao,
    private val suppliesDao: FeedSuppliesDao,
) : BaseViewModel() {

    private val _goBack = SingleLiveEvent<Unit>()
    val goBack: LiveData<Unit> = _goBack

    private val _vendors = SingleLiveEvent<List<Vendor>>()
    val vendors: LiveData<List<Vendor>> = _vendors

    init {
        loadVendors()
    }

    private fun loadVendors() {
        vendorsDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onVendors,
                ::onError
            ).unsubscribeOnCleared()
    }

    fun onSubmit(supply: FeedSupply) {
        suppliesDao.addSupply(supply)
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onAddSupplySuccess,
                ::onError
            ).unsubscribeOnCleared()
    }

    private fun onVendors(vendors: List<Vendor>) = _vendors.update { vendors }

    private fun onAddSupplySuccess() = _goBack.push()
}