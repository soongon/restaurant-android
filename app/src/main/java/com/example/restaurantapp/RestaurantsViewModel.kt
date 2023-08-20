package com.example.restaurantapp

import androidx.lifecycle.ViewModel

class RestaurantsViewModel(): ViewModel() {
    fun getRestaurants() = dummyRestaurants
}