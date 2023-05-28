package ru.nsu.databases.ui.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.nsu.databases.data.repository.database.daos.feed_in_stock.FoodInStockDao
import ru.nsu.databases.domain.model.zoo.FoodInStock
import ru.nsu.databases.ui.base.live_data.SingleLiveEvent
import ru.nsu.databases.ui.base.live_data.update
import ru.nsu.databases.ui.base.view.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FoodInStockViewModel @Inject constructor(
    private val foodInStockDao: FoodInStockDao,
) : BaseViewModel() {

    private val _foodInStock: MutableLiveData<List<FoodInStock>> = MutableLiveData()
    val foodInStock: LiveData<List<FoodInStock>> = _foodInStock

    private val _navEvent = SingleLiveEvent<FoodInStockFragmentDirections>()
    val navEvent: LiveData<FoodInStockFragmentDirections> = _navEvent

    init {
        loadFoodInStock()
    }

    private fun loadFoodInStock() {
        foodInStockDao.getAll()
            .setupDefaultSchedulers()
            .bindLoading()
            .subscribe(
                ::onFoodStockLoaded,
                ::onError,
            ).unsubscribeOnCleared()
    }

    fun refreshStock() {
        foodInStockDao.getAll()
            .setupDefaultSchedulers()
            .subscribe(
                ::onFoodStockLoaded,
                ::onError,
            ).unsubscribeOnCleared()
    }

    private fun onFoodStockLoaded(foods: List<FoodInStock>) =
        _foodInStock.update { foods }

    fun onAddSupply() = _navEvent.update { FoodInStockFragmentDirections.ToAddSupply }

}