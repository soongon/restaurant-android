package com.example.restaurantapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RestaurantsViewModel(): ViewModel() {

    // 상태값을 ViewModel 에서 관리
    val state: MutableState<List<Restaurant>> =  mutableStateOf(dummyRestaurants)

    // 레스토랑 id 값으로 해당 레스토랑의 isFavorite 값을 변경
    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        state.value = restaurants
    }

}