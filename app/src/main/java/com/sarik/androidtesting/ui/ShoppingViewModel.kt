package com.sarik.androidtesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Resource
import com.sarik.androidtesting.data.local.ShoppingItem
import com.sarik.androidtesting.others.Constants
import com.sarik.androidtesting.others.Event
import com.sarik.androidtesting.repositories.ShoppingRepository
import kotlinx.coroutines.launch

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
class ShoppingViewModel @ViewModelInject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = shoppingRepository.observeAllShoppingItem()
    val totalPrice = shoppingRepository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> =
        _images // we use this from fragment and disable user to change livedata from fragment

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurrentImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemToDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amount: String, price: String) {
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Values are empty !", null)))
            return
        }

        if (name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("name is exceeds the length", null)))
            return
        }

        if (price.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("price is exceeds the length", null)))
            return
        }

        val amountInt = try {
            amount.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Enter valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItem(name, amountInt, price.toFloat(), _curImageUrl.value ?: "")
        insertShoppingItemToDB(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = shoppingRepository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

}